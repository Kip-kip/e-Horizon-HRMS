package stlhorizon.org.hrmselfservice;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.core.content.ContextCompat;

/**
 * Utility class for view logic
 */
public class ViewUtils {

    /**
     * Retrieves pool of colors for calendars and events
     * @param context    resources provider
     * @return  array of {@link android.support.annotation.ColorInt} integers
     */
    public static int[] getCalendarColors(Context context) {
        int transparentColor = ContextCompat.getColor(context, android.R.color.transparent);
        TypedArray ta = context.getResources().obtainTypedArray(R.array.tab_colors);//todo change this to calendar_colors
        int[] colors;
        if (ta.length() > 0) {
            colors = new int[ta.length()];
            for (int i = 0; i < ta.length(); i++) {
                colors[i] = ta.getColor(i, transparentColor);
            }
        } else {
            colors = new int[]{transparentColor};
        }
        ta.recycle();
        return colors;
    }

}
