package  com.limagiran.hearthstone.poder.util;

import com.limagiran.hearthstone.util.Utils;
import java.util.ArrayList;
import java.util.List;
import static com.limagiran.hearthstone.poder.control.PoderHeroico.*;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public class Util {

    /**
     * Retorna um id de poder heroico aleatório
     *
     * @return id do poder heroico
     */
    public static String getRandom() {
        return Values.PODER_HEROICO_BASICO[Utils.random(Values.PODER_HEROICO_BASICO.length) - 1];
    }

    /**
     * Retorna uma lista com ids de poderes heroicos aleatórios
     *
     * @param quantidade quantidade de poderes diferentes
     * @return List ids dos poderes heroicos
     */
    public static List<String> getRandom(int quantidade) {
        List<String> retorno = new ArrayList<>();
        if (Values.PODER_HEROICO_BASICO.length < quantidade) {
            return retorno;
        }
        while (retorno.size() < quantidade) {
            String poder = getRandom();
            if (!retorno.contains(poder)) {
                retorno.add(poder);
            }
        }
        return retorno;
    }

    /**
     * Retorna a versão melhorada do poder heroico
     *
     * @param id id do poder heroico atual
     * @return id do poder heroico correspondente melhorado ou o mesmo id para
     * poder não inicial
     */
    public static String poderMelhorado(final String id) {

        switch (id) {
            case PALADIN_DEFAULT:
                return PALADIN_MELHORADO;
            case ROGUE_DEFAULT:
                return ROGUE_MELHORADO;
            case PRIEST_DEFAULT:
                return PRIEST_MELHORADO;
            case WARRIOR_DEFAULT:
                return WARRIOR_MELHORADO;
            case WARLOCK_DEFAULT:
                return WARLOCK_MELHORADO;
            case MAGE_DEFAULT:
                return MAGE_MELHORADO;
            case DRUID_DEFAULT:
                return DRUID_MELHORADO;
            case HUNTER_DEFAULT:
                return HUNTER_MELHORADO;
            case SHAMAN_DEFAULT:
                return SHAMAN_MELHORADO;
            default:
                return id;
        }
    }
}