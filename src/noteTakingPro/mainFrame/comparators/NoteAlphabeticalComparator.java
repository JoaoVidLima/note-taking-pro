package noteTakingPro.mainFrame.comparators;

import noteTakingPro.mainFrame.notesPanel.Note;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Comparator that compares two Notes alphabetically by title and in case they have the same title by
 * last modification date.
 * @author joaoVidLima
 */
public class NoteAlphabeticalComparator implements Comparator<Note>, Serializable {
    @Override
    public int compare(Note o1, Note o2) {
        String firstTitle = o1.getTitle();
        String secondTitle = o2.getTitle();
        if(!firstTitle.equals(secondTitle))
            return firstTitle.compareTo(secondTitle);
        else {
            LocalDateTime firstDate = o1.getLastModDate();
            LocalDateTime secondDate = o2.getLastModDate();
            if(!firstDate.equals(secondDate))
                return secondDate.compareTo(firstDate);
            else
                return 0;
        }
    }
}
