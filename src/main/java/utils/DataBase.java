package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DataBase {

  public static void loadDatabase() {
    String url = "jdbc:sqlite:./dictionary.db";

    try (Connection connection = DriverManager.getConnection(url)) {
      if (connection != null) {

        DatabaseMetaData meta = connection.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());

        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        statement.executeUpdate("create table if not exists catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");

        statement.executeUpdate("create table if not exists history (id INTEGER, searchTerm string ,title string, date timestamp)");

      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testDB()
  {

    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      ResultSet rs = statement.executeQuery("select * from catalog");
      while(rs.next())
      {
        System.out.println("id = " + rs.getInt("id"));
        System.out.println("title = " + rs.getString("title"));
        System.out.println("extract = " + rs.getString("extract"));
        System.out.println("source = " + rs.getString("source"));

      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }

  public static ArrayList<String> getTitles()
  {
    ArrayList<String> titles = new ArrayList<>();
    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      ResultSet rs = statement.executeQuery("select * from catalog");
      while(rs.next()) titles.add(rs.getString("title"));
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
      return titles;
    }
  }

  public static void saveInfo(String title, String extract)
  {
    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");

      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      System.out.println("INSERT  " + title + "', '"+ extract);

      statement.executeUpdate("replace into catalog values(null, '"+ title + "', '"+ extract + "', 1)");
    }
    catch(SQLException e)
    {
      System.err.println("Error saving " + e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        System.err.println( e);
      }
    }
  }

  public static String getExtract(String title)
  {

    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      ResultSet rs = statement.executeQuery("select * from catalog WHERE title = '" + title + "'" );
      rs.next();
      return rs.getString("extract");
    }
    catch(SQLException e)
    {
      System.err.println("Get title error " + e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
    return null;
  }

  public static void deleteEntry(String title)
  {

    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      statement.executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'" );

    }
    catch(SQLException e)
    {
      System.err.println("Get title error " + e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }


  public static void historySave(String title, String searchTerm) {
    {
      Connection connection = null;
      try
      {
        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");

        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        java.util.Date date = new Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

        System.out.println("INSERT  '" + title + "', '"+ searchTerm + "', '" + timestamp + "'");

        statement.executeUpdate("replace into history values(null, '" + title + "', '"+ searchTerm + "', '" + timestamp + "')");
      }
      catch(SQLException e)
      {
        System.err.println("Error saving " + e.getMessage());
      }
      finally
      {
        try
        {
          if(connection != null)
            connection.close();
        }
        catch(SQLException e)
        {
          System.err.println( e);
        }
      }
    }
  }

  public static ArrayList<String> getHistory()
  {
    ArrayList<String> entriesOfHistory = new ArrayList<>();
    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      ResultSet rs = statement.executeQuery("select * from history");
      while(rs.next()) {
        String titleOfSearched = rs.getString("title");
        String searchedTerm = rs.getString("searchTerm");
        String dateOfSearch = rs.getTimestamp("date").toString();
        entriesOfHistory.add(dateOfSearch + " | " + searchedTerm + " | " +titleOfSearched);
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
      return entriesOfHistory;
    }
  }
}
