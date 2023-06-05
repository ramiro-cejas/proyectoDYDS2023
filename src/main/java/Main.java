import controller.ControllerVideoGameWiki;
import model.ModelVideoGameWiki;
import utils.WikipediaPageAPI;

public class Main {

    public static void main(String[] args) {
        ModelVideoGameWiki model = new ModelVideoGameWiki();
        ControllerVideoGameWiki controller = new ControllerVideoGameWiki(model);
        controller.start();
    }
}