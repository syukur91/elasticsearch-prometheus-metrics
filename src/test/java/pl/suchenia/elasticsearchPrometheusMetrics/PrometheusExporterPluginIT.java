package pl.suchenia.elasticsearchPrometheusMetrics;

import com.carrotsearch.randomizedtesting.annotations.Name;
import com.carrotsearch.randomizedtesting.annotations.ParametersFactory;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.test.rest.yaml.ClientYamlTestCandidate;
import org.elasticsearch.test.rest.yaml.ESClientYamlSuiteTestCase;

import java.io.IOException;
import java.util.List;

public class PrometheusExporterPluginIT extends ESClientYamlSuiteTestCase {

    public PrometheusExporterPluginIT(@Name("yaml") ClientYamlTestCandidate testCandidate) {
        super(testCandidate);
    }

    @ParametersFactory
    public static Iterable<Object[]> parameters() throws IOException {
        return ESClientYamlSuiteTestCase.createParameters();
    }
}
