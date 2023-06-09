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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTest {

    private ModelVideoGameWikiInterface modelToTest;
    private ViewVideoGameWikiInterface viewToTest;
    private ControllerVideoGameWikiInterface controllerToTest;

    @BeforeEach
    public void initElementsToTest() throws IOException {
        modelToTest = new ModelVideoGameWiki();
        controllerToTest = new ControllerVideoGameWiki(modelToTest);
        viewToTest = new ViewClassExtensionToTest(controllerToTest,modelToTest);
        ApiHandler apiHandlerMockedForTest = mock(ApiHandler.class);
        mockSearchByIDInApiHandler(apiHandlerMockedForTest);
        mockSearchByTermInApiHandler(apiHandlerMockedForTest);
    }

    private void mockSearchByIDInApiHandler(ApiHandler apiHandlerMocked) throws IOException {
        when(apiHandlerMocked.getExtractByPageID("0")).thenReturn(new ResultInPlainText("Title Expected","0","Snippet Expected","Extract Expected"));
        modelToTest.setApiHandler(apiHandlerMocked);
    }

    private void mockSearchByTermInApiHandler(ApiHandler apiHandlerMocked) throws IOException {
        when(apiHandlerMocked.searchForTerm("League of Legends")).thenReturn(
                List.of(new ResultInPlainText("Title Expected: " + "League of Legends", "0", "Snippet Expected", "Extract Expected"),new ResultInPlainText("Title Expected: " + "League of Legends", "0", "Snippet Expected", "Extract Expected"),new ResultInPlainText("Title Expected: " + "League of Legends", "0", "Snippet Expected", "Extract Expected"))
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
    public void testBusquedaParcialValido() {
        String termToSearch = "League of legends";

        String expected = "Title Expected: " + termToSearch;
        List<ResultInPlainText> obtainedList = (((ViewClassExtensionToTest)viewToTest).pressSearchButtonWithGivenTermToSearch(termToSearch));

        for (ResultInPlainText obtained : obtainedList) {
            assertEquals(expected, obtained.title);
        }
    }

}