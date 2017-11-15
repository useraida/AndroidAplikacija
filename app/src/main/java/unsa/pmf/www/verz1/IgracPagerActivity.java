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
 * Created by acer on 28.7.2017..
 */

public class IgracPagerActivity extends AppCompatActivity {

    private static final String EXTRA_IGRAC_ID = "unsa.pmf.www.verz1.igrac_id";

    private ViewPager mViewPager;
    private List<Igrac> mIgraci;

    public static Intent newIntent(Context packageContext, UUID igracId)
    {
        Intent intent = new Intent(packageContext, IgracPagerActivity.class);
        intent.putExtra(EXTRA_IGRAC_ID, igracId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igrac_pager);

        UUID igracId = (UUID) getIntent().getSerializableExtra(EXTRA_IGRAC_ID);

        mViewPager = (ViewPager) findViewById(R.id.igrac_view_pager);
        mIgraci = IgracLab.get(this).dajIgrace();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Igrac igrac = mIgraci.get(position);
                return IgracFragment.newInstance(igrac.getId());
            }

            @Override
            public int getCount() {
                return mIgraci.size();
            }
        });

        for(int i = 0; i < mIgraci.size(); i++)
        {
            if(mIgraci.get(i).getId().equals(igracId))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
