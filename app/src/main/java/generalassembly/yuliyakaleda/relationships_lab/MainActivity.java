package generalassembly.yuliyakaleda.relationships_lab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addDataToDatabase();

        result = (ListView) findViewById(R.id.result_list);

        Helper helper = Helper.getInstance(MainActivity.this);

        List<String> macyResult = helper.getEmployeesWorkingAtMacys();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(this);
        //TODO: Set click listeners for buttons, so they can query the database and update the ListView.

        Button macys = (Button) findViewById(R.id.same_workplace);
        macys.setOnClickListener(this);
        result.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, macyResult));
    }



    /**
     * Adds default data to the database if none exists.
     */
    private void addDataToDatabase() {
        File dbFile = getDatabasePath(Helper.DB_NAME);
        if(!dbFile.exists()) Helper.getInstance(this).addDataToDb();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(this, DialogActivity.class);
                startActivity(intent);
                break;
//            case R.id.same_workplace:
////                Intent intent2 = new Intent(this, Helper.class);
////                intent2.putExtra(Intent.EXTRA_TEXT, )
////                startActivity(intent2);
//
//                result.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, macyResult));
//                break;
        }
    }



}
