package view;

import utils.DataBase;

import javax.swing.*;

public class UpdaterViewHandler {
    private final ViewVideoGameWikiLogic viewVideoGameWikiLogic;

    public UpdaterViewHandler(ViewVideoGameWikiLogic viewVideoGameWikiLogic) {
        this.viewVideoGameWikiLogic = viewVideoGameWikiLogic;
    }

    void updateHistoryComboBox() {
        viewVideoGameWikiLogic.getComboBoxHistory().setModel(new DefaultComboBoxModel<Object>(DataBase.getHistory().stream().sorted().toArray()));
    }

    void updateStoredExtract() {
        viewVideoGameWikiLogic.getTextPaneStored().setText(viewVideoGameWikiLogic.getModelVideoGameWiki().getStoredResultExtract());
    }

    public void updateStoredComboBox() {
        viewVideoGameWikiLogic.getComboBoxStored().setModel(new DefaultComboBoxModel<Object>(DataBase.getTitles().stream().sorted().toArray()));
    }
}