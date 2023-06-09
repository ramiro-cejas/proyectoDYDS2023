import controller.ControllerVideoGameWikiInterface;
import model.ModelVideoGameWikiExceptionListener;
import model.ModelVideoGameWikiInterface;
import model.ModelVideoGameWikiListener;
import utils.ResultInPlainText;
import utils.SearchResult;
import view.ViewPopUPHandler;
import view.ViewVideoGameWiki;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ViewClassExtensionToTest extends ViewVideoGameWiki {
    public volatile boolean processFinished = false;
    public volatile boolean processQueryFinished = false;
    public String exceptionProduced = "";
    public List<ResultInPlainText> partialSearchResult = null;
    public ResultInPlainText queryResult = null;
    public String storedInfo = null;
    public ViewClassExtensionToTest(ControllerVideoGameWikiInterface controllerVideoGame, ModelVideoGameWikiInterface modelVideoGame) {
        super(controllerVideoGame, modelVideoGame);
        ViewPopUPHandler popUPMocked = mock(ViewPopUPHandler.class);
        getViewLogic().setPopUpHandler(popUPMocked);
        modelVideoGame.addCommonListener(new ModelVideoGameWikiListener() {
            @Override
            public void partialSearchHasFinished() {
                partialSearchResult = modelVideoGame.getParcialResults();
                processFinished = true;
            }

            @Override
            public void queryHasFinished() {
                queryResult = modelVideoGame.getLastSearchResult();
                processQueryFinished = true;
            }

            @Override
            public void saveHasFinished() {
                processFinished = true;
            }

            @Override
            public void searchForStoredFinished() {
                storedInfo = modelVideoGame.getStoredResultExtract();
                processFinished = true;
            }

            @Override
            public void deleteFinished() {

            }

            @Override
            public void updateHasFinished() {

            }

            @Override
            public void historySaveHasFinished() {

            }

            @Override
            public void searchTitlesFromSavedResultHasFinished() {

            }

            @Override
            public void searchFromHistoryHasFinished() {
            }
        });
        modelVideoGame.addExceptionListener(new ModelVideoGameWikiExceptionListener() {
            @Override
            public void searchTermExceptionHasOcurred() {
                exceptionProduced = "searchTermExceptionHasOcurred";
                processFinished = true;
                processQueryFinished = true;
            }

            @Override
            public void searchByIDExceptionHasOcurred() {
                exceptionProduced = "searchByIDExceptionHasOcurred";
                processFinished = true;
                processQueryFinished = true;
            }

            @Override
            public void sqlExceptionHasOcurred() {
                exceptionProduced = "sqlExceptionHasOcurred";
                processFinished = true;
                processQueryFinished = true;
            }
        });
    }


    public ResultInPlainText searchByIDForTesting(String idToSearch){
        getViewLogic().getControllerVideoGameWiki().onEventSearchSelectedResult(new SearchResult("",idToSearch,""),"");
        waitForQueryProcessFinishForTesting();
        if (exceptionProduced != "")
            return new ResultInPlainText("","","",exceptionProduced);
        else
            return queryResult;
    }

    public List<ResultInPlainText> pressSearchButtonWithGivenTermToSearch(String termToSearch) {
        this.getViewLogic().getTextFieldSearchTerm().setText(termToSearch);
        Arrays.stream(getViewLogic().getButtonSearch().getActionListeners()).iterator().next().actionPerformed(null);
        
        waitForProcessFinishForTesting();
        return getViewLogic().getModelVideoGameWiki().getParcialResults();
    }

    public void saveLastSearchedExtractForTesting(){
        getViewLogic().getControllerVideoGameWiki().onEventSaveSearched(queryResult.extract);
        waitForProcessFinishForTesting();
    }

    public String getLastSavedExtractSearchedInTheComboBoxStored() {
        int lastIndex = getViewLogic().getComboBoxStored().getItemCount() - 1;
        getViewLogic().getComboBoxStored().setSelectedItem(getViewLogic().getComboBoxStored().getItemAt(lastIndex));

        waitForProcessFinishForTesting();

        return getViewLogic().getTextPaneStored().getText();
    }

    private void waitForProcessFinishForTesting(){
        while (!processFinished) Thread.onSpinWait();
        processFinished = false;
    }
    private void waitForQueryProcessFinishForTesting(){
        while (!processQueryFinished) Thread.onSpinWait();
        processQueryFinished = false;
    }
}
