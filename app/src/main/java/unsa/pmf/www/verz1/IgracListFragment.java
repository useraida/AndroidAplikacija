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
import android.widget.Toast;

import java.util.List;

/**
 * Created by acer on 27.7.2017..
 */

public class IgracListFragment extends Fragment {
    private RecyclerView mIgracRecyclerView;
    private IgracAdapter mAdapter;
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
        View view = inflater.inflate(R.layout.fragment_igrac_list, container, false);
        mIgracRecyclerView = (RecyclerView) view.findViewById(R.id.igrac_recycler_view);
        //LayoutManager pozicionira items na ekran, i definise kako radi skrolanje
        //u slucaju da zaboravimo postaviti LayoutManager RecyclerView ne bi mogao raditi
        mIgracRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState != null)
        {
            mPodnaslovVisible = savedInstanceState.getBoolean(SAVED_PODNASLOV_VISIBLE);
        }

        updateUI();

        return view;
    }

    // kada izmjenimo neke podatke u IgracPagerActivity, i kliknemo na back button, ti podaci ne bi bili izmjenjeni
    // na IgracListFragmentu ukoliko ne stavimo updateUI() unutar metode onResume()
    // Adapter od RecyclerView-a mora biti informisan o podacima koji su se izmijenili, tako da on moze reloadati listu
    // Kada IgracListFragment startuje instancu  IgracPagerActivity-a, IgracPagerActivity je na vrhu steka, sto znaci da se
    // tada instanca od IgracListActivity pauzira i stopira a koja je prethodno bila na vrhu steka.
    // Kada korisnik klikne back button da se vrati nazad na listu, IgracPagerActivity je tada skinuta sa steka i unistena
    // i tada IgracListActivity je started and resumed
    /*
    Why
    override onResume() to update the RecyclerView and not onStart()? You cannot assume
     that your activity will be stopped when another activity is in front of it. If the other activity is transparent,
     your activity may just be paused. If your activity is paused and your update code is in onStart(), then
     the list will not be reloaded. In general, onResume() is the safest place to take action to update a
    fragment’s view.
    */
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
        // prilikom rotacije da se sacuva broj igraca
        outState.putBoolean(SAVED_PODNASLOV_VISIBLE, mPodnaslovVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_igrac_list, menu);

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
            case R.id.novi_igrac:
                Igrac igrac = new Igrac();
                IgracLab.get(getActivity()).dodajIgraca(igrac);
                Intent intent = IgracPagerActivity.newIntent(getActivity(), igrac.getId());
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
        IgracLab igracLab = IgracLab.get(getActivity());
        int brojIgraca = igracLab.dajIgrace().size();
        String podnaslov = getResources().getQuantityString(R.plurals.podnaslov_mnozina, brojIgraca, brojIgraca);
        if(!mPodnaslovVisible)
        {
            podnaslov = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(podnaslov);
    }

    private void updateUI()
    {
        IgracLab igracLab = IgracLab.get(getActivity());
        List<Igrac> igraci = igracLab.dajIgrace();
        if(mAdapter == null)
        {
            mAdapter = new IgracAdapter(igraci);
            mIgracRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setIgraci(igraci);
            // ukoliko su izmjenjeni odredjeni podaci u IgracPagerActivity da se izmjene u listi, tj ime igraca konkretno
            mAdapter.notifyDataSetChanged();
        }
        azurirajPodnaslov();
    }

    private class IgracHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mNazivTextView;
        private Igrac mIgrac;

        public IgracHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_igrac, parent, false));
            itemView.setOnClickListener(this);

            mNazivTextView = (TextView) itemView.findViewById(R.id.igrac_naziv);
        }

        public void bind(Igrac igrac)
        {
            mIgrac = igrac;
            mNazivTextView.setText(mIgrac.getNaziv());
        }

        @Override
        public void onClick(View view)
        {
            Intent intent = IgracPagerActivity.newIntent(getActivity(), mIgrac.getId());
            startActivity(intent);
        }
    }

    private class IgracAdapter extends RecyclerView.Adapter<IgracHolder>
    {
        private List<Igrac> mIgraci;

        public IgracAdapter(List<Igrac> igraci)
        {
            mIgraci = igraci;
        }

        @Override
        public IgracHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new IgracHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(IgracHolder holder, int position) {
            Igrac igrac = mIgraci.get(position);
            holder.bind(igrac);
        }

        @Override
        public int getItemCount() {
            return mIgraci.size();
        }

        public void setIgraci(List<Igrac> igraci)
        {
            mIgraci = igraci;
        }
    }

}
