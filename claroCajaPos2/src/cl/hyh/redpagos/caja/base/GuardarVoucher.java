package cl.hyh.redpagos.caja.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import cl.hyh.redpagos.caja.base.parser.DefVoucher;
import cl.hyh.redpagos.caja.base.parser.DefVoucherLine;
import cl.hyh.redpagos.caja.base.parser.DefVoucherPrint;
import cl.hyh.redpagos.caja.pos.PosDeviceException;
import jpos.JposException;
import ws.claro.cl.AnuncioMarketingResponseDTO;
import ws.claro.cl.proxy.AppControlProxy;


public class GuardarVoucher {
    
    /**
     * 
     * jgomez -  Metodo para guardado de documento PDF en Base de datos y envio a filnet si se debe.
     * 
     * @param lineas Arreglo de lineas a ser imprimidas
     */
    public static void guardarFile(ArrayList <LineaVoucher>lineas, boolean isLogo, String fileName){
        
    }
    
  
}
