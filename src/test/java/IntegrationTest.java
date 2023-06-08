import controller.ControllerVideoGameWiki;
import controller.ControllerVideoGameWikiInterface;
import model.ModelVideoGameWiki;
import model.ModelVideoGameWikiInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.ViewVideoGameWikiInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    private TestUtilities testUtilities;
    private ModelVideoGameWikiInterface modelToTest;
    private ViewVideoGameWikiInterface viewToTest;
    private ControllerVideoGameWikiInterface controllerToTest;

    @BeforeEach
    public void initElementsToTest() {
        testUtilities = new TestUtilities();
        modelToTest = new ModelVideoGameWiki();
        controllerToTest = new ControllerVideoGameWiki(modelToTest);
        viewToTest = new ViewClassExtensionToTest(controllerToTest,modelToTest);
    }

    @Test
    public void testBusquedaIDValido(){
        String idToSearch = "21838352";

        String obtained = ((ViewClassExtensionToTest)viewToTest).searchByIDForTesting(idToSearch);
        String expected = "";
        
        assertEquals(expected, TestUtilities.cleanAllHTMLTags(obtained));
    }
}