package de.berlin.htw.boundary;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import io.smallrye.reactive.messaging.kafka.Record;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
public class TwitterProducer {

    @Inject
    @Channel("twitter")
    Emitter<Record<Integer, String>> producer;
    
    @Incoming("twitter")
    public void consume(Record<Integer, String> record) {
        // TODO
    }
    
}
