package view;

import javax.swing.*;

public class PopUPHandler {
    private final JPanel parentComponent;

    public PopUPHandler(JPanel parentComponent) {
        this.parentComponent = parentComponent;
    }

    public void showDeleteFinished() {
        JOptionPane.showMessageDialog(parentComponent, "Deleted Successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showSavedFinished() {
        JOptionPane.showMessageDialog(parentComponent, "Saved Successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showSQLException() {
        JOptionPane.showMessageDialog(parentComponent, "An error has occurred while accessing the database!", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public void showSearchException() {
        JOptionPane.showMessageDialog(parentComponent, "An error has occurred while searching the given term!", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public void showSearchByIDException() {
        JOptionPane.showMessageDialog(parentComponent, "An error has occurred while searching the given ID!", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
