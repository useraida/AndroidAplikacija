package unsa.pmf.www.verz1;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by acer on 31.7.2017..
 */

public class DatumPickerFragment extends DialogFragment {

    public static final String EXTRA_DATUM_RODJENJA = "unsa.pmf.www.verz1.datumrodjenja";
    public static final String EXTRA_DATUM_ULASKA_U_KLUB = "unsa.pmf.www.verz1.datum_ulaska_u_klub";
    public static final String EXTRA_DATUM_RODJENJA_LICNOST = "unsa.pmf.www.verz1.datum_rodjenja_licnost";

    private static final String ARG_DATUM = "datum";
    private DatePicker mDatumPicker;
    // newInstance() mijenja konstruktor fragmenta, na ovaj nacin fragment ne mora nista da zna o aktivnosti
    // koja ga hostuje, i samim tim ovaj fragment moze da koristi bilo koja aktivnost
    // da ne radimo na ovaj nacin, morali bismo koristiti EXTRA i ovaj fragment bi mogla koristiti samo jedna aktivnost
    // na osnovu intenta koji se proslijedi
    public static DatumPickerFragment newInstance(Date datum) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATUM, datum);
        DatumPickerFragment fragment = new DatumPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // FragmentManager aktivnosti koja hostuje ovaj fragment poziva ovu metodu koja sluzi za postavljanje DialogFragmenta na ekran
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Date datum = (Date) getArguments().getSerializable(ARG_DATUM);
        Calendar kalendar = Calendar.getInstance();
        kalendar.setTime(datum);
        int god = kalendar.get(Calendar.YEAR);
        int mj = kalendar.get(Calendar.MONTH);
        int dan = kalendar.get(Calendar.DAY_OF_MONTH);

        // inflejta izgled DatumPickera, a i prilikom rotacije ce sacuvati izgled, zato sto je ovo View
        // a View moze sacuvati stanje samo ako ima id, a u ovom slucaju smo u dialog_datum.xml stavili id za ovaj DatumPicker
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_datum, null);

        mDatumPicker = (DatePicker) v.findViewById(R.id.dialog_datum_picker);
        mDatumPicker.init(god, mj, dan, null);
        // koristi se AlertDialog.Builder klasa koja pruza interfejs za konstruisanje AlertDialog instance
        // prvo u konstruktoru AlertDialog.Builder stavljamo context, sto vraca instancu od AlertDialog.Builder
        // public AlertDialog.Builder setView() dodaje DatePicker() AlertDialogu
        // setView() konfigurise dialog tako da moze prikazati proslijedjeni View objekt, i prikauje ga
        // izmedju naslova dialoga --> "Datum: " i dugmeta --> "OK"
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.datum_picker_naziv).
                setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int god = mDatumPicker.getYear();
                        int mj = mDatumPicker.getMonth();
                        int dan = mDatumPicker.getDayOfMonth();
                        Date datum = new GregorianCalendar(god, mj, dan).getTime();
                        posaljiRezultat(Activity.RESULT_OK, datum);
                    }
                }).create();
    }

    private void posaljiRezultat(int resultCode, Date datum) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATUM_RODJENJA, datum);
        intent.putExtra(EXTRA_DATUM_ULASKA_U_KLUB, datum);
        intent.putExtra(EXTRA_DATUM_RODJENJA_LICNOST, datum);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
