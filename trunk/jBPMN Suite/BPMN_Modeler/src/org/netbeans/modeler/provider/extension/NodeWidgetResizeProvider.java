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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.action.ResizeProvider.ControlPoint;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.jbpmn.core.widget.ActivityWidget;
import org.netbeans.jbpmn.core.widget.BoundaryEventWidget;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.label.ILabelConnectionWidget;
import org.netbeans.modeler.provider.ResizeStrategyProvider;
import org.netbeans.modeler.specification.model.document.widget.IModelerSubScene;
import org.netbeans.modeler.widget.node.INodeWidget;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.image.NodeImageWidget;

/**
 *
 *
 */
public class NodeWidgetResizeProvider extends ResizeStrategyProvider {

    private ArrayList<ResizeProvider.ControlPoint> points;
    Dimension minimumBounds, maximumBounds;
    /*
     *
     * all side of are active for resizing
     * @param provider
     */

    public NodeWidgetResizeProvider() {
        this(new ResizeProvider.ControlPoint[]{
            ResizeProvider.ControlPoint.TOP_LEFT,
            ResizeProvider.ControlPoint.TOP_CENTER,
            ResizeProvider.ControlPoint.TOP_RIGHT,
            ResizeProvider.ControlPoint.CENTER_LEFT,
            ResizeProvider.ControlPoint.BOTTOM_LEFT,
            ResizeProvider.ControlPoint.BOTTOM_CENTER,
            ResizeProvider.ControlPoint.BOTTOM_RIGHT,
            ResizeProvider.ControlPoint.CENTER_RIGHT
        });
    }

    /*
     *
     *
     * @param provider
     * @param points active control points
     */
    public NodeWidgetResizeProvider(ResizeProvider.ControlPoint points[]) {
        this.points = new ArrayList<ResizeProvider.ControlPoint>();
        if (points != null) {
            for (int i = 0; i < points.length; i++) {
                this.points.add(points[i]);
            }
        }
    }

    //PROVIDER SECTION
    @Override
    public void resizingStarted(Widget widget) {
//        NodeImageWidget imageWidget = (NodeImageWidget) widget;
        INodeWidget nodeWidget = (INodeWidget) widget;//imageWidget.getParentNodeWidget();
        NodeImageWidget imageWidget = ((NodeWidget) nodeWidget).getNodeImageWidget();
        nodeWidget.setAnchorState(true);

        if (nodeWidget instanceof ActivityWidget) {
            ActivityWidget activityWidget = (ActivityWidget) nodeWidget;
            for (BoundaryEventWidget boundaryEventWidget : activityWidget.getBoundaryEvents()) {
                boundaryEventWidget.setAnchorState(true);
            }
        }
        imageWidget.resizingStarted();
        NBModelerUtil.hideContextPalette(nodeWidget.getModelerScene());

        if (nodeWidget instanceof IModelerSubScene) {
            nodeWidget.setMinimumSize(getMinBound((NodeWidget) nodeWidget));
            nodeWidget.setMaximumSize(getMaxBound((NodeWidget) nodeWidget));
        }
        minimumBounds = nodeWidget.getMinimumSize();
        maximumBounds = nodeWidget.getMaximumSize();
    }

    Dimension getMinBound(NodeWidget nodeWidget) {
        /* Restrict Parent Widget to min resize */
        double minWidth = 0, minHeight = 0;
//        int padding = 10;

        for (Widget widget_Tmp : nodeWidget.getNodeImageWidget().getChildren()) {
            if (widget_Tmp instanceof NodeWidget) {
                Point point = widget_Tmp.getLocation();
                // widget_Tmp.convertLocalToScene(widget_Tmp.getLocation())
                Rectangle area = widget_Tmp.getClientArea();
                if (area != null) {
                    if (point.x + area.width > minWidth) {
                        minWidth = point.x + area.width;
                    }
                    if (point.y + area.height > minHeight) {
                        minHeight = point.y + area.height;
                    }
                }
            }
        }
        double padding = nodeWidget.getOuterElementBorderWidth() * 5;
        return nodeWidget.manageMinRangeConstraint((int) (minWidth + padding), (int) (minHeight + padding));

    }

