package de.berlin.htw.boundary;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.inject.Singleton;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@Singleton
public class TwitterConsumer {

    BlockingQueue<String> queue = new LinkedBlockingQueue<>(50);

    public void add(final String tweet) {
        try {
            queue.put(tweet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public String get() throws InterruptedException {
        return queue.take();
    }
}
