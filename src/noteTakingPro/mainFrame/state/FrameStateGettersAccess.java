package noteTakingPro.mainFrame.state;

/**
 * Interface that gives only access to the getters of the current state of the application
 * (current note ordering, current menu and last menu selected).
 * @author joaoVidLima
 */
public interface FrameStateGettersAccess {

    MenuOption getCurrentMenu();

    MenuOption getLastMenuSelected();

    NoteOrdering getCurrentNoteOrder();

}
