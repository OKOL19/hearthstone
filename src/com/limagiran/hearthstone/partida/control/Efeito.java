package  com.limagiran.hearthstone.partida.control;

import java.io.Serializable;

/**
 *
 * @author Vinicius Silva
 */
public class Efeito implements Serializable {

    public static final int INICIO_TURNO = 0;
    public static final int FINAL_TURNO = 1;
    private String id;
    private int momento;
    private int sleep;
    private Pacote pacote;

    public Efeito(String id, int momento, int sleep, Pacote pacote) {
        this.id = id;
        this.momento = momento;
        this.sleep = sleep;
        this.pacote = pacote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMomento() {
        return momento;
    }

    public void setMomento(int momento) {
        this.momento = momento;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setParam(Pacote pacote) {
        this.pacote = pacote;
    }
    
    public void decrement(){
        sleep--;
    }
    
    public String getParamString(String param) {
        return pacote.getParamString(param);
    }

    public int getParamInt(String param) {
        return pacote.getParamInt(param);
    }

    public long getParamLong(String param) {
        return pacote.getParamLong(param);
    }
    
    public boolean getParamBoolean(String param) {
        return pacote.getParamBoolean(param);
    }
    
}