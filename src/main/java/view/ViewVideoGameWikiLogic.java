package view;

import controller.ControllerVideoGameWiki;
import model.ModelVideoGameWiki;

public class ViewVideoGameWikiLogic extends ViewVideoGameWikiVisual {
    private final ModelVideoGameWiki modelVideoGameWiki;
    private final ControllerVideoGameWiki controllerVideoGameWiki;
    private final UpdaterViewHandler updaterViewHandler = new UpdaterViewHandler(this);
    private final ShowerViewHandler showerViewHandler = new ShowerViewHandler(this);
    private PopUPHandler popUpHandler;

    public ViewVideoGameWikiLogic(ControllerVideoGameWiki controllerVideoGame, ModelVideoGameWiki modelVideoGame) {
        this.controllerVideoGameWiki = controllerVideoGame;
        this.modelVideoGameWiki = modelVideoGame;
        setUp();
    }

    private void setUp() {
        ListenerHandler listenerHandler = new ListenerHandler(this);
        listenerHandler.initListeners();
        updaterViewHandler.updateStoredComboBox();
        updaterViewHandler.updateHistoryComboBox();
        popUpHandler = new PopUPHandler(contentPane);
    }

    void showResult() {
        showerViewHandler.showResult();
    }

    void showPartialResults() {
        showerViewHandler.showPartialResults();
    }

    public String getTextofTermToSearch() {
        return textFieldSearchTerm.getText();
    }

    void updateHistoryComboBox() {
        updaterViewHandler.updateHistoryComboBox();
    }

    public void updateStoredComboBox(){
        updaterViewHandler.updateStoredComboBox();
    }

    void updateStoredExtract() {
        updaterViewHandler.updateStoredExtract();
    }

    public ControllerVideoGameWiki getControllerVideoGameWiki() {
        return controllerVideoGameWiki;
    }

    public ModelVideoGameWiki getModelVideoGameWiki() {
        return modelVideoGameWiki;
    }

    public PopUPHandler getPopUpHandler() {
        return popUpHandler;
    }
}