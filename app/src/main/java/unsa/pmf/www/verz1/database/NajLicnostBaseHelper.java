package unsa.pmf.www.verz1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import unsa.pmf.www.verz1.NajLicnost;
import unsa.pmf.www.verz1.database.NajLicnostDbSchema.NajLicnostTable;

/**
 * Created by acer on 17.9.2017..
 */

public class NajLicnostBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String IME_BAZE_PODATAKA = "najLicnostBaza.db";

    public NajLicnostBaseHelper(Context context)
    {
        super(context, IME_BAZE_PODATAKA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + NajLicnostTable.IME + "(" +
                "_id integer primary key autoincrement, " +
                NajLicnostTable.Kolone.UUID + ", " +
                NajLicnostTable.Kolone.ImeIPrezime + ", " +
                NajLicnostTable.Kolone.DatumRodjenja + ", " +
                NajLicnostTable.Kolone.DatumRodjenjaString + ", " +
                NajLicnostTable.Kolone.MjestoRodjenja + ", " +
                NajLicnostTable.Kolone.ZnacajniPodaci + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
