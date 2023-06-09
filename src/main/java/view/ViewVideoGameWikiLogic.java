package view;

import controller.ControllerVideoGameWikiInterface;
import model.ModelVideoGameWikiInterface;

public class ViewVideoGameWikiLogic extends ViewVideoGameWikiVisual {
    private final ModelVideoGameWikiInterface modelVideoGameWiki;
    private final ControllerVideoGameWikiInterface controllerVideoGameWiki;
    final ViewUpdaterHandler viewUpdaterHandler = new ViewUpdaterHandler(this);
    final ViewShowerHandler viewShowerHandler = new ViewShowerHandler(this);
    private ViewPopUPHandler viewPopUpHandler;

    public ViewVideoGameWikiLogic(ControllerVideoGameWikiInterface controllerVideoGame, ModelVideoGameWikiInterface modelVideoGame) {
        this.controllerVideoGameWiki = controllerVideoGame;
        this.modelVideoGameWiki = modelVideoGame;
        setUp();
    }

    private void setUp() {
        ViewListenerHandler viewListenerHandler = new ViewListenerHandler(this);
        viewListenerHandler.initListeners();
        viewPopUpHandler = new ViewPopUPHandler(contentPane);
        modelVideoGameWiki.searchTitlesFromSavedResults();
        modelVideoGameWiki.searchElementsFromHistory();
    }

    public String getTextofTermToSearch() {
        return textFieldSearchTerm.getText();
    }

    public ControllerVideoGameWikiInterface getControllerVideoGameWiki() {
        return controllerVideoGameWiki;
    }

    public ModelVideoGameWikiInterface getModelVideoGameWiki() {
        return modelVideoGameWiki;
    }

    public ViewPopUPHandler getPopUpHandler() {
        return viewPopUpHandler;
    }

    public void setPopUpHandler(ViewPopUPHandler viewPopUpHandler) {
        this.viewPopUpHandler = viewPopUpHandler;
    }

}