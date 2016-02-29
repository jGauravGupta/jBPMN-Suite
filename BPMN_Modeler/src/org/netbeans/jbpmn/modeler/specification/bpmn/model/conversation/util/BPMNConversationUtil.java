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
package org.netbeans.jbpmn.modeler.specification.bpmn.model.conversation.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.io.FileUtils;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.jbpmn.core.widget.ArtifactWidget;
import org.netbeans.jbpmn.core.widget.AssociationWidget;
import org.netbeans.jbpmn.core.widget.BaseElementWidget;
import org.netbeans.jbpmn.core.widget.conversation.CollaborationNodeWidget;
import org.netbeans.jbpmn.core.widget.conversation.ConversationLinkWidget;
import org.netbeans.jbpmn.core.widget.conversation.ConversationNodeWidget;
import org.netbeans.jbpmn.core.widget.conversation.MessageFlowWidget;
import org.netbeans.jbpmn.core.widget.conversation.ParticipantWidget;
import org.netbeans.jbpmn.modeler.specification.bpmn.util.BPMNModelUtil;
import org.netbeans.jbpmn.spec.BPMNDiagram;
import org.netbeans.jbpmn.spec.BPMNEdge;
import org.netbeans.jbpmn.spec.BPMNLabel;
import org.netbeans.jbpmn.spec.BPMNPlane;
import org.netbeans.jbpmn.spec.BPMNShape;
import org.netbeans.jbpmn.spec.Bounds;
import org.netbeans.jbpmn.spec.DiagramElement;
import org.netbeans.jbpmn.spec.TAssociation;
import org.netbeans.jbpmn.spec.TCollaboration;
import org.netbeans.jbpmn.spec.TConversationLink;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TExtensionElements;
import org.netbeans.jbpmn.spec.TGroup;
import org.netbeans.jbpmn.spec.TMessageFlow;
import org.netbeans.jbpmn.spec.TProcess;
import org.netbeans.jbpmn.spec.TRootElement;
import org.netbeans.jbpmn.spec.TTextAnnotation;
import org.netbeans.jbpmn.spec.extend.TCollaborationNode;
import org.netbeans.modeler.anchors.CustomPathAnchor;
import org.netbeans.modeler.anchors.CustomRectangularAnchor;
import org.netbeans.modeler.config.document.IModelerDocument;
import org.netbeans.modeler.config.document.ModelerDocumentFactory;
import org.netbeans.modeler.config.palette.SubCategoryNodeConfig;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.exception.InvalidElmentException;
import org.netbeans.modeler.core.exception.ModelerException;
import org.netbeans.modeler.shape.Border;
import org.netbeans.modeler.shape.GradientPaint;
import org.netbeans.modeler.shape.InnerShapeContext;
import org.netbeans.modeler.shape.OuterShapeContext;
import org.netbeans.modeler.shape.ShapeDesign;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.INModelerScene;
import org.netbeans.modeler.specification.model.document.core.IArtifact;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.core.IFlowEdge;
import org.netbeans.modeler.specification.model.document.core.IFlowNode;
import org.netbeans.modeler.specification.model.document.widget.IBaseElementWidget;
import org.netbeans.modeler.specification.model.document.widget.IFlowNodeWidget;
import org.netbeans.modeler.validation.jaxb.ValidateJAXB;
import org.netbeans.modeler.widget.edge.EdgeWidget;
import org.netbeans.modeler.widget.edge.IEdgeWidget;
import org.netbeans.modeler.widget.edge.info.EdgeWidgetInfo;
import org.netbeans.modeler.widget.node.INodeWidget;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.image.NodeImageWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.openide.util.Exceptions;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.w3c.dom.Element;

public class BPMNConversationUtil extends BPMNModelUtil {

    /*---------------------------------Load File Start---------------------------------*/
//    public static void loadBPMN(final BPMNFile file) {
//
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                loadBPMNImpl(file);
//            }
//        };
//        final RequestProcessor.Task theTask = RP.create(runnable);
//        final ProgressHandle ph = ProgressHandleFactory.createHandle("Loading BPMN File...", theTask);
//        theTask.addTaskListener(new TaskListener() {
//            @Override
//            public void taskFinished(org.openide.util.Task task) {
//                ph.finish();
//            }
//        });
//        ph.start();
//        theTask.schedule(0);
//    }
//
    JAXBContext bpmnConversationContext;
    Unmarshaller bpmnConversationUnmarshaller;
    Marshaller bpmnConversationMarshaller;
    InputOutput io = IOProvider.getDefault().getIO("jBPMN Console", false);

