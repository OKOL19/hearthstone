package com.limagiran.hearthstone.util;

import static com.limagiran.hearthstone.util.Utils.reflection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius
 */
public class Filtro {

    /**
     * Filtrar os lacaios que não estão furtivos e não são imunes a feitiços
     *
     * @param cards Lista de lacaios a serem filtrados
     * @return List Card
     */
    public static List excluirFurtivoEImune(List cards) {
        List retorno = new ArrayList<>();
        cards.stream().filter(c -> (!reflection(Boolean.class, c, "isImuneAlvo")
                && !reflection(Boolean.class, c, "isFurtivo"))).forEach(retorno::add);
        return retorno;
    }

    /**
     * Filtrar os lacaios com o valor de ataque até o limite passado por
     * parâmetro
     *
     * @param lacaios lista de lacaios
     * @param ataque ataque máximo
     * @return List Card
     */
    public static List lacaiosComXOuMenosDeAtaque(List lacaios, int ataque) {
        List retorno = new ArrayList<>();
        lacaios.stream().filter(c -> (reflection(Integer.class, c, "getAtaque") <= ataque)).forEach(retorno::add);
        return retorno;
    }

    /**
     * Filtrar os lacaios com o valor de ataque maior ou igual o limite passado
     * por parâmetro
     *
     * @param lacaios lista de lacaios
     * @param ataque ataque mínimo
     * @return List Card
     */
    public static List lacaiosComXOuMaisDeAtaque(List lacaios, int ataque) {
        List retorno = new ArrayList<>();
        lacaios.stream().filter((c) -> (reflection(Integer.class, c, "getAtaque") >= ataque)).forEach(retorno::add);
        return retorno;
    }

    /**
     * Filtrar os lacaios com o valor de ataque maior ou igual o limite passado
     * por parâmetro
     *
     * @param lacaios lista de lacaios
     * @param ataque ataque mínimo
     * @return List Card
     */
    public static List lacaiosComXOuMaisDeAtaqueSemAura(List lacaios, int ataque) {
        List retorno = new ArrayList<>();
        lacaios.stream().filter(c -> (reflection(Integer.class, c, "getAtaqueSemAura") >= ataque)).forEach(retorno::add);
        return retorno;
    }

    /**
     * Filtra os lacaios pela raca
     *
     * @param lacaios lista de lacaios
     * @param race raca a ser filtrada
     * @return List Card
     */
    public static List raca(List lacaios, String race) {
        List retorno = new ArrayList<>();
        lacaios.stream().filter(c -> (reflection(String.class, c, "getRace") != null
                && reflection(String.class, c, "getRace").equals(race)))
                .forEach(retorno::add);
        return retorno;
    }

    /**
     * Filtra os cards que são segredos
     *
     * @param cards lista de cards
     * @return List Card
     */
    public static List segredo(List cards) {
        List retorno = new ArrayList<>();
        cards.stream().filter(c -> (reflection(Boolean.class, c, "isSegredo"))).forEach(retorno::add);
        return retorno;
    }

    /**
     * Filtra os cards pelo custo
     *
     * @param cards lista de cards
     * @param custo custo a ser filtrado
     * @return List Card
     */
    public static List custo(List cards, int custo) {
        List retorno = new ArrayList<>();
        cards.stream().filter(c -> (reflection(Integer.class, c, "getCost") == custo)).forEach(retorno::add);
        return retorno;
    }

    /**
     * Filtra os lacaios
     *
     * @param cards lista de cards
     * @return List Card
     */
    public static List lacaio(List cards) {
        List retorno = new ArrayList<>();
        cards.stream().filter(c -> (reflection(Boolean.class, c, "isLacaio"))).forEach(retorno::add);
        return retorno;
    }
}
