package unsa.pmf.www.verz1.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import unsa.pmf.www.verz1.Igrac;
import unsa.pmf.www.verz1.database.IgracDbSchema.IgracTable;

/**
 * Created by acer on 3.8.2017..
 */

//A Cursor leaves a lot to be desired as a way to look at a table. All it does is give you raw column
// values.
    //  A CursorWrapper
// lets you wrap a Cursor you received from another place and add new methods on top of it.

public class IgracCursorWrapper extends CursorWrapper {
    public IgracCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Igrac getIgrac()
    {
        String uuidString = getString(getColumnIndex(IgracTable.Kolone.UUID));
        String naziv = getString(getColumnIndex(IgracTable.Kolone.ImeIPrezime));
        String datumRodjenja = getString(getColumnIndex(IgracTable.Kolone.DatumRodjenja));
        String mjestoRodjenja = getString(getColumnIndex(IgracTable.Kolone.MjestoRodjenja));
        String datumUlaskaUKlub = getString(getColumnIndex(IgracTable.Kolone.DatumUlaskaUKlub));
        String pozicijaIgraca = getString(getColumnIndex(IgracTable.Kolone.PozicijaIgraca));
        String prethodniKlub = getString(getColumnIndex(IgracTable.Kolone.PrethodniKlub));
        long datum = getLong(getColumnIndex(IgracTable.Kolone.Datum));
        int isPrviTim = getInt(getColumnIndex(IgracTable.Kolone.PrviTim));

        Igrac igrac = new Igrac(UUID.fromString(uuidString));
        igrac.setNaziv(naziv);
        igrac.setDatumRodjenja(datumRodjenja);
        igrac.setMjestoRodjenja(mjestoRodjenja);
        igrac.setDatumUlaskaUKlub(datumUlaskaUKlub);
        igrac.setPozicijaIgraca(pozicijaIgraca);
        igrac.setPrethodniKlub(prethodniKlub);
        igrac.setDatum(new Date(datum));
        igrac.setPrviTim(isPrviTim != 0);

        return igrac;
    }
}
