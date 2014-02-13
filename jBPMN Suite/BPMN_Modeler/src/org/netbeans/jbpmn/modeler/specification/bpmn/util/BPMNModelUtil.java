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
package org.netbeans.jbpmn.modeler.specification.bpmn.util;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import org.netbeans.jbpmn.core.widget.AdHocSubProcessWidget;
import org.netbeans.jbpmn.core.widget.ArtifactWidget;
import org.netbeans.jbpmn.core.widget.AssociationWidget;
import org.netbeans.jbpmn.core.widget.BaseElementWidget;
import org.netbeans.jbpmn.core.widget.BoundaryEventWidget;
import org.netbeans.jbpmn.core.widget.BusinessRuleTaskWidget;
import org.netbeans.jbpmn.core.widget.CallActivityWidget;
import org.netbeans.jbpmn.core.widget.ComplexGatewayWidget;
import org.netbeans.jbpmn.core.widget.DefaultSubProcessWidget;
import org.netbeans.jbpmn.core.widget.DocumentModelType;
import org.netbeans.jbpmn.core.widget.EndEventWidget;
import org.netbeans.jbpmn.core.widget.EventBasedGatewayWidget;
import org.netbeans.jbpmn.core.widget.EventSubProcessWidget;
import org.netbeans.jbpmn.core.widget.EventWidget;
import org.netbeans.jbpmn.core.widget.ExclusiveGatewayWidget;
import org.netbeans.jbpmn.core.widget.FlowNodeWidget;
import org.netbeans.jbpmn.core.widget.GroupWidget;
import org.netbeans.jbpmn.core.widget.InclusiveGatewayWidget;
import org.netbeans.jbpmn.core.widget.IntermediateCatchEventWidget;
import org.netbeans.jbpmn.core.widget.IntermediateThrowEventWidget;
import org.netbeans.jbpmn.core.widget.ManualTaskWidget;
import org.netbeans.jbpmn.core.widget.ParallelGatewayWidget;
import org.netbeans.jbpmn.core.widget.ReceiveTaskWidget;
import org.netbeans.jbpmn.core.widget.ScriptTaskWidget;
import org.netbeans.jbpmn.core.widget.SendTaskWidget;
import org.netbeans.jbpmn.core.widget.SequenceFlowWidget;
import org.netbeans.jbpmn.core.widget.ServiceTaskWidget;
import org.netbeans.jbpmn.core.widget.StartEventWidget;
import org.netbeans.jbpmn.core.widget.TaskWidget;
import org.netbeans.jbpmn.core.widget.TextAnnotationWidget;
import org.netbeans.jbpmn.core.widget.TransactionSubProcessWidget;
import org.netbeans.jbpmn.core.widget.UserTaskWidget;
import org.netbeans.jbpmn.core.widget.conversation.CollaborationEdgeWidget;
import org.netbeans.jbpmn.core.widget.conversation.CollaborationNodeWidget;
import org.netbeans.jbpmn.core.widget.conversation.ConversationLinkWidget;
import org.netbeans.jbpmn.core.widget.conversation.ConversationWidget;
import org.netbeans.jbpmn.core.widget.conversation.MessageFlowWidget;
import org.netbeans.jbpmn.core.widget.conversation.ParticipantWidget;
import org.netbeans.jbpmn.core.widget.conversation.SubConversationWidget;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TFormalExpression;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TResource;
import org.netbeans.jbpmn.spec.TRootElement;
import org.netbeans.jbpmn.spec.extend.LanguageManager;
import org.netbeans.modeler.border.ResizeBorder;
import org.netbeans.modeler.config.document.FlowDimensionType;
import org.netbeans.modeler.config.document.IModelerDocument;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.exception.InvalidElmentException;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.widget.IBaseElementWidget;
import org.netbeans.modeler.specification.model.util.NModelerUtil;
import org.netbeans.modeler.widget.connection.relation.IRelationValidator;
import org.netbeans.modeler.widget.edge.IEdgeWidget;
import org.netbeans.modeler.widget.edge.info.EdgeWidgetInfo;
import org.netbeans.modeler.widget.node.INodeWidget;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.NodeWidgetStatus;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;

public abstract class BPMNModelUtil implements NModelerUtil {

