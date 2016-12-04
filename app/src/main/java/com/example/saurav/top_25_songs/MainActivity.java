package com.example.saurav.top_25_songs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button btnParse;
    private ListView ListApps;
    private String mFileContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnParse = (Button) findViewById(R.id.button);
        ListApps = (ListView) findViewById(R.id.xmlListView);
        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseApplication parseApplication = new ParseApplication(mFileContents);
                parseApplication.process();
                ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(MainActivity.this,R.layout.list_view,parseApplication.getApplications());
                ListApps.setAdapter(arrayAdapter);
            }
        });
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class DownloadData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXMLFile(params[0]);
            if (mFileContents == null){
                Log.d("Download Data", "Error Downloading");
            }
            return mFileContents;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("Download Data", "Result is "+result);

        }

        private String downloadXMLFile(String urlPath){
            StringBuilder builder =  new StringBuilder();
            try {
                URL url =  new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                int response = connection.getResponseCode();
                Log.d("Download Data", "Response Code is "+response);
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                int charRead;
                char[] inputBuffer = new char[500];
                while (true){
                    charRead = reader.read(inputBuffer);
                    if (charRead <= 0) {
                        break;
                    }
                    builder.append(String.copyValueOf(inputBuffer,0,charRead));
                }
                return builder.toString();
            }catch (IOException e){
                Log.d("Download Data", "Error in IO exception"+e.getMessage());
            }catch (SecurityException s){
                Log.d("Download Data", "Security exception"+s.getMessage());
            }
            return null;
        }
    }
}
