package kz.eugales.re4.helper;

/**
 * Created by Adil on 05.08.2016.
 */
public class Constants {

    public static final class HTTP {
        //public static final String BASE_URL = "http://192.168.100.5:8080/WebServices-1/";//10.0.2.2:8080
        public static final String BASE_URL = "http://192.168.100.5:8084/WebServices/";
        public static final String API_KEY = "";
        public static final String TOKEN = "";
    }

    public static final class DATABASE {
        public static final String DB_NAME = "companies";
        public static final String DB_TABLE_COMPANY = "company";
        public static final String DB_TABLE_PHONE = "phone";
        public static final int DB_VERSION = 1;

        public static final String COMPANY_ID = "companyId";
        public static final String COMPANY_NAME = "companyName";
        public static final String COMPANY_SUBJECT = "companySubject";
        public static final String COMPANY_PHOTO_URL = "companyPhotoUrl";
        public static final String COMPANY_PHOTO_BITMAP = "companyPhotoBitmap";
        public static final String PHONE_ID = "phoneId";
        public static final String PHONE_NAME = "phoneName";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String PHOTO_URL = "photoUrl";
        public static final String PHOTO_BITMAP = "photoBitmap";

        public static final String DROP_TABLES = "DROP TABLE IF EXISTS " + DB_TABLE_COMPANY + "," + DB_TABLE_PHONE;
        public static final String CREATE_TABLE_COMPANY = "CREATE TABLE " + DB_TABLE_COMPANY +
                "(" + COMPANY_ID + " INTEGER PRIMARY KEY not null," +
                COMPANY_NAME + " TEXT not null, " +
                COMPANY_SUBJECT + " TEXT not null," +
                COMPANY_PHOTO_URL + " TEXT, " +
                COMPANY_PHOTO_BITMAP + " BLOB);";

        public static final String CREATE_TABLE_PHONE = "CREATE TABLE " + DB_TABLE_PHONE +
                "(" + PHONE_ID + " INTEGER PRIMARY KEY not null," +
                PHONE_NAME + " TEXT not null, " +
                PHONE_NUMBER + " TEXT not null, " +
                COMPANY_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COMPANY_ID + ") REFERENCES " + DB_TABLE_PHONE + "(" + COMPANY_ID + "));";


        public static final String FETCH_COMPANIES = "SELECT * FROM " + DB_TABLE_COMPANY;

        public static final String FETCH_PHONES_BY_COMPANY_ID = "SELECT * FROM " + DB_TABLE_PHONE +
                " WHERE " + COMPANY_ID + "=?";


    }

    public static final class REFERENCE {
        public static final String COMPANY = CONFIG.PACKAGE_NAME + "COMPANY";
    }

    public static final class CONFIG {
        public static final String APP_NAME = "RE";
        public static final String PACKAGE_NAME = "kz.eugales.re4";
        public static final String PREF_NAME = "prefs";
        public static final String PREF_HASH = "HASH_VALUE";
        public static final String PREF_PHONE_NUMBER= "PHONE_NUMBER";
        public static final String PREF_NOT_EXIST= "PREF_NOT_EXIST";
        public static final String PREF_REAL_ESTATE = "REAL_ESTATE";
        public static final String PREF_HOME = "HOME";
        public static final String PREF_PORCH = "PORCH";
        public static final String PREF_FLAT = "FLAT";

        public final static String ERROR_JSON = "error";
        public final static String VALUE_JSON = "value";
        public final static String SUCCESS = "0";

    }

}
