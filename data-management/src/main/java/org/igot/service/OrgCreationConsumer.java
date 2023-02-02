package org.igot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.igot.model.ApiRespOrgainsation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class OrgCreationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgCreationConsumer.class);
    ObjectMapper mapper = new ObjectMapper();
    Gson gson = new Gson();

    @Autowired
    OrgService orgService;



    @KafkaListener(topicPartitions = {
            @TopicPartition(topic = "${kafka.topics.org.creation}", partitions = { "0", "1", "2", "3" }) })
    public void processAutoCreateUserEvent(ConsumerRecord<String, String> data) {
        try {
            ApiRespOrgainsation org = gson.fromJson(data.value(), ApiRespOrgainsation.class);
            LOGGER.info("Consumed Request in Topic to create org:: " + mapper.writeValueAsString(org));
            orgService.initiateCreateOrgFlow(org);
        } catch (Exception e) {
            LOGGER.error("Failed to process message in Topic to create org.", e);
        }
    }

    
}
