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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for tOperation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tOperation">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tBaseElement">
 *       <sequence>
 *         <element name="inMessageRef" type="{http://www.w3.org/2001/XMLSchema}QName"/>
 *         <element name="outMessageRef" type="{http://www.w3.org/2001/XMLSchema}QName" minOccurs="0"/>
 *         <element name="errorRef" type="{http://www.w3.org/2001/XMLSchema}QName" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "tOperation", propOrder = {
    "inMessageRef",
    "outMessageRef",
    "errorRef"
})
public class TOperation
    extends TBaseElement
{


    @XmlAttribute(required = true)
    protected String name;
//        @XmlElement(required = true)
//    protected QName inMessageRef;
//    protected QName outMessageRef;
//    protected List<QName> errorRef;
//    @XmlAttribute
//    protected QName implementationRef;
    @XmlElement(required = true)
    protected String inMessageRef;
    protected String outMessageRef;
    protected List<String> errorRef;
    @XmlAttribute
    protected String implementationRef;
    @XmlTransient
    private String interface_Ref;
//    @XmlTransient
//    private String targetInterface_Ref;

    /**
     * Gets the value of the inMessageRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public String getInMessageRef() {
        return inMessageRef;
    }

    /**
     * Sets the value of the inMessageRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setInMessageRef(String value) {
        this.inMessageRef = value;
    }

    /**
     * Gets the value of the outMessageRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public String getOutMessageRef() {
        return outMessageRef;
    }

    /**
     * Sets the value of the outMessageRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setOutMessageRef(String value) {
        this.outMessageRef = value;
    }

    /**
     * Gets the value of the errorRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QName }
     * 
     * 
     */
    public List<String> getErrorRef() {
        if (errorRef == null) {
            errorRef = new ArrayList<String>();
        }
        return this.errorRef;
    }
    public void addErrorRef(String error) {
        if (errorRef == null) {
            errorRef = new ArrayList<String>();
        }
         errorRef.add(error);
    }
    public void removeErrorRef(String error) {
        if (errorRef != null) {
            errorRef.remove(error);
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

    /**
     * @return the interface_Ref
     */
    public String getInterface_Ref() {
        return interface_Ref;
    }

    /**
     * @param interface_Ref the interface_Ref to set
     */
    public void setInterface_Ref(String interface_Ref) {
        this.interface_Ref = interface_Ref;
    }


}
