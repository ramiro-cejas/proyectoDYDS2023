package dyds.videogameInfo.fulllogic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainWindow {
  private JTextField textFieldSearchTerm;
  private JButton buttonSearch;
  public JPanel contentPane;
  private JTextPane textPaneResult;
  private JButton buttonSaveLocally;
  private JTabbedPane tabbedPane1;
  private JPanel searchPanel;
  private JPanel storagePanel;
  private JComboBox comboBoxStored;
  private JTextPane textPaneStored;
  private JComboBox comboBoxHistory;
  private JPanel historyPanel;

  DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
  String selectedResultTitle = null; //For storage purposes, se below that it may not coincide with the searched term
  String text = ""; //Last searched text! this variable is central for everything

  public MainWindow() {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://en.wikipedia.org/w/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();

    WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
    WikipediaPageAPI pageAPI = retrofit.create(WikipediaPageAPI.class);

    comboBoxStored.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));

    textPaneResult.setContentType("text/html");
    textPaneStored.setContentType("text/html");
    // this is needed to open a link in the browser

    textFieldSearchTerm.addActionListener(actionEvent -> {System.out.println("ACCION!!!");});
    System.out.println("TYPED!!!");
    textFieldSearchTerm.addPropertyChangeListener(propertyChangeEvent -> {
              System.out.println("TYPED!!!");
    });

    //ToGustavo: They told us that you were having difficulties understanding this code,
    // Don't panic! We added several helpful comments to guide you through it ;)

    // From here on is where the magic happends: querying wikipedia, showing results, etc.
    buttonSearch.addActionListener(e -> new Thread(() -> {
              //This may take some time, dear user be patient in the meanwhile!
              setWorkingStatus();
              // get from service
              Response<String> callForSearchResponse;
              try {

                //ToGustavo: First, lets search for the term in Wikipedia
                callForSearchResponse = searchAPI.searchForTerm(textFieldSearchTerm.getText() + " articletopic:\"video-games\"").execute();

                //Show the result for testing reasons, if it works, dont forget to delete!
                System.out.println("JSON " + callForSearchResponse.body());

                //ToGustavo: This is the code process the search results for the query
                //Each result is stored in a JsonArray
                Gson gson = new Gson();
                JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
                JsonObject query = jobj.get("query").getAsJsonObject();
                Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
                JsonArray jsonResults = query.get("search").getAsJsonArray();

                //ToGustavo: shows each result in the JSonArry in a Popupmenu
                JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
                for (JsonElement je : jsonResults) {
                  JsonObject searchResult = je.getAsJsonObject();
                  String searchResultTitle = searchResult.get("title").getAsString();
                  String searchResultPageId = searchResult.get("pageid").getAsString();
                  String searchResultSnippet = searchResult.get("snippet").getAsString();

                  SearchResult sr = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
                  searchOptionsMenu.add(sr);

                  //ToGustavo: Adding an event to retrive the wikipage when the user clicks an item in the Popupmenu
                  sr.addActionListener(actionEvent -> {
                    try {
                      //This may take some time, dear user be patient in the meanwhile!
                      setWorkingStatus();
                      Response<String> callForPageResponse = pageAPI.getExtractByPageID(sr.pageID).execute();

                      System.out.println("JSON despued de seleccionar" + callForPageResponse.body());

                      //ToGustavo: This is similar to the code above, but here we parse the wikipage answer.
                      JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
                      JsonObject query2 = jobj2.get("query").getAsJsonObject();
                      JsonObject pages = query2.get("pages").getAsJsonObject();
                      Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
                      Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
                      JsonObject page = first.getValue().getAsJsonObject();
                      JsonElement searchResultExtract2 = page.get("extract");
                      if (searchResultExtract2 == null) {
                        text = "No Results";
                      } else {
                        text = "<h1>" + sr.title + "</h1>";
                        selectedResultTitle = sr.title;
                        text += searchResultExtract2.getAsString().replace("\\n", "\n");
                        text = textToHtml(text);

                        //Not yet...
                        //text+="\n" + "<a href=https://en.wikipedia.org/?curid=" + searchResultPageId +">View Full Article</a>";
                      }
                      textPaneResult.setText(text);
                      textPaneResult.setCaretPosition(0);
                      //Back to edit time!
                      setWatingStatus();
                    } catch (Exception e12) {
                      System.out.println(e12.getMessage());
                    }
                  });
                }
                searchOptionsMenu.show(textFieldSearchTerm, textFieldSearchTerm.getX(), textFieldSearchTerm.getY());
              } catch (IOException e1) {
                e1.printStackTrace();
              }

              //Now you can keep searching stuff!
              setWatingStatus();
    }).start());

    buttonSaveLocally.addActionListener(actionEvent -> {
      if(text != ""){
        // save to DB  <o/
        DataBase.saveInfo(selectedResultTitle.replace("'", "`"), text);  //Dont forget the ' sql problem
        comboBoxStored.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
      }
    });

    comboBoxStored.addActionListener(actionEvent -> textPaneStored.setText(textToHtml(DataBase.getExtract(comboBoxStored.getSelectedItem().toString()))));

    JPopupMenu storedInfoPopup = new JPopupMenu();

    JMenuItem deleteItem = new JMenuItem("Delete!");
    deleteItem.addActionListener(actionEvent -> {
        if(comboBoxStored.getSelectedIndex() > -1){
          DataBase.deleteEntry(comboBoxStored.getSelectedItem().toString());
          comboBoxStored.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
          textPaneStored.setText("");
        }
    });
    storedInfoPopup.add(deleteItem);

    JMenuItem saveItem = new JMenuItem("Save Changes!");
    saveItem.addActionListener(actionEvent -> {
        // save to DB  <o/
        DataBase.saveInfo(comboBoxStored.getSelectedItem().toString().replace("'", "`"), textPaneStored.getText());  //Dont forget the ' sql problem
        //comboBox1.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
    });
    storedInfoPopup.add(saveItem);

    textPaneStored.setComponentPopupMenu(storedInfoPopup);


  }
  public static void main(String[] args) {

    JFrame frame = new JFrame("Video Game Info");
    frame.setContentPane(new MainWindow().contentPane);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

    DataBase.loadDatabase();
    DataBase.saveInfo("test", "sarasa");


    System.out.println(DataBase.getExtract("test"));
    System.out.println(DataBase.getExtract("nada"));
  }

  private void setWorkingStatus() {
    for(Component c: this.searchPanel.getComponents()) c.setEnabled(false);
    textPaneResult.setEnabled(false);
  }

  private void setWatingStatus() {
    for(Component c: this.searchPanel.getComponents()) c.setEnabled(true);
    textPaneResult.setEnabled(true);
  }


  public static String textToHtml(String text) {

    StringBuilder builder = new StringBuilder();

    builder.append("<font face=\"arial\">");

    String fixedText = text
        .replace("'", "`"); //Replace to avoid SQL errors, we will have to find a workaround..

    builder.append(fixedText);

    builder.append("</font>");

    return builder.toString();
  }

}
