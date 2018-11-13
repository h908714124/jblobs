package net.jblobs.compiler;

import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaFileObjects.forSourceLines;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;
import static java.util.Collections.singletonList;

class ProcessorTest {

    @Test
    void invalidPrimitiveReturnTypeNotOptional() {
        List<String> sourceLines = withImports(
                "@MapBlob",
                "abstract class ValidArguments {",
                "",
                "  @Property",
                "  abstract int x();",
                "}");
        JavaFileObject javaFile = forSourceLines("test.ValidArguments", sourceLines);
        assertAbout(javaSources()).that(singletonList(javaFile))
                .processedWith(new Processor())
                .compilesWithoutError();
    }

    private static List<String> withImports(String... lines) {
        List<String> header = Arrays.asList(
                "package test;",
                "",
                "import net.jblobs.MapMirror;",
                "import net.jblobs.Parameter;",
                "");
        List<String> moreLines = new ArrayList<>(lines.length + header.size());
        moreLines.addAll(header);
        Collections.addAll(moreLines, lines);
        return moreLines;
    }
}