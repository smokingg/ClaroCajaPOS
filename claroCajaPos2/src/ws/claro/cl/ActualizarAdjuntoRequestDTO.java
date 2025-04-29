/**
 * ActualizarAdjuntoRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public class ActualizarAdjuntoRequestDTO  implements java.io.Serializable {
    private java.lang.String idAdjunto;

    private java.lang.String idFileNet;

    private java.lang.String XMLFilenet;

    public ActualizarAdjuntoRequestDTO() {
    }

    public ActualizarAdjuntoRequestDTO(
           java.lang.String idAdjunto,
           java.lang.String idFileNet,
           java.lang.String XMLFilenet) {
           this.idAdjunto = idAdjunto;
           this.idFileNet = idFileNet;
           this.XMLFilenet = XMLFilenet;
    }


    /**
     * Gets the idAdjunto value for this ActualizarAdjuntoRequestDTO.
     * 
     * @return idAdjunto
     */
    public java.lang.String getIdAdjunto() {
        return idAdjunto;
    }


    /**
     * Sets the idAdjunto value for this ActualizarAdjuntoRequestDTO.
     * 
     * @param idAdjunto
     */
    public void setIdAdjunto(java.lang.String idAdjunto) {
        this.idAdjunto = idAdjunto;
    }


    /**
     * Gets the idFileNet value for this ActualizarAdjuntoRequestDTO.
     * 
     * @return idFileNet
     */
    public java.lang.String getIdFileNet() {
        return idFileNet;
    }


    /**
     * Sets the idFileNet value for this ActualizarAdjuntoRequestDTO.
     * 
     * @param idFileNet
     */
    public void setIdFileNet(java.lang.String idFileNet) {
        this.idFileNet = idFileNet;
    }


    /**
     * Gets the XMLFilenet value for this ActualizarAdjuntoRequestDTO.
     * 
     * @return XMLFilenet
     */
    public java.lang.String getXMLFilenet() {
        return XMLFilenet;
    }


    /**
     * Sets the XMLFilenet value for this ActualizarAdjuntoRequestDTO.
     * 
     * @param XMLFilenet
     */
    public void setXMLFilenet(java.lang.String XMLFilenet) {
        this.XMLFilenet = XMLFilenet;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ActualizarAdjuntoRequestDTO)) return false;
        ActualizarAdjuntoRequestDTO other = (ActualizarAdjuntoRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idAdjunto==null && other.getIdAdjunto()==null) || 
             (this.idAdjunto!=null &&
              this.idAdjunto.equals(other.getIdAdjunto()))) &&
            ((this.idFileNet==null && other.getIdFileNet()==null) || 
             (this.idFileNet!=null &&
              this.idFileNet.equals(other.getIdFileNet()))) &&
            ((this.XMLFilenet==null && other.getXMLFilenet()==null) || 
             (this.XMLFilenet!=null &&
              this.XMLFilenet.equals(other.getXMLFilenet())));
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
        if (getIdAdjunto() != null) {
            _hashCode += getIdAdjunto().hashCode();
        }
        if (getIdFileNet() != null) {
            _hashCode += getIdFileNet().hashCode();
        }
        if (getXMLFilenet() != null) {
            _hashCode += getXMLFilenet().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ActualizarAdjuntoRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://cl.claro.ws/", "actualizarAdjuntoRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAdjunto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAdjunto"));
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
        elemField.setFieldName("XMLFilenet");
        elemField.setXmlName(new javax.xml.namespace.QName("", "XMLFilenet"));
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
