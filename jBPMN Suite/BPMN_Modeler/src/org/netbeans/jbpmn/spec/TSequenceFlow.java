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

import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.netbeans.modeler.specification.model.document.core.IFlowEdge;

/**
 * <p>
 * Java class for tSequenceFlow complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tSequenceFlow">
 * <complexContent>
 * <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tFlowElement">
 * <sequence>
 * <element name="conditionExpression"
 * type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tExpression"
 * minOccurs="0"/>
 * </sequence>
 * <attribute name="sourceRef" use="required"
 * type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 * <attribute name="targetRef" use="required"
 * type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 * <attribute name="isImmediate"
 * type="{http://www.w3.org/2001/XMLSchema}boolean" />
 * <anyAttribute processContents='lax' namespace='##other'/>
 * </extension>
 * </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tSequenceFlow", propOrder = {
    "conditionExpression"
})
@XmlRootElement(name = "sequenceFlow") // JBPMN
public class TSequenceFlow
        extends TFlowElement implements IFlowEdge {

//    protected TExpression conditionExpression;
    protected TExpression conditionExpression;
    @XmlAttribute(required = true)
    //@XmlIDREF
    //@XmlSchemaType(name = "IDREF")
    protected String sourceRef;//Object sourceRef
    @XmlAttribute(required = true)
    //@XmlIDREF
    //@XmlSchemaType(name = "IDREF")
    protected String targetRef;//Object targetRef;
    @XmlAttribute
    protected Boolean isImmediate;

    void beforeMarshal(Marshaller marshaller) {
        super.beforeMarshal(marshaller);

        if (conditionExpression != null) {
            if (!(conditionExpression.getContent() != null && !conditionExpression.getContent().trim().isEmpty())) {
                conditionExpression = null;
            }
        }
    }

    /**
     * Gets the value of the conditionExpression property.
     *
     * @return possible object is {@link TExpression }
     *
     */
    public TFormalExpression getConditionExpression() {
        if (conditionExpression == null) {
            conditionExpression = TFormalExpression.getNewInstance();
        }
        if (!(conditionExpression instanceof TFormalExpression) && conditionExpression != null) {
            conditionExpression = conditionExpression.getFormalExpression();
        }
        return (TFormalExpression) conditionExpression;
    }

    /**
     * Sets the value of the conditionExpression property.
     *
     * @param value allowed object is {@link TExpression }
     *
     */
    public void setConditionExpression(TFormalExpression value) {
        this.conditionExpression = value;
    }

    /**
     * Gets the value of the sourceRef property.
     *
     * @return possible object is {@link Object }
     *
     */
    public String getSourceRef() {
        return sourceRef;
    }

    /**
     * Sets the value of the sourceRef property.
     *
     * @param value allowed object is {@link Object }
     *
     */
    public void setSourceRef(String value) {
        this.sourceRef = value;
    }

    /**
     * Gets the value of the targetRef property.
     *
     * @return possible object is {@link Object }
     *
     */
    public String getTargetRef() {
        return targetRef;
    }

    /**
     * Sets the value of the targetRef property.
     *
     * @param value allowed object is {@link Object }
     *
     */
    public void setTargetRef(String value) {
        this.targetRef = value;
    }

    /**
     * Gets the value of the isImmediate property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getIsImmediate() {
        return isImmediate;
    }

    /**
     * Sets the value of the isImmediate property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setIsImmediate(Boolean value) {
        this.isImmediate = value;
    }
}
