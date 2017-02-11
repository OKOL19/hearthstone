package  com.limagiran.hearthstone.partida.view;

import com.limagiran.hearthstone.util.Fontes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Vinicius Silva
 */
public final class PopUp extends JLabel {
    
    public final String text;
    private ImageIcon imageIcon;
    private int size;

    public PopUp(String text, int size) {
        this.text = text;
        this.size = size;
        try {
            init();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void init() throws IOException {        
        JLabel label = new JLabel(text);
        label.setFont(Fontes.getBelwe(size));
        Dimension d = label.getPreferredSize();

        BufferedImage image = new BufferedImage(d.width + 20, d.height + 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        adjustGraphics(g);

        g.setBackground(new Color(0, 0, 0, 0));
        TextLayout textLayout = new TextLayout(text, Fontes.getBelwe(size), g.getFontRenderContext());
        g.setPaint(new Color(180, 180, 180));
        textLayout.draw(g, 13, 68);
        g.dispose();

        float ninth = 1.0f / 9.0f;
        float[] kernel = {ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth};
        ConvolveOp op = new ConvolveOp(new Kernel(3, 3, kernel), ConvolveOp.EDGE_NO_OP, null);
        BufferedImage image2 = op.filter(image, null);

        Graphics2D g2 = image2.createGraphics();
        adjustGraphics(g2);
        g2.setPaint(Color.BLACK);
        textLayout.draw(g2, 10, 70);
        imageIcon = new ImageIcon(image2);
        setIcon(imageIcon);
        d = new Dimension(image2.getWidth() + 20, image2.getHeight());
        setSize(d);
        setPreferredSize(d);
    }

    private void adjustGraphics(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }
       
    public ImageIcon getImageIcon(){
        return imageIcon;
    }
}