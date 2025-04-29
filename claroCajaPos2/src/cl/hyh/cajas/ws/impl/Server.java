/**
 * Server.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public interface Server extends java.rmi.Remote {
    public cl.hyh.cajas.ws.impl.Response disconnect(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.NumeroOperacionOut numeroOperacion(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.Response autorizaDevolucion(cl.hyh.cajas.ws.impl.AutorizaDevolucionIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.AperturaCajaOut aperturaCaja(cl.hyh.cajas.ws.impl.AperturaCajaIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.AutentificaOut autentifica(cl.hyh.cajas.ws.impl.AutentificaIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.Response envioOperacion(cl.hyh.cajas.ws.impl.OperacionIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.Response remesa(cl.hyh.cajas.ws.impl.RemesaIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.ConsultaSaldoFavorVtrOut consultaSaldoFavorVtr(cl.hyh.cajas.ws.impl.ConsultaSaldoFavorVtrIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.ConsultaCuentasVtrOut consultaCuentasVtr(cl.hyh.cajas.ws.impl.ConsultaCuentasVtrIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.ConsultaDeudaVtrOut consultaDeudaVtr(cl.hyh.cajas.ws.impl.ConsultaDeudaRutVTRIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.Response envioPago(cl.hyh.cajas.ws.impl.EnvioPagoIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.Response envioReversa(cl.hyh.cajas.ws.impl.EnvioReversaIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.ListaRecaudadoresOut listaRecaudadores(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.Response cambioPassword(cl.hyh.cajas.ws.impl.CambioPasswordIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.PingOut ping(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException;
    public void testConnection() throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.RecargaOut recarga(cl.hyh.cajas.ws.impl.RecargaIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.AutorizaChequeOut autorizaCheque(cl.hyh.cajas.ws.impl.AutorizaChequeIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.InicializaCajaOut inicializaCaja(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.LoginOut login(cl.hyh.cajas.ws.impl.LoginIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.CierreDiarioOut cierre(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.CierreDiarioOut consultaCierreDiario(cl.hyh.cajas.ws.impl.ConsultaCierreDiarioIn arg0) throws java.rmi.RemoteException;
    public cl.hyh.cajas.ws.impl.ConsultaOperacionOut consultaOperacion(cl.hyh.cajas.ws.impl.ConsultaOperacionIn arg0) throws java.rmi.RemoteException;
}
