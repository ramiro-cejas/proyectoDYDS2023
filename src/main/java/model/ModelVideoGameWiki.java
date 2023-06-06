package model;

import com.google.gson.JsonArray;
import utils.DataBase;
import utils.SearchResult;
import utils.WikipediaPageAPI;

import java.sql.SQLException;

public class ModelVideoGameWiki implements ModelVideoGameWikiInterface {
    private final ModelSaveHandler modelSaveHandler;
    private final ModelSearchHandler modelSearchHandler;
    private final ModelNotifierHandler modelNotifierHandler;
    private String selectedResultTitleRaw = null;
    private String selectedResult = null;
    private String selectedExtractRaw = null;
    private String storedResultExtract = null;
    private JsonArray parcialResults = null;

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
        return selectedResultTitleRaw;
    }

    @Override
    public void setSelectedResultTitle(String selectedResultTitleRaw) {
        this.selectedResultTitleRaw = selectedResultTitleRaw;
    }

    public String getSelectedResultExtract() {
        return selectedExtractRaw;
    }

    @Override
    public String getLastSearchResult() {
        return selectedResult;
    }

    @Override
    public JsonArray getParcialResults() {
        return parcialResults;
    }

    @Override
    public void setParcialResults(JsonArray parcialResults) {
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
    public WikipediaPageAPI getWikipediaPageAPI() {
        return modelSearchHandler.getWikipediaPageAPI();
    }

    @Override
    public void setWikipediaPageAPI(WikipediaPageAPI wikipediaPageAPI) {
        modelSearchHandler.setWikipediaPageAPI(wikipediaPageAPI);
    }
}