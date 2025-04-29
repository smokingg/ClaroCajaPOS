/**
 * PlataformaPagoConsultarItemsOne_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne;

public class PlataformaPagoConsultarItemsOne_ServiceLocator extends org.apache.axis.client.Service implements cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarItemsOne_Service {

    public PlataformaPagoConsultarItemsOne_ServiceLocator() {
    }


    public PlataformaPagoConsultarItemsOne_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PlataformaPagoConsultarItemsOne_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PlataformaPagoConsultarSOAP
    private java.lang.String PlataformaPagoConsultarSOAP_address = "http://localhost:7001/AppControlCaja/PlataformaPagoConsultarItemsOne";

    public java.lang.String getPlataformaPagoConsultarSOAPAddress() {
        return PlataformaPagoConsultarSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PlataformaPagoConsultarSOAPWSDDServiceName = "PlataformaPagoConsultarSOAP";

    public java.lang.String getPlataformaPagoConsultarSOAPWSDDServiceName() {
        return PlataformaPagoConsultarSOAPWSDDServiceName;
    }

    public void setPlataformaPagoConsultarSOAPWSDDServiceName(java.lang.String name) {
        PlataformaPagoConsultarSOAPWSDDServiceName = name;
    }

    public cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarItemsOne_PortType getPlataformaPagoConsultarSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PlataformaPagoConsultarSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPlataformaPagoConsultarSOAP(endpoint);
    }

    public cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarItemsOne_PortType getPlataformaPagoConsultarSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarSOAPStub _stub = new cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarSOAPStub(portAddress, this);
            _stub.setPortName(getPlataformaPagoConsultarSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPlataformaPagoConsultarSOAPEndpointAddress(java.lang.String address) {
        PlataformaPagoConsultarSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarItemsOne_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarSOAPStub _stub = new cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarSOAPStub(new java.net.URL(PlataformaPagoConsultarSOAP_address), this);
                _stub.setPortName(getPlataformaPagoConsultarSOAPWSDDServiceName());
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
        if ("PlataformaPagoConsultarSOAP".equals(inputPortName)) {
            return getPlataformaPagoConsultarSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultarItemsOne/", "PlataformaPagoConsultarItemsOne");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultarItemsOne/", "PlataformaPagoConsultarSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PlataformaPagoConsultarSOAP".equals(portName)) {
            setPlataformaPagoConsultarSOAPEndpointAddress(address);
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
