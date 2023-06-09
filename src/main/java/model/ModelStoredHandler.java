package model;

import utils.DataBase;

import java.sql.SQLException;

public class ModelStoredHandler {
    private final ModelVideoGameWiki modelVideoGameWiki;

    public ModelStoredHandler(ModelVideoGameWiki modelVideoGameWiki) {
        this.modelVideoGameWiki = modelVideoGameWiki;
    }

    public void updateStoredResult(String title, String body) {
        try {
            DataBase.saveInfo(title, body);
        } catch (SQLException e) {
            modelVideoGameWiki.getModelNotifierHandler().notifyExceptionSQL();
        }
        modelVideoGameWiki.getModelNotifierHandler().notifyUpdateStoredFinish();
    }

    public void deleteStoredResult(String titleStored) {
        try {
            DataBase.deleteEntry(titleStored);
        } catch (SQLException e) {
            modelVideoGameWiki.getModelNotifierHandler().notifyExceptionSQL();
        }
        modelVideoGameWiki.notifyDeleteStoredFinish();
    }

    public void searchStoredResult(String titleStored) {
        try {
            modelVideoGameWiki.setStoredResultExtract(DataBase.getExtract(titleStored));
        } catch (SQLException e) {
            modelVideoGameWiki.getModelNotifierHandler().notifyExceptionSQL();
        }
        modelVideoGameWiki.getModelNotifierHandler().notifySearchForStoredFinish();
    }

    public void searchTitlesFromSavedResults() {
        try {
            modelVideoGameWiki.setTitlesFromSavedResults(DataBase.getTitles().toArray());
            modelVideoGameWiki.getModelNotifierHandler().notifySearchTitlesFromSavedResultsHasFinished();
        } catch (SQLException e) {
            modelVideoGameWiki.getModelNotifierHandler().notifyExceptionSQL();
        }
    }
}