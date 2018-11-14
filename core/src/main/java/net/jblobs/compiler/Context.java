package net.jblobs.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.TypeElement;

class Context {

    final TypeElement sourceType;

    final ClassName generatedClass;

    private Context(TypeElement sourceType, ClassName generatedClass) {
        this.sourceType = sourceType;
        this.generatedClass = generatedClass;
    }

    static Context create(TypeElement sourceType) {
        return new Context(sourceType, parserClass(sourceType));
    }

    TypeSpec define() {
        return null;
    }

    private static ClassName parserClass(TypeElement typeElement) {
        ClassName type = ClassName.get(typeElement);
        String name = String.join("_", type.simpleNames()) + "_Parser";
        return type.topLevelClassName().peerClass(name);
    }
}
