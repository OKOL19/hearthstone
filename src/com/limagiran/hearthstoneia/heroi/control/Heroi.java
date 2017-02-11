package com.limagiran.hearthstoneia.heroi.control;

import com.limagiran.hearthstone.util.RW;
import com.limagiran.hearthstone.util.RWObj;
import com.limagiran.hearthstone.evento.AlterarCusto;
import com.limagiran.hearthstone.evento.FeiticosAtivos;
import com.limagiran.hearthstone.heroi.control.JogouCardException;
import com.limagiran.hearthstoneia.card.control.Aura;
import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstoneia.evento.*;
import com.limagiran.hearthstoneia.heroi.view.View;
import com.limagiran.hearthstone.partida.control.Efeito;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.poder.control.PoderHeroico;
import com.limagiran.hearthstone.poder.util.Util;
import static com.limagiran.hearthstone.settings.Settings.EnumSettings.TIME_ANIMATION;

import com.limagiran.hearthstoneia.server.GameCliente;
import static com.limagiran.hearthstone.util.Param.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import com.limagiran.hearthstone.util.Values;
import static com.limagiran.hearthstone.util.GamePlay.INSTANCE;

/**
 *
 * @author Vinicius Silva
 */
public final class Heroi extends View implements Serializable {

    private static Heroi instanceDefault = null;
    private Partida partida;
    private final long heroi;
    private String type;
    private final PoderHeroico poderHeroico;
    private int health;
    private int attack = 0;
    private int shield = INSTANCE.getShieldInicial();
    private int mana = 0;
    private int manaUtilizada = 0;
    private int manaBloqueadaEsteTurno = 0;
    private int manaBloqueadaTurnoAnterior = 0;
    private Card arma = null;
    private List<Card> deck;
    private final List<Card> mesa = new ArrayList<>();
    private final List<Card> mao = new ArrayList<>();
    private final List<Card> segredo = new ArrayList<>();
    private final List<Card> morto = new ArrayList<>();
    private final List<Card> cardsJogadosNaRodada = new ArrayList<>();
    private final List<Card> lacaiosMortosNaRodada = new ArrayList<>();
    private boolean imune = false;
    private boolean freeze = false;
    private boolean cumpriuFreeze = false;
    private boolean poderHeroicoUtilizado = false;
    private int poderHeroicoUtilizadoTurno;
    private int poderHeroicoUtilizadoPartida;
    private int ataquesRealizados = 0;
    private int vidaTotal;
    private final Aura aura;

    private final String nome;
    private int fadiga = 1;

    public static Heroi getDefault(long heroi) {
        if (instanceDefault == null) {
            instanceDefault = new Heroi("", heroi, Values.MAGO, new ArrayList<>());
        }
        return instanceDefault;
    }

    public Heroi(String nome, long heroi, String type, List<Card> deck) {
        this.nome = nome;
        this.heroi = heroi;
        this.type = type;
        poderHeroico = PoderHeroico.getDefault(getInstance());
        vidaTotal = INSTANCE.getVidaTotalHeroi();
        health = vidaTotal;
        this.deck = deck;
        aura = new Aura(getInstance());
        loadView(getInstance());
        refreshDanoMagico();
    }

    /**
     * Evento de jogar uma carta (qualquer carta que está na mão)
     *
     * @param id_long código da carta
     * @param index posição na mesa onde o lacaio será evocado, caso seja um
     * lacaio
     */
    public void jogarCarta(long id_long, int index) {
        AlterarCusto ac = getPartida().getAlterarCusto().clone(getPartida());
        FeiticosAtivos fa = RWObj.clone(getPartida().getFeiticosAtivos(), FeiticosAtivos.class);
        Card card = getPartida().findCardByIDLong(id_long);
        if (card != null) {
            int i = getMao().indexOf(card);
            try {
                addManaUtilizada(card.getCustoAlterado(true));
                addCardJogadoNaRodada(card);
                delMao(card);
                if (!card.isSegredo()) {
                    GameCliente.enviar(ANIMACAO_CARD_JOGADO, card.getId());
                }
                if (card.isSegredo()) {
                    utilizarSegredo(card);
                } else if (card.isArma()) {
                    setWeaponComGritoDeGuerra(card);
                } else if (card.isLacaio()) {
                    evocarComGritoDeGuerra(index, card);
                } else if (card.isFeitico()) {
                    UtilizarFeitico.processar(card);
                }
                GameCliente.setPodeEnviar(true);
                if (!card.isFeitico()) {
                    getPartida().addHistorico(getNome() + " jogou " + card.getName() + ".");
                }
            } catch (JogouCardException e) {
                addMao(i, card);
                cardsJogadosNaRodada.remove(card);
                getPartida().setAlterarCusto(ac);
                getPartida().setFeiticosAtivos(fa);
                GameCliente.clearPacotes();
                GameCliente.setPodeEnviar(true);
            }
        }
    }

