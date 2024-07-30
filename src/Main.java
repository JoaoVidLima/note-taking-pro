import configurations.AppConfig;
import configurations.AppConfigClass;
import noteTakingPro.welcomingFrame.GetUsernameFrame;
import noteTakingPro.welcomingFrame.WelcomingFrame;

/**
 * It's the Main class of the application, it calls the main method.
 * Its created a AppConfig object which have the configurations and properties related to the user and the app.
 * If the user have already filled the username it's loaded the Welcoming frame, otherwise it's showed
 * an initial frame asking for the username.
 * @author joaoVidLima */

public class Main {

    public static void main(String[] args) {

        System.setProperty("sun.java2d.uiScale", "1.0"); //To avoid issues from GUI scalling

        System.setProperty("awt.useSystemAAFontSettings", "on"); //Better rendering on fonts
        System.setProperty("swing.aatext", "true");

        AppConfig configs = new AppConfigClass();

        if(!configs.usernameExists())
            new GetUsernameFrame(configs);
        else
            new WelcomingFrame(configs);
    }

}