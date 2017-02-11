package com.limagiran.hearthstone.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Vinicius Silva
 */
public class Dir {

    /**
     * Deleta todos os arquivos da pasta e a pasta passada por parâmetro
     *
     * @param dir pasta que será deletada
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] files = dir.list();
            for (int i = 0; i < files.length; i++) {
                boolean success = deleteDir(new File(dir, files[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // O diretório está vazio, então é só deletar
        return dir.delete();
    }

    /**
     * Abre uma janela para seleção do local onde um arquivo será salvo. Retorna
     * o caminho completo para o diretório. Retorna null para diretório não
     * selecionado.
     *
     * @param titulo Título exibido na janela de seleção do diretório
     * @param nomeSugerido Nome que aparecerá na caixa de seleção como sugestão
     * @param diretorioInicial Pasta inicial exibida
     * @param extensao Extensão do arquivo a ser salvo
     * @param tipoDeArquivo Descrição do tipo de arquivo a ser salvo
     * @return caminho absoluto com o nome digitado para salvar o arquivo
     */
    public static File janelaFileSalvar(String titulo, String nomeSugerido, File diretorioInicial, final String extensao, final String tipoDeArquivo) {
        JFileChooser abrir = new JFileChooser();
        abrir.setApproveButtonText("Salvar");
        abrir.setAcceptAllFileFilterUsed(false);
        abrir.setDialogTitle(titulo);
        abrir.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(extensao) || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return tipoDeArquivo;
            }
        });
        if (diretorioInicial.getAbsoluteFile().exists()) {
            abrir.setCurrentDirectory(diretorioInicial.getAbsoluteFile());
        }
        if (!nomeSugerido.isEmpty()) {
            abrir.setSelectedFile(new File(abrir.getCurrentDirectory().getAbsoluteFile() + File.separator + nomeSugerido));
        }
        while (true) {
            int teste = abrir.showOpenDialog(null);
            if (teste == JFileChooser.APPROVE_OPTION) {
                File buffer = abrir.getSelectedFile();
                if (!buffer.getName().endsWith(extensao)) {
                    buffer = new File(abrir.getSelectedFile() + extensao);
                }
                String[] validar = new String[]{"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
                boolean flag0 = true;
                for (String s : validar) {
                    if (buffer.getName().contains(s)) {
                        flag0 = false;
                    }
                }
                if (flag0) {
                    int flag1 = 0;
                    if (buffer.exists()) {
                        flag1 = JOptionPane.showConfirmDialog(null, "nome já existe.\nDeseja substituí-lo?", "Salvar Arquivo", JOptionPane.YES_NO_OPTION);
                    }
                    if (flag1 == 0) {
                        try {
                            return buffer;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "ERRO!" + "\n" + ex.getMessage());
                            return null;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Os nomes de arquivo não podem conter nenhum dos seguintes caracteres:\n     \\ / : * ? \" < > |");
                }
            } else if (teste == JFileChooser.CANCEL_OPTION) {
                return null;
            }
        }
    }

    /**
     * Abre uma janela para seleção do local onde um arquivo será salvo. Retorna
     * o caminho completo para o diretório. Retorna null para diretório não
     * selecionado.
     *
     * @param titulo Título exibido na janela de seleção do diretório
     * @param nomeSugerido Nome que aparecerá na caixa de seleção como sugestão
     * @param extensao Extensão do arquivo a ser salvo
     * @param tipoDeArquivo Descrição do tipo de arquivo a ser salvo
     * @return caminho absoluto com o nome digitado para salvar o arquivo
     */
    public static File janelaFileSalvar(String titulo, String nomeSugerido, final String extensao, final String tipoDeArquivo) {
        return janelaFileSalvar(titulo, nomeSugerido, new File(""), extensao, tipoDeArquivo);
    }

    /**
     * Abre uma janela para seleção do local onde um arquivo será salvo. Retorna
     * o caminho completo para o diretório. Retorna null para diretório não
     * selecionado.
     *
     * @param titulo Título exibido na janela de seleção do diretório
     * @param diretorioInicial
     * @param extensao Extensão do arquivo a ser salvo
     * @param tipoDeArquivo Descrição do tipo de arquivo a ser salvo
     * @return caminho absoluto com o nome digitado para salvar o arquivo
     */
    public static File janelaFileSalvar(String titulo, File diretorioInicial, final String extensao, final String tipoDeArquivo) {
        return janelaFileSalvar(titulo, "", diretorioInicial, extensao, tipoDeArquivo);
    }

    /**
     * Abre uma janela para seleção do local onde um arquivo será salvo. Retorna
     * o caminho completo para o diretório. Retorna null para diretório não
     * selecionado.
     *
     * @param titulo Título exibido na janela de seleção do diretório
     * @param extensao Extensão do arquivo a ser salvo
     * @param tipoDeArquivo Descrição do tipo de arquivo a ser salvo
     * @return caminho absoluto com o nome digitado para salvar o arquivo
     */
    public static File janelaFileSalvar(String titulo, final String extensao, final String tipoDeArquivo) {
        return janelaFileSalvar(titulo, "", new File(""), extensao, tipoDeArquivo);
    }

    /**
     * Abre uma janela para seleção de arquivo. Retorna o caminho completo para
     * o arquivo. Retorna null para arquivo não selecionado.
     *
     * @param titulo Título exibido na janela de seleção de arquivo
     * @param diretorioInicial Pasta inicial para procurar o arquivo
     * @param extensao Extensão válida para procurar arquivo
     * @param tipoDeArquivo Descrição do tipo de arquivo a ser selecionado
     * @return caminho absoluto do arquivo selecionado
     */
    public static File janelaFile(String titulo, File diretorioInicial, final String extensao, final String tipoDeArquivo) {
        return janelaFile(titulo, diretorioInicial, new String[]{extensao}, tipoDeArquivo);
    }

    /**
     * Abre uma janela para seleção de arquivo. Retorna o caminho completo para
     * o arquivo. Retorna null para arquivo não selecionado.
     *
     * @param titulo Título exibido na janela de seleção de arquivo
     * @param diretorioInicial Pasta inicial para procurar o arquivo
     * @param extensao Extensão válida para procurar arquivo
     * @param tipoDeArquivo Descrição do tipo de arquivo
     * @return caminho absoluto do arquivo selecionado
     */
    public static File janelaFile(String titulo, File diretorioInicial, final String[] extensao, final String tipoDeArquivo) {
        JFileChooser abrir = new JFileChooser();
        abrir.setAcceptAllFileFilterUsed(false);
        abrir.setDialogTitle(titulo);
        abrir.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return extensaoValida(f, extensao) || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return tipoDeArquivo;
            }

            private boolean extensaoValida(File f, String extensao[]) {
                for (String e : extensao) {
                    if (f.getName().toLowerCase().endsWith(e)) {
                        return true;
                    }
                }
                return false;
            }
        });
        if (diretorioInicial.getAbsoluteFile().exists()) {
            abrir.setCurrentDirectory(diretorioInicial.getAbsoluteFile());
        }
        int teste = abrir.showOpenDialog(null);
        if (teste == JFileChooser.APPROVE_OPTION) {
            DataInputStream is = null;
            try {
                File buffer = abrir.getSelectedFile();
                return buffer;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Falha na abertura do arquivo!");
            }
            abrir.setVisible(false);
        }
        return null;
    }

    /**
     * Abre uma janela para seleção de arquivo. Retorna o caminho completo para
     * o arquivo. Retorna null para arquivo não selecionado.
     *
     * @param titulo Título exibido na janela de seleção de arquivo
     * @param extensao Extensão válida para procurar arquivo
     * @param tipoDeArquivo Descrição do tipo de arquivo a ser selecionado
     * @return caminho absoluto do arquivo selecionado
     */
    public static File janelaFile(String titulo, final String extensao, final String tipoDeArquivo) {
        return janelaFile(titulo, new File(""), new String[]{extensao}, tipoDeArquivo);
    }

    /**
     * Cria a cópia de um arquivo para o local passado como parâmetro
     *
     * @param origem caminho completo do arquivo a ser copiado
     * @param destino caminho completo (com nome e extensão) do local de
     * gravação
     * @return {@code true} ou {@code false}
     * @throws java.io.IOException
     *
     */
    public static boolean copiarArquivos(File origem, File destino) throws IOException {
        if (destino.exists()) {
            destino.delete();
        }
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;

        try {
            sourceChannel = new FileInputStream(origem).getChannel();
            destinationChannel = new FileOutputStream(destino).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } catch (IOException ioe) {
            return false;
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen()) {
                sourceChannel.close();
            }
            if (destinationChannel != null && destinationChannel.isOpen()) {
                destinationChannel.close();
            }
        }
        return true;
    }

    /**
     * Retorna o caminho absoluto, incluindo caminhos de rede
     *
     * @return caminho absoluto
     */
    public static String getDirAbsoluto() {
        String retorno = "", dir = new File("").getAbsolutePath();
        if (!dir.contains(":")) {
            return dir.substring(2);
        }
        try {
            Runtime cmd = Runtime.getRuntime();
            Process processo = cmd.exec("NET USE " + dir.charAt(0) + ":");
            BufferedReader in
                    = new BufferedReader(
                            new InputStreamReader(processo.getInputStream()));
            Thread.sleep(100);
            boolean rede = false;
            while (in.ready()) {
                String buff = in.readLine();
                if (buff.contains("Nome remoto")) {
                    rede = true;
                    retorno = dir.replace(dir.substring(0, dir.indexOf(":") + 1), buff.substring(buff.indexOf("\\\\") + 2));
                }
            }
            in.close();
            return rede ? retorno : dir;
        } catch (IOException | InterruptedException ex) {
            return new File("").getAbsoluteFile().toString();
        }
    }

    /**
     * Torna o arquivo ou pasta passado como parâmetro em oculto.
     *
     * @param caminho caminho do arquivo ou pasta
     */
    public static boolean ocultarArquivo(String caminho) {
        try {
            String comando = "C:\\WINDOWS\\System32\\ATTRIB.EXE +H \"" + caminho + "\"";
            Runtime.getRuntime().exec(comando);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Retorna a data de criação do arquivo passado por parâmetro
     *
     * @param file arquivo a ser analisado
     * @return data no formato long. Retorna -1 para arquivo não encontrado
     */
    public static long getCreationTime(File file) throws IOException {
        Path path = file.toPath();
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        return attr.creationTime().toMillis();
    }

    /**
     * Retorna a data da última modificação no arquivo
     *
     * @param file local do arquivo
     * @return data da última modificação no arquivo do banco de dados no
     * formato longtime
     */
    public static long getLastModifiedTime(File file) {
        try {
            Path path = file.toPath();
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            return attr.lastModifiedTime().toMillis();
        } catch (IOException ex) {
            return 0;
        }
    }

    /**
     * altera o longtime da última modificação no arquivo
     *
     * @param time data no formato longtime
     */
    public static void setLastModifiedTime(File file, long time) {
        try {
            Path path = file.toPath();
            FileTime fileTime = FileTime.fromMillis(time);
            Files.setLastModifiedTime(path, fileTime);
        } catch (IOException ex) {
        }
    }

    /**
     * Retorna o caminho absoluto do arquivo .jar em execução
     *
     * @return objeto File
     */
    public static File getFileEmExecucao() {
        try {
            return new File(URLDecoder.decode(Dir.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath(), "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new File(Dir.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath().replace("%20", " "));
        }
    }

    /**
     * Procura o diretório onde se encontra o arquivo javaw.exe
     *
     * @return diretório ou apenas "javaw.exe" para não encontrado
     */
    public static String findJavaw() {
        Path pathToJavaw = null;
        Path temp;
        String keyNode = "HKLM\\Software\\JavaSoft\\Java Runtime Environment";
        List<String> output = new ArrayList<>();
        executeCommand(new String[]{"REG", "QUERY", "\"" + keyNode + "\"", "/v", "CurrentVersion"}, output);
        Pattern pattern = Pattern.compile("\\s*CurrentVersion\\s+\\S+\\s+(.*)$");
        for (String line : output) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                keyNode += "\\" + matcher.group(1);
                List<String> output2 = new ArrayList<>();
                executeCommand(new String[]{"REG", "QUERY", "\"" + keyNode + "\"", "/v", "JavaHome"}, output2);
                Pattern pattern2 = Pattern.compile("\\s*JavaHome\\s+\\S+\\s+(.*)$");
                for (String line2 : output2) {
                    Matcher matcher2 = pattern2.matcher(line2);
                    if (matcher2.find()) {
                        pathToJavaw = Paths.get(matcher2.group(1), "bin", "javaw.exe");
                        break;
                    }
                }
                break;
            }
        }
        try {
            if (Files.exists(pathToJavaw)) {
                return pathToJavaw.toString();
            }
        } catch (Exception ignored) {
        }

        pathToJavaw = Paths.get("C:\\Windows\\System32\\javaw.exe");
        try {
            if (Files.exists(pathToJavaw)) {
                return pathToJavaw.toString();
            }
        } catch (Exception ignored) {
        }

        pathToJavaw = null;
        temp = Paths.get("C:\\Program Files\\Java");
        if (Files.exists(temp)) {
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(temp, "jre*")) {
                for (Path path : dirStream) {
                    temp = Paths.get(path.toString(), "bin", "javaw.exe");
                    if (Files.exists(temp)) {
                        pathToJavaw = temp;
                    }
                }
                if (pathToJavaw != null) {
                    return pathToJavaw.toString();
                }
            } catch (Exception ignored) {
            }
        }
        pathToJavaw = null;
        temp = Paths.get("C:\\Program Files (x86)\\Java");
        if (Files.exists(temp)) {
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(temp, "jre*")) {
                for (Path path : dirStream) {
                    temp = Paths.get(path.toString(), "bin", "javaw.exe");
                    if (Files.exists(temp)) {
                        pathToJavaw = temp;
                    }
                }
                if (pathToJavaw != null) {
                    return pathToJavaw.toString();
                }
            } catch (Exception ignored) {
            }
        }

        pathToJavaw = null;
        temp = Paths.get("C:\\Program Files\\Java");
        if (Files.exists(temp)) {
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(temp, "jdk*")) {
                for (Path path : dirStream) {
                    temp = Paths.get(path.toString(), "jre", "bin", "javaw.exe");
                    if (Files.exists(temp)) {
                        pathToJavaw = temp;
                    }
                }
                if (pathToJavaw != null) {
                    return pathToJavaw.toString();
                }
            } catch (Exception ignored) {
            }
        }

        pathToJavaw = null;
        temp = Paths.get("C:\\Program Files (x86)\\Java");
        if (Files.exists(temp)) {
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(temp, "jdk*")) {
                for (Path path : dirStream) {
                    temp = Paths.get(path.toString(), "jre", "bin", "javaw.exe");
                    if (Files.exists(temp)) {
                        pathToJavaw = temp;
                        // Don't "break", in order to find the latest JDK version
                    }
                }
                if (pathToJavaw != null) {
                    return pathToJavaw.toString();
                }
            } catch (Exception ignored) {
            }
        }
        return "javaw.exe";
    }

    /**
     * Apoio ao método findJavaw
     *
     * @param cmds comandos
     * @param outputs resultados dos comandos
     */
    private static void executeCommand(String[] cmds, List<String> outputs) {
        StringBuilder output = new StringBuilder();
        String line;
        Process p;
        try {
            for (String cmd : cmds) {
                p = Runtime.getRuntime().exec(cmd);
                p.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                outputs.add(output.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}