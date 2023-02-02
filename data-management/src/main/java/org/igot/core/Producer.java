package org.igot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    //private CbExtLogger log = new CbExtLogger(getClass().getName());
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Producer.class);


/*    @Autowired
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
    } */
}
