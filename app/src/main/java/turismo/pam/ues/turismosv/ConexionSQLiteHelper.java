package turismo.pam.ues.turismosv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by irvin on 06-17-18.
 */

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    String CREAR_TABLA_CATEGORIA = "CREATE TABLE categoria (idCategoria INTEGER PRIMARY KEY, nombre TEXT)";
    String CREAR_TABLA_SITIO = "CREATE TABLE sitio (idSitio INTEGER PRIMARY KEY, nombre TEXT, latitud REAL, longitud REAL, descripcion TEXT, imagen BLOB, idCategoria INTEGER, FOREIGN KEY (idCategoria) REFERENCES categoria(idCategoria))";

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        super.onOpen(db);
//        if (!db.isReadOnly()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                db.setForeignKeyConstraintsEnabled(true);
//            } else {
//                db.execSQL("PRAGMA foreign_keys=ON");
//            }
//        }
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys='ON'");
        //db.setForeignKeyConstraintsEnabled(true);
        db.execSQL(CREAR_TABLA_CATEGORIA);
        db.execSQL(CREAR_TABLA_SITIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS categoria");
        db.execSQL("DROP TABLE IF EXISTS sitio");
        onCreate(db);
    }
}
