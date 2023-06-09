package view;

public interface ViewVideoGameWikiInterface {

    void start();

    String getTextofTermToSearch();

    ViewPopUPHandler getPopUPHandler();

    ViewVideoGameWikiLogic getViewLogic();

    void setWaitingStatus();

    void setWorkingStatus();
}
