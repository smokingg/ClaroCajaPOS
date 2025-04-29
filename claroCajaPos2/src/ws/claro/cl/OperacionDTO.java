/**
 * OperacionDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class OperacionDTO  implements java.io.Serializable {
    private java.lang.String agencia;

    private java.lang.String anulable;

    private java.lang.String anulableTbk;

    private java.lang.String caja;

    private java.lang.String canal;

    private java.lang.String codigoOperacion;

    private java.lang.String codigoRecaudador;

    private java.lang.String codigoSesion;

    private java.lang.String entidad;

    private java.lang.String fechaOperacion;

    private java.lang.String fechaPago;

    private java.lang.String horaOperacion;

    private java.lang.String idOperacionExterno;

    private ws.claro.cl.MedioPagoDTO[] mediosdepago;

    private java.lang.String monto;

    private java.lang.String numeroOperacion;

    private ws.claro.cl.TransaccionDTO[] transacciones;

    private java.lang.String usuario;

    public OperacionDTO() {
    }

    public OperacionDTO(
           java.lang.String agencia,
           java.lang.String anulable,
           java.lang.String anulableTbk,
           java.lang.String caja,
           java.lang.String canal,
           java.lang.String codigoOperacion,
           java.lang.String codigoRecaudador,
           java.lang.String codigoSesion,
           java.lang.String entidad,
           java.lang.String fechaOperacion,
           java.lang.String fechaPago,
           java.lang.String horaOperacion,
           java.lang.String idOperacionExterno,
           ws.claro.cl.MedioPagoDTO[] mediosdepago,
           java.lang.String monto,
           java.lang.String numeroOperacion,
           ws.claro.cl.TransaccionDTO[] transacciones,
           java.lang.String usuario) {
           this.agencia = agencia;
           this.anulable = anulable;
           this.anulableTbk = anulableTbk;
           this.caja = caja;
           this.canal = canal;
           this.codigoOperacion = codigoOperacion;
           this.codigoRecaudador = codigoRecaudador;
           this.codigoSesion = codigoSesion;
           this.entidad = entidad;
           this.fechaOperacion = fechaOperacion;
           this.fechaPago = fechaPago;
           this.horaOperacion = horaOperacion;
           this.idOperacionExterno = idOperacionExterno;
           this.mediosdepago = mediosdepago;
           this.monto = monto;
           this.numeroOperacion = numeroOperacion;
           this.transacciones = transacciones;
           this.usuario = usuario;
    }


    /**
     * Gets the agencia value for this OperacionDTO.
     * 
     * @return agencia
     */
    public java.lang.String getAgencia() {
        return agencia;
    }


    /**
     * Sets the agencia value for this OperacionDTO.
     * 
     * @param agencia
     */
    public void setAgencia(java.lang.String agencia) {
        this.agencia = agencia;
    }


    /**
     * Gets the anulable value for this OperacionDTO.
     * 
     * @return anulable
     */
    public java.lang.String getAnulable() {
        return anulable;
    }


    /**
     * Sets the anulable value for this OperacionDTO.
     * 
     * @param anulable
     */
    public void setAnulable(java.lang.String anulable) {
        this.anulable = anulable;
    }


    /**
     * Gets the anulableTbk value for this OperacionDTO.
     * 
     * @return anulableTbk
     */
    public java.lang.String getAnulableTbk() {
        return anulableTbk;
    }


    /**
     * Sets the anulableTbk value for this OperacionDTO.
     * 
     * @param anulableTbk
     */
    public void setAnulableTbk(java.lang.String anulableTbk) {
        this.anulableTbk = anulableTbk;
    }


    /**
     * Gets the caja value for this OperacionDTO.
     * 
     * @return caja
     */
    public java.lang.String getCaja() {
        return caja;
    }


    /**
     * Sets the caja value for this OperacionDTO.
     * 
     * @param caja
     */
    public void setCaja(java.lang.String caja) {
        this.caja = caja;
    }


    /**
     * Gets the canal value for this OperacionDTO.
     * 
     * @return canal
     */
    public java.lang.String getCanal() {
        return canal;
    }


    /**
     * Sets the canal value for this OperacionDTO.
     * 
     * @param canal
     */
    public void setCanal(java.lang.String canal) {
        this.canal = canal;
    }


    /**
     * Gets the codigoOperacion value for this OperacionDTO.
     * 
     * @return codigoOperacion
     */
    public java.lang.String getCodigoOperacion() {
        return codigoOperacion;
    }


    /**
     * Sets the codigoOperacion value for this OperacionDTO.
     * 
     * @param codigoOperacion
     */
    public void setCodigoOperacion(java.lang.String codigoOperacion) {
        this.codigoOperacion = codigoOperacion;
    }


    /**
     * Gets the codigoRecaudador value for this OperacionDTO.
     * 
     * @return codigoRecaudador
     */
    public java.lang.String getCodigoRecaudador() {
        return codigoRecaudador;
    }


    /**
     * Sets the codigoRecaudador value for this OperacionDTO.
     * 
     * @param codigoRecaudador
     */
    public void setCodigoRecaudador(java.lang.String codigoRecaudador) {
        this.codigoRecaudador = codigoRecaudador;
    }


    /**
     * Gets the codigoSesion value for this OperacionDTO.
     * 
     * @return codigoSesion
     */
    public java.lang.String getCodigoSesion() {
        return codigoSesion;
    }


    /**
     * Sets the codigoSesion value for this OperacionDTO.
     * 
     * @param codigoSesion
     */
    public void setCodigoSesion(java.lang.String codigoSesion) {
        this.codigoSesion = codigoSesion;
    }


    /**
     * Gets the entidad value for this OperacionDTO.
     * 
     * @return entidad
     */
    public java.lang.String getEntidad() {
        return entidad;
    }


    /**
     * Sets the entidad value for this OperacionDTO.
     * 
     * @param entidad
     */
    public void setEntidad(java.lang.String entidad) {
        this.entidad = entidad;
    }


    /**
     * Gets the fechaOperacion value for this OperacionDTO.
     * 
     * @return fechaOperacion
     */
    public java.lang.String getFechaOperacion() {
        return fechaOperacion;
    }


    /**
     * Sets the fechaOperacion value for this OperacionDTO.
     * 
     * @param fechaOperacion
     */
    public void setFechaOperacion(java.lang.String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }


    /**
     * Gets the fechaPago value for this OperacionDTO.
     * 
     * @return fechaPago
     */
    public java.lang.String getFechaPago() {
        return fechaPago;
    }


    /**
     * Sets the fechaPago value for this OperacionDTO.
     * 
     * @param fechaPago
     */
    public void setFechaPago(java.lang.String fechaPago) {
        this.fechaPago = fechaPago;
    }


    /**
     * Gets the horaOperacion value for this OperacionDTO.
     * 
     * @return horaOperacion
     */
    public java.lang.String getHoraOperacion() {
        return horaOperacion;
    }


    /**
     * Sets the horaOperacion value for this OperacionDTO.
     * 
     * @param horaOperacion
     */
    public void setHoraOperacion(java.lang.String horaOperacion) {
        this.horaOperacion = horaOperacion;
    }


    /**
     * Gets the idOperacionExterno value for this OperacionDTO.
     * 
     * @return idOperacionExterno
     */
    public java.lang.String getIdOperacionExterno() {
        return idOperacionExterno;
    }


    /**
     * Sets the idOperacionExterno value for this OperacionDTO.
     * 
     * @param idOperacionExterno
     */
    public void setIdOperacionExterno(java.lang.String idOperacionExterno) {
        this.idOperacionExterno = idOperacionExterno;
    }


    /**
     * Gets the mediosdepago value for this OperacionDTO.
     * 
     * @return mediosdepago
     */
    public ws.claro.cl.MedioPagoDTO[] getMediosdepago() {
        return mediosdepago;
    }


    /**
     * Sets the mediosdepago value for this OperacionDTO.
     * 
     * @param mediosdepago
     */
    public void setMediosdepago(ws.claro.cl.MedioPagoDTO[] mediosdepago) {
        this.mediosdepago = mediosdepago;
    }

    public ws.claro.cl.MedioPagoDTO getMediosdepago(int i) {
        return this.mediosdepago[i];
    }

    public void setMediosdepago(int i, ws.claro.cl.MedioPagoDTO _value) {
        this.mediosdepago[i] = _value;
    }


    /**
     * Gets the monto value for this OperacionDTO.
     * 
     * @return monto
     */
    public java.lang.String getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this OperacionDTO.
     * 
     * @param monto
     */
    public void setMonto(java.lang.String monto) {
        this.monto = monto;
    }


    /**
     * Gets the numeroOperacion value for this OperacionDTO.
     * 
     * @return numeroOperacion
     */
    public java.lang.String getNumeroOperacion() {
        return numeroOperacion;
    }


    /**
     * Sets the numeroOperacion value for this OperacionDTO.
     * 
     * @param numeroOperacion
     */
    public void setNumeroOperacion(java.lang.String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }


    /**
     * Gets the transacciones value for this OperacionDTO.
     * 
     * @return transacciones
     */
    public ws.claro.cl.TransaccionDTO[] getTransacciones() {
        return transacciones;
    }


    /**
     * Sets the transacciones value for this OperacionDTO.
     * 
     * @param transacciones
     */
    public void setTransacciones(ws.claro.cl.TransaccionDTO[] transacciones) {
        this.transacciones = transacciones;
    }

    public ws.claro.cl.TransaccionDTO getTransacciones(int i) {
        return this.transacciones[i];
    }

    public void setTransacciones(int i, ws.claro.cl.TransaccionDTO _value) {
        this.transacciones[i] = _value;
    }


    /**
     * Gets the usuario value for this OperacionDTO.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this OperacionDTO.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionDTO)) return false;
        OperacionDTO other = (OperacionDTO) obj;
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
            ((this.anulable==null && other.getAnulable()==null) || 
             (this.anulable!=null &&
              this.anulable.equals(other.getAnulable()))) &&
            ((this.anulableTbk==null && other.getAnulableTbk()==null) || 
             (this.anulableTbk!=null &&
              this.anulableTbk.equals(other.getAnulableTbk()))) &&
            ((this.caja==null && other.getCaja()==null) || 
             (this.caja!=null &&
              this.caja.equals(other.getCaja()))) &&
            ((this.canal==null && other.getCanal()==null) || 
             (this.canal!=null &&
              this.canal.equals(other.getCanal()))) &&
            ((this.codigoOperacion==null && other.getCodigoOperacion()==null) || 
             (this.codigoOperacion!=null &&
              this.codigoOperacion.equals(other.getCodigoOperacion()))) &&
            ((this.codigoRecaudador==null && other.getCodigoRecaudador()==null) || 
             (this.codigoRecaudador!=null &&
              this.codigoRecaudador.equals(other.getCodigoRecaudador()))) &&
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
            ((this.idOperacionExterno==null && other.getIdOperacionExterno()==null) || 
             (this.idOperacionExterno!=null &&
              this.idOperacionExterno.equals(other.getIdOperacionExterno()))) &&
            ((this.mediosdepago==null && other.getMediosdepago()==null) || 
             (this.mediosdepago!=null &&
              java.util.Arrays.equals(this.mediosdepago, other.getMediosdepago()))) &&
            ((this.monto==null && other.getMonto()==null) || 
             (this.monto!=null &&
              this.monto.equals(other.getMonto()))) &&
            ((this.numeroOperacion==null && other.getNumeroOperacion()==null) || 
             (this.numeroOperacion!=null &&
              this.numeroOperacion.equals(other.getNumeroOperacion()))) &&
            ((this.transacciones==null && other.getTransacciones()==null) || 
             (this.transacciones!=null &&
              java.util.Arrays.equals(this.transacciones, other.getTransacciones()))) &&
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
        if (getAnulable() != null) {
            _hashCode += getAnulable().hashCode();
        }
        if (getAnulableTbk() != null) {
            _hashCode += getAnulableTbk().hashCode();
        }
        if (getCaja() != null) {
            _hashCode += getCaja().hashCode();
        }
        if (getCanal() != null) {
            _hashCode += getCanal().hashCode();
        }
        if (getCodigoOperacion() != null) {
            _hashCode += getCodigoOperacion().hashCode();
        }
        if (getCodigoRecaudador() != null) {
            _hashCode += getCodigoRecaudador().hashCode();
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
        if (getIdOperacionExterno() != null) {
            _hashCode += getIdOperacionExterno().hashCode();
        }
        if (getMediosdepago() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMediosdepago());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMediosdepago(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMonto() != null) {
            _hashCode += getMonto().hashCode();
        }
        if (getNumeroOperacion() != null) {
            _hashCode += getNumeroOperacion().hashCode();
        }
        if (getTransacciones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTransacciones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTransacciones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperacionDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "operacionDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anulable");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anulable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anulableTbk");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anulableTbk"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "canal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("codigoRecaudador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoRecaudador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSesion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSesion"));
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
        elemField.setFieldName("idOperacionExterno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOperacionExterno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mediosdepago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mediosdepago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "medioPagoDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transacciones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transacciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "transaccionDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
