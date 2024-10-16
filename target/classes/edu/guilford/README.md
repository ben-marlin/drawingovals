# Drawing with Mouse Events

This project introduces event handling. You will produce a program that the user click & drag a mouse to draw an oval in the window. In doing this, you will gain experience with the following.

- mouse events
- mouse motion events
- paint & repaint methods
- graphics objects
- global vs. local variables

## Sample Code

You've been given a program titled `CustomMouseAdapterExample.java`. It illustrates the methods common to window-based applications. Run it, move your mouse around, click, drag, press & release and see what happens.

## Listeners

We've already seen an example with a button, but sometimes we need more detail in our events than just a pressed button. This project introduces the MouseListener and MouseMotionListener. Each is associated with a different sort of "event" that happens when dealing with the mouse.

MouseListener watches for one location-based events. These are the following.

- mousePressed: the button is pressed
- mouseReleased: the button is released
- mouseClicked: the button is pressed & released without moving
- mouseEntered: the mouse first hovers over the object
- mouseExited: the mouse leaves the object

MouseMotionListener watches for a motion that is in progress. These events are the following.

- mouseMoved: the mouse moves while the button is not pressed
- mouseClicked: the mouse moves while the button is pressed

Naturally, we can find uses for any of these events. The provided example concentrates on reporting the locations the events happened and displaying them in a label at the bottom of the window. Experiment with it and try to get it to display for enter, exit, move, click, press, release, and drag.

## Creating your own

We're going to build our own class here. As in previous projects, create a Java file by using `File > New File > New Java File > Class` and name it OvalDrawingFrame. Let VSC add the .java for you to avoid typos.

Add the package and then the following imports.
```
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
```
The swing package has components like JFrame, JPanel, JButton, etc. The MouseAdapter contains blank versions of the listeners with methods for you to overwrite. The MouseEvents hold information about the event's location, which button, etc.

Now change the class header so it extends JFrame. This will then inherit all manner of pre-built frame things like a title bar, a close button, etc.

Create a constructor, either through VSC or by typing the header. Then add the following code.
```
// set up the frame
setTitle("Oval Drawing Frame");
setSize(800, 600);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
```
Now add a main method which instantiates an OvalDrawingFrame object, and sets it to be visible.

Test your program now. It should display, but won't do anything yet.

## Add the Panel

In previous projects, we had a panel as a container. This time, our panel needs to do something - record mouse event locations and draw an oval. 

Between the constructor and main method, add the following code.
```
private class DrawingPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // draw the background color

    }
}
```
This private class will be a version of JPanel we can add custom methods to. Because we don't need it to do anything in particular, we don't need to write a constructor because it will default to the empty one from JPanel. We need to override the paintComponent method because we're going to draw on the panel, but leave it empty for now.

Now we need to instantiate the panel. In the constructor, add this code after the line that sets the close operation.
```
// set up the drawing panel
DrawingPanel drawingPanel = new DrawingPanel();
add(drawingPanel);
```
This should let you test the program. It won't do anything yet, of course, but it should run.

## Recording Locations

As we need the locations of our mouse events to be communicated throughout the class, add the following to the class after the signature but before the constructor.
```
private Point startPoint;   
private Point endPoint;     
```
These are used to record where the mouse is pressed & released. Next we need to actually assign the values. To do this, we first assign startPoint when the button is pressed. Add the following after the line that adds the panel.
```
// record start point
drawingPanel.addMouseListener(new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
    }
});
```
Since we are only interested in the press & release, we don't need to override all the event methods. But we also won't implement the release method for reasons we will see shortly.

The MouseEvent e contains information about what button was pressed and where. As seen in the example, other sorts of events will need to read other information. Here, we read the point the mouse was pressed using the getPoint() method. This just records the x & y coordinates it was pressed.

Testing your program at this point will help catch any miscellaneous errors that may have cropped up, so it's a good idea. But pressing the mouse button won't cause any visible event yet.

We add the following code to record where the mouse button is released.
```
// record end point
drawingPanel.addMouseMotionListener(new MouseAdapter() {

    // helps draw the circle while dragging
    @Override
    public void mouseDragged(MouseEvent e) {
        endPoint = e.getPoint();
        drawingPanel.repaint(); // repaint will call paintComponent
    }
});
```
We have to use the MouseMotionListener to get the mouseDragged method. The reason we want to use mouseDragged instead of mouseReleased in the MouseListener is so we can have an oval being drawn while the user is dragging the mouse. It lets them see what they're doing instead of just having it appear at the end.

As with mousePressed, the MouseEvent is read to get the point that the mouse has been dragged to. Then calling repaint() from drawingPanel will do some housekeeping before calling the paintComponent method - which we've overwritten earlier, but we haven't finished up with yet. Consequently, it would be a good idea to test your program at this point to make sure no random error has arisen, but clicking & dragging won't draw an oval yet.

## Drawing the Oval

Inside the paintComponent method of the DrawingPanel class, add the following code after the call to super.paintComponent (which takes care of things like the color of the background).
```
int x = Math.min(startPoint.x, endPoint.x); // choose left
int y = Math.min(startPoint.y, endPoint.y); // choose upper

int width = Math.abs(startPoint.x - endPoint.x); // abs to avoid negatives
int height = Math.abs(startPoint.y - endPoint.y);

g.setColor(Color.BLUE);
g.drawOval(x, y, width, height);    // does the actual drawing
```
As drawing an oval starts with the upper left corner of the bounding box, we need to determine x and y as the farthest left and top of startPoint and endPoint - thus the minimum. The width and height will be the difference between start and end, but since we don't know ahead of time which position they're in, we take the absolute value of the difference to make sure the measurements are positive.

Test at this point, and it should draw an oval while you are dragging across the screen. 

## Experiment a Little

Try changing drawOval to fillOval, drawRectangle, and fillRectangle. If you'd like to try drawRoundedRectangle and fillRoundedRectangle, you'll need to add two more entries at the end - maybe 5, 5 or 10, 10 or something.

Try changing the color. I put it in BLUE, but default colors include BLACK, BLUE, CYAN, DARK_GRAY, GRAY, GREEN, LIGHT_GRAY, MAGENTA, ORANGE, PINK, RED, WHITE. Go back to the constructor and change the background color by adding a line that says setBackground(Color.PINK) or some other color after the setSize line.

## Wrapping Up

Make sure you've put comments in places that will help you remember what the code does - and don't forget your name! Save it, stage changes, write a commit message, commit, and sync to the GitHub repo. Then go to Canvas. Paste a screenshot and a copy of the repo URL there.