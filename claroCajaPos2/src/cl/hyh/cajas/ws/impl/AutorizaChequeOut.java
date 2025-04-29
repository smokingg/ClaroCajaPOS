/**
 * AutorizaChequeOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class AutorizaChequeOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private java.lang.String codigoAutorizacion;

    private java.lang.String glosaRechazo;

    private int rc;

    public AutorizaChequeOut() {
    }

    public AutorizaChequeOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           java.lang.String codigoAutorizacion,
           java.lang.String glosaRechazo,
           int rc) {
        super(
            headerOut);
        this.codigoAutorizacion = codigoAutorizacion;
        this.glosaRechazo = glosaRechazo;
        this.rc = rc;
    }


    /**
     * Gets the codigoAutorizacion value for this AutorizaChequeOut.
     * 
     * @return codigoAutorizacion
     */
    public java.lang.String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }


    /**
     * Sets the codigoAutorizacion value for this AutorizaChequeOut.
     * 
     * @param codigoAutorizacion
     */
    public void setCodigoAutorizacion(java.lang.String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }


    /**
     * Gets the glosaRechazo value for this AutorizaChequeOut.
     * 
     * @return glosaRechazo
     */
    public java.lang.String getGlosaRechazo() {
        return glosaRechazo;
    }


    /**
     * Sets the glosaRechazo value for this AutorizaChequeOut.
     * 
     * @param glosaRechazo
     */
    public void setGlosaRechazo(java.lang.String glosaRechazo) {
        this.glosaRechazo = glosaRechazo;
    }


    /**
     * Gets the rc value for this AutorizaChequeOut.
     * 
     * @return rc
     */
    public int getRc() {
        return rc;
    }


    /**
     * Sets the rc value for this AutorizaChequeOut.
     * 
     * @param rc
     */
    public void setRc(int rc) {
        this.rc = rc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutorizaChequeOut)) return false;
        AutorizaChequeOut other = (AutorizaChequeOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codigoAutorizacion==null && other.getCodigoAutorizacion()==null) || 
             (this.codigoAutorizacion!=null &&
              this.codigoAutorizacion.equals(other.getCodigoAutorizacion()))) &&
            ((this.glosaRechazo==null && other.getGlosaRechazo()==null) || 
             (this.glosaRechazo!=null &&
              this.glosaRechazo.equals(other.getGlosaRechazo()))) &&
            this.rc == other.getRc();
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
        if (getCodigoAutorizacion() != null) {
            _hashCode += getCodigoAutorizacion().hashCode();
        }
        if (getGlosaRechazo() != null) {
            _hashCode += getGlosaRechazo().hashCode();
        }
        _hashCode += getRc();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutorizaChequeOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "autorizaChequeOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoAutorizacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoAutorizacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("glosaRechazo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "glosaRechazo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rc"));
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
