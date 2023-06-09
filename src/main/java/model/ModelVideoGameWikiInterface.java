package model;

import utils.ApiHandler;
import utils.ResultInPlainText;
import utils.SearchResult;

import java.util.List;

public interface ModelVideoGameWikiInterface {
    void addCommonListener(ModelVideoGameWikiListener listener);

    void addExceptionListener(ModelVideoGameWikiExceptionListener listener);

    ResultInPlainText getLastSearchResult();

    List<ResultInPlainText> getParcialResults();

    void setParcialResults(List<ResultInPlainText> parcialResults);

    String getStoredResultExtract();

    void searchTerm(String termToSearch);

    void searchTermByPageId(String pageId);

    void searchSelectedTerm(SearchResult searchResult, String searchTerm);

    void notifyDeleteStoredFinish();

    void updateStoredResult(String title, String body);

    void deleteStoredResult(String titleStored);

    void searchStoredResult(String titleStored);

    void saveSearchedResult(String resultBody);

    String getSelectedResultTitle();

    ApiHandler getApiHandler();

    void setApiHandler(ApiHandler apiHandler);

    Object[] getTitles();

    Object[] getHistory();

    void searchTitlesFromSavedResults();

    void searchElementsFromHistory();

    public String getLastTitleSavedOnHistory();

    public String getLastSearchTermSavedOnHistory();

}