    @Override
    public void loadModelerFile(ModelerFile file) {
        try {
            IModelerScene scene = file.getModelerScene();
            File savedFile = file.getFile();
            if (bpmnConversationContext == null) {
                bpmnConversationContext = JAXBContext.newInstance(new Class<?>[]{ShapeDesign.class, TDefinitions.class});
            }
            if (bpmnConversationUnmarshaller == null) {
                bpmnConversationUnmarshaller = bpmnConversationContext.createUnmarshaller();
            }
            bpmnConversationUnmarshaller.setEventHandler(new ValidateJAXB());
            TDefinitions definition_Load = bpmnConversationUnmarshaller.unmarshal(new StreamSource(savedFile), TDefinitions.class).getValue();
//            TDefinitions definition_Load = (TDefinitions) JAXBIntrospector.getValue(bpmnConversationUnmarshaller.unmarshal(savedFile));

            TCollaboration collaboration = new TCollaboration();

            for (TRootElement element : new CopyOnWriteArrayList<TRootElement>(definition_Load.getRootElement())) {
                if (element instanceof TCollaboration) {
                    TCollaboration collaboration_Tmp = ((TCollaboration) element);
                    String name = collaboration_Tmp.getName();
                    if (name != null && !name.trim().isEmpty()) {
                        collaboration.setName(name);
                    }
                    String id = collaboration_Tmp.getId();
                    if (id != null && !id.trim().isEmpty()) {
                        collaboration.setId(id);
                    }
                    collaboration.getConversationLink().addAll(collaboration_Tmp.getConversationLink());
                    collaboration.getMessageFlow().addAll(collaboration_Tmp.getMessageFlow());
                    collaboration.getConversationNode().addAll(collaboration_Tmp.getConversationNode());
                    collaboration.getParticipant().addAll(collaboration_Tmp.getParticipant());
                    collaboration.getArtifact().addAll(collaboration_Tmp.getArtifact());
                    definition_Load.getRootElement().remove(collaboration_Tmp);
                } else if (element instanceof TProcess) {
                    TProcess process_Tmp = (TProcess) element;
                    collaboration.getArtifact().addAll(process_Tmp.getArtifact());
                    definition_Load.getRootElement().remove(process_Tmp);
                }
            }

            definition_Load.getRootElement().add(collaboration);
            scene.setRootElementSpec(collaboration);

            BPMNDiagram diagram = new BPMNDiagram();
            diagram.setId(NBModelerUtil.getAutoGeneratedStringId());
            BPMNPlane plane = new BPMNPlane();
            plane.setId(NBModelerUtil.getAutoGeneratedStringId());
            diagram.setBPMNPlane(plane);

            for (BPMNDiagram diagram_Tmp : definition_Load.getBPMNDiagram()) {
                if (diagram_Tmp instanceof BPMNDiagram) {
                    BPMNPlane tmpPlane = diagram_Tmp.getBPMNPlane();
                    for (DiagramElement element : tmpPlane.getDiagramElement()) {
                        plane.getDiagramElement().add(element);
                    }
                }
            }
            definition_Load.getBPMNDiagram().removeAll(definition_Load.getBPMNDiagram());
            definition_Load.getBPMNDiagram().add(diagram);

            file.getModelerDiagramModel().setDefinitionElement(definition_Load);
            file.getModelerDiagramModel().setRootElement(collaboration);
            file.getModelerDiagramModel().setDiagramElement(diagram);

//ELEMENT_UPGRADE
            for (IFlowNode flowNode : new CopyOnWriteArrayList<IFlowNode>(collaboration.getConversationNode())) {
                loadNode(scene, (Widget) scene, flowNode);
            }

            for (IFlowNode flowNode : new CopyOnWriteArrayList<IFlowNode>(collaboration.getParticipant())) {
                loadNode(scene, (Widget) scene, flowNode);
            }

            for (IFlowEdge flowEdge : new CopyOnWriteArrayList<IFlowEdge>(collaboration.getConversationLink())) {
                loadEdge(scene, flowEdge);
            }

            for (IFlowEdge flowEdge : new CopyOnWriteArrayList<IFlowEdge>(collaboration.getMessageFlow())) {
                loadEdge(scene, flowEdge);
            }

            for (IArtifact artifact_Load : new CopyOnWriteArrayList<IArtifact>(collaboration.getArtifact())) {
                loadArtifact(scene, artifact_Load);
            }

            for (DiagramElement diagramElement_Tmp : diagram.getBPMNPlane().getDiagramElement()) {
                loadDiagram(scene, diagram, diagramElement_Tmp);
            }
        } catch (JAXBException e) {
            io.getOut().println("Exception: " + e.toString());
            e.printStackTrace();
//            Exceptions.printStackTrace(e);
            System.out.println("Document XML Not Exist");
        }

    }

