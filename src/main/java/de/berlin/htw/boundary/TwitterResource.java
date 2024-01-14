package de.berlin.htw.boundary;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.berlin.htw.control.TweetController;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Path("/tweets")
public class TwitterResource {

    @ConfigProperty(name = "htw.tweet.history-size", defaultValue = "10")
    Integer historySize;

    @Context
    Sse sse;

    @Inject
    TweetController controller;

    @Inject
    TwitterConsumer consumer;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTweets() {
        return consumer.getLastTweets();
    }

    @GET
    @Path("sse")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void getLiveTweets(@Context SseEventSink sseEventSink) {

        try (SseEventSink sink = sseEventSink) {
            int eventId = 1;
            while (true) {
                String tweet = consumer.get();

                if (tweet != null) {
                    OutboundSseEvent sseEvent = sse.newEventBuilder()
                            .name("myTweets")
                            .id(String.valueOf(eventId))
                            .mediaType(MediaType.APPLICATION_JSON_TYPE)
                            .data(String.class, tweet)
                            .reconnectDelay(3000)
                            .build();
                    sseEventSink.send(sseEvent);
                    eventId++;
                }
            }
        } catch (InterruptedException e) {
            throw new InternalServerErrorException("Excption while waitinmg for new element", e);
        }
    }

}