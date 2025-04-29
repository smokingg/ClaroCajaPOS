/**
 * BancoCtaCteDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class BancoCtaCteDTO  implements java.io.Serializable {
    private int codBanco;

    private java.lang.String nombre;

    private java.lang.String numCtaCte;

    private java.lang.String uso;

    public BancoCtaCteDTO() {
    }

    public BancoCtaCteDTO(
           int codBanco,
           java.lang.String nombre,
           java.lang.String numCtaCte,
           java.lang.String uso) {
           this.codBanco = codBanco;
           this.nombre = nombre;
           this.numCtaCte = numCtaCte;
           this.uso = uso;
    }


    /**
     * Gets the codBanco value for this BancoCtaCteDTO.
     * 
     * @return codBanco
     */
    public int getCodBanco() {
        return codBanco;
    }


    /**
     * Sets the codBanco value for this BancoCtaCteDTO.
     * 
     * @param codBanco
     */
    public void setCodBanco(int codBanco) {
        this.codBanco = codBanco;
    }


    /**
     * Gets the nombre value for this BancoCtaCteDTO.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this BancoCtaCteDTO.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the numCtaCte value for this BancoCtaCteDTO.
     * 
     * @return numCtaCte
     */
    public java.lang.String getNumCtaCte() {
        return numCtaCte;
    }


    /**
     * Sets the numCtaCte value for this BancoCtaCteDTO.
     * 
     * @param numCtaCte
     */
    public void setNumCtaCte(java.lang.String numCtaCte) {
        this.numCtaCte = numCtaCte;
    }


    /**
     * Gets the uso value for this BancoCtaCteDTO.
     * 
     * @return uso
     */
    public java.lang.String getUso() {
        return uso;
    }


    /**
     * Sets the uso value for this BancoCtaCteDTO.
     * 
     * @param uso
     */
    public void setUso(java.lang.String uso) {
        this.uso = uso;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BancoCtaCteDTO)) return false;
        BancoCtaCteDTO other = (BancoCtaCteDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codBanco == other.getCodBanco() &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.numCtaCte==null && other.getNumCtaCte()==null) || 
             (this.numCtaCte!=null &&
              this.numCtaCte.equals(other.getNumCtaCte()))) &&
            ((this.uso==null && other.getUso()==null) || 
             (this.uso!=null &&
              this.uso.equals(other.getUso())));
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
        _hashCode += getCodBanco();
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getNumCtaCte() != null) {
            _hashCode += getNumCtaCte().hashCode();
        }
        if (getUso() != null) {
            _hashCode += getUso().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BancoCtaCteDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "bancoCtaCteDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codBanco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codBanco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numCtaCte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numCtaCte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uso"));
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
