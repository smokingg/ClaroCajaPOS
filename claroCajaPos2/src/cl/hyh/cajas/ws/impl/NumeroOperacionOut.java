/**
 * NumeroOperacionOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class NumeroOperacionOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private long numeroOperacion;

    private long numeroOperacionReversa;

    public NumeroOperacionOut() {
    }

    public NumeroOperacionOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           long numeroOperacion,
           long numeroOperacionReversa) {
        super(
            headerOut);
        this.numeroOperacion = numeroOperacion;
        this.numeroOperacionReversa = numeroOperacionReversa;
    }


    /**
     * Gets the numeroOperacion value for this NumeroOperacionOut.
     * 
     * @return numeroOperacion
     */
    public long getNumeroOperacion() {
        return numeroOperacion;
    }


    /**
     * Sets the numeroOperacion value for this NumeroOperacionOut.
     * 
     * @param numeroOperacion
     */
    public void setNumeroOperacion(long numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }


    /**
     * Gets the numeroOperacionReversa value for this NumeroOperacionOut.
     * 
     * @return numeroOperacionReversa
     */
    public long getNumeroOperacionReversa() {
        return numeroOperacionReversa;
    }


    /**
     * Sets the numeroOperacionReversa value for this NumeroOperacionOut.
     * 
     * @param numeroOperacionReversa
     */
    public void setNumeroOperacionReversa(long numeroOperacionReversa) {
        this.numeroOperacionReversa = numeroOperacionReversa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NumeroOperacionOut)) return false;
        NumeroOperacionOut other = (NumeroOperacionOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.numeroOperacion == other.getNumeroOperacion() &&
            this.numeroOperacionReversa == other.getNumeroOperacionReversa();
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
        _hashCode += new Long(getNumeroOperacion()).hashCode();
        _hashCode += new Long(getNumeroOperacionReversa()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NumeroOperacionOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "numeroOperacionOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroOperacionReversa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroOperacionReversa"));
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