    private void loadNode(IModelerScene scene, Widget parentWidget, IFlowNode flowElement) {
        IModelerDocument document = null;
        ModelerDocumentFactory modelerDocumentFactory = scene.getModelerFile().getVendorSpecification().getModelerDocumentFactory();
        try {
            document = modelerDocumentFactory.getModelerDocument(flowElement);
        } catch (ModelerException ex) {
            Exceptions.printStackTrace(ex);
        }

        SubCategoryNodeConfig subCategoryNodeConfig = scene.getModelerFile().getVendorSpecification().getPaletteConfig().findSubCategoryNodeConfig(document);

        NodeWidgetInfo nodeWidgetInfo = new NodeWidgetInfo(flowElement.getId(), subCategoryNodeConfig, new Point(0, 0));
        nodeWidgetInfo.setName(flowElement.getName());
        nodeWidgetInfo.setExist(Boolean.TRUE);//to Load BPMN
        nodeWidgetInfo.setBaseElementSpec(flowElement);//to Load BPMN
        NodeWidget nodeWidget = (NodeWidget) scene.createNodeWidget(nodeWidgetInfo);
        if (flowElement.getName() != null) {
            nodeWidget.setLabel(flowElement.getName());
        }

    }

    private void loadEdge(IModelerScene scene, IBaseElement baseElement) {
        EdgeWidgetInfo edgeWidgetInfo = new EdgeWidgetInfo();
        edgeWidgetInfo.setId(baseElement.getId());
        edgeWidgetInfo.setExist(Boolean.TRUE);//to Load BPMN
        edgeWidgetInfo.setBaseElementSpec(baseElement);
        if (baseElement instanceof TConversationLink) {
            TConversationLink conversationLink = (TConversationLink) baseElement;
            edgeWidgetInfo.setName(((TConversationLink) baseElement).getName());
            edgeWidgetInfo.setType("CONVERSATIONLINK");

            NodeWidget sourceNodeWidget = (NodeWidget) scene.findBaseElement(conversationLink.getSourceRef());//REMOVE_PRE  must be getFlowElement
            NodeWidget targetNodeWidget = (NodeWidget) scene.findBaseElement(conversationLink.getTargetRef());//REMOVE_PRE  must be getFlowElement
            edgeWidgetInfo.setSource(sourceNodeWidget.getNodeWidgetInfo().getId());
            edgeWidgetInfo.setTarget(targetNodeWidget.getNodeWidgetInfo().getId());

            // edge.setName("C" + ((BPMNScene)scene).getEdgeCounter());
            IEdgeWidget edgeWidget = scene.createEdgeWidget(edgeWidgetInfo);
            if (((TConversationLink) baseElement).getName() != null) {
                edgeWidget.setLabel(((TConversationLink) baseElement).getName());
            }

            ((INModelerScene) scene).setEdgeWidgetSource(edgeWidgetInfo, sourceNodeWidget.getNodeWidgetInfo());
            ((INModelerScene) scene).setEdgeWidgetTarget(edgeWidgetInfo, targetNodeWidget.getNodeWidgetInfo());

        } else if (baseElement instanceof TMessageFlow) {
            TMessageFlow messageFlow = (TMessageFlow) baseElement;
            edgeWidgetInfo.setName(((TMessageFlow) baseElement).getName());
            edgeWidgetInfo.setType("MESSAGEFLOW");

            NodeWidget sourceNodeWidget = (NodeWidget) scene.findBaseElement(messageFlow.getSourceRef());//REMOVE_PRE  must be getFlowElement
            NodeWidget targetNodeWidget = (NodeWidget) scene.findBaseElement(messageFlow.getTargetRef());//REMOVE_PRE  must be getFlowElement
            edgeWidgetInfo.setSource(sourceNodeWidget.getNodeWidgetInfo().getId());
            edgeWidgetInfo.setTarget(targetNodeWidget.getNodeWidgetInfo().getId());

            // edge.setName("C" + ((BPMNScene)scene).getEdgeCounter());
            IEdgeWidget edgeWidget = scene.createEdgeWidget(edgeWidgetInfo);
            if (((TMessageFlow) baseElement).getName() != null) {
                edgeWidget.setLabel(((TMessageFlow) baseElement).getName());
            }

            ((INModelerScene) scene).setEdgeWidgetSource(edgeWidgetInfo, sourceNodeWidget.getNodeWidgetInfo());
            ((INModelerScene) scene).setEdgeWidgetTarget(edgeWidgetInfo, targetNodeWidget.getNodeWidgetInfo());

        } else if (baseElement instanceof TAssociation) {
            TAssociation association = (TAssociation) baseElement;
            edgeWidgetInfo.setType("ASSOCIATION");

            NodeWidget sourceNodeWidget = (NodeWidget) scene.findBaseElement(association.getSourceRef());//REMOVE_PRE  must be getFlowElement
            NodeWidget targetNodeWidget = (NodeWidget) scene.findBaseElement(association.getTargetRef());//REMOVE_PRE  must be getFlowElement
            edgeWidgetInfo.setSource(sourceNodeWidget.getNodeWidgetInfo().getId());
            edgeWidgetInfo.setTarget(targetNodeWidget.getNodeWidgetInfo().getId());

            // edge.setName("C" + ((BPMNScene)scene).getEdgeCounter());
            IEdgeWidget edgeWidget = scene.createEdgeWidget(edgeWidgetInfo);

            ((INModelerScene) scene).setEdgeWidgetSource(edgeWidgetInfo, sourceNodeWidget.getNodeWidgetInfo());
            ((INModelerScene) scene).setEdgeWidgetTarget(edgeWidgetInfo, targetNodeWidget.getNodeWidgetInfo());

        }
    }

