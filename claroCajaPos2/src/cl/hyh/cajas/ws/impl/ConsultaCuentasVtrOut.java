/**
 * ConsultaCuentasVtrOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class ConsultaCuentasVtrOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private cl.hyh.cajas.ws.impl.CuentaAbonoVTR[] cuentas;

    public ConsultaCuentasVtrOut() {
    }

    public ConsultaCuentasVtrOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           cl.hyh.cajas.ws.impl.CuentaAbonoVTR[] cuentas) {
        super(
            headerOut);
        this.cuentas = cuentas;
    }


    /**
     * Gets the cuentas value for this ConsultaCuentasVtrOut.
     * 
     * @return cuentas
     */
    public cl.hyh.cajas.ws.impl.CuentaAbonoVTR[] getCuentas() {
        return cuentas;
    }


    /**
     * Sets the cuentas value for this ConsultaCuentasVtrOut.
     * 
     * @param cuentas
     */
    public void setCuentas(cl.hyh.cajas.ws.impl.CuentaAbonoVTR[] cuentas) {
        this.cuentas = cuentas;
    }

    public cl.hyh.cajas.ws.impl.CuentaAbonoVTR getCuentas(int i) {
        return this.cuentas[i];
    }

    public void setCuentas(int i, cl.hyh.cajas.ws.impl.CuentaAbonoVTR _value) {
        this.cuentas[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaCuentasVtrOut)) return false;
        ConsultaCuentasVtrOut other = (ConsultaCuentasVtrOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cuentas==null && other.getCuentas()==null) || 
             (this.cuentas!=null &&
              java.util.Arrays.equals(this.cuentas, other.getCuentas())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultaCuentasVtrOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "consultaCuentasVtrOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuentas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuentas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "cuentaAbonoVTR"));
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
