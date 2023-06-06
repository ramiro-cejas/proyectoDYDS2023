import model.ModelVideoGameWiki;
import model.ModelVideoGameWikiInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.WikipediaPageAPI;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelUnitTest {

    private static WikipediaPageAPI wikiApiTest;
    private final TestUtilities testUtilities = new TestUtilities();
    private final ModelVideoGameWikiInterface modelToTest = new ModelVideoGameWiki();

    @BeforeAll
    public static void initApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        wikiApiTest = retrofit.create(WikipediaPageAPI.class);
    }

    @BeforeEach
    public void initModel() {
        modelToTest.setWikipediaPageAPI(wikiApiTest);
    }

    @Test
    public void testBusquedaTerminoValido() throws Exception {
        String termToSearch = "fifa";
        modelToTest.searchTerm(termToSearch);
        ArrayList expected = testUtilities.convertResultFromApiToSearchResult(testUtilities.processResultFromApi(termToSearch, wikiApiTest));
        ArrayList obtained = testUtilities.convertResultFromApiToSearchResult(modelToTest.getParcialResults());
        assertEquals(expected.toString(), obtained.toString());
    }

    @Test
    public void testBusquedaTerminoVacio() throws Exception {
        String termToSearch = "";
        modelToTest.searchTerm(termToSearch);
        ArrayList expected = testUtilities.convertResultFromApiToSearchResult(testUtilities.processResultFromApi(termToSearch, wikiApiTest));
        ArrayList obtained = testUtilities.convertResultFromApiToSearchResult(modelToTest.getParcialResults());
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
        //String expected = testUtilities.getTitleWithExtractFromResponseByID(wikiApiTest.getExtractByPageID(idToSearch).execute());
        String obtained = modelToTest.getSelectedResultTitle() + "\n" + modelToTest.getSelectedResultExtract();
        assertEquals("null\nnull", obtained);
    }
}