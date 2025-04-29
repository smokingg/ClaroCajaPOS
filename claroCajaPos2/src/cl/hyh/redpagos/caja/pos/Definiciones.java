package cl.hyh.redpagos.caja.pos;

/**
 * Clase que contiene las definiciones de transformaciones de numeros a palabras
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Definiciones {
    
    public String[] decimas;
    public String[] decenas;
    public String[] centenas;
    public String[] mes;
    public String centena;
    public String miles;
    public String millones;
    public String diez;

    /**
     * Instancia las distintas definciones numericas para las transformaciones de numeros a palabras
     * 
     */
    public Definiciones(){
        decimas = new String[16];
        decenas = new String[10];
        centenas = new String[10];
        mes = new String[12];
        
        centena = "ciento";
        miles = "mil";
        millones = "millones";
        diez = "dieci";
        
        decimas[0]="cero";
        decimas[1]="un";
        decimas[2]="dos";
        decimas[3]="tres";
        decimas[4]="cuatro";
        decimas[5]="cinco";
        decimas[6]="seis";
        decimas[7]="siete";
        decimas[8]="ocho";
        decimas[9]="nueve";
        decimas[10]="diez";
        decimas[11]="once";
        decimas[12]="doce";
        decimas[13]="trece";
        decimas[14]="catorce";
        decimas[15]="quince";
        
        decenas[0]="cero";
        decenas[1]="diez";
        decenas[2]="veinte";
        decenas[3]="treinta";
        decenas[4]="cuarenta";
        decenas[5]="cincuenta";
        decenas[6]="sesenta";
        decenas[7]="setenta";
        decenas[8]="ochenta";
        decenas[9]="noventa";
        
        centenas[0]="cero";
        centenas[1]="ciento";
        centenas[2]="doscientos";
        centenas[3]="trescientos";
        centenas[4]="cuatroscientos";
        centenas[5]="quinientos";
        centenas[6]="seiscientos";
        centenas[7]="sietecientos";
        centenas[8]="ochocientos";
        centenas[9]="novecientos";
        
        mes[0]= "Enero";
        mes[1]= "Febrero";
        mes[2]= "Marzo";
        mes[3]= "Abril";
        mes[4]= "Mayo";
        mes[5]= "Junio";
        mes[6]= "Julio";
        mes[7]= "Agosto";
        mes[8]= "Septiembre";
        mes[9]= "Octubre";
        mes[10]= "Noviembre";
        mes[11]= "Diciembre";
        
    }
}
