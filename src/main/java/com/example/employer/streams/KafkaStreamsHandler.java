package com.example.employer.streams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class KafkaStreamsHandler {
//    @Bean
//    public BiFunction<KStream<String, Employer>, KTable<String, Product>, KStream<String, com.bytes.springcloudproducts.model.Product>> mapDataPoints() {
//        return (dataPointsStream, deviceBaseTable) -> dataPointsStream
//                .peek((key, dataPointsMessage) -> {
//                    if (log.isTraceEnabled()) {
//                        log.trace("processing message with key {}", key);
//                    }
//                })
//                .join(deviceBaseTable, messageMapper::map)
//                .filter((key, deviceChangedMessage) -> deviceChangedMessage.isPresent())
//                .mapValues((key, deviceChangedMessage) -> deviceChangedMessage.get());
//    }
}
