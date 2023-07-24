package com.example.employer.streams;

import com.example.employer.dto.EmployerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    public static final String TOPIC_PRODUCT = "employer";


    public void sendProducts(EmployerDTO event) {
        send(TOPIC_PRODUCT, event.getId(), event);
    }

    private void send(String topic, String key, Object event) {
        log.info("sending payload='{}' with key='{}' to topic='{}'", event, key, topic);
        kafkaTemplate.send(topic, key, event);
    }
}
