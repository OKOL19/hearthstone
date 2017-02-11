package com.limagiran.hearthstone.partida.control;

import com.limagiran.hearthstone.util.Param;
import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.evento.AlterarCusto;
import com.limagiran.hearthstone.evento.Aura;
import com.limagiran.hearthstone.util.Sort;
import com.limagiran.hearthstone.evento.AuraEstatica;
import com.limagiran.hearthstone.evento.EfeitoProgramado;
import com.limagiran.hearthstone.evento.FeiticosAtivos;
import com.limagiran.hearthstone.evento.PodeAtacar;
import static com.limagiran.hearthstone.HearthStone.CARTAS;
import com.limagiran.hearthstone.heroi.control.Heroi;
import com.limagiran.hearthstone.util.JsonUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import com.limagiran.hearthstone.partida.view.PartidaView;
import com.limagiran.hearthstone.server.GameCliente;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class Partida implements Serializable {

    private transient final List<Runnable> runList = Collections.synchronizedList(new ArrayList<Runnable>());
    private static Partida INSTANCE_DEFAULT = null;
    private transient PartidaView partidaView;
    private final Jogada jogada;
    private AlterarCusto alterarCusto;
    private final AuraEstatica auraEstatica;
    private final EfeitoProgramado efeitoProgramado;
    private final PodeAtacar podeAtacar;
    private FeiticosAtivos feiticosAtivos;
    private final int player;
    private final Heroi hero;
    private final Heroi oponente;
    private int vezDoPlayer;
    private int turno;
    public final List<Card> allCards = new ArrayList<>();
    private final List<Efeito> efeito = new ArrayList<>();
    private long novo_alvo;
    private final int playerIniciouPartida;
    private long vencedor = -1;

    public Partida(int player, int vezDoPlayer, Heroi hero, Heroi oponente) {
        this.player = player;
        this.vezDoPlayer = vezDoPlayer;
        this.turno = 0;
        this.hero = hero;
        this.oponente = oponente;
        novo_alvo = -1;
        playerIniciouPartida = vezDoPlayer;
        this.hero.setPartida(getInstance());
        this.oponente.setPartida(getInstance());
        jogada = new Jogada(getInstance());
        alterarCusto = new AlterarCusto(getInstance());
        auraEstatica = new AuraEstatica(getInstance());
        efeitoProgramado = new EfeitoProgramado(getInstance());
        podeAtacar = new PodeAtacar(getInstance());
        feiticosAtivos = new FeiticosAtivos();
    }

    private Partida getInstance() {
        return this;
    }

    public static Partida getDefault() {
        if (INSTANCE_DEFAULT == null) {
            INSTANCE_DEFAULT = new Partida(0, 0, Heroi.getDefault(Param.HEROI), Heroi.getDefault(Param.OPONENTE));
        }
        return INSTANCE_DEFAULT;
    }

    public void setPartidaView(PartidaView partidaView) {
        this.partidaView = partidaView;
    }

    public PartidaView getPartidaView() {
        return partidaView;
    }

    public Jogada getJogada() {
        return jogada;
    }

    public AlterarCusto getAlterarCusto() {
        return alterarCusto;
    }
    
    public void setAlterarCusto(AlterarCusto alterarCusto) {
        this.alterarCusto = alterarCusto;
    }

    public AuraEstatica getAuraEstatica() {
        return auraEstatica;
    }

    public EfeitoProgramado getEfeitoProgramado() {
        return efeitoProgramado;
    }

    public PodeAtacar getPodeAtacar() {
        return podeAtacar;
    }

    public FeiticosAtivos getFeiticosAtivos() {
        return feiticosAtivos;
    }
    
    public void setFeiticosAtivos(FeiticosAtivos feiticosAtivos) {
        this.feiticosAtivos = feiticosAtivos;
    }

    public Card criarCard(String id, long id_long) {
        Card criar = CARTAS.createCard(id);
        criar.setPartida(this);
        criar.id_long = id_long;
        criar.setAura();
        allCards.add(criar);
        Pacote pack = new Pacote(Param.PARTIDA_CRIAR_CARD);
        pack.set(Param.VALUE, id);
        pack.set(Param.CARD_ID_LONG, id_long);
        GameCliente.send(pack);
        return criar;
    }

    public Heroi getOponente() {
        return oponente;
    }

    public boolean isVezHeroi() {
        return player == getVezDoPlayer();
    }

    public void addHistorico(String msg) {
        GameCliente.send(Param.PARTIDA_SET_HISTORICO_ADD, msg);
    }

    public void addEfeito(Efeito efeito) {
        getEfeitoProgramado().ativar(efeito);
        this.efeito.add(efeito);
        GameCliente.send(Param.PARTIDA_SET_EFEITO_ADD, JsonUtils.toJSon(efeito));
    }

    public void delEfeito(int index) {
        this.efeito.remove(index);
        GameCliente.send(Param.PARTIDA_SET_EFEITO_REMOVE, index);
    }

    public List<Efeito> getEfeitos() {
        return efeito;
    }

    public void passarVez() {
        List<Card> list = getMesa().subList(0, getMesa().size());
        for (Card card : list) {
            card.turnosNaMesaIncrement();
        }
        getHero().clearCardsJogadosNaRodada();
        getOponente().clearCardsJogadosNaRodada();
        getHero().lacaiosMortosNaRodadaRemoveAll();
        getOponente().lacaiosMortosNaRodadaRemoveAll();
        getHero().fimTurno();
        if (getOponente().getArma() != null) {
            getOponente().getArma().setArmaAtivada(true);
        }
        if (getHero().getArma() != null) {
            getHero().getArma().setArmaAtivada(false);
        }
        GameCliente.send(Param.VALUE, Param.REFRESH);
        setVezDoPlayer(vezDoPlayer + 1);
        if (vezDoPlayer == playerIniciouPartida) {
            setTurno(turno + 1);
        }
        GameCliente.send(Param.PARTIDA_SET_VEZ_DO_PLAYER, vezDoPlayer);
    }

    public int getPlayer() {
        return player;
    }

    public int getVezDoPlayer() {
        return vezDoPlayer % 2;
    }

    public void setVezDoPlayer(int vezDoPlayer) {
        this.vezDoPlayer = vezDoPlayer;
        auraEstatica.decrement();
    }

    public Heroi getHero() {
        return hero;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
        GameCliente.send(Param.PARTIDA_SET_TURNO, this.turno);
    }

    public long getNovo_alvo() {
        return novo_alvo;
    }

    public void setNovo_alvo(long novo_alvo) {
        this.novo_alvo = novo_alvo;
        GameCliente.send(Param.PARTIDA_SET_NOVO_ALVO, this.novo_alvo);
    }

    public List<Card> getMesa() {
        List<Card> retorno = new ArrayList<>();
        retorno.addAll(getOponente().getMesa());
        retorno.addAll(getHero().getMesa());
        return retorno;
    }

    public List<Card> getAllCardsIncludeSecrets() {
        List<Card> retorno = new ArrayList<>();
        retorno.addAll(hero.getMesa());
        retorno.addAll(oponente.getMesa());
        if (hero.getArma() != null) {
            retorno.add(hero.getArma());
        }
        if (oponente.getArma() != null) {
            retorno.add(oponente.getArma());
        }
        if (!getOponente().getSegredo().isEmpty()) {
            retorno.addAll(getOponente().getSegredo());
        }
        retorno.sort(Sort.time());
        return retorno;
    }

    public List<Card> getAllCards() {
        List<Card> retorno = new ArrayList<>();
        retorno.addAll(hero.getMesa());
        retorno.addAll(oponente.getMesa());
        if (hero.getArma() != null) {
            retorno.add(hero.getArma());
        }
        if (oponente.getArma() != null) {
            retorno.add(oponente.getArma());
        }
        retorno.sort(Sort.time());
        return retorno;
    }

    public int getPlayerIniciouPartida() {
        return playerIniciouPartida;
    }

    public long getVencedor() {
        return vencedor;
    }

    public void setVencedor(long heroi) {
        if (vencedor == -1) {
            vencedor = heroi;
            Pacote pack = new Pacote(Param.PARTIDA_SET_VENCEDOR);
            pack.set(Param.VALUE, heroi);
            GameCliente.send(pack);
            GameCliente.jogoEncerrado();
        }
    }

    public void encerrar(long vencedor) {
        this.vencedor = vencedor;
        GameCliente.jogoEncerrado();
    }

    public Card findCardByIDLong(long id) {
        for (Card c : allCards) {
            if (c != null && c.id_long == id) {
                return c;
            }
        }
        return Card.createDefault();
    }

    public void removeEfeitos() {
        for (int i = 0; i < efeito.size(); i++) {
            if (efeito.get(i).getSleep() < 0) {
                GameCliente.send(Param.PARTIDA_SET_EFEITO_REMOVE, i);
                efeito.remove(i);
                i--;
            }
        }
    }

    public List<Card> getMao() {
        List<Card> retorno = new ArrayList<>();
        retorno.addAll(getHero().getMao());
        retorno.addAll(getOponente().getMao());
        return retorno;
    }

    public Card clonar(long id_long, long nanoTime) {
        Card card = findCardByIDLong(id_long);
        if (card == null) {
            JOptionPane.showMessageDialog(null, "ERRO - id_long PARTIDA.clonar(long) não encontrado");
            System.exit(0);
        }
        Card clonado = card.clone();
        clonado.id_long = nanoTime;
        clonado.setAura();
        allCards.add(clonado);
        Pacote pack = new Pacote(Param.PARTIDA_CLONAR);
        pack.set(Param.VALUE, id_long);
        pack.set(Param.CARD_ID_LONG, clonado.id_long);
        GameCliente.send(pack);
        return clonado;
    }

    public Card tornarSeCopia(long id_long, long alvo) {
        Card cardAlvo = findCardByIDLong(alvo);
        if (cardAlvo == null) {
            JOptionPane.showMessageDialog(null, "ERRO - id_long PARTIDA.clonar(long) não encontrado");
            System.exit(0);
        }
        Card tornarSeCopia = findCardByIDLong(id_long);
        Card clone = cardAlvo.clone();
        tornarSeCopia.copia(clone);
        tornarSeCopia.setAura();
        Pacote pack = new Pacote(Param.PARTIDA_COPIA);
        pack.set(Param.VALUE, alvo);
        pack.set(Param.CARD_ID_LONG, tornarSeCopia.id_long);
        GameCliente.send(pack);
        return tornarSeCopia;
    }

    public void cardDefault(long id_long, String id) {
        Card card = transformarReiniciar(id_long, id);
        Pacote pack = new Pacote(Param.PARTIDA_CARD_DEFAULT);
        pack.set(Param.VALUE, id);
        pack.set(Param.CARD_ID_LONG, id_long);
        GameCliente.send(pack);
        card.atualizarAtributos();
        card.atualizarMecanicas();
        if (getHero().isCard(id_long)) {
            getHero().getPanelMesa().atualizar();
        } else {
            getOponente().getPanelMesa().atualizar();
        }
    }

    public void polimorfia(long id_long, String newCard, boolean historico) {
        String alvo = findCardByIDLong(id_long).getName();
        Card card = transformarReiniciar(id_long, newCard);
        if (historico) {
            addHistorico(alvo + " foi transformado em " + card.getName() + ".");
        }
        Pacote pack = new Pacote(Param.PARTIDA_POLIMORFIA);
        pack.set(Param.VALUE, newCard);
        pack.set(Param.CARD_ID_LONG, id_long);
        GameCliente.send(pack);
        if (getHero().isCard(id_long)) {
            getHero().getPanelMesa().atualizar();
        } else {
            getOponente().getPanelMesa().atualizar();
        }
    }

    private Card transformarReiniciar(long id_long, String newCard) {
        Card alvo = findCardByIDLong(id_long);
        Card novoCard = CARTAS.findCardById(newCard);
        alvo.polimorfia(novoCard);
        return alvo;
    }

    public void voltarPraMaoDoDono(long id_long) {
        Card card = findCardByIDLong(id_long);
        cardDefault(card.id_long, card.getId());
        addHistorico(card.getName() + " voltou à mão do dono.");
        card.getDono().addMao(card);
        card.getDono().delMesa(card);
    }

    public void roubar(long id_long) {
        if (getHero().isCard(id_long)) {
            roubar(id_long, getHero(), getOponente());
        } else {
            roubar(id_long, getOponente(), getHero());
        }
    }

    private void roubar(long id_long, Heroi origem, Heroi destino) {
        Card roubar = findCardByIDLong(id_long);
        destino.addMesa(destino.getMesa().size(), roubar);
        origem.delMesa(roubar);
        destino.getPanelMesa().atualizar();
        origem.getPanelMesa().atualizar();
        addHistorico(destino.getNome() + " assumiu o controle do lacaio " + roubar.getName());
    }

    public void removeEfeitosOf(Card card) {
        for (int i = 0; i < efeito.size(); i++) {
            if (efeito.get(i).getParamLong(Param.CARD_ID_LONG) == card.id_long) {
                delEfeito(i);
                i--;
            }
        }
    }

    public void addAllCards(List<Card> deck) {
        deck.stream().forEach((card) -> {
            card.setPartida(this);
            allCards.add(card);
        });
    }

    public void iniciarPartida() {
        for (int i = 0; i < 3; i++) {
            hero.comprarCarta(Card.COMPRA_INICIO_PARTIDA);
        }
        for (int i = 0; i < 5; i++) {
            oponente.comprarCarta(Card.COMPRA_INICIO_PARTIDA);
        }
        iniciarTurno();
    }

    public void gerarJogada(long lado, String clicado, String solto) {
        Pacote pack = jogar(lado, clicado, solto);
        if (pack != null) {
            jogada.jogar(pack);
        }
    }

    /**
     * Gera uma jogada de acordo com o clique do mouse
     *
     * @param pressed nome do componente em que o mouse foi clicado
     * @param released nome do componente em que o mouse foi solto
     * @return Pacote com a jogada configurada ou null para jogada inválida
     */
    private Pacote jogar(long lado, String pressed, String released) {
        try {
            long atacante = getIdLong(pressed);
            long alvo = getIdLong(released);
            if (atacante != alvo || pressed.equals(Values.TO_STRING_PODER_HEROICO + Param.HEROI)) {
                if (pressed.equals(Values.TO_STRING_HEROI + Param.HEROI)) {
                    return heroAction(alvo, released);
                } else if (pressed.equals(Values.TO_STRING_PODER_HEROICO + Param.HEROI)) {
                    return poderHeroicoAction(alvo, released);
                } else if (pressed.contains(Values.TO_STRING_MAO)) {
                    return jogarCardAction(lado, atacante, alvo, released);
                } else if (pressed.contains(Values.TO_STRING_LACAIO) && hero.isCard(atacante)) {
                    return lacaioAction(atacante, alvo, released);
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * Verifica se o alvo tem provocar ou está furtivo
     *
     * @param alvo idLong do lacaio
     * @return true or false
     */
    private boolean validarAlvoAtaque(long alvo) {
        Card card_alvo = findCardByIDLong(alvo);
        for (Card c : oponente.getMesa()) {
            if (c.isProvocar() && !c.isFurtivo() && (alvo == Param.OPONENTE || (card_alvo != null && !card_alvo.isProvocar()))) {
                GameCliente.showPopUp("Você precisa atacar um lacaio com Provocar!");
                return false;
            }
        }
        if (card_alvo != null && card_alvo.isFurtivo()) {
            GameCliente.showPopUp("Você não pode atacar um lacaio escondido!");
            return false;
        }
        return true;
    }

    /**
     * Verifica se o alvo pode ser alvo de poder heroico
     *
     * @param alvo idLong do lacaio
     * @return true or false
     */
    private boolean validarAlvoPoder(long alvo) {
        Card card_alvo = findCardByIDLong(alvo);
        if (card_alvo != null && card_alvo.isImuneAlvo()) {
            GameCliente.showPopUp("Este lacaio não pode ser alvo de poder heroico!");
            return false;
        }
        return true;
    }

    /**
     * Realiza o procedimento padrão para um início de turno.
     */
    public void iniciarTurno() {
        oponente.getPanelAtaque().setVisible(false);
        if (oponente.getArma() != null) {
            oponente.getArma().setArmaAtivada(false);
        }
        if (hero.getArma() != null) {
            hero.getArma().setArmaAtivada(true);
        }
        hero.lacaiosMortosNaRodadaRemoveAll();
        oponente.lacaiosMortosNaRodadaRemoveAll();
        hero.inicioTurno();
        Aura.refresh(this);
        Utils.sleep(100);
        if (partidaView != null) {
            partidaView.iniciarTurno();
        }
    }

    /**
     * Passar o turno
     */
    public void passarTurno() {
        Pacote pack = new Pacote("");
        pack.set(Param.TIPO_JOGADA, Jogada.PASSAR_TURNO);
        jogada.jogar(pack);

    }

    /**
     * Gera um evento para o herói
     *
     * @param alvo alvo do herói
     * @return Pacote com os parâmetros da jogada configurados ou null para
 jogada inválida
     */
    private Pacote heroAction(long alvo, String solto) {
        Pacote pack = new Pacote("");
        if (hero.isInvestida() && hero.getAttack() > 0) {
            pack.set(Param.ALVO, alvo);
            if (solto.equals(Values.TO_STRING_HEROI + Param.OPONENTE)) {
                pack.set(Param.TIPO_JOGADA, Jogada.HEROI_ATACA_HEROI);
            } else if (solto.contains(Values.TO_STRING_LACAIO) && oponente.getMesa().contains(findCardByIDLong(alvo))) {
                pack.set(Param.TIPO_JOGADA, Jogada.HEROI_ATACA_LACAIO);
            } else {
                return null;
            }
            return validarAlvoAtaque(alvo) ? pack : null;
        } else {
            if (!hero.isInvestida()) {
                GameCliente.showPopUp("Seu heroi não pode atacar");
            }
            return null;
        }
    }

    /**
     * Gera um evento para o poder heroico
     *
     * @param alvo alvo do poder heroico
     * @return Pacote com os parâmetros da jogada configurados ou null para
 jogada inválida
     */
    private Pacote poderHeroicoAction(long alvo, String solto) {
        Pacote pack = new Pacote("");
        if (!hero.isPoderHeroicoUtilizado() && hero.getManaDisponivel() >= hero.getPoderHeroico().getCusto(false)) {
            pack.set(Param.TIPO_JOGADA, Jogada.PODER_HEROICO);
            if (solto.equals(Values.TO_STRING_PODER_HEROICO + Param.HEROI)) {
                pack.set(Param.TIPO_JOGADA, Jogada.PODER_HEROICO);
            } else if (solto.contains(Values.TO_STRING_HEROI) || solto.contains(Values.TO_STRING_LACAIO)) {
                pack.set(Param.ALVO, alvo);
            } else {
                return null;
            }
            return validarAlvoPoder(alvo) ? pack : null;
        } else {
            GameCliente.showPopUp((hero.isPoderHeroicoUtilizado()
                    ? "Você já utilizou seu poder heroico!"
                    : "Você não tem mana suficiente!"));
            return null;
        }
    }

    /**
     * Gera u evento para jogar um card
     *
     * @param atacante idLong do card selecionado
     * @param alvo alvo do feitiço ou local onde o lacaio será evocado
     * @return Pacote com os parâmetros da jogada configurados ou null para
 jogada inválida
     */
    private Pacote jogarCardAction(long lado, long atacante, long alvo, String solto) {
        Pacote pack = new Pacote("");
        pack.set(Param.ALVO, alvo);
        if (hero.getManaDisponivel() >= findCardByIDLong(atacante).getCustoAlterado(false)) {
            pack.set(Param.TIPO_JOGADA, Jogada.JOGAR_CARTA);
            pack.set(Param.CARD_ID_LONG, atacante);
            if (solto.contains(Values.TO_STRING_MESA)) {
                pack.set(Param.ALVO, Param.ALVO_CANCEL);
                if (hero.getMesa().isEmpty() || lado == Values.LEFT) {
                    pack.set(Param.POSICAO_CARTA_JOGADA, 0);
                } else {
                    pack.set(Param.POSICAO_CARTA_JOGADA, hero.getMesa().size());
                }
            } else if (solto.contains(Values.TO_STRING_LACAIO) && hero.isCard(alvo)) {
                pack.set(Param.POSICAO_CARTA_JOGADA, hero.getPosicaoNaMesa(alvo));
            } else if (findCardByIDLong(atacante).isFeitico()) {
                return pack;
            } else {
                return null;
            }
            if (findCardByIDLong(atacante).isLacaio() && hero.getMesa().size() >= 7) {
                GameCliente.showPopUp("Sua mesa está cheia!");
                return null;
            }
            return pack;
        } else {
            GameCliente.showPopUp("Você não tem mana suficiente!");
            return null;
        }
    }

    /**
     * Gera um evento para um lacaio
     *
     * @param atacante idLong do lacaio selecionado
     * @param alvo alvo do lacaio
     * @return Pacote com os parâmetros da jogada configurados ou null para
 jogada inválida
     */
    private Pacote lacaioAction(long atacante, long alvo, String solto) {
        Pacote pack = new Pacote("");
        if (findCardByIDLong(atacante).isPodeAtacar()) {
            pack.set(Param.ATACANTE, atacante);
            pack.set(Param.ALVO, alvo);
            if (solto.equals(Values.TO_STRING_HEROI + Param.OPONENTE)) {
                if (podeAtacarHeroi(findCardByIDLong(atacante))) {
                    pack.set(Param.TIPO_JOGADA, Jogada.LACAIO_ATACA_HEROI);
                } else {
                    return null;
                }
            } else if (solto.contains(Values.TO_STRING_LACAIO) && oponente.isCard(alvo)) {
                pack.set(Param.TIPO_JOGADA, Jogada.LACAIO_ATACA_LACAIO);
            } else {
                return null;
            }
            return validarAlvoAtaque(alvo) ? pack : null;
        } else {
            GameCliente.showPopUp("Este lacaio não pode atacar!");
            return null;
        }
    }

    /**
     * Verifica se o lacaio passado por parâmetro pode atacar herói
     *
     * @param lacaio lacaio atacando
     * @return {@code true} or {@code false}
     */
    private boolean podeAtacarHeroi(Card lacaio) {
        switch (lacaio.getId()) {
            //Uivagelo (Investida. Não pode atacar heróis)
            case "AT_125":
                return lacaio.isSilenciado();
        }
        return true;
    }

    /**
     * Captura o id_long da string passada por parâmetro
     *
     * @param id toString com o id_long após ponto-e-vírgula
     * @return id_long ou Param.ALVO_CANCEL para Exception
     */
    public static long getIdLong(String id) {
        try {
            return Long.parseLong(id.substring(id.lastIndexOf(";") + 1));
        } catch (Exception e) {
            return Param.ALVO_CANCEL;
        }
    }

    public void addJogada(Runnable runnable) {
        runList.add(runnable);
    }

    public void iniciarThreadExecutarJogadas() {
        new Thread(() -> {
            while (getPartidaView().gameCliente.onLine) {
                if (!runList.isEmpty() && !partidaView.isBloqueado()) {
                    runList.remove(0).run();
                }
                Utils.sleep(50);
            }
        }).start();
    }

    public void finalizar() {
        if (hero != null) {
            hero.finalizar();
        }
        if (oponente != null) {
            oponente.finalizar();
        }
        for (Card card : allCards) {
            card.finalizar();
            card = null;
        }
        allCards.clear();
        Partida p = getInstance();
        p = null;
    }

}