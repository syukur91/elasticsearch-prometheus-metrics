package pl.suchenia.elasticsearchPrometheusMetrics.generators;

import org.apache.logging.log4j.Logger;
import org.elasticsearch.Version;
import org.elasticsearch.common.logging.Loggers;
import pl.suchenia.elasticsearchPrometheusMetrics.writer.PrometheusFormatWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class MetricsGenerator<T> {
    private static final Logger logger = Loggers.getLogger(MetricsGenerator.class);

    public PrometheusFormatWriter generateMetricsWithHeader(PrometheusFormatWriter writer, T inputParam) {
        String version = MetricsGenerator.class.getPackage().getImplementationVersion();
        writer.addGauge("es_prometheus_version")
                .withHelp("Plugin version to track across a cluster")
                .value(1, "pluginVersion", version, "es_version", Version.CURRENT.toString());

        return generateMetrics(writer, inputParam);
    }

    static double getDynamicValue(Object obj, String methodName) {
        try {
            Method method = obj.getClass().getMethod(methodName);
            Object value = method.invoke(obj);
            if (value.getClass().isAssignableFrom(Long.class)) {
                Long l = (Long) value;
                return l.doubleValue();
            }
            return (double) value;
        } catch (NoSuchMethodException e) {
            logger.error("There are no getTotalSizeInBytes method defined");
            return -1.0;
        } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
            logger.error("Exception during method invocation: {}", e.getMessage());
            return -1.0;
        }
    }
    abstract PrometheusFormatWriter generateMetrics(PrometheusFormatWriter writer, T inputData);
}
