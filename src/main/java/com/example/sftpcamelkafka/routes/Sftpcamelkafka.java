package com.example.sftpcamelkafka.routes;

import com.example.sftpcamelkafka.dto.Player;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.BindyDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.UUID;

@Component
public class Sftpcamelkafka extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        BindyDataFormat bindy = new BindyDataFormat();
        bindy.setClassType(Player.class);
        bindy.setType("Fixed");
        bindy.setLocale("en");

        from(getSFTPUrl())
                .unmarshal(bindy)
                .log("${body}")
                .split(body())
                .setHeader("name",simple("${body.name}"))
                .marshal()
                .json(JsonLibrary.Jackson)
                .log("${body}")
                .setHeader(KafkaConstants.KEY,constant(UUID.randomUUID()))
                .setHeader("myHeader",constant("MY_HEADER_VALUE"))
                .to("kafka:myKafkaTopic?brokers=localhost:9092");
     }

    private String  getSFTPUrl() throws URISyntaxException {
        return new URIBuilder()
                .setScheme("sftp")
                .setHost("127.0.0.1")
                .setPort(2222)
                .setPath("/upload/in")
                .addParameter("username", "foo")
                .addParameter("password", "pass")
                .addParameter("passiveMode", "false")
                .addParameter("initialDelay", "10s")
                .addParameter("delay", "50")
                .addParameter("moveFailed", "/upload/error")
                .addParameter("move", "/upload/done")
                .addParameter("preMove", "/upload/in-progress")
                .addParameter("readLock", "changed")
                .addParameter("readLockMinAge", "1m")
                .addParameter("readLockTimeout", "70000")
                .addParameter("readLockCheckInterval", "5000")
                .addParameter("stepwise", "false")
                .addParameter("useUserKnownHostsFile", "false")
                .build()
                .toString();
    }
}
