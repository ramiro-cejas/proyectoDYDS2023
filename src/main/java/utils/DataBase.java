package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DataBase {
    public static void loadDatabase() throws SQLException {
        excecuteGivenUpdate("create table if not exists catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");
        excecuteGivenUpdate("create table if not exists history (id INTEGER PRIMARY KEY AUTOINCREMENT, searchTerm string ,title string, date timestamp, pageId string)");
    }

    private static Connection openConnection() throws SQLException {
        Connection connection;
        String url = "jdbc:sqlite:./dictionary.db";
        connection = DriverManager.getConnection(url);

        return connection;
    }

    private static void closeConnection(Connection connection) throws SQLException {
        if (connection != null)
            connection.close();
    }

    private static ResultSet excecuteGivenQuery(Connection connection, String query) throws SQLException {
        ResultSet resultSetToReturn;
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        resultSetToReturn = statement.executeQuery(query);
        return resultSetToReturn;
    }

    private static void excecuteGivenUpdate(String query) throws SQLException {
        Connection connection = openConnection();
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(query);
        closeConnection(connection);
    }

    public static ArrayList<String> getTitles() throws SQLException {
        ArrayList<String> titles = new ArrayList<>();
        Connection connection = openConnection();
        ResultSet resultSet = excecuteGivenQuery(connection, "select * from catalog");

        while (resultSet.next()) titles.add(resultSet.getString("title"));

        closeConnection(connection);
        return titles;
    }

    public static void saveInfo(String title, String extract) throws SQLException {
        excecuteGivenUpdate("replace into catalog values(null, '" + title + "', '" + extract + "', 1)");
    }

    public static String getExtract(String title) throws SQLException {
        String extractToReturn;
        Connection connection = openConnection();

        ResultSet rs = excecuteGivenQuery(connection, "select * from catalog WHERE title = '" + title + "'");
        rs.next();
        extractToReturn = rs.getString("extract");

        closeConnection(connection);
        return extractToReturn;
    }

    public static void deleteEntry(String title) throws SQLException {
        excecuteGivenUpdate("DELETE FROM catalog WHERE title = '" + title + "'");
    }

    public static void historySave(String title, String searchTerm, String pageId) throws SQLException {
        Date today = new Date();
        Timestamp timestampNow = new Timestamp(today.getTime());
        excecuteGivenUpdate("replace into history values(null, '" + title + "', '" + searchTerm + "', '" + timestampNow + "', '" + pageId + "')");
    }

    public static ArrayList<String> getHistory() throws SQLException {
        ArrayList<String> entriesOfHistory = new ArrayList<>();
        Connection connection = openConnection();
        ResultSet rs = excecuteGivenQuery(connection, "select * from history");
        while (rs.next()) {
            String idOfSearchedTermEntry = rs.getString("id");
            String titleOfSearched = rs.getString("title");
            String searchedTerm = rs.getString("searchTerm");
            String dateOfSearch = rs.getTimestamp("date").toString();
            entriesOfHistory.add(idOfSearchedTermEntry + ". " + dateOfSearch + " | " + searchedTerm + " | " + titleOfSearched);
        }

        closeConnection(connection);
        return entriesOfHistory;
    }

    public static String getPageIdFromSavedHistorySearch(int id) throws SQLException {
        String pageIdToReturn;
        Connection connection = openConnection();
        ResultSet rs = excecuteGivenQuery(connection, "select * from history WHERE id = '" + id + "'");
        rs.next();
        pageIdToReturn = rs.getString("pageId");
        closeConnection(connection);
        return pageIdToReturn;
    }
}