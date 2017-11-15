package unsa.pmf.www.verz1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import unsa.pmf.www.verz1.database.IgracBaseHelper;
import unsa.pmf.www.verz1.database.IgracCursorWrapper;
import unsa.pmf.www.verz1.database.IgracDbSchema.IgracTable;

/**
 * Created by acer on 26.7.2017..
 */

public class IgracLab {
    private static IgracLab sIgracLab;

    private Context mContext;
    private SQLiteDatabase mBazaPodataka;

    public static IgracLab get(Context context)
    {
        if(sIgracLab == null)
        {
            sIgracLab = new IgracLab(context);
        }
        return sIgracLab;
    }

    private IgracLab(Context context)
    {
        mContext = context.getApplicationContext();
        mBazaPodataka = new IgracBaseHelper(mContext).getWritableDatabase();

    }

    public void dodajIgraca(Igrac i)
    {
        ContentValues values = getContentValues(i);
        mBazaPodataka.insert(IgracTable.IME, null, values);
    }

    public void izbrisiIgraca(UUID igracID)
    {
        String uuidString = igracID.toString();
        mBazaPodataka.delete(IgracTable.IME, IgracTable.Kolone.UUID + " = ?", new String[] {uuidString});
    }

    public List<Igrac> dajIgrace()
    {
        List<Igrac> igraci = new ArrayList<>();
        IgracCursorWrapper cursor = queryIgraci(null, null);
        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                igraci.add(cursor.getIgrac());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return igraci;
    }

    public Igrac dajIgraca(UUID id)
    {
        IgracCursorWrapper cursor = queryIgraci(
                IgracTable.Kolone.UUID + " = ?",
                new String[] {id.toString()}
        );
        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getIgrac();
        } finally {
            cursor.close();
        }
    }

    public void updateIgrac(Igrac igrac)
    {
        String uuidString = igrac.getId().toString();
        ContentValues values = getContentValues(igrac);
        mBazaPodataka.update(IgracTable.IME, values, IgracTable.Kolone.UUID + " = ?", new String[] {uuidString});
    }

    private IgracCursorWrapper queryIgraci(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mBazaPodataka.query(
                IgracTable.IME,
                null, // kolone - null selektira sve kolone
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new IgracCursorWrapper(cursor);
    }

    // pisanje u bazu, i azuriranje baze se vrsi pomocu pomocne klase ContentValues
    private static ContentValues getContentValues(Igrac igrac)
    {
        ContentValues values = new ContentValues();
        values.put(IgracTable.Kolone.UUID, igrac.getId().toString());
        values.put(IgracTable.Kolone.ImeIPrezime, igrac.getNaziv());
        values.put(IgracTable.Kolone.DatumRodjenja, igrac.getDatumRodjenja());
        values.put(IgracTable.Kolone.MjestoRodjenja, igrac.getMjestoRodjenja());
        values.put(IgracTable.Kolone.DatumUlaskaUKlub, igrac.getDatumUlaskaUKlub());
        values.put(IgracTable.Kolone.PozicijaIgraca, igrac.getPozicijaIgraca());
        values.put(IgracTable.Kolone.PrethodniKlub, igrac.getPrethodniKlub());
        values.put(IgracTable.Kolone.Datum, igrac.getDatum().getTime());
        values.put(IgracTable.Kolone.PrviTim, igrac.isPrviTim() ? 1 : 0);

        return values;
    }
}
