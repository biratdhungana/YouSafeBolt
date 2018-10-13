package at.dhungana.birat.ysbolt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton door1;
    ImageButton door2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        door1 = (ImageButton) findViewById(R.id.door1button);
        door2 = (ImageButton) findViewById(R.id.door2button);

        door1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentloaddoor1 = new Intent(MainActivity.this, Door1Activity.class);
                startActivity(intentloaddoor1);
            }
        });

        door2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentloaddoor2 = new Intent(MainActivity.this, Door2Activity.class);
                startActivity(intentloaddoor2);
            }
        });
    }
}
