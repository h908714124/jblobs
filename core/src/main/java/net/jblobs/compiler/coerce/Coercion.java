package net.jblobs.compiler.coerce;

import com.squareup.javapoet.CodeBlock;

import java.util.Optional;

public class Coercion {

    private final Optional<MapperInfo> mapperInfo;

    private final CodeBlock extractExpr;

    private Coercion(Optional<MapperInfo> mapperInfo, CodeBlock extractExpr) {
        this.mapperInfo = mapperInfo;
        this.extractExpr = extractExpr;
    }

    static Coercion create(Optional<MapperInfo> mapperInfo, CodeBlock extractExpr) {
        return new Coercion(mapperInfo, extractExpr);
    }

    public Optional<MapperInfo> getMapperInfo() {
        return mapperInfo;
    }

    public CodeBlock getExtractExpr() {
        return extractExpr;
    }
}