    public IEdgeWidget getEdgeWidget(IModelerScene scene, EdgeWidgetInfo edgeWidgetInfo) {
        IEdgeWidget edgeWidget = null;
        if (edgeWidgetInfo.getType().equals("SEQUENCEFLOW")) {
            edgeWidget = new SequenceFlowWidget(scene, edgeWidgetInfo);
        } else if (edgeWidgetInfo.getType().equals("ASSOCIATION")) {
            edgeWidget = new AssociationWidget(scene, edgeWidgetInfo);
        } else if (edgeWidgetInfo.getType().equals("CONVERSATIONLINK")) {
            edgeWidget = new ConversationLinkWidget(scene, edgeWidgetInfo);
        } else if (edgeWidgetInfo.getType().equals("MESSAGEFLOW")) {
            edgeWidget = new MessageFlowWidget(scene, edgeWidgetInfo);
        } else {
            throw new InvalidElmentException("Invalid BPMN Edge Type");
        }
        return edgeWidget;
    }

    @Override
    public IEdgeWidget attachEdgeWidget(IModelerScene scene, EdgeWidgetInfo widgetInfo) {
        IEdgeWidget edgeWidget = getEdgeWidget(scene, widgetInfo);

        return edgeWidget;
    }

    @Override
    public INodeWidget attachNodeWidget(IModelerScene scene, NodeWidgetInfo widgetInfo) {
        BaseElementWidget widget = null;
        IModelerDocument bpmnDocument = widgetInfo.getModelerDocument();
        //ELEMENT_UPGRADE
        if (bpmnDocument.getDocumentModel().equals(DocumentModelType.ACTIVITY.name())) {
            if (bpmnDocument.getId().equals("Abstract_Task")) {
                widget = new TaskWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("User_Task")) {
                widget = new UserTaskWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Service_Task")) {
                widget = new ServiceTaskWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Manual_Task")) {
                widget = new ManualTaskWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Script_Task")) {
                widget = new ScriptTaskWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("BusinessRule_Task")) {
                widget = new BusinessRuleTaskWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Send_Task")) {
                widget = new SendTaskWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Receive_Task")) {
                widget = new ReceiveTaskWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Default_SubProcess")) {
                widget = new DefaultSubProcessWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Event_SubProcess")) {
                widget = new EventSubProcessWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Transaction_SubProcess")) {
                widget = new TransactionSubProcessWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("AdHoc_SubProcess")) {
                widget = new AdHocSubProcessWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Call_Activity")) {
                widget = new CallActivityWidget(scene, widgetInfo);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else if (bpmnDocument.getDocumentModel().equals(DocumentModelType.EVENT.name())) {
            if (bpmnDocument.getFlowDimension() == FlowDimensionType.START) {
                widget = new StartEventWidget(scene, widgetInfo);
            } else if (bpmnDocument.getFlowDimension() == FlowDimensionType.BOUNDARY) {
                widget = new BoundaryEventWidget(scene, widgetInfo);
            } else if (bpmnDocument.getFlowDimension() == FlowDimensionType.INTERMIDATE_CATCH) {
                widget = new IntermediateCatchEventWidget(scene, widgetInfo);
            } else if (bpmnDocument.getFlowDimension() == FlowDimensionType.INTERMIDATE_THROW) {
                widget = new IntermediateThrowEventWidget(scene, widgetInfo);
            } else if (bpmnDocument.getFlowDimension() == FlowDimensionType.END) {
                widget = new EndEventWidget(scene, widgetInfo);
            } else {
                throw new InvalidElmentException("Invalid BPMN Event : " + bpmnDocument.getFlowDimension());
            }

        } else if (bpmnDocument.getDocumentModel().equals(DocumentModelType.GATEWAY.name())) {
            if (bpmnDocument.getId().equals("Complex_Gateway")) {
                widget = new ComplexGatewayWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("EventBased_Gateway")) {
                widget = new EventBasedGatewayWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Exclusive_Gateway")) {
                widget = new ExclusiveGatewayWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Inclusive_Gateway")) {
                widget = new InclusiveGatewayWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Parallel_Gateway")) {
                widget = new ParallelGatewayWidget(scene, widgetInfo);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else if (bpmnDocument.getDocumentModel().equals(DocumentModelType.ARTIFACT.name())) {
            if (bpmnDocument.getId().equals("TextAnnotation_Artifact")) {
                widget = new TextAnnotationWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Group_Artifact")) {
                widget = new GroupWidget(scene, widgetInfo);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else if (bpmnDocument.getDocumentModel().equals(DocumentModelType.SWIMLANE.name())) {
//            if (bpmnDocument.getId().equals("Pool_Swimlane")) {
//                widget = new LaneSetWidget(scene);
//            } else if (bpmnDocument.getId().equals("Lane_Swimlane")) {
//                widget = new LaneWidget(scene);
//            } else {
//                throw new InvalidElmentException("Invalid BPMN Element");
//            }
        } else if (bpmnDocument.getDocumentModel().equals(DocumentModelType.CONVERSATION.name())) {
            if (bpmnDocument.getId().equals("Conversation_Conversation")) {
                widget = new ConversationWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("SubConversation_Conversation")) {
                widget = new SubConversationWidget(scene, widgetInfo);
            } else if (bpmnDocument.getId().equals("Participant_Conversation")) {
                widget = new ParticipantWidget(scene, widgetInfo);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else {
            throw new InvalidElmentException("Invalid BPMN Model" + bpmnDocument.getDocumentModel());
        }
        return (INodeWidget) widget;
    }

    @Override
    public String getEdgeType(INodeWidget sourceNodeWidget, INodeWidget targetNodeWidget, String connectionContextToolId) {
        String edgeType = null;
        if (sourceNodeWidget instanceof FlowNodeWidget && targetNodeWidget instanceof FlowNodeWidget) {
            edgeType = "SEQUENCEFLOW";
        } else if (sourceNodeWidget instanceof ArtifactWidget || targetNodeWidget instanceof ArtifactWidget) {
            edgeType = "ASSOCIATION";
        } else if (sourceNodeWidget instanceof CollaborationNodeWidget || targetNodeWidget instanceof CollaborationNodeWidget) {
            if (sourceNodeWidget instanceof ParticipantWidget && targetNodeWidget instanceof ParticipantWidget) {
                edgeType = "MESSAGEFLOW";
            } else {
                edgeType = "CONVERSATIONLINK";
            }
        }
        return edgeType;
    }

    @Override
    public void dettachEdgeSourceAnchor(IModelerScene scene, IEdgeWidget edgeWidget, INodeWidget sourceNodeWidget) {
        if (sourceNodeWidget instanceof FlowNodeWidget) {
            if (edgeWidget instanceof SequenceFlowWidget) {
                ((FlowNodeWidget) sourceNodeWidget).removeOutgoingSequenceFlow((SequenceFlowWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((SequenceFlowWidget) edgeWidget).setSourceNode(null);
            } else if (edgeWidget instanceof AssociationWidget) {
                ((FlowNodeWidget) sourceNodeWidget).removeOutgoingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((AssociationWidget) edgeWidget).setSourceElementWidget(null);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else if (sourceNodeWidget instanceof CollaborationNodeWidget) {
            if (edgeWidget instanceof CollaborationEdgeWidget) {
                ((CollaborationNodeWidget) sourceNodeWidget).removeOutgoingCollaborationEdge((CollaborationEdgeWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((CollaborationEdgeWidget) edgeWidget).setSourceNode(null);
            } else if (edgeWidget instanceof AssociationWidget) {
                ((CollaborationNodeWidget) sourceNodeWidget).removeOutgoingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((AssociationWidget) edgeWidget).setSourceElementWidget(null);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else if (sourceNodeWidget instanceof ArtifactWidget) {
            if (sourceNodeWidget instanceof TextAnnotationWidget) {
                if (edgeWidget instanceof AssociationWidget) {
                    ((TextAnnotationWidget) sourceNodeWidget).removeOutgoingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                    ((AssociationWidget) edgeWidget).setSourceElementWidget(null);
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else if (sourceNodeWidget instanceof GroupWidget) {
                if (edgeWidget instanceof AssociationWidget) {
                    ((GroupWidget) sourceNodeWidget).removeOutgoingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                    ((AssociationWidget) edgeWidget).setSourceElementWidget(null);
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
    }

    @Override
    public void dettachEdgeTargetAnchor(IModelerScene scene, IEdgeWidget edgeWidget, INodeWidget targetNodeWidget) {
        if (targetNodeWidget instanceof FlowNodeWidget) {
            if (edgeWidget instanceof SequenceFlowWidget) {
                ((FlowNodeWidget) targetNodeWidget).removeIncomingSequenceFlow((SequenceFlowWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((SequenceFlowWidget) edgeWidget).setTargetNode(null);
            } else if (edgeWidget instanceof AssociationWidget) {
                ((FlowNodeWidget) targetNodeWidget).removeIncomingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((AssociationWidget) edgeWidget).setTargetElementWidget(null);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else if (targetNodeWidget instanceof CollaborationNodeWidget) {
            if (edgeWidget instanceof CollaborationEdgeWidget) {
                ((CollaborationNodeWidget) targetNodeWidget).removeIncomingCollaborationEdge((CollaborationEdgeWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((CollaborationEdgeWidget) edgeWidget).setTargetNode(null);
            } else if (edgeWidget instanceof AssociationWidget) {
                ((CollaborationNodeWidget) targetNodeWidget).removeIncomingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((AssociationWidget) edgeWidget).setTargetElementWidget(null);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else if (targetNodeWidget instanceof ArtifactWidget) {
            if (targetNodeWidget instanceof TextAnnotationWidget) {
                if (edgeWidget instanceof AssociationWidget) {
                    ((TextAnnotationWidget) targetNodeWidget).removeIncomingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                    ((AssociationWidget) edgeWidget).setTargetElementWidget(null);
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else if (targetNodeWidget instanceof GroupWidget) {
                if (edgeWidget instanceof AssociationWidget) {
                    ((GroupWidget) targetNodeWidget).removeIncomingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                    ((AssociationWidget) edgeWidget).setTargetElementWidget(null);
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }

        //        FlowNodeWidget sourceNodeWidget = sequenceFlowWidget.getSourceNode();
//        if(BPMNUtil.isValidRelationship(sourceNodeWidget, targetNodeWidget,sequenceFlowWidget , false ) == false){
//            BPMNUtil.detachEdgeWidget(scene, sequenceFlowWidget , false);
//        }
//        targetNodeWidget.setStatus(NodeWidgetStatus.NONE);
    }

    @Override
    public void attachEdgeSourceAnchor(IModelerScene scene, IEdgeWidget edgeWidget, INodeWidget sourceNodeWidget) {

//        CustomRectangularAnchor cra = new CustomRectangularAnchor();
        if (sourceNodeWidget == null) {
            return;
        }
        if (sourceNodeWidget instanceof FlowNodeWidget) {
            if (edgeWidget instanceof SequenceFlowWidget) {
                ((FlowNodeWidget) sourceNodeWidget).addOutgoingSequenceFlow((SequenceFlowWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((SequenceFlowWidget) edgeWidget).setSourceNode((FlowNodeWidget) sourceNodeWidget);
            } else if (edgeWidget instanceof AssociationWidget) {
                ((FlowNodeWidget) sourceNodeWidget).addOutgoingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((AssociationWidget) edgeWidget).setSourceElementWidget((FlowNodeWidget) sourceNodeWidget);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else if (sourceNodeWidget instanceof CollaborationNodeWidget) {
            if (edgeWidget instanceof CollaborationEdgeWidget) {
                ((CollaborationNodeWidget) sourceNodeWidget).addOutgoingCollaborationEdge((CollaborationEdgeWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((CollaborationEdgeWidget) edgeWidget).setSourceNode((CollaborationNodeWidget) sourceNodeWidget);
            } else if (edgeWidget instanceof AssociationWidget) {
                ((CollaborationNodeWidget) sourceNodeWidget).addOutgoingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((AssociationWidget) edgeWidget).setSourceElementWidget((CollaborationNodeWidget) sourceNodeWidget);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else if (sourceNodeWidget instanceof ArtifactWidget) {
            if (sourceNodeWidget instanceof TextAnnotationWidget) {
                if (edgeWidget instanceof AssociationWidget) {
                    ((TextAnnotationWidget) sourceNodeWidget).addOutgoingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                    ((AssociationWidget) edgeWidget).setSourceElementWidget((TextAnnotationWidget) sourceNodeWidget);
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else if (sourceNodeWidget instanceof GroupWidget) {
                if (edgeWidget instanceof AssociationWidget) {
                    ((GroupWidget) sourceNodeWidget).addOutgoingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                    ((AssociationWidget) edgeWidget).setSourceElementWidget((GroupWidget) sourceNodeWidget);
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
        edgeWidget.setSourceAnchor(NBModelerUtil.getAnchor(sourceNodeWidget));
    }

    @Override
    public void attachEdgeTargetAnchor(IModelerScene scene, IEdgeWidget edgeWidget, INodeWidget targetNodeWidget) {
        IRelationValidator relationValidator = scene.getModelerFile().getVendorSpecification().getModelerDiagramModel().getRelationValidator();
        if (targetNodeWidget == null) {
            return;
        }
        if (targetNodeWidget instanceof FlowNodeWidget) {
            if (edgeWidget instanceof SequenceFlowWidget) {
                ((FlowNodeWidget) targetNodeWidget).addIncomingSequenceFlow((SequenceFlowWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((SequenceFlowWidget) edgeWidget).setTargetNode((FlowNodeWidget) targetNodeWidget);
            } else if (edgeWidget instanceof AssociationWidget) {
                ((FlowNodeWidget) targetNodeWidget).addIncomingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((AssociationWidget) edgeWidget).setTargetElementWidget((FlowNodeWidget) targetNodeWidget);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else if (targetNodeWidget instanceof CollaborationNodeWidget) {
            if (edgeWidget instanceof CollaborationEdgeWidget) {
                ((CollaborationNodeWidget) targetNodeWidget).addIncomingCollaborationEdge((CollaborationEdgeWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((CollaborationEdgeWidget) edgeWidget).setTargetNode((CollaborationNodeWidget) targetNodeWidget);
            } else if (edgeWidget instanceof AssociationWidget) {
                ((CollaborationNodeWidget) targetNodeWidget).addIncomingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                ((AssociationWidget) edgeWidget).setTargetElementWidget((CollaborationNodeWidget) targetNodeWidget);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }

        } else if (targetNodeWidget instanceof ArtifactWidget) {
            if (targetNodeWidget instanceof TextAnnotationWidget) {
                if (edgeWidget instanceof AssociationWidget) {
                    ((TextAnnotationWidget) targetNodeWidget).addIncomingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                    ((AssociationWidget) edgeWidget).setTargetElementWidget((TextAnnotationWidget) targetNodeWidget);
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else if (targetNodeWidget instanceof GroupWidget) {
                if (edgeWidget instanceof AssociationWidget) {
                    ((GroupWidget) targetNodeWidget).addIncomingAssociation((AssociationWidget) edgeWidget);//.addOutgoingSequenceFlowWidget(widget);
                    ((AssociationWidget) edgeWidget).setTargetElementWidget((GroupWidget) targetNodeWidget);
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
        edgeWidget.setTargetAnchor(NBModelerUtil.getAnchor(targetNodeWidget));


        /* Validate relation : rollback if error*/
        //ENHANCEMENT : Remove below validation already completed in  SceneConnectProvider.java
        if (edgeWidget instanceof SequenceFlowWidget) {
            FlowNodeWidget sourceNodeWidget = ((SequenceFlowWidget) edgeWidget).getSourceNode();
            if (NBModelerUtil.isValidRelationship(relationValidator, sourceNodeWidget, targetNodeWidget, edgeWidget.getEdgeWidgetInfo().getType(), false) == false) {
                edgeWidget.remove();
            }
            targetNodeWidget.setStatus(NodeWidgetStatus.NONE);
        } else if (edgeWidget instanceof AssociationWidget) {
            IBaseElementWidget sourceNodeWidget = ((AssociationWidget) edgeWidget).getSourceElementWidget();
            if (NBModelerUtil.isValidRelationship(relationValidator, (INodeWidget) sourceNodeWidget, targetNodeWidget, edgeWidget.getEdgeWidgetInfo().getType(), false) == false) {
                edgeWidget.remove();
            }
            targetNodeWidget.setStatus(NodeWidgetStatus.NONE);
        }

    }

    @Override
    public ResizeBorder getNodeBorder(INodeWidget nodeWidget) {
        if (nodeWidget instanceof EventWidget) {
            nodeWidget.setWidgetBorder(NodeWidget.CIRCLE_RESIZE_BORDER);
            return NodeWidget.CIRCLE_RESIZE_BORDER;
        } else {
            nodeWidget.setWidgetBorder(NodeWidget.RECTANGLE_RESIZE_BORDER);
            return NodeWidget.RECTANGLE_RESIZE_BORDER;
        }
    }

    public static List<ComboBoxValue<TItemDefinition>> getItemDefinitionList(ModelerFile file) {
        List<ComboBoxValue<TItemDefinition>> values = new ArrayList<ComboBoxValue<TItemDefinition>>();
        values.add(new ComboBoxValue<TItemDefinition>(null, null, ""));
        TDefinitions definition_Local = (TDefinitions) file.getDefinitionElement();
        for (TRootElement rootElement : definition_Local.getRootElement()) {
            if (rootElement instanceof TItemDefinition) {
                TItemDefinition itemDefinition = (TItemDefinition) rootElement;
                String display;
                if (itemDefinition.isIsCollection()) {
                    display = itemDefinition.getStructureRef() + "[]";
                } else {
                    display = itemDefinition.getStructureRef();
                }
                values.add(new ComboBoxValue<TItemDefinition>(itemDefinition.getId(), itemDefinition, display));
            }
        }
        return values;
    }

    public static List<ComboBoxValue<TResource>> getResourceList(ModelerFile file) {
        List<ComboBoxValue<TResource>> values = new ArrayList<ComboBoxValue<TResource>>();
        values.add(new ComboBoxValue<TResource>(null, null, ""));
        TDefinitions definition_Local = (TDefinitions) file.getDefinitionElement();
        for (TRootElement rootElement : definition_Local.getRootElement()) {
            if (rootElement instanceof TResource) {
                TResource resource = (TResource) rootElement;
                String display;
                display = resource.getName();

                values.add(new ComboBoxValue<TResource>(resource.getId(), resource, display));
            }
        }
        return values;
    }

    public static void initExpressionPanel(TFormalExpression formalExpression, JComboBox language_ComboBox, JTextArea content_TextArea, JComboBox evaluatesToType_ComboBox) {
        language_ComboBox.setSelectedItem(LanguageManager.getLanguage(formalExpression == null ? null : formalExpression.getLanguage()));
        content_TextArea.setText(formalExpression == null ? "" : formalExpression.getContent());
        if (formalExpression != null && formalExpression.getEvaluatesToTypeRef() != null) {
            for (int i = 0; i < evaluatesToType_ComboBox.getItemCount(); i++) {
                ComboBoxValue<TItemDefinition> itemDefinition = (ComboBoxValue<TItemDefinition>) evaluatesToType_ComboBox.getItemAt(i);
                if (itemDefinition.getValue() != null && itemDefinition.getValue().getId().equals(formalExpression.getEvaluatesToTypeRef())) {
                    evaluatesToType_ComboBox.setSelectedItem(itemDefinition);
                    break;
                }
            }
        } else {
            evaluatesToType_ComboBox.setSelectedItem(new ComboBoxValue<TItemDefinition>(null, null, ""));
        }
    }

    public static void setExpressionPanelValue(TFormalExpression formalExpression, JComboBox language_ComboBox, JTextArea content_TextArea, JComboBox evaluatesToType_ComboBox) {

        ComboBoxValue<String> language_comboBoxValue;
        if (language_ComboBox.getSelectedItem() instanceof String) {
            language_comboBoxValue = new ComboBoxValue<String>((String) language_ComboBox.getSelectedItem(), (String) language_ComboBox.getSelectedItem());
        } else {
            language_comboBoxValue = (ComboBoxValue<String>) language_ComboBox.getSelectedItem();
        }
        formalExpression.setLanguage(language_comboBoxValue.getValue());
        formalExpression.setContent(content_TextArea.getText().trim());
        formalExpression.setEvaluatesToTypeRef(((ComboBoxValue<TItemDefinition>) evaluatesToType_ComboBox.getSelectedItem()).getId());

    }

}
