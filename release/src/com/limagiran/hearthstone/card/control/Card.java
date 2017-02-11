package com.limagiran.hearthstone.card.control;

import static com.limagiran.hearthstone.HearthStone.CARTAS;
import static com.limagiran.hearthstone.settings.Settings.EnumSettings.TIME_ANIMATION;
import com.limagiran.hearthstone.card.view.Animacao;
import com.limagiran.hearthstone.card.view.View;
import com.limagiran.hearthstone.util.Utils;
import com.google.gson.annotations.Expose;
import com.limagiran.hearthstone.evento.*;
import com.limagiran.hearthstone.util.*;
import com.limagiran.hearthstone.partida.control.*;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.io.Serializable;
import java.util.*;
import com.limagiran.hearthstone.server.GameCliente;

public final class Card extends View implements Serializable, Values {

    private String id; //ID HearthStone do Card    
    private String name; //Nome do Card
    private int cost; //Custo de mana do cartão
    private String type; //Tipo de Card. Exemplo: Lacaio, Feitiço, Arma, etc.
    private String rarity; //Raridade do Card. Comum, Raro, Épico e Lendário
    private String faction; //A facção do cartão. Values de exemplo: aliança, horda, Neutro
    private String race; //A raça do Card. Exemplo: Murloc, totem, pirata, Dragão, etc.
    private String playerClass; //A classe de herói do Card. Exemplo: Druida, Caçador, Mago, Paladino, etc.
    private String text; //O texto do Card quando ele estiver em sua mão.
    private String inPlayText; //O texto do cartão quando ele estiver em jogo.
    private ArrayList<String> mechanics; //A mecânica do Card. Exemplo: Combo, Segredo, Grito de Guerra, Provocar, etc.
    private String flavor; //Bordão do Card
    private String artist; //Artista do Card
    private int attack; //Ataque do Card
    private int health; //Vida do Card
    private int durability; //Durabilidade do Card
    private boolean collectible; //Se o Card é colecionável, ou seja, pode ser adquirido.
    private boolean elite; //Se o Card é de elite.
    private String howToGet; //Como obter o Card. Valor válido apenas para cards que não são adquiridos com abertura de pacote.
    private String howToGetGold; //Como obter a versão dourada do Card. Valor válido apenas para cards que não são adquiridos com abertura de pacote.

    public static final int PLAYER_ONE = 0;
    public static final int PLAYER_TWO = 1;
    public static final int COMPRA_INICIO_PARTIDA = 0;
    public static final int COMPRA_INICIO_TURNO = 1;
    public static final int COMPRA_PODER_HEROICO = 2;
    public static final int COMPRA_FEITICO = 3;
    public static final int COMPRA_EVENTO = 4;
    public static final int COMPRA_AURA = 5;
    public static final int MORREU = 0;
    public static final int MORREU_EM_AREA = 1;

    public int DURABILITY_DEFAULT;

    private int player = -1;
    private int vidaOriginal;
    private int vidaMaxima;
    private int vidaAtual;
    private int ataque;
    private int custo;
    private int ataquesRealizados;
    private int ataqueEnfurecer = 0;
    private int danoMagico;
    private int turnosNaMesa = 0;
    private boolean imune = false;
    private boolean sleep = true;
    private boolean freeze = false;
    private boolean cumpriuFreeze = false;
    private boolean silenciado = false;
    private boolean ultimoSuspiroReviver = false;
    private boolean atacou = false;

    @Expose(serialize = false, deserialize = false)
    private List<String> evocarUltimoSuspiro = new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    private List<String> addDeckUltimoSuspiro = new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    private List<String> addMaoUltimoSuspiro = new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    private List<String[]> fazerAoAtacar = new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    private Partida partida;

    public long id_long;
    private long time;

    private int vidaAnterior;

    private boolean flag_ui = false;

    @Expose(serialize = false, deserialize = false)
    private Aura aura;

    /**
     * Construtor padrão
     */
    public Card() {
    }

    /**
     * Criar um objeto padrão
     *
     * @return
     */
    public static Card createDefault() {
        Card retorno = new Card();
        retorno.setId("default");
        retorno.setAura();
        return retorno;
    }

