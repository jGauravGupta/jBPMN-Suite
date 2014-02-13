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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.netbeans.modeler.specification.model.document.IContainerElement;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.core.IFlowElement;

/**
 * <p>
 * Java class for tSubProcess complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tSubProcess">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tActivity">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}laneSet" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}flowElement" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}artifact" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="triggeredByEvent" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tSubProcess", propOrder = {
    "laneSet",
    "flowElement",
    "artifact"
})
@XmlSeeAlso({
    TAdHocSubProcess.class,
    TTransaction.class
})
public class TSubProcess
        extends TActivity implements IContainerElement {

    protected List<TLaneSet> laneSet;
    //@XmlElementRef(name = "flowElement", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    //protected List<JAXBElement<? extends IFlowElement>> flowElement;
    @XmlElements({
        @XmlElement(name = "startEvent", type = TStartEvent.class),
        @XmlElement(name = "boundaryEvent", type = TBoundaryEvent.class),
        @XmlElement(name = "intermediateCatchEvent", type = TIntermediateCatchEvent.class),
        @XmlElement(name = "intermediateThrowEvent", type = TIntermediateThrowEvent.class),
        @XmlElement(name = "endEvent", type = TEndEvent.class),
        @XmlElement(name = "sequenceFlow", type = TSequenceFlow.class),
        @XmlElement(name = "task", type = TTask.class),
        @XmlElement(name = "userTask", type = TUserTask.class),
        @XmlElement(name = "serviceTask", type = TServiceTask.class),
        @XmlElement(name = "manualTask", type = TManualTask.class),
        @XmlElement(name = "scriptTask", type = TScriptTask.class),
        @XmlElement(name = "businessRuleTask", type = TBusinessRuleTask.class),
        @XmlElement(name = "sendTask", type = TSendTask.class),
        @XmlElement(name = "receiveTask", type = TReceiveTask.class),
        @XmlElement(name = "complexGateway", type = TComplexGateway.class),
        @XmlElement(name = "eventBasedGateway", type = TEventBasedGateway.class),
        @XmlElement(name = "exclusiveGateway", type = TExclusiveGateway.class),
        @XmlElement(name = "inclusiveGateway", type = TInclusiveGateway.class),
        @XmlElement(name = "parallelGateway", type = TParallelGateway.class),
        @XmlElement(name = "subProcess", type = TSubProcess.class),
        @XmlElement(name = "transaction", type = TTransaction.class),
        @XmlElement(name = "adHocSubProcess", type = TAdHocSubProcess.class),
        @XmlElement(name = "callActivity", type = TCallActivity.class)
    })//JBPMN  //ELEMENT_UPGRADE
    protected List<IFlowElement> flowElement;
    //@XmlElementRef(name = "artifact", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    @XmlElements({
        @XmlElement(name = "textAnnotation", type = TTextAnnotation.class),
        @XmlElement(name = "association", type = TAssociation.class),
        @XmlElement(name = "group", type = TGroup.class)
    })//JBPMN  //ELEMENT_UPGRADE
    private List<TArtifact> artifact;// List<JAXBElement<? extends TArtifact>> artifact;

    @XmlAttribute
    protected Boolean triggeredByEvent = false;//if true then event subprocess

    void beforeMarshal(Marshaller marshaller) {
        super.beforeMarshal(marshaller);
        if (auditing != null) {
            if (auditing.getDocumentation().size() < 1) {
                auditing = null;
            }
        }
        if (monitoring != null) {
            if (monitoring.getDocumentation().size() < 1) {
                monitoring = null;
            }
        }
    }

    /**
     * Gets the value of the laneSet property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the laneSet property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLaneSet().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TLaneSet }
     *
     *
     */
    public List<TLaneSet> getLaneSet() {
        if (laneSet == null) {
            laneSet = new ArrayList<TLaneSet>();
        }
        return this.laneSet;
    }

    /**
     * Gets the value of the flowElement property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the flowElement property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlowElement().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list null null null     {@link JAXBElement }{@code <}{@link TSubChoreography }{@code >}
     * {@link JAXBElement }{@code <}{@link TComplexGateway }{@code >}
     * {@link JAXBElement }{@code <}{@link TServiceTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TScriptTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TDataObject }{@code >}
     * {@link JAXBElement }{@code <}{@link TCallActivity }{@code >}
     * {@link JAXBElement }{@code <}{@link TChoreographyTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TIntermediateThrowEvent }{@code >}
     * {@link JAXBElement }{@code <}{@link TSequenceFlow }{@code >}
     * {@link JAXBElement }{@code <}{@link TIntermediateCatchEvent }{@code >}
     * {@link JAXBElement }{@code <}{@link TUserTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TDataStoreReference }{@code >}
     * {@link JAXBElement }{@code <}{@link TSubProcess }{@code >}
     * {@link JAXBElement }{@code <}{@link TBoundaryEvent }{@code >}
     * {@link JAXBElement }{@code <}{@link TImplicitThrowEvent }{@code >}
     * {@link JAXBElement }{@code <}{@link TTransaction }{@code >}
     * {@link JAXBElement }{@code <}{@link TEndEvent }{@code >}
     * {@link JAXBElement }{@code <}{@link IFlowElement }{@code >}
     * {@link JAXBElement }{@code <}{@link TManualTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TEventBasedGateway }{@code >}
     * {@link JAXBElement }{@code <}{@link TDataObjectReference }{@code >}
     * {@link JAXBElement }{@code <}{@link TInclusiveGateway }{@code >}
     * {@link JAXBElement }{@code <}{@link TAdHocSubProcess }{@code >}
     * {@link JAXBElement }{@code <}{@link TSendTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TEvent }{@code >}
     * {@link JAXBElement }{@code <}{@link TExclusiveGateway }{@code >}
     * {@link JAXBElement }{@code <}{@link TReceiveTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TCallChoreography }{@code >}
     * {@link JAXBElement }{@code <}{@link TStartEvent }{@code >}
     * {@link JAXBElement }{@code <}{@link TParallelGateway }{@code >}
     * {@link JAXBElement }{@code <}{@link TBusinessRuleTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TTask }{@code >}
     *
     *
     */
    public List<IFlowElement> getFlowElement() {
        if (flowElement == null) {
            flowElement = new ArrayList<IFlowElement>();
        }
        return this.flowElement;
    }

    public IFlowElement getFlowElement(String id) {
        if (flowElement == null) {
            flowElement = new ArrayList<IFlowElement>();
        }
        IFlowElement flowElement_Out = null;
        for (IFlowElement flowElement_Tmp : flowElement) {
            if (flowElement_Tmp.getId().equals(id)) {
                flowElement_Out = flowElement_Tmp;
                break;
            }
        }
        return flowElement_Out;
    }

    public void removeFlowElement(IFlowElement flowElement_In) {
        if (flowElement != null) {
            this.flowElement.remove(flowElement_In);
        }
    }

    public void addFlowElement(IFlowElement flowElement_In) {
        if (flowElement == null) {
            flowElement = new ArrayList<IFlowElement>();
        }
        this.flowElement.add(flowElement_In);
    }

    /**
     * Gets the value of the artifact property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the artifact property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArtifact().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list null null null     {@link JAXBElement }{@code <}{@link TTextAnnotation }{@code >}
     * {@link JAXBElement }{@code <}{@link TGroup }{@code >}
     * {@link JAXBElement }{@code <}{@link TAssociation }{@code >}
     * {@link JAXBElement }{@code <}{@link TArtifact }{@code >}
     *
     *
     */
    public List<TArtifact> getArtifact() {
        if (artifact == null) {
            setArtifact(new ArrayList<TArtifact>());
        }
        return this.artifact;
    }

    public TArtifact getArtifact(String id) {
        if (artifact == null) {
            artifact = new ArrayList<TArtifact>();
        }
        TArtifact artifact_Out = null;
        for (TArtifact artifact_Tmp : artifact) {
            if (artifact_Tmp.getId().equals(id)) {
                artifact_Out = artifact_Tmp;
                break;
            }
        }
        return artifact_Out;
    }

    public void removeArtifact(TArtifact artifact_In) {
        if (artifact != null) {
            this.artifact.remove(artifact_In);
        }
    }

    public void addArtifact(TArtifact artifact_In) {
        if (artifact == null) {
            artifact = new ArrayList<TArtifact>();
        }
        this.artifact.add(artifact_In);
    }

    /**
     * @param artifact the artifact to set
     */
    public void setArtifact(List<TArtifact> artifact) {
        this.artifact = artifact;
    }

    public void removeBaseElement(IBaseElement baseElement_In) {
        if (baseElement_In instanceof IFlowElement) {
            this.removeFlowElement((IFlowElement) baseElement_In);
        } else if (baseElement_In instanceof TArtifact) {
            this.removeArtifact((TArtifact) baseElement_In);
        }
    }

    public void addBaseElement(IBaseElement baseElement_In) {
        if (baseElement_In instanceof IFlowElement) {
            this.addFlowElement((IFlowElement) baseElement_In);
        } else if (baseElement_In instanceof TArtifact) {
            this.addArtifact((TArtifact) baseElement_In);
        }
    }

    /**
     * Gets the value of the triggeredByEvent property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean getTriggeredByEvent() {//isTriggeredByEvent
        if (triggeredByEvent == null) {
            return false;
        } else {
            return triggeredByEvent;
        }
    }

    /**
     * Sets the value of the triggeredByEvent property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setTriggeredByEvent(Boolean value) {
        this.triggeredByEvent = value;
    }

}
