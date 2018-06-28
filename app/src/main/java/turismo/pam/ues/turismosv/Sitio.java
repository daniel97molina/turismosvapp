package turismo.pam.ues.turismosv;

import android.graphics.Bitmap;

/**
 * Created by irvin on 06-24-18.
 */

public class Sitio {

    private Integer idSitio;
    private String nombre;
    private double latitud;
    private double longitud;
    private String descripcion;
    private Bitmap imagen;
    private Integer idCategoria;


    public Sitio(Integer idSitio, String nombre, double latitud, double longitud, String descripcion, Bitmap imagen, Integer idCategoria) {
        this.idSitio = idSitio;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.idCategoria = idCategoria;
    }

    public Sitio() {
    }

    public Integer getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(Integer idSitio) {
        this.idSitio = idSitio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
}
