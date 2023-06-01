package view;

import javax.swing.*;
import java.awt.*;

public class ViewVideoGameWikiVisual {
    protected JTextField textFieldSearchTerm;
    protected JButton buttonSearch;
    protected JPanel contentPane;
    protected JTextPane textPaneResult;
    protected JButton buttonSaveLocally;
    protected JTabbedPane tabbedPane1;
    protected JPanel searchPanel;
    protected JPanel storagePanel;
    protected JComboBox comboBoxStored;
    protected JTextPane textPaneStored;
    protected JComboBox comboBoxHistory;
    protected JPanel historyPanel;
    protected JMenuItem deleteItem = new JMenuItem("Delete!");
    protected JMenuItem saveItem = new JMenuItem("Save Changes!");
    protected JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
    public ViewVideoGameWikiVisual() {
        textPaneResult.setContentType("text/html");
        textPaneStored.setContentType("text/html");
        JPopupMenu storedInfoPopup = new JPopupMenu();
        storedInfoPopup.add(deleteItem);
        storedInfoPopup.add(saveItem);
        textPaneStored.setComponentPopupMenu(storedInfoPopup);
    }
    public void setWorkingStatus() {
        for(Component component: this.searchPanel.getComponents()) component.setEnabled(false);
        for(Component component: this.historyPanel.getComponents()) component.setEnabled(false);
        for(Component component: this.storagePanel.getComponents()) component.setEnabled(false);
    }
    public void setWaitingStatus() {
        for(Component component: this.searchPanel.getComponents()) component.setEnabled(true);
        for(Component component: this.historyPanel.getComponents()) component.setEnabled(true);
        for(Component component: this.storagePanel.getComponents()) component.setEnabled(true);
    }
    public void showView() {
        JFrame frame = new JFrame("Video Game Info");
        frame.setResizable(false);
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public AbstractButton getButtonSearch() {
        return buttonSearch;
    }

    public JTextField getTextFieldSearchTerm() {
        return textFieldSearchTerm;
    }

    public void setTextFieldSearchTerm(JTextField textFieldSearchTerm) {
        this.textFieldSearchTerm = textFieldSearchTerm;
    }

    public void setButtonSearch(JButton buttonSearch) {
        this.buttonSearch = buttonSearch;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

    public JTextPane getTextPaneResult() {
        return textPaneResult;
    }

    public void setTextPaneResult(JTextPane textPaneResult) {
        this.textPaneResult = textPaneResult;
    }

    public JButton getButtonSaveLocally() {
        return buttonSaveLocally;
    }

    public void setButtonSaveLocally(JButton buttonSaveLocally) {
        this.buttonSaveLocally = buttonSaveLocally;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public void setTabbedPane1(JTabbedPane tabbedPane1) {
        this.tabbedPane1 = tabbedPane1;
    }

    public JPanel getSearchPanel() {
        return searchPanel;
    }

    public void setSearchPanel(JPanel searchPanel) {
        this.searchPanel = searchPanel;
    }

    public JPanel getStoragePanel() {
        return storagePanel;
    }

    public void setStoragePanel(JPanel storagePanel) {
        this.storagePanel = storagePanel;
    }

    public JComboBox getComboBoxStored() {
        return comboBoxStored;
    }

    public void setComboBoxStored(JComboBox comboBoxStored) {
        this.comboBoxStored = comboBoxStored;
    }

    public JTextPane getTextPaneStored() {
        return textPaneStored;
    }

    public void setTextPaneStored(JTextPane textPaneStored) {
        this.textPaneStored = textPaneStored;
    }

    public JComboBox getComboBoxHistory() {
        return comboBoxHistory;
    }

    public void setComboBoxHistory(JComboBox comboBoxHistory) {
        this.comboBoxHistory = comboBoxHistory;
    }

    public JPanel getHistoryPanel() {
        return historyPanel;
    }

    public void setHistoryPanel(JPanel historyPanel) {
        this.historyPanel = historyPanel;
    }

    public JMenuItem getDeleteItem() {
        return deleteItem;
    }

    public void setDeleteItem(JMenuItem deleteItem) {
        this.deleteItem = deleteItem;
    }

    public JMenuItem getSaveItem() {
        return saveItem;
    }

    public void setSaveItem(JMenuItem saveItem) {
        this.saveItem = saveItem;
    }
}