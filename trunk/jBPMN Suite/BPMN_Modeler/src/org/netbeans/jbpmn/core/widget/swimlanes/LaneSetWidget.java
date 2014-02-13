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
package org.netbeans.jbpmn.core.widget.swimlanes;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.modeler.scene.AbstractModelerScene;
import org.netbeans.modeler.widget.node.ContainerWidget;

public class LaneSetWidget extends ContainerWidget  {

    private LaneSetHeadWidget headWidget;
    private LaneSetBodyWidget bodyWidget;

    public LaneSetWidget(AbstractModelerScene scene) {
        super(scene);
//       this.setLabelWidget(null);
        // this.setBPMNImageWidget(null);
        //this.setBorder(BorderFactory.createLineBorder(2, Color.RED));

        // setCheckClipping(true);
        this.setPreferredSize(new Dimension(400, 100));
        this.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));

        headWidget = new LaneSetHeadWidget(scene);
        headWidget.setLaneSetWidget(this);
        bodyWidget = new LaneSetBodyWidget(scene);
        bodyWidget.setLaneSetWidget(this);
        this.setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.JUSTIFY, 0));



        this.addChild(headWidget);
        this.addChild(bodyWidget);
        getBodyWidget().addLane(new LaneWidget(scene));
        getBodyWidget().addLane(new LaneWidget(scene));
        getBodyWidget().addLane(new LaneWidget(scene));

  this.getActions().addAction(ActionFactory.createResizeAction (null , new PoolResizeProvider(scene)));
  this.setBorder (org.netbeans.api.visual.border.BorderFactory.createResizeBorder (4, new Color(242,132,0), false));

    }
//  @Override
//    public void init() {
//         System.out.println("aa");
//    
//        //headWidget.setPreferredLocation(new Point(0,0));
//        // bodyWidget.setPreferredLocation(new Point(0,0));
//
//      Point headLoc = this.convertLocalToScene(this.getLocation());
//      headLoc = new Point(headLoc.x + 2 , headLoc.y +1 );
//        getHeadWidget().setPreferredLocation(headLoc);
//     Point bodyLoc =new Point(headLoc.x  -2 + getHeadWidget().getNodeImageWidget().getDimension().width, headLoc.y );
//        getBodyWidget().setPreferredLocation(bodyLoc);
//
////        getBodyWidget().getNodeImageWidget().setLayout (LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.JUSTIFY,-30));
//         getBodyWidget().setLayout(LayoutFactory.createAbsoluteLayout());
//      
//       
//        getBodyWidget().addLane(new LaneWidget(this.getBPMNScene(), this.getBPMNScene().getMainLayer(), this.getBPMNScene().getInterractionLayer(), this.getBPMNScene().getConnectionLayer()));
//        getBodyWidget().addLane(new LaneWidget(this.getBPMNScene(), this.getBPMNScene().getMainLayer(), this.getBPMNScene().getInterractionLayer(), this.getBPMNScene().getConnectionLayer()));
//       getBodyWidget().manageLane();
//      
//     }
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the headWidget
     */
    public LaneSetHeadWidget getHeadWidget() {
        return headWidget;
    }

    /**
     * @param headWidget the headWidget to set
     */
    public void setHeadWidget(LaneSetHeadWidget headWidget) {
        this.headWidget = headWidget;
    }

    /**
     * @return the bodyWidget
     */
    public LaneSetBodyWidget getBodyWidget() {
        return bodyWidget;
    }

    /**
     * @param bodyWidget the bodyWidget to set
     */
    public void setBodyWidget(LaneSetBodyWidget bodyWidget) {
        this.bodyWidget = bodyWidget;
    }

   
}
