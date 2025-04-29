/**
 * MotivoJustificacionDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class MotivoJustificacionDTO  implements java.io.Serializable {
    private java.lang.String descMotivoJustificacion;

    private java.lang.String estado;

    private java.lang.String fecha;

    private long idMotivoJustificacion;

    private long idUsuario;

    private ws.claro.cl.TipoJustifacionDTO tipoJustificacion;

    public MotivoJustificacionDTO() {
    }

    public MotivoJustificacionDTO(
           java.lang.String descMotivoJustificacion,
           java.lang.String estado,
           java.lang.String fecha,
           long idMotivoJustificacion,
           long idUsuario,
           ws.claro.cl.TipoJustifacionDTO tipoJustificacion) {
           this.descMotivoJustificacion = descMotivoJustificacion;
           this.estado = estado;
           this.fecha = fecha;
           this.idMotivoJustificacion = idMotivoJustificacion;
           this.idUsuario = idUsuario;
           this.tipoJustificacion = tipoJustificacion;
    }


    /**
     * Gets the descMotivoJustificacion value for this MotivoJustificacionDTO.
     * 
     * @return descMotivoJustificacion
     */
    public java.lang.String getDescMotivoJustificacion() {
        return descMotivoJustificacion;
    }


    /**
     * Sets the descMotivoJustificacion value for this MotivoJustificacionDTO.
     * 
     * @param descMotivoJustificacion
     */
    public void setDescMotivoJustificacion(java.lang.String descMotivoJustificacion) {
        this.descMotivoJustificacion = descMotivoJustificacion;
    }


    /**
     * Gets the estado value for this MotivoJustificacionDTO.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this MotivoJustificacionDTO.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the fecha value for this MotivoJustificacionDTO.
     * 
     * @return fecha
     */
    public java.lang.String getFecha() {
        return fecha;
    }


    /**
     * Sets the fecha value for this MotivoJustificacionDTO.
     * 
     * @param fecha
     */
    public void setFecha(java.lang.String fecha) {
        this.fecha = fecha;
    }


    /**
     * Gets the idMotivoJustificacion value for this MotivoJustificacionDTO.
     * 
     * @return idMotivoJustificacion
     */
    public long getIdMotivoJustificacion() {
        return idMotivoJustificacion;
    }


    /**
     * Sets the idMotivoJustificacion value for this MotivoJustificacionDTO.
     * 
     * @param idMotivoJustificacion
     */
    public void setIdMotivoJustificacion(long idMotivoJustificacion) {
        this.idMotivoJustificacion = idMotivoJustificacion;
    }


    /**
     * Gets the idUsuario value for this MotivoJustificacionDTO.
     * 
     * @return idUsuario
     */
    public long getIdUsuario() {
        return idUsuario;
    }


    /**
     * Sets the idUsuario value for this MotivoJustificacionDTO.
     * 
     * @param idUsuario
     */
    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }


    /**
     * Gets the tipoJustificacion value for this MotivoJustificacionDTO.
     * 
     * @return tipoJustificacion
     */
    public ws.claro.cl.TipoJustifacionDTO getTipoJustificacion() {
        return tipoJustificacion;
    }


    /**
     * Sets the tipoJustificacion value for this MotivoJustificacionDTO.
     * 
     * @param tipoJustificacion
     */
    public void setTipoJustificacion(ws.claro.cl.TipoJustifacionDTO tipoJustificacion) {
        this.tipoJustificacion = tipoJustificacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MotivoJustificacionDTO)) return false;
        MotivoJustificacionDTO other = (MotivoJustificacionDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.descMotivoJustificacion==null && other.getDescMotivoJustificacion()==null) || 
             (this.descMotivoJustificacion!=null &&
              this.descMotivoJustificacion.equals(other.getDescMotivoJustificacion()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.fecha==null && other.getFecha()==null) || 
             (this.fecha!=null &&
              this.fecha.equals(other.getFecha()))) &&
            this.idMotivoJustificacion == other.getIdMotivoJustificacion() &&
            this.idUsuario == other.getIdUsuario() &&
            ((this.tipoJustificacion==null && other.getTipoJustificacion()==null) || 
             (this.tipoJustificacion!=null &&
              this.tipoJustificacion.equals(other.getTipoJustificacion())));
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
        if (getDescMotivoJustificacion() != null) {
            _hashCode += getDescMotivoJustificacion().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        _hashCode += new Long(getIdMotivoJustificacion()).hashCode();
        _hashCode += new Long(getIdUsuario()).hashCode();
        if (getTipoJustificacion() != null) {
            _hashCode += getTipoJustificacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MotivoJustificacionDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "motivoJustificacionDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descMotivoJustificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descMotivoJustificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fecha"));
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
        elemField.setFieldName("idUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUsuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoJustificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoJustificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "tipoJustifacionDTO"));
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
