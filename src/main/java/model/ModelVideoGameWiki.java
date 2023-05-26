package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.videogameInfo.fulllogic.DataBase;
import dyds.videogameInfo.fulllogic.SearchResult;
import dyds.videogameInfo.fulllogic.WikipediaPageAPI;
import dyds.videogameInfo.fulllogic.WikipediaSearchAPI;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ModelVideoGameWiki {
    private String selectedResultTitle = null;
    private String selectedResult = null;
    private String storedResultExtract = null;
    private JsonArray parcialResults = null;
    private ArrayList<ModelVideoGameWikiListener> listeners = new ArrayList<>();
    private WikipediaSearchAPI wikipediaSearchAPI;
    private WikipediaPageAPI wikipediaPageAPI;
    public void addListener(ModelVideoGameWikiListener listener) { this.listeners.add(listener); }
    public String getLastSearchResultTitle() { return selectedResultTitle;  }
    public String getLastSearchResult() { return selectedResult;  }
    public JsonArray getParcialResults() { return parcialResults;  }

    public String getStoredResultExtract() { return storedResultExtract; }
    public ModelVideoGameWiki(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        wikipediaSearchAPI = retrofit.create(WikipediaSearchAPI.class);
        wikipediaPageAPI = retrofit.create(WikipediaPageAPI.class);
    }
    public void searchTerm(String termToSearch) {

        Response<String> callForSearchResponse;
        try {
            callForSearchResponse = wikipediaSearchAPI.searchForTerm(termToSearch + " articletopic:\"video-games\"").execute();
            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
            JsonObject query = jobj.get("query").getAsJsonObject();
            parcialResults = query.get("search").getAsJsonArray();
            notifyParcialSearchHasFinished();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void searchSelectedTerm(SearchResult searchResult){
        try{
            Response<String> callForPageResponse = wikipediaPageAPI.getExtractByPageID(searchResult.pageID).execute();

            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callForPageResponse.body(), JsonObject.class);
            JsonObject query = jobj.get("query").getAsJsonObject();
            JsonObject pages = query.get("pages").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
            JsonObject page = first.getValue().getAsJsonObject();
            JsonElement searchResultExtract = page.get("extract");
            if (searchResultExtract == null) {
                selectedResult = "No Results";
            } else {
                selectedResultTitle = searchResult.title;

                selectedResult = "<h1>" + searchResult.title + "</h1>";
                selectedResult += searchResultExtract.getAsString().replace("\\n", "\n");
                selectedResult = textToHtml(selectedResult);
            }
            notifyQueryHasFinished();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void notifyParcialSearchHasFinished() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.parcialSearchHasFinished();
        }
    }
    private void notifyQueryHasFinished() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.queryHasFinished();
        }
    }
    private void notifyUpdateStoredFinish() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.saveHasFinished();
        }
    }
    private void notifySearchForStoredFinish() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.searchForStoredFinished();
        }
    }
    private void notifyDeleteStoredFinish() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.deleteFinished();
        }
    }
    private static String textToHtml(String text) {
        StringBuilder builder = new StringBuilder();
        builder.append("<font face=\"arial\">");
        String fixedText = text
                .replace("'", "`");
        builder.append(fixedText);
        builder.append("</font>");
        return builder.toString();
    }

    public void updateStoredResult(String resultBody) {
        DataBase.saveInfo(selectedResultTitle, resultBody);
        notifyUpdateStoredFinish();
    }
    public void updateStoredResult(String title,String body) {
        DataBase.saveInfo(title, body);
        notifyUpdateStoredFinish();
    }

    public void deleteStoredResult(String titleStored) {
        DataBase.deleteEntry(titleStored);
        notifyDeleteStoredFinish();
    }


    public void searchStoredResult(String titleStored) {
        storedResultExtract = DataBase.getExtract(titleStored);
        notifySearchForStoredFinish();
    }
}
