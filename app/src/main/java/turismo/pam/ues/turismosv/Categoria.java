package turismo.pam.ues.turismosv;

import java.util.List;

/**
 * Created by danm on 06-17-18.
 */

public class Categoria {

    private Integer idCategoria;
    private String nombre;
    private List<Sitio> sitioList;

    public Categoria(Integer idCategoria, String nombre) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    }

    public Categoria() {
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public List<Sitio> getSitioList() {
        return sitioList;
    }

    public void setSitioList(List<Sitio> sitioList) {
        this.sitioList = sitioList;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
