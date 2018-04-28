package in.blogspot.iamdhariot.welcomeslidedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnRestartSlider ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnRestartSlider = (Button)findViewById(R.id.btnRestartSlider);
        btnRestartSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSlides(v);


                }


        });
    }
    public void loadSlides(View view) {
        new PreferenceManager(this).clearPreference();
        finish();
        startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
    }
}
