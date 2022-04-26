package de.hska.iwi.vislab.lab2.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FibonacciTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and
        // Main.startServer())
        // --
        // c.configuration().enable(new
        // org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testFibonacciResource() {
        String responseMsg = target.path("fibonacci")
                .request().accept(MediaType.TEXT_PLAIN).post(Entity.text(""),
                        String.class);
        int id = Integer.parseInt(responseMsg);

        int[] fibonacciSeries = {1, 2, 3, 5, 8, 13, 21};
        for (int expected : fibonacciSeries) {
            responseMsg = target.path("fibonacci/" + id).request().accept(MediaType.TEXT_PLAIN).post(Entity.text(""), String.class);
            int result = Integer.parseInt(responseMsg);
            assertEquals(expected, result);
        }

        target.path("fibonacci/" + id).request().accept(MediaType.TEXT_PLAIN).put(Entity.text(""), String.class);
        for (int expected : fibonacciSeries) {
            responseMsg = target.path("fibonacci/" + id).request().accept(MediaType.TEXT_PLAIN).post(Entity.text(""), String.class);
            int result = Integer.parseInt(responseMsg);
            assertEquals(expected, result);
        }

        target.path("fibonacci/" + id).request().accept(MediaType.TEXT_PLAIN).delete(String.class);
        Response response = target.path("fibonacci/" + id).request().accept(MediaType.TEXT_PLAIN).post(Entity.text(""));
        assertEquals(response.getStatus(), 404);
    }
}
