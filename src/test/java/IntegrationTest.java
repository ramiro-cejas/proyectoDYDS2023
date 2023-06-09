import controller.ControllerVideoGameWiki;
import controller.ControllerVideoGameWikiInterface;
import model.ModelVideoGameWiki;
import model.ModelVideoGameWikiInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ApiHandler;
import utils.ResultInPlainText;
import view.ViewVideoGameWikiInterface;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTest {

    private ModelVideoGameWikiInterface modelToTest;
    private ViewVideoGameWikiInterface viewToTest;
    private ControllerVideoGameWikiInterface controllerToTest;
    private ApiHandler apiHandlerMockedForTest;
    @BeforeEach
    public void initElementsToTest() throws IOException {
        modelToTest = new ModelVideoGameWiki();
        controllerToTest = new ControllerVideoGameWiki(modelToTest);
        viewToTest = new ViewClassExtensionToTest(controllerToTest,modelToTest);
        apiHandlerMockedForTest = mock(ApiHandler.class);
        mockSearchByIDInApiHandler(apiHandlerMockedForTest);
    }

    private void mockSearchByIDInApiHandler(ApiHandler apiHandlerMocked) throws IOException {
        when(apiHandlerMocked.getExtractByPageID("0")).thenReturn(new ResultInPlainText("Title Expected","0","Snippet Expected","Extract Expected"));
        modelToTest.setApiHandler(apiHandlerMocked);
    }

    private void mockSearchByTermInApiHandler(ApiHandler apiHandlerMocked, String termToMock) throws IOException {
        when(apiHandlerMocked.searchForTerm(termToMock)).thenReturn(
                List.of(new ResultInPlainText("Title: "+termToMock, "0", "Snippet: "+termToMock, "Extract: "+termToMock),new ResultInPlainText("Title: "+termToMock, "0", "Snippet: "+termToMock, "Extract: "+termToMock),new ResultInPlainText("Title: "+termToMock, "0", "Snippet: "+termToMock, "Extract: "+termToMock))
        );
        modelToTest.setApiHandler(apiHandlerMocked);
    }

    @Test
    public void testBusquedaIDValido(){
        String idToSearch = "0";

        String expected = TestUtilities.cleanAllHTMLTags("Extract Expected");
        String obtained = TestUtilities.cleanAllHTMLTags(((ViewClassExtensionToTest)viewToTest).searchByIDForTesting(idToSearch).extract);

        assertEquals(expected, obtained);
    }

    @Test
    public void testBusquedaIDInvalido() {
        String idToSearch = "2";

        String expected = ("searchByIDExceptionHasOcurred");
        String obtained = (((ViewClassExtensionToTest)viewToTest).searchByIDForTesting(idToSearch).extract);

        assertEquals(expected, obtained);
    }

    @Test
    public void testBusquedaIDVacio(){
        String idToSearch = "";

        String expected = ("searchByIDExceptionHasOcurred");
        String obtained = (((ViewClassExtensionToTest)viewToTest).searchByIDForTesting(idToSearch).extract);

        assertEquals(expected, obtained);
    }

    @Test
    public void testBusquedaParcialValido() throws IOException {
        String termToSearch = "League of legends";
        mockSearchByTermInApiHandler(apiHandlerMockedForTest, termToSearch);

        String expected = "Title: " + termToSearch;
        List<ResultInPlainText> obtainedListOfResults = (((ViewClassExtensionToTest)viewToTest).pressSearchButtonWithGivenTermToSearch(termToSearch));

        for (ResultInPlainText obtained : obtainedListOfResults) {
            assertEquals(expected, obtained.title);
        }
    }

    @Test
    public void testBusquedaParcialInvalido() {
        String termToSearch = "League of legends";

        List<ResultInPlainText> obtainedListOfResults = (((ViewClassExtensionToTest)viewToTest).pressSearchButtonWithGivenTermToSearch(termToSearch));

        for (ResultInPlainText ignored : obtainedListOfResults) {
            fail();
        }
    }

    @Test
    public void testSaveLastSearchedResult() {
        String idToSearch = "0";

        ((ViewClassExtensionToTest)viewToTest).searchByIDForTesting(idToSearch);
        ((ViewClassExtensionToTest)viewToTest).saveLastSearchedExtractForTesting();
        String obtained = ((ViewClassExtensionToTest)viewToTest).getLastSavedExtractSearchedInTheComboBoxStored();
        String expected = "Extract Expected";

        assertEquals(expected,TestUtilities.cleanAllFormatToPlainText(obtained));
    }

}