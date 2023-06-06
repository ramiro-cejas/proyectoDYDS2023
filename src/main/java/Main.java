import controller.ControllerVideoGameWiki;
import model.ModelVideoGameWiki;
import model.ModelVideoGameWikiInterface;

public class Main {

    public static void main(String[] args) {
        ModelVideoGameWikiInterface model = new ModelVideoGameWiki();
        ControllerVideoGameWiki controller = new ControllerVideoGameWiki(model);
        controller.start();
    }
}