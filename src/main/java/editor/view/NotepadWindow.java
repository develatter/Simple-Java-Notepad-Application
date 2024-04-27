package editor.view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
/**
 * Window class for the Notepad application. It represents the view of the MVC pattern.
 * @version 1.0
 * @author Alejandro López Martínez
 */
public class NotepadWindow extends JFrame {
    private JTextArea ta_text;
    private JLabel lb_path;
    private LinkedHashMap<String, JMenuItem> lhm_items_archive;
    private LinkedHashMap<String, JMenuItem> lhm_items_edit;

    /**
     * Constructor for the NotepadWindow class. It initializes the window and its components.
     */
    public NotepadWindow() {
        initWindow();
        initComponents();
        initLabel();
    }

    /**
     * Initializes the window properties, such as initial size, location and close operation.
     */
    private void initWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(100, 100);
        setSize(600, 900);
    }

    /**
     * Initializes the components of the window, such as the text area and the menu bar.
     */
    private void initComponents() {
        ta_text = new JTextArea();
        ta_text.setFont(new Font(Font.MONOSPACED,
                Font.PLAIN,
                12)
        );
        JScrollPane js_scroll = new JScrollPane(ta_text);
        add(js_scroll, BorderLayout.CENTER);
        initMenu();
    }


    /**
     * Initializes the menu bar of the window, adding the items to the "Archive" and "Edit" menus.
     */
    private void initMenu() {
        lhm_items_archive = new LinkedHashMap<>();
        lhm_items_edit = new LinkedHashMap<>();
        JMenuBar jm_bar = new JMenuBar();
        JMenu jm_archivo = new JMenu("Archive");
        JMenu jm_editar = new JMenu("Edit");
        jm_bar.add(jm_archivo);
        jm_bar.add(jm_editar);
        setJMenuBar(jm_bar);

        lhm_items_archive.put("new", new JMenuItem("New"));
        lhm_items_archive.put("open", new JMenuItem("Open"));
        lhm_items_archive.put("save", new JMenuItem("Save"));
        lhm_items_archive.put("saveAs", new JMenuItem("Save as..."));
        lhm_items_archive.put("exit", new JMenuItem("Exit"));

        for (JMenuItem item : lhm_items_archive.values()) {
            jm_archivo.add(item);
        }
        // NOTA: Sé que solo hay un elemento, pero esta forma hace el método mucho más escalable
        lhm_items_edit.put("search", new JMenuItem("Search"));
        for (JMenuItem item : lhm_items_edit.values()) {
            jm_editar.add(item);
        }
    }

    /**
     * Initializes the label that shows the current path of the file.
     */
    private void initLabel() {
        lb_path = new JLabel("/");
        add(lb_path,BorderLayout.SOUTH);
    }

    /**
     * Getter for the text area of the window.
     * @return The text area of the window.
     */
    public JTextArea getTextArea() {
        return ta_text;
    }

    /**
     * Setter for the text area of the window.
     * @param s The text to set in the text area.
     */
    public void setText(String s) {
        ta_text.setText(s);
    }

    /**
     * Setter for the path label of the window.
     * @param s The path to set in the label.
     */
    public void setPath(String s) {
        lb_path.setText(s);
    }

    /**
     * Getter for the items of the "Archive" menu.
     * @return The items of the "Archive" menu.
     */
    public LinkedHashMap<String, JMenuItem> getItemsArchive() {
        return lhm_items_archive;
    }

    /**
     * Getter for the items of the "Edit" menu.
     * @return The items of the "Edit" menu.
     */
    public LinkedHashMap<String, JMenuItem> getItemsEdit() {
        return lhm_items_edit;
    }
}