    private void loadArtifact(IModelerScene scene,/* Widget parentWidget,*/ IArtifact artifact) {
        IModelerDocument document = null;
        ModelerDocumentFactory modelerDocumentFactory = scene.getModelerFile().getVendorSpecification().getModelerDocumentFactory();
        if (artifact instanceof TTextAnnotation || artifact instanceof TGroup) {
            try {
                document = modelerDocumentFactory.getModelerDocument(artifact);
            } catch (ModelerException ex) {
                Exceptions.printStackTrace(ex);
            }
            SubCategoryNodeConfig subCategoryNodeConfig = scene.getModelerFile().getVendorSpecification().getPaletteConfig().findSubCategoryNodeConfig(document);

            NodeWidgetInfo nodeWidgetInfo = new NodeWidgetInfo(artifact.getId(), subCategoryNodeConfig, new Point(0, 0));
            nodeWidgetInfo.setExist(Boolean.TRUE);//to Load BPMN
            nodeWidgetInfo.setBaseElementSpec(artifact);//to Load BPMN
            NodeWidget nodeWidget = (NodeWidget) scene.createNodeWidget(nodeWidgetInfo);
//            nodeWidget.setLabel(/*No name for artifact*/);
        } else if (artifact instanceof TAssociation) {
            loadEdge(scene, artifact);
        }
    }

