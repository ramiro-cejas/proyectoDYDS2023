package view;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controller.ControllerVideoGameWiki;
import utils.DataBase;
import utils.SearchResult;
import model.ModelVideoGameWiki;
import model.ModelVideoGameWikiListener;

import javax.swing.*;
import java.awt.*;

public class View {
    private JTextField textFieldSearchTerm;
    private JButton buttonSearch;
    private JPanel contentPane;
    private JTextPane textPaneResult;
    private JButton buttonSaveLocally;
    private JTabbedPane tabbedPane1;
    private JPanel searchPanel;
    private JPanel storagePanel;
    private JComboBox comboBoxStored;
    private JTextPane textPaneStored;
    private JComboBox comboBoxHistory;
    private JPanel historyPanel;
    private ModelVideoGameWiki modelVideoGameWiki;
    private ControllerVideoGameWiki controllerVideoGameWiki;
    private JMenuItem deleteItem = new JMenuItem("Delete!");
    private JMenuItem saveItem = new JMenuItem("Save Changes!");
    private PopUPHandler popUpHandler;

    public View(ControllerVideoGameWiki controllerVideoGame, ModelVideoGameWiki modelVideoGame) {
        this.controllerVideoGameWiki = controllerVideoGame;
        this.modelVideoGameWiki = modelVideoGame;
        initListeners();
        setUp();
    }

    private void setUp() {
        textPaneResult.setContentType("text/html");
        textPaneStored.setContentType("text/html");
        updateStoredComboBox();
        updateHistoryComboBox();

        JPopupMenu storedInfoPopup = new JPopupMenu();
        storedInfoPopup.add(deleteItem);
        storedInfoPopup.add(saveItem);
        textPaneStored.setComponentPopupMenu(storedInfoPopup);
        popUpHandler = new PopUPHandler(contentPane);
    }

    private void initListeners() {
        buttonSearch.addActionListener(actionEvent -> {
            controllerVideoGameWiki.onEventSearch();
        });

        buttonSaveLocally.addActionListener(actionEvent -> {
            controllerVideoGameWiki.onEventSaveSearched(textPaneResult.getText());
        });

        comboBoxStored.addActionListener(actionEvent -> {
            controllerVideoGameWiki.onEventSelectStored(comboBoxStored.getSelectedItem().toString());
        });

        deleteItem.addActionListener(actionEvent -> {
            controllerVideoGameWiki.onEventDeleteStoredResult(comboBoxStored.getSelectedItem().toString());
        });

        saveItem.addActionListener(actionEvent -> {
            controllerVideoGameWiki.onEventUpdateStroredResult(comboBoxStored.getSelectedItem().toString(),textPaneStored.getText());
        });

        comboBoxHistory.addActionListener(actionEvent -> {
            controllerVideoGameWiki.onEventSelectHistory(comboBoxHistory.getSelectedItem().toString());
        });

        modelVideoGameWiki.addListener(new ModelVideoGameWikiListener() {
            @Override
            public void parcialSearchHasFinished() {
                showParcialResults();
                setWatingStatus();
            }
            @Override
            public void queryHasFinished() {
                showResult();
                setWatingStatus();
            }

            @Override
            public void saveHasFinished() {
                updateStoredComboBox();
                textPaneStored.setText("");
                popUpHandler.showSavedFinished();
            }

            @Override
            public void searchForStoredFinished() {
                updateStoredExtract();
            }

            @Override
            public void deleteFinished() {
                updateStoredComboBox();
                textPaneStored.setText("");
                popUpHandler.showDeleteFinished();
            }

            @Override
            public void updateHasFinished() {
                popUpHandler.showSavedFinished();
            }

            @Override
            public void historySaveHasFinished() {
                updateHistoryComboBox();
            }
        });
    }

    private void updateHistoryComboBox() {
        comboBoxHistory.setModel(new DefaultComboBoxModel(DataBase.getHistory().stream().sorted().toArray()));
    }

    private void updateStoredExtract() {
        textPaneStored.setText(modelVideoGameWiki.getStoredResultExtract());
    }

    private void showResult() {
        textPaneResult.setText(modelVideoGameWiki.getLastSearchResult());
        textPaneResult.setCaretPosition(0);
    }

    private void showParcialResults() {
        JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
        JsonArray jsonResults = modelVideoGameWiki.getParcialResults();
        for (JsonElement je : jsonResults) {
            JsonObject jsonObject = je.getAsJsonObject();
            String searchResultTitle = jsonObject.get("title").getAsString();
            String searchResultPageId = jsonObject.get("pageid").getAsString();
            String searchResultSnippet = jsonObject.get("snippet").getAsString();

            SearchResult searchResult = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
            searchOptionsMenu.add(searchResult);
            searchResult.addActionListener(actionEvent ->{
                setWorkingStatus();
                controllerVideoGameWiki.onEventSearchSelectedResult(searchResult, textFieldSearchTerm.getText());
            });
        }
        searchOptionsMenu.show(textFieldSearchTerm, textFieldSearchTerm.getX(), textFieldSearchTerm.getY());
    }

    public void setWorkingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(false);
        textPaneResult.setEnabled(false);
    }
    public void setWatingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(true);
        textPaneResult.setEnabled(true);
    }

    public String getTextofTermToSearch() {
        return textFieldSearchTerm.getText();
    }

    public void updateStoredComboBox(){
        comboBoxStored.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
    }
    public void showView() {
        JFrame frame = new JFrame("Video Game Info");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
