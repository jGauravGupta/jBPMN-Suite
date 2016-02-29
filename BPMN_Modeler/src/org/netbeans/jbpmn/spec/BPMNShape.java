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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for BPMNShape complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="BPMNShape">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/DD/20100524/DI}LabeledShape">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/DI}BPMNLabel" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="bpmnElement" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <attribute name="isHorizontal" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       <attribute name="isExpanded" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       <attribute name="isMarkerVisible" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       <attribute name="isMessageVisible" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       <attribute name="participantBandKind" type="{http://www.omg.org/spec/BPMN/20100524/DI}ParticipantBandKind" />
 *       <attribute name="choreographyActivityShape" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "BPMNShape") // JBPMN
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BPMNShape", propOrder = {
    "bpmnLabel"/*,"bpmnShapeDesign"*/
})
public class BPMNShape
    extends LabeledShape
{
//    @XmlElementWrapper( name="extensionElements" )
//    @XmlElement(name = "extension")
//    private BPMNShapeDesign bpmnShapeDesign;
    @XmlElement(name = "BPMNLabel", namespace = "http://www.omg.org/spec/BPMN/20100524/DI")
    protected BPMNLabel bpmnLabel;
    @XmlAttribute
    protected String bpmnElement;//QName bpmnElement;
    @XmlAttribute
    protected Boolean isHorizontal;
    @XmlAttribute
    protected Boolean isExpanded;
    @XmlAttribute
    protected Boolean isMarkerVisible;
    @XmlAttribute
    protected Boolean isMessageVisible;
    @XmlAttribute
    protected ParticipantBandKind participantBandKind;
    @XmlAttribute
    protected QName choreographyActivityShape;

    /**
     * Gets the value of the bpmnLabel property.
     * 
     * @return
     *     possible object is
     *     {@link BPMNLabel }
     *     
     */
    public BPMNLabel getBPMNLabel() {
        return bpmnLabel;
    }

    /**
     * Sets the value of the bpmnLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link BPMNLabel }
     *     
     */
    public void setBPMNLabel(BPMNLabel value) {
        this.bpmnLabel = value;
    }

    /**
     * Gets the value of the bpmnElement property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public String getBpmnElement() {
        return bpmnElement;
    }

    /**
     * Sets the value of the bpmnElement property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setBpmnElement(String value) {
        this.bpmnElement = value;
    }

    /**
     * Gets the value of the isHorizontal property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsHorizontal() {
        return isHorizontal;
    }

    /**
     * Sets the value of the isHorizontal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsHorizontal(Boolean value) {
        this.isHorizontal = value;
    }

    /**
     * Gets the value of the isExpanded property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsExpanded() {
        return isExpanded;
    }

    /**
     * Sets the value of the isExpanded property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsExpanded(Boolean value) {
        this.isExpanded = value;
    }

    /**
     * Gets the value of the isMarkerVisible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMarkerVisible() {
        return isMarkerVisible;
    }

    /**
     * Sets the value of the isMarkerVisible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMarkerVisible(Boolean value) {
        this.isMarkerVisible = value;
    }

    /**
     * Gets the value of the isMessageVisible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMessageVisible() {
        return isMessageVisible;
    }

    /**
     * Sets the value of the isMessageVisible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMessageVisible(Boolean value) {
        this.isMessageVisible = value;
    }

    /**
     * Gets the value of the participantBandKind property.
     * 
     * @return
     *     possible object is
     *     {@link ParticipantBandKind }
     *     
     */
    public ParticipantBandKind getParticipantBandKind() {
        return participantBandKind;
    }

    /**
     * Sets the value of the participantBandKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipantBandKind }
     *     
     */
    public void setParticipantBandKind(ParticipantBandKind value) {
        this.participantBandKind = value;
    }

    /**
     * Gets the value of the choreographyActivityShape property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getChoreographyActivityShape() {
        return choreographyActivityShape;
    }

    /**
     * Sets the value of the choreographyActivityShape property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setChoreographyActivityShape(QName value) {
        this.choreographyActivityShape = value;
    }

//    /**
//     * @return the bpmnShapeDesign
//     */
//    public BPMNShapeDesign getBpmnShapeDesign() {
//        return bpmnShapeDesign;
//    }
//
//    /**
//     * @param bpmnShapeDesign the bpmnShapeDesign to set
//     */
//    public void setBpmnShapeDesign(BPMNShapeDesign bpmnShapeDesign) {
//        this.bpmnShapeDesign = bpmnShapeDesign;
//    }

}
