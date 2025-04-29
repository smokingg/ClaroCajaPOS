/**
 * RegistrarJustificacionResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class RegistrarJustificacionResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private long idJustificacionCierre;

    public RegistrarJustificacionResponseDTO() {
    }

    public RegistrarJustificacionResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           long idJustificacionCierre) {
        super(
            retCode,
            retDesc);
        this.idJustificacionCierre = idJustificacionCierre;
    }


    /**
     * Gets the idJustificacionCierre value for this RegistrarJustificacionResponseDTO.
     * 
     * @return idJustificacionCierre
     */
    public long getIdJustificacionCierre() {
        return idJustificacionCierre;
    }


    /**
     * Sets the idJustificacionCierre value for this RegistrarJustificacionResponseDTO.
     * 
     * @param idJustificacionCierre
     */
    public void setIdJustificacionCierre(long idJustificacionCierre) {
        this.idJustificacionCierre = idJustificacionCierre;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistrarJustificacionResponseDTO)) return false;
        RegistrarJustificacionResponseDTO other = (RegistrarJustificacionResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.idJustificacionCierre == other.getIdJustificacionCierre();
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
        _hashCode += new Long(getIdJustificacionCierre()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistrarJustificacionResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "registrarJustificacionResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idJustificacionCierre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idJustificacionCierre"));
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
