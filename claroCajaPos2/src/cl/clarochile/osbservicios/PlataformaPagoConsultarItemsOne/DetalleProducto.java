/**
 * DetalleProducto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne;

public class DetalleProducto  implements java.io.Serializable {
    private java.lang.String idServicio;

    private java.lang.String sistemaOrigen;

    private java.lang.String tipoDocumento;

    private long saldoAdeudado;

    private java.lang.String fechaEmisionDocumento;

    private java.lang.String fechaVencimientoDocumento;

    public DetalleProducto() {
    }

    public DetalleProducto(
           java.lang.String idServicio,
           java.lang.String sistemaOrigen,
           java.lang.String tipoDocumento,
           long saldoAdeudado,
           java.lang.String fechaEmisionDocumento,
           java.lang.String fechaVencimientoDocumento) {
           this.idServicio = idServicio;
           this.sistemaOrigen = sistemaOrigen;
           this.tipoDocumento = tipoDocumento;
           this.saldoAdeudado = saldoAdeudado;
           this.fechaEmisionDocumento = fechaEmisionDocumento;
           this.fechaVencimientoDocumento = fechaVencimientoDocumento;
    }


    /**
     * Gets the idServicio value for this DetalleProducto.
     * 
     * @return idServicio
     */
    public java.lang.String getIdServicio() {
        return idServicio;
    }


    /**
     * Sets the idServicio value for this DetalleProducto.
     * 
     * @param idServicio
     */
    public void setIdServicio(java.lang.String idServicio) {
        this.idServicio = idServicio;
    }


    /**
     * Gets the sistemaOrigen value for this DetalleProducto.
     * 
     * @return sistemaOrigen
     */
    public java.lang.String getSistemaOrigen() {
        return sistemaOrigen;
    }


    /**
     * Sets the sistemaOrigen value for this DetalleProducto.
     * 
     * @param sistemaOrigen
     */
    public void setSistemaOrigen(java.lang.String sistemaOrigen) {
        this.sistemaOrigen = sistemaOrigen;
    }


    /**
     * Gets the tipoDocumento value for this DetalleProducto.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this DetalleProducto.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the saldoAdeudado value for this DetalleProducto.
     * 
     * @return saldoAdeudado
     */
    public long getSaldoAdeudado() {
        return saldoAdeudado;
    }


    /**
     * Sets the saldoAdeudado value for this DetalleProducto.
     * 
     * @param saldoAdeudado
     */
    public void setSaldoAdeudado(long saldoAdeudado) {
        this.saldoAdeudado = saldoAdeudado;
    }


    /**
     * Gets the fechaEmisionDocumento value for this DetalleProducto.
     * 
     * @return fechaEmisionDocumento
     */
    public java.lang.String getFechaEmisionDocumento() {
        return fechaEmisionDocumento;
    }


    /**
     * Sets the fechaEmisionDocumento value for this DetalleProducto.
     * 
     * @param fechaEmisionDocumento
     */
    public void setFechaEmisionDocumento(java.lang.String fechaEmisionDocumento) {
        this.fechaEmisionDocumento = fechaEmisionDocumento;
    }


    /**
     * Gets the fechaVencimientoDocumento value for this DetalleProducto.
     * 
     * @return fechaVencimientoDocumento
     */
    public java.lang.String getFechaVencimientoDocumento() {
        return fechaVencimientoDocumento;
    }


    /**
     * Sets the fechaVencimientoDocumento value for this DetalleProducto.
     * 
     * @param fechaVencimientoDocumento
     */
    public void setFechaVencimientoDocumento(java.lang.String fechaVencimientoDocumento) {
        this.fechaVencimientoDocumento = fechaVencimientoDocumento;
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
            ((this.idServicio==null && other.getIdServicio()==null) || 
             (this.idServicio!=null &&
              this.idServicio.equals(other.getIdServicio()))) &&
            ((this.sistemaOrigen==null && other.getSistemaOrigen()==null) || 
             (this.sistemaOrigen!=null &&
              this.sistemaOrigen.equals(other.getSistemaOrigen()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            this.saldoAdeudado == other.getSaldoAdeudado() &&
            ((this.fechaEmisionDocumento==null && other.getFechaEmisionDocumento()==null) || 
             (this.fechaEmisionDocumento!=null &&
              this.fechaEmisionDocumento.equals(other.getFechaEmisionDocumento()))) &&
            ((this.fechaVencimientoDocumento==null && other.getFechaVencimientoDocumento()==null) || 
             (this.fechaVencimientoDocumento!=null &&
              this.fechaVencimientoDocumento.equals(other.getFechaVencimientoDocumento())));
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
        if (getIdServicio() != null) {
            _hashCode += getIdServicio().hashCode();
        }
        if (getSistemaOrigen() != null) {
            _hashCode += getSistemaOrigen().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        _hashCode += new Long(getSaldoAdeudado()).hashCode();
        if (getFechaEmisionDocumento() != null) {
            _hashCode += getFechaEmisionDocumento().hashCode();
        }
        if (getFechaVencimientoDocumento() != null) {
            _hashCode += getFechaVencimientoDocumento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DetalleProducto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultarItemsOne/", "DetalleProducto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idServicio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idServicio"));
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
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
