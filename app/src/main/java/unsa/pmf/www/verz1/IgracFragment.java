package unsa.pmf.www.verz1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * Created by acer on 26.7.2017..
 */

public class IgracFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";

    //ovaj string identifikuje DialogFragment u listi fragmenata fragmentManagera
    private static final String DIALOG_DATUM = "DialogDatum";
    private static final int SELECTED_PICTURE = 1;  // sluzi za onActivityResult(), izabrana slika iz galerije

    private static final int REQUEST_DATE_RODJENJE = 0;
    private static final int REQUEST_DATUM_ULASKA_U_KLUB = 2;

    private Igrac mIgrac;
    private EditText mNaziv;
    private Button mDatumRodjenjaButton;
    private Button mDatumUlaskaUKlubButton;
    private EditText mMjestoRodjenja;
    private EditText mPozicijaIgraca;
    private EditText mPrethodniKlub;
    private CheckBox mPrviTimCheckBox;
    private Button mIzaberiSliku;
    private ImageView mSlika;  // prikazuje sliku iz baze
    private Bitmap bp;         // pretvara u bitmap sliku koju smo izabrali iz galerije telefona
    private Bitmap smanjeniBp;   // sluzi da smanji sliku koju cemo cuvati u bazi
    private byte[] mSlikaByte;  // slika u bazi je sacuvana kao byte[]
    private String mDatumRodjenjaSring; // formatira datum kao datum /dan/mjesec/godina, bez time-a i bez gmt-a
    private String mDatumUlaskaUKlubString;

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
        // instanca igrac je izmijenjena u IgracFragmentu, i treba biti ispisana kada smo zavrsili sa IgracFragmentom
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
        mPozicijaIgraca = (EditText) v.findViewById(R.id.igrac_pozicija);
        mPozicijaIgraca.setText(mIgrac.getPozicijaIgraca());

        mPrethodniKlub = (EditText) v.findViewById(R.id.igrac_prethodni_klub);
        mPrethodniKlub.setText(mIgrac.getPrethodniKlub());

        mSlika = (ImageView) v.findViewById(R.id.izabrana_slika);

        // provjerava da li postoji slika u bazi, ako postoji onda je postavlja
        // posto je slika u bazi smjestena kao byte[], to pretvaramo tu sliku u Bitmap da bi se mogla prikazati korisniku
        if(mIgrac.getSlika() != null){
            mSlika.setImageBitmap(pretvoriUBitmap(mIgrac.getSlika()));
        }

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

        mDatumRodjenjaButton = (Button) v.findViewById(R.id.datum_rodjenja_button);
        azurirajDatumRodjenja(); // postavlja text buttona mDatumRodjenjaButton na trenutno vrijeme kreiranja igraca

        if(mIgrac.getDatumRodjenjaString() != null)  // ovo smo stavili da nije null iz razloga da kada se tek kreira novi igrac, na dugmetu da se prikaze
        {                                            // i datum i time i gmt, cisto da korisnik zna lakse da tu treba kliknuti za unos datuma
            azurirajDatumRodjenjaString();
        }
        mDatumRodjenjaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatumPickerFragment dialog = DatumPickerFragment.newInstance(mIgrac.getDatumRodjenja());
                // prenosenje podataka izmedju aktivnosti: pozivala se StartActivityForResult(), i ActivityManager je pratio
                // odnos roditelj-dijete, kada aktivnost dijete umre, ActivityManager zna koju aktivnost da koristi
                // slicnu vezu pravimo za prenosenje podataka izmedju dva fragmenta, tako sto pravimo IgracFragment target
                // fragmentom
                dialog.setTargetFragment(IgracFragment.this, REQUEST_DATE_RODJENJE);
                // da bi dodali DialogFragment FragmentManageru i postavili ga na ekran pozivamo metodu show()
                dialog.show(manager, DIALOG_DATUM);
            }
        });

        mDatumUlaskaUKlubButton = (Button) v.findViewById(R.id.datum_ulaska_u_klub_button);
        azurirajDatumUlaskaUKlub();

        if (mIgrac.getDatumUlaskaUKlubString() != null){
            azurirajDatumUlaskaUKlubString();
        }
        mDatumUlaskaUKlubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatumPickerFragment dialog = DatumPickerFragment.newInstance(mIgrac.getDatumUlaskaUKlub());
                dialog.setTargetFragment(IgracFragment.this, REQUEST_DATUM_ULASKA_U_KLUB);
                dialog.show(manager, DIALOG_DATUM);
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

        // button koji koristimo da izaberemo sliku iz galerije
        mIzaberiSliku = (Button) v.findViewById(R.id.izaberi_sliku);
        mIzaberiSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECTED_PICTURE && resultCode == RESULT_OK)
        {
            Uri izabranaSlika = data.getData();  // slika koju smo izabrali iz galerije
            try {
                bp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), izabranaSlika);
                smanjeniBp = decodeUri(izabranaSlika, 300); // smanjujemo velicinu sliku radi brzeg snimanja u bazu, te da se korisniku predstavi u tacno odredjenoj velicini
                mSlika.setImageBitmap(smanjeniBp); // predstavljamo sliku korisniku kada je izabere iz galerije, i prikazuje je trenutno(jer slika jos nije snimljena u bazu)
                mSlikaByte = BitmapUByte(smanjeniBp); // pretvara izabranu sliku u byte[] da bi se slika mogla snimiti u bazu
                mIgrac.setSlika(mSlikaByte);  // dodaje izabranu sliku u bazu
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == REQUEST_DATE_RODJENJE){
            Date datum = (Date) data.getSerializableExtra(DatumPickerFragment.EXTRA_DATUM_RODJENJA);
            mIgrac.setDatumRodjenja(datum);
            azurirajDatumRodjenja();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // pretvara datum + time + gmt u obicni datum
            mDatumRodjenjaSring = df.format(datum);  // --> /dan/mjesec/godina
            mIgrac.setDatumRodjenjaString(mDatumRodjenjaSring);  // dodaje u bazu obicni datum --> dan/mjesec/godina
            azurirajDatumRodjenjaString();  //prikazuje izabrani datum na buttonu
        }
        else if(requestCode == REQUEST_DATUM_ULASKA_U_KLUB){
            Date datum = (Date) data.getSerializableExtra(DatumPickerFragment.EXTRA_DATUM_ULASKA_U_KLUB);
            mIgrac.setDatumUlaskaUKlub(datum);
            azurirajDatumUlaskaUKlub();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            mDatumUlaskaUKlubString = dateFormat.format(datum);
            mIgrac.setDatumUlaskaUKlubString(mDatumUlaskaUKlubString);
            azurirajDatumUlaskaUKlubString();
        }
    }

    private void azurirajDatumUlaskaUKlub() {
        mDatumUlaskaUKlubButton.setText(mIgrac.getDatumUlaskaUKlub().toString());
    }

    private void azurirajDatumUlaskaUKlubString() {
        mDatumUlaskaUKlubButton.setText(mIgrac.getDatumUlaskaUKlubString());
    }

    private void azurirajDatumRodjenjaString() {
        mDatumRodjenjaButton.setText(mIgrac.getDatumRodjenjaString());
    }

    private void azurirajDatumRodjenja() {
        mDatumRodjenjaButton.setText(mIgrac.getDatumRodjenja().toString());
    }

    //pretvori bitmap u byte[]
    public byte[] BitmapUByte(Bitmap b){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    public Bitmap pretvoriUBitmap(byte[] b){
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    //COnvert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);
            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;
            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
