package noteTakingPro.mainFrame;

import configurations.AppConfig;
import visuals.Visuals;
import visuals.PlaceHolderEffect;
import noteTakingPro.mainFrame.notesPanel.Note;
import noteTakingPro.mainFrame.notesPanel.NoteClickEvent;
import noteTakingPro.mainFrame.notesPanel.NotesPanel;
import noteTakingPro.mainFrame.state.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;

/**
 * It's the main Frame of the application. It will handle all the inputs regarding all the menus.
 * It can be divided in 4 main parts:
 * - The home menu
 * - All the specific menus (stared, done and bin)
 * - Creating/updating notes menu
 * - Settings
 * Each one of this 4 groups have a different layout (swapping card layouts).
 * Furthermore, this frame creates another crucial frame, the NotePanel, responsible for handling all the notes.
 * This frame doesn't interact directly with the notes, it uses the NotePanel methods to do so.
 * The only exception is when the user clicks a note to edit it. When this happen this frame will be
 * notified directly (since it implements NoteClickListener) and pursuing corresponding actions.
 * Lastly, this frame also has full access to the properties object (by the AppConfig interface),
 * since it will need to access and update much information there.
 * @author joaoVidLima
 */
public class MainFrame extends JFrame implements NoteClickListener, Serializable {

    //Card names used in all the different layouts
    private static final String DEFAULT_MENUS_CARD_NAME = "defaultMenus";
    private static final String HOME_MENU_TOP_BAR_CARD_NAME = "homeMenuTopBar";
    private static final String SPECIFIC_MENU_TOP_BAR_CARD_NAME = "specificMenuTopBar";

    private static final String CREATE_EDIT_NOTE_MENU_CARD_NAME = "createEditNoteMenu";
    private static final String TITLE_CREATE_EDIT_NOTE_PLACEHOLDER_TEXT = "Insert title..";
    private static final String MESSAGE_CREATE_EDIT_NOTE_PLACEHOLDER_TEXT = "Start your note..";

    private static final String SETTINGS_MENU_CARD_NAME = "settingsMenu";


    private JPanel mainPanel;

    //main panel
    private JPanel mainPanelDefault;
    private JPanel mainPanelDefaultTopBar;
    private JPanel mainPanelDefaultSideBar;
    private JPanel mainPanelDefaultBody;
    private JPanel NoteTakingPROBackPanel;
    private JLabel NoteTakingPROLabel;
    private JPanel sideBarTop;
    private JPanel sideBarBotton;
    private JButton homeButton;
    private JButton staredButton;
    private JButton doneButton;
    private JButton binButton;
    private JButton saveButton;
    private JButton settingsButton;
    private JSeparator menuSeparator;
    private JPanel topBarCardPanel;
    private JPanel topBarHomeMenu;
    private JPanel topBarSpecificMenu;
    private JPanel newNotePanel;
    private JLabel homeTopBarLabel;
    private JPanel sortPanel;
    private JButton newNoteButton;
    private JButton sortButton;
    private JPanel menuTitlePanel;
    private JLabel menuTitleLabel;
    private JPanel removeAllPanel;
    private JButton removeAllButton;

    //createEdit panel
    private JPanel mainPanelCreateEditNote;
    private JLabel createEditNoteLabel;
    private JScrollPane scrollPaneCreateEditNote;
    private JButton backArrowButton;
    private JButton checkButton;
    private JButton palleteButton;
    private JTextPane messageAreaCreateEditNote;
    private JTextPane titleAreaCreateEditNote;

    //Settings panel
    private JPanel mainPanelSettings;
    private JButton backArrowSettingsButton;
    private JButton changeUsernameButton;
    private JButton freshStartButton;
    private JButton deleteAllNotesPermanentlyButton;

    // notesPanel
    private JScrollPane scrollNotesPane;
    private NotesPanel notesPanel;

    // Instance of AppConfig
    private AppConfig configs;

    // Object always holding the latest state of the app (current note ordering selected and current menu)
    FrameStateTotalAccess state;

    // note to edit (sent a NoteCickEvent to the MainFrame)
    private Note noteBeingEdited;


