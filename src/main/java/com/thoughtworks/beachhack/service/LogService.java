package com.thoughtworks.beachhack.service;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.beachhack.model.LogEntry;
import com.thoughtworks.beachhack.model.LogLevel;

import java.time.Clock;
import java.util.UUID;

public class LogService {

    private final ElasticSearchClient elasticSearchClient;
    private final IdGenerator idGenerator;
    private final Clock clock;

    public LogService() {
        this(new ElasticSearchClient(), new IdGenerator(), Clock.systemUTC());
    }

    public LogService(ElasticSearchClient elasticSearchClient, IdGenerator idGenerator, Clock clock) {
        this.elasticSearchClient = elasticSearchClient;
        this.idGenerator = idGenerator;
        this.clock = clock;
    }

    public void log(LogLevel level, String message) {
        String id = idGenerator.generate();
        LogEntry logEntry = new LogEntry()
                .setLogLevel(level)
                .setMessage(message)
                .setId(id)
                .setTimestamp(clock.millis());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            elasticSearchClient.sendDocumentToElasticSearch(null, id, objectMapper.writeValueAsString(logEntry));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }
}
