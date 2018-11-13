package net.jblobs.compiler;

import com.sun.tools.example.debug.tty.TTY;
import net.jblobs.MapMirror;
import net.jblobs.Parameter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public final class Processor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream.of(MapMirror.class, Parameter.class)
                .map(Class::getCanonicalName)
                .collect(toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        try {
            TypeTool.setInstance(getTypeUtils(), getElementUtils());
        } finally {
            TypeTool.unset();
        }
        return false;
    }

    private Elements getElementUtils() {
        return processingEnv.getElementUtils();
    }

    private Types getTypeUtils() {
        return processingEnv.getTypeUtils();
    }
}
