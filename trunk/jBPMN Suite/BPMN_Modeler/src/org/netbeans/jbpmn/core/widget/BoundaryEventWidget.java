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

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import org.netbeans.jbpmn.spec.TBoundaryEvent;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.specification.model.document.widget.IBounadryFlowNodeWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.generic.ElementCustomPropertySupport;
import org.netbeans.modeler.widget.properties.generic.ElementPropertySupport;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;
import org.openide.util.Exceptions;
import test.ClosestSegment;
import test.GeometryClosestPointManager;

/**
 *
 *
 *
 *
 */
public class BoundaryEventWidget extends CatchEventWidget implements IBounadryFlowNodeWidget {

    private ActivityWidget activityWidget;
    private boolean autoMoveActionLocked = false;//it is created to remove bug : when task is moved inside subprocess then task boundary event moved to sub process border so on task move it boundary event is locked so in Activity BoundaryAcceptAction is boundary event widget is locked then it is skipped to accept in sub process

    public BoundaryEventWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
    }

    @Override
    public void init() {
        super.init();
        manageCancelActivityIcon();
    }

    public void manageCancelActivityIcon() {
        if (((TBoundaryEvent) this.getBaseElementSpec()).getCancelActivity()) {
            setElementValue("outer", "stroke-dasharray", "0");
        } else {
            setElementValue("outer", "stroke-dasharray", "5.5, 3");
        }
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        super.createPropertySet(set);
        try {
            super.createPropertySet(set);
            set.put("BASIC_PROP", new ElementCustomPropertySupport(this.getModelerScene().getModelerFile(), this.getBaseElementSpec(), Boolean.class,
                    "cancelActivity", "Cancel activity", "Denotes whether the activity should be canceled or not (i.e., whether the boundary catch event acts as an Error or an Escalation). If the activity is not canceled, multiple instances of that handler can run concurrently.",
                    new PropertyChangeListener<Boolean>() {
                        @Override
                        public void changePerformed(Boolean value) {
                            manageCancelActivityIcon();
                        }
                    }));

        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    /**
     * @return the activityWidget
     */
    public ActivityWidget getActivityWidget() {
        return activityWidget;
    }

    /**
     * @param activityWidget the activityWidget to set
     */
    public void setActivityWidget(ActivityWidget activityWidget) {
        this.activityWidget = activityWidget;
    }

    public Point getBoundaryWidgetLocation(ActivityWidget activityWidget) {
        return getBoundaryWidgetLocation(activityWidget.convertLocalToScene(activityWidget.getClientArea()));
    }

    public Point getBoundaryWidgetLocation(Rectangle activityWidgetSceneArea) {
        BoundaryEventWidget boundaryWidget = this;
        Point localLoc = boundaryWidget.getPreferredLocation();
        //localLoc = curWidget.convertLocalToScene(curWidget.getPreferredLocation());
        Rectangle localArea = boundaryWidget.getClientArea();

        Point centerPoint = new Point(localLoc.x + localArea.width / 2, localLoc.y + localArea.height / 2);

        activityWidgetSceneArea.setLocation((int) activityWidgetSceneArea.getX() + 5, (int) activityWidgetSceneArea.getY() + 5);
        activityWidgetSceneArea.setSize((int) activityWidgetSceneArea.getWidth() - 15, (int) activityWidgetSceneArea.getHeight() - 15);

        ClosestSegment cs = GeometryClosestPointManager.getClosetsPoint(activityWidgetSceneArea, new Point2D.Double(centerPoint.getX(), centerPoint.getY()));

        int x = 0, y = 0;
        x = (int) cs.getBestPoint().x;
        y = (int) cs.getBestPoint().y;

        x = x - (int) boundaryWidget.getPreferredBounds().getWidth() / 2;
        y = y - (int) boundaryWidget.getPreferredBounds().getHeight() / 2;

        return new Point(x, y);
    }

    /**
     * @return the isAutoMoveActionLocked
     */
    public boolean isAutoMoveActionLocked() {
        return autoMoveActionLocked;
    }

    /**
     * @param isAutoMoveActionLocked the isAutoMoveActionLocked to set
     */
    public void setAutoMoveActionLocked(boolean autoMoveActionLocked) {
        this.autoMoveActionLocked = autoMoveActionLocked;
    }

}
