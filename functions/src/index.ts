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
import {getFirestore, Timestamp, Query, DocumentReference} from "firebase-admin/firestore";

initializeApp();

setGlobalOptions({maxInstances: 10});

/**
 * Interface for marketplace agreement request
 */
interface MarketplaceAgreementRequest {
    isAgreementShown: boolean;
    version?: number;
}

export const setMarketplaceAgreementState = onCall<MarketplaceAgreementRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return setMarketplaceAgreementState_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

async function setMarketplaceAgreementState_v1(request: CallableRequest<MarketplaceAgreementRequest>) {
    const auth = request.auth;
    if (!auth) {
        throw new HttpsError("unauthenticated", "User must be authenticated to update preferences.");
    }

    const {isAgreementShown} = request.data;
    const uid = auth.uid;

    const db = getFirestore();
    const purchaseDocRef = db.doc(`Users/${uid}/Account/TransactionHistory/PurchaseHistory/PurchaseItem`);

    try {
        await db.runTransaction(async (transaction) => {
            const snapshot = await transaction.get(purchaseDocRef);
            if (!snapshot.exists) {
                transaction.set(purchaseDocRef, {
                    marketplaceAgreementShown: isAgreementShown,
                });
            }
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
 */
interface PurchaseRequest {
    itemId: string;
    itemType: "theme" | "typography" | "bundle";
    version?: number;
}

export const purchaseItemWithCredits = onCall<PurchaseRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return purchaseItemWithCredits_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

async function purchaseItemWithCredits_v1(request: CallableRequest<PurchaseRequest>) {
    const auth = request.auth;
    if (!auth) {
        throw new HttpsError("unauthenticated", "User must be authenticated to purchase items.");
    }

    const {itemId, itemType} = request.data;
    const uid = auth.uid;

    const db = getFirestore();
    const userCreditsRef = db.doc(`Users/${uid}/Account/Credits`);
    const unlockHistoryRef = db.collection(`Users/${uid}/Account/TransactionHistory/UnlockHistory`);

    let itemRef: DocumentReference;

    switch (itemType) {
    case "theme": {
        itemRef = db.doc(`Store/Merchandise/Themes/${itemId}`);
        break;
    }
    case "bundle": {
        itemRef = db.doc(`Store/Merchandise/Bundles/${itemId}`);
        break;
    }
    default: {
        throw new HttpsError("invalid-argument", "Invalid item type.");
    }
    }

    try {
        const result = await db.runTransaction(async (transaction) => {
            // 1. Get Item Data
            const itemSnap = await transaction.get(itemRef);
            if (!itemSnap.exists) {
                throw new HttpsError("not-found", "Item not found.");
            }
            const itemData = itemSnap.data();
            const price = itemData?.buyCredits || 0;

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

            // 4. Update Credits
            transaction.set(userCreditsRef, {
                earnedCredits: earnedCredits - price,
                spentCredits: spentCredits + price,
            }, {merge: true});

            // 5. Unlock Item(s)
            const now = Timestamp.now();

            switch (itemType) {
            case "theme": {
                transaction.set(
                    unlockHistoryRef.doc(itemId), {
                        type: "Single Theme",
                        dateUnlocked: now,
                    }, {merge: true}
                );
                break;
            }
            case "bundle": {
                const themeRefs = itemData?.items as DocumentReference[] | undefined;
                if (themeRefs && Array.isArray(themeRefs)) {
                    themeRefs.forEach((ref) => {
                        transaction.set(unlockHistoryRef.doc(ref.id), {
                            type: "Bundle Theme",
                            dateUnlocked: now,
                            bundleRef: itemRef,
                        }, {merge: true});
                    });
                }
                break;
            }
            }

            return {success: true, newBalance: earnedCredits - price};
        });

        return result;
    } catch (error) {
        logger.error("Purchase failed:", error);
        if (error instanceof HttpsError) throw error;
        throw new HttpsError("internal", "An internal error occurred during purchase.");
    }
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

export const fetchTypographies = onCall<QueryRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return fetchTypographies_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

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
        throw new HttpsError("internal", "An error occurred while fetching typographies.");
    }
}

export const fetchPalettes = onCall<QueryRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return fetchPalettes_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

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
        throw new HttpsError("internal", "An error occurred while fetching palettes.");
    }
}

export const fetchBundles = onCall<QueryRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return fetchBundles_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

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

export const addCredits = onCall<AddCreditsRequest>(async (request) => {
    const version = request.data.version || 1;
    switch (version) {
    case 1:
        return addCredits_v1(request);
    default:
        throw new HttpsError("invalid-argument", `Unsupported version: ${version}`);
    }
});

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

            transaction.set(userCreditsRef, {
                earnedCredits: newEarnedCredits,
                spentCredits: spentCredits,
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
