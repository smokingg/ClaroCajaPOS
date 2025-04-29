/**
 * HeaderIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class HeaderIn  implements java.io.Serializable {
    private int agencia;

    private int cajaFisica;

    private int cajero;

    private int entidad;

    private java.lang.String recaudador;

    private int session;

    private java.lang.String usuario;

    public HeaderIn() {
    }

    public HeaderIn(
           int agencia,
           int cajaFisica,
           int cajero,
           int entidad,
           java.lang.String recaudador,
           int session,
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
     * Gets the agencia value for this HeaderIn.
     * 
     * @return agencia
     */
    public int getAgencia() {
        return agencia;
    }


    /**
     * Sets the agencia value for this HeaderIn.
     * 
     * @param agencia
     */
    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }


    /**
     * Gets the cajaFisica value for this HeaderIn.
     * 
     * @return cajaFisica
     */
    public int getCajaFisica() {
        return cajaFisica;
    }


    /**
     * Sets the cajaFisica value for this HeaderIn.
     * 
     * @param cajaFisica
     */
    public void setCajaFisica(int cajaFisica) {
        this.cajaFisica = cajaFisica;
    }


    /**
     * Gets the cajero value for this HeaderIn.
     * 
     * @return cajero
     */
    public int getCajero() {
        return cajero;
    }


    /**
     * Sets the cajero value for this HeaderIn.
     * 
     * @param cajero
     */
    public void setCajero(int cajero) {
        this.cajero = cajero;
    }


    /**
     * Gets the entidad value for this HeaderIn.
     * 
     * @return entidad
     */
    public int getEntidad() {
        return entidad;
    }


    /**
     * Sets the entidad value for this HeaderIn.
     * 
     * @param entidad
     */
    public void setEntidad(int entidad) {
        this.entidad = entidad;
    }


    /**
     * Gets the recaudador value for this HeaderIn.
     * 
     * @return recaudador
     */
    public java.lang.String getRecaudador() {
        return recaudador;
    }


    /**
     * Sets the recaudador value for this HeaderIn.
     * 
     * @param recaudador
     */
    public void setRecaudador(java.lang.String recaudador) {
        this.recaudador = recaudador;
    }


    /**
     * Gets the session value for this HeaderIn.
     * 
     * @return session
     */
    public int getSession() {
        return session;
    }


    /**
     * Sets the session value for this HeaderIn.
     * 
     * @param session
     */
    public void setSession(int session) {
        this.session = session;
    }


    /**
     * Gets the usuario value for this HeaderIn.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this HeaderIn.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HeaderIn)) return false;
        HeaderIn other = (HeaderIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.agencia == other.getAgencia() &&
            this.cajaFisica == other.getCajaFisica() &&
            this.cajero == other.getCajero() &&
            this.entidad == other.getEntidad() &&
            ((this.recaudador==null && other.getRecaudador()==null) || 
             (this.recaudador!=null &&
              this.recaudador.equals(other.getRecaudador()))) &&
            this.session == other.getSession() &&
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
        _hashCode += getAgencia();
        _hashCode += getCajaFisica();
        _hashCode += getCajero();
        _hashCode += getEntidad();
        if (getRecaudador() != null) {
            _hashCode += getRecaudador().hashCode();
        }
        _hashCode += getSession();
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HeaderIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "headerIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cajaFisica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cajaFisica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cajero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cajero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entidad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
