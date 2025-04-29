/**
 * PlataformaPagoEnviarCorreoSilverpopWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS;

public interface PlataformaPagoEnviarCorreoSilverpopWS extends java.rmi.Remote {
    public void enviarCorreo(java.lang.String idOperacion, java.lang.String mails, javax.xml.rpc.holders.StringHolder status, javax.xml.rpc.holders.StringHolder errorCode) throws java.rmi.RemoteException;
}
