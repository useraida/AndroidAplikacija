package unsa.pmf.www.verz1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import unsa.pmf.www.verz1.database.NajLicnostDbSchema;
import unsa.pmf.www.verz1.database.NajLicnostBaseHelper;
import unsa.pmf.www.verz1.database.NajLicnostDbSchema.NajLicnostTable;
import unsa.pmf.www.verz1.database.NajLicnostCursorWrapper;

/**
 * Created by acer on 17.9.2017..
 */

public class NajLicnostLab {
    private static NajLicnostLab sNajLicnostLab;

    private Context mContext;
    private SQLiteDatabase mBazaPodataka;

    public static NajLicnostLab get(Context context)
    {
        if(sNajLicnostLab == null)
        {
            sNajLicnostLab = new NajLicnostLab(context);
        }
        return sNajLicnostLab;
    }

    private NajLicnostLab(Context context)
    {
        mContext = context.getApplicationContext();
        mBazaPodataka = new NajLicnostBaseHelper(mContext).getWritableDatabase();

    }

    public void dodajNajLicnost(NajLicnost i)
    {
        ContentValues values = getContentValues(i);
        mBazaPodataka.insert(NajLicnostDbSchema.NajLicnostTable.IME, null, values);
    }

    public void izbrisiNajLicnost(UUID NajLicnostID)
    {
        String uuidString = NajLicnostID.toString();
        mBazaPodataka.delete(NajLicnostDbSchema.NajLicnostTable.IME, NajLicnostDbSchema.NajLicnostTable.Kolone.UUID + " = ?", new String[] {uuidString});
    }

    public List<NajLicnost> dajNajLicnosti()
    {
        List<NajLicnost> najLicnosti = new ArrayList<>();
        NajLicnostCursorWrapper cursor = queryLicnosti(null, null);
        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                najLicnosti.add(cursor.getNajLicnost());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return najLicnosti;
    }

    public NajLicnost dajNajLicnost(UUID id)
    {
        NajLicnostCursorWrapper cursor = queryLicnosti(
                NajLicnostDbSchema.NajLicnostTable.Kolone.UUID + " = ?",
                new String[] {id.toString()}
        );
        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getNajLicnost();
        } finally {
            cursor.close();
        }
    }

    public void updateNajLicnost(NajLicnost najLicnost)
    {
        String uuidString = najLicnost.getId().toString();
        ContentValues values = getContentValues(najLicnost);
        mBazaPodataka.update(NajLicnostTable.IME, values, NajLicnostTable.Kolone.UUID + " = ?", new String[] {uuidString});
    }

    private NajLicnostCursorWrapper queryLicnosti(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mBazaPodataka.query(
                NajLicnostDbSchema.NajLicnostTable.IME,
                null, // kolone - null selektira sve kolone
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new NajLicnostCursorWrapper(cursor);
    }

    // pisanje u bazu, i azuriranje baze se vrsi pomocu pomocne klase ContentValues
    private static ContentValues getContentValues(NajLicnost najLicnost)
    {
        ContentValues values = new ContentValues();
        values.put(NajLicnostDbSchema.NajLicnostTable.Kolone.UUID, najLicnost.getId().toString());
        values.put(NajLicnostDbSchema.NajLicnostTable.Kolone.ImeIPrezime, najLicnost.getNaziv());
        values.put(NajLicnostDbSchema.NajLicnostTable.Kolone.DatumRodjenja, najLicnost.getDatumRodjenja().getTime());
        values.put(NajLicnostTable.Kolone.DatumRodjenjaString, najLicnost.getDatumRodjenjaString());
        values.put(NajLicnostDbSchema.NajLicnostTable.Kolone.MjestoRodjenja, najLicnost.getMjestoRodjenja());
        values.put(NajLicnostDbSchema.NajLicnostTable.Kolone.ZnacajniPodaci, najLicnost.getZnacajniPodaci());

        return values;
    }
}
