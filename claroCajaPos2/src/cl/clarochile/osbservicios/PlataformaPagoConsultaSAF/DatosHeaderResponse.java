/**
 * DatosHeaderResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultaSAF;

public class DatosHeaderResponse  implements java.io.Serializable {
    private java.lang.String idServer;

    private java.util.Calendar reqSrvDate;

    private java.util.Calendar respSrvDate;

    public DatosHeaderResponse() {
    }

    public DatosHeaderResponse(
           java.lang.String idServer,
           java.util.Calendar reqSrvDate,
           java.util.Calendar respSrvDate) {
           this.idServer = idServer;
           this.reqSrvDate = reqSrvDate;
           this.respSrvDate = respSrvDate;
    }


    /**
     * Gets the idServer value for this DatosHeaderResponse.
     * 
     * @return idServer
     */
    public java.lang.String getIdServer() {
        return idServer;
    }


    /**
     * Sets the idServer value for this DatosHeaderResponse.
     * 
     * @param idServer
     */
    public void setIdServer(java.lang.String idServer) {
        this.idServer = idServer;
    }


    /**
     * Gets the reqSrvDate value for this DatosHeaderResponse.
     * 
     * @return reqSrvDate
     */
    public java.util.Calendar getReqSrvDate() {
        return reqSrvDate;
    }


    /**
     * Sets the reqSrvDate value for this DatosHeaderResponse.
     * 
     * @param reqSrvDate
     */
    public void setReqSrvDate(java.util.Calendar reqSrvDate) {
        this.reqSrvDate = reqSrvDate;
    }


    /**
     * Gets the respSrvDate value for this DatosHeaderResponse.
     * 
     * @return respSrvDate
     */
    public java.util.Calendar getRespSrvDate() {
        return respSrvDate;
    }


    /**
     * Sets the respSrvDate value for this DatosHeaderResponse.
     * 
     * @param respSrvDate
     */
    public void setRespSrvDate(java.util.Calendar respSrvDate) {
        this.respSrvDate = respSrvDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosHeaderResponse)) return false;
        DatosHeaderResponse other = (DatosHeaderResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idServer==null && other.getIdServer()==null) || 
             (this.idServer!=null &&
              this.idServer.equals(other.getIdServer()))) &&
            ((this.reqSrvDate==null && other.getReqSrvDate()==null) || 
             (this.reqSrvDate!=null &&
              this.reqSrvDate.equals(other.getReqSrvDate()))) &&
            ((this.respSrvDate==null && other.getRespSrvDate()==null) || 
             (this.respSrvDate!=null &&
              this.respSrvDate.equals(other.getRespSrvDate())));
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
        if (getIdServer() != null) {
            _hashCode += getIdServer().hashCode();
        }
        if (getReqSrvDate() != null) {
            _hashCode += getReqSrvDate().hashCode();
        }
        if (getRespSrvDate() != null) {
            _hashCode += getRespSrvDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosHeaderResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/HeaderResponse", "datosHeaderResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idServer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/HeaderResponse", "IdServer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reqSrvDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/HeaderResponse", "ReqSrvDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("respSrvDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/HeaderResponse", "RespSrvDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
