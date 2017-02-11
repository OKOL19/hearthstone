package com.limagiran.hearthstone.card.control;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.limagiran.hearthstone.util.Sort;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.partida.util.Random;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class Cards {

    @SerializedName("Basic")
    private List<Card> basic = new ArrayList<>();
    @SerializedName("BlackrockMountain")
    private List<Card> blackrockMountain = new ArrayList<>();
    @SerializedName("Classic")
    private List<Card> classic = new ArrayList<>();
    @SerializedName("Credits")
    private List<Card> credits = new ArrayList<>();
    @SerializedName("CurseOfNaxxramas")
    private List<Card> curseOfNaxxramas = new ArrayList<>();
    @SerializedName("Debug")
    private List<Card> debug = new ArrayList<>();
    @SerializedName("GoblinsVsGnomes")
    private List<Card> goblinsVsGnomes = new ArrayList<>();
    @SerializedName("HeroSkins")
    private List<Card> heroSkins = new ArrayList<>();
    @SerializedName("LeagueOfExplorers")
    private List<Card> leagueOfExplorers = new ArrayList<>();
    @SerializedName("Missions")
    private List<Card> missions = new ArrayList<>();
    @SerializedName("Promotion")
    private List<Card> promotion = new ArrayList<>();
    @SerializedName("Reward")
    private List<Card> reward = new ArrayList<>();
    @SerializedName("System")
    private List<Card> system = new ArrayList<>();
    @SerializedName("TavernBrawl")
    private List<Card> tavernBrawls = new ArrayList<>();
    @SerializedName("TheGrandTournament")
    private List<Card> theGrandTournaments = new ArrayList<>();

    @Expose(serialize = false, deserialize = false)
    private final List<Card> allCards = new ArrayList<>();

    public Cards() {
    }

    public List<Card> getBasic() {
        return new ArrayList<>(basic);
    }

    public void setBasic(List<Card> basic) {
        this.basic = basic;
    }

    public List<Card> getBlackrockMountain() {
        return new ArrayList<>(blackrockMountain);
    }

    public void setBlackrockMountain(List<Card> blackrockMountain) {
        this.blackrockMountain = blackrockMountain;
    }

    public List<Card> getClassic() {
        return new ArrayList<>(classic);
    }

    public void setClassic(List<Card> classic) {
        this.classic = classic;
    }

    public List<Card> getCredits() {
        return new ArrayList<>(credits);
    }

    public void setCredits(List<Card> Credits) {
        this.credits = Credits;
    }

    public List<Card> getCurseOfNaxxramas() {
        return new ArrayList<>(curseOfNaxxramas);
    }

    public void setCurseOfNaxxramas(List<Card> curseOfNaxxramas) {
        this.curseOfNaxxramas = curseOfNaxxramas;
    }

    public List<Card> getDebug() {
        return new ArrayList<>(debug);
    }

    public void setDebug(List<Card> Debug) {
        this.debug = Debug;
    }

    public List<Card> getGoblinsVsGnomes() {
        return new ArrayList<>(goblinsVsGnomes);
    }

    public void setGoblinsVsGnomes(List<Card> goblinsVsGnomes) {
        this.goblinsVsGnomes = goblinsVsGnomes;
    }

    public List<Card> getHeroSkins() {
        return new ArrayList<>(heroSkins);
    }

    public void setHeroSkins(List<Card> HeroSkins) {
        this.heroSkins = HeroSkins;
    }

    public List<Card> getLeagueOfExplorers() {
        return new ArrayList<>(leagueOfExplorers);
    }

    public void setLeagueOfExplorers(List<Card> leagueOfExplorers) {
        this.leagueOfExplorers = leagueOfExplorers;
    }

    public List<Card> getMissions() {
        return new ArrayList<>(missions);
    }

    public void setMissions(List<Card> missions) {
        this.missions = missions;
    }

    public List<Card> getPromotion() {
        return new ArrayList<>(promotion);
    }

    public void setPromotion(List<Card> promotion) {
        this.promotion = promotion;
    }

    public List<Card> getReward() {
        return new ArrayList<>(reward);
    }

    public void setReward(List<Card> reward) {
        this.reward = reward;
    }

    public List<Card> getSystem() {
        return new ArrayList<>(system);
    }

    public void setSystem(List<Card> system) {
        this.system = system;
    }

    public List<Card> getTavernBrawls() {
        return new ArrayList<>(tavernBrawls);
    }

    public void setTavernBrawls(List<Card> tavernBrawls) {
        this.tavernBrawls = tavernBrawls;
    }

    public List<Card> getTheGrandTournaments() {
        return new ArrayList<>(theGrandTournaments);
    }

    public void setTheGrandTournaments(List<Card> theGrandTournaments) {
        this.theGrandTournaments = theGrandTournaments;
    }

    public List<Card> getAllCards() {
        return new ArrayList<>(allCards);
    }

    /**
     * Retorna um card
     *
     * @param id id do card
     * @return objeto Card ou null para não encontrado
     */
    public Card findCardById(String id) {
        if (id == null) {
            return null;
        }
        if (allCards.isEmpty()) {
            allCards();
        }
        int index = findCard(allCards, id);
        return index != -1 ? allCards.get(index) : null;
    }
    
    /**
     * Procura de forma binária o objeto passado por parâmetro no array ordenado
     * passado por parâmetro.
     *
     * @param list array onde o objeto será procurado
     * @param value ID do Card a ser procurado
     * @return index do objeto para valor encontrado e -1 para não encontrado
     */
    private static int findCard(List<Card> list, String value) {
        if (list.isEmpty()) {
            return -1;
        }
        int width = list.size();
        int middle = width / 2;
        int left = ((width % 2 == 0) ? (middle - 1) : middle);
        int right = ((width % 2 == 0) ? middle : middle++);
        do {
            if (list.get(middle - 1).getId().compareTo(value) == 0) {
                return middle - 1;
            } else if (value.compareTo(list.get(middle - 1).getId()) < 0) {
                if (left == 0) {
                    return -1;
                }
                right = (left / 2);
                left = ((left % 2 == 0) ? (right - 1) : right);
                middle -= (right + 1);
            } else {
                if (right == 0) {
                    return -1;
                }
                if (right % 2 == 0) {
                    right = (right / 2);
                    left = (right - 1);
                } else {
                    right = (right / 2);
                    left = right;
                }
                middle += (left + 1);
            }
        } while (true);
    }

    /**
     * Retorna um card
     *
     * @param name nome do card
     * @return objeto Card ou null para não encontrado
     */
    public String findIdByName(String name) {
        if (allCards.isEmpty()) {
            allCards();
        }
        name = name.toLowerCase();
        for (Card card : allCards) {
            if (card.getId() != null && card.getName().toLowerCase().equals(name)) {
                return card.getId();
            }
        }
        return null;
    }

    /**
     * Procura o id de um card pelo nome removendo todos os espaços em branco
     *
     * @param name nome do card
     * @return card encontrado
     */
    public String findIdByNameNoSpaces(String name) {
        if (allCards.isEmpty()) {
            allCards();
        }
        name = name.toLowerCase().replace(" ", "");
        for (Card card : allCards) {
            if (card.getId() != null && card.getName().toLowerCase().replace(" ", "").equals(name)) {
                return card.getId();
            }
        }
        return null;
    }

    /**
     * Adiciona os cards dos conjuntos à lista de todos os cards
     */
    private void allCards() {
        allCards.addAll(basic);
        allCards.addAll(blackrockMountain);
        allCards.addAll(classic);
        allCards.addAll(credits);
        allCards.addAll(curseOfNaxxramas);
        allCards.addAll(debug);
        allCards.addAll(goblinsVsGnomes);
        allCards.addAll(heroSkins);
        allCards.addAll(leagueOfExplorers);
        allCards.addAll(missions);
        allCards.addAll(promotion);
        allCards.addAll(reward);
        allCards.addAll(system);
        allCards.addAll(tavernBrawls);
        allCards.addAll(theGrandTournaments);
        allCards.sort(Sort.id());
    }

    /**
     * Criar um card com todas as views carregadas
     *
     * @param id id do card
     * @return Objeto Card carregado e configurado
     */
    public Card createCard(String id) {
        Card c = findCardById(id);
        return c == null ? new Card(Random.lacaio()) : new Card(c);
    }

    /**
     * Criar um card com todas as views carregadas
     *
     * @param id id do card
     * @return Objeto Card carregado e configurado
     */
    public com.limagiran.hearthstoneia.card.control.Card createCardIA(String id) {
        Card c = findCardById(id);
        return c == null ? new com.limagiran.hearthstoneia.card.control.Card(Random.lacaio())
                : new com.limagiran.hearthstoneia.card.control.Card(c);
    }

    /**
     * Elimina da lista de card todos os cards que estão fora da coleção
     */
    public void filter() {
        filtrarCardForaDaColecao(basic);
        filtrarCardForaDaColecao(blackrockMountain);
        filtrarCardForaDaColecao(classic);
        filtrarCardForaDaColecao(credits);
        filtrarCardForaDaColecao(curseOfNaxxramas);
        filtrarCardForaDaColecao(debug);
        filtrarCardForaDaColecao(goblinsVsGnomes);
        filtrarCardForaDaColecao(heroSkins);
        filtrarCardForaDaColecao(leagueOfExplorers);
        filtrarCardForaDaColecao(missions);
        filtrarCardForaDaColecao(promotion);
        filtrarCardForaDaColecao(reward);
        filtrarCardForaDaColecao(system);
        filtrarCardForaDaColecao(tavernBrawls);
        filtrarCardForaDaColecao(theGrandTournaments);
        allCards();
    }

    /**
     * Elimina da lista de card todos os cards que estão fora da coleção
     *
     * @param list lista de cards
     * @return lista filtrada
     */
    private void filtrarCardForaDaColecao(List<Card> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals("DS1_178") || !Values.CARD_ID.contains(list.get(i).getId())) {
                list.remove(i);
                i--;
            }
        }
    }
}