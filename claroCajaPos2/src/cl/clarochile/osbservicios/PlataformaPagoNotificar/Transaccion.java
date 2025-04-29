/**
 * Transaccion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoNotificar;

public class Transaccion  implements java.io.Serializable {
    private java.lang.String tipoTransaccion;

    private java.lang.Long numeroDocumento;

    private java.lang.String tipoDocumento;

    private java.lang.String servicio;

    private long monto;

    private java.lang.String origen;

    private java.lang.Integer empresa;

    private java.lang.Long rut;

    private java.lang.String dv;

    private java.lang.String tipoRegistro;

    private java.lang.String fechaVencimiento;

    private java.lang.Integer codigoPortador;

    private java.lang.String cuentaCliente;

    private java.lang.Long nroOperacionAReversar;

    public Transaccion() {
    }

    public Transaccion(
           java.lang.String tipoTransaccion,
           java.lang.Long numeroDocumento,
           java.lang.String tipoDocumento,
           java.lang.String servicio,
           long monto,
           java.lang.String origen,
           java.lang.Integer empresa,
           java.lang.Long rut,
           java.lang.String dv,
           java.lang.String tipoRegistro,
           java.lang.String fechaVencimiento,
           java.lang.Integer codigoPortador,
           java.lang.String cuentaCliente,
           java.lang.Long nroOperacionAReversar) {
           this.tipoTransaccion = tipoTransaccion;
           this.numeroDocumento = numeroDocumento;
           this.tipoDocumento = tipoDocumento;
           this.servicio = servicio;
           this.monto = monto;
           this.origen = origen;
           this.empresa = empresa;
           this.rut = rut;
           this.dv = dv;
           this.tipoRegistro = tipoRegistro;
           this.fechaVencimiento = fechaVencimiento;
           this.codigoPortador = codigoPortador;
           this.cuentaCliente = cuentaCliente;
           this.nroOperacionAReversar = nroOperacionAReversar;
    }


    /**
     * Gets the tipoTransaccion value for this Transaccion.
     * 
     * @return tipoTransaccion
     */
    public java.lang.String getTipoTransaccion() {
        return tipoTransaccion;
    }


    /**
     * Sets the tipoTransaccion value for this Transaccion.
     * 
     * @param tipoTransaccion
     */
    public void setTipoTransaccion(java.lang.String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }


    /**
     * Gets the numeroDocumento value for this Transaccion.
     * 
     * @return numeroDocumento
     */
    public java.lang.Long getNumeroDocumento() {
        return numeroDocumento;
    }


    /**
     * Sets the numeroDocumento value for this Transaccion.
     * 
     * @param numeroDocumento
     */
    public void setNumeroDocumento(java.lang.Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }


    /**
     * Gets the tipoDocumento value for this Transaccion.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this Transaccion.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the servicio value for this Transaccion.
     * 
     * @return servicio
     */
    public java.lang.String getServicio() {
        return servicio;
    }


    /**
     * Sets the servicio value for this Transaccion.
     * 
     * @param servicio
     */
    public void setServicio(java.lang.String servicio) {
        this.servicio = servicio;
    }


    /**
     * Gets the monto value for this Transaccion.
     * 
     * @return monto
     */
    public long getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this Transaccion.
     * 
     * @param monto
     */
    public void setMonto(long monto) {
        this.monto = monto;
    }


    /**
     * Gets the origen value for this Transaccion.
     * 
     * @return origen
     */
    public java.lang.String getOrigen() {
        return origen;
    }


    /**
     * Sets the origen value for this Transaccion.
     * 
     * @param origen
     */
    public void setOrigen(java.lang.String origen) {
        this.origen = origen;
    }


    /**
     * Gets the empresa value for this Transaccion.
     * 
     * @return empresa
     */
    public java.lang.Integer getEmpresa() {
        return empresa;
    }


    /**
     * Sets the empresa value for this Transaccion.
     * 
     * @param empresa
     */
    public void setEmpresa(java.lang.Integer empresa) {
        this.empresa = empresa;
    }


    /**
     * Gets the rut value for this Transaccion.
     * 
     * @return rut
     */
    public java.lang.Long getRut() {
        return rut;
    }


    /**
     * Sets the rut value for this Transaccion.
     * 
     * @param rut
     */
    public void setRut(java.lang.Long rut) {
        this.rut = rut;
    }


    /**
     * Gets the dv value for this Transaccion.
     * 
     * @return dv
     */
    public java.lang.String getDv() {
        return dv;
    }


    /**
     * Sets the dv value for this Transaccion.
     * 
     * @param dv
     */
    public void setDv(java.lang.String dv) {
        this.dv = dv;
    }


    /**
     * Gets the tipoRegistro value for this Transaccion.
     * 
     * @return tipoRegistro
     */
    public java.lang.String getTipoRegistro() {
        return tipoRegistro;
    }


    /**
     * Sets the tipoRegistro value for this Transaccion.
     * 
     * @param tipoRegistro
     */
    public void setTipoRegistro(java.lang.String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }


    /**
     * Gets the fechaVencimiento value for this Transaccion.
     * 
     * @return fechaVencimiento
     */
    public java.lang.String getFechaVencimiento() {
        return fechaVencimiento;
    }


    /**
     * Sets the fechaVencimiento value for this Transaccion.
     * 
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(java.lang.String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    /**
     * Gets the codigoPortador value for this Transaccion.
     * 
     * @return codigoPortador
     */
    public java.lang.Integer getCodigoPortador() {
        return codigoPortador;
    }


    /**
     * Sets the codigoPortador value for this Transaccion.
     * 
     * @param codigoPortador
     */
    public void setCodigoPortador(java.lang.Integer codigoPortador) {
        this.codigoPortador = codigoPortador;
    }


    /**
     * Gets the cuentaCliente value for this Transaccion.
     * 
     * @return cuentaCliente
     */
    public java.lang.String getCuentaCliente() {
        return cuentaCliente;
    }


    /**
     * Sets the cuentaCliente value for this Transaccion.
     * 
     * @param cuentaCliente
     */
    public void setCuentaCliente(java.lang.String cuentaCliente) {
        this.cuentaCliente = cuentaCliente;
    }


    /**
     * Gets the nroOperacionAReversar value for this Transaccion.
     * 
     * @return nroOperacionAReversar
     */
    public java.lang.Long getNroOperacionAReversar() {
        return nroOperacionAReversar;
    }


    /**
     * Sets the nroOperacionAReversar value for this Transaccion.
     * 
     * @param nroOperacionAReversar
     */
    public void setNroOperacionAReversar(java.lang.Long nroOperacionAReversar) {
        this.nroOperacionAReversar = nroOperacionAReversar;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Transaccion)) return false;
        Transaccion other = (Transaccion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tipoTransaccion==null && other.getTipoTransaccion()==null) || 
             (this.tipoTransaccion!=null &&
              this.tipoTransaccion.equals(other.getTipoTransaccion()))) &&
            ((this.numeroDocumento==null && other.getNumeroDocumento()==null) || 
             (this.numeroDocumento!=null &&
              this.numeroDocumento.equals(other.getNumeroDocumento()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            ((this.servicio==null && other.getServicio()==null) || 
             (this.servicio!=null &&
              this.servicio.equals(other.getServicio()))) &&
            this.monto == other.getMonto() &&
            ((this.origen==null && other.getOrigen()==null) || 
             (this.origen!=null &&
              this.origen.equals(other.getOrigen()))) &&
            ((this.empresa==null && other.getEmpresa()==null) || 
             (this.empresa!=null &&
              this.empresa.equals(other.getEmpresa()))) &&
            ((this.rut==null && other.getRut()==null) || 
             (this.rut!=null &&
              this.rut.equals(other.getRut()))) &&
            ((this.dv==null && other.getDv()==null) || 
             (this.dv!=null &&
              this.dv.equals(other.getDv()))) &&
            ((this.tipoRegistro==null && other.getTipoRegistro()==null) || 
             (this.tipoRegistro!=null &&
              this.tipoRegistro.equals(other.getTipoRegistro()))) &&
            ((this.fechaVencimiento==null && other.getFechaVencimiento()==null) || 
             (this.fechaVencimiento!=null &&
              this.fechaVencimiento.equals(other.getFechaVencimiento()))) &&
            ((this.codigoPortador==null && other.getCodigoPortador()==null) || 
             (this.codigoPortador!=null &&
              this.codigoPortador.equals(other.getCodigoPortador()))) &&
            ((this.cuentaCliente==null && other.getCuentaCliente()==null) || 
             (this.cuentaCliente!=null &&
              this.cuentaCliente.equals(other.getCuentaCliente()))) &&
            ((this.nroOperacionAReversar==null && other.getNroOperacionAReversar()==null) || 
             (this.nroOperacionAReversar!=null &&
              this.nroOperacionAReversar.equals(other.getNroOperacionAReversar())));
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
        if (getTipoTransaccion() != null) {
            _hashCode += getTipoTransaccion().hashCode();
        }
        if (getNumeroDocumento() != null) {
            _hashCode += getNumeroDocumento().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        if (getServicio() != null) {
            _hashCode += getServicio().hashCode();
        }
        _hashCode += new Long(getMonto()).hashCode();
        if (getOrigen() != null) {
            _hashCode += getOrigen().hashCode();
        }
        if (getEmpresa() != null) {
            _hashCode += getEmpresa().hashCode();
        }
        if (getRut() != null) {
            _hashCode += getRut().hashCode();
        }
        if (getDv() != null) {
            _hashCode += getDv().hashCode();
        }
        if (getTipoRegistro() != null) {
            _hashCode += getTipoRegistro().hashCode();
        }
        if (getFechaVencimiento() != null) {
            _hashCode += getFechaVencimiento().hashCode();
        }
        if (getCodigoPortador() != null) {
            _hashCode += getCodigoPortador().hashCode();
        }
        if (getCuentaCliente() != null) {
            _hashCode += getCuentaCliente().hashCode();
        }
        if (getNroOperacionAReversar() != null) {
            _hashCode += getNroOperacionAReversar().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Transaccion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoNotificar/", "Transaccion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTransaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTransaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
        elemField.setFieldName("servicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servicio"));
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
        elemField.setFieldName("origen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "origen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("rut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rut"));
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
        elemField.setFieldName("tipoRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaVencimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaVencimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPortador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoPortador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuentaCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuentaCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
