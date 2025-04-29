/**
 * AppControlCajaWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class AppControlCajaWSServiceLocator extends org.apache.axis.client.Service implements ws.claro.cl.AppControlCajaWSService {

    public AppControlCajaWSServiceLocator() {
    }


    public AppControlCajaWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AppControlCajaWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AppControlCajaWSPort
    private java.lang.String AppControlCajaWSPort_address = "http://localhost:7001/AppControlCaja/AppControlCajaWSService";

    public java.lang.String getAppControlCajaWSPortAddress() {
        return AppControlCajaWSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AppControlCajaWSPortWSDDServiceName = "AppControlCajaWSPort";

    public java.lang.String getAppControlCajaWSPortWSDDServiceName() {
        return AppControlCajaWSPortWSDDServiceName;
    }

    public void setAppControlCajaWSPortWSDDServiceName(java.lang.String name) {
        AppControlCajaWSPortWSDDServiceName = name;
    }

    public ws.claro.cl.AppControlCajaWS getAppControlCajaWSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AppControlCajaWSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAppControlCajaWSPort(endpoint);
    }

    public ws.claro.cl.AppControlCajaWS getAppControlCajaWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ws.claro.cl.AppControlCajaWSPortBindingStub _stub = new ws.claro.cl.AppControlCajaWSPortBindingStub(portAddress, this);
            _stub.setPortName(getAppControlCajaWSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAppControlCajaWSPortEndpointAddress(java.lang.String address) {
        AppControlCajaWSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ws.claro.cl.AppControlCajaWS.class.isAssignableFrom(serviceEndpointInterface)) {
                ws.claro.cl.AppControlCajaWSPortBindingStub _stub = new ws.claro.cl.AppControlCajaWSPortBindingStub(new java.net.URL(AppControlCajaWSPort_address), this);
                _stub.setPortName(getAppControlCajaWSPortWSDDServiceName());
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
        if ("AppControlCajaWSPort".equals(inputPortName)) {
            return getAppControlCajaWSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://cl.claro.ws/", "AppControlCajaWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://cl.claro.ws/", "AppControlCajaWSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AppControlCajaWSPort".equals(portName)) {
            setAppControlCajaWSPortEndpointAddress(address);
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
