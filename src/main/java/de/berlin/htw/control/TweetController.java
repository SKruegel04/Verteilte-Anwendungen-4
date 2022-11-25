package de.berlin.htw.control;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@Dependent
public class TweetController {
	
	@Inject
	Logger logger;

	@Inject
	TweetGenerator generator;

    public String getTweet() {
        final String tweet = generator.generateTweet();
        logger.info("new tweet: " + tweet);
        return tweet;
    }
}
