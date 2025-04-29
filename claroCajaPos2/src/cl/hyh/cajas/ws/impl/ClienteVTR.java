/**
 * ClienteVTR.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class ClienteVTR  implements java.io.Serializable {
    private java.lang.String estadoSistemaOrigen;

    private java.util.Calendar fechaVencimiento;

    private java.lang.String nombreCliente;

    private java.lang.String rutCliente;

    private int saldoTotal;

    public ClienteVTR() {
    }

    public ClienteVTR(
           java.lang.String estadoSistemaOrigen,
           java.util.Calendar fechaVencimiento,
           java.lang.String nombreCliente,
           java.lang.String rutCliente,
           int saldoTotal) {
           this.estadoSistemaOrigen = estadoSistemaOrigen;
           this.fechaVencimiento = fechaVencimiento;
           this.nombreCliente = nombreCliente;
           this.rutCliente = rutCliente;
           this.saldoTotal = saldoTotal;
    }


    /**
     * Gets the estadoSistemaOrigen value for this ClienteVTR.
     * 
     * @return estadoSistemaOrigen
     */
    public java.lang.String getEstadoSistemaOrigen() {
        return estadoSistemaOrigen;
    }


    /**
     * Sets the estadoSistemaOrigen value for this ClienteVTR.
     * 
     * @param estadoSistemaOrigen
     */
    public void setEstadoSistemaOrigen(java.lang.String estadoSistemaOrigen) {
        this.estadoSistemaOrigen = estadoSistemaOrigen;
    }


    /**
     * Gets the fechaVencimiento value for this ClienteVTR.
     * 
     * @return fechaVencimiento
     */
    public java.util.Calendar getFechaVencimiento() {
        return fechaVencimiento;
    }


    /**
     * Sets the fechaVencimiento value for this ClienteVTR.
     * 
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(java.util.Calendar fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    /**
     * Gets the nombreCliente value for this ClienteVTR.
     * 
     * @return nombreCliente
     */
    public java.lang.String getNombreCliente() {
        return nombreCliente;
    }


    /**
     * Sets the nombreCliente value for this ClienteVTR.
     * 
     * @param nombreCliente
     */
    public void setNombreCliente(java.lang.String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }


    /**
     * Gets the rutCliente value for this ClienteVTR.
     * 
     * @return rutCliente
     */
    public java.lang.String getRutCliente() {
        return rutCliente;
    }


    /**
     * Sets the rutCliente value for this ClienteVTR.
     * 
     * @param rutCliente
     */
    public void setRutCliente(java.lang.String rutCliente) {
        this.rutCliente = rutCliente;
    }


    /**
     * Gets the saldoTotal value for this ClienteVTR.
     * 
     * @return saldoTotal
     */
    public int getSaldoTotal() {
        return saldoTotal;
    }


    /**
     * Sets the saldoTotal value for this ClienteVTR.
     * 
     * @param saldoTotal
     */
    public void setSaldoTotal(int saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClienteVTR)) return false;
        ClienteVTR other = (ClienteVTR) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.estadoSistemaOrigen==null && other.getEstadoSistemaOrigen()==null) || 
             (this.estadoSistemaOrigen!=null &&
              this.estadoSistemaOrigen.equals(other.getEstadoSistemaOrigen()))) &&
            ((this.fechaVencimiento==null && other.getFechaVencimiento()==null) || 
             (this.fechaVencimiento!=null &&
              this.fechaVencimiento.equals(other.getFechaVencimiento()))) &&
            ((this.nombreCliente==null && other.getNombreCliente()==null) || 
             (this.nombreCliente!=null &&
              this.nombreCliente.equals(other.getNombreCliente()))) &&
            ((this.rutCliente==null && other.getRutCliente()==null) || 
             (this.rutCliente!=null &&
              this.rutCliente.equals(other.getRutCliente()))) &&
            this.saldoTotal == other.getSaldoTotal();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getEstadoSistemaOrigen() != null) {
            _hashCode += getEstadoSistemaOrigen().hashCode();
        }
        if (getFechaVencimiento() != null) {
            _hashCode += getFechaVencimiento().hashCode();
        }
        if (getNombreCliente() != null) {
            _hashCode += getNombreCliente().hashCode();
        }
        if (getRutCliente() != null) {
            _hashCode += getRutCliente().hashCode();
        }
        _hashCode += getSaldoTotal();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClienteVTR.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "clienteVTR"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estadoSistemaOrigen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estadoSistemaOrigen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaVencimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaVencimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rutCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rutCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoTotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
