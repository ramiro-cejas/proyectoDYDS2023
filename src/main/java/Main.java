import controller.ControllerVideoGameWiki;
import controller.ControllerVideoGameWikiInterface;
import model.ModelVideoGameWiki;
import model.ModelVideoGameWikiInterface;
import view.ViewVideoGameWiki;
import view.ViewVideoGameWikiInterface;

public class Main {

    public static void main(String[] args) {
        ModelVideoGameWikiInterface model = new ModelVideoGameWiki();
        ControllerVideoGameWikiInterface controller = new ControllerVideoGameWiki(model);
        ViewVideoGameWikiInterface vista = new ViewVideoGameWiki(controller,model);
        vista.start();
    }
}