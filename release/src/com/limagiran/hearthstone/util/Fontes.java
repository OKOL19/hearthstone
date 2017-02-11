package  com.limagiran.hearthstone.util;

import java.awt.Font;

/**
 *
 * @author Vinicius Silva
 */
public class Fontes {

    public static Font getBelwe(int size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Fontes.class.getResourceAsStream("/com/limagiran/hearthstone/util/belwe.ttf"));
            font = font.deriveFont(Font.BOLD, size);
            return font;
        } catch (Exception ex) {
        }
        return new Font("Tahoma", Font.BOLD, size);
    }
}