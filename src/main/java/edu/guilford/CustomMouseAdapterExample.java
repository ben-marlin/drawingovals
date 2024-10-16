package edu.guilford;

// a demo to illustrate mouse events

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomMouseAdapterExample {
    public static void main(String[] args) {

        // creates the frame
        JFrame frame = new JFrame("MouseAdapter Example");
        frame.setLayout(new BorderLayout());

        // create & add label to display mouse event messages
        JLabel messageLabel = new JLabel("Mouse events will appear here");
        frame.add(messageLabel, BorderLayout.SOUTH);

        // create & add panel, which supports mouse events
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);        

        // attach mouse listener containing event handlers
        panel.addMouseListener(new MouseAdapter() {

            // mouse click event - both press & release at same point
            @Override
            public void mouseClicked(MouseEvent e) {
                messageLabel.setText("Mouse clicked at (" + e.getX() + ", " + e.getY() + ")");
            }

            // mouse press event - button not released before moving
            @Override
            public void mousePressed(MouseEvent e) {
                messageLabel.setText("Mouse pressed at (" + e.getX() + ", " + e.getY() + ")");
            }

            // mouse released event - not at same location as press
            @Override
            public void mouseReleased(MouseEvent e) {
                messageLabel.setText("Mouse released at (" + e.getX() + ", " + e.getY() + ")");
            }

            // mouse enter event - when cursor is over component
            @Override
            public void mouseEntered(MouseEvent e) {
                messageLabel.setText("Mouse entered the component");
            }

            // mouse exit event - when cursor leaves component
            @Override
            public void mouseExited(MouseEvent e) {
                messageLabel.setText("Mouse exited the component");
            }
        });

        // motion listener is separate because they're a different sort of event
        panel.addMouseMotionListener(new MouseAdapter() {

            // mouse dragged event - click, move, then release
            @Override
            public void mouseDragged(MouseEvent e) {
                messageLabel.setText("Mouse dragged to (" + e.getX() + ", " + e.getY() + ")");
            }

            // mouse moved event - no click is involved
            @Override
            public void mouseMoved(MouseEvent e) {
                messageLabel.setText("Mouse moved to (" + e.getX() + ", " + e.getY() + ")");
            }
        });

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}