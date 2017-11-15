package unsa.pmf.www.verz1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by acer on 17.9.2017..
 */

public abstract class SingleFragmentLicnostActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licnost_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        // moze vec postojati ako se npr prilikom rotacije prekine aktivnost, onda svu tu listu fragmenata
        // sa id-em preuzima FragmantManager, i prilikom ponovnog kreiranja aktivnosti stvara se novi
        // FragmentManager koji preuzima tu listu i vraca se sve na staro
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }
}
