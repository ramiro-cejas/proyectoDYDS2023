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
            modelVideoGameWiki.setLastTitleSavedOnHistory(title);
            modelVideoGameWiki.setLastSearchTermSavedOnHistory(searchTerm);
            modelVideoGameWiki.getModelNotifier().notifyHistorySaveFinished();
        } catch (SQLException e) {
            modelVideoGameWiki.getModelNotifier().notifyExceptionSQL();
        }
    }

    void saveSearchedResult(String resultBody) {
        try {
            DataBase.saveInfo(modelVideoGameWiki.getSelectedResultTitle(), resultBody);
            modelVideoGameWiki.getModelNotifier().notifySaveSearchedResultFinish();
        } catch (SQLException e) {
            modelVideoGameWiki.getModelNotifier().notifyExceptionSQL();
        }
    }
}