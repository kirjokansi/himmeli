package fi.kirjokansi.venuesearch;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sampo on 15.12.2014.
 */
public class TestData {

    final static int venueCount = 4;
    final static String date = "20141213";

    final static String venueName1     = "Ravintola YingYang";
    final static String venueAddress1  = "Laivurinkatu 11";
    final static String venueDistance1 = "321m";
    final static int venueDistanceMeters1 = 321;

    final static String venueName2     = "Ravintola Hai Long";
    final static String venueAddress2  = "N/A";
    final static String venueDistance2 = "9km";
    final static int venueDistanceMeters2 = 9567;

    final static String venueName3     = "Ravintola Portenos";
    final static String venueAddress3  = "N/A";
    final static String venueDistance3 = "10000km";
    final static int venueDistanceMeters3 = 10000987;

    final static String venueName4     = "Ravintola Dado";
    final static String venueAddress4  = "N/A";
    final static String venueDistance4 = "123m";
    final static int venueDistanceMeters4 = 123;

    final static String venueString1 = venueName1+", "+venueAddress1+", "+venueDistance1;
    final static String venueString2 = venueName2+", "+venueAddress2+", "+venueDistance2;
    final static String venueString3 = venueName3+", "+venueAddress3+", "+venueDistance3;
    final static String venueString4 = venueName4+", "+venueAddress4+", "+venueDistance4;

    final static List<String> venueList =
        Arrays.asList(venueString1, venueString2, venueString3, venueString4);

    final static String apiUrl =
        "https://api.foursquare.com/v2/venues/search?client_id=LP3WEJ03DJIKCHCG5XVQUC4U2RJOWZIANDR2EUBAT3LWQGLE&client_secret=0M2U3RKWALTMMUU3BV0O3XTAHXVVZH4BIZYI4XURPL30OFPI&v="+date+"&ll=123.4,432.1&query=r";

    final static public String jsonBroken =
        "{\n" +
            "    meta: {\n" +
            "        code: 200\n" +
            "    }\n" +
            "    response: {\n" +
            "        venues: [\n";

    final static public String jsonOk =
        "{\"meta\":{\"code\":200},\"response\":{"+
            "\"venues\":[" +
            "{" +
                "\"id\":\"4db7f7f76e81c67f61c2a819\","+
                "\"name\":\""+venueName1+"\"," +
                "\"location\":{" +
                    "\"address\":\""+venueAddress1+"\"," +
                    "\"lat\":64.68374982831138," +
                    "\"lng\":24.480371475219727," +
                    "\"distance\":"+venueDistanceMeters1 +
                "}" +
            "}," +
            "{" +
                "\"id\":\"4db7f7f76e81c67f61c2a819\","+
                "\"name\":\""+venueName2+"\"," +
                "\"location\":{" +
                    "\"address\":\""+venueAddress2+"\"," +
                    "\"lat\":64.68374982831138," +
                    "\"lng\":24.480371475219727," +
                    "\"distance\":"+venueDistanceMeters2 +
                "}" +
            "}," +
            "{" +
                "\"id\":\"4db7f7f76e81c67f61c2a819\","+
                "\"name\":\""+venueName3+"\"," +
                "\"location\":{" +
                    "\"address\":\""+venueAddress3+"\"," +
                    "\"lat\":64.68374982831138," +
                    "\"lng\":24.480371475219727," +
                    "\"distance\":"+venueDistanceMeters3 +
                "}" +
            "}," +
            "{" +
                "\"id\":\"4db7f7f76e81c67f61c2a819\","+
                "\"name\":\""+venueName4+"\"," +
                "\"location\":{" +
                    "\"address\":\""+venueAddress4+"\"," +
                    "\"lat\":64.68374982831138," +
                    "\"lng\":24.480371475219727," +
                    "\"distance\":"+venueDistanceMeters4 +
                "}" +
            "}" +
        "]}}";

}
