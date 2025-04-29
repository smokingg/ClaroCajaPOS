/**
 * ListaRecaudadoresOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class ListaRecaudadoresOut  extends cl.hyh.cajas.ws.impl.Response  implements java.io.Serializable {
    private cl.hyh.cajas.ws.impl.Recaudador[] recaudadores;

    public ListaRecaudadoresOut() {
    }

    public ListaRecaudadoresOut(
           cl.hyh.cajas.ws.impl.HeaderOut headerOut,
           cl.hyh.cajas.ws.impl.Recaudador[] recaudadores) {
        super(
            headerOut);
        this.recaudadores = recaudadores;
    }


    /**
     * Gets the recaudadores value for this ListaRecaudadoresOut.
     * 
     * @return recaudadores
     */
    public cl.hyh.cajas.ws.impl.Recaudador[] getRecaudadores() {
        return recaudadores;
    }


    /**
     * Sets the recaudadores value for this ListaRecaudadoresOut.
     * 
     * @param recaudadores
     */
    public void setRecaudadores(cl.hyh.cajas.ws.impl.Recaudador[] recaudadores) {
        this.recaudadores = recaudadores;
    }

    public cl.hyh.cajas.ws.impl.Recaudador getRecaudadores(int i) {
        return this.recaudadores[i];
    }

    public void setRecaudadores(int i, cl.hyh.cajas.ws.impl.Recaudador _value) {
        this.recaudadores[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListaRecaudadoresOut)) return false;
        ListaRecaudadoresOut other = (ListaRecaudadoresOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.recaudadores==null && other.getRecaudadores()==null) || 
             (this.recaudadores!=null &&
              java.util.Arrays.equals(this.recaudadores, other.getRecaudadores())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getRecaudadores() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRecaudadores());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRecaudadores(), i);
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
        new org.apache.axis.description.TypeDesc(ListaRecaudadoresOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "listaRecaudadoresOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recaudadores");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recaudadores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "recaudador"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
