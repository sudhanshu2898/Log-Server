package logging.service.models;

import java.util.Map;

public class LogRequestDTO {
    LogLevel level;
    LogFormat format;
    Map<String, String> row;

    public LogRequestDTO() {
    }

    public LogRequestDTO(LogLevel level, LogFormat format, Map<String, String> row) {
        this.level = level;
        this.format = format;
        this.row = row;
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