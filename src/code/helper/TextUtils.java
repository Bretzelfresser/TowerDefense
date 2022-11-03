package code.helper;

import java.awt.*;

public class TextUtils {

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param graphics The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    public static void drawCenteredString(Graphics graphics, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        Graphics g = graphics.create();
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    /**
     * draws a String inside the rectangle at the top but in the middle so the margin from left and right is the same
     * not that this wont affect any of ur further drawing
     * @param graphics the graphics instance
     * @param text the Text that will be drawn
     * @param rect the rectangle this should be drawn in
     * @param font the font the text should have
     */
    public static void drawXMiddleString(Graphics graphics, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        Graphics g = graphics.create();
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, rect.y);
    }
}
