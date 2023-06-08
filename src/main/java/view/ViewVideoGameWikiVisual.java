package view;

import javax.swing.*;
import java.awt.*;

public class ViewVideoGameWikiVisual {
    protected JTextField textFieldSearchTerm;
    protected JButton buttonSearch;
    protected JPanel contentPane;
    protected JTextPane textPaneResult;
    protected JButton buttonSaveLocally;
    protected JTabbedPane tabbedPane;
    protected JPanel searchPanel;
    protected JPanel storagePanel;
    protected JComboBox comboBoxStored;
    protected JTextPane textPaneStored;
    protected JComboBox comboBoxHistory;
    protected JPanel historyPanel;
    protected final JMenuItem deleteItem = new JMenuItem("Delete!");
    protected final JMenuItem saveItem = new JMenuItem("Save Changes!");
    protected final JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");

    public ViewVideoGameWikiVisual() {

        textPaneResult.setContentType("text/html");
        textPaneStored.setContentType("text/html");

        JPopupMenu storedInfoPopup = new JPopupMenu();
        storedInfoPopup.add(deleteItem);
        storedInfoPopup.add(saveItem);
        textPaneStored.setComponentPopupMenu(storedInfoPopup);
        Font formattedFont = new Font("Roboto Light", Font.BOLD, 12);
        buttonSearch.setFont(formattedFont);
        buttonSaveLocally.setFont(formattedFont);
        tabbedPane.setFont(formattedFont);
        comboBoxHistory.setFont(formattedFont);
    }

    public void setWorkingStatus() {
        for (Component component : this.searchPanel.getComponents()) component.setEnabled(false);
        for (Component component : this.historyPanel.getComponents()) component.setEnabled(false);
        for (Component component : this.storagePanel.getComponents()) component.setEnabled(false);
    }

    public void setWaitingStatus() {
        for (Component component : this.searchPanel.getComponents()) component.setEnabled(true);
        for (Component component : this.historyPanel.getComponents()) component.setEnabled(true);
        for (Component component : this.storagePanel.getComponents()) component.setEnabled(true);
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

    public JTextPane getTextPaneResult() {
        return textPaneResult;
    }

    public JButton getButtonSaveLocally() {
        return buttonSaveLocally;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JComboBox getComboBoxStored() {
        return comboBoxStored;
    }

    public JTextPane getTextPaneStored() {
        return textPaneStored;
    }

    public JComboBox getComboBoxHistory() {
        return comboBoxHistory;
    }

    public JMenuItem getDeleteItem() {
        return deleteItem;
    }

    public JMenuItem getSaveItem() {
        return saveItem;
    }

}