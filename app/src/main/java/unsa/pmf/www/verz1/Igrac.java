package unsa.pmf.www.verz1;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by acer on 26.7.2017..
 */

public class Igrac {
    private UUID mId;
    private String mNaziv;
    private String mDatumRodjenja;
    private String mMjestoRodjenja;
    private String mDatumUlaskaUKlub;
    private String mPozicijaIgraca;
    private String mPrethodniKlub;
    private Date mDatum;
    private boolean mPrviTim;  // prvi tim

    public Igrac(){
        this(UUID.randomUUID());
    }

    public Igrac(UUID id)
    {
        mId = id;
        mDatum = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getNaziv() {
        return mNaziv;
    }

    public void setNaziv(String naziv) {
        mNaziv = naziv;
    }

    public String getDatumRodjenja() {
        return mDatumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        mDatumRodjenja = datumRodjenja;
    }

    public String getMjestoRodjenja() {
        return mMjestoRodjenja;
    }

    public void setMjestoRodjenja(String mjestoRodjenja) {
        mMjestoRodjenja = mjestoRodjenja;
    }

    public String getDatumUlaskaUKlub() {
        return mDatumUlaskaUKlub;
    }

    public void setDatumUlaskaUKlub(String datumUlaskaUKlub) {
        mDatumUlaskaUKlub = datumUlaskaUKlub;
    }

    public String getPozicijaIgraca() {
        return mPozicijaIgraca;
    }

    public void setPozicijaIgraca(String pozicijaIgraca) {
        mPozicijaIgraca = pozicijaIgraca;
    }

    public String getPrethodniKlub() {
        return mPrethodniKlub;
    }

    public void setPrethodniKlub(String prethodniKlub) {
        mPrethodniKlub = prethodniKlub;
    }

    public Date getDatum() {
        return mDatum;
    }

    public void setDatum(Date datum) {
        mDatum = datum;
    }

    public boolean isPrviTim() {
        return mPrviTim;
    }

    public void setPrviTim(boolean prviTim) {
        mPrviTim = prviTim;
    }
}
