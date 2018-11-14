package net.jblobs.compiler.coerce;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.ExecutableElement;
import java.util.Optional;

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
        return Coercion.create(Optional.empty(), CodeBlock.builder().build());
    }
}
