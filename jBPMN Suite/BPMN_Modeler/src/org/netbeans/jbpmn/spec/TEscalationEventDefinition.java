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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for tEscalationEventDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tEscalationEventDefinition">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tEventDefinition">
 *       <attribute name="escalationRef" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tEscalationEventDefinition")
public class TEscalationEventDefinition
    extends TEventDefinition
{

    @XmlAttribute
    protected String escalationRef;//QName escalationRef;

    /**
     * Gets the value of the escalationRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public String getEscalationRef() {
        return escalationRef;
    }

    /**
     * Sets the value of the escalationRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setEscalationRef(String value) {
        this.escalationRef = value;
    }
    
    
    
    
    
    
    
//        /**
//     * @return the errorNameEmbedded
//     */
//    public String getEscalationNameEmbedded() {
//        if (escalationRef != null && !escalationRef.isEmpty()) {
//            TDefinitions definitions = (TDefinitions) this.getDefinitionElement();
//            TEscalation escalation = (TEscalation) definitions.getRootElement(escalationRef, TEscalation.class);
//            if (escalation != null && escalation.getName() != null) {
//                return escalation.getName();
//            }
//        }
//        return "";
//    }
//
//    /**
//     * @param escalationNameEmbedded the escalationNameEmbedded to set
//     */
//    public void setEscalationNameEmbedded(String escalationNameEmbedded) {
//        TDefinitions definitions = (TDefinitions) this.getDefinitionElement();
//        TEscalation escalation = null;
//        if (escalationRef != null && !escalationRef.isEmpty()) {
//            escalation = (TEscalation) definitions.getRootElement(escalationRef, TEscalation.class);
//        }
//        if (escalation == null) {
//            escalation = new TEscalation();
//            escalationRef = escalation.getId();
//            definitions.addRootElement(escalation);
//        }
//        if (escalationNameEmbedded != null && !escalationNameEmbedded.trim().isEmpty()) {
//            escalation.setName(escalationNameEmbedded);
//        } else {
//            escalation.setName(null);
//        }
//
//    }
//
//    /**
//     * @return the escalationCodeEmbedded
//     */
//    public String getEscalationCodeEmbedded() {
//         if (escalationRef != null && !escalationRef.isEmpty()) {
//            TDefinitions definitions = (TDefinitions) this.getDefinitionElement();
//            TEscalation escalation = (TEscalation) definitions.getRootElement(escalationRef, TEscalation.class);
//            if (escalation != null && escalation.getEscalationCode()!= null) {
//                return escalation.getEscalationCode();
//            }
//        }
//        return "";
//    }
//
//    /**
//     * @param escalationCodeEmbedded the escalationCodeEmbedded to set
//     */
//    public void setEscalationCodeEmbedded(String escalationCodeEmbedded) {
//        TDefinitions definitions = (TDefinitions) this.getDefinitionElement();
//        TEscalation escalation = null;
//        if (escalationRef != null && !escalationRef.isEmpty()) {
//            escalation = (TEscalation) definitions.getRootElement(escalationRef, TEscalation.class);
//        }
//        if (escalation == null) {
//            escalation = new TEscalation();
//            escalationRef = escalation.getId();
//            definitions.addRootElement(escalation);
//        }
//        if (escalationCodeEmbedded != null && !escalationCodeEmbedded.trim().isEmpty()) {
//            escalation.setEscalationCode(escalationCodeEmbedded);
//        } else {
//            escalation.setEscalationCode(null);
//        }
//    }

}
