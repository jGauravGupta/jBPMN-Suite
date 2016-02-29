/** Copyright [2014] Gaurav Gupta
   *
   *Licensed under the Apache License, Version 2.0 (the "License");
   *you may not use this file except in compliance with the License.
   *You may obtain a copy of the License at
   *
   *    http://www.apache.org/licenses/LICENSE-2.0
   *
   *Unless required by applicable law or agreed to in writing, software
   *distributed under the License is distributed on an "AS IS" BASIS,
   *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   *See the License for the specific language governing permissions and
   *limitations under the License.
   */
package org.netbeans.jbpmn.core.widget.swimlanes;

import java.awt.Point;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modeler.scene.AbstractModelerScene;

public class PoolMultiMoveProvider implements MoveProvider {

    AbstractModelerScene scene;
//        private HashMap<Widget, Point> originals = new HashMap<Widget, Point> ();
    private Point original;
    private Point current;

    public PoolMultiMoveProvider(AbstractModelerScene scene) {
        this.scene = scene;
    }

    @Override
    public void movementStarted(Widget widget) {
           original = widget.getLocation();
        current = original;
        
    }

    @Override
    public void movementFinished(Widget widget) {
//            originals.clear ();
        original = null;
    }

    @Override
    public Point getOriginalLocation(Widget widget) {
        original = widget.getLocation();//getPreferredLocation
        current = original;
        return original;
    }

    @Override
    public void setNewLocation(Widget widget, Point location) {
        int dx = location.x - current.x;
        int dy = location.y - current.y;
        current = location;
        Point laneLoc = ((LaneSetHeadWidget) widget).getLaneSetWidget().getLocation();
        laneLoc = new Point(laneLoc.x + dx, laneLoc.y + dy);
        ((LaneSetHeadWidget) widget).getLaneSetWidget().setPreferredLocation(laneLoc);
    }
}