package editor.controller;

import editor.model.NotepadModel;
import editor.view.NotepadWindow;

import javax.swing.*;
import java.io.*;
import java.util.LinkedHashMap;

/**
 * Controller of the Notepad application. It manages the events of the menu items and the text area.
 *
 * @author Alejandro López Martínez
 * @version 1.0
 */
public class NotepadController {
    private NotepadWindow view;
    private NotepadModel model;

    /**
     * Constructor of the NotepadController class. It initializes the view and the model of the application.
     *
     * @param view  NotepadWindow object representing the view of the application.
     * @param model NotepadModel object representing the model of the application.
     */
    public NotepadController(NotepadWindow view, NotepadModel model) {
        this.view = view;
        this.model = model;
        initListeners();
        view.setVisible(true);
    }

    /**
     * Initializes the listeners of the menu items.
     */
    private void initListeners() {
        LinkedHashMap<String, JMenuItem> lhm_items_archive = view.getItemsArchive();
        LinkedHashMap<String, JMenuItem> lhm_items_edit = view.getItemsEdit();
        lhm_items_archive.get("saveAs").addActionListener(e -> saveAsFile());
        lhm_items_archive.get("save").addActionListener(e -> saveFile());
        lhm_items_archive.get("new").addActionListener(e -> newFile());
        lhm_items_archive.get("open").addActionListener(e -> loadFile());
        lhm_items_archive.get("exit").addActionListener(e -> exit());
        lhm_items_edit.get("search").addActionListener(e -> searchWord());
    }

    /**
     * Saves the text of the text area in a file selected by the user.
     */
    private void saveAsFile() {
        File file = model.getSelectedFile(NotepadModel.SAVE);
        if (file != null) {
            if (file.isFile()) {
                int option = JOptionPane.showConfirmDialog(view.getTextArea(),
                        "You are going to overwrite the selected file.\nDo you want to save the changes?",
                        "Save changes"
                        , JOptionPane.YES_NO_OPTION);
                if (option != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            try (BufferedWriter bfw = new BufferedWriter(new FileWriter(file))) {
                bfw.write(view.getTextArea().getText());
                updatePath(file.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view.getTextArea(),
                        "There was an error processing the file",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Saves the text of the text area in the file opened by the user. If the file has not been opened yet or
     * does not exist, it calls the saveAsFile method.
     */
    private void saveFile() {
        if (!model.rutaFichero.isEmpty()) {
            try (BufferedWriter bfw = new BufferedWriter(
                    new FileWriter(
                            model.rutaFichero))) {
                bfw.write(view.getTextArea().getText());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view.getTextArea(),
                        "The file could not be saved",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveAsFile();
        }
    }

    /**
     * Creates a new file. If the text area is not empty, it asks the user if they want to save the changes.
     */
    private void newFile() {
        askBeforeClose();
        view.setText("");
        updatePath("");
    }

    /**
     * Loads a file selected by the user and shows its content in the text area.
     */
    private void loadFile() {
        askBeforeClose();
        File file = model.getSelectedFile(NotepadModel.OPEN);
        StringBuilder text = new StringBuilder();
        if (file != null) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = bfr.readLine()) != null) {
                    text.append(line).append("\n");
                }
                view.setText(text.toString());
                updatePath(file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(view.getTextArea(),
                        "The file could not be found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view.getTextArea(),
                        "The file could not be read.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Searches a word in the text area. It shows a dialog to enter the word to search and shows a message dialog
     * with the result of the search. This method is not case sensitive.
     * This method has been implemented only for didactic purposes and will change in future versions.
     */
    private void searchWord() {
        String word = JOptionPane.showInputDialog(view.getTextArea(),
                "",
                "Search word",
                JOptionPane.INFORMATION_MESSAGE);

        if (word != null && !word.isEmpty()) {
            if (view.getTextArea().getText().toLowerCase().contains(word.toLowerCase())) {
                JOptionPane.showMessageDialog(view.getTextArea(),
                        "The text has been found in the file.",
                       "Successful search",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view.getTextArea(),
                        "The text could not be found in the file.",
                        "Failed search",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Asks the user if they want to save the changes before closing the file.
     */
    private void askBeforeClose() {
        if (!view.getTextArea().getText().isEmpty()) {
            int option = JOptionPane.showConfirmDialog(
                    view.getTextArea(),
                    "Do you want to save the changes before closing the file?",
                    "Save changes",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                saveFile();
                JOptionPane.showMessageDialog(view.getTextArea(),
                        "The changes have been saved.",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Updates the path of the file in the model and the view.
     *
     * @param s The path of the file.
     */
    private void updatePath(String s) {
        model.rutaFichero = s;
        view.setPath(model.rutaFichero);
    }

    /**
     * Exits the application. Asks the user if they want to save the changes before closing the file.
     */
    private void exit() {
        askBeforeClose();
        System.exit(0);
    }
}
