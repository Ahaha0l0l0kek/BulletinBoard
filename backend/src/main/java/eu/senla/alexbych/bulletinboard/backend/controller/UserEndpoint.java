package eu.senla.alexbych.bulletinboard.backend.controller;

import eu.senla.alexbych.bulletinboard.backend.service.UserService;
import eu.senla.alexbych.bulletinboard.backend.ws.UserSOADetailsRequest;
import eu.senla.alexbych.bulletinboard.backend.ws.UserSOADetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint {

    @Autowired
    private UserService userService;

    @PayloadRoot(namespace = "http://senla.eu/alexbych/bulletinboard/backend/ws",
            localPart = "UserSOADetailsRequest")
    @ResponsePayload
    public UserSOADetailsResponse getUserRequest(@RequestPayload UserSOADetailsRequest request) {
        UserSOADetailsResponse response = new UserSOADetailsResponse();
        response.setUser(userService.getUserSOA(request.getLogin()));
        return response;
    }
}
