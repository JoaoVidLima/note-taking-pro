package configurations;

import visuals.Visuals;
import noteTakingPro.mainFrame.state.NoteOrdering;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Creates a Properties object with many configurations and properties about the user and the app.
 * All this information is stored on a .properties file that will be access and modified to get and
 * update information.
 * @author joaoVidLima */
public class AppConfigClass implements AppConfig{

    private static final String CONFIG_FILE = "config.properties";
    private static final String APP_ICON_PROPERTY_KEY = "app.icon";
    private static final String APP_VERSION_PROPERTY_KEY = "app.version";
    private static final String APP_WF_HEIGHT_PROPERTY_KEY = "app.wfHeiht";
    private static final String APP_WF_WIDTH_PROPERTY_KEY = "app.wfWidth";
    private static final String APP_GUF_HEIGHT_PROPERTY_KEY = "app.gufHeiht";
    private static final String APP_GUF_WIDTH_PROPERTY_KEY = "app.gufWidth";
    private static final String APP_MF_HEIGHT_PROPERTY_KEY = "app.mfHeiht";
    private static final String APP_MF_WIDTH_PROPERTY_KEY = "app.mfWidth";
    private static final String APP_NOTE_HEIGHT_PROPERTY_KEY = "app.noteHeiht";
    private static final String APP_NOTE_WIDTH_PROPERTY_KEY = "app.noteWidth";
    private static final String APP_NAME_PROPERTY_KEY = "app.name";
    private static final String USER_NAME_PROPERTY_KEY = "user.name";
    private static final String FEATURES_LAST_USED_TIME_PROPERTY_KEY = "features.lastUsedTime";
    private static final String FEATURES_PENDING_NOTES = "features.pendingNotes";
    private static final String FEATURES_ORDER_MODE = "features.orderMode";

    private static final String FRESH_CONFIGURATIONS_UPDATE_COMMENT = "Fresh configurations";
    private static final String NORMAL_CONFIGURATIONS_UPDATE_COMMENT = "Updated configurations";

    private static final String ERROR_LOAD_PROP_MESSAGE =
            "Unexpected error trying to load the file configurations.\nRecent changes may not be reflected!\nClosing the app..";
    private static final String ERROR_GETTING_ORDER_MODE_MESSAGE =
            "Unexpected error trying to get the order mode stored in the file configurations.\nRecent changes may not be reflected!\nClosing the app..";

    private static final String FIRST_TIME_LAUNCHING_LAST_USED_TIME_MESSAGE = "First time";

    private static final Properties properties;


    /* Loading the existing properties staticly (only done once despite how many configurations.AppConfigClass objects
     are created) from the config file or creating a new file if it's the first time running the app
     and the file wasn't created yet */
    static {
        properties = new Properties();
        try{
            if(new File(CONFIG_FILE).exists()){
                FileInputStream fis = new FileInputStream(CONFIG_FILE);
                properties.load(fis);
                fis.close();
            }
            else {
                properties.setProperty(APP_ICON_PROPERTY_KEY, "/images/NoteTakingPROIcon.png");
                properties.setProperty(APP_VERSION_PROPERTY_KEY, "1.0");
                properties.setProperty(APP_NAME_PROPERTY_KEY, "NoteTaking PRO");
                properties.setProperty(APP_WF_HEIGHT_PROPERTY_KEY, "800");
                properties.setProperty(APP_WF_WIDTH_PROPERTY_KEY, "1200");
                properties.setProperty(APP_GUF_HEIGHT_PROPERTY_KEY, "350");
                properties.setProperty(APP_GUF_WIDTH_PROPERTY_KEY, "500");
                properties.setProperty(APP_MF_HEIGHT_PROPERTY_KEY, "1024");
                properties.setProperty(APP_MF_WIDTH_PROPERTY_KEY, "1920");
                properties.setProperty(APP_NOTE_HEIGHT_PROPERTY_KEY, "400");
                properties.setProperty(APP_NOTE_WIDTH_PROPERTY_KEY, "350");
                properties.setProperty(USER_NAME_PROPERTY_KEY, "");
                properties.setProperty(FEATURES_LAST_USED_TIME_PROPERTY_KEY, "First time");
                properties.setProperty(FEATURES_PENDING_NOTES, "0");
                properties.setProperty(FEATURES_ORDER_MODE, NoteOrdering.LAST_ADDED.name());

                storeProperties(FRESH_CONFIGURATIONS_UPDATE_COMMENT);
            }
        } catch (IOException e){
            e.getStackTrace();
            Visuals.showErrorMessage(ERROR_LOAD_PROP_MESSAGE);
        }
    }

