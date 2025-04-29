/**
 * ValidarSencilloCajeroResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ValidarSencilloCajeroResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private boolean sencilloIngresado;

    public ValidarSencilloCajeroResponseDTO() {
    }

    public ValidarSencilloCajeroResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           boolean sencilloIngresado) {
        super(
            retCode,
            retDesc);
        this.sencilloIngresado = sencilloIngresado;
    }


    /**
     * Gets the sencilloIngresado value for this ValidarSencilloCajeroResponseDTO.
     * 
     * @return sencilloIngresado
     */
    public boolean isSencilloIngresado() {
        return sencilloIngresado;
    }


    /**
     * Sets the sencilloIngresado value for this ValidarSencilloCajeroResponseDTO.
     * 
     * @param sencilloIngresado
     */
    public void setSencilloIngresado(boolean sencilloIngresado) {
        this.sencilloIngresado = sencilloIngresado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidarSencilloCajeroResponseDTO)) return false;
        ValidarSencilloCajeroResponseDTO other = (ValidarSencilloCajeroResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.sencilloIngresado == other.isSencilloIngresado();
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
        _hashCode += (isSencilloIngresado() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidarSencilloCajeroResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "validarSencilloCajeroResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sencilloIngresado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sencilloIngresado"));
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
