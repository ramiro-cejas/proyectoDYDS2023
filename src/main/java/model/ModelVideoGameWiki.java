package model;

import utils.*;
import utils.APIHandler.ApiHandler;

import java.sql.SQLException;
import java.util.List;

public class ModelVideoGameWiki implements ModelVideoGameWikiInterface {
    private final ModelSaveHandler modelSaveHandler;
    private final ModelSearchHandler modelSearchHandler;
    private final ModelNotifierHandler modelNotifierHandler;
    private final ModelStoredHandler modelStoredHandler = new ModelStoredHandler(this);
    private String selectedResultTitleRaw = null;
    private String selectedResult = null;
    private String selectedExtractRaw = null;
    private String storedResultExtract = null;
    private List<ResultInPlainText> parcialResults = null;
    private Object[] titlesFromSavedResults = null;
    private Object[] elementsFromHistory = null;
    private ResultInPlainText selectedResultInPlainText;
    String lastTitleSavedOnHistory = null;
    String lastSearchTermSavedOnHistory = null;

    public ModelVideoGameWiki() {
        modelNotifierHandler = new ModelNotifierHandler();
        modelSaveHandler = new ModelSaveHandler(this);
        modelSearchHandler = new ModelSearchHandler(this);
        setUpModel();
    }


    public String getLastTitleSavedOnHistory() {
        return lastTitleSavedOnHistory;
    }

    public void setLastTitleSavedOnHistory(String lastTitleSavedOnHistory) {
        this.lastTitleSavedOnHistory = lastTitleSavedOnHistory;
    }

    public String getLastSearchTermSavedOnHistory() {
        return lastSearchTermSavedOnHistory;
    }

    public void setLastSearchTermSavedOnHistory(String lastSearchTermSavedOnHistory) {
        this.lastSearchTermSavedOnHistory = lastSearchTermSavedOnHistory;
    }

    public void setSelectedResultInPlainText(ResultInPlainText resultInPlainText) {
        this.selectedResultInPlainText = resultInPlainText;
    }

    @Override
    public void addCommonListener(ModelVideoGameWikiListener listener) {
        modelNotifierHandler.addCommonListener(listener);
    }

    @Override
    public void addExceptionListener(ModelVideoGameWikiExceptionListener listener) {
        modelNotifierHandler.addExceptionListener(listener);
    }

    @Override
    public String getSelectedResultTitle() {
        return selectedResultInPlainText.title;
    }

    @Override
    public ResultInPlainText getLastSearchResult() {
        return selectedResultInPlainText;
    }

    @Override
    public List<ResultInPlainText> getParcialResults() {
        return parcialResults;
    }

    @Override
    public void setParcialResults(List<ResultInPlainText> parcialResults) {
        this.parcialResults = parcialResults;
    }

    @Override
    public String getStoredResultExtract() {
        return storedResultExtract;
    }

    private void setUpModel() {
        try {
            DataBase.loadDatabase();
        } catch (SQLException e) {
            modelNotifierHandler.notifyExceptionSQL();
        }
    }

    @Override
    public void searchTerm(String termToSearch) {
        modelSearchHandler.searchTerm(termToSearch);
    }

    @Override
    public void searchTermByPageId(String pageId) {
        modelSearchHandler.searchTermByPageId(pageId);
    }

    @Override
    public void searchSelectedTerm(SearchResult searchResult, String searchTerm) {
        modelSearchHandler.searchSelectedTerm(searchResult, searchTerm);
    }

    @Override
    public void notifyDeleteStoredFinish() {
        modelNotifierHandler.notifyDeleteStoredFinish();
    }

    @Override
    public void updateStoredResult(String title, String body) {
        modelStoredHandler.updateStoredResult(title, body);
    }

    @Override
    public void deleteStoredResult(String titleStored) {
        modelStoredHandler.deleteStoredResult(titleStored);
    }

    @Override
    public void searchStoredResult(String titleStored) {
        modelStoredHandler.searchStoredResult(titleStored);
    }

    @Override
    public void saveSearchedResult(String resultBody) {
        modelSaveHandler.saveSearchedResult(resultBody);
    }

    @Override
    public ApiHandler getApiHandler() {
        return modelSearchHandler.getApiHandler();
    }

    @Override
    public void setApiHandler(ApiHandler apiHandler) {
        modelSearchHandler.setApiHandler(apiHandler);
    }

    @Override
    public Object[] getTitles() {
        return titlesFromSavedResults;
    }

    @Override
    public Object[] getHistory() {
        return elementsFromHistory;
    }

    @Override
    public void searchTitlesFromSavedResults() {
        modelStoredHandler.searchTitlesFromSavedResults();
    }

    @Override
    public void searchElementsFromHistory() {
        modelSearchHandler.searchElementsFromHistory();
    }

    ModelNotifierHandler getModelNotifierHandler() {
        return modelNotifierHandler;
    }

    void setStoredResultExtract(String extract) {
        this.storedResultExtract = extract;
    }

    void setTitlesFromSavedResults(Object[] array) {
        this.titlesFromSavedResults = array;
    }

    void setElementsFromHistory(Object[] array) {
        this.elementsFromHistory = array;
    }

    public ModelNotifierHandler getModelNotifier() {
        return this.modelNotifierHandler;
    }

    public ModelSaveHandler getModelSaveHandler() {
        return this.modelSaveHandler;
    }
}