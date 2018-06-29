package turismo.pam.ues.turismosv;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ListaSitiosActivity extends AppCompatActivity {

    SQLiteDatabase db;

    //Instancia de SitioService
    SitioService sitioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sitios);

        //Manejo de la BD
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this, "DBTurismo", null, 1);
        db = conexion.getWritableDatabase();
        sitioService = new SitioService(db, getApplicationContext());

        Intent intent = getIntent();
        TextView txtTitulo = (TextView) findViewById(R.id.tituloSitios);

        int codigoBusqueda = intent.getIntExtra("CodigoBusqueda",1);


        Log.d("CODIGO BUSQUEDA",String.valueOf(codigoBusqueda));
        System.out.println("CODIGO BUSQUEDA");
        System.out.println(codigoBusqueda);
        SitioService service = sitioService;
        List listaPrevia;
        switch (codigoBusqueda){
            case 0:
                txtTitulo.setText("Sitios Cercanos");
                //TODO cercanos
                listaPrevia = service.findByCategoria(1);
                break;
            case -1:
                txtTitulo.setText("Resultados de búsqueda");
                //TODO por nombre
                listaPrevia = service.findByCategoria(1);
                break;
            default:
                txtTitulo.setText(sitioService.nombreCategoria(codigoBusqueda));
                listaPrevia = service.findByCategoria(codigoBusqueda);
                break;
        }


        final List<Sitio> sitios = listaPrevia;

        ListView lv = (ListView) findViewById(R.id.listaSitios);

        AdapterSitio adapter = new AdapterSitio(this, sitios);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaSitiosActivity.this, SitioActivity.class);
                intent.putExtra("idSitio", sitios.get(position).getIdSitio());
                startActivity(intent);
            }
        });

    }


}
