package net.jblobs.compiler;

import com.squareup.javapoet.TypeSpec;

import java.util.List;

class Blob {

    private final Context context;

    private final List<Param> params;

    private Blob(Context context, List<Param> params) {
        this.context = context;
        this.params = params;
    }

    static Blob create(Context context, List<Param> params) {
        return new Blob(context, params);
    }

    TypeSpec define() {
        TypeSpec.Builder spec = TypeSpec.classBuilder(context.generatedClass);
        return spec.build();
    }
}
