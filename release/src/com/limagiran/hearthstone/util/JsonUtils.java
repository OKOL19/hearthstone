package  com.limagiran.hearthstone.util;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.limagiran.hearthstone.partida.control.Pacote;

/**
 *
 * @author Vinicius
 */
public class JsonUtils {

    private static final Gson GSON = new Gson();

    /**
     * Converte o arquivo JsonUtils em Pacote
     * @param json String JsonUtils
     * @return objeto Pacote
     */
    public static Pacote toObject(String json) {
        return GSON.fromJson(json, Pacote.class);
    }

    /**
     * Converte um Pacote em JsonUtils
     * @param pacote objeto Pacote
     * @return String JsonUtils
     */
    public static String toJSon(Pacote pacote) {
        return GSON.toJson(pacote);
    }

    /**
     * Converte um objeto em JsonUtils
     * @param object objeto
     * @return String JsonUtils
     */
    public static String toJSon(Object object) {
        return GSON.toJson(object);
    }

    /**
     * Instancia um objeto Gson onde exclui as variáveis com anotação
     * @return Objeto Gson
     */
    public static Gson newGsonExcluirAnotados() {
        return new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).addDeserializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.deserialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create();
    }
}