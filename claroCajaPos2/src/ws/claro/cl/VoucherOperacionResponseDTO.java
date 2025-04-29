/**
 * VoucherOperacionResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class VoucherOperacionResponseDTO  extends ws.claro.cl.GenericResponseDTO  implements java.io.Serializable {
    private java.lang.String codigoSesion;

    private java.lang.String codigoUsuarioEnvia;

    private java.lang.String comprobantePdf;

    private java.lang.String emailPara;

    private java.lang.String estadoAlmacenamiento;

    private java.lang.String estadoEnvio;

    private java.lang.String fechaEnvio;

    private java.lang.String fechaRegistro;

    private java.lang.String fechaUltimoReenvio;

    private java.lang.String idComprobanteAdjunto;

    private java.lang.String idComprobanteOperacion;

    private java.lang.String idEnvioComprobante;

    private java.lang.String idFileNet;

    private java.lang.String idNumeroOperacion;

    private java.lang.String propietario;

    private java.lang.String tipoComprobante;

    private java.lang.String tipoCopia;

    private java.lang.String tipoOperacion;

    public VoucherOperacionResponseDTO() {
    }

    public VoucherOperacionResponseDTO(
           java.lang.String retCode,
           java.lang.String retDesc,
           java.lang.String codigoSesion,
           java.lang.String codigoUsuarioEnvia,
           java.lang.String comprobantePdf,
           java.lang.String emailPara,
           java.lang.String estadoAlmacenamiento,
           java.lang.String estadoEnvio,
           java.lang.String fechaEnvio,
           java.lang.String fechaRegistro,
           java.lang.String fechaUltimoReenvio,
           java.lang.String idComprobanteAdjunto,
           java.lang.String idComprobanteOperacion,
           java.lang.String idEnvioComprobante,
           java.lang.String idFileNet,
           java.lang.String idNumeroOperacion,
           java.lang.String propietario,
           java.lang.String tipoComprobante,
           java.lang.String tipoCopia,
           java.lang.String tipoOperacion) {
        super(
            retCode,
            retDesc);
        this.codigoSesion = codigoSesion;
        this.codigoUsuarioEnvia = codigoUsuarioEnvia;
        this.comprobantePdf = comprobantePdf;
        this.emailPara = emailPara;
        this.estadoAlmacenamiento = estadoAlmacenamiento;
        this.estadoEnvio = estadoEnvio;
        this.fechaEnvio = fechaEnvio;
        this.fechaRegistro = fechaRegistro;
        this.fechaUltimoReenvio = fechaUltimoReenvio;
        this.idComprobanteAdjunto = idComprobanteAdjunto;
        this.idComprobanteOperacion = idComprobanteOperacion;
        this.idEnvioComprobante = idEnvioComprobante;
        this.idFileNet = idFileNet;
        this.idNumeroOperacion = idNumeroOperacion;
        this.propietario = propietario;
        this.tipoComprobante = tipoComprobante;
        this.tipoCopia = tipoCopia;
        this.tipoOperacion = tipoOperacion;
    }


    /**
     * Gets the codigoSesion value for this VoucherOperacionResponseDTO.
     * 
     * @return codigoSesion
     */
    public java.lang.String getCodigoSesion() {
        return codigoSesion;
    }


    /**
     * Sets the codigoSesion value for this VoucherOperacionResponseDTO.
     * 
     * @param codigoSesion
     */
    public void setCodigoSesion(java.lang.String codigoSesion) {
        this.codigoSesion = codigoSesion;
    }


    /**
     * Gets the codigoUsuarioEnvia value for this VoucherOperacionResponseDTO.
     * 
     * @return codigoUsuarioEnvia
     */
    public java.lang.String getCodigoUsuarioEnvia() {
        return codigoUsuarioEnvia;
    }


    /**
     * Sets the codigoUsuarioEnvia value for this VoucherOperacionResponseDTO.
     * 
     * @param codigoUsuarioEnvia
     */
    public void setCodigoUsuarioEnvia(java.lang.String codigoUsuarioEnvia) {
        this.codigoUsuarioEnvia = codigoUsuarioEnvia;
    }


    /**
     * Gets the comprobantePdf value for this VoucherOperacionResponseDTO.
     * 
     * @return comprobantePdf
     */
    public java.lang.String getComprobantePdf() {
        return comprobantePdf;
    }


    /**
     * Sets the comprobantePdf value for this VoucherOperacionResponseDTO.
     * 
     * @param comprobantePdf
     */
    public void setComprobantePdf(java.lang.String comprobantePdf) {
        this.comprobantePdf = comprobantePdf;
    }


    /**
     * Gets the emailPara value for this VoucherOperacionResponseDTO.
     * 
     * @return emailPara
     */
    public java.lang.String getEmailPara() {
        return emailPara;
    }


    /**
     * Sets the emailPara value for this VoucherOperacionResponseDTO.
     * 
     * @param emailPara
     */
    public void setEmailPara(java.lang.String emailPara) {
        this.emailPara = emailPara;
    }


    /**
     * Gets the estadoAlmacenamiento value for this VoucherOperacionResponseDTO.
     * 
     * @return estadoAlmacenamiento
     */
    public java.lang.String getEstadoAlmacenamiento() {
        return estadoAlmacenamiento;
    }


    /**
     * Sets the estadoAlmacenamiento value for this VoucherOperacionResponseDTO.
     * 
     * @param estadoAlmacenamiento
     */
    public void setEstadoAlmacenamiento(java.lang.String estadoAlmacenamiento) {
        this.estadoAlmacenamiento = estadoAlmacenamiento;
    }


    /**
     * Gets the estadoEnvio value for this VoucherOperacionResponseDTO.
     * 
     * @return estadoEnvio
     */
    public java.lang.String getEstadoEnvio() {
        return estadoEnvio;
    }


    /**
     * Sets the estadoEnvio value for this VoucherOperacionResponseDTO.
     * 
     * @param estadoEnvio
     */
    public void setEstadoEnvio(java.lang.String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }


    /**
     * Gets the fechaEnvio value for this VoucherOperacionResponseDTO.
     * 
     * @return fechaEnvio
     */
    public java.lang.String getFechaEnvio() {
        return fechaEnvio;
    }


    /**
     * Sets the fechaEnvio value for this VoucherOperacionResponseDTO.
     * 
     * @param fechaEnvio
     */
    public void setFechaEnvio(java.lang.String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }


    /**
     * Gets the fechaRegistro value for this VoucherOperacionResponseDTO.
     * 
     * @return fechaRegistro
     */
    public java.lang.String getFechaRegistro() {
        return fechaRegistro;
    }


    /**
     * Sets the fechaRegistro value for this VoucherOperacionResponseDTO.
     * 
     * @param fechaRegistro
     */
    public void setFechaRegistro(java.lang.String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    /**
     * Gets the fechaUltimoReenvio value for this VoucherOperacionResponseDTO.
     * 
     * @return fechaUltimoReenvio
     */
    public java.lang.String getFechaUltimoReenvio() {
        return fechaUltimoReenvio;
    }


    /**
     * Sets the fechaUltimoReenvio value for this VoucherOperacionResponseDTO.
     * 
     * @param fechaUltimoReenvio
     */
    public void setFechaUltimoReenvio(java.lang.String fechaUltimoReenvio) {
        this.fechaUltimoReenvio = fechaUltimoReenvio;
    }


    /**
     * Gets the idComprobanteAdjunto value for this VoucherOperacionResponseDTO.
     * 
     * @return idComprobanteAdjunto
     */
    public java.lang.String getIdComprobanteAdjunto() {
        return idComprobanteAdjunto;
    }


    /**
     * Sets the idComprobanteAdjunto value for this VoucherOperacionResponseDTO.
     * 
     * @param idComprobanteAdjunto
     */
    public void setIdComprobanteAdjunto(java.lang.String idComprobanteAdjunto) {
        this.idComprobanteAdjunto = idComprobanteAdjunto;
    }


    /**
     * Gets the idComprobanteOperacion value for this VoucherOperacionResponseDTO.
     * 
     * @return idComprobanteOperacion
     */
    public java.lang.String getIdComprobanteOperacion() {
        return idComprobanteOperacion;
    }


    /**
     * Sets the idComprobanteOperacion value for this VoucherOperacionResponseDTO.
     * 
     * @param idComprobanteOperacion
     */
    public void setIdComprobanteOperacion(java.lang.String idComprobanteOperacion) {
        this.idComprobanteOperacion = idComprobanteOperacion;
    }


    /**
     * Gets the idEnvioComprobante value for this VoucherOperacionResponseDTO.
     * 
     * @return idEnvioComprobante
     */
    public java.lang.String getIdEnvioComprobante() {
        return idEnvioComprobante;
    }


    /**
     * Sets the idEnvioComprobante value for this VoucherOperacionResponseDTO.
     * 
     * @param idEnvioComprobante
     */
    public void setIdEnvioComprobante(java.lang.String idEnvioComprobante) {
        this.idEnvioComprobante = idEnvioComprobante;
    }


    /**
     * Gets the idFileNet value for this VoucherOperacionResponseDTO.
     * 
     * @return idFileNet
     */
    public java.lang.String getIdFileNet() {
        return idFileNet;
    }


    /**
     * Sets the idFileNet value for this VoucherOperacionResponseDTO.
     * 
     * @param idFileNet
     */
    public void setIdFileNet(java.lang.String idFileNet) {
        this.idFileNet = idFileNet;
    }


    /**
     * Gets the idNumeroOperacion value for this VoucherOperacionResponseDTO.
     * 
     * @return idNumeroOperacion
     */
    public java.lang.String getIdNumeroOperacion() {
        return idNumeroOperacion;
    }


    /**
     * Sets the idNumeroOperacion value for this VoucherOperacionResponseDTO.
     * 
     * @param idNumeroOperacion
     */
    public void setIdNumeroOperacion(java.lang.String idNumeroOperacion) {
        this.idNumeroOperacion = idNumeroOperacion;
    }


    /**
     * Gets the propietario value for this VoucherOperacionResponseDTO.
     * 
     * @return propietario
     */
    public java.lang.String getPropietario() {
        return propietario;
    }


    /**
     * Sets the propietario value for this VoucherOperacionResponseDTO.
     * 
     * @param propietario
     */
    public void setPropietario(java.lang.String propietario) {
        this.propietario = propietario;
    }


    /**
     * Gets the tipoComprobante value for this VoucherOperacionResponseDTO.
     * 
     * @return tipoComprobante
     */
    public java.lang.String getTipoComprobante() {
        return tipoComprobante;
    }


    /**
     * Sets the tipoComprobante value for this VoucherOperacionResponseDTO.
     * 
     * @param tipoComprobante
     */
    public void setTipoComprobante(java.lang.String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }


    /**
     * Gets the tipoCopia value for this VoucherOperacionResponseDTO.
     * 
     * @return tipoCopia
     */
    public java.lang.String getTipoCopia() {
        return tipoCopia;
    }


    /**
     * Sets the tipoCopia value for this VoucherOperacionResponseDTO.
     * 
     * @param tipoCopia
     */
    public void setTipoCopia(java.lang.String tipoCopia) {
        this.tipoCopia = tipoCopia;
    }


    /**
     * Gets the tipoOperacion value for this VoucherOperacionResponseDTO.
     * 
     * @return tipoOperacion
     */
    public java.lang.String getTipoOperacion() {
        return tipoOperacion;
    }


    /**
     * Sets the tipoOperacion value for this VoucherOperacionResponseDTO.
     * 
     * @param tipoOperacion
     */
    public void setTipoOperacion(java.lang.String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VoucherOperacionResponseDTO)) return false;
        VoucherOperacionResponseDTO other = (VoucherOperacionResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codigoSesion==null && other.getCodigoSesion()==null) || 
             (this.codigoSesion!=null &&
              this.codigoSesion.equals(other.getCodigoSesion()))) &&
            ((this.codigoUsuarioEnvia==null && other.getCodigoUsuarioEnvia()==null) || 
             (this.codigoUsuarioEnvia!=null &&
              this.codigoUsuarioEnvia.equals(other.getCodigoUsuarioEnvia()))) &&
            ((this.comprobantePdf==null && other.getComprobantePdf()==null) || 
             (this.comprobantePdf!=null &&
              this.comprobantePdf.equals(other.getComprobantePdf()))) &&
            ((this.emailPara==null && other.getEmailPara()==null) || 
             (this.emailPara!=null &&
              this.emailPara.equals(other.getEmailPara()))) &&
            ((this.estadoAlmacenamiento==null && other.getEstadoAlmacenamiento()==null) || 
             (this.estadoAlmacenamiento!=null &&
              this.estadoAlmacenamiento.equals(other.getEstadoAlmacenamiento()))) &&
            ((this.estadoEnvio==null && other.getEstadoEnvio()==null) || 
             (this.estadoEnvio!=null &&
              this.estadoEnvio.equals(other.getEstadoEnvio()))) &&
            ((this.fechaEnvio==null && other.getFechaEnvio()==null) || 
             (this.fechaEnvio!=null &&
              this.fechaEnvio.equals(other.getFechaEnvio()))) &&
            ((this.fechaRegistro==null && other.getFechaRegistro()==null) || 
             (this.fechaRegistro!=null &&
              this.fechaRegistro.equals(other.getFechaRegistro()))) &&
            ((this.fechaUltimoReenvio==null && other.getFechaUltimoReenvio()==null) || 
             (this.fechaUltimoReenvio!=null &&
              this.fechaUltimoReenvio.equals(other.getFechaUltimoReenvio()))) &&
            ((this.idComprobanteAdjunto==null && other.getIdComprobanteAdjunto()==null) || 
             (this.idComprobanteAdjunto!=null &&
              this.idComprobanteAdjunto.equals(other.getIdComprobanteAdjunto()))) &&
            ((this.idComprobanteOperacion==null && other.getIdComprobanteOperacion()==null) || 
             (this.idComprobanteOperacion!=null &&
              this.idComprobanteOperacion.equals(other.getIdComprobanteOperacion()))) &&
            ((this.idEnvioComprobante==null && other.getIdEnvioComprobante()==null) || 
             (this.idEnvioComprobante!=null &&
              this.idEnvioComprobante.equals(other.getIdEnvioComprobante()))) &&
            ((this.idFileNet==null && other.getIdFileNet()==null) || 
             (this.idFileNet!=null &&
              this.idFileNet.equals(other.getIdFileNet()))) &&
            ((this.idNumeroOperacion==null && other.getIdNumeroOperacion()==null) || 
             (this.idNumeroOperacion!=null &&
              this.idNumeroOperacion.equals(other.getIdNumeroOperacion()))) &&
            ((this.propietario==null && other.getPropietario()==null) || 
             (this.propietario!=null &&
              this.propietario.equals(other.getPropietario()))) &&
            ((this.tipoComprobante==null && other.getTipoComprobante()==null) || 
             (this.tipoComprobante!=null &&
              this.tipoComprobante.equals(other.getTipoComprobante()))) &&
            ((this.tipoCopia==null && other.getTipoCopia()==null) || 
             (this.tipoCopia!=null &&
              this.tipoCopia.equals(other.getTipoCopia()))) &&
            ((this.tipoOperacion==null && other.getTipoOperacion()==null) || 
             (this.tipoOperacion!=null &&
              this.tipoOperacion.equals(other.getTipoOperacion())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCodigoSesion() != null) {
            _hashCode += getCodigoSesion().hashCode();
        }
        if (getCodigoUsuarioEnvia() != null) {
            _hashCode += getCodigoUsuarioEnvia().hashCode();
        }
        if (getComprobantePdf() != null) {
            _hashCode += getComprobantePdf().hashCode();
        }
        if (getEmailPara() != null) {
            _hashCode += getEmailPara().hashCode();
        }
        if (getEstadoAlmacenamiento() != null) {
            _hashCode += getEstadoAlmacenamiento().hashCode();
        }
        if (getEstadoEnvio() != null) {
            _hashCode += getEstadoEnvio().hashCode();
        }
        if (getFechaEnvio() != null) {
            _hashCode += getFechaEnvio().hashCode();
        }
        if (getFechaRegistro() != null) {
            _hashCode += getFechaRegistro().hashCode();
        }
        if (getFechaUltimoReenvio() != null) {
            _hashCode += getFechaUltimoReenvio().hashCode();
        }
        if (getIdComprobanteAdjunto() != null) {
            _hashCode += getIdComprobanteAdjunto().hashCode();
        }
        if (getIdComprobanteOperacion() != null) {
            _hashCode += getIdComprobanteOperacion().hashCode();
        }
        if (getIdEnvioComprobante() != null) {
            _hashCode += getIdEnvioComprobante().hashCode();
        }
        if (getIdFileNet() != null) {
            _hashCode += getIdFileNet().hashCode();
        }
        if (getIdNumeroOperacion() != null) {
            _hashCode += getIdNumeroOperacion().hashCode();
        }
        if (getPropietario() != null) {
            _hashCode += getPropietario().hashCode();
        }
        if (getTipoComprobante() != null) {
            _hashCode += getTipoComprobante().hashCode();
        }
        if (getTipoCopia() != null) {
            _hashCode += getTipoCopia().hashCode();
        }
        if (getTipoOperacion() != null) {
            _hashCode += getTipoOperacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VoucherOperacionResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "voucherOperacionResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSesion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSesion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoUsuarioEnvia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoUsuarioEnvia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comprobantePdf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comprobantePdf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailPara");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailPara"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estadoAlmacenamiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estadoAlmacenamiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estadoEnvio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estadoEnvio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaEnvio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaEnvio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaUltimoReenvio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaUltimoReenvio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idComprobanteAdjunto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idComprobanteAdjunto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idComprobanteOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idComprobanteOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEnvioComprobante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idEnvioComprobante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFileNet");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFileNet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idNumeroOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idNumeroOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propietario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "propietario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoComprobante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoComprobante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCopia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoCopia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
