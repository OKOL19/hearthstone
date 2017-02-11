package com.limagiran.hearthstoneia;

import static com.limagiran.hearthstone.HearthStone.CARTAS;
import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.partida.view.EscolherCard;
import com.limagiran.hearthstone.util.Param;
import com.limagiran.hearthstone.util.Sort;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class Utils {

    public static final long ALVO_INVALIDO = -1;
    
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

   /**
     * Retorna uma lista de índices aleatórios dentro do limite passado
     *
     * @param quantidade quantidade de indices na lista
     * @param limite limite do índice (exemplo limite 5 = índices de 0 a 4);
     * @return lista de índices sem repetições
     */
    public static List<Integer> random(int quantidade, int limite) {
        List<Integer> retorno = new ArrayList<>();
        if (quantidade > limite) {
            return retorno;
        }
        while (retorno.size() < quantidade) {
            Integer random = Utils.random(limite) - 1;
            if (!retorno.contains(random)) {
                retorno.add(random);
            }
        }
        return retorno;
    }

    /**
     * Retorna uma lista de lacaios escolhidos aleatoriamente
     *
     * @param quantidade quantidade de lacaios
     * @param lacaios lista de lacaios
     * @return lista de lacaios escolhidos aleatoriamente
     */
    public static List<Card> random(int quantidade, List<Card> lacaios) {
        List<Card> retorno = new ArrayList<>();
        if (quantidade > lacaios.size()) {
            return retorno;
        }
        List<Integer> indices = random(quantidade, lacaios.size());
        for (int i : indices) {
            retorno.add(lacaios.get(i));
        }
        return retorno;
    }

    /**
     * Verifica se o card jogado é um combo no turno (se algum card foi jogado
     * antes dele)
     *
     * @param card card jogado
     * @return true para combo. false para primeiro card do turno.
     */
    public static boolean isCombo(Card card) {
        return card.getDono().getCardsJogadosNaRodada().stream().anyMatch((c) -> (!c.equals(card)));
    }

    /**
     * Retorna o lacaio com maior valor de ataque
     *
     * @param lacaios lista de lacaios
     * @return lacaio com o maior valor de ataque. Caso ocorra empate, será
     * escolhido um aleatoriamente.
     */
    public static Card getMaiorAtaque(List<Card> lacaios) {
        if (lacaios.isEmpty()) {
            return null;
        }
        List<Card> maioresAtaques = new ArrayList<>();
        int ataque = -1;
        for (Card card : lacaios) {
            if (card.getAtaque() > ataque) {
                ataque = card.getAtaque();
            }
        }
        for (Card card : lacaios) {
            if (card.getAtaque() == ataque) {
                maioresAtaques.add(card);
            }
        }
        return maioresAtaques.get(Utils.random(maioresAtaques.size()) - 1);
    }

    /**
     * Retorna uma lista com todos os segredos existentes na lista passada por
     * parâmetro, sem segredos repetidos.
     *
     * @param cards lista de cards
     * @return lista de segredos sem repetições
     */
    public static List<Card> getSegredosSemRepetir(List<Card> cards) {
        List<Card> segredos = getSegredos(cards);
        segredos.sort(Sort.id());
        for (int i = 1; i < segredos.size(); i++) {
            if (segredos.get(i).getId().equals(segredos.get(i - 1).getId())) {
                segredos.remove(i);
                i--;
            }
        }
        return segredos;
    }

    /**
     * Retorna todos os segredos da lista passada por parâmetro
     *
     * @param cards lista de cards
     * @return lista de segredos
     */
    public static List<Card> getSegredos(List<Card> cards) {
        List<Card> retorno = new ArrayList<>();
        cards.stream().filter((c) -> (c.isSegredo())).forEach((c) -> {
            retorno.add(c);
        });
        return retorno;
    }

    /**
     * Realiza o evento de 50% de chance de errar o alvo
     *
     * @return true para errar o alvo. false para não errar o alvo.
     */
    public static boolean chanceErrar() {
        return (((int) (Math.random() * 99)) % 2) == 0;
    }

    /**
     * Retorna um oponente aleatório.
     *
     * @param excluir excluir personagem da seleção aleatória
     * @return id_long do card ou Param.OPONENTE para herói inimigo.
     */
    public static long getAleatorioOponente(Partida partida, long excluir) {
        Heroi oponente = partida.getOponente();
        int random = Utils.random(oponente.getMesa().size() + 1) - 1;
        while ((random == oponente.getMesa().size() && excluir == Param.OPONENTE)
                || (random < oponente.getMesa().size() && excluir == oponente.getMesa().get(random).id_long)) {
            random = Utils.random(oponente.getMesa().size() + 1) - 1;
        }
        return random == oponente.getMesa().size() ? Param.OPONENTE : oponente.getMesa().get(random).id_long;
    }

    /**
     * Retorna um oponente aleatório.
     *
     * @return id_long do card ou Param.OPONENTE para herói inimigo.
     */
    public static long getAleatorioOponente(Partida partida) {
        Heroi oponente = partida.getOponente();
        if (oponente.getMesa().isEmpty()) {
            return Param.OPONENTE;
        }
        int random = Utils.random(oponente.getMesa().size() + 1) - 1;
        return random == oponente.getMesa().size() ? Param.OPONENTE : oponente.getMesa().get(random).id_long;
    }

    /**
     * Gera dano ao alvo passado por parâmetro
     *
     * @param alvo código do alvo (id_long do card ou Param.HEROI ou
     * Param.OPONENTE)
     * @param dano valor do dano gerado
     */
    public static void dano(Partida partida, long alvo, int dano) {
        if (alvo == Param.HEROI || alvo == Param.OPONENTE) {
            (alvo == Param.HEROI ? partida.getHero() : partida.getOponente()).delHealth(dano);
        } else {
            partida.findCardByIDLong(alvo).delVida(dano);
        }
    }

    /**
     * Cura o alvo passado por parâmetro
     *
     * @param alvo código do alvo (id_long do card ou Param.HEROI ou
     * Param.OPONENTE)
     * @param vida valor da vida restaurada
     */
    public static void cura(Partida partida, long alvo, int vida) {
        if (alvo == Param.HEROI || alvo == Param.OPONENTE) {
            (alvo == Param.HEROI ? partida.getHero() : partida.getOponente()).addHealth(vida);
        } else {
            partida.findCardByIDLong(alvo).addVida(vida);
        }
    }

    /**
     * Adiciona ataque ao alvo passado por parâmetro
     *
     * @param alvo código do alvo (id_long do card ou Param.HEROI ou
     * Param.OPONENTE)
     * @param ataque valor do ataque adicionado
     */
    public static void addAtaque(Partida partida, long alvo, int ataque) {
        if (alvo == Param.HEROI || alvo == Param.OPONENTE) {
            (alvo == Param.HEROI ? partida.getHero() : partida.getOponente()).addAttack(ataque);
        } else {
            partida.findCardByIDLong(alvo).addAtaque(ataque);
        }
    }

    /**
     * Retorna uma lista com todos os lacaios lendários da lista passada por
     * parâmetro
     *
     * @param lacaios lista de lacaios
     * @return lista de lacaios lendários
     */
    public static List<Card> getLendarios(List<Card> lacaios) {
        List<Card> retorno = new ArrayList<>();
        lacaios.stream().filter((lacaio) -> (lacaio.isLendario())).forEach((lacaio) -> {
            retorno.add(lacaio);
        });
        return retorno;
    }

    /**
     * Seleciona um lacaio na lista aleatoriamente
     *
     * @param lacaios lista de lacaios
     * @param excluir excluir card da seleção aleatória
     * @return lacaio escolhido aleatoriamente
     */
    public static Card getLacaioAleatorio(List<Card> lacaios, Card excluir) {
        if (lacaios.size() <= 1) {
            return null;
        }
        Card retorno = lacaios.get(Utils.random(lacaios.size()) - 1);
        while (retorno.equals(excluir)) {
            retorno = lacaios.get(Utils.random(lacaios.size()) - 1);
        }
        return retorno;
    }

    /**
     * Retorna os lacaios adjacentes ao lacaio passado por parâmetro
     *
     * @param lacaio lacaio na mesa
     * @return lacaio ou lacaios adjacentes
     */
    public static List<Card> getAdjacentes(Card lacaio) {
        List<Card> retorno = new ArrayList<>();
        int index = lacaio.getDono().getMesa().indexOf(lacaio);
        if (index > 0) {
            retorno.add(lacaio.getDono().getMesa().get(index - 1));
        }
        if (index + 1 < lacaio.getDono().getMesa().size()) {
            retorno.add(lacaio.getDono().getMesa().get(index + 1));
        }
        return retorno;
    }

    /**
     * Seleciona um personagem aleatório
     *
     * @param excluir excluir o personagem da seleção aleatória
     * @return id_long do card ou Param.HEROI ou Param.OPONENTE
     */
    public static long getPersonagemAleatorio(Partida partida, long excluir) {
        List<Card> lacaios = partida.getMesa();
        int random = Utils.random(lacaios.size() + 2) - 1;
        while ((random == (lacaios.size() + 1) && excluir == Param.HEROI)
                || (random == lacaios.size() && excluir == Param.OPONENTE)
                || (random < lacaios.size() && excluir == lacaios.get(random).id_long)) {
            random = Utils.random(lacaios.size() + 2) - 1;
        }
        return random == lacaios.size() + 1 ? Param.HEROI : random == lacaios.size() ? Param.OPONENTE : lacaios.get(random).id_long;
    }

    /**
     * Seleciona um personagem aleatório
     *
     * @return id_long do card ou Param.HEROI ou Param.OPONENTE
     */
    public static long getPersonagemAleatorio(Partida partida) {
        List<Card> lacaios = partida.getMesa();
        int random = Utils.random(lacaios.size() + 2) - 1;
        return random == lacaios.size() + 1 ? Param.HEROI : random == lacaios.size() ? Param.OPONENTE : lacaios.get(random).id_long;
    }

    /**
     * Seleciona um personagem ileso aleatório
     *
     * @return id_long do card ou Param.HEROI ou Param.OPONENTE. Retorna
     * ALVO_INVALIDO caso não haja personagem ileso.
     */
    public static long getPersonagemAleatorioIleso(Partida partida) {
        Heroi hero = partida.getHero();
        Heroi oponente = partida.getOponente();
        List<Card> ilesos = getIlesos(partida.getMesa());
        if (hero.getHealth() < hero.getVidaTotal() && oponente.getHealth() < oponente.getVidaTotal() && ilesos.isEmpty()) {
            return ALVO_INVALIDO;
        }
        int random = Utils.random(ilesos.size() + 2) - 1;

        while ((random == ilesos.size() + 1 && hero.getHealth() < hero.getVidaTotal())
                || (random == ilesos.size() && oponente.getHealth() < oponente.getVidaTotal())
                || (random < ilesos.size() && !ilesos.get(random).isIleso())) {
            random = Utils.random(ilesos.size() + 2) - 1;
        }

        return random == ilesos.size() + 1 ? Param.HEROI : random == ilesos.size() ? Param.OPONENTE : ilesos.get(random).id_long;
    }

    /**
     * Seleciona um personagem ferido aleatório
     *
     * @return id_long do card ou Param.HEROI ou Param.OPONENTE. Retorna
     * ALVO_INVALIDO caso não haja personagem ferido.
     */
    public static long getPersonagemAleatorioFerido(Partida partida) {
        Heroi hero = partida.getHero();
        Heroi oponente = partida.getOponente();
        List<Card> feridos = getFeridos(partida.getMesa());
        if (hero.getHealth() == hero.getVidaTotal() && oponente.getHealth() == oponente.getVidaTotal() && feridos.isEmpty()) {
            return ALVO_INVALIDO;
        }
        int random = Utils.random(feridos.size() + 2) - 1;

        while ((random == feridos.size() + 1 && hero.getHealth() == hero.getVidaTotal())
                || (random == feridos.size() && oponente.getHealth() == oponente.getVidaTotal())
                || (random < feridos.size() && feridos.get(random).isIleso())) {
            random = Utils.random(feridos.size() + 2) - 1;
        }

        return random == feridos.size() + 1 ? Param.HEROI : random == feridos.size() ? Param.OPONENTE : feridos.get(random).id_long;
    }

    /**
     * Seleciona um lacaio aleatório da mesa
     *
     * @param excluir excluir lacaio da seleção aleatória
     * @return lacaio selecionado aleatoriamente ou null se não houver lacaios
     */
    public static Card getLacaioAleatorioNaMesa(Partida partida, long excluir) {
        List<Card> lacaios = partida.getMesa();
        if (lacaios.size() <= 1) {
            return null;
        }
        int random = Utils.random(lacaios.size()) - 1;
        while (lacaios.get(random).id_long == excluir) {
            random = Utils.random(lacaios.size()) - 1;
        }
        return lacaios.get(random);
    }

    /**
     * Seleciona um lacaio aleatoriamente
     *
     * @param cards lista de lacaios
     * @return lacaio aleatório ou null se não houver lacaio.
     */
    public static Card getLacaioAleatorio(List<Card> cards) {
        if (cards.isEmpty()) {
            return null;
        }
        boolean flag = false;
        for (Card c : cards) {
            if (c.isLacaio()) {
                flag = true;
                break;
            }
        }
        if (flag) {
            int random = Utils.random(cards.size()) - 1;
            while (!cards.get(random).isLacaio()) {
                random = Utils.random(cards.size()) - 1;
            }
            return cards.get(random);
        } else {
            return null;
        }
    }

    /**
     * Retorna uma lista com os lacaios ilesos
     *
     * @param lacaios lista de lacaios
     * @return lista de lacaios ilesos
     */
    public static List<Card> getIlesos(List<Card> lacaios) {
        List<Card> retorno = new ArrayList<>();
        lacaios.stream().filter((lacaio) -> (lacaio.isIleso())).forEach((lacaio) -> {
            retorno.add(lacaio);
        });
        return retorno;
    }

    /**
     * Retorna uma lista com os lacaios feridos
     *
     * @param lacaios lista de lacaios
     * @return lista de lacaios feridos
     */
    public static List<Card> getFeridos(List<Card> lacaios) {
        List<Card> retorno = new ArrayList<>();
        lacaios.stream().filter((lacaio) -> (!lacaio.isIleso())).forEach((lacaio) -> {
            retorno.add(lacaio);
        });
        return retorno;
    }

    /**
     * Retorna o valor da sobrecarga do card, caso ele tenha a mecânica
     * <b>Sobregarga</b>
     *
     * @param card Card a ser verificado
     * @return valor da sobrecarga ou 0 (zero) caso não haja.
     */
    public static int getSobrecarga(Card card) {
        try {
            String text = card.getText();
            text = text.substring(text.indexOf("Sobrecarga"));
            return Integer.parseInt("" + text.charAt(text.indexOf("(") + 1));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Seleciona um alvo aleatoriamente do herói passado por parâmetro
     *
     * @param heroi Herói
     * @return alvo aleatório
     */
    public static long getAlvoAleatorio(Partida partida, Heroi heroi) {
        List<Card> lacaios = heroi.getMesa();
        int random = Utils.random(lacaios.size() + 1) - 1;
        return random == lacaios.size()
                ? partida.getHero().equals(heroi)
                ? Param.HEROI
                : Param.OPONENTE
                : lacaios.get(random).id_long;
    }

    /**
     * Descobrir um card
     *
     * @param titulo Mensagem exibida na janela de seleção
     * @param ids lista de id's dos cards disponíveis para seleção
     * @return id escolhido
     */
    public static String descobrir(String titulo, String[] ids) {
        String escolhido = EscolherCard.main(titulo, ids);
        while (escolhido == null) {
            escolhido = EscolherCard.main(titulo, ids);
        }
        return escolhido;
    }

    /**
     * Retorna um array com os id's dos cards passados por parâmetro
     *
     * @param cards lista de cards
     * @return String array com os id's dos cards
     */
    public static String[] getIds(List<Card> cards) {
        String[] ids = new String[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            ids[i] = cards.get(i).getId();
        }
        return ids;
    }

    /**
     * Seleciona um card aleatoriamente da lista passada por parâmetro
     *
     * @param lista lista de cards
     * @return card aleatorio ou null para lista vazia.
     */
    public static Card random(List<Card> lista) {
        if (lista.isEmpty()) {
            return null;
        }
        return lista.get(Utils.random(lista.size()) - 1);
    }

    /**
     * Retorna o valor do dano do feitiço com o dano mágico do herói calculado
     *
     * @param dano dano do feitiço
     * @param hero dono do feitiço
     * @return dano do feitiço com o efeito do dano mágico calculado
     */
    public static int getDanoFeitico(int dano, Heroi hero) {
        return hero.getDobrarDanoECura(dano + hero.getDanoMagico());
    }

    /**
     * Thread.sleep();
     *
     * @param time tempo em espera
     */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception ex) {
        }
    }

    /**
     * Retorna um id aleatório da lista de totens disponíveis para o poder
     * heroico do Xamã.
     *
     * @param mesa mesa do Herói que invocou o poder heroico
     * @return id do totem ou null se a mesa já contém todos os totens.
     */
    public static String getTotemXamaPoderHeroico(List<Card> mesa) {
        for (String totem : Values.TOTENS_SHAMAN_PODER) {
            if (mesa.stream().anyMatch((card) -> (card.getId().equals(totem)))) {
                return null;
            }
        }
        int index = Utils.random(Values.TOTENS_SHAMAN_PODER.length) - 1;
        while (contemIdNaLista(mesa, Values.TOTENS_SHAMAN_PODER[index])) {
            index = Utils.random(Values.TOTENS_SHAMAN_PODER.length) - 1;
        }
        return Values.TOTENS_SHAMAN_PODER[index];
    }

    /**
     * Verifica se existe algum id específico na lista
     *
     * @param list lista de cards
     * @param id id do card
     * @return {@code true} ou {@code false}
     */
    public static boolean contemIdNaLista(List<Card> list, String id) {
        return list.stream().anyMatch((card) -> (card.getId().equals(id)));
    }

    public static List<Card> getDeckCardIA(List<String> ids) {
        List<Card> retorno = new ArrayList<>();
        for (String id : ids) {
            retorno.add(CARTAS.createCardIA(id));
        }
        return retorno;
    }

    /**
     * Retorna a descrição do card
     *
     * @param nome nome do herói
     * @param card Card
     * @return "#nomeHeroi = #nomeCard (#custo/#ataque/#vida) #textoDoCard
     */
    public static String getDescricao(String nome, Card card) {
        return card != null ? nome + " = " + card.getName()
                + " (" + card.getCost() + "/" + card.getAttack() + "/" + card.getVidaOriginal() + ") "
                + card.getDescricao()
                : "";

    }

}