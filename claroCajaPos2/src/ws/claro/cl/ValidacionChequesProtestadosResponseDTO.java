/**
 * ValidacionChequesProtestadosResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ValidacionChequesProtestadosResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private boolean hasAprobacion;

    private boolean hasProtesto;

    public ValidacionChequesProtestadosResponseDTO() {
    }

    public ValidacionChequesProtestadosResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           boolean hasAprobacion,
           boolean hasProtesto) {
        super(
            retCode,
            retDesc);
        this.hasAprobacion = hasAprobacion;
        this.hasProtesto = hasProtesto;
    }


    /**
     * Gets the hasAprobacion value for this ValidacionChequesProtestadosResponseDTO.
     * 
     * @return hasAprobacion
     */
    public boolean isHasAprobacion() {
        return hasAprobacion;
    }


    /**
     * Sets the hasAprobacion value for this ValidacionChequesProtestadosResponseDTO.
     * 
     * @param hasAprobacion
     */
    public void setHasAprobacion(boolean hasAprobacion) {
        this.hasAprobacion = hasAprobacion;
    }


    /**
     * Gets the hasProtesto value for this ValidacionChequesProtestadosResponseDTO.
     * 
     * @return hasProtesto
     */
    public boolean isHasProtesto() {
        return hasProtesto;
    }


    /**
     * Sets the hasProtesto value for this ValidacionChequesProtestadosResponseDTO.
     * 
     * @param hasProtesto
     */
    public void setHasProtesto(boolean hasProtesto) {
        this.hasProtesto = hasProtesto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidacionChequesProtestadosResponseDTO)) return false;
        ValidacionChequesProtestadosResponseDTO other = (ValidacionChequesProtestadosResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.hasAprobacion == other.isHasAprobacion() &&
            this.hasProtesto == other.isHasProtesto();
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
        _hashCode += (isHasAprobacion() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isHasProtesto() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidacionChequesProtestadosResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "validacionChequesProtestadosResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasAprobacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasAprobacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasProtesto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasProtesto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
