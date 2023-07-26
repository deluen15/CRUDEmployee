package com.example.employer.streams;

import com.example.employer.model.Employer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttProducer {
    private static final String EMPLOYER_TOPIC = "employer-topic";

    @Value("${mqtt.broker.url}")
    private String mqttBrokerUrl;

    @Value("${mqtt.client.id}")
    private String mqttClientId;

    public void sendEmployerMessage(Employer event) throws MqttException {
        send(EMPLOYER_TOPIC, event.getId(), event);
    }

    private void send(String topic, String key, Object event) throws MqttException {
        MqttClient mqttClient = new MqttClient(mqttBrokerUrl, mqttClientId, new MemoryPersistence());
        mqttClient.connect();

        String payload = event.toString();
        MqttMessage mqttMessage = new MqttMessage(payload.getBytes());
        mqttMessage.setQos(1);

        mqttClient.publish(topic, mqttMessage);
        log.info("sending payload='{}' with key='{}' to topic='{}'", payload, key, topic);

        mqttClient.disconnect();
    }
}
