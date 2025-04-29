/**
 * Respuesta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne;

public class Respuesta  implements java.io.Serializable {
    private int retCode;

    private java.lang.String retDescError;

    public Respuesta() {
    }

    public Respuesta(
           int retCode,
           java.lang.String retDescError) {
           this.retCode = retCode;
           this.retDescError = retDescError;
    }


    /**
     * Gets the retCode value for this Respuesta.
     * 
     * @return retCode
     */
    public int getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this Respuesta.
     * 
     * @param retCode
     */
    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDescError value for this Respuesta.
     * 
     * @return retDescError
     */
    public java.lang.String getRetDescError() {
        return retDescError;
    }


    /**
     * Sets the retDescError value for this Respuesta.
     * 
     * @param retDescError
     */
    public void setRetDescError(java.lang.String retDescError) {
        this.retDescError = retDescError;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Respuesta)) return false;
        Respuesta other = (Respuesta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.retCode == other.getRetCode() &&
            ((this.retDescError==null && other.getRetDescError()==null) || 
             (this.retDescError!=null &&
              this.retDescError.equals(other.getRetDescError())));
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
        _hashCode += getRetCode();
        if (getRetDescError() != null) {
            _hashCode += getRetDescError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Respuesta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultaNVOne/", "Respuesta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retDescError");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retDescError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
