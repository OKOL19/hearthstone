package com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import com.limagiran.hearthstone.partida.view.PartidaView;
import com.limagiran.hearthstone.server.GameCliente;
import com.limagiran.hearthstone.util.*;

/**
 *
 * @author Vinicius Silva
 */
public final class Animacao extends JLabel {

    private static final int VIDA = 0;
    private static final int ESCUDO = 1;
    private static final int ATAQUE = 2;

    private static final Dimension SIZE = new Dimension(130, 150);
    private boolean sleep = true;
    private final JLabel text = getJLabelDefault();
    private final JLabel icon = getJLabelDefault();

    private final Heroi heroi;

    public Animacao(Heroi heroi) {
        super("", SwingConstants.CENTER);
        this.heroi = heroi;
        init();
    }

    @Override
    public String toString() {
        return heroi.getToString();
    }

    /**
     * Configurar o painel
     */
    private void init() {
        text.setLayout(new AbsoluteLayout());
        add(text);
        add(icon);
        setVisible(false);
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
                return heroi.getToString();
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
        update();
        if (sleep) {
            Utils.sleep(time);
            clear();
        }
        sleep = true;
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
    public void vida(int value, long time) {
        vida(value, time, true);
    }

    /**
     * Animação de cura ou dano
     *
     * @param value valor da cura ou do dano (positivo para cura, negativo para
     * dano)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     */
    public void vida(int value, long time, boolean sleep) {
        this.sleep = sleep;
        clear();
        Color color = value > 0 ? Color.GREEN : Color.RED;
        String string = ((value > 0 ? "+" : "") + value);
        TextoComBorda t = TextoComBorda.create(string, 40, color, toString());
        int x = text.getWidth() / 2 - t.getWidth() / 2;
        int y = text.getHeight() / 2;
        text.add(t, new AbsoluteConstraints(x - 5, y - 30, t.getWidth(), t.getHeight()));
        text.setVisible(true);
        if (value <= 0) {
            icon.setIcon(Images.ICON_DANO_HEROI);
            icon.setVisible(true);
        }
        enviarPacote(VIDA, value);
        Audios.playEfeitos(value > 0 ? Audios.PARTIDA_CURAR : Audios.PARTIDA_DANO_GERADO);
        show(time);
    }

    /**
     * Animação de escudo alterado
     *
     * @param value valor alterado do escudo (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void escudo(int value, long time) {
        escudo(value, time, true);
    }

    /**
     * Animação de escudo alterado
     *
     * @param value valor alterado do escudo (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     */
    public void escudo(int value, long time, boolean sleep) {
        this.sleep = sleep;
        if (value > 0) {
            clear();
            TextoComBorda t = TextoComBorda.create("+" + value, 40, Color.GRAY.brighter(), toString());
            int x = text.getWidth() / 2 - t.getWidth() / 2;
            int y = text.getHeight() / 2;
            text.add(t, new AbsoluteConstraints(x - 5, y - 30, t.getWidth(), t.getHeight()));
            text.setVisible(true);
            icon.setIcon(Images.HEROI_ESCUDO);
            icon.setVisible(true);
            enviarPacote(ESCUDO, value);
            show(time);
        }
    }

    /**
     * Animação de ataque alterado
     *
     * @param value valor alterado do ataque (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void ataque(int value, long time) {
        ataque(value, time, true);
    }

    /**
     * Animação de ataque alterado
     *
     * @param value valor alterado do ataque (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     */
    public void ataque(int value, long time, boolean sleep) {
        this.sleep = sleep;
        if (value != 0) {
            clear();
            TextoComBorda t = TextoComBorda.create("+" + value, 36, Color.GRAY.brighter(), toString());
            int x = text.getWidth() / 2 - t.getWidth() / 2;
            int y = text.getHeight() / 2;
            text.add(t, new AbsoluteConstraints(x, y - 22, t.getWidth(), t.getHeight()));
            text.setVisible(true);
            icon.setIcon(Images.ICON_ATAQUE);
            icon.setVisible(true);
            enviarPacote(ATAQUE, value);
            show(time);
        }
    }

    /**
     * Enviar pacote da animação pela rede
     *
     * @param type tipo da animação
     * @param value valor do atributo
     */
    public void enviarPacote(int type, int value) {
        switch (type) {
            case VIDA:
                GameCliente.send(heroi.getHeroi() != Param.HEROI
                        ? Param.ANIMACAO_HEROI_VIDA
                        : Param.ANIMACAO_OPONENTE_VIDA, value);
                break;
            case ESCUDO:
                GameCliente.send(heroi.getHeroi() != Param.HEROI
                        ? Param.ANIMACAO_HEROI_ESCUDO
                        : Param.ANIMACAO_OPONENTE_ESCUDO, value);
                break;
            case ATAQUE:
                GameCliente.send(heroi.getHeroi() != Param.HEROI
                        ? Param.ANIMACAO_HEROI_ATAQUE
                        : Param.ANIMACAO_OPONENTE_ATAQUE, value);
                break;
        }
    }

}