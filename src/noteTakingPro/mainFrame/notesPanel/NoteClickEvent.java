package noteTakingPro.mainFrame.notesPanel;

import java.io.Serializable;
import java.util.EventObject;

/**
 * Custom event sent by a note when they are clicked. The NoteClickListener implementations will be notified.
 * @author joaoVidLima
 */
public class NoteClickEvent extends EventObject implements Serializable {
    private final Note note;

    public NoteClickEvent(Object source, Note note) {
        super(source);
        this.note = note;
    }

    public Note getNote() {
        return note;
    }
}
