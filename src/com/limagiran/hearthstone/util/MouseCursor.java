package  com.limagiran.hearthstone.util;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Silva
 */
public class MouseCursor {

    public static final Cursor DEFAULT = createCursorMouse(Images.MOUSE_CURSOR_DEFAULT, new Point(0, 3));
    public static final Cursor ALVO = createCursorMouse(Images.MOUSE_CURSOR_ALVO);

    public static Cursor createCursorMouse(ImageIcon icon) {
        Point point = new Point(icon.getIconWidth() / 2, icon.getIconHeight() / 2);
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(icon.getImage(), point, "icon");
        return cursor;
    }

    public static Cursor createCursorMouse(ImageIcon icon, Point point) {
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(icon.getImage(), point, "icon");
        return cursor;
    }
}