package net.jblobs.compiler;

import net.jblobs.MapMirror;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public final class Processor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream.of(MapMirror.class)
                .map(Class::getCanonicalName)
                .collect(toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        Set<TypeElement> typeElements = ElementFilter.typesIn(env.getElementsAnnotatedWith(MapMirror.class));
        TypeElement next = typeElements.iterator().next();
        List<? extends Element> elements = next.getEnclosedElements();
        List<ExecutableElement> executableElements = ElementFilter.methodsIn(elements);
        ExecutableElement method = executableElements.get(0);
        List<? extends Element> enclosedElements = method.getEnclosedElements();
        return false;
    }
}
