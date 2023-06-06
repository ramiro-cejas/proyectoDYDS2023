package model;

import utils.DataBase;

import java.sql.SQLException;

public class ModelSaveHandler {
    private final ModelVideoGameWikiInterface modelVideoGameWiki;

    ModelSaveHandler(ModelVideoGameWikiInterface modelVideoGameWiki) {
        this.modelVideoGameWiki = modelVideoGameWiki;
    }

    void saveHistoryOfTermSearched(String title, String searchTerm, String pageId) {
        try {
            DataBase.historySave(title, searchTerm, pageId);
        } catch (SQLException e) {
            modelVideoGameWiki.getModelNotifier().notifyExceptionSQL();
        }
        modelVideoGameWiki.getModelNotifier().notifyHistorySaveFinished();
    }

    void saveSearchedResult(String resultBody) {
        try {
            DataBase.saveInfo(modelVideoGameWiki.getSelectedResultTitle(), resultBody);
        } catch (SQLException e) {
            modelVideoGameWiki.getModelNotifier().notifyExceptionSQL();
        }
        modelVideoGameWiki.getModelNotifier().notifySaveSearchedResultFinish();
    }
}