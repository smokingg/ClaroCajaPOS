/**
 * PlataformaPagoNotificar_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoNotificar;

public class PlataformaPagoNotificar_ServiceLocator extends org.apache.axis.client.Service implements cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificar_Service {

    public PlataformaPagoNotificar_ServiceLocator() {
    }


    public PlataformaPagoNotificar_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PlataformaPagoNotificar_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PlataformaPagoNotificarSOAP
    private java.lang.String PlataformaPagoNotificarSOAP_address = "http://172.16.1.43:7001/AppControlCaja/PlataformaPagoNotificar";

    public java.lang.String getPlataformaPagoNotificarSOAPAddress() {
        return PlataformaPagoNotificarSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PlataformaPagoNotificarSOAPWSDDServiceName = "PlataformaPagoNotificarSOAP";

    public java.lang.String getPlataformaPagoNotificarSOAPWSDDServiceName() {
        return PlataformaPagoNotificarSOAPWSDDServiceName;
    }

    public void setPlataformaPagoNotificarSOAPWSDDServiceName(java.lang.String name) {
        PlataformaPagoNotificarSOAPWSDDServiceName = name;
    }

    public cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificar_PortType getPlataformaPagoNotificarSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PlataformaPagoNotificarSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPlataformaPagoNotificarSOAP(endpoint);
    }

    public cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificar_PortType getPlataformaPagoNotificarSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarSOAPBindingStub _stub = new cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarSOAPBindingStub(portAddress, this);
            _stub.setPortName(getPlataformaPagoNotificarSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPlataformaPagoNotificarSOAPEndpointAddress(java.lang.String address) {
        PlataformaPagoNotificarSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificar_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarSOAPBindingStub _stub = new cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarSOAPBindingStub(new java.net.URL(PlataformaPagoNotificarSOAP_address), this);
                _stub.setPortName(getPlataformaPagoNotificarSOAPWSDDServiceName());
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
        if ("PlataformaPagoNotificarSOAP".equals(inputPortName)) {
            return getPlataformaPagoNotificarSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoNotificar/", "PlataformaPagoNotificar");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoNotificar/", "PlataformaPagoNotificarSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PlataformaPagoNotificarSOAP".equals(portName)) {
            setPlataformaPagoNotificarSOAPEndpointAddress(address);
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
