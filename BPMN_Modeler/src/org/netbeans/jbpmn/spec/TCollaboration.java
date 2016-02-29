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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;


/**
 * <p>Java class for tCollaboration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="tCollaboration">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tRootElement">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}participant" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}messageFlow" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}artifact" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}conversationNode" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}conversationAssociation" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}participantAssociation" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}messageFlowAssociation" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}correlationKey" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="choreographyRef" type="{http://www.w3.org/2001/XMLSchema}QName" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}conversationLink" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       <attribute name="isClosed" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCollaboration", propOrder = {
    "participant",
    "messageFlow",
    "artifact",
    "conversationNode",
    "conversationAssociation",
    "participantAssociation",
    "messageFlowAssociation",
    "correlationKey",
    "choreographyRef",
    "conversationLink"
})
@XmlSeeAlso({
    TGlobalConversation.class,
    TChoreography.class
})
public class TCollaboration
    extends TRootElement
{

    protected List<TParticipant> participant;
     @XmlElement(name = "messageFlow")
    protected List<TMessageFlow> messageFlow;
    //@XmlElementRef(name = "artifact", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    @XmlElements({
        @XmlElement(name = "textAnnotation", type = TTextAnnotation.class),
        @XmlElement(name = "association", type = TAssociation.class),
        @XmlElement(name = "group", type = TGroup.class)
    })//JBPMN  //ELEMENT_UPGRADE
   protected List<TArtifact> artifact;//protected List<JAXBElement<? extends TArtifact>> artifact;
    //@XmlElementRef(name = "conversationNode", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
    
     @XmlElements({
        @XmlElement(name = "conversation", type = TConversation.class),
        @XmlElement(name = "callConversation", type = TCallConversation.class),
        @XmlElement(name = "subConversation", type = TSubConversation.class)
    })//JBPMN  //ELEMENT_UPGRADE
    protected List<TConversationNode> conversationNode;// protected List<JAXBElement<? extends TConversationNode>> conversationNode;
    protected List<TConversationAssociation> conversationAssociation;
    protected List<TParticipantAssociation> participantAssociation;
    protected List<TMessageFlowAssociation> messageFlowAssociation;
    protected List<TCorrelationKey> correlationKey;
    protected List<QName> choreographyRef;
     @XmlElement(name = "conversationLink")
    protected List<TConversationLink> conversationLink;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected Boolean isClosed = false;

    /**
     * Gets the value of the participant property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participant property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipant().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TParticipant }
     * 
     * 
     */
    public List<TParticipant> getParticipant() {
        if (participant == null) {
            participant = new ArrayList<TParticipant>();
        }
        return this.participant;
    }
    

    public TParticipant getParticipant(String id) {
        if (participant == null) {
            participant = new ArrayList<TParticipant>();
        }
        TParticipant participant_Out = null;
        for (TParticipant participant_Tmp : participant) {
            if (participant_Tmp.getId().equals(id)) {
                participant_Out = participant_Tmp;
                break;
            }
        }
        return participant_Out;
    }

    public void removeParticipant(TParticipant participant_In) {
        if (participant != null) {
            this.participant.remove(participant_In);
        }
    }

    public void addParticipant(TParticipant participant_In) {
        if (participant == null) {
            participant = new ArrayList<TParticipant>();
        }
        this.participant.add(participant_In);
    }

    /**
     * Gets the value of the messageFlow property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messageFlow property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessageFlow().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TMessageFlow }
     * 
     * 
     */
    public List<TMessageFlow> getMessageFlow() {
        if (messageFlow == null) {
            messageFlow = new ArrayList<TMessageFlow>();
        }
        return this.messageFlow;
    }
 public TMessageFlow getMessageFlow(String id) {
        if (messageFlow == null) {
            messageFlow = new ArrayList<TMessageFlow>();
        }
        TMessageFlow messageFlow_Out = null;
        for (TMessageFlow messageFlow_Tmp : messageFlow) {
            if (messageFlow_Tmp.getId().equals(id)) {
                messageFlow_Out = messageFlow_Tmp;
                break;
            }
        }
        return messageFlow_Out;
    }

    public void removeMessageFlow(TMessageFlow messageFlow_In) {
        if (messageFlow != null) {
            this.messageFlow.remove(messageFlow_In);
        }
    }

    public void addMessageFlow(TMessageFlow messageFlow_In) {
        if (messageFlow == null) {
            messageFlow = new ArrayList<TMessageFlow>();
        }
        this.messageFlow.add(messageFlow_In);
    }
    /**
     * Gets the value of the artifact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the artifact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArtifact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TTextAnnotation }{@code >}
     * {@link JAXBElement }{@code <}{@link TGroup }{@code >}
     * {@link JAXBElement }{@code <}{@link TAssociation }{@code >}
     * {@link JAXBElement }{@code <}{@link TArtifact }{@code >}
     * 
     * 
     */
    public List<TArtifact> getArtifact() {
        if (artifact == null) {
            artifact = new ArrayList<TArtifact>();
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
     * Gets the value of the conversationNode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conversationNode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConversationNode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TConversationNode }{@code >}
     * {@link JAXBElement }{@code <}{@link TConversation }{@code >}
     * {@link JAXBElement }{@code <}{@link TCallConversation }{@code >}
     * {@link JAXBElement }{@code <}{@link TSubConversation }{@code >}
     * 
     * 
     */
    public List<TConversationNode> getConversationNode() {
        if (conversationNode == null) {
            conversationNode = new ArrayList<TConversationNode>();
        }
        return this.conversationNode;
    }
    public TConversationNode getConversationNode(String id) {
        if (conversationNode == null) {
            conversationNode = new ArrayList<TConversationNode>();
        }
        TConversationNode conversationNode_Out = null;
        for (TConversationNode conversationNode_Tmp : conversationNode) {
            if (conversationNode_Tmp.getId().equals(id)) {
                conversationNode_Out = conversationNode_Tmp;
                break;
            }
        }
        return conversationNode_Out;
    }

    public void removeConversationNode(TConversationNode conversationNode_In) {
        if (conversationNode != null) {
            this.conversationNode.remove(conversationNode_In);
        }
    }

    public void addConversationNode(TConversationNode conversationNode_In) {
        if (conversationNode == null) {
            conversationNode = new ArrayList<TConversationNode>();
        }
        this.conversationNode.add(conversationNode_In);
    }
    /**
     * Gets the value of the conversationAssociation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conversationAssociation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConversationAssociation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TConversationAssociation }
     * 
     * 
     */
    public List<TConversationAssociation> getConversationAssociation() {
        if (conversationAssociation == null) {
            conversationAssociation = new ArrayList<TConversationAssociation>();
        }
        return this.conversationAssociation;
    }

    /**
     * Gets the value of the participantAssociation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participantAssociation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipantAssociation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TParticipantAssociation }
     * 
     * 
     */
    public List<TParticipantAssociation> getParticipantAssociation() {
        if (participantAssociation == null) {
            participantAssociation = new ArrayList<TParticipantAssociation>();
        }
        return this.participantAssociation;
    }

    /**
     * Gets the value of the messageFlowAssociation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messageFlowAssociation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessageFlowAssociation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TMessageFlowAssociation }
     * 
     * 
     */
    public List<TMessageFlowAssociation> getMessageFlowAssociation() {
        if (messageFlowAssociation == null) {
            messageFlowAssociation = new ArrayList<TMessageFlowAssociation>();
        }
        return this.messageFlowAssociation;
    }

    /**
     * Gets the value of the correlationKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the correlationKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCorrelationKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCorrelationKey }
     * 
     * 
     */
    public List<TCorrelationKey> getCorrelationKey() {
        if (correlationKey == null) {
            correlationKey = new ArrayList<TCorrelationKey>();
        }
        return this.correlationKey;
    }

    /**
     * Gets the value of the choreographyRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the choreographyRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChoreographyRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QName }
     * 
     * 
     */
    public List<QName> getChoreographyRef() {
        if (choreographyRef == null) {
            choreographyRef = new ArrayList<QName>();
        }
        return this.choreographyRef;
    }

    /**
     * Gets the value of the conversationLink property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conversationLink property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConversationLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TConversationLink }
     * 
     * 
     */
    public List<TConversationLink> getConversationLink() {
        if (conversationLink == null) {
            conversationLink = new ArrayList<TConversationLink>();
        }
        return this.conversationLink;
    }
   
    public TConversationLink getConversationLink(String id) {
        if (conversationLink == null) {
            conversationLink = new ArrayList<TConversationLink>();
        }
        TConversationLink conversationLink_Out = null;
        for (TConversationLink conversationLink_Tmp : conversationLink) {
            if (conversationLink_Tmp.getId().equals(id)) {
                conversationLink_Out = conversationLink_Tmp;
                break;
            }
        }
        return conversationLink_Out;
    }

    public void removeConversationLink(TConversationLink conversationLink_In) {
        if (conversationLink != null) {
            this.conversationLink.remove(conversationLink_In);
        }
    }

    public void addConversationLink(TConversationLink conversationLink_In) {
        if (conversationLink == null) {
            conversationLink = new ArrayList<TConversationLink>();
        }
        this.conversationLink.add(conversationLink_In);
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
     * Gets the value of the isClosed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean getIsClosed() {
        if (isClosed == null) {
            return false;
        } else {
            return isClosed;
        }
    }

    /**
     * Sets the value of the isClosed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsClosed(Boolean value) {
        this.isClosed = value;
    }
    
      public void removeBaseElement(IBaseElement baseElement_In) {
        if (baseElement_In instanceof TParticipant) {
            this.removeParticipant((TParticipant)baseElement_In);
        } else if (baseElement_In instanceof TConversationNode) {
            this.removeConversationNode((TConversationNode)baseElement_In);
        } else if (baseElement_In instanceof TArtifact) {
            this.removeArtifact((TArtifact)baseElement_In);
        }else if (baseElement_In instanceof TConversationLink) {
            this.removeConversationLink((TConversationLink)baseElement_In);
        }else if (baseElement_In instanceof TMessageFlow) {
            this.removeMessageFlow((TMessageFlow)baseElement_In);
        }
    }
    public void addBaseElement(IBaseElement baseElement_In) {
        if (baseElement_In instanceof TParticipant) {
            this.addParticipant((TParticipant)baseElement_In);
        }else if (baseElement_In instanceof TConversationNode) {
            this.addConversationNode((TConversationNode)baseElement_In);
        } else if (baseElement_In instanceof TArtifact) {
            this.addArtifact((TArtifact)baseElement_In);
        }else if (baseElement_In instanceof TConversationLink) {
            this.addConversationLink((TConversationLink)baseElement_In);
        }else if (baseElement_In instanceof TMessageFlow) {
            this.addMessageFlow((TMessageFlow)baseElement_In);
        }
    }

}
