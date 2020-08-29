//package stlhorizon.org.hrmselfservice.utils;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.SharedPreferences;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.Drawable;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Build;
//import android.text.TextUtils;
//import android.view.ContextThemeWrapper;
//import android.widget.ImageView;
//
//import androidx.core.graphics.drawable.DrawableCompat;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.bumptech.glide.Glide;
//
//import java.util.Date;
//import java.util.Random;
//import java.util.TimeZone;
//import java.util.concurrent.TimeUnit;
//
//import stlhorizon.org.hrmselfservice.CalendarUtils;
//import stlhorizon.org.hrmselfservice.R;
//
//import static stlhorizon.org.hrmselfservice.utils.StringConstants.AUTH_TOKEN;
//
///**
// * Created by makari on 11/1/2017.
// */
//
//public class Utils {
//    private static final String PREFERENCES_FILE = "materialsample_settings";
//
//
//    public static int getToolbarHeight(Context context) {
//        int height = (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
//        return height;
//    }
//
////    public static int getStatusBarHeight(Context context) {
////        int height = (int) context.getResources().getDimension(R.dimen.statusbar_size);
////        return height;
////    }
//
//
//    public static Drawable tintMyDrawable(Drawable drawable, int color) {
//        drawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setTint(drawable, color);
//        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
//        return drawable;
//    }
//
//
//    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
//        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
//        return sharedPref.getString(settingName, defaultValue);
//    }
//
//    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
//        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(settingName, settingValue);
//        editor.apply();
//    }
//
//    /**
//     * Check if build is later than Lollipop
//     * @return true or false
//     */
//    public static boolean laterThanLollipop(){
//        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);
//    }
//
//    /**
//     * Default retry policy for requests.
//     * @return the policy for making requests
//     */
//    public static DefaultRetryPolicy getRetryPolicy(){
//        return new DefaultRetryPolicy(
//                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//    }
//
//    /**
//     * Capitalize a give string
//     * @param str the string to be capitalized
//     * @return capitalized string
//     */
//    public static String capitalizeString(String str) {
//        if (TextUtils.isEmpty(str)) {
//            return str;
//        }
//        char[] arr = str.toCharArray();
//        boolean capitalizeNext = true;
//
//        StringBuilder phrase = new StringBuilder();
//        for (char c : arr) {
//            if (capitalizeNext && Character.isLetter(c)) {
//                phrase.append(Character.toUpperCase(c));
//                capitalizeNext = false;
//                continue;
//            } else if (Character.isWhitespace(c)) {
//                capitalizeNext = true;
//            }
//            phrase.append(c);
//        }
//
//        return phrase.toString();
//    }
//
//    /**
//     * Check if there is internet
//     * @param context the context
//     * @return true if connected, else false
//     */
//    private boolean checkInternetConenction(Context context) {
//        // get Connectivity Manager object to check connection
//        ConnectivityManager connect =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        // Check for network connections
//        if(connect != null) {
//            if (connect.getNetworkInfo(0).getState() ==
//                    android.net.NetworkInfo.State.CONNECTED ||
//                    connect.getNetworkInfo(0).getState() ==
//                            android.net.NetworkInfo.State.CONNECTING ||
//                    connect.getNetworkInfo(1).getState() ==
//                            android.net.NetworkInfo.State.CONNECTING ||
//                    connect.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Show a general alert dialog
//     * @param context the context
//     * @param title the title of the message
//     * @param msg the message
//     */
////    public static void showGeneralDialog(Context context, String title, String msg){
////        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MyCheckBox));
////        builder.setTitle(title)
////                .setMessage(msg)
////                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int id) {
////                    }
////                })
////                .show();
////    }
//
//    /**
//     * Load user avatar
//     * @param context the context
//     * @param imageView the view to be show the avatar
//     */
////    public static void loadAvatar(Context context, ImageView imageView){
////        String token_value = readAuthToken(context, AUTH_TOKEN, "null");
////        if(token_value.equals("null"))
////            return;
////        String thumbnail_ulr = USER_AVATAR_API + TOKEN_KEY + token_value;
////        Glide.with(context)
////                .load(thumbnail_ulr)
////                .asBitmap()
////                .placeholder(R.drawable.ic_profile)
////                .into(imageView);
////    }
//
//    /**
//     * Fetch random color from the color array resources
//     * @return an integer value of the fetched color
//     */
//    public static int randomColor(Context context){
//        int[] allColors = context.getResources().getIntArray(R.array.category);
//        return allColors[new Random().nextInt(allColors.length)];
//    }
//
//    /**
//     * Gets the timezone
//     * @return local system timezone ID, or UTC if event is all day
//     */
//    public static String getTimeZone(boolean isAllDay) {
//        return isAllDay ? CalendarUtils.TIMEZONE_UTC : TimeZone.getDefault().getID();
//    }
//
//    /**
//     * Get tasks duration
//     * @param start_date task's start date
//     * @param end_date task's end date
//     * @return formatted task duration
//     */
//    public static String getDuration(long start_date, long end_date){
//        Date startDate = new Date(start_date);
//        Date endDate = new Date(end_date);
//
//        long duration  = endDate.getTime() - startDate.getTime();
//        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
//        long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
//        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
//
//        if(diffInMinutes<60){
//            return "" + (diffInMinutes==1 ? diffInMinutes + "min" : diffInMinutes + "mins");
//        } if(diffInHours<24){
//            long mins = diffInMinutes%60;
//            return "" + (diffInHours==1 ? diffInHours + "hr " : diffInHours + "hrs ")
//                    + (mins==0 ? "" : (mins==1 ? mins + "min" : mins + "mins"));
//        }
//        long hrs = diffInHours%24;
//        return "" + (diffInDays==1 ? diffInDays + "day " : diffInDays + "days ")
//                + (hrs==0 ? "" : (hrs==1 ? hrs + "hr" : hrs + "hrs"));
//    }
//
//    /**
//     * Check if there is any connectivity
//     *
//     * @param context the context
//     * @return is Device Connected
//     */
//    public static boolean isConnected(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (null != cm) {
//            NetworkInfo info = cm.getActiveNetworkInfo();
//            return (info != null && info.isConnected());
//        }
//        return false;
//    }
//}
