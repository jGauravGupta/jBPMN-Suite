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
 package org.netbeans.jbpmn.core.widget;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.netbeans.jbpmn.modeler.widget.properties.operation.OperationPanel;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ActionHandler;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ComboBoxListener;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.support.ComboBoxPropertySupport;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TInterface;
import org.netbeans.jbpmn.spec.TOperation;
import org.netbeans.jbpmn.spec.TRootElement;
import org.netbeans.jbpmn.spec.TServiceTask;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;


public class ServiceTaskWidget extends TaskWidget {
 public ServiceTaskWidget(IModelerScene scene,  NodeWidgetInfo node) {
        super(scene, node);
      }
 
 
    
    
}
