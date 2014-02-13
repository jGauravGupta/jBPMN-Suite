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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * <p>
 * Java class for tMultiInstanceLoopCharacteristics complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tMultiInstanceLoopCharacteristics">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tLoopCharacteristics">
 *       <sequence>
 *         <element name="loopCardinality" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tExpression" minOccurs="0"/>
 *         <element name="loopDataInputRef" type="{http://www.w3.org/2001/XMLSchema}QName" minOccurs="0"/>
 *         <element name="loopDataOutputRef" type="{http://www.w3.org/2001/XMLSchema}QName" minOccurs="0"/>
 *         <element name="inputDataItem" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tDataInput" minOccurs="0"/>
 *         <element name="outputDataItem" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tDataOutput" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}complexBehaviorDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="completionCondition" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tExpression" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="isSequential" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       <attribute name="behavior" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tMultiInstanceFlowCondition" default="All" />
 *       <attribute name="oneBehaviorEventRef" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <attribute name="noneBehaviorEventRef" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlRootElement(name = "multiInstanceLoopCharacteristics") // JBPMN
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tMultiInstanceLoopCharacteristics", propOrder = {
    "loopCardinality",
    "loopDataInputRef",
    "loopDataOutputRef",
    "inputDataItem",
    "outputDataItem",
    "complexBehaviorDefinition",
    "completionCondition"
})
public class TMultiInstanceLoopCharacteristics
        extends TLoopCharacteristics {

    protected TExpression loopCardinality;//TExpression loopCardinality;
//    protected QName loopDataInputRef;
//    protected QName loopDataOutputRef;
    protected String loopDataInputRef;
    protected String loopDataOutputRef;
    protected TDataInput inputDataItem;
    protected TDataOutput outputDataItem;
    protected List<TComplexBehaviorDefinition> complexBehaviorDefinition;
    protected TExpression completionCondition;//TExpression completionCondition;
    @XmlAttribute
    protected Boolean isSequential = false;
    @XmlAttribute
    protected TMultiInstanceFlowCondition behavior;
    @XmlAttribute
    protected QName oneBehaviorEventRef;
    @XmlAttribute
    protected QName noneBehaviorEventRef;

    public TMultiInstanceLoopCharacteristics() {
    }

    public TMultiInstanceLoopCharacteristics(String id, Boolean isSequential) {
        this.setId(id);
        this.isSequential = isSequential;
    }

    /**
     * Gets the value of the loopCardinality property.
     *
     * @return possible object is {@link TExpression }
     *
     */
    public TFormalExpression getLoopCardinality() {
        if (!(loopCardinality instanceof TFormalExpression) && loopCardinality != null) {
            loopCardinality = loopCardinality.getFormalExpression();
        }
        return (TFormalExpression) loopCardinality;
    }

    /**
     * Sets the value of the loopCardinality property.
     *
     * @param value allowed object is {@link TExpression }
     *
     */
    public void setLoopCardinality(TExpression value) {
        this.loopCardinality = value;
    }

    /**
     * Gets the value of the loopDataInputRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public String getLoopDataInputRef() {
        return loopDataInputRef;
    }

    /**
     * Sets the value of the loopDataInputRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setLoopDataInputRef(String value) {
        this.loopDataInputRef = value;
    }

    /**
     * Gets the value of the loopDataOutputRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public String getLoopDataOutputRef() {
        return loopDataOutputRef;
    }

    /**
     * Sets the value of the loopDataOutputRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setLoopDataOutputRef(String value) {
        this.loopDataOutputRef = value;
    }

    /**
     * Gets the value of the inputDataItem property.
     *
     * @return possible object is {@link TDataInput }
     *
     */
    public TDataInput getInputDataItem() {
        return inputDataItem;
    }

    /**
     * Sets the value of the inputDataItem property.
     *
     * @param value allowed object is {@link TDataInput }
     *
     */
    public void setInputDataItem(TDataInput value) {
        this.inputDataItem = value;
    }

    /**
     * Gets the value of the outputDataItem property.
     *
     * @return possible object is {@link TDataOutput }
     *
     */
    public TDataOutput getOutputDataItem() {
        return outputDataItem;
    }

    /**
     * Sets the value of the outputDataItem property.
     *
     * @param value allowed object is {@link TDataOutput }
     *
     */
    public void setOutputDataItem(TDataOutput value) {
        this.outputDataItem = value;
    }

    /**
     * Gets the value of the complexBehaviorDefinition property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the complexBehaviorDefinition property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplexBehaviorDefinition().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TComplexBehaviorDefinition }
     *
     *
     */
    public List<TComplexBehaviorDefinition> getComplexBehaviorDefinition() {
        if (complexBehaviorDefinition == null) {
            complexBehaviorDefinition = new ArrayList<TComplexBehaviorDefinition>();
        }
        return this.complexBehaviorDefinition;
    }

    /**
     * Gets the value of the completionCondition property.
     *
     * @return possible object is {@link TExpression }
     *
     */
    public TFormalExpression getCompletionCondition() {
        if (!(completionCondition instanceof TFormalExpression) && completionCondition != null) {
            completionCondition = completionCondition.getFormalExpression();
        }
        return (TFormalExpression) completionCondition;
    }

    /**
     * Sets the value of the completionCondition property.
     *
     * @param value allowed object is {@link TExpression }
     *
     */
    public void setCompletionCondition(TExpression value) {
        this.completionCondition = value;
    }

    /**
     * Gets the value of the isSequential property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isIsSequential() {
        if (isSequential == null) {
            return false;
        } else {
            return isSequential;
        }
    }

    /**
     * Sets the value of the isSequential property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setIsSequential(Boolean value) {
        this.isSequential = value;
    }

    /**
     * Gets the value of the behavior property.
     *
     * @return possible object is {@link TMultiInstanceFlowCondition }
     *
     */
    public TMultiInstanceFlowCondition getBehavior() {
        if (behavior == null) {
            return TMultiInstanceFlowCondition.ALL;
        } else {
            return behavior;
        }
    }

    /**
     * Sets the value of the behavior property.
     *
     * @param value allowed object is {@link TMultiInstanceFlowCondition }
     *
     */
    public void setBehavior(TMultiInstanceFlowCondition value) {
        this.behavior = value;
    }

    /**
     * Gets the value of the oneBehaviorEventRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public QName getOneBehaviorEventRef() {
        return oneBehaviorEventRef;
    }

    /**
     * Sets the value of the oneBehaviorEventRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setOneBehaviorEventRef(QName value) {
        this.oneBehaviorEventRef = value;
    }

    /**
     * Gets the value of the noneBehaviorEventRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public QName getNoneBehaviorEventRef() {
        return noneBehaviorEventRef;
    }

    /**
     * Sets the value of the noneBehaviorEventRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setNoneBehaviorEventRef(QName value) {
        this.noneBehaviorEventRef = value;
    }

}
