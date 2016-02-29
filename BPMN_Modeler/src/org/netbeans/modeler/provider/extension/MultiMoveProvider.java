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
package org.netbeans.modeler.provider.extension;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.jbpmn.core.widget.ActivityWidget;
import org.netbeans.jbpmn.core.widget.BoundaryEventWidget;
import org.netbeans.jbpmn.core.widget.FlowElementWidget;
import org.netbeans.jbpmn.core.widget.FlowNodeWidget;
import org.netbeans.jbpmn.core.widget.SequenceFlowWidget;
import org.netbeans.jbpmn.core.widget.SubProcessWidget;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.label.ILabelConnectionWidget;
import org.netbeans.modeler.scene.AbstractModelerScene;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.widget.IFlowEdgeWidget;
import org.netbeans.modeler.specification.model.document.widget.IFlowNodeWidget;
import org.netbeans.modeler.specification.model.document.widget.IModelerSubScene;
import org.netbeans.modeler.widget.edge.EdgeWidget;
import org.netbeans.modeler.widget.node.INodeWidget;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.transferable.MoveDropTargetDropEvent;
import static org.netbeans.modeler.widget.transferable.TransAlignWithMoveStrategyProvider.adjustControlPoints;

public class MultiMoveProvider implements MoveProvider {

    private HashMap<Widget, Point> originals = new HashMap<Widget, Point>();
    private Point original;
    IModelerScene scene;
    private Set< MultiMoveProvider.MovingWidgetDetails> movingWidgets = null;
    private static int eventID = 0;
    Point lastPoint = null;

    private boolean locationChanged = false;

    public MultiMoveProvider(IModelerScene scene) {
        this.scene = (IModelerScene) scene;

    }

//    List<BoundaryEventWidget> boundaryEventWidgets = null;
    @Override
    public void movementStarted(Widget widget) {
        locationChanged = false;
        INodeWidget nodeWidget = (INodeWidget) widget;
        NBModelerUtil.hideContextPalette(nodeWidget.getModelerScene());
        nodeWidget.hideResizeBorder();
        LayerWidget interractionLayer = ((IModelerScene) widget.getScene()).getInterractionLayer();
        LayerWidget connectionLayer = ((IModelerScene) widget.getScene()).getConnectionLayer();
        interractionLayer.bringToFront();
        connectionLayer.bringToFront();

        Object object = scene.findObject(widget);
        if (scene.isNode(object)) {
            for (Object o : scene.getSelectedObjects()) {
                if (scene.isNode(o)) {
                    Widget w = scene.findWidget(o);
                    if (w != null) {
                        originals.put(w, w.getPreferredLocation());
                    }
                }
            }
        } else {
            originals.put(widget, widget.getPreferredLocation());
        }
    }

