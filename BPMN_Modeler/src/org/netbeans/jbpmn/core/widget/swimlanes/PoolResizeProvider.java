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

import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modeler.scene.AbstractModelerScene;


public class PoolResizeProvider implements ResizeProvider {

    AbstractModelerScene scene;
    public PoolResizeProvider(AbstractModelerScene scene){
        this.scene = scene;
    }
    @Override
    public void resizingStarted(Widget widget) {
//      widget.getParentWidget().removeChild(widget);
//      scene.getInterractionLayer().addChild(widget);
        System.out.println("Start Width : "+widget.getBounds().getWidth());
        System.out.println("Start Height : "+widget.getBounds().getHeight());
        
    }

    @Override
    public void resizingFinished(Widget widget) {
//      scene.getInterractionLayer().removeChild(widget);
//      scene.getMainLayer().addChild(widget);
       
        System.out.println("Fin Width : "+widget.getBounds().getWidth());
        System.out.println("Fin Height : "+widget.getBounds().getHeight());
    }
    
}