    /**
     * Criar um novo card
     *
     * @param c novo card
     */
    public Card(Card c) {
        copiarAtributosBasicos(c);
        vidaMaxima = health;
        vidaOriginal = health;
        vidaAtual = health;
        DURABILITY_DEFAULT = durability;
        ataque = attack;
        custo = cost;
        ataquesRealizados = mechanics.contains(FURIA_DOS_VENTOS) ? 2 : 1;
        ataquesRealizados = mechanics.contains(INVESTIDA) ? 0 : ataquesRealizados;
        danoMagico = mechanics.contains(PODER_MAGICO) ? getPoderMagico() : 0;
        id_long = System.nanoTime();
        aura = new Aura(getInstance());
        time = id_long;
        init(getInstance());
    }

    /**
     * Transformar um lacaio em outro
     *
     * @param c lacaio em que será transformado
     */
    public void polimorfia(Card c) {
        flag_ui = false;
        copiarAtributosBasicos(c);
        vidaOriginal = c.health;
        vidaMaxima = c.health;
        vidaAtual = c.health;
        DURABILITY_DEFAULT = c.DURABILITY_DEFAULT;
        evocarUltimoSuspiro = new ArrayList<>();
        addDeckUltimoSuspiro = new ArrayList<>();
        addMaoUltimoSuspiro = new ArrayList<>();
        fazerAoAtacar = new ArrayList<>();
        player = -1;
        ataque = attack;
        custo = cost;
        turnosNaMesa = 0;
        ataquesRealizados = mechanics.contains(FURIA_DOS_VENTOS) ? 2 : 1;
        ataquesRealizados = mechanics.contains(INVESTIDA) ? 0 : ataquesRealizados;
        danoMagico = mechanics.contains(PODER_MAGICO) ? getPoderMagico() : 0;
        ataqueEnfurecer = 0;
        imune = false;
        sleep = true;
        freeze = false;
        cumpriuFreeze = false;
        silenciado = false;
        ultimoSuspiroReviver = false;
        atacou = false;
        aura = new Aura(getInstance());
        init(getInstance());
        getAnimacao().evocado(false);
        getDono().getPanelMesa().atualizar();
    }

    /**
     * Tornar este lacaio cópia de outro
     *
     * @param c lacaio que será copiado
     */
    public void copia(Card c) {
        flag_ui = false;
        copiarAtributosBasicos(c);
        vidaOriginal = c.vidaOriginal;
        vidaMaxima = c.vidaMaxima;
        vidaAtual = c.vidaAtual;
        DURABILITY_DEFAULT = c.DURABILITY_DEFAULT;
        evocarUltimoSuspiro = c.evocarUltimoSuspiro;
        addDeckUltimoSuspiro = c.addDeckUltimoSuspiro;
        addMaoUltimoSuspiro = c.addMaoUltimoSuspiro;
        fazerAoAtacar = c.fazerAoAtacar;
        player = c.player;
        ataque = c.ataque;
        custo = c.custo;
        turnosNaMesa = 0;
        ataquesRealizados = c.ataquesRealizados;
        danoMagico = c.danoMagico;
        ataqueEnfurecer = c.ataqueEnfurecer;
        imune = c.imune;
        sleep = c.sleep;
        freeze = c.freeze;
        cumpriuFreeze = c.cumpriuFreeze;
        silenciado = c.silenciado;
        ultimoSuspiroReviver = c.ultimoSuspiroReviver;
        atacou = c.atacou;
        aura = new Aura(getInstance());
        init(getInstance());
        getAnimacao().evocado(false);
        getDono().getPanelMesa().atualizar();
    }

    /**
     * Copia todos os atributos básicos de um card
     *
     * @param c card
     */
    private void copiarAtributosBasicos(Card c) {
        id = c.id;
        name = c.name;
        cost = c.cost;
        type = c.type;
        rarity = c.rarity;
        faction = c.faction;
        race = c.race;
        playerClass = c.playerClass;
        text = c.text;
        inPlayText = c.inPlayText;
        flavor = c.flavor;
        artist = c.artist;
        attack = c.attack;
        health = c.health;
        durability = c.durability;
        collectible = c.collectible;
        elite = c.elite;
        howToGet = c.howToGet;
        howToGetGold = c.howToGetGold;
        ArrayList<String> temp = ((ArrayList<String>) c.getMechanics().clone());
        mechanics = temp != null ? temp : new ArrayList<>();
    }

    public int getVida() {
        return vidaAtual + getAura().getVida();
    }

    public int getVidaSemAura() {
        return vidaAtual;
    }

    public void setVida(int vida) {
        setVida(vida, true);
    }

