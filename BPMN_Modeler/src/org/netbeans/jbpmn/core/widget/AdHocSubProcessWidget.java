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

import java.util.Arrays;
import java.util.List;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.expression.ExpressionPanel;
import org.netbeans.jbpmn.spec.TAdHocOrdering;
import org.netbeans.jbpmn.spec.TAdHocSubProcess;
import org.netbeans.jbpmn.spec.TExpression;
import org.netbeans.modeler.properties.embedded.EmbeddedDataListener;
import org.netbeans.modeler.properties.embedded.EmbeddedPropertySupport;
import org.netbeans.modeler.properties.embedded.GenericEmbedded;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ActionHandler;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ComboBoxListener;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.support.ComboBoxPropertySupport;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;

public class AdHocSubProcessWidget extends SubProcessWidget {

    public AdHocSubProcessWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        super.createPropertySet(set);
        set.put("BASIC_PROP", getProcessTypeProperty());

        GenericEmbedded entity = new GenericEmbedded("completionCondition", "Completion Condition", "");
        entity.setDataListener(new EmbeddedDataListener<TExpression>() {
            TAdHocSubProcess adHocSubProcess;

            @Override
            public void init() {
                adHocSubProcess = (TAdHocSubProcess) AdHocSubProcessWidget.this.getBaseElementSpec();
            }

            @Override
            public TExpression getData() {
                return adHocSubProcess.getCompletionCondition();
            }

            @Override
            public void setData(TExpression data) {
                adHocSubProcess.setCompletionCondition(data);
            }

            @Override
            public String getDisplay() {
                return adHocSubProcess.getCompletionCondition() == null ? "" : adHocSubProcess.getCompletionCondition().getContent();
            }
        });
        entity.setEntityEditor(new ExpressionPanel(this.getModelerScene().getModelerFile()));
        set.put("BASIC_PROP", new EmbeddedPropertySupport(this.getModelerScene().getModelerFile(),entity));

    }

    private ComboBoxPropertySupport getProcessTypeProperty() {
        final AdHocSubProcessWidget adHocSubProcessWidget = this;
        final TAdHocSubProcess adHocSubProcess = (TAdHocSubProcess) adHocSubProcessWidget.getBaseElementSpec();
        ComboBoxListener comboBoxListener = new ComboBoxListener() {
            @Override
            public void setItem(ComboBoxValue value) {
                adHocSubProcess.setOrdering((TAdHocOrdering) value.getValue());
            }

            @Override
            public ComboBoxValue getItem() {
                return new ComboBoxValue(adHocSubProcess.getOrdering(), adHocSubProcess.getOrdering() == null ? "" : adHocSubProcess.getOrdering().value());
            }

            @Override
            public List<ComboBoxValue> getItemList() {
                ComboBoxValue[] values = new ComboBoxValue[]{
                    new ComboBoxValue(TAdHocOrdering.PARALLEL, "Parallel"),
                    new ComboBoxValue(TAdHocOrdering.SEQUENTIAL, "Sequential")};
                return Arrays.asList(values);
            }

            @Override
            public String getDefaultText() {
                return "";
            }

            @Override
            public ActionHandler getActionHandler() {
                return null;
            }
        };
        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(),"orderingType", "Ordering Type", "", comboBoxListener);
    }

}
