package net.jblobs.compiler;

import com.squareup.javapoet.CodeBlock;
import net.jblobs.Parameter;
import net.jblobs.compiler.coerce.Coercion;
import net.jblobs.compiler.coerce.CoercionProvider;
import net.jblobs.compiler.coerce.MapperInfo;

import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.Optional;

class Param {

    // any string
    private final String key;

    // this is a java identifier
    private final String name;

    private final boolean optional;

    private final Optional<MapperInfo> mapperInfo;

    private final CodeBlock extractExpr;

    private Param(String key, String name, boolean optional, Optional<MapperInfo> mapperInfo, CodeBlock extractExpr) {
        this.key = key;
        this.name = name;
        this.optional = optional;
        this.mapperInfo = mapperInfo;
        this.extractExpr = extractExpr;
    }

    static Param create(
            List<Param> params,
            ExecutableElement sourceMethod) {
        boolean optional = isOptional(sourceMethod);
        String name = sourceMethod.getSimpleName().toString();
        Coercion coercion = CoercionProvider.get().findCoercion(sourceMethod, optional);
        CodeBlock extractExpr = coercion.getExtractExpr();
        Optional<MapperInfo> mapperInfo = coercion.getMapperInfo();
        String key = getKey(params, sourceMethod);
        return new Param(key, name, optional, mapperInfo, extractExpr);
    }

    private static boolean isOptional(ExecutableElement sourceMethod) {
        Parameter parameter = sourceMethod.getAnnotation(Parameter.class);
        return parameter.optional();
    }

    private static String getKey(List<Param> params, ExecutableElement sourceMethod) {
        Parameter parameter = sourceMethod.getAnnotation(Parameter.class);
        String key = parameter.key();
        for (Param param : params) {
            if (param.key.equals(key)) {
                throw ValidationException.create(sourceMethod, "Duplicate key");
            }
        }
        return key;
    }
}
