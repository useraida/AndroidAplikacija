package unsa.pmf.www.verz1.database;

/**
 * Created by acer on 2.8.2017..
 */

public class IgracDbSchema {
    public static final class IgracTable
    {
        // ime tabele
        public static final String IME = "igraci";

        // kolone tabele
        public static final class Kolone
        {
            public static final String UUID = "uuid";
            public static final String ImeIPrezime = "ime_i_prezime";
            public static final String MjestoRodjenja = "mjesto_rodjenja";
            public static final String DatumRodjenja = "datum_rodjenja";
            public static final String DatumUlaskaUKlub = "datum_ulaska_u_klub";
            public static final String PozicijaIgraca = "pozicija_igraca";
            public static final String PrethodniKlub = "prethodni_klub";
            public static final String Datum = "datum";
            public static final String PrviTim = "prvi_tim";
            public static final String DatumRodjenjaString = "datum_rodjenja_string";
            public static final String DatumUlaskaUKlubString = "datum_ulaska_u_klub_string";
            public static final String Slika = "slika";
        }
    }
}
