package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
@Slf4j
public class TimerRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer://timer1 period=1000")
                .process(new Processor() {
                    public void process(Exchange msg) {
                        log.info("Processing {}", msg);
                    }});
    }
}
