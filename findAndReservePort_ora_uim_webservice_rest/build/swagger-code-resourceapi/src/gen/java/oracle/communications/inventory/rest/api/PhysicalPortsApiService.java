package oracle.communications.inventory.rest.api;

import oracle.communications.inventory.rest.api.*;
import oracle.communications.inventory.rest.model.*;

import oracle.communications.inventory.rest.model.ReservePortRequestType;
import oracle.communications.inventory.rest.model.ReservePortResponseType;

import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

public interface PhysicalPortsApiService {
      Response reserveFeasibilityPort(ReservePortRequestType body,SecurityContext securityContext)
      ;
}
