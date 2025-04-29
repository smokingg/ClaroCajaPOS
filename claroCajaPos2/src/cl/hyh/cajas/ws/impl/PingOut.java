/**
 * PingOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class PingOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private long cheques;

    private long chequesFecha;

    private long efectivo;

    public PingOut() {
    }

    public PingOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           long cheques,
           long chequesFecha,
           long efectivo) {
        super(
            headerOut);
        this.cheques = cheques;
        this.chequesFecha = chequesFecha;
        this.efectivo = efectivo;
    }


    /**
     * Gets the cheques value for this PingOut.
     * 
     * @return cheques
     */
    public long getCheques() {
        return cheques;
    }


    /**
     * Sets the cheques value for this PingOut.
     * 
     * @param cheques
     */
    public void setCheques(long cheques) {
        this.cheques = cheques;
    }


    /**
     * Gets the chequesFecha value for this PingOut.
     * 
     * @return chequesFecha
     */
    public long getChequesFecha() {
        return chequesFecha;
    }


    /**
     * Sets the chequesFecha value for this PingOut.
     * 
     * @param chequesFecha
     */
    public void setChequesFecha(long chequesFecha) {
        this.chequesFecha = chequesFecha;
    }


    /**
     * Gets the efectivo value for this PingOut.
     * 
     * @return efectivo
     */
    public long getEfectivo() {
        return efectivo;
    }


    /**
     * Sets the efectivo value for this PingOut.
     * 
     * @param efectivo
     */
    public void setEfectivo(long efectivo) {
        this.efectivo = efectivo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PingOut)) return false;
        PingOut other = (PingOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.cheques == other.getCheques() &&
            this.chequesFecha == other.getChequesFecha() &&
            this.efectivo == other.getEfectivo();
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
        _hashCode += new Long(getCheques()).hashCode();
        _hashCode += new Long(getChequesFecha()).hashCode();
        _hashCode += new Long(getEfectivo()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PingOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "pingOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cheques");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cheques"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chequesFecha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chequesFecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("efectivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "efectivo"));
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
