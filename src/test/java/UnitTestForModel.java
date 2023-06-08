import model.ModelVideoGameWiki;
import model.ModelVideoGameWikiInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.SearchResult;
import utils.WikipediaPageAPI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestForModel {

    private static WikipediaPageAPI wikiApiTest;
    private TestUtilities testUtilities = new TestUtilities();
    private ModelVideoGameWikiInterface modelToTest = new ModelVideoGameWiki();

    @BeforeAll
    public static void initApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        wikiApiTest = retrofit.create(WikipediaPageAPI.class);
    }
    /*
    @BeforeEach
    public void initModel() {
        modelToTest.setApiHandler(wikiApiTest);
    }

    @Test
    public void testBusquedaTerminoValido() throws Exception {
        String termToSearch = "fifa";
        modelToTest.searchTerm(termToSearch);
        List<ResultInStrings> expected = testUtilities.convertResultFromApiToSearchResult(testUtilities.processResultFromApi(termToSearch, wikiApiTest));
        List<ResultInStrings> obtained = testUtilities.convertResultFromApiToSearchResult(modelToTest.getParcialResults());
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