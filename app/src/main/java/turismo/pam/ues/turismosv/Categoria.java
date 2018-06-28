package turismo.pam.ues.turismosv;

import android.graphics.drawable.Drawable;

/**
 * Created by danm on 06-17-18.
 */

public class Categoria {

        private String title;
        private String categoryId;
        private String description;
        private Drawable imagen;

        public Categoria() {
            super();
        }

        public Categoria(String categoryId, String title, String description, Drawable imagen) {
            super();
            this.title = title;
            this.description = description;
            this.imagen = imagen;
            this.categoryId = categoryId;
        }


        public String getTitle() {
            return title;
        }

        public void setTittle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Drawable getImage() {
            return imagen;
        }

        public void setImagen(Drawable imagen) {
            this.imagen = imagen;
        }

        public String getCategoryId(){return categoryId;}

        public void setCategoryId(String categoryId){this.categoryId = categoryId;}
}