    public void setVida(int vida, boolean animacao) {
        vidaAnterior = getVida();
        if (vida <= 0) {
            if (getPartida().getFeiticosAtivos().isNEW1_036() && getPartida().getHero().isCard(id_long)) {
                vida = 1;
            }
        }
        vidaAtual = vida;
        vidaAtual = vidaAtual > vidaMaxima ? vidaMaxima : vidaAtual;
        enviar(Param.CARD_SET_VIDA_ATUAL, vidaAtual);
        if (getVida() <= 0) {
            getAnimacao().vidaAtual(getVida() - vidaAnterior, TIME_ANIMATION.getLong());
            morreuSemHistorico(animacao);
        } else if (isEnfurecer() && vidaAnterior == getVidaMaxima() && vidaAnterior > getVida()) {
            GameCliente.addHistorico(getName() + " ficou enfurecido.", false);
            Enfurecer.processar(this, true);
            viewCardMesa.setEnfurecer(true);
        } else if (isEnfurecer() && vidaAnterior < getVidaMaxima() && getVida() == getVidaMaxima()) {
            Enfurecer.processar(this, false);
            viewCardMesa.setEnfurecer(false);
        }
    }

    public void addVida(int cura) {
        int anterior = getVida();
        if (getPartida().getHero().curaIsDano()) {
            delVida(cura);
        } else if (getVida() < getVidaMaxima()) {
            int diferenca = vidaMaxima - vidaAtual;
            setVida(cura > diferenca ? vidaAtual + diferenca : vidaAtual + cura);
            cura -= diferenca;
            if (cura > 0) {
                getAura().addVida(diferenca);
            }
            cura = (getVida() - anterior);
            getAnimacao().vidaAtual(getVida() - vidaAnterior, TIME_ANIMATION.getLong());
            if (cura > 0) {
                GameCliente.addHistorico(getName() + " teve " + cura + " de vida restaurada.", true);
                PersonagemFoiCurado.processar(null, this, null);
            }
        }
    }

    public void addVidaEmArea(int cura) {
        if (getPartida().getHero().curaIsDano()) {
            delVidaEmArea(cura);
        } else if (getVida() < getVidaMaxima()) {
            int diferenca = vidaMaxima - vidaAtual;
            setVidaEmArea(cura > diferenca ? vidaAtual + diferenca : vidaAtual + cura);
            cura -= diferenca;
            if (cura > 0) {
                getAura().addVida(diferenca);
            }
        }
    }

    public void delVida(int dano) {
        if (dano > 0) {
            if (!getAura().isImune() && !isImune()) {
                if (!isEscudoDivino()) {
                    dano(dano);
                } else {
                    delMechanics(ESCUDO_DIVINO);
                    GameCliente.addHistorico(getName() + " não sofreu dano e perdeu escudo divino.", true);
                    getAnimacao().vidaAtual(0, TIME_ANIMATION.getLong());
                }
            } else {
                GameCliente.addHistorico(getName() + " não sofreu dano porque está imune.", true);
                getAnimacao().vidaAtual(0, TIME_ANIMATION.getLong());
            }
        }
    }

    private void dano(int d) {
        vidaAnterior = getVida();
        if (getAura().getVida() > 0) {
            getAura().delVida(d);
            if (getAura().getVida() < 0) {
                int diferenca = getAura().getVida();
                getAura().setVida(0);
                setVida(vidaAtual + diferenca);
            }
        } else {
            setVida(vidaAtual - d);
        }
        int dano = vidaAnterior - getVida();
        if (dano > 0) {
            if (getVida() <= 0) {
                PersonagemRecebeuDano.processar(null, this, dano, new ArrayList<>(Arrays.asList(new Card[]{this})));
            } else {
                GameCliente.addHistorico(getName() + " recebeu " + dano + " de dano.", true);
                getAnimacao().vidaAtual(-dano, TIME_ANIMATION.getLong());
                PersonagemRecebeuDano.processar(null, this, dano, null);
            }
        }
    }

    public void delVidaEmArea(int d) {
        if (d > 0) {
            if (!getAura().isImune()) {
                if (!isEscudoDivino()) {
                    danoEmArea(d);
                } else {
                    delMechanics("Divine Shield");
                    GameCliente.addHistorico(getName() + " não sofreu dano e perdeu escudo divino.", true);
                }
            } else {
                GameCliente.addHistorico(getName() + " não sofreu dano porque está imune.", true);
            }
        }
    }

    private void danoEmArea(int d) {
        if (getAura().getVida() > 0) {
            getAura().delVida(d);
            if (getAura().getVida() < 0) {
                int diferenca = getAura().getVida();
                getAura().setVida(0);
                setVidaEmArea(vidaAtual + diferenca);
            }
        } else {
            setVidaEmArea(vidaAtual - d);
        }
    }

