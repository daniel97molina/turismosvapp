package turismo.pam.ues.turismosv;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private Button btnBuscarSitio;
    private Button btnBuscarCategoria;
    private Button btnFindByNameLike;
    private Button btnFindByLatitudLongitud;
    private TextView idSitio;
    private TextView nombreSitio;
    private TextView latitudSitio;
    private TextView longitudSitio;
    private TextView descripcionSitio;
    private TextView categoriaSitio;
    private ImageView imagenSitio;

    Typeface tf;
    SQLiteDatabase db;

    //Instancia de SitioService
    SitioService sitioService;

    public MainActivity(){
        //Manejo de la BD
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this, "DBTurismo", null, 1);
        db = conexion.getWritableDatabase();
        sitioService = new SitioService(db, getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                inputText = (EditText) findViewById(R.id.inputText);
        btnBuscarSitio = (Button) findViewById(R.id.btnBuscarSitio);
        btnBuscarCategoria = (Button) findViewById(R.id.btnBuscarCategoria);
        btnFindByNameLike = (Button) findViewById(R.id.btnFindByNameLike);
        btnFindByLatitudLongitud = (Button) findViewById(R.id.btnFindByLatitudLongitud);
        idSitio = (TextView) findViewById(R.id.idSitio);
        nombreSitio = (TextView) findViewById(R.id.nombreSitio);
        latitudSitio = (TextView) findViewById(R.id.latitudSitio);
        longitudSitio = (TextView) findViewById(R.id.longitudSitio);
        descripcionSitio = (TextView) findViewById(R.id.descripcionSitio);
        categoriaSitio = (TextView) findViewById(R.id.categoriaSitio);
        imagenSitio = (ImageView) findViewById(R.id.imagenSitio);

        // TODO tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + fontName;
        // TODO setTypeface(tf);



        //Insersion de datos a la base
        //sitioService.insertarCategorias();
        //sitioService.insertarSitios();


//        final ArrayList<Categoria> categorias = new ArrayList<Categoria>();
//        ListView lv = (ListView) findViewById(R.id.listview);
//        AdapterCategoria adapter = new AdapterCategoria(this, categorias);
//
//        lv.setAdapter(adapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, SitioActivity.class);
////                intent.putExtra("sitio_nombre", category.get(position).getTitle());
//                startActivity(intent);
//            }
//        });
//
//


        btnBuscarSitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sitio sitio = new Sitio();
                sitio = sitioService.findById(Integer.parseInt(inputText.getText().toString()));

                idSitio.setText(sitio.getIdSitio().toString());
                nombreSitio.setText(sitio.getNombre().toString());
                latitudSitio.setText(String.valueOf(sitio.getLatitud()));
                longitudSitio.setText(String.valueOf(sitio.getLongitud()));
                categoriaSitio.setText(sitioService.nombreCategoria(sitio.getIdCategoria()).toString());
                descripcionSitio.setText(sitio.getDescripcion().toString());
                imagenSitio.setImageBitmap(sitio.getImagen());

            }
        });
        btnBuscarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Sitio> listaSitios = new ArrayList<Sitio>();
                listaSitios = sitioService.findByCategoria(Integer.parseInt(inputText.getText().toString()));

                idSitio.setText(listaSitios.get(1).getIdSitio().toString());
                nombreSitio.setText(listaSitios.get(1).getNombre().toString());
                latitudSitio.setText(String.valueOf(listaSitios.get(1).getLatitud()));
                longitudSitio.setText(String.valueOf(listaSitios.get(1).getLongitud()));
                categoriaSitio.setText(sitioService.nombreCategoria(listaSitios.get(1).getIdCategoria()).toString());
                descripcionSitio.setText(listaSitios.get(1).getDescripcion().toString());
                imagenSitio.setImageBitmap(listaSitios.get(1).getImagen());

            }
        });
        btnFindByNameLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Sitio> listaSitios = new ArrayList<Sitio>();
                listaSitios = sitioService.findByNameLike(inputText.getText().toString());

                idSitio.setText(listaSitios.get(0).getIdSitio().toString());
                nombreSitio.setText(listaSitios.get(0).getNombre().toString());
                latitudSitio.setText(String.valueOf(listaSitios.get(0).getLatitud()));
                longitudSitio.setText(String.valueOf(listaSitios.get(0).getLongitud()));
                categoriaSitio.setText(sitioService.nombreCategoria(listaSitios.get(0).getIdCategoria()).toString());
                descripcionSitio.setText(listaSitios.get(0).getDescripcion().toString());
                imagenSitio.setImageBitmap(listaSitios.get(0).getImagen());

            }
        });
        btnFindByLatitudLongitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Sitio> listaSitios = new ArrayList<Sitio>();
                listaSitios = sitioService.findByLatitudLongitud("13.979577","-89.674207");

                idSitio.setText(listaSitios.get(0).getIdSitio().toString());
                nombreSitio.setText(listaSitios.get(0).getNombre().toString());
                latitudSitio.setText(String.valueOf(listaSitios.get(0).getLatitud()));
                longitudSitio.setText(String.valueOf(listaSitios.get(0).getLongitud()));
                categoriaSitio.setText(sitioService.nombreCategoria(listaSitios.get(0).getIdCategoria()).toString());
                descripcionSitio.setText(listaSitios.get(0).getDescripcion().toString());
                imagenSitio.setImageBitmap(listaSitios.get(0).getImagen());

            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent;
//        switch (item.getItemId()){
//            case R.id.barra_buscar:
//                intent = new Intent(this, BuscarActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.barra_ubicacion:
//                intent = new Intent(this, ListaSitiosActivity.class);
//                intent.putExtra("CodigoBusqueda",0);
//                //TODO llamar metodo de cercanos
//                startActivity(intent);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    public void redirigirBotones(View v){
        Intent intent = new Intent(this, ListaSitiosActivity.class);
        intent.putExtra("CodigoBusqueda",v.getTag().toString());
        startActivity(intent);
    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
