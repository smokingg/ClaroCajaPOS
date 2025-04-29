/**
 * PlataformaPagoEnviarAlmacenarDocumentoSOAPQSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento;

public class PlataformaPagoEnviarAlmacenarDocumentoSOAPQSServiceLocator extends org.apache.axis.client.Service implements cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumentoSOAPQSService {

    public PlataformaPagoEnviarAlmacenarDocumentoSOAPQSServiceLocator() {
    }


    public PlataformaPagoEnviarAlmacenarDocumentoSOAPQSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PlataformaPagoEnviarAlmacenarDocumentoSOAPQSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort
    private java.lang.String PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort_address = "http://172.16.1.132:7001/AppControlCaja/PlataformaPagoEnviarAlmacenarDocumentoSOAPQSService";

    public java.lang.String getPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPortAddress() {
        return PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPortWSDDServiceName = "PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort";

    public java.lang.String getPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPortWSDDServiceName() {
        return PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPortWSDDServiceName;
    }

    public void setPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPortWSDDServiceName(java.lang.String name) {
        PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPortWSDDServiceName = name;
    }

    public cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumento getPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort(endpoint);
    }

    public cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumento getPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumentoSOAPStub _stub = new cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumentoSOAPStub(portAddress, this);
            _stub.setPortName(getPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPortEndpointAddress(java.lang.String address) {
        PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumento.class.isAssignableFrom(serviceEndpointInterface)) {
                cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumentoSOAPStub _stub = new cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumentoSOAPStub(new java.net.URL(PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort_address), this);
                _stub.setPortName(getPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPortWSDDServiceName());
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
        if ("PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort".equals(inputPortName)) {
            return getPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoEnviarAlmacenarDocumento/", "PlataformaPagoEnviarAlmacenarDocumentoSOAPQSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoEnviarAlmacenarDocumento/", "PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort".equals(portName)) {
            setPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPortEndpointAddress(address);
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
