package visuals;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Implementation of a placeholder effect for JTextPanes.
 * @author joaoVidLima
 */
public class PlaceHolderEffect implements FocusListener {

    private JTextPane textArea;
    private String placeHolderText;

    public PlaceHolderEffect(JTextPane textArea, String placeHolderText){
        this.textArea = textArea;
        this.placeHolderText = placeHolderText;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (textArea.getText().equals(placeHolderText)) {
            textArea.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (textArea.getText().isEmpty()) {
            textArea.setText(placeHolderText);
        }
    }

}
