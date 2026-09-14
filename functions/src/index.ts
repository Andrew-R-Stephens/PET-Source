/**
 * Import function triggers from their respective submodules:
 *
 * import {onCall} from "firebase-functions/v2/https";
 * import {onDocumentWritten} from "firebase-functions/v2/firestore";
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

import {setGlobalOptions} from "firebase-functions";
import {onCall, HttpsError, CallableRequest} from "firebase-functions/v2/https";
import * as logger from "firebase-functions/logger";
import {initializeApp} from "firebase-admin/app";
import {getFirestore, Timestamp, Query, DocumentReference, Firestore, Transaction} from "firebase-admin/firestore";

initializeApp();

setGlobalOptions({maxInstances: 10});

/**
 * Interface for marketplace agreement request
 */
interface MarketplaceAgreementRequest {
    isAgreementShown: boolean;
    version?: number;
}

/*
 * Set marketplace agreement state
 */
export const setMarketplaceAgreementState = onCall<MarketplaceAgreementRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return setMarketplaceAgreementState_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

/*
 * Set marketplace agreement state
 * Version 1
 */
async function setMarketplaceAgreementState_v1(request: CallableRequest<MarketplaceAgreementRequest>) {
    const auth = request.auth;
    if (!auth) {
        throw new HttpsError("unauthenticated", "User must be authenticated to update preferences.");
    }

    const {isAgreementShown} = request.data;
    const uid = auth.uid;

    const db = getFirestore();
    const userRef = db.doc(`Users/${uid}`);
    const preferencesRef = db.doc(`Users/${uid}/Account/Preferences`);

    try {
        await db.runTransaction(async (transaction) => {
            // Ensure parent documents exist to avoid "ghost" documents in the console
            transaction.set(userRef, {}, {merge: true});

            transaction.set(preferencesRef, {
                marketplaceAgreementShown: isAgreementShown
            }, {merge: true});
        });

        return {success: true, isAgreementShown};
    } catch (error) {
        logger.error("Set marketplace agreement state failed:", error);
        if (error instanceof HttpsError) throw error;
        throw new HttpsError("internal", "An internal error occurred while updating marketplace agreement state.");
    }
}

/**
 * Interface for purchase request
 */
interface PurchaseRequest {
    itemId: string;
    itemType: "theme" | "typography" | "bundle";
    version?: number;
}

/*
 * Purchase item with credits
 */
export const purchaseItemWithCredits = onCall<PurchaseRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return purchaseItemWithCredits_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

/*
 * Purchase item with credits
 * Version 1
 */
async function purchaseItemWithCredits_v1(request: CallableRequest<PurchaseRequest>) {
    const auth = request.auth;
    if (!auth) {
        throw new HttpsError("unauthenticated", "User must be authenticated to purchase items.");
    }

    const {itemId, itemType} = request.data;
    const uid = auth.uid;
    const db = getFirestore();

    try {
        switch (itemType) {
        case "theme":
            return await purchaseThemeTransaction_v1(db, uid, itemId);
        case "typography":
            return await purchaseTypographyTransaction_v1(db, uid, itemId);
        case "bundle":
            return await purchaseBundleTransaction_v1(db, uid, itemId);
        default:
            throw new HttpsError("invalid-argument", "Invalid item type.");
        }
    } catch (error) {
        logger.error("Purchase failed:", error);
        if (error instanceof HttpsError) throw error;
        throw new HttpsError("internal", "An internal error occurred during purchase.");
    }
}

/**
 * Helper to calculate bundle pricing based on unlocked items.
 */
function calculateBundlePricing(
    bundleBuyCredits: number,
    unlockedCount: number,
    totalCount: number,
    lockedCount: number,
    oneLockedItemPrice: number | null
): number {
    const bundlePrice = (lockedCount === 1 && oneLockedItemPrice !== null) ?
        oneLockedItemPrice : bundleBuyCredits;

    const proratedDiscountRatio = totalCount > 0 ? unlockedCount / totalCount : 0;
    const proratedDiscount = Math.floor(bundlePrice * proratedDiscountRatio);
    const finalPrice = bundlePrice - proratedDiscount;

    return finalPrice;
}

/**
 * Helper to process credit deduction and item fetching within a transaction.
 */
