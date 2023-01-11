package de.berlin.htw.control;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.berlin.htw.boundary.TwitterConsumer;
import io.quarkus.scheduler.Scheduled;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@Dependent
public class TweetController {
	
	@Inject
	Logger logger;

	@Inject
	TweetGenerator generator;
	
	@Inject
	TwitterConsumer toBeChangedProducer;
	
    public String getTweet() {
        final String tweet = generator.generateTweet();
        logger.info("new tweet: " + tweet);
        return tweet;
    }
    
    @Scheduled(every="10s")     
    void produceTweet() {
        final String tweet = this.getTweet();
        // TODO: send the tweet to kafka
        toBeChangedProducer.add(tweet);
    }

}