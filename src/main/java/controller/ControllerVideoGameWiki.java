package controller;

import utils.DataBase;
import utils.SearchResult;
import model.ModelVideoGameWiki;
import view.ViewVideoGameWiki;

public class ControllerVideoGameWiki {
    private ModelVideoGameWiki modelVideoGameWiki;
    private ViewVideoGameWiki viewVideoGameWiki;
    private Thread taskThread;

    public ControllerVideoGameWiki(ModelVideoGameWiki modelVideoGameWiki) {
        this.modelVideoGameWiki = modelVideoGameWiki;
    }

    public void start() {
        viewVideoGameWiki = new ViewVideoGameWiki(this, modelVideoGameWiki);
        viewVideoGameWiki.showView();
    }

    public void setViewVideoGameWiki(ViewVideoGameWiki viewVideoGameWiki){
        this.viewVideoGameWiki = viewVideoGameWiki;
    }

    public void onEventSearch() {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.searchTerm(viewVideoGameWiki.getTextofTermToSearch());
            viewVideoGameWiki.setWaitingStatus();
        });
        taskThread.start();
    }

    public void onEventSearchSelectedResult(SearchResult sr, String searchTerm) {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.searchSelectedTerm(sr,searchTerm);
            viewVideoGameWiki.setWaitingStatus();
        });
        taskThread.start();
    }

    public void onEventSaveSearched(String resultBody) {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.saveSearchedResult(resultBody);
            viewVideoGameWiki.setWaitingStatus();
        });
        taskThread.start();
    }

    public void onEventSelectStored(String titleStored) {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.searchStoredResult(titleStored);
            viewVideoGameWiki.setWaitingStatus();
        });
        taskThread.start();
    }

    public void onEventDeleteStoredResult(String titleStored) {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.deleteStoredResult(titleStored);
            viewVideoGameWiki.setWaitingStatus();
        });
        taskThread.start();
    }

    public void onEventUpdateStroredResult(String titleToUpdate, String bodyToUpdate) {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.updateStoredResult(titleToUpdate,bodyToUpdate);
            viewVideoGameWiki.setWaitingStatus();
        });
        taskThread.start();
    }

    public void onEventSelectHistory(String elementOfHistoryComboBox) {
        String[] arrayOfSplitedString = elementOfHistoryComboBox.split("\\.");
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.searchTermByPageId(DataBase.getPageIdFromSavedHistorySearch(Integer.parseInt(arrayOfSplitedString[0])));
            viewVideoGameWiki.setWaitingStatus();
        });
        taskThread.start();
    }
}