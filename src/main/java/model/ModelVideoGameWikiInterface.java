package model;

import com.google.gson.JsonArray;
import utils.SearchResult;
import utils.WikipediaPageAPI;

public interface ModelVideoGameWikiInterface {
    void addCommonListener(ModelVideoGameWikiListener listener);

    void addExceptionListener(ModelVideoGameWikiExceptionListener listener);

    String getLastSearchResult();

    JsonArray getParcialResults();

    void setParcialResults(JsonArray parcialResults);

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

    ModelNotifierHandler getModelNotifier();

    String getSelectedResultTitle();

    void setSelectedResultTitle(String selectedResultTitleRaw);

    void setSelectedResultExctract(String selectedExctractRaw);

    String getSelectedResultExtract();

    String getSelectedResult();

    void setSelectedResult(String selectedResult);

    WikipediaPageAPI getWikipediaPageAPI();

    void setWikipediaPageAPI(WikipediaPageAPI wikipediaPageAPI);
}
