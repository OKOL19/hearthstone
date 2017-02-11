package com.limagiran.hearthstoneia.gameplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius Silva
 */
public class Pacote implements Serializable {

    public final String TIPO;
    private final List<String> nomes = new ArrayList<>();
    private final List<String> values = new ArrayList<>();

    public Pacote(String tipo) {
        TIPO = tipo;
    }

    /**
     * Retorna os valores da lista separados por vírgula e cada valor entre aspas
     * @param list lista de String
     * @return 
     */
    public String listToJSon(List<String> list) {
        String retorno = "";
        for (String nome : list) {
            retorno += (!retorno.isEmpty() ? "," : "") + "\"" + nome + "\"";
        }
        return retorno + "";
    }

    /**
     * Retorna a classe no formato JSon
     *
     * @return String JSON
     */
    public String getJSon() {
        StringBuilder retorno = new StringBuilder();
        retorno.append('{');
        retorno.append("\"TIPO\"");
        retorno.append(':');
        retorno.append(('"' + TIPO + '"'));
        retorno.append(',');
        retorno.append("\"nomes\"");
        retorno.append(':');
        retorno.append(('[' + listToJSon(nomes) + ']'));
        retorno.append(',');
        retorno.append("\"values\"");
        retorno.append(':');
        retorno.append(('[' + listToJSon(values) + ']'));
        retorno.append('}');
        return retorno.toString();
    }

    public void set(String nome, String value) {
        int index = nomes.indexOf(nome);
        if (index != -1) {
            nomes.set(index, nome);
            values.set(index, value);
        } else {
            nomes.add(nome);
            values.add(value);
        }
    }

    public void set(String nome, Long value) {
        set(nome, value.toString());
    }

    public void set(String nome, Integer value) {
        set(nome, value.toString());
    }

    public void set(String nome, Boolean value) {
        set(nome, value.toString());
    }

    public void remove(String nome, String value) {
        nomes.remove(nome);
        values.remove(value);
    }

    public String getParamString(String nome) {
        return nomes.contains(nome) ? values.get(nomes.indexOf(nome)) : "";
    }

    public boolean getParamBoolean(String param) {
        return Boolean.parseBoolean(getParamString(param));
    }

    public int getParamInt(String param) {
        try {
            return Integer.parseInt(getParamString(param));
        } catch (Exception ex) {
        }
        return -9857;
    }

    public long getParamLong(String param) {
        try {
            return Long.parseLong(getParamString(param));
        } catch (Exception ex) {
        }
        return -9857;
    }
}