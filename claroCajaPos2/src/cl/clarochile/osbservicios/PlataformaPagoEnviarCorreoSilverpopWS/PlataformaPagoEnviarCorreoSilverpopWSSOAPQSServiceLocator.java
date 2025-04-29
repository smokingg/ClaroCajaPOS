/**
 * PlataformaPagoEnviarCorreoSilverpopWSSOAPQSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS;

public class PlataformaPagoEnviarCorreoSilverpopWSSOAPQSServiceLocator extends org.apache.axis.client.Service implements cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWSSOAPQSService {

    public PlataformaPagoEnviarCorreoSilverpopWSSOAPQSServiceLocator() {
    }


    public PlataformaPagoEnviarCorreoSilverpopWSSOAPQSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PlataformaPagoEnviarCorreoSilverpopWSSOAPQSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort
    private java.lang.String PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort_address = "http://172.16.1.132:7001/AppControlCaja/PlataformaPagoEnviarCorreoSilverpopWSSOAPQSService";

    public java.lang.String getPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPortAddress() {
        return PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPortWSDDServiceName = "PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort";

    public java.lang.String getPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPortWSDDServiceName() {
        return PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPortWSDDServiceName;
    }

    public void setPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPortWSDDServiceName(java.lang.String name) {
        PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPortWSDDServiceName = name;
    }

    public cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWS getPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort(endpoint);
    }

    public cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWS getPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWSSOAPStub _stub = new cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWSSOAPStub(portAddress, this);
            _stub.setPortName(getPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPortEndpointAddress(java.lang.String address) {
        PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWS.class.isAssignableFrom(serviceEndpointInterface)) {
                cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWSSOAPStub _stub = new cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWSSOAPStub(new java.net.URL(PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort_address), this);
                _stub.setPortName(getPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPortWSDDServiceName());
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
        if ("PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort".equals(inputPortName)) {
            return getPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoEnviarCorreoSilverpopWS/", "PlataformaPagoEnviarCorreoSilverpopWSSOAPQSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoEnviarCorreoSilverpopWS/", "PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort".equals(portName)) {
            setPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPortEndpointAddress(address);
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
