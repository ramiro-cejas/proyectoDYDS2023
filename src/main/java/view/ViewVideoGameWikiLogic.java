package view;

import controller.ControllerVideoGameWiki;
import model.ModelVideoGameWikiInterface;

import java.sql.SQLException;

public class ViewVideoGameWikiLogic extends ViewVideoGameWikiVisual {
    private final ModelVideoGameWikiInterface modelVideoGameWiki;
    private final ControllerVideoGameWiki controllerVideoGameWiki;
    private final UpdaterViewHandler updaterViewHandler = new UpdaterViewHandler(this);
    private final ShowerViewHandler showerViewHandler = new ShowerViewHandler(this);
    private PopUPHandler popUpHandler;

    public ViewVideoGameWikiLogic(ControllerVideoGameWiki controllerVideoGame, ModelVideoGameWikiInterface modelVideoGame) {
        this.controllerVideoGameWiki = controllerVideoGame;
        this.modelVideoGameWiki = modelVideoGame;
        setUp();
    }

    private void setUp() {
        ListenerHandler listenerHandler = new ListenerHandler(this);
        listenerHandler.initListeners();
        popUpHandler = new PopUPHandler(contentPane);
        try {
            updaterViewHandler.updateStoredComboBox();
            updaterViewHandler.updateHistoryComboBox();
        } catch (SQLException e) {
            popUpHandler.showSQLException();
        }
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

    void updateHistoryComboBox() throws SQLException {
        updaterViewHandler.updateHistoryComboBox();
    }

    public void updateStoredComboBox() throws SQLException {
        updaterViewHandler.updateStoredComboBox();
    }

    void updateStoredExtract() {
        updaterViewHandler.updateStoredExtract();
    }

    public ControllerVideoGameWiki getControllerVideoGameWiki() {
        return controllerVideoGameWiki;
    }

    public ModelVideoGameWikiInterface getModelVideoGameWiki() {
        return modelVideoGameWiki;
    }

    public PopUPHandler getPopUpHandler() {
        return popUpHandler;
    }
}