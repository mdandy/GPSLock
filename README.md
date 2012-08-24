#GPS LOCK

GPS Lock is an amalgam of UNIX pthread library and Global Positioning System(GPS). This GPS Lock provides location-based synchronization in which it is 
used to block on a location-based condition variable. There are three main 
functionalities that a GPS Lock supports: gps_lock, gps_unlock, and 
gps_wait. Similar to the pthread library, GPS Lock requires an explicit lock 
and unlock. Hence, it is user's responsibility to prevent any 
synchronization problem such as deadlock and data race. Unlike the pthread 
library, GPS Lock has an implicit signal/broadcast mechanism. As soon as the 
device is within proximity of the specified location, all sleeping threads 
that are waiting on this lock will be awakened.
The GPS Lock takes three parameters: latitude, longitude, and radius. Both 
latitude and longitude are expressed in E6 format in order to be consistent 
with Google Maps API. This geo-position will determine where the location 
event will be raised. The proximity of the location is specified by the 
radius of which is measured in meters.

##Authors
* Michael Dandy: http://github.com/mdandy
* Donovan Hatch: http://www.donovanhatch.com
