package net.jblobs.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import net.jblobs.MapBlob;
import net.jblobs.Blobberty;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.util.ElementFilter.methodsIn;

public final class Processor extends AbstractProcessor {

    private final boolean debug;

    public Processor() {
        this(false);
    }

    // visible for testing
    Processor(boolean debug) {
        this.debug = debug;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream.of(MapBlob.class, Blobberty.class)
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
            Set<TypeElement> typeElements = ElementFilter.typesIn(env.getElementsAnnotatedWith(MapBlob.class));
            for (TypeElement sourceType : typeElements) {
                List<Param> parameters = getParams(sourceType);
                Context context = Context.create(sourceType);
                TypeSpec typeSpec = Blob.create(context, parameters).define();
                write(sourceType, context.generatedClass, typeSpec);
            }
        } finally {
            TypeTool.unset();
        }
        return false;
    }

    private void write(
            TypeElement sourceType,
            ClassName generatedType,
            TypeSpec definedType) {
        JavaFile.Builder builder = JavaFile.builder(generatedType.packageName(), definedType);
        JavaFile javaFile = builder
                .skipJavaLangImports(true)
                .build();
        try {
            JavaFileObject sourceFile = processingEnv.getFiler()
                    .createSourceFile(generatedType.toString(),
                            javaFile.typeSpec.originatingElements.toArray(new Element[0]));
            try (Writer writer = sourceFile.openWriter()) {
                String sourceCode = javaFile.toString();
                writer.write(sourceCode);
                if (debug) {
                    System.err.println("##############");
                    System.err.println("# Debug info #");
                    System.err.println("##############");
                    System.err.println(sourceCode);
                }
            } catch (IOException e) {
                handleIOException(sourceType, e);
            }
        } catch (IOException e) {
            handleIOException(sourceType, e);
        }
    }

    private List<Param> getParams(TypeElement sourceType) {
        List<ExecutableElement> abstractMethods = methodsIn(sourceType.getEnclosedElements()).stream()
                .filter(method -> method.getModifiers().contains(ABSTRACT))
                .collect(toList());
        List<Param> result = new ArrayList<>(abstractMethods.size());
        for (ExecutableElement sourceMethod : abstractMethods) {
            result.add(Param.create(result, sourceMethod));
        }
        return result;
    }

    private Elements getElementUtils() {
        return processingEnv.getElementUtils();
    }

    private Types getTypeUtils() {
        return processingEnv.getTypeUtils();
    }

    private void handleIOException(
            TypeElement sourceType,
            IOException e) {
        String message = String.format("Unexpected error while processing %s: %s", sourceType, e.getMessage());
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, sourceType);
    }
}
