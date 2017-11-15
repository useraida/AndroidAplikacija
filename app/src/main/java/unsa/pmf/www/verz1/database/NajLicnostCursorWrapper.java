package unsa.pmf.www.verz1.database;


import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import unsa.pmf.www.verz1.NajLicnost;
import unsa.pmf.www.verz1.database.NajLicnostDbSchema.NajLicnostTable;

/**
 * Created by acer on 17.9.2017..
 */

public class NajLicnostCursorWrapper extends CursorWrapper{
    public NajLicnostCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }
    public NajLicnost getNajLicnost()
    {
        String uuidString = getString(getColumnIndex(NajLicnostTable.Kolone.UUID));
        String naziv = getString(getColumnIndex(NajLicnostTable.Kolone.ImeIPrezime));
        String datumRodjenja = getString(getColumnIndex(NajLicnostTable.Kolone.DatumRodjenja));
        String mjestoRodjenja = getString(getColumnIndex(NajLicnostTable.Kolone.MjestoRodjenja));
        String podaci = getString(getColumnIndex(NajLicnostTable.Kolone.ZnacajniPodaci));

        NajLicnost najLicnost = new NajLicnost(UUID.fromString(uuidString));
        najLicnost.setNaziv(naziv);
        najLicnost.setDatumRodjenja(datumRodjenja);
        najLicnost.setMjestoRodjenja(mjestoRodjenja);
        najLicnost.setZnacajniPodaci(podaci);

        return najLicnost;
    }

}
