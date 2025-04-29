/**
 * ValidarJustificacionRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ValidarJustificacionRequestDTO  implements java.io.Serializable {
    private ws.claro.cl.JustificacionOperacionDTO datosOperacion;

    private ws.claro.cl.JustificacionCierreDTO justificacion;

    public ValidarJustificacionRequestDTO() {
    }

    public ValidarJustificacionRequestDTO(
           ws.claro.cl.JustificacionOperacionDTO datosOperacion,
           ws.claro.cl.JustificacionCierreDTO justificacion) {
           this.datosOperacion = datosOperacion;
           this.justificacion = justificacion;
    }


    /**
     * Gets the datosOperacion value for this ValidarJustificacionRequestDTO.
     * 
     * @return datosOperacion
     */
    public ws.claro.cl.JustificacionOperacionDTO getDatosOperacion() {
        return datosOperacion;
    }


    /**
     * Sets the datosOperacion value for this ValidarJustificacionRequestDTO.
     * 
     * @param datosOperacion
     */
    public void setDatosOperacion(ws.claro.cl.JustificacionOperacionDTO datosOperacion) {
        this.datosOperacion = datosOperacion;
    }


    /**
     * Gets the justificacion value for this ValidarJustificacionRequestDTO.
     * 
     * @return justificacion
     */
    public ws.claro.cl.JustificacionCierreDTO getJustificacion() {
        return justificacion;
    }


    /**
     * Sets the justificacion value for this ValidarJustificacionRequestDTO.
     * 
     * @param justificacion
     */
    public void setJustificacion(ws.claro.cl.JustificacionCierreDTO justificacion) {
        this.justificacion = justificacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidarJustificacionRequestDTO)) return false;
        ValidarJustificacionRequestDTO other = (ValidarJustificacionRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.datosOperacion==null && other.getDatosOperacion()==null) || 
             (this.datosOperacion!=null &&
              this.datosOperacion.equals(other.getDatosOperacion()))) &&
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
        if (getDatosOperacion() != null) {
            _hashCode += getDatosOperacion().hashCode();
        }
        if (getJustificacion() != null) {
            _hashCode += getJustificacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidarJustificacionRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "validarJustificacionRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datosOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "justificacionOperacionDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
