package controller;

import utils.DataBase;

import java.sql.SQLException;

public class ControllerSelectHandler {
    private final ControllerVideoGameWikiInterface controllerVideoGameWiki;

    public ControllerSelectHandler(ControllerVideoGameWikiInterface controllerVideoGameWiki) {
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
            try {
                controllerVideoGameWiki.getModelVideoGameWiki().searchTermByPageId(DataBase.getPageIdFromSavedHistorySearch(Integer.parseInt(arrayOfSplitedString[0])));
            } catch (SQLException e) {
                controllerVideoGameWiki.getViewVideoGameWiki().getPopUPHandler().showSQLException();
            }
            controllerVideoGameWiki.getViewVideoGameWiki().setWaitingStatus();
        }));
        thread.start();
    }
}