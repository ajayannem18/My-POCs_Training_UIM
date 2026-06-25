package oracle.communications.inventory.rest.api;

import oracle.communications.inventory.rest.api.*;
import oracle.communications.inventory.rest.model.*;

import oracle.communications.inventory.rest.model.FindPortsResponseType;

import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

public interface FindAvailablePortsApiService {
      Response findAvailablePorts(String serviceLocationCode,String networkLocationCode,SecurityContext securityContext)
      ;
}
