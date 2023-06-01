package model;

import utils.DataBase;

public class ModelSaveHandler {
    private final ModelVideoGameWiki modelVideoGameWiki;

    ModelSaveHandler(ModelVideoGameWiki modelVideoGameWiki) {
        this.modelVideoGameWiki = modelVideoGameWiki;
    }

    void saveHistoryOfTermSearcherd(String title, String searchTerm, String pageId) {
        DataBase.historySave(title, searchTerm, pageId);
        modelVideoGameWiki.getModelNotifier().notifyHistorySaveFinished();
    }

    void saveSearchedResult(String resultBody) {
        DataBase.saveInfo(modelVideoGameWiki.getSelectedResultTitle(), resultBody);
        modelVideoGameWiki.getModelNotifier().notifySaveSearchedResultFinish();
    }
}