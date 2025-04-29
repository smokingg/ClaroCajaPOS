/**
 * DetalleDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultar;

public class DetalleDocumento  implements java.io.Serializable {
    private int codigoEmpresa;

    private int codigoPortador;

    private java.lang.String idServicio;

    private java.lang.String nombreCliente;

    private java.lang.String direccionCliente;

    private java.lang.String giro;

    private java.lang.String telefonoContacto;

    private java.lang.String codigoZIP;

    private java.lang.String sistemaOrigen;

    private java.lang.String numeroCuenta;

    private long folioDocumento;

    private java.lang.String tipoDocumento;

    private long saldoNeto;

    private long saldoImpuesto;

    private long montoTotalDocumento;

    private long saldo;

    private long saldoLDI;

    private long saldoAdeudado;

    private java.lang.String fechaEmisionDocumento;

    private java.lang.String fechaVencimientoDocumento;

    private java.lang.String tipoRegistro;

    public DetalleDocumento() {
    }

    public DetalleDocumento(
           int codigoEmpresa,
           int codigoPortador,
           java.lang.String idServicio,
           java.lang.String nombreCliente,
           java.lang.String direccionCliente,
           java.lang.String giro,
           java.lang.String telefonoContacto,
           java.lang.String codigoZIP,
           java.lang.String sistemaOrigen,
           java.lang.String numeroCuenta,
           long folioDocumento,
           java.lang.String tipoDocumento,
           long saldoNeto,
           long saldoImpuesto,
           long montoTotalDocumento,
           long saldo,
           long saldoLDI,
           long saldoAdeudado,
           java.lang.String fechaEmisionDocumento,
           java.lang.String fechaVencimientoDocumento,
           java.lang.String tipoRegistro) {
           this.codigoEmpresa = codigoEmpresa;
           this.codigoPortador = codigoPortador;
           this.idServicio = idServicio;
           this.nombreCliente = nombreCliente;
           this.direccionCliente = direccionCliente;
           this.giro = giro;
           this.telefonoContacto = telefonoContacto;
           this.codigoZIP = codigoZIP;
           this.sistemaOrigen = sistemaOrigen;
           this.numeroCuenta = numeroCuenta;
           this.folioDocumento = folioDocumento;
           this.tipoDocumento = tipoDocumento;
           this.saldoNeto = saldoNeto;
           this.saldoImpuesto = saldoImpuesto;
           this.montoTotalDocumento = montoTotalDocumento;
           this.saldo = saldo;
           this.saldoLDI = saldoLDI;
           this.saldoAdeudado = saldoAdeudado;
           this.fechaEmisionDocumento = fechaEmisionDocumento;
           this.fechaVencimientoDocumento = fechaVencimientoDocumento;
           this.tipoRegistro = tipoRegistro;
    }


    /**
     * Gets the codigoEmpresa value for this DetalleDocumento.
     * 
     * @return codigoEmpresa
     */
    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }


    /**
     * Sets the codigoEmpresa value for this DetalleDocumento.
     * 
     * @param codigoEmpresa
     */
    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }


    /**
     * Gets the codigoPortador value for this DetalleDocumento.
     * 
     * @return codigoPortador
     */
    public int getCodigoPortador() {
        return codigoPortador;
    }


    /**
     * Sets the codigoPortador value for this DetalleDocumento.
     * 
     * @param codigoPortador
     */
    public void setCodigoPortador(int codigoPortador) {
        this.codigoPortador = codigoPortador;
    }


    /**
     * Gets the idServicio value for this DetalleDocumento.
     * 
     * @return idServicio
     */
    public java.lang.String getIdServicio() {
        return idServicio;
    }


    /**
     * Sets the idServicio value for this DetalleDocumento.
     * 
     * @param idServicio
     */
    public void setIdServicio(java.lang.String idServicio) {
        this.idServicio = idServicio;
    }


    /**
     * Gets the nombreCliente value for this DetalleDocumento.
     * 
     * @return nombreCliente
     */
    public java.lang.String getNombreCliente() {
        return nombreCliente;
    }


    /**
     * Sets the nombreCliente value for this DetalleDocumento.
     * 
     * @param nombreCliente
     */
    public void setNombreCliente(java.lang.String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }


    /**
     * Gets the direccionCliente value for this DetalleDocumento.
     * 
     * @return direccionCliente
     */
    public java.lang.String getDireccionCliente() {
        return direccionCliente;
    }


    /**
     * Sets the direccionCliente value for this DetalleDocumento.
     * 
     * @param direccionCliente
     */
    public void setDireccionCliente(java.lang.String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }


    /**
     * Gets the giro value for this DetalleDocumento.
     * 
     * @return giro
     */
    public java.lang.String getGiro() {
        return giro;
    }


    /**
     * Sets the giro value for this DetalleDocumento.
     * 
     * @param giro
     */
    public void setGiro(java.lang.String giro) {
        this.giro = giro;
    }


    /**
     * Gets the telefonoContacto value for this DetalleDocumento.
     * 
     * @return telefonoContacto
     */
    public java.lang.String getTelefonoContacto() {
        return telefonoContacto;
    }


    /**
     * Sets the telefonoContacto value for this DetalleDocumento.
     * 
     * @param telefonoContacto
     */
    public void setTelefonoContacto(java.lang.String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }


    /**
     * Gets the codigoZIP value for this DetalleDocumento.
     * 
     * @return codigoZIP
     */
    public java.lang.String getCodigoZIP() {
        return codigoZIP;
    }


    /**
     * Sets the codigoZIP value for this DetalleDocumento.
     * 
     * @param codigoZIP
     */
    public void setCodigoZIP(java.lang.String codigoZIP) {
        this.codigoZIP = codigoZIP;
    }


    /**
     * Gets the sistemaOrigen value for this DetalleDocumento.
     * 
     * @return sistemaOrigen
     */
    public java.lang.String getSistemaOrigen() {
        return sistemaOrigen;
    }


    /**
     * Sets the sistemaOrigen value for this DetalleDocumento.
     * 
     * @param sistemaOrigen
     */
    public void setSistemaOrigen(java.lang.String sistemaOrigen) {
        this.sistemaOrigen = sistemaOrigen;
    }


    /**
     * Gets the numeroCuenta value for this DetalleDocumento.
     * 
     * @return numeroCuenta
     */
    public java.lang.String getNumeroCuenta() {
        return numeroCuenta;
    }


    /**
     * Sets the numeroCuenta value for this DetalleDocumento.
     * 
     * @param numeroCuenta
     */
    public void setNumeroCuenta(java.lang.String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    /**
     * Gets the folioDocumento value for this DetalleDocumento.
     * 
     * @return folioDocumento
     */
    public long getFolioDocumento() {
        return folioDocumento;
    }


    /**
     * Sets the folioDocumento value for this DetalleDocumento.
     * 
     * @param folioDocumento
     */
    public void setFolioDocumento(long folioDocumento) {
        this.folioDocumento = folioDocumento;
    }


    /**
     * Gets the tipoDocumento value for this DetalleDocumento.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this DetalleDocumento.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the saldoNeto value for this DetalleDocumento.
     * 
     * @return saldoNeto
     */
    public long getSaldoNeto() {
        return saldoNeto;
    }


    /**
     * Sets the saldoNeto value for this DetalleDocumento.
     * 
     * @param saldoNeto
     */
    public void setSaldoNeto(long saldoNeto) {
        this.saldoNeto = saldoNeto;
    }


    /**
     * Gets the saldoImpuesto value for this DetalleDocumento.
     * 
     * @return saldoImpuesto
     */
    public long getSaldoImpuesto() {
        return saldoImpuesto;
    }


    /**
     * Sets the saldoImpuesto value for this DetalleDocumento.
     * 
     * @param saldoImpuesto
     */
    public void setSaldoImpuesto(long saldoImpuesto) {
        this.saldoImpuesto = saldoImpuesto;
    }


    /**
     * Gets the montoTotalDocumento value for this DetalleDocumento.
     * 
     * @return montoTotalDocumento
     */
    public long getMontoTotalDocumento() {
        return montoTotalDocumento;
    }


    /**
     * Sets the montoTotalDocumento value for this DetalleDocumento.
     * 
     * @param montoTotalDocumento
     */
    public void setMontoTotalDocumento(long montoTotalDocumento) {
        this.montoTotalDocumento = montoTotalDocumento;
    }


    /**
     * Gets the saldo value for this DetalleDocumento.
     * 
     * @return saldo
     */
    public long getSaldo() {
        return saldo;
    }


    /**
     * Sets the saldo value for this DetalleDocumento.
     * 
     * @param saldo
     */
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }


    /**
     * Gets the saldoLDI value for this DetalleDocumento.
     * 
     * @return saldoLDI
     */
    public long getSaldoLDI() {
        return saldoLDI;
    }


    /**
     * Sets the saldoLDI value for this DetalleDocumento.
     * 
     * @param saldoLDI
     */
    public void setSaldoLDI(long saldoLDI) {
        this.saldoLDI = saldoLDI;
    }


    /**
     * Gets the saldoAdeudado value for this DetalleDocumento.
     * 
     * @return saldoAdeudado
     */
    public long getSaldoAdeudado() {
        return saldoAdeudado;
    }


    /**
     * Sets the saldoAdeudado value for this DetalleDocumento.
     * 
     * @param saldoAdeudado
     */
    public void setSaldoAdeudado(long saldoAdeudado) {
        this.saldoAdeudado = saldoAdeudado;
    }


    /**
     * Gets the fechaEmisionDocumento value for this DetalleDocumento.
     * 
     * @return fechaEmisionDocumento
     */
    public java.lang.String getFechaEmisionDocumento() {
        return fechaEmisionDocumento;
    }


    /**
     * Sets the fechaEmisionDocumento value for this DetalleDocumento.
     * 
     * @param fechaEmisionDocumento
     */
    public void setFechaEmisionDocumento(java.lang.String fechaEmisionDocumento) {
        this.fechaEmisionDocumento = fechaEmisionDocumento;
    }


    /**
     * Gets the fechaVencimientoDocumento value for this DetalleDocumento.
     * 
     * @return fechaVencimientoDocumento
     */
    public java.lang.String getFechaVencimientoDocumento() {
        return fechaVencimientoDocumento;
    }


    /**
     * Sets the fechaVencimientoDocumento value for this DetalleDocumento.
     * 
     * @param fechaVencimientoDocumento
     */
    public void setFechaVencimientoDocumento(java.lang.String fechaVencimientoDocumento) {
        this.fechaVencimientoDocumento = fechaVencimientoDocumento;
    }


    /**
     * Gets the tipoRegistro value for this DetalleDocumento.
     * 
     * @return tipoRegistro
     */
    public java.lang.String getTipoRegistro() {
        return tipoRegistro;
    }


    /**
     * Sets the tipoRegistro value for this DetalleDocumento.
     * 
     * @param tipoRegistro
     */
    public void setTipoRegistro(java.lang.String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DetalleDocumento)) return false;
        DetalleDocumento other = (DetalleDocumento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigoEmpresa == other.getCodigoEmpresa() &&
            this.codigoPortador == other.getCodigoPortador() &&
            ((this.idServicio==null && other.getIdServicio()==null) || 
             (this.idServicio!=null &&
              this.idServicio.equals(other.getIdServicio()))) &&
            ((this.nombreCliente==null && other.getNombreCliente()==null) || 
             (this.nombreCliente!=null &&
              this.nombreCliente.equals(other.getNombreCliente()))) &&
            ((this.direccionCliente==null && other.getDireccionCliente()==null) || 
             (this.direccionCliente!=null &&
              this.direccionCliente.equals(other.getDireccionCliente()))) &&
            ((this.giro==null && other.getGiro()==null) || 
             (this.giro!=null &&
              this.giro.equals(other.getGiro()))) &&
            ((this.telefonoContacto==null && other.getTelefonoContacto()==null) || 
             (this.telefonoContacto!=null &&
              this.telefonoContacto.equals(other.getTelefonoContacto()))) &&
            ((this.codigoZIP==null && other.getCodigoZIP()==null) || 
             (this.codigoZIP!=null &&
              this.codigoZIP.equals(other.getCodigoZIP()))) &&
            ((this.sistemaOrigen==null && other.getSistemaOrigen()==null) || 
             (this.sistemaOrigen!=null &&
              this.sistemaOrigen.equals(other.getSistemaOrigen()))) &&
            ((this.numeroCuenta==null && other.getNumeroCuenta()==null) || 
             (this.numeroCuenta!=null &&
              this.numeroCuenta.equals(other.getNumeroCuenta()))) &&
            this.folioDocumento == other.getFolioDocumento() &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            this.saldoNeto == other.getSaldoNeto() &&
            this.saldoImpuesto == other.getSaldoImpuesto() &&
            this.montoTotalDocumento == other.getMontoTotalDocumento() &&
            this.saldo == other.getSaldo() &&
            this.saldoLDI == other.getSaldoLDI() &&
            this.saldoAdeudado == other.getSaldoAdeudado() &&
            ((this.fechaEmisionDocumento==null && other.getFechaEmisionDocumento()==null) || 
             (this.fechaEmisionDocumento!=null &&
              this.fechaEmisionDocumento.equals(other.getFechaEmisionDocumento()))) &&
            ((this.fechaVencimientoDocumento==null && other.getFechaVencimientoDocumento()==null) || 
             (this.fechaVencimientoDocumento!=null &&
              this.fechaVencimientoDocumento.equals(other.getFechaVencimientoDocumento()))) &&
            ((this.tipoRegistro==null && other.getTipoRegistro()==null) || 
             (this.tipoRegistro!=null &&
              this.tipoRegistro.equals(other.getTipoRegistro())));
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
        _hashCode += getCodigoEmpresa();
        _hashCode += getCodigoPortador();
        if (getIdServicio() != null) {
            _hashCode += getIdServicio().hashCode();
        }
        if (getNombreCliente() != null) {
            _hashCode += getNombreCliente().hashCode();
        }
        if (getDireccionCliente() != null) {
            _hashCode += getDireccionCliente().hashCode();
        }
        if (getGiro() != null) {
            _hashCode += getGiro().hashCode();
        }
        if (getTelefonoContacto() != null) {
            _hashCode += getTelefonoContacto().hashCode();
        }
        if (getCodigoZIP() != null) {
            _hashCode += getCodigoZIP().hashCode();
        }
        if (getSistemaOrigen() != null) {
            _hashCode += getSistemaOrigen().hashCode();
        }
        if (getNumeroCuenta() != null) {
            _hashCode += getNumeroCuenta().hashCode();
        }
        _hashCode += new Long(getFolioDocumento()).hashCode();
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        _hashCode += new Long(getSaldoNeto()).hashCode();
        _hashCode += new Long(getSaldoImpuesto()).hashCode();
        _hashCode += new Long(getMontoTotalDocumento()).hashCode();
        _hashCode += new Long(getSaldo()).hashCode();
        _hashCode += new Long(getSaldoLDI()).hashCode();
        _hashCode += new Long(getSaldoAdeudado()).hashCode();
        if (getFechaEmisionDocumento() != null) {
            _hashCode += getFechaEmisionDocumento().hashCode();
        }
        if (getFechaVencimientoDocumento() != null) {
            _hashCode += getFechaVencimientoDocumento().hashCode();
        }
        if (getTipoRegistro() != null) {
            _hashCode += getTipoRegistro().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DetalleDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultar/", "DetalleDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoEmpresa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoEmpresa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPortador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoPortador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idServicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idServicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccionCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direccionCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "giro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telefonoContacto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "telefonoContacto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoZIP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoZIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemaOrigen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemaOrigen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folioDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "folioDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoNeto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoNeto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoImpuesto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoImpuesto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("montoTotalDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "montoTotalDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoLDI");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoLDI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saldoAdeudado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saldoAdeudado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaEmisionDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaEmisionDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaVencimientoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaVencimientoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
