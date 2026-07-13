package oracle.communications.inventory.rest.api.impl_helperClass;

import java.util.List;

import oracle.communications.inventory.api.entity.PropertyLocation;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.location.LocationManager;
import oracle.communications.inventory.api.location.LocationSearchCriteria;
import oracle.communications.platform.persistence.PersistenceHelper;

public class FindNearestPop {
	public static PropertyLocation findNearestPop(double userLatitude,
            double userLongitude)
throws ValidationException {

System.out.println("====================================================");
System.out.println("Nearest POP Finder Started");
System.out.println("User Latitude  : " + userLatitude);
System.out.println("User Longitude : " + userLongitude);
System.out.println("====================================================");

LocationManager locationManager = PersistenceHelper.makeLocationManager();

LocationSearchCriteria searchCriteria =
locationManager.makePropertyLocationSearchCriteria();

System.out.println("Fetching all Property Locations from UIM...");

List<PropertyLocation> propertyLocations =
locationManager.findPropertyLocations(searchCriteria);

if (propertyLocations == null || propertyLocations.isEmpty()) {
System.out.println("No Property Locations found in UIM.");
return null;
}

System.out.println("Total Property Locations Found : "
+ propertyLocations.size());

PropertyLocation nearestPop = null;
double minimumDistance = Double.MAX_VALUE;

int count = 1;

for (PropertyLocation pop : propertyLocations) {

System.out.println();
System.out.println("----------------------------------------------");
System.out.println("Checking Property Location : " + count++);
System.out.println("----------------------------------------------");

System.out.println("Name                 : " + pop.getName());
System.out.println("Network Location Code: " + pop.getNetworkLocationCode());

if (pop.getLatitude() == null ||
pop.getLongitude() == null ||
pop.getLatitude().trim().isEmpty() ||
pop.getLongitude().trim().isEmpty()) {

System.out.println("Latitude/Longitude not available. Skipping...");
continue;
}

double popLatitude = Double.parseDouble(pop.getLatitude());
double popLongitude = Double.parseDouble(pop.getLongitude());

System.out.println("POP Latitude  : " + popLatitude);
System.out.println("POP Longitude : " + popLongitude);

double distance = Distance.distance(
userLatitude,
userLongitude,
popLatitude,
popLongitude,
"K");

System.out.println("Calculated Distance : "
+ String.format("%.3f", distance)
+ " KM");

if (distance < minimumDistance) {

System.out.println("Nearest POP updated.");

minimumDistance = distance;
nearestPop = pop;

System.out.println("Current Minimum Distance : "
+ String.format("%.3f", minimumDistance)
+ " KM");

System.out.println("Current Nearest POP : "
+ nearestPop.getName());

System.out.println("Network Location Code : "
+ nearestPop.getNetworkLocationCode());
}
}

System.out.println();
System.out.println("====================================================");

if (nearestPop != null) {

System.out.println("Nearest POP Found Successfully");

System.out.println("POP Name              : "
+ nearestPop.getName());

System.out.println("Network Location Code : "
+ nearestPop.getNetworkLocationCode());

System.out.println("Latitude              : "
+ nearestPop.getLatitude());

System.out.println("Longitude             : "
+ nearestPop.getLongitude());

System.out.println("Minimum Distance      : "
+ String.format("%.3f", minimumDistance)
+ " KM");
} else {

System.out.println("No nearest POP found.");
}

System.out.println("Nearest POP Finder Completed");
System.out.println("====================================================");

return nearestPop;
}
	
}
