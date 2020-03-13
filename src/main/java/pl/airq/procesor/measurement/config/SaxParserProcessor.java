package pl.airq.procesor.measurement.config;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;

public class SaxParserProcessor {

    @BuildStep
    ReflectiveClassBuildItem reflection() {
        // since we only need reflection to the constructor of the class, we can specify `false` for both the methods and the fields arguments.
        return new ReflectiveClassBuildItem(false, false, "com.sun.org.apache.xerces.internal.parsers.SAXParser");
    }

}
