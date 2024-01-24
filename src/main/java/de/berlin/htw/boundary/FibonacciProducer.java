package de.berlin.htw.boundary;

import de.berlin.htw.boundary.dto.FibonacciTuple;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.smallrye.reactive.messaging.kafka.Record;

import org.jboss.logging.Logger;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
public class FibonacciProducer {

    @Inject
    Logger logger;

    @Inject
    @Channel("fibonacci-producer")
    Emitter<Record<Integer, String>> producer;

    public void produce(Record<Integer, String> record) {
        logger.info("Send to fibonacci topic: " + record.value());
        producer.send(record);
    }

    @PostConstruct
    public void generate() {
        FibonacciTuple tuple = new FibonacciTuple();
        tuple.setLast(1);
        tuple.setCurrent(1);
        produce(tuple.toRecord());
    }
}
