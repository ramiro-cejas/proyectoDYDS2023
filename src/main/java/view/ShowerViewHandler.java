package view;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import utils.SearchResult;

import javax.swing.*;

public class ShowerViewHandler {
    private final ViewVideoGameWikiLogic viewVideoGameWikiLogic;

    public ShowerViewHandler(ViewVideoGameWikiLogic viewVideoGameWikiLogic) {
        this.viewVideoGameWikiLogic = viewVideoGameWikiLogic;
    }

    void showResult() {
        viewVideoGameWikiLogic.getTabbedPane1().setSelectedIndex(1);
        viewVideoGameWikiLogic.getTextPaneResult().setText(viewVideoGameWikiLogic.getModelVideoGameWiki().getLastSearchResult());
        viewVideoGameWikiLogic.getTextPaneResult().setCaretPosition(0);
    }

    void showPartialResults() {
        JsonArray jsonOfPartialResults = viewVideoGameWikiLogic.getModelVideoGameWiki().getParcialResults();
        for (JsonElement jsonElementOfPartialResult : jsonOfPartialResults) {
            JsonObject jsonObjectOfPartialResult = jsonElementOfPartialResult.getAsJsonObject();
            SearchResult searchResult = getSearchResult(jsonObjectOfPartialResult);
            viewVideoGameWikiLogic.searchOptionsMenu.add(searchResult);
            setListenersOfOptionsMenu(searchResult);
        }
        viewVideoGameWikiLogic.searchOptionsMenu.show(viewVideoGameWikiLogic.getTextFieldSearchTerm(), viewVideoGameWikiLogic.getTextFieldSearchTerm().getX(), viewVideoGameWikiLogic.getTextFieldSearchTerm().getY());
    }

    private void setListenersOfOptionsMenu(SearchResult searchResult) {
        searchResult.addActionListener(actionEvent -> {
            viewVideoGameWikiLogic.setWorkingStatus();
            viewVideoGameWikiLogic.getControllerVideoGameWiki().onEventSearchSelectedResult(searchResult, viewVideoGameWikiLogic.getTextFieldSearchTerm().getText());
        });
    }

    private static SearchResult getSearchResult(JsonObject jsonObject) {
        String searchResultTitle = jsonObject.get("title").getAsString();
        String searchResultPageId = jsonObject.get("pageid").getAsString();
        String searchResultSnippet = jsonObject.get("snippet").getAsString();

        return new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
    }
}