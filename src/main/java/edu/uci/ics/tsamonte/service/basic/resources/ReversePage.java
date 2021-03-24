package edu.uci.ics.tsamonte.service.basic.resources;

import edu.uci.ics.tsamonte.service.basic.logger.ServiceLogger;
import edu.uci.ics.tsamonte.service.basic.models.ReverseResponseModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("reverse")
public class ReversePage {
    @Path("{string}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response reverseString(@Context HttpHeaders headers, @PathParam("string") String message) {

        ReverseResponseModel responseModel;

        try {
            // if the message contains invalid characters, produce a JSON with resultCode = 12, and HTTP statue 200 OK
            if (!isValidString(message)) {
                responseModel = new ReverseResponseModel(12, "String contains invalid characters.", null);
                ServiceLogger.LOGGER.warning("String contains invalid characters.");
                return Response.status(Response.Status.OK).entity(responseModel).build();
            }


            //
            ServiceLogger.LOGGER.info("Received reverse request (get)");
            ServiceLogger.LOGGER.info("Request: " + message);

            String reversedMessage = reverseString(message);
            ServiceLogger.LOGGER.info("Reversed string");

            responseModel = new ReverseResponseModel(10, "String successfully reversed.", reversedMessage);
            return Response.status(Response.Status.OK).entity(responseModel).build();
        }

        // If there is an internal server error, resultCode = -1, HTTP status = 500 Internal Server Error
        catch(Exception e) {
            e.printStackTrace();
            responseModel = new ReverseResponseModel(-1, "Internal server error.", null);
            ServiceLogger.LOGGER.severe("Internal Server Error");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseModel).build();
        }
    }

    // if String is empty, produce a JSON with resultCode = 11 and HTTP status 200 OK
    // must make a different response procedure bc PathParam("string") is missing
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response EmptyString(@Context HttpHeaders headers) {

        ReverseResponseModel responseModel;

        try {
            responseModel = new ReverseResponseModel(11, "String is empty.", null);
            ServiceLogger.LOGGER.warning("String is empty.");
            return Response.status(Response.Status.OK).entity(responseModel).build();
        }

        // If there is an internal server error, resultCode = -1, HTTP status = 500 Internal Server Error
        catch(Exception e) {
            e.printStackTrace();
            responseModel = new ReverseResponseModel(-1, "Internal server error.", null);
            ServiceLogger.LOGGER.severe("Internal Server Error");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseModel).build();
        }
    }

    // Helper functions that takes in a string and returns it in reverse
    private String reverseString(String message) {
        String reversedString = "";
        for(int i = message.length()-1; i >= 0; i--) {
            reversedString += message.charAt(i);
        }
        return reversedString;
    }

    private boolean isValidString(String message) {
        // TODO: Ask on piazza: does it just mean space (spacebar) or are other whitespaces allowed (i.e. tab)?
        String regex = "^[a-zA-Z0-9_ ]+$";
        if(message.matches(regex)) return true;
        else return false;
    }
}
