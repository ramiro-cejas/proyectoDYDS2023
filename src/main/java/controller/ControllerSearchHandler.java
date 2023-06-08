package controller;

import utils.SearchResult;

public class ControllerSearchHandler {
    private final ControllerVideoGameWikiInterface controllerVideoGameWiki;

    public ControllerSearchHandler(ControllerVideoGameWikiInterface controllerVideoGameWiki) {
        this.controllerVideoGameWiki = controllerVideoGameWiki;
    }

    public void onEventSearch() {
        Thread thread = (new Thread(() -> {
            controllerVideoGameWiki.getViewVideoGameWiki().setWorkingStatus();
            controllerVideoGameWiki.getModelVideoGameWiki().searchTerm(controllerVideoGameWiki.getViewVideoGameWiki().getTextofTermToSearch());
            controllerVideoGameWiki.getViewVideoGameWiki().setWaitingStatus();
        }));
        thread.start();
    }

    public void onEventSearchSelectedResult(SearchResult sr, String searchTerm) {
        Thread thread = (new Thread(() -> {
            controllerVideoGameWiki.getViewVideoGameWiki().setWorkingStatus();
            controllerVideoGameWiki.getModelVideoGameWiki().searchSelectedTerm(sr, searchTerm);
            controllerVideoGameWiki.getViewVideoGameWiki().setWaitingStatus();
        }));
        thread.start();
    }
}