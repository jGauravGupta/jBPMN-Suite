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
package org.netbeans.jbpmn.core.widget;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.scene.BPMNProcessScene;
import org.netbeans.jbpmn.spec.TArtifact;
import org.netbeans.jbpmn.spec.TFlowNode;
import org.netbeans.jbpmn.spec.TSequenceFlow;
import org.netbeans.jbpmn.spec.TSubProcess;
import org.netbeans.modeler.config.document.FlowDimensionType;
import org.netbeans.modeler.config.palette.SubCategoryNodeConfig;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.exception.InvalidElmentException;
import org.netbeans.modeler.scene.AbstractModelerScene;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.IRootElement;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.widget.IBaseElementWidget;
import org.netbeans.modeler.specification.model.document.widget.IModelerSubScene;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.image.NodeImageWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;

public class SubProcessWidget extends ActivityWidget implements IModelerSubScene {

    public SubProcessWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);

        setLayout(LayoutFactory.createAbsoluteLayout());
        setCheckClipping(true); // required to hide the content of details widget beyond its border

//            this.getNodeImageWidget().setResizeHandler(new SVGResizeHandler(){
//           @Override
//            public void onResize(SVGDocument svgDocument,ResizeType resizeType,Double documentWidth ,Double documentHeight,Double scaleX ,Double scaleY ){
//                if (resizeType == ResizeType.OUTER) {//for annotation
//                    String w2 = new Double(new Double(getElementValue(svgDocument, "OUTLINE", "w")) * scaleX).toString();
//                    String h2 = new Double(new Double(getElementValue(svgDocument, "OUTLINE", "h")) * scaleY).toString();
//                    SubProcessWidget.this.setElementValue(svgDocument, "OUTLINE", "width", w2);
//                    SubProcessWidget.this.setElementValue(svgDocument, "OUTLINE", "height", h2);
//
//                     svgDocument.getRootElement().setAttribute("width", new Double((documentWidth * scaleX) + 4).toString());
//                     svgDocument.getRootElement().setAttribute("height", new Double((documentHeight * scaleY) + 4).toString());
//
//                    String x_loop = new Double(new Double(getElementValue(svgDocument, "standard", "x")) * scaleX).toString();
//                    String y_loop = new Double(new Double(getElementValue(svgDocument, "standard", "y")) * scaleY).toString();
//                    SubProcessWidget.this.setElementValue(svgDocument, "standard", "transform", "translate(" + x_loop + "," + y_loop + ")");
//                    SubProcessWidget.this.setElementValue(svgDocument, "parallel", "transform", "translate(" + x_loop + "," + y_loop + ")");
//                    SubProcessWidget.this.setElementValue(svgDocument, "sequential", "transform", "translate(" + x_loop + "," + y_loop + ")");
//                    x_loop = new Double(new Double(getElementValue(svgDocument, "compensation", "x")) * scaleX).toString();
//                    SubProcessWidget.this.setElementValue(svgDocument, "compensation", "transform", "translate(" + x_loop + "," + y_loop + ")");
//
//                }
//             }
//           });
    }

    public WidgetAction getAcceptProvider() {
        WidgetAction acceptAction = ActionFactory.createAcceptAction(new ContainerAcceptProvider(this));
        return acceptAction;
    }
    //Replica of FlowElementsContainerScene
    private List<FlowElementWidget> flowElements = new ArrayList<FlowElementWidget>(); // Linked hashmap to preserve order of inserted elements
    private List<ArtifactWidget> artifacts = new ArrayList< ArtifactWidget>(); // Linked hashmap to preserve order of inserted elements

    /**
     * @return the flowElements
     */
    public List<FlowElementWidget> getFlowElements() {
        return flowElements;
    }

    public FlowElementWidget getFlowElement(String id) {
        FlowElementWidget widget = null;
        for (FlowElementWidget flowElementWidget : flowElements) {
            if (flowElementWidget instanceof FlowNodeWidget) {
                if (((FlowNodeWidget) flowElementWidget).getId().equals(id)) {
                    widget = flowElementWidget;
                    break;
                }
            } else if (flowElementWidget instanceof SequenceFlowWidget) {
                if (((SequenceFlowWidget) flowElementWidget).getId().equals(id)) {
                    widget = flowElementWidget;
                    break;
                }
            } else {
                throw new InvalidElmentException("Invalid BPMN Element" + flowElementWidget);
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
     * @param flowElements the flowElements to set
     */
    public void setFlowElements(List<FlowElementWidget> flowElements) {
        this.flowElements = flowElements;
    }

    public void removeFlowElement(FlowElementWidget flowElementWidget) {
        this.flowElements.remove(flowElementWidget);
    }

    public void addFlowElement(FlowElementWidget flowElementWidget) {
        this.flowElements.add(flowElementWidget);
    }

    @Override
    public BaseElementWidget findBaseElement(String id) {
        BaseElementWidget widget = null;
        List<BaseElementWidget> baseElementWidgets = new ArrayList<BaseElementWidget>(flowElements);
        baseElementWidgets.addAll(artifacts);
        for (BaseElementWidget baseElementWidget : baseElementWidgets) {
            if (baseElementWidget instanceof FlowNodeWidget) {
                if (((FlowNodeWidget) baseElementWidget).getId().equals(id)) {
                    widget = baseElementWidget;
                    break;
                } else if (baseElementWidget instanceof SubProcessWidget) {
                    widget = ((SubProcessWidget) baseElementWidget).findBaseElement(id);
                    if (widget != null) {
                        return widget;
                    }
                }
            } else if (baseElementWidget instanceof SequenceFlowWidget) {
                if (((SequenceFlowWidget) baseElementWidget).getId().equals(id)) {
                    widget = baseElementWidget;
                    break;
                }
            } else if (baseElementWidget instanceof ArtifactWidget) {
                if (((ArtifactWidget) baseElementWidget).getId().equals(id)) {
                    widget = baseElementWidget;
                    break;
                }
            } else {
                throw new InvalidElmentException("Invalid BPMN Element" + baseElementWidget);
            }
        }
        return widget;
    }

    @Override
    public void removeBaseElementElement(IBaseElementWidget baseElementWidget) {
        if (baseElementWidget instanceof FlowElementWidget) {
            removeFlowElement((FlowElementWidget) baseElementWidget);
        } else if (baseElementWidget instanceof ArtifactWidget) {
            removeArtifact((ArtifactWidget) baseElementWidget);
        }
    }
    //TBF_CODE

    @Override
    public void addBaseElementElement(IBaseElementWidget baseElementWidget) {
        if (baseElementWidget instanceof FlowElementWidget) {
            addFlowElement((FlowElementWidget) baseElementWidget);
        } else if (baseElementWidget instanceof ArtifactWidget) {
            addArtifact((ArtifactWidget) baseElementWidget);
        }
    }

    public void deleteBaseElement(IBaseElementWidget baseElementWidget) {
//        ModelerFile file = this.getModelerScene().getModelerFile();
        TSubProcess subProcess = (TSubProcess) this.getBaseElementSpec();
        if (baseElementWidget instanceof FlowElementWidget) {
            if (baseElementWidget instanceof FlowNodeWidget) { //reverse ref
                FlowNodeWidget flowNodeWidget = (FlowNodeWidget) baseElementWidget;
                IBaseElement baseElementSpec = flowNodeWidget.getBaseElementSpec();

                List<SequenceFlowWidget> sequenceFlowWidgetList = new ArrayList<SequenceFlowWidget>();
                sequenceFlowWidgetList.addAll(flowNodeWidget.getOutgoingSequenceFlows());
                sequenceFlowWidgetList.addAll(flowNodeWidget.getIncomingSequenceFlows());

                for (SequenceFlowWidget sequenceFlowWidget : sequenceFlowWidgetList) {
                    sequenceFlowWidget.remove();
                }
                if (flowNodeWidget instanceof ActivityWidget) {
                    ActivityWidget activityWidget = (ActivityWidget) flowNodeWidget;
                    for (BoundaryEventWidget boundaryEventWidget : activityWidget.getBoundaryEvents()) {
                        boundaryEventWidget.remove();
                    }
                    if (flowNodeWidget instanceof SubProcessWidget) {
                    }
                }
                subProcess.removeBaseElement(baseElementSpec);
                flowNodeWidget.setFlowElementsContainer(null);
                this.flowElements.remove(flowNodeWidget);
            } else if (baseElementWidget instanceof SequenceFlowWidget) {
                SequenceFlowWidget sequenceFlowWidget = ((SequenceFlowWidget) baseElementWidget);
                TSequenceFlow sequenceFlowSpec = sequenceFlowWidget.getSequenceFlowSpec();
                FlowNodeWidget sourceWidget = sequenceFlowWidget.getSourceNode();
                TFlowNode sourceSpec = (TFlowNode) sourceWidget.getBaseElementSpec();
                FlowNodeWidget targetWidget = sequenceFlowWidget.getTargetNode();
                TFlowNode targetSpec = (TFlowNode) targetWidget.getBaseElementSpec();

                sourceSpec.getOutgoing().remove(sequenceFlowSpec.getId());
                targetSpec.getIncoming().remove(sequenceFlowSpec.getId());

                sourceWidget.getOutgoingSequenceFlows().remove(sequenceFlowWidget);
                targetWidget.getIncomingSequenceFlows().remove(sequenceFlowWidget);

                subProcess.removeFlowElement(sequenceFlowSpec);
                sequenceFlowWidget.setFlowElementsContainer(null);
                this.flowElements.remove(sequenceFlowWidget);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else if (baseElementWidget instanceof ArtifactWidget) {
            ArtifactWidget artifactWidget = (ArtifactWidget) baseElementWidget;
            TArtifact artifactSpec = (TArtifact) artifactWidget.getBaseElementSpec();
            subProcess.removeArtifact(artifactSpec);
            artifactWidget.setFlowElementsContainer(null);
            this.artifacts.remove(artifactWidget);
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }

    }

//    public void createBaseElement(BaseElementWidget baseElementWidget) {
////does not need to create
//  }
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

//    @Override
//    public List<IBaseElementWidget> getBaseElements() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public IBaseElementWidget getBaseElement(String id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public class ContainerAcceptProvider implements /*Scene*/ AcceptProvider {

        private Widget containerWidget = null;

        public ContainerAcceptProvider(SubProcessWidget containerW) {
            containerWidget = containerW;
        }

        @Override
        public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
            ConnectorState retVal = ConnectorState.ACCEPT;
//            SubProcessWidget subProcessWidget = SubProcessWidget.this;
            if (isWidgetMove(transferable)) {
                Widget[] target = new Widget[]{getWidget(transferable)};
                for (Widget curWidget : target) {
                    if (curWidget instanceof BoundaryEventWidget || curWidget instanceof ArtifactWidget) {
                        break;
                    }
                    if (widget == curWidget) {
                        retVal = ConnectorState.REJECT;
                        break;
                    }
                    if (isFullyContained(curWidget) == false) {
                        retVal = ConnectorState.REJECT;
                        break;
                    }

                    if (curWidget instanceof NodeWidget) {
                        NodeWidget nodeWidget = (NodeWidget) curWidget;
                        if (nodeWidget.getNodeWidgetInfo().getModelerDocument().getFlowDimension() == FlowDimensionType.BOUNDARY) {
                            retVal = ConnectorState.REJECT;
                            return retVal;
                        }

                    }
                }
            } else if (isPaletteItem(transferable)) {
                SubCategoryNodeConfig subCategoryInfo = getSubCategory(transferable);
                Image dragImage = subCategoryInfo.getImage();
                JComponent view = containerWidget.getScene().getView();
                Graphics2D g2 = (Graphics2D) view.getGraphics();
                Rectangle visRect = view.getVisibleRect();
                view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);

                point = containerWidget.convertLocalToScene(point);
                g2.drawImage(dragImage,
                        AffineTransform.getTranslateInstance(point.getLocation().getX(),
                                point.getLocation().getY()),
                        null);

                if (subCategoryInfo.getModelerDocument().getFlowDimension() == FlowDimensionType.BOUNDARY) {
                    retVal = ConnectorState.REJECT;
                    return retVal;
                }
                if (subCategoryInfo.getModelerDocument().getDocumentModel().equals(DocumentModelType.ARTIFACT.name())) {
                    retVal = ConnectorState.REJECT;
                    return retVal;
                }

            }

            return retVal;
        }

        @Override
        public void accept(Widget widget, Point point, Transferable transferable) {
            BPMNProcessScene scene = (BPMNProcessScene) widget.getScene();
            SubProcessWidget subProcessWidget = SubProcessWidget.this;
            NodeImageWidget container = subProcessWidget.getNodeImageWidget();
            if (isWidgetMove(transferable)) {
                Widget[] target;
                try {
                    target = new Widget[]{getWidget(transferable)};
                } catch (Exception e) {
                    target = new Widget[0];
                }

                for (Widget curWidget : target) {

                    if (curWidget instanceof BoundaryEventWidget || curWidget instanceof ArtifactWidget) {
                        break;
                    }

                    if (curWidget instanceof SubProcessWidget && subProcessWidget == (SubProcessWidget) curWidget) {  // Self
                        break;
                    }

                    if (widget == curWidget) {
                        break;
                    }
                    if (!isFullyContained(curWidget)) {
                        break;
                    }

                    // curWidget.getParentWidget()== ((BPMNScene)curWidget.getScene()).getInterractionLayer()
                    addFlowNodeWidget((FlowNodeWidget) curWidget);

                }

            } else if (isPaletteItem(transferable)) {
                SubCategoryNodeConfig subCategoryInfo = getSubCategory(transferable);
                if (subCategoryInfo.getModelerDocument().getFlowDimension() == FlowDimensionType.BOUNDARY) {
                    return;
                }
                if (subCategoryInfo.getModelerDocument().getDocumentModel().equals(DocumentModelType.ARTIFACT.name())) {
                    return;
                }
                FlowNodeWidget newNodeWidget = (FlowNodeWidget) scene.createNodeWidget(new NodeWidgetInfo(NBModelerUtil.getAutoGeneratedStringId(), subCategoryInfo, point));
                //   getSceneAnimator().animatePreferredLocation(w, widget.convertLocalToScene(point));
                newNodeWidget.setPreferredLocation(widget.convertLocalToScene(point));

                moveFlowNodeWidget(newNodeWidget);

            }
            container.revalidate();
            scene.validate();

        }
    }

    /* Move FlowNodeWidget from Scene to SubProcess or SubProcess to SubProcess*/
    public void moveFlowNodeWidget(FlowNodeWidget newNodeWidget) {
        BPMNProcessScene scene = (BPMNProcessScene) newNodeWidget.getScene();
        SubProcessWidget subProcessWidget = SubProcessWidget.this;
        NodeImageWidget container = this.getNodeImageWidget();
        /* move scene/subprocess to subprocess */
        newNodeWidget.getParentWidget().removeChild(newNodeWidget);
        Point curPt = newNodeWidget.getPreferredLocation();
        if (curPt == null) {
            curPt = newNodeWidget.getLocation();
        }
        container.addChild(newNodeWidget);
        newNodeWidget.setPreferredLocation(container.convertSceneToLocal(curPt));
        /* move scene to sub process */

        /* manage if dropped partial part is outside container */
        Point localLoc = container.convertLocalToScene(newNodeWidget.getPreferredLocation());
        //localLoc = curWidget.convertLocalToScene(curWidget.getPreferredLocation());
        Rectangle localArea = newNodeWidget.getClientArea();
        Point containerLoc = container.convertLocalToScene(container.getLocation());
        Rectangle containerArea = container.getClientArea();

        int dx = 0, dy = 0;
        if (localLoc.x + localArea.width > containerLoc.x + containerArea.width) {
            dx = (localLoc.x + localArea.width) - (containerLoc.x + containerArea.width);
        }

        if (localLoc.y + localArea.height > containerLoc.y + containerArea.height) {
            dy = (localLoc.y + localArea.height) - (containerLoc.y + containerArea.height);
        }
        int x = (int) localLoc.getX() - dx, y = (int) localLoc.getY() - dy;
        newNodeWidget.getScene().getSceneAnimator().animatePreferredLocation(newNodeWidget, container.convertSceneToLocal(new Point(x, y)));

        /* manage if dropped partial part is outside container */
        /*Manage Widget and Widget Specification Start */
        scene.removeFlowElement(newNodeWidget);
        subProcessWidget.addFlowElement(newNodeWidget);
        newNodeWidget.setFlowElementsContainer(subProcessWidget);

        IRootElement rootElementSpec = scene.getRootElementSpec();
        TSubProcess subProcessSpec = (TSubProcess) subProcessWidget.getBaseElementSpec();
        IBaseElement baseElementSpec = newNodeWidget.getBaseElementSpec();

        rootElementSpec.removeBaseElement(baseElementSpec);
        subProcessSpec.addBaseElement(baseElementSpec);
        /*Manage Widget and Widget Specification End */
    }

    public void addFlowNodeWidget(FlowNodeWidget newNodeWidget) {
        SubProcessWidget subProcessWidget = this;
        BPMNProcessScene scene = (BPMNProcessScene) subProcessWidget.getScene();
        NodeImageWidget container = subProcessWidget.getNodeImageWidget();
        if (newNodeWidget.getParentWidget() != null) {
            newNodeWidget.getParentWidget().removeChild(newNodeWidget);
        }

        Point curPt = newNodeWidget.getPreferredLocation();
        if (curPt == null) {
            curPt = newNodeWidget.getLocation();
        }
        container.addChild(newNodeWidget);
        newNodeWidget.setPreferredLocation(container.convertSceneToLocal(curPt));
//        newNodeWidget.setPreferredLocation(curPt);

        if (newNodeWidget.getFlowElementsContainer() != subProcessWidget) {  // Self Parent

            /*Manage Widget and Widget Specification Start */
            if (newNodeWidget.getFlowElementsContainer() instanceof AbstractModelerScene) {
                scene.removeFlowElement(newNodeWidget);
                subProcessWidget.addFlowElement(newNodeWidget);
                newNodeWidget.setFlowElementsContainer(subProcessWidget);

                IRootElement rootElementSpec = scene.getRootElementSpec();
                TSubProcess subProcessSpec = (TSubProcess) subProcessWidget.getBaseElementSpec();
                IBaseElement baseElementSpec = newNodeWidget.getBaseElementSpec();

                rootElementSpec.removeBaseElement(baseElementSpec);
                subProcessSpec.addBaseElement(baseElementSpec);
            } else if (newNodeWidget.getFlowElementsContainer() instanceof SubProcessWidget) {
                SubProcessWidget subProcessWidget_Parent = (SubProcessWidget) newNodeWidget.getFlowElementsContainer();
                subProcessWidget_Parent.removeFlowElement(newNodeWidget);
                subProcessWidget.addFlowElement(newNodeWidget);
                newNodeWidget.setFlowElementsContainer(subProcessWidget);

                TSubProcess subProcessSpec_Parent = (TSubProcess) subProcessWidget_Parent.getBaseElementSpec();
                TSubProcess subProcessSpec = (TSubProcess) subProcessWidget.getBaseElementSpec();
                IBaseElement baseElementSpec = newNodeWidget.getBaseElementSpec();

                subProcessSpec_Parent.removeBaseElement(baseElementSpec);
                subProcessSpec.addBaseElement(baseElementSpec);
            }
            /*Manage Widget and Widget Specification End */
        }
    }

    public List<ActivityWidget> getAllActivities() {
        List<ActivityWidget> activities = new ArrayList<ActivityWidget>();
        List<FlowElementWidget> flowElements_Tmp = this.getFlowElements();
        for (FlowElementWidget flowElementWidget : flowElements_Tmp) {
            if (flowElementWidget instanceof ActivityWidget) {
                if (flowElementWidget instanceof SubProcessWidget) {
                    activities.addAll(((SubProcessWidget) flowElementWidget).getAllActivities());
                } else {
                    activities.add((ActivityWidget) flowElementWidget);
                }
            }
        }
        activities.add(this);
        return activities;
    }

    public List<BoundaryEventWidget> getAllBoundaryEvents() {
        List<BoundaryEventWidget> boundaryEventWidget = new ArrayList<BoundaryEventWidget>();
        for (ActivityWidget activityWidget : this.getAllActivities()) {
            boundaryEventWidget.addAll(activityWidget.getBoundaryEvents());
        }
        return boundaryEventWidget;
    }
}
