package com.example.demo;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocalTransformRoute extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger( LocalTransformRoute.class );

    @Value("${route.parserfile.info}")
    private String location;

    @Value("${route.parserfile.dir}")
    private String locationDir;

    @Autowired
    LocationFileProcessor locationFileProcessor;

    @Override
    public void configure() throws Exception {
        //from( location ).process( locationFileProcessor ).to( locationDir ).log(LoggingLevel.INFO, logger, "tirans  file ${file:name} complete.");
        from("file:C:/ftp/test/?fileName=test.csv&noop=true")
                .unmarshal().csv()
                .to("mock:daltons")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();
                        for (List<String> line : data) {
                            logger.debug(String.format("%s has an IQ of %s and is currently %s", line.get(0), line.get(1), line.get(2)));
                        }
                    }
                });
    }
}
