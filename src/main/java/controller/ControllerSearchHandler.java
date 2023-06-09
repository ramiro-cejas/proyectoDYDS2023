package controller;

import utils.SearchResult;

public class ControllerSearchHandler {
    private final ControllerVideoGameWikiInterface controllerVideoGameWiki;

    ControllerSearchHandler(ControllerVideoGameWikiInterface controllerVideoGameWiki) {
        this.controllerVideoGameWiki = controllerVideoGameWiki;
    }

    void onEventSearch() {
        Thread thread = (new Thread(() -> {
            controllerVideoGameWiki.getViewVideoGameWiki().setWorkingStatus();
            controllerVideoGameWiki.getModelVideoGameWiki().searchTerm(controllerVideoGameWiki.getViewVideoGameWiki().getTextofTermToSearch());
            controllerVideoGameWiki.getViewVideoGameWiki().setWaitingStatus();
        }));
        thread.start();
    }

    void onEventSearchSelectedResult(SearchResult sr, String searchTerm) {
        Thread thread = (new Thread(() -> {
            controllerVideoGameWiki.getViewVideoGameWiki().setWorkingStatus();
            controllerVideoGameWiki.getModelVideoGameWiki().searchSelectedTerm(sr, searchTerm);
            controllerVideoGameWiki.getViewVideoGameWiki().setWaitingStatus();
        }));
        thread.start();
    }
}