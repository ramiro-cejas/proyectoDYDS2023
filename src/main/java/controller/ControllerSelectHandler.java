package controller;

import utils.DataBase;

public class ControllerSelectHandler {
    private final ControllerVideoGameWiki controllerVideoGameWiki;

    public ControllerSelectHandler(ControllerVideoGameWiki controllerVideoGameWiki) {
        this.controllerVideoGameWiki = controllerVideoGameWiki;
    }

    public void onEventSelectStored(String titleStored) {
        Thread thread = (new Thread(() -> {
            controllerVideoGameWiki.getViewVideoGameWiki().setWorkingStatus();
            controllerVideoGameWiki.getModelVideoGameWiki().searchStoredResult(titleStored);
            controllerVideoGameWiki.getViewVideoGameWiki().setWaitingStatus();
        }));
        thread.start();
    }

    public void onEventSelectHistory(String elementOfHistoryComboBox) {
        String[] arrayOfSplitedString = elementOfHistoryComboBox.split("\\.");
        Thread thread = (new Thread(() -> {
            controllerVideoGameWiki.getViewVideoGameWiki().setWorkingStatus();
            controllerVideoGameWiki.getModelVideoGameWiki().searchTermByPageId(DataBase.getPageIdFromSavedHistorySearch(Integer.parseInt(arrayOfSplitedString[0])));
            controllerVideoGameWiki.getViewVideoGameWiki().setWaitingStatus();
        }));
        thread.start();
    }
}