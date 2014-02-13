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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tDataAssociation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tDataAssociation">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tBaseElement">
 *       <sequence>
 *         <element name="sourceRef" type="{http://www.w3.org/2001/XMLSchema}IDREF" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="targetRef" type="{http://www.w3.org/2001/XMLSchema}IDREF"/>
 *         <element name="transformation" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tFormalExpression" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}assignment" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "tDataAssociation", propOrder = {
    "sourceRef",
    "targetRef",
    "transformation",
    "assignment"
})
@XmlSeeAlso({
    TDataInputAssociation.class,
    TDataOutputAssociation.class
})
public class TDataAssociation
    extends TBaseElement
{

//    @XmlElementRef(name = "sourceRef", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
//    protected List<JAXBElement<Object>> sourceRef;
//    @XmlElement(required = true)
//    @XmlIDREF
//    @XmlSchemaType(name = "IDREF")
//    protected Object targetRef;
    @XmlElement(name = "sourceRef")
    private String sourceRef;
    @XmlElement(required = true)
    protected String targetRef;
    protected TFormalExpression transformation;
//    protected List<TAssignment> assignment;
    private TAssignment assignment;

    /**
     * Gets the value of the sourceRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * 
     */
//    public List<JAXBElement<Object>> getSourceRef() {
//        if (sourceRef == null) {
//            sourceRef = new ArrayList<JAXBElement<Object>>();
//        }
//        return this.sourceRef;
//    }
    public String getSourceRef() {
         return this.sourceRef;
    }
    /**
     * Gets the value of the targetRef property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
//    public Object getTargetRef() {
//        return targetRef;
//    }   
    public String getTargetRef() {
        return targetRef;
    }

    /**
     * Sets the value of the targetRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
//    public void setTargetRef(Object value) {
//        this.targetRef = value;
//    }
   public void setTargetRef(String value) {
        this.targetRef = value;
    }
    /**
     * Gets the value of the transformation property.
     * 
     * @return
     *     possible object is
     *     {@link TFormalExpression }
     *     
     */
    public TFormalExpression getTransformation() {
        return transformation;
    }

    /**
     * Sets the value of the transformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TFormalExpression }
     *     
     */
    public void setTransformation(TFormalExpression value) {
        this.transformation = value;
    }

    /**
     * Gets the value of the assignment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assignment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssignment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TAssignment }
     * 
     * 
     * @return 
     */
//    public List<TAssignment> getAssignment() {
//        if (assignment == null) {
//            assignment = new ArrayList<TAssignment>();
//        }
//        return this.assignment;
//    }
    public TAssignment getAssignment() {
        return this.assignment;
    }

    /**
     * @param sourceRef the sourceRef to set
     */
    public void setSourceRef(String sourceRef) {
        this.sourceRef = sourceRef;
    }

    /**
     * @param assignment the assignment to set
     */
    public void setAssignment(TAssignment assignment) {
        this.assignment = assignment;
    }
}
