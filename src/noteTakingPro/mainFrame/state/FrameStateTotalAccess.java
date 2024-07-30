package noteTakingPro.mainFrame.state;

/**
 * Interface that let's full access to the getters and setters of the current state of the application
 * (current note ordering and current menu).
 * @author joaoVidLima
 */
public interface FrameStateTotalAccess extends FrameStateGettersAccess{

    void setCurrentMenu(MenuOption menuOption);

    void setCurrentNoteOrder(NoteOrdering noteOrdering);
}
