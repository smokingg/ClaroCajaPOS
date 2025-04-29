/**
 * UltimaSesionResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class UltimaSesionResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private ws.claro.cl.SesionDTO sesion;

    public UltimaSesionResponseDTO() {
    }

    public UltimaSesionResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           ws.claro.cl.SesionDTO sesion) {
        super(
            retCode,
            retDesc);
        this.sesion = sesion;
    }


    /**
     * Gets the sesion value for this UltimaSesionResponseDTO.
     * 
     * @return sesion
     */
    public ws.claro.cl.SesionDTO getSesion() {
        return sesion;
    }


    /**
     * Sets the sesion value for this UltimaSesionResponseDTO.
     * 
     * @param sesion
     */
    public void setSesion(ws.claro.cl.SesionDTO sesion) {
        this.sesion = sesion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UltimaSesionResponseDTO)) return false;
        UltimaSesionResponseDTO other = (UltimaSesionResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.sesion==null && other.getSesion()==null) || 
             (this.sesion!=null &&
              this.sesion.equals(other.getSesion())));
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
        if (getSesion() != null) {
            _hashCode += getSesion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UltimaSesionResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "ultimaSesionResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sesion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sesion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "sesionDTO"));
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
