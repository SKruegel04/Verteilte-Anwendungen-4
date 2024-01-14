package de.berlin.htw.boundary;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import de.berlin.htw.boundary.dto.Tweet;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Singleton
public class TwitterProducer {

    @Inject
    @Channel("twitter-producer")
    Emitter<String> producer;

    public void sendMessage(final String message) {
        producer.send(message);
    }
    
}
