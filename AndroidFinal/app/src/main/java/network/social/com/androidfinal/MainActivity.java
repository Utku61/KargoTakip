package network.social.com.androidfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashEkran();
    }

    private void splashEkran(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final ImageView splashImage = (ImageView) findViewById(R.id.splashImage);
        animation.reset();
        splashImage.clearAnimation();
        splashImage.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                splashImage.setVisibility(View.INVISIBLE);
                Intent goLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(goLogin);
                MainActivity.this.finish();
            }
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
    }
}

