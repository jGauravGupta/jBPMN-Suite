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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.netbeans.jbpmn.spec.extend.ResourceRoleHandler;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;

/**
 * <p>
 * Java class for tProcess complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tProcess">
 * <complexContent>
 * <extension
 * base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tCallableElement">
 * <sequence>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}auditing"
 * minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}monitoring"
 * minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}property"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}laneSet"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}flowElement"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}artifact"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}resourceRole"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element
 * ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}correlationSubscription"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element name="supports" type="{http://www.w3.org/2001/XMLSchema}QName"
 * maxOccurs="unbounded" minOccurs="0"/>
 * </sequence>
 * <attribute name="processType"
 * type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tProcessType"
 * default="None" />
 * <attribute name="isClosed" type="{http://www.w3.org/2001/XMLSchema}boolean"
 * default="false" />
 * <attribute name="isExecutable"
 * type="{http://www.w3.org/2001/XMLSchema}boolean" />
 * <attribute name="definitionalCollaborationRef"
 * type="{http://www.w3.org/2001/XMLSchema}QName" />
 * <anyAttribute processContents='lax' namespace='##other'/>
 * </extension>
 * </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlRootElement(name = "process")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tProcess", propOrder = {
    "auditing",
    "monitoring",
    "property",
    "laneSet",
    "flowElement",
    "artifact",
    "resourceRole",
    "correlationSubscription",
    "supports"
})
public class TProcess
        extends TCallableElement implements ResourceRoleHandler {

//   @XmlElement(name="property")
//    private List<TProperty> property = new ArrayList<TProperty>();
//      @XmlElement(name="property1")
//      private List<TProperty1> property1 = new ArrayList<TProperty1>();
//       @XmlElement(name="property12")
//       TProperty1 property12;
    protected TAuditing auditing;
    protected TMonitoring monitoring;
//    @XmlElementRef(name = "property", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    @XmlElement(name = "property")
    protected List<TProperty> property;
    protected List<TLaneSet> laneSet;
    //@XmlElementRef(name = "flowElement", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
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
    protected List<TFlowElement> flowElement;
    //@XmlElementRef(name = "artifact", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    @XmlElements({
        @XmlElement(name = "textAnnotation", type = TTextAnnotation.class),
        @XmlElement(name = "association", type = TAssociation.class),
        @XmlElement(name = "group", type = TGroup.class)
    })//JBPMN  //ELEMENT_UPGRADE
    private List<TArtifact> artifact;// List<JAXBElement<? extends TArtifact>> artifact;
    @XmlElement(name = "resourceRole")//, namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    protected List<? extends TResourceRole> resourceRole;//protected List<TResourceRole> resourceRole;
    protected List<TCorrelationSubscription> correlationSubscription;
    protected List<QName> supports;
    @XmlAttribute
    protected TProcessType processType;
    @XmlAttribute
    protected Boolean isClosed = false;
    @XmlAttribute
    protected Boolean isExecutable;
    @XmlAttribute
    protected QName definitionalCollaborationRef;

//    /*JBPMN isRoot  */
//   //@XmlTransient
//    @XmlAttribute(name="root")
//   private Boolean isRoot;
//
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
     * Gets the value of the auditing property.
     *
     * @return possible object is {@link TAuditing }
     *
     */
    public TAuditing getAuditing() {
        return auditing;
    }

    /**
     * Sets the value of the auditing property.
     *
     * @param value allowed object is {@link TAuditing }
     *
     */
    public void setAuditing(TAuditing value) {
        this.auditing = value;
    }

    public String getAuditingEmbedded() {
        if (auditing == null) {
            auditing = new TAuditing();
        }
        return auditing.getDocumentationEmbedded();
    }

    public void setAuditingEmbedded(String value) {
        if (auditing == null) {
            auditing = new TAuditing();
            auditing.setId(NBModelerUtil.getAutoGeneratedStringId());
        }
        auditing.setDocumentationEmbedded(value);

    }

    /**
     * Gets the value of the monitoring property.
     *
     * @return possible object is {@link TMonitoring }
     *
     */
    public TMonitoring getMonitoring() {
        return monitoring;
    }

    /**
     * Sets the value of the monitoring property.
     *
     * @param value allowed object is {@link TMonitoring }
     *
     */
    public void setMonitoring(TMonitoring value) {
        this.monitoring = value;
    }

    public String getMonitoringEmbedded() {
        if (monitoring == null) {
            monitoring = new TMonitoring();
        }
        return monitoring.getDocumentationEmbedded();
    }

    public void setMonitoringEmbedded(String value) {
        if (monitoring == null) {
            monitoring = new TMonitoring();
            monitoring.setId(NBModelerUtil.getAutoGeneratedStringId());
        }
        monitoring.setDocumentationEmbedded(value);

    }

    /**
     * Gets the value of the property property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the property property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TProperty }
     *
     *
     */
    public List<TProperty> getProperty() {
        if (property == null) {
            property = new ArrayList<TProperty>();
        }
        return this.property;
    }

    public TProperty getProperty(String propertyId) {
        if (property == null) {
            return null;
        }
        for (TProperty property_Tmp : property) {
            if (property_Tmp.getId().equals(propertyId)) {
                return property_Tmp;
            }
        }
        return null;
    }

    public TProperty findProperty(String propertyName) {
        if (property == null) {
            return null;
        }
        for (TProperty property_Tmp : property) {
            if (property_Tmp.getName().equals(propertyName)) {
                return property_Tmp;
            }
        }
        return null;
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
     * Objects of the following type(s) are allowed in the list null null null
     * null null null     {@link JAXBElement }{@code <}{@link TSubChoreography }{@code >}
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
     * {@link JAXBElement }{@code <}{@link TFlowElement }{@code >}
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
    public List<TFlowElement> getFlowElement() {
        if (flowElement == null) {
            flowElement = new ArrayList<TFlowElement>();
        }
        return this.flowElement;
    }

    public TFlowElement getFlowElement(String id) {
        if (flowElement == null) {
            flowElement = new ArrayList<TFlowElement>();
        }
        TFlowElement flowElement_Out = null;
        for (TFlowElement flowElement_Tmp : flowElement) {
            if (flowElement_Tmp.getId().equals(id)) {
                flowElement_Out = flowElement_Tmp;
                break;
            }
        }
        return flowElement_Out;
    }

    public TFlowElement getFlowElement(String id, Class<? extends TFlowElement> clazz) {
        if (flowElement == null) {
            flowElement = new ArrayList<TFlowElement>();
        }
        TFlowElement flowElement_Out = null;
        for (TFlowElement flowElement_Tmp : flowElement) {
            if (flowElement_Tmp.getId().equals(id) && flowElement_Tmp.getClass() == clazz) {
                flowElement_Out = flowElement_Tmp;
                break;
            }
        }
        return flowElement_Out;
    }

    public void removeFlowElement(TFlowElement flowElement_In) {
        if (flowElement != null) {
            this.flowElement.remove(flowElement_In);
        }
    }

    public void addFlowElement(TFlowElement flowElement_In) {
        if (flowElement == null) {
            flowElement = new ArrayList<TFlowElement>();
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
     * Objects of the following type(s) are allowed in the list null null null
     * null null null     {@link JAXBElement }{@code <}{@link TTextAnnotation }{@code >}
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
     * Gets the value of the resourceRole property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the resourceRole property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourceRole().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list null null null
     * null null null     {@link JAXBElement }{@code <}{@link TPerformer }{@code >}
     * {@link JAXBElement }{@code <}{@link TResourceRole }{@code >}
     * {@link JAXBElement }{@code <}{@link THumanPerformer }{@code >}
     * {@link JAXBElement }{@code <}{@link TPotentialOwner }{@code >}
     *
     *
     */
    public List<? extends TResourceRole> getResourceRole() {
        if (resourceRole == null) {
            resourceRole = new ArrayList<TResourceRole>();
        }
        return this.resourceRole;
    }

    /**
     * @param resourceRole the resourceRole to set
     */
    public void setResourceRole(List<? extends TResourceRole> resourceRole) {
        this.resourceRole = resourceRole;
    }

    /**
     * Gets the value of the correlationSubscription property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the correlationSubscription property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCorrelationSubscription().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCorrelationSubscription }
     *
     *
     */
    public List<TCorrelationSubscription> getCorrelationSubscription() {
        if (correlationSubscription == null) {
            correlationSubscription = new ArrayList<TCorrelationSubscription>();
        }
        return this.correlationSubscription;
    }

    /**
     * Gets the value of the supports property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the supports property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupports().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link QName }
     *
     *
     */
    public List<QName> getSupports() {
        if (supports == null) {
            supports = new ArrayList<QName>();
        }
        return this.supports;
    }

    /**
     * Gets the value of the processType property.
     *
     * @return possible object is {@link TProcessType }
     *
     */
    public TProcessType getProcessType() {
        if (processType == null) {
            return TProcessType.NONE;
        } else {
            return processType;
        }
    }

    /**
     * Sets the value of the processType property.
     *
     * @param value allowed object is {@link TProcessType }
     *
     */
    public void setProcessType(TProcessType value) {
        this.processType = value;
    }

    /**
     * Gets the value of the isClosed property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean getIsClosed() { // JBPMN isIsClosed()
        if (isClosed == null) {
            return false;
        } else {
            return isClosed;
        }
    }

    /**
     * Sets the value of the isClosed property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setIsClosed(Boolean value) {
        this.isClosed = value;
    }

    /**
     * Gets the value of the isExecutable property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getIsExecutable() { // JBPMN isIsExecutable()
        return isExecutable;
    }

    /**
     * Sets the value of the isExecutable property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setIsExecutable(Boolean value) {
        this.isExecutable = value;
    }

    /**
     * Gets the value of the definitionalCollaborationRef property.
     *
     * @return possible object is {@link QName }
     *
     */
    public QName getDefinitionalCollaborationRef() {
        return definitionalCollaborationRef;
    }

    /**
     * Sets the value of the definitionalCollaborationRef property.
     *
     * @param value allowed object is {@link QName }
     *
     */
    public void setDefinitionalCollaborationRef(QName value) {
        this.definitionalCollaborationRef = value;
    }

    /**
     * @param artifact the artifact to set
     */
    public void setArtifact(List<TArtifact> artifact) {
        this.artifact = artifact;
    }

    @Override
    public void removeBaseElement(IBaseElement baseElement_In) {
        if (baseElement_In instanceof TFlowElement) {
            this.removeFlowElement((TFlowElement) baseElement_In);
        } else if (baseElement_In instanceof TArtifact) {
            this.removeArtifact((TArtifact) baseElement_In);
        }
    }

    @Override
    public void addBaseElement(IBaseElement baseElement_In) {
        if (baseElement_In instanceof TFlowElement) {
            this.addFlowElement((TFlowElement) baseElement_In);
        } else if (baseElement_In instanceof TArtifact) {
            this.addArtifact((TArtifact) baseElement_In);
        }
    }

    /**
     * @param property the property to set
     */
    public void setProperty(List<TProperty> property) {
        this.property = property;
    }

}
