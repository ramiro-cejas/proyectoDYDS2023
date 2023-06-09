package controller;

import model.ModelVideoGameWikiInterface;
import utils.SearchResult;
import view.ViewVideoGameWikiInterface;

public interface ControllerVideoGameWikiInterface {

    void onEventSearch();

    void onEventSearchSelectedResult(SearchResult sr, String searchTerm);

    void onEventSaveSearched(String resultBody);

    void onEventSelectStored(String titleStored);

    void onEventDeleteStoredResult(String titleStored);

    void onEventUpdateStroredResult(String titleToUpdate, String bodyToUpdate);

    void onEventSelectHistory(String elementOfHistoryComboBox);

    ViewVideoGameWikiInterface getViewVideoGameWiki();

    ModelVideoGameWikiInterface getModelVideoGameWiki();

    void setView(ViewVideoGameWikiInterface viewVideoGameWikiLogic);

    void searchTitlesFromSavedResults();

    void searchElementsFromHistory();
}
