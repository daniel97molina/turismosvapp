package turismo.pam.ues.turismosv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Categoria> category = new ArrayList<Categoria>();
        category.add(new Categoria("1","cat1","descripcion1",
                getResources().getDrawable(R.drawable.ic_map_black_24dp)));
        category.add(new Categoria("2","categoria 2 nombre","descripcion1 larga gran dfescripcion",
                getResources().getDrawable(R.drawable.ic_map_black_24dp)));
        ListView lv = (ListView) findViewById(R.id.listview);

        AdapterItem adapter = new AdapterItem(this, category);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SitioActivity.class);
                intent.putExtra("sitio_nombre", category.get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
