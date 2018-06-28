package turismo.pam.ues.turismosv;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SitioActivity extends AppCompatActivity {

    SQLiteDatabase db;

    //Instancia de SitioService
    SitioService sitioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitio);

        //Manejo de la BD
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this, "DBTurismo", null, 1);
        db = conexion.getWritableDatabase();
        sitioService = new SitioService(db, getApplicationContext());

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("sitio_nombre");

        TextView sitio_nombre = findViewById(R.id.sitio_nombre);
        TextView sitio_descripcion = findViewById(R.id.sitio_descripcion);
        sitio_descripcion.setText("La activity nueva incluye un archivo de diseño en blanco, por lo que ahora agregarás una vista de texto en la que aparecerá el mensaje.");
        sitio_nombre.setText(nombre);
    }


}
