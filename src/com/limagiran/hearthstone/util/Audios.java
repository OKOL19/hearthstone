package com.limagiran.hearthstone.util;

import static com.limagiran.hearthstone.settings.Settings.EnumSettings.*;
import java.io.File;
import javafx.scene.media.*;

/**
 *
 * @author Vinicius
 */
public class Audios {

    private static MediaPlayer musicaFundo;
    private static MediaPlayer efeitos;

    public static final Media MUSICA_FUNDO_COLECAO = getMediaFile("bin/a/partida_colecao.mp3");
    public static final Media MUSICA_FUNDO_PARTIDA = getMediaFile("bin/a/partida_duelo.mp3");
    public static final Media COLECAO_ADD_CARD = getMediaResource("colecao/audio/addCard.mp3");
    public static final Media COLECAO_AVANCAR_PAGINA = getMediaResource("colecao/audio/avancarPagina.mp3");
    public static final Media COLECAO_ERRO_LIMITE = getMediaResource("colecao/audio/erroLimite.mp3");
    public static final Media COLECAO_VOLTAR_PAGINA = getMediaResource("colecao/audio/voltarPagina.mp3");
    public static final Media COLECAO_REMOVER_CARD = getMediaResource("colecao/audio/removerCard.mp3");
    public static final Media COLECAO_REMOVER_DECK = getMediaResource("colecao/audio/removerDeck.mp3");
    public static final Media PARTIDA_ARMA_ATIVAR = getMediaResource("partida/audio/ativarArma.mp3");
    public static final Media PARTIDA_ARMA_DESATIVAR = getMediaResource("partida/audio/desativarArma.mp3");
    public static final Media PARTIDA_ARMA_DESTRUIR = getMediaResource("partida/audio/destruirArma.mp3");
    public static final Media PARTIDA_ARMA_EQUIPAR = getMediaResource("partida/audio/equiparArma.mp3");
    public static final Media PARTIDA_ARMA_PERDER_DURABILIDADE = getMediaResource("partida/audio/removerDurabilidade.mp3");
    public static final Media PARTIDA_CARD_JOGAR = getMediaResource("partida/audio/jogarCard.mp3");
    public static final Media PARTIDA_COMPRAR_CARD = getMediaResource("partida/audio/comprarCard.mp3");
    public static final Media PARTIDA_CURAR = getMediaResource("partida/audio/curar.mp3");
    public static final Media PARTIDA_DANO_GERADO = getMediaResource("partida/audio/dano.mp3");
    public static final Media PARTIDA_ENCERRADA_PERDEU = getMediaResource("partida/audio/partidaEncerradaLose.mp3");
    public static final Media PARTIDA_ENCERRADA_VENCEU = getMediaResource("partida/audio/partidaEncerradaWin.mp3");
    public static final Media PARTIDA_ESCOLHA_UM = getMediaResource("partida/audio/EscolhaUm.mp3");
    public static final Media PARTIDA_HEROI_ESCUDO_GANHAR = getMediaResource("partida/audio/ganharEscudo.mp3");
    public static final Media PARTIDA_HEROI_ESCUDO_PERDER = getMediaResource("partida/audio/perderEscudo.mp3");
    public static final Media PARTIDA_HEROI_MORREU_1 = getMediaResource("partida/audio/heroiMorreu1.mp3");
    public static final Media PARTIDA_HEROI_MORREU_2 = getMediaResource("partida/audio/heroiMorreu2.mp3");
    public static final Media PARTIDA_LACAIO_GATILHO = getMediaResource("partida/audio/gatilhoEvento.mp3");
    public static final Media PARTIDA_LACAIO_EVOCAR = getMediaResource("partida/audio/lacaioEvocado.mp3");
    public static final Media PARTIDA_LACAIO_MORREU = getMediaResource("partida/audio/lacaioMorreu.mp3");
    public static final Media PARTIDA_ENCERRAR_TURNO = getMediaResource("partida/audio/encerrarTurno.mp3");
    public static final Media PARTIDA_PODER_HEROICO_FECHADO = getMediaResource("partida/audio/fecharPoderHeroico.mp3");
    public static final Media PARTIDA_SEGREDO_ATIVAR = getMediaResource("partida/audio/ativarSegredo.mp3");
    public static final Media PARTIDA_SEGREDO_REVELAR = getMediaResource("partida/audio/revelarSegredo.mp3");
    public static final Media PARTIDA_SUA_VEZ = getMediaResource("partida/audio/suaVez.mp3");

    public static void playEfeitos(Media audio) {
        try {
            if (VOLUME_EFFECT.getDouble() > 0.1) {
                stopEfeitos();
                efeitos = new MediaPlayer(audio);
                efeitos.setVolume(VOLUME_EFFECT.getDouble());
                efeitos.play();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //ignorar
        }
    }

    public static void playMusicaFundo(Media audio) {
        try {
            stopMusicaFundo();
            musicaFundo = new MediaPlayer(audio);
            musicaFundo.setCycleCount(100);
            musicaFundo.setVolume(VOLUME_MUSIC.getDouble());
            musicaFundo.play();
        } catch (Exception ex) {
            //ignorar
        }
    }

    public static void stopMusicaFundo() {
        try {
            musicaFundo.stop();
            musicaFundo.dispose();
            musicaFundo = null;
        } catch (Exception ex) {
            //ignorar
        }
    }

    public static void stopEfeitos() {
        try {
            efeitos.stop();
            efeitos.dispose();
            efeitos = null;
        } catch (Exception ex) {
            //ignorar
        }
    }

    public static void setVolumeMusicaFundo(double volume) {
        try {
            musicaFundo.setVolume(volume);
        } catch (Exception e) {
            //ignorar
        }
    }
    
    private static Media getMediaResource(String audio) {
        try {
            return new Media(Audios.class.getResource("/com/limagiran/hearthstone/" + audio).toURI().toString());
        } catch (Exception ex) {
            return null;
        }
    }

    private static Media getMediaFile(String audio) {
        try {
            return new Media(new File(audio).toURI().toString());
        } catch (Exception ex) {
            return null;
        }
    }
}