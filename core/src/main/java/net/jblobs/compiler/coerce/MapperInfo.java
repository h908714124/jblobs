package net.jblobs.compiler.coerce;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public final class MapperInfo {

    private final TypeElement mapperType;

    private final TypeMirror supplierType;

    private MapperInfo(TypeElement mapperType, TypeMirror supplierType) {
        this.mapperType = mapperType;
        this.supplierType = supplierType;
    }

    static MapperInfo create(TypeElement mapperType, TypeMirror supplierType) {
        return new MapperInfo(mapperType, supplierType);
    }
}
