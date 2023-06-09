package utils.APIHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.ResultInPlainText;
import utils.WikipediaPageAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiHandler {
    private WikipediaPageAPI wikiApi;

    public ApiHandler() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        this.wikiApi = retrofit.create(WikipediaPageAPI.class);
    }

    public List<ResultInPlainText> searchForTerm(String termToSearch) throws IOException {
        wikiApi.searchForTerm(termToSearch);

        List<ResultInPlainText> toReturn = new ArrayList<>();

        Response<String> callForSearchResponse;
        callForSearchResponse = wikiApi.searchForTerm(termToSearch + " articletopic:\"video-games\"").execute();
        JsonArray resultsAsJsonArray = (ResponseHandler.getQueryAsJsonObject(callForSearchResponse).get("search").getAsJsonArray());
        for (JsonElement jsonElementOfPartialResult : resultsAsJsonArray) {
            JsonObject jsonObjectOfPartialResult = jsonElementOfPartialResult.getAsJsonObject();
            ResultInPlainText resultInPlainText = getResultInPlainText(jsonObjectOfPartialResult);
            toReturn.add(resultInPlainText);
        }
        return toReturn;
    }

    public ResultInPlainText getExtractByPageID(String pageId) throws IOException {
        Response<String> callForPageResponse = wikiApi.getExtractByPageID(pageId).execute();
        ResultInPlainText toReturn = processResponseByID(callForPageResponse);
        toReturn.pageID = pageId;
        return toReturn;
    }

    private static ResultInPlainText getResultInPlainText(JsonObject jsonObject) {
        String searchResultTitle = "";
        String searchResultPageId = "";
        String searchResultSnippet = "";
        String searchResultExtract = "";

        if (jsonObject.get("title") != null){
            searchResultTitle = jsonObject.get("title").getAsString();
        }
        if (jsonObject.get("pageid") != null){
            searchResultPageId = jsonObject.get("pageid").getAsString();
        }
        if (jsonObject.get("snippet") != null){
            searchResultSnippet = jsonObject.get("snippet").getAsString();
        }
        if (jsonObject.get("extract") != null){
            searchResultExtract = jsonObject.get("extract").getAsString();
        }

        return new ResultInPlainText(searchResultTitle, searchResultPageId, searchResultSnippet, searchResultExtract);
    }

    ResultInPlainText processResponseByID(Response<String> callForPageResponse) {
        ResultInPlainText toReturn = new ResultInPlainText("","","","");
        JsonObject page = ResponseHandler.transformResponseToJsonObject(ResponseHandler.getQueryAsJsonObject(callForPageResponse));
        JsonElement searchResultExtract = page.get("extract");
        if (searchResultExtract == null) {
            toReturn.extract = "No Results";
        } else {
            String searchResultTitle = page.get("title").getAsString();
            toReturn.title = searchResultTitle.replace("\\n", "\n");
            toReturn.extract = searchResultExtract.getAsString().replace("\\n", "\n");
        }
        return toReturn;
    }
}
