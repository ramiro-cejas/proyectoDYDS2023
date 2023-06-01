package model;

import com.google.gson.JsonArray;
import utils.SearchResult;
import utils.WikipediaPageAPI;
import utils.WikipediaSearchAPI;

public interface ModelVideoGameWikiInterface {
    void addListener(ModelVideoGameWikiListener listener);

    String getLastSearchResultTitle();

    String getLastSearchResult();

    JsonArray getParcialResults();

    String getStoredResultExtract();

    void searchTerm(String termToSearch);

    void searchTermByPageId(String pageId);

    void searchSelectedTerm(SearchResult searchResult, String searchTerm);

    void notifyDeleteStoredFinish();

    void updateStoredResult(String title, String body);

    void deleteStoredResult(String titleStored);

    void searchStoredResult(String titleStored);

    void saveSearchedResult(String resultBody);

    ModelSaveHandler getModelSaveHandler();

    ModelSearchHandler getModelSearchHandler();

    ModelNotifier getModelNotifier();

    String getSelectedResultTitle();

    void setSelectedResultTitle(String selectedResultTitle);

    String getSelectedResult();

    void setSelectedResult(String selectedResult);

    void setParcialResults(JsonArray parcialResults);

    WikipediaSearchAPI getWikipediaSearchAPI();

    void setWikipediaSearchAPI(WikipediaSearchAPI wikipediaSearchAPI);

    WikipediaPageAPI getWikipediaPageAPI();

    void setWikipediaPageAPI(WikipediaPageAPI wikipediaPageAPI);
}
