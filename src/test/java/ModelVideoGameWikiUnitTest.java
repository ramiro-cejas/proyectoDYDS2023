package tests;


import main.java.model.SearchModel;
import main.java.model.SearchModelListener;
import main.java.utils.WikiSearchSimlator;
import model.ModelVideoGameWiki;
import org.junit.Before;
import org.junit.Test;
import utils.WikipediaPageAPI;


import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelVideoGameWikiUnitTest {

    ModelVideoGameWiki modelToTest;

    @Before
    public void setUp() throws Exception {
        modelToTest = new ModelVideoGameWiki();

        WikipediaPageAPI wikiSearchSimulator = mock(WikipediaPageAPI.class);
        String term = "";
        when(wikiSearchSimulator.searchForTerm(term)).thenReturn();
        modelToTest.setWikipediaPageAPI(wikiSearchSimulator);
    }

    @Test
    public void last2SeachesInHistoryWithNoElements() {
        modelToTest.calculateLast2Searches();
        assertEquals("There are no searches in history!", modelToTest.getLast2Searches());
    }

    @Test
    public void last2SeachesInHistoryWith1elment() {
        modelToTest.getSearchHistory().add("search1");
        modelToTest.calculateLast2Searches();
        assertEquals("search1", modelToTest.getLast2Searches());
    }

    @Test
    public void last2SeachesInHistoryWith2elments() {
        modelToTest.getSearchHistory().addAll(Arrays.asList("search1","search2"));
        modelToTest.calculateLast2Searches();
        assertEquals("search1-search2", modelToTest.getLast2Searches());
    }
    @Test
    public void last2SeachesInHistoryWith5elments() {
        modelToTest.getSearchHistory().addAll(Arrays.asList("search1","search2","search3","search4","search5"));
        modelToTest.calculateLast2Searches();
        assertEquals("search4-search5", modelToTest.getLast2Searches());
    }

    @Test
    public void searchTermWithBlankInput() {
        String testTerm = "";
        modelToTest.searchTerm(testTerm);
        assertEquals("No search can be made for empty terms", modelToTest.getLastSearchResult());
    }

    @Test
    public void searchTermWithLessThan20Chars() {
        String testTerm = "Hello World";
        modelToTest.searchTerm(testTerm);
        assertEquals("This is a simulated text result for the searched term: "+testTerm, modelToTest.getLastSearchResult());
    }

    @Test
    public void searchTermWithMoreThan20Chars() {
        String testTerm = "Hello World this is a test, just trying to break the method searchTerm";
        modelToTest.searchTerm(testTerm);
        assertEquals("The term:"+testTerm+" exedes the lenght limit of 20 chars, search for something shorter", modelToTest.getLastSearchResult());
    }

    @Test
    public void searchTermWithExactThan20Chars() {
        String testTerm = "This term have 20 ch";
        modelToTest.searchTerm(testTerm);
        assertEquals("This is a simulated text result for the searched term: "+testTerm, modelToTest.getLastSearchResult());
    }

}
