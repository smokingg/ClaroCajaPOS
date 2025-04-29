/**
 * Caja.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultar;

public class Caja  implements java.io.Serializable {
    private int idCaja;

    private java.lang.String entidad;

    private int canal;

    private java.lang.String agencia;

    private java.lang.String recaudador;

    private java.lang.String usuario;

    private java.lang.Long codigoSesion;

    public Caja() {
    }

    public Caja(
           int idCaja,
           java.lang.String entidad,
           int canal,
           java.lang.String agencia,
           java.lang.String recaudador,
           java.lang.String usuario,
           java.lang.Long codigoSesion) {
           this.idCaja = idCaja;
           this.entidad = entidad;
           this.canal = canal;
           this.agencia = agencia;
           this.recaudador = recaudador;
           this.usuario = usuario;
           this.codigoSesion = codigoSesion;
    }


    /**
     * Gets the idCaja value for this Caja.
     * 
     * @return idCaja
     */
    public int getIdCaja() {
        return idCaja;
    }


    /**
     * Sets the idCaja value for this Caja.
     * 
     * @param idCaja
     */
    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }


    /**
     * Gets the entidad value for this Caja.
     * 
     * @return entidad
     */
    public java.lang.String getEntidad() {
        return entidad;
    }


    /**
     * Sets the entidad value for this Caja.
     * 
     * @param entidad
     */
    public void setEntidad(java.lang.String entidad) {
        this.entidad = entidad;
    }


    /**
     * Gets the canal value for this Caja.
     * 
     * @return canal
     */
    public int getCanal() {
        return canal;
    }


    /**
     * Sets the canal value for this Caja.
     * 
     * @param canal
     */
    public void setCanal(int canal) {
        this.canal = canal;
    }


    /**
     * Gets the agencia value for this Caja.
     * 
     * @return agencia
     */
    public java.lang.String getAgencia() {
        return agencia;
    }


    /**
     * Sets the agencia value for this Caja.
     * 
     * @param agencia
     */
    public void setAgencia(java.lang.String agencia) {
        this.agencia = agencia;
    }


    /**
     * Gets the recaudador value for this Caja.
     * 
     * @return recaudador
     */
    public java.lang.String getRecaudador() {
        return recaudador;
    }


    /**
     * Sets the recaudador value for this Caja.
     * 
     * @param recaudador
     */
    public void setRecaudador(java.lang.String recaudador) {
        this.recaudador = recaudador;
    }


    /**
     * Gets the usuario value for this Caja.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this Caja.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }


    /**
     * Gets the codigoSesion value for this Caja.
     * 
     * @return codigoSesion
     */
    public java.lang.Long getCodigoSesion() {
        return codigoSesion;
    }


    /**
     * Sets the codigoSesion value for this Caja.
     * 
     * @param codigoSesion
     */
    public void setCodigoSesion(java.lang.Long codigoSesion) {
        this.codigoSesion = codigoSesion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Caja)) return false;
        Caja other = (Caja) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.idCaja == other.getIdCaja() &&
            ((this.entidad==null && other.getEntidad()==null) || 
             (this.entidad!=null &&
              this.entidad.equals(other.getEntidad()))) &&
            this.canal == other.getCanal() &&
            ((this.agencia==null && other.getAgencia()==null) || 
             (this.agencia!=null &&
              this.agencia.equals(other.getAgencia()))) &&
            ((this.recaudador==null && other.getRecaudador()==null) || 
             (this.recaudador!=null &&
              this.recaudador.equals(other.getRecaudador()))) &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario()))) &&
            ((this.codigoSesion==null && other.getCodigoSesion()==null) || 
             (this.codigoSesion!=null &&
              this.codigoSesion.equals(other.getCodigoSesion())));
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
        _hashCode += getIdCaja();
        if (getEntidad() != null) {
            _hashCode += getEntidad().hashCode();
        }
        _hashCode += getCanal();
        if (getAgencia() != null) {
            _hashCode += getAgencia().hashCode();
        }
        if (getRecaudador() != null) {
            _hashCode += getRecaudador().hashCode();
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        if (getCodigoSesion() != null) {
            _hashCode += getCodigoSesion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Caja.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultar/", "Caja"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCaja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCaja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entidad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "canal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSesion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSesion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
