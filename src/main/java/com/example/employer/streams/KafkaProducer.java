package com.example.employer.streams;

import com.example.employer.model.Employer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    public static final String EMPLOYER_TOPIC = "employer";


    public void sendEmployerMessage(Employer event) {
        send(EMPLOYER_TOPIC, event.getId(), event);
    }

    private void send(String topic, String key, Object event) {
        log.info("sending payload='{}' with key='{}' to topic='{}'", event, key, topic);
        kafkaTemplate.send(topic, key, event);
    }
}
