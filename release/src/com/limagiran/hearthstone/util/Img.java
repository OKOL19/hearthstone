package  com.limagiran.hearthstone.util;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Vinicius Silva
 */
public class Img {

    /**
     * Redimensiona a imagem do caminho passado por parâmetro
     *
     * @param image imagem
     * @param new_w nova largura
     * @param new_h nova altura
     * @return retorna a imagem redimensionada
     */
    public static ImageIcon redimensionaImg(Image image, int new_w, int new_h) {
        BufferedImage new_img = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = new_img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.drawImage(image, 0, 0, new_w, new_h, null);
        g.dispose();
        return new ImageIcon(new_img);
    }

    /**
     * Redimensiona a imagem do caminho passado por parâmetro
     *
     * @param image imagem
     * @param razao proporção do redimensionamento
     * @return retorna a imagem redimensionada
     */
    public static ImageIcon redimensionaImg(Image image, double razao) {
        double new_w, new_h;
        ImageIcon img = new ImageIcon(image);
        new_w = img.getIconWidth();
        new_h = img.getIconHeight();
        new_w *= razao;
        new_h *= razao;
        BufferedImage new_img = new BufferedImage((int) new_w, (int) new_h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = new_img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.drawImage(image, 0, 0, (int) new_w, (int) new_h, null);
        g.dispose();
        return new ImageIcon(new_img);
    }

    /**
     * Salva uma imagem .png com o print do container
     *
     * @param component objeto container
     * @param file local onde a imagem será salva
     */
    public static void printContainer(Component component, File file) {
        int width = component.getWidth();
        int height = component.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        component.paintAll(graphics);
        graphics.dispose();
        try {
            ImageIO.write(image, "PNG", file);
        } catch (IOException ex) {
        }
    }

    /**
     * Retorna a image do container
     *
     * @param component objeto container
     * @return imagem do container
     */
    public static ImageIcon get(Component component) {
        int width = component.getWidth();
        int height = component.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        component.paintAll(graphics);
        graphics.dispose();
        return new ImageIcon(image);
    }

    /**
     * Salva uma imagem .png com o printscreen da tela da área definida
     *
     * @param point início do retângulo x e y
     * @param dimension tamanho da área
     * @param file local onde a imagem será salva
     * @return {@code true} para imagem salva. {@code false} para erro ao salvar
     * imagem.
     */
    public static boolean printScreen(Point point, Dimension dimension, File file) {
        try {
            BufferedImage bi = new Robot().createScreenCapture(new Rectangle(point, dimension));
            ImageIO.write(bi, "PNG", file);
            return true;
        } catch (IOException | AWTException ex) {
            return false;
        }
    }

    /**
     * Salva uma imagem .png com o printscreen do Container passado por
     * parâmetro
     *
     * @param c Container a ser salvo
     * @param file local onde a imagem será salva
     * @return {@code true} para imagem salva. {@code false} para erro ao salvar
     * imagem.
     */
    public static boolean printScreen(Container c, File file) {
        return printScreen(c.getLocationOnScreen(), c.getSize(), file);
    }

    /**
     * Salva uma imagem .png com o printscreen da tela atual
     *
     * @param file local a ser salva a imagem
     * @return {@code true} para imagem salva. {@code false} para erro ao salvar
     * imagem.
     */
    public static boolean printScreen(File file) {
        try {
            Robot robot = new Robot();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension d = toolkit.getScreenSize();
            BufferedImage bi = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height));
            ImageIO.write(bi, "PNG", file);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Salva uma imagem .png com o printscreen da tela atual na pasta raiz do
     * programa
     *
     * @return {@code true} para imagem salva. {@code false} para erro ao salvar
     * imagem.
     */
    public static boolean printScreen() {
        return printScreen(new File("printscreen_" + System.currentTimeMillis() + ".PNG"));
    }
    
    /**
     * Converter String Base64 em imagem
     *
     * @param imageString string com a imagem no formato base64
     * @return imagem
     * @throws java.lang.Exception
     */
    public static BufferedImage decodeToImage(String imageString) throws Exception {
        BufferedImage image;
        byte[] imageByte = DatatypeConverter.parseBase64Binary(imageString);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis);
        bis.close();
        return image;
    }

