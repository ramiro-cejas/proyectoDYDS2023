package model;

public interface ModelVideoGameWikiListener {

    void partialSearchHasFinished();

    void queryHasFinished();

    void saveHasFinished();

    void searchForStoredFinished();

    void deleteFinished();

    void updateHasFinished();

    void historySaveHasFinished();
}