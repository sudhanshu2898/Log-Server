package log.writer.core;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import log.commons.models.LogFormat;
import log.commons.models.LogLevel;
import log.commons.models.LogObject;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Logger {
    long maxFileSize = 52428800L; //50MB
    public void log(LogObject log){
        if(log != null && log.getBucket() != null && log.getBucket().length() > 0 && log.getFormat() != null && log.getLevel() != null && log.getRow() != null){
            if(log.getFormat() == LogFormat.CSV) {
                logCSV(log.getTime(), log.getService(), log.getBucket(), log.getLevel(), log.getRow());
            }else if(log.getFormat() == LogFormat.JSON){
                logJSON(log.getTime(), log.getService(), log.getBucket(), log.getLevel(), log.getRow());
            }
        }
    }

    private void logCSV(String time, String service, String bucket, LogLevel level, Map<String, String> row){
        try {

            row.put("time", time);

            File fileHandler = getFileHandler(service, bucket, level, LogFormat.CSV);
            CSVReader csvReader = new CSVReader(new FileReader(fileHandler));
            CSVWriter csvWriter = new CSVWriter(new FileWriter(fileHandler, true));

            String[] header = csvReader.readNext();
            if(header == null){
                List<String> tempHeader = new ArrayList<>();
                List<Object> tempRow = new ArrayList<>();
                row.forEach((key, value) -> {
                    tempHeader.add(key);
                    tempRow.add(value);
                });
                header = tempHeader.toArray(new String[0]);
                csvWriter.writeNext(header);
                csvWriter.writeNext(tempRow.toArray(new String[0]));
            }else{
                /**
                 * Arrange map values in order of header,
                 * Check if there is any new header also, if yes then update all rows and header.
                 * **/
                List<Object> tempRow = new ArrayList<>();
                for (String s : header) {
                    Object item = row.remove(s);
                    if (item == null) {
                        tempRow.add("");
                    } else {
                        tempRow.add(item);
                    }
                }

                if(row.size() == 0){
                    csvWriter.writeNext(tempRow.toArray(new String[0]));
                }else{

                    //Add empty cols to all row
                    List<String[]> allData = csvReader.readAll();
                    for(int i=0; i<allData.size(); i++){
                        List<String> localRow = new ArrayList<>();
                        String[] oldRow = allData.get(i);
                        for(int j=0; j<oldRow.length; j++){
                            localRow.add(oldRow[j]);
                        }
                        row.forEach((key, value)->{
                            localRow.add("");
                        });
                        allData.set(i, localRow.toArray(new String[0]));
                    }

                    //Create new Header
                    List<String> tempHeader = new ArrayList<>();
                    for(int i=0; i<header.length; i++){
                        tempHeader.add(header[i]);
                    }
                    row.forEach((key, value) -> {
                        tempHeader.add(key);
                        tempRow.add(value);
                    });

                    //Add header to all data
                    allData.add(0, tempHeader.toArray(new String[0]));

                    //add new row to all data
                    allData.add(tempRow.toArray(new String[0]));

                    CSVWriter tempCSVWriter = new CSVWriter(new FileWriter(fileHandler));
                    tempCSVWriter.writeAll(allData);
                    tempCSVWriter.close();
                }
            }

            csvReader.close();
            csvWriter.close();
        } catch (Exception ex) {
            System.out.println("Exception in writing CSV :"+ex.getMessage());
        }
    }

    private void logJSON(String time, String service, String bucket, LogLevel level, Map<String, String> row){
        try {
            row.put("time", time);

            FileWriter writer = new FileWriter(getFileHandler(service, bucket, level, LogFormat.JSON), true);
            writer.write(new Gson().toJson(row));
            writer.close();
        }catch (Exception ex){
            System.out.println("Exception in writing JSON :"+ex.getMessage());
        }
    }

    private String getFilePath(String service, String bucket, LogLevel level, LogFormat format, int index){
        String filePath = "logs/"+service.toLowerCase()+"/"+bucket.toLowerCase()+"/"+level.toString().toLowerCase()+"-"+index;
        if(format == LogFormat.CSV){
            filePath = filePath + ".csv";
        }else if(format == LogFormat.JSON){
            filePath = filePath + ".json";
        }
        return filePath;
    }

    private void createFile(String service, String bucket, LogLevel level, LogFormat format, int index){
        try{
            Path logDirPath = Paths.get("logs/"+service+"/"+bucket);
            Files.createDirectories(logDirPath);

            File file = new File(getFilePath(service, bucket, level, format, index));
            if(!file.exists() || !file.isFile()){
                file.createNewFile();
            }
        }catch (Exception ex){
            System.out.println("Exception in creating file :"+ex.getMessage());
        }
    }

    private File getFileHandler(String service, String bucket, LogLevel level, LogFormat format){
        int index = 0;
        File file = null;
        boolean runLoop = true;
        while(runLoop){
            String filePath = getFilePath(service, bucket, level, format, index++);
            file = new File(filePath);
            if(file.exists() && file.isFile()){
                if(file.length() < maxFileSize){
                    runLoop = false;
                }
            }else{
                createFile(service, bucket, level, format, index);
            }
        }
        return file;
    }

}