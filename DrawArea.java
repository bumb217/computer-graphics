import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class DrawArea extends JComponent {

    private ArrayList<Point> points;
    private LinkedList<Point> convexHull;

    private Image image;
    private Graphics2D graphics2D;

    private final int PAINT_RADIUS = 10;

    public DrawArea() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.add(e.getPoint());
                graphics2D.fillOval(e.getX(), e.getY(), PAINT_RADIUS, PAINT_RADIUS);
                graphics2D.drawString(new Integer(points.size() - 1).toString(), e.getX(), e.getY());
                repaint();
            }
        });
    }

    public void clearData() {
        clear();
        initialize();
    }

    public void createConvexHull() {
        QuickHull quickHull = new QuickHull();

        convexHull = quickHull.createConvexHull(points);

        graphics2D.setPaint(Color.green);
        for (int i = 0; i < convexHull.size(); i++){
            graphics2D.fillOval(convexHull.get(i).x, convexHull.get(i).y, PAINT_RADIUS, PAINT_RADIUS);
        }
        graphics2D.setPaint(Color.black);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clearData();
        }
        g.drawImage(image, 0, 0, null);
    }

    private void  initialize() {
        points = new ArrayList<>();
        graphics2D.setPaint(Color.black);
    }

    private void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        repaint();
    }
}