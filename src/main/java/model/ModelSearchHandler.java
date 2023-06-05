package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.SearchResult;
import utils.WikipediaPageAPI;

public class ModelSearchHandler {
    private final ModelVideoGameWiki modelVideoGameWiki;
    private final ResponseHandler responseHandler = new ResponseHandler(this);

    private WikipediaPageAPI wikipediaPageAPI;

    ModelSearchHandler(ModelVideoGameWiki modelVideoGameWiki) {
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
            //TODO Todos los catch evaluar ponerlos en un listener en la vista con un ExceptionListener
            System.out.println(e.getMessage());
        }
    }

    void searchTermByPageId(String pageId) {
        try {
            Response<String> callForPageResponse = wikipediaPageAPI.getExtractByPageID(pageId).execute();
            responseHandler.processResponseByID(callForPageResponse);
            modelVideoGameWiki.getModelNotifier().notifyQueryHasFinished();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void searchSelectedTerm(SearchResult searchResult, String searchTerm) {
        try {
            Response<String> callForPageResponse = wikipediaPageAPI.getExtractByPageID(searchResult.pageID).execute();
            responseHandler.processResponseByID(callForPageResponse);
            modelVideoGameWiki.getModelSaveHandler().saveHistoryOfTermSearcherd(searchResult.title, searchTerm, searchResult.pageID);
            modelVideoGameWiki.getModelNotifier().notifyQueryHasFinished();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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