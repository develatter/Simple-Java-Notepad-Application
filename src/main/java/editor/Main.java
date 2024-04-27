package editor;

import editor.controller.NotepadController;
import editor.model.NotepadModel;
import editor.view.NotepadWindow;

/**
 * Main class of the Notepad application. This is a very simple text editor that I made for an exam. I have added some
 * features to make it more useful and probably I will improve it in the future.
 * @version 1.0
 * @author Alejandro López Martínez
 */
public class Main {
    public static void main(String[] args) {
        new NotepadController(
                new NotepadWindow(),
                new NotepadModel()
        );
    }
}