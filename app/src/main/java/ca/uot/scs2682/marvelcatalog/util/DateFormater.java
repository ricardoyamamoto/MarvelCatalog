package ca.uot.scs2682.marvelcatalog.util;

/**
 * Created by ricardohidekiyamamoto on 2017-03-27.
 */

public class DateFormater {

    public static String formatDate(String dateString){
        if (dateString != null){
            String year = dateString.substring(0,4);
            String month = dateString.substring(5,7);
            String day = dateString.substring(8,10);
            return dateString.substring(5,7) + "/"  + dateString.substring(8,10) + "/" + dateString.substring(0,4);
        } else {
            return null;
        }

    }

}
