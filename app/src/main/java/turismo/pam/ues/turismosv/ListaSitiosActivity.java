package turismo.pam.ues.turismosv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListaSitiosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sitios);

        Intent intent = getIntent();
        TextView txtTitulo = (TextView) findViewById(R.id.tituloSitios);

        int codigoBusqueda = intent.getIntExtra("codigoBusqueda",1);
        String nombreCategoria = intent.getStringExtra("nombreCategoria");

        MainActivity ma = new MainActivity();
        SitioService service = ma.sitioService;
        List listaPrevia;
        switch (codigoBusqueda){
            case 0:
                txtTitulo.setText("Sitios Cercanos");
                listaPrevia = service.findByCategoria(1);
                break;
            case -1:
                txtTitulo.setText("Resultados de búsqueda");
                listaPrevia = service.findByCategoria(1);
                break;
            default:
                txtTitulo.setText(nombreCategoria);
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
                intent.putExtra("sitio_nombre", sitios.get(position).getNombre());
                startActivity(intent);
            }
        });
    }


}
