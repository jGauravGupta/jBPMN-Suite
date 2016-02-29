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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;

/**
 * <p>
 * Java class for tBaseElement complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tBaseElement">
 * <complexContent>
 * <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 * <sequence>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}documentation"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}extensionElements"
 * minOccurs="0"/>
 * </sequence>
 * <attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 * <anyAttribute processContents='lax' namespace='##other'/>
 * </restriction>
 * </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tBaseElement", propOrder = {
    "documentation",
    "extensionElements"
})
@XmlSeeAlso({
    TOperation.class,
    TCorrelationSubscription.class,
    TResourceAssignmentExpression.class,
    TMonitoring.class,
    TParticipant.class,
    TParticipantMultiplicity.class,
    TInputSet.class,
    TOutputSet.class,
    TRelationship.class,
    TAssignment.class,
    TMessageFlow.class,
    TInputOutputBinding.class,
    TResourceParameter.class,
    TProperty.class,
    TDataInput.class,
    TComplexBehaviorDefinition.class,
    TMessageFlowAssociation.class,
    TConversationLink.class,
    TDataAssociation.class,
    TParticipantAssociation.class,
    TCategoryValue.class,
    TLoopCharacteristics.class,
    TCorrelationPropertyBinding.class,
    TResourceRole.class,
    TConversationNode.class,
    TLane.class,
    TCorrelationPropertyRetrievalExpression.class,
    TDataState.class,
    TLaneSet.class,
    TConversationAssociation.class,
    TInputOutputSpecification.class,
    TCorrelationKey.class,
    TResourceParameterBinding.class,
    TRendering.class,
    TFlowElement.class,
    TRootElement.class,
    TAuditing.class,
    TArtifact.class,
    TDataOutput.class
})
public abstract class TBaseElement implements IBaseElement {

    protected List<TDocumentation> documentation;
    protected TExtensionElements extensionElements;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

//    public TBaseElement() {
//        ElementConfigFactory.initializeObjectValue(this);
//    }
    /**
     * Gets the value of the documentation property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the documentation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TDocumentation }
     *
     *
     */
    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {//XML to Object
//        System.out.println("BI afterUnmarshal value : " + this.getId() );

    }

    void beforeMarshal(Marshaller marshaller) {

    }

    public List<TDocumentation> getDocumentation() {
        if (documentation == null) {
            documentation = new ArrayList<TDocumentation>();
        }
        return this.documentation;
    }

    public String getDocumentationEmbedded() {
        if (documentation == null) {
            documentation = new ArrayList<TDocumentation>();
        }
        if (!this.documentation.isEmpty()) {
            String content = this.documentation.get(0).getContent();
            if (content != null && !content.isEmpty()) {
                return content;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public void setDocumentationEmbedded(String documentationEmbedded) {
        if (documentationEmbedded == null || documentationEmbedded.isEmpty()) {
            documentation = null;
        } else {
            if (documentation == null) {
                documentation = new ArrayList<TDocumentation>();
            }
            TDocumentation documentation1;
            if (documentation.isEmpty()) {
                documentation1 = new TDocumentation();
                documentation1.setId(NBModelerUtil.getAutoGeneratedStringId());
                documentation.add(documentation1);
            }
            documentation.get(0).setContent(documentationEmbedded);
        }
    }

    /**
     * Gets the value of the extensionElements property.
     *
     * @return possible object is {@link TExtensionElements }
     *
     */
    public TExtensionElements getExtensionElements() {
        return extensionElements;
    }

    /**
     * Sets the value of the extensionElements property.
     *
     * @param value allowed object is {@link TExtensionElements }
     *
     */
    public void setExtensionElements(TExtensionElements value) {
        this.extensionElements = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed
     * property on this class.
     *
     * <p>
     * the map is keyed by the name of the attribute and the value is the string
     * value of the attribute.
     *
     * the map returned by this method is live, and you can add new attribute by
     * updating the map directly. Because of this design, there's no setter.
     *
     *
     * @return always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    @Override
    public Map<String, String> getCustomAttributes() {
        Map<String, String> otherAttributesEmbedded = new LinkedHashMap<String, String>();

        Iterator<Entry<QName, String>> otherAttributes_Itr = otherAttributes.entrySet().iterator();
        while (otherAttributes_Itr.hasNext()) {
            Entry<QName, String> entry = otherAttributes_Itr.next();
            otherAttributesEmbedded.put(entry.getKey().getLocalPart(), entry.getValue());
        }

        return otherAttributesEmbedded;
    }

    @Override
    public void setCustomAttributes(Map<String, String> otherAttributesEmbedded) {
        Iterator<Entry<String, String>> otherAttributesEmbedded_Itr = otherAttributesEmbedded.entrySet().iterator();
        otherAttributes = new HashMap<QName, String>();
        while (otherAttributesEmbedded_Itr.hasNext()) {
            Entry<String, String> entry = otherAttributesEmbedded_Itr.next();
            otherAttributes.put(new QName("", entry.getKey(), ""), entry.getValue());
        }
    }
}
