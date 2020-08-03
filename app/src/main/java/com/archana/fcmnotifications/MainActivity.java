package com.archana.fcmnotifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<Topic> {

    String TAG = "MainActivity";
    Button btnSend,btnSubscribe;
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        btnSend = findViewById(R.id.btnSend);
        btnSubscribe = findViewById(R.id.btnSubscribe);

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().subscribeToTopic("test")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                message  = "Topic subscribed successfully";
                                if (!task.isSuccessful()) {
                                    message  = "Topic subscribed successfully";
                                }
                                Log.d(TAG, "Topic Subscribed");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFCMNotification();
            }
        });


    }


    private void sendFCMNotification() {
        Log.d(TAG, "sendFCMNotification");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        try {

            JSONObject mainObject = new JSONObject();
            JSONObject dataParentObject = new JSONObject();
            JSONObject dataChildObject = new JSONObject();
            dataChildObject.put("title", "Notification title!");
            dataChildObject.put("message",  "Notification Message");
            dataParentObject.put("data", dataChildObject);
            mainObject.put("to", "/topics/test");
            mainObject.put("data", dataParentObject);

            Log.d(TAG, "dataObject :: " + mainObject);

            Call<Topic> userCall = apiInterface.pushNotification(mainObject.toString());
            userCall.enqueue(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResponse(Call<Topic> call, Response<Topic> response) {
        Log.d(TAG, "response:::: " + response.toString());
    }

    @Override
    public void onFailure(Call<Topic> call, Throwable t) {

    }
}