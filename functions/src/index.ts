/**
 * Import function triggers from their respective submodules:
 *
 * import {onCall} from "firebase-functions/v2/https";
 * import {onDocumentWritten} from "firebase-functions/v2/firestore";
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

import {setGlobalOptions} from "firebase-functions";
import {onCall, HttpsError} from "firebase-functions/v2/https";
import * as logger from "firebase-functions/logger";
import {initializeApp} from "firebase-admin/app";
import {getFirestore, Timestamp, Query, DocumentReference} from "firebase-admin/firestore";

initializeApp();

setGlobalOptions({maxInstances: 10});

/**
 * Interface for purchase request
 */
interface PurchaseRequest {
    itemId: string;
    itemType: "theme" | "bundle";
}

export const purchaseItemWithCredits = onCall<PurchaseRequest>(async (request) => {
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
    if (itemType === "theme") {
        itemRef = db.doc(`Store/Merchandise/Themes/${itemId}`);
    } else if (itemType === "bundle") {
        itemRef = db.doc(`Store/Merchandise/Bundles/${itemId}`);
    } else {
        throw new HttpsError("invalid-argument", "Invalid item type.");
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
            if (itemType === "theme") {
                transaction.set(unlockHistoryRef.doc(itemId), {
                    type: "Single Theme",
                    dateUnlocked: now,
                }, {merge: true});
            } else if (itemType === "bundle") {
                // Unlock the bundle itself (or just its contents?)
                // Based on app code, it seems themes are unlocked individually.
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
                // Also mark the bundle as purchased if needed
                /* transaction.set(unlockHistoryRef.doc(itemId), {
                    type: "Theme Bundle",
                    dateUnlocked: now,
                }, {merge: true}); */
            }

            return {success: true, newBalance: earnedCredits - price};
        });

        return result;
    } catch (error) {
        logger.error("Purchase failed:", error);
        if (error instanceof HttpsError) throw error;
        throw new HttpsError("internal", "An internal error occurred during purchase.");
    }
});

/**
 * Interface for Typography query options
 */
interface TypographyQueryRequest {
    filterField?: string;
    filterValue?: any;
    orderField?: string;
    orderDirection?: "ASCENDING" | "DESCENDING";
    limit?: number;
}

export const fetchTypographies = onCall<TypographyQueryRequest>(async (request) => {
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
});

/**
 * Interface for Palette query options
 */
interface PaletteQueryRequest {
    filterField?: string;
    filterValue?: any;
    orderField?: string;
    orderDirection?: "ASCENDING" | "DESCENDING";
    limit?: number;
}

export const fetchPalettes = onCall<PaletteQueryRequest>(async (request) => {
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
});


