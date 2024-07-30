package visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Cleaner hover effect used in almost every button instead of the default one.
 * It lightens or darkens the background color of the button (based on the brigtness of the button color)
 * when the mouse enteres it, and sets the original color when exits.
 * @author joaoVidLima
 */
public class MouseHoverEffect implements MouseListener {

    private JButton button;
    private Color color;

    public MouseHoverEffect(JButton button){
        this.button = button;
        this.color = button.getBackground();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        color = button.getBackground();
        float limitBrightness = 0.9f;

        if(Visuals.getBrightness(color) > limitBrightness)
            button.setBackground(Visuals.darkenColor(color));
        else
            button.setBackground(Visuals.lightenColor(color));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setBackground(color);
    }
}
