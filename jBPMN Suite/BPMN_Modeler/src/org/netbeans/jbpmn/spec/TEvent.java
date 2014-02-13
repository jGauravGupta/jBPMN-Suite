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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * <p>
 * Java class for tEvent complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tEvent">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tFlowNode">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}property" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "tEvent", propOrder = {
    "eventDefinition",
    "eventDefinitionRef",
    "property"
})
@XmlSeeAlso({
    TThrowEvent.class,
    TCatchEvent.class
})
public abstract class TEvent
        extends TFlowNode {

    @XmlElements({
        @XmlElement(name = "messageEventDefinition", type = TMessageEventDefinition.class),
        @XmlElement(name = "timerEventDefinition", type = TTimerEventDefinition.class),
        @XmlElement(name = "errorEventDefinition", type = TErrorEventDefinition.class),
        @XmlElement(name = "escalationEventDefinition", type = TEscalationEventDefinition.class),
        @XmlElement(name = "cancelEventDefinition", type = TCancelEventDefinition.class),
        @XmlElement(name = "compensateEventDefinition", type = TCompensateEventDefinition.class),
        @XmlElement(name = "conditionalEventDefinition", type = TConditionalEventDefinition.class),
        @XmlElement(name = "linkEventDefinition", type = TLinkEventDefinition.class),
        @XmlElement(name = "signalEventDefinition", type = TSignalEventDefinition.class),
        @XmlElement(name = "terminateEventDefinition", type = TTerminateEventDefinition.class)
    })//JBPMN  //ELEMENT_UPGRADE
    protected List<TEventDefinition> eventDefinition;//List<JAXBElement<? extends TEventDefinition>> eventDefinition;
    protected List<QName> eventDefinitionRef;

    protected List<TProperty> property;

    /**
     * Gets the value of the eventDefinition property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the eventDefinition property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEventDefinition().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list      {@link JAXBElement }{@code <}{@link TCompensateEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TErrorEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TTerminateEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TCancelEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TConditionalEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TLinkEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TMessageEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TEscalationEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TSignalEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TTimerEventDefinition }{@code >}
     *
     *
     */
    public List<TEventDefinition> getEventDefinition() {
        if (eventDefinition == null) {
            eventDefinition = new ArrayList<TEventDefinition>();
        }
        return this.eventDefinition;
    }

    public void addEventDefinition(TEventDefinition eventDefinition_In) {
        if (eventDefinition == null) {
            eventDefinition = new ArrayList<TEventDefinition>();
        }
        this.eventDefinition.add(eventDefinition_In);
    }

    public void setEventDefinition(List<TEventDefinition> eventDefinitions_In) {
        if (eventDefinition == null) {
            eventDefinition = new ArrayList<TEventDefinition>();
        }
        this.eventDefinition = eventDefinitions_In;
    }

    /**
     * Gets the value of the eventDefinitionRef property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the eventDefinitionRef property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEventDefinitionRef().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link QName }
     *
     *
     */
    public List<QName> getEventDefinitionRef() {
        if (eventDefinitionRef == null) {
            eventDefinitionRef = new ArrayList<QName>();
        }
        return this.eventDefinitionRef;
    }

    /**
     * Gets the value of the property property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the property property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TProperty }
     *
     *
     */
    public List<TProperty> getProperty() {
        if (property == null) {
            property = new ArrayList<TProperty>();
        }
        return this.property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(List<TProperty> property) {
        this.property = property;
    }

}
