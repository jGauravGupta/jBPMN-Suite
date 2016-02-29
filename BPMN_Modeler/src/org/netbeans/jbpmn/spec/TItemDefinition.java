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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;

/**
 * <p>
 * Java class for tItemDefinition complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tItemDefinition">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tRootElement">
 *       <attribute name="structureRef" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <attribute name="isCollection" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       <attribute name="itemKind" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tItemKind" default="Information" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tItemDefinition")
public class TItemDefinition
        extends TRootElement {

    @XmlAttribute
//    protected QName structureRef;
    protected String structureRef;
    @XmlAttribute
    protected Boolean isCollection;
    @XmlAttribute
    protected TItemKind itemKind;

    public String getDisplayValue() {
        if (isCollection) {
            return structureRef + "[]";
        } else {
            return structureRef;
        }

    }

    /**
     * Gets the value of the structureRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public String getStructureRef() {
        return structureRef;
    }

    /**
     * Sets the value of the structureRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setStructureRef(String value) {
        this.structureRef = value;
    }

    /**
     * Gets the value of the isCollection property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isIsCollection() {
        if (isCollection == null) {
            return false;
        } else {
            return isCollection;
        }
    }

    /**
     * Sets the value of the isCollection property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setIsCollection(Boolean value) {
        this.isCollection = value;
    }

    /**
     * Gets the value of the itemKind property.
     *
     * @return possible object is {@link TItemKind }
     *
     */
    public TItemKind getItemKind() {
        if (itemKind == null) {
            return TItemKind.INFORMATION;
        } else {
            return itemKind;
        }
    }

    /**
     * Sets the value of the itemKind property.
     *
     * @param value allowed object is {@link TItemKind }
     *
     */
    public void setItemKind(TItemKind value) {
        this.itemKind = value;
    }

    @Override
    public void removeBaseElement(IBaseElement baseElement_In) {
    }

    @Override
    public void addBaseElement(IBaseElement baseElement_In) {
    }
}
