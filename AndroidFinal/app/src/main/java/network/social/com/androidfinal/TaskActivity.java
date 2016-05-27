package network.social.com.androidfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import network.social.com.androidfinal.database.DBHelper;
import network.social.com.androidfinal.model.Gorev;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        final ListView list = (ListView) findViewById(R.id.aliciList);
        DBHelper database=new DBHelper(getApplicationContext());
        List<Gorev> tasks=database.getCustomerTaskInformation();

        TaskAdapter myAdapter=new TaskAdapter(this,tasks);

        list.setAdapter(myAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final AlertDialog.Builder b = new AlertDialog.Builder(TaskActivity.this);
                b.setMessage("Kargo Teslim Yerine Gelindi Mi?");
                b.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedFromList;
                        selectedFromList = list.getItemAtPosition(position).toString();
                        gorevTamamla(selectedFromList);
                    }
                });
                b.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                b.show();
                return false;
            }
        });

    }






    public void gorevTamamla(String liste ){
        Intent gorevTamamla=new Intent(this,MissionComplete.class);
        gorevTamamla.putExtra("gorev",liste);
        startActivity(gorevTamamla);


    }
}
