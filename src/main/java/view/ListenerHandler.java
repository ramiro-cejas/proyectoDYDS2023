package view;

import model.ModelVideoGameWikiExceptionListener;
import model.ModelVideoGameWikiListener;

import java.sql.SQLException;
import java.util.Objects;

public class ListenerHandler {
    private final ViewVideoGameWikiLogic viewVideoGameWikiLogic;

    public ListenerHandler(ViewVideoGameWikiLogic viewVideoGameWikiLogic) {
        this.viewVideoGameWikiLogic = viewVideoGameWikiLogic;
    }

    void initListeners() {
        viewVideoGameWikiLogic.getButtonSearch().addActionListener(actionEvent -> {
            viewVideoGameWikiLogic.getControllerVideoGameWiki().onEventSearch();
        });
        viewVideoGameWikiLogic.getButtonSaveLocally().addActionListener(actionEvent -> {
            viewVideoGameWikiLogic.getControllerVideoGameWiki().onEventSaveSearched(viewVideoGameWikiLogic.getTextPaneResult().getText());
        });
        viewVideoGameWikiLogic.getComboBoxStored().addActionListener(actionEvent -> {
            viewVideoGameWikiLogic.getControllerVideoGameWiki().onEventSelectStored(Objects.requireNonNull(viewVideoGameWikiLogic.getComboBoxStored().getSelectedItem()).toString());
        });
        viewVideoGameWikiLogic.getDeleteItem().addActionListener(actionEvent -> {
            viewVideoGameWikiLogic.getControllerVideoGameWiki().onEventDeleteStoredResult(Objects.requireNonNull(viewVideoGameWikiLogic.getComboBoxStored().getSelectedItem()).toString());
        });
        viewVideoGameWikiLogic.getSaveItem().addActionListener(actionEvent -> {
            viewVideoGameWikiLogic.getControllerVideoGameWiki().onEventUpdateStroredResult(Objects.requireNonNull(viewVideoGameWikiLogic.getComboBoxStored().getSelectedItem()).toString(), viewVideoGameWikiLogic.getTextPaneStored().getText());
        });
        viewVideoGameWikiLogic.getComboBoxHistory().addActionListener(actionEvent -> {
            viewVideoGameWikiLogic.getControllerVideoGameWiki().onEventSelectHistory(Objects.requireNonNull(viewVideoGameWikiLogic.getComboBoxHistory().getSelectedItem()).toString());
        });
        viewVideoGameWikiLogic.getModelVideoGameWiki().addCommonListener(new ModelVideoGameWikiListener() {
            @Override
            public void partialSearchHasFinished() {
                viewVideoGameWikiLogic.showPartialResults();
                viewVideoGameWikiLogic.setWaitingStatus();
            }

            @Override
            public void queryHasFinished() {
                viewVideoGameWikiLogic.showResult();
                viewVideoGameWikiLogic.setWaitingStatus();
            }

            @Override
            public void saveHasFinished() {
                try {
                    viewVideoGameWikiLogic.updateStoredComboBox();
                } catch (SQLException e) {
                    viewVideoGameWikiLogic.getPopUpHandler().showSQLException();
                }
                viewVideoGameWikiLogic.getTextPaneStored().setText("");
                viewVideoGameWikiLogic.getPopUpHandler().showSavedFinished();
            }

            @Override
            public void searchForStoredFinished() {
                viewVideoGameWikiLogic.updateStoredExtract();
            }

            @Override
            public void deleteFinished() {
                try {
                    viewVideoGameWikiLogic.updateStoredComboBox();
                } catch (SQLException e) {
                    viewVideoGameWikiLogic.getPopUpHandler().showSQLException();
                }
                viewVideoGameWikiLogic.getTextPaneStored().setText("");
                viewVideoGameWikiLogic.getPopUpHandler().showDeleteFinished();
            }

            @Override
            public void updateHasFinished() {
                viewVideoGameWikiLogic.getPopUpHandler().showSavedFinished();
            }

            @Override
            public void historySaveHasFinished() {
                try {
                    viewVideoGameWikiLogic.updateHistoryComboBox();
                } catch (SQLException e) {
                    viewVideoGameWikiLogic.getPopUpHandler().showSQLException();
                }
            }
        });
        viewVideoGameWikiLogic.getModelVideoGameWiki().addExceptionListener(new ModelVideoGameWikiExceptionListener() {
            @Override
            public void searchTermExceptionHasOcurred() {
                viewVideoGameWikiLogic.getPopUpHandler().showSearchException();
            }

            @Override
            public void searchByIDExceptionHasOcurred() {
                viewVideoGameWikiLogic.getPopUpHandler().showSearchByIDException();
            }

            @Override
            public void sqlExceptionHasOcurred() {
                viewVideoGameWikiLogic.getPopUpHandler().showSQLException();
            }
        });
    }
}