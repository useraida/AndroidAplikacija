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
        // getWritableDatabase() radi sljedece: otvara data/data/unsa.pmf.wwww.verz1/databases/IgracdbSchema
        // kreirajuci novi database file ako ne postoji
        // ako je baza prvi put kreirana, poziva onCreate(SQLiteDatabase), i zatim sacuva zadnju verziju
        // ako baza nije prvi put kreirana, onda provjerava verziju u bazi. Ako je verzija u IgracBaseHelper visa,
        // pozovi onUpgrade(SQLiteDatabase, int, int)
        mBazaPodataka = new IgracBaseHelper(mContext).getWritableDatabase();
    }

    public void dodajIgraca(Igrac i)
    {
        ContentValues values = getContentValues(i);
        // null parametar:
        // ukoliko odlucimo da pozovemo insert sa praznim ContentValues(), SQLite nece to dozvoliti, tako da insert() moze pasti
        // ako proslijedimo vrijednost uuid-a za nullColumnHack, to bi ignorisalo prazne ContentValues. Umjesto toga,
        // proslijedio bi u ContentValues sa uuid postavljenim na null. To bi omogucilo da insert (...) uspije i kreira novi red
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
        // treci argument od upadte predstavlja where clause, jer se mora znati koji se red tacno azurira
        mBazaPodataka.update(IgracTable.IME, values, IgracTable.Kolone.UUID + " = ?", new String[] {uuidString});
    }


    private IgracCursorWrapper queryIgraci(String whereClause, String[] whereArgs)
    {
        // citanje podataka iz baze vrsi se pomocu guery-a
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
        values.put(IgracTable.Kolone.MjestoRodjenja, igrac.getMjestoRodjenja());
        values.put(IgracTable.Kolone.DatumRodjenja, igrac.getDatumRodjenja().getTime());
        values.put(IgracTable.Kolone.DatumUlaskaUKlub, igrac.getDatumUlaskaUKlub().getTime());
        values.put(IgracTable.Kolone.PozicijaIgraca, igrac.getPozicijaIgraca());
        values.put(IgracTable.Kolone.PrethodniKlub, igrac.getPrethodniKlub());
        values.put(IgracTable.Kolone.PrviTim, igrac.isPrviTim() ? 1 : 0);
        values.put(IgracTable.Kolone.DatumRodjenjaString, igrac.getDatumRodjenjaString());
        values.put(IgracTable.Kolone.DatumUlaskaUKlubString, igrac.getDatumUlaskaUKlubString());
        values.put(IgracTable.Kolone.Slika, igrac.getSlika());

        return values;
    }
}
