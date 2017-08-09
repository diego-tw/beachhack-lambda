package com.thoughtworks.beachhack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.beachhack.model.LogEntry;
import com.thoughtworks.beachhack.model.LogLevel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {

    @Mock
    private ElasticSearchClient elasticSearchClient;

    @Mock
    private IdGenerator idGenerator;

    private Clock clock;

    private LogService logService;

    @Before
    public void setup() {
        clock = Clock.fixed(Instant.ofEpochMilli(1000), ZoneOffset.UTC);
        logService = new LogService(elasticSearchClient, idGenerator, clock);
    }

    @Test
    public void logShouldSendMessageToElasticSearch() {
        when(idGenerator.generate()).thenReturn("myid");

        logService.log(LogLevel.INFO, "This is our message");

        String expectedMessage = createLogEntryMessage(LogLevel.INFO, "This is our message", "myid");
        verify(elasticSearchClient).sendDocumentToElasticSearch(any(), eq("myid"), eq(expectedMessage));
    }

    @Test
    public void warnShouldSendCorrectMessageToElasticSearch() {
        when(idGenerator.generate()).thenReturn("foobar");

        logService.warn("This is a warning message");

        String expectedMessage = createLogEntryMessage(LogLevel.WARN, "This is a warning message", "foobar");
        verify(elasticSearchClient).sendDocumentToElasticSearch(any(), eq("foobar"), eq(expectedMessage));
    }

    @Test
    public void infoShouldSendCorrectMessageToElasticSearch() {
        when(idGenerator.generate()).thenReturn("foobar");

        logService.info("This is an info message");

        String expectedMessage = createLogEntryMessage(LogLevel.INFO, "This is an info message", "foobar");
        verify(elasticSearchClient).sendDocumentToElasticSearch(any(), eq("foobar"), eq(expectedMessage));
    }

    @Test
    public void debugShouldSendCorrectMessageToElasticSearch() {
        when(idGenerator.generate()).thenReturn("foobar");

        logService.debug("This is a debug message");

        String expectedMessage = createLogEntryMessage(LogLevel.DEBUG, "This is a debug message", "foobar");
        verify(elasticSearchClient).sendDocumentToElasticSearch(any(), eq("foobar"), eq(expectedMessage));
    }

    @Test
    public void errorShouldSendCorrectMessageToElasticSearch() {
        when(idGenerator.generate()).thenReturn("foobar");

        logService.error("This is an error message");

        String expectedMessage = createLogEntryMessage(LogLevel.ERROR, "This is an error message", "foobar");
        verify(elasticSearchClient).sendDocumentToElasticSearch(any(), eq("foobar"), eq(expectedMessage));
    }

    @Test
    public void fatalShouldSendCorrectMessageToElasticSearch() {
        when(idGenerator.generate()).thenReturn("foobar");

        logService.fatal("This is a fatal error message");

        String expectedMessage = createLogEntryMessage(LogLevel.FATAL, "This is a fatal error message", "foobar");
        verify(elasticSearchClient).sendDocumentToElasticSearch(any(), eq("foobar"), eq(expectedMessage));
    }

    private String createLogEntryMessage(LogLevel logLevel, String message, String id) {
        LogEntry logEntry = new LogEntry()
                .setId(id)
                .setMessage(message)
                .setLogLevel(logLevel)
                .setTimestamp(clock.millis());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(logEntry);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
