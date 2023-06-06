package utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaPageAPI {

    @GET("api.php?format=json&action=query&prop=extracts&exlimit=1&explaintext=1&exintro=1")
    Call<String> getExtractByPageID(@Query("pageids") String term);

    @GET("api.php?action=query&list=search&utf8=1&format=json&srlimit=5")
    Call<String> searchForTerm(@Query("srsearch") String term);

}
