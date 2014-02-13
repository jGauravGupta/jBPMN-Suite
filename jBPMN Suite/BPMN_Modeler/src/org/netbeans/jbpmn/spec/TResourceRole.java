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
import javax.xml.namespace.QName;

/**
 * <p>
 * Java class for tResourceRole complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tResourceRole">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tBaseElement">
 *       <choice>
 *         <sequence>
 *           <element name="resourceRef" type="{http://www.w3.org/2001/XMLSchema}QName"/>
 *           <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}resourceParameterBinding" maxOccurs="unbounded" minOccurs="0"/>
 *         </sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}resourceAssignmentExpression" minOccurs="0"/>
 *       </choice>
 *       <attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tResourceRole", propOrder = {
    "resourceRef",
    "resourceParameterBinding",
    "resourceAssignmentExpression"
})
@XmlSeeAlso({
    TPerformer.class
})
public class TResourceRole
        extends TBaseElement {

    @XmlAttribute
    protected String resourceRef;//QName resourceRef
    protected List<TResourceParameterBinding> resourceParameterBinding;
    protected TResourceAssignmentExpression resourceAssignmentExpression;
    @XmlAttribute
    protected String name;

    /**
     * Gets the value of the resourceRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public String getResourceRef() {
        return resourceRef;
    }

    /**
     * Sets the value of the resourceRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setResourceRef(String value) {
        this.resourceRef = value;
    }

    /**
     * Gets the value of the resourceParameterBinding property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the resourceParameterBinding property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourceParameterBinding().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TResourceParameterBinding }
     *
     *
     */
    public List<TResourceParameterBinding> getResourceParameterBinding() {
        if (resourceParameterBinding == null) {
            resourceParameterBinding = new ArrayList<TResourceParameterBinding>();
        }
        return this.resourceParameterBinding;
    }

    /**
     * Gets the value of the resourceAssignmentExpression property.
     *
     * @return possible object is {@link TResourceAssignmentExpression }
     *
     */
    public TResourceAssignmentExpression getResourceAssignmentExpression() {
        return resourceAssignmentExpression;
    }

    /**
     * Sets the value of the resourceAssignmentExpression property.
     *
     * @param value allowed object is {@link TResourceAssignmentExpression }
     *
     */
    public void setResourceAssignmentExpression(TResourceAssignmentExpression value) {
        this.resourceAssignmentExpression = value;
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

}
