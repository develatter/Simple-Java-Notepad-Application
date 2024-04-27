package editor.model;

import javax.swing.*;
import java.io.File;

/**
 * Represents the model of the Notepad application. It contains the path of the file to be opened or saved.
 * @version 1.0
 * @author Alejandro López Martínez
 */
public class NotepadModel {
    public String rutaFichero;
    public static final byte OPEN = 0;
    public static final byte SAVE = 1;

    /**
     * Constructor for the NotepadModel class. It just initializes the path of the file to be opened or saved.
     */
    public NotepadModel() {
        rutaFichero = "";
    }

    /**
     * Shows a dialog to select a file to be opened or saved.
     * @param operation The operation to be performed: OPEN or SAVE.
     * @return The file selected by the user or null if the user cancels the operation.
     */
    public File getSelectedFile(byte operation) {
        JFileChooser jfc = new JFileChooser();
        File file = null;
        int option;
        if (operation == OPEN) {
            option = jfc.showOpenDialog(null);
        } else if (operation == SAVE) {
            option = jfc.showSaveDialog(null);
        } else {
            throw new IllegalArgumentException("Operation not valid");
        }
        if (option == JFileChooser.APPROVE_OPTION) {
            file = jfc.getSelectedFile();
            rutaFichero = file.getAbsolutePath();
        }
        return file;
    }
}
