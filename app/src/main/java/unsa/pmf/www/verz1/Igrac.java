package unsa.pmf.www.verz1;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by acer on 26.7.2017..
 */

public class Igrac {
    private UUID mId;
    private String mNaziv;
    private Date mDatumRodjenja;  // uzima iz datumPickera datum kao dan/mjesec/godina
    private Date mDatumUlaskaUKlub;
    private String mMjestoRodjenja;
    private String mPozicijaIgraca;
    private String mPrethodniKlub;
    private boolean mPrviTim;  // prvi tim
    private byte[] mSlika;
    private String mDatumRodjenjaString; // sluzi da se datum rodjenja igraca prikaze kao /day/month/year bez gmt-a i bez vremena
    private String mDatumUlaskaUKlubString; // sluzi da se datum ulaska igraca u klub prikaze kao /day/month/year bez gmt-a i bez vremena

    public Igrac(){
        this(UUID.randomUUID());
    }

    public Igrac(UUID id)
    {
        mId = id;
        mDatumRodjenja = new Date();  // inicijalizira na trenutni datum
        mDatumUlaskaUKlub = new Date();
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

    public String getMjestoRodjenja() {
        return mMjestoRodjenja;
    }

    public void setMjestoRodjenja(String mjestoRodjenja) {
        mMjestoRodjenja = mjestoRodjenja;
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

    public Date getDatumRodjenja() {
        return mDatumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        mDatumRodjenja = datumRodjenja;
    }

    public boolean isPrviTim() {
        return mPrviTim;
    }

    public void setPrviTim(boolean prviTim) {
        mPrviTim = prviTim;
    }

    public byte[] getSlika() {
        return mSlika;
    }

    public void setSlika(byte[] slika) {
        mSlika = slika;
    }

    public String getDatumRodjenjaString() {
        return mDatumRodjenjaString;
    }

    public void setDatumRodjenjaString(String datumRodjenjaString) {
        mDatumRodjenjaString = datumRodjenjaString;
    }

    public Date getDatumUlaskaUKlub() {
        return mDatumUlaskaUKlub;
    }

    public void setDatumUlaskaUKlub(Date datumUlaskaUKlub) {
        mDatumUlaskaUKlub = datumUlaskaUKlub;
    }

    public String getDatumUlaskaUKlubString() {
        return mDatumUlaskaUKlubString;
    }

    public void setDatumUlaskaUKlubString(String datumUlaskaUKlubString) {
        mDatumUlaskaUKlubString = datumUlaskaUKlubString;
    }
}
