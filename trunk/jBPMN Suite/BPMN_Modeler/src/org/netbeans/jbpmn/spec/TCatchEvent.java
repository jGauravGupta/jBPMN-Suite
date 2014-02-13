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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for tCatchEvent complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tCatchEvent">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tEvent">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}dataOutput" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}dataOutputAssociation" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}outputSet" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}eventDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="eventDefinitionRef" type="{http://www.w3.org/2001/XMLSchema}QName" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="parallelMultiple" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCatchEvent", propOrder = {
    "dataOutput",
    "dataOutputAssociation",
    "outputSet"

})
@XmlSeeAlso({
    TStartEvent.class,
    TIntermediateCatchEvent.class,
    TBoundaryEvent.class
})
public abstract class TCatchEvent
        extends TEvent {

    private List<TDataOutput> dataOutput;
    private List<TDataOutputAssociation> dataOutputAssociation;
    protected TOutputSet outputSet;
    //@XmlElementRef(name = "eventDefinition", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)

    @XmlAttribute
    private Boolean parallelMultiple = false;

    /**
     * Gets the value of the dataOutput property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the dataOutput property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataOutput().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TDataOutput }
     *
     *
     */
    public List<TDataOutput> getDataOutput() {
        if (dataOutput == null) {
            setDataOutput(new ArrayList<TDataOutput>());
        }
        return this.dataOutput;
    }

    /**
     * Gets the value of the dataOutputAssociation property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the dataOutputAssociation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataOutputAssociation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TDataOutputAssociation }
     *
     *
     */
    public List<TDataOutputAssociation> getDataOutputAssociation() {
        if (dataOutputAssociation == null) {
            setDataOutputAssociation(new ArrayList<TDataOutputAssociation>());
        }
        return this.dataOutputAssociation;
    }

    /**
     * Gets the value of the outputSet property.
     *
     * @return possible object is {@link TOutputSet }
     *
     */
    public TOutputSet getOutputSet() {
        return outputSet;
    }

    /**
     * Sets the value of the outputSet property.
     *
     * @param value allowed object is {@link TOutputSet }
     *
     */
    public void setOutputSet(TOutputSet value) {
        this.outputSet = value;
    }

    /**
     * Gets the value of the parallelMultiple property.
     *
     * @return possible object is {@link Boolean }
     *
     */
//    public boolean isParallelMultiple() {
//        if (getParallelMultiple() == null) {
//            return false;
//        } else {
//            return getParallelMultiple();
//        }
//    }
    /**
     * Sets the value of the parallelMultiple property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setParallelMultiple(Boolean value) {
        this.parallelMultiple = value;
    }

    /**
     * @return the parallelMultiple
     */
    public Boolean getParallelMultiple() {
        return parallelMultiple;
    }

    /**
     * @param dataOutput the dataOutput to set
     */
    public void setDataOutput(List<TDataOutput> dataOutput) {
        this.dataOutput = dataOutput;
    }

    /**
     * @param dataOutputAssociation the dataOutputAssociation to set
     */
    public void setDataOutputAssociation(List<TDataOutputAssociation> dataOutputAssociation) {
        this.dataOutputAssociation = dataOutputAssociation;
    }

    public void addDataOutputAssociation(TDataOutputAssociation dataOutputAssociation) {
        if (dataOutputAssociation == null) {
            setDataOutputAssociation(new ArrayList<TDataOutputAssociation>());
        }
        this.dataOutputAssociation.add(dataOutputAssociation);
    }
}
