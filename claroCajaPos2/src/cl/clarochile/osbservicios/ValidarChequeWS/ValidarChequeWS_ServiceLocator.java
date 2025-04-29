/**
 * ValidarChequeWS_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.ValidarChequeWS;

public class ValidarChequeWS_ServiceLocator extends org.apache.axis.client.Service implements cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWS_Service {

    public ValidarChequeWS_ServiceLocator() {
    }


    public ValidarChequeWS_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ValidarChequeWS_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ValidarChequeWSSOAP
    private java.lang.String ValidarChequeWSSOAP_address = "http://jenny:7001/Recaudacion/ValidacionChequeFWSProxy";

    public java.lang.String getValidarChequeWSSOAPAddress() {
        return ValidarChequeWSSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ValidarChequeWSSOAPWSDDServiceName = "ValidarChequeWSSOAP";

    public java.lang.String getValidarChequeWSSOAPWSDDServiceName() {
        return ValidarChequeWSSOAPWSDDServiceName;
    }

    public void setValidarChequeWSSOAPWSDDServiceName(java.lang.String name) {
        ValidarChequeWSSOAPWSDDServiceName = name;
    }

    public cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWS_PortType getValidarChequeWSSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ValidarChequeWSSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getValidarChequeWSSOAP(endpoint);
    }

    public cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWS_PortType getValidarChequeWSSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWSSOAPStub _stub = new cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWSSOAPStub(portAddress, this);
            _stub.setPortName(getValidarChequeWSSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setValidarChequeWSSOAPEndpointAddress(java.lang.String address) {
        ValidarChequeWSSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWS_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWSSOAPStub _stub = new cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWSSOAPStub(new java.net.URL(ValidarChequeWSSOAP_address), this);
                _stub.setPortName(getValidarChequeWSSOAPWSDDServiceName());
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
        if ("ValidarChequeWSSOAP".equals(inputPortName)) {
            return getValidarChequeWSSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/ValidarChequeWS/", "ValidarChequeWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/ValidarChequeWS/", "ValidarChequeWSSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ValidarChequeWSSOAP".equals(portName)) {
            setValidarChequeWSSOAPEndpointAddress(address);
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
