/**
 * MedioPagoCaja.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class MedioPagoCaja  implements java.io.Serializable {
    private java.lang.Integer cantidadCuotas;

    private java.lang.String codigoAutorizacion;

    private java.lang.Integer codigoBanco;

    private java.lang.Integer codigoPlaza;

    private java.lang.String depositante;

    private java.util.Calendar fechaVencimiento;

    private long monto;

    private java.lang.String nombrePagador;

    private java.lang.String numeroCheque;

    private java.lang.String numeroCtaCte;

    private java.lang.String numeroDeposito;

    private java.lang.String numeroTarjeta;

    private java.lang.Long numeroTransaccion;

    private java.lang.String rutPagador;

    private java.lang.String serieValeVista;

    private java.lang.String tipoTotal;

    private java.lang.String tipoTransaccion;

    public MedioPagoCaja() {
    }

    public MedioPagoCaja(
           java.lang.Integer cantidadCuotas,
           java.lang.String codigoAutorizacion,
           java.lang.Integer codigoBanco,
           java.lang.Integer codigoPlaza,
           java.lang.String depositante,
           java.util.Calendar fechaVencimiento,
           long monto,
           java.lang.String nombrePagador,
           java.lang.String numeroCheque,
           java.lang.String numeroCtaCte,
           java.lang.String numeroDeposito,
           java.lang.String numeroTarjeta,
           java.lang.Long numeroTransaccion,
           java.lang.String rutPagador,
           java.lang.String serieValeVista,
           java.lang.String tipoTotal,
           java.lang.String tipoTransaccion) {
           this.cantidadCuotas = cantidadCuotas;
           this.codigoAutorizacion = codigoAutorizacion;
           this.codigoBanco = codigoBanco;
           this.codigoPlaza = codigoPlaza;
           this.depositante = depositante;
           this.fechaVencimiento = fechaVencimiento;
           this.monto = monto;
           this.nombrePagador = nombrePagador;
           this.numeroCheque = numeroCheque;
           this.numeroCtaCte = numeroCtaCte;
           this.numeroDeposito = numeroDeposito;
           this.numeroTarjeta = numeroTarjeta;
           this.numeroTransaccion = numeroTransaccion;
           this.rutPagador = rutPagador;
           this.serieValeVista = serieValeVista;
           this.tipoTotal = tipoTotal;
           this.tipoTransaccion = tipoTransaccion;
    }


    /**
     * Gets the cantidadCuotas value for this MedioPagoCaja.
     * 
     * @return cantidadCuotas
     */
    public java.lang.Integer getCantidadCuotas() {
        return cantidadCuotas;
    }


    /**
     * Sets the cantidadCuotas value for this MedioPagoCaja.
     * 
     * @param cantidadCuotas
     */
    public void setCantidadCuotas(java.lang.Integer cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }


    /**
     * Gets the codigoAutorizacion value for this MedioPagoCaja.
     * 
     * @return codigoAutorizacion
     */
    public java.lang.String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }


    /**
     * Sets the codigoAutorizacion value for this MedioPagoCaja.
     * 
     * @param codigoAutorizacion
     */
    public void setCodigoAutorizacion(java.lang.String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }


    /**
     * Gets the codigoBanco value for this MedioPagoCaja.
     * 
     * @return codigoBanco
     */
    public java.lang.Integer getCodigoBanco() {
        return codigoBanco;
    }


    /**
     * Sets the codigoBanco value for this MedioPagoCaja.
     * 
     * @param codigoBanco
     */
    public void setCodigoBanco(java.lang.Integer codigoBanco) {
        this.codigoBanco = codigoBanco;
    }


    /**
     * Gets the codigoPlaza value for this MedioPagoCaja.
     * 
     * @return codigoPlaza
     */
    public java.lang.Integer getCodigoPlaza() {
        return codigoPlaza;
    }


    /**
     * Sets the codigoPlaza value for this MedioPagoCaja.
     * 
     * @param codigoPlaza
     */
    public void setCodigoPlaza(java.lang.Integer codigoPlaza) {
        this.codigoPlaza = codigoPlaza;
    }


    /**
     * Gets the depositante value for this MedioPagoCaja.
     * 
     * @return depositante
     */
    public java.lang.String getDepositante() {
        return depositante;
    }


    /**
     * Sets the depositante value for this MedioPagoCaja.
     * 
     * @param depositante
     */
    public void setDepositante(java.lang.String depositante) {
        this.depositante = depositante;
    }


    /**
     * Gets the fechaVencimiento value for this MedioPagoCaja.
     * 
     * @return fechaVencimiento
     */
    public java.util.Calendar getFechaVencimiento() {
        return fechaVencimiento;
    }


    /**
     * Sets the fechaVencimiento value for this MedioPagoCaja.
     * 
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(java.util.Calendar fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    /**
     * Gets the monto value for this MedioPagoCaja.
     * 
     * @return monto
     */
    public long getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this MedioPagoCaja.
     * 
     * @param monto
     */
    public void setMonto(long monto) {
        this.monto = monto;
    }


    /**
     * Gets the nombrePagador value for this MedioPagoCaja.
     * 
     * @return nombrePagador
     */
    public java.lang.String getNombrePagador() {
        return nombrePagador;
    }


    /**
     * Sets the nombrePagador value for this MedioPagoCaja.
     * 
     * @param nombrePagador
     */
    public void setNombrePagador(java.lang.String nombrePagador) {
        this.nombrePagador = nombrePagador;
    }


    /**
     * Gets the numeroCheque value for this MedioPagoCaja.
     * 
     * @return numeroCheque
     */
    public java.lang.String getNumeroCheque() {
        return numeroCheque;
    }


    /**
     * Sets the numeroCheque value for this MedioPagoCaja.
     * 
     * @param numeroCheque
     */
    public void setNumeroCheque(java.lang.String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }


    /**
     * Gets the numeroCtaCte value for this MedioPagoCaja.
     * 
     * @return numeroCtaCte
     */
    public java.lang.String getNumeroCtaCte() {
        return numeroCtaCte;
    }


    /**
     * Sets the numeroCtaCte value for this MedioPagoCaja.
     * 
     * @param numeroCtaCte
     */
    public void setNumeroCtaCte(java.lang.String numeroCtaCte) {
        this.numeroCtaCte = numeroCtaCte;
    }


    /**
     * Gets the numeroDeposito value for this MedioPagoCaja.
     * 
     * @return numeroDeposito
     */
    public java.lang.String getNumeroDeposito() {
        return numeroDeposito;
    }


    /**
     * Sets the numeroDeposito value for this MedioPagoCaja.
     * 
     * @param numeroDeposito
     */
    public void setNumeroDeposito(java.lang.String numeroDeposito) {
        this.numeroDeposito = numeroDeposito;
    }


    /**
     * Gets the numeroTarjeta value for this MedioPagoCaja.
     * 
     * @return numeroTarjeta
     */
    public java.lang.String getNumeroTarjeta() {
        return numeroTarjeta;
    }


    /**
     * Sets the numeroTarjeta value for this MedioPagoCaja.
     * 
     * @param numeroTarjeta
     */
    public void setNumeroTarjeta(java.lang.String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }


    /**
     * Gets the numeroTransaccion value for this MedioPagoCaja.
     * 
     * @return numeroTransaccion
     */
    public java.lang.Long getNumeroTransaccion() {
        return numeroTransaccion;
    }


    /**
     * Sets the numeroTransaccion value for this MedioPagoCaja.
     * 
     * @param numeroTransaccion
     */
    public void setNumeroTransaccion(java.lang.Long numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }


    /**
     * Gets the rutPagador value for this MedioPagoCaja.
     * 
     * @return rutPagador
     */
    public java.lang.String getRutPagador() {
        return rutPagador;
    }


    /**
     * Sets the rutPagador value for this MedioPagoCaja.
     * 
     * @param rutPagador
     */
    public void setRutPagador(java.lang.String rutPagador) {
        this.rutPagador = rutPagador;
    }


    /**
     * Gets the serieValeVista value for this MedioPagoCaja.
     * 
     * @return serieValeVista
     */
    public java.lang.String getSerieValeVista() {
        return serieValeVista;
    }


    /**
     * Sets the serieValeVista value for this MedioPagoCaja.
     * 
     * @param serieValeVista
     */
    public void setSerieValeVista(java.lang.String serieValeVista) {
        this.serieValeVista = serieValeVista;
    }


    /**
     * Gets the tipoTotal value for this MedioPagoCaja.
     * 
     * @return tipoTotal
     */
    public java.lang.String getTipoTotal() {
        return tipoTotal;
    }


    /**
     * Sets the tipoTotal value for this MedioPagoCaja.
     * 
     * @param tipoTotal
     */
    public void setTipoTotal(java.lang.String tipoTotal) {
        this.tipoTotal = tipoTotal;
    }


    /**
     * Gets the tipoTransaccion value for this MedioPagoCaja.
     * 
     * @return tipoTransaccion
     */
    public java.lang.String getTipoTransaccion() {
        return tipoTransaccion;
    }


    /**
     * Sets the tipoTransaccion value for this MedioPagoCaja.
     * 
     * @param tipoTransaccion
     */
    public void setTipoTransaccion(java.lang.String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MedioPagoCaja)) return false;
        MedioPagoCaja other = (MedioPagoCaja) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cantidadCuotas==null && other.getCantidadCuotas()==null) || 
             (this.cantidadCuotas!=null &&
              this.cantidadCuotas.equals(other.getCantidadCuotas()))) &&
            ((this.codigoAutorizacion==null && other.getCodigoAutorizacion()==null) || 
             (this.codigoAutorizacion!=null &&
              this.codigoAutorizacion.equals(other.getCodigoAutorizacion()))) &&
            ((this.codigoBanco==null && other.getCodigoBanco()==null) || 
             (this.codigoBanco!=null &&
              this.codigoBanco.equals(other.getCodigoBanco()))) &&
            ((this.codigoPlaza==null && other.getCodigoPlaza()==null) || 
             (this.codigoPlaza!=null &&
              this.codigoPlaza.equals(other.getCodigoPlaza()))) &&
            ((this.depositante==null && other.getDepositante()==null) || 
             (this.depositante!=null &&
              this.depositante.equals(other.getDepositante()))) &&
            ((this.fechaVencimiento==null && other.getFechaVencimiento()==null) || 
             (this.fechaVencimiento!=null &&
              this.fechaVencimiento.equals(other.getFechaVencimiento()))) &&
            this.monto == other.getMonto() &&
            ((this.nombrePagador==null && other.getNombrePagador()==null) || 
             (this.nombrePagador!=null &&
              this.nombrePagador.equals(other.getNombrePagador()))) &&
            ((this.numeroCheque==null && other.getNumeroCheque()==null) || 
             (this.numeroCheque!=null &&
              this.numeroCheque.equals(other.getNumeroCheque()))) &&
            ((this.numeroCtaCte==null && other.getNumeroCtaCte()==null) || 
             (this.numeroCtaCte!=null &&
              this.numeroCtaCte.equals(other.getNumeroCtaCte()))) &&
            ((this.numeroDeposito==null && other.getNumeroDeposito()==null) || 
             (this.numeroDeposito!=null &&
              this.numeroDeposito.equals(other.getNumeroDeposito()))) &&
            ((this.numeroTarjeta==null && other.getNumeroTarjeta()==null) || 
             (this.numeroTarjeta!=null &&
              this.numeroTarjeta.equals(other.getNumeroTarjeta()))) &&
            ((this.numeroTransaccion==null && other.getNumeroTransaccion()==null) || 
             (this.numeroTransaccion!=null &&
              this.numeroTransaccion.equals(other.getNumeroTransaccion()))) &&
            ((this.rutPagador==null && other.getRutPagador()==null) || 
             (this.rutPagador!=null &&
              this.rutPagador.equals(other.getRutPagador()))) &&
            ((this.serieValeVista==null && other.getSerieValeVista()==null) || 
             (this.serieValeVista!=null &&
              this.serieValeVista.equals(other.getSerieValeVista()))) &&
            ((this.tipoTotal==null && other.getTipoTotal()==null) || 
             (this.tipoTotal!=null &&
              this.tipoTotal.equals(other.getTipoTotal()))) &&
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
        if (getCantidadCuotas() != null) {
            _hashCode += getCantidadCuotas().hashCode();
        }
        if (getCodigoAutorizacion() != null) {
            _hashCode += getCodigoAutorizacion().hashCode();
        }
        if (getCodigoBanco() != null) {
            _hashCode += getCodigoBanco().hashCode();
        }
        if (getCodigoPlaza() != null) {
            _hashCode += getCodigoPlaza().hashCode();
        }
        if (getDepositante() != null) {
            _hashCode += getDepositante().hashCode();
        }
        if (getFechaVencimiento() != null) {
            _hashCode += getFechaVencimiento().hashCode();
        }
        _hashCode += new Long(getMonto()).hashCode();
        if (getNombrePagador() != null) {
            _hashCode += getNombrePagador().hashCode();
        }
        if (getNumeroCheque() != null) {
            _hashCode += getNumeroCheque().hashCode();
        }
        if (getNumeroCtaCte() != null) {
            _hashCode += getNumeroCtaCte().hashCode();
        }
        if (getNumeroDeposito() != null) {
            _hashCode += getNumeroDeposito().hashCode();
        }
        if (getNumeroTarjeta() != null) {
            _hashCode += getNumeroTarjeta().hashCode();
        }
        if (getNumeroTransaccion() != null) {
            _hashCode += getNumeroTransaccion().hashCode();
        }
        if (getRutPagador() != null) {
            _hashCode += getRutPagador().hashCode();
        }
        if (getSerieValeVista() != null) {
            _hashCode += getSerieValeVista().hashCode();
        }
        if (getTipoTotal() != null) {
            _hashCode += getTipoTotal().hashCode();
        }
        if (getTipoTransaccion() != null) {
            _hashCode += getTipoTransaccion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MedioPagoCaja.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "medioPagoCaja"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cantidadCuotas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cantidadCuotas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("depositante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depositante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombrePagador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombrePagador"));
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
        elemField.setFieldName("numeroDeposito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroDeposito"));
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
        elemField.setFieldName("numeroTransaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroTransaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rutPagador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rutPagador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("tipoTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTotal"));
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
