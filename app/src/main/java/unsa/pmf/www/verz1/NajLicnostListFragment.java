package unsa.pmf.www.verz1;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by acer on 17.9.2017..
 */

public class NajLicnostListFragment extends Fragment {
    private RecyclerView mLicnostRecyclerView;
    private NajLicnostAdapter mAdapter;
    private boolean mPodnaslovVisible;
    private static final String SAVED_PODNASLOV_VISIBLE = "podnaslov";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_licnost_list, container, false);
        mLicnostRecyclerView = (RecyclerView) view.findViewById(R.id.licnost_recycler_view);
        mLicnostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState != null)
        {
            mPodnaslovVisible = savedInstanceState.getBoolean(SAVED_PODNASLOV_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_PODNASLOV_VISIBLE, mPodnaslovVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_licnost_list, menu);

        MenuItem podnaslovItem = menu.findItem(R.id.prikazi_podnaslov);
        if(mPodnaslovVisible)
        {
            podnaslovItem.setTitle(R.string.sakrij_podnaslov);
        }
        else
        {
            podnaslovItem.setTitle(R.string.prikazi_podnaslov);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nova_licnost:
                NajLicnost najLicnost = new NajLicnost();
                NajLicnostLab.get(getActivity()).dodajNajLicnost(najLicnost);
                Intent intent = NajLicnostPagerActivity.newIntent(getActivity(), najLicnost.getId());
                startActivity(intent);
                return true;
            case R.id.prikazi_podnaslov:
                mPodnaslovVisible = !mPodnaslovVisible;
                getActivity().invalidateOptionsMenu();
                azurirajPodnaslov();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void azurirajPodnaslov()
    {
        NajLicnostLab najLicnostLab = NajLicnostLab.get(getActivity());
        int brojLicnosti = najLicnostLab.dajNajLicnosti().size();
        String podnaslov = getResources().getQuantityString(R.plurals.podnaslov_mnozina, brojLicnosti, brojLicnosti);
        if(!mPodnaslovVisible)
        {
            podnaslov = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(podnaslov);
    }

    private void updateUI()
    {
        NajLicnostLab najLicnostLab = NajLicnostLab.get(getActivity());
        List<NajLicnost> najLicnosti = najLicnostLab.dajNajLicnosti();
        if(mAdapter == null)
        {
            mAdapter = new NajLicnostAdapter(najLicnosti);
            mLicnostRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setNajLicnosti(najLicnosti);
            mAdapter.notifyDataSetChanged();
        }
        azurirajPodnaslov();
    }

    private class NajLicnostHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mNazivTextView;
        private NajLicnost mNajLicnost;

        public NajLicnostHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_licnost, parent, false));
            itemView.setOnClickListener(this);

            mNazivTextView = (TextView) itemView.findViewById(R.id.licnost_naziv);
        }

        public void bind(NajLicnost najLicnost)
        {
            mNajLicnost = najLicnost;
            mNazivTextView.setText(mNajLicnost.getNaziv());
        }

        @Override
        public void onClick(View view)
        {
            Intent intent = NajLicnostPagerActivity.newIntent(getActivity(), mNajLicnost.getId());
            startActivity(intent);
        }
    }

    private class NajLicnostAdapter extends RecyclerView.Adapter<NajLicnostHolder>
    {
        private List<NajLicnost> mNajLicnosti;

        public NajLicnostAdapter(List<NajLicnost> najLicnosti)
        {
            mNajLicnosti = najLicnosti;
        }

        @Override
        public NajLicnostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NajLicnostHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(NajLicnostHolder holder, int position) {
            NajLicnost najLicnost = mNajLicnosti.get(position);
            holder.bind(najLicnost);
        }

        @Override
        public int getItemCount() {
            return mNajLicnosti.size();
        }

        public void setNajLicnosti(List<NajLicnost> najLicnosti)
        {
            mNajLicnosti = najLicnosti;
        }
    }
}
