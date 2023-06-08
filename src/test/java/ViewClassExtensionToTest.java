import com.google.gson.JsonArray;
import controller.ControllerVideoGameWikiInterface;
import model.ModelVideoGameWikiInterface;
import model.ModelVideoGameWikiListener;
import utils.ResultInPlainText;
import utils.SearchResult;
import view.ViewVideoGameWiki;

import javax.xml.transform.Result;
import java.util.List;

public class ViewClassExtensionToTest extends ViewVideoGameWiki {
    public volatile boolean processFinished = false;
    public List<ResultInPlainText> partialSearchResult = null;
    public String queryResult = null;
    public String storedInfo = null;
    public ViewClassExtensionToTest(ControllerVideoGameWikiInterface controllerVideoGame, ModelVideoGameWikiInterface modelVideoGame) {
        super(controllerVideoGame, modelVideoGame);
        modelVideoGame.addCommonListener(new ModelVideoGameWikiListener() {
            @Override
            public void partialSearchHasFinished() {
                partialSearchResult = modelVideoGame.getParcialResults();
                processFinished = true;
            }

            @Override
            public void queryHasFinished() {
                System.out.println("Entre");
                queryResult = modelVideoGame.getLastSearchResult();
                processFinished = true;
            }

            @Override
            public void saveHasFinished() {

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
    }


    public String searchByIDForTesting(String idToSearch){
        getViewLogic().getControllerVideoGameWiki().onEventSearchSelectedResult(new SearchResult("League of Legends",idToSearch,"League of"),"League of");
        waitForProcessFinishForTesting();
        return queryResult;
    }

    private void waitForProcessFinishForTesting(){
        while (!processFinished) Thread.onSpinWait();
    }
}
