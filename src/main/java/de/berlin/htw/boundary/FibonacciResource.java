package de.berlin.htw.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.berlin.htw.boundary.dto.FibonacciTuple;
import de.berlin.htw.control.FibonacciController;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@Path("/fibonacci")
public class FibonacciResource {

    @ConfigProperty(name = "htw.fibonacci.sequence-size", defaultValue="10")
    Integer sequenceSize;

	@Inject
	FibonacciController controller;
	
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Integer> getFibonacciSequence(FibonacciTuple current) {
    	List<Integer> sequence = new ArrayList<>();
        sequence.add(current.getLast());
        sequence.add(current.getCurrent());
    	for (int i = 2; i < sequenceSize; i++) {
    	    current = controller.calculateNext(current);
    	    sequence.add(current.getCurrent());
        }
        return sequence;
    }

}