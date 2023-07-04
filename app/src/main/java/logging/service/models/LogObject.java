package logging.service.models;

import java.util.Map;

public class LogObject implements Comparable<LogObject>{

    String time;
    String service;
    LogLevel level;
    LogFormat format;
    Map<String, String> row;

    public LogObject(String time, String service, LogLevel level, LogFormat format, Map<String, String> row){
        this.time = time;
        this.service = service;
        this.level = level;
        this.format = format;
        this.row = row;
    }

    public String getTime() {
        return time;
    }

    public String getService(){
        return service;
    }

    public LogLevel getLevel() {
        return level;
    }

    public LogFormat getFormat() {
        return format;
    }

    public Map<String, String> getRow() {
        return row;
    }

    @Override
    public int compareTo(LogObject logObject) {
        return 0;
    }
}