    public void setVidaEmArea(int v) {
        int anterior = getVida();
        v = getPartida().getFeiticosAtivos().isNEW1_036() ? 1 : v;
        vidaAtual = v;
        vidaAtual = vidaAtual > vidaMaxima ? vidaMaxima : vidaAtual;
        enviar(Param.CARD_SET_VIDA_EM_AREA, vidaAtual);
        if (getVida() > 0) {
            if (isEnfurecer() && anterior == getVidaMaxima() && anterior > getVida()) {
                GameCliente.addHistorico(getName() + " ficou enfurecido.", false);
                Enfurecer.processar(this, true);
                viewCardMesa.setEnfurecer(true);
            } else if (isEnfurecer() && (anterior < getVidaMaxima()) && (getVida() == getVidaMaxima())) {
                Enfurecer.processar(this, false);
                viewCardMesa.setEnfurecer(false);
            }
        }
    }

    public boolean isIleso() {
        return getVida() == getVidaMaxima();
    }

    public void restaurarVida() {
        if (getPartida().getHero().curaIsDano()) {
            if (vidaAtual < vidaMaxima) {
                delVida(vidaMaxima - vidaAtual);
            }
        } else {
            setVida(vidaMaxima);
            getAura().setVida(getAura().getVidaMaxima());
        }
    }

    public void morreu() {
        morreu(true);
    }

    public void morreu(boolean animacao) {
        getPartida().getJogada().ultimoSuspiro(getDono().morreu(this, animacao), this);
        GameCliente.addHistorico(getName() + " morreu.", true);
    }

    private void morreuSemHistorico(boolean animacao) {
        getPartida().getJogada().ultimoSuspiro(getDono().morreu(this, animacao), this);
        GameCliente.addHistorico(getName() + " recebeu " + (vidaAnterior - getVida()) + " de dano e morreu.", false);
    }

    public int getAtaqueSemAura() {
        return ataque;
    }

    public int getAtaque() {
        return ataque + getAura().getAtaque() + getAura().getDanoAdicional() + getAtaque_enfurecer();
    }

    public void setAtaque(int ataque, boolean historico) {
        int anterior = this.ataque;
        this.ataque = Math.max(ataque, 0);
        enviar(Param.CARD_SET_ATAQUE, this.ataque);
        if (historico && getPartida().getMesa().contains(this)) {
            if (this.ataque > anterior) {
                GameCliente.addHistorico(getName() + " ganhou " + (this.ataque - anterior) + " de ataque.", true);
            } else if (this.ataque < anterior) {
                GameCliente.addHistorico(getName() + " perdeu " + (anterior - this.ataque) + " de ataque.", true);
            }
        }
    }

    public void addAtaque(int ataque) {
        setAtaque(this.ataque + ataque, true);
        getAnimacao().ataque(ataque, TIME_ANIMATION.getLong());
        setAtaquesRealizados(getAtaquesRealizados());
    }

    public void delAtaque(int ataque) {
        setAtaque(this.ataque - ataque, true);
        getAnimacao().ataque(-ataque, TIME_ANIMATION.getLong());
    }

    public int getDanoMagico() {
        return danoMagico;
    }

    public void setDanoMagico(int danoMagico) {
        enviar(Param.CARD_SET_DANO_MAGICO, (this.danoMagico = danoMagico));
    }

    public void addDanoMagico(int danoMagico) {
        setDanoMagico(this.danoMagico + danoMagico);
    }

    public void delDanoMagico(int danoMagico) {
        setDanoMagico(this.danoMagico - danoMagico);
    }

    public int getCustoAlterado(boolean flag) {
        return getPartida().getAlterarCusto().processarCard(this, flag);
    }

    public int getCusto() {
        return Math.max(custo + getAura().getCusto(), 0);
    }

    public int getCustoSemAura() {
        return Math.max(custo, 0);
    }

    public void setCusto(int custo) {
        enviar(Param.CARD_SET_CUSTO, (this.custo = Math.max(custo, 0)));
    }

    public void addCusto(int custo) {
        setCusto(this.custo + custo);
    }

    public void delCusto(int custo) {
        setCusto(this.custo - custo);
    }

    public ArrayList<String> getMechanics() {
        if (mechanics == null) {
            return new ArrayList<>();
        }
        return mechanics;
    }