    @Override
    public void movementFinished(Widget widget) {

        INodeWidget nodeWidget = (INodeWidget) widget;
        NBModelerUtil.showContextPalette(nodeWidget.getModelerScene(), nodeWidget);
        nodeWidget.showResizeBorder();
        if (locationChanged) {
            scene.getModelerPanelTopComponent().changePersistenceState(false);
        }
        //BUG[5611] : https://java.net/bugzilla/show_bug.cgi?id=5611
        //Align Edge Start
        if (nodeWidget instanceof IFlowNodeWidget) {  // check [not Artifact]
            for (IFlowEdgeWidget flowEdgeWidget : ((IFlowNodeWidget) nodeWidget).getOutgoingFlowEdgeWidget()) {
                if (flowEdgeWidget instanceof EdgeWidget) {
                    ((EdgeWidget) flowEdgeWidget).manageControlPoint();
                }
            }
            for (IFlowEdgeWidget flowEdgeWidget : ((IFlowNodeWidget) nodeWidget).getIncommingFlowEdgeWidget()) {
                if (flowEdgeWidget instanceof EdgeWidget) {
                    ((EdgeWidget) flowEdgeWidget).manageControlPoint();
                }
            }
        }
        //Align Edge End

//        LayerWidget boundaryWidgetLayer = ((IModelerScene) widget.getScene()).getBoundaryWidgetLayer();
//        LayerWidget interractionLayer = ((IModelerScene) widget.getScene()).getInterractionLayer();
        if (movingWidgets != null) {
            for (MultiMoveProvider.MovingWidgetDetails details : movingWidgets) {
                if (details.getWidget() instanceof INodeWidget) { //anchor disable for selected node
                    INodeWidget inodeWidget = (INodeWidget) details.getWidget();
                    inodeWidget.setAnchorState(false);
                }

                Widget curWidget = details.getWidget();
                Point location = curWidget.getLocation();

                MoveDropTargetDropEvent dropEvent = new MoveDropTargetDropEvent(curWidget, location);
                WidgetAction.WidgetDropTargetDropEvent event = new WidgetAction.WidgetDropTargetDropEvent(++eventID, dropEvent);

                processLocationOperator((Scene) scene, event, location);
                finishedOverScene(details);
//                }
            }

            movingWidgets.clear();
            movingWidgets = null;
        }
        originals.clear();
        original = null;

//        if (widget instanceof ActivityWidget) {
////            ActivityWidget activityWidget = (ActivityWidget) widget;
//            for (BoundaryEventWidget boundaryEventWidget : boundaryEventWidgets) {//activityWidget.getBoundaryEvents()) {
//                interractionLayer.removeChild(boundaryEventWidget);
//                boundaryWidgetLayer.addChild(boundaryEventWidget);
//            }
//        }
        //BUG[5612] : https://java.net/bugzilla/show_bug.cgi?id=5612
//        if (widget instanceof IFlowElementWidget) {
//            IFlowElementWidget flowElementWidget = (IFlowElementWidget) widget;
//            if (flowElementWidget.getFlowElementsContainer() instanceof ModelerScene) {
//                widget.getParentWidget().removeChild(widget);
//                scene.getMainLayer().addChild(widget);
//            } else {
//                // handle bug for sub process
//            }
//        }
//         widget.getParentWidget().removeChild(widget);    // these lines are commented because widget can be moved inside sub process widget
//         scene.getMainLayer().addChild(widget);
        ((IModelerScene) widget.getScene()).manageLayerWidget();

//        if (widget instanceof ActivityWidget) {
//            boundaryEventWidgets = null;
//        }
        locationChanged = false;
    }

    @Override
    public Point getOriginalLocation(Widget widget) {
        original = widget.getPreferredLocation();
        initializeMovingWidgets(widget.getScene(), widget);
        return original;
    }

    @Override
    public void setNewLocation(Widget widget, Point location) {

//        INodeWidget nodeWidget = (INodeWidget) widget;
//        nodeWidget.setAnchorState(true);
        if (location != null && original != null) {

            int dx = location.x - widget.getPreferredLocation().x;
            int dy = location.y - widget.getPreferredLocation().y;
            if (dx != 0 || dy != 0) {
                if (movingWidgets == null) {
                    //in case if this class is used only as provider and strategy isn't used
                    initializeMovingWidgets(widget.getScene(), widget);
                }

                // The dx is calcuated using a start location of when the move
                // first started.  However for connection points we do not have
                // the connection point values from before the move started.
                //
                // Therefore use the reference widget to determine the dx.
                locationChanged = true;
                lastPoint = location;
                adjustControlPoints(getMovingWidgetList(), dx, dy);
                for (MultiMoveProvider.MovingWidgetDetails details : movingWidgets) {
                    Point point = details.getWidget().getPreferredLocation();
                    if (point == null) {
                        point = details.getWidget().getLocation();
                    }
//                    System.out.println("Loc : " + point);
                    Point newPt = new Point(point.x + dx, point.y + dy);
//                    System.out.println("Dx : " + dx + " dy : " + dy);
//                    System.out.println("N_Loc : " + newPt);
                    if (details.getWidget() instanceof ConnectionWidget) {
                    } else {
                        details.getWidget().setPreferredLocation(newPt);
                    }
//                    if (details.getWidget() instanceof ActivityWidget) {
//                        //ActivityWidget activityWidget = (ActivityWidget) details.getWidget();
//                        for (BoundaryEventWidget boundaryEventWidget : boundaryEventWidgets) {//activityWidget.getBoundaryEvents()) {
////                            ActivityWidget activityWidget = (ActivityWidget) details.getWidget();
////                        for (BoundaryEventWidget boundaryEventWidget : activityWidget.getBoundaryEvents()) {
//                            Point point_Tmp = boundaryEventWidget.getPreferredLocation();
//                            if (point_Tmp == null) {
//                                point_Tmp = details.getWidget().getLocation();
//                            }
//                            Point newPoint = new Point(point_Tmp.x + dx, point_Tmp.y + dy);
//                            boundaryEventWidget.setPreferredLocation(newPoint);
//                        }
//                    }

                }
            }
        }
    }

