package view;

import utils.ResultInPlainText;
import utils.SearchResult;

import java.util.List;

public class ViewShowerHandler {
    private final ViewVideoGameWikiLogic viewVideoGameWikiLogic;

    public ViewShowerHandler(ViewVideoGameWikiLogic viewVideoGameWikiLogic) {
        this.viewVideoGameWikiLogic = viewVideoGameWikiLogic;
    }

    void showResult() {
        viewVideoGameWikiLogic.getTabbedPane().setSelectedIndex(1);
        viewVideoGameWikiLogic.getTextPaneResult().setText(TextTools.doHTMLFromResultInPlainText(viewVideoGameWikiLogic.getModelVideoGameWiki().getLastSearchResult()));
        viewVideoGameWikiLogic.getTextPaneResult().setCaretPosition(0);
    }

    void showPartialResults() {
        List<ResultInPlainText> resultList = viewVideoGameWikiLogic.getModelVideoGameWiki().getParcialResults();
        viewVideoGameWikiLogic.searchOptionsMenu.removeAll();
        for (ResultInPlainText resultInPlainText : resultList) {
            SearchResult searchResult = getSearchResult(resultInPlainText);
            viewVideoGameWikiLogic.searchOptionsMenu.add(searchResult);
            setListenersOfOptionsMenu(searchResult);
        }
        viewVideoGameWikiLogic.searchOptionsMenu.show(viewVideoGameWikiLogic.getTextFieldSearchTerm(), viewVideoGameWikiLogic.getTextFieldSearchTerm().getX(), viewVideoGameWikiLogic.getTextFieldSearchTerm().getY());
    }

    private SearchResult getSearchResult(ResultInPlainText resultInPlainText) {
        return new SearchResult(resultInPlainText.title,resultInPlainText.pageID,resultInPlainText.snippet);
    }

    private void setListenersOfOptionsMenu(SearchResult searchResult) {
        searchResult.addActionListener(actionEvent -> {
            viewVideoGameWikiLogic.setWorkingStatus();
            viewVideoGameWikiLogic.getControllerVideoGameWiki().onEventSearchSelectedResult(searchResult, viewVideoGameWikiLogic.getTextFieldSearchTerm().getText());
        });
    }
}