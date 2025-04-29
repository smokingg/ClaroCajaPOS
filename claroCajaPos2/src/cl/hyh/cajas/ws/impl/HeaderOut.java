/**
 * HeaderOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class HeaderOut  implements java.io.Serializable {
    private int rc;

    private java.lang.String rcClass;

    private java.lang.String rcMessage;

    private java.lang.String sessionId;

    public HeaderOut() {
    }

    public HeaderOut(
           int rc,
           java.lang.String rcClass,
           java.lang.String rcMessage,
           java.lang.String sessionId) {
           this.rc = rc;
           this.rcClass = rcClass;
           this.rcMessage = rcMessage;
           this.sessionId = sessionId;
    }


    /**
     * Gets the rc value for this HeaderOut.
     * 
     * @return rc
     */
    public int getRc() {
        return rc;
    }


    /**
     * Sets the rc value for this HeaderOut.
     * 
     * @param rc
     */
    public void setRc(int rc) {
        this.rc = rc;
    }


    /**
     * Gets the rcClass value for this HeaderOut.
     * 
     * @return rcClass
     */
    public java.lang.String getRcClass() {
        return rcClass;
    }


    /**
     * Sets the rcClass value for this HeaderOut.
     * 
     * @param rcClass
     */
    public void setRcClass(java.lang.String rcClass) {
        this.rcClass = rcClass;
    }


    /**
     * Gets the rcMessage value for this HeaderOut.
     * 
     * @return rcMessage
     */
    public java.lang.String getRcMessage() {
        return rcMessage;
    }


    /**
     * Sets the rcMessage value for this HeaderOut.
     * 
     * @param rcMessage
     */
    public void setRcMessage(java.lang.String rcMessage) {
        this.rcMessage = rcMessage;
    }


    /**
     * Gets the sessionId value for this HeaderOut.
     * 
     * @return sessionId
     */
    public java.lang.String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this HeaderOut.
     * 
     * @param sessionId
     */
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HeaderOut)) return false;
        HeaderOut other = (HeaderOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.rc == other.getRc() &&
            ((this.rcClass==null && other.getRcClass()==null) || 
             (this.rcClass!=null &&
              this.rcClass.equals(other.getRcClass()))) &&
            ((this.rcMessage==null && other.getRcMessage()==null) || 
             (this.rcMessage!=null &&
              this.rcMessage.equals(other.getRcMessage()))) &&
            ((this.sessionId==null && other.getSessionId()==null) || 
             (this.sessionId!=null &&
              this.sessionId.equals(other.getSessionId())));
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
        _hashCode += getRc();
        if (getRcClass() != null) {
            _hashCode += getRcClass().hashCode();
        }
        if (getRcMessage() != null) {
            _hashCode += getRcMessage().hashCode();
        }
        if (getSessionId() != null) {
            _hashCode += getSessionId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HeaderOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "headerOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rcClass");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rcClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rcMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rcMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sessionId"));
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