    /**
     * Evocar um lacaio na mesa processando o grito de guerra dele
     *
     * @param posicao posição na mesa onde o lacaio será evocado
     * @param lacaio lacaio evocado
     * @throws JogouCardException
     */
    private void evocarComGritoDeGuerra(int posicao, Card lacaio) throws JogouCardException {
        if (getMesa().size() < 7) {
            lacaio.setTime(System.nanoTime());
            addMesa(posicao, lacaio);
            GameCliente.enviar(ANIMACAO_CARD_EVOCADO, lacaio.id_long);
            try {
                GritoDeGuerra.processar(lacaio);
                Combo.processar(lacaio);
                EscolhaUm.processar(lacaio);
                JogouCard.processar(lacaio);
                LacaioEvocado.processar(lacaio);
            } catch (JogouCardException e) {
                delMesa(lacaio);
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Evocar um lacaio na mesa
     *
     * @param posicao posição na mesa onde o lacaio será evocado
     * @param lacaio lacaio evocado
     */
    public void evocar(int posicao, Card lacaio) {
        if (getMesa().size() < 7) {
            lacaio.setTime(System.nanoTime());
            addMesa(posicao, lacaio);
            GameCliente.enviar(ANIMACAO_CARD_EVOCADO, lacaio.id_long);
            getPartida().addHistorico(lacaio.getName() + " foi evocado.");
            LacaioEvocado.processar(lacaio);
        }
    }

    /**
     * Evoca um lacaio na mesa
     *
     * @param lacaio lacaio evocado
     */
    public void evocar(Card lacaio) {
        evocar(mesa.size(), lacaio);
    }

    /**
     * Compra uma carta do deck
     *
     * @param mode modo de compra Card.FEITICO, Card.INICIO_TURNO, etc...
     * @return card comprado
     */
    public Card comprarCarta(int mode) {
        return comprarCarta(mode, 0);
    }

    /**
     * Compra uma carta do deck
     *
     * @param mode modo de compra Card.FEITICO, Card.INICIO_TURNO, etc...
     * @param index índice do card no deck
     * @return card comprado
     */
    public Card comprarCarta(int mode, int index) {
        if (!deck.isEmpty()) {
            if (index >= 0 && index < deck.size()) {
                Card comprado = deck.get(index);
                if (mode != Card.COMPRA_INICIO_TURNO && mode != Card.COMPRA_INICIO_PARTIDA) {
                    getPartida().addHistorico(getNome() + " comprou card.");
                }
                if (mode != Card.COMPRA_INICIO_PARTIDA) {
                    if (getMao().size() >= INSTANCE.getCartasNaMao()) {
                        GameCliente.exibirCardQueimado(comprado.getId(), heroi);
                    } else {
                        GameCliente.exibirCardComprado(comprado.getId(), heroi);
                    }
                    ComprouCard.processar(comprado);
                }
                if (getMao().size() >= INSTANCE.getCartasNaMao()) {
                    getPartida().addHistorico(getNome() + " teve o card queimado pois sua mão está cheia!\n" + Utils.getDescricao(getNome(), comprado));
                } else {
                    switch (mode) {
                        case Card.COMPRA_PODER_HEROICO:
                            addMao(comprado);
                            if (temNaMesa("AT_027")) {
                                mao.get(mao.size() - 1).setCusto(0);
                            }
                            break;
                        default:
                            addMao(comprado);
                            break;
                    }
                }
                delDeck(comprado);
                if (deck.isEmpty()) {
                    getPartida().addHistorico(getNome() + " não tem mais card!");
                }
                return comprado;
            }
            return null;
        } else {
            fadiga();
            return null;
        }
    }

    /**
     * Adiciona um card numa posição aleatória no deck
     *
     * @param card card adicionado
     */
    public void addCardDeckAleatoriamente(Card card) {
        adicionarCartaNoDeck(Utils.random(deck.size() + 1) - 1, card);
    }

    /**
     * Adiciona um card no deck
     *
     * @param index índice no deck onde o card será adicionado
     * @param card card adicionado
     */
    private void adicionarCartaNoDeck(int index, Card card) {
        if (index >= 0 && index <= deck.size()) {
            addDeck(index, card);
        }
    }

    /**
     * Realiza o procedimento padrão para quando um herói ataca
     *
     * @param alvo alvo atacado
     */
    public void atacou(long alvo) {
        if (arma != null) {
            //Uivo Sangrento (Atacar um lacaio custa 1 de Ataque em vez de 1 de Durabilidade)
            if (arma.getId().equals("EX1_411") && alvo != OPONENTE) {
                arma.delAtaque(1);
            } else {
                arma.delDurabilidade(1);
            }
        }
    }

    /**
     * Arma equipada
     *
     * @return Arma equipada
     */
    public Card getArma() {
        return arma;
    }

    /**
     * Altera a arma equipada
     *
     * @param arma nova arma
     */
    public void setArma(Card arma) {
        if (getArma() != null) {
            GameCliente.addHistorico(getArma().getName() + " foi destruída.", false);
            //UltimoSuspiro.processar(this.arma, 0);
            getPartida().getJogada().ultimoSuspiro(0, this.arma);
        }
        this.arma = arma;
        if (getArma() != null) {
            GameCliente.addHistorico(getNome() + " equipou " + getArma().getName() + ".", false);
            ArmaEquipada.processar(this);
            setAtaquesRealizados(getAtaquesRealizados());
        }
    }

    /**
     * Executa o procedimento de equipar uma arma executando o grito de guerra
     *
     * @param arma arma sendo equipada
     * @throws JogouCardException caso o grito de guerra seja cancelado
     */
    private void setWeaponComGritoDeGuerra(Card arma) throws JogouCardException {
        if (arma != null) {
            GritoDeGuerra.processar(arma);
            Combo.processar(arma);
            EscolhaUm.processar(arma);
            JogouCard.processar(arma);
        }
        setArma(arma);
        GameCliente.enviar(getHeroi() != HEROI ? HEROI_SET_ARMA : OPONENTE_SET_ARMA,
                this.arma == null ? "null" : this.arma.id_long);
        if (getArma() != null) {
            getArma().setTime(System.nanoTime());
        }
    }

    /**
     * Executa o procedimento de equipar uma arma
     *
     * @param arma arma sendo equipada
     */
    public void setWeapon(Card arma) {
        setArma(arma);
        GameCliente.enviar(getHeroi() != HEROI ? HEROI_SET_ARMA : OPONENTE_SET_ARMA,
                this.arma == null ? "null" : this.arma.id_long);
        if (getArma() != null) {
            getArma().setTime(System.nanoTime());
        }
    }

    public List<Card> getDeck() {
        return new ArrayList<>(deck);
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
        getPartida().addAllCards(this.deck);
    }

    public void addDeck(int index, Card card) {
        deck.add(index, card);
        Pacote pack = new Pacote(getHeroi() != HEROI
                ? HEROI_SET_DECK_ADD
                : OPONENTE_SET_DECK_ADD);
        pack.set(VALUE, card.id_long);
        pack.set(INDEX_CARTA, index);
        GameCliente.enviar(pack);
    }

    public void delDeck(Card card) {
        deck.remove(card);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_DECK_REMOVE
                : OPONENTE_SET_DECK_REMOVE, card.id_long);
    }

    public List<Card> getSegredo() {
        return new ArrayList<>(segredo);
    }

    public void addSegredo(Card card) {
        card.setTime(System.nanoTime());
        segredo.add(card);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_SEGREDO_ADD
                : OPONENTE_SET_SEGREDO_ADD, card.id_long);
        GameCliente.addHistorico(getNome() + " adicionou um segredo.", false);
    }

    public void delSegredo(Card card) {
        this.segredo.remove(card);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_SEGREDO_REMOVE
                : OPONENTE_SET_SEGREDO_REMOVE, card.id_long);
    }

