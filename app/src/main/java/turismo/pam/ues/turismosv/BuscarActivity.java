package turismo.pam.ues.turismosv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BuscarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
    }

    public void redirigirBotones(View v){
        TextView tv = (TextView)findViewById(R.id.txtBuscar);
        String texto = tv.getText().toString();
        if(texto == null || texto.isEmpty()){
            Toast.makeText(getApplicationContext(), "Ingrese el nombre", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this, ListaSitiosActivity.class);
            intent.putExtra("CodigoBusqueda", -1);

            intent.putExtra("Nombre", texto);
            startActivity(intent);
        }
    }
}
