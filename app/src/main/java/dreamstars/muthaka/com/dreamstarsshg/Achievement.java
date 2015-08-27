package dreamstars.muthaka.com.dreamstarsshg;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Peter Muthaka on 8/23/2015.
 */
public class Achievement extends ListActivity {


    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> achievementList;


    // url to get all products list
    private static String url_all_achievements = "http://petsamod.site40.net/dreamstartsSHG/Users/get_all_achievement.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CONTRIBUTIONS = "achievement";
    private static final String TAG_EID = "eid";
    private static final String TAG_NAME = "name";
    private static final String TAG_DATE = "date";


    // contribution JSONArray
    JSONArray achievement = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement);


        // Hashmap for ListView
        achievementList = new ArrayList<HashMap<String, String>>();

        // Loading contribution in Background Thread
        new LoadAllAchievements().execute();
    }


    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllAchievements extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Achievement.this);
            pDialog.setMessage("Loading Achievements. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All contribution from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_achievements, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Achievements: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // contribution found
                    // Getting Array of contribution
                    achievement = json.getJSONArray(TAG_CONTRIBUTIONS);

                    // looping through All Products
                    for (int i = 0; i < achievement.length(); i++) {
                        JSONObject c = achievement.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_EID);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_EID, id);
                        map.put(TAG_NAME, name);

                        // adding HashList to ArrayList
                        achievementList.add(map);
                    }
                } else {
                    // no contribution found
                    // Launch Start Activity
                    Intent i = new Intent(getApplicationContext(),
                            Start.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all contribution
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            Achievement.this, achievementList,
                            R.layout.list_achievement, new String[] { TAG_EID,
                            TAG_NAME},
                            new int[] { R.id.eid, R.id.name });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}
