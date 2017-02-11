package com.limagiran.hearthstone;

import static java.io.File.separator;
import static com.limagiran.hearthstone.util.Values.*;
import com.limagiran.hearthstone.util.*;
import java.io.*;
import com.limagiran.hearthstone.util.Zip;
import java.awt.Desktop;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 *
 * @author Vinicius
 */
public class Update {

    public static final String NAME = "Hearthstone LimaGiran - OpenSource";
    public static final String VERSION = "1.0.0";
    public static final String URL = "https://github.com/limagiran/hearthstone/raw/master/";
    public static final String URL_VERSION = URL + "release/currentVersion.txt";
    public static final String URL_SOFTWARE = URL + "release/HearthStone.jar";
    public static final String URL_SOFTWARE_HASH = URL + "release/hashCurrentVersion.txt";
    public static final String URL_BIN_ZIP = "https://dl.dropboxusercontent.com/s/sqppq9ameh4q0b5/bin.zip";
    public static final File FILE_BIN_ZIP = new File("bin.zip").getAbsoluteFile();
    private static final double TOTAL_ITEMS_BIN = (CARD_ID.size() * 3) + AUDIOS_LIST.size() + 40;

    /**
     * Consulta no servidor a versão mais atual do programa
     *
     * @param consumer versão atual ou {@code null} para erro.
     */
    public static void getCurrentVersion(Consumer<String> consumer) {
        PopUpWorker.exe(new PopUpWorker.MySwingWorker(null, "Verificando versão...") {
            @Override
            protected String doInBackground() throws Exception {
                Download d = Download.download(URL_VERSION);
                return (d.isOk() ? ("t" + d.getText()) : ("f" + d.getError().getMessage()));
            }

            @Override
            public void finish() {
                if (isOk()) {
                    consumer.accept(getResult());
                } else {
                    consumer.accept(null);
                }
            }

            @Override
            public void canceled() {
                consumer.accept(null);
            }
        }, true);
    }

    /**
     * Verifica todos os Cards necessários e faz o download dos que estão
     * faltando na base de dados
     *
     * @param ifTrue run if {@code true}
     */
    public static void updateDatabase(Runnable ifTrue) {
        PopUpWorker.exe(new PopUpWorker.MySwingWorker(null, "Baixando banco de dados...") {
            @Override
            protected String doInBackground() throws Exception {
                createIfNotExists("bin" + separator);
                Arrays.asList("b", "m", "o", "s", "a").forEach(c -> createIfNotExists("bin" + separator + c + separator));
                double index = 0;
                for (String id : CARD_ID) {
                    downloadCard('s', id);
                    downloadCard('m', id);
                    downloadCard('o', id);
                    setProgress((int) (Utils.percentage((index += 3), TOTAL_ITEMS_BIN) * 100.0));
                }
                for (int i = 1; i <= 40; i++) {
                    downloadCardBack(i);
                    setProgress((int) (Utils.percentage(++index, TOTAL_ITEMS_BIN) * 100.0));
                }
                for (String audio : AUDIOS_LIST) {
                    downloadAudio(audio);
                    setProgress((int) (Utils.percentage(++index, TOTAL_ITEMS_BIN) * 100.0));
                }
                return "t";
            }

            @Override
            public void finish() {
                if (isOk()) {
                    ifTrue.run();
                }
            }

            @Override
            public void canceled() {
                System.exit(0);
            }
        }, true);
    }

    /**
     * Verifica se o card existe na base de dados. Realiza o download dos cards
     * da base de dados, caso ele não exista
     *
     * @param type tamanho do Card s = small, m = medium, o = original
     * @param id id do Card
     */
    private synchronized static void downloadCard(char type, String id) {
        File fileCard = new File("bin" + separator + type + separator + id + ".hsi");
        if (fileCard.exists()) {
            return;
        }
        final String url = URL + "bin/" + type + "/" + fileCard.getName();
        while (!Download.download(url, fileCard, null, null).isOk()) {
            Utils.sleep(100);
        }
    }

    /**
     * Realiza o download dos versos de card disponíveis no jogo
     *
     * @param id
     */
    private static void downloadCardBack(int id) {
        File fileCardBack = new File("bin" + separator + "b" + separator + "cardback_" + id + ".hsi");
        if (fileCardBack.exists()) {
            return;
        }
        final String url = URL + "bin/b/" + fileCardBack.getName();
        while (!Download.download(url, fileCardBack, null, null).isOk()) {
            Utils.sleep(100);
        }
    }

    /**
     * Realiza o download dos áudios do programa
     */
    private static void downloadAudio(String nome) {
        File fileAudio = new File("bin" + separator + "a" + separator + nome + ".mp3");
        if (fileAudio.exists()) {
            return;
        }
        final String url = URL + "bin/a/" + fileAudio.getName();
        while (!Download.download(url, fileAudio, null, null).isOk()) {
            Utils.sleep(100);
        }
    }

    /**
     * Cria o diretório, caso não exista
     *
     * @param dir nome da pasta do caminho relativo onde o programa está
     */
    private static void createIfNotExists(String dir) {
        File bin = new File(dir);
        if (!bin.exists()) {
            bin.mkdir();
        }
    }

