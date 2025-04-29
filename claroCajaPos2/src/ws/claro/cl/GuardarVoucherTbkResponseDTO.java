/**
 * GuardarVoucherTbkResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class GuardarVoucherTbkResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private java.lang.String idCompoperacion;

    public GuardarVoucherTbkResponseDTO() {
    }

    public GuardarVoucherTbkResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           java.lang.String idCompoperacion) {
        super(
            retCode,
            retDesc);
        this.idCompoperacion = idCompoperacion;
    }


    /**
     * Gets the idCompoperacion value for this GuardarVoucherTbkResponseDTO.
     * 
     * @return idCompoperacion
     */
    public java.lang.String getIdCompoperacion() {
        return idCompoperacion;
    }


    /**
     * Sets the idCompoperacion value for this GuardarVoucherTbkResponseDTO.
     * 
     * @param idCompoperacion
     */
    public void setIdCompoperacion(java.lang.String idCompoperacion) {
        this.idCompoperacion = idCompoperacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GuardarVoucherTbkResponseDTO)) return false;
        GuardarVoucherTbkResponseDTO other = (GuardarVoucherTbkResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idCompoperacion==null && other.getIdCompoperacion()==null) || 
             (this.idCompoperacion!=null &&
              this.idCompoperacion.equals(other.getIdCompoperacion())));
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
        if (getIdCompoperacion() != null) {
            _hashCode += getIdCompoperacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GuardarVoucherTbkResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "guardarVoucherTbkResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCompoperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCompoperacion"));
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
