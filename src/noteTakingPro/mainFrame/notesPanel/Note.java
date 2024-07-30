package noteTakingPro.mainFrame.notesPanel;

import visuals.Visuals;
import noteTakingPro.mainFrame.NoteClickListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class represents a Note.
 * It has to react to several button presses and update atributes based on them.
 * @author joaoVidLima
 */
public class Note extends JPanel implements Serializable {

    //Buttons
    private JButton starButton;
    private JButton doneButton;
    private JButton binButton;
    private JButton deletePermanentlyButton;

    //Important panels
    private JPanel topPanel;
    private JPanel bottonPanel;
    private JLabel titleLabel;
    private JTextArea textArea;

    //Atributes
    private LocalDateTime lastModDate;
    private boolean isStared;
    private boolean isDone;
    private boolean isInBin;

    /* The Listener that will be notified about the click on this note. I don't want to serialize it (transient)
    since it will always be attached in runtime */
    private transient NoteClickListener listenerFromClick;

    public Note(String title, String message, Color color, int width, int heigth, NoteClickListener listenerFromClick){
        super();
        this.lastModDate = LocalDateTime.now();
        this.isStared = false;
        this.isDone = false;
        this.isInBin = false;

        //Base Layout
        setLayout(new BorderLayout());

        //Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Visuals.MY_DARK_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Top Panel
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Visuals.MY_BRICK_RED);
        topPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        //Star Button
        starButton = new JButton();
        topPanel.add(starButton, BorderLayout.WEST);

        //Title Label
        titleLabel = new JLabel(title);
        titleLabel.setFont(Visuals.TITLE_LABEL_NOTE_FONT);
        titleLabel.setForeground(Visuals.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        //Message textArea
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(message);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(8, 8, 8, 8));
        textArea.setForeground(Visuals.BLACK);
        textArea.setFont(Visuals.TEXT_AREA_NOTE_FONT);
        textArea.setBackground(color);

        //Scroll Pane
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBackground(color);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanel.add(scroll, BorderLayout.CENTER);

        //Botton Panel
        bottonPanel = new JPanel(new GridLayout());
        bottonPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        bottonPanel.setBackground(color);

        doneButton = new JButton();
        binButton = new JButton();
        deletePermanentlyButton = new JButton();

        bottonPanel.add(doneButton);
        bottonPanel.add(binButton);
        bottonPanel.add(deletePermanentlyButton);

        deletePermanentlyButton.setVisible(false);

        mainPanel.add(bottonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        attachActions(listenerFromClick);
        setPreferredSize(new Dimension(width, heigth));
        setVisible(true);
    }

    //Getters
    public String getTitle() {return this.titleLabel.getText().trim();}
    public String getMessage(){return this.textArea.getText().trim();}
    public LocalDateTime getLastModDate(){return  this.lastModDate;}
    public Color getColor() {return this.textArea.getBackground();}
    public boolean isStared(){return isStared;}
    public boolean isDone(){return isDone;}
    public boolean isInBin(){return isInBin;}

    //Setters
    public void setNoteVisuals(String title, String message, Color color){
        titleLabel.setText(title);
        textArea.setText(message);
        textArea.setBackground(color);
        bottonPanel.setBackground(color);
        lastModDate = LocalDateTime.now();
        doneButton.setBackground(color);
        binButton.setBackground(color);
        deletePermanentlyButton.setBackground(color);
    }

    //Actions
    public void attachActions(NoteClickListener listenerFromClick) {

        //Hover effect and visuals
        Visuals.setButtonVisual(starButton, Visuals.MY_BRICK_RED, "/images/starUnfilled.png", 50, 50);
        Visuals.setButtonVisual(doneButton, getColor(), "/images/done.png", 50, 50);
        Visuals.setButtonVisual(binButton, getColor(), "/images/bin.png", 50, 50);
        Visuals.setButtonVisual(deletePermanentlyButton, getColor(), "/images/deletePerma.png", 50, 50);

        //Click action
        setStarButtonAction();
        setDoneButtonAction();
        setBinButtonAction();
        setdeletePermanentlyButtonAction();
        editNoteAction(listenerFromClick);
    }


