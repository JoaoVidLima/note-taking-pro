package noteTakingPro.mainFrame.comparators;

import noteTakingPro.mainFrame.notesPanel.Note;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Comparator that compares two Notes by last modification date.
 * @author joaoVidLima
 */
public class NoteLastModifiedComparator implements Comparator<Note>, Serializable {
    @Override
    public int compare(Note o1, Note o2) {
        LocalDateTime firstDate = o1.getLastModDate();
        LocalDateTime secondDate = o2.getLastModDate();
        if(!firstDate.equals(secondDate))
            return secondDate.compareTo(firstDate);
        else
            return 0;
    }
}
