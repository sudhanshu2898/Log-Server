package log.server.dtos;

import log.commons.models.LogFormat;
import log.commons.models.LogLevel;

import java.util.Map;

public class LogRequestDTO {
    LogLevel level;
    LogFormat format;
    String bucket;
    Map<String, String> row;

    public LogRequestDTO() {
    }

    public LogRequestDTO(String bucket, LogLevel level, LogFormat format, Map<String, String> row) {
        this.bucket = bucket;
        this.level = level;
        this.format = format;
        this.row = row;
    }

    public String getBucket(){
        return bucket;
    }

    public void setBucket(String bucket){
        this.bucket = bucket;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public LogFormat getFormat() {
        return format;
    }

    public void setFormat(LogFormat format) {
        this.format = format;
    }

    public Map<String, String> getRow() {
        return row;
    }

    public void setRow(Map<String, String> row) {
        this.row = row;
    }
}