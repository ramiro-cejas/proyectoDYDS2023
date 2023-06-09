import model.ModelVideoGameWiki;
import model.ModelVideoGameWikiInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.ApiHandler;
import utils.ResultInPlainText;
import utils.SearchResult;
import utils.WikipediaPageAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UnitTestForModel {

    private ApiHandler apiHandlerMockedForTest;
    private ModelVideoGameWikiInterface modelToTest;

    @BeforeEach
    public void initModel() throws IOException {
        modelToTest = new ModelVideoGameWiki();

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
        modelToTest.searchTerm(termToSearch);
        List<ResultInStrings> expected = TestUtilities.convertResultFromApiToSearchResult(TestUtilities.processResultFromApi(termToSearch, wikiApiTest));
        List<ResultInStrings> obtained = TestUtilities.convertResultFromApiToSearchResult(modelToTest.getParcialResults());
        assertEquals(expected.toString(), obtained.toString());
    }

    @Test
    public void testBusquedaTerminoSinResultados() throws Exception {
        String termToSearch = "asddsaasddsaasdqwe";
        modelToTest.searchTerm(termToSearch);
        ArrayList<ResultInStrings> expected = testUtilities.convertResultFromApiToSearchResult(testUtilities.processResultFromApi(termToSearch, wikiApiTest));
        ArrayList<ResultInStrings> obtained = testUtilities.convertResultFromApiToSearchResult(modelToTest.getParcialResults());
        assertEquals(expected.toString(), obtained.toString());
    }

    @Test
    public void testBusquedaTerminoVacio() throws Exception {
        String termToSearch = "";
        modelToTest.searchTerm(termToSearch);
        ArrayList<ResultInStrings> expected = testUtilities.convertResultFromApiToSearchResult(testUtilities.processResultFromApi(termToSearch, wikiApiTest));
        ArrayList<ResultInStrings> obtained = testUtilities.convertResultFromApiToSearchResult(modelToTest.getParcialResults());
        assertEquals(expected.toString(), obtained.toString());
    }

    @Test
    public void testBusquedaPorIDValido() throws Exception {
        String idToSearch = "21838352";
        modelToTest.searchTermByPageId(idToSearch);
        String expected = testUtilities.getTitleWithExtractFromResponseByID(wikiApiTest.getExtractByPageID(idToSearch).execute());
        String obtained = modelToTest.getSelectedResultTitle() + "\n" + modelToTest.getSelectedResultExtract();
        assertEquals(expected, obtained);
    }

    @Test
    public void testBusquedaPorIDInvalido() {
        String idToSearch = "0";
        modelToTest.searchTermByPageId(idToSearch);
        String obtained = modelToTest.getSelectedResultTitle() + "\n" + modelToTest.getSelectedResultExtract();
        assertEquals("null\nnull", obtained);
    }

    @Test
    public void testBusquedaPorIDVacio() {
        String idToSearch = "";
        modelToTest.searchTermByPageId(idToSearch);
        String obtained = modelToTest.getSelectedResultTitle() + "\n" + modelToTest.getSelectedResultExtract();
        assertEquals("null\nnull", obtained);
    }

    @Test
    public void saveInDB() {
        String idToSearch = "21838352";
        modelToTest.searchTermByPageId(idToSearch);
        modelToTest.saveSearchedResult(modelToTest.getSelectedResultExtract());
        modelToTest.searchStoredResult(modelToTest.getSelectedResultTitle());

        String expected = TestUtilities.removeBlankSpaces(TestUtilities.removeLineBreaks(TestUtilities.cleanAllHTMLTags(modelToTest.getLastSearchResult())));
        String obtained = TestUtilities.removeBlankSpaces(TestUtilities.removeLineBreaks((TestUtilities.cleanAllHTMLTags(modelToTest.getStoredResultExtract()))));
        assertEquals(expected, obtained);
    }

    @Test
    public void testHistory() {
        String termToSearch = "Legaue of Leg";
        String titleExpected = "League of Legends";

        modelToTest.searchSelectedTerm(new SearchResult(titleExpected,"21838352",termToSearch),termToSearch);

        String titleObtained = modelToTest.getLastTitleSavedOnHistory();
        String termObtained = modelToTest.getLastSearchTermSavedOnHistory();
        assertEquals(termToSearch+titleExpected, termObtained+titleObtained);
    }

    */
}