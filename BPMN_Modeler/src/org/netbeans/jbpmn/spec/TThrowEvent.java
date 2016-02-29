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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for tThrowEvent complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tThrowEvent">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tEvent">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}dataInput" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}dataInputAssociation" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}inputSet" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}eventDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="eventDefinitionRef" type="{http://www.w3.org/2001/XMLSchema}QName" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "tThrowEvent", propOrder = {
    "dataInput",
    "dataInputAssociation",
    "inputSet"
})
@XmlSeeAlso({
    TIntermediateThrowEvent.class,
    TImplicitThrowEvent.class,
    TEndEvent.class
})
public abstract class TThrowEvent
        extends TEvent {

    private List<TDataInput> dataInput;
    private List<TDataInputAssociation> dataInputAssociation;
    protected TInputSet inputSet;

    /**
     * Gets the value of the dataInput property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the dataInput property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataInput().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TDataInput }
     *
     *
     */
    public List<TDataInput> getDataInput() {
        if (dataInput == null) {
            setDataInput(new ArrayList<TDataInput>());
        }
        return this.dataInput;
    }

    /**
     * Gets the value of the dataInputAssociation property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the dataInputAssociation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataInputAssociation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TDataInputAssociation }
     *
     *
     */
    public List<TDataInputAssociation> getDataInputAssociation() {
        if (dataInputAssociation == null) {
            setDataInputAssociation(new ArrayList<TDataInputAssociation>());
        }
        return this.dataInputAssociation;
    }

    /**
     * Gets the value of the inputSet property.
     *
     * @return possible object is {@link TInputSet }
     *
     */
    public TInputSet getInputSet() {
        return inputSet;
    }

    /**
     * Sets the value of the inputSet property.
     *
     * @param value allowed object is {@link TInputSet }
     *
     */
    public void setInputSet(TInputSet value) {
        this.inputSet = value;
    }

    /**
     * @param dataInputAssociation the dataInputAssociation to set
     */
    public void setDataInputAssociation(List<TDataInputAssociation> dataInputAssociation) {
        this.dataInputAssociation = dataInputAssociation;
    }

    public void addDataInputAssociation(TDataInputAssociation dataInputAssociation) {
        if (dataInputAssociation == null) {
            setDataInputAssociation(new ArrayList<TDataInputAssociation>());
        }
        this.dataInputAssociation.add(dataInputAssociation);
    }

    /**
     * @param dataInput the dataInput to set
     */
    public void setDataInput(List<TDataInput> dataInput) {
        this.dataInput = dataInput;
    }

}