    protected Set<MultiMoveProvider.MovingWidgetDetails> getMovingDetails() {
        return movingWidgets;
    }

    protected List< Widget> getMovingWidgetList() {
        ArrayList< Widget> retVal = new ArrayList< Widget>();

        for (MultiMoveProvider.MovingWidgetDetails details : movingWidgets) {
            retVal.add(details.getWidget());
        }

        return retVal;
    }

    private void initializeMovingWidgets(Scene scene, Widget widget) {
        if (movingWidgets != null) {
            // We should never be here;
            movingWidgets.clear();
            movingWidgets = null;
        }
//need to sort in order to return back properly without indexOutOfBounds
        movingWidgets = new HashSet< MultiMoveProvider.MovingWidgetDetails>();

        if (scene instanceof IModelerScene) {
            IModelerScene gscene = (IModelerScene) scene;
            Object object = gscene.findObject(widget);
            if (gscene.isNode(object)) {
                Set< ?> selected = gscene.getSelectedObjects();
                for (Object selectedObject : selected) {
                    if ((gscene.isNode(selectedObject)) && (isOwnerSelected(selectedObject, selected, gscene) == false)) {
                        Widget selectedWidget = gscene.findWidget(selectedObject);
                        if (selectedWidget != null && selectedWidget.getParentWidget() != null) {  //selectedWidget.getParentWidget()!=null [if widget is deleted so parent is null]
                            addToMovingWidgetDetails(gscene, (Widget) selectedWidget);

                            /* ILabelConnectionWidget Added to interraction Layer Start */
                            if (selectedWidget instanceof INodeWidget && ((NodeWidget) selectedWidget).getLabelManager() != null) {
                                ILabelConnectionWidget labelWidget = ((NodeWidget) selectedWidget).getLabelManager().getLabelConnectionWidget();
                                addToMovingWidgetDetails(gscene, (Widget) labelWidget);
                            }

                            /* ILabelConnectionWidget Added to interraction Layer End */
                            /* BoundaryWidget Added to interraction Layer Start */
                            List<BoundaryEventWidget> boundaryEventWidgets;
                            if (selectedWidget instanceof ActivityWidget) {
                                if (selectedWidget instanceof IModelerSubScene) {
                                    boundaryEventWidgets = ((SubProcessWidget) selectedWidget).getAllBoundaryEvents();
                                } else {
                                    boundaryEventWidgets = ((ActivityWidget) selectedWidget).getBoundaryEvents();
                                }
                                for (BoundaryEventWidget boundaryEventWidget : boundaryEventWidgets) {
                                    addToMovingWidgetDetails(gscene, (Widget) boundaryEventWidget);
                                }
                            }
                            /* BoundaryWidget Added to interraction Layer End */
                            /* SubProcessWidget Child Anchor Enabled Start*/
                            if (selectedWidget instanceof SubProcessWidget) {
                                SubProcessWidget subProcessWidget = (SubProcessWidget) selectedWidget;
                                for (FlowElementWidget flowWidget : subProcessWidget.getFlowElements()) {
                                    ((INodeWidget) flowWidget).setAnchorState(true);

                                    /*SubProcess > Child ILabelConnectionWidget Added to interraction Layer Start */
                                    if (flowWidget instanceof INodeWidget && ((NodeWidget) flowWidget).getLabelManager() != null) {
                                        ILabelConnectionWidget labelWidget = ((NodeWidget) flowWidget).getLabelManager().getLabelConnectionWidget();
                                        addToMovingWidgetDetails(gscene, (Widget) labelWidget);
                                    }
                                }
                            }
                            /* SubProcessWidget Child Anchor Enabled End*/

                        }
                    }
                }
                //need to sort in order to return back properly without indexOutOfBounds
//                Collections.sort(movingWidgets, new Comparator<MultiMoveProvider.MovingWidgetDetails>() {
//                    public int compare(MultiMoveProvider.MovingWidgetDetails o1, MultiMoveProvider.MovingWidgetDetails o2) {
//                        return o1.getOriginalIndex() - o2.getOriginalIndex();
//                    }
//                });
            } else {
                MultiMoveProvider.MovingWidgetDetails details = new MultiMoveProvider.MovingWidgetDetails(widget, widget.getParentWidget(), widget.getPreferredLocation());

                movingWidgets.add(details);
            }
        }
        boolean isAutoMoveActionLocked = false;
        if (movingWidgets.size() > 1) {
            isAutoMoveActionLocked = true;
        }

        for (MultiMoveProvider.MovingWidgetDetails details : movingWidgets) {   //allow anchor enable for selected node
            if (details.getWidget() instanceof INodeWidget) {
                INodeWidget nodeWidget = (INodeWidget) details.getWidget();
                if (nodeWidget instanceof BoundaryEventWidget && isAutoMoveActionLocked) {
                    BoundaryEventWidget boundaryEventWidget = (BoundaryEventWidget) nodeWidget;
                    boundaryEventWidget.setAutoMoveActionLocked(true);
                }
                nodeWidget.setAnchorState(true);
            }
        }

    }

