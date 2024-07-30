package noteTakingPro.mainFrame;

import noteTakingPro.mainFrame.notesPanel.NoteClickEvent;

import java.util.EventListener;

/**
 * A custom interface for a listener to a NoteClickEvent(when a note is clicked, so it gets edit).
 * @author joaoVidLima
 */
public interface NoteClickListener extends EventListener{
    void noteClicked(NoteClickEvent event);
}
