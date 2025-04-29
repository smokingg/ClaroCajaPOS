/**
 * PingOutDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class PingOutDTO  implements java.io.Serializable {
    private long cheques;

    private long chequesFecha;

    private long efectivo;

    private java.lang.String retCode;

    private java.lang.String retDesc;

    public PingOutDTO() {
    }

    public PingOutDTO(
           long cheques,
           long chequesFecha,
           long efectivo,
           java.lang.String retCode,
           java.lang.String retDesc) {
           this.cheques = cheques;
           this.chequesFecha = chequesFecha;
           this.efectivo = efectivo;
           this.retCode = retCode;
           this.retDesc = retDesc;
    }


    /**
     * Gets the cheques value for this PingOutDTO.
     * 
     * @return cheques
     */
    public long getCheques() {
        return cheques;
    }


    /**
     * Sets the cheques value for this PingOutDTO.
     * 
     * @param cheques
     */
    public void setCheques(long cheques) {
        this.cheques = cheques;
    }


    /**
     * Gets the chequesFecha value for this PingOutDTO.
     * 
     * @return chequesFecha
     */
    public long getChequesFecha() {
        return chequesFecha;
    }


    /**
     * Sets the chequesFecha value for this PingOutDTO.
     * 
     * @param chequesFecha
     */
    public void setChequesFecha(long chequesFecha) {
        this.chequesFecha = chequesFecha;
    }


    /**
     * Gets the efectivo value for this PingOutDTO.
     * 
     * @return efectivo
     */
    public long getEfectivo() {
        return efectivo;
    }


    /**
     * Sets the efectivo value for this PingOutDTO.
     * 
     * @param efectivo
     */
    public void setEfectivo(long efectivo) {
        this.efectivo = efectivo;
    }


    /**
     * Gets the retCode value for this PingOutDTO.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this PingOutDTO.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDesc value for this PingOutDTO.
     * 
     * @return retDesc
     */
    public java.lang.String getRetDesc() {
        return retDesc;
    }


    /**
     * Sets the retDesc value for this PingOutDTO.
     * 
     * @param retDesc
     */
    public void setRetDesc(java.lang.String retDesc) {
        this.retDesc = retDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PingOutDTO)) return false;
        PingOutDTO other = (PingOutDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.cheques == other.getCheques() &&
            this.chequesFecha == other.getChequesFecha() &&
            this.efectivo == other.getEfectivo() &&
            ((this.retCode==null && other.getRetCode()==null) || 
             (this.retCode!=null &&
              this.retCode.equals(other.getRetCode()))) &&
            ((this.retDesc==null && other.getRetDesc()==null) || 
             (this.retDesc!=null &&
              this.retDesc.equals(other.getRetDesc())));
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
        _hashCode += new Long(getCheques()).hashCode();
        _hashCode += new Long(getChequesFecha()).hashCode();
        _hashCode += new Long(getEfectivo()).hashCode();
        if (getRetCode() != null) {
            _hashCode += getRetCode().hashCode();
        }
        if (getRetDesc() != null) {
            _hashCode += getRetDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PingOutDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "pingOutDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cheques");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cheques"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chequesFecha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chequesFecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("efectivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "efectivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retDesc"));
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
