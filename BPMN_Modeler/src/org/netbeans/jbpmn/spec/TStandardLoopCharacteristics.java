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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for tStandardLoopCharacteristics complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tStandardLoopCharacteristics">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tLoopCharacteristics">
 *       <sequence>
 *         <element name="loopCondition" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tExpression" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="testBefore" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       <attribute name="loopMaximum" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlRootElement(name = "standardLoopCharacteristics") // JBPMN
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tStandardLoopCharacteristics", propOrder = {
    "loopCondition"
})
public class TStandardLoopCharacteristics
        extends TLoopCharacteristics {

    protected TExpression loopCondition;//TExpression loopCondition;
    @XmlAttribute
    protected Boolean testBefore = false;
    @XmlAttribute
    protected int loopMaximum;//BigInteger loopMaximum;

    public TStandardLoopCharacteristics() {
    }

    public TStandardLoopCharacteristics(String id) {
        this.setId(id);
    }

    /**
     * Gets the value of the loopCondition property.
     *
     * @return possible object is {@link TExpression }
     *
     */
    public TFormalExpression getLoopCondition() {
        if (!(loopCondition instanceof TFormalExpression) && loopCondition != null) {
            loopCondition = loopCondition.getFormalExpression();
        }
        return (TFormalExpression) loopCondition;
    }

    /**
     * Sets the value of the loopCondition property.
     *
     * @param value allowed object is {@link TExpression }
     *
     */
    public void setLoopCondition(TExpression value) {
        this.loopCondition = value;
    }

    /**
     * Gets the value of the testBefore property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isTestBefore() {
        if (testBefore == null) {
            return false;
        } else {
            return testBefore;
        }
    }

    /**
     * Sets the value of the testBefore property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setTestBefore(Boolean value) {
        this.testBefore = value;
    }

    /**
     * Gets the value of the loopMaximum property.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public int getLoopMaximum() {
        return loopMaximum;
    }

    /**
     * Sets the value of the loopMaximum property.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setLoopMaximum(int value) {
        this.loopMaximum = value;
    }

}
