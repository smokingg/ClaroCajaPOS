/**
 * OperacionOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne;

public class OperacionOut  implements java.io.Serializable {
    private long totalSaldo;

    private cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.DetalleProducto[] detalleProducto;

    public OperacionOut() {
    }

    public OperacionOut(
           long totalSaldo,
           cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.DetalleProducto[] detalleProducto) {
           this.totalSaldo = totalSaldo;
           this.detalleProducto = detalleProducto;
    }


    /**
     * Gets the totalSaldo value for this OperacionOut.
     * 
     * @return totalSaldo
     */
    public long getTotalSaldo() {
        return totalSaldo;
    }


    /**
     * Sets the totalSaldo value for this OperacionOut.
     * 
     * @param totalSaldo
     */
    public void setTotalSaldo(long totalSaldo) {
        this.totalSaldo = totalSaldo;
    }


    /**
     * Gets the detalleProducto value for this OperacionOut.
     * 
     * @return detalleProducto
     */
    public cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.DetalleProducto[] getDetalleProducto() {
        return detalleProducto;
    }


    /**
     * Sets the detalleProducto value for this OperacionOut.
     * 
     * @param detalleProducto
     */
    public void setDetalleProducto(cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.DetalleProducto[] detalleProducto) {
        this.detalleProducto = detalleProducto;
    }

    public cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.DetalleProducto getDetalleProducto(int i) {
        return this.detalleProducto[i];
    }

    public void setDetalleProducto(int i, cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.DetalleProducto _value) {
        this.detalleProducto[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionOut)) return false;
        OperacionOut other = (OperacionOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.totalSaldo == other.getTotalSaldo() &&
            ((this.detalleProducto==null && other.getDetalleProducto()==null) || 
             (this.detalleProducto!=null &&
              java.util.Arrays.equals(this.detalleProducto, other.getDetalleProducto())));
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
        _hashCode += new Long(getTotalSaldo()).hashCode();
        if (getDetalleProducto() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDetalleProducto());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDetalleProducto(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperacionOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultarItemsOne/", "OperacionOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalSaldo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalSaldo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detalleProducto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "detalleProducto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://osbservicios.clarochile.cl/PlataformaPagoConsultarItemsOne/", "DetalleProducto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
