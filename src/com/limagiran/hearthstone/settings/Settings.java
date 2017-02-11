package com.limagiran.hearthstone.settings;

import com.limagiran.hearthstone.util.RWObj;
import com.limagiran.hearthstone.util.Utils;
import static java.util.Optional.ofNullable;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vinicius
 */
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final File FILE = new File("settings.dat");
    public static final Settings SET;

    private static boolean save = false;

    static {
        SET = load();
        Runtime.getRuntime().addShutdownHook(new Thread(SET::saveNow));
        threadSave();
    }
    
    /**
     * Carrega as configurações do programa
     */
    private static Settings load() {
        Settings _return = RWObj.read(FILE, Settings.class);
        return ((_return != null) ? _return : new Settings());
    }

    private static void threadSave() {
        new Thread(() -> {
            while (true) {
                Utils.sleep(3000);
                if (save) {
                    save = !save;
                    save();
                }
            }
        }).start();
    }

    private final Map<Object, Boolean> mapB = new HashMap();
    private final Map<Object, String> mapS = new HashMap();
    private final Map<Object, Long> mapL = new HashMap();
    private final Map<Object, Double> mapD = new HashMap();
    private final Map<Object, Integer> mapI = new HashMap();

    protected Settings() {
    }

    private synchronized static void save() {
        RWObj.write(SET, FILE);
    }

    public void saveNow() {
        save = false;
        save();
    }

    public void saveTrue() {
        save = true;
    }

    public static enum EnumSettings {
        HERO_NAME(String.class, ""),
        SERVER(String.class, ""),
        TIME_ANIMATION(Long.class, 1200L),
        VOLUME_EFFECT(Double.class, 1.0),
        VOLUME_MUSIC(Double.class, 1.0);

        public final Class clazz;
        public final Object _default;

        private EnumSettings(Class clazz, Object _default) {
            this.clazz = clazz;
            this._default = _default;
        }

        public boolean is() {
            return ofNullable(SET.mapB.get(name())).orElse(getDefault(Boolean.class, Boolean.FALSE));
        }

        public String getString() {
            return ofNullable(SET.mapS.get(name())).orElse(getDefault(String.class, ""));
        }

        public long getLong() {
            return ofNullable(SET.mapL.get(name())).orElse(getDefault(Long.class, 0L));
        }
        
        public double getDouble() {
            return ofNullable(SET.mapD.get(name())).orElse(getDefault(Double.class, 0d));
        }

        public int getInt() {
            return ofNullable(SET.mapI.get(name())).orElse(getDefault(Integer.class, 0));
        }

        private <T> T getDefault(Class<T> clazz, T _default) {
            try {
                return (clazz.isInstance(this._default) ? ((T) this._default) : _default);
            } catch (Exception e) {
                return _default;
            }
        }

        public <T> T set(T value) {
            if (String.class.isInstance(value)) {
                SET.mapS.put(name(), (String) value);
            } else if (Boolean.class.isInstance(value) || boolean.class.isInstance(value)) {
                SET.mapB.put(name(), Boolean.valueOf(value.toString()));
            } else if (Long.class.isInstance(value) || long.class.isInstance(value)) {
                SET.mapL.put(name(), Long.parseLong(value.toString()));
            } else if (Double.class.isInstance(value) || double.class.isInstance(value)) {
                SET.mapD.put(name(), Double.parseDouble(value.toString()));
            } else if (Integer.class.isInstance(value) || int.class.isInstance(value)) {
                SET.mapI.put(name(), Integer.parseInt(value.toString()));
            }
            save = true;
            return value;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Version">
    private long version = 1L;

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }
    //</editor-fold>

}