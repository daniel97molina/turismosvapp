package turismo.pam.ues.turismosv;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danm on 06-28-18.
 */

public class AdapterSitio extends BaseAdapter{


        protected Activity activity;
        protected List<Sitio> items;

        public AdapterSitio(Activity activity, List<Sitio> items) {
        this.activity = activity;
        this.items = items;
    }

        @Override
        public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Sitio> lista) {
        for (int i = 0; i < lista.size(); i++) {
            items.add(lista.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.listasitio, null);
        }


            Sitio s = items.get(position);

            TextView title = (TextView) v.findViewById(R.id.txtTituloSitio);
            title.setText(s.getNombre());

            TextView description = (TextView) v.findViewById(R.id.txtDescripcionSitio);
            String desc = s.getDescripcion().toString();
            if(desc.length()>170){
                desc = desc.substring(0,169);
                desc = desc+"...";
            }
            description.setText(desc);

            ImageView imagen = (ImageView) v.findViewById(R.id.imgSitio);
            imagen.setImageBitmap(s.getImagen());

        return v;
    }
}
