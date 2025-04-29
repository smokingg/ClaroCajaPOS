/**
 * HeaderRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoNotificarSAF;

public class HeaderRequest  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String company;

    private java.lang.String appName;

    private java.lang.String idClient;

    private java.util.Calendar reqDate;

    public HeaderRequest() {
    }

    public HeaderRequest(
           java.lang.String username,
           java.lang.String company,
           java.lang.String appName,
           java.lang.String idClient,
           java.util.Calendar reqDate) {
           this.username = username;
           this.company = company;
           this.appName = appName;
           this.idClient = idClient;
           this.reqDate = reqDate;
    }


    /**
     * Gets the username value for this HeaderRequest.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this HeaderRequest.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the company value for this HeaderRequest.
     * 
     * @return company
     */
    public java.lang.String getCompany() {
        return company;
    }


    /**
     * Sets the company value for this HeaderRequest.
     * 
     * @param company
     */
    public void setCompany(java.lang.String company) {
        this.company = company;
    }


    /**
     * Gets the appName value for this HeaderRequest.
     * 
     * @return appName
     */
    public java.lang.String getAppName() {
        return appName;
    }


    /**
     * Sets the appName value for this HeaderRequest.
     * 
     * @param appName
     */
    public void setAppName(java.lang.String appName) {
        this.appName = appName;
    }


    /**
     * Gets the idClient value for this HeaderRequest.
     * 
     * @return idClient
     */
    public java.lang.String getIdClient() {
        return idClient;
    }


    /**
     * Sets the idClient value for this HeaderRequest.
     * 
     * @param idClient
     */
    public void setIdClient(java.lang.String idClient) {
        this.idClient = idClient;
    }


    /**
     * Gets the reqDate value for this HeaderRequest.
     * 
     * @return reqDate
     */
    public java.util.Calendar getReqDate() {
        return reqDate;
    }


    /**
     * Sets the reqDate value for this HeaderRequest.
     * 
     * @param reqDate
     */
    public void setReqDate(java.util.Calendar reqDate) {
        this.reqDate = reqDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HeaderRequest)) return false;
        HeaderRequest other = (HeaderRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.company==null && other.getCompany()==null) || 
             (this.company!=null &&
              this.company.equals(other.getCompany()))) &&
            ((this.appName==null && other.getAppName()==null) || 
             (this.appName!=null &&
              this.appName.equals(other.getAppName()))) &&
            ((this.idClient==null && other.getIdClient()==null) || 
             (this.idClient!=null &&
              this.idClient.equals(other.getIdClient()))) &&
            ((this.reqDate==null && other.getReqDate()==null) || 
             (this.reqDate!=null &&
              this.reqDate.equals(other.getReqDate())));
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
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getCompany() != null) {
            _hashCode += getCompany().hashCode();
        }
        if (getAppName() != null) {
            _hashCode += getAppName().hashCode();
        }
        if (getIdClient() != null) {
            _hashCode += getIdClient().hashCode();
        }
        if (getReqDate() != null) {
            _hashCode += getReqDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HeaderRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/HeaderRequest", "HeaderRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/HeaderRequest", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("company");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/HeaderRequest", "Company"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/HeaderRequest", "AppName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idClient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/HeaderRequest", "IdClient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reqDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://osbcorp.vtr.cl/GLOBAL/EMP/HeaderRequest", "ReqDate"));
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
