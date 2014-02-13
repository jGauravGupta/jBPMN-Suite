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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.netbeans.jbpmn.spec.extend.TCollaborationNode;

/**
 * <p>
 * Java class for tParticipant complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tParticipant">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tBaseElement">
 *       <sequence>
 *         <element name="interfaceRef" type="{http://www.w3.org/2001/XMLSchema}QName" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="endPointRef" type="{http://www.w3.org/2001/XMLSchema}QName" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}participantMultiplicity" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       <attribute name="processRef" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tParticipant", propOrder = {
    "interfaceRef",
    "endPointRef",
    "participantMultiplicity"
})
public class TParticipant
        extends TCollaborationNode {

    protected List<QName> interfaceRef;
    protected List<QName> endPointRef;
    protected TParticipantMultiplicity participantMultiplicity;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected String processRef;//QName processRef;//JBPMN

    /**
     * Gets the value of the interfaceRef property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the interfaceRef property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInterfaceRef().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link QName }
     *
     *
     */
    public List<QName> getInterfaceRef() {
        if (interfaceRef == null) {
            interfaceRef = new ArrayList<QName>();
        }
        return this.interfaceRef;
    }

    /**
     * Gets the value of the endPointRef property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the endPointRef property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEndPointRef().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link QName }
     *
     *
     */
    public List<QName> getEndPointRef() {
        if (endPointRef == null) {
            endPointRef = new ArrayList<QName>();
        }
        return this.endPointRef;
    }

    /**
     * Gets the value of the participantMultiplicity property.
     *
     * @return possible object is {@link TParticipantMultiplicity }
     *
     */
    public TParticipantMultiplicity getParticipantMultiplicity() {
        return participantMultiplicity;
    }

    /**
     * Sets the value of the participantMultiplicity property.
     *
     * @param value allowed object is {@link TParticipantMultiplicity }
     *
     */
    public void setParticipantMultiplicity(TParticipantMultiplicity value) {
        this.participantMultiplicity = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the processRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public String getProcessRef() {
        return processRef;
    }

    /**
     * Sets the value of the processRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setProcessRef(String value) {
        this.processRef = value;
    }

    @Override
    public List<String> getIncoming() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getOutgoing() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
