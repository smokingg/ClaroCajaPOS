/**
 * MedioPago.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoNotificar;

public class MedioPago  implements java.io.Serializable {
    private java.lang.String tipoTransaccion;

    private java.lang.Integer codigoBanco;

    private java.lang.Integer codigoPlaza;

    private long monto;

    private java.lang.String fechaVencimiento;

    private java.lang.String numeroCheque;

    private java.lang.String numeroCtaCte;

    private java.lang.String pagadorRut;

    private java.lang.String pagadorDigitoVerificador;

    private java.lang.String pagadorNombre;

    private java.lang.String codigoAutorizacion;

    private java.lang.String numeroTarjeta;

    private java.lang.Integer cantidadCuotas;

    private java.lang.String serieValeVista;

    private java.lang.String numeroDeposito;

    private java.lang.String depositante;

    private java.lang.String tipoTotal;

    private java.lang.String fechaContable;

    private java.lang.String idTrxTbk;

    private java.lang.String tbkContingencia;

    private java.lang.String productoTarjeta;

    public MedioPago() {
    }

    public MedioPago(
           java.lang.String tipoTransaccion,
           java.lang.Integer codigoBanco,
           java.lang.Integer codigoPlaza,
           long monto,
           java.lang.String fechaVencimiento,
           java.lang.String numeroCheque,
           java.lang.String numeroCtaCte,
           java.lang.String pagadorRut,
           java.lang.String pagadorDigitoVerificador,
           java.lang.String pagadorNombre,
           java.lang.String codigoAutorizacion,
           java.lang.String numeroTarjeta,
           java.lang.Integer cantidadCuotas,
           java.lang.String serieValeVista,
           java.lang.String numeroDeposito,
           java.lang.String depositante,
           java.lang.String tipoTotal,
           java.lang.String fechaContable,
           java.lang.String idTrxTbk,
           java.lang.String tbkContingencia,
           java.lang.String productoTarjeta) {
           this.tipoTransaccion = tipoTransaccion;
           this.codigoBanco = codigoBanco;
           this.codigoPlaza = codigoPlaza;
           this.monto = monto;
           this.fechaVencimiento = fechaVencimiento;
           this.numeroCheque = numeroCheque;
           this.numeroCtaCte = numeroCtaCte;
           this.pagadorRut = pagadorRut;
           this.pagadorDigitoVerificador = pagadorDigitoVerificador;
           this.pagadorNombre = pagadorNombre;
           this.codigoAutorizacion = codigoAutorizacion;
           this.numeroTarjeta = numeroTarjeta;
           this.cantidadCuotas = cantidadCuotas;
           this.serieValeVista = serieValeVista;
           this.numeroDeposito = numeroDeposito;
           this.depositante = depositante;
           this.tipoTotal = tipoTotal;
           this.fechaContable = fechaContable;
           this.idTrxTbk = idTrxTbk;
           this.tbkContingencia = tbkContingencia;
           this.productoTarjeta = productoTarjeta;
    }


    /**
     * Gets the tipoTransaccion value for this MedioPago.
     * 
     * @return tipoTransaccion
     */
    public java.lang.String getTipoTransaccion() {
        return tipoTransaccion;
    }


    /**
     * Sets the tipoTransaccion value for this MedioPago.
     * 
     * @param tipoTransaccion
     */
    public void setTipoTransaccion(java.lang.String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }


    /**
     * Gets the codigoBanco value for this MedioPago.
     * 
     * @return codigoBanco
     */
    public java.lang.Integer getCodigoBanco() {
        return codigoBanco;
    }


    /**
     * Sets the codigoBanco value for this MedioPago.
     * 
     * @param codigoBanco
     */
    public void setCodigoBanco(java.lang.Integer codigoBanco) {
        this.codigoBanco = codigoBanco;
    }


    /**
     * Gets the codigoPlaza value for this MedioPago.
     * 
     * @return codigoPlaza
     */
    public java.lang.Integer getCodigoPlaza() {
        return codigoPlaza;
    }


    /**
     * Sets the codigoPlaza value for this MedioPago.
     * 
     * @param codigoPlaza
     */
    public void setCodigoPlaza(java.lang.Integer codigoPlaza) {
        this.codigoPlaza = codigoPlaza;
    }


    /**
     * Gets the monto value for this MedioPago.
     * 
     * @return monto
     */
    public long getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this MedioPago.
     * 
     * @param monto
     */
    public void setMonto(long monto) {
        this.monto = monto;
    }


    /**
     * Gets the fechaVencimiento value for this MedioPago.
     * 
     * @return fechaVencimiento
     */
    public java.lang.String getFechaVencimiento() {
        return fechaVencimiento;
    }


    /**
     * Sets the fechaVencimiento value for this MedioPago.
     * 
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(java.lang.String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    /**
     * Gets the numeroCheque value for this MedioPago.
     * 
     * @return numeroCheque
     */
    public java.lang.String getNumeroCheque() {
        return numeroCheque;
    }


    /**
     * Sets the numeroCheque value for this MedioPago.
     * 
     * @param numeroCheque
     */
    public void setNumeroCheque(java.lang.String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }


    /**
     * Gets the numeroCtaCte value for this MedioPago.
     * 
     * @return numeroCtaCte
     */
    public java.lang.String getNumeroCtaCte() {
        return numeroCtaCte;
    }


    /**
     * Sets the numeroCtaCte value for this MedioPago.
     * 
     * @param numeroCtaCte
     */
    public void setNumeroCtaCte(java.lang.String numeroCtaCte) {
        this.numeroCtaCte = numeroCtaCte;
    }


    /**
     * Gets the pagadorRut value for this MedioPago.
     * 
     * @return pagadorRut
     */
    public java.lang.String getPagadorRut() {
        return pagadorRut;
    }


    /**
     * Sets the pagadorRut value for this MedioPago.
     * 
     * @param pagadorRut
     */
    public void setPagadorRut(java.lang.String pagadorRut) {
        this.pagadorRut = pagadorRut;
    }


    /**
     * Gets the pagadorDigitoVerificador value for this MedioPago.
     * 
     * @return pagadorDigitoVerificador
     */
    public java.lang.String getPagadorDigitoVerificador() {
        return pagadorDigitoVerificador;
    }


    /**
     * Sets the pagadorDigitoVerificador value for this MedioPago.
     * 
     * @param pagadorDigitoVerificador
     */
    public void setPagadorDigitoVerificador(java.lang.String pagadorDigitoVerificador) {
        this.pagadorDigitoVerificador = pagadorDigitoVerificador;
    }


    /**
     * Gets the pagadorNombre value for this MedioPago.
     * 
     * @return pagadorNombre
     */
    public java.lang.String getPagadorNombre() {
        return pagadorNombre;
    }


    /**
     * Sets the pagadorNombre value for this MedioPago.
     * 
     * @param pagadorNombre
     */
    public void setPagadorNombre(java.lang.String pagadorNombre) {
        this.pagadorNombre = pagadorNombre;
    }


    /**
     * Gets the codigoAutorizacion value for this MedioPago.
     * 
     * @return codigoAutorizacion
     */
    public java.lang.String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }


    /**
     * Sets the codigoAutorizacion value for this MedioPago.
     * 
     * @param codigoAutorizacion
     */
    public void setCodigoAutorizacion(java.lang.String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }


    /**
     * Gets the numeroTarjeta value for this MedioPago.
     * 
     * @return numeroTarjeta
     */
    public java.lang.String getNumeroTarjeta() {
        return numeroTarjeta;
    }


    /**
     * Sets the numeroTarjeta value for this MedioPago.
     * 
     * @param numeroTarjeta
     */
    public void setNumeroTarjeta(java.lang.String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }


    /**
     * Gets the cantidadCuotas value for this MedioPago.
     * 
     * @return cantidadCuotas
     */
    public java.lang.Integer getCantidadCuotas() {
        return cantidadCuotas;
    }


    /**
     * Sets the cantidadCuotas value for this MedioPago.
     * 
     * @param cantidadCuotas
     */
    public void setCantidadCuotas(java.lang.Integer cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }


    /**
     * Gets the serieValeVista value for this MedioPago.
     * 
     * @return serieValeVista
     */
    public java.lang.String getSerieValeVista() {
        return serieValeVista;
    }


    /**
     * Sets the serieValeVista value for this MedioPago.
     * 
     * @param serieValeVista
     */
    public void setSerieValeVista(java.lang.String serieValeVista) {
        this.serieValeVista = serieValeVista;
    }


    /**
     * Gets the numeroDeposito value for this MedioPago.
     * 
     * @return numeroDeposito
     */
    public java.lang.String getNumeroDeposito() {
        return numeroDeposito;
    }


    /**
     * Sets the numeroDeposito value for this MedioPago.
     * 
     * @param numeroDeposito
     */
    public void setNumeroDeposito(java.lang.String numeroDeposito) {
        this.numeroDeposito = numeroDeposito;
    }


    /**
     * Gets the depositante value for this MedioPago.
     * 
     * @return depositante
     */
    public java.lang.String getDepositante() {
        return depositante;
    }


    /**
     * Sets the depositante value for this MedioPago.
     * 
     * @param depositante
     */
    public void setDepositante(java.lang.String depositante) {
        this.depositante = depositante;
    }


    /**
     * Gets the tipoTotal value for this MedioPago.
     * 
     * @return tipoTotal
     */
    public java.lang.String getTipoTotal() {
        return tipoTotal;
    }


    /**
     * Sets the tipoTotal value for this MedioPago.
     * 
     * @param tipoTotal
     */
    public void setTipoTotal(java.lang.String tipoTotal) {
        this.tipoTotal = tipoTotal;
    }


    /**
     * Gets the fechaContable value for this MedioPago.
     * 
     * @return fechaContable
     */
    public java.lang.String getFechaContable() {
        return fechaContable;
    }


    /**
     * Sets the fechaContable value for this MedioPago.
     * 
     * @param fechaContable
     */
    public void setFechaContable(java.lang.String fechaContable) {
        this.fechaContable = fechaContable;
    }


    /**
     * Gets the idTrxTbk value for this MedioPago.
     * 
     * @return idTrxTbk
     */
    public java.lang.String getIdTrxTbk() {
        return idTrxTbk;
    }


    /**
     * Sets the idTrxTbk value for this MedioPago.
     * 
     * @param idTrxTbk
     */
    public void setIdTrxTbk(java.lang.String idTrxTbk) {
        this.idTrxTbk = idTrxTbk;
    }


    /**
     * Gets the tbkContingencia value for this MedioPago.
     * 
     * @return tbkContingencia
     */
    public java.lang.String getTbkContingencia() {
        return tbkContingencia;
    }


    /**
     * Sets the tbkContingencia value for this MedioPago.
     * 
     * @param tbkContingencia
     */
    public void setTbkContingencia(java.lang.String tbkContingencia) {
        this.tbkContingencia = tbkContingencia;
    }


    /**
     * Gets the productoTarjeta value for this MedioPago.
     * 
     * @return productoTarjeta
     */
    public java.lang.String getProductoTarjeta() {
        return productoTarjeta;
    }


    /**
     * Sets the productoTarjeta value for this MedioPago.
     * 
     * @param productoTarjeta
     */
    public void setProductoTarjeta(java.lang.String productoTarjeta) {
        this.productoTarjeta = productoTarjeta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MedioPago)) return false;
        MedioPago other = (MedioPago) obj;
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
            ((this.codigoBanco==null && other.getCodigoBanco()==null) || 
             (this.codigoBanco!=null &&
              this.codigoBanco.equals(other.getCodigoBanco()))) &&
            ((this.codigoPlaza==null && other.getCodigoPlaza()==null) || 
             (this.codigoPlaza!=null &&
              this.codigoPlaza.equals(other.getCodigoPlaza()))) &&
            this.monto == other.getMonto() &&
            ((this.fechaVencimiento==null && other.getFechaVencimiento()==null) || 
             (this.fechaVencimiento!=null &&
              this.fechaVencimiento.equals(other.getFechaVencimiento()))) &&
            ((this.numeroCheque==null && other.getNumeroCheque()==null) || 
             (this.numeroCheque!=null &&
              this.numeroCheque.equals(other.getNumeroCheque()))) &&
            ((this.numeroCtaCte==null && other.getNumeroCtaCte()==null) || 
             (this.numeroCtaCte!=null &&
              this.numeroCtaCte.equals(other.getNumeroCtaCte()))) &&
            ((this.pagadorRut==null && other.getPagadorRut()==null) || 
             (this.pagadorRut!=null &&
              this.pagadorRut.equals(other.getPagadorRut()))) &&
            ((this.pagadorDigitoVerificador==null && other.getPagadorDigitoVerificador()==null) || 
             (this.pagadorDigitoVerificador!=null &&
              this.pagadorDigitoVerificador.equals(other.getPagadorDigitoVerificador()))) &&
            ((this.pagadorNombre==null && other.getPagadorNombre()==null) || 
             (this.pagadorNombre!=null &&
              this.pagadorNombre.equals(other.getPagadorNombre()))) &&
            ((this.codigoAutorizacion==null && other.getCodigoAutorizacion()==null) || 
             (this.codigoAutorizacion!=null &&
              this.codigoAutorizacion.equals(other.getCodigoAutorizacion()))) &&
            ((this.numeroTarjeta==null && other.getNumeroTarjeta()==null) || 
             (this.numeroTarjeta!=null &&
              this.numeroTarjeta.equals(other.getNumeroTarjeta()))) &&
            ((this.cantidadCuotas==null && other.getCantidadCuotas()==null) || 
             (this.cantidadCuotas!=null &&
              this.cantidadCuotas.equals(other.getCantidadCuotas()))) &&
            ((this.serieValeVista==null && other.getSerieValeVista()==null) || 
             (this.serieValeVista!=null &&
              this.serieValeVista.equals(other.getSerieValeVista()))) &&
            ((this.numeroDeposito==null && other.getNumeroDeposito()==null) || 
             (this.numeroDeposito!=null &&
              this.numeroDeposito.equals(other.getNumeroDeposito()))) &&
            ((this.depositante==null && other.getDepositante()==null) || 
             (this.depositante!=null &&
              this.depositante.equals(other.getDepositante()))) &&
            ((this.tipoTotal==null && other.getTipoTotal()==null) || 
             (this.tipoTotal!=null &&
              this.tipoTotal.equals(other.getTipoTotal()))) &&
            ((this.fechaContable==null && other.getFechaContable()==null) || 
             (this.fechaContable!=null &&
              this.fechaContable.equals(other.getFechaContable()))) &&
            ((this.idTrxTbk==null && other.getIdTrxTbk()==null) || 
             (this.idTrxTbk!=null &&
              this.idTrxTbk.equals(other.getIdTrxTbk()))) &&
            ((this.tbkContingencia==null && other.getTbkContingencia()==null) || 
             (this.tbkContingencia!=null &&
              this.tbkContingencia.equals(other.getTbkContingencia()))) &&
            ((this.productoTarjeta==null && other.getProductoTarjeta()==null) || 
             (this.productoTarjeta!=null &&
              this.productoTarjeta.equals(other.getProductoTarjeta())));
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
        if (getCodigoBanco() != null) {
            _hashCode += getCodigoBanco().hashCode();
        }
        if (getCodigoPlaza() != null) {
            _hashCode += getCodigoPlaza().hashCode();
        }
        _hashCode += new Long(getMonto()).hashCode();
        if (getFechaVencimiento() != null) {
            _hashCode += getFechaVencimiento().hashCode();
        }
        if (getNumeroCheque() != null) {
            _hashCode += getNumeroCheque().hashCode();
        }
        if (getNumeroCtaCte() != null) {
            _hashCode += getNumeroCtaCte().hashCode();
        }
        if (getPagadorRut() != null) {
            _hashCode += getPagadorRut().hashCode();
        }
        if (getPagadorDigitoVerificador() != null) {
            _hashCode += getPagadorDigitoVerificador().hashCode();
        }
        if (getPagadorNombre() != null) {
            _hashCode += getPagadorNombre().hashCode();
        }
        if (getCodigoAutorizacion() != null) {
            _hashCode += getCodigoAutorizacion().hashCode();
        }
        if (getNumeroTarjeta() != null) {
            _hashCode += getNumeroTarjeta().hashCode();
        }
        if (getCantidadCuotas() != null) {
            _hashCode += getCantidadCuotas().hashCode();
        }
        if (getSerieValeVista() != null) {
            _hashCode += getSerieValeVista().hashCode();
        }
        if (getNumeroDeposito() != null) {
            _hashCode += getNumeroDeposito().hashCode();
        }
        if (getDepositante() != null) {
            _hashCode += getDepositante().hashCode();
        }
        if (getTipoTotal() != null) {
            _hashCode += getTipoTotal().hashCode();
        }
        if (getFechaContable() != null) {
            _hashCode += getFechaContable().hashCode();
        }
        if (getIdTrxTbk() != null) {
            _hashCode += getIdTrxTbk().hashCode();
        }
        if (getTbkContingencia() != null) {
            _hashCode += getTbkContingencia().hashCode();
        }
        if (getProductoTarjeta() != null) {
            _hashCode += getProductoTarjeta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MedioPago.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoNotificar/", "MedioPago"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTransaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTransaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoBanco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoBanco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPlaza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoPlaza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("fechaVencimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaVencimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCheque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCheque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCtaCte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCtaCte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pagadorRut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pagadorRut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pagadorDigitoVerificador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pagadorDigitoVerificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pagadorNombre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pagadorNombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoAutorizacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoAutorizacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroTarjeta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroTarjeta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cantidadCuotas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cantidadCuotas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serieValeVista");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serieValeVista"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDeposito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroDeposito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depositante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depositante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaContable");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaContable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTrxTbk");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTrxTbk"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tbkContingencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tbkContingencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productoTarjeta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productoTarjeta"));
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
