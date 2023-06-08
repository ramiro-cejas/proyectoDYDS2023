package model;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.ApiHandler;
import utils.ResultInPlainText;
import utils.SearchResult;
import utils.WikipediaPageAPI;

public class ModelSearchHandler {
    private final ModelVideoGameWikiInterface modelVideoGameWiki;
    private ApiHandler apiHandler;

    ModelSearchHandler(ModelVideoGameWikiInterface modelVideoGameWiki) {
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

    public ApiHandler getApiHandler() {
        return apiHandler;
    }

    public void setApiHandler(ApiHandler apiHandler) {
        this.apiHandler = apiHandler;
    }

    public ModelVideoGameWikiInterface getModelVideoGameWiki() {
        return modelVideoGameWiki;
    }
}