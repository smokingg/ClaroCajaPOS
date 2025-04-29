/**
 * ConsultarAprobacionChInDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ConsultarAprobacionChInDTO  implements java.io.Serializable {
    private java.lang.String codigoAprobacion;

    public ConsultarAprobacionChInDTO() {
    }

    public ConsultarAprobacionChInDTO(
           java.lang.String codigoAprobacion) {
           this.codigoAprobacion = codigoAprobacion;
    }


    /**
     * Gets the codigoAprobacion value for this ConsultarAprobacionChInDTO.
     * 
     * @return codigoAprobacion
     */
    public java.lang.String getCodigoAprobacion() {
        return codigoAprobacion;
    }


    /**
     * Sets the codigoAprobacion value for this ConsultarAprobacionChInDTO.
     * 
     * @param codigoAprobacion
     */
    public void setCodigoAprobacion(java.lang.String codigoAprobacion) {
        this.codigoAprobacion = codigoAprobacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultarAprobacionChInDTO)) return false;
        ConsultarAprobacionChInDTO other = (ConsultarAprobacionChInDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoAprobacion==null && other.getCodigoAprobacion()==null) || 
             (this.codigoAprobacion!=null &&
              this.codigoAprobacion.equals(other.getCodigoAprobacion())));
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
        if (getCodigoAprobacion() != null) {
            _hashCode += getCodigoAprobacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultarAprobacionChInDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "consultarAprobacionChInDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoAprobacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoAprobacion"));
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
