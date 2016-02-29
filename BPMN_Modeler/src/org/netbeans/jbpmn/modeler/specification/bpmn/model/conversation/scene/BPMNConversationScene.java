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
package org.netbeans.jbpmn.modeler.specification.bpmn.model.conversation.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.jbpmn.core.widget.ArtifactWidget;
import org.netbeans.jbpmn.core.widget.AssociationWidget;
import org.netbeans.jbpmn.core.widget.BaseElementWidget;
import org.netbeans.jbpmn.core.widget.FlowNodeWidget;
import org.netbeans.jbpmn.core.widget.GroupWidget;
import org.netbeans.jbpmn.core.widget.TextAnnotationWidget;
import org.netbeans.jbpmn.core.widget.conversation.CollaborationEdgeWidget;
import org.netbeans.jbpmn.core.widget.conversation.CollaborationElementWidget;
import org.netbeans.jbpmn.core.widget.conversation.CollaborationNodeWidget;
import org.netbeans.jbpmn.core.widget.conversation.ConversationLinkWidget;
import org.netbeans.jbpmn.core.widget.conversation.ConversationNodeWidget;
import org.netbeans.jbpmn.core.widget.conversation.ConversationWidget;
import org.netbeans.jbpmn.core.widget.conversation.MessageFlowWidget;
import org.netbeans.jbpmn.core.widget.conversation.ParticipantWidget;
import org.netbeans.jbpmn.core.widget.conversation.SubConversationWidget;
import org.netbeans.jbpmn.spec.TArtifact;
import org.netbeans.jbpmn.spec.TAssociation;
import org.netbeans.jbpmn.spec.TCollaboration;
import org.netbeans.jbpmn.spec.TConversation;
import org.netbeans.jbpmn.spec.TConversationLink;
import org.netbeans.jbpmn.spec.TFlowNode;
import org.netbeans.jbpmn.spec.TGroup;
import org.netbeans.jbpmn.spec.TMessageFlow;
import org.netbeans.jbpmn.spec.TParticipant;
import org.netbeans.jbpmn.spec.TSubConversation;
import org.netbeans.jbpmn.spec.TTextAnnotation;
import org.netbeans.jbpmn.spec.extend.TCollaborationNode;
import org.netbeans.modeler.config.element.ElementConfigFactory;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.exception.InvalidElmentException;
import org.netbeans.modeler.core.scene.ModelerScene;
import org.netbeans.modeler.specification.model.document.IDefinitionElement;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.specification.model.document.widget.IArtifactNodeWidget;
import org.netbeans.modeler.specification.model.document.widget.IBaseElementWidget;
import org.netbeans.modeler.specification.model.document.widget.IFlowNodeWidget;
import org.netbeans.modeler.widget.edge.EdgeWidget;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;

public class BPMNConversationScene extends ModelerScene {

    public BPMNConversationScene() {
        this.addPropertyChangeListener("name", new PropertyChangeListener<String>() {
            @Override
            public void changePerformed(String value) {
                setName(value);
            }
        });
    }

    private List<CollaborationElementWidget> conversationElements = new ArrayList<CollaborationElementWidget>(); // Linked hashmap to preserve order of inserted elements
    private List<ArtifactWidget> artifacts = new ArrayList< ArtifactWidget>(); // Linked hashmap to preserve order of inserted elements

    @Override
    public void createPropertySet(ElementPropertySet set) {
        TCollaboration collaboration = (TCollaboration) this.getModelerFile().getRootElement();
        ElementConfigFactory elementConfigFactory = this.getModelerFile().getVendorSpecification().getElementConfigFactory();
        elementConfigFactory.createPropertySet(set, collaboration, getPropertyChangeListeners());
        IDefinitionElement definitionElement = this.getModelerFile().getDefinitionElement();
        elementConfigFactory.createPropertySet(set, definitionElement, null);

    }

    @Override
    public void manageLayerWidget() {
        for (ArtifactWidget artifact : this.getArtifacts()) {
            if (artifact instanceof GroupWidget) {
                ((GroupWidget) artifact).bringToBack();
            } else if (artifact instanceof TextAnnotationWidget) {
                ((TextAnnotationWidget) artifact).bringToFront();
            }
        }
        super.manageLayerWidget();
    }

