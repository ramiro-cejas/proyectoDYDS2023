package view;

import utils.DataBase;

import javax.swing.*;
import java.sql.SQLException;

public class UpdaterViewHandler {
    private final ViewVideoGameWikiLogic viewVideoGameWikiLogic;

    public UpdaterViewHandler(ViewVideoGameWikiLogic viewVideoGameWikiLogic) {
        this.viewVideoGameWikiLogic = viewVideoGameWikiLogic;
    }

    void updateHistoryComboBox() throws SQLException {
        viewVideoGameWikiLogic.getComboBoxHistory().setModel(new DefaultComboBoxModel<Object>(DataBase.getHistory().toArray()));
    }

    void updateStoredExtract() {
        viewVideoGameWikiLogic.getTextPaneStored().setText(viewVideoGameWikiLogic.getModelVideoGameWiki().getStoredResultExtract());
    }

    public void updateStoredComboBox() throws SQLException {
        viewVideoGameWikiLogic.getComboBoxStored().setModel(new DefaultComboBoxModel<Object>(DataBase.getTitles().toArray()));
    }
}