package may.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {
    TextView splashtext;
    ImageView krishna;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sp=getSharedPreferences(constantdata.pref,MODE_PRIVATE);

        AlphaAnimation animation =new AlphaAnimation(0,1);
        animation.setDuration(2000);

        splashtext=findViewById(R.id.splash_text);
        krishna=findViewById(R.id.krishna);
        krishna.setAnimation(animation);
        splashtext.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sp.getString(constantdata.name,"").equals("")){
                    new common(splash_screen.this, MainActivity.class);
                    finish();
                }
                else {
                    new common(splash_screen.this, home_page.class);
                    finish();
                }
            }
        },2000);
    }
}