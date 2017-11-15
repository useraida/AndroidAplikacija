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
    public static final String EXTRA_DATUM = "unsa.pmf.www.verz1.datum";

    private static final String ARG_DATUM = "datum";
    private DatePicker mDatumPicker;
    public static DatumPickerFragment newInstance(Date datum) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATUM, datum);
        DatumPickerFragment fragment = new DatumPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Date datum = (Date) getArguments().getSerializable(ARG_DATUM);
        Calendar kalendar = Calendar.getInstance();
        kalendar.setTime(datum);
        int god = kalendar.get(Calendar.YEAR);
        int mj = kalendar.get(Calendar.MONTH);
        int dan = kalendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_datum, null);

        mDatumPicker = (DatePicker) v.findViewById(R.id.dialog_datum_picker);
        mDatumPicker.init(god, mj, dan, null);
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
        intent.putExtra(EXTRA_DATUM, datum);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
