package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.ModelSearchHandler;
import model.TextHandler;
import retrofit2.Response;

import java.util.Map;
import java.util.Set;

public class ResponseHandler {
    private final ModelSearchHandler modelSearchHandler;

    public ResponseHandler(ModelSearchHandler modelSearchHandler) {
        this.modelSearchHandler = modelSearchHandler;
    }

    public static JsonObject transformResponseToJsonObject(JsonObject query) {
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

}