    /**
     * @return the conversationElements
     */
    public List<CollaborationElementWidget> getConversationElements() {
        return conversationElements;
    }

    public CollaborationElementWidget getConversationElement(String id) {
        CollaborationElementWidget widget = null;
        for (CollaborationElementWidget conversationElementWidget : conversationElements) {
            if (conversationElementWidget.getId().equals(id)) {
                widget = conversationElementWidget;
                break;
            }
        }
        return widget;
    }

    public ArtifactWidget getArtifact(String id) {
        ArtifactWidget widget = null;
        for (ArtifactWidget artifactWidget : artifacts) {
            if (artifactWidget.getId().equals(id)) {
                widget = artifactWidget;
                break;
            }
        }
        return widget;
    }

    /**
     * @param conversationElements the flowElements to set
     */
    public void setConversationElements(List<CollaborationElementWidget> conversationElements) {
        this.conversationElements = conversationElements;
    }

    public void removeConversationElement(CollaborationElementWidget conversationElementWidget) {
        this.conversationElements.remove(conversationElementWidget);
    }

    public void addConversationElement(CollaborationElementWidget conversationElementWidget) {
        this.conversationElements.add(conversationElementWidget);
    }

    @Override
    public BaseElementWidget getBaseElement(String id) {
        BaseElementWidget widget = null;
        List<BaseElementWidget> baseElementWidgets = new ArrayList<BaseElementWidget>(conversationElements);
        baseElementWidgets.addAll(artifacts);
        for (BaseElementWidget baseElementWidget : baseElementWidgets) {
            if (baseElementWidget.getId().equals(id)) {
                widget = baseElementWidget;
                break;
            }
        }
        return widget;
    }

    @Override
    public List<IBaseElementWidget> getBaseElements() {
        List<IBaseElementWidget> baseElementWidgets = new ArrayList<IBaseElementWidget>(conversationElements);
        baseElementWidgets.addAll(artifacts);
        return baseElementWidgets;
    }

    @Override
    public IBaseElementWidget findBaseElement(String id) {
        BaseElementWidget widget = null;
        List<BaseElementWidget> baseElementWidgets = new ArrayList<BaseElementWidget>(conversationElements);
        baseElementWidgets.addAll(artifacts);
        for (BaseElementWidget baseElementWidget : baseElementWidgets) {
            if (baseElementWidget.getId().equals(id)) {
                widget = baseElementWidget;
                break;
            }
        }
        return widget;
    }

    public void removeBaseElement(IBaseElementWidget baseElementWidget) {
        if (baseElementWidget instanceof CollaborationElementWidget) {
            removeConversationElement((CollaborationElementWidget) baseElementWidget);
        } else if (baseElementWidget instanceof ArtifactWidget) {
            removeArtifact((ArtifactWidget) baseElementWidget);
        }
    }

    @Override
    public void addBaseElement(IBaseElementWidget baseElementWidget) {
        if (baseElementWidget instanceof CollaborationElementWidget) {
            addConversationElement((CollaborationElementWidget) baseElementWidget);
        } else if (baseElementWidget instanceof ArtifactWidget) {
            addArtifact((ArtifactWidget) baseElementWidget);
        }
    }

