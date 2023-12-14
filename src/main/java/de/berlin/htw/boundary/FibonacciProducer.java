package de.berlin.htw.boundary;

import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.smallrye.reactive.messaging.kafka.Record;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
public class FibonacciProducer {

    @Inject
    @Channel("fibonacci-producer")
    Emitter<Record<Integer, String>> producer;
    
    public void produce(Record<Integer, String> record) {
        producer.send(record);
    }
    
}
