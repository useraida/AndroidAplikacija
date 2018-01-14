package unsa.pmf.www.verz1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import unsa.pmf.www.verz1.database.IgracDbSchema.IgracTable;

/**
 * Created by acer on 2.8.2017..
 */

/*
    SQLiteOpenHelper provjerava da li baza vec postoji, ukoliko ne postoji kreira je i kreira tabele kao i pocetne podatke
    ako vec postoji baza, otvara je i provjerava koju verziju ima IgracDbSchema
    ako je stara verzija, azurira novu
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
                IgracTable.Kolone.MjestoRodjenja + ", " +
                IgracTable.Kolone.DatumRodjenja + ", " +
                IgracTable.Kolone.DatumUlaskaUKlub + ", " +
                IgracTable.Kolone.PozicijaIgraca + ", " +
                IgracTable.Kolone.PrethodniKlub + ", " +
                IgracTable.Kolone.Datum + ", " +
                IgracTable.Kolone.PrviTim + ", " +
                IgracTable.Kolone.DatumRodjenjaString + ", " +
                IgracTable.Kolone.DatumUlaskaUKlubString + ", " +
                IgracTable.Kolone.Slika + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
