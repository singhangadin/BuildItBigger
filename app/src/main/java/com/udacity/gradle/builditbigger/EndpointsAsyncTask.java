package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**<p>
 * Created by Angad on 06-11-2016.
 * </p>
 */

public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private JokeLoadListener jokeLoadListener;

    @Override
    protected final String doInBackground(Context... context) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        this.context = context[0];
        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public void setJokeLoadListener(JokeLoadListener jokeLoadListener) {
        this.jokeLoadListener = jokeLoadListener;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.contains("failed to connect to"))
        {   jokeLoadListener.onJokeLoaded("");
            Toast.makeText(context,"Oops, Failed to contact Joker!",Toast.LENGTH_SHORT).show();
        }
        else
        {   jokeLoadListener.onJokeLoaded(result);
        }
    }
}
