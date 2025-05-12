package src.panels.Style;

import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * A custom border with rounded corners.
 */
public class RoundedBorder extends AbstractBorder {
    private final int radius;
    private final Color color;

    /**
     * Constructor that allows specifying both radius and border color.
     *
     * @param radius the corner radius of the rounded border
     * @param color  the color of the border
     */
    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    /**
     * Overloaded constructor that uses a default color (white).
     *
     * @param radius the corner radius of the rounded border
     */
    public RoundedBorder(int radius) {
        this(radius, Color.WHITE);  // default border color
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x + 1, y + 1, width - 3, height - 3, radius, radius);
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
