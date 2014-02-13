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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.netbeans.modeler.specification.model.document.IDiagramElement;


/**
 * <p>Java class for BPMNDiagram complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="BPMNDiagram">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/DD/20100524/DI}Diagram">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/DI}BPMNPlane"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/DI}BPMNLabelStyle" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "BPMNDiagram") // JBPMN
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BPMNDiagram", namespace = "http://www.omg.org/spec/BPMN/20100524/DI", propOrder = {
    "bpmnPlane",
    "bpmnLabelStyle"
})
public class BPMNDiagram
    extends Diagram implements IDiagramElement
{

    @XmlElement(name = "BPMNPlane", required = true , namespace = "http://www.omg.org/spec/BPMN/20100524/DI")
    protected BPMNPlane bpmnPlane;
    @XmlElement(name = "BPMNLabelStyle")
    protected List<BPMNLabelStyle> bpmnLabelStyle;


    /**
     * Gets the value of the bpmnPlane property.
     * 
     * @return
     *     possible object is
     *     {@link BPMNPlane }
     *     
     */
    public BPMNPlane getBPMNPlane() {
        return bpmnPlane;
    }

    /**
     * Sets the value of the bpmnPlane property.
     * 
     * @param value
     *     allowed object is
     *     {@link BPMNPlane }
     *     
     */
    public void setBPMNPlane(BPMNPlane value) {
        this.bpmnPlane = value;
    }

    /**
     * Gets the value of the bpmnLabelStyle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bpmnLabelStyle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBPMNLabelStyle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BPMNLabelStyle }
     * 
     * 
     */
    public List<BPMNLabelStyle> getBPMNLabelStyle() {
        if (bpmnLabelStyle == null) {
            bpmnLabelStyle = new ArrayList<BPMNLabelStyle>();
        }
        return this.bpmnLabelStyle;
    }
    
  

}
