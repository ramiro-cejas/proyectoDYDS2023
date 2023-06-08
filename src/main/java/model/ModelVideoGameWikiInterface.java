package model;

import utils.ApiHandler;
import utils.ResultInPlainText;
import utils.SearchResult;
import utils.WikipediaPageAPI;

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

    ModelSaveHandler getModelSaveHandler();

    ModelSearchHandler getModelSearchHandler();

    ModelNotifierHandler getModelNotifier();

    String getSelectedResultTitle();

    void setSelectedResultTitle(String selectedResultTitleRaw);

    void setSelectedResultExctract(String selectedExctractRaw);

    String getSelectedResultExtract();

    String getSelectedResult();

    void setSelectedResult(String selectedResult);

    ApiHandler getApiHandler();

    void setApiHandler(ApiHandler apiHandler);

    Object[] getTitles();

    Object[] getHistory();

    void searchTitlesFromSavedResults();

    void searchElementsFromHistory();

    public String getLastTitleSavedOnHistory();

    public void setLastTitleSavedOnHistory(String lastTitleSavedOnHistory);

    public String getLastSearchTermSavedOnHistory();

    public void setLastSearchTermSavedOnHistory(String lastSearchTermSavedOnHistory);

    void setSelectedResultInPlainText(ResultInPlainText resultInPlainText);
}
