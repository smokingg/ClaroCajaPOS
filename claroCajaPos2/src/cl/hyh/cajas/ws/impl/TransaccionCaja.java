/**
 * TransaccionCaja.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class TransaccionCaja  implements java.io.Serializable {
    private java.lang.Integer codigoPortador;

    private java.lang.Long cuentaCliente;

    private java.lang.String dv;

    private java.lang.Integer empresa;

    private java.util.Calendar fechaVencimiento;

    private java.lang.Long monto;

    private java.lang.Long nroOperacionAReversar;

    private java.lang.String numeroCorrelativo;

    private java.lang.String numeroDocumento;

    private java.lang.Long numeroTransaccion;

    private java.lang.String origen;

    private java.lang.String rut;

    private java.lang.String servicio;

    private java.lang.String tipoCorrelativo;

    private java.lang.String tipoDocumento;

    private java.lang.String tipoRegistro;

    private java.lang.String tipoTransaccion;

    public TransaccionCaja() {
    }

    public TransaccionCaja(
           java.lang.Integer codigoPortador,
           java.lang.Long cuentaCliente,
           java.lang.String dv,
           java.lang.Integer empresa,
           java.util.Calendar fechaVencimiento,
           java.lang.Long monto,
           java.lang.Long nroOperacionAReversar,
           java.lang.String numeroCorrelativo,
           java.lang.String numeroDocumento,
           java.lang.Long numeroTransaccion,
           java.lang.String origen,
           java.lang.String rut,
           java.lang.String servicio,
           java.lang.String tipoCorrelativo,
           java.lang.String tipoDocumento,
           java.lang.String tipoRegistro,
           java.lang.String tipoTransaccion) {
           this.codigoPortador = codigoPortador;
           this.cuentaCliente = cuentaCliente;
           this.dv = dv;
           this.empresa = empresa;
           this.fechaVencimiento = fechaVencimiento;
           this.monto = monto;
           this.nroOperacionAReversar = nroOperacionAReversar;
           this.numeroCorrelativo = numeroCorrelativo;
           this.numeroDocumento = numeroDocumento;
           this.numeroTransaccion = numeroTransaccion;
           this.origen = origen;
           this.rut = rut;
           this.servicio = servicio;
           this.tipoCorrelativo = tipoCorrelativo;
           this.tipoDocumento = tipoDocumento;
           this.tipoRegistro = tipoRegistro;
           this.tipoTransaccion = tipoTransaccion;
    }


    /**
     * Gets the codigoPortador value for this TransaccionCaja.
     * 
     * @return codigoPortador
     */
    public java.lang.Integer getCodigoPortador() {
        return codigoPortador;
    }


    /**
     * Sets the codigoPortador value for this TransaccionCaja.
     * 
     * @param codigoPortador
     */
    public void setCodigoPortador(java.lang.Integer codigoPortador) {
        this.codigoPortador = codigoPortador;
    }


    /**
     * Gets the cuentaCliente value for this TransaccionCaja.
     * 
     * @return cuentaCliente
     */
    public java.lang.Long getCuentaCliente() {
        return cuentaCliente;
    }


    /**
     * Sets the cuentaCliente value for this TransaccionCaja.
     * 
     * @param cuentaCliente
     */
    public void setCuentaCliente(java.lang.Long cuentaCliente) {
        this.cuentaCliente = cuentaCliente;
    }


    /**
     * Gets the dv value for this TransaccionCaja.
     * 
     * @return dv
     */
    public java.lang.String getDv() {
        return dv;
    }


    /**
     * Sets the dv value for this TransaccionCaja.
     * 
     * @param dv
     */
    public void setDv(java.lang.String dv) {
        this.dv = dv;
    }


    /**
     * Gets the empresa value for this TransaccionCaja.
     * 
     * @return empresa
     */
    public java.lang.Integer getEmpresa() {
        return empresa;
    }


    /**
     * Sets the empresa value for this TransaccionCaja.
     * 
     * @param empresa
     */
    public void setEmpresa(java.lang.Integer empresa) {
        this.empresa = empresa;
    }


    /**
     * Gets the fechaVencimiento value for this TransaccionCaja.
     * 
     * @return fechaVencimiento
     */
    public java.util.Calendar getFechaVencimiento() {
        return fechaVencimiento;
    }


    /**
     * Sets the fechaVencimiento value for this TransaccionCaja.
     * 
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(java.util.Calendar fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    /**
     * Gets the monto value for this TransaccionCaja.
     * 
     * @return monto
     */
    public java.lang.Long getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this TransaccionCaja.
     * 
     * @param monto
     */
    public void setMonto(java.lang.Long monto) {
        this.monto = monto;
    }


    /**
     * Gets the nroOperacionAReversar value for this TransaccionCaja.
     * 
     * @return nroOperacionAReversar
     */
    public java.lang.Long getNroOperacionAReversar() {
        return nroOperacionAReversar;
    }


    /**
     * Sets the nroOperacionAReversar value for this TransaccionCaja.
     * 
     * @param nroOperacionAReversar
     */
    public void setNroOperacionAReversar(java.lang.Long nroOperacionAReversar) {
        this.nroOperacionAReversar = nroOperacionAReversar;
    }


    /**
     * Gets the numeroCorrelativo value for this TransaccionCaja.
     * 
     * @return numeroCorrelativo
     */
    public java.lang.String getNumeroCorrelativo() {
        return numeroCorrelativo;
    }


    /**
     * Sets the numeroCorrelativo value for this TransaccionCaja.
     * 
     * @param numeroCorrelativo
     */
    public void setNumeroCorrelativo(java.lang.String numeroCorrelativo) {
        this.numeroCorrelativo = numeroCorrelativo;
    }


    /**
     * Gets the numeroDocumento value for this TransaccionCaja.
     * 
     * @return numeroDocumento
     */
    public java.lang.String getNumeroDocumento() {
        return numeroDocumento;
    }


    /**
     * Sets the numeroDocumento value for this TransaccionCaja.
     * 
     * @param numeroDocumento
     */
    public void setNumeroDocumento(java.lang.String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }


    /**
     * Gets the numeroTransaccion value for this TransaccionCaja.
     * 
     * @return numeroTransaccion
     */
    public java.lang.Long getNumeroTransaccion() {
        return numeroTransaccion;
    }


    /**
     * Sets the numeroTransaccion value for this TransaccionCaja.
     * 
     * @param numeroTransaccion
     */
    public void setNumeroTransaccion(java.lang.Long numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }


    /**
     * Gets the origen value for this TransaccionCaja.
     * 
     * @return origen
     */
    public java.lang.String getOrigen() {
        return origen;
    }


    /**
     * Sets the origen value for this TransaccionCaja.
     * 
     * @param origen
     */
    public void setOrigen(java.lang.String origen) {
        this.origen = origen;
    }


    /**
     * Gets the rut value for this TransaccionCaja.
     * 
     * @return rut
     */
    public java.lang.String getRut() {
        return rut;
    }


    /**
     * Sets the rut value for this TransaccionCaja.
     * 
     * @param rut
     */
    public void setRut(java.lang.String rut) {
        this.rut = rut;
    }


    /**
     * Gets the servicio value for this TransaccionCaja.
     * 
     * @return servicio
     */
    public java.lang.String getServicio() {
        return servicio;
    }


    /**
     * Sets the servicio value for this TransaccionCaja.
     * 
     * @param servicio
     */
    public void setServicio(java.lang.String servicio) {
        this.servicio = servicio;
    }


    /**
     * Gets the tipoCorrelativo value for this TransaccionCaja.
     * 
     * @return tipoCorrelativo
     */
    public java.lang.String getTipoCorrelativo() {
        return tipoCorrelativo;
    }


    /**
     * Sets the tipoCorrelativo value for this TransaccionCaja.
     * 
     * @param tipoCorrelativo
     */
    public void setTipoCorrelativo(java.lang.String tipoCorrelativo) {
        this.tipoCorrelativo = tipoCorrelativo;
    }


    /**
     * Gets the tipoDocumento value for this TransaccionCaja.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this TransaccionCaja.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the tipoRegistro value for this TransaccionCaja.
     * 
     * @return tipoRegistro
     */
    public java.lang.String getTipoRegistro() {
        return tipoRegistro;
    }


    /**
     * Sets the tipoRegistro value for this TransaccionCaja.
     * 
     * @param tipoRegistro
     */
    public void setTipoRegistro(java.lang.String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }


    /**
     * Gets the tipoTransaccion value for this TransaccionCaja.
     * 
     * @return tipoTransaccion
     */
    public java.lang.String getTipoTransaccion() {
        return tipoTransaccion;
    }


    /**
     * Sets the tipoTransaccion value for this TransaccionCaja.
     * 
     * @param tipoTransaccion
     */
    public void setTipoTransaccion(java.lang.String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransaccionCaja)) return false;
        TransaccionCaja other = (TransaccionCaja) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoPortador==null && other.getCodigoPortador()==null) || 
             (this.codigoPortador!=null &&
              this.codigoPortador.equals(other.getCodigoPortador()))) &&
            ((this.cuentaCliente==null && other.getCuentaCliente()==null) || 
             (this.cuentaCliente!=null &&
              this.cuentaCliente.equals(other.getCuentaCliente()))) &&
            ((this.dv==null && other.getDv()==null) || 
             (this.dv!=null &&
              this.dv.equals(other.getDv()))) &&
            ((this.empresa==null && other.getEmpresa()==null) || 
             (this.empresa!=null &&
              this.empresa.equals(other.getEmpresa()))) &&
            ((this.fechaVencimiento==null && other.getFechaVencimiento()==null) || 
             (this.fechaVencimiento!=null &&
              this.fechaVencimiento.equals(other.getFechaVencimiento()))) &&
            ((this.monto==null && other.getMonto()==null) || 
             (this.monto!=null &&
              this.monto.equals(other.getMonto()))) &&
            ((this.nroOperacionAReversar==null && other.getNroOperacionAReversar()==null) || 
             (this.nroOperacionAReversar!=null &&
              this.nroOperacionAReversar.equals(other.getNroOperacionAReversar()))) &&
            ((this.numeroCorrelativo==null && other.getNumeroCorrelativo()==null) || 
             (this.numeroCorrelativo!=null &&
              this.numeroCorrelativo.equals(other.getNumeroCorrelativo()))) &&
            ((this.numeroDocumento==null && other.getNumeroDocumento()==null) || 
             (this.numeroDocumento!=null &&
              this.numeroDocumento.equals(other.getNumeroDocumento()))) &&
            ((this.numeroTransaccion==null && other.getNumeroTransaccion()==null) || 
             (this.numeroTransaccion!=null &&
              this.numeroTransaccion.equals(other.getNumeroTransaccion()))) &&
            ((this.origen==null && other.getOrigen()==null) || 
             (this.origen!=null &&
              this.origen.equals(other.getOrigen()))) &&
            ((this.rut==null && other.getRut()==null) || 
             (this.rut!=null &&
              this.rut.equals(other.getRut()))) &&
            ((this.servicio==null && other.getServicio()==null) || 
             (this.servicio!=null &&
              this.servicio.equals(other.getServicio()))) &&
            ((this.tipoCorrelativo==null && other.getTipoCorrelativo()==null) || 
             (this.tipoCorrelativo!=null &&
              this.tipoCorrelativo.equals(other.getTipoCorrelativo()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            ((this.tipoRegistro==null && other.getTipoRegistro()==null) || 
             (this.tipoRegistro!=null &&
              this.tipoRegistro.equals(other.getTipoRegistro()))) &&
            ((this.tipoTransaccion==null && other.getTipoTransaccion()==null) || 
             (this.tipoTransaccion!=null &&
              this.tipoTransaccion.equals(other.getTipoTransaccion())));
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
        if (getCodigoPortador() != null) {
            _hashCode += getCodigoPortador().hashCode();
        }
        if (getCuentaCliente() != null) {
            _hashCode += getCuentaCliente().hashCode();
        }
        if (getDv() != null) {
            _hashCode += getDv().hashCode();
        }
        if (getEmpresa() != null) {
            _hashCode += getEmpresa().hashCode();
        }
        if (getFechaVencimiento() != null) {
            _hashCode += getFechaVencimiento().hashCode();
        }
        if (getMonto() != null) {
            _hashCode += getMonto().hashCode();
        }
        if (getNroOperacionAReversar() != null) {
            _hashCode += getNroOperacionAReversar().hashCode();
        }
        if (getNumeroCorrelativo() != null) {
            _hashCode += getNumeroCorrelativo().hashCode();
        }
        if (getNumeroDocumento() != null) {
            _hashCode += getNumeroDocumento().hashCode();
        }
        if (getNumeroTransaccion() != null) {
            _hashCode += getNumeroTransaccion().hashCode();
        }
        if (getOrigen() != null) {
            _hashCode += getOrigen().hashCode();
        }
        if (getRut() != null) {
            _hashCode += getRut().hashCode();
        }
        if (getServicio() != null) {
            _hashCode += getServicio().hashCode();
        }
        if (getTipoCorrelativo() != null) {
            _hashCode += getTipoCorrelativo().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        if (getTipoRegistro() != null) {
            _hashCode += getTipoRegistro().hashCode();
        }
        if (getTipoTransaccion() != null) {
            _hashCode += getTipoTransaccion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TransaccionCaja.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "transaccionCaja"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPortador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoPortador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuentaCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuentaCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("empresa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "empresa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaVencimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaVencimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nroOperacionAReversar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nroOperacionAReversar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCorrelativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCorrelativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroTransaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroTransaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("origen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "origen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCorrelativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoCorrelativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTransaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTransaccion"));
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
