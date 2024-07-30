package visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Static class used by many other classes. It has some very commoned and usefull methods regarding
 * colors, visual effects, dialogs, etc. Everything in one place.
 * @author joaoVidLima
 */
public class Visuals {

    //Colors used
    public static final Color MY_LIGHT_GRAY = Color.decode("#404040");
    public static final Color MY_DARK_GRAY = Color.decode("#1E1E1E");
    public static final Color MY_GRAY = Color.decode("#2C2C2E");
    public static final Color MY_LIGHT_BROWN_GRAY = Color.decode("#B2A0A0");
    public static final Color MY_YELLOW = Color.decode("#E0BC3E");
    public static final Color MY_BRICK_RED = Color.decode("#993434");
    public static final Color WHITE = Color.decode("#FFFFFF");
    public static final Color BLACK = Color.decode("#000000");


    //Fonts used
    public static final Font WELCOMING_TITLE_FONT = new Font("Inter", Font.BOLD, 24);
    public static final Font WELCOMING_TEXT_FONT = new Font("Inter", Font.BOLD, 18);
    public static final Font USERNAME_FIELD_FONT = new Font("Inter", Font.BOLD, 26);
    public static final Font CONFIRMATION_BUTTON_OK_FONT = new Font("Inter", Font.BOLD, 26);
    public static final Font TITLE_LABEL_NOTE_FONT = new Font("Inter", Font.BOLD, 26);
    public static final Font TEXT_AREA_NOTE_FONT = new Font("Inter", Font.PLAIN, 20);


    //Button Visual Setup
    public static void addButtonHoverEffect(JButton button){
        button.addMouseListener(new MouseHoverEffect(button));
    }

    public static void removeButtonHoverEffectAndClick(JButton button){
        for (ActionListener al : button.getActionListeners())
            button.removeActionListener(al);

        for(MouseListener ml : button.getMouseListeners()){
            if(ml instanceof MouseHoverEffect)
                button.removeMouseListener(ml);
        }
    }

    public static void removeMouseActions(Component component){
        for(MouseListener ml : component.getMouseListeners())
            component.removeMouseListener(ml);
    }

    public static void setButtonVisual(JButton button){
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        addButtonHoverEffect(button);
    }

    public static void setButtonVisual(JButton button, Color color, String iconName, int width, int height){
        button.setBackground(color);
        button.setIcon(getIcon(iconName, width, height));
        button.setPreferredSize(new Dimension(width, height));
        setButtonVisual(button);
    }


    public static Icon getIcon(String iconName, int width, int height){
        ImageIcon icon = new ImageIcon(Visuals.class.getResource(iconName));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public static float getBrightness(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return hsb[2]; // Brightness component in HSB
    }

    public static Color lightenColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return Color.getHSBColor(hsb[0], 0.2f, hsb[2]);
    }

    public static Color darkenColor(Color color) {
        int r = Math.max(0, (int)(color.getRed() * 0.8));
        int g = Math.max(0, (int)(color.getGreen() * 0.8));
        int b = Math.max(0, (int)(color.getBlue() * 0.8));
        return new Color(r, g, b);
    }

    //Dialog messages
    public static void showErrorMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarningMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static void showInformativeMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static String getSimpleInputDialog(String message){

        return JOptionPane.showInputDialog(null, message, "Enter your input", JOptionPane.PLAIN_MESSAGE);
    }

    public static boolean getConfirmationInputDialog(String message){

        int response = JOptionPane.showConfirmDialog(null, message, "Confirm your action", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return (response == JOptionPane.YES_OPTION);
    }

}
