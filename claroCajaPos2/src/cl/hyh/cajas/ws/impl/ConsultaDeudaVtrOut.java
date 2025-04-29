/**
 * ConsultaDeudaVtrOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class ConsultaDeudaVtrOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private cl.hyh.cajas.ws.impl.ClienteVTR cliente;

    private cl.hyh.cajas.ws.impl.CuentaVTR[] cuentas;

    private cl.hyh.cajas.ws.impl.DocumentoVTR[] documentos;

    private cl.hyh.cajas.ws.impl.ServicioVTR[] servicios;

    public ConsultaDeudaVtrOut() {
    }

    public ConsultaDeudaVtrOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           cl.hyh.cajas.ws.impl.ClienteVTR cliente,
           cl.hyh.cajas.ws.impl.CuentaVTR[] cuentas,
           cl.hyh.cajas.ws.impl.DocumentoVTR[] documentos,
           cl.hyh.cajas.ws.impl.ServicioVTR[] servicios) {
        super(
            headerOut);
        this.cliente = cliente;
        this.cuentas = cuentas;
        this.documentos = documentos;
        this.servicios = servicios;
    }


    /**
     * Gets the cliente value for this ConsultaDeudaVtrOut.
     * 
     * @return cliente
     */
    public cl.hyh.cajas.ws.impl.ClienteVTR getCliente() {
        return cliente;
    }


    /**
     * Sets the cliente value for this ConsultaDeudaVtrOut.
     * 
     * @param cliente
     */
    public void setCliente(cl.hyh.cajas.ws.impl.ClienteVTR cliente) {
        this.cliente = cliente;
    }


    /**
     * Gets the cuentas value for this ConsultaDeudaVtrOut.
     * 
     * @return cuentas
     */
    public cl.hyh.cajas.ws.impl.CuentaVTR[] getCuentas() {
        return cuentas;
    }


    /**
     * Sets the cuentas value for this ConsultaDeudaVtrOut.
     * 
     * @param cuentas
     */
    public void setCuentas(cl.hyh.cajas.ws.impl.CuentaVTR[] cuentas) {
        this.cuentas = cuentas;
    }

    public cl.hyh.cajas.ws.impl.CuentaVTR getCuentas(int i) {
        return this.cuentas[i];
    }

    public void setCuentas(int i, cl.hyh.cajas.ws.impl.CuentaVTR _value) {
        this.cuentas[i] = _value;
    }


    /**
     * Gets the documentos value for this ConsultaDeudaVtrOut.
     * 
     * @return documentos
     */
    public cl.hyh.cajas.ws.impl.DocumentoVTR[] getDocumentos() {
        return documentos;
    }


    /**
     * Sets the documentos value for this ConsultaDeudaVtrOut.
     * 
     * @param documentos
     */
    public void setDocumentos(cl.hyh.cajas.ws.impl.DocumentoVTR[] documentos) {
        this.documentos = documentos;
    }

    public cl.hyh.cajas.ws.impl.DocumentoVTR getDocumentos(int i) {
        return this.documentos[i];
    }

    public void setDocumentos(int i, cl.hyh.cajas.ws.impl.DocumentoVTR _value) {
        this.documentos[i] = _value;
    }


    /**
     * Gets the servicios value for this ConsultaDeudaVtrOut.
     * 
     * @return servicios
     */
    public cl.hyh.cajas.ws.impl.ServicioVTR[] getServicios() {
        return servicios;
    }


    /**
     * Sets the servicios value for this ConsultaDeudaVtrOut.
     * 
     * @param servicios
     */
    public void setServicios(cl.hyh.cajas.ws.impl.ServicioVTR[] servicios) {
        this.servicios = servicios;
    }

    public cl.hyh.cajas.ws.impl.ServicioVTR getServicios(int i) {
        return this.servicios[i];
    }

    public void setServicios(int i, cl.hyh.cajas.ws.impl.ServicioVTR _value) {
        this.servicios[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaDeudaVtrOut)) return false;
        ConsultaDeudaVtrOut other = (ConsultaDeudaVtrOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cliente==null && other.getCliente()==null) || 
             (this.cliente!=null &&
              this.cliente.equals(other.getCliente()))) &&
            ((this.cuentas==null && other.getCuentas()==null) || 
             (this.cuentas!=null &&
              java.util.Arrays.equals(this.cuentas, other.getCuentas()))) &&
            ((this.documentos==null && other.getDocumentos()==null) || 
             (this.documentos!=null &&
              java.util.Arrays.equals(this.documentos, other.getDocumentos()))) &&
            ((this.servicios==null && other.getServicios()==null) || 
             (this.servicios!=null &&
              java.util.Arrays.equals(this.servicios, other.getServicios())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCliente() != null) {
            _hashCode += getCliente().hashCode();
        }
        if (getCuentas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCuentas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCuentas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDocumentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getServicios() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getServicios());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getServicios(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultaDeudaVtrOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "consultaDeudaVtrOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "clienteVTR"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuentas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuentas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "cuentaVTR"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "documentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "documentoVTR"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servicios");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servicios"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "servicioVTR"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
