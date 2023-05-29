package view;

import javax.swing.*;

public class PopUPHandler {
    private JPanel parentComponent;

    public PopUPHandler(JPanel parentComponent){
        this.parentComponent = parentComponent;
    }
    public void showDeleteFinished() {
        JOptionPane.showMessageDialog(parentComponent, "Delete Succesfull!", "INFO", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showSavedFinished() {
        JOptionPane.showMessageDialog(parentComponent, "Saved Succesfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
    }
}
