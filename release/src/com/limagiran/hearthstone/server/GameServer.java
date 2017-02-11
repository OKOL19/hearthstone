package com.limagiran.hearthstone.server;

import com.limagiran.hearthstone.util.GamePlay;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Vinicius Silva
 */
public class GameServer implements Runnable {

    private Socket playerOne;
    private Socket playerTwo;
    private BufferedReader entradaPlay1;
    private BufferedReader entradaPlay2;
    private PrintStream saidaPlay1;
    private PrintStream saidaPlay2;

    public GameServer(Socket playerOne, Socket playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        try {
            entradaPlay1 = new BufferedReader(new InputStreamReader(this.playerOne.getInputStream()));
            entradaPlay2 = new BufferedReader(new InputStreamReader(this.playerTwo.getInputStream()));
            saidaPlay1 = new PrintStream(this.playerOne.getOutputStream());
            saidaPlay2 = new PrintStream(this.playerTwo.getOutputStream());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO! O programa será encerrado!");
            System.exit(0);
        }
    }

    @Override
    public void run() {
        int random = random(2) % 2;
        final Pack pacote = new Pack(ServerConstants.PLAYER_INICIO);
        pacote.set(ServerConstants.PLAYER_INICIO, random);
        pacote.set(ServerConstants.PLAYER, ServerConstants.PLAYER_ONE);
        pacote.set(ServerConstants.VIDA_TOTAL_HEROI, GamePlay.INSTANCE.getVidaTotalHeroi());
        pacote.set(ServerConstants.CARTAS_NA_MAO, GamePlay.INSTANCE.getCartasNaMao());
        pacote.set(ServerConstants.SHIELD_INICIAL, GamePlay.INSTANCE.getShieldInicial());
        String json = pacote.getJSon();
        pacote.set(ServerConstants.PLAYER, ServerConstants.PLAYER_TWO);
        runThread(playerOne, entradaPlay1, saidaPlay2, json);
        runThread(playerTwo, entradaPlay2, saidaPlay1, pacote.getJSon());
    }

    /**
     * Gera um número aleatório
     *
     * @param limite valor máximo a ser retornado
     * @return um número aleatório entre 1 e o limite informado. Retorna -1 para
     * valores menores do que 1.
     */
    public static int random(int limite) {
        return limite < 1 ? -1 : (int) ((Math.random() * 100000) % (limite)) + 1;
    }

    private void runThread(Socket player, BufferedReader entrada, PrintStream saida, String json) {
        new Thread(() -> {
            try {
                saida.flush();
                saida.println(json);
                while (player.isConnected()) {
                    String pack = entrada.readLine();
                    saida.flush();
                    saida.println(pack);
                }
            } catch (Exception ex) {
                saida.flush();
                saida.println(new Pack(ServerConstants.OPONENTE_DESISTIU).getJSon());
                close(entrada);
                close(saida);
                close(player);
                close(player.equals(playerOne) ? playerTwo : playerOne);
            }
        }).start();
    }

    private void close(Object object) {
        try {
            if (object instanceof Socket) {
                ((Socket) object).close();
            } else if (object instanceof BufferedReader) {
                ((BufferedReader) object).close();
            } else if (object instanceof PrintStream) {
                ((PrintStream) object).close();
            }
        } catch (Exception e) {
        }
    }
}
