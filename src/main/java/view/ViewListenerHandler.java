package view;

import model.ModelVideoGameWikiExceptionListener;
import model.ModelVideoGameWikiListener;

import java.util.Objects;

public class ViewListenerHandler {
    private final ViewVideoGameWikiLogic viewVideoGameWikiLogic;

    public ViewListenerHandler(ViewVideoGameWikiLogic viewVideoGameWikiLogic) {
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
            if (viewVideoGameWikiLogic.getComboBoxStored().getSelectedItem() != null) {
                viewVideoGameWikiLogic.getControllerVideoGameWiki().onEventSelectStored(Objects.requireNonNull(viewVideoGameWikiLogic.getComboBoxStored().getSelectedItem()).toString());
            }
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
                viewVideoGameWikiLogic.viewShowerHandler.showPartialResults();
            }

            @Override
            public void queryHasFinished() {
                viewVideoGameWikiLogic.viewShowerHandler.showResult();
                viewVideoGameWikiLogic.getControllerVideoGameWiki().searchElementsFromHistory();
            }

            @Override
            public void saveHasFinished() {
                viewVideoGameWikiLogic.getTextPaneStored().setText("");
                viewVideoGameWikiLogic.getPopUpHandler().showSavedFinished();
                viewVideoGameWikiLogic.getControllerVideoGameWiki().searchTitlesFromSavedResults();
            }

            @Override
            public void searchForStoredFinished() {
                viewVideoGameWikiLogic.viewUpdaterHandler.updateStoredExtract();
            }

            @Override
            public void deleteFinished() {
                viewVideoGameWikiLogic.getControllerVideoGameWiki().searchTitlesFromSavedResults();
                viewVideoGameWikiLogic.getTextPaneStored().setText("");
                viewVideoGameWikiLogic.getComboBoxStored().setSelectedItem(null);
                viewVideoGameWikiLogic.getPopUpHandler().showDeleteFinished();
            }

            @Override
            public void updateHasFinished() {
                viewVideoGameWikiLogic.getPopUpHandler().showSavedFinished();
            }

            @Override
            public void historySaveHasFinished() {
                viewVideoGameWikiLogic.getControllerVideoGameWiki().searchElementsFromHistory();
            }

            @Override
            public void searchTitlesFromSavedResultHasFinished() {
                viewVideoGameWikiLogic.viewUpdaterHandler.updateStoredComboBox();
            }

            @Override
            public void searchFromHistoryHasFinished() {
                viewVideoGameWikiLogic.viewUpdaterHandler.updateHistoryComboBox();
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