    public void addMechanics(String mechanic) {
        if (!mechanics.contains(mechanic)) {
            mechanics.add(mechanic);
            enviar(Param.CARD_SET_MECANICA_ADD, mechanic);
            addMecanicaLabel(mechanic);
            GameCliente.addHistorico(getName() + " ganhou " + Utils.traduzirMecanica(mechanic), false);
            if (mechanic.equals(FURIA_DOS_VENTOS) && getAtaquesRealizados() < 2) {
                setAtaquesRealizados(getAtaquesRealizados());
            } else if (mechanic.equals(INVESTIDA) && !getAtacou() && getTurnosNaMesa() == 0) {
                setAtaquesRealizados(0);
            }
        }
    }

    public void delMechanics(String mechanic) {
        if (mechanics.contains(mechanic)) {
            mechanics.remove(mechanic);
            enviar(Param.CARD_SET_MECANICA_REMOVE, mechanic);
            delMecanicaLabel(mechanic);
            GameCliente.addHistorico(getName() + " perdeu " + Utils.traduzirMecanica(mechanic), false);
        }
    }

    public int getVidaMaxima() {
        return vidaMaxima + getAura().getVidaMaxima();
    }

    public int getVidaOriginal() {
        return vidaOriginal;
    }

    public void setVidaMaxima(int vidaMaxima, boolean historico) {
        this.vidaMaxima = vidaMaxima;
        enviar(Param.CARD_SET_VIDA_MAXIMA, this.vidaMaxima);
        if (historico && getPartida().getMesa().contains(this)) {
            GameCliente.addHistorico(getName() + " teve sua vida alterada para " + this.vidaMaxima + ".", true);
        }
        if (this.vidaAtual > this.vidaMaxima) {
            setVida(this.vidaMaxima, false);
        }
    }

    public void setVidaOriginal(int vidaOriginal, boolean historico) {
        vidaMaxima = vidaOriginal;
        this.vidaOriginal = this.vidaMaxima;
        enviar(Param.CARD_SET_VIDA_ORIGINAL, vidaMaxima);
        if (historico && getPartida().getMesa().contains(this)) {
            GameCliente.addHistorico(getName() + " teve sua vida alterada para " + vidaMaxima + ".", true);
        }
        setVida(vidaMaxima, false);
        getAnimacao().vidaOriginal(vidaOriginal, TIME_ANIMATION.getLong());
    }

    public void addVidaMaxima(int v) {
        setVidaMaxima(vidaMaxima + v, true);
        setVida(vidaAtual + v);
        getAnimacao().vidaMaxima(v, TIME_ANIMATION.getLong());
    }

    public void delVidaMaxima(int v) {
        setVidaMaxima(vidaMaxima - v, true);
        getAnimacao().vidaMaxima(-v, TIME_ANIMATION.getLong());
    }

    public String getToString() {
        switch (type) {
            case LACAIO:
                return TO_STRING_LACAIO + id_long;
            case ARMA:
                return TO_STRING_ARMA + id_long;
            case FEITICO:
                return TO_STRING_FEITICO + id_long;
            default:
                return "";
        }
    }