    Dimension getMaxBound(NodeWidget nodeWidget) {
        /* Restrict Child Widget to max resize */
        NodeWidget parentNodeWidget = (NodeWidget) nodeWidget.getParentWidget().getParentWidget();
        Point nodeWidgetPoint = nodeWidget.getLocation();
        Rectangle parentNodeWidgetArea = parentNodeWidget.getClientArea();
        double padding = nodeWidget.getOuterElementBorderWidth() / 2;
        int x = (int) (parentNodeWidgetArea.width - nodeWidgetPoint.x - padding);
        int y = (int) (parentNodeWidgetArea.height - nodeWidgetPoint.y - padding);
        return nodeWidget.manageMaxRangeConstraint(x, y);
    }

    @Override
    public Rectangle boundsSuggested(Widget widget, Rectangle originalBounds, Rectangle suggestedBounds, ControlPoint controlPoint) {
        INodeWidget nodeWidget = (INodeWidget) widget;
//        NodeImageWidget imageWidget = ((NodeWidget) nodeWidget).getNodeImageWidget();
        boolean widthChanged = true;
        boolean heightChanged = true;
        Rectangle suggested = suggestedBounds;

        int width = suggested.width, height = suggested.height;
        if (maximumBounds.width < suggested.width) {
            width = maximumBounds.width;
            widthChanged = false;
        }
        if (maximumBounds.height < suggested.height) {
            height = maximumBounds.height;
            heightChanged = false;
        }
        if (minimumBounds.width > suggested.width) {
            width = minimumBounds.width;
            widthChanged = false;
        }
        if (minimumBounds.height > suggested.height) {
            height = minimumBounds.height;
            heightChanged = false;
        }
        suggested = new Rectangle(suggested.x, suggested.y, width, height);

//        if (maximumBounds != null) {
//            if (maximumBounds.width < suggested.width || maximumBounds.height < suggested.height) {
//                suggested = new Rectangle(suggested.x, widget.getBounds().y,
//                        Math.min(maximumBounds.width, suggested.width),
//                        Math.min(maximumBounds.height, suggested.height));
//                widthChanged = false;
//            }
//        }
//        if (minimumBounds != null) {
//            if (minimumBounds.width > suggested.width || minimumBounds.height > suggested.height) {
//                suggested = new Rectangle(suggested.x, widget.getBounds().y,
//                        Math.max(minimumBounds.width, suggested.width),
//                        Math.max(minimumBounds.height, suggested.height));
//                heightChanged = false;
//            }
//        }
        if (controlPoint != ControlPoint.BOTTOM_RIGHT && controlPoint != ControlPoint.CENTER_RIGHT && controlPoint != ControlPoint.BOTTOM_CENTER) {
            return originalBounds;
        }
        if (widthChanged || heightChanged) {
            if (widget instanceof ActivityWidget) {
                ActivityWidget activityWidget = (ActivityWidget) widget;
                for (BoundaryEventWidget boundaryEventWidget : activityWidget.getBoundaryEvents()) {
                    boundaryEventWidget.setPreferredLocation(boundaryEventWidget.getBoundaryWidgetLocation(suggested));
                }
            }
        }
        if (widthChanged || heightChanged) {
            if (widget instanceof INodeWidget && ((NodeWidget) widget).getLabelManager() != null) {
                ILabelConnectionWidget labelWidget = ((NodeWidget) widget).getLabelManager().getLabelConnectionWidget();
                Rectangle currentBounds = nodeWidget.getPreferredBounds();
                int dx = suggested.width - currentBounds.width;
                int dy = suggested.height - currentBounds.height;
                Point location = labelWidget.getPreferredLocation();
                labelWidget.setPreferredLocation(new Point(location.x + dx, location.y + dy));
            }
        }

//        suggested = suggestedBounds;
//        imageWidget.updateWidget(suggested.getWidth(), suggested.getHeight(), suggested.getWidth(), suggested.getHeight());
        return suggested;
    }

    public void resizingFinished(Widget widget) {
        INodeWidget nodeWidget = (INodeWidget) widget;
        NodeImageWidget imageWidget = ((NodeWidget) nodeWidget).getNodeImageWidget();
        nodeWidget.setAnchorState(false);
        if (nodeWidget instanceof ActivityWidget) {
            ActivityWidget activityWidget = (ActivityWidget) nodeWidget;
            for (BoundaryEventWidget boundaryEventWidget : activityWidget.getBoundaryEvents()) {
                boundaryEventWidget.setAnchorState(false);
            }
        }
        nodeWidget.getModelerScene().getModelerPanelTopComponent().changePersistenceState(false);
        NBModelerUtil.showContextPalette(nodeWidget.getModelerScene(), nodeWidget);
        minimumBounds = null;
        maximumBounds = null;
        imageWidget.resizingFinished();
    }
}
