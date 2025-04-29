/**
 * ConsultaSaldoFavorVtrOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class ConsultaSaldoFavorVtrOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private cl.hyh.cajas.ws.impl.CuentaVTR[] saldosFavor;

    public ConsultaSaldoFavorVtrOut() {
    }

    public ConsultaSaldoFavorVtrOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           cl.hyh.cajas.ws.impl.CuentaVTR[] saldosFavor) {
        super(
            headerOut);
        this.saldosFavor = saldosFavor;
    }


    /**
     * Gets the saldosFavor value for this ConsultaSaldoFavorVtrOut.
     * 
     * @return saldosFavor
     */
    public cl.hyh.cajas.ws.impl.CuentaVTR[] getSaldosFavor() {
        return saldosFavor;
    }


    /**
     * Sets the saldosFavor value for this ConsultaSaldoFavorVtrOut.
     * 
     * @param saldosFavor
     */
    public void setSaldosFavor(cl.hyh.cajas.ws.impl.CuentaVTR[] saldosFavor) {
        this.saldosFavor = saldosFavor;
    }

    public cl.hyh.cajas.ws.impl.CuentaVTR getSaldosFavor(int i) {
        return this.saldosFavor[i];
    }

    public void setSaldosFavor(int i, cl.hyh.cajas.ws.impl.CuentaVTR _value) {
        this.saldosFavor[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaSaldoFavorVtrOut)) return false;
        ConsultaSaldoFavorVtrOut other = (ConsultaSaldoFavorVtrOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.saldosFavor==null && other.getSaldosFavor()==null) || 
             (this.saldosFavor!=null &&
              java.util.Arrays.equals(this.saldosFavor, other.getSaldosFavor())));
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
        if (getSaldosFavor() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSaldosFavor());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSaldosFavor(), i);
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
        new org.apache.axis.description.TypeDesc(ConsultaSaldoFavorVtrOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "consultaSaldoFavorVtrOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldosFavor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldosFavor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "cuentaVTR"));
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