async function processPurchaseTransaction(
    transaction: Transaction,
    itemRef: DocumentReference,
    userCreditsRef: DocumentReference,
    userRef: DocumentReference,
    transactionHistoryRef: DocumentReference,
    priceOverride?: number
) {
    const now = Timestamp.now();

    // 1. Get Item Data
    let price = 0;
    let itemData = null;
    if (priceOverride !== undefined) {
        price = priceOverride;
    } else {
        const itemSnap = await transaction.get(itemRef);
        if (!itemSnap.exists) {
            throw new HttpsError("not-found", "Item not found.");
        }
        itemData = itemSnap.data();
        price = itemData?.buyCredits || 0;
    }

    // 2. Get User Credits
    const creditsSnap = await transaction.get(userCreditsRef);
    let earnedCredits = 0;
    let spentCredits = 0;

    if (creditsSnap.exists) {
        const creditsData = creditsSnap.data();
        earnedCredits = creditsData?.earnedCredits || 0;
        spentCredits = creditsData?.spentCredits || 0;
    }

    // 3. Check Balance
    if (earnedCredits < price) {
        throw new HttpsError("failed-precondition", "Insufficient credits.");
    }

    // 4. Update Credits & Ensure Parents Exist (Avoid ghost documents)
    transaction.set(userRef, {}, {merge: true});
    transaction.set(transactionHistoryRef, {}, {merge: true});

    transaction.set(userCreditsRef, {
        earnedCredits: earnedCredits - price,
        spentCredits: spentCredits + price,
    }, {merge: true});

    return {price, newBalance: earnedCredits - price, itemData, now};
}

/*
 * Transaction logic for purchasing a theme
 */
async function purchaseThemeTransaction_v1(db: Firestore, uid: string, itemId: string) {
    const itemRef = db.doc(`Store/Merchandise/Themes/${itemId}`);
    const userRef = db.doc(`Users/${uid}`);
    const userCreditsRef = db.doc(`Users/${uid}/Account/Credits`);
    const transactionHistoryRef = db.doc(`Users/${uid}/Account/TransactionHistory`);
    const unlockHistoryRef = db.collection(`Users/${uid}/Account/TransactionHistory/UnlockHistory`);

    return db.runTransaction(async (transaction) => {
        const {newBalance, now} = await processPurchaseTransaction(
            transaction, itemRef, userCreditsRef, userRef, transactionHistoryRef
        );

        transaction.set(unlockHistoryRef.doc(itemId), {
            type: "Single Theme",
            dateUnlocked: now,
        }, {merge: true});

        return {success: true, newBalance};
    });
}

/*
 * Transaction logic for purchasing a typography
 */
async function purchaseTypographyTransaction_v1(db: Firestore, uid: string, itemId: string) {
    const itemRef = db.doc(`Store/Merchandise/Typographies/${itemId}`);
    const userRef = db.doc(`Users/${uid}`);
    const userCreditsRef = db.doc(`Users/${uid}/Account/Credits`);
    const transactionHistoryRef = db.doc(`Users/${uid}/Account/TransactionHistory`);
    const unlockHistoryRef = db.collection(`Users/${uid}/Account/TransactionHistory/UnlockHistory`);

    return db.runTransaction(async (transaction) => {
        const {newBalance, now} = await processPurchaseTransaction(
            transaction, itemRef, userCreditsRef, userRef, transactionHistoryRef
        );

        transaction.set(unlockHistoryRef.doc(itemId), {
            type: "Typography",
            dateUnlocked: now,
        }, {merge: true});

        return {success: true, newBalance};
    });
}

/*
 * Transaction logic for purchasing a bundle
 */