    @Override
    public String getAppIconPath() {
        return properties.getProperty(APP_ICON_PROPERTY_KEY);
    }

    @Override
    public String getLastUsedTime() {
        return properties.getProperty(FEATURES_LAST_USED_TIME_PROPERTY_KEY);
    }

    @Override
    public void setLastUsedTime(String updatedTime) {
        properties.setProperty(FEATURES_LAST_USED_TIME_PROPERTY_KEY, updatedTime);
        storeProperties(NORMAL_CONFIGURATIONS_UPDATE_COMMENT);
    }

    @Override
    public String getNumOfPendingNotes() {
        return properties.getProperty(FEATURES_PENDING_NOTES);
    }

    @Override
    public void setNumOfPendingNotes(int newNumOfNotes) {
        properties.setProperty(FEATURES_PENDING_NOTES, String.valueOf(newNumOfNotes));
        storeProperties(NORMAL_CONFIGURATIONS_UPDATE_COMMENT);
    }

    @Override
    public String getUsername() {
        return properties.getProperty(USER_NAME_PROPERTY_KEY);
    }

    @Override
    public void setUsername(String newUsername) {
        properties.setProperty(USER_NAME_PROPERTY_KEY, newUsername);
        storeProperties(NORMAL_CONFIGURATIONS_UPDATE_COMMENT);
    }

    @Override
    public String getVersion() {
        return properties.getProperty(APP_VERSION_PROPERTY_KEY);
    }

    @Override
    public String getAppName() {
        return properties.getProperty(APP_NAME_PROPERTY_KEY);
    }

    @Override
    public String getWelcomingFrameHeight() {
        return properties.getProperty(APP_WF_HEIGHT_PROPERTY_KEY);
    }

    @Override
    public String getWelcomingFrameWidth() {
        return properties.getProperty(APP_WF_WIDTH_PROPERTY_KEY);
    }

    @Override
    public String getUsernameFrameHeight() {
        return properties.getProperty(APP_GUF_HEIGHT_PROPERTY_KEY);
    }

    @Override
    public String getUsernameFrameWidth() {
        return properties.getProperty(APP_GUF_WIDTH_PROPERTY_KEY);
    }

    @Override
    public String getMainFrameHeight() {
        return properties.getProperty(APP_MF_HEIGHT_PROPERTY_KEY);
    }

    @Override
    public String getMainFrameWidth() {
        return properties.getProperty(APP_MF_WIDTH_PROPERTY_KEY);
    }

    @Override
    public String getNoteHeight() {
        return properties.getProperty(APP_NOTE_HEIGHT_PROPERTY_KEY);
    }

    @Override
    public String getNoteWidth() {
        return properties.getProperty(APP_NOTE_WIDTH_PROPERTY_KEY);
    }

    @Override
    public boolean usernameExists() {
        return !properties.getProperty(USER_NAME_PROPERTY_KEY).isEmpty();
    }

    @Override
    public boolean firstTimeLaunching() {
        return properties.getProperty(FEATURES_LAST_USED_TIME_PROPERTY_KEY).equals(FIRST_TIME_LAUNCHING_LAST_USED_TIME_MESSAGE);
    }

    @Override
    public NoteOrdering getNoteOrderingSelected() {

        NoteOrdering ret = null;
        String orderMode = properties.getProperty(FEATURES_ORDER_MODE);
        String a_to_z = NoteOrdering.A_TO_Z.name();
        String last_modif = NoteOrdering.LAST_ADDED.name();

        if (orderMode.equals(a_to_z))
            ret = NoteOrdering.valueOf(a_to_z);
        else if(orderMode.equals(last_modif))
            ret = NoteOrdering.valueOf(last_modif);
        else
            Visuals.showErrorMessage(ERROR_GETTING_ORDER_MODE_MESSAGE);

        return ret;
    }

    @Override
    public void setNoteOrderingSelected(NoteOrdering ordering) {
        properties.setProperty(FEATURES_ORDER_MODE, ordering.name());
        storeProperties(NORMAL_CONFIGURATIONS_UPDATE_COMMENT);
    }

    public boolean removeConfigFile(){
        File file = new File(CONFIG_FILE);
        if(file.exists())
            return file.delete();
        else
            return true;
    }

    private static void storeProperties(String comment){

        try {
            FileOutputStream fos = new FileOutputStream(CONFIG_FILE);
            properties.store(fos, comment);
            fos.flush();
            fos.close();
        } catch (IOException e){
            e.getStackTrace();
            Visuals.showErrorMessage(ERROR_GETTING_ORDER_MODE_MESSAGE);
        }
    }
}
