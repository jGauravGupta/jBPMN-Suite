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

import org.netbeans.jbpmn.spec.TActivity;
import org.netbeans.jbpmn.spec.TStartEvent;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.specification.model.document.widget.IStartFlowNodeWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.generic.ElementCustomPropertySupport;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;
import org.openide.util.Exceptions;

/**
 *
 *
 *
 *
 */
public class StartEventWidget extends CatchEventWidget implements IStartFlowNodeWidget {

    public StartEventWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        try {
            super.createPropertySet(set);
            set.put("BASIC_PROP", new ElementCustomPropertySupport(this.getModelerScene().getModelerFile(), this.getBaseElementSpec(), Boolean.class,
                    "isInterrupting", "Is Interrupting", "",
                    new PropertyChangeListener<Boolean>() {
                        @Override
                        public void changePerformed(Boolean value) {
                            manageInterruptingState();
                        }
                    }));

        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    @Override
    public void init() {
        super.init();
        manageInterruptingState();
    }

    private void manageInterruptingState() {
        if (((TStartEvent) this.getBaseElementSpec()).getIsInterrupting()) {
            setElementValue("outer", "stroke-dasharray", "0");
        } else {
            setElementValue("outer", "stroke-dasharray", "5.5, 3");
        }
    }

}
