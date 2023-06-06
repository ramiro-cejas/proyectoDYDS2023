package model;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.SearchResult;
import utils.WikipediaPageAPI;

public class ModelSearchHandler {
    private final ModelVideoGameWikiInterface modelVideoGameWiki;
    private final ResponseHandler responseHandler = new ResponseHandler(this);

    private WikipediaPageAPI wikipediaPageAPI;

    ModelSearchHandler(ModelVideoGameWikiInterface modelVideoGameWiki) {
        this.modelVideoGameWiki = modelVideoGameWiki;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        this.wikipediaPageAPI = retrofit.create(WikipediaPageAPI.class);
    }

    void searchTerm(String termToSearch) {
        Response<String> callForSearchResponse;
        try {
            callForSearchResponse = wikipediaPageAPI.searchForTerm(termToSearch + " articletopic:\"video-games\"").execute();
            modelVideoGameWiki.setParcialResults(ResponseHandler.getQueryAsJsonObject(callForSearchResponse).get("search").getAsJsonArray());
            modelVideoGameWiki.getModelNotifier().notifyParcialSearchHasFinished();
        } catch (Exception e) {
            modelVideoGameWiki.getModelNotifier().notifyExceptionSearchTerm();
        }
    }

    void searchTermByPageId(String pageId) {
        try {
            Response<String> callForPageResponse = wikipediaPageAPI.getExtractByPageID(pageId).execute();
            responseHandler.processResponseByID(callForPageResponse);
            modelVideoGameWiki.getModelNotifier().notifyQueryHasFinished();
        } catch (Exception e) {
            modelVideoGameWiki.getModelNotifier().notifyExceptionSearchByID();
        }
    }

    void searchSelectedTerm(SearchResult searchResult, String searchTerm) {
        try {
            Response<String> callForPageResponse = wikipediaPageAPI.getExtractByPageID(searchResult.pageID).execute();
            responseHandler.processResponseByID(callForPageResponse);
            modelVideoGameWiki.getModelSaveHandler().saveHistoryOfTermSearched(searchResult.title, searchTerm, searchResult.pageID);
            modelVideoGameWiki.getModelNotifier().notifyQueryHasFinished();
        } catch (Exception e) {
            modelVideoGameWiki.getModelNotifier().notifyExceptionSearchByID();
        }
    }

    public WikipediaPageAPI getWikipediaPageAPI() {
        return wikipediaPageAPI;
    }

    public void setWikipediaPageAPI(WikipediaPageAPI wikipediaPageAPI) {
        this.wikipediaPageAPI = wikipediaPageAPI;
    }

    public ModelVideoGameWikiInterface getModelVideoGameWiki() {
        return modelVideoGameWiki;
    }
}