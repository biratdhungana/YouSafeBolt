package at.dhungana.birat.ysbolt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

import org.w3c.dom.Text;

public class Door1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door1);

        setTitle("Door 1");

        //Getting the data from MainActivity to Door1Activity.
        Intent intent1 = getIntent();
        String message = intent1.getStringExtra("message");
        TextView textView = (TextView)findViewById(R.id.notifications);
        textView.setText(message);

    }
}
