package de.berlin.htw.boundary;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import de.berlin.htw.boundary.dto.Tweet;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Singleton
public class TwitterProducer {

    @Inject
    private Event<Tweet> twitterChannel;

    public void sendMessage(final String message) {
        final Tweet tweet = new Tweet();
        tweet.setMessage(message);
        sendTweet(tweet);
    }
    
    public void sendTweet(final Tweet tweet) {
        twitterChannel.fire(tweet);
    }
    
    public void sendTweetAsync(final Tweet tweet) {
        twitterChannel.fireAsync(tweet);
    }
    
}
