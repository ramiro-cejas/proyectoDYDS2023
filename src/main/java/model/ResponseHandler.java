package model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;

import java.util.Map;
import java.util.Set;

public class ResponseHandler {
    private final ModelSearchHandler modelSearchHandler;

    public ResponseHandler(ModelSearchHandler modelSearchHandler) {
        this.modelSearchHandler = modelSearchHandler;
    }

    static JsonObject transformResponseToJsonObject(JsonObject query) {
        JsonObject pages = query.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        return first.getValue().getAsJsonObject();
    }

    public static JsonObject getQueryAsJsonObject(Response<String> callForPageResponse) {
        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(callForPageResponse.body(), JsonObject.class);
        return jobj.get("query").getAsJsonObject();
    }

    void processResponseByID(Response<String> callForPageResponse) {

        JsonObject page = transformResponseToJsonObject(getQueryAsJsonObject(callForPageResponse));
        JsonElement searchResultExtract = page.get("extract");
        if (searchResultExtract == null) {
            modelSearchHandler.getModelVideoGameWiki().setSelectedResult("No Results");
        } else {
            JsonElement searchResultTitle = page.get("title");
            modelSearchHandler.getModelVideoGameWiki().setSelectedResultTitle(searchResultTitle.getAsString().replace("\\n", "\n"));
            modelSearchHandler.getModelVideoGameWiki().setSelectedResultExctract(searchResultExtract.getAsString().replace("\\n", "\n"));
            modelSearchHandler.getModelVideoGameWiki().setSelectedResult("<h1 face=\"roboto\" color=\"white\">" + searchResultTitle.getAsString().replace("\\n", "\n") + "</h1>");
            modelSearchHandler.getModelVideoGameWiki().setSelectedResult(modelSearchHandler.getModelVideoGameWiki().getSelectedResult() + "<font face=\"roboto\" color=\"white\">" + searchResultExtract.getAsString().replace("\\n", "\n") + "</font>");
            modelSearchHandler.getModelVideoGameWiki().setSelectedResult(TextHandler.textToHtml(modelSearchHandler.getModelVideoGameWiki().getSelectedResult()));
        }
    }
}