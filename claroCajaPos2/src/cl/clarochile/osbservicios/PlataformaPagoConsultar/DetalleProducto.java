/**
 * DetalleProducto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultar;

public class DetalleProducto  implements java.io.Serializable {
    private java.lang.String codigoItem;

    private java.lang.String descripcionItem;

    private java.lang.String tipoItem;

    private long montoItem;

    private java.lang.String indicadorImpugnacion;

    private java.lang.String codigoSubItem;

    private java.lang.String descripcionSubItem;

    private long montoSubItem;

    public DetalleProducto() {
    }

    public DetalleProducto(
           java.lang.String codigoItem,
           java.lang.String descripcionItem,
           java.lang.String tipoItem,
           long montoItem,
           java.lang.String indicadorImpugnacion,
           java.lang.String codigoSubItem,
           java.lang.String descripcionSubItem,
           long montoSubItem) {
           this.codigoItem = codigoItem;
           this.descripcionItem = descripcionItem;
           this.tipoItem = tipoItem;
           this.montoItem = montoItem;
           this.indicadorImpugnacion = indicadorImpugnacion;
           this.codigoSubItem = codigoSubItem;
           this.descripcionSubItem = descripcionSubItem;
           this.montoSubItem = montoSubItem;
    }


    /**
     * Gets the codigoItem value for this DetalleProducto.
     * 
     * @return codigoItem
     */
    public java.lang.String getCodigoItem() {
        return codigoItem;
    }


    /**
     * Sets the codigoItem value for this DetalleProducto.
     * 
     * @param codigoItem
     */
    public void setCodigoItem(java.lang.String codigoItem) {
        this.codigoItem = codigoItem;
    }


    /**
     * Gets the descripcionItem value for this DetalleProducto.
     * 
     * @return descripcionItem
     */
    public java.lang.String getDescripcionItem() {
        return descripcionItem;
    }


    /**
     * Sets the descripcionItem value for this DetalleProducto.
     * 
     * @param descripcionItem
     */
    public void setDescripcionItem(java.lang.String descripcionItem) {
        this.descripcionItem = descripcionItem;
    }


    /**
     * Gets the tipoItem value for this DetalleProducto.
     * 
     * @return tipoItem
     */
    public java.lang.String getTipoItem() {
        return tipoItem;
    }


    /**
     * Sets the tipoItem value for this DetalleProducto.
     * 
     * @param tipoItem
     */
    public void setTipoItem(java.lang.String tipoItem) {
        this.tipoItem = tipoItem;
    }


    /**
     * Gets the montoItem value for this DetalleProducto.
     * 
     * @return montoItem
     */
    public long getMontoItem() {
        return montoItem;
    }


    /**
     * Sets the montoItem value for this DetalleProducto.
     * 
     * @param montoItem
     */
    public void setMontoItem(long montoItem) {
        this.montoItem = montoItem;
    }


    /**
     * Gets the indicadorImpugnacion value for this DetalleProducto.
     * 
     * @return indicadorImpugnacion
     */
    public java.lang.String getIndicadorImpugnacion() {
        return indicadorImpugnacion;
    }


    /**
     * Sets the indicadorImpugnacion value for this DetalleProducto.
     * 
     * @param indicadorImpugnacion
     */
    public void setIndicadorImpugnacion(java.lang.String indicadorImpugnacion) {
        this.indicadorImpugnacion = indicadorImpugnacion;
    }


    /**
     * Gets the codigoSubItem value for this DetalleProducto.
     * 
     * @return codigoSubItem
     */
    public java.lang.String getCodigoSubItem() {
        return codigoSubItem;
    }


    /**
     * Sets the codigoSubItem value for this DetalleProducto.
     * 
     * @param codigoSubItem
     */
    public void setCodigoSubItem(java.lang.String codigoSubItem) {
        this.codigoSubItem = codigoSubItem;
    }


    /**
     * Gets the descripcionSubItem value for this DetalleProducto.
     * 
     * @return descripcionSubItem
     */
    public java.lang.String getDescripcionSubItem() {
        return descripcionSubItem;
    }


    /**
     * Sets the descripcionSubItem value for this DetalleProducto.
     * 
     * @param descripcionSubItem
     */
    public void setDescripcionSubItem(java.lang.String descripcionSubItem) {
        this.descripcionSubItem = descripcionSubItem;
    }


    /**
     * Gets the montoSubItem value for this DetalleProducto.
     * 
     * @return montoSubItem
     */
    public long getMontoSubItem() {
        return montoSubItem;
    }


    /**
     * Sets the montoSubItem value for this DetalleProducto.
     * 
     * @param montoSubItem
     */
    public void setMontoSubItem(long montoSubItem) {
        this.montoSubItem = montoSubItem;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DetalleProducto)) return false;
        DetalleProducto other = (DetalleProducto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoItem==null && other.getCodigoItem()==null) || 
             (this.codigoItem!=null &&
              this.codigoItem.equals(other.getCodigoItem()))) &&
            ((this.descripcionItem==null && other.getDescripcionItem()==null) || 
             (this.descripcionItem!=null &&
              this.descripcionItem.equals(other.getDescripcionItem()))) &&
            ((this.tipoItem==null && other.getTipoItem()==null) || 
             (this.tipoItem!=null &&
              this.tipoItem.equals(other.getTipoItem()))) &&
            this.montoItem == other.getMontoItem() &&
            ((this.indicadorImpugnacion==null && other.getIndicadorImpugnacion()==null) || 
             (this.indicadorImpugnacion!=null &&
              this.indicadorImpugnacion.equals(other.getIndicadorImpugnacion()))) &&
            ((this.codigoSubItem==null && other.getCodigoSubItem()==null) || 
             (this.codigoSubItem!=null &&
              this.codigoSubItem.equals(other.getCodigoSubItem()))) &&
            ((this.descripcionSubItem==null && other.getDescripcionSubItem()==null) || 
             (this.descripcionSubItem!=null &&
              this.descripcionSubItem.equals(other.getDescripcionSubItem()))) &&
            this.montoSubItem == other.getMontoSubItem();
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
        if (getCodigoItem() != null) {
            _hashCode += getCodigoItem().hashCode();
        }
        if (getDescripcionItem() != null) {
            _hashCode += getDescripcionItem().hashCode();
        }
        if (getTipoItem() != null) {
            _hashCode += getTipoItem().hashCode();
        }
        _hashCode += new Long(getMontoItem()).hashCode();
        if (getIndicadorImpugnacion() != null) {
            _hashCode += getIndicadorImpugnacion().hashCode();
        }
        if (getCodigoSubItem() != null) {
            _hashCode += getCodigoSubItem().hashCode();
        }
        if (getDescripcionSubItem() != null) {
            _hashCode += getDescripcionSubItem().hashCode();
        }
        _hashCode += new Long(getMontoSubItem()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DetalleProducto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultar/", "DetalleProducto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcionItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("montoItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "montoItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indicadorImpugnacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indicadorImpugnacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoSubItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoSubItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionSubItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcionSubItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("montoSubItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "montoSubItem"));
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
