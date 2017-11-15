package unsa.pmf.www.verz1.database;

/**
 * Created by acer on 17.9.2017..
 */

public class NajLicnostDbSchema {
    public static final class NajLicnostTable
    {
        public static final String IME = "naj_licnosti";

        public static final class Kolone
        {
            public static final String UUID = "uuid";
            public static final String ImeIPrezime = "ime_i_prezime";
            public static final String DatumRodjenja = "datum_rodjenja";
            public static final String MjestoRodjenja = "mjesto_rodjenja";
            public static final String ZnacajniPodaci = "znacajni_podaci";
        }
    }
}
