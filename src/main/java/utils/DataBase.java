package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DataBase {
  private static String url = "jdbc:sqlite:./dictionary.db";
  public static void loadDatabase() {
      excecuteGivenUpdate("create table if not exists catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");
      excecuteGivenUpdate("create table if not exists history (id INTEGER PRIMARY KEY AUTOINCREMENT, searchTerm string ,title string, date timestamp, pageId string)");
  }
  public static void testDB() {
    Connection connection = openConnection();
    try {
      ResultSet rs = excecuteGivenQuery(connection,"select * from catalog");
      while(rs.next()) {
        System.out.println("id = " + rs.getInt("id"));
        System.out.println("title = " + rs.getString("title"));
        System.out.println("extract = " + rs.getString("extract"));
        System.out.println("source = " + rs.getString("source"));
      }
    }
    catch(SQLException e) {
      System.out.println(e.getMessage());
    }
    closeConnection(connection);
  }
  private static Connection openConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url);
    }catch (SQLException e){
      System.out.println(e.getMessage());
    }
    return connection;
  }
  private static void closeConnection(Connection connection) {
    try {
      if(connection != null)
        connection.close();
    }
    catch(SQLException e) {
      System.err.println(e.getMessage());
    }
  }
  private static ResultSet excecuteGivenQuery(Connection connection, String query){
    ResultSet resultSetToReturn = null;
    try {
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      resultSetToReturn = statement.executeQuery(query);
    }catch (SQLException e){
      System.out.println(e.getMessage());
    }
    return resultSetToReturn;
  }
  private static void excecuteGivenUpdate(String query){
    Connection connection = openConnection();
    try {
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate(query);
    }catch (SQLException e){
      System.out.println(e.getMessage());
    }
    closeConnection(connection);
  }
  public static ArrayList<String> getTitles() {
    ArrayList<String> titles = new ArrayList<>();
    Connection connection = openConnection();
    ResultSet resultSet = excecuteGivenQuery(connection,"select * from catalog");
    try {
      while(resultSet.next()) titles.add(resultSet.getString("title"));
    } catch (SQLException e){
      System.out.println(e.getMessage());
    }
    closeConnection(connection);
    return titles;
  }
  public static void saveInfo(String title, String extract) {
    excecuteGivenUpdate("replace into catalog values(null, '"+ title + "', '"+ extract + "', 1)");
  }
  public static String getExtract(String title) {
    String extractToReturn = null;
    Connection connection = openConnection();
    try {
      ResultSet rs = excecuteGivenQuery(connection,"select * from catalog WHERE title = '" + title + "'" );
      rs.next();
      extractToReturn = rs.getString("extract");
    }
    catch(SQLException e) {
      System.err.println("Get title error " + e.getMessage());
    }
    closeConnection(connection);
    return extractToReturn;
  }
  public static void deleteEntry(String title) {
    excecuteGivenUpdate("DELETE FROM catalog WHERE title = '" + title + "'" );
  }
  public static void historySave(String title, String searchTerm, String pageId){
    Date today = new Date();
    Timestamp timestampNow = new Timestamp(today.getTime());
    excecuteGivenUpdate("replace into history values(null, '" + title + "', '"+ searchTerm + "', '" + timestampNow + "', '"+pageId+"')");
  }
  public static ArrayList<String> getHistory() {
    ArrayList<String> entriesOfHistory = new ArrayList<>();
    Connection connection = openConnection();
    try {
      ResultSet rs = excecuteGivenQuery(connection,"select * from history");
      while(rs.next()) {
        String idOfSearchedTermEntry = rs.getString("id");
        String titleOfSearched = rs.getString("title");
        String searchedTerm = rs.getString("searchTerm");
        String dateOfSearch = rs.getTimestamp("date").toString();
        System.out.println(idOfSearchedTermEntry + ". " +dateOfSearch + " | " + searchedTerm + " | " +titleOfSearched);
        entriesOfHistory.add(idOfSearchedTermEntry + ". " +dateOfSearch + " | " + searchedTerm + " | " +titleOfSearched);
      }
    }
    catch(SQLException e) {
      System.err.println(e.getMessage());
    }
    closeConnection(connection);
    return entriesOfHistory;
  }
  public static String getPageIdFromSavedHistorySearch(int id) {
    String pageIdToReturn = null;
    Connection connection = openConnection();
    try {
      ResultSet rs = excecuteGivenQuery(connection,"select * from history WHERE id = '" + id + "'" );
      rs.next();
      pageIdToReturn = rs.getString("pageId");
    }
    catch(SQLException e) {
      System.err.println("Get title error " + e.getMessage());
    }
    closeConnection(connection);
    return pageIdToReturn;
  }
}