    /**
     * Verifica se todos os cards necessários estão baixados
     *
     * @return {@code true} or {@code false}.
     */
    public static boolean checkDatabase() {
        final String pathBin = "bin" + separator + "?c" + separator;
        final String path = pathBin + "?id.hsi";
        //validar card
        if (!CARD_ID.stream()
                .map(id -> path.replace("?id", id))
                .noneMatch(p -> (!new File(p.replace("?c", "s")).exists()
                        || !new File(p.replace("?c", "m")).exists()
                        || !new File(p.replace("?c", "o")).exists()))) {
            return false;
        }
        //validar versos de card
        for (int i = 1; i <= 40; i++) {
            if (!new File(pathBin.replace("?c", "b") + "cardback_" + i + ".hsi").exists()) {
                return false;
            }
        }
        //validar áudio
        return AUDIOS_LIST.stream()
                .map(a -> new File(pathBin.replace("?c", "a") + a + ".mp3"))
                .noneMatch(f -> (!f.exists()));
    }

    /**
     * Verifica se a versão do programa é a mais recente
     *
     * @param ifTrue ação executada se for {@code true}
     * @param ifFalse ação executada se for {@code false}
     */
    public static void checkVersion(Runnable ifTrue, Runnable ifFalse) {
        getCurrentVersion(v -> {
            if ((v == null) || !v.equals(VERSION)) {
                ifFalse.run();
            } else {
                ifTrue.run();
            }
        });
    }

    /**
     * Exibe uma caixa de diálogo para atualizar a versão do programa
     *
     * @param ifFalse
     */
    public static void askUpdate(Runnable ifFalse) {
        if (Utils.confirm("Versão do programa desatualizada, deseja atualizar?", "Atualizar")) {
            PopUpWorker.exe(new PopUpWorker.MySwingWorker(null) {
                private File fileDownload;

                @Override
                protected String doInBackground() throws Exception {
                    String hash = Download.download(URL_SOFTWARE_HASH).getText();
                    String currentVersion = Download.download(URL_VERSION).getText();
                    fileDownload = new File("Hearthstone Java (" + currentVersion + ").jar").getAbsoluteFile();
                    Download d = Download.download(URL_SOFTWARE, fileDownload, hash, null);
                    return (d.isOk() ? "t" : "f" + d.getError().getMessage());
                }

                @Override
                public void finish() {
                    if (isOk()) {
                        try {
                            Desktop.getDesktop().open(fileDownload);
                            System.exit(0);
                        } catch (Exception ex) {
                            Utils.error("Erro ao abrir nova instância");
                        }
                    } else {
                        showResult();
                    }
                }
            }, true);
        } else {
            ifFalse.run();
        }
    }

    /**
     * Exibe uma caixa de diálogo para atualizar a base de dados
     *
     * @param ifTrue
     */
    public static void askUpdateDatabase(Runnable ifTrue) {
        if (!Utils.confirm("Banco de Dados incompleto, deseja atualizar?", "Database")) {
            System.exit(0);
        }
        File bin = new File("bin" + separator + "o" + separator).getAbsoluteFile();
        if (bin.exists() && (bin.list().length > 500)) {
            updateDatabase(ifTrue);
        } else {
            downloadDatabase(ifTrue);
        }
    }

    /**
     * Baixa o arquivo .zip com todos os arquivos necessários
     *
     */
    private static void downloadDatabase(Runnable ifTrue) {
        PopUpWorker.exe(new PopUpWorker.MySwingWorker(null, "Baixando Banco de Dados...") {
            @Override
            protected String doInBackground() throws Exception {
                Download d = Download.download(URL_BIN_ZIP, FILE_BIN_ZIP, null, null);
                while (!d.isFinish()) {
                    Utils.sleep(1000);
                    setProgress((int) d.getPercentage());
                    process(Arrays.asList("<html>Baixando Banco de Dados...<br/><span style=\"font-size:70%\">" + d.getBytesStatus() + " - " + d.getVelocity() + "</span>"));
                }
                if (d.isOk()) {
                    Zip.totalFilesUnzip = -1;
                    process(Arrays.asList("Extraindo arquivos..."));
                    new Thread(() -> {
                        while (Zip.totalFilesUnzip != -1) {
                            setProgress((int) (Utils.percentage(Zip.totalFilesUnzip, TOTAL_ITEMS_BIN) * 100.0));
                            Utils.sleep(500);
                        }
                    }).start();
                    try {
                        Zip.unzipDir(FILE_BIN_ZIP);
                    } catch (Exception e) {
                        exit("Erro ao extrair arquivos:\n" + e.getMessage());
                        return null;
                    }
                    Zip.totalFilesUnzip = -1;
                    return "t";
                } else {
                    exit("Erro ao baixar banco de dados:\n" + d.getError().getMessage());
                    return null;
                }
            }

            @Override
            public void finish() {
                if (isOk()) {
                    ifTrue.run();
                } else {
                    canceled();
                }
            }

            @Override
            public void canceled() {
                exit("Atualização não concluída");
            }

            public void exit(String error) {
                Utils.error(error + "\n\nO programa será encerrado");
                System.exit(0);
            }
        }, true);
    }

}
