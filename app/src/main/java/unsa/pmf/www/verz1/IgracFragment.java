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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by acer on 26.7.2017..
 */

public class IgracFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Igrac mIgrac;
    private EditText mNaziv;
    private EditText mMjestoRodjenja;
    private EditText mDatumRodjenja;
    private EditText mDatumUlaskaUKlub;
    private EditText mPozicijaIgraca;
    private EditText mPrethodniKlub;
    private Button mDatumButton;
    private CheckBox mPrviTimCheckBox;

    public static IgracFragment newInstance(UUID igracId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, igracId);

        IgracFragment fragment = new IgracFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID igracId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mIgrac = IgracLab.get(getActivity()).dajIgraca(igracId);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        IgracLab.get(getActivity()).updateIgrac(mIgrac);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_igrac, container, false);

        mNaziv = (EditText) v.findViewById(R.id.igrac_naziv);
        mNaziv.setText(mIgrac.getNaziv());
        mMjestoRodjenja = (EditText) v.findViewById(R.id.igrac_mjestoR);
        mMjestoRodjenja.setText(mIgrac.getMjestoRodjenja());
        mDatumRodjenja = (EditText) v.findViewById(R.id.igrac_datumR);
        mDatumRodjenja.setText(mIgrac.getDatumRodjenja());
        mPozicijaIgraca = (EditText) v.findViewById(R.id.igrac_pozicija);
        mPozicijaIgraca.setText(mIgrac.getPozicijaIgraca());
        mDatumUlaskaUKlub = (EditText) v.findViewById(R.id.igrac_datumU);
        mDatumUlaskaUKlub.setText(mIgrac.getDatumUlaskaUKlub());

        mPrethodniKlub = (EditText) v.findViewById(R.id.igrac_prethodni_klub);
        mPrethodniKlub.setText(mIgrac.getPrethodniKlub());

        mNaziv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIgrac.setNaziv(s.toString());
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
                mIgrac.setMjestoRodjenja(s.toString());
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
                mIgrac.setDatumRodjenja(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDatumUlaskaUKlub.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIgrac.setDatumUlaskaUKlub(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPozicijaIgraca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIgrac.setPozicijaIgraca(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPrethodniKlub.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIgrac.setPrethodniKlub(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

      /*  mDatumButton = (Button) v.findViewById(R.id.igrac_datum);
        azurirajDatum();
        mDatumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatumPickerFragment dialog = DatumPickerFragment.newInstance(mIgrac.getDatum());
                dialog.setTargetFragment(IgracFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });*/

        mPrviTimCheckBox = (CheckBox) v.findViewById(R.id.igrac_prvi_tim);
        mPrviTimCheckBox.setChecked(mIgrac.isPrviTim());
        mPrviTimCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mIgrac.setPrviTim(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.brisanje_igraca, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.izbrisi_igraca:
                UUID igracID = mIgrac.getId();
                IgracLab.get(getActivity()).izbrisiIgraca(igracID);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent podatak) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date datum = (Date) podatak
                    .getSerializableExtra(DatumPickerFragment.EXTRA_DATUM);
            mIgrac.setDatum(datum);
            azurirajDatum();
        }
    }

    private void azurirajDatum() {
        mDatumButton.setText(mIgrac.getDatum().toString());
    }*/
}