    private void loadDiagram(IModelerScene scene, BPMNDiagram diagram, DiagramElement diagramElement) {
//       BPMNProcessUtil util = new BPMNProcessUtil();
        if (diagramElement instanceof BPMNShape) {
            BPMNShape shape = (BPMNShape) diagramElement;
            Bounds bounds = shape.getBounds();
            Widget widget = (Widget) scene.findBaseElement(shape.getBpmnElement());
            if (widget != null) {
                if (widget instanceof NodeWidget) { //reverse ref
                    NodeWidget nodeWidget = (NodeWidget) widget;
                    NodeImageWidget imageWidget = nodeWidget.getNodeImageWidget();

                    imageWidget.updateWidget((int) bounds.getWidth(), (int) bounds.getHeight(), (int) bounds.getWidth(), (int) bounds.getHeight());
                    imageWidget.setPreferredSize(new Dimension((int) bounds.getWidth(), (int) bounds.getHeight()));

//                    if (nodeWidget instanceof IFlowNodeWidget && ((IFlowNodeWidget) nodeWidget).getFlowElementsContainer() instanceof SubProcessWidget) {
//                        SubProcessWidget parentWidget = (SubProcessWidget) ((IFlowNodeWidget) nodeWidget).getFlowElementsContainer();
//                        BPMNShape parentShape = (BPMNShape) diagram.getBPMNPlane().getBPMNShape(parentWidget.getBaseElementSpec().getId());
//                        Bounds parentbounds = parentShape.getBounds();
//                        Point location = new Point((int) (bounds.getX() - parentbounds.getX()), (int) (bounds.getY() - parentbounds.getY()));
//                        nodeWidget.setPreferredLocation(nodeWidget.convertSceneToLocal(location));
//                    } else {
                    Point location = new Point((int) bounds.getX(), (int) bounds.getY());
                    nodeWidget.setPreferredLocation(location);
//                    }

                    if (shape.getBPMNLabel() != null) {
                        Bounds bound = shape.getBPMNLabel().getBounds();
//                        sequenceFlowWidget.getLabelManager().getLabelWidget().getParentWidget().setPreferredLocation(bound.toPoint());

                        nodeWidget.getLabelManager().getLabelWidget().getParentWidget().setPreferredBounds(
                                nodeWidget.getLabelManager().getLabelWidget().getParentWidget().convertSceneToLocal(bound.toRectangle()));
                    } else {
                        if (nodeWidget.getLabelManager() != null) {
                            nodeWidget.getScene().validate();
                            nodeWidget.getLabelManager().setDefaultPosition(); //if location not found in di then set default position to nodewidget
                        }
                    }

                    nodeWidget.setActiveStatus(false);//Active Status is used to prevent reloading SVGDocument until complete document is loaded
                    ShapeDesign shapeDesign = null; //// BPMNShapeDesign XML Location Change Here
                    if (nodeWidget instanceof CollaborationNodeWidget) {
                        TCollaborationNode collaborationNode = (TCollaborationNode) ((CollaborationNodeWidget) nodeWidget).getBaseElementSpec();
                        TExtensionElements extensionElements = collaborationNode.getExtensionElements();
                        if (extensionElements != null) {
                            for (Object obj : extensionElements.getAny()) {
                                if (obj instanceof Element) { //if ShapeDesign is not in JAXB Context
                                    Element element = (Element) obj;
                                    if ("ShapeDesign".equals(element.getNodeName())) {
                                        try {
                                            shapeDesign = bpmnConversationUnmarshaller.unmarshal((Element) extensionElements.getAny().get(0), ShapeDesign.class).getValue();
                                            shapeDesign.afterUnmarshal();
                                        } catch (JAXBException ex) {
                                            Exceptions.printStackTrace(ex);
                                        }
                                    }
                                } else if (obj instanceof ShapeDesign) {
                                    shapeDesign = (ShapeDesign) obj;
                                }
                            }
                        }
                    }
                    if (shapeDesign != null) {
                        nodeWidget = (NodeWidget) updateNodeWidgetDesign(shapeDesign, nodeWidget);
//                        org.netbeans.modeler.widget.node.INodeWidget in = nodeWidget;
                    }
                    nodeWidget.setActiveStatus(true);
                    nodeWidget.reloadSVGDocument();
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element : " + widget);
                }
            }
        } else if (diagramElement instanceof BPMNEdge) {
            BPMNEdge edge = (BPMNEdge) diagramElement;

            EdgeWidget widget = (EdgeWidget) scene.getBaseElement(edge.getBpmnElement());
            if (widget != null) {
                if (widget instanceof ConversationLinkWidget || widget instanceof MessageFlowWidget || widget instanceof AssociationWidget) {
//                    ConversationLinkWidget conversationLinkWidget = (ConversationLinkWidget) widget;
                    widget.setControlPoints(edge.getWaypointCollection(), true);
                    if (edge.getBPMNLabel() != null) {
                        Bounds bound = edge.getBPMNLabel().getBounds();
                        widget.getLabelManager().getLabelWidget().getParentWidget().setPreferredLocation(
                                widget.getLabelManager().getLabelWidget().convertSceneToLocal(bound.toPoint()));

                    }
                } else if (widget instanceof AssociationWidget) {
                    AssociationWidget associationWidget = (AssociationWidget) widget;
                    associationWidget.setControlPoints(edge.getWaypointCollection(), true);
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            }

        }
    }

    /*---------------------------------Load File End---------------------------------*/
    /*---------------------------------Save File Satrt---------------------------------*/
//      public static void saveBPMN(final BPMNFile file) {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                saveBPMNImpl(file);
//            }
//        };
//        final Requesor.Task theTask = RP.create(runnable);
//        final ProgressHandle ph = ProgressHandleFactory.createHandle("Saving BPMN File...", theTask);
//        theTask.addTaskListener(new TaskListener() {
//            @Override
//            public void taskFinished(org.openide.util.Task task) {
//                ph.finish();
//            }
//        });
//        ph.start();
//        theTask.schedule(0);
//    }
//
    public void saveModelerFile(ModelerFile file) {
        try {
            updateBPMNDiagram(file);
            File savedFile = file.getFile();

            if (bpmnConversationContext == null) {
                bpmnConversationContext = JAXBContext.newInstance(new Class<?>[]{ShapeDesign.class, TDefinitions.class});
            }
            if (bpmnConversationMarshaller == null) {
                bpmnConversationMarshaller = bpmnConversationContext.createMarshaller();
            }

            // output pretty printed
            bpmnConversationMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            bpmnConversationMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.omg.org/spec/BPMN/20100524/MODEL http://www.omg.org/spec/BPMN/2.0/20100501/BPMN20.xsd");
//

//            jaxbMarshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() { // property required for CDATA
//                @Override
//                public void escape(char[] ac, int i, int j, boolean flag,
//                Writer writer) throws IOException {
//                writer.write( ac, i, j ); }
//                });
            bpmnConversationMarshaller.setEventHandler(new ValidateJAXB());

            StringWriter sw = new StringWriter();
            bpmnConversationMarshaller.marshal(file.getDefinitionElement(), sw);
            bpmnConversationMarshaller.marshal(file.getDefinitionElement(), System.out);

            FileUtils.writeStringToFile(savedFile, sw.toString().replaceFirst("xmlns:ns[A-Za-z\\d]{0,3}=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"",
                    "xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\""));

        } catch (JAXBException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        // file.get
    }

    public static ShapeDesign getBPMNShapeDesign(NodeWidget nodeWidget) {
        ShapeDesign shapeDesign = new ShapeDesign();
        shapeDesign.setOuterShapeContext(new OuterShapeContext(
                new GradientPaint(nodeWidget.getOuterElementStartBackgroundColor(), nodeWidget.getOuterElementStartOffset(),
                        nodeWidget.getOuterElementEndBackgroundColor(), nodeWidget.getOuterElementEndOffset()),
                new Border(nodeWidget.getOuterElementBorderColor(), nodeWidget.getOuterElementBorderWidth())));
        shapeDesign.setInnerShapeContext(new InnerShapeContext(
                new GradientPaint(nodeWidget.getInnerElementStartBackgroundColor(), nodeWidget.getInnerElementStartOffset(),
                        nodeWidget.getInnerElementEndBackgroundColor(), nodeWidget.getInnerElementEndOffset()),
                new Border(nodeWidget.getInnerElementBorderColor(), nodeWidget.getInnerElementBorderWidth())));
        shapeDesign.beforeMarshal();//to save design in json
        return shapeDesign;
    }

    @Override
    public INodeWidget updateNodeWidgetDesign(ShapeDesign shapeDesign, INodeWidget inodeWidget) {
        NodeWidget nodeWidget = (NodeWidget) inodeWidget;
        //ELEMENT_UPGRADE
        if (shapeDesign != null) {
            if (shapeDesign.getOuterShapeContext() != null) {
                if (shapeDesign.getOuterShapeContext().getBackground() != null) {
                    nodeWidget.setOuterElementStartBackgroundColor(shapeDesign.getOuterShapeContext().getBackground().getStartColor());
                    nodeWidget.setOuterElementEndBackgroundColor(shapeDesign.getOuterShapeContext().getBackground().getEndColor());
                }
                if (shapeDesign.getOuterShapeContext().getBorder() != null) {
                    nodeWidget.setOuterElementBorderColor(shapeDesign.getOuterShapeContext().getBorder().getColor());
                    nodeWidget.setOuterElementBorderWidth(shapeDesign.getOuterShapeContext().getBorder().getWidth());
                }
            }
            if (shapeDesign.getInnerShapeContext() != null) {
                if (shapeDesign.getInnerShapeContext().getBackground() != null) {
                    nodeWidget.setInnerElementStartBackgroundColor(shapeDesign.getInnerShapeContext().getBackground().getStartColor());
                    nodeWidget.setInnerElementEndBackgroundColor(shapeDesign.getInnerShapeContext().getBackground().getEndColor());
                }
                if (shapeDesign.getInnerShapeContext().getBorder() != null) {
                    nodeWidget.setInnerElementBorderColor(shapeDesign.getInnerShapeContext().getBorder().getColor());
                    nodeWidget.setInnerElementBorderWidth(shapeDesign.getInnerShapeContext().getBorder().getWidth());
                }
            }
        }

        return nodeWidget;
    }

    public static void updateDiagramFlowElement(BPMNPlane plane, Widget widget) {
        //Diagram Model
        if (widget instanceof NodeWidget) { //reverse ref
            NodeWidget nodeWidget = (NodeWidget) widget;
            Rectangle rec = nodeWidget.getSceneViewBound();

            BPMNShape shape = new BPMNShape();
            shape.setBounds(new Bounds(rec));//(new Bounds(flowNodeWidget.getBounds()));
            shape.setBpmnElement(((BaseElementWidget) nodeWidget).getId());
            shape.setId(((BaseElementWidget) nodeWidget).getId() + "_gui");
            if (nodeWidget.getLabelManager() != null && nodeWidget.getLabelManager().isVisible() && nodeWidget.getLabelManager().getLabel() != null && !nodeWidget.getLabelManager().getLabel().trim().isEmpty()) {
                Rectangle bound = nodeWidget.getLabelManager().getLabelWidget().getParentWidget().getPreferredBounds();
                bound = nodeWidget.getLabelManager().getLabelWidget().getParentWidget().convertLocalToScene(bound);

                Rectangle rec_label = new Rectangle(bound.x, bound.y, (int) bound.getWidth(), (int) bound.getHeight());

                BPMNLabel label = new BPMNLabel();
                label.setBounds(new Bounds(rec_label));
                shape.setBPMNLabel(label);
            }
            plane.addDiagramElement(shape);
            ShapeDesign shapeDesign = null;// BPMNShapeDesign XML Location Change Here
            if (nodeWidget instanceof CollaborationNodeWidget) {
                TCollaborationNode collaborationNode = (TCollaborationNode) ((CollaborationNodeWidget) nodeWidget).getBaseElementSpec();
                if (collaborationNode.getExtensionElements() == null) {
                    collaborationNode.setExtensionElements(new TExtensionElements());
                }
                TExtensionElements extensionElements = collaborationNode.getExtensionElements();
                for (Object obj : extensionElements.getAny()) {
                    if (obj instanceof Element) { //first time save
                        Element element = (Element) obj;
                        if ("ShapeDesign".equals(element.getNodeName())) {
                            shapeDesign = getBPMNShapeDesign(nodeWidget);
                            extensionElements.getAny().remove(obj);
                            extensionElements.getAny().add(shapeDesign);
                            break;
                        }
                    } else if (obj instanceof ShapeDesign) {
                        shapeDesign = getBPMNShapeDesign(nodeWidget);
                        extensionElements.getAny().remove(obj);
                        extensionElements.getAny().add(shapeDesign);
                        break;
                    }
                }
            }

            if (shapeDesign == null) {
                if (nodeWidget instanceof CollaborationNodeWidget) {
                    TCollaborationNode collaborationNode = (TCollaborationNode) ((CollaborationNodeWidget) nodeWidget).getBaseElementSpec();
                    TExtensionElements extensionElements = collaborationNode.getExtensionElements();
                    shapeDesign = getBPMNShapeDesign(nodeWidget);
                    extensionElements.getAny().add(shapeDesign);
                }
            }
//            shape.setBpmnShapeDesign(getBPMNShapeDesign(nodeWidget));
//            if (nodeWidget instanceof SubProcessWidget) {
//                SubProcessWidget subProcessWidget = (SubProcessWidget) nodeWidget;
//                for (FlowElementWidget flowElementChildrenWidget : subProcessWidget.getFlowElements()) {
//                    updateDiagramFlowElement(plane, (Widget) flowElementChildrenWidget);
//                }
//            }
        } else if (widget instanceof EdgeWidget) {
            EdgeWidget edgeWidget = (EdgeWidget) widget;
            BPMNEdge edge = new BPMNEdge();
            for (java.awt.Point point : edgeWidget.getControlPoints()) {
                edge.addWaypoint(point);
            }
            edge.setBpmnElement(((BaseElementWidget) edgeWidget).getId());
            edge.setId(((BaseElementWidget) edgeWidget).getId() + "_gui");

            if (widget instanceof ConversationLinkWidget || widget instanceof MessageFlowWidget) { // AssociationWidget has no label
                if (edgeWidget.getLabelManager() != null && edgeWidget.getLabelManager().isVisible() && edgeWidget.getLabelManager().getLabel() != null && !edgeWidget.getLabelManager().getLabel().trim().isEmpty()) {
                    Rectangle bound = edgeWidget.getLabelManager().getLabelWidget().getParentWidget().getPreferredBounds();
                    bound = edgeWidget.getLabelManager().getLabelWidget().getParentWidget().convertLocalToScene(bound);

                    Rectangle rec = new Rectangle(bound.x, bound.y, (int) bound.getWidth(), (int) bound.getHeight());

                    BPMNLabel label = new BPMNLabel();
                    label.setBounds(new Bounds(rec));
                    edge.setBPMNLabel(label);
                }
            }
            plane.addDiagramElement(edge);

        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }

    }

    public static void updateBPMNDiagram(ModelerFile file) {
        BPMNPlane plane = ((BPMNDiagram) file.getDiagramElement()).getBPMNPlane();
        plane.getDiagramElement().clear();
        IModelerScene processScene = file.getModelerScene();
        for (IBaseElementWidget flowElementWidget : processScene.getBaseElements()) {
            updateDiagramFlowElement(plane, (Widget) flowElementWidget);
        }
    }

    /*---------------------------------Save File End---------------------------------*/
    @Override
    public Anchor getAnchor(INodeWidget inodeWidget) {
        NodeWidget nodeWidget = (NodeWidget) inodeWidget;
        Anchor sourceAnchor;
        if (nodeWidget instanceof CollaborationNodeWidget) {
            if (nodeWidget instanceof ConversationNodeWidget) {
                sourceAnchor = new CustomPathAnchor(nodeWidget, true);
            } else if (nodeWidget instanceof ParticipantWidget) {
                sourceAnchor = new CustomRectangularAnchor(nodeWidget, 0, true);
            } else {
                throw new InvalidElmentException("Invalid BPMN Conversation Element : " + nodeWidget);
            }
        } else if (nodeWidget instanceof ArtifactWidget) {
            sourceAnchor = new CustomRectangularAnchor(nodeWidget, 0, true);
        } else {
            throw new InvalidElmentException("Invalid BPMN Conversation Element : " + nodeWidget);
        }

        return sourceAnchor;
    }

    @Override
    public void transformNode(IFlowNodeWidget flowNodeWidget, IModelerDocument document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
