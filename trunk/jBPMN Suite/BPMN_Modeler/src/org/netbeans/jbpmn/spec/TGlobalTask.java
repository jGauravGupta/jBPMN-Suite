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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tGlobalTask complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tGlobalTask">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tCallableElement">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}resourceRole" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "tGlobalTask", propOrder = {
    "resourceRole"
})
@XmlSeeAlso({
    TGlobalUserTask.class,
    TGlobalBusinessRuleTask.class,
    TGlobalScriptTask.class,
    TGlobalManualTask.class
})
public class TGlobalTask
    extends TCallableElement
{

    @XmlElementRef(name = "resourceRole", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    protected List<JAXBElement<? extends TResourceRole>> resourceRole;

    /**
     * Gets the value of the resourceRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resourceRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourceRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TPerformer }{@code >}
     * {@link JAXBElement }{@code <}{@link TResourceRole }{@code >}
     * {@link JAXBElement }{@code <}{@link THumanPerformer }{@code >}
     * {@link JAXBElement }{@code <}{@link TPotentialOwner }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends TResourceRole>> getResourceRole() {
        if (resourceRole == null) {
            resourceRole = new ArrayList<JAXBElement<? extends TResourceRole>>();
        }
        return this.resourceRole;
    }

}
