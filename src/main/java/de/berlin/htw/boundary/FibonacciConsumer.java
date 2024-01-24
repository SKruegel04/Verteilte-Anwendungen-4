package de.berlin.htw.boundary;

import de.berlin.htw.boundary.dto.FibonacciTuple;
import de.berlin.htw.control.FibonacciController;
import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import io.smallrye.reactive.messaging.kafka.Record;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
public class FibonacciConsumer {

    @Inject
    Logger logger;

    @Inject
    FibonacciProducer producer;

    @Inject
    FibonacciController controller;

    private final Map<Integer, Integer> sequence = new ConcurrentHashMap<>();

    @Incoming("fibonacci-consumer")
    public void consume(Record<Integer, String> record) {
        logger.info("Received from fibonacci topic: " + record.value());
        FibonacciTuple tuple = FibonacciTuple.fromRecord(record);
        sequence.put(tuple.getKey(), tuple.getCurrent());

        FibonacciTuple nextTuple = controller.calculateNext(tuple);
        producer.produce(nextTuple.toRecord());
    }

    public List<Integer> getLastValues(Integer count) {
        return sequence.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByKey().reversed())
                .limit(count)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
