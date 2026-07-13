package oracle.communications.inventory.rest.api.impl_helperClass;

public class Distance {
	 public static double distance(double lat1,
             double lon1,
             double lat2,
             double lon2,
             String unit) {
System.out.println("Executing nearest distance find method from customer");

// Same location
if (lat1 == lat2 && lon1 == lon2) {
System.out.println("POP location and customer lcation is same");
return 0;
}

// Difference in longitude
double theta = lon1 - lon2;

// Calculate central angle using Spherical Law of Cosines
System.out.println("Calculating central angle using Spherical Law of Cosines");
double dist =
Math.sin(Math.toRadians(lat1))
* Math.sin(Math.toRadians(lat2))
+ Math.cos(Math.toRadians(lat1))
* Math.cos(Math.toRadians(lat2))
* Math.cos(Math.toRadians(theta));

// Convert cosine value to angle
System.out.println("Converting cosine value to angle");
dist = Math.acos(dist);

// Convert radians to degrees
System.out.println("Converting radians to degrees");
dist = Math.toDegrees(dist);

// Convert degrees to miles
dist = dist * 60 * 1.1515;

// Convert miles into requested unit
if ("K".equalsIgnoreCase(unit)) {
dist = dist * 1.609344;
} else if ("N".equalsIgnoreCase(unit)) {
dist = dist * 0.8684;
}

return dist;
}
}