    /**
     * Codificar imagem para base64
     *
     * @param file local da imagem
     * @param type formato da codificação: jpeg, bmp, ...
     * @return String com a imagem na base64
     * @throws java.lang.Exception
     */
    public static String encodeToString(File file, String type) throws Exception {
        BufferedImage img = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(img, type, bos);
        String imageString = DatatypeConverter.printBase64Binary(bos.toByteArray());
        bos.close();
        return imageString;
    }

    /**
     * Salva a imagem no caminho passado por parâmetro
     *
     * @param imagem imagem a ser salva
     * @param file caminho onde a imagem será salva
     * @param type tipo da imagem: png, jpeg, ...
     * @throws java.io.IOException
     */
    public static void salvarImagem(BufferedImage imagem, File file, String type) throws IOException {
        ImageIO.write(imagem, type, file);
    }

    /**
     * Rotaciona uma imagem
     *
     * @param image imagem a ser rotacionada
     * @param angle ângulo da rotação
     * @return imagem rotacionada
     */
    public static ImageIcon girar(ImageIcon image, double angle) {
        BufferedImage rotateImage = ImageIconToBufferedImage(image);
        angle %= 360;
        if (angle < 0) {
            angle += 360;
        }
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), rotateImage.getWidth() / 2.0, rotateImage.getHeight() / 2.0);

        double ytrans;
        double xtrans;
        if (angle <= 90) {
            xtrans = tx.transform(new Point2D.Double(0, rotateImage.getHeight()), null).getX();
            ytrans = tx.transform(new Point2D.Double(0.0, 0.0), null).getY();
        } else if (angle <= 180) {
            xtrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), rotateImage.getHeight()), null).getX();
            ytrans = tx.transform(new Point2D.Double(0, rotateImage.getHeight()), null).getY();
        } else if (angle <= 270) {
            xtrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), 0), null).getX();
            ytrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), rotateImage.getHeight()), null).getY();
        } else {
            xtrans = tx.transform(new Point2D.Double(0, 0), null).getX();
            ytrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), 0), null).getY();
        }

        AffineTransform translationTransform = new AffineTransform();
        translationTransform.translate(-xtrans, -ytrans);
        tx.preConcatenate(translationTransform);

        return new ImageIcon(new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR).filter(rotateImage, null));
    }

    /**
     * Converte um objeto ImageIcon para BufferedImage
     *
     * @param image imagem a ser convertida
     * @return imagem convertida
     */
    public static BufferedImage ImageIconToBufferedImage(ImageIcon image) {
        BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        try {
            bi = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            image.paintIcon(null, g, 0, 0);
            g.dispose();
            return bi;
        } catch (Exception e) {
            return bi;
        }
    }

    /**
     * Retorna um recorte cirular da imagem passada por parâmetro
     *
     * @param imagem imagem a ser recortada
     * @param x início do corte na horizontal
     * @param y início do corte na vertical
     * @param w largura do corte
     * @param h altura do corte
     * @return imagem recortada ou a mesma imagem para valores inválidos
     */
    public static ImageIcon getCirculo(ImageIcon imagem, int x, int y, int w, int h) {
        try {
            BufferedImage other = new BufferedImage(imagem.getIconWidth(), imagem.getIconHeight(), TYPE_INT_ARGB);
            Graphics2D g2d = other.createGraphics();
            g2d.setClip(new Ellipse2D.Double(x, y, w, h));
            g2d.drawImage(imagem.getImage(), 0, 0, null);
            g2d.dispose();
            other = other.getSubimage(x, y, w, h);
            return new ImageIcon(other);
        } catch (Exception ex) {
            return imagem;
        }
    }

    /**
     * Aplica uma porcentagem de transparencia na imagem
     *
     * @param img imagem a ser editada
     * @param transparencia porcentagem de transparência (de 0.0 a 1.0)
     * @return imagem editada
     */
    public static ImageIcon transparencia(ImageIcon img, float transparencia) {
        BufferedImage new_img = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = new_img.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparencia));
        g.drawImage(img.getImage(), 0, 0, null);
        g.dispose();
        return new ImageIcon(new_img);
    }

    /**
     * Aplica transparência a uma determinada cor
     *
     * @param image imagem a ser editada
     * @param keyColor cor a se tornar transparente
     * @return imagem editada
     */
    public static ImageIcon aplicaTransparencia(ImageIcon image, Color keyColor) {
        try {
            Image img = image.getImage();
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            int[] pxls = getPixels(img);
            for (int i = 0; i < pxls.length; i++) {
                if (pxls[i] == keyColor.getRGB()) {
                    pxls[i] = 0x00ffffff;
                }
            }
            return new ImageIcon(getImage(pxls, w, h));
        } catch (Exception e) {
            e.printStackTrace();
            return image;
        }
    }

    private static Image getImage(int[] pixels, int w, int h) {
        MemoryImageSource source = new MemoryImageSource(w, h, pixels, 0, w);
        return Toolkit.getDefaultToolkit().createImage(source);
    }

    private static int[] getPixels(Image img) throws InterruptedException {
        int[] pix = new int[img.getWidth(null) * img.getHeight(null)];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, img.getWidth(null), img.getHeight(null), pix, 0, img.getWidth(null));
        pg.grabPixels();
        return pix;
    }

    /**
     * Retorna um recorte da imagem passada por parâmetro
     *
     * @param imagem imagem a ser recortada
     * @param x início do corte na horizontal
     * @param y início do corte na vertical
     * @param w largura do corte
     * @param h altura do corte
     * @return imagem recortada ou a mesma imagem para valores inválidos
     */
    public static ImageIcon recortar(ImageIcon imagem, int x, int y, int w, int h) {
        try {
            BufferedImage other = new BufferedImage(imagem.getIconWidth(), imagem.getIconHeight(), TYPE_INT_ARGB);
            Graphics2D g2d = other.createGraphics();
            g2d.setClip(x, y, w, h);
            g2d.drawImage(imagem.getImage(), 0, 0, null);
            g2d.dispose();
            other = other.getSubimage(x, y, w, h);
            return new ImageIcon(other);
        } catch (Exception ex) {
            return imagem;
        }
    }

    /**
     * Retorna a cor do pixel da imagem na coordenada passada por parâmetro
     *
     * @param img imagem
     * @param x coordenada X
     * @param y coordenada Y
     * @return Color
     */
    public static Color getColor(Image img, int x, int y) {
        try {
            int[] pix = new int[img.getWidth(null) * img.getHeight(null)];
            PixelGrabber pg = new PixelGrabber(img, 0, 0, img.getWidth(null), img.getHeight(null), pix, 0, img.getWidth(null));
            pg.grabPixels();
            int index = y * img.getWidth(null) + x;
            Color color = new Color(pix[index], true);
            return color;
        } catch (Exception ex) {
            return new Color(0);
        }
    }
    
    /**
     * Aplicar uma textura como plano de fundo no JLabel passado por parâmetro
     * @param textura imagem da textura a ser aplicada
     * @param label label que receberá a textura
     */
    public static void aplicarTextura(ImageIcon textura, JLabel label) {
        BufferedImage bi = Img.ImageIconToBufferedImage(textura);
        BufferedImage bi2 = new BufferedImage(label.getWidth(), label.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) bi2.getGraphics();
        Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
        g2.setPaint(new TexturePaint(bi, r));
        Rectangle rect = new Rectangle(0, 0, label.getWidth(), label.getHeight());
        g2.fill(rect);
        g2.dispose();
        label.setIcon(new ImageIcon(bi2));
    }
    
    /**
     * Aplicar uma textura no gráfico passado por parâmetro
     * @param textura imagem da textura a ser aplicada
     * @param component Gráfico que receberá a textura
     */
    public static void aplicarTextura(ImageIcon textura, Component component) {
        BufferedImage bi = Img.ImageIconToBufferedImage(textura);
        Graphics2D g2 = (Graphics2D) component.getGraphics();
        Rectangle r = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
        g2.setPaint(new TexturePaint(bi, r));
        Rectangle rect = new Rectangle(0, 0, component.getWidth(), component.getHeight());
        g2.fill(rect);
        g2.dispose();
    }
}