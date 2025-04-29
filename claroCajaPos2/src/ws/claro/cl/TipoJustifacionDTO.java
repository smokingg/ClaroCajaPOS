/**
 * TipoJustifacionDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class TipoJustifacionDTO  implements java.io.Serializable {
    private java.lang.String descTipoJustificacion;

    private long idTipoJustificacion;

    private long idUsuario;

    public TipoJustifacionDTO() {
    }

    public TipoJustifacionDTO(
           java.lang.String descTipoJustificacion,
           long idTipoJustificacion,
           long idUsuario) {
           this.descTipoJustificacion = descTipoJustificacion;
           this.idTipoJustificacion = idTipoJustificacion;
           this.idUsuario = idUsuario;
    }


    /**
     * Gets the descTipoJustificacion value for this TipoJustifacionDTO.
     * 
     * @return descTipoJustificacion
     */
    public java.lang.String getDescTipoJustificacion() {
        return descTipoJustificacion;
    }


    /**
     * Sets the descTipoJustificacion value for this TipoJustifacionDTO.
     * 
     * @param descTipoJustificacion
     */
    public void setDescTipoJustificacion(java.lang.String descTipoJustificacion) {
        this.descTipoJustificacion = descTipoJustificacion;
    }


    /**
     * Gets the idTipoJustificacion value for this TipoJustifacionDTO.
     * 
     * @return idTipoJustificacion
     */
    public long getIdTipoJustificacion() {
        return idTipoJustificacion;
    }


    /**
     * Sets the idTipoJustificacion value for this TipoJustifacionDTO.
     * 
     * @param idTipoJustificacion
     */
    public void setIdTipoJustificacion(long idTipoJustificacion) {
        this.idTipoJustificacion = idTipoJustificacion;
    }


    /**
     * Gets the idUsuario value for this TipoJustifacionDTO.
     * 
     * @return idUsuario
     */
    public long getIdUsuario() {
        return idUsuario;
    }


    /**
     * Sets the idUsuario value for this TipoJustifacionDTO.
     * 
     * @param idUsuario
     */
    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoJustifacionDTO)) return false;
        TipoJustifacionDTO other = (TipoJustifacionDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.descTipoJustificacion==null && other.getDescTipoJustificacion()==null) || 
             (this.descTipoJustificacion!=null &&
              this.descTipoJustificacion.equals(other.getDescTipoJustificacion()))) &&
            this.idTipoJustificacion == other.getIdTipoJustificacion() &&
            this.idUsuario == other.getIdUsuario();
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
        if (getDescTipoJustificacion() != null) {
            _hashCode += getDescTipoJustificacion().hashCode();
        }
        _hashCode += new Long(getIdTipoJustificacion()).hashCode();
        _hashCode += new Long(getIdUsuario()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoJustifacionDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "tipoJustifacionDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descTipoJustificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descTipoJustificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoJustificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoJustificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUsuario"));
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
