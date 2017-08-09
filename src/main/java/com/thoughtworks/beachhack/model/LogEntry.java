package com.thoughtworks.beachhack.model;

public class LogEntry {
    private String id;
    private LogLevel logLevel;
    private String message;
    private long timestamp;

    public String getId() {
        return id;
    }

    public LogEntry setId(String id) {
        this.id = id;
        return this;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public LogEntry setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LogEntry setMessage(String message) {
        this.message = message;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public LogEntry setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
