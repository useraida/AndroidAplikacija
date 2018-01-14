package unsa.pmf.www.verz1;

import java.util.Date;
import java.util.UUID;

/**
 * Created by acer on 17.9.2017..
 */

public class NajLicnost {
    private UUID mId;
    private String mNaziv;
  //  private String mDatumRodjenja;
    private Date mDatumRodjenja;
    private String mDatumRodjenjaString;
    private String mMjestoRodjenja;
    private String mZnacajniPodaci;

    public NajLicnost(){
        this(UUID.randomUUID());
    }

    public NajLicnost(UUID id)
    {
        mId = id;
        mDatumRodjenja = new Date();
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

  /*  public String getDatumRodjenja() {
        return mDatumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        mDatumRodjenja = datumRodjenja;
    }*/

    public String getMjestoRodjenja() {
        return mMjestoRodjenja;
    }

    public void setMjestoRodjenja(String mjestoRodjenja) {
        mMjestoRodjenja = mjestoRodjenja;
    }

    public String getZnacajniPodaci() {
        return mZnacajniPodaci;
    }

    public void setZnacajniPodaci(String znacajniPodaci) {
        mZnacajniPodaci = znacajniPodaci;
    }

    public Date getDatumRodjenja() {
        return mDatumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        mDatumRodjenja = datumRodjenja;
    }

    public String getDatumRodjenjaString() {
        return mDatumRodjenjaString;
    }

    public void setDatumRodjenjaString(String datumRodjenjaString) {
        mDatumRodjenjaString = datumRodjenjaString;
    }
}
