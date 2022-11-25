package de.berlin.htw.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.berlin.htw.control.TweetController;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@Path("/tweets")
public class TwitterResource {

	@ConfigProperty(name = "htw.tweet.history-size", defaultValue="10")
	Integer historySize;

	@Inject
	TweetController controller;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTweets() {
    	List<String> tweets = new ArrayList<>();
    	for (int i = 0; i < historySize; i++) {
    		tweets.add(controller.getTweet());
        }
        return tweets;
    }

}