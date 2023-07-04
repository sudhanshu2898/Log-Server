package logging.service.core;
import logging.service.models.LogObject;

import java.util.PriorityQueue;
import java.util.Queue;

public class MessageQueue extends Thread{

    volatile Queue<LogObject> currentQueue = new PriorityQueue<>();

    @Override
    public void run() {
        Logger logger = new Logger();
        while (true){
            if(currentQueue.size() > 0){
                logger.log(currentQueue.poll());
            }
        }
    }

    public void push(LogObject log){
        currentQueue.add(log);
    }
}
