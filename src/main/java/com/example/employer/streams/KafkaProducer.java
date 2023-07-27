package com.example.employer.streams;

import com.example.employer.markers.ConnectivityMarkers;
import com.example.employer.model.Employer;
import com.example.employer.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
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
        produceLogsForKey(topic, key, event);
        kafkaTemplate.send(topic, key, event);
    }

    private static void produceLogsForKey(String topic, String key, Object event) {
        if (log.isDebugEnabled()) {
            log.debug(ConnectivityMarkers.combine(
                            Markers.append("event", JsonUtils.toPrettyJson(event)),
                            Markers.append("key", JsonUtils.toPrettyJson(key))),
                    "sending payload to topic {} with key {}", topic, key);
        }
    }
}
