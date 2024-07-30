package noteTakingPro.welcomingFrame;

import configurations.AppConfig;
import visuals.Visuals;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * First frame seen by the user when first opening the app.
 * It handles the input of the username from the user.
 * @author joaoVidLima
 */
public class GetUsernameFrame extends JFrame{

    private JPanel mainPanel;
    private JPanel titlePanel;
    private JLabel welcomingTitle;
    private JLabel welcomingText;
    private JTextField usernameField;
    private JButton confirmationButton;
    private String username;

    public GetUsernameFrame(AppConfig configs){
        super();
        setSize(Integer.valueOf(configs.getUsernameFrameWidth()), Integer.valueOf(configs.getUsernameFrameHeight()));
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(configs.getAppIconPath())));
        setTitle(configs.getAppName());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        //Main Panel
        mainPanel = new JPanel(new GridLayout(3,0));
        mainPanel.setBackground(Visuals.MY_GRAY);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        //Title Panel
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        //Welcoming Title
        welcomingTitle = new JLabel("Welcome to NoteTaking PRO!");
        welcomingTitle.setFont(Visuals.WELCOMING_TITLE_FONT);
        welcomingTitle.setForeground(Visuals.MY_YELLOW);
        welcomingTitle.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(welcomingTitle, BorderLayout.NORTH);

        //Welcoming Text
        welcomingText = new JLabel("Let's start by entering your name:");
        welcomingText.setFont(Visuals.WELCOMING_TEXT_FONT);
        welcomingText.setForeground(Color.white);
        welcomingText.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(welcomingText, BorderLayout.CENTER);

        mainPanel.add(titlePanel);

        //Text field
        usernameField = new JTextField(20);
        usernameField.setBackground(Visuals.MY_LIGHT_GRAY);
        usernameField.setForeground(Color.white);
        usernameField.setFont(Visuals.USERNAME_FIELD_FONT);
        usernameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { /*not used*/}

            @Override
            public void keyPressed(KeyEvent e) { //Pressing ENTER in the text field confirms the name typed.
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {confirmationButton.doClick();}
            }

            @Override
            public void keyReleased(KeyEvent e) { /*not used*/ }
        });
        mainPanel.add(usernameField);

        //Button
        confirmationButton = new JButton("OK");
        confirmationButton.setBackground(Visuals.MY_LIGHT_GRAY);
        confirmationButton.setForeground(Visuals.WHITE);
        confirmationButton.setFont(Visuals.CONFIRMATION_BUTTON_OK_FONT);
        confirmationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText().trim();
                if(username.equals("")) //Empty username inputed.
                    Visuals.showWarningMessage("Please insert a valid name!");
                else{
                    dispose();
                    configs.setUsername(username);
                    new WelcomingFrame(configs);
                }

            }
        });
        mainPanel.add(confirmationButton);

        add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }
}
