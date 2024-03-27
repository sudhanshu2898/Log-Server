package log.commons.models;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LogObject implements Comparable<LogObject>{

    String time;
    String service;
    String bucket;
    LogLevel level;
    LogFormat format;
    Map<String, String> row;

    public LogObject(String time, String service, String bucket, LogLevel level, LogFormat format, Map<String, String> row){
        this.time = time;
        this.service = service;
        this.bucket = bucket;
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

    public String getBucket(){
        return bucket;
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

    @Override
    public String toString(){
        Map<String, Object> variables = new HashMap<>();
        variables.put("time", time);
        variables.put("service", service);
        variables.put("bucket", bucket);
        variables.put("level", level);
        variables.put("format", format);
        variables.put("row", row);
        return new Gson().toJson(variables);
    }
}
