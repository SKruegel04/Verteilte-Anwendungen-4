package de.berlin.htw.boundary;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;

import de.berlin.htw.boundary.dto.Tweet;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
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
