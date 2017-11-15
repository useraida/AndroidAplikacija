package unsa.pmf.www.verz1;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by acer on 17.9.2017..
 */

public class NajLicnostFragment extends Fragment{
    private static final String ARG_NAJLICNOST_ID = "naj_licnost_id";

    private NajLicnost mNajLicnost;
    private EditText mNaziv;
    private EditText mMjestoRodjenja;
    private EditText mDatumRodjenja;
    private EditText mPodaci;

    public static NajLicnostFragment newInstance(UUID najLicnostId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NAJLICNOST_ID, najLicnostId);

        NajLicnostFragment fragment = new NajLicnostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID najLicnostId = (UUID) getArguments().getSerializable(ARG_NAJLICNOST_ID);
        mNajLicnost = NajLicnostLab.get(getActivity()).dajNajLicnost(najLicnostId);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        NajLicnostLab.get(getActivity()).updateNajLicnost(mNajLicnost);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_naj_licnost, container, false);

        mNaziv = (EditText) v.findViewById(R.id.licnost_naziv);
        mNaziv.setText(mNajLicnost.getNaziv());
        mMjestoRodjenja = (EditText) v.findViewById(R.id.licnost_mjestoR);
        mMjestoRodjenja.setText(mNajLicnost.getMjestoRodjenja());
        mDatumRodjenja = (EditText) v.findViewById(R.id.licnost_datumR);
        mDatumRodjenja.setText(mNajLicnost.getDatumRodjenja());
        mPodaci = (EditText) v.findViewById(R.id.licnost_podaci);
        mPodaci.setText(mNajLicnost.getZnacajniPodaci());

        mNaziv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNajLicnost.setNaziv(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMjestoRodjenja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNajLicnost.setMjestoRodjenja(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDatumRodjenja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNajLicnost.setDatumRodjenja(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPodaci.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNajLicnost.setZnacajniPodaci(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.brisanje_licnosti, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.izbrisi_licnost:
                UUID najLicnostID = mNajLicnost.getId();
                NajLicnostLab.get(getActivity()).izbrisiNajLicnost(najLicnostID);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent podatak) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }

}
