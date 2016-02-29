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

import java.util.Arrays;
import java.util.List;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ActionHandler;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ComboBoxListener;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.support.ComboBoxPropertySupport;
import org.netbeans.jbpmn.spec.TComplexGateway;
import org.netbeans.jbpmn.spec.TEventBasedGateway;
import org.netbeans.jbpmn.spec.TEventBasedGatewayType;
import org.netbeans.jbpmn.spec.TExclusiveGateway;
import org.netbeans.jbpmn.spec.TGateway;
import org.netbeans.jbpmn.spec.TGatewayDirection;
import org.netbeans.jbpmn.spec.TInclusiveGateway;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;

/**
 *
 * 
 *  
 *    
 */
public class EventBasedGatewayWidget extends GatewayWidget {
   public EventBasedGatewayWidget(IModelerScene scene,  NodeWidgetInfo node) {
        super(scene, node);
      }
 
   @Override
    public void createPropertySet(ElementPropertySet set) {
        super.createPropertySet(set);
        set.put("BASIC_PROP", getEventBasedGatewayTypeProperty());
        }
        
         private ComboBoxPropertySupport getEventBasedGatewayTypeProperty() {
        final GatewayWidget gatewayWidget = this;
        final TEventBasedGateway gatewaySpec = (TEventBasedGateway) gatewayWidget.getBaseElementSpec();
        ComboBoxListener comboBoxListener = new ComboBoxListener() {
            @Override
            public void setItem(ComboBoxValue value) {
                gatewaySpec.setEventGatewayType((TEventBasedGatewayType) value.getValue());
            }

            @Override
            public ComboBoxValue getItem() {
                return new ComboBoxValue(gatewaySpec.getEventGatewayType(), gatewaySpec.getEventGatewayType().value());
            }

            @Override
            public List<ComboBoxValue> getItemList() {
                ComboBoxValue[] values = new ComboBoxValue[]{
                    new ComboBoxValue(TEventBasedGatewayType.EXCLUSIVE, TEventBasedGatewayType.EXCLUSIVE.value()),
                    new ComboBoxValue(TEventBasedGatewayType.PARALLEL, TEventBasedGatewayType.PARALLEL.value())};
                return Arrays.asList(values);
            }

            @Override
            public String getDefaultText() {
                return TEventBasedGatewayType.EXCLUSIVE.value();
            }

            @Override
            public ActionHandler getActionHandler() {
                return null;
            }
        };
        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(),"TEventBasedGatewayType", "Event Gateway Type", "", comboBoxListener);
    }

}
