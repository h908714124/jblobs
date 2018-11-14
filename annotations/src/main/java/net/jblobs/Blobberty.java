package net.jblobs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Blobberty {

    String key();

    boolean optional() default false;

    Class<? extends Supplier> mappedBy() default Supplier.class;
}
