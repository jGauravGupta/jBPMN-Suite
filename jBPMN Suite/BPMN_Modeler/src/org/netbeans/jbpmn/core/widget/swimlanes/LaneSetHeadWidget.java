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
import org.netbeans.modeler.scene.AbstractModelerScene;
import org.netbeans.modeler.widget.node.ContainerWidget;

public class LaneSetHeadWidget extends ContainerWidget {

    public LaneSetHeadWidget(AbstractModelerScene scene) {
        super(scene);
        this.setPreferredSize(new Dimension(30, 100));
        this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));

        this.getActions().addAction(ActionFactory.createMoveAction(null, new PoolMultiMoveProvider(scene)));

    }
    private LaneSetWidget laneSetWidget;

    /**
     * @return the laneSetWidget
     */
    public LaneSetWidget getLaneSetWidget() {
        return laneSetWidget;
    }

    /**
     * @param laneSetWidget the laneSetWidget to set
     */
    public void setLaneSetWidget(LaneSetWidget laneSetWidget) {
        this.laneSetWidget = laneSetWidget;
    }
}