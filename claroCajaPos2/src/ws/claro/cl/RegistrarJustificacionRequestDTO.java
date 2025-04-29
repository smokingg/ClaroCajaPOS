/**
 * RegistrarJustificacionRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class RegistrarJustificacionRequestDTO  implements java.io.Serializable {
    private ws.claro.cl.JustificacionCierreDTO justificacion;

    public RegistrarJustificacionRequestDTO() {
    }

    public RegistrarJustificacionRequestDTO(
           ws.claro.cl.JustificacionCierreDTO justificacion) {
           this.justificacion = justificacion;
    }


    /**
     * Gets the justificacion value for this RegistrarJustificacionRequestDTO.
     * 
     * @return justificacion
     */
    public ws.claro.cl.JustificacionCierreDTO getJustificacion() {
        return justificacion;
    }


    /**
     * Sets the justificacion value for this RegistrarJustificacionRequestDTO.
     * 
     * @param justificacion
     */
    public void setJustificacion(ws.claro.cl.JustificacionCierreDTO justificacion) {
        this.justificacion = justificacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistrarJustificacionRequestDTO)) return false;
        RegistrarJustificacionRequestDTO other = (RegistrarJustificacionRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.justificacion==null && other.getJustificacion()==null) || 
             (this.justificacion!=null &&
              this.justificacion.equals(other.getJustificacion())));
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
        if (getJustificacion() != null) {
            _hashCode += getJustificacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistrarJustificacionRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "registrarJustificacionRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "justificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "justificacionCierreDTO"));
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
