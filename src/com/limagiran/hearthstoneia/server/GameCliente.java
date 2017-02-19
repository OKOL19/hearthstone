package com.limagiran.hearthstoneia.server;

import static com.limagiran.hearthstone.HearthStone.CARTAS;
import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstoneia.partida.control.*;
import com.limagiran.hearthstoneia.partida.view.PartidaView;
import com.limagiran.hearthstone.util.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author Vinicius Silva
 */
public class GameCliente implements Runnable {

    private static Socket server;
    private static PrintStream saida;
    private static BufferedReader entrada;
    public static PartidaView partidaView = null;
    public Heroi heroi;
    public Heroi oponente = null;
    public static boolean onLine = false;
    private static final List<String> PACK_IN = Collections.synchronizedList(new ArrayList<String>());
    private static final List<String> PACK_OUT = Collections.synchronizedList(new ArrayList<String>());
    private static boolean podeEnviar;
    private static boolean intArtProcessando;

    public GameCliente(Socket server, Heroi heroi) {
        onLine = true;
        PACK_IN.clear();
        PACK_OUT.clear();
        podeEnviar = true;
        intArtProcessando = false;
        GameCliente.server = server;
        this.heroi = heroi;
        partidaView = null;
        try {
            entrada = new BufferedReader(new InputStreamReader(GameCliente.server.getInputStream()));
            saida = new PrintStream(GameCliente.server.getOutputStream());
        } catch (Exception ex) {
        }
    }

    /**
     * Encerra a conexão com o servidor
     */
    public static void close() {
        try {
            server.close();
        } catch (Exception ex) {
        } finally {
            onLine = false;
        }
    }

    @Override
    public void run() {
        try {
            Pacote packStart = JsonUtils.toObject(entrada.readLine());
            enviarHeroi();
            Pacote packOpponent = JsonUtils.toObject(entrada.readLine());
            oponente = getOponente(packOpponent);
            new Thread(() -> start(packStart)).start();
        } catch (Exception e) {
            close();
        }
    }

    public void start(Pacote pack) {
        try {
            iniciarThreadEnviarPacotes();
            iniciarThreadReceberPacotes();

            //capturando valores do pacote inicial
            int playerInicio = pack.getParamInt(Param.PLAYER_INICIO);
            int player = pack.getParamInt(Param.PLAYER);
            GamePlay.INSTANCE.setVidaTotalHeroi(pack.getParamInt(Param.VIDA_TOTAL_HEROI));
            GamePlay.INSTANCE.setCartasNaMao(pack.getParamInt(Param.CARTAS_NA_MAO));
            GamePlay.INSTANCE.setShieldInicial(pack.getParamInt(Param.SHIELD_INICIAL));
            Arrays.asList(heroi, oponente).forEach(h -> {
                h.setShield(GamePlay.INSTANCE.getShieldInicial(), false);
                h.setVidaTotal(GamePlay.INSTANCE.getVidaTotalHeroi(), false);
            });
            //enviar as informações do herói
            enviarHeroi();
            //inicia a partidaView            
            (partidaView = PartidaView.main(new Partida(player, playerInicio,
                    heroi, oponente), playerInicio == player)).start();
            //aguarda o deck muligado do oponente ser recebido pela rede
            partidaView.PARTIDA.getOponente().setDeck(getDeck(JsonUtils.toObject(entrada.readLine())));
            partidaView.setMuligado(true);
            while (onLine) {
                String pacotes = entrada.readLine();
                PACK_IN.add(pacotes);
            }
        } catch (Exception ex) {
            close();
            onLine = false;
        }
    }

    public static void exibirCardQueimado(String id, long heroi) {
        Pacote pacote = new Pacote(Param.ANIMACAO_CARD_QUEIMADO);
        pacote.set(Param.CARD_ID, id);
        pacote.set(Param.VALUE, heroi != Param.HEROI ? Param.HEROI : Param.OPONENTE);
        GameCliente.enviar(pacote);
    }

    public static void exibirCardComprado(String id, long heroi) {
        Pacote pacote = new Pacote(Param.ANIMACAO_CARD_COMPRADO);
        pacote.set(Param.CARD_ID, id);
        pacote.set(Param.VALUE, heroi != Param.HEROI ? Param.HEROI : Param.OPONENTE);
        GameCliente.enviar(pacote);
    }

    public static void exibirDanoFadiga(int fadiga, long heroi) {
        Pacote pacote = new Pacote(Param.ANIMACAO_FADIGA);
        pacote.set(Param.ALVO, heroi != Param.HEROI ? Param.HEROI : Param.OPONENTE);
        pacote.set(Param.VALUE, fadiga);
        GameCliente.enviar(pacote);
    }

    public static void exibirJustas(String idHeroi, String idOponente) {
        Pacote pacote = new Pacote(Param.ANIMACAO_CARD_JUSTAS);
        pacote.set(Param.CARD_JUSTAS_HEROI, idOponente);
        pacote.set(Param.CARD_JUSTAS_OPONENTE, idHeroi);
        GameCliente.enviar(pacote);
    }

