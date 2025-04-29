/**
 * Request.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class Request  implements java.io.Serializable {
    private cl.hyh.cajas.ws.impl.HeaderIn headerIn;

    public Request() {
    }

    public Request(
           cl.hyh.cajas.ws.impl.HeaderIn headerIn) {
           this.headerIn = headerIn;
    }


    /**
     * Gets the headerIn value for this Request.
     * 
     * @return headerIn
     */
    public cl.hyh.cajas.ws.impl.HeaderIn getHeaderIn() {
        return headerIn;
    }


    /**
     * Sets the headerIn value for this Request.
     * 
     * @param headerIn
     */
    public void setHeaderIn(cl.hyh.cajas.ws.impl.HeaderIn headerIn) {
        this.headerIn = headerIn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Request)) return false;
        Request other = (Request) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.headerIn==null && other.getHeaderIn()==null) || 
             (this.headerIn!=null &&
              this.headerIn.equals(other.getHeaderIn())));
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
        if (getHeaderIn() != null) {
            _hashCode += getHeaderIn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Request.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "request"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("headerIn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "headerIn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "headerIn"));
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
