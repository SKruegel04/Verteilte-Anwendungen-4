package de.berlin.htw.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Singleton;

import de.berlin.htw.boundary.dto.Tweet;
import org.eclipse.microprofile.reactive.messaging.Incoming;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Singleton
public class TwitterConsumer {

    BlockingQueue<String> queue = new LinkedBlockingQueue<>(50);
    List<String> lastTweets = new ArrayList<>(10);

    @Incoming("twitter-consumer")
    public void consume(String tweet) {
        try {
            queue.put(tweet);
            if (lastTweets.size() > 10) {
                lastTweets.remove(0);
            }
            lastTweets.add(tweet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLastTweets() {
        return lastTweets;
    }
    
    public String get() throws InterruptedException {
        return queue.take();
    }
}
