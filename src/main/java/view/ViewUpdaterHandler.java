package view;

import javax.swing.*;

public class ViewUpdaterHandler {
    private final ViewVideoGameWikiLogic viewVideoGameWikiLogic;

    public ViewUpdaterHandler(ViewVideoGameWikiLogic viewVideoGameWikiLogic) {
        this.viewVideoGameWikiLogic = viewVideoGameWikiLogic;
    }

    void updateHistoryComboBox() {
        viewVideoGameWikiLogic.getComboBoxHistory().setModel(new DefaultComboBoxModel<>(viewVideoGameWikiLogic.getModelVideoGameWiki().getHistory()));
    }

    void updateStoredExtract() {
        viewVideoGameWikiLogic.getTextPaneStored().setText(viewVideoGameWikiLogic.getModelVideoGameWiki().getStoredResultExtract());
    }

    public void updateStoredComboBox() {
        viewVideoGameWikiLogic.getComboBoxStored().setModel(new DefaultComboBoxModel<>(viewVideoGameWikiLogic.getModelVideoGameWiki().getTitles()));
    }
}