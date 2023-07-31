package com.example.employer.streams;

import com.bytes.springcloudproducts.model.Product;
import com.example.employer.model.ProductEmp;
import com.example.employer.service.mappers.ProductMapper;
import com.example.employer.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class KafkaStreamsHandler {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Bean
    public Consumer<KStream<String, Product>> mergeEmployersWithProducts() {
        return kstream -> kstream
                .map((key, value) -> new KeyValue<>(new ProductEmp(), value))
                .mapValues((s, product) -> productMapper.map(product))
                .peek((key, product) -> {
                    log.debug("Saving product: {}", product);
                    productRepository.save(product);
                });
    }
}
