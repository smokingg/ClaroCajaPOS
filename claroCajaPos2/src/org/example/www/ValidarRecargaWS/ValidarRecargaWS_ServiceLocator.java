/**
 * ValidarRecargaWS_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.ValidarRecargaWS;

public class ValidarRecargaWS_ServiceLocator extends org.apache.axis.client.Service implements org.example.www.ValidarRecargaWS.ValidarRecargaWS_Service {

    public ValidarRecargaWS_ServiceLocator() {
    }


    public ValidarRecargaWS_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ValidarRecargaWS_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ValidarRecargaWSSOAP
    private java.lang.String ValidarRecargaWSSOAP_address = "http://10.38.63.13:8001/AppControlCaja/ValidarRecargaWS";

    public java.lang.String getValidarRecargaWSSOAPAddress() {
        return ValidarRecargaWSSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ValidarRecargaWSSOAPWSDDServiceName = "ValidarRecargaWSSOAP";

    public java.lang.String getValidarRecargaWSSOAPWSDDServiceName() {
        return ValidarRecargaWSSOAPWSDDServiceName;
    }

    public void setValidarRecargaWSSOAPWSDDServiceName(java.lang.String name) {
        ValidarRecargaWSSOAPWSDDServiceName = name;
    }

    public org.example.www.ValidarRecargaWS.ValidarRecargaWS_PortType getValidarRecargaWSSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ValidarRecargaWSSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getValidarRecargaWSSOAP(endpoint);
    }

    public org.example.www.ValidarRecargaWS.ValidarRecargaWS_PortType getValidarRecargaWSSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.example.www.ValidarRecargaWS.ValidarRecargaWSSOAPStub _stub = new org.example.www.ValidarRecargaWS.ValidarRecargaWSSOAPStub(portAddress, this);
            _stub.setPortName(getValidarRecargaWSSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setValidarRecargaWSSOAPEndpointAddress(java.lang.String address) {
        ValidarRecargaWSSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.example.www.ValidarRecargaWS.ValidarRecargaWS_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.example.www.ValidarRecargaWS.ValidarRecargaWSSOAPStub _stub = new org.example.www.ValidarRecargaWS.ValidarRecargaWSSOAPStub(new java.net.URL(ValidarRecargaWSSOAP_address), this);
                _stub.setPortName(getValidarRecargaWSSOAPWSDDServiceName());
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
        if ("ValidarRecargaWSSOAP".equals(inputPortName)) {
            return getValidarRecargaWSSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.example.org/ValidarRecargaWS/", "ValidarRecargaWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.example.org/ValidarRecargaWS/", "ValidarRecargaWSSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ValidarRecargaWSSOAP".equals(portName)) {
            setValidarRecargaWSSOAPEndpointAddress(address);
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