async function purchaseBundleTransaction_v1(db: Firestore, uid: string, itemId: string) {
    const itemRef = db.doc(`Store/Merchandise/Bundles/${itemId}`);
    const userRef = db.doc(`Users/${uid}`);
    const userCreditsRef = db.doc(`Users/${uid}/Account/Credits`);
    const transactionHistoryRef = db.doc(`Users/${uid}/Account/TransactionHistory`);
    const unlockHistoryRef = db.collection(`Users/${uid}/Account/TransactionHistory/UnlockHistory`);

    return db.runTransaction(async (transaction) => {
        // 1. Get Bundle Data
        const bundleSnap = await transaction.get(itemRef);
        if (!bundleSnap.exists) {
            throw new HttpsError("not-found", "Bundle not found.");
        }
        const bundleData = bundleSnap.data();
        const bundleBuyCredits = bundleData?.buyCredits || 0;
        const itemRefs = bundleData?.items as DocumentReference[] | undefined;

        if (!itemRefs || !Array.isArray(itemRefs)) {
            throw new HttpsError("internal", "Bundle has no items.");
        }

        // 2. Fetch all item snapshots and their unlock status
        const itemSnaps = await Promise.all(itemRefs.map((ref) => transaction.get(ref)));
        const unlockSnaps = await Promise.all(itemRefs.map((ref) => transaction.get(unlockHistoryRef.doc(ref.id))));

        let unlockedCount = 0;
        let oneLockedItemPrice: number | null = null;
        const totalCount = itemRefs.length;

        for (let i = 0; i < totalCount; i++) {
            if (unlockSnaps[i].exists) {
                unlockedCount++;
            } else {
                oneLockedItemPrice = itemSnaps[i].data()?.buyCredits || 0;
            }
        }

        const lockedCount = totalCount - unlockedCount;
        if (lockedCount === 0) {
            throw new HttpsError("failed-precondition", "Bundle already fully unlocked.");
        }

        // 3. Calculate Dynamic Price
        const finalPrice = calculateBundlePricing(
            bundleBuyCredits,
            unlockedCount,
            totalCount,
            lockedCount,
            lockedCount === 1 ? oneLockedItemPrice : null
        );

        const {newBalance, now} = await processPurchaseTransaction(
            transaction,
            itemRef,
            userCreditsRef,
            userRef,
            transactionHistoryRef,
            finalPrice
        );

        itemRefs.forEach((ref, index) => {
            if (!unlockSnaps[index].exists) {
                transaction.set(unlockHistoryRef.doc(ref.id), {
                    type: "Bundle Theme",
                    dateUnlocked: now,
                    bundleRef: itemRef,
                }, {merge: true});
            }
        });

        return {success: true, newBalance};
    });
}


/**
 * Interface for query options
 */
interface QueryRequest {
    filterField?: string;
    filterValue?: any;
    orderField?: string;
    orderDirection?: "ASCENDING" | "DESCENDING";
    limit?: number;
    version?: number;
}

/*
 * Fetch typographies
 */
export const fetchTypographies = onCall<QueryRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return fetchTypographies_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

/*
 * Fetch typographies
 * Version 1
 */
async function fetchTypographies_v1(request: CallableRequest<QueryRequest>) {
    const {filterField, filterValue, orderField, orderDirection, limit} = request.data;

    const db = getFirestore();
    let query: Query = db.collection("Store/Merchandise/Typographies");

    if (filterField && filterValue !== undefined && filterValue !== null) {
        query = query.where(filterField, "==", filterValue);
    }

    if (orderField) {
        const direction = orderDirection === "DESCENDING" ? "desc" : "asc";
        query = query.orderBy(orderField, direction);
    }

    if (limit) {
        query = query.limit(limit);
    }

    try {
        const snapshot = await query.get();
        const typographies = snapshot.docs.map((doc) => {
            const data = doc.data();
            return {
                uuid: doc.id,
                name: data.name || "",
                group: data.group || "",
                buyCredits: data.buyCredits || 0,
            };
        });

        return typographies;
    } catch (error) {
        logger.error("Error fetching typographies:", error);
        if (error instanceof HttpsError) throw error;
        throw new HttpsError("internal", "An error occurred while fetching typographies.");
    }
}

/*
 * Fetch palettes
 */
export const fetchPalettes = onCall<QueryRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return fetchPalettes_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

/*
 * Fetch palettes
 * Version 1
 */
