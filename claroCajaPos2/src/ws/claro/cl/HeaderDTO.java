/**
 * HeaderDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class HeaderDTO  implements java.io.Serializable {
    private java.lang.String agencia;

    private java.lang.String cajaFisica;

    private java.lang.String cajero;

    private java.lang.String entidad;

    private java.lang.String recaudador;

    private java.lang.String session;

    private java.lang.String usuario;

    public HeaderDTO() {
    }

    public HeaderDTO(
           java.lang.String agencia,
           java.lang.String cajaFisica,
           java.lang.String cajero,
           java.lang.String entidad,
           java.lang.String recaudador,
           java.lang.String session,
           java.lang.String usuario) {
           this.agencia = agencia;
           this.cajaFisica = cajaFisica;
           this.cajero = cajero;
           this.entidad = entidad;
           this.recaudador = recaudador;
           this.session = session;
           this.usuario = usuario;
    }


    /**
     * Gets the agencia value for this HeaderDTO.
     * 
     * @return agencia
     */
    public java.lang.String getAgencia() {
        return agencia;
    }


    /**
     * Sets the agencia value for this HeaderDTO.
     * 
     * @param agencia
     */
    public void setAgencia(java.lang.String agencia) {
        this.agencia = agencia;
    }


    /**
     * Gets the cajaFisica value for this HeaderDTO.
     * 
     * @return cajaFisica
     */
    public java.lang.String getCajaFisica() {
        return cajaFisica;
    }


    /**
     * Sets the cajaFisica value for this HeaderDTO.
     * 
     * @param cajaFisica
     */
    public void setCajaFisica(java.lang.String cajaFisica) {
        this.cajaFisica = cajaFisica;
    }


    /**
     * Gets the cajero value for this HeaderDTO.
     * 
     * @return cajero
     */
    public java.lang.String getCajero() {
        return cajero;
    }


    /**
     * Sets the cajero value for this HeaderDTO.
     * 
     * @param cajero
     */
    public void setCajero(java.lang.String cajero) {
        this.cajero = cajero;
    }


    /**
     * Gets the entidad value for this HeaderDTO.
     * 
     * @return entidad
     */
    public java.lang.String getEntidad() {
        return entidad;
    }


    /**
     * Sets the entidad value for this HeaderDTO.
     * 
     * @param entidad
     */
    public void setEntidad(java.lang.String entidad) {
        this.entidad = entidad;
    }


    /**
     * Gets the recaudador value for this HeaderDTO.
     * 
     * @return recaudador
     */
    public java.lang.String getRecaudador() {
        return recaudador;
    }


    /**
     * Sets the recaudador value for this HeaderDTO.
     * 
     * @param recaudador
     */
    public void setRecaudador(java.lang.String recaudador) {
        this.recaudador = recaudador;
    }


    /**
     * Gets the session value for this HeaderDTO.
     * 
     * @return session
     */
    public java.lang.String getSession() {
        return session;
    }


    /**
     * Sets the session value for this HeaderDTO.
     * 
     * @param session
     */
    public void setSession(java.lang.String session) {
        this.session = session;
    }


    /**
     * Gets the usuario value for this HeaderDTO.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this HeaderDTO.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HeaderDTO)) return false;
        HeaderDTO other = (HeaderDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agencia==null && other.getAgencia()==null) || 
             (this.agencia!=null &&
              this.agencia.equals(other.getAgencia()))) &&
            ((this.cajaFisica==null && other.getCajaFisica()==null) || 
             (this.cajaFisica!=null &&
              this.cajaFisica.equals(other.getCajaFisica()))) &&
            ((this.cajero==null && other.getCajero()==null) || 
             (this.cajero!=null &&
              this.cajero.equals(other.getCajero()))) &&
            ((this.entidad==null && other.getEntidad()==null) || 
             (this.entidad!=null &&
              this.entidad.equals(other.getEntidad()))) &&
            ((this.recaudador==null && other.getRecaudador()==null) || 
             (this.recaudador!=null &&
              this.recaudador.equals(other.getRecaudador()))) &&
            ((this.session==null && other.getSession()==null) || 
             (this.session!=null &&
              this.session.equals(other.getSession()))) &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario())));
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
        if (getAgencia() != null) {
            _hashCode += getAgencia().hashCode();
        }
        if (getCajaFisica() != null) {
            _hashCode += getCajaFisica().hashCode();
        }
        if (getCajero() != null) {
            _hashCode += getCajero().hashCode();
        }
        if (getEntidad() != null) {
            _hashCode += getEntidad().hashCode();
        }
        if (getRecaudador() != null) {
            _hashCode += getRecaudador().hashCode();
        }
        if (getSession() != null) {
            _hashCode += getSession().hashCode();
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HeaderDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "headerDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cajaFisica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cajaFisica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cajero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cajero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entidad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recaudador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recaudador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("session");
        elemField.setXmlName(new javax.xml.namespace.QName("", "session"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usuario"));
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
