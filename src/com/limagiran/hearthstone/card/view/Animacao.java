package com.limagiran.hearthstone.card.view;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstone.partida.view.PartidaView;
import com.limagiran.hearthstone.server.GameCliente;
import com.limagiran.hearthstone.util.*;

/**
 *
 * @author Vinicius Silva
 */
public final class Animacao extends JLabel {

    private static final Dimension SIZE = DimensionValues.MESA;
    private boolean sleep = true;
    private final JLabel text = getJLabelDefault();
    private final JLabel icon = getJLabelDefault();

    private boolean disparoEvento = false;
    private Color ColorDisparoEvento = new Color(0, 0, 0, 0);
    private Color background = new Color(0, 0, 0, 0);

    private final Card card;

    public Animacao(Card card) {
        super("", SwingConstants.CENTER);
        this.card = card;
        init();
    }

    @Override
    public String toString() {
        return card.getToString();
    }

    /**
     * configurar o painel
     */
    private void init() {
        text.setLayout(new AbsoluteLayout());
        add(text);
        add(icon);
        background = new Color(238, 199, 122, 255);
        setVisible(true);
        setOpaque(false);
    }

    /**
     * Criar JLabel padrão para as animações
     *
     * @return JLabel padrão
     */
    private JLabel getJLabelDefault() {
        JLabel retorno = new JLabel("", SwingConstants.CENTER) {
            @Override
            public String toString() {
                return card.getToString();
            }
        };
        retorno.setSize(SIZE);
        retorno.setPreferredSize(SIZE);
        retorno.setVisible(false);
        return retorno;
    }

    /**
     * Exibir a animação atual
     *
     * @param time tempo de exibição em milissegundos
     */
    private void show(long time) {
        if (card.getDono().getMesa().contains(card)) {
            update();
            if (sleep) {
                Utils.sleep(time);
                clear();
            } else {
                new Thread(() -> {
                    Utils.sleep(time);
                    clear();
                }).start();
            }
            sleep = true;
        }
    }

    /**
     * Faz um update da interface do painel e exibe-o
     */
    private void update() {
        SwingUtils.runOnUIThread(() -> {
            setVisible(true);
            invalidate();
            validate();
            repaint();
        });
    }

    /**
     * Remove as animações configuradas e oculta o painel
     */
    private void clear() {
        try {
            SwingUtils.runOnUIThread(() -> {
                setVisible(false);
                setOpaque(false);
                text.setVisible(false);
                icon.setVisible(false);
                text.removeAll();
                icon.removeAll();
            });
        } catch (Exception ex) {
        }
    }

    /**
     * Animação de cura ou dano
     *
     * @param value valor da cura ou do dano (positivo para cura, negativo para
     * dano)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void vidaAtual(int value, long time) {
        vidaAtual(value, time, true, true);
    }

    /**
     * Animação de cura ou dano
     *
     * @param value valor da cura ou do dano (positivo para cura, negativo para
     * dano)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     * @param enviar send o pacote de animação pela rede {@code true} ou
     * {@code false}
     */
    public void vidaAtual(int value, long time, boolean sleep, boolean enviar) {
        if (this.sleep = sleep && card.getDono().getMesa().contains(card)) {
            Audios.playEfeitos(value > 0 ? Audios.PARTIDA_CURAR : Audios.PARTIDA_DANO_GERADO);
        }
        clear();
        Color color = value > 0 ? Color.GREEN : Color.RED;
        String string = ((value > 0 ? "+" : "") + value);
        TextoComBorda t = TextoComBorda.create(string, 40, color, toString());
        int x = text.getWidth() / 2 - t.getWidth() / 2;
        int y = text.getHeight() / 2;
        text.add(t, new AbsoluteConstraints(x - 5, y - 30, t.getWidth(), t.getHeight()));
        text.setVisible(true);
        if (value <= 0) {
            icon.setIcon(Images.ICON_DANO_LACAIO);
            icon.setVisible(true);
        }
        if (enviar) {
            enviarPacote(Param.ANIMACAO_CARD_VIDA_ATUAL, value);
        }
        show(time);
    }

    /**
     * Animação de vidaAtual alterada
     *
     * @param value quantidade de vidaAtual alterada (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void vidaMaxima(int value, long time) {
        vidaMaxima(value, time, true);
    }

    /**
     * Animação de vidaAtual alterada
     *
     * @param value quantidade de vidaAtual alterada (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     */
    public void vidaMaxima(int value, long time, boolean sleep) {
        this.sleep = sleep;
        if (value != 0) {
            clear();
            Color color = value > 0 ? Color.GREEN : Color.RED;
            String string = ((value > 0 ? "+" : "") + value);
            TextoComBorda t = TextoComBorda.create(string, 40, color, toString());
            int x = text.getWidth() / 2 - t.getWidth() / 2;
            int y = text.getHeight() / 2;
            text.add(t, new AbsoluteConstraints(x - 5, y - 25, t.getWidth(), t.getHeight()));
            text.setVisible(true);
            icon.setIcon(Images.ICON_HEALTH);
            icon.setVisible(true);
            enviarPacote(Param.ANIMACAO_CARD_VIDA_MAXIMA, value);
            show(time);
        }
    }

