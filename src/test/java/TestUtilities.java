import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import utils.WikipediaPageAPI;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtilities {
    public TestUtilities() {
    }

    JsonArray processResultFromApi(String term, WikipediaPageAPI wikiApiTest) throws IOException {
        JsonArray toReturn;
        Response<String> callForSearchResponse = wikiApiTest.searchForTerm(term + " articletopic:\"video-games\"").execute();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jsonObject.get("query").getAsJsonObject();
        toReturn = query.get("search").getAsJsonArray();
        return toReturn;
    }

    List<ResultInStrings> convertResultFromApiToSearchResult(JsonArray responseFromApi) {
        ArrayList<ResultInStrings> toReturn = new ArrayList<>();
        for (JsonElement je : responseFromApi) {
            JsonObject searchResult = je.getAsJsonObject();
            String searchResultTitle = searchResult.get("title").getAsString();
            String searchResultPageId = searchResult.get("pageid").getAsString();
            String searchResultSnippet = searchResult.get("snippet").getAsString();

            toReturn.add(new ResultInStrings(searchResultTitle, searchResultPageId, searchResultSnippet));
        }
        Collections.sort(toReturn);
        return toReturn;
    }

    public String getTitleWithExtractFromResponseByID(Response<String> callForPageResponse) {
        String toReturn;
        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(callForPageResponse.body(), JsonObject.class);
        JsonObject query = jobj.get("query").getAsJsonObject();
        JsonObject pages = query.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement searchResultTitle = page.get("title");
        JsonElement searchResultExtract = page.get("extract");
        if (searchResultExtract == null){
            toReturn = "No Results";
        }else {
            String title = (searchResultTitle.getAsString().replace("\\n", "\n"));
            String extract = (searchResultExtract.getAsString().replace("\\n", "\n"));
            toReturn = title +"\n"+extract;
        }
        return toReturn;
    }

    public static String cleanAllHTMLTags(String textToClean){
        String pattern = "<.*?>";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(textToClean);
        return matcher.replaceAll("").replace("&quot;","\"");
    }

    public static String removeLineBreaks(String text) {
        return text.replaceAll("\\r?\\n", "").replace(". ",".");
    }

    public static String removeBlankSpaces(String text){
        return text.replaceAll("    ", "").strip();
    }
}