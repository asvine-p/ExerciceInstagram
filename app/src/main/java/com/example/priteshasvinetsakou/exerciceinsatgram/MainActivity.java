package com.example.priteshasvinetsakou.exerciceinsatgram;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationData.instagramApp= new InstagramApp(this,
                ApplicationData.CLIENT_ID,
                ApplicationData.CLIENT_SECRET,
                ApplicationData.CALLBACK_URL);

        ApplicationData.instagramApp.setListener(new InstagramApp.OAuthAuthenticationListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Intent  mIntent =   new Intent(MainActivity.this, UserImage.class);

                startActivity(mIntent);
                finish();
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });

        if (ApplicationData.instagramApp.hasAccessToken())
            ApplicationData.instagramApp.getInfo();
        else
            ApplicationData.instagramApp.authorize();

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
