/**
 * ConsultaDevolucionSAFServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultaSAF;

public class ConsultaDevolucionSAFServiceLocator extends org.apache.axis.client.Service implements cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaDevolucionSAFService {

    public ConsultaDevolucionSAFServiceLocator() {
    }


    public ConsultaDevolucionSAFServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ConsultaDevolucionSAFServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ConsultaDevolucionSAFPort
    private java.lang.String ConsultaDevolucionSAFPort_address = "http://200.111.143.196:7001/AppControlCaja/ConsultaDevolucionSAFService";

    public java.lang.String getConsultaDevolucionSAFPortAddress() {
        return ConsultaDevolucionSAFPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ConsultaDevolucionSAFPortWSDDServiceName = "ConsultaDevolucionSAFPort";

    public java.lang.String getConsultaDevolucionSAFPortWSDDServiceName() {
        return ConsultaDevolucionSAFPortWSDDServiceName;
    }

    public void setConsultaDevolucionSAFPortWSDDServiceName(java.lang.String name) {
        ConsultaDevolucionSAFPortWSDDServiceName = name;
    }

    public cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.PlataformaPagoconsultaDevolucionSAF getConsultaDevolucionSAFPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ConsultaDevolucionSAFPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getConsultaDevolucionSAFPort(endpoint);
    }

    public cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.PlataformaPagoconsultaDevolucionSAF getConsultaDevolucionSAFPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaDevolucionSAFPortBindingStub _stub = new cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaDevolucionSAFPortBindingStub(portAddress, this);
            _stub.setPortName(getConsultaDevolucionSAFPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setConsultaDevolucionSAFPortEndpointAddress(java.lang.String address) {
        ConsultaDevolucionSAFPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.PlataformaPagoconsultaDevolucionSAF.class.isAssignableFrom(serviceEndpointInterface)) {
                cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaDevolucionSAFPortBindingStub _stub = new cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaDevolucionSAFPortBindingStub(new java.net.URL(ConsultaDevolucionSAFPort_address), this);
                _stub.setPortName(getConsultaDevolucionSAFPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ConsultaDevolucionSAFPort".equals(inputPortName)) {
            return getConsultaDevolucionSAFPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/ConsultarSaldoFavor/", "ConsultaDevolucionSAFService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/REC/EMP/ConsultarSaldoFavor/", "ConsultaDevolucionSAFPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ConsultaDevolucionSAFPort".equals(portName)) {
            setConsultaDevolucionSAFPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
