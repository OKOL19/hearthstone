package com.limagiran.hearthstone.server;

import static com.limagiran.hearthstone.HearthStone.*;
import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Collections;
import com.limagiran.hearthstone.util.JsonUtils;
import com.limagiran.hearthstone.partida.control.*;
import com.limagiran.hearthstone.partida.view.*;
import com.limagiran.hearthstone.util.*;
import java.util.Arrays;

/**
 *
 * @author Vinicius Silva
 */
public class GameCliente implements Runnable, Param {

    //public static final StringBuilder ENVIAR = new StringBuilder();
    //public static final String DELIMITADOR = "#@!";
    private static Socket server;
    private static PrintStream saida;
    private static BufferedReader entrada;
    public static PartidaView partidaView = null;
    public Heroi heroi;
    public Heroi oponente = null;
    public boolean onLine;
    private static final List<String> PACK_IN = Collections.synchronizedList(new ArrayList<String>());
    private static final List<String> PACK_OUT = Collections.synchronizedList(new ArrayList<String>());
    private static boolean blockSend;
    private final Runnable callback;

    public GameCliente(Socket server, Heroi heroi, Runnable callback) {
        this.callback = callback;
        onLine = true;
        PACK_IN.clear();
        PACK_OUT.clear();
        blockSend = true;
        GameCliente.server = server;
        this.heroi = heroi;
        partidaView = null;
        try {
            entrada = new BufferedReader(new InputStreamReader(GameCliente.server.getInputStream()));
            saida = new PrintStream(GameCliente.server.getOutputStream());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO! O programa será encerrado!");
            System.exit(0);
        }
    }

    /**
     * Encerra a conexão com o servidor
     */
    public void exit() {
        try {
            server.close();
        } catch (Exception ex) {
        } finally {
            onLine = false;
            callback.run();
        }
    }

    public static void exibirSegredoRevelado(String id, long heroi) {
        if (partidaView != null) {
            partidaView.exibirSegredoRevelado(id, heroi);
        }
    }

    public static void exibirCardComprado(String id, long heroi) {
        if (partidaView != null) {
            partidaView.exibirCardComprado(id, heroi);
        }
    }

    public static void exibirCardQueimado(String id, long heroi) {
        if (partidaView != null) {
            partidaView.exibirCardQueimado(id, heroi);
        }
    }

    public static void exibirDanoFadiga(int dano, long heroi) {
        if (partidaView != null) {
            partidaView.exibirDanoFadiga(dano, heroi);
        }
    }

    public static void exibirJustas(String heroi, String oponente) {
        if (partidaView != null) {
            partidaView.exibirJustas(heroi, oponente);
        }
    }

    public static void chanceDeAtacarInimigoErradoAnimacao() {
        if (partidaView != null) {
            partidaView.chanceDeAtacarInimigoErradoAnimacao();
        }
    }

    public static void exibirLinhaEvento(long atacante, long alvo) {
        if (partidaView != null) {
            partidaView.exibirLinhaEvento(atacante, alvo);
        }
    }

    @Override
    public void run() {
        waitingForOpponent();
    }

    private void waitingForOpponent() {
        PopUpWorker.exe(new PopUpWorker.MySwingWorker(null, "Aguardando Adversário...") {
            @Override
            protected String doInBackground() throws Exception {
                try {
                    Pacote packStart = JsonUtils.toObject(entrada.readLine());
                    sendHero();
                    Pacote packOpponent = JsonUtils.toObject(entrada.readLine());
                    oponente = getOponente(packOpponent);
                    new Thread(() -> start(packStart)).start();
                    return "t";
                } catch (Exception e) {
                    return "f" + e.getMessage();
                }
            }

            @Override
            public void finish() {
                if (!isOk()) {
                    showResult();
                    exit();
                }
            }
        }, true);
    }

    private void start(Pacote packStart) {
        try {
            startThreadSend();
            startThreadReceive();
            //capturando valores do Pacote inicial
            int playerInicio = packStart.getParamInt(PLAYER_INICIO);
            int player = packStart.getParamInt(PLAYER);
            GamePlay.INSTANCE.setVidaTotalHeroi(packStart.getParamInt(VIDA_TOTAL_HEROI));
            GamePlay.INSTANCE.setCartasNaMao(packStart.getParamInt(CARTAS_NA_MAO));
            GamePlay.INSTANCE.setShieldInicial(packStart.getParamInt(SHIELD_INICIAL));
            Arrays.asList(heroi, oponente).forEach(h -> {
                h.setShield(GamePlay.INSTANCE.getShieldInicial(), false);
                h.setVidaTotal(GamePlay.INSTANCE.getVidaTotalHeroi(), false);
            });
            //enviar as informações do herói
            SwingUtils.runOnUIThread(() -> {
                Partida partida = new Partida(player, playerInicio, heroi, oponente);
                partidaView = PartidaView.main(partida, playerInicio == player, this);
                partidaView.start();
            });
            partidaView.PARTIDA.getOponente().setDeck(getDeck(JsonUtils.toObject(entrada.readLine())));
            partidaView.setMuligado(true);
            while (onLine) {
                String pacotes = entrada.readLine();
                PACK_IN.add(pacotes);
            }
        } catch (Exception ex) {
            if (partidaView.PARTIDA.getVencedor() == -1) {
                JOptionPane.showMessageDialog(null, "Seu oponente saiu!");
            }
            exit();
        } finally {
            SwingUtils.runOnUIThread(() -> {
                Muligar.close();
                EscolherCard.close();
                if (partidaView != null) {
                    partidaView.disposeAllPopUps();
                    partidaView.dispose();
                }
            });
        }
    }

