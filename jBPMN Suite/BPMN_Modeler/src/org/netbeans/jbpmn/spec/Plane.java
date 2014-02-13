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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Plane complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="Plane">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/DD/20100524/DI}Node">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/DD/20100524/DI}DiagramElement" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "Plane", namespace = "http://www.omg.org/spec/DD/20100524/DI", propOrder = {
    "diagramElement"
})
@XmlSeeAlso({
    BPMNPlane.class
})
public abstract class Plane
    extends Node
{

   // @XmlElementRef(name = "DiagramElement", namespace = "http://www.omg.org/spec/DD/20100524/DI", type = JAXBElement.class)  //JBPMN
    @XmlElements({
        @XmlElement(name = "BPMNEdge", type = BPMNEdge.class , namespace = "http://www.omg.org/spec/BPMN/20100524/DI"),
        @XmlElement(name = "BPMNShape", type = BPMNShape.class , namespace = "http://www.omg.org/spec/BPMN/20100524/DI")
    })
    protected List<DiagramElement> diagramElement;//List<JAXBElement<? extends DiagramElement>> diagramElement;

    /**
     * Gets the value of the diagramElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diagramElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiagramElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link DiagramElement }{@code >}
     * {@link JAXBElement }{@code <}{@link BPMNShape }{@code >}
     * {@link JAXBElement }{@code <}{@link BPMNEdge }{@code >}
     * 
     * 
     */
    public List<DiagramElement> getDiagramElement() {
        if (diagramElement == null) {
            diagramElement = new ArrayList<DiagramElement>();
        }
        return this.diagramElement;
    }
    
    public BPMNShape getBPMNShape(String bpmnElement) {
        BPMNShape bpmnShape = null;
        if (diagramElement != null) {
            for (DiagramElement diagramElement_TMP : diagramElement) {
                if (diagramElement_TMP instanceof BPMNShape) {
                    BPMNShape bpmnShape_TMP = (BPMNShape) diagramElement_TMP;
                    if (bpmnShape_TMP.getBpmnElement().equals(bpmnElement)) {
                        bpmnShape = bpmnShape_TMP;
                        break;
                    }
                }
            }
        }
        return bpmnShape;
    }
    
     public void addDiagramElement(DiagramElement diagramElement_In) {
        if (diagramElement == null) {
            diagramElement = new ArrayList<DiagramElement>();
        }
        this.diagramElement.add(diagramElement_In);
    }

}
