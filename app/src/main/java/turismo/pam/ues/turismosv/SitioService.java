package turismo.pam.ues.turismosv;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SitioService extends AppCompatActivity {
    SQLiteDatabase db;

    private Context contexto;

    public SitioService(Context contexto) {
        this.contexto = contexto;
    }

    public SitioService(SQLiteDatabase db, Context contexto) {
        this.db = db;
        this.contexto = contexto;
    }

    public Sitio findById(int idSitio) {
        String[] parametros = new String[]{String.valueOf(idSitio)};
        String[] campos = new String[]{"idSitio", "nombre", "latitud", "longitud", "descripcion", "imagen", "idCategoria"};

        try {
            Cursor cursor = db.query("sitio", campos, "idSitio=?", parametros, null, null, null);

            if (cursor.moveToFirst()) {
                byte[] blob = cursor.getBlob(5);
                Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);

                Sitio sitio = new Sitio(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4), bmp, cursor.getInt(6));
                return sitio;

            } else {
                Toast.makeText(this.contexto, "Sitio no encontrado", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(this.contexto, "Error al buscar el sitio", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public List<Sitio> findByCategoria(int idCategoria) {
        String[] parametros = new String[]{String.valueOf(idCategoria)};
        String[] campos = new String[]{"idSitio", "nombre", "latitud", "longitud", "descripcion", "imagen", "idCategoria"};

        try {
            ArrayList<Sitio> list = new ArrayList<Sitio>();
            Cursor cursor = db.query("sitio", campos, "idCategoria=?", parametros, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    byte[] blob = cursor.getBlob(5);
                    Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                    Sitio sitio = new Sitio(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4), bmp, cursor.getInt(6));

                    list.add(sitio);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this.contexto, "Categoria no encontrada", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (cursor != null && !cursor.isClosed()) {//Se cierra el cursor si no está cerrado ya
                cursor.close();
            }

            return list;

        } catch (Exception e) {
            Toast.makeText(this.contexto, "Error al buscar la categoria", Toast.LENGTH_SHORT).show();
            return Collections.emptyList();
        }
    }

    public List<Sitio> findByNameLike(String texto) {
        String[] parametros = new String[]{"%" + texto + "%"};

        try {
            ArrayList<Sitio> list = new ArrayList<Sitio>();
            Cursor cursor = db.rawQuery("SELECT * FROM sitio WHERE nombre LIKE ?", parametros);

            if (cursor.moveToFirst()) {
                do {
                    byte[] blob = cursor.getBlob(5);
                    Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                    Sitio sitio = new Sitio(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4), bmp, cursor.getInt(6));

                    list.add(sitio);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this.contexto, "No se encontro ningún sitio", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (cursor != null && !cursor.isClosed()) {//Se cierra el cursor si no está cerrado ya
                cursor.close();
            }

            return list;

        } catch (Exception e) {
            Toast.makeText(this.contexto, "Error al buscar el sitio", Toast.LENGTH_SHORT).show();
            return Collections.emptyList();
        }
    }

    public List<Sitio> findByLatitudLongitud(String latitud, String longitud) {
        String[] parametros = new String[]{latitud, longitud};

        try {
            ArrayList<Sitio> list = new ArrayList<Sitio>();
            Cursor cursor = db.rawQuery("SELECT * FROM sitio WHERE latitud <= ? AND longitud <= ?", parametros);

            if (cursor.moveToFirst()) {
                do {
                    byte[] blob = cursor.getBlob(5);
                    Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                    Sitio sitio = new Sitio(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4), bmp, cursor.getInt(6));

                    list.add(sitio);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this.contexto, "No se encontro ningún sitio", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (cursor != null && !cursor.isClosed()) {//Se cierra el cursor si no está cerrado ya
                cursor.close();
            }

            return list;

        } catch (Exception e) {
            Toast.makeText(this.contexto, "Error al buscar el sitio", Toast.LENGTH_SHORT).show();
            return Collections.emptyList();
        }
    }

    public String nombreCategoria(int idCategoria) {
        String[] parametros = new String[]{String.valueOf(idCategoria)};
        String[] campos = new String[]{"nombre"};

        try {
            Cursor cursor = db.query("categoria", campos, "idCategoria=?", parametros, null, null, null);

            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            } else {
                return "Categoria no encontrada";
            }
        } catch (Exception e) {
            Toast.makeText(this.contexto, "Error al buscar la categoria", Toast.LENGTH_SHORT).show();
            return "Categoria no encontrada";
        }
    }

    public byte[] convertidor(String url) {
        try {

            int imageResource = this.contexto.getResources().getIdentifier(url, null, this.contexto.getPackageName());
            Drawable imagen = ContextCompat.getDrawable(this.contexto, imageResource);

            Bitmap bitmap = ((BitmapDrawable) imagen).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitMapData = stream.toByteArray();

            return bitMapData;

        } catch (Exception e) {
            Toast.makeText(this.contexto, "Error al leer y convertir la imagen", Toast.LENGTH_SHORT).show();
        }
        return new byte[0];
    }

    public void insertarCategorias() {
        //Insertar Categoria 1
        ContentValues nuevaCategoria = new ContentValues();
        nuevaCategoria.put("idCategoria", 1);
        nuevaCategoria.put("nombre", "Sitio Arqueológico");
        db.insert("categoria", null, nuevaCategoria);

        //Insertar Categoria 2
        ContentValues nuevaCategoria2 = new ContentValues();
        nuevaCategoria2.put("idCategoria", 2);
        nuevaCategoria2.put("nombre", "Sitio Turístico");
        db.insert("categoria", null, nuevaCategoria2);

        //Insertar Categoria 3
        ContentValues nuevaCategoria3 = new ContentValues();
        nuevaCategoria3.put("idCategoria", 3);
        nuevaCategoria3.put("nombre", "Parque Nacional");
        db.insert("categoria", null, nuevaCategoria3);

        //Insertar Categoria 4
        ContentValues nuevaCategoria4 = new ContentValues();
        nuevaCategoria4.put("idCategoria", 4);
        nuevaCategoria4.put("nombre", "Playa");
        db.insert("categoria", null, nuevaCategoria4);

        //Insertar Categoria 5
        ContentValues nuevaCategoria5 = new ContentValues();
        nuevaCategoria5.put("idCategoria", 5);
        nuevaCategoria5.put("nombre", "Pueblo Vivo");
        db.insert("categoria", null, nuevaCategoria5);
    }

    public void insertarSitios() {
        //Insertar Sitio 1
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("idSitio", 1);
        nuevoRegistro.put("nombre", "El Tazumal");
        nuevoRegistro.put("latitud", 13.979577);
        nuevoRegistro.put("longitud", -89.674207);
        nuevoRegistro.put("idCategoria", 1);
        byte[] byteImagen = convertidor("@mipmap/tazumal");
        nuevoRegistro.put("imagen", byteImagen);
        nuevoRegistro.put("descripcion", "El Tazumal está ubicada en el corazón del municipio de Chalchuapa, departamento de Santa Ana, El Salvador a ochenta kilómetros al occidente de la capital. Esta zona está dentro del área arqueológica de Chalchuapa, cuya superficie aproximada es de 10 km² y donde también se localizan los sitios arqueológicos de Pampe, Casa Blanca, El Trapiche y Las Victorias. Chalchuapa fue un sitio que recibió la influencia de Copán y la influencia teotihuacana y tolteca.\n\n" +
                "Tazumal comprende una serie de estructuras que fueron el escenario de un importante y sofisticado asentamiento maya que existió alrededor de los años 100 - 1200 DC. Los restos incluyen sistemas de drenaje de aguas, tumbas, pirámides y templos. Su mayor desarrollo corresponde al horizonte Clásico (250 a 900 d. C.). Desde el 900 D.C. Se construyeron una pirámide de estilo Tolteca, un juego de pelota, entre otras. Alrededor del año 1,200 D.C. fue abandonado definitivamente.");
        db.insert("sitio", null, nuevoRegistro);

        //Insertar Sitio 2
        ContentValues nuevoRegistro2 = new ContentValues();
        nuevoRegistro2.put("idSitio", 2);
        nuevoRegistro2.put("nombre", "Casa Blanca");
        nuevoRegistro2.put("latitud", 13.988027);
        nuevoRegistro2.put("longitud", -89.671336);
        nuevoRegistro2.put("idCategoria", 1);
        byte[] byteImagen2 = convertidor("@mipmap/casablanca");
        nuevoRegistro2.put("imagen", byteImagen2);
        nuevoRegistro2.put("descripcion", "Casa Blanca es un sitio arqueológico ubicado en la zona arqueológica de Chalchuapa, en el municipio homónimo, Departamento de Santa Ana, zona occidental de El Salvador. Su nombre es dado por la hacienda cafetalera que funcionaba en el actual parque arqueológico.\n\n" +
                "Las investigaciones arqueológicas lo sitúan entre el período preclásico (500 a. C. - 250 d. C.) y el período clásico (250 d. C. - 900 d. C.). Su proximidad con Guatemala facilitó mucho el comercio de cerámica, obsidiana y otros productos con Kaminaljuyú y fue influenciado por los olmecas y Teotihuacan. Tiene una gran relación con los sitios locales Tazumal, El Trapiche y Laguna Cuzcachapa, entre otros.");
        db.insert("sitio", null, nuevoRegistro2);

        //Insertar Sitio 3
        ContentValues nuevoRegistro3 = new ContentValues();
        nuevoRegistro3.put("idSitio", 3);
        nuevoRegistro3.put("nombre", "Museo Regional de Occidente");
        nuevoRegistro3.put("latitud", 13.993551);
        nuevoRegistro3.put("longitud", -89.557331);
        nuevoRegistro3.put("idCategoria", 2);
        byte[] byteImagen3 = convertidor("@mipmap/museosa");
        nuevoRegistro3.put("imagen", byteImagen3);
        nuevoRegistro3.put("descripcion", "El Museo Regional de Occidente es el principal recinto de su clase en la ciudad salvadoreña de Santa Ana. Inaugurado en febrero de 1999, está ubicado en el edificio donde tenía asiento el Banco de Central de Reserva de la ciudad, y es administrado por la Secretaría de Cultura de El Salvador.\n\n" +
                "Cuenta con una gran colección de piezas arqueológicas y de historia contemporánea de los departamentos de Santa Ana, Ahuachapán y Sonsonate. Asimismo, dispone de salas temporales, biblioteca, cafetería y un exposición permanente de la Historia de La Moneda en El Salvador.");
        db.insert("sitio", null, nuevoRegistro3);

        //Insertar Sitio 4
        ContentValues nuevoRegistro4 = new ContentValues();
        nuevoRegistro4.put("idSitio", 4);
        nuevoRegistro4.put("nombre", "Catedral de Nuestra Señora Santa Ana");
        nuevoRegistro4.put("latitud", 13.995068);
        nuevoRegistro4.put("longitud", -89.555946);
        nuevoRegistro4.put("idCategoria", 2);
        byte[] byteImagen4 = convertidor("@mipmap/catedralsa");
        nuevoRegistro4.put("imagen", byteImagen4);
        nuevoRegistro4.put("descripcion", "La Catedral de la Señora Santa Ana, es la iglesia principal de la diócesis católica de Santa Ana, en la ciudad de Santa Ana, El Salvador. Este templo tiene la advocación de la Señora Santa Ana, la madre de la Bienaventurada Virgen María.\n\n" +
                "La edificación fue diseñada como catedral neogótica, en contraste con el estilo colonial español de la mayor parte de las catedrales de El Salvador y el resto de América latina. Está formada por tres naves, las cuales son de las medidas siguientes: la nave central con 22 metros de largo y 22 metros de ancho, las naves laterales miden 2 metros de largo y ocho metros de ancho; en conjunto las tres naves forman una cruz.");
        db.insert("sitio", null, nuevoRegistro4);

        //Insertar Sitio 5
        ContentValues nuevoRegistro5 = new ContentValues();
        nuevoRegistro5.put("idSitio", 5);
        nuevoRegistro5.put("nombre", "Teatro de Santa Ana");
        nuevoRegistro5.put("latitud", 13.995293);
        nuevoRegistro5.put("longitud", -89.556716);
        nuevoRegistro5.put("idCategoria", 2);
        byte[] byteImagen5 = convertidor("@mipmap/teatrosa");
        nuevoRegistro5.put("imagen", byteImagen5);
        nuevoRegistro5.put("descripcion", "El Teatro de Santa Ana es el principal centro para la representación de las artes escénicas de la ciudad salvadoreña de Santa Ana. Su construcción inició en 1902, terminándose en 1910. Hasta el año 2009 fue administrado por la Asociación del Patrimonio Cultural de Santa Ana (Acapulsa) y en la actualidad es parte de la Secretaría de Cultura de El Salvador.\n\n" +
                "El teatro cuenta con varias áreas, las cuales son el vestíbulo, el Salón Foyer, la Gran Sala, el Escenario, los Palcos y la Terraza Española. También posee detalles arquitectónicos labrados con madera de árboles de caoba, adornos hechos de yeso y pinturas de artistas italianos.");
        db.insert("sitio", null, nuevoRegistro5);

        //Insertar Sitio 6
        ContentValues nuevoRegistro6 = new ContentValues();
        nuevoRegistro6.put("idSitio", 6);
        nuevoRegistro6.put("nombre", "Sihuatehuacán");
        nuevoRegistro6.put("latitud", 13.992233);
        nuevoRegistro6.put("longitud", -89.542712);
        nuevoRegistro6.put("idCategoria", 2);
        byte[] byteImagen6 = convertidor("@mipmap/sihua");
        nuevoRegistro6.put("imagen", byteImagen6);
        nuevoRegistro6.put("descripcion", "Sihuatehuacán cuenta con importantes recursos naturales, los cuales han servido de base para el desarrollo de una infraestructura y una serie de actividades de tipo cultural, deportiva y recreativa para el servicio de las familias salvadoreñas.\n\n" +
                "Además de contar con piscinas para niños y adultos, el turicentro cuenta con cuatro canchas de tennis, pistas de patinaje, instalación de juegos infantiles y ambientación general.");
        db.insert("sitio", null, nuevoRegistro6);

        //Insertar Sitio 7
        ContentValues nuevoRegistro7 = new ContentValues();
        nuevoRegistro7.put("idSitio", 7);
        nuevoRegistro7.put("nombre", "Parque Nacional Cerro Verde");
        nuevoRegistro7.put("latitud", 13.826705);
        nuevoRegistro7.put("longitud", -89.624315);
        nuevoRegistro7.put("idCategoria", 3);
        byte[] byteImagen7 = convertidor("@mipmap/cerroverde");
        nuevoRegistro7.put("imagen", byteImagen7);
        nuevoRegistro7.put("descripcion", "El Cerro Verde o Cuntetepeque​ es un volcán extinto ubicado en el Departamento de Santa Ana, El Salvador, en la cordillera de Apaneca. Tiene una altura de 2030 msnm y su cráter se encuentra erosionado y cubierto por un espeso bosque nebuloso. Se estima que su última erupción fue hace 25 mil años a. C.\n\n"+
                "El Cerro Verde es parte del Parque Nacional Los Volcanes El Salvador contando con una extensión de 2,734.6 hectáreas y una total todo el parque de 4,500 hectáreas, entre tierras estatales, municipales y privadas que es administrado por el Ministerio de Medio Ambiental y Recursos Naturales; ofrece miradores a los volcanes de Santa Ana, Izalco y al Lago de Coatepeque, además de un orquidiario, paseo por el bosque y escaladas al mismo Volcán de Izalco (altura 1,980 metros sobre el nivel del mar) y al de Santa Ana (altura 2,381 metros sobre el nivel del mar). El parque también cuenta con tres senderos recreativos: Las Flores Misteriosas, Ventana a la Naturaleza y Antiguo Hotel de Montaña.");
        db.insert("sitio", null, nuevoRegistro7);

        //Insertar Sitio 8
        ContentValues nuevoRegistro8 = new ContentValues();
        nuevoRegistro8.put("idSitio", 8);
        nuevoRegistro8.put("nombre", "Parque Nacional El Imposible");
        nuevoRegistro8.put("latitud", 13.833253);
        nuevoRegistro8.put("longitud", -89.934668);
        nuevoRegistro8.put("idCategoria", 3);
        byte[] byteImagen8 = convertidor("@mipmap/imposible");
        nuevoRegistro8.put("imagen", byteImagen8);
        nuevoRegistro8.put("descripcion", "El Parque Nacional El Imposible es un parque nacional en El Salvador. Fue creado el 1 de enero de 1989 y cubre un área de 5,000 hectáreas. Tiene una altitud de entre 250 y 1.425 metros.\n\n"+
                "En 1992 El Imposible entró en las listas provisionales del Patrimonio de la Humanidad de la UNESCO junto con Cara Sucia.");
        db.insert("sitio", null, nuevoRegistro8);

        //Insertar Sitio 9
        ContentValues nuevoRegistro9 = new ContentValues();
        nuevoRegistro9.put("idSitio", 9);
        nuevoRegistro9.put("nombre", "Playa Metalío");
        nuevoRegistro9.put("latitud", 13.629466);
        nuevoRegistro9.put("longitud",-89.887554);
        nuevoRegistro9.put("idCategoria", 4);
        byte[] byteImagen9 = convertidor("@mipmap/metalio");
        nuevoRegistro9.put("imagen", byteImagen9);
        nuevoRegistro9.put("descripcion", "Esta playa se caracteriza por tener arenas grisáceas, aguas claras y una variedad de restaurantes a la orilla donde se pueden degustar exquisitos platillos, por lo cual se convierte en un destino perfecto para los turistas.\n\n"+
                "Esta playa también es un destino perfecto para los fotógrafos y las personas que deseen observar los hermosos atardeceres en cualquier época del año.");
        db.insert("sitio", null, nuevoRegistro9);

        //Insertar Sitio 10
        ContentValues nuevoRegistro10 = new ContentValues();
        nuevoRegistro10.put("idSitio", 10);
        nuevoRegistro10.put("nombre", "Playa Los Cabanos");
        nuevoRegistro10.put("latitud", 13.525550);
        nuevoRegistro10.put("longitud", -89.796690);
        nuevoRegistro10.put("idCategoria", 4);
        byte[] byteImagen10 = convertidor("@mipmap/cobanos");
        nuevoRegistro10.put("imagen", byteImagen10);
        nuevoRegistro10.put("descripcion", "Debido a que contiene muchos arrecifes, se convierte también en una excelente opción para los amantes del buceo; que de hecho algunos operadores turísticos ofrecen en sus paquetes en la temporada del verano.\n\n"+
                "Los Cóbanos es una playa de origen volcánico, su arena de color blanco y sus rocas te sorprenderán.");
        db.insert("sitio", null, nuevoRegistro10);

        //Insertar Sitio 11
        ContentValues nuevoRegistro11 = new ContentValues();
        nuevoRegistro11.put("idSitio", 11);
        nuevoRegistro11.put("nombre", "Tacuba");
        nuevoRegistro11.put("latitud", 13.900304);
        nuevoRegistro11.put("longitud", -89.932486);
        nuevoRegistro11.put("idCategoria", 5);
        byte[] byteImagen11 = convertidor("@mipmap/tacuba");
        nuevoRegistro11.put("imagen", byteImagen11);
        nuevoRegistro11.put("descripcion", "Tacuba es un municipio del departamento de Ahuachapán, El Salvador. Tiene una población estimada de 31 209 habitantes para el año 2013.\n\n"+
                "Las fiestas patronales de Tacuba se celebran en el mes de julio en honor a Santa María Magdalena.Entre los atractivos del municipio se encuentran las ruinas del antiguo templo católico construido a principios del siglo XVII, el cual terminó derrumbado por los terremotos de 1773.También existe la práctica del senderismo, ya que se encuentra cercano al Parque nacional El Imposible.");
        db.insert("sitio", null, nuevoRegistro11);

        //Insertar Sitio 12
        ContentValues nuevoRegistro12 = new ContentValues();
        nuevoRegistro12.put("idSitio", 12);
        nuevoRegistro12.put("nombre", "Apaneca");
        nuevoRegistro12.put("latitud", 13.856644);
        nuevoRegistro12.put("longitud", -89.803628);
        nuevoRegistro12.put("idCategoria", 5);
        byte[] byteImagen12 = convertidor("@mipmap/apaneca");
        nuevoRegistro12.put("imagen", byteImagen12);
        nuevoRegistro12.put("descripcion", "Apaneca es un municipio del departamento de Ahuachapán, El Salvador. Tiene una población estimada de 8350 habitantes para el año 2013.\n\n"+
                "Unas de las experiencias que puedes vivir es el Laberinto de Albania, el más grande de Centro América, es una lugar hermoso, con un clima fresco y su característico olor a cipres es un lugar que te enamorará.");
        db.insert("sitio", null, nuevoRegistro12);
    }


}
