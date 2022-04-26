package de.hska.iwi.vislab.lab2.example;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class FibonacciResource {

    static public int counter = 1;
    static public Map<Integer, FibonacciSeries> seriesMap = new HashMap<>();

    // The resource represents a fibonacci series. Its status is stored in a separate Object.
    // There are the following endpoints:
    // POST   /fibonacci -> id
    // POST   /fibonacci/{id}
    // PUT    /fibonacci/{id}
    // DELETE /fibonacci/{id}

    @POST
    @Path("fibonacci")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createFibonacciSeries() {
        try {
            int id = counter++;
            FibonacciSeries fibonacciSeries = new FibonacciSeries(id);
            seriesMap.put(id, fibonacciSeries);
            return Response.status(Response.Status.CREATED).entity(id).build();
        } catch (Exception ex) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("fibonacci/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getNextFibonacciNumber(@PathParam(value = "id") Integer id) {
        try {
            int result = seriesMap.get(id).getNext();
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Path("fibonacci/{id}")
    public Response resetFibonacciSeries(@PathParam(value = "id") Integer id) {
        try {
            seriesMap.put(id, new FibonacciSeries(id));
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @DELETE
    @Path("fibonacci/{id}")
    public Response deleteFibonacciSeries(@PathParam(value = "id") Integer id) {
        try {
            seriesMap.remove(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
