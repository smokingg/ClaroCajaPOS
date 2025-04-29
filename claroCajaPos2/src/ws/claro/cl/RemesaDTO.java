/**
 * RemesaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class RemesaDTO  implements java.io.Serializable {
    private long codAgencia;

    private long codCajafisica;

    private int codOperacion;

    private long codRecaudador;

    private long codSesion;

    private java.lang.String descripcionOperacion;

    private java.lang.String estadoOperacion;

    private java.lang.String fechaPago;

    private java.lang.String fechaProc;

    private java.lang.String horaPago;

    private java.lang.String horaProc;

    private long montoOperacion;

    private long numOperacion;

    public RemesaDTO() {
    }

    public RemesaDTO(
           long codAgencia,
           long codCajafisica,
           int codOperacion,
           long codRecaudador,
           long codSesion,
           java.lang.String descripcionOperacion,
           java.lang.String estadoOperacion,
           java.lang.String fechaPago,
           java.lang.String fechaProc,
           java.lang.String horaPago,
           java.lang.String horaProc,
           long montoOperacion,
           long numOperacion) {
           this.codAgencia = codAgencia;
           this.codCajafisica = codCajafisica;
           this.codOperacion = codOperacion;
           this.codRecaudador = codRecaudador;
           this.codSesion = codSesion;
           this.descripcionOperacion = descripcionOperacion;
           this.estadoOperacion = estadoOperacion;
           this.fechaPago = fechaPago;
           this.fechaProc = fechaProc;
           this.horaPago = horaPago;
           this.horaProc = horaProc;
           this.montoOperacion = montoOperacion;
           this.numOperacion = numOperacion;
    }


    /**
     * Gets the codAgencia value for this RemesaDTO.
     * 
     * @return codAgencia
     */
    public long getCodAgencia() {
        return codAgencia;
    }


    /**
     * Sets the codAgencia value for this RemesaDTO.
     * 
     * @param codAgencia
     */
    public void setCodAgencia(long codAgencia) {
        this.codAgencia = codAgencia;
    }


    /**
     * Gets the codCajafisica value for this RemesaDTO.
     * 
     * @return codCajafisica
     */
    public long getCodCajafisica() {
        return codCajafisica;
    }


    /**
     * Sets the codCajafisica value for this RemesaDTO.
     * 
     * @param codCajafisica
     */
    public void setCodCajafisica(long codCajafisica) {
        this.codCajafisica = codCajafisica;
    }


    /**
     * Gets the codOperacion value for this RemesaDTO.
     * 
     * @return codOperacion
     */
    public int getCodOperacion() {
        return codOperacion;
    }


    /**
     * Sets the codOperacion value for this RemesaDTO.
     * 
     * @param codOperacion
     */
    public void setCodOperacion(int codOperacion) {
        this.codOperacion = codOperacion;
    }


    /**
     * Gets the codRecaudador value for this RemesaDTO.
     * 
     * @return codRecaudador
     */
    public long getCodRecaudador() {
        return codRecaudador;
    }


    /**
     * Sets the codRecaudador value for this RemesaDTO.
     * 
     * @param codRecaudador
     */
    public void setCodRecaudador(long codRecaudador) {
        this.codRecaudador = codRecaudador;
    }


    /**
     * Gets the codSesion value for this RemesaDTO.
     * 
     * @return codSesion
     */
    public long getCodSesion() {
        return codSesion;
    }


    /**
     * Sets the codSesion value for this RemesaDTO.
     * 
     * @param codSesion
     */
    public void setCodSesion(long codSesion) {
        this.codSesion = codSesion;
    }


    /**
     * Gets the descripcionOperacion value for this RemesaDTO.
     * 
     * @return descripcionOperacion
     */
    public java.lang.String getDescripcionOperacion() {
        return descripcionOperacion;
    }


    /**
     * Sets the descripcionOperacion value for this RemesaDTO.
     * 
     * @param descripcionOperacion
     */
    public void setDescripcionOperacion(java.lang.String descripcionOperacion) {
        this.descripcionOperacion = descripcionOperacion;
    }


    /**
     * Gets the estadoOperacion value for this RemesaDTO.
     * 
     * @return estadoOperacion
     */
    public java.lang.String getEstadoOperacion() {
        return estadoOperacion;
    }


    /**
     * Sets the estadoOperacion value for this RemesaDTO.
     * 
     * @param estadoOperacion
     */
    public void setEstadoOperacion(java.lang.String estadoOperacion) {
        this.estadoOperacion = estadoOperacion;
    }


    /**
     * Gets the fechaPago value for this RemesaDTO.
     * 
     * @return fechaPago
     */
    public java.lang.String getFechaPago() {
        return fechaPago;
    }


    /**
     * Sets the fechaPago value for this RemesaDTO.
     * 
     * @param fechaPago
     */
    public void setFechaPago(java.lang.String fechaPago) {
        this.fechaPago = fechaPago;
    }


    /**
     * Gets the fechaProc value for this RemesaDTO.
     * 
     * @return fechaProc
     */
    public java.lang.String getFechaProc() {
        return fechaProc;
    }


    /**
     * Sets the fechaProc value for this RemesaDTO.
     * 
     * @param fechaProc
     */
    public void setFechaProc(java.lang.String fechaProc) {
        this.fechaProc = fechaProc;
    }


    /**
     * Gets the horaPago value for this RemesaDTO.
     * 
     * @return horaPago
     */
    public java.lang.String getHoraPago() {
        return horaPago;
    }


    /**
     * Sets the horaPago value for this RemesaDTO.
     * 
     * @param horaPago
     */
    public void setHoraPago(java.lang.String horaPago) {
        this.horaPago = horaPago;
    }


    /**
     * Gets the horaProc value for this RemesaDTO.
     * 
     * @return horaProc
     */
    public java.lang.String getHoraProc() {
        return horaProc;
    }


    /**
     * Sets the horaProc value for this RemesaDTO.
     * 
     * @param horaProc
     */
    public void setHoraProc(java.lang.String horaProc) {
        this.horaProc = horaProc;
    }


    /**
     * Gets the montoOperacion value for this RemesaDTO.
     * 
     * @return montoOperacion
     */
    public long getMontoOperacion() {
        return montoOperacion;
    }


    /**
     * Sets the montoOperacion value for this RemesaDTO.
     * 
     * @param montoOperacion
     */
    public void setMontoOperacion(long montoOperacion) {
        this.montoOperacion = montoOperacion;
    }


    /**
     * Gets the numOperacion value for this RemesaDTO.
     * 
     * @return numOperacion
     */
    public long getNumOperacion() {
        return numOperacion;
    }


    /**
     * Sets the numOperacion value for this RemesaDTO.
     * 
     * @param numOperacion
     */
    public void setNumOperacion(long numOperacion) {
        this.numOperacion = numOperacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RemesaDTO)) return false;
        RemesaDTO other = (RemesaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codAgencia == other.getCodAgencia() &&
            this.codCajafisica == other.getCodCajafisica() &&
            this.codOperacion == other.getCodOperacion() &&
            this.codRecaudador == other.getCodRecaudador() &&
            this.codSesion == other.getCodSesion() &&
            ((this.descripcionOperacion==null && other.getDescripcionOperacion()==null) || 
             (this.descripcionOperacion!=null &&
              this.descripcionOperacion.equals(other.getDescripcionOperacion()))) &&
            ((this.estadoOperacion==null && other.getEstadoOperacion()==null) || 
             (this.estadoOperacion!=null &&
              this.estadoOperacion.equals(other.getEstadoOperacion()))) &&
            ((this.fechaPago==null && other.getFechaPago()==null) || 
             (this.fechaPago!=null &&
              this.fechaPago.equals(other.getFechaPago()))) &&
            ((this.fechaProc==null && other.getFechaProc()==null) || 
             (this.fechaProc!=null &&
              this.fechaProc.equals(other.getFechaProc()))) &&
            ((this.horaPago==null && other.getHoraPago()==null) || 
             (this.horaPago!=null &&
              this.horaPago.equals(other.getHoraPago()))) &&
            ((this.horaProc==null && other.getHoraProc()==null) || 
             (this.horaProc!=null &&
              this.horaProc.equals(other.getHoraProc()))) &&
            this.montoOperacion == other.getMontoOperacion() &&
            this.numOperacion == other.getNumOperacion();
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
        _hashCode += new Long(getCodAgencia()).hashCode();
        _hashCode += new Long(getCodCajafisica()).hashCode();
        _hashCode += getCodOperacion();
        _hashCode += new Long(getCodRecaudador()).hashCode();
        _hashCode += new Long(getCodSesion()).hashCode();
        if (getDescripcionOperacion() != null) {
            _hashCode += getDescripcionOperacion().hashCode();
        }
        if (getEstadoOperacion() != null) {
            _hashCode += getEstadoOperacion().hashCode();
        }
        if (getFechaPago() != null) {
            _hashCode += getFechaPago().hashCode();
        }
        if (getFechaProc() != null) {
            _hashCode += getFechaProc().hashCode();
        }
        if (getHoraPago() != null) {
            _hashCode += getHoraPago().hashCode();
        }
        if (getHoraProc() != null) {
            _hashCode += getHoraProc().hashCode();
        }
        _hashCode += new Long(getMontoOperacion()).hashCode();
        _hashCode += new Long(getNumOperacion()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RemesaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "remesaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codAgencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codAgencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codCajafisica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codCajafisica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codRecaudador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codRecaudador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codSesion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codSesion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcionOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estadoOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estadoOperacion"));
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
        elemField.setFieldName("fechaProc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaProc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horaPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "horaPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horaProc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "horaProc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("montoOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "montoOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numOperacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numOperacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
