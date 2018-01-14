package unsa.pmf.www.verz1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by acer on 27.7.2017..
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        // moze vec postojati ako se prilikom rotacije prekine aktivnost, onda svu tu listu fragmenata
        // sa id-em preuzima FragmentManager, i prilikom ponovnog kreiranja aktivnosti stvara se novi
        // FragmentManager koji preuzima tu listu i vraca se sve na staro
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }
}
