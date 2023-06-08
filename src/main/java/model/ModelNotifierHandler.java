package model;

import java.util.ArrayList;

public class ModelNotifierHandler {
    private final ArrayList<ModelVideoGameWikiListener> commonListeners;
    private final ArrayList<ModelVideoGameWikiExceptionListener> exceptionListeners;

    public ModelNotifierHandler() {
        commonListeners = new ArrayList<>();
        exceptionListeners = new ArrayList<>();
    }

    public void notifyParcialSearchHasFinished() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.partialSearchHasFinished();
        }
    }

    public void notifyQueryHasFinished() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.queryHasFinished();
        }
    }

    public void notifyUpdateStoredFinish() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.updateHasFinished();
        }
    }

    public void notifySearchForStoredFinish() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.searchForStoredFinished();
        }
    }

    public void notifyDeleteStoredFinish() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.deleteFinished();
        }
    }

    public void notifySaveSearchedResultFinish() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.saveHasFinished();
        }
    }

    public void addCommonListener(ModelVideoGameWikiListener listener) {
        commonListeners.add(listener);
    }

    public void addExceptionListener(ModelVideoGameWikiExceptionListener listener) {
        exceptionListeners.add(listener);
    }

    public void notifyHistorySaveFinished() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.historySaveHasFinished();
        }
    }

    public void notifyExceptionSearchTerm() {
        for (ModelVideoGameWikiExceptionListener listener : exceptionListeners) {
            listener.searchTermExceptionHasOcurred();
        }
    }

    public void notifyExceptionSearchByID() {
        for (ModelVideoGameWikiExceptionListener listener : exceptionListeners) {
            listener.searchByIDExceptionHasOcurred();
        }
    }

    public void notifyExceptionSQL() {
        for (ModelVideoGameWikiExceptionListener listener : exceptionListeners) {
            listener.sqlExceptionHasOcurred();
        }
    }

    public void notifySearchTitlesFromSavedResultsHasFinished() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.searchTitlesFromSavedResultHasFinished();
        }
    }

    public void notifySearchFromHistoryHasFinished() {
        for (ModelVideoGameWikiListener listener : commonListeners) {
            listener.searchFromHistoryHasFinished();
        }
    }
}
