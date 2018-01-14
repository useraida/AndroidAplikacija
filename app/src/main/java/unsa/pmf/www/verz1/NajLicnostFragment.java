package unsa.pmf.www.verz1;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by acer on 17.9.2017..
 */

public class NajLicnostFragment extends Fragment{
    private static final String ARG_NAJLICNOST_ID = "naj_licnost_id";

    private static final int REQUEST_DATUM_RODJENJA_LICNOST = 0;
    private static final String DIALOG_DATUM = "DialogDatum";

    private NajLicnost mNajLicnost;
    private EditText mNaziv;
    private EditText mMjestoRodjenja;
    private Button mDatumRodjenjaButton;
    private String mDatumRodjenjaString;
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

        mDatumRodjenjaButton = (Button) v.findViewById(R.id.datum_rodjenja_licnost_button);
        azurirajDatumRodjenja();

        if(mNajLicnost.getDatumRodjenjaString() != null){
            azurirajDatumRodjenjaString();
        }
        mDatumRodjenjaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatumPickerFragment dialog = DatumPickerFragment.newInstance(mNajLicnost.getDatumRodjenja());
                dialog.setTargetFragment(NajLicnostFragment.this, REQUEST_DATUM_RODJENJA_LICNOST);
                dialog.show(manager, DIALOG_DATUM);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_DATUM_RODJENJA_LICNOST){
            Date datum = (Date) data.getSerializableExtra(DatumPickerFragment.EXTRA_DATUM_RODJENJA_LICNOST);
            mNajLicnost.setDatumRodjenja(datum);
            azurirajDatumRodjenja();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            mDatumRodjenjaString = df.format(datum);
            mNajLicnost.setDatumRodjenjaString(mDatumRodjenjaString);
            azurirajDatumRodjenjaString();
        }
    }

    private void azurirajDatumRodjenja() {
        mDatumRodjenjaButton.setText(mNajLicnost.getDatumRodjenja().toString());
    }

    private void azurirajDatumRodjenjaString() {
        mDatumRodjenjaButton.setText(mNajLicnost.getDatumRodjenjaString());
    }

}
