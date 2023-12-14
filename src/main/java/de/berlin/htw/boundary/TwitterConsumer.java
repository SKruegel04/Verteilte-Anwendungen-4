package de.berlin.htw.boundary;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Singleton;

import de.berlin.htw.boundary.dto.Tweet;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Singleton
public class TwitterConsumer {

    BlockingQueue<Tweet> queue = new LinkedBlockingQueue<>(50);

    public void onEvent(@Observes final Tweet tweet) {
        try {
            queue.put(tweet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void onAsyncEvent(@ObservesAsync final Tweet tweet) {
        try {
            queue.put(tweet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public String get() throws InterruptedException {
        return queue.take().getMessage();
    }
}
