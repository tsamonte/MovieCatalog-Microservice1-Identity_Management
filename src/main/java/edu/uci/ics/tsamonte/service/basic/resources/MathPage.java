package edu.uci.ics.tsamonte.service.basic.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.tsamonte.service.basic.logger.ServiceLogger;
import edu.uci.ics.tsamonte.service.basic.models.MathRequestModel;
import edu.uci.ics.tsamonte.service.basic.models.MathResponseModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("math")
public class MathPage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response equationResponse(@Context HttpHeaders headers, String jsonText) {
        MathRequestModel requestModel;
        MathResponseModel responseModel;
        ObjectMapper mapper = new ObjectMapper();

        // validate model and map JSON to POJO
        try {
            requestModel = mapper.readValue(jsonText, MathRequestModel.class);
        }
        catch (IOException e) {
            e.printStackTrace();

            // resultCode = -3 JSON parse exception, 400 Bad Request
            if(e instanceof JsonParseException) {
                responseModel = new MathResponseModel(-3, "JSON parse exception.", null);
                ServiceLogger.LOGGER.warning("Unable to map JSON to POJO (JSON Parse Exception)");
                return Response.status(Response.Status.BAD_REQUEST).entity(responseModel).build();
            }
            // resultCode = -2 JSON mapping exception, 400 Bad Request
            else if (e instanceof JsonMappingException) {
                responseModel = new MathResponseModel(-2, "JSON mapping exception.", null);
                ServiceLogger.LOGGER.warning("Unable to map JSON to POJO (JSON Mapping Exception)");
                return Response.status(Response.Status.BAD_REQUEST).entity(responseModel).build();
            }
            // resultCode = -1 Internal server error, 500 Internal Server Error
            else {
                responseModel = new MathResponseModel(-1, "Internal server error.", null);
                ServiceLogger.LOGGER.severe("Internal Server Error");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseModel).build();
            }
        }

        // resultCode = 21 Data contains invalid integers, 200 OK
        // The following are all valid integers:
        //      x > 0 && x < 100
        //      y > 0 && y < 100
        //      z >= -10 && z <= 10
        int x = requestModel.getNum_x();
        int y = requestModel.getNum_y();
        int z = requestModel.getNum_z();
        if((x <= 0 || x >=100) ||
                (y <= 0 || y >= 100) ||
                (z < -10 || z > 10)) {
            responseModel = new MathResponseModel(21, "Data contains invalid integers.", null);
            ServiceLogger.LOGGER.warning("Data contains invalid integers");
            return Response.status(Response.Status.OK).entity(responseModel).build();
        }

        // resultCode = 20 Calculation successful, 200 OK
        ServiceLogger.LOGGER.info("Received math request");
        ServiceLogger.LOGGER.info("Request:\n" + jsonText);

        Integer value = x * y + z;
        ServiceLogger.LOGGER.info("Calculated equation");

        responseModel = new MathResponseModel(20, "Calculation successful.", value);
        return Response.status(Response.Status.OK).entity(responseModel).build();
    }



}
