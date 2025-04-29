package cl.hyh.redpagos.caja.base;

import java.io.Serializable;

/**
 * Representacion de una linea a ser imprimida en la boleta
 * @author Rafael Hernandez
 *
 */
public class LineaVoucher implements Serializable {
    private boolean center = false;
    private boolean bold = false;
    private boolean underline = false;
    private boolean right = false;
    private boolean caps = false;
    private boolean small = false;
    private boolean tri = false;
    
    String linea = "";

    /**
     * Retorna la variable center
     * @return
     */
    public boolean isCenter() {
        return center;
    }

    /**
     * Setea la variable center
     * @param center
     */
    public void setCenter(boolean center) {
        this.center = center;
    }

    /**
     * Retorna la variable bold
     * @return
     */
    public boolean isBold() {
        return bold;
    }

    /**
     * Setea la variable bold
     * @param bold
     */
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    /**
     * Retorna la variable linea
     * @return
     */
    public String getLinea() {
        return linea;
    }

    /**
     * Setea la variable linea
     * @param linea
     */
    public void setLinea(String linea) {
        this.linea = linea;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isCaps() {
        return caps;
    }

    public void setCaps(boolean caps) {
        this.caps = caps;
    }

    public boolean isSmall() {
        return small;
    }

    public void setSmall(boolean small) {
        this.small = small;
    }

    public boolean isTri() {
        return tri;
    }

    public void setTri(boolean tri) {
        this.tri = tri;
    }
    
    
}