    public List<Card> getMesa() {
        return new ArrayList<>(mesa);
    }

    public void addMesa(int index, Card card) {
        card.setTime(System.nanoTime());
        index = index < 0 || index > mesa.size() ? mesa.size() : index;
        mesa.add(index, card);
        Pacote pack = new Pacote(getHeroi() != HEROI
                ? HEROI_SET_MESA_ADD
                : OPONENTE_SET_MESA_ADD);
        pack.set(VALUE, card.id_long);
        pack.set(INDEX_CARTA, index);
        GameCliente.enviar(pack);
    }

    public void delMesa(Card card) {
        mesa.remove(card);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_MESA_REMOVE
                : OPONENTE_SET_MESA_REMOVE, card.id_long);
    }

    public boolean temEspacoNaMesa() {
        return getMesa().size() < 7;
    }

    public List<Card> getMao() {
        return new ArrayList<>(mao);
    }

    public void addMao(Card card) {
        if (getMao().size() < INSTANCE.getCartasNaMao()) {
            mao.add(card);
            GameCliente.enviar(getHeroi() != HEROI
                    ? HEROI_SET_MAO_ADD
                    : OPONENTE_SET_MAO_ADD, card.id_long);
        }
    }

    public void addMao(int index, Card card) {
        if (getMao().size() < INSTANCE.getCartasNaMao() && index != -1 && mao.size() > index) {
            mao.add(index, card);
            Pacote pack = new Pacote(getHeroi() != HEROI
                    ? HEROI_SET_MAO_INDEX_ADD
                    : OPONENTE_SET_MAO_INDEX_ADD);
            pack.set(CARD_ID_LONG, card.id_long);
            pack.set(INDEX_CARTA, index);
            GameCliente.enviar(pack);
        }
    }

    public void delMao(Card card) {
        mao.remove(card);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_MAO_REMOVE
                : OPONENTE_SET_MAO_REMOVE, card.id_long);
    }

    /**
     * Descarta um card da mão
     *
     * @param card card descartado
     */
    public void descartar(Card card) {
        delMao(card);
        CardDescartado.processar(this);
        GameCliente.addHistorico(getNome() + " descartou " + Utils.getDescricao("", card), true);
    }

    /**
     * Descarta um card aleatório da mão
     *
     * @return {@code true} para card descartado. {@code false} o contrário.
     */
    public boolean descartar() {
        int random = Utils.random(getMao().size()) - 1;
        if (random >= 0) {
            Card descartar = getMao().get(random);
            delMao(descartar);
            CardDescartado.processar(this);
            GameCliente.addHistorico(getNome() + " descartou " + Utils.getDescricao("", descartar), true);
            return true;
        }
        return false;
    }

    public List<Card> getMorto() {
        return new ArrayList<>(morto);
    }

    public void addMorto(Card card) {
        morto.add(card);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_MORTO_ADD
                : OPONENTE_SET_MORTO_ADD, card.id_long);
    }

    public void delMorto(Card card) {
        morto.remove(card);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_MORTO_REMOVE
                : OPONENTE_SET_MORTO_REMOVE, card.id_long);
    }

    /**
     * Retira o lacaio da mesa.
     *
     * @param lacaio lacaio morto
     * @param animacao {@code true} ou {@code false} = executar a animação do
     * lacaio morrendo.
     * @return
     */
    public int morreu(Card lacaio, boolean animacao) {
        int retorno = getMesa().indexOf(lacaio);
        if (animacao) {
            GameCliente.enviar(ANIMACAO_CARD_MORREU, lacaio.id_long);
        }
        delMesa(lacaio);
        if (!morto.contains(lacaio)) {
            addMorto(lacaio);
        }
        lacaiosMortosNaRodada.add(lacaio);
        return retorno;
    }

    public int getAttack() {
        return attack + aura.getAtaque() + aura.getDanoAdicional() + (arma != null ? arma.getAtaque() : 0);
    }

    public void setAttack(int attack) {
        this.attack = attack;
        this.attack = this.attack < 0 ? 0 : this.attack;
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_ATAQUE
                : OPONENTE_SET_ATAQUE, this.attack);
        if ((getHeroi() == HEROI && getPartida().isVezHeroi())
                || getHeroi() == OPONENTE && !getPartida().isVezHeroi()) {
            setAtaquesRealizados(getAtaquesRealizados());
        }
    }

    public void addAttack(int attack) {
        setAttack(this.attack + attack);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_ATAQUE
                : OPONENTE_SET_ATAQUE, this.attack);
        getPanelHeroi().getAnimacao().ataque(attack, TIME_ANIMATION.getLong());
        setAtaquesRealizados(getAtaquesRealizados());
    }

    public void delAttack(int attack) {
        setAttack(this.attack - attack);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_ATAQUE
                : OPONENTE_SET_ATAQUE, this.attack);
    }

    public int getHealth() {
        return health + aura.getVida();
    }

    public void setHealth(int health) {
        int anterior = getHealth();
        this.health = health;
        this.health = this.health > getVidaTotal() ? getVidaTotal() : this.health;
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_HEALTH
                : OPONENTE_SET_HEALTH, this.health);
        if (this.health < anterior) {
            GameCliente.addHistorico(getNome() + " recebeu " + (anterior - getHealth()) + " de dano.", false);
            PersonagemRecebeuDano.processar(this, null, anterior - getHealth(), null);
        } else if (this.health > anterior) {
            PersonagemFoiCurado.processar(this, null, null);
            GameCliente.addHistorico(getNome() + " teve " + (getHealth() - anterior) + " de vida restaurada.", false);
        }
    }

    public void addHealth(int health) {
        if (this.health < getVidaTotal()) {
            setHealth(this.health + health);
        }
        getPanelHeroi().getAnimacao().vida(health, TIME_ANIMATION.getLong());
    }

    public void delHealth(int damage) {
        if (!isImune() && !aura.isImune()) {
            damage = AlterarDano.processar(this, damage);
            if (damage > 0 && !ReceberDanoNoLugarDoHeroi.processar(this, damage)) {
                if ((getHealth() + getShield()) > damage || !ex1_295()) {
                    if (shield > 0) {
                        delShield(damage);
                        if (shield < 0) {
                            setHealth(this.health + shield);
                            GameCliente.enviar(getHeroi() != HEROI
                                    ? HEROI_SET_HEALTH
                                    : OPONENTE_SET_HEALTH, this.health);
                            setShield(0, true);
                        }
                    } else {
                        setHealth(this.health - damage);
                        GameCliente.enviar(getHeroi() != HEROI
                                ? HEROI_SET_HEALTH
                                : OPONENTE_SET_HEALTH, this.health);
                    }
                    getPanelHeroi().getAnimacao().vida(-damage, TIME_ANIMATION.getLong());
                }
            }
        } else {
            GameCliente.addHistorico(getNome() + " não recebeu dano pois está imune.", false);
            getPanelHeroi().getAnimacao().vida(0, TIME_ANIMATION.getLong());
        }
        if (getHealth() <= 0) {
            getPartida().setVencedor(heroi == OPONENTE
                    ? getPartida().getPlayer()
                    : (getPartida().getPlayer() + 1) % 2);
        }
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield, boolean flag) {
        this.shield = shield;
        if (flag) {
            GameCliente.enviar(getHeroi() != HEROI
                    ? HEROI_SET_SHIELD
                    : OPONENTE_SET_SHIELD, this.shield);
        }
    }

    public void addShield(int shield) {
        setShield(this.shield + shield, true);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_SHIELD
                : OPONENTE_SET_SHIELD, this.shield);
        getPanelHeroi().getAnimacao().escudo(shield, TIME_ANIMATION.getLong());
        getPartida().addHistorico(getNome() + " ganhou " + shield + " de armadura.");
    }

    public void delShield(int shield) {
        int anterior = this.shield;
        setShield(this.shield - shield, true);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_SHIELD
                : OPONENTE_SET_SHIELD, this.shield);
        getPartida().addHistorico(getNome() + " perdeu " + (this.shield <= 0 ? anterior : anterior - this.shield) + " de armadura.");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_TYPE
                : OPONENTE_SET_TYPE, this.type);
    }

    public int getManaDisponivel() {
        return (mana - manaUtilizada) - manaBloqueadaTurnoAnterior;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        if (mana <= 10) {
            this.mana = mana;
            this.mana = this.mana <= 10 ? this.mana : 10;
            GameCliente.enviar(getHeroi() != HEROI
                    ? HEROI_SET_MANA
                    : OPONENTE_SET_MANA, this.mana);
        }
    }

    public void addMana(int mana) {
        setMana(this.mana + mana);
    }

    public void delMana(int mana) {
        setMana(this.mana - mana);
    }

    public int getManaUtilizada() {
        return manaUtilizada;
    }

    public void setManaUtilizada(int manaUtilizada) {
        this.manaUtilizada = manaUtilizada < 0 ? 0 : manaUtilizada;
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_MANA_UTILIZADA
                : OPONENTE_SET_MANA_UTILIZADA, this.manaUtilizada);
    }

    public void addManaUtilizada(int manaUtilizada) {
        setManaUtilizada(this.manaUtilizada + manaUtilizada);
    }

    public void delManaUtilizada(int manaUtilizada) {
        setManaUtilizada(this.manaUtilizada - manaUtilizada);
    }

    public int getManaBloqueadaEsteTurno() {
        return manaBloqueadaEsteTurno;
    }

    public void setManaBloqueadaEsteTurno(int manaBloqueada) {
        this.manaBloqueadaEsteTurno = manaBloqueada;
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_MANA_BLOQ_ESTE_TURNO
                : OPONENTE_SET_MANA_BLOQ_ESTE_TURNO, this.manaBloqueadaEsteTurno);
    }

    public void addManaBloqueadaEsteTurno(int manaBloqueadaEsteTurno) {
        setManaBloqueadaEsteTurno(this.manaBloqueadaEsteTurno + manaBloqueadaEsteTurno);
    }

    public void delManaBloqueadaEsteTurno(int manaBloqueadaEsteTurno) {
        setManaBloqueadaEsteTurno(this.manaBloqueadaEsteTurno - manaBloqueadaEsteTurno);
    }

    public void setManaBloqueadaTurnoAnterior(int manaBloqueadaTurnoAnterior) {
        this.manaBloqueadaTurnoAnterior = manaBloqueadaTurnoAnterior;
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_MANA_BLOQ_TURNO_ANT
                : OPONENTE_SET_MANA_BLOQ_TURNO_ANT, this.manaBloqueadaTurnoAnterior);
    }

    public int getAtaquesRealizados() {
        return ataquesRealizados;
    }

    public void setAtaquesRealizados(int ataques_realizados) {
        this.ataquesRealizados = ataques_realizados;
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_ATAQUES_REALIZADOS
                : OPONENTE_SET_ATAQUES_REALIZADOS, this.ataquesRealizados);
        setInvestidaVisible(!isFreeze() && (getAtaquesRealizados() == 0
                || (getAtaquesRealizados() == 1 && arma != null && arma.isFuriaDosVentos())));
    }

    public void addAtaquesRealizados(int ataquesRealizados) {
        setAtaquesRealizados(getAtaquesRealizados() + ataquesRealizados);
    }

    public boolean isImune() {
        return imune;
    }

    public void setImune(boolean imune) {
        this.imune = imune;
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_IMUNE
                : OPONENTE_SET_IMUNE, this.imune);
    }

    public int getDanoMagico() {
        return getPanelDanoMagico().getDano();
    }

    /**
     * Profeta Velen (Dobre o dano e a cura dos seus feitiços e Poder Heroico)
     *
     * @param dano dano inicial
     * @return dano alterado
     */
    public int getDobrarDanoECura(int dano) {
        List<Card> list = getMesa().subList(0, getMesa().size());
        int dobrar = 0;
        for (Card c : list) {
            //Profeta Velen (Dobre o dano e a cura dos seus feitiços e Poder Heroico)
            if (c.getId().equals("EX1_350")) {
                dobrar++;
            }
        }
        return dano * (int) Math.pow(2, dobrar);
    }

    public void addCardJogadoNaRodada(Card card) {
        cardsJogadosNaRodada.add(card);
    }

    public List<Card> getCardsJogadosNaRodada() {
        return new ArrayList<>(cardsJogadosNaRodada);
    }

    public Long getLacaiosJogadosNaRodada() {
        return getCardsJogadosNaRodada().stream().filter((card) -> card.isLacaio()).count();
    }

    public void clearCardsJogadosNaRodada() {
        cardsJogadosNaRodada.clear();
    }

    /**
     * Altera o poder heroico
     *
     * @param type novo tipo do poder heroico. Ex.: PoderHeroico.DRUID_DEFAULT,
     * PoderHeroico.HUNTER_MELHORADO, PoderHeroico.PRIEST_EX1_625
     */
    public void setPoderHeroico(String type) {
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_TIPO_PODER
                : OPONENTE_SET_TIPO_PODER, type);
        getPoderHeroico().setTipo(type);
    }

    public boolean isPoderHeroicoUtilizado() {
        return !getPoderHeroico().getPoder().isAtivado();
    }

    public void setPoderHeroicoUtilizado(boolean poderHeroicoUtilizado) {
        this.poderHeroicoUtilizado = poderHeroicoUtilizado;
        getPoderHeroico().setAtivado(!this.poderHeroicoUtilizado);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_PODER_UTILIZADO
                : OPONENTE_SET_PODER_UTILIZADO, this.poderHeroicoUtilizado);
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
        getPanelHeroi().setFreeze(this.freeze);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_FREEZE
                : OPONENTE_SET_FREEZE, this.freeze);
        if (freeze) {
            setCumpriuFreeze(false);
        }
    }

    public boolean isCumpriu_freeze() {
        return cumpriuFreeze;
    }

    public void setCumpriuFreeze(boolean cumpriu_freeze) {
        this.cumpriuFreeze = cumpriu_freeze;
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_CUMPRIU_FREEZE
                : OPONENTE_SET_CUMPRIU_FREEZE, this.cumpriuFreeze);
    }

    public boolean isInvestida() {
        return isInvestidaView();
    }

    /**
     * Poder heroico utilizado no turno atual
     *
     * @return quantidade de vezes que o poder heroico foi utilizado no turno
     * atual
     */
    public int getPoderHeroicoUtilizadoTurno() {
        return poderHeroicoUtilizadoTurno;
    }

    private void setPoderHeroicoUtilizadoTurno(int poderHeroicoUtilizadoTurno) {
        this.poderHeroicoUtilizadoTurno = poderHeroicoUtilizadoTurno;
    }

    /**
     * Poder heroico utilizado na partida inteira
     *
     * @return quantidade de vezes que o poder heroico foi utilizado na partida
     * inteira
     */
    public int getPoderHeroicoUtilizadoPartida() {
        return poderHeroicoUtilizadoPartida;
    }

    private void setPoderHeroicoUtilizadoPartida(int poderHeroicoUtilizadoPartida) {
        this.poderHeroicoUtilizadoPartida = poderHeroicoUtilizadoPartida;
    }

    /**
     * Executa o procedimento padrão para o poder heroico utilizado
     */
    public void poderHeroicoUtilizado() {
        setPoderHeroicoUtilizado(true);
        setPoderHeroicoUtilizadoTurno(getPoderHeroicoUtilizadoTurno() + 1);
        setPoderHeroicoUtilizadoPartida(getPoderHeroicoUtilizadoPartida() + 1);
    }

    /**
     * Verifica se o herói ou os lacaios da mesa estão congelados, se sim,
     * verifica se está na hora de descongelar
     */
    public void descongelar() {
        if (freeze && cumpriuFreeze) {
            setFreeze(false);
        } else if (freeze && !cumpriuFreeze) {
            setCumpriuFreeze(true);
        }
        mesa.stream().forEach((c) -> {
            if (c.isFreeze() && c.isCumpriuFreeze()) {
                c.setFreeze(false);
            } else if (c.isFreeze() && !c.isCumpriuFreeze()) {
                c.setCumpriuFreeze(true);
            }
        });
    }

    public Aura getAura() {
        return aura;
    }

    /**
     * Executar os procedimentos padrões para o início de um turno. Descongelar,
     * efeitos programados, mana, etc...
     */
    public void inicioTurno() {
        descongelar();
        InicioDoTurno.processar(getPartida());
        getPartida().getEfeitoProgramado().processar(Efeito.INICIO_TURNO);
        getPartida().getJogada().ultimoSuspiro();
        setPoderHeroicoUtilizado(false);
        setPoderHeroicoUtilizadoTurno(0);
        setManaUtilizada(0);
        setManaBloqueadaTurnoAnterior(getManaBloqueadaEsteTurno());
        setManaBloqueadaEsteTurno(0);
        addMana(1);
        comprarCarta(Card.COMPRA_INICIO_TURNO);
        for (Card c : mesa) {
            c.setAtaquesRealizados(0);
            c.setAtacou(false);
        }
        setAtaquesRealizados(0);
    }

    /**
     * Executa os procedimentos padrões para o fim de um turno
     */
    public void fimTurno() {
        getPartida().getPodeAtacar().getPodemAtacarNesteTurno().clear();
        for (Card c : mesa) {
            c.setAtaquesRealizados(4);
        }
        setInvestidaVisible(false);
        setPoderHeroicoUtilizado(true);
        getPartida().getFeiticosAtivos().clear();
    }

    /**
     * Sacerdalma Auchenai (Seus cards e poderes que restauram Vida agora deixam
     * de restaurar e causam dano)
     *
     * @return {@code true} ou {@code false}
     */
    public boolean curaIsDano() {
        //Sacerdalma Auchenai (Seus cards e poderes que restauram Vida agora deixam de restaurar e causam dano)
        return temNaMesa("EX1_591");

    }

    /**
     * Posiçao do lacaio na mesa
     *
     * @param id id_long do lacaio
     * @return índice do lacaio na mesa
     */
    public int getPosicaoNaMesa(long id) {
        for (int index = 0; index < mesa.size(); index++) {
            if (mesa.get(index).id_long == id) {
                return index;
            }
        }
        return 0;
    }

    /**
     * Verifica se o herói é dono de um card
     *
     * @param id id_long do card
     * @return {@code true} ou {@code false}
     */
    public boolean isCard(long id) {
        List<Card> cards = new ArrayList<>();
        cards.add(arma);
        cards.addAll(cardsJogadosNaRodada);
        cards.addAll(deck);
        cards.addAll(mao);
        cards.addAll(mesa);
        cards.addAll(segredo);
        cards.addAll(morto);
        return cards.stream().anyMatch((c) -> (c != null && c.id_long == id));
    }

    public String getNome() {
        return nome;
    }

    public long getHeroi() {
        return heroi;
    }

    public Partida getPartida() {
        return partida != null ? partida : Partida.getDefault();
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    private Heroi getInstance() {
        return this;
    }

    private void refreshDanoMagico() {
        new Thread(() -> {
            while (GameCliente.onLine) {
                final int dano = getMesa().stream().map((c) -> c.getDanoMagico()).reduce(0, Integer::sum);
                getPanelDanoMagico().setDano(dano);
                getPanelDanoMagico().setVisible(dano > 0);
                getPanelDanoMagico().repaint();
                Utils.sleep(500);
            }
        }).start();
    }

    /**
     * Verifica se existe um card de ID específico na mesa
     *
     * @param id ID do card
     * @return {@code true} ou {@code false}
     */
    public boolean temNaMesa(String id) {
        return mesa.stream().anyMatch((c) -> (c.getId().equals(id) && !c.isSilenciado()));
    }

    /**
     * Verifica se existe um segredo de ID específico nos segredos ativados
     *
     * @param id ID do card
     * @return {@code true} ou {@code false}
     */
    public boolean temSegredoAtivado(String id) {
        return segredo.stream().anyMatch((c) -> (c.getId().equals(id)));
    }

    public List<Card> getLacaiosMortosNaRodada() {
        return new ArrayList<>(lacaiosMortosNaRodada);
    }

    public void lacaiosMortosNaRodadaRemoveAll() {
        while (!lacaiosMortosNaRodada.isEmpty()) {
            lacaiosMortosNaRodada.remove(0);
        }
    }

    /**
     * Procedimento padrão para destruir uma arma, caso tenha uma equipada
     *
     * @return {@code true} para arma destruída. {@code false} o contrário.
     */
    public boolean destruirArma() {
        if (getArma() != null) {
            setWeapon(null);
            return true;
        }
        return false;
    }

    /**
     * Bloco de Gelo (Segredo: Quando seu herói receber dano fatal, ignore-o e
     * fique Imune até o fim do turno)
     *
     * @return true para segredo em jogo; false para segredo não ativado
     */
    private boolean ex1_295() {
        for (Card secret : segredo) {
            if (secret.getId().equals("EX1_295")) {
                GameCliente.exibirSegredoRevelado(secret.getId(), secret.getDono().getHeroi());
                getPartida().getEfeitoProgramado().concederImunidadeHeroiUmTurno(
                        getPartida().getHero().equals(this)
                        ? getPartida().getPlayer()
                        : (getPartida().getPlayer() + 1) % 2);
                SegredoRevelado.processar(secret);
                return true;
            }
        }
        return false;
    }

    public boolean isIleso() {
        return getHealth() == getVidaTotal();
    }

    public boolean temRacaNaMesa(String race) {
        return getMesa().stream().anyMatch((lacaio) -> (lacaio.getRace() != null && lacaio.getRace().equals(race)));
    }

    public boolean temFeraNaMesa() {
        return temRacaNaMesa(Values.FERA);
    }

    public boolean temDemonioNaMesa() {
        return temRacaNaMesa(Values.DEMONIO);
    }

    public boolean temDragaoNaMesa() {
        return temRacaNaMesa(Values.DRAGAO);
    }

    public boolean temMecanoideNaMesa() {
        return temRacaNaMesa(Values.MECANOIDE);
    }

    public boolean temMurlocNaMesa() {
        return temRacaNaMesa(Values.MURLOC);
    }

    public boolean temPirataNaMesa() {
        return temRacaNaMesa(Values.PIRATA);
    }

    public boolean temTotemNaMesa() {
        return temRacaNaMesa(Values.TOTEM);
    }

    public boolean temRacaNaMesa(String race, Card excluir) {
        return getMesa().stream().anyMatch((lacaio) -> (lacaio.getRace() != null
                && lacaio.getRace().equals(race) && !lacaio.equals(excluir)));
    }

    public boolean temFeraNaMesa(Card excluir) {
        return temRacaNaMesa(Values.FERA, excluir);
    }

    public boolean temDemonioNaMesa(Card excluir) {
        return temRacaNaMesa(Values.DEMONIO, excluir);
    }

    public boolean temDragaoNaMesa(Card excluir) {
        return temRacaNaMesa(Values.DRAGAO, excluir);
    }

    public boolean temMecanoideNaMesa(Card excluir) {
        return temRacaNaMesa(Values.MECANOIDE, excluir);
    }

    public boolean temMurlocNaMesa(Card excluir) {
        return temRacaNaMesa(Values.MURLOC, excluir);
    }

    public boolean temPirataNaMesa(Card excluir) {
        return temRacaNaMesa(Values.PIRATA, excluir);
    }

    public boolean temTotemNaMesa(Card excluir) {
        return temRacaNaMesa(Values.TOTEM, excluir);
    }

    public void setVidaTotal(int vidaTotal, boolean flag) {
        this.vidaTotal = vidaTotal;
        this.health = this.vidaTotal;
        if (flag) {
            GameCliente.enviar(getHeroi() != HEROI
                    ? HEROI_SET_VIDA_TOTAL
                    : OPONENTE_SET_VIDA_TOTAL, this.vidaTotal);
            GameCliente.enviar(getHeroi() != HEROI
                    ? HEROI_SET_HEALTH
                    : OPONENTE_SET_HEALTH, this.health);
        }
    }

    public int getVidaTotal() {
        return vidaTotal;
    }

    public PoderHeroico getPoderHeroico() {
        return poderHeroico;
    }

    public JPanel getPanelPoder() {
        return getPoderHeroico().getPanelPoderHeroico();
    }

    /**
     * Altera o poder heroico para a versão melhorada dele
     */
    public void melhorarPoderHeroico() {
        String melhorado = Util.poderMelhorado(getPoderHeroico().getTipo());
        if (!getPoderHeroico().getTipo().equals(melhorado)) {
            setPoderHeroico(melhorado);
        }
    }

    public String getToString() {
        return Values.TO_STRING_HEROI + heroi;
    }

    /**
     * Método para ativar um segredo
     *
     * @param segredo segredo ativado
     * @exception heroi.control.JogouCardException "Você não pode ter 2 segredos
     * iguais em jogo!"
     */
    private void utilizarSegredo(Card segredo) throws JogouCardException {
        if (temSegredoAtivado(segredo.getId())) {
            throw new JogouCardException("Você não pode ter 2 segredos iguais em jogo!");
        } else {
            GameCliente.setPodeEnviar(true);
            if (!UtilizarFeitico.anularSegredo(getPartida(), segredo)) {
                GameCliente.enviar(ANIMACAO_CARD_SEGREDO_ATIVADO, "null");
                addSegredo(segredo);
            } else {
                GameCliente.enviar(ANIMACAO_CARD_JOGADO, segredo.getId());
            }
            JogouCard.processar(segredo);
        }
    }

    private void fadiga() {
        getPartida().addHistorico(getNome() + " - fadiga = " + fadiga + ".");
        GameCliente.exibirDanoFadiga(fadiga, heroi);
        delHealth(fadiga);
        GameCliente.enviar(getHeroi() != HEROI
                ? HEROI_SET_FADIGA
                : OPONENTE_SET_FADIGA, ++fadiga);
    }
    
    public void setFadiga(int fadiga) {
        this.fadiga = fadiga;
    }

}