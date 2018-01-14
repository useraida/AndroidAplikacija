package unsa.pmf.www.verz1;

import android.support.v4.app.Fragment;

/**
 * Created by acer on 17.9.2017..
 */

public class NajLicnostListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment()
    {
        return new NajLicnostListFragment();
    }
}
