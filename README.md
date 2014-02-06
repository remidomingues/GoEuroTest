GoEuroTest
==========
Presentation
------------
The application allows a user to ask for geolocalized information about a
position located in Europe.
To do that, the application will query the GoEuro API with a location
description in parameter, then parse the JSON stream returned and save the
locations retrieved in a CSV file.

Command line description
------------------------
Standalone application used as follows:
java -jar GoEuroTest.jar [-v] <position>

<position>:
	This field must be the name of a position located in Europe, like a city.
	The city name can be incomplete and can include spaces.
	If this field contains spaces, it must be surrounded by quotation marks.

'-v' option:
	This option to enable certificate validation.
	Please note you won't be able to access an API which has a self-signed or
	expired certificate if this option is set.

Default configuration does not validate the SSL certificate.

Requirements
------------
JavaSE-1.6 environment
JSON in Java library :
- See https://github.com/douglascrockford/JSON-java
- .jar download link : http://www.java2s.com/Code/Jar/j/Downloadjavajsonjar.htm
