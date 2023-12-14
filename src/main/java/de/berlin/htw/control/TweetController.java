package de.berlin.htw.control;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import org.jboss.logging.Logger;

import de.berlin.htw.boundary.TwitterProducer;
import io.quarkus.scheduler.Scheduled;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Dependent
public class TweetController {
	
	@Inject
	Logger logger;

	@Inject
	TweetGenerator generator;
	
	@Inject
	TwitterProducer producer;
	
    public String getTweet() {
        final String tweet = generator.generateTweet();
        logger.info("new tweet: " + tweet);
        return tweet;
    }
    
    @Scheduled(every="10s")     
    void produceTweet() {
        final String tweet = this.getTweet();
        // TODO: send the tweet to kafka
        producer.sendMessage(tweet);
    }

}