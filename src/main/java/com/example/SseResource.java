package com.example;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Path("events")
public class SseResource {
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput getServerSentEvents() {
        final EventOutput eventOutput = new EventOutput();
        new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    final OutboundEvent.Builder eventBuilder
                            = new OutboundEvent.Builder();
                    eventBuilder.name("message-to-client");
                    eventBuilder.data(String.class,
                            "Hello world " + i + "!");
                    final OutboundEvent event = eventBuilder.build();
                    eventOutput.write(event);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(
                        "Error when writing the event.", e);
            } finally {
                try {
                    eventOutput.close();
                } catch (IOException ioClose) {
                    throw new RuntimeException(
                            "Error when closing the event output.", ioClose);
                }
            }
        }).start();
        return eventOutput;
    }
}
