/**
 * OperacionCaja.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class OperacionCaja  implements java.io.Serializable {
    private java.lang.String agencia;

    private int caja;

    private int canal;

    private java.lang.String codigoOperacion;

    private java.lang.Integer codigoSesion;

    private java.lang.String entidad;

    private java.lang.String fechaOperacion;

    private java.lang.String fechaPago;

    private java.lang.String horaOperacion;

    private long monto;

    private java.lang.Long numeroOperacion;

    private java.lang.Integer recaudador;

    private java.lang.String tipoOperacion;

    private java.lang.String usuario;

    public OperacionCaja() {
    }

    public OperacionCaja(
           java.lang.String agencia,
           int caja,
           int canal,
           java.lang.String codigoOperacion,
           java.lang.Integer codigoSesion,
           java.lang.String entidad,
           java.lang.String fechaOperacion,
           java.lang.String fechaPago,
           java.lang.String horaOperacion,
           long monto,
           java.lang.Long numeroOperacion,
           java.lang.Integer recaudador,
           java.lang.String tipoOperacion,
           java.lang.String usuario) {
           this.agencia = agencia;
           this.caja = caja;
           this.canal = canal;
           this.codigoOperacion = codigoOperacion;
           this.codigoSesion = codigoSesion;
           this.entidad = entidad;
           this.fechaOperacion = fechaOperacion;
           this.fechaPago = fechaPago;
           this.horaOperacion = horaOperacion;
           this.monto = monto;
           this.numeroOperacion = numeroOperacion;
           this.recaudador = recaudador;
           this.tipoOperacion = tipoOperacion;
           this.usuario = usuario;
    }


    /**
     * Gets the agencia value for this OperacionCaja.
     * 
     * @return agencia
     */
    public java.lang.String getAgencia() {
        return agencia;
    }


    /**
     * Sets the agencia value for this OperacionCaja.
     * 
     * @param agencia
     */
    public void setAgencia(java.lang.String agencia) {
        this.agencia = agencia;
    }


    /**
     * Gets the caja value for this OperacionCaja.
     * 
     * @return caja
     */
    public int getCaja() {
        return caja;
    }


    /**
     * Sets the caja value for this OperacionCaja.
     * 
     * @param caja
     */
    public void setCaja(int caja) {
        this.caja = caja;
    }


    /**
     * Gets the canal value for this OperacionCaja.
     * 
     * @return canal
     */
    public int getCanal() {
        return canal;
    }


    /**
     * Sets the canal value for this OperacionCaja.
     * 
     * @param canal
     */
    public void setCanal(int canal) {
        this.canal = canal;
    }


    /**
     * Gets the codigoOperacion value for this OperacionCaja.
     * 
     * @return codigoOperacion
     */
    public java.lang.String getCodigoOperacion() {
        return codigoOperacion;
    }


    /**
     * Sets the codigoOperacion value for this OperacionCaja.
     * 
     * @param codigoOperacion
     */
    public void setCodigoOperacion(java.lang.String codigoOperacion) {
        this.codigoOperacion = codigoOperacion;
    }


    /**
     * Gets the codigoSesion value for this OperacionCaja.
     * 
     * @return codigoSesion
     */
    public java.lang.Integer getCodigoSesion() {
        return codigoSesion;
    }


    /**
     * Sets the codigoSesion value for this OperacionCaja.
     * 
     * @param codigoSesion
     */
    public void setCodigoSesion(java.lang.Integer codigoSesion) {
        this.codigoSesion = codigoSesion;
    }


    /**
     * Gets the entidad value for this OperacionCaja.
     * 
     * @return entidad
     */
    public java.lang.String getEntidad() {
        return entidad;
    }


    /**
     * Sets the entidad value for this OperacionCaja.
     * 
     * @param entidad
     */
    public void setEntidad(java.lang.String entidad) {
        this.entidad = entidad;
    }


    /**
     * Gets the fechaOperacion value for this OperacionCaja.
     * 
     * @return fechaOperacion
     */
    public java.lang.String getFechaOperacion() {
        return fechaOperacion;
    }


    /**
     * Sets the fechaOperacion value for this OperacionCaja.
     * 
     * @param fechaOperacion
     */
    public void setFechaOperacion(java.lang.String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }


    /**
     * Gets the fechaPago value for this OperacionCaja.
     * 
     * @return fechaPago
     */
    public java.lang.String getFechaPago() {
        return fechaPago;
    }


    /**
     * Sets the fechaPago value for this OperacionCaja.
     * 
     * @param fechaPago
     */
    public void setFechaPago(java.lang.String fechaPago) {
        this.fechaPago = fechaPago;
    }


    /**
     * Gets the horaOperacion value for this OperacionCaja.
     * 
     * @return horaOperacion
     */
    public java.lang.String getHoraOperacion() {
        return horaOperacion;
    }


    /**
     * Sets the horaOperacion value for this OperacionCaja.
     * 
     * @param horaOperacion
     */
    public void setHoraOperacion(java.lang.String horaOperacion) {
        this.horaOperacion = horaOperacion;
    }


    /**
     * Gets the monto value for this OperacionCaja.
     * 
     * @return monto
     */
    public long getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this OperacionCaja.
     * 
     * @param monto
     */
    public void setMonto(long monto) {
        this.monto = monto;
    }


    /**
     * Gets the numeroOperacion value for this OperacionCaja.
     * 
     * @return numeroOperacion
     */
    public java.lang.Long getNumeroOperacion() {
        return numeroOperacion;
    }


    /**
     * Sets the numeroOperacion value for this OperacionCaja.
     * 
     * @param numeroOperacion
     */
    public void setNumeroOperacion(java.lang.Long numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }


    /**
     * Gets the recaudador value for this OperacionCaja.
     * 
     * @return recaudador
     */
    public java.lang.Integer getRecaudador() {
        return recaudador;
    }


    /**
     * Sets the recaudador value for this OperacionCaja.
     * 
     * @param recaudador
     */
    public void setRecaudador(java.lang.Integer recaudador) {
        this.recaudador = recaudador;
    }


    /**
     * Gets the tipoOperacion value for this OperacionCaja.
     * 
     * @return tipoOperacion
     */
    public java.lang.String getTipoOperacion() {
        return tipoOperacion;
    }


    /**
     * Sets the tipoOperacion value for this OperacionCaja.
     * 
     * @param tipoOperacion
     */
    public void setTipoOperacion(java.lang.String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }


    /**
     * Gets the usuario value for this OperacionCaja.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this OperacionCaja.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionCaja)) return false;
        OperacionCaja other = (OperacionCaja) obj;
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
            this.caja == other.getCaja() &&
            this.canal == other.getCanal() &&
            ((this.codigoOperacion==null && other.getCodigoOperacion()==null) || 
             (this.codigoOperacion!=null &&
              this.codigoOperacion.equals(other.getCodigoOperacion()))) &&
            ((this.codigoSesion==null && other.getCodigoSesion()==null) || 
             (this.codigoSesion!=null &&
              this.codigoSesion.equals(other.getCodigoSesion()))) &&
            ((this.entidad==null && other.getEntidad()==null) || 
             (this.entidad!=null &&
              this.entidad.equals(other.getEntidad()))) &&
            ((this.fechaOperacion==null && other.getFechaOperacion()==null) || 
             (this.fechaOperacion!=null &&
              this.fechaOperacion.equals(other.getFechaOperacion()))) &&
            ((this.fechaPago==null && other.getFechaPago()==null) || 
             (this.fechaPago!=null &&
              this.fechaPago.equals(other.getFechaPago()))) &&
            ((this.horaOperacion==null && other.getHoraOperacion()==null) || 
             (this.horaOperacion!=null &&
              this.horaOperacion.equals(other.getHoraOperacion()))) &&
            this.monto == other.getMonto() &&
            ((this.numeroOperacion==null && other.getNumeroOperacion()==null) || 
             (this.numeroOperacion!=null &&
              this.numeroOperacion.equals(other.getNumeroOperacion()))) &&
            ((this.recaudador==null && other.getRecaudador()==null) || 
             (this.recaudador!=null &&
              this.recaudador.equals(other.getRecaudador()))) &&
            ((this.tipoOperacion==null && other.getTipoOperacion()==null) || 
             (this.tipoOperacion!=null &&
              this.tipoOperacion.equals(other.getTipoOperacion()))) &&
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
        _hashCode += getCaja();
        _hashCode += getCanal();
        if (getCodigoOperacion() != null) {
            _hashCode += getCodigoOperacion().hashCode();
        }
        if (getCodigoSesion() != null) {
            _hashCode += getCodigoSesion().hashCode();
        }
        if (getEntidad() != null) {
            _hashCode += getEntidad().hashCode();
        }
        if (getFechaOperacion() != null) {
            _hashCode += getFechaOperacion().hashCode();
        }
        if (getFechaPago() != null) {
            _hashCode += getFechaPago().hashCode();
        }
        if (getHoraOperacion() != null) {
            _hashCode += getHoraOperacion().hashCode();
        }
        _hashCode += new Long(getMonto()).hashCode();
        if (getNumeroOperacion() != null) {
            _hashCode += getNumeroOperacion().hashCode();
        }
        if (getRecaudador() != null) {
            _hashCode += getRecaudador().hashCode();
        }
        if (getTipoOperacion() != null) {
            _hashCode += getTipoOperacion().hashCode();
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperacionCaja.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "operacionCaja"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "canal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSesion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSesion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("fechaOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horaOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "horaOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recaudador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recaudador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoOperacion"));
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
