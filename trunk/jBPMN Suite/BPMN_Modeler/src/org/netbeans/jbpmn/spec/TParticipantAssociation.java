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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for tParticipantAssociation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tParticipantAssociation">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tBaseElement">
 *       <sequence>
 *         <element name="innerParticipantRef" type="{http://www.w3.org/2001/XMLSchema}QName"/>
 *         <element name="outerParticipantRef" type="{http://www.w3.org/2001/XMLSchema}QName"/>
 *       </sequence>
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tParticipantAssociation", propOrder = {
    "innerParticipantRef",
    "outerParticipantRef"
})
public class TParticipantAssociation
    extends TBaseElement
{

    @XmlElement(required = true)
    protected QName innerParticipantRef;
    @XmlElement(required = true)
    protected QName outerParticipantRef;

    /**
     * Gets the value of the innerParticipantRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getInnerParticipantRef() {
        return innerParticipantRef;
    }

    /**
     * Sets the value of the innerParticipantRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setInnerParticipantRef(QName value) {
        this.innerParticipantRef = value;
    }

    /**
     * Gets the value of the outerParticipantRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getOuterParticipantRef() {
        return outerParticipantRef;
    }

    /**
     * Sets the value of the outerParticipantRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setOuterParticipantRef(QName value) {
        this.outerParticipantRef = value;
    }

}
