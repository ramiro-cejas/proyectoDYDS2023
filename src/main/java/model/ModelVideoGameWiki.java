package model;

import com.google.gson.JsonArray;
import utils.DataBase;
import utils.SearchResult;
import utils.WikipediaPageAPI;
import utils.WikipediaSearchAPI;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ModelVideoGameWiki implements ModelVideoGameWikiInterface {
    private final ModelSaveHandler modelSaveHandler = new ModelSaveHandler(this);
    private final ModelSearchHandler modelSearchHandler = new ModelSearchHandler(this);
    private final ModelNotifierHandler modelNotifierHandler;
    private String selectedResultTitle = null;
    private String selectedResult = null;
    private String storedResultExtract = null;
    private JsonArray parcialResults = null;
    @Override
    public void addListener(ModelVideoGameWikiListener listener) { modelNotifierHandler.addListener(listener); }
    @Override
    public String getLastSearchResultTitle() { return selectedResultTitle;  }
    @Override
    public String getLastSearchResult() { return selectedResult;  }
    @Override
    public JsonArray getParcialResults() { return parcialResults;  }
    @Override
    public String getStoredResultExtract() { return storedResultExtract; }
    public ModelVideoGameWiki() {
        modelNotifierHandler = new ModelNotifierHandler();
        setUpModel();
    }

    private void setUpModel(){
        DataBase.loadDatabase();
    }
    @Override
    public void searchTerm(String termToSearch) {
        modelSearchHandler.searchTerm(termToSearch);
    }
    @Override
    public void searchTermByPageId(String pageId){
        modelSearchHandler.searchTermByPageId(pageId);
    }
    @Override
    public void searchSelectedTerm(SearchResult searchResult, String searchTerm){
        modelSearchHandler.searchSelectedTerm(searchResult, searchTerm);
    }

    @Override
    public void notifyDeleteStoredFinish() {
        modelNotifierHandler.notifyDeleteStoredFinish();
    }

    @Override
    public void updateStoredResult(String title, String body) {
        DataBase.saveInfo(title, body);
        modelNotifierHandler.notifyUpdateStoredFinish();
    }
    @Override
    public void deleteStoredResult(String titleStored) {
        DataBase.deleteEntry(titleStored);
        notifyDeleteStoredFinish();
    }
    @Override
    public void searchStoredResult(String titleStored) {
        storedResultExtract = DataBase.getExtract(titleStored);
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
    public String getSelectedResultTitle() {
        return selectedResultTitle;
    }

    @Override
    public void setSelectedResultTitle(String selectedResultTitle) {
        this.selectedResultTitle = selectedResultTitle;
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
    public void setParcialResults(JsonArray parcialResults) {
        this.parcialResults = parcialResults;
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