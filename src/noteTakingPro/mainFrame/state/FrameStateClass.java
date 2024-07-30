package noteTakingPro.mainFrame.state;

/**
 * Implementation of FrameStateTotalAccess that stores and handles the current state of the application
 * (current note ordering, current menu and last menu selected).
 * @author joaoVidLima
 */
public class FrameStateClass implements FrameStateTotalAccess {

    private MenuOption currentMenuSelected;
    private MenuOption lastMenuSelected;
    private NoteOrdering currentOrderSelected;

    public FrameStateClass(NoteOrdering lastOrderSelected){
        this.currentMenuSelected = MenuOption.HOME;
        this.lastMenuSelected = null;
        this.currentOrderSelected = lastOrderSelected;
    }

    @Override
    public MenuOption getCurrentMenu() {
        return currentMenuSelected;
    }

    @Override
    public MenuOption getLastMenuSelected() {
        return lastMenuSelected;
    }

    @Override
    public NoteOrdering getCurrentNoteOrder() {
        return currentOrderSelected;
    }

    @Override
    public void setCurrentMenu(MenuOption newMenuOption) {
        lastMenuSelected = currentMenuSelected;
        currentMenuSelected = newMenuOption;
    }

    @Override
    public void setCurrentNoteOrder(NoteOrdering noteOrdering) {
        currentOrderSelected = noteOrdering;
    }
}
