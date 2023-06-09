package model;

import java.util.ArrayList;

public class ModelNotifierHandler {
    private final ArrayList<ModelVideoGameWikiListener> commonListeners;
    private final ArrayList<ModelVideoGameWikiExceptionListener> exceptionListeners;

    ModelNotifierHandler() {
        commonListeners = new ArrayList<>();
        exceptionListeners = new ArrayList<>();
    }

    void notifyParcialSearchHasFinished() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.partialSearchHasFinished();
        }
    }

    void notifyQueryHasFinished() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.queryHasFinished();
        }
    }

    void notifyUpdateStoredFinish() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.updateHasFinished();
        }
    }

    void notifySearchForStoredFinish() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.searchForStoredFinished();
        }
    }

    void notifyDeleteStoredFinish() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.deleteFinished();
        }
    }

    void notifySaveSearchedResultFinish() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.saveHasFinished();
        }
    }

    void addCommonListener(ModelVideoGameWikiListener listener) {
        commonListeners.add(listener);
    }

    void addExceptionListener(ModelVideoGameWikiExceptionListener listener) {
        exceptionListeners.add(listener);
    }

    void notifyHistorySaveFinished() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.historySaveHasFinished();
        }
    }

    void notifyExceptionSearchTerm() {
        for (ModelVideoGameWikiExceptionListener listener : exceptionListeners) {
            listener.searchTermExceptionHasOcurred();
        }
    }

    void notifyExceptionSearchByID() {
        for (ModelVideoGameWikiExceptionListener listener : exceptionListeners) {
            listener.searchByIDExceptionHasOcurred();
        }
    }

    void notifyExceptionSQL() {
        for (ModelVideoGameWikiExceptionListener listener : exceptionListeners) {
            listener.sqlExceptionHasOcurred();
        }
    }

    void notifySearchTitlesFromSavedResultsHasFinished() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.searchTitlesFromSavedResultHasFinished();
        }
    }

    void notifySearchFromHistoryHasFinished() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.searchFromHistoryHasFinished();
        }
    }
}
