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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tOutputSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tOutputSet">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tBaseElement">
 *       <sequence>
 *         <element name="dataOutputRefs" type="{http://www.w3.org/2001/XMLSchema}IDREF" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="optionalOutputRefs" type="{http://www.w3.org/2001/XMLSchema}IDREF" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="whileExecutingOutputRefs" type="{http://www.w3.org/2001/XMLSchema}IDREF" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="inputSetRefs" type="{http://www.w3.org/2001/XMLSchema}IDREF" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
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
@XmlType(name = "tOutputSet", propOrder = {
    "dataOutputRefs",
    "optionalOutputRefs",
    "whileExecutingOutputRefs",
    "inputSetRefs"
})
public class TOutputSet
    extends TBaseElement
{

//    @XmlElementRef(name = "dataOutputRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
//    protected List<JAXBElement<Object>> dataOutputRefs;  //Object => String to avoid //xsi:type="xs:string"
    @XmlElement(name = "dataOutputRefs")//, namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    private List<String> dataOutputRefs;
    @XmlElementRef(name = "optionalOutputRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    protected List<JAXBElement<Object>> optionalOutputRefs;
    @XmlElementRef(name = "whileExecutingOutputRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    protected List<JAXBElement<Object>> whileExecutingOutputRefs;
    @XmlElementRef(name = "inputSetRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    protected List<JAXBElement<Object>> inputSetRefs;
    @XmlAttribute
    protected String name;

    /**
     * Gets the value of the dataOutputRefs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataOutputRefs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataOutputRefs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * 
     */
//    public List<JAXBElement<Object>> getDataOutputRefs() {
//        if (dataOutputRefs == null) {
//            dataOutputRefs = new ArrayList<JAXBElement<Object>>();
//        }
//        return this.dataOutputRefs;
//    }
        public List<String> getDataOutputRefs() {
        if (dataOutputRefs == null) {
            setDataOutputRefs(new ArrayList<String>());
        }
        return this.dataOutputRefs;
    }
        
 public void addDataOutputRefs(String obj) {
        if (dataOutputRefs == null) {
            setDataOutputRefs(new ArrayList<String>());
        }
        this.dataOutputRefs.add(obj);
    }

    /**
     * Gets the value of the optionalOutputRefs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optionalOutputRefs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptionalOutputRefs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * 
     */
    public List<JAXBElement<Object>> getOptionalOutputRefs() {
        if (optionalOutputRefs == null) {
            optionalOutputRefs = new ArrayList<JAXBElement<Object>>();
        }
        return this.optionalOutputRefs;
    }

    /**
     * Gets the value of the whileExecutingOutputRefs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the whileExecutingOutputRefs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWhileExecutingOutputRefs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * 
     */
    public List<JAXBElement<Object>> getWhileExecutingOutputRefs() {
        if (whileExecutingOutputRefs == null) {
            whileExecutingOutputRefs = new ArrayList<JAXBElement<Object>>();
        }
        return this.whileExecutingOutputRefs;
    }

    /**
     * Gets the value of the inputSetRefs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputSetRefs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputSetRefs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * 
     */
    public List<JAXBElement<Object>> getInputSetRefs() {
        if (inputSetRefs == null) {
            inputSetRefs = new ArrayList<JAXBElement<Object>>();
        }
        return this.inputSetRefs;
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
     * @param dataOutputRefs the dataOutputRefs to set
     */
    public void setDataOutputRefs(List<String> dataOutputRefs) {
        this.dataOutputRefs = dataOutputRefs;
    }

}
