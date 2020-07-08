package edu.pucmm.market.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.persistence.Table;

@Embeddable
@Table(name = "FOTO_PRODUCTO")
public class Foto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "MIME")
    private String mimeType;
    @Lob
    @Column(name = "BASE64")
    private String base64;
 
    public Foto(String nombre, String mimeType, String base64){
        this.nombre = nombre;
        this.mimeType = mimeType;
        this.base64 = base64;
    }
    
    public Foto(){
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}