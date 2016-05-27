package network.social.com.androidfinal;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class MenuActivity extends AppCompatActivity {
    ImageView map, tasks, report, blabla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        map = (ImageView) findViewById(R.id.menuMapButton);
        tasks = (ImageView) findViewById(R.id.menuMissionButton);

    }


    public void startTaskActivity(View view) {

        Intent gorevActivity = new Intent(this, TaskActivity.class);
        startActivity(gorevActivity);

    }

    public void startMapActivity(View view){
        Intent haritaActivity=new Intent(this,MapActivity.class);
        startActivity(haritaActivity);
    }




}