    void addToMovingWidgetDetails(IModelerScene scene, Widget widget) {
        Point widget_pt = widget.getPreferredLocation();
        Widget widget_owner = widget.getParentWidget();
        if (widget_owner != null) {
            widget_pt = widget_owner.convertLocalToScene(widget_pt);
            widget.setPreferredLocation(widget_pt);
        }
        movingWidgets.add(new MultiMoveProvider.MovingWidgetDetails((Widget) widget, widget_owner, widget_pt));
        if (widget_owner != null) {
            widget_owner.removeChild((Widget) widget);
        }

        scene.getInterractionLayer().addChild((Widget) widget);
    }

    private boolean processLocationOperator(Widget widget,
            WidgetAction.WidgetDropTargetDropEvent event,
            Point cursorSceneLocation) {
        Scene scene = widget.getScene();
        Point location = scene.getLocation();
        return processLocationOperator2(scene, event, new Point(cursorSceneLocation.x + location.x, cursorSceneLocation.y + location.y));
    }

    private boolean processLocationOperator2(Widget widget,
            WidgetAction.WidgetDropTargetDropEvent event,
            Point point) {
        boolean retVal = false;

        if (!widget.isVisible()) {
            return false;
        }

        Point location = widget.getLocation();
        point.translate(-location.x, -location.y);

        Rectangle bounds = widget.getBounds();
        if (bounds.contains(point)) {
            List<Widget> children = widget.getChildren();
            Widget[] childrenArray = children.toArray(new Widget[children.size()]);

            for (int i = childrenArray.length - 1; i >= 0; i--) {
                if (processLocationOperator2(childrenArray[i], event, point) == true) {
                    retVal = true;
                    break;
                }
            }

            if ((retVal == false) && (widget.isHitAt(point) == true)) {
                retVal = sendEvents(widget, event, point);
            }
        }

        point.translate(location.x, location.y);
        return retVal;
    }

