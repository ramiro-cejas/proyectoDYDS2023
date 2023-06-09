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

        //TODO hacer el api handler para que todo el formateo HTML lo haga la vista, el API handler parsea el json de la api de wiki y retorna Strings crudos o bien una nueva clase que sea

        //TODO en la documentacion agregar que en los test se utiliza el repo local, deberia poder usarse una db alternativa
        //TODO agregar que todos los handler y explicar como arme los nombres, ej todo lo que sea handler surge de delegacion de dependendias
        //TODO agregar tambien que utilice jupiter en vez de junit4 por eso se usa BeforeEach
        //TODO el test se desarrolla en una unica clase para testear la totalidad de la funcionalidad del modelo pero deberia ser en varias clases chiquitas para cada funcionalidad, cuestion de tiempo de desarrollo
        //TODO tambien decir que en terminos de la inversion de dependencias, la utilizacion de las interfaces en todo lo que sea Handler
        //TODO

    }
}