/**
 * JustificacionCierreDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class JustificacionCierreDTO  implements java.io.Serializable {
    private java.lang.String estado;

    private java.lang.String fechaRegistro;

    private long idAgencia;

    private java.lang.String idMedioPago;

    private long idMotivoJustificacion;

    private long idOperacion;

    private long idUsuario;

    private long montoJustificado;

    private java.lang.String observacion;

    public JustificacionCierreDTO() {
    }

    public JustificacionCierreDTO(
           java.lang.String estado,
           java.lang.String fechaRegistro,
           long idAgencia,
           java.lang.String idMedioPago,
           long idMotivoJustificacion,
           long idOperacion,
           long idUsuario,
           long montoJustificado,
           java.lang.String observacion) {
           this.estado = estado;
           this.fechaRegistro = fechaRegistro;
           this.idAgencia = idAgencia;
           this.idMedioPago = idMedioPago;
           this.idMotivoJustificacion = idMotivoJustificacion;
           this.idOperacion = idOperacion;
           this.idUsuario = idUsuario;
           this.montoJustificado = montoJustificado;
           this.observacion = observacion;
    }


    /**
     * Gets the estado value for this JustificacionCierreDTO.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this JustificacionCierreDTO.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the fechaRegistro value for this JustificacionCierreDTO.
     * 
     * @return fechaRegistro
     */
    public java.lang.String getFechaRegistro() {
        return fechaRegistro;
    }


    /**
     * Sets the fechaRegistro value for this JustificacionCierreDTO.
     * 
     * @param fechaRegistro
     */
    public void setFechaRegistro(java.lang.String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    /**
     * Gets the idAgencia value for this JustificacionCierreDTO.
     * 
     * @return idAgencia
     */
    public long getIdAgencia() {
        return idAgencia;
    }


    /**
     * Sets the idAgencia value for this JustificacionCierreDTO.
     * 
     * @param idAgencia
     */
    public void setIdAgencia(long idAgencia) {
        this.idAgencia = idAgencia;
    }


    /**
     * Gets the idMedioPago value for this JustificacionCierreDTO.
     * 
     * @return idMedioPago
     */
    public java.lang.String getIdMedioPago() {
        return idMedioPago;
    }


    /**
     * Sets the idMedioPago value for this JustificacionCierreDTO.
     * 
     * @param idMedioPago
     */
    public void setIdMedioPago(java.lang.String idMedioPago) {
        this.idMedioPago = idMedioPago;
    }


    /**
     * Gets the idMotivoJustificacion value for this JustificacionCierreDTO.
     * 
     * @return idMotivoJustificacion
     */
    public long getIdMotivoJustificacion() {
        return idMotivoJustificacion;
    }


    /**
     * Sets the idMotivoJustificacion value for this JustificacionCierreDTO.
     * 
     * @param idMotivoJustificacion
     */
    public void setIdMotivoJustificacion(long idMotivoJustificacion) {
        this.idMotivoJustificacion = idMotivoJustificacion;
    }


    /**
     * Gets the idOperacion value for this JustificacionCierreDTO.
     * 
     * @return idOperacion
     */
    public long getIdOperacion() {
        return idOperacion;
    }


    /**
     * Sets the idOperacion value for this JustificacionCierreDTO.
     * 
     * @param idOperacion
     */
    public void setIdOperacion(long idOperacion) {
        this.idOperacion = idOperacion;
    }


    /**
     * Gets the idUsuario value for this JustificacionCierreDTO.
     * 
     * @return idUsuario
     */
    public long getIdUsuario() {
        return idUsuario;
    }


    /**
     * Sets the idUsuario value for this JustificacionCierreDTO.
     * 
     * @param idUsuario
     */
    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }


    /**
     * Gets the montoJustificado value for this JustificacionCierreDTO.
     * 
     * @return montoJustificado
     */
    public long getMontoJustificado() {
        return montoJustificado;
    }


    /**
     * Sets the montoJustificado value for this JustificacionCierreDTO.
     * 
     * @param montoJustificado
     */
    public void setMontoJustificado(long montoJustificado) {
        this.montoJustificado = montoJustificado;
    }


    /**
     * Gets the observacion value for this JustificacionCierreDTO.
     * 
     * @return observacion
     */
    public java.lang.String getObservacion() {
        return observacion;
    }


    /**
     * Sets the observacion value for this JustificacionCierreDTO.
     * 
     * @param observacion
     */
    public void setObservacion(java.lang.String observacion) {
        this.observacion = observacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JustificacionCierreDTO)) return false;
        JustificacionCierreDTO other = (JustificacionCierreDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.fechaRegistro==null && other.getFechaRegistro()==null) || 
             (this.fechaRegistro!=null &&
              this.fechaRegistro.equals(other.getFechaRegistro()))) &&
            this.idAgencia == other.getIdAgencia() &&
            ((this.idMedioPago==null && other.getIdMedioPago()==null) || 
             (this.idMedioPago!=null &&
              this.idMedioPago.equals(other.getIdMedioPago()))) &&
            this.idMotivoJustificacion == other.getIdMotivoJustificacion() &&
            this.idOperacion == other.getIdOperacion() &&
            this.idUsuario == other.getIdUsuario() &&
            this.montoJustificado == other.getMontoJustificado() &&
            ((this.observacion==null && other.getObservacion()==null) || 
             (this.observacion!=null &&
              this.observacion.equals(other.getObservacion())));
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
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getFechaRegistro() != null) {
            _hashCode += getFechaRegistro().hashCode();
        }
        _hashCode += new Long(getIdAgencia()).hashCode();
        if (getIdMedioPago() != null) {
            _hashCode += getIdMedioPago().hashCode();
        }
        _hashCode += new Long(getIdMotivoJustificacion()).hashCode();
        _hashCode += new Long(getIdOperacion()).hashCode();
        _hashCode += new Long(getIdUsuario()).hashCode();
        _hashCode += new Long(getMontoJustificado()).hashCode();
        if (getObservacion() != null) {
            _hashCode += getObservacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JustificacionCierreDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "justificacionCierreDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAgencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAgencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMedioPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMedioPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMotivoJustificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMotivoJustificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUsuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("montoJustificado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "montoJustificado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("observacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "observacion"));
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
