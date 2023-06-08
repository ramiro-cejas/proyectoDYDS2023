package controller;

import model.ModelVideoGameWikiInterface;
import utils.SearchResult;
import view.ViewVideoGameWikiInterface;

public class ControllerVideoGameWiki implements ControllerVideoGameWikiInterface {
    private final ModelVideoGameWikiInterface modelVideoGameWiki;
    private final ControllerSearchHandler controllerSearchHandler = new ControllerSearchHandler(this);
    private final ControllerStoredHandler controllerStoredHandler = new ControllerStoredHandler(this);
    private final ControllerSelectHandler controllerSelectHandler = new ControllerSelectHandler(this);
    private final ControllerHistoryHandler controllerHistoryHandler = new ControllerHistoryHandler(this);
    private ViewVideoGameWikiInterface viewVideoGameWiki;

    public ControllerVideoGameWiki(ModelVideoGameWikiInterface modelVideoGameWiki) {
        this.modelVideoGameWiki = modelVideoGameWiki;
    }

    @Override
    public void setView(ViewVideoGameWikiInterface viewVideoGameWiki) {
        this.viewVideoGameWiki = viewVideoGameWiki;
    }

    @Override
    public void onEventSearch() {
        controllerSearchHandler.onEventSearch();
    }

    @Override
    public void onEventSearchSelectedResult(SearchResult sr, String searchTerm) {
        controllerSearchHandler.onEventSearchSelectedResult(sr, searchTerm);
    }

    @Override
    public void onEventSaveSearched(String resultBody) {
        controllerHistoryHandler.onEventSaveSearched(resultBody);
    }

    @Override
    public void onEventSelectStored(String titleStored) {
        controllerSelectHandler.onEventSelectStored(titleStored);
    }

    @Override
    public void onEventDeleteStoredResult(String titleStored) {
        controllerStoredHandler.onEventDeleteStoredResult(titleStored);
    }

    @Override
    public void onEventUpdateStroredResult(String titleToUpdate, String bodyToUpdate) {
        controllerStoredHandler.onEventUpdateStoredResult(titleToUpdate, bodyToUpdate);
    }

    @Override
    public void onEventSelectHistory(String elementOfHistoryComboBox) {
        controllerSelectHandler.onEventSelectHistory(elementOfHistoryComboBox);
    }

    @Override
    public ViewVideoGameWikiInterface getViewVideoGameWiki() {
        return viewVideoGameWiki;
    }

    @Override
    public ModelVideoGameWikiInterface getModelVideoGameWiki() {
        return modelVideoGameWiki;
    }
}