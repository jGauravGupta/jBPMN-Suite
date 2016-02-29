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
package org.netbeans.jbpmn.modeler.specification.bpmn.model.process.util;

import com.google.gson.Gson;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.jbpmn.core.widget.ActivityWidget;
import org.netbeans.jbpmn.core.widget.ArtifactWidget;
import org.netbeans.jbpmn.core.widget.AssociationWidget;
import org.netbeans.jbpmn.core.widget.BaseElementWidget;
import org.netbeans.jbpmn.core.widget.BoundaryEventWidget;
import org.netbeans.jbpmn.core.widget.EventWidget;
import org.netbeans.jbpmn.core.widget.FlowElementWidget;
import org.netbeans.jbpmn.core.widget.FlowNodeWidget;
import org.netbeans.jbpmn.core.widget.GatewayWidget;
import org.netbeans.jbpmn.core.widget.SequenceFlowWidget;
import org.netbeans.jbpmn.core.widget.SubProcessWidget;
import org.netbeans.jbpmn.modeler.specification.bpmn.util.BPMNModelUtil;
import org.netbeans.jbpmn.modeler.widget.properties.resource.ResourceRoleDialog;
import org.netbeans.jbpmn.spec.BPMNDiagram;
import org.netbeans.jbpmn.spec.BPMNEdge;
import org.netbeans.jbpmn.spec.BPMNLabel;
import org.netbeans.jbpmn.spec.BPMNPlane;
import org.netbeans.jbpmn.spec.BPMNShape;
import org.netbeans.jbpmn.spec.Bounds;
import org.netbeans.jbpmn.spec.DiagramElement;
import org.netbeans.jbpmn.spec.TAssociation;
import org.netbeans.jbpmn.spec.TBoundaryEvent;
import org.netbeans.jbpmn.spec.TCollaboration;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TEvent;
import org.netbeans.jbpmn.spec.TEventDefinition;
import org.netbeans.jbpmn.spec.TExtensionElements;
import org.netbeans.jbpmn.spec.TFlowNode;
import org.netbeans.jbpmn.spec.TFormalExpression;
import org.netbeans.jbpmn.spec.TGroup;
import org.netbeans.jbpmn.spec.THumanPerformer;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TParticipant;
import org.netbeans.jbpmn.spec.TPerformer;
import org.netbeans.jbpmn.spec.TPotentialOwner;
import org.netbeans.jbpmn.spec.TProcess;
import org.netbeans.jbpmn.spec.TProperty;
import org.netbeans.jbpmn.spec.TResource;
import org.netbeans.jbpmn.spec.TResourceAssignmentExpression;
import org.netbeans.jbpmn.spec.TResourceRole;
import org.netbeans.jbpmn.spec.TRootElement;
import org.netbeans.jbpmn.spec.TSequenceFlow;
import org.netbeans.jbpmn.spec.TSubProcess;
import org.netbeans.jbpmn.spec.TTextAnnotation;
import org.netbeans.jbpmn.spec.TTransaction;
import org.netbeans.jbpmn.spec.extend.LanguageManager;
import org.netbeans.jbpmn.spec.extend.ResourceRoleHandler;
import org.netbeans.modeler.anchors.CustomCircularAnchor;
import org.netbeans.modeler.anchors.CustomPathAnchor;
import org.netbeans.modeler.anchors.CustomRectangularAnchor;
import org.netbeans.modeler.config.document.IModelerDocument;
import org.netbeans.modeler.config.document.ModelerDocumentFactory;
import org.netbeans.modeler.config.palette.SubCategoryNodeConfig;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.exception.InvalidElmentException;
import org.netbeans.modeler.core.exception.ModelerException;
import org.netbeans.modeler.properties.nentity.Column;
import org.netbeans.modeler.properties.nentity.NAttributeEntity;
import org.netbeans.modeler.properties.nentity.NEntityDataListener;
import org.netbeans.modeler.properties.nentity.NEntityPropertySupport;
import org.netbeans.modeler.properties.nentity.Table;
import org.netbeans.modeler.shape.Border;
import org.netbeans.modeler.shape.GradientPaint;
import org.netbeans.modeler.shape.InnerShapeContext;
import org.netbeans.modeler.shape.OuterShapeContext;
import org.netbeans.modeler.shape.ShapeDesign;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.INModelerScene;
import org.netbeans.modeler.specification.model.document.core.IArtifact;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.core.IFlowElement;
import org.netbeans.modeler.specification.model.document.core.IFlowNode;
import org.netbeans.modeler.specification.model.document.widget.IBaseElementWidget;
import org.netbeans.modeler.specification.model.document.widget.IFlowNodeWidget;
import org.netbeans.modeler.specification.model.document.widget.IModelerSubScene;
import org.netbeans.modeler.validation.jaxb.ValidateJAXB;
import org.netbeans.modeler.widget.edge.EdgeWidget;
import org.netbeans.modeler.widget.edge.IEdgeWidget;
import org.netbeans.modeler.widget.edge.info.EdgeWidgetInfo;
import org.netbeans.modeler.widget.node.INodeWidget;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.image.NodeImageWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.openide.nodes.PropertySupport;
import org.openide.util.Exceptions;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.w3c.dom.Element;

public class BPMNProcessUtil extends BPMNModelUtil {

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
    JAXBContext bpmnProcessContext;
    Unmarshaller bpmnProcessUnmarshaller;
    Marshaller bpmnProcessMarshaller;
    InputOutput io = IOProvider.getDefault().getIO("jBPMN Console", false);

    @Override
    public void loadModelerFile(ModelerFile file) {
        try {
            IModelerScene scene = file.getModelerScene();
            File savedFile = file.getFile();
//            savedFile.getTotalSpace()
            if (bpmnProcessContext == null) {
                bpmnProcessContext = JAXBContext.newInstance(new Class<?>[]{ShapeDesign.class, TDefinitions.class});
            }
            if (bpmnProcessUnmarshaller == null) {
                bpmnProcessUnmarshaller = bpmnProcessContext.createUnmarshaller();
            }
            bpmnProcessUnmarshaller.setEventHandler(new ValidateJAXB());
            TDefinitions definition_Load = bpmnProcessUnmarshaller.unmarshal(new StreamSource(savedFile), TDefinitions.class).getValue();

            Set<String> noneTypeProcess = new HashSet<String>();
            Set<String> allProcess = new HashSet<String>();
            Set<String> mainProcess;

            for (TRootElement element : definition_Load.getRootElement()) {
                if (element instanceof TCollaboration) {
                    TCollaboration collaboration = ((TCollaboration) element);
                    for (TParticipant participant : collaboration.getParticipant()) {
                        noneTypeProcess.add(participant.getProcessRef());
                    }
                }
                if (element instanceof TProcess) {
                    TProcess process_Load = (TProcess) element;
                    allProcess.add(process_Load.getId());
                }
            }
            mainProcess = new HashSet<String>(allProcess);
            mainProcess.removeAll(noneTypeProcess);

            TProcess process = new TProcess();

            for (String processId : mainProcess) {
                TProcess tmpProcess = definition_Load.getProcess(processId);
                if (tmpProcess != null) {
                    String name = tmpProcess.getName();
                    if (name != null && !name.trim().isEmpty()) {
                        process.setName(name);
                    }
                    String id = tmpProcess.getId();
                    if (id != null && !id.trim().isEmpty()) {
                        process.setId(id);
                    }
                    process.getProperty().addAll(tmpProcess.getProperty());
                    process.getFlowElement().addAll(tmpProcess.getFlowElement());
                    process.getArtifact().addAll(tmpProcess.getArtifact());
                    definition_Load.getRootElement().remove(tmpProcess);
                }
            }
            definition_Load.getRootElement().add(process);
            scene.setRootElementSpec(process);

            BPMNDiagram diagram = new BPMNDiagram();
            diagram.setId(NBModelerUtil.getAutoGeneratedStringId());
            BPMNPlane plane = new BPMNPlane();
            plane.setId(NBModelerUtil.getAutoGeneratedStringId());
            diagram.setBPMNPlane(plane);
//        plane.setBpmnElement(process.getId());

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
            file.getModelerDiagramModel().setRootElement(process);
            file.getModelerDiagramModel().setDiagramElement(diagram);

//ELEMENT_UPGRADE
            for (IFlowElement flowElement_Load : new CopyOnWriteArrayList<IFlowElement>(process.getFlowElement())) {
                loadFlowNode(scene, (Widget) scene, flowElement_Load);
            }

            for (IFlowElement flowElement_Load : new CopyOnWriteArrayList<IFlowElement>(process.getFlowElement())) {
                loadBoundaryEvent(scene, flowElement_Load);
            }

            for (IFlowElement flowElement_Load : new CopyOnWriteArrayList<IFlowElement>(process.getFlowElement())) {
                loadEdge(scene, flowElement_Load);
            }

            for (IArtifact artifact_Load : new CopyOnWriteArrayList<IArtifact>(process.getArtifact())) {
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

    private void loadFlowNode(IModelerScene scene, Widget parentWidget, IFlowElement flowElement) {
        IModelerDocument document = null;
        ModelerDocumentFactory modelerDocumentFactory = scene.getModelerFile().getVendorSpecification().getModelerDocumentFactory();
        if (flowElement instanceof TFlowNode) {
            try {
                if (flowElement instanceof TSubProcess) {
                    if (flowElement instanceof TTransaction) {
                        document = modelerDocumentFactory.getModelerDocument(flowElement);
                    } else {
                        if (((TSubProcess) flowElement).getTriggeredByEvent()) {
                            document = modelerDocumentFactory.getModelerDocument(flowElement, "triggeredByEvent");
                        } else {
                            document = modelerDocumentFactory.getModelerDocument(flowElement);
                        }
                    }
                } else if (flowElement instanceof TEvent) {
                    String definitionClass = null;
                    TEventDefinition eventDefinition = getEventDefinition((TEvent) flowElement);
                    if (eventDefinition != null) {
                        definitionClass = eventDefinition.getClass().getSimpleName();
                    }
                    document = modelerDocumentFactory.getModelerDocument(flowElement, definitionClass);
                } else {
                    document = modelerDocumentFactory.getModelerDocument(flowElement);
                }
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
            //clear incomming & outgoing it will added on sequenceflow auto connection
            ((TFlowNode) flowElement).getIncoming().clear();
            ((TFlowNode) flowElement).getOutgoing().clear();

            if (parentWidget instanceof SubProcessWidget) {
                // Move FlowSpec from SubProcesss To Process because it will be moved from Process to SubProcess in next call SubProcessWidget.addFlowNodeWidget
                ((TSubProcess) ((SubProcessWidget) parentWidget).getBaseElementSpec()).removeFlowElement(flowElement);
                scene.getRootElementSpec().addBaseElement(flowElement);

                ((SubProcessWidget) parentWidget).addFlowNodeWidget((FlowNodeWidget) nodeWidget);
            }

            if (flowElement instanceof TSubProcess) {
                for (IFlowElement flowElementChild : new CopyOnWriteArrayList<IFlowElement>(((TSubProcess) flowElement).getFlowElement())) {
                    loadFlowNode(scene, (FlowNodeWidget) nodeWidget, flowElementChild);
                }
            }

        }
    }

    private void loadBoundaryEvent(IModelerScene scene, IFlowElement flowElement) {
        if (flowElement instanceof TBoundaryEvent) {
            TBoundaryEvent boundaryEvent = (TBoundaryEvent) flowElement;
            BoundaryEventWidget boundaryEventWidget = (BoundaryEventWidget) scene.getBaseElement(boundaryEvent.getId());
            ActivityWidget activityWidget = (ActivityWidget) scene.getBaseElement(boundaryEvent.getAttachedToRef());
            boundaryEventWidget.setActivityWidget(activityWidget);
            activityWidget.addBoundaryEventWidget(boundaryEventWidget);
        } else if (flowElement instanceof TSubProcess) {
            for (IFlowElement flowElementChild : ((TSubProcess) flowElement).getFlowElement()) {
                loadBoundaryEvent(scene, flowElementChild);
            }
        }
    }

    private void loadEdge(IModelerScene scene, IBaseElement baseElement) {
        EdgeWidgetInfo edgeWidgetInfo = new EdgeWidgetInfo();
        edgeWidgetInfo.setId(baseElement.getId());
        edgeWidgetInfo.setExist(Boolean.TRUE);//to Load BPMN
        edgeWidgetInfo.setBaseElementSpec(baseElement);
        if (baseElement instanceof TSequenceFlow) {
            TSequenceFlow sequenceFlow = (TSequenceFlow) baseElement;
            edgeWidgetInfo.setName(((TSequenceFlow) baseElement).getName());
            edgeWidgetInfo.setType("SEQUENCEFLOW");

            NodeWidget sourceNodeWidget = (NodeWidget) scene.findBaseElement(sequenceFlow.getSourceRef());//REMOVE_PRE  must be getFlowElement
            NodeWidget targetNodeWidget = (NodeWidget) scene.findBaseElement(sequenceFlow.getTargetRef());//REMOVE_PRE  must be getFlowElement
            edgeWidgetInfo.setSource(sourceNodeWidget.getNodeWidgetInfo().getId());
            edgeWidgetInfo.setTarget(targetNodeWidget.getNodeWidgetInfo().getId());

            // edge.setName("C" + ((BPMNScene)scene).getEdgeCounter());
            IEdgeWidget edgeWidget = scene.createEdgeWidget(edgeWidgetInfo);
            if (((TSequenceFlow) baseElement).getName() != null) {
                edgeWidget.setLabel(((TSequenceFlow) baseElement).getName());
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

        } else if (baseElement instanceof TSubProcess) {
            for (IFlowElement flowElementChild : ((TSubProcess) baseElement).getFlowElement()) {
                loadEdge(scene, flowElementChild);
            }
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
//            nodeWidget.setLabel(/*No Name Exist for Artifact*/);

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

                    if (nodeWidget instanceof IFlowNodeWidget && ((IFlowNodeWidget) nodeWidget).getFlowElementsContainer() instanceof SubProcessWidget) {
                        SubProcessWidget parentWidget = (SubProcessWidget) ((IFlowNodeWidget) nodeWidget).getFlowElementsContainer();
                        BPMNShape parentShape = (BPMNShape) diagram.getBPMNPlane().getBPMNShape(parentWidget.getBaseElementSpec().getId());
                        Bounds parentbounds = parentShape.getBounds();
                        Point location = new Point((int) (bounds.getX() - parentbounds.getX()), (int) (bounds.getY() - parentbounds.getY()));
                        nodeWidget.setPreferredLocation(nodeWidget.convertSceneToLocal(location));
                    } else {
                        Point location = new Point((int) bounds.getX(), (int) bounds.getY());
                        nodeWidget.setPreferredLocation(location);
                    }

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
                    if (nodeWidget instanceof FlowNodeWidget) {
                        TFlowNode flowNode = (TFlowNode) ((FlowNodeWidget) nodeWidget).getBaseElementSpec();
                        TExtensionElements extensionElements = flowNode.getExtensionElements();
                        if (extensionElements != null) {
                            for (Object obj : extensionElements.getAny()) {
                                if (obj instanceof Element) { //if ShapeDesign is not in JAXB Context
                                    Element element = (Element) obj;
                                    if ("ShapeDesign".equals(element.getNodeName())) {
                                        try {
                                            shapeDesign = bpmnProcessUnmarshaller.unmarshal((Element) extensionElements.getAny().get(0), ShapeDesign.class).getValue();
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
            Widget widget = (Widget) scene.getBaseElement(edge.getBpmnElement());
            if (widget != null && widget instanceof EdgeWidget) {
                if (widget instanceof SequenceFlowWidget) {
                    SequenceFlowWidget sequenceFlowWidget = (SequenceFlowWidget) widget;
                    sequenceFlowWidget.setControlPoints(edge.getWaypointCollection(), true);
                    if (edge.getBPMNLabel() != null) {
                        Bounds bound = edge.getBPMNLabel().getBounds();
//                        sequenceFlowWidget.getLabelManager().getLabelWidget().getParentWidget().setPreferredLocation(bound.toPoint());
                        sequenceFlowWidget.getLabelManager().getLabelWidget().getParentWidget().setPreferredLocation(
                                sequenceFlowWidget.getLabelManager().getLabelWidget().convertSceneToLocal(bound.toPoint()));
                    }
                } else if (widget instanceof AssociationWidget) {
                    AssociationWidget associationWidget = (AssociationWidget) widget;
                    associationWidget.setControlPoints(edge.getWaypointCollection(), true);
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
//                EdgeWidget edgeWidget = (EdgeWidget)widget;
//                edgeWidget.manageControlPoint();

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
//        final RequestProcessor.Task theTask = RP.create(runnable);
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

            if (bpmnProcessContext == null) {
                bpmnProcessContext = JAXBContext.newInstance(new Class<?>[]{ShapeDesign.class, TDefinitions.class, TProperty.class});
            }
            if (bpmnProcessMarshaller == null) {
                bpmnProcessMarshaller = bpmnProcessContext.createMarshaller();
            }

            // output pretty printed
            bpmnProcessMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            bpmnProcessMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.omg.org/spec/BPMN/20100524/MODEL http://www.omg.org/spec/BPMN/2.0/20100501/BPMN20.xsd");
//            bpmnProcessMarshaller.setProperty("com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler", new CharacterEscapeHandler() { // property required for CDATA
//                @Override
//                public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
//                    writer.write(ac, i, j);
//                }
//            });

//            bpmnProcessMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
//            bpmnProcessMarshaller.marshal(file.getDefinitionElement(), savedFile);
//            com.sun.xml.bind.marshaller.NamespacePrefixMapper mapper = new com.sun.xml.bind.marshaller.NamespacePrefixMapper() {
//                public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
//                    if ("http://www.omg.org/spec/BPMN/20100524/MODEL".equals(namespaceUri) && !requirePrefix) {
//                        return "";
//                    }
//                    return "ns";
//                }
//            };
//            bpmnProcessMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
            bpmnProcessMarshaller.setEventHandler(new ValidateJAXB());
            bpmnProcessMarshaller.marshal(file.getDefinitionElement(), System.out);
            StringWriter sw = new StringWriter();
            bpmnProcessMarshaller.marshal(file.getDefinitionElement(), sw);

            FileUtils.writeStringToFile(savedFile, sw.toString().replaceFirst("xmlns:ns[A-Za-z\\d]{0,3}=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"",
                    "xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\""));

//            NamespacePrefixMapper prefixMapper = new BPMNNamespacePrefixMapper();
//jaxbMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", prefixMapper);
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
        shapeDesign.beforeMarshal();
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
            if (nodeWidget instanceof FlowNodeWidget) {
                TFlowNode flowNode = (TFlowNode) ((FlowNodeWidget) nodeWidget).getBaseElementSpec();
                if (flowNode.getExtensionElements() == null) {
                    flowNode.setExtensionElements(new TExtensionElements());
                }
                TExtensionElements extensionElements = flowNode.getExtensionElements();
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
                if (nodeWidget instanceof FlowNodeWidget) {
                    TFlowNode flowNode = (TFlowNode) ((FlowNodeWidget) nodeWidget).getBaseElementSpec();
                    TExtensionElements extensionElements = flowNode.getExtensionElements();
                    shapeDesign = getBPMNShapeDesign(nodeWidget);
                    extensionElements.getAny().add(shapeDesign);
                }
            }

//            shape.setBpmnShapeDesign(getBPMNShapeDesign(nodeWidget));
            if (nodeWidget instanceof SubProcessWidget) {
                SubProcessWidget subProcessWidget = (SubProcessWidget) nodeWidget;
                for (FlowElementWidget flowElementChildrenWidget : subProcessWidget.getFlowElements()) {
                    updateDiagramFlowElement(plane, (Widget) flowElementChildrenWidget);
                }
            }

        } else if (widget instanceof EdgeWidget) {
            EdgeWidget edgeWidget = (EdgeWidget) widget;
            BPMNEdge edge = new BPMNEdge();
            for (java.awt.Point point : edgeWidget.getControlPoints()) {
                edge.addWaypoint(point);
            }
            edge.setBpmnElement(((BaseElementWidget) edgeWidget).getId());
            edge.setId(((BaseElementWidget) edgeWidget).getId() + "_gui");

            if (widget instanceof SequenceFlowWidget) {
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
//        NodeWidgetInfo nodeWidgetInfo = nodeWidget.getNodeWidgetInfo();
        if (nodeWidget instanceof FlowNodeWidget) {
            if (nodeWidget instanceof EventWidget) {
                sourceAnchor = new CustomCircularAnchor(nodeWidget);//, (int) nodeWidgetInfo.getDimension().getWidth() / 2);
            } else if (nodeWidget instanceof ActivityWidget) {
                sourceAnchor = new CustomRectangularAnchor(nodeWidget, 0, true);
            } else if (nodeWidget instanceof GatewayWidget) {
                sourceAnchor = new CustomPathAnchor(nodeWidget, true);
            } else {
                throw new InvalidElmentException("Invalid BPMN Process Element : " + nodeWidget);
            }
        } else if (nodeWidget instanceof ArtifactWidget) {
            sourceAnchor = new CustomRectangularAnchor(nodeWidget, 0, true);
        } else {
            throw new InvalidElmentException("Invalid BPMN Process Element : " + nodeWidget);
        }
        return sourceAnchor;
    }

    public static TEventDefinition getEventDefinition(TEvent event) {
        List<TEventDefinition> eventDefinitions = event.getEventDefinition();
        TEventDefinition eventDefinition = null;
        if (eventDefinitions.isEmpty()) {
            eventDefinition = null;
        } else if (eventDefinitions.size() == 1) {
            eventDefinition = eventDefinitions.get(0);
        } else if (eventDefinitions.size() > 1) {  // Temp Solution select 1 st event def untill multiple is not supported
            //Multiple Pending
            eventDefinition = eventDefinitions.get(0);
        }

        return eventDefinition;
    }

    @Override
    public void transformNode(IFlowNodeWidget flowNodeWidget, IModelerDocument document) {
        IModelerScene scene = flowNodeWidget.getModelerScene();

        NodeWidget sourceNodeWidget = (NodeWidget) flowNodeWidget;
        NodeWidgetInfo sourceNodeWidgetInfo = sourceNodeWidget.getNodeWidgetInfo();
        NodeWidgetInfo targetNodeWidgetInfo = null;

        targetNodeWidgetInfo = sourceNodeWidgetInfo.cloneNodeWidgetInfo();

        targetNodeWidgetInfo.setExist(Boolean.FALSE);
        SubCategoryNodeConfig subCategoryNodeConfig = scene.getModelerFile().getVendorSpecification().getPaletteConfig().findSubCategoryNodeConfig(document);

        targetNodeWidgetInfo.setSubCategoryNodeConfig(subCategoryNodeConfig);

        INodeWidget targetNodeWidget = scene.createNodeWidget(targetNodeWidgetInfo);
        try {
            BeanUtils.copyProperties(((IFlowNodeWidget) targetNodeWidget).getBaseElementSpec(), ((IFlowNodeWidget) sourceNodeWidget).getBaseElementSpec());
        } catch (IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
        } catch (InvocationTargetException ex) {
            Exceptions.printStackTrace(ex);
        }

        ((IFlowNode) ((IFlowNodeWidget) targetNodeWidget).getBaseElementSpec()).getIncoming().clear();
        ((IFlowNode) ((IFlowNodeWidget) targetNodeWidget).getBaseElementSpec()).getOutgoing().clear();
        //clear incoming and outgoing reference because it is reconnected by following using visual api

        /* BUG : On transform , widget is Selected with resize border then [NodeWidget + border] width is calculated as bound */
        /*BUG Fix Start : Hide Resize border of all selected NodeWidget*/
        sourceNodeWidget.hideResizeBorder();
        scene.validate();
        /*BUG Fix End*/
        Rectangle bound = sourceNodeWidget.getSceneViewBound();
        Point location = sourceNodeWidget.getPreferredLocation();
        targetNodeWidgetInfo.setDimension(new Dimension(bound.width, bound.height));
        ((NodeWidget) targetNodeWidget).getNodeImageWidget().setDimension(new Dimension(bound.width, bound.height));

        if (((IFlowNodeWidget) sourceNodeWidget).getFlowElementsContainer() instanceof IModelerSubScene) {
            IModelerSubScene modelerSubScene = (IModelerSubScene) ((IFlowNodeWidget) sourceNodeWidget).getFlowElementsContainer();
            ((NodeWidget) targetNodeWidget).setPreferredLocation(modelerSubScene.convertLocalToScene(location));
            ((SubProcessWidget) modelerSubScene).moveFlowNodeWidget((FlowNodeWidget) targetNodeWidget);
        } else {
            targetNodeWidget.setPreferredLocation(location);
        }

        if (flowNodeWidget instanceof FlowNodeWidget) {
            for (SequenceFlowWidget sequenceFlowWidget : new CopyOnWriteArrayList<SequenceFlowWidget>(((FlowNodeWidget) flowNodeWidget).getIncomingSequenceFlows())) {
                NBModelerUtil.dettachEdgeTargetAnchor(scene, sequenceFlowWidget, sourceNodeWidget);
                NBModelerUtil.attachEdgeTargetAnchor(scene, sequenceFlowWidget, targetNodeWidget);
            }
            for (SequenceFlowWidget sequenceFlowWidget : new CopyOnWriteArrayList<SequenceFlowWidget>(((FlowNodeWidget) flowNodeWidget).getOutgoingSequenceFlows())) {
                NBModelerUtil.dettachEdgeSourceAnchor(scene, sequenceFlowWidget, sourceNodeWidget);
                NBModelerUtil.attachEdgeSourceAnchor(scene, sequenceFlowWidget, targetNodeWidget);
            }
        }

        String name = ((IFlowNode) ((IFlowNodeWidget) targetNodeWidget).getBaseElementSpec()).getName();
        if (name != null && !name.trim().isEmpty()) {
            ((INodeWidget) targetNodeWidget).setLabel(name);
        }

        sourceNodeWidget.remove();
//        scene.revalidate();
        scene.validate();

    }

    /**
     * ------- TResourceRole Start -------- *
     */
    public static PropertySupport addTResourceRole(IModelerScene modelerScene, IBaseElement baseElementSpec) {
        final ModelerFile modelerFile = modelerScene.getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();
        final NAttributeEntity attributeEntity = new NAttributeEntity("TResourceRole", "Resource Role", "Resource Role Desc");
        attributeEntity.setCountDisplay(new String[]{"No Resource Roles set", "One Resource Role set", "Resource Roles set"});
        final ResourceRoleHandler resourceRoleHandler = (ResourceRoleHandler) baseElementSpec;
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class, 0));
        columns.add(new Column("Type", false, String.class, 130, new Object[]{"Performer", "Human Performer", "Potential Owner"}));
        columns.add(new Column("Resource", false, String.class, 170));
        columns.add(new Column("Language", false, String.class, 75));
        columns.add(new Column("Resource Assignment expression", false, String.class, 300));
        columns.add(new Column("Evaluates to Type", false, String.class, 125));
        attributeEntity.setColumns(columns);
        attributeEntity.setTable(new Table(800, 400));
        attributeEntity.setCustomDialog(new ResourceRoleDialog(modelerFile));//TODO : MAINTAIN EntityComponent row to entity converter
        attributeEntity.setTableDataListener(new NEntityDataListener() {
            List<Object[]> data;
            List<String> resourceRoles_ORG = new ArrayList<String>();
            int count;

            @Override
            public void initCount() {
                count = resourceRoleHandler.getResourceRole().size();
            }

            @Override
            public int getCount() {
                return count;
            }

            @Override
            public void initData() {
                Gson json = new Gson();
                List<? extends TResourceRole> resourceRoles = resourceRoleHandler.getResourceRole();
                List<Object[]> data_local = new LinkedList<Object[]>();
                Iterator<? extends TResourceRole> itr = resourceRoles.iterator();
                while (itr.hasNext()) {
                    TResourceRole resourceRole = itr.next();
                    resourceRoles_ORG.add(json.toJson(resourceRole));
                    Object[] row = new Object[attributeEntity.getColumns().size()];
                    row[0] = resourceRole;

                    if (resourceRole.getClass() == TPotentialOwner.class) {
                        row[1] = "Potential Owner";
                    } else if (resourceRole.getClass() == THumanPerformer.class) {
                        row[1] = "Human Performer";
                    } else if (resourceRole.getClass() == TPerformer.class) {
                        row[1] = "Performer";
                    }

                    TResource resource = (TResource) definition.getRootElement(resourceRole.getResourceRef(), TResource.class);
                    row[2] = resource != null ? resource.getName() : null;

                    if (resourceRole.getResourceAssignmentExpression() == null) {
                        TResourceAssignmentExpression resourceAssignmentExp = new TResourceAssignmentExpression();
                        resourceRole.setResourceAssignmentExpression(resourceAssignmentExp);
                    }
                    if (resourceRole.getResourceAssignmentExpression().getExpression() == null) {
                        resourceRole.getResourceAssignmentExpression().setExpression(new TFormalExpression());
                    }

                    row[3] = LanguageManager.getLanguage(resourceRole.getResourceAssignmentExpression().getExpression().getLanguage());

                    row[4] = resourceRole.getResourceAssignmentExpression().getExpression().getContent();
                    TItemDefinition itemDefinition = (TItemDefinition) definition.getRootElement(resourceRole.getResourceAssignmentExpression().getExpression().getEvaluatesToTypeRef(), TItemDefinition.class);
                    row[5] = itemDefinition == null ? null : itemDefinition.getDisplayValue();

                    data_local.add(row);
                }
                this.data = data_local;
            }

            @Override
            public List<Object[]> getData() {
                return data;
            }

            @Override
            public void setData(List<Object[]> data) {
                List<TResourceRole> resourceRoles = new ArrayList<TResourceRole>();
                for (Object[] row : data) {
                    TResourceRole resourceRole = (TResourceRole) row[0];
                    resourceRoles.add(resourceRole);
                }
                resourceRoleHandler.setResourceRole(resourceRoles);
                this.data = data;
            }
        });
        return new NEntityPropertySupport(modelerScene.getModelerFile(), attributeEntity);
    }

    /**
     * ------- TResourceRole End -------- *
     */
}
