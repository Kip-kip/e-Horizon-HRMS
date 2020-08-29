package stlhorizon.org.hrmselfservice.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by makari on 3/20/2017.
 */

public class DateFormatter {
    public static SimpleDateFormat dateFormatter(String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat;
    }
}