    /**
     * Animação de ataque alterado
     *
     * @param value quantidade de ataque alterado (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void ataque(int value, long time) {
        ataque(value, time, true);
    }

    /**
     * Animação de ataque alterado
     *
     * @param value quantidade de ataque alterado (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     */
    public void ataque(int value, long time, boolean sleep) {
        this.sleep = sleep;
        if (value != 0) {
            clear();
            Color color = value > 0 ? Color.GREEN : Color.RED;
            String string = ((value > 0 ? "+" : "") + value);
            TextoComBorda t = TextoComBorda.create(string, 40, color, toString());
            int x = text.getWidth() / 2 - t.getWidth() / 2;
            int y = text.getHeight() / 2;
            text.add(t, new AbsoluteConstraints(x, y - 24, t.getWidth(), t.getHeight()));
            text.setVisible(true);
            icon.setIcon(Images.ICON_ATAQUE);
            icon.setVisible(true);
            enviarPacote(Param.ANIMACAO_CARD_ATAQUE, value);
            show(time);
        }
    }

    /**
     * Animação de vidaAtual original alterada
     *
     * @param value novo valor de vidaAtual
     * @param time tempo de exibição da animação em milissegundos
     */
    public void vidaOriginal(int value, long time) {
        this.sleep = true;
        if (value > 0) {
            clear();
            TextoComBorda t = TextoComBorda.create(Integer.toString(value), 40, Color.GRAY.brighter(), toString());
            int x = text.getWidth() / 2 - t.getWidth() / 2;
            int y = text.getHeight() / 2;
            text.add(t, new AbsoluteConstraints(x - 5, y - 25, t.getWidth(), t.getHeight()));
            text.setVisible(true);
            icon.setIcon(Images.ICON_HEALTH);
            icon.setVisible(true);
            enviarPacote(Param.ANIMACAO_CARD_VIDA_ORIGINAL, value);
            show(time);
        }
    }

    /**
     * Animação lacaio morreu
     */
    public void morreu() {
        try {
            if (card.getDono().getMesa().contains(card)) {
                Audios.playEfeitos(Audios.PARTIDA_LACAIO_MORREU);
                SwingUtils.runOnUIThread(() -> {
                    setBackground(new Color(0, 0, 0, 0));
                    setVisible(true);
                });
                for (int i = 0; i <= 7; i++) {
                    background = new Color(238, 199, 122, 32 * i);
                    Utils.sleep(140);
                }
                SwingUtils.runOnUIThread(() -> {
                    setVisible(false);
                });
            }
        } catch (Exception ex) {
        }
    }

    /**
     * Animação lacaio evocado
     */
    public void evocado() {
        evocado(true);
    }

    /**
     * Animação lacaio evocado
     *
     * @param flag {@code true} ou {@code false} = Thread.sleep
     */
    public void evocado(boolean flag) {
        try {
            if (card.getDono().getMesa().contains(card)) {
                if (flag) {
                    Audios.playEfeitos(Audios.PARTIDA_LACAIO_EVOCAR);
                }
                SwingUtils.runOnUIThread(() -> {
                    setVisible(true);
                });
                for (int i = 7; i >= 0; i--) {
                    background = new Color(238, 199, 122, 32 * i);
                    Utils.sleep(flag ? 140 : 0);
                }
                SwingUtils.runOnUIThread(() -> {
                    setVisible(false);
                });
            }
        } catch (Exception ex) {
        }
    }
    
    /**
     * Animação realizada quando um lacaio é a origem de um gatilho
     */
    public void disparoDeEvento() {
        try {
            if (card.getDono().getMesa().contains(card)) {
                Audios.playEfeitos(Audios.PARTIDA_LACAIO_GATILHO);
                disparoEvento = true;
                SwingUtils.runOnUIThread(() -> {
                    setVisible(true);
                });
                for (int i = 1; i <= 5; i++) {
                    ColorDisparoEvento = new Color(255, 255, 255, Math.abs(35 * i));
                    Utils.sleep(140);
                }
                for (int i = 5; i > 0; i--) {
                    ColorDisparoEvento = new Color(255, 255, 255, Math.abs(35 * i));
                    Utils.sleep(140);
                }
                disparoEvento = false;
                SwingUtils.runOnUIThread(() -> {
                    setVisible(false);
                });
                enviarPacote(Param.ANIMACAO_CARD_GATILHO, 0);
            }
        } catch (Exception ex) {
        }
    }

    /**
     * Enviar pacote da animação pela rede
     *
     * @param type tipo da animação
     * @param value valor do atributo
     */
    public void enviarPacote(String type, int value) {
        Pacote pack = new Pacote(type);
        pack.set(Param.CARD_ID_LONG, card.id_long);
        pack.set(Param.VALUE, value);
        GameCliente.send(pack);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (disparoEvento) {
            g.setColor(ColorDisparoEvento);
            g.fillOval(AbsolutesConstraints.MESA_LACAIO_LABEL_MECANICA.x,
                    AbsolutesConstraints.MESA_LACAIO_LABEL_MECANICA.y,
                    AbsolutesConstraints.MESA_LACAIO_LABEL_MECANICA.width,
                    AbsolutesConstraints.MESA_LACAIO_LABEL_MECANICA.height);
        }
    }
}