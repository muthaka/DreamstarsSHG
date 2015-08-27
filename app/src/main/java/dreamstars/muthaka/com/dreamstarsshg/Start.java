package dreamstars.muthaka.com.dreamstarsshg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Peter Muthaka on 8/23/2015.
 */
public class Start extends Activity {
    Button contribution,constitution,achievement,members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        contribution = (Button) findViewById(R.id.start_contribution);
        constitution = (Button) findViewById(R.id.start_constitution);
        achievement = (Button) findViewById(R.id.start_btnachiev);
        members = (Button) findViewById(R.id.start_btnmembers);


        // view contribution click event
        contribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Launching Contribution Activity
                Intent i = new Intent(getApplicationContext(), Contribution.class);
                startActivity(i);

            }
        });

        // view constitution click event
        constitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Launching Constitution Activity
                Intent i = new Intent(getApplicationContext(), Constitution.class);
                startActivity(i);
            }
        });

        // view achievement click event
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Launching Achievement Activity
                Intent i = new Intent(getApplicationContext(), Achievement.class);
                startActivity(i);

            }
        });

        // view members click event
        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Launching Members Activity
                Intent i = new Intent(getApplicationContext(), Members.class);
                startActivity(i);

            }
        });

    }
}
