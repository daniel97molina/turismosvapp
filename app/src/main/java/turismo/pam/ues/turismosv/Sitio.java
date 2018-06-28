package turismo.pam.ues.turismosv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Sitio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitio);

        Intent intent = getIntent();
        String message = intent.getStringExtra("sitio_nombre");

        TextView sitio_nombre = findViewById(R.id.sitio_nombre);
        TextView sitio_descripcion = findViewById(R.id.sitio_descripcion);
        sitio_descripcion.setText("La activity nueva incluye un archivo de diseño en blanco, por lo que ahora agregarás una vista de texto en la que aparecerá el mensaje.");
        sitio_nombre.setText(message);
    }


}
