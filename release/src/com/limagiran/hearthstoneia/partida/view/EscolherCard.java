package com.limagiran.hearthstoneia.partida.view;

/**
 *
 * @author Vinicius
 */
public class EscolherCard {

    /**
     * Retorna o último card da lista passada por parâmetro
     *
     * @param titulo descrição da lista
     * @param ids lista dos ID's das cartas disponíveis para seleção
     * @return último id da lista ou null para lista vazia ou nula
     */
    public static String main(String titulo, String[] ids) {
        return ids != null && ids.length > 0 ? ids[ids.length - 1] : "";
    }
}