package com.swapnilborkar.ustop25;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String mFileContents;
    private Button btnParse;
    private ListView listSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Top Albums");

       // btnParse=(Button)findViewById(R.id.btnParse);
        listSongs=(ListView)findViewById(R.id.listView);

        //btnParse.setOnClickListener(new View.OnClickListener() {
           //@Override
            //public void onClick(View v) {
                //ParseSongs parseSongs = new ParseSongs(mFileContents);
                //parseSongs.process();
                //ArrayAdapter<Song> arrayAdapter = new ArrayAdapter<Song>(MainActivity.this, R.layout.list_item, parseSongs.getSongs());
                //listSongs.setAdapter(arrayAdapter);
            //}
        //});

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topalbums/limit=25/xml");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ParseSongs parseSongs = new ParseSongs(mFileContents);
               parseSongs.process();
               ArrayAdapter<Song> arrayAdapter = new ArrayAdapter<Song>(MainActivity.this, R.layout.list_item, parseSongs.getSongs());
               listSongs.setAdapter(arrayAdapter);
          }
        });
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



    private class DownloadData extends AsyncTask<String, Void, String > {



        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXMLFile(params[0]);
            if (mFileContents == null) {
                Log.d("Download Data", "Error Downloading");
            }
            return mFileContents;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("Download Data", "Result was: " + result);
//            xmlTextView.setText(mFileContents);
        }

        private String downloadXMLFile(String urlPath) {
            StringBuilder tempBuffer = new StringBuilder();
            try
            {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d("DownloadData", "The Response Code was: " +response);

                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int charRead;
                char[] inputBuffer = new char[500];
                while (true)
                {
                    charRead = inputStreamReader.read(inputBuffer);

                    if(charRead<=0)
                    {
                        break;
                    }

                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));

                }

                return tempBuffer.toString();
            }

            catch(IOException e)
            {
                Log.d("DownloadData", "IOException Reading Error: " + e.getMessage());
            }

            catch (SecurityException e)
            {
                Log.d("DownloadData", "Security Exception. Needs Permissions?");
            }

            return null;
        }
    }
}
