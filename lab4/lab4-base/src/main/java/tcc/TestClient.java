package tcc;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tcc.flight.FlightReservationDoc;
import tcc.hotel.HotelReservationDoc;

/**
 * Simple non-transactional client. Can be used to populate the booking services
 * with some requests.
 */
public class TestClient {
	public static void main(String[] args) {
		try {
			boolean hotelBookingSuccessful = true, flightBookingSuccessful = true;

			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(TestServer.BASE_URI);

			GregorianCalendar tomorrow = new GregorianCalendar();
			tomorrow.setTime(new Date());
			tomorrow.add(GregorianCalendar.DAY_OF_YEAR, 1);

			// book flight

			WebTarget webTargetFlight = target.path("flight");

			FlightReservationDoc docFlight = new FlightReservationDoc();
			docFlight.setName("Christian");
			docFlight.setFrom("Karlsruhe");
			docFlight.setTo("Berlin");
			docFlight.setAirline("airberlin");
			docFlight.setDate(tomorrow.getTimeInMillis());

			Response responseFlight = webTargetFlight.request().accept(MediaType.APPLICATION_XML)
					.post(Entity.xml(docFlight));

			if (responseFlight.getStatus() != 200) {
				flightBookingSuccessful = false;
				System.out.println("Failed : HTTP error code : " + responseFlight.getStatus());
			}

			FlightReservationDoc outputFlight = responseFlight.readEntity(FlightReservationDoc.class);
			System.out.println("Output from Server: " + outputFlight);

			// book hotel

			WebTarget webTargetHotel = target.path("hotel");

			HotelReservationDoc docHotel = new HotelReservationDoc();
			docHotel.setName("Christian");
			docHotel.setHotel("Interconti");
			docHotel.setDate(tomorrow.getTimeInMillis());

			Response responseHotel = webTargetHotel.request().accept(MediaType.APPLICATION_XML)
					.post(Entity.xml(docHotel));

			if (responseHotel.getStatus() != 200) {
				hotelBookingSuccessful = false;
				System.out.println("Failed : HTTP error code : " + responseHotel.getStatus());
			}

			HotelReservationDoc outputHotel = responseHotel.readEntity(HotelReservationDoc.class);
			System.out.println("Output from Server: " + outputHotel);

			if (hotelBookingSuccessful && flightBookingSuccessful) {
				target = client.target(outputHotel.getUrl());
				responseHotel = target.request().put(Entity.text(""));

				if (responseHotel.getStatus() != 200) {
					hotelBookingSuccessful = false;
					System.out.println("Failed : HTTP error code : " + responseHotel.getStatus());
				} else {
					System.out.println("Hotel booking successful!");
				}
				target = client.target(outputFlight.getUrl());
				if (hotelBookingSuccessful) {
					responseFlight = target.request().put(Entity.text(""));

					if (responseFlight.getStatus() != 200) {
						System.out.println("Failed : HTTP error code : " + responseFlight.getStatus());
					} else {
						System.out.println("Flight booking successful!");
					}
				} else {
					target.request().delete();
				}
			} else {
				System.out.println("Rollback!");
				if (hotelBookingSuccessful) {
					target = client.target(outputHotel.getUrl());
					responseHotel = target.request().delete();
					System.out.println("Hotel: " + responseHotel.readEntity(String.class));
				}
				if (flightBookingSuccessful) {
					target = client.target(outputFlight.getUrl());
					responseFlight = target.request().delete();
					System.out.println("Flight: " + responseFlight.readEntity(String.class));
				}
			}
			System.out.println("Transaction complete!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
