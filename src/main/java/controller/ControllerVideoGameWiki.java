package controller;

import dyds.videogameInfo.fulllogic.DataBase;
import dyds.videogameInfo.fulllogic.SearchResult;
import model.ModelVideoGameWiki;
import view.View;

public class ControllerVideoGameWiki {
    private ModelVideoGameWiki modelVideoGameWiki;
    private View viewVideoGameWiki;
    private Thread taskThread;

    public ControllerVideoGameWiki(ModelVideoGameWiki modelVideoGameWiki) {
        this.modelVideoGameWiki = modelVideoGameWiki;
    }

    public void start() {
        viewVideoGameWiki = new View(this, modelVideoGameWiki);
        viewVideoGameWiki.showView();
    }

    public void setViewVideoGameWiki(View viewVideoGameWiki){
        this.viewVideoGameWiki = viewVideoGameWiki;
    }

    public void onEventSearch() {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.searchTerm(viewVideoGameWiki.getTextofTermToSearch());
            viewVideoGameWiki.setWatingStatus();
        });
        taskThread.start();
    }

    public void onEventSearchSelectedResult(SearchResult sr) {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.searchSelectedTerm(sr);
            viewVideoGameWiki.setWatingStatus();
        });
        taskThread.start();
    }

    public void onEventSaveSearched(String resultBody) {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.updateStoredResult(resultBody);
            viewVideoGameWiki.setWatingStatus();
        });
        taskThread.start();
    }

    public void onEventSelectStored(String titleStored) {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.searchStoredResult(titleStored);
            viewVideoGameWiki.setWatingStatus();
        });
        taskThread.start();
    }

    public void onEventDeleteStoredResult(String titleStored) {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.deleteStoredResult(titleStored);
            viewVideoGameWiki.setWatingStatus();
        });
        taskThread.start();
    }

    public void onEventUpdateStroredResult(String titleToUpdate, String bodyToUpdate) {
        taskThread = new Thread(() -> {
            viewVideoGameWiki.setWorkingStatus();
            modelVideoGameWiki.updateStoredResult(titleToUpdate,bodyToUpdate);
            viewVideoGameWiki.setWatingStatus();
        });
        taskThread.start();
    }
}