    @Override
    public void deleteBaseElement(IBaseElementWidget baseElementWidget) {
        TCollaboration collaborationSpec = (TCollaboration) this.getModelerFile().getRootElement();
        if (baseElementWidget instanceof CollaborationElementWidget) {
            if (baseElementWidget instanceof CollaborationNodeWidget) { //reverse ref
                CollaborationNodeWidget collaborationNodeWidget = (CollaborationNodeWidget) baseElementWidget;
                IBaseElement baseElementSpec = collaborationNodeWidget.getBaseElementSpec();

                List<CollaborationEdgeWidget> collaborationEdgeWidgetList = new ArrayList<CollaborationEdgeWidget>();
                collaborationEdgeWidgetList.addAll(collaborationNodeWidget.getOutgoingCollaborationEdges());
                collaborationEdgeWidgetList.addAll(collaborationNodeWidget.getIncomingCollaborationEdges());

                for (CollaborationEdgeWidget collaborationEdgeWidget : collaborationEdgeWidgetList) {
                    collaborationEdgeWidget.remove();
                }

                List<AssociationWidget> associationWidgetList = new ArrayList<AssociationWidget>();
                associationWidgetList.addAll(collaborationNodeWidget.getOutgoingAssociation());
                associationWidgetList.addAll(collaborationNodeWidget.getIncomingAssociation());

                for (AssociationWidget associationWidget : associationWidgetList) {
                    associationWidget.remove();
                }

                collaborationSpec.removeBaseElement(baseElementSpec);
                collaborationNodeWidget.setFlowElementsContainer(null);
                this.conversationElements.remove(collaborationNodeWidget);
            } else if (baseElementWidget instanceof CollaborationEdgeWidget) {
                CollaborationEdgeWidget collaborationEdgeWidget = ((CollaborationEdgeWidget) baseElementWidget);
                IBaseElement baseElementSpec = collaborationEdgeWidget.getBaseElementSpec();
                CollaborationNodeWidget sourceWidget = collaborationEdgeWidget.getSourceNode();
                CollaborationNodeWidget targetWidget = collaborationEdgeWidget.getTargetNode();
//                TCollaborationNode sourceSpec = (TCollaborationNode)sourceWidget.getBaseElementSpec();
//                TCollaborationNode targetSpec = (TCollaborationNode)targetWidget.getBaseElementSpec();
//                sourceSpec.getOutgoing().remove(conversationLinkSpec.getId());
//                targetSpec.getIncoming().remove(conversationLinkSpec.getId());
                sourceWidget.getOutgoingCollaborationEdges().remove(collaborationEdgeWidget);
                targetWidget.getIncomingCollaborationEdges().remove(collaborationEdgeWidget);

                collaborationSpec.removeBaseElement(baseElementSpec);
                collaborationEdgeWidget.setFlowElementsContainer(null);
                this.conversationElements.remove(collaborationEdgeWidget);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else if (baseElementWidget instanceof ArtifactWidget) {
            if (baseElementWidget instanceof AssociationWidget) {
                AssociationWidget associationWidget = ((AssociationWidget) baseElementWidget);
                TAssociation associationSpec = (TAssociation) associationWidget.getBaseElementSpec();

                IBaseElementWidget sourceBaseElementWidget = associationWidget.getSourceElementWidget();
                if (sourceBaseElementWidget instanceof IFlowNodeWidget) {
                    CollaborationNodeWidget sourceWidget = (CollaborationNodeWidget) sourceBaseElementWidget;
//                    TCollaborationNode sourceSpec = (TCollaborationNode) sourceWidget.getBaseElementSpec();
//                    sourceSpec.getOutgoing().remove(associationSpec.getId());//not supported
                    sourceWidget.getOutgoingAssociation().remove(associationWidget);
                } else if (sourceBaseElementWidget instanceof IArtifactNodeWidget) {
                    IArtifactNodeWidget sourceWidget = (IArtifactNodeWidget) sourceBaseElementWidget;
                    sourceWidget.getOutgoingArtifactEdgeWidget().remove(associationWidget);
                }

                IBaseElementWidget targetBaseElementWidget = associationWidget.getTargetElementWidget();
                if (targetBaseElementWidget instanceof IFlowNodeWidget) {
                    CollaborationNodeWidget targetWidget = (CollaborationNodeWidget) targetBaseElementWidget;
//                    TCollaborationNode targetSpec = (TCollaborationNode) targetWidget.getBaseElementSpec();
//                    targetSpec.getIncoming().remove(associationSpec.getId());//not supported
                    targetWidget.getIncomingAssociation().remove(associationWidget);
                } else if (targetBaseElementWidget instanceof IArtifactNodeWidget) {
                    IArtifactNodeWidget targetWidget = (IArtifactNodeWidget) targetBaseElementWidget;
                    targetWidget.getIncommingArtifactEdgeWidget().remove(associationWidget);
                }

                collaborationSpec.removeArtifact(associationSpec);
                associationWidget.setFlowElementsContainer(null);
                this.artifacts.remove(associationWidget);
            } else {
                ArtifactWidget artifactWidget = (ArtifactWidget) baseElementWidget;
                TArtifact artifactSpec = (TArtifact) artifactWidget.getBaseElementSpec();

                List<AssociationWidget> associationWidgetList = new ArrayList<AssociationWidget>();
                if (baseElementWidget instanceof TextAnnotationWidget) {
                    associationWidgetList.addAll(((TextAnnotationWidget) artifactWidget).getOutgoingAssociation());
                    associationWidgetList.addAll(((TextAnnotationWidget) artifactWidget).getIncomingAssociation());
                } else if (baseElementWidget instanceof GroupWidget) {
                    associationWidgetList.addAll(((GroupWidget) artifactWidget).getOutgoingAssociation());
                    associationWidgetList.addAll(((GroupWidget) artifactWidget).getIncomingAssociation());
                }
                for (AssociationWidget associationWidget : associationWidgetList) {
                    associationWidget.remove();
                }

                collaborationSpec.removeArtifact(artifactSpec);
                artifactWidget.setFlowElementsContainer(null);
                this.artifacts.remove(artifactWidget);
            }

        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }

    }

    public void createBaseElement(IBaseElementWidget baseElementWidget) {
        String baseElementId = "";
        Boolean isExist = false;
        if (baseElementWidget instanceof CollaborationElementWidget) {
            this.conversationElements.add((CollaborationElementWidget) baseElementWidget);
            if (baseElementWidget instanceof CollaborationNodeWidget) { //reverse ref
                ((CollaborationNodeWidget) baseElementWidget).setFlowElementsContainer(this);
                isExist = ((CollaborationNodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
            } else if (baseElementWidget instanceof CollaborationEdgeWidget) {
                ((CollaborationEdgeWidget) baseElementWidget).setFlowElementsContainer(this);
                isExist = ((CollaborationEdgeWidget) baseElementWidget).getEdgeWidgetInfo().isExist();
            } else {
                throw new InvalidElmentException("Invalid BPMN FlowElement : " + baseElementWidget);
            }
//               else if (baseElementWidget instanceof ParticipantWidget) {
//            ((ParticipantWidget) baseElementWidget).setFlowElementsContainer(this);
//            baseElementId = ((ParticipantWidget) baseElementWidget).getId();
//            isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
//        } else if (baseElementWidget instanceof ConversationNodeWidget) {
//            if (baseElementWidget instanceof ConversationWidget) {
//                ((ConversationWidget) baseElementWidget).setFlowElementsContainer(this);
//                baseElementId = ((ConversationWidget) baseElementWidget).getId();
//                isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
//            } else if (baseElementWidget instanceof SubConversationWidget) {
//                ((SubConversationWidget) baseElementWidget).setFlowElementsContainer(this);
//                baseElementId = ((SubConversationWidget) baseElementWidget).getId();
//                isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
//            } else {
//                throw new InvalidElmentException("Invalid BPMN ConversationNodeWidget Element");
//            }
//        }
        } else if (baseElementWidget instanceof ArtifactWidget) {
            this.artifacts.add((ArtifactWidget) baseElementWidget);
            if (baseElementWidget instanceof TextAnnotationWidget) { //reverse ref
                ((TextAnnotationWidget) baseElementWidget).setFlowElementsContainer(this);
                isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
            } else if (baseElementWidget instanceof AssociationWidget) {
                ((AssociationWidget) baseElementWidget).setFlowElementsContainer(this);
                isExist = ((EdgeWidget) baseElementWidget).getEdgeWidgetInfo().isExist();
            } else if (baseElementWidget instanceof GroupWidget) { //reverse ref
                ((GroupWidget) baseElementWidget).setFlowElementsContainer(this);
                isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
            } else {
                throw new InvalidElmentException("Invalid BPMN ArtifactWidget : " + baseElementWidget);
            }
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
        baseElementId = baseElementWidget.getId();

        if (!isExist) {
            /*specification Start*///ELEMENT_UPGRADE
            //Conversation Model
            TCollaboration collaboration = (TCollaboration) this.getModelerFile().getRootElement();
            IBaseElement baseElement = null;
            if (baseElementWidget instanceof CollaborationElementWidget) {
                if (baseElementWidget instanceof CollaborationNodeWidget) {
                    if (baseElementWidget instanceof ConversationNodeWidget) {
                        if (baseElementWidget instanceof ConversationWidget) {
                            baseElement = new TConversation();
                        } else if (baseElementWidget instanceof SubConversationWidget) {
                            baseElement = new TSubConversation();
                        } else {
                            throw new InvalidElmentException("Invalid BPMN Task Element : " + baseElement);
                        }
                    } else if (baseElementWidget instanceof ParticipantWidget) {
                        baseElement = new TParticipant();
                    } else {
                        throw new InvalidElmentException("Invalid BPMN Task Element : " + baseElement);
                    }

                } else if (baseElementWidget instanceof ConversationLinkWidget) {
                    baseElement = new TConversationLink();
                } else if (baseElementWidget instanceof MessageFlowWidget) {
                    baseElement = new TMessageFlow();
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else if (baseElementWidget instanceof ArtifactWidget) {
                if (baseElementWidget instanceof TextAnnotationWidget) { //reverse ref
                    baseElement = new TTextAnnotation();
                } else if (baseElementWidget instanceof AssociationWidget) {
                    baseElement = new TAssociation();
                } else if (baseElementWidget instanceof GroupWidget) {
                    baseElement = new TGroup();
                } else {
                    throw new InvalidElmentException("Invalid BPMN ArtifactWidget : " + baseElementWidget);
                }
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
            baseElementWidget.setBaseElementSpec(baseElement);
            baseElement.setId(baseElementId);
            collaboration.addBaseElement(baseElement);

            ElementConfigFactory elementConfigFactory = this.getModelerFile().getVendorSpecification().getElementConfigFactory();
            elementConfigFactory.initializeObjectValue(baseElement);

        } else {
            if (baseElementWidget instanceof CollaborationElementWidget) {
                if (baseElementWidget instanceof CollaborationNodeWidget) {
                    CollaborationNodeWidget collaborationNodeWidget = (CollaborationNodeWidget) baseElementWidget;
                    collaborationNodeWidget.setBaseElementSpec(collaborationNodeWidget.getNodeWidgetInfo().getBaseElementSpec());
                } else if (baseElementWidget instanceof CollaborationEdgeWidget) {
                    CollaborationEdgeWidget collaborationEdgeWidget = (CollaborationEdgeWidget) baseElementWidget;//TBF_CODE
                    baseElementWidget.setBaseElementSpec(collaborationEdgeWidget.getEdgeWidgetInfo().getBaseElementSpec());
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else if (baseElementWidget instanceof ArtifactWidget) {
                if (baseElementWidget instanceof TextAnnotationWidget) {
                    TextAnnotationWidget textAnnotationWidget = (TextAnnotationWidget) baseElementWidget;
                    textAnnotationWidget.setBaseElementSpec(textAnnotationWidget.getNodeWidgetInfo().getBaseElementSpec());
                } else if (baseElementWidget instanceof AssociationWidget) {
                    AssociationWidget associationWidget = (AssociationWidget) baseElementWidget;
                    associationWidget.setBaseElementSpec(associationWidget.getEdgeWidgetInfo().getBaseElementSpec());
                } else if (baseElementWidget instanceof GroupWidget) {
                    GroupWidget groupWidget = (GroupWidget) baseElementWidget;
                    groupWidget.setBaseElementSpec(groupWidget.getNodeWidgetInfo().getBaseElementSpec());
                } else {
                    throw new InvalidElmentException("Invalid BPMN ArtifactWidget : " + baseElementWidget);
                }
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        }

    }

    /**
     * @return the artifacts
     */
    public List<ArtifactWidget> getArtifacts() {
        return artifacts;
    }

    /**
     * @param artifacts the artifacts to set
     */
    public void setArtifacts(List<ArtifactWidget> artifacts) {
        this.artifacts = artifacts;
    }

    public void addArtifact(ArtifactWidget artifactWidget) {
        this.artifacts.add(artifactWidget);
    }

    public void removeArtifact(ArtifactWidget artifactWidget) {
        this.artifacts.remove(artifactWidget);
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createVisualPropertySet(ElementPropertySet elementPropertySet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
