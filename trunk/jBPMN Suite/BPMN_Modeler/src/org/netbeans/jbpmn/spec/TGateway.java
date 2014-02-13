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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tGateway complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tGateway">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tFlowNode">
 *       <attribute name="gatewayDirection" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tGatewayDirection" default="Unspecified" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGateway")
@XmlSeeAlso({
    TComplexGateway.class,
    TInclusiveGateway.class,
    TEventBasedGateway.class,
    TParallelGateway.class,
    TExclusiveGateway.class
})
public class TGateway
    extends TFlowNode
{

    @XmlAttribute
    protected TGatewayDirection gatewayDirection;

    /**
     * Gets the value of the gatewayDirection property.
     * 
     * @return
     *     possible object is
     *     {@link TGatewayDirection }
     *     
     */
    public TGatewayDirection getGatewayDirection() {
        if (gatewayDirection == null) {
            return TGatewayDirection.UNSPECIFIED;
        } else {
            return gatewayDirection;
        }
    }

    /**
     * Sets the value of the gatewayDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link TGatewayDirection }
     *     
     */
    public void setGatewayDirection(TGatewayDirection value) {
        this.gatewayDirection = value;
    }

}
