package view;

public interface ViewVideoGameWikiInterface {

    void start();

    void setWorkingStatus();

    void setWaitingStatus();

    String getTextofTermToSearch();

    String getResultOfSearch();

    String getSelectedSavedItem();

    String getSelectedHistoryItem();

    void setSelectedHistoryItem(String item);

    void setSelectedStoredItem(String item);

    void pressSearchButton();

    PopUPHandler getPopUPHandler();
}
