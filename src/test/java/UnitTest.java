import model.ModelVideoGameWiki;
import model.ModelVideoGameWikiInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.APIHandler.ApiHandler;
import utils.ResultInPlainText;
import utils.SearchResult;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitTest {

    private ApiHandler apiHandlerMockedForTest;
    private ModelVideoGameWikiInterface modelToTest;

    @BeforeEach
    public void initModel() throws IOException {
        modelToTest = new ModelVideoGameWiki();
        apiHandlerMockedForTest = mock(ApiHandler.class);

        mockSearchByIDInApiHandler(apiHandlerMockedForTest);

        modelToTest.setApiHandler(apiHandlerMockedForTest);
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
    public void testBusquedaTerminoValido() throws Exception {
        String termToSearch = "fifa";

        mockSearchByTermInApiHandler(apiHandlerMockedForTest,termToSearch);
        modelToTest.searchTerm(termToSearch);
        ResultInPlainText expected = new ResultInPlainText("Title: "+termToSearch, "0", "Snippet: "+termToSearch, "Extract: "+termToSearch);
        List<ResultInPlainText> obtainedList = (modelToTest.getParcialResults());
        for (ResultInPlainText obtained:obtainedList) {
            assertEquals(expected.toString(), obtained.toString());
        }
    }

    @Test
    public void testBusquedaTerminoSinResultados() {
        String termToSearch = "asddsaasddsaasdqwe";
        modelToTest.searchTerm(termToSearch);
        List<ResultInPlainText> obtainedList = (modelToTest.getParcialResults());
        for (ResultInPlainText ignored :obtainedList) {
            fail();
        }
    }

    @Test
    public void testBusquedaTerminoVacio() {
        String termToSearch = "";
        modelToTest.searchTerm(termToSearch);
        List<ResultInPlainText> obtainedList = (modelToTest.getParcialResults());
        for (ResultInPlainText ignored :obtainedList) {
            fail();
        }
    }

    @Test
    public void testBusquedaPorIDValido() {
        String idToSearch = "0";
        modelToTest.searchTermByPageId(idToSearch);
        ResultInPlainText expected = new ResultInPlainText("Title Expected","0","Snippet Expected","Extract Expected");
        ResultInPlainText obtained = (modelToTest.getLastSearchResult());
        assertEquals(expected.toString(), obtained.toString());
    }

    @Test
    public void testBusquedaPorIDInvalido() {
        String idToSearch = "232123";
        modelToTest.searchTermByPageId(idToSearch);
        ResultInPlainText obtained = (modelToTest.getLastSearchResult());
        assertNull(obtained);
    }

    @Test
    public void testBusquedaPorIDVacio() {
        String idToSearch = "";
        modelToTest.searchTermByPageId(idToSearch);
        ResultInPlainText obtained = (modelToTest.getLastSearchResult());
        assertNull(obtained);
    }

    @Test
    public void saveInDBWithValidIDBefore() {
        String idToSearch = "0";
        modelToTest.searchTermByPageId(idToSearch);
        modelToTest.saveSearchedResult(modelToTest.getLastSearchResult().extract);
        modelToTest.searchStoredResult(modelToTest.getLastSearchResult().title);

        String expected = modelToTest.getLastSearchResult().extract;
        String obtained = modelToTest.getStoredResultExtract();
        assertEquals(expected, obtained);
    }

    @Test
    public void saveInDBWithInvalidIDBefore() {
        String idToSearch = "123321";
        modelToTest.searchTermByPageId(idToSearch);
        try {
            modelToTest.saveSearchedResult(modelToTest.getLastSearchResult().extract);
            modelToTest.searchStoredResult(modelToTest.getLastSearchResult().title);
            fail();
        } catch (Exception e){
            String obtained = modelToTest.getStoredResultExtract();
            assertNull(obtained);
        }
    }

    @Test
    public void testHistory() {
        String termToSearch = "League of Leg";
        String titleExpected = "League of Legends";

        modelToTest.searchSelectedTerm(new SearchResult(titleExpected,"21838352",termToSearch),termToSearch);

        String titleObtained = modelToTest.getLastTitleSavedOnHistory();
        String termObtained = modelToTest.getLastSearchTermSavedOnHistory();
        assertEquals(termToSearch+titleExpected, termObtained+titleObtained);
    }

}