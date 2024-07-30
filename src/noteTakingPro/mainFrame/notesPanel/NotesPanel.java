package noteTakingPro.mainFrame.notesPanel;

import configurations.AppConfig;
import visuals.Visuals;
import noteTakingPro.mainFrame.NoteClickListener;
import noteTakingPro.mainFrame.comparators.NoteAlphabeticalComparator;
import noteTakingPro.mainFrame.comparators.NoteLastModifiedComparator;
import noteTakingPro.mainFrame.state.FrameStateGettersAccess;
import noteTakingPro.mainFrame.state.MenuOption;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class is responsible for storing and handling all the notes from the application.
 * It will sort and display the notes according to the current note ordering and menu selected.
 * @author joaoVidLima
 */
public class NotesPanel extends JPanel {

    private static final String INVALID_MENU_TO_DISPLAY_NOTES_MESSAGE = "Unexpected error displaying notes in the chosen menu.";
    private static final String REMOVE_ALL_NOTES_ERROR_MESSAGE = "Unexpected error removing all the notes in the chosen menu.";
    private static final String INVALID_PREDICATE_FOR_MENU_MESSAGE = "Unexpected error filtering the notes for the chosen menu.";
    private static final String NOTES_SERIALIZING_FILE = "notesSerializingfile";
    private static final String ERROR_SERIALIZING_NOTES_MESSAGE = "Unexpected error saving your notes. \nRecent changes may not be reflected!\nClosing the app..";
    private static final String ERROR_DESERIALIZING_NOTES_MESSAGE = "Unexpected error loading your notes. \nRecent changes may not be reflected!\nClosing the app..";

    private AppConfig configs;
    private FrameStateGettersAccess currentState;
    private List<Note> notes;
    private NoteFilter noteFilter;

    public NotesPanel(FrameStateGettersAccess state, AppConfig configs, NoteClickListener listenerFromClick) {

        super(new WrapLayout(0, 50, 50));
        this.configs = configs;
        this.currentState = state;
        this.noteFilter = new NoteFilter();

        if (new File(NOTES_SERIALIZING_FILE).exists()) {
            notes = deserializeNotes(NOTES_SERIALIZING_FILE, listenerFromClick);
            printNotesMainMenu();
        } else
            notes = new LinkedList<>();

        setBackground(Visuals.MY_DARK_GRAY);
    }

    //Auxiliar methods
    private void sortNoteCollection() {

        switch (currentState.getCurrentNoteOrder()) {
            case LAST_ADDED -> Collections.sort(notes, new NoteLastModifiedComparator());
            case A_TO_Z -> Collections.sort(notes, new NoteAlphabeticalComparator());
        }
    }

    private Predicate<Note> getPredicateForMenu(MenuOption menu) {
        switch (menu) {
            case HOME -> {
                return noteFilter.isNotInBin().and(noteFilter.isNotDone());
            }
            case STARED -> {
                return noteFilter.isNotInBin().and(noteFilter.isStared());
            }
            case DONE -> {
                return noteFilter.isNotInBin().and(noteFilter.isDone());
            }
            case BIN -> {
                return noteFilter.isInBin();
            }
            default -> {
                Visuals.showErrorMessage(INVALID_PREDICATE_FOR_MENU_MESSAGE);
                return null;
            }
        }
    }

    private List<Note> applyPredicate(Predicate<Note> predicate, Collection<Note> notes) {
        return notes.stream().filter(predicate).collect(Collectors.toList());
    }

    private void printNotesInPaneWithPredicate(Predicate<Note> predicate) {

        sortNoteCollection();

        //Getting the new list applying the predicate given
        List<Note> newNotes = applyPredicate(predicate, notes);

        //Printing to the panel
        removeAll();
        for (Note note : newNotes)
            add(note);
    }

    private void printNotesMainMenu() {
        printNotesInPaneWithPredicate(getPredicateForMenu(MenuOption.HOME));
    }

    private void printStaredNotes() {
        printNotesInPaneWithPredicate(getPredicateForMenu(MenuOption.STARED));
    }

    private void printDoneNotes() {
        printNotesInPaneWithPredicate(getPredicateForMenu(MenuOption.DONE));
    }

    private void printBinNotes() {
        printNotesInPaneWithPredicate(getPredicateForMenu(MenuOption.BIN));
    }


    //Public used methods
    public int numOfNotesToDo() {
        return applyPredicate(getPredicateForMenu(MenuOption.HOME), notes).size();
    }

    public void addNote(String title, String message, Color color, NoteClickListener listenerFromClick) {
        Note note = new Note(title, message, color, Integer.valueOf(configs.getNoteWidth()), Integer.valueOf(configs.getNoteHeight()), listenerFromClick);
        notes.add(note);
    }

    public void removeNote(Note note) {
        notes.remove(note);
        remove(note);
    }

    public void removePermantlyAllNotes() {
        notes = new LinkedList<>();
    }

    public void reOrder() {
        //Assuming the state have already been changed
        printNotesMainMenu();
    }

    public void displayNotesForSelectedMenu() {
        switch (currentState.getCurrentMenu()) {
            case HOME -> printNotesMainMenu();
            case STARED -> printStaredNotes();
            case DONE -> printDoneNotes();
            case BIN -> printBinNotes();
            default -> Visuals.showErrorMessage(INVALID_MENU_TO_DISPLAY_NOTES_MESSAGE);
        }
    }

    public void removeAllNotesButtonPressed() {
        List<Note> newNotes = null;
        switch (currentState.getCurrentMenu()) {

            case STARED -> newNotes = applyPredicate(getPredicateForMenu(MenuOption.STARED), notes);

            case DONE -> newNotes = applyPredicate(getPredicateForMenu(MenuOption.DONE), notes);

            case BIN -> newNotes = applyPredicate(getPredicateForMenu(MenuOption.BIN), notes);

            default -> Visuals.showErrorMessage(REMOVE_ALL_NOTES_ERROR_MESSAGE);
        }

        for (Note note : newNotes) {
            note.removeAllPressed();
        }
    }

    // Serializing and deserializing Notes
    public List<Note> deserializeNotes(String fileName, NoteClickListener listenerFromClick) {

        List<Note> notes = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            notes = (List<Note>) ois.readObject();
            ois.close();

            //reattach actions to all notes, because actions are not serialized.
            reattachActions(notes, listenerFromClick);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Visuals.showErrorMessage(ERROR_DESERIALIZING_NOTES_MESSAGE);
        }
        return notes;
    }

    private void reattachActions(Collection<Note> notes, NoteClickListener listenerFromClick) {
        for (Note note : notes)
            note.attachActions(listenerFromClick);
    }

    public void serializeNotes() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOTES_SERIALIZING_FILE));
            oos.writeObject(notes);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Visuals.showErrorMessage(ERROR_SERIALIZING_NOTES_MESSAGE);
        }
    }

    public boolean removeSerializeNotesFile() {
        File file = new File(NOTES_SERIALIZING_FILE);
        if(file.exists())
            return file.delete();
        else
            return true;
    }
}
