package com.erwindev.opentracing.server1.util;

import brave.Tracing;
import brave.opentracing.BraveTracer;
import com.uber.jaeger.Configuration;
import com.uber.jaeger.samplers.ConstSampler;
import io.opentracing.Scope;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapExtractAdapter;
import io.opentracing.tag.Tags;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

import java.util.HashMap;

public class TraceUtil {
    public static com.uber.jaeger.Tracer jaegerTracer(String service) {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
                .withType(ConstSampler.TYPE)
                .withParam(1);

        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
                .withLogSpans(true);

        Configuration config = new Configuration(service)
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);

        return (com.uber.jaeger.Tracer) config.getTracer();
    }

    public static io.opentracing.Tracer zipkinTracer(String service) {
        OkHttpSender sender = OkHttpSender.create("http://127.0.0.1:9411/api/v2/spans");
        AsyncReporter spanReporter = AsyncReporter.create(sender);

        Tracing braveTracing = Tracing.newBuilder()
                .localServiceName(service)
                .spanReporter(spanReporter)
                .build();

        return BraveTracer.create(braveTracing);
    }

    public static Scope startServerSpan(Tracer tracer, org.springframework.http.HttpHeaders httpHeaders,
                                        String operationName) {
        // format the headers for extraction
        final HashMap<String, String> headers = new HashMap<String, String>();
        for (String key : httpHeaders.keySet()){
            headers.put(key, httpHeaders.get(key).get(0));
        }

        Tracer.SpanBuilder spanBuilder;
        try {
            SpanContext parentSpanCtx = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers));
            if (parentSpanCtx == null) {
                spanBuilder = tracer.buildSpan(operationName);
            } else {
                spanBuilder = tracer.buildSpan(operationName).asChildOf(parentSpanCtx);
            }
        } catch (IllegalArgumentException e) {
            spanBuilder = tracer.buildSpan(operationName);
        }
        // TODO could add more tags like http.url
        return spanBuilder.withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_SERVER).startActive(true);
    }
}
