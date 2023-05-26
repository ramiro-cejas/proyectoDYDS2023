import controller.ControllerVideoGameWiki;
import model.ModelVideoGameWiki;

public class Main {

    public static void main(String[] args) {
        ModelVideoGameWiki model = new ModelVideoGameWiki();
        ControllerVideoGameWiki controller = new ControllerVideoGameWiki(model);
        controller.start();
    }
}