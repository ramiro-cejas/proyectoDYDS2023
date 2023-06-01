package view;

import model.ModelVideoGameWikiListener;

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
        viewVideoGameWikiLogic.getModelVideoGameWiki().addListener(new ModelVideoGameWikiListener() {
            @Override
            public void parcialSearchHasFinished() {
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
                viewVideoGameWikiLogic.updateStoredComboBox();
                viewVideoGameWikiLogic.getTextPaneStored().setText("");
                viewVideoGameWikiLogic.getPopUpHandler().showSavedFinished();
            }

            @Override
            public void searchForStoredFinished() {
                viewVideoGameWikiLogic.updateStoredExtract();
            }

            @Override
            public void deleteFinished() {
                viewVideoGameWikiLogic.updateStoredComboBox();
                viewVideoGameWikiLogic.getTextPaneStored().setText("");
                viewVideoGameWikiLogic.getPopUpHandler().showDeleteFinished();
            }

            @Override
            public void updateHasFinished() {
                viewVideoGameWikiLogic.getPopUpHandler().showSavedFinished();
            }

            @Override
            public void historySaveHasFinished() {
                viewVideoGameWikiLogic.updateHistoryComboBox();
            }
        });
    }
}