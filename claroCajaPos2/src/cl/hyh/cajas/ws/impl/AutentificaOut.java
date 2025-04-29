/**
 * AutentificaOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class AutentificaOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private int cajero;

    private java.lang.String perfil;

    public AutentificaOut() {
    }

    public AutentificaOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           int cajero,
           java.lang.String perfil) {
        super(
            headerOut);
        this.cajero = cajero;
        this.perfil = perfil;
    }


    /**
     * Gets the cajero value for this AutentificaOut.
     * 
     * @return cajero
     */
    public int getCajero() {
        return cajero;
    }


    /**
     * Sets the cajero value for this AutentificaOut.
     * 
     * @param cajero
     */
    public void setCajero(int cajero) {
        this.cajero = cajero;
    }


    /**
     * Gets the perfil value for this AutentificaOut.
     * 
     * @return perfil
     */
    public java.lang.String getPerfil() {
        return perfil;
    }


    /**
     * Sets the perfil value for this AutentificaOut.
     * 
     * @param perfil
     */
    public void setPerfil(java.lang.String perfil) {
        this.perfil = perfil;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutentificaOut)) return false;
        AutentificaOut other = (AutentificaOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.cajero == other.getCajero() &&
            ((this.perfil==null && other.getPerfil()==null) || 
             (this.perfil!=null &&
              this.perfil.equals(other.getPerfil())));
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
        _hashCode += getCajero();
        if (getPerfil() != null) {
            _hashCode += getPerfil().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutentificaOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "autentificaOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cajero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cajero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perfil");
        elemField.setXmlName(new javax.xml.namespace.QName("", "perfil"));
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
