package noteTakingPro.mainFrame.notesPanel;

import java.util.function.Predicate;

/**
 * This class has all the different predicates used to filter the notes.
 * It uses functional programming.
 * @author joaoVidLima
 */
public class NoteFilter {

    public NoteFilter(){}

    public Predicate<Note> isStared(){
        return note -> note.isStared();
    }

    public Predicate<Note> isDone(){
        return note -> note.isDone();
    }

    public Predicate<Note> isNotDone(){
        return note -> !note.isDone();
    }

    public Predicate<Note> isInBin(){
        return note -> note.isInBin();
    }

    public Predicate<Note> isNotInBin(){
        return note -> !note.isInBin();
    }



}
