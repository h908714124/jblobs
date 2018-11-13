package net.jblobs.compiler.coerce;

import javax.lang.model.element.ExecutableElement;

public class CoercionProvider {

    private static CoercionProvider instance;

    public static CoercionProvider get() {
        if (instance == null) {
            instance = new CoercionProvider();
        }
        return instance;
    }

    public Coercion findCoercion(
            ExecutableElement sourceMethod, boolean optional) {
        return null;
    }
}
