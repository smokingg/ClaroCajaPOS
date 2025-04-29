package cl.hyh.redpagos.caja.pos;

/**
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class TransNumeros {
    
    public static String Transformar(long num){
        Definiciones def = new Definiciones();
        
        String numero = "";
        long numMM = num/1000000;
        long numM = (num-(numMM*1000000))/1000;
        long numC = num-(numMM*1000000)-(numM*1000);
        
        if(numMM != 0){
            if(numMM == 1){
                numero = "un millon ";
            }
            else{
                numero = procesar(numMM,def) +  " millones ";
            }
        }
        if(numM != 0){
            if(numM == 1){
                numero = "mil "; 
            }
            else{
                numero = numero + procesar(numM,def) +  " mil ";
            }
        }
        if(numC == 100){
            numero = numero + "cien ";
        }
        else{
            numero = numero + procesar(numC,def) +  ".";
        }
        return numero;
    }
    public static String procesarMes(int mes){
        Definiciones def = new Definiciones();
        return def.mes[mes-1];
    }
    public static String procesar(long num,Definiciones def){
        long aux = num;
        String numero= "";
        int i;
        
        while(aux!=0){
            for(i = 10;i<=1000;i=i*10){
                if(aux/i >0){
                    continue;
                }
                else{
                    break;
                }
            }
            switch(i){
            case(10):
                numero = numero + def.decimas[(int)aux];
            break;
            case(100):
                if(aux >= 20){
                    if(aux%10 == 0){
                        numero = numero + def.decenas[(int)(aux*10)/100];
                    }
                    else{
                        numero = numero + def.decenas[(int)(aux*10)/100] + " y ";
                    }
                }
                else{
                    if(aux <16){
                        numero = numero + def.decimas[(int)aux];
                        aux = 0;
                    }
                    else{
                        numero = numero + def.diez;
                    }
                }
            break;
            case(1000):
                numero = numero + def.centenas[(int)(aux)/100] + " ";
            break;
            }
            aux = aux%(i/10);
        }
        return numero;
    }
}
