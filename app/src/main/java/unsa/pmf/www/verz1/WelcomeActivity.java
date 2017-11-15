package unsa.pmf.www.verz1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView mStartButton;
    private ImageView mHistorijaKluba;
    private ImageView mOAplikacijiButton;
    private ImageView mNajznacajnijeLicnosti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mStartButton = (ImageView) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, IgracListActivity.class);
                startActivity(intent);
            }
        });

        mHistorijaKluba = (ImageView) findViewById(R.id.historija_kluba_button);
        mHistorijaKluba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, HistorijaKlubaActivity.class);
                startActivity(intent);
            }
        });

        mOAplikacijiButton = (ImageView) findViewById(R.id.o_aplikaciji_button);
        mOAplikacijiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, OAplikacijiActivity.class);
                startActivity(intent);
            }
        });

        mNajznacajnijeLicnosti = (ImageView) findViewById(R.id.najznacajnije_licnosti_button);
        mNajznacajnijeLicnosti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, NajLicnostListActivity.class);
                startActivity(intent);
            }
        });
    }
}
