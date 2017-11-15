package unsa.pmf.www.verz1;

import android.support.v4.app.Fragment;

/**
 * Created by acer on 27.7.2017..
 */

public class IgracListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment()
    {
        return new IgracListFragment();
    }
}
