package com.limagiran.hearthstone;

import com.limagiran.hearthstone.card.control.Cards;
import com.limagiran.hearthstone.cardback.CardBack;
import com.limagiran.hearthstone.util.*;
import com.limagiran.hearthstone.colecao.view.Colecao;
import com.google.gson.Gson;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.JFXPanel;
import javax.swing.JOptionPane;
import com.limagiran.hearthstone.util.JsonUtils;
import java.util.Locale;
import java.util.stream.IntStream;

/**
 *
 * @author Vinicius Silva
 */
public class HearthStone {

    //inicia o javafx para utilizar os recursos dele
    private static final JFXPanel fx = new JFXPanel();
    public static Cards CARTAS;
    public static final List<CardBack> CARDBACK = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //AplicaNimbus.pegaNimbus();       
        Locale.setDefault(new Locale("pt", "BR"));
        validateAvailableMemory();
        try {
            //verifica se precisa atualizar o banco de dados
            if (!Update.checkDatabase()) {
                //se sim, pergunta se deseja atualizar 
                //e inicia a janela principal depois de atualizado ou encerra
                Update.askUpdateDatabase(HearthStone::start);
                return;
            }

            //verifica se tem nova versão do programa disponível
            //se sim, pergunta se deseja atualizar a nova versão
            //e depois inicia a janela principal
            Update.checkVersion(HearthStone::start, () -> Update.askUpdate(HearthStone::start));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO! Reinicie o programa!\n" + e.getMessage());
            System.exit(0);
        }
        //Desenvolvimento.main();        
    }

    /**
     * Inicia a janela principal do programa.
     */
    private static void start() {
        PopUpWorker.exe(new PopUpWorker.MySwingWorker(null, "Abrindo o programa") {

            @Override
            protected String doInBackground() throws Exception {
                Images.loadCardBack();
                CARTAS = loadCards();
                IntStream.rangeClosed(1, 40).mapToObj(CardBack::new).forEach(CARDBACK::add);
                return null;
            }

            @Override
            public void finish() {
                Colecao.main();
            }
        });
    }

    /**
     * Carrega a lista de cards do arquivo .json
     *
     * @return objeto Cards com todos os cards disponíveis
     */
    private static Cards loadCards() {
        try {
            Gson gson = JsonUtils.newGsonExcluirAnotados();
            String json = RW.readJarS("/com/limagiran/hearthstone/card/util/cards.json");
            //Verifica se o arquivo card.json não está corrompido
            if (Security.getSHA256(json).equals(Values.CARD_JSON_HASH)) {
                Cards _return = gson.fromJson(json, Cards.class);
                _return.filter();
                return _return;
            } else {
                Utils.error("ARQUIVOS CORROMPIDOS\nO programa será encerrado.");
            }
        } catch (Exception ex) {
            Utils.error("ERRO! Reinicie o programa!\n" + ex.getMessage());
        }
        System.exit(0);
        return null;
    }

    /**
     * Verifica a memória total disponível do computador e quanto de memória a
     * JVM disponibilizou para o programa, caso tenha sido menor do que o
     * necessário, reinicia o programa definindo a memória máxima para 1024mb
     */
    private static void validateAvailableMemory() {
        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
        if (memorySize < 900000000) {
            JOptionPane.showMessageDialog(null, "É necessário 1GB de memória RAM para executar este programa.");
            System.exit(0);
        }
        if (Runtime.getRuntime().maxMemory() < 900000000) {
            try {
                Runtime.getRuntime().exec("\"" + Dir.findJavaw() + "\" -jar -Xms1024m -Xmx1024m \"" + Dir.getFileEmExecucao().getAbsolutePath() + "\"").waitFor();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ERRO!\n" + e.getMessage());
            }
            System.exit(0);
        }
    }
}
