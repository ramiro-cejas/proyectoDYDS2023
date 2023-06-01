package model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import utils.SearchResult;

import java.util.Map;
import java.util.Set;

public class ModelSearchHandler {
    private final ModelVideoGameWiki modelVideoGameWiki;

    ModelSearchHandler(ModelVideoGameWiki modelVideoGameWiki) {
        this.modelVideoGameWiki = modelVideoGameWiki;
    }

    void searchTerm(String termToSearch) {
        Response<String> callForSearchResponse;
        try {
            callForSearchResponse = modelVideoGameWiki.getWikipediaSearchAPI().searchForTerm(termToSearch + " articletopic:\"video-games\"").execute();
            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
            JsonObject query = jobj.get("query").getAsJsonObject();
            modelVideoGameWiki.setParcialResults(query.get("search").getAsJsonArray());
            modelVideoGameWiki.getModelNotifier().notifyParcialSearchHasFinished();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void searchTermByPageId(String pageId) {
        try {
            Response<String> callForPageResponse = modelVideoGameWiki.getWikipediaPageAPI().getExtractByPageID(pageId).execute();

            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callForPageResponse.body(), JsonObject.class);
            JsonObject query = jobj.get("query").getAsJsonObject();
            JsonObject pages = query.get("pages").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
            JsonObject page = first.getValue().getAsJsonObject();
            JsonElement searchResultExtract = page.get("extract");
            if (searchResultExtract == null) {
                modelVideoGameWiki.setSelectedResult("No Results");
            } else {
                JsonElement searchResultTitle = page.get("title");
                modelVideoGameWiki.setSelectedResultTitle(searchResultTitle.getAsString().replace("\\n", "\n"));

                modelVideoGameWiki.setSelectedResult("<h1>" + searchResultTitle.getAsString().replace("\\n", "\n") + "</h1>");
                modelVideoGameWiki.setSelectedResult(modelVideoGameWiki.getSelectedResult() + searchResultExtract.getAsString().replace("\\n", "\n"));
                modelVideoGameWiki.setSelectedResult(ModelVideoGameWiki.textToHtml(modelVideoGameWiki.getSelectedResult()));
            }
            modelVideoGameWiki.getModelNotifier().notifyQueryHasFinished();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void searchSelectedTerm(SearchResult searchResult, String searchTerm) {
        try {
            Response<String> callForPageResponse = modelVideoGameWiki.getWikipediaPageAPI().getExtractByPageID(searchResult.pageID).execute();

            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callForPageResponse.body(), JsonObject.class);
            JsonObject query = jobj.get("query").getAsJsonObject();
            JsonObject pages = query.get("pages").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
            JsonObject page = first.getValue().getAsJsonObject();
            JsonElement searchResultExtract = page.get("extract");
            if (searchResultExtract == null) {
                modelVideoGameWiki.setSelectedResult("No Results");
            } else {
                modelVideoGameWiki.setSelectedResultTitle(searchResult.title);

                modelVideoGameWiki.setSelectedResult("<h1>" + searchResult.title + "</h1>");
                modelVideoGameWiki.setSelectedResult(modelVideoGameWiki.getSelectedResult() + searchResultExtract.getAsString().replace("\\n", "\n"));
                modelVideoGameWiki.setSelectedResult(ModelVideoGameWiki.textToHtml(modelVideoGameWiki.getSelectedResult()));
            }
            modelVideoGameWiki.getModelSaveHandler().saveHistoryOfTermSearcherd(searchResult.title, searchTerm, searchResult.pageID);
            modelVideoGameWiki.getModelNotifier().notifyQueryHasFinished();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}