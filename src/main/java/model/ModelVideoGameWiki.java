package model;

import utils.*;

import java.sql.SQLException;
import java.util.List;

public class ModelVideoGameWiki implements ModelVideoGameWikiInterface {
    private final ModelSaveHandler modelSaveHandler;
    private final ModelSearchHandler modelSearchHandler;
    private final ModelNotifierHandler modelNotifierHandler;
    private String selectedResultTitleRaw = null;
    private String selectedResult = null;
    private String selectedExtractRaw = null;
    private String storedResultExtract = null;
    private List<ResultInPlainText> parcialResults = null;
    private Object[] titlesFromSavedResults = null;
    private Object[] elementsFromHistory = null;
    private ResultInPlainText selectedResultInPlainText;

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

    @Override
    public void setSelectedResultInPlainText(ResultInPlainText resultInPlainText) {
        this.selectedResultInPlainText = resultInPlainText;
    }

    String lastTitleSavedOnHistory = null;
    String lastSearchTermSavedOnHistory = null;

    public ModelVideoGameWiki() {
        modelNotifierHandler = new ModelNotifierHandler();
        modelSaveHandler = new ModelSaveHandler(this);
        modelSearchHandler = new ModelSearchHandler(this);
        setUpModel();
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
    public void setSelectedResultTitle(String selectedResultTitle) {
        this.selectedResultInPlainText.title = selectedResultTitle;
    }

    public String getSelectedResultExtract() {
        return selectedResultInPlainText.extract;
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
        try {
            DataBase.saveInfo(title, body);
        } catch (SQLException e) {
            modelNotifierHandler.notifyExceptionSQL();
        }
        modelNotifierHandler.notifyUpdateStoredFinish();
    }

    @Override
    public void deleteStoredResult(String titleStored) {
        try {
            DataBase.deleteEntry(titleStored);
        } catch (SQLException e) {
            modelNotifierHandler.notifyExceptionSQL();
        }
        notifyDeleteStoredFinish();
    }

    @Override
    public void searchStoredResult(String titleStored) {
        try {
            storedResultExtract = DataBase.getExtract(titleStored);
        } catch (SQLException e) {
            modelNotifierHandler.notifyExceptionSQL();
        }
        modelNotifierHandler.notifySearchForStoredFinish();
    }

    @Override
    public void saveSearchedResult(String resultBody) {
        modelSaveHandler.saveSearchedResult(resultBody);
    }

    @Override
    public ModelSaveHandler getModelSaveHandler() {
        return modelSaveHandler;
    }

    @Override
    public ModelSearchHandler getModelSearchHandler() {
        return modelSearchHandler;
    }

    @Override
    public ModelNotifierHandler getModelNotifier() {
        return modelNotifierHandler;
    }

    @Override
    public void setSelectedResultExctract(String selectedExctractRaw) {
        this.selectedExtractRaw = selectedExctractRaw;
    }

    @Override
    public String getSelectedResult() {
        return selectedResult;
    }

    @Override
    public void setSelectedResult(String selectedResult) {
        this.selectedResult = selectedResult;
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
        try {
            titlesFromSavedResults = DataBase.getTitles().toArray();
            modelNotifierHandler.notifySearchTitlesFromSavedResultsHasFinished();
        } catch (SQLException e) {
            modelNotifierHandler.notifyExceptionSQL();
        }
    }

    @Override
    public void searchElementsFromHistory() {
        try {
            elementsFromHistory = DataBase.getHistory().toArray();
            modelNotifierHandler.notifySearchFromHistoryHasFinished();
        } catch (SQLException e) {
            modelNotifierHandler.notifyExceptionSQL();
        }
    }
}