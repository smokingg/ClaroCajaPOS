/**
 * OperacionIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.hyh.cajas.ws.impl;

public class OperacionIn  extends cl.hyh.cajas.ws.impl.Request  implements java.io.Serializable {
    private cl.hyh.cajas.ws.impl.MedioPagoCaja[] mediosPago;

    private cl.hyh.cajas.ws.impl.OperacionCaja operacion;

    private cl.hyh.cajas.ws.impl.TransaccionCaja[] transacciones;

    public OperacionIn() {
    }

    public OperacionIn(
           cl.hyh.cajas.ws.impl.HeaderIn headerIn,
           cl.hyh.cajas.ws.impl.MedioPagoCaja[] mediosPago,
           cl.hyh.cajas.ws.impl.OperacionCaja operacion,
           cl.hyh.cajas.ws.impl.TransaccionCaja[] transacciones) {
        super(
            headerIn);
        this.mediosPago = mediosPago;
        this.operacion = operacion;
        this.transacciones = transacciones;
    }


    /**
     * Gets the mediosPago value for this OperacionIn.
     * 
     * @return mediosPago
     */
    public cl.hyh.cajas.ws.impl.MedioPagoCaja[] getMediosPago() {
        return mediosPago;
    }


    /**
     * Sets the mediosPago value for this OperacionIn.
     * 
     * @param mediosPago
     */
    public void setMediosPago(cl.hyh.cajas.ws.impl.MedioPagoCaja[] mediosPago) {
        this.mediosPago = mediosPago;
    }

    public cl.hyh.cajas.ws.impl.MedioPagoCaja getMediosPago(int i) {
        return this.mediosPago[i];
    }

    public void setMediosPago(int i, cl.hyh.cajas.ws.impl.MedioPagoCaja _value) {
        this.mediosPago[i] = _value;
    }


    /**
     * Gets the operacion value for this OperacionIn.
     * 
     * @return operacion
     */
    public cl.hyh.cajas.ws.impl.OperacionCaja getOperacion() {
        return operacion;
    }


    /**
     * Sets the operacion value for this OperacionIn.
     * 
     * @param operacion
     */
    public void setOperacion(cl.hyh.cajas.ws.impl.OperacionCaja operacion) {
        this.operacion = operacion;
    }


    /**
     * Gets the transacciones value for this OperacionIn.
     * 
     * @return transacciones
     */
    public cl.hyh.cajas.ws.impl.TransaccionCaja[] getTransacciones() {
        return transacciones;
    }


    /**
     * Sets the transacciones value for this OperacionIn.
     * 
     * @param transacciones
     */
    public void setTransacciones(cl.hyh.cajas.ws.impl.TransaccionCaja[] transacciones) {
        this.transacciones = transacciones;
    }

    public cl.hyh.cajas.ws.impl.TransaccionCaja getTransacciones(int i) {
        return this.transacciones[i];
    }

    public void setTransacciones(int i, cl.hyh.cajas.ws.impl.TransaccionCaja _value) {
        this.transacciones[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperacionIn)) return false;
        OperacionIn other = (OperacionIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.mediosPago==null && other.getMediosPago()==null) || 
             (this.mediosPago!=null &&
              java.util.Arrays.equals(this.mediosPago, other.getMediosPago()))) &&
            ((this.operacion==null && other.getOperacion()==null) || 
             (this.operacion!=null &&
              this.operacion.equals(other.getOperacion()))) &&
            ((this.transacciones==null && other.getTransacciones()==null) || 
             (this.transacciones!=null &&
              java.util.Arrays.equals(this.transacciones, other.getTransacciones())));
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
        if (getMediosPago() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMediosPago());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMediosPago(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOperacion() != null) {
            _hashCode += getOperacion().hashCode();
        }
        if (getTransacciones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTransacciones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTransacciones(), i);
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
        new org.apache.axis.description.TypeDesc(OperacionIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "operacionIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mediosPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mediosPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "medioPagoCaja"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "operacionCaja"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transacciones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transacciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://impl.ws.cajas.hyh.cl/", "transaccionCaja"));
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
