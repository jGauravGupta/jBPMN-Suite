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


/**
 * <p>Java class for tConditionalEventDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tConditionalEventDefinition">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tEventDefinition">
 *       <sequence>
 *         <element name="condition" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tExpression"/>
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
//@XmlType(name = "tConditionalEventDefinition", propOrder = {
//    "condition"
//})
@XmlType(name = "tConditionalEventDefinition")
public class TConditionalEventDefinition
    extends TEventDefinition
{

   // <conditionalEventDefinition id="sid-536f594d-7259-4d37-adab-19fc8b499dbd">
//            <condition id="sid-59d97061-6d39-46d0-852a-222e33d4d4a3" xsi:type="tFormalExpression">sasssssssssss</condition>
//         </conditionalEventDefinition>
    @XmlElement(required = true)
    protected TExpression condition;

    
    
        
    /**
     * @return the conditionEmbedded
     */
    public String getConditionEmbedded() {
        TConditionalEventDefinition conditionalEventDefinition = this;
        if (conditionalEventDefinition != null
                && conditionalEventDefinition.getCondition() != null
                && conditionalEventDefinition.getCondition().getContent() != null) {
            return conditionalEventDefinition.getCondition().getContent();
        }
        return "";
    }

    /**
     * @param conditionEmbedded the conditionEmbedded to set
     */
    public void setConditionEmbedded(String conditionEmbedded) {
        TConditionalEventDefinition conditionalEventDefinition = this;
        if (conditionalEventDefinition != null) {
            if (conditionalEventDefinition.getCondition() == null) {
                conditionalEventDefinition.setCondition(new TExpression());
            }
            if (conditionEmbedded != null && !conditionEmbedded.trim().isEmpty()) {
                conditionalEventDefinition.getCondition().setContent(conditionEmbedded);
            } else {
                conditionalEventDefinition.getCondition().setContent(null);
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link TExpression }
     *     
     */
    public TExpression getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link TExpression }
     *     
     */
    public void setCondition(TExpression value) {
        this.condition = value;
    }

}
