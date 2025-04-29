/**
 * OperacionIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.example.www.ValidarRecargaWS;

public class OperacionIn  implements java.io.Serializable {
    private java.lang.String tipoOperacion;

    private java.lang.String valorOperacion;

    private long monto;

    private long numOperacionPago;

    public OperacionIn() {
    }

    public OperacionIn(
           java.lang.String tipoOperacion,
           java.lang.String valorOperacion,
           long monto,
           long numOperacionPago) {
           this.tipoOperacion = tipoOperacion;
           this.valorOperacion = valorOperacion;
           this.monto = monto;
           this.numOperacionPago = numOperacionPago;
    }


    /**
     * Gets the tipoOperacion value for this OperacionIn.
     * 
     * @return tipoOperacion
     */
    public java.lang.String getTipoOperacion() {
        return tipoOperacion;
    }


    /**
     * Sets the tipoOperacion value for this OperacionIn.
     * 
     * @param tipoOperacion
     */
    public void setTipoOperacion(java.lang.String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }


    /**
     * Gets the valorOperacion value for this OperacionIn.
     * 
     * @return valorOperacion
     */
    public java.lang.String getValorOperacion() {
        return valorOperacion;
    }


    /**
     * Sets the valorOperacion value for this OperacionIn.
     * 
     * @param valorOperacion
     */
    public void setValorOperacion(java.lang.String valorOperacion) {
        this.valorOperacion = valorOperacion;
    }


    /**
     * Gets the monto value for this OperacionIn.
     * 
     * @return monto
     */
    public long getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this OperacionIn.
     * 
     * @param monto
     */
    public void setMonto(long monto) {
        this.monto = monto;
    }


    /**
     * Gets the numOperacionPago value for this OperacionIn.
     * 
     * @return numOperacionPago
     */
    public long getNumOperacionPago() {
        return numOperacionPago;
    }


    /**
     * Sets the numOperacionPago value for this OperacionIn.
     * 
     * @param numOperacionPago
     */
    public void setNumOperacionPago(long numOperacionPago) {
        this.numOperacionPago = numOperacionPago;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionIn)) return false;
        OperacionIn other = (OperacionIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tipoOperacion==null && other.getTipoOperacion()==null) || 
             (this.tipoOperacion!=null &&
              this.tipoOperacion.equals(other.getTipoOperacion()))) &&
            ((this.valorOperacion==null && other.getValorOperacion()==null) || 
             (this.valorOperacion!=null &&
              this.valorOperacion.equals(other.getValorOperacion()))) &&
            this.monto == other.getMonto() &&
            this.numOperacionPago == other.getNumOperacionPago();
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
        if (getTipoOperacion() != null) {
            _hashCode += getTipoOperacion().hashCode();
        }
        if (getValorOperacion() != null) {
            _hashCode += getValorOperacion().hashCode();
        }
        _hashCode += new Long(getMonto()).hashCode();
        _hashCode += new Long(getNumOperacionPago()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperacionIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.example.org/ValidarRecargaWS/", "OperacionIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numOperacionPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numOperacionPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
