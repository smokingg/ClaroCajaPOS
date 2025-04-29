/**
 * OperacionTbkDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class OperacionTbkDTO  implements java.io.Serializable {
    private int codagencia;

    private int codcajafisica;

    private java.lang.String dv;

    private java.lang.String fechapago;

    private java.lang.String idtrxtbk;

    private long monto;

    private int rut;

    private java.lang.String tipoTrx;

    public OperacionTbkDTO() {
    }

    public OperacionTbkDTO(
           int codagencia,
           int codcajafisica,
           java.lang.String dv,
           java.lang.String fechapago,
           java.lang.String idtrxtbk,
           long monto,
           int rut,
           java.lang.String tipoTrx) {
           this.codagencia = codagencia;
           this.codcajafisica = codcajafisica;
           this.dv = dv;
           this.fechapago = fechapago;
           this.idtrxtbk = idtrxtbk;
           this.monto = monto;
           this.rut = rut;
           this.tipoTrx = tipoTrx;
    }


    /**
     * Gets the codagencia value for this OperacionTbkDTO.
     * 
     * @return codagencia
     */
    public int getCodagencia() {
        return codagencia;
    }


    /**
     * Sets the codagencia value for this OperacionTbkDTO.
     * 
     * @param codagencia
     */
    public void setCodagencia(int codagencia) {
        this.codagencia = codagencia;
    }


    /**
     * Gets the codcajafisica value for this OperacionTbkDTO.
     * 
     * @return codcajafisica
     */
    public int getCodcajafisica() {
        return codcajafisica;
    }


    /**
     * Sets the codcajafisica value for this OperacionTbkDTO.
     * 
     * @param codcajafisica
     */
    public void setCodcajafisica(int codcajafisica) {
        this.codcajafisica = codcajafisica;
    }


    /**
     * Gets the dv value for this OperacionTbkDTO.
     * 
     * @return dv
     */
    public java.lang.String getDv() {
        return dv;
    }


    /**
     * Sets the dv value for this OperacionTbkDTO.
     * 
     * @param dv
     */
    public void setDv(java.lang.String dv) {
        this.dv = dv;
    }


    /**
     * Gets the fechapago value for this OperacionTbkDTO.
     * 
     * @return fechapago
     */
    public java.lang.String getFechapago() {
        return fechapago;
    }


    /**
     * Sets the fechapago value for this OperacionTbkDTO.
     * 
     * @param fechapago
     */
    public void setFechapago(java.lang.String fechapago) {
        this.fechapago = fechapago;
    }


    /**
     * Gets the idtrxtbk value for this OperacionTbkDTO.
     * 
     * @return idtrxtbk
     */
    public java.lang.String getIdtrxtbk() {
        return idtrxtbk;
    }


    /**
     * Sets the idtrxtbk value for this OperacionTbkDTO.
     * 
     * @param idtrxtbk
     */
    public void setIdtrxtbk(java.lang.String idtrxtbk) {
        this.idtrxtbk = idtrxtbk;
    }


    /**
     * Gets the monto value for this OperacionTbkDTO.
     * 
     * @return monto
     */
    public long getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this OperacionTbkDTO.
     * 
     * @param monto
     */
    public void setMonto(long monto) {
        this.monto = monto;
    }


    /**
     * Gets the rut value for this OperacionTbkDTO.
     * 
     * @return rut
     */
    public int getRut() {
        return rut;
    }


    /**
     * Sets the rut value for this OperacionTbkDTO.
     * 
     * @param rut
     */
    public void setRut(int rut) {
        this.rut = rut;
    }


    /**
     * Gets the tipoTrx value for this OperacionTbkDTO.
     * 
     * @return tipoTrx
     */
    public java.lang.String getTipoTrx() {
        return tipoTrx;
    }


    /**
     * Sets the tipoTrx value for this OperacionTbkDTO.
     * 
     * @param tipoTrx
     */
    public void setTipoTrx(java.lang.String tipoTrx) {
        this.tipoTrx = tipoTrx;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionTbkDTO)) return false;
        OperacionTbkDTO other = (OperacionTbkDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codagencia == other.getCodagencia() &&
            this.codcajafisica == other.getCodcajafisica() &&
            ((this.dv==null && other.getDv()==null) || 
             (this.dv!=null &&
              this.dv.equals(other.getDv()))) &&
            ((this.fechapago==null && other.getFechapago()==null) || 
             (this.fechapago!=null &&
              this.fechapago.equals(other.getFechapago()))) &&
            ((this.idtrxtbk==null && other.getIdtrxtbk()==null) || 
             (this.idtrxtbk!=null &&
              this.idtrxtbk.equals(other.getIdtrxtbk()))) &&
            this.monto == other.getMonto() &&
            this.rut == other.getRut() &&
            ((this.tipoTrx==null && other.getTipoTrx()==null) || 
             (this.tipoTrx!=null &&
              this.tipoTrx.equals(other.getTipoTrx())));
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
        _hashCode += getCodagencia();
        _hashCode += getCodcajafisica();
        if (getDv() != null) {
            _hashCode += getDv().hashCode();
        }
        if (getFechapago() != null) {
            _hashCode += getFechapago().hashCode();
        }
        if (getIdtrxtbk() != null) {
            _hashCode += getIdtrxtbk().hashCode();
        }
        _hashCode += new Long(getMonto()).hashCode();
        _hashCode += getRut();
        if (getTipoTrx() != null) {
            _hashCode += getTipoTrx().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperacionTbkDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "operacionTbkDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codagencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codagencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codcajafisica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codcajafisica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("fechapago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechapago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idtrxtbk");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idtrxtbk"));
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
        elemField.setFieldName("rut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTrx");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTrx"));
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