    public Partida getPartida() {
        return partida != null ? partida : Partida.getDefault();
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public int getAtaquesRealizados() {
        return ataquesRealizados;
    }

    public void setAtaquesRealizados(int ataquesRealizados) {
        this.ataquesRealizados = ataquesRealizados;
        enviar(Param.CARD_SET_ATAQUES_REALIZADOS, getAtaquesRealizados());
        setInvestidaVisible(false);
        if ((getTurnosNaMesa() != 0
                || (getTurnosNaMesa() == 0 && getMechanics().contains(INVESTIDA)))
                && (getAtaque() > 0) && !isFreeze()
                && ((getAtaquesRealizados() == 0)
                || ((getAtaquesRealizados() == 1) && isFuriaDosVentos())
                || (id.equals(V07TR0N) && (getAtaquesRealizados() < 4)))) {
            setInvestidaVisible(true);
        } else if (getAura().isInvestida()) {
            setInvestidaVisible(true);
        }
    }

    public void atacou() {
        setAtacou(true);
        setAtaquesRealizados(getAtaquesRealizados() + 1);
        delMechanics(FURTIVIDADE);
    }

    public boolean isSleep() {
        return sleep;
    }

    public void setSleep(boolean sleep) {
        enviar(Param.CARD_SET_SLEEP, (this.sleep = sleep));
    }

    public boolean isImune() {
        return imune;
    }

    public void setImune(boolean imune) {
        enviar(Param.CARD_SET_IMUNE, (this.imune = imune));
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setAtacou(boolean atacou) {
        this.atacou = atacou;
    }

    public boolean getAtacou() {
        return atacou;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        boolean flag = this.freeze != freeze;
        this.freeze = freeze;
        viewCardMesa.setCongelado(freeze);
        setAtaquesRealizados(getAtaquesRealizados());
        enviar(Param.CARD_SET_FREEZE, this.freeze);
        if (flag) {
            GameCliente.addHistorico(getName() + (freeze ? " foi congelado." : " foi descongelado."), false);
        }
        if (freeze) {
            setCumpriuFreeze(false);
        }
    }

    public boolean isUltimoSuspiroReviver() {
        return ultimoSuspiroReviver;
    }

    public void setUltimoSuspiroReviver(boolean ultimoSuspiroReviver) {
        this.ultimoSuspiroReviver = ultimoSuspiroReviver;
        enviar(Param.CARD_SET_ULTIMO_SUSPIRO_REVIVER, this.ultimoSuspiroReviver);
        if (this.ultimoSuspiroReviver) {
            addMechanics(ULTIMO_SUSPIRO);
        }
    }

    public boolean isCumpriuFreeze() {
        return cumpriuFreeze;
    }

    public void setCumpriuFreeze(boolean cumpriuFreeze) {
        enviar(Param.CARD_SET_CUMPRIU_FREEZE, (this.cumpriuFreeze = cumpriuFreeze));
    }

    public boolean isSilenciado() {
        return silenciado;
    }

    public void setSilenciado(boolean silenciado) {
        enviar(Param.CARD_SET_SILENCIADO, (this.silenciado = silenciado));
        if (this.silenciado) {
            silenciar();
            GameCliente.addHistorico(getName() + " foi silenciado.", false);
        }
    }

    private void silenciar() {
        viewCardMesa.setCongelado(false);
        setFreeze(false);
        viewCardMesa.setSilenciado(true);
        while (!getMechanics().isEmpty()) {
            delMechanics(getMechanics().get(0));
        }
        viewCardMesa.setEnfurecer(false);
        viewCardMesa.setMecanicaIcon(false);
        setAtaque(attack, false);
        setAtaqueEnfurecer(0);
        setDanoMagico(0);
        evocarUltimoSuspiro = new ArrayList<>();
        addMaoUltimoSuspiro = new ArrayList<>();
        addDeckUltimoSuspiro = new ArrayList<>();
        fazerAoAtacar = new ArrayList<>();
        if (getVidaMaxima() < getVidaOriginal()) {
            int diferenca = getVidaMaxima() - getVida();
            setVidaMaxima(getVidaOriginal(), false);
            setVida(getVidaMaxima() - diferenca);
        } else {
            setVidaMaxima(getVidaOriginal(), false);
        }
        getPartida().removeEfeitosOf(this);
    }

    public boolean isInvestida() {
        return ((mechanics != null) && mechanics.contains(INVESTIDA)) || getAura().isInvestida();
    }

    public boolean isLacaio() {
        return getType().equals(LACAIO);
    }

    public boolean isArma() {
        return getType().equals(ARMA);
    }

    public boolean isFeitico() {
        return getType().equals(FEITICO);
    }

    public boolean isPodeAtacar() {
        return viewCardMesa.isInvestida();
    }

    public boolean isFuriaDosVentos() {
        return getMechanics().contains(FURIA_DOS_VENTOS);
    }

    public boolean isImuneAlvo() {
        return ((getText() != null) && getText().contains(IMUNE_PODER_MAGICO_TEXT)) || getAura().isImuneAlvo();
    }

    public boolean isGritoDeGuerra() {
        return getMechanics().contains(GRITO_DE_GUERRA);
    }

    public boolean isEscudoDivino() {
        return getMechanics().contains(ESCUDO_DIVINO);
    }

    public boolean isFurtivo() {
        return getMechanics().contains(FURTIVIDADE);
    }

    public boolean isEnfurecer() {
        return getMechanics().contains(ENFURECER);
    }

    public boolean isProvocar() {
        return getMechanics().contains(PROVOCAR);
    }

    public boolean isVeneno() {
        return getMechanics().contains(VENENO);
    }

    public boolean isUltimoSuspiro() {
        return getMechanics().contains(ULTIMO_SUSPIRO);
    }

    public boolean isSobrecarga() {
        return getMechanics().contains(SOBRECARGA);
    }

    public boolean isSegredo() {
        return getMechanics().contains(SEGREDO);
    }

    public boolean isInspirar() {
        return getMechanics().contains(INSPIRAR);
    }

    public boolean isUmTurnoEfeito() {
        return getMechanics().contains(EFEITO_POR_UM_TURNO);
    }

    public boolean isCongela() {
        return getMechanics().contains(CONGELAR);
    }

    public boolean isAfetadoPeloPoderMagico() {
        return getMechanics().contains(AFETADO_PELO_PODER_MAGICO) || ((getText() != null) && getText().contains("$"));
    }

    public boolean isFera() {
        return ((getRace() != null) && getRace().equals(FERA));
    }

    public boolean isDemonio() {
        return ((getRace() != null) && getRace().equals(DEMONIO));
    }

    public boolean isDragao() {
        return ((getRace() != null) && getRace().equals(DRAGAO));
    }

    public boolean isMecanoide() {
        return ((getRace() != null) && getRace().equals(MECANOIDE));
    }

    public boolean isMurloc() {
        return ((getRace() != null) && getRace().equals(MURLOC));
    }

    public boolean isPirata() {
        return ((getRace() != null) && getRace().equals(PIRATA));
    }

    public boolean isTotem() {
        return ((getRace() != null) && getRace().equals(TOTEM));
    }

    public boolean isRaceGeral() {
        return (getRace() == null);
    }

    public int getAtaque_enfurecer() {
        return ataqueEnfurecer;
    }

    public void setAtaqueEnfurecer(int ataqueEnfurecer) {
        enviar(Param.CARD_SET_ATAQUE_ENFURECER, (this.ataqueEnfurecer = ataqueEnfurecer));
    }

    public boolean isFlag_ui() {
        return flag_ui;
    }

    public void setFlag_ui(boolean flag_ui) {
        this.flag_ui = flag_ui;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInPlayText() {
        return inPlayText;
    }

    public void setInPlayText(String inPlayText) {
        this.inPlayText = inPlayText;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        if (this.durability > durability) {
            Audios.playEfeitos(Audios.PARTIDA_ARMA_PERDER_DURABILIDADE);
        }
        this.durability = durability;
        enviar(Param.CARD_SET_DURABILIDADE, this.durability);
        if (this.durability <= 0) {
            getDono().destruirArma();
        }
    }

    public void addDurabilidade(int durability) {
        setDurability(this.durability + durability);
    }

    public void delDurabilidade(int durability) {
        setDurability(this.durability - durability);
    }

    public boolean isCollectible() {
        return collectible;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

    public boolean isElite() {
        return elite;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }

    public String getHowToGet() {
        return howToGet;
    }

    public void setHowToGet(String howToGet) {
        this.howToGet = howToGet;
    }

    public String getHowToGetGold() {
        return howToGetGold;
    }

    public void setHowToGetGold(String howToGetGold) {
        this.howToGetGold = howToGetGold;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
        enviar(Param.CARD_SET_TIME, this.time);
    }

    public Aura getAura() {
        return aura;
    }

    private Card getInstance() {
        return this;
    }

    /**
     * Envia um pacote pela rede
     *
     * @param tipo TIPO do pacote
     * @param value valor do atributo
     */
    public void enviar(String tipo, Object value) {
        Pacote pack = new Pacote(tipo);
        pack.set(Param.CARD_ID_LONG, id_long);
        pack.set(Param.VALUE, value.toString());
        GameCliente.send(pack);
    }

    public void envenenado() {
        GameCliente.addHistorico(getName() + " foi envenenado.", true);
        morreu();
    }

    private int getPoderMagico() {
        try {
            return Integer.parseInt(getText().substring(getText().indexOf("<b>+") + 4, getText().indexOf(" de Dano Mágico</b>")));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Retorna a lista de ids que o lacaio evocará no último suspiro
     *
     * @return lista de IDs
     */
    public List<String> getEvocarUltimoSuspiro() {
        return evocarUltimoSuspiro;
    }

    /**
     * Adiciona um ID que o lacaio evocará no último suspiro
     *
     * @param id ID do lacaio que será evocado
     */
    public void addEvocarUltimoSuspiro(String id) {
        evocarUltimoSuspiro.add(id);
        enviar(Param.CARD_EVOCAR_ULTIMO_SUSPIRO_ADD, id);
        addMechanics(ULTIMO_SUSPIRO);
    }

    /**
     * Retorna a lista de ids que o lacaio adicionará ao deck no último suspiro
     *
     * @return lista de IDs
     */
    public List<String> getAddDeckUltimoSuspiro() {
        return addDeckUltimoSuspiro;
    }

    /**
     * Adiciona um ID que o lacaio adicionará ao deck no último suspiro
     *
     * @param id ID do card que será adicionado ao deck
     */
    public void addAddDeckUltimoSuspiro(String id) {
        addDeckUltimoSuspiro.add(id);
        enviar(Param.CARD_ADD_DECK_ULTIMO_SUSPIRO_ADD, id);
        addMechanics(ULTIMO_SUSPIRO);
    }

    /**
     * Retorna a lista de ids que o lacaio adicionará à mão do dono no último
     * suspiro
     *
     * @return lista de IDs
     */
    public List<String> getAddMaoUltimoSuspiro() {
        return addMaoUltimoSuspiro;
    }

    /**
     * Adiciona um ID que o lacaio adicionará à mão do dono no último suspiro
     *
     * @param id ID do card que será adicionado à mão do dono
     */
    public void addAddMaoUltimoSuspiro(String id) {
        addMaoUltimoSuspiro.add(id);
        enviar(Param.CARD_ADD_MAO_ULTIMO_SUSPIRO_ADD, id);
        addMechanics(ULTIMO_SUSPIRO);
    }

    /**
     * Retorna a lista de coisas que o lacaio faz ao atacar
     *
     * @return List de array de String com as configurações do efeito
     */
    public List<String[]> getFazerAoAtacar() {
        return fazerAoAtacar;
    }

    /**
     * Adiciona um efeito para o lacaio fazer algo ao atacar
     *
     * @param values array de String com as configurações do efeito
     */
    public void addFazerAoAtacar(String[] values) {
        fazerAoAtacar.add(values);
        Pacote pack = new Pacote(Param.CARD_FAZER_AO_ATACAR_ADD);
        pack.set(Param.CARD_ID_LONG, id_long);
        pack.set(Param.CARD_FAZER_AO_ATACAR_HEROI, values[0]);
        pack.set(Param.CARD_FAZER_AO_ATACAR_TIPO, values[1]);
        pack.set(Param.CARD_FAZER_AO_ATACAR_VALUE, values[2]);
        GameCliente.send(pack);
    }

    public void ultimoSuspiro(int index) {
        //Barão Rivendare (Seus lacaios ativam os Últimos Suspiros duas vezes)
        if (getDono().temNaMesa(BARAO_RIVENDARE)) {
            UltimoSuspiro.processar(this, index);
        }
        UltimoSuspiro.processar(this, index);
    }

    public void turnosNaMesaIncrement() {
        setTurnosNaMesa(turnosNaMesa + 1);
    }

    public void setTurnosNaMesa(int turnosNaMesa) {
        this.turnosNaMesa = turnosNaMesa;
        enviar(Param.CARD_SET_TURNOS_NA_MESA, this.turnosNaMesa);
    }

    public int getTurnosNaMesa() {
        return turnosNaMesa;
    }

    public boolean isHero() {
        return getPartida().getHero().isCard(id_long);
    }

    @Override
    public Card clone() {
        try {
            return RWObj.clone(this, Card.class);
        } catch (Exception e) {
            return CARTAS.createCard(id);
        }
    }

    public void setAura() {
        aura = new Aura(getInstance());
    }

    public String getDescricao() {
        return getText() != null ? getText()
                .replace("<b>", "")
                .replace("<i>", "")
                .replace("</i>", "")
                .replace("</b>", "")
                .replace("$", "")
                .replace("#", "") : "";
    }

    public Heroi getDono() {
        return getPartida().getHero().isCard(id_long) ? getPartida().getHero() : getPartida().getOponente();
    }

    public Heroi getOponente() {
        return getPartida().getHero().isCard(id_long) ? getPartida().getOponente() : getPartida().getHero();
    }

    public boolean isLendario() {
        return ((getRarity() != null) && getRarity().equals(LENDARIO));
    }

    public boolean isMao() {
        return getDono().getMao().contains(this);
    }

    public boolean isMesa() {
        return (getDono().getMesa().contains(this)
                || ((getDono().getArma() != null) && getDono().getArma().equals(this)));
    }

    public boolean isMorto() {
        return ((getVida() <= 0) || getDono().getMorto().contains(this));
    }

    public Animacao getAnimacao() {
        return viewCardMesa.getAnimacao();
    }

}
