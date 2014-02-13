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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.netbeans.jbpmn.spec.extend.ResourceRoleHandler;

/**
 * <p>
 * Java class for tActivity complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tActivity">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tFlowNode">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}ioSpecification" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}property" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}dataInputAssociation" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}dataOutputAssociation" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}resourceRole" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}loopCharacteristics" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="isForCompensation" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       <attribute name="startQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" default="1" />
 *       <attribute name="completionQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" default="1" />
 *       <attribute name="default" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tActivity", propOrder = {
    "ioSpecification",
    "property",
    "dataInputAssociation",
    "dataOutputAssociation",
    "resourceRole",
    "loopCharacteristics"
})
@XmlSeeAlso({
    TSubProcess.class,
    TTask.class,
    TCallActivity.class
})
public abstract class TActivity
        extends TFlowNode implements ResourceRoleHandler {

    protected TInputOutputSpecification ioSpecification;
    private List<TProperty> property;
    private List<TDataInputAssociation> dataInputAssociation;
    private List<TDataOutputAssociation> dataOutputAssociation;
//    @XmlElementRef(name = "resourceRole", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
//    private List<JAXBElement<? extends TResourceRole>> resourceRole;
    @XmlElements({
        @XmlElement(name = "performer", type = TPerformer.class),
        @XmlElement(name = "humanPerformer", type = THumanPerformer.class),
        @XmlElement(name = "potentialOwner", type = TPotentialOwner.class)
    })
    private List<? extends TResourceRole> resourceRole;
    //@XmlElementRef(name = "loopCharacteristics", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
//    @XmlElement(name = "loopCharacteristics")
    @XmlElements({
        @XmlElement(name = "standardLoopCharacteristics", type = TStandardLoopCharacteristics.class),
        @XmlElement(name = "multiInstanceLoopCharacteristics", type = TMultiInstanceLoopCharacteristics.class)
    })
    protected TLoopCharacteristics loopCharacteristics;//JBPMN JAXBElement<? extends TLoopCharacteristics> loopCharacteristics;
    @XmlAttribute
    protected boolean isForCompensation = false;
    @XmlAttribute
    protected Integer startQuantity = 1;//JBPMN BigInteger startQuantity;Property Sheet does not support
    @XmlAttribute
    protected Integer completionQuantity = 1;//JBPMN BigInteger completionQuantity;Property Sheet does not support
    @XmlAttribute(name = "default")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object _default;

    void beforeMarshal(Marshaller marshaller) {
        super.beforeMarshal(marshaller);
        if (ioSpecification != null) {
            if (ioSpecification.getDataInput().size() + ioSpecification.getDataOutput().size() < 1) {
                setIoSpecification(null);
            }
        }
    }

    /**
     * Gets the value of the ioSpecification property.
     *
     * @return possible object is {@link TInputOutputSpecification }
     *
     */
    public TInputOutputSpecification getIoSpecification() {
        return ioSpecification;
    }

    /**
     * Sets the value of the ioSpecification property.
     *
     * @param value allowed object is {@link TInputOutputSpecification }
     *
     */
    public void setIoSpecification(TInputOutputSpecification value) {
        this.ioSpecification = value;
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
            setProperty(new ArrayList<TProperty>());
        }
        return this.property;
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

    public void addDataInputAssociation(TDataInputAssociation dataInputAssociation) {
        if (dataInputAssociation == null) {
            setDataInputAssociation(new ArrayList<TDataInputAssociation>());
        }
        this.dataInputAssociation.add(dataInputAssociation);
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

    public void addDataOutputAssociation(TDataOutputAssociation dataOutputAssociation) {
        if (dataOutputAssociation == null) {
            setDataOutputAssociation(new ArrayList<TDataOutputAssociation>());
        }
        this.dataOutputAssociation.add(dataOutputAssociation);
    }

    /**
     * Gets the value of the resourceRole property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the resourceRole property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourceRole().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list null null     {@link JAXBElement }{@code <}{@link TPerformer }{@code >}
     * {@link JAXBElement }{@code <}{@link TResourceRole }{@code >}
     * {@link JAXBElement }{@code <}{@link THumanPerformer }{@code >}
     * {@link JAXBElement }{@code <}{@link TPotentialOwner }{@code >}
     *
     *
     */
    public List<? extends TResourceRole> getResourceRole() {
        if (resourceRole == null) {
            setResourceRole(new ArrayList<TResourceRole>());
        }
        return this.resourceRole;
    }

    /**
     * Gets the value of the loopCharacteristics property.
     *
     * @return possible object is null null     {@link JAXBElement }{@code <}{@link TLoopCharacteristics }{@code >}
     *     {@link JAXBElement }{@code <}{@link TMultiInstanceLoopCharacteristics }{@code >}
     *     {@link JAXBElement }{@code <}{@link TStandardLoopCharacteristics }{@code >}
     *
     */
    public TLoopCharacteristics getLoopCharacteristics() {
        return loopCharacteristics;
    }

    /**
     * Sets the value of the loopCharacteristics property.
     *
     * @param value allowed object is null null     {@link JAXBElement }{@code <}{@link TLoopCharacteristics }{@code >}
     *     {@link JAXBElement }{@code <}{@link TMultiInstanceLoopCharacteristics }{@code >}
     *     {@link JAXBElement }{@code <}{@link TStandardLoopCharacteristics }{@code >}
     *
     */
    public void setLoopCharacteristics(TLoopCharacteristics value) {
        this.loopCharacteristics = value;
    }

    /**
     * Gets the value of the isForCompensation property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean getIsForCompensation() {  //JBPMN isIsForCompensation => getIsForCompensation boolean is/get conflict internal
//        if (isForCompensation == null) {
//            return false;
//        } else {
        return isForCompensation;
//        }
    }

    /**
     * Sets the value of the isForCompensation property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setIsForCompensation(Boolean value) {
        this.isForCompensation = value;
    }

    /**
     * Gets the value of the startQuantity property.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public Integer getStartQuantity() {
        if (startQuantity == null) {
            return new Integer("1");
        } else {
            return startQuantity;
        }
    }

    /**
     * Sets the value of the startQuantity property.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setStartQuantity(Integer value) {
        this.startQuantity = value;
    }

    /**
     * Gets the value of the completionQuantity property.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public Integer getCompletionQuantity() {
        if (completionQuantity == null) {
            return new Integer("1");
        } else {
            return completionQuantity;
        }
    }

    /**
     * Sets the value of the completionQuantity property.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setCompletionQuantity(Integer value) {
        this.completionQuantity = value;
    }

    /**
     * Gets the value of the default property.
     *
     * @return possible object is {@link Object }
     *
     */
    public Object getDefault() {
        return _default;
    }

    /**
     * Sets the value of the default property.
     *
     * @param value allowed object is {@link Object }
     *
     */
    public void setDefault(Object value) {
        this._default = value;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(List<TProperty> property) {
        this.property = property;
    }

    /**
     * @param dataInputAssociation the dataInputAssociation to set
     */
    public void setDataInputAssociation(List<TDataInputAssociation> dataInputAssociation) {
        this.dataInputAssociation = dataInputAssociation;
    }

    /**
     * @param dataOutputAssociation the dataOutputAssociation to set
     */
    public void setDataOutputAssociation(List<TDataOutputAssociation> dataOutputAssociation) {
        this.dataOutputAssociation = dataOutputAssociation;
    }

    /**
     * @param resourceRole the resourceRole to set
     */
    public void setResourceRole(List<? extends TResourceRole> resourceRole) {
        this.resourceRole = resourceRole;
    }

}
