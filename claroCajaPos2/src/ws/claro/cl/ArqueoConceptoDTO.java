/**
 * ArqueoConceptoDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ArqueoConceptoDTO  implements java.io.Serializable {
    private java.lang.String concepto;

    private long totalCajero;

    private long totalSistema;

    public ArqueoConceptoDTO() {
    }

    public ArqueoConceptoDTO(
           java.lang.String concepto,
           long totalCajero,
           long totalSistema) {
           this.concepto = concepto;
           this.totalCajero = totalCajero;
           this.totalSistema = totalSistema;
    }


    /**
     * Gets the concepto value for this ArqueoConceptoDTO.
     * 
     * @return concepto
     */
    public java.lang.String getConcepto() {
        return concepto;
    }


    /**
     * Sets the concepto value for this ArqueoConceptoDTO.
     * 
     * @param concepto
     */
    public void setConcepto(java.lang.String concepto) {
        this.concepto = concepto;
    }


    /**
     * Gets the totalCajero value for this ArqueoConceptoDTO.
     * 
     * @return totalCajero
     */
    public long getTotalCajero() {
        return totalCajero;
    }


    /**
     * Sets the totalCajero value for this ArqueoConceptoDTO.
     * 
     * @param totalCajero
     */
    public void setTotalCajero(long totalCajero) {
        this.totalCajero = totalCajero;
    }


    /**
     * Gets the totalSistema value for this ArqueoConceptoDTO.
     * 
     * @return totalSistema
     */
    public long getTotalSistema() {
        return totalSistema;
    }


    /**
     * Sets the totalSistema value for this ArqueoConceptoDTO.
     * 
     * @param totalSistema
     */
    public void setTotalSistema(long totalSistema) {
        this.totalSistema = totalSistema;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArqueoConceptoDTO)) return false;
        ArqueoConceptoDTO other = (ArqueoConceptoDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.concepto==null && other.getConcepto()==null) || 
             (this.concepto!=null &&
              this.concepto.equals(other.getConcepto()))) &&
            this.totalCajero == other.getTotalCajero() &&
            this.totalSistema == other.getTotalSistema();
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
        if (getConcepto() != null) {
            _hashCode += getConcepto().hashCode();
        }
        _hashCode += new Long(getTotalCajero()).hashCode();
        _hashCode += new Long(getTotalSistema()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArqueoConceptoDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "arqueoConceptoDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concepto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concepto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCajero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalCajero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalSistema");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalSistema"));
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
