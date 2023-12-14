package de.berlin.htw.boundary;

import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import io.smallrye.reactive.messaging.kafka.Record;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
public class FibonacciConsumer {

    @Inject
    Logger logger;
    
    @Incoming("fibonacci-consumer")
    public void consume(Record<Integer, String> record) {
        logger.info("Received from fibonacci topic: " + record.value());
    }
    
}
