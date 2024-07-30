package noteTakingPro.mainFrame.state;

/**
 * Enum representing all the diferent menus of the application and the corresponding title.
 * @author joaoVidLima
 */
public enum MenuOption {

    HOME("Home"),
    STARED("Stared"),
    DONE("Done"),
    BIN("Bin"),
    SETTINGS("Settings"),
    CREATING_NOTE("Creating a new note.."),
    EDITING_NOTE("Editing a note..");

    private String name;

    MenuOption(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
