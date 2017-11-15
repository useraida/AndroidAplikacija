package unsa.pmf.www.verz1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import unsa.pmf.www.verz1.database.IgracDbSchema.IgracTable;

/**
 * Created by acer on 2.8.2017..
 */

public class IgracBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String IME_BAZE_PODATAKA = "igracBaza.db";

    public IgracBaseHelper(Context context)
    {
        super(context, IME_BAZE_PODATAKA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + IgracTable.IME + "(" +
                "_id integer primary key autoincrement, " +
                IgracTable.Kolone.UUID + ", " +
                IgracTable.Kolone.ImeIPrezime + ", " +
                IgracTable.Kolone.DatumRodjenja + ", " +
                IgracTable.Kolone.MjestoRodjenja + ", " +
                IgracTable.Kolone.DatumUlaskaUKlub + ", " +
                IgracTable.Kolone.PozicijaIgraca + ", " +
                IgracTable.Kolone.PrethodniKlub + ", " +
                IgracTable.Kolone.Datum + ", " +
                IgracTable.Kolone.PrviTim + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
