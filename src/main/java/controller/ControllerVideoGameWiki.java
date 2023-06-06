package controller;

import model.ModelVideoGameWikiInterface;
import utils.SearchResult;
import view.ViewVideoGameWiki;
import view.ViewVideoGameWikiInterface;

public class ControllerVideoGameWiki {
    private final ModelVideoGameWikiInterface modelVideoGameWiki;
    private final ControllerSearchHandler controllerSearchHandler = new ControllerSearchHandler(this);
    private final ControllerStoredHandler controllerStoredHandler = new ControllerStoredHandler(this);
    private final ControllerSelectHandler controllerSelectHandler = new ControllerSelectHandler(this);
    private final ControllerHistoryHandler controllerHistoryHandler = new ControllerHistoryHandler(this);
    private ViewVideoGameWikiInterface viewVideoGameWiki;

    public ControllerVideoGameWiki(ModelVideoGameWikiInterface modelVideoGameWiki) {
        this.modelVideoGameWiki = modelVideoGameWiki;
    }

    public void start() {
        viewVideoGameWiki = new ViewVideoGameWiki(this, modelVideoGameWiki);
        viewVideoGameWiki.start();
    }

    public void onEventSearch() {
        controllerSearchHandler.onEventSearch();
    }

    public void onEventSearchSelectedResult(SearchResult sr, String searchTerm) {
        controllerSearchHandler.onEventSearchSelectedResult(sr, searchTerm);
    }

    public void onEventSaveSearched(String resultBody) {
        controllerHistoryHandler.onEventSaveSearched(resultBody);
    }

    public void onEventSelectStored(String titleStored) {
        controllerSelectHandler.onEventSelectStored(titleStored);
    }

    public void onEventDeleteStoredResult(String titleStored) {
        controllerStoredHandler.onEventDeleteStoredResult(titleStored);
    }

    public void onEventUpdateStroredResult(String titleToUpdate, String bodyToUpdate) {
        controllerStoredHandler.onEventUpdateStoredResult(titleToUpdate, bodyToUpdate);
    }

    public void onEventSelectHistory(String elementOfHistoryComboBox) {
        controllerSelectHandler.onEventSelectHistory(elementOfHistoryComboBox);
    }

    public ViewVideoGameWikiInterface getViewVideoGameWiki() {
        return viewVideoGameWiki;
    }

    public ModelVideoGameWikiInterface getModelVideoGameWiki() {
        return modelVideoGameWiki;
    }
}