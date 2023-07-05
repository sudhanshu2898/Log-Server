# Log Server #

A centralised log server (Microservice) written in java using Java NIO.

This Log Server generates separate logs for each connected service.

It can generate following levels of logs:-

* INFO 
* EMERGENCY 
* CRITICAL 
* ALERT 
* ERROR
* WARNING

Logs can be created in CSV or JSON format.

### *Starting Server:* 💻 ###

Server can be started simply by creating instance of Server class.  
Server class has 2 constructor, one is default and other is parameterised.  
Default constructor runs the server with following configuration:-
* Host: `localhost`
* Port: `7777`
* Max Bytes: `524288`
* Worker Thread: `1`

With parameterised constructor we can specify our own configuration:

```
/*
    Parameterised constructor:
    Server(String serverHost, int serverPort, int maxBytes, int workerThreadSize);
*/
//Creating Instance
new Server("localhost", 9999, 2048, 2);

```

Once Server is started, you are good to go.
you can connect to server using any websocket client or program 🎉.

### *Registering Service:* 🔗 ###
To Register a service just connect your service via websocket to server on `localhost:7777` or wherever you have started the server.

The moment you connect to the server, server is expecting to reply with a service name. respond with a JSON string with unique service name.    

Eg: `{"service":"accounts-service"}`

And BOOM 💥💥🎉🎉. Your service is now registered with the Log-Server. Now you can generate logs.

### *Generating Logs:* 📝 ###
Generating logs is very simple, send your log as JSON string to server 👌 .

JSON string should have 3 objects only.
* level: Indicating Log level, should be one amoung those mentioned above.
* format: Indicating log format, should be either CSV or JSON only.
* row: A JSON String representing data.

Eg: `{"level":"INFO", "format":"CSV", "row":{"message":"This is a test message", "url":"github.com/sudhanshu2898"}}`

### *Example:* ###

___Java Example:___

```
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) {
        
        try{
        
            JSONObject auth = new JSONObject();
            auth.put("service", "sample-client-service");

            JSONObject row = new JSONObject();
            row.put("name", "Sudhanshu Jha");
            row.put("state", "A.P.");
            row.put("message", "This is a simple message");

            JSONObject log = new JSONObject();
            log.put("level", "INFO");
            log.put("format", "CSV");
            log.put("row", row);

            Socket socket = new Socket("localhost", 7777);
            if(socket.isConnected()){
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(auth.toString().getBytes(StandardCharsets.UTF_8));
                Thread.sleep(100);
                outputStream.write(log.toString().getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }
            socket.close();
        }catch (Exception ex){
            System.out.println("Exception: "+ex.getMessage());
        }
    }
}
```
***
