package noteTakingPro.welcomingFrame;

import configurations.AppConfig;
import configurations.AppConfigClass;
import noteTakingPro.mainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Welcoming frame is an intial frame before the application itsel.
 * Its shows some relevant information for the user such as the number of notes undone.
 * @author joaoVidLima
 */
public class WelcomingFrame extends JFrame{
    private JPanel mainPanelWF;
    private JPanel topBarPanelWF;
    private JLabel topBarLabelWF;
    private JPanel bodyPanelWF;
    private JPanel bodyTitleWF;
    private JPanel bodyContentWF;
    private JPanel bodyContentLeftSideWF;
    private JPanel bodyContentRightSideWF;
    private JLabel bodyTitleLabelWF;
    private JPanel pressAnyKeyWF;
    private JPanel outerPanelInfoTableWF;
    private JLabel lastTimeUsedWF;
    private JLabel notesToBeCompletedWF;
    private JPanel infoTableWF;
    private JLabel lastTimeUsedFieldWF;
    private JLabel notesToBeCompletedFieldWF;
    private JPanel noteShapeWF;
    private JPanel noteHeaderWF;
    private JLabel versionWF;

    public WelcomingFrame(AppConfig configs){
        super();

        //Frame configurations
        setContentPane(mainPanelWF);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(configs.getAppIconPath())));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setFrameTexts(configs);
        addPressAnyKeyListener(configs);
        setVisible(true);
    }

    private void setFrameTexts(AppConfig configs) {

        setTitle(configs.getAppName());
        setSize(Integer.valueOf(configs.getWelcomingFrameWidth()), Integer.valueOf(configs.getWelcomingFrameHeight()));

        if(configs.firstTimeLaunching()) {
            lastTimeUsedFieldWF.setText(String.format("%s", configs.getLastUsedTime()));
            topBarLabelWF.setText(String.format("Welcome %s!", configs.getUsername()));
        }
        else {
            lastTimeUsedFieldWF.setText(String.format("%s", getFormattedTime(configs.getLastUsedTime())));
            topBarLabelWF.setText(String.format("Welcome Back %s!", configs.getUsername()));
        }

        notesToBeCompletedFieldWF.setText(configs.getNumOfPendingNotes());
        versionWF.setText(String.format("Version %s", configs.getVersion()));
    }

    private String getFormattedTime(String lastUsedTime) {
        Date date = new Date(Long.parseLong(lastUsedTime));
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH':'mm  dd/MM");
        return dateFormat.format(date);
    }

    private void updateLastTimeUsed(AppConfig configs) {
        configs.setLastUsedTime(String.valueOf(System.currentTimeMillis()));
    }

    private void addPressAnyKeyListener(AppConfig configs) {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {/* not needed */}

            @Override
            public void keyPressed(KeyEvent e) {
                dispose();
                updateLastTimeUsed(configs);
                new MainFrame(configs);
            }

            @Override
            public void keyReleased(KeyEvent e) {/* not needed */}
        });
    }


}
