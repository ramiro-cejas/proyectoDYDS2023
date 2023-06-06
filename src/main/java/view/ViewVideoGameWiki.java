package view;

import controller.ControllerVideoGameWiki;
import model.ModelVideoGameWikiInterface;
import utils.SearchResult;

import java.util.Arrays;
import java.util.Objects;

public class ViewVideoGameWiki implements ViewVideoGameWikiInterface {
    final ViewVideoGameWikiLogic viewLogic;

    public ViewVideoGameWiki(ControllerVideoGameWiki controllerVideoGame, ModelVideoGameWikiInterface modelVideoGame) {
        viewLogic = new ViewVideoGameWikiLogic(controllerVideoGame, modelVideoGame);
    }

    @Override
    public void start() {
        viewLogic.showView();
    }

    @Override
    public void setWorkingStatus() {
        viewLogic.setWorkingStatus();
    }

    @Override
    public void setWaitingStatus() {
        viewLogic.setWaitingStatus();
    }

    @Override
    public String getTextofTermToSearch() {
        return viewLogic.getTextofTermToSearch();
    }

    @Override
    public String getResultOfSearch() {
        return viewLogic.textPaneResult.getText();
    }

    @Override
    public String getSelectedSavedItem() {
        return Objects.requireNonNull(viewLogic.comboBoxStored.getSelectedItem()).toString();
    }

    @Override
    public String getSelectedHistoryItem() {
        return Objects.requireNonNull(viewLogic.comboBoxHistory.getSelectedItem()).toString();
    }

    @Override
    public void setSelectedHistoryItem(String item) {
        SearchResult searchResult = new SearchResult(item, "", "");
        viewLogic.comboBoxHistory.setSelectedItem(searchResult);
    }

    @Override
    public void setSelectedStoredItem(String title) {
        SearchResult searchResult = new SearchResult(title, "", "");
        viewLogic.comboBoxStored.setSelectedItem(searchResult);
    }

    @Override
    public void pressSearchButton() {
        Arrays.stream(viewLogic.buttonSearch.getActionListeners()).iterator().next().actionPerformed(null);
    }

    @Override
    public PopUPHandler getPopUPHandler() {
        return viewLogic.getPopUpHandler();
    }
}
