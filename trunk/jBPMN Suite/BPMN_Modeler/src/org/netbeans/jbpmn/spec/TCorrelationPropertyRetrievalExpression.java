/** Copyright [2014] Gaurav Gupta
   *
   *Licensed under the Apache License, Version 2.0 (the "License");
   *you may not use this file except in compliance with the License.
   *You may obtain a copy of the License at
   *
   *    http://www.apache.org/licenses/LICENSE-2.0
   *
   *Unless required by applicable law or agreed to in writing, software
   *distributed under the License is distributed on an "AS IS" BASIS,
   *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   *See the License for the specific language governing permissions and
   *limitations under the License.
   */
 package org.netbeans.jbpmn.spec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for tCorrelationPropertyRetrievalExpression complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tCorrelationPropertyRetrievalExpression">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tBaseElement">
 *       <sequence>
 *         <element name="messagePath" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tFormalExpression"/>
 *       </sequence>
 *       <attribute name="messageRef" use="required" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCorrelationPropertyRetrievalExpression", propOrder = {
    "messagePath"
})
public class TCorrelationPropertyRetrievalExpression
    extends TBaseElement
{

    @XmlElement(required = true)
    protected TFormalExpression messagePath;
    @XmlAttribute(required = true)
    protected QName messageRef;

    /**
     * Gets the value of the messagePath property.
     * 
     * @return
     *     possible object is
     *     {@link TFormalExpression }
     *     
     */
    public TFormalExpression getMessagePath() {
        return messagePath;
    }

    /**
     * Sets the value of the messagePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link TFormalExpression }
     *     
     */
    public void setMessagePath(TFormalExpression value) {
        this.messagePath = value;
    }

    /**
     * Gets the value of the messageRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getMessageRef() {
        return messageRef;
    }

    /**
     * Sets the value of the messageRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setMessageRef(QName value) {
        this.messageRef = value;
    }

}
