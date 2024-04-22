package com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data;

import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.investigationtype.GhostList;
import com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels.EvidenceViewModel;

public class GhostOrderData {

    private final EvidenceViewModel evidenceViewModel;

    private int[] prevOrder;
    private int[] currOrder;

    public GhostOrderData(EvidenceViewModel evidenceViewModel) {
        this.evidenceViewModel = evidenceViewModel;
    }

    /**
     * createOrder
     * Initializes both current and previous order of ghosts to default order
     */
    public void createOrder() {

        createPrevOrder();
        createCurrOrder();

    }

    /**
     * createPrevOrder
     * Initializes both current and previous order of ghosts to default order
     */
    private void createPrevOrder() {

        prevOrder = new int[InvestigationData.ghostList.getList().size()];

        for(int i = 0; i < prevOrder.length; i++) {
            prevOrder[i] = i;
        }

    }

    /**
     * createCurrOrder
     * Initializes both current and previous order of ghosts to default order
     */
    private void createCurrOrder() {

        currOrder = new int[InvestigationData.ghostList.getList().size()];

        for(int i = 0; i < currOrder.length; i++) {
            currOrder[i] = i;
        }

    }

    public void updateOrder() {

        int[] newOrder = new int[InvestigationData.ghostList.getList().size()];

        // Replace previous with current
        if(currOrder == null) {
            createCurrOrder();
        }
        if(prevOrder == null) {
            createPrevOrder();
        }
        System.arraycopy(currOrder, 0, prevOrder, 0, currOrder.length);

        // Set default numbers into placeholder array
        for(int i = 0; i < newOrder.length; i++) {
            newOrder[i] = i;
        }

        // Order placeholder array based on scores
        for (int i = 0; i < newOrder.length - 1; ) {

            GhostList ghostList = evidenceViewModel.getInvestigationData().getGhostList();

            int ratingA = ghostList.getAt(newOrder[i]).getEvidenceScore();
            int ratingB = ghostList.getAt(newOrder[i + 1]).getEvidenceScore();

            if (ratingA < ratingB) {
                int t = newOrder[i + 1];
                newOrder[i + 1] = newOrder[i];
                newOrder[i] = t;

                if (i > 0) {
                    i--;
                }
            } else {
                i++;
            }
        }

        // Replace current with placeholders
        currOrder = newOrder;
    }

    public int[] getPrevOrder() {
        if(prevOrder == null) {
            createPrevOrder();
        }

        return prevOrder;
    }

    public int[] getCurrOrder() {
        if(currOrder == null) {
            createCurrOrder();
        }

        return currOrder;
    }

    public boolean hasChanges() {

        if(prevOrder == null) {
            createPrevOrder();
        }
        if(currOrder == null) {
            createCurrOrder();
        }

        for(int i = 0; i < currOrder.length; i++) {
            if(currOrder[i] != prevOrder[i])
                return true;
        }

        return false;
    }

}
