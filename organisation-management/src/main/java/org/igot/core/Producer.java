package org.igot.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.igot.logger.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    //private CbExtLogger log = new CbExtLogger(getClass().getName());
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Producer.class);


    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void push(String topic, Object value) {
        ObjectMapper mapper = new ObjectMapper();
        String message = null;
        try {
            message = mapper.writeValueAsString(value);
            kafkaTemplate.send(topic, message);
        } catch (JsonProcessingException e) {
            //log.error(e);
        }
    }

    
}
