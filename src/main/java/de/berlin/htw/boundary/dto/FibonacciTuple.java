package de.berlin.htw.boundary.dto;

import io.smallrye.reactive.messaging.kafka.Record;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
public class FibonacciTuple {

    private Integer last;
    
    private Integer current;

    public Integer getKey() {
        return getCurrent();
    }

    public Integer getLast() {
        return last;
    }

    public void setLast(Integer last) {
        this.last = last;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Record<Integer, String> toRecord() {
        return Record.of(getKey(), getCurrent().toString());
    }

    public static FibonacciTuple fromRecord(final Record<Integer, String> record) {
        FibonacciTuple tuple = new FibonacciTuple();
        tuple.setLast(record.key());
        tuple.setCurrent(Integer.parseInt(record.value()));
        return tuple;
    }
}
