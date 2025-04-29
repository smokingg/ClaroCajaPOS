/**
 * ObtenerAuthMailResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ObtenerAuthMailResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private java.lang.String mapa;

    public ObtenerAuthMailResponseDTO() {
    }

    public ObtenerAuthMailResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           java.lang.String mapa) {
        super(
            retCode,
            retDesc);
        this.mapa = mapa;
    }


    /**
     * Gets the mapa value for this ObtenerAuthMailResponseDTO.
     * 
     * @return mapa
     */
    public java.lang.String getMapa() {
        return mapa;
    }


    /**
     * Sets the mapa value for this ObtenerAuthMailResponseDTO.
     * 
     * @param mapa
     */
    public void setMapa(java.lang.String mapa) {
        this.mapa = mapa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ObtenerAuthMailResponseDTO)) return false;
        ObtenerAuthMailResponseDTO other = (ObtenerAuthMailResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.mapa==null && other.getMapa()==null) || 
             (this.mapa!=null &&
              this.mapa.equals(other.getMapa())));
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
        if (getMapa() != null) {
            _hashCode += getMapa().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ObtenerAuthMailResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "obtenerAuthMailResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mapa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mapa"));
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
