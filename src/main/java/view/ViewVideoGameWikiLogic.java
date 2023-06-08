package view;

import controller.ControllerVideoGameWikiInterface;
import model.ModelVideoGameWikiInterface;

public class ViewVideoGameWikiLogic extends ViewVideoGameWikiVisual {
    private final ModelVideoGameWikiInterface modelVideoGameWiki;
    private final ControllerVideoGameWikiInterface controllerVideoGameWiki;
    private final ViewUpdaterHandler viewUpdaterHandler = new ViewUpdaterHandler(this);
    private final ViewShowerHandler viewShowerHandler = new ViewShowerHandler(this);
    private ViewPopUPHandler viewPopUpHandler;
    private String idToSearch = "";

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

    void showResult() {
        viewShowerHandler.showResult();
    }

    void showPartialResults() {
        viewShowerHandler.showPartialResults();
    }

    public String getTextofTermToSearch() {
        return textFieldSearchTerm.getText();
    }

    void updateHistoryComboBox() {
        viewUpdaterHandler.updateHistoryComboBox();
    }

    public void updateStoredComboBox() {
        viewUpdaterHandler.updateStoredComboBox();
    }

    void updateStoredExtract() {
        viewUpdaterHandler.updateStoredExtract();
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

}