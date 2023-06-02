package model;

import java.util.ArrayList;

public class ModelNotifierHandler {

    private ArrayList<ModelVideoGameWikiListener> listeners;

    public ModelNotifierHandler(){
        listeners = new ArrayList<>();
    }

    public void notifyParcialSearchHasFinished() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.parcialSearchHasFinished();
        }
    }
    public void notifyQueryHasFinished() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.queryHasFinished();
        }
    }
    public void notifyUpdateStoredFinish() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.updateHasFinished();
        }
    }
    public void notifySearchForStoredFinish() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.searchForStoredFinished();
        }
    }
    public void notifyDeleteStoredFinish() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.deleteFinished();
        }
    }
    public void notifySaveSearchedResultFinish() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.saveHasFinished();
        }
    }
    public void addListener(ModelVideoGameWikiListener listener) {
        listeners.add(listener);
    }

    public void notifyHistorySaveFinished() {
        for (ModelVideoGameWikiListener listener: listeners) {
            listener.historySaveHasFinished();
        }
    }
}