    private void finishedOverScene(MultiMoveProvider.MovingWidgetDetails details) {
        Widget widget = details.getWidget();
        List<Widget> children = this.scene.getInterractionLayer().getChildren(); //BUG : don't check that it is contaning in interraction layer because in drop event it is added to scene layer
        if ((children != null && children.contains(widget)) || widget.getParentWidget() == scene) {
            if (widget instanceof INodeWidget) {
                if (widget instanceof BoundaryEventWidget) {
                    widget.getParentWidget().removeChild(widget);//this.scene.getInterractionLayer().removeChild(widget);
                    this.scene.getBoundaryWidgetLayer().addChild(widget);
                    if (movingWidgets.size() == 1) { //if only BoundaryEventWidget is moved then need to evaluate postion nearest otherwise if multi moved then it will be placaced as other element
                        BoundaryEventWidget boundaryEventWidget = (BoundaryEventWidget) widget;
                        boundaryEventWidget.setAnchorState(true);
                        boundaryEventWidget.setPreferredLocation(boundaryEventWidget.getBoundaryWidgetLocation(boundaryEventWidget.getActivityWidget()));//widget.getScene().getSceneAnimator().animatePreferredLocation(widget, boundaryEventWidget.getBoundaryWidgetLocation(boundaryEventWidget.getActivityWidget()));
                        boundaryEventWidget.getScene().validate();
                        boundaryEventWidget.setAnchorState(false);
                    } else if (movingWidgets.size() > 1) {
                        BoundaryEventWidget boundaryEventWidget = (BoundaryEventWidget) widget;
                        boundaryEventWidget.setAutoMoveActionLocked(false);
                    }
                } else {
                    /* SubProcessWidget Child Anchor Disabled Start*/
                    if (widget instanceof SubProcessWidget) {
                        SubProcessWidget subProcessWidget = (SubProcessWidget) widget;
                        for (FlowElementWidget flowWidget : subProcessWidget.getFlowElements()) {
                            ((INodeWidget) flowWidget).setAnchorState(false);
                        }
                    }
                    /* SubProcessWidget Child Anchor Disabled Start*/

                    widget.getParentWidget().removeChild(widget);//this.scene.getInterractionLayer().removeChild(widget); BUG : it may be in Scene
                    this.scene.getMainLayer().addChild(widget);
                }
            } else if (widget instanceof ILabelConnectionWidget) {
                widget.getParentWidget().removeChild(widget);//this.scene.getInterractionLayer().removeChild(widget);
                this.scene.getLabelLayer().addChild(widget);
            }
        }

    }

    private boolean sendEvents(Widget target,
            WidgetAction.WidgetDropTargetDropEvent event,
            Point pt) {
        boolean retVal = false;

        if (target != null) {
            if (sendEvents(target.getActions(), target, event) == false) {
                String tool = target.getScene().getActiveTool();
                retVal = sendEvents(target.getActions(tool), target, event);
            } else {
                retVal = true;
            }
        }

        return retVal;
    }

    private boolean sendEvents(WidgetAction.Chain actions,
            Widget target,
            WidgetAction.WidgetDropTargetDropEvent event) {
        boolean retVal = false;

        if (actions != null) {
            for (WidgetAction action : actions.getActions()) {
                if (action.drop(target, event) == WidgetAction.State.CONSUMED) {
                    retVal = true;
                    break;
                }
            }
        }

        return retVal;
    }

    private boolean isOwnerSelected(Object o,
            Set<?> selected,
            IModelerScene gscene) {
        boolean retVal = false;

        Widget widget = gscene.findWidget(o);
        if (widget != null) {
            Widget parent = widget.getParentWidget();
            Object parentObj = gscene.findObject(parent);

            if (parentObj != null) {
                if (selected.contains(parentObj) == true) {
                    retVal = true;
                } else {
                    retVal = isOwnerSelected(parentObj, selected, gscene);
                }
            }
        }

        return retVal;
    }

