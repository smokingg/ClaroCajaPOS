/**
 * JustificacionDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class JustificacionDTO  extends ws.claro.cl.JustificacionCierreDTO  implements java.io.Serializable {
    private long idJustificacion;

    private ws.claro.cl.MotivoJustificacionDTO motivo;

    public JustificacionDTO() {
    }

    public JustificacionDTO(
           java.lang.String estado,
           java.lang.String fechaRegistro,
           long idAgencia,
           java.lang.String idMedioPago,
           long idMotivoJustificacion,
           long idOperacion,
           long idUsuario,
           long montoJustificado,
           java.lang.String observacion,
           long idJustificacion,
           ws.claro.cl.MotivoJustificacionDTO motivo) {
        super(
            estado,
            fechaRegistro,
            idAgencia,
            idMedioPago,
            idMotivoJustificacion,
            idOperacion,
            idUsuario,
            montoJustificado,
            observacion);
        this.idJustificacion = idJustificacion;
        this.motivo = motivo;
    }


    /**
     * Gets the idJustificacion value for this JustificacionDTO.
     * 
     * @return idJustificacion
     */
    public long getIdJustificacion() {
        return idJustificacion;
    }


    /**
     * Sets the idJustificacion value for this JustificacionDTO.
     * 
     * @param idJustificacion
     */
    public void setIdJustificacion(long idJustificacion) {
        this.idJustificacion = idJustificacion;
    }


    /**
     * Gets the motivo value for this JustificacionDTO.
     * 
     * @return motivo
     */
    public ws.claro.cl.MotivoJustificacionDTO getMotivo() {
        return motivo;
    }


    /**
     * Sets the motivo value for this JustificacionDTO.
     * 
     * @param motivo
     */
    public void setMotivo(ws.claro.cl.MotivoJustificacionDTO motivo) {
        this.motivo = motivo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JustificacionDTO)) return false;
        JustificacionDTO other = (JustificacionDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.idJustificacion == other.getIdJustificacion() &&
            ((this.motivo==null && other.getMotivo()==null) || 
             (this.motivo!=null &&
              this.motivo.equals(other.getMotivo())));
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
        _hashCode += new Long(getIdJustificacion()).hashCode();
        if (getMotivo() != null) {
            _hashCode += getMotivo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JustificacionDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "justificacionDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idJustificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idJustificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "motivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "motivoJustificacionDTO"));
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