async function fetchPalettes_v1(request: CallableRequest<QueryRequest>) {
    const {filterField, filterValue, orderField, orderDirection, limit} = request.data;

    const db = getFirestore();
    let query: Query = db.collection("Store/Merchandise/Themes");

    if (filterField && filterValue !== undefined && filterValue !== null) {
        query = query.where(filterField, "==", filterValue);
    }

    if (orderField) {
        const direction = orderDirection === "DESCENDING" ? "desc" : "asc";
        query = query.orderBy(orderField, direction);
    }

    if (limit) {
        query = query.limit(limit);
    }

    try {
        const snapshot = await query.get();
        const palettes = snapshot.docs.map((doc) => {
            const data = doc.data();
            return {
                uuid: doc.id,
                name: data.name || "",
                group: data.group || "",
                buyCredits: data.buyCredits || 0,
            };
        });

        return palettes;
    } catch (error) {
        logger.error("Error fetching palettes:", error);
        if (error instanceof HttpsError) throw error;
        throw new HttpsError("internal", "An error occurred while fetching palettes.");
    }
}

/*
 * Fetch theme bundles
 */
export const fetchBundles = onCall<QueryRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return fetchBundles_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

/*
 * Fetch theme bundles
 * Version 1
 */
async function fetchBundles_v1(request: CallableRequest<QueryRequest>) {
    const {filterField, filterValue, orderField, orderDirection, limit} = request.data;

    const db = getFirestore();
    let query: Query = db.collection("Store/Merchandise/Bundles");

    if (filterField && filterValue !== undefined && filterValue !== null) {
        query = query.where(filterField, "==", filterValue);
    }

    if (orderField) {
        const direction = orderDirection === "DESCENDING" ? "desc" : "asc";
        query = query.orderBy(orderField, direction);
    }

    if (limit) {
        query = query.limit(limit);
    }

    try {
        const snapshot = await query.get();
        const bundles = snapshot.docs.map((doc) => {
            const data = doc.data();
            const themeUUIDs: string[] = [];
            if (data.items && Array.isArray(data.items)) {
                data.items.forEach((item: any) => {
                    if (item instanceof DocumentReference) {
                        themeUUIDs.push(item.id);
                    } else if (typeof item === "string") {
                        themeUUIDs.push(item);
                    }
                });
            }

            return {
                uuid: doc.id,
                name: data.name || "",
                buyCredits: data.buyCredits || 0,
                items: themeUUIDs,
            };
        });

        return bundles;
    } catch (error) {
        logger.error("Error fetching bundles:", error);
        if (error instanceof HttpsError) throw error;
        throw new HttpsError("internal", "An error occurred while fetching bundles.");
    }
}

/**
 * Interface for Add Credits request
 */
interface AddCreditsRequest {
    credits: number;
    version?: number;
}

/*
 * Add credits to user account
 */
export const addCredits = onCall<AddCreditsRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return addCredits_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

/*
 * Add credits to user account
 * Version 1
 */
async function addCredits_v1(request: CallableRequest<AddCreditsRequest>) {
    const auth = request.auth;
    if (!auth) {
        throw new HttpsError("unauthenticated", "User must be authenticated to add credits.");
    }

    const {credits} = request.data;
    if (credits <= 0) {
        throw new HttpsError("invalid-argument", "Credits must be a positive number.");
    }

    const uid = auth.uid;
    const db = getFirestore();
    const userRef = db.doc(`Users/${uid}`);
    const userCreditsRef = db.doc(`Users/${uid}/Account/Credits`);

    try {
        const result = await db.runTransaction(async (transaction) => {
            const creditsSnap = await transaction.get(userCreditsRef);
            let earnedCredits = 0;
            let spentCredits = 0;

            if (creditsSnap.exists) {
                const creditsData = creditsSnap.data();
                earnedCredits = creditsData?.earnedCredits || 0;
                spentCredits = creditsData?.spentCredits || 0;
            }

            const newEarnedCredits = earnedCredits + credits;

            // Ensure parent document exists to avoid "ghost" documents in the console
            transaction.set(userRef, {}, {merge: true});

            transaction.set(userCreditsRef, {
                earnedCredits: newEarnedCredits,
                spentCredits: spentCredits
            }, {merge: true});

            return {success: true, newBalance: newEarnedCredits};
        });

        return result;
    } catch (error) {
        logger.error("Add credits failed:", error);
        if (error instanceof HttpsError) throw error;
        throw new HttpsError("internal", "An internal error occurred while adding credits.");
    }
}
