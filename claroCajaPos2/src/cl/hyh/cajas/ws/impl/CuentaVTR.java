/**
 * CuentaVTR.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class CuentaVTR  implements java.io.Serializable {
    private java.lang.String cuentaUnica;

    private java.lang.String direccionCobranza;

    private java.util.Calendar fechaVencimiento;

    private long numeroCuenta;

    private long saldoCuenta;

    private java.lang.String sistemaOrigen;

    public CuentaVTR() {
    }

    public CuentaVTR(
           java.lang.String cuentaUnica,
           java.lang.String direccionCobranza,
           java.util.Calendar fechaVencimiento,
           long numeroCuenta,
           long saldoCuenta,
           java.lang.String sistemaOrigen) {
           this.cuentaUnica = cuentaUnica;
           this.direccionCobranza = direccionCobranza;
           this.fechaVencimiento = fechaVencimiento;
           this.numeroCuenta = numeroCuenta;
           this.saldoCuenta = saldoCuenta;
           this.sistemaOrigen = sistemaOrigen;
    }


    /**
     * Gets the cuentaUnica value for this CuentaVTR.
     * 
     * @return cuentaUnica
     */
    public java.lang.String getCuentaUnica() {
        return cuentaUnica;
    }


    /**
     * Sets the cuentaUnica value for this CuentaVTR.
     * 
     * @param cuentaUnica
     */
    public void setCuentaUnica(java.lang.String cuentaUnica) {
        this.cuentaUnica = cuentaUnica;
    }


    /**
     * Gets the direccionCobranza value for this CuentaVTR.
     * 
     * @return direccionCobranza
     */
    public java.lang.String getDireccionCobranza() {
        return direccionCobranza;
    }


    /**
     * Sets the direccionCobranza value for this CuentaVTR.
     * 
     * @param direccionCobranza
     */
    public void setDireccionCobranza(java.lang.String direccionCobranza) {
        this.direccionCobranza = direccionCobranza;
    }


    /**
     * Gets the fechaVencimiento value for this CuentaVTR.
     * 
     * @return fechaVencimiento
     */
    public java.util.Calendar getFechaVencimiento() {
        return fechaVencimiento;
    }


    /**
     * Sets the fechaVencimiento value for this CuentaVTR.
     * 
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(java.util.Calendar fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    /**
     * Gets the numeroCuenta value for this CuentaVTR.
     * 
     * @return numeroCuenta
     */
    public long getNumeroCuenta() {
        return numeroCuenta;
    }


    /**
     * Sets the numeroCuenta value for this CuentaVTR.
     * 
     * @param numeroCuenta
     */
    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    /**
     * Gets the saldoCuenta value for this CuentaVTR.
     * 
     * @return saldoCuenta
     */
    public long getSaldoCuenta() {
        return saldoCuenta;
    }


    /**
     * Sets the saldoCuenta value for this CuentaVTR.
     * 
     * @param saldoCuenta
     */
    public void setSaldoCuenta(long saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }


    /**
     * Gets the sistemaOrigen value for this CuentaVTR.
     * 
     * @return sistemaOrigen
     */
    public java.lang.String getSistemaOrigen() {
        return sistemaOrigen;
    }


    /**
     * Sets the sistemaOrigen value for this CuentaVTR.
     * 
     * @param sistemaOrigen
     */
    public void setSistemaOrigen(java.lang.String sistemaOrigen) {
        this.sistemaOrigen = sistemaOrigen;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CuentaVTR)) return false;
        CuentaVTR other = (CuentaVTR) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cuentaUnica==null && other.getCuentaUnica()==null) || 
             (this.cuentaUnica!=null &&
              this.cuentaUnica.equals(other.getCuentaUnica()))) &&
            ((this.direccionCobranza==null && other.getDireccionCobranza()==null) || 
             (this.direccionCobranza!=null &&
              this.direccionCobranza.equals(other.getDireccionCobranza()))) &&
            ((this.fechaVencimiento==null && other.getFechaVencimiento()==null) || 
             (this.fechaVencimiento!=null &&
              this.fechaVencimiento.equals(other.getFechaVencimiento()))) &&
            this.numeroCuenta == other.getNumeroCuenta() &&
            this.saldoCuenta == other.getSaldoCuenta() &&
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
        if (getCuentaUnica() != null) {
            _hashCode += getCuentaUnica().hashCode();
        }
        if (getDireccionCobranza() != null) {
            _hashCode += getDireccionCobranza().hashCode();
        }
        if (getFechaVencimiento() != null) {
            _hashCode += getFechaVencimiento().hashCode();
        }
        _hashCode += new Long(getNumeroCuenta()).hashCode();
        _hashCode += new Long(getSaldoCuenta()).hashCode();
        if (getSistemaOrigen() != null) {
            _hashCode += getSistemaOrigen().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CuentaVTR.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "cuentaVTR"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuentaUnica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuentaUnica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccionCobranza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direccionCobranza"));
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
        elemField.setFieldName("numeroCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoCuenta"));
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
