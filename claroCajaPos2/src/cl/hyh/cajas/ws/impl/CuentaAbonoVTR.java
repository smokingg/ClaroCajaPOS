/**
 * CuentaAbonoVTR.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class CuentaAbonoVTR  implements java.io.Serializable {
    private java.lang.String direccionPostal;

    private long numeroCuenta;

    private java.lang.String sistemaOrigen;

    public CuentaAbonoVTR() {
    }

    public CuentaAbonoVTR(
           java.lang.String direccionPostal,
           long numeroCuenta,
           java.lang.String sistemaOrigen) {
           this.direccionPostal = direccionPostal;
           this.numeroCuenta = numeroCuenta;
           this.sistemaOrigen = sistemaOrigen;
    }


    /**
     * Gets the direccionPostal value for this CuentaAbonoVTR.
     * 
     * @return direccionPostal
     */
    public java.lang.String getDireccionPostal() {
        return direccionPostal;
    }


    /**
     * Sets the direccionPostal value for this CuentaAbonoVTR.
     * 
     * @param direccionPostal
     */
    public void setDireccionPostal(java.lang.String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }


    /**
     * Gets the numeroCuenta value for this CuentaAbonoVTR.
     * 
     * @return numeroCuenta
     */
    public long getNumeroCuenta() {
        return numeroCuenta;
    }


    /**
     * Sets the numeroCuenta value for this CuentaAbonoVTR.
     * 
     * @param numeroCuenta
     */
    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    /**
     * Gets the sistemaOrigen value for this CuentaAbonoVTR.
     * 
     * @return sistemaOrigen
     */
    public java.lang.String getSistemaOrigen() {
        return sistemaOrigen;
    }


    /**
     * Sets the sistemaOrigen value for this CuentaAbonoVTR.
     * 
     * @param sistemaOrigen
     */
    public void setSistemaOrigen(java.lang.String sistemaOrigen) {
        this.sistemaOrigen = sistemaOrigen;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CuentaAbonoVTR)) return false;
        CuentaAbonoVTR other = (CuentaAbonoVTR) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.direccionPostal==null && other.getDireccionPostal()==null) || 
             (this.direccionPostal!=null &&
              this.direccionPostal.equals(other.getDireccionPostal()))) &&
            this.numeroCuenta == other.getNumeroCuenta() &&
            ((this.sistemaOrigen==null && other.getSistemaOrigen()==null) || 
             (this.sistemaOrigen!=null &&
              this.sistemaOrigen.equals(other.getSistemaOrigen())));
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
        if (getDireccionPostal() != null) {
            _hashCode += getDireccionPostal().hashCode();
        }
        _hashCode += new Long(getNumeroCuenta()).hashCode();
        if (getSistemaOrigen() != null) {
            _hashCode += getSistemaOrigen().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CuentaAbonoVTR.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "cuentaAbonoVTR"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccionPostal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direccionPostal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemaOrigen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemaOrigen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
