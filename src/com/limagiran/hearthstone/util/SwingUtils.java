package com.limagiran.hearthstone.util;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 *
 * @author Vinicius
 */
public class SwingUtils {

    /**
     * Executar uma thread na thread de interface
     *
     * @param runnable runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        try {
            if (EventQueue.isDispatchThread()) {
                runnable.run();
            } else {
                SwingUtilities.invokeAndWait(runnable);
            }
        } catch (Exception ex) {
            RW.log(ex);
        }
    }
    
    public static void blockSpinnerUpDown(JSpinner spinner) {
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField()
                .addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case VK_UP:
                            case VK_DOWN:
                                e.consume();
                        }
                    }
                });
    }

    /**
     * Maximiza um JDialog
     *
     * @param dialog janela
     * @param margin distância da margem que não será maximizada
     */
    public static void maximize(JDialog dialog, int margin) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        d.width -= margin;
        d.height -= margin;
        dialog.setSize(d);
        dialog.setPreferredSize(d);
        dialog.setLocationRelativeTo(null);
    }

    /**
     * Maximiza um JDialog
     *
     * @param dialog janela
     */
    public static void maximize(JDialog dialog) {
        maximize(dialog, 0);
    }
    
    /**
     * Cria um documento com limite de caracteres
     *
     * @param max máximo de caracteres
     * @return Documento configurado
     */
    public static PlainDocument createDocMaxSize(final int max) {
        return new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (getLength() < max) {
                    int free = (max - getLength());
                    if ((str != null) && (str.length() > free)) {
                        str = str.substring(0, free);
                    }
                    super.insertString(offs, str, a);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        };
    }

    /**
     * Cria um documento que só aceita letras, números e espaço
     *
     * @param max limite de caracteres. -1 == sem limite
     * @return Documento configurado
     */
    public static PlainDocument createDocOnlyWordNumberAndSpace(int max) {
        return new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) {
                    return;
                }
                str = str.replaceAll("[^\\d\\w _àèìòùÀÈÌÒÙáéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛãñõÃÑÕäëïöüÿÄËÏÖÜŸçÇ]", "");
                if (max == -1) {
                    super.insertString(offs, str, a);
                }
                if (getLength() < max) {
                    int free = (max - getLength());
                    if (str.length() > free) {
                        str = str.substring(0, free);
                    }
                    super.insertString(offs, str, a);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        };
    }

    /**
     * Cria um documento que só aceita letras, números e espaço
     *
     * @return Documento configurado
     */
    public static PlainDocument createDocOnlyWordNumberAndSpace() {
        return createDocOnlyWordNumberAndSpace(-1);
    }
   
    public static void requestFocusOnVisible(Component c) {
        new Thread(() -> {
            while (true) {
                try {
                    Utils.sleep(100);
                    c.getLocationOnScreen();
                    break;
                } catch (Exception ex) {
                }
            }
            SwingUtilities.invokeLater(() -> c.requestFocus());
        }).start();
    }
}