    private void refreshParentPanel(boolean toPermaDelete){
        //Refresh NotesPanel after changes on a Note
        NotesPanel parent = (NotesPanel) Note.this.getParent();
        if(parent != null){
            if(toPermaDelete)
                parent.removeNote(Note.this);

            parent.displayNotesForSelectedMenu();

            parent.revalidate();
            parent.repaint();
        }
    }

    private void setStarButtonAction() {
        starButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isStared = !isStared;
                if(isStared)
                    starButton.setIcon(Visuals.getIcon("/images/starFilled.png", 50, 50));
                else
                    starButton.setIcon(Visuals.getIcon("/images/starUnfilled.png", 50, 50));

                starButton.setBackground(topPanel.getBackground()); //Reseting hover effect color
                refreshParentPanel(false);
            }
        });
    }

    private void setDoneButtonAction() {
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDone = !isDone;
                if(isDone)
                    doneButton.setIcon(Visuals.getIcon("/images/undone.png", 50, 50));
                else
                    doneButton.setIcon(Visuals.getIcon("/images/done.png", 50, 50));

                doneButton.setBackground(getColor()); //Reseting hover effect color
                refreshParentPanel(false);
            }
        });

    }

    private void setBinButtonAction() {
        binButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isInBin = !isInBin;
                if(isInBin){
                    binButton.setIcon(Visuals.getIcon("/images/restore.png", 50, 50));
                    deletePermanentlyButton.setVisible(true);
                    removeBinMenuActions();
                }
                else{
                    binButton.setIcon(Visuals.getIcon("/images/bin.png", 50, 50));
                    deletePermanentlyButton.setVisible(false);
                    setStarButtonAction();
                    setDoneButtonAction();
                    Visuals.addButtonHoverEffect(starButton);
                    Visuals.addButtonHoverEffect(doneButton);
                    editNoteAction(listenerFromClick);
                }

                binButton.setBackground(getColor()); //Reseting hover effect color
                refreshParentPanel(false);
            }
        });
    }

    public void removeAllPressed(){
        if(isInBin)
            refreshParentPanel(true);
        else{
            isInBin = true;
            deletePermanentlyButton.setVisible(true);
            binButton.setIcon(Visuals.getIcon("/images/restore.png", 50, 50));
            removeBinMenuActions();
            refreshParentPanel(false);
        }
    }

    private void removeBinMenuActions(){
        Visuals.removeButtonHoverEffectAndClick(starButton);
        Visuals.removeButtonHoverEffectAndClick(doneButton);
        Visuals.removeMouseActions(topPanel);
        Visuals.removeMouseActions(textArea);
    }

    private void editNoteAction(NoteClickListener listenerFromClick){
        this.listenerFromClick = listenerFromClick;
        topPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fireNoteClicked();
            }
        });

        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fireNoteClicked();
            }
        });
    }

    protected void fireNoteClicked() {
        NoteClickEvent event = new NoteClickEvent(this, this);
        listenerFromClick.noteClicked(event);
    }

    private void setdeletePermanentlyButtonAction() {
        deletePermanentlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePermanentlyButton.setBackground(getColor()); //Reseting hover effect color
                refreshParentPanel(true);
            }
        });
    }


    /**
     * Redefines the method <code>equals</code> in the superclass Object.
     * Two notes are equal if they have the same title and creatingDate.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Note))
            return false;

        Note other = (Note) obj;
        if (this.titleLabel.getText().trim() == null) {
            if (other.getTitle() != null)
                return false;
        } else if (!this.titleLabel.getText().trim().equals(other.getTitle())) {
            return false;

        } else if (lastModDate == null) {
            if (other.getLastModDate() != null)
                return false;
        } else if (!lastModDate.equals(other.getLastModDate())) {
            return false;
        }
        return true;
    }

}