    public static void jogoEncerrado() {
        if (partidaView != null) {
            partidaView.jogoEncerrado();
        }
    }

    public static boolean isPartidaEncerrada() {
        return ((partidaView == null) || partidaView.isPartidaEncerrada());
    }

    public static void oponenteDesistiu() {
        if (partidaView != null) {
            partidaView.oponenteDesistiu();
        }
    }

    public static void addHistorico(String msg, boolean enviar) {
        if (partidaView != null) {
            partidaView.addHistorico(msg);
            if (enviar) {
                partidaView.PARTIDA.addHistorico(msg);
            }
        }
    }

    public static void showPopUp(String msg) {
        if (partidaView != null) {
            partidaView.showPopUp(msg);
        }
    }

    /**
     * Adiciona um Pacote na fila de envio
     *
     * @param pack objeto Pacote
     */
    public static void send(Pacote pack) {
        if ((partidaView == null) || partidaView.PARTIDA.isVezHeroi()
                || pack.TIPO.equals(PARTIDA_SET_VEZ_DO_PLAYER)) {
            PACK_OUT.add(JsonUtils.toJSon(pack));
        }
    }

    /**
     * Envia um Pacote ao servidor instantaneamente, sem deixá-lo na fila
     *
     * @param pack objeto Pacote
     */
    public static void sendNow(Pacote pack) {
        if (server != null) {
            try {
                saida.flush();
                saida.println(JsonUtils.toJSon(pack));
                Utils.sleep(100);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Captura as informações do oponente
     *
     * @param pack Pacote recebido do servidor
     * @return Objeto Heroi do oponente
     */
    private Heroi getOponente(Pacote pack) {
        String nome = pack.getParamString(HEROI_NOME);
        String type = pack.getParamString(HEROI_CLASSE);
        int cardBack = pack.getParamInt(CARD_BACK);
        List<Card> deckOponente = getDeck(pack);
        return new Heroi(nome, OPONENTE, type, deckOponente, CARDBACK.get(cardBack - 1));
    }

    /**
     * Envia o deck do herói ao servidor
     *
     * @param deck Lista de cards do deck
     */
    public static void sendDeck(List<Card> deck) {
        Pacote pack = new Pacote(CARD_DECK);
        for (int index = 0; index < deck.size(); index++) {
            pack.set(CARD_DECK + "_" + index, deck.get(index).getId());
            pack.set(CARD_ID_LONG + "_" + index, deck.get(index).id_long);
        }
        if (server != null) {
            saida.flush();
            saida.println(JsonUtils.toJSon(pack));
        }
    }

    /**
     * Captura o deck do oponente com o Pacote recebido por parâmetro
     *
     * @param pack Pacote recebido do servidor
     * @return Lista de cards do deck
     */
    private List<Card> getDeck(Pacote pack) {
        List<Card> deckOponente = new ArrayList<>();
        for (int index = 0; index <= 30; index++) {
            String id = pack.getParamString(CARD_DECK + "_" + index);
            long id_long = pack.getParamLong(CARD_ID_LONG + "_" + index);
            if (!id.isEmpty()) {
                Card c = CARTAS.createCard(id);
                c.id_long = id_long;
                c.setAura();
                deckOponente.add(c);
            }
        }
        return deckOponente;
    }

    /**
     * Envia para o servidor as informações do herói
     */
    private void sendHero() {
        Pacote pack = new Pacote(Long.toString(OPONENTE));
        for (int index = 0; index < heroi.getDeck().size(); index++) {
            pack.set(CARD_DECK + "_" + index, heroi.getDeck().get(index).getId());
            pack.set(CARD_ID_LONG + "_" + index, heroi.getDeck().get(index).id_long);
        }
        pack.set(HEROI_NOME, heroi.getNome());
        pack.set(HEROI_CLASSE, heroi.getType());
        pack.set(CARD_BACK, Integer.toString(heroi.getCardback().getId()));
        if (server != null) {
            saida.flush();
            saida.println(JsonUtils.toJSon(pack));
        }
    }

    /**
     * Adiciona um Pacote na fila para envio
     *
     * @param type Tipo do Pacote
     * @param object valor da variável
     */
    public static void send(String type, Object object) {
        Pacote pack = new Pacote(type);
        pack.set(VALUE, object.toString());
        send(pack);
    }

    private void startThreadReceive() {
        new Thread(() -> {
            String buff;
            while (onLine) {
                if (!PACK_IN.isEmpty()) {
                    try {
                        buff = PACK_IN.remove(0);
                        if (!buff.isEmpty()) {
                            Sincronizar.sincronizar(partidaView.PARTIDA, JsonUtils.toObject(buff));
                        }
                    } catch (Exception e) {
                        oponenteDesistiu();
                        exit();
                    }
                } else {
                    Utils.sleep(5);
                }
            }
        }).start();
    }

    private void startThreadSend() {
        PACK_OUT.clear();
        new Thread(() -> {
            while (onLine) {
                try {
                    if (blockSend && !PACK_OUT.isEmpty()) {
                        saida.flush();
                        saida.println(PACK_OUT.remove(0));
                    } else {
                        Utils.sleep(10);
                    }
                } catch (Exception e) {
                    oponenteDesistiu();
                }
            }
        }).start();
    }

    public static void clearPacks() {
        PACK_OUT.clear();
    }

    public static void setBlockSend(boolean blockSend) {
        GameCliente.blockSend = blockSend;
    }

}
