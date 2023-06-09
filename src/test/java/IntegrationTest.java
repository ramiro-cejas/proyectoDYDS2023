import controller.ControllerVideoGameWiki;
import controller.ControllerVideoGameWikiInterface;
import model.ModelVideoGameWiki;
import model.ModelVideoGameWikiInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testUtilities.TestUtilities;
import testUtilities.ViewClassExtensionToTest;
import utils.APIHandler.ApiHandler;
import utils.ResultInPlainText;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTest {

    private ModelVideoGameWikiInterface modelToTest;
    private ViewClassExtensionToTest viewToTest;
    private ApiHandler apiHandlerMockedForTest;

    @BeforeEach
    public void initElementsToTest() throws IOException {
        modelToTest = new ModelVideoGameWiki();
        ControllerVideoGameWikiInterface controllerToTest = new ControllerVideoGameWiki(modelToTest);
        viewToTest = new ViewClassExtensionToTest(controllerToTest, modelToTest);
        apiHandlerMockedForTest = mock(ApiHandler.class);
        mockSearchByIDInApiHandler(apiHandlerMockedForTest);
    }

    private void mockSearchByIDInApiHandler(ApiHandler apiHandlerMocked) throws IOException {
        when(apiHandlerMocked.getExtractByPageID("0")).thenReturn(new ResultInPlainText("Title Expected", "0", "Snippet Expected", "Extract Expected"));
        modelToTest.setApiHandler(apiHandlerMocked);
    }

    private void mockSearchByTermInApiHandler(ApiHandler apiHandlerMocked, String termToMock) throws IOException {
        when(apiHandlerMocked.searchForTerm(termToMock)).thenReturn(
                List.of(new ResultInPlainText("Title: " + termToMock, "0", "Snippet: " + termToMock, "Extract: " + termToMock), new ResultInPlainText("Title: " + termToMock, "0", "Snippet: " + termToMock, "Extract: " + termToMock), new ResultInPlainText("Title: " + termToMock, "0", "Snippet: " + termToMock, "Extract: " + termToMock))
        );
        modelToTest.setApiHandler(apiHandlerMocked);
    }

    @Test
    public void testSearchWithValidID() {
        String idToSearch = "0";

        String expected = TestUtilities.cleanAllHTMLTags("Extract Expected");
        String obtained = TestUtilities.cleanAllHTMLTags((viewToTest).searchByIDForTesting(idToSearch).extract);

        assertEquals(expected, obtained);
    }

    @Test
    public void testSearchWithInvalidID() {
        String idToSearch = "2";

        String expected = ("searchByIDExceptionHasOcurred");
        String obtained = ((viewToTest).searchByIDForTesting(idToSearch).extract);

        assertEquals(expected, obtained);
    }

    @Test
    public void testSearchWithEmptyID() {
        String idToSearch = "";

        String expected = ("searchByIDExceptionHasOcurred");
        String obtained = ((viewToTest).searchByIDForTesting(idToSearch).extract);

        assertEquals(expected, obtained);
    }

    @Test
    public void testPartialSearchWithValidTerm() throws IOException {
        String termToSearch = "League of legends";
        mockSearchByTermInApiHandler(apiHandlerMockedForTest, termToSearch);

        String expected = "Title: " + termToSearch;
        List<ResultInPlainText> obtainedListOfResults = ((viewToTest).pressSearchButtonWithGivenTermToSearch(termToSearch));

        for (ResultInPlainText obtained : obtainedListOfResults) {
            assertEquals(expected, obtained.title);
        }
    }

    @Test
    public void testPartialSearchWithInvalidTerm() {
        String termToSearch = "League of legends";

        List<ResultInPlainText> obtainedListOfResults = ((viewToTest).pressSearchButtonWithGivenTermToSearch(termToSearch));

        for (ResultInPlainText ignored : obtainedListOfResults) {
            fail();
        }
    }

    @Test
    public void testSaveLastSearchedResult() {
        String idToSearch = "0";

        (viewToTest).searchByIDForTesting(idToSearch);
        (viewToTest).saveLastSearchedExtractForTesting();
        String obtained = (viewToTest).getLastSavedExtractSearchedInTheComboBoxStored();
        String expected = "Extract Expected";

        assertEquals(expected, TestUtilities.cleanAllFormatToPlainText(obtained));
    }

    @Test
    public void testLastSearchIsSavedInHistory() {
        String idToSearch = "0";

        (viewToTest).searchByIDForTesting(idToSearch);
        (viewToTest).saveLastSearchedExtractForTesting();
        String obtained = (viewToTest).getLastSavedExtractSearchedInTheComboBoxStored();
        String expected = "Extract Expected";

        assertEquals(expected, TestUtilities.cleanAllFormatToPlainText(obtained));
    }

}