package inf.puc.rio.opus.composite.analysis.analysis.refactoring;

import inf.puc.rio.opus.composite.analysis.utils.CompositeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RefactoringAnalyzer {

    public static void main(String[] args) {

        List<String> projects = new ArrayList<>(Arrays.asList(
        "achilles",
        "android-async",
        "async-http-client",
        "byte-buddy",
        "checkstyle",
        "geoserver",
        "hikariCP",
         "hystrix",
        "java-driver",
        "jackson-databind",
        "jitwatch",
        "material-dialogs",
        "material-drawer",
        "mockito",
         "netty",
        "rest-assured",
        "retrolambda",
         "quasar",
        "realm-java",
        "xabber-android"));

        CompositeUtils.countComposites(projects);
    }
}