    void addSequenceFlowIntraction(FlowNodeWidget flowNodeWidget) {
        if (flowNodeWidget instanceof SubProcessWidget) {
            LayerWidget interractionLayer = ((AbstractModelerScene) flowNodeWidget.getScene()).getInterractionLayer();
            for (Widget widget : flowNodeWidget.getNodeImageWidget().getChildren()) {
                if (widget instanceof FlowNodeWidget) {
                    FlowNodeWidget flowNodeWidgetChildren = (FlowNodeWidget) widget;
                    addSequenceFlowIntraction(flowNodeWidgetChildren);
                }
            }
            for (SequenceFlowWidget sequenceFlowWidget : flowNodeWidget.getOutgoingSequenceFlows()) {
                sequenceFlowWidget.getParentWidget().removeChild(sequenceFlowWidget);
                interractionLayer.addChild(sequenceFlowWidget);
            }
            for (SequenceFlowWidget sequenceFlowWidget : flowNodeWidget.getIncomingSequenceFlows()) {
                sequenceFlowWidget.getParentWidget().removeChild(sequenceFlowWidget);
                interractionLayer.addChild(sequenceFlowWidget);
            }
        }
    }

    void removeSequenceFlowIntraction(FlowNodeWidget flowNodeWidget) {
        if (flowNodeWidget instanceof SubProcessWidget) {
            LayerWidget connectionLayer = ((AbstractModelerScene) flowNodeWidget.getScene()).getConnectionLayer();
            for (Widget widget : flowNodeWidget.getNodeImageWidget().getChildren()) {
                if (widget instanceof FlowNodeWidget) {
                    FlowNodeWidget flowNodeWidgetChildren = (FlowNodeWidget) widget;
                    removeSequenceFlowIntraction(flowNodeWidgetChildren);
                }
            }
            for (SequenceFlowWidget sequenceFlowWidget : flowNodeWidget.getOutgoingSequenceFlows()) {
                sequenceFlowWidget.getParentWidget().removeChild(sequenceFlowWidget);
                connectionLayer.addChild(sequenceFlowWidget);
            }
            for (SequenceFlowWidget sequenceFlowWidget : flowNodeWidget.getIncomingSequenceFlows()) {
                sequenceFlowWidget.getParentWidget().removeChild(sequenceFlowWidget);//interractionLayer.removeChild(sequenceFlowWidget);

                connectionLayer.addChild(sequenceFlowWidget);
            }
        }
    }

    private class MovingWidgetDetails {

        private Widget widget = null;
        private Widget owner = null;
        private Point originalLocation = null;
        private int originalIndex = -1;

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 23 * hash + (this.widget != null ? this.widget.hashCode() : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final MovingWidgetDetails other = (MovingWidgetDetails) obj;
            if (this.widget != other.widget && (this.widget == null || !this.widget.equals(other.widget))) {
                return false;
            }
            return true;
        }

        public MovingWidgetDetails(Widget widget,
                Widget owner,
                Point location) {
            this.widget = widget;
            this.owner = owner;
            this.originalLocation = location;

            if (owner != null) {
                originalIndex = owner.getChildren().indexOf(widget);
            }
        }

        public Point getOriginalLocation() {
            return originalLocation;
        }

        public Widget getOwner() {
            return owner;
        }

        public Widget getWidget() {
            return widget;
        }

        public int getOriginalIndex() {
            return originalIndex;
        }

        public void updateIndexIfRequired() {
            if (owner.equals(widget.getParentWidget())) {
                owner.removeChild(widget);
                //Adjust the original index.
                owner.addChild(--originalIndex < 0 ? 0 : originalIndex, widget);
            }
        }
    }
}