    public static void exibirSegredoRevelado(String id, long heroi) {
        Pacote pacote = new Pacote(Param.ANIMACAO_CARD_SEGREDO_REVELADO);
        pacote.set(Param.CARD_ID, id);
        pacote.set(Param.VALUE, heroi == Param.HEROI ? Param.OPONENTE : Param.HEROI);
        GameCliente.enviar(pacote);
    }

    public static void jogoEncerrado() {
        if (partidaView != null) {
            partidaView.jogoEncerrado();
        }
    }

    public static boolean isPartidaEncerrada() {
        return partidaView == null || partidaView.isPartidaEncerrada();
    }

    public static void oponenteDesistiu() {
        if (partidaView != null) {
            partidaView.oponenteDesistiu();
        }
    }

    /**
     * Adiciona um pacote na fila de envio
     *
     * @param pacote objeto Pacote
     */
    public static void enviar(Pacote pacote) {
        if (!intArtProcessando && (partidaView == null || partidaView.PARTIDA.isVezHeroi()
                || pacote.TIPO.equals(Param.PARTIDA_SET_VEZ_DO_PLAYER))) {
            PACK_OUT.add(JsonUtils.toJSon(pacote));
        }
    }

    /**
     * Envia um pacote ao servidor instantaneamente, sem deixá-lo na fila
     *
     * @param pacote objeto Pacote
     */
    public static void enviarAgora(Pacote pacote) {
        if (server != null && !intArtProcessando) {
            try {
                saida.flush();
                saida.println(JsonUtils.toJSon(pacote));
                Thread.sleep(100);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Captura as informações do oponente
     *
     * @param pacote Pacote recebido do servidor
     * @return Objeto Heroi do oponente
     */
    private Heroi getOponente(Pacote pacote) {
        String nome = pacote.getParamString(Param.HEROI_NOME);
        String type = pacote.getParamString(Param.HEROI_CLASSE);
        List<Card> deckOponente = getDeck(pacote);
        return new Heroi(nome, Param.OPONENTE, type, deckOponente);
    }

    /**
     * Envia o deck do herói ao servidor
     *
     * @param deck Lista de cards do deck
     */
    public static void enviarDeck(List<Card> deck) {
        Pacote pacote = new Pacote(Param.CARD_DECK);
        for (int index = 0; index < deck.size(); index++) {
            pacote.set(Param.CARD_DECK + "_" + index, deck.get(index).getId());
            pacote.set(Param.CARD_ID_LONG + "_" + index, deck.get(index).id_long);
        }
        if (server != null) {
            saida.flush();
            saida.println(JsonUtils.toJSon(pacote));
        }
    }

    /**
     * Captura o deck do oponente com o pacote recebido por parâmetro
     *
     * @param pacote Pacote recebido do servidor
     * @return Lista de cards do deck
     */
    private List<Card> getDeck(Pacote pacote) {
        List<Card> deckOponente = new ArrayList<>();
        for (int index = 0; index <= 30; index++) {
            String id = pacote.getParamString(Param.CARD_DECK + "_" + index);
            long id_long = pacote.getParamLong(Param.CARD_ID_LONG + "_" + index);
            if (!id.isEmpty()) {
                Card c = CARTAS.createCardIA(id);
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
    private void enviarHeroi() {
        Pacote pacote = new Pacote(Long.toString(Param.OPONENTE));
        for (int index = 0; index < heroi.getDeck().size(); index++) {
            pacote.set(Param.CARD_DECK + "_" + index, heroi.getDeck().get(index).getId());
            pacote.set(Param.CARD_ID_LONG + "_" + index, heroi.getDeck().get(index).id_long);
        }
        pacote.set(Param.HEROI_NOME, heroi.getNome());
        pacote.set(Param.HEROI_CLASSE, heroi.getType());
        pacote.set(Param.CARD_BACK, 19);
        if (server != null) {
            saida.flush();
            saida.println(JsonUtils.toJSon(pacote));
        }
    }

    /**
     * Adiciona um pacote na fila para envio
     *
     * @param tipo Tipo do pacote
     * @param object valor da variável
     */
    public static void enviar(String tipo, Object object) {
        Pacote pacote = new Pacote(tipo);
        pacote.set(Param.VALUE, object.toString());
        enviar(pacote);
    }

    private void iniciarThreadReceberPacotes() {
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
                        onLine = false;
                    }
                } else {
                    Utils.sleep(5);
                }
            }
        }).start();
    }
    
    private void iniciarThreadEnviarPacotes() {
        PACK_OUT.clear();
        new Thread(() -> {
            while (onLine) {
                try {
                    if (podeEnviar && !PACK_OUT.isEmpty()) {
                        saida.flush();
                        saida.println(PACK_OUT.remove(0));
                    } else {
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    oponenteDesistiu();
                }
            }
        }).start();
    }

    public static void clearPacotes() {
        PACK_OUT.clear();
    }

    public static void setPodeEnviar(boolean podeEnviar) {
        GameCliente.podeEnviar = podeEnviar;
    }

    public static void setIntArtProcessando(boolean intArtProcessando) {
        GameCliente.intArtProcessando = intArtProcessando;
    }

    public static boolean isIntArtProcessando() {
        return intArtProcessando;
    }

    public static void addHistorico(String string, boolean enviar) {
        if (partidaView != null && partidaView.PARTIDA != null && enviar) {
            partidaView.PARTIDA.addHistorico(string);
        }
    }
}
