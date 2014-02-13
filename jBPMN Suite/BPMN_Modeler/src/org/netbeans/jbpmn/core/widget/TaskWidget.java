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

import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;

public class TaskWidget extends ActivityWidget {

    public TaskWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);

    }

//
//
//  @Override
//    public JMenuItem getConvertWidgetSetting() {
//           JMenu menu = new JMenu(I18n.getString("Transform Shape"));
////           this.getModelerScene().getModelerFile().
//
//
//
//      if (this.getClass() != BusinessRuleTaskWidget.class) {
//          JMenuItem businessRuleTaskWidget = getMenuItem(TaskWidget.this,BusinessRuleTaskWidget.class);
//          menu.add(businessRuleTaskWidget);
//      }
//      if (this.getClass() != ManualTaskWidget.class) {
//          JMenuItem manualTaskWidget = getMenuItem(TaskWidget.this,ManualTaskWidget.class);
//          menu.add(manualTaskWidget);
//      }
//      if (this.getClass() != ScriptTaskWidget.class) {
//          JMenuItem scriptTaskWidget = getMenuItem(TaskWidget.this,ScriptTaskWidget.class);
//          menu.add(scriptTaskWidget);
//      }
//      if (this.getClass() != ServiceTaskWidget.class) {
//          JMenuItem serviceTaskWidget = getMenuItem(TaskWidget.this,ServiceTaskWidget.class);
//          menu.add(serviceTaskWidget);
//      }
//      if (this.getClass() != UserTaskWidget.class) {
//          JMenuItem userTaskWidget = getMenuItem(TaskWidget.this,UserTaskWidget.class);
//          menu.add(userTaskWidget);
//      }
//      if (this.getClass() != SendTaskWidget.class) {
//          JMenuItem sendTaskWidget = getMenuItem(TaskWidget.this,SendTaskWidget.class);
//          menu.add(sendTaskWidget);
//      }
//      if (this.getClass() != ReceiveTaskWidget.class) {
//          JMenuItem receiveTaskWidget = getMenuItem(TaskWidget.this,ReceiveTaskWidget.class);
//          menu.add(receiveTaskWidget);
//      }
//
//        return menu;
//
//    }
//
//
//
//
}
