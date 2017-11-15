package unsa.pmf.www.verz1;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by acer on 17.9.2017..
 */

public class NajLicnostPagerActivity  extends AppCompatActivity{
    private static final String EXTRA_LICNOST_ID = "unsa.pmf.www.verz1.licnost_id";

    private ViewPager mViewPager;
    private List<NajLicnost> mLicnosti;

    public static Intent newIntent(Context packageContext, UUID najLicnostId)
    {
        Intent intent = new Intent(packageContext, NajLicnostPagerActivity.class);
        intent.putExtra(EXTRA_LICNOST_ID, najLicnostId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licnost_pager);

        UUID licnostId = (UUID) getIntent().getSerializableExtra(EXTRA_LICNOST_ID);

        mViewPager = (ViewPager) findViewById(R.id.licnost_view_pager);
        mLicnosti = NajLicnostLab.get(this).dajNajLicnosti();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                NajLicnost najLicnost = mLicnosti.get(position);
                return NajLicnostFragment.newInstance(najLicnost.getId());
            }

            @Override
            public int getCount() {
                return mLicnosti.size();
            }
        });

        for(int i = 0; i < mLicnosti.size(); i++)
        {
            if(mLicnosti.get(i).getId().equals(licnostId))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
