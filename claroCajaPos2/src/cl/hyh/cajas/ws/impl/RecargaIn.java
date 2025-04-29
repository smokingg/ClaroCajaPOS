/**
 * RecargaIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class RecargaIn  extends cl.hyh.cajas.ws.impl.Request  implements java.io.Serializable {
    private long abonado;

    private int canal;

    private int codigoProducto;

    private java.lang.String fechaVenta;

    private java.lang.String horaVenta;

    private int idDistribuidor;

    private int idSubDistribuidor;

    private long idTerminal;

    private int monto;

    private int secuenciaTransaccion;

    public RecargaIn() {
    }

    public RecargaIn(
           cl.hyh.cajas.ws.impl.HeaderIn headerIn,
           long abonado,
           int canal,
           int codigoProducto,
           java.lang.String fechaVenta,
           java.lang.String horaVenta,
           int idDistribuidor,
           int idSubDistribuidor,
           long idTerminal,
           int monto,
           int secuenciaTransaccion) {
        super(
            headerIn);
        this.abonado = abonado;
        this.canal = canal;
        this.codigoProducto = codigoProducto;
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.idDistribuidor = idDistribuidor;
        this.idSubDistribuidor = idSubDistribuidor;
        this.idTerminal = idTerminal;
        this.monto = monto;
        this.secuenciaTransaccion = secuenciaTransaccion;
    }


    /**
     * Gets the abonado value for this RecargaIn.
     * 
     * @return abonado
     */
    public long getAbonado() {
        return abonado;
    }


    /**
     * Sets the abonado value for this RecargaIn.
     * 
     * @param abonado
     */
    public void setAbonado(long abonado) {
        this.abonado = abonado;
    }


    /**
     * Gets the canal value for this RecargaIn.
     * 
     * @return canal
     */
    public int getCanal() {
        return canal;
    }


    /**
     * Sets the canal value for this RecargaIn.
     * 
     * @param canal
     */
    public void setCanal(int canal) {
        this.canal = canal;
    }


    /**
     * Gets the codigoProducto value for this RecargaIn.
     * 
     * @return codigoProducto
     */
    public int getCodigoProducto() {
        return codigoProducto;
    }


    /**
     * Sets the codigoProducto value for this RecargaIn.
     * 
     * @param codigoProducto
     */
    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }


    /**
     * Gets the fechaVenta value for this RecargaIn.
     * 
     * @return fechaVenta
     */
    public java.lang.String getFechaVenta() {
        return fechaVenta;
    }


    /**
     * Sets the fechaVenta value for this RecargaIn.
     * 
     * @param fechaVenta
     */
    public void setFechaVenta(java.lang.String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }


    /**
     * Gets the horaVenta value for this RecargaIn.
     * 
     * @return horaVenta
     */
    public java.lang.String getHoraVenta() {
        return horaVenta;
    }


    /**
     * Sets the horaVenta value for this RecargaIn.
     * 
     * @param horaVenta
     */
    public void setHoraVenta(java.lang.String horaVenta) {
        this.horaVenta = horaVenta;
    }


    /**
     * Gets the idDistribuidor value for this RecargaIn.
     * 
     * @return idDistribuidor
     */
    public int getIdDistribuidor() {
        return idDistribuidor;
    }


    /**
     * Sets the idDistribuidor value for this RecargaIn.
     * 
     * @param idDistribuidor
     */
    public void setIdDistribuidor(int idDistribuidor) {
        this.idDistribuidor = idDistribuidor;
    }


    /**
     * Gets the idSubDistribuidor value for this RecargaIn.
     * 
     * @return idSubDistribuidor
     */
    public int getIdSubDistribuidor() {
        return idSubDistribuidor;
    }


    /**
     * Sets the idSubDistribuidor value for this RecargaIn.
     * 
     * @param idSubDistribuidor
     */
    public void setIdSubDistribuidor(int idSubDistribuidor) {
        this.idSubDistribuidor = idSubDistribuidor;
    }


    /**
     * Gets the idTerminal value for this RecargaIn.
     * 
     * @return idTerminal
     */
    public long getIdTerminal() {
        return idTerminal;
    }


    /**
     * Sets the idTerminal value for this RecargaIn.
     * 
     * @param idTerminal
     */
    public void setIdTerminal(long idTerminal) {
        this.idTerminal = idTerminal;
    }


    /**
     * Gets the monto value for this RecargaIn.
     * 
     * @return monto
     */
    public int getMonto() {
        return monto;
    }


    /**
     * Sets the monto value for this RecargaIn.
     * 
     * @param monto
     */
    public void setMonto(int monto) {
        this.monto = monto;
    }


    /**
     * Gets the secuenciaTransaccion value for this RecargaIn.
     * 
     * @return secuenciaTransaccion
     */
    public int getSecuenciaTransaccion() {
        return secuenciaTransaccion;
    }


    /**
     * Sets the secuenciaTransaccion value for this RecargaIn.
     * 
     * @param secuenciaTransaccion
     */
    public void setSecuenciaTransaccion(int secuenciaTransaccion) {
        this.secuenciaTransaccion = secuenciaTransaccion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RecargaIn)) return false;
        RecargaIn other = (RecargaIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.abonado == other.getAbonado() &&
            this.canal == other.getCanal() &&
            this.codigoProducto == other.getCodigoProducto() &&
            ((this.fechaVenta==null && other.getFechaVenta()==null) || 
             (this.fechaVenta!=null &&
              this.fechaVenta.equals(other.getFechaVenta()))) &&
            ((this.horaVenta==null && other.getHoraVenta()==null) || 
             (this.horaVenta!=null &&
              this.horaVenta.equals(other.getHoraVenta()))) &&
            this.idDistribuidor == other.getIdDistribuidor() &&
            this.idSubDistribuidor == other.getIdSubDistribuidor() &&
            this.idTerminal == other.getIdTerminal() &&
            this.monto == other.getMonto() &&
            this.secuenciaTransaccion == other.getSecuenciaTransaccion();
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
        _hashCode += new Long(getAbonado()).hashCode();
        _hashCode += getCanal();
        _hashCode += getCodigoProducto();
        if (getFechaVenta() != null) {
            _hashCode += getFechaVenta().hashCode();
        }
        if (getHoraVenta() != null) {
            _hashCode += getHoraVenta().hashCode();
        }
        _hashCode += getIdDistribuidor();
        _hashCode += getIdSubDistribuidor();
        _hashCode += new Long(getIdTerminal()).hashCode();
        _hashCode += getMonto();
        _hashCode += getSecuenciaTransaccion();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RecargaIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "recargaIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("abonado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "abonado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "canal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoProducto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoProducto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaVenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaVenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horaVenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "horaVenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDistribuidor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDistribuidor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSubDistribuidor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSubDistribuidor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTerminal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTerminal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secuenciaTransaccion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "secuenciaTransaccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