    public MainFrame(AppConfig configs) {
        super();
        this.configs = configs;
        this.state = new FrameStateClass(configs.getNoteOrderingSelected());
        this.noteBeingEdited = null;

        setContentPane(mainPanel);
        setTitle(configs.getAppName());
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(configs.getAppIconPath())));
        setSize(Integer.valueOf(configs.getMainFrameWidth()), Integer.valueOf(configs.getMainFrameHeight()));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setNotesPanel();
        setClosingAction();
        setAllButtons();

        setVisible(true);
    }

    private void setClosingAction() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveAll();
                dispose();
            }
        });
    }

    private void saveAll(){
        configs.setNumOfPendingNotes(notesPanel.numOfNotesToDo());
        configs.setNoteOrderingSelected(state.getCurrentNoteOrder());
        notesPanel.serializeNotes();
    }

    private void setNotesPanel() {
        //The NotesPanel has access to the state object, but only to the getters.
        //The NotesPanel will set this class as the listenerFromClick to each Note.
        notesPanel = new NotesPanel((FrameStateGettersAccess)state, configs, MainFrame.this);
        scrollNotesPane = new JScrollPane(notesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollNotesPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollNotesPane.setBorder(BorderFactory.createEmptyBorder());

        mainPanelDefaultBody.add(scrollNotesPane, BorderLayout.CENTER);
    }

    private void setAllButtons() {

        //Default frame
        setNewNoteButton();
        setSortButton();
        setHomeButton();
        setStaredButton();
        setDoneButton();
        setBinButton();
        setSaveButton();
        setSettingsButton();
        setRemoveAllButton();

        //Create/edit note frame
        setBackArowButton();
        setPalleteButton();
        setCheckButton();
        setCreateOrEditNoteTextAreas();

        //Settings
        setBackArrowSettingsButton();
        setChangeUsernameButton();
        setFreshStartButton();
        setCleanNotesButton();
    }

    private void setNewNoteButton() {
        //Initializing
        Visuals.setButtonVisual(newNoteButton);

        //Action
        newNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Put default settings again
                setEditNoteTexts(TITLE_CREATE_EDIT_NOTE_PLACEHOLDER_TEXT, MESSAGE_CREATE_EDIT_NOTE_PLACEHOLDER_TEXT, Visuals.MY_LIGHT_BROWN_GRAY);

                changeMenu(MenuOption.CREATING_NOTE);
            }
        });
    }

    private void setSortButton() {
        //Initializing
        Visuals.setButtonVisual(sortButton);
        if(state.getCurrentNoteOrder() == NoteOrdering.A_TO_Z)
            sortButton.setIcon(Visuals.getIcon("/images/alphabSort.png", 50, 50));
        else
            sortButton.setIcon(Visuals.getIcon("/images/arrowUp.png", 50, 50));

        //Action
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NoteOrdering newNoteOrdering = null;
                if (state.getCurrentNoteOrder() == NoteOrdering.LAST_ADDED){
                    newNoteOrdering = NoteOrdering.A_TO_Z;
                    sortButton.setIcon(Visuals.getIcon("/images/alphabSort.png", 50, 50));
                }
                else{
                    newNoteOrdering = NoteOrdering.LAST_ADDED;
                    sortButton.setIcon(Visuals.getIcon("/images/arrowUp.png", 50, 50));
                }
                state.setCurrentNoteOrder(newNoteOrdering);

                notesPanel.reOrder();
                notesPanel.revalidate();
                notesPanel.repaint();
            }
        });
    }

    private void changeMenu(MenuOption newMenuOption) {

        setMenuLook(newMenuOption);
        state.setCurrentMenu(newMenuOption);

        //In these menu this frame will ask the NotesPane to update the notes displayed for the new menu.
        switch (newMenuOption){
            case HOME, STARED, DONE, BIN -> {
                notesPanel.displayNotesForSelectedMenu();
                notesPanel.revalidate();
                notesPanel.repaint();
            }
        }
    }

    private void showDefaultLayout(){
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, DEFAULT_MENUS_CARD_NAME);
    }

    private void showHomeTopBar(){
        CardLayout cardLayout = (CardLayout) topBarCardPanel.getLayout();
        cardLayout.show(topBarCardPanel, HOME_MENU_TOP_BAR_CARD_NAME);
    }

    private void showSpecificMenuTopBar(){
        CardLayout cardLayout = (CardLayout) topBarCardPanel.getLayout();
        cardLayout.show(topBarCardPanel, SPECIFIC_MENU_TOP_BAR_CARD_NAME);
    }

    private void showCreateOrEditNoteLayout(){
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, CREATE_EDIT_NOTE_MENU_CARD_NAME);
    }

    private void showSettingsLayout(){
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, SETTINGS_MENU_CARD_NAME);
    }


    private void setMenuLook(MenuOption newMenuOption){

        switch (newMenuOption){
            case HOME -> {
                showDefaultLayout();
                showHomeTopBar();
            }
            case STARED, DONE, BIN ->{
                showDefaultLayout();
                showSpecificMenuTopBar();
                menuTitleLabel.setText(newMenuOption.getName());
                menuTitleLabel.setIcon(getIconForMenu(newMenuOption));
            }
            case CREATING_NOTE, EDITING_NOTE -> {
                showCreateOrEditNoteLayout();
                createEditNoteLabel.setText(newMenuOption.getName());
            }
            case SETTINGS -> {
                showSettingsLayout();
            }
        }
        topBarCardPanel.revalidate();
        topBarCardPanel.repaint();
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    private Icon getIconForMenu(MenuOption menu){

        String path = null;
        switch (menu){
            case HOME -> path = "/images/home.png";
            case STARED -> path = "/images/starFilled.png";
            case DONE -> path = "/images/done.png";
            case BIN -> path = "/images/bin.png";
            case SETTINGS -> path = "/images/settings.png";
            default -> Visuals.showErrorMessage("Finding icon for an invalid menu!");
        }
        return new ImageIcon(getClass().getResource(path));
    }

    //--Default Panel
    private void setHomeButton() {
        //Initializing
        Visuals.setButtonVisual(homeButton);

        //Action
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu(MenuOption.HOME);
            }
        });
    }

    private void setStaredButton () {
        //Initializing
        Visuals.setButtonVisual(staredButton);

        //Action
        staredButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu(MenuOption.STARED);
            }
        });
    }

    private void setDoneButton () {
        //Initializing
        Visuals.setButtonVisual(doneButton);

        //Action
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu(MenuOption.DONE);
            }
        });
    }

    private void setBinButton () {
        //Initializing
        Visuals.setButtonVisual(binButton);

        //Action
        binButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu(MenuOption.BIN);
            }
        });
    }

    private void setRemoveAllButton() {
        //Initializing
        Visuals.setButtonVisual(removeAllButton);

        //Action
        removeAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notesPanel.removeAllNotesButtonPressed();
                notesPanel.revalidate();
                notesPanel.repaint();
            }
        });
    }

    private void setSaveButton () {
        //Initializing
       Visuals.setButtonVisual(saveButton);

        //Action
       saveButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               saveAll();
               Visuals.showInformativeMessage("Changes saved successfully!");
           }
       });
    }

    private void setSettingsButton () {
        //Initializing
        Visuals.setButtonVisual(settingsButton);

        //Action
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu(MenuOption.SETTINGS);
            }
        });

    }

    //--CreateEditNote Panel

    private void setBackArowButton() {
        Visuals.setButtonVisual(backArrowButton);
        backArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu(state.getLastMenuSelected());
                noteBeingEdited = null; //revert a possible note beeing edited
            }
        });
    }

    private void setPalleteButton() {
        Visuals.setButtonVisual(palleteButton);
        palleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialogColorPallete = new JDialog();
                dialogColorPallete.setSize(300, 200);
                dialogColorPallete.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/NoteTakingPROIcon.png")));
                Color chosenColor = JColorChooser.showDialog(dialogColorPallete, "Color Pallete", messageAreaCreateEditNote.getBackground());
                if(chosenColor != null)
                    messageAreaCreateEditNote.setBackground(chosenColor);

                messageAreaCreateEditNote.revalidate();
                messageAreaCreateEditNote.repaint();
            }
        });
    }

    private void setCheckButton() {
        Visuals.setButtonVisual(checkButton);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleAreaCreateEditNote.getText().trim();
                String message = messageAreaCreateEditNote.getText().trim();
                Color color = messageAreaCreateEditNote.getBackground();

                if(noteBeingEdited == null){ //Adding note
                    notesPanel.addNote(title, message, color, MainFrame.this);
                } else{ //Editing existing note
                    noteBeingEdited.setNoteVisuals(title, message, color);
                    noteBeingEdited = null;
                }
                changeMenu(state.getLastMenuSelected());

                //Put default settings again
                setEditNoteTexts(TITLE_CREATE_EDIT_NOTE_PLACEHOLDER_TEXT, MESSAGE_CREATE_EDIT_NOTE_PLACEHOLDER_TEXT, Visuals.MY_LIGHT_BROWN_GRAY);
            }
        });
    }

    @Override
    public void noteClicked(NoteClickEvent event) {
        Note clickedNote = event.getNote();
        noteBeingEdited = clickedNote;
        setEditNoteTexts(clickedNote.getTitle(), clickedNote.getMessage(), clickedNote.getColor());

        changeMenu(MenuOption.EDITING_NOTE);
    }

    private void setCreateOrEditNoteTextAreas(){
        titleAreaCreateEditNote.setBorder(new EmptyBorder(15, 10, 10, 10));
        messageAreaCreateEditNote.setBorder(new EmptyBorder(10, 10, 10, 10));

        titleAreaCreateEditNote.getCaret().setVisible(true);
        titleAreaCreateEditNote.getCaret().setSelectionVisible(true);
        messageAreaCreateEditNote.getCaret().setVisible(true);
        messageAreaCreateEditNote.getCaret().setSelectionVisible(true);

        scrollPaneCreateEditNote.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneCreateEditNote.getVerticalScrollBar().setUnitIncrement(16);

        titleAreaCreateEditNote.addFocusListener(new PlaceHolderEffect(titleAreaCreateEditNote, "Insert title.."));
        messageAreaCreateEditNote.addFocusListener(new PlaceHolderEffect(messageAreaCreateEditNote, "Start your note.."));
    }

    private void setEditNoteTexts(String title, String message, Color color){
        titleAreaCreateEditNote.setText(title);
        messageAreaCreateEditNote.setText(message);
        messageAreaCreateEditNote.setBackground(color);
    }

    //Settings Panel
    private void setBackArrowSettingsButton() {
        Visuals.setButtonVisual(backArrowSettingsButton);

        backArrowSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu(state.getLastMenuSelected());
            }
        });
    }

    private void setChangeUsernameButton() {
        changeUsernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = Visuals.getSimpleInputDialog("Enter the new username:");

                if(input != null){
                    configs.setUsername(input);
                    Visuals.showInformativeMessage("Username changed with sucess!");
                }
            }
        });
    }

    private void setCleanNotesButton() {
        deleteAllNotesPermanentlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean response = Visuals.getConfirmationInputDialog("Do you want to proceed removing ALL notes permantly?");

                if(response){
                    notesPanel.removePermantlyAllNotes();
                    Visuals.showInformativeMessage("All notes have been permantly removed!");
                }
            }
        });
    }

    private void setFreshStartButton() {
        freshStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean response = Visuals.getConfirmationInputDialog("Do you want to proceed?");

                if(response){
                    boolean wasRemoved1 = notesPanel.removeSerializeNotesFile();
                    boolean wasRemoved2 = configs.removeConfigFile();

                    if(wasRemoved1 && wasRemoved2){
                        Visuals.showInformativeMessage("Changes applied with sucess! Restarting the app!");
                        restartApplication();
                    }
                    else
                        Visuals.showErrorMessage("Something went wrong deleting existing data!");
                }

            }
        });
    }

    public static void restartApplication() {
        try {
            // Get the current Java process
            String java = System.getProperty("java.home") + "/bin/java";
            String classpath = System.getProperty("java.class.path");
            String className = "Main";

            // Restart the application
            ProcessBuilder processBuilder = new ProcessBuilder(java, "-cp", classpath, className);
            processBuilder.start();

            // Exiting the current process
            System.exit(0);
        } catch (IOException e) {
            Visuals.showErrorMessage("Failed to restart the application.");
        }
    }

}

