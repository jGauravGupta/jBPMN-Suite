/**
 * Copyright [2014] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.jbpmn.spec;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * <p>
 * Java class for tResourceParameterBinding complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tResourceParameterBinding">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tBaseElement">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}expression"/>
 *       </sequence>
 *       <attribute name="parameterRef" use="required" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tResourceParameterBinding", propOrder = {
    "expression"
})
public class TResourceParameterBinding
        extends TBaseElement {

//    @XmlElementRef(name = "expression", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
//    protected JAXBElement<? extends TExpression> expression;
    @XmlElement(name = "expression", type = TFormalExpression.class)
    protected TExpression expression;

    @XmlAttribute(required = true)
    protected String parameterRef;// protected QName parameterRef;

    /**
     * Gets the value of the expression property.
     *
     * @return possible object is null     {@link JAXBElement }{@code <}{@link TExpression }{@code >}
     *     {@link JAXBElement }{@code <}{@link TFormalExpression }{@code >}
     *
     */
//    public JAXBElement<? extends TExpression> getExpression() {
//        return expression;
//    }
    public TFormalExpression getExpression() {
        if (!(expression instanceof TFormalExpression) && expression != null) {
            expression = expression.getFormalExpression();
        }
        return (TFormalExpression) expression;
    }

    /**
     * Sets the value of the expression property.
     *
     * @param value allowed object is null     {@link JAXBElement }{@code <}{@link TExpression }{@code >}
     *     {@link JAXBElement }{@code <}{@link TFormalExpression }{@code >}
     *
     */
//    public void setExpression(JAXBElement<? extends TExpression> value) {
//        this.expression = ((JAXBElement<? extends TExpression> ) value);
//    }
    public void setExpression(TExpression value) {
        this.expression = value;
    }

    /**
     * Gets the value of the parameterRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public String getParameterRef() {
        return parameterRef;
    }

    /**
     * Sets the value of the parameterRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setParameterRef(String value) {
        this.parameterRef = value;
    }

}
