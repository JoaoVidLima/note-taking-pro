package configurations;

import noteTakingPro.mainFrame.state.NoteOrdering;

/**
 * Interface for the AppConfigClass with all it's public methods.
 * It has methods to access and modify configurations and properties about the user and the app itself.
 * @author joaoVidLima */
public interface AppConfig {

    /**
     * Returns a String with the icon path of the app.
     * @return a String with the icon path of the app.
     */
    String getAppIconPath();

    /**
     * Returns a String with the last time the app was used from the config file.
     * @return a String with the last time the app was used from the config file.
     */
    String getLastUsedTime();

    /**
     * Updates the last used time from the config file with the time given.
     * @param updatedTime - the updated time.
     */
    void setLastUsedTime(String updatedTime);

    /**
     * Returns a String with the number of pending notes from the config file.
     * @return a String with the number of pending notes from the config file.
     */
    String getNumOfPendingNotes();

    /**
     * Updates the number of pending notes from the config file with the number of notes given.
     * @param newNumOfNotes - the new number of notes.
     */
    void setNumOfPendingNotes(int newNumOfNotes);

    /**
     * Returns a String with the username from the config file.
     * @return a String with the username from the config file.
     */
    String getUsername();

    /**
     * Updates the username from the config file with the new username.
     */
    void setUsername(String newUsername);

    /**
     * Returns a String with the version of the app from the config file.
     * @return a String with the version of the app from the config file.
     */
    String getVersion();

    /**
     * Returns a String with the name of the app from the config file.
     * @return a String with the name of the app from the config file.
     */
    String getAppName();

    /**
     * Returns a String with the height of the welcoming frame from the config file.
     * @return a String with the height of the welcoming frame from the config file.
     */
    String getWelcomingFrameHeight();

    /**
     * Returns a String with the width of the welcoming frame from the config file.
     * @return a String with the width of the welcoming frame from the config file.
     */
    String getWelcomingFrameWidth();

    /**
     * Returns a String with the height of the getUsernameFrame from the config file.
     * @return a String with the height of the getUsernameFrame from the config file.
     */
    String getUsernameFrameHeight();

    /**
     * Returns a String with the width of the getUsernameFrame from the config file.
     * @return a String with the width of the getUsernameFrame from the config file.
     */
    String getUsernameFrameWidth();

    /**
     * Returns a String with the height of the MainFrame from the config file.
     * @return a String with the height of the MainFrame from the config file.
     */
    String getMainFrameHeight();

    /**
     * Returns a String with the width of the MainFrame from the config file.
     * @return a String with the width of the MainFrame from the config file.
     */
    String getMainFrameWidth();

    /**
     * Returns a String with the height of a Note from the config file.
     * @return a String with the height of a Note from the config file.
     */
    String getNoteHeight();

    /**
     * Returns a String with the width of a Note from the config file.
     * @return a String with the width of a Note from the config file.
     */
    String getNoteWidth();

    /**
     * Returns true if the user already set his username in the GetUsernameFrame, false otherwise.
     * @return true if the user already set his username in the GetUsernameFrame, false otherwise.
     */
    boolean usernameExists();

    /**
     * Returns true if the MainFrame hasn't been lauched ever yet. It's the first time the using the app. False otherwise.
     * @return true if the MainFrame hasn't been lauched ever yet. It's the first time the using the app. False otherwise.
     */
    boolean firstTimeLaunching();

    /**
     * Returns the corresponding enum value for the last order mode selected by the user before closing the app.
     * @return the corresponding enum value for the last order mode selected by the user before closing the app.
     */
    NoteOrdering getNoteOrderingSelected();

    /**
     * Sets in the config file the last order mode selected by the user before closing the app.
     * Sets in the config file the last order mode selected by the user before closing the app.
     */
    void setNoteOrderingSelected(NoteOrdering ordering);

    /**
     * Removes the configuration file. If done successfully returns true, false otherwise.
     * @return true if the file was deleted, false otherwise.
     */
    boolean removeConfigFile();
}
