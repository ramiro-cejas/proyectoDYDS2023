package model;

import utils.ApiHandler;
import utils.DataBase;
import utils.ResultInPlainText;
import utils.SearchResult;

import java.sql.SQLException;

public class ModelSearchHandler {
    private final ModelVideoGameWiki modelVideoGameWiki;
    private ApiHandler apiHandler;

    ModelSearchHandler(ModelVideoGameWiki modelVideoGameWiki) {
        this.modelVideoGameWiki = modelVideoGameWiki;
        apiHandler = new ApiHandler();
    }

    void searchTerm(String termToSearch) {
        try {
            modelVideoGameWiki.setParcialResults(apiHandler.searchForTerm(termToSearch));
            modelVideoGameWiki.getModelNotifier().notifyParcialSearchHasFinished();
        } catch (Exception e) {
            modelVideoGameWiki.getModelNotifier().notifyExceptionSearchTerm();
        }
    }

    void searchTermByPageId(String pageId) {
        try {
            ResultInPlainText resultInPlainText = apiHandler.getExtractByPageID(pageId);
            modelVideoGameWiki.setSelectedResultInPlainText(resultInPlainText);
            modelVideoGameWiki.searchElementsFromHistory();
            modelVideoGameWiki.getModelNotifier().notifyQueryHasFinished();
        } catch (Exception e) {
            modelVideoGameWiki.getModelNotifier().notifyExceptionSearchByID();
        }
    }

    void searchSelectedTerm(SearchResult searchResult, String searchTerm) {
        try {
            ResultInPlainText resultInPlainText = apiHandler.getExtractByPageID(searchResult.pageID);
            modelVideoGameWiki.setSelectedResultInPlainText(resultInPlainText);
            modelVideoGameWiki.getModelSaveHandler().saveHistoryOfTermSearched(searchResult.title, searchTerm, searchResult.pageID);
            modelVideoGameWiki.searchElementsFromHistory();
            modelVideoGameWiki.getModelNotifier().notifyQueryHasFinished();
        } catch (Exception e) {
            modelVideoGameWiki.getModelNotifier().notifyExceptionSearchByID();
        }
    }

    ApiHandler getApiHandler() {
        return apiHandler;
    }

    void setApiHandler(ApiHandler apiHandler) {
        this.apiHandler = apiHandler;
    }

    void searchElementsFromHistory() {
        try {
            modelVideoGameWiki.setElementsFromHistory(DataBase.getHistory().toArray());
            modelVideoGameWiki.getModelNotifierHandler().notifySearchFromHistoryHasFinished();
        } catch (SQLException e) {
            modelVideoGameWiki.getModelNotifierHandler().notifyExceptionSQL();
        }
    }
}