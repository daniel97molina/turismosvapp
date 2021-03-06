package turismo.pam.ues.turismosv;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SitioActivity extends AppCompatActivity {

    SQLiteDatabase db;

    //Instancia de SitioService
    SitioService sitioService;
    Sitio sitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitio);

        //Manejo de la BD
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this, "DBTurismo", null, 1);
        db = conexion.getWritableDatabase();
        sitioService = new SitioService(db, getApplicationContext());

        Intent intent = getIntent();
        int idSitio = intent.getIntExtra("idSitio",1);

        sitio = sitioService.findById(idSitio);

        TextView sitio_nombre = findViewById(R.id.sitio_nombre);
        TextView sitio_descripcion = findViewById(R.id.sitio_descripcion);
        ImageView img = (ImageView) findViewById(R.id.sitio_imagen);
        sitio_nombre.setText(sitio.getNombre());
        sitio_descripcion.setText(sitio.getDescripcion().toString());
        img.setImageBitmap(sitio.getImagen());

        final List<Sitio> sitios = sitioService.findByRelacionados(sitio.getIdSitio());

        ListView lv = (ListView) findViewById(R.id.sitio_relacionados);
        AdapterSitio adapter = new AdapterSitio(this, sitios);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SitioActivity.this, SitioActivity.class);
                intent.putExtra("idSitio", sitios.get(position).getIdSitio());
                startActivity(intent);
            }
        });

    }

    public void mapa(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitud",sitio.getLatitud());
        intent.putExtra("longitud",sitio.getLongitud());
        intent.putExtra("nombre",sitio.getNombre());
        startActivity(intent);
    }


}
