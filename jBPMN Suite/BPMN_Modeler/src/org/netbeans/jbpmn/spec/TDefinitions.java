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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import static org.netbeans.jbpmn.spec.TDefinitions.ROOTELEMENT_ORDER;
import org.netbeans.modeler.specification.model.document.IDefinitionElement;

/**
 * <p>
 * Java class for tDefinitions complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tDefinitions">
 * <complexContent>
 * <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 * <sequence>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}import"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}extension"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}rootElement"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/DI}BPMNDiagram"
 * maxOccurs="unbounded" minOccurs="0"/>
 * <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}relationship"
 * maxOccurs="unbounded" minOccurs="0"/>
 * </sequence>
 * <attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 * <attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 * <attribute name="targetNamespace" use="required"
 * type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 * <attribute name="expressionLanguage"
 * type="{http://www.w3.org/2001/XMLSchema}anyURI"
 * default="http://www.w3.org/1999/XPath" />
 * <attribute name="typeLanguage"
 * type="{http://www.w3.org/2001/XMLSchema}anyURI"
 * default="http://www.w3.org/2001/XMLSchema" />
 * <attribute name="exporter" type="{http://www.w3.org/2001/XMLSchema}string" />
 * <attribute name="exporterVersion"
 * type="{http://www.w3.org/2001/XMLSchema}string" />
 * <anyAttribute processContents='lax' namespace='##other'/>
 * </restriction>
 * </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlRootElement(name = "definitions", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL") // JBPMN
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDefinitions", propOrder = {
    "_import",
    "extension",
    "rootElement",
    "bpmnDiagram",
    "relationship"
})
public class TDefinitions implements IDefinitionElement {

    @XmlElement(name = "import")
    protected List<TImport> _import;
    protected List<TExtension> extension;
    //@XmlElementRef(name = "rootElement", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
//      @XmlElementRef(name = "rootElement", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
//    protected List<JAXBElement<? extends TRootElement>> rootElement1;
    @XmlElements({
        @XmlElement(name = "itemDefinition", type = TItemDefinition.class),
        @XmlElement(name = "resource", type = TResource.class),
        @XmlElement(name = "error", type = TError.class),
        @XmlElement(name = "escalation", type = TEscalation.class),
        @XmlElement(name = "message", type = TMessage.class),
        @XmlElement(name = "signal", type = TSignal.class),
        @XmlElement(name = "interface", type = TInterface.class),
        @XmlElement(name = "collaboration", type = TCollaboration.class),
        @XmlElement(name = "process", type = TProcess.class)
    })//JBPMN
    protected List<TRootElement> rootElement;// JBPMN //protected List<JAXBElement<? extends TRootElement>> rootElement;
    static final Comparator<TRootElement> ROOTELEMENT_ORDER = new Comparator<TRootElement>() {
        final List<Class<? extends TRootElement>> classList = Arrays.asList(
                TItemDefinition.class,
                TCategory.class,
                TEndPoint.class,
                TPartnerRole.class,
                TPartnerEntity.class,
                TGlobalConversation.class,
                TSignal.class,
                TEventDefinition.class,
                TDataStore.class,
                TError.class,
                TCorrelationProperty.class,
                TMessage.class,
                TEscalation.class,
                TResource.class,
                TInterface.class,
                TProcess.class,
                TGlobalTask.class,
                TChoreography.class);

        public int compare(TRootElement r1, TRootElement r2) {
            int i1 = classList.indexOf(r1.getClass());
            int i2 = classList.indexOf(r2.getClass());
            return i1 - i2;
        }
    };

    void beforeMarshal(Marshaller marshaller) {
        Collections.sort(rootElement, ROOTELEMENT_ORDER);
    }
    @XmlElement(name = "BPMNDiagram", namespace = "http://www.omg.org/spec/BPMN/20100524/DI")
    protected List<BPMNDiagram> bpmnDiagram;
    protected List<TRelationship> relationship;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute
    protected String name;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String targetNamespace = "http://www.omg.org/spec/BPMN/20100524/MODEL";
    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String expressionLanguage;
    @XmlAttribute
    @XmlSchemaType(name = "anyURI")
    protected String typeLanguage;
    @XmlAttribute
    protected String exporter;
    @XmlAttribute
    protected String exporterVersion;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

//    public TDefinitions() {
//         System.out.println("TDefinitions vv11" + this);
//        ElementConfigFactory.initializeObjectValue(this);
//
//    }
    /**
     * Gets the value of the import property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the import property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImport().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TImport }
     *
     *
     */
    public List<TImport> getImport() {
        if (_import == null) {
            _import = new ArrayList<TImport>();
        }
        return this._import;
    }

    /**
     * Gets the value of the extension property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the extension property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtension().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TExtension }
     *
     *
     */
    public List<TExtension> getExtension() {
        if (extension == null) {
            extension = new ArrayList<TExtension>();
        }
        return this.extension;
    }

    /**
     * Gets the value of the rootElement property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the rootElement property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRootElement().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list null null null
     * null null null null null     {@link JAXBElement }{@code <}{@link TErrorEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TGlobalManualTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TCancelEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TCorrelationProperty }{@code >}
     * {@link JAXBElement }{@code <}{@link TMessage }{@code >}
     * {@link JAXBElement }{@code <}{@link TGlobalChoreographyTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TGlobalTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TEscalation }{@code >}
     * {@link JAXBElement }{@code <}{@link TGlobalBusinessRuleTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TRootElement }{@code >}
     * {@link JAXBElement }{@code <}{@link TGlobalScriptTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TDataStore }{@code >}
     * {@link JAXBElement }{@code <}{@link TLinkEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TItemDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TCollaboration }{@code >}
     * {@link JAXBElement }{@code <}{@link TTimerEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TInterface }{@code >}
     * {@link JAXBElement }{@code <}{@link TEndPoint }{@code >}
     * {@link JAXBElement }{@code <}{@link TResource }{@code >}
     * {@link JAXBElement }{@code <}{@link TError }{@code >}
     * {@link JAXBElement }{@code <}{@link TPartnerRole }{@code >}
     * {@link JAXBElement }{@code <}{@link TTerminateEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TConditionalEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TEscalationEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TSignal }{@code >}
     * {@link JAXBElement }{@code <}{@link TPartnerEntity }{@code >}
     * {@link JAXBElement }{@code <}{@link TGlobalConversation }{@code >}
     * {@link JAXBElement }{@code <}{@link TCompensateEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TChoreography }{@code >}
     * {@link JAXBElement }{@code <}{@link TGlobalUserTask }{@code >}
     * {@link JAXBElement }{@code <}{@link TMessageEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TSignalEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TCategory }{@code >}
     * {@link JAXBElement }{@code <}{@link TEventDefinition }{@code >}
     * {@link JAXBElement }{@code <}{@link TProcess }{@code >}
     *
     *
     */
    public List<TRootElement> getRootElement() {
        if (rootElement == null) {
            rootElement = new ArrayList<TRootElement>();
        }
        return this.rootElement;
    }

    public TRootElement getRootElement(String id) {
        if (rootElement != null) {
            for (TRootElement rootElement_Tmp : rootElement) {
                if (rootElement_Tmp.getId().equals(id)) {
                    return rootElement_Tmp;
                }
            }
        }
        return null;
    }

    public TRootElement getRootElement(String id, Class<? extends TRootElement> clazz) {
        if (rootElement != null) {
            for (TRootElement rootElement_Tmp : rootElement) {
                if (rootElement_Tmp.getId().equals(id) && rootElement_Tmp.getClass() == clazz) {
                    return rootElement_Tmp;
                }
            }
        }
        return null;
    }

    public void addRootElement(TRootElement rootElement_In) {
        if (rootElement == null) {
            rootElement = new ArrayList<TRootElement>();
        }
        this.rootElement.add(rootElement_In);
    }

    public boolean isItemDefinitionExist(TItemDefinition itemDefinition) {
        if (rootElement == null) {
            rootElement = new ArrayList<TRootElement>();
        }
        if (itemDefinition.getStructureRef() == null || itemDefinition.getStructureRef().isEmpty()) {
            return false;
        }
        TItemDefinition itemDefinition_Exist = null;

        for (TRootElement rootElement_Tmp : rootElement) {
            if (rootElement_Tmp instanceof TItemDefinition
                    && ((TItemDefinition) rootElement_Tmp).getStructureRef().equals(itemDefinition.getStructureRef())
                    && ((TItemDefinition) rootElement_Tmp).isIsCollection() == itemDefinition.isIsCollection()) {
                itemDefinition_Exist = (TItemDefinition) rootElement_Tmp;
            }
        }
        if (itemDefinition_Exist == null) {
//            this.rootElement.add(itemDefinition);
            return false;
        } else {
            return true;
        }
    }

    public TItemDefinition addItemDefinition(TItemDefinition itemDefinition) {
        if (rootElement == null) {
            rootElement = new ArrayList<TRootElement>();
        }
        if (itemDefinition.getStructureRef() == null || itemDefinition.getStructureRef().isEmpty()) {
            return null;
        }
        TItemDefinition itemDefinition_Exist = null;

        for (TRootElement rootElement_Tmp : rootElement) {
            if (rootElement_Tmp instanceof TItemDefinition
                    && ((TItemDefinition) rootElement_Tmp).getStructureRef().equals(itemDefinition.getStructureRef())
                    && ((TItemDefinition) rootElement_Tmp).isIsCollection() == itemDefinition.isIsCollection()) {
                itemDefinition_Exist = (TItemDefinition) rootElement_Tmp;
            }
        }
        if (itemDefinition_Exist == null) {
            this.rootElement.add(itemDefinition);
            return itemDefinition;
        } else {
            return itemDefinition_Exist;
        }
    }

    public void removeRootElement(TRootElement rootElement_In) {
        if (rootElement == null) {
            rootElement = new ArrayList<TRootElement>();
        } else {
            this.rootElement.remove(rootElement_In);
        }
    }

    /**
     * Gets the value of the bpmnDiagram property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the bpmnDiagram property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBPMNDiagram().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BPMNDiagram }
     *
     *
     */
    public List<BPMNDiagram> getBPMNDiagram() {
        if (bpmnDiagram == null) {
            bpmnDiagram = new ArrayList<BPMNDiagram>();
        }
        return this.bpmnDiagram;
    }

    public void addBPMNDiagram(BPMNDiagram bpmnDiagram_In) {
        if (bpmnDiagram == null) {
            bpmnDiagram = new ArrayList<BPMNDiagram>();
        }
        this.bpmnDiagram.add(bpmnDiagram_In);
    }

    /**
     * Gets the value of the relationship property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the relationship property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelationship().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TRelationship }
     *
     *
     */
    public List<TRelationship> getRelationship() {
        if (relationship == null) {
            relationship = new ArrayList<TRelationship>();
        }
        return this.relationship;
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
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the targetNamespace property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTargetNamespace() {
        return targetNamespace;

    }

    /**
     * Sets the value of the targetNamespace property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTargetNamespace(String value) {
        this.targetNamespace = value;
    }

    /**
     * Gets the value of the expressionLanguage property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getExpressionLanguage() {
        if (expressionLanguage == null) {
            return "http://www.w3.org/1999/XPath";
        } else {
            return expressionLanguage;
        }
    }

    /**
     * Sets the value of the expressionLanguage property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setExpressionLanguage(String value) {
        this.expressionLanguage = value;
    }

    /**
     * Gets the value of the typeLanguage property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTypeLanguage() {
        if (typeLanguage == null) {
            return "http://www.w3.org/2001/XMLSchema";
        } else {
            return typeLanguage;
        }
    }

    /**
     * Sets the value of the typeLanguage property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTypeLanguage(String value) {
        this.typeLanguage = value;
    }

    /**
     * Gets the value of the exporter property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getExporter() {
        return exporter;
    }

    /**
     * Sets the value of the exporter property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setExporter(String value) {
        this.exporter = value;
    }

    /**
     * Gets the value of the exporterVersion property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getExporterVersion() {
        return exporterVersion;
    }

    /**
     * Sets the value of the exporterVersion property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setExporterVersion(String value) {
        this.exporterVersion = value;
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

    public TProcess getProcess(String id) { //JBPMN
        TProcess process = null;
        if (rootElement != null) {
            for (TRootElement element : rootElement) {
                if (element instanceof TProcess) {
                    if (((TProcess) element).getId().equals(id)) {
                        process = ((TProcess) element);
                        break;
                    }
                }
            }
        }
        return process;
    }

//    public String getItemDefinitionStructure(QName itemSubjectRefQname) {
//        String itemSubjectRef = itemSubjectRefQname == null ? null
//                : itemSubjectRefQname.getLocalPart();
//        TItemDefinition itemDefinition = itemSubjectRef == null ? null
//                : (TItemDefinition) this.getRootElement(itemSubjectRef, TItemDefinition.class);
////                    QName standardType_Qname = itemDefinition == null ? null
////                            : itemDefinition.getStructureRef();
////                    String standardType = standardType_Qname == null ? null : standardType_Qname.getLocalPart();
//        String standardType = itemDefinition == null ? null : itemDefinition.getStructureRef();
//
//        return standardType != null ? standardType : "";
//    }
    public String getItemDefinitionStructure(String itemSubjectRef) {
        TItemDefinition itemDefinition = itemSubjectRef == null ? null
                : (TItemDefinition) this.getRootElement(itemSubjectRef, TItemDefinition.class);
        String standardType = itemDefinition == null ? null : itemDefinition.getStructureRef();

        return standardType != null ? standardType : "";
    }

    public TItemDefinition findItemDefinition(String structureRef) {
        for (TRootElement rootElement_Local : this.getRootElement()) {
            if (rootElement_Local instanceof TItemDefinition) {
                TItemDefinition itemDefinition = (TItemDefinition) rootElement_Local;
                if (itemDefinition.getStructureRef().equals(structureRef)) {
                    return itemDefinition;
                }
            }
        }
        return null;
    }
//     public TResource findResource(String resourceRef) {
//        for (TRootElement rootElement_Local : this.getRootElement()) {
//            if (rootElement_Local instanceof TResource) {
//                TResource resource = (TResource) rootElement_Local;
//                if (resource.getId().equals(structureRef)) {
//                    return resource;
//                }
//            }
//        }
//        return null;
//    }
}
