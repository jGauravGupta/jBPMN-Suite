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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;


/**
 * <p>Java class for tInterface complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tInterface">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tRootElement">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}operation" maxOccurs="unbounded"/>
 *       </sequence>
 *       <attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       <attribute name="implementationRef" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInterface", propOrder = {
    "operation"
})
public class TInterface
    extends TRootElement
{

    @XmlElement(required = true)
    protected List<TOperation> operation;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected String implementationRef;//   protected QName implementationRef;

    /**
     * Gets the value of the operation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TOperation }
     * 
     * 
     */
    public List<TOperation> getOperation() {
        if (operation == null) {
            operation = new ArrayList<TOperation>();
        }
        return this.operation;
    } 
    public void addOperation(TOperation operation_Local) {
        if (operation == null) {
            operation = new ArrayList<TOperation>();
        }
        this.operation.add(operation_Local);
    }
    public void removeOperation(TOperation operation_Local) {
        if (operation != null) {
            this.operation.remove(operation_Local);
        }
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the implementationRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public String getImplementationRef() {
        return implementationRef;
    }

    /**
     * Sets the value of the implementationRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setImplementationRef(String value) {
        this.implementationRef = value;
    }
    @Override
    public  void removeBaseElement(IBaseElement baseElement_In){}
    @Override
    public  void addBaseElement(IBaseElement baseElement_In){}
}
