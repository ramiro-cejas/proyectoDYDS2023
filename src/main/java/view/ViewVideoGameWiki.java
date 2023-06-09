package view;

import controller.ControllerVideoGameWikiInterface;
import model.ModelVideoGameWikiInterface;

public class ViewVideoGameWiki implements ViewVideoGameWikiInterface {
    final ViewVideoGameWikiLogic viewLogic;

    public ViewVideoGameWiki(ControllerVideoGameWikiInterface controllerVideoGame, ModelVideoGameWikiInterface modelVideoGame) {
        controllerVideoGame.setView(this);
        viewLogic = new ViewVideoGameWikiLogic(controllerVideoGame, modelVideoGame);
    }

    @Override
    public void start() {
        viewLogic.showView();
    }

    @Override
    public String getTextofTermToSearch() {
        return viewLogic.getTextofTermToSearch();
    }

    @Override
    public ViewPopUPHandler getPopUPHandler() {
        return viewLogic.getPopUpHandler();
    }

    public ViewVideoGameWikiLogic getViewLogic(){
        return viewLogic;
    }

    @Override
    public void setWaitingStatus() {
        viewLogic.setWaitingStatus();
    }

    @Override
    public void setWorkingStatus() {
        viewLogic.setWorkingStatus();
    }

}
