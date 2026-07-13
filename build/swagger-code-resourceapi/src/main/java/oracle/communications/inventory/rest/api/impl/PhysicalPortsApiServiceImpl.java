package oracle.communications.inventory.rest.api.impl;

import oracle.communications.inventory.rest.api.*;
import oracle.communications.inventory.rest.model.*;

import oracle.communications.inventory.rest.model.ReservePortRequestType;
import oracle.communications.inventory.rest.model.ReservePortResponseType;

import java.util.List;

import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@RequestScoped
public class PhysicalPortsApiServiceImpl implements PhysicalPortsApiService {
      public Response reserveFeasibilityPort(ReservePortRequestType body,SecurityContext securityContext)
       {
      // do some magic!
      return Response.ok().entity("magic!"/*new ApiResponseMessage(ApiResponseMessage.OK, "magic!")*/).build();
  }
}
