package log.writer.core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import log.commons.models.LogObject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class Consumer {
    KafkaConsumer<String, byte[]> kafkaConsumer;
    String bootstrapServer;
    String consumerGroup;
    String consumerTopic;
    Logger logger;

    public Consumer(){
        this.bootstrapServer = "localhost:9092";
        this.consumerGroup = "logs_consumer";
        this.consumerTopic = "logs";
        logger = new Logger();

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singletonList(consumerTopic));

        init();
    }

    private void init(){
        while (true){
            try{
                ConsumerRecords<String, byte[]> records = kafkaConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, byte[]> record : records){
                    LogObject logObject = new Gson().fromJson(new String(record.value()), LogObject.class);
                    logger.log(logObject);
                }
            }catch (Exception ex){

            }
        }
    }

}