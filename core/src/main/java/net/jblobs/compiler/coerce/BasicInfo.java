package net.jblobs.compiler.coerce;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;

import javax.lang.model.type.TypeMirror;
import java.util.function.Function;

public final class BasicInfo {

    final boolean optional;

    private final LiftedType liftedType;

    private BasicInfo(boolean optional, LiftedType liftedType) {
        this.optional = optional;
        this.liftedType = liftedType;
    }

    static BasicInfo create(boolean optional, TypeMirror returnType) {
        return new BasicInfo(optional, LiftedType.lift(returnType));
    }

    TypeMirror returnType() {
        return liftedType.liftedType();
    }

    public TypeMirror originalReturnType() {
        return liftedType.liftedType();
    }

    Function<ParameterSpec, CodeBlock> extractExpr() {
        return liftedType.extractExpr();
    }
}
