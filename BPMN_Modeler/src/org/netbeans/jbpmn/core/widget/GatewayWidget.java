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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.netbeans.modeler.properties.nentity.Column;
import org.netbeans.modeler.properties.nentity.NEntityPropertySupport;
import org.netbeans.modeler.properties.nentity.NAttributeEntity;
import org.netbeans.modeler.properties.nentity.Table;
import org.netbeans.modeler.properties.nentity.NEntityDataListener;
import org.netbeans.jbpmn.modeler.widget.properties.gateway.SequenceFlowConditionPanel;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ActionHandler;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ComboBoxListener;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.support.ComboBoxPropertySupport;
import org.netbeans.jbpmn.spec.TComplexGateway;
import org.netbeans.jbpmn.spec.TExclusiveGateway;
import org.netbeans.jbpmn.spec.TFormalExpression;
import org.netbeans.jbpmn.spec.TGateway;
import org.netbeans.jbpmn.spec.TGatewayDirection;
import org.netbeans.jbpmn.spec.TInclusiveGateway;
import org.netbeans.jbpmn.spec.TSequenceFlow;
import org.netbeans.jbpmn.spec.extend.LanguageManager;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.openide.nodes.PropertySupport;

/**
 *
 *
 *
 *
 */
public class GatewayWidget extends FlowNodeWidget {

    public GatewayWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        super.createPropertySet(set);
        set.put("BASIC_PROP", getGatewayDirectionTypeProperty());
        if (this.getBaseElementSpec() instanceof TComplexGateway
                || this.getBaseElementSpec() instanceof TInclusiveGateway
                || this.getBaseElementSpec() instanceof TExclusiveGateway) {
            set.put("BASIC_PROP", addSequenceFlowConditionProperty());
        }

    }

    PropertySupport addSequenceFlowConditionProperty() {
        final NAttributeEntity attributeEntity = new NAttributeEntity("SequenceFlow", "SequenceFlow List", "");
        attributeEntity.setCountDisplay(new String[]{"No SequenceFlows", "One SequenceFlow", "SequenceFlows"});

//        final ModelerFile modelerFile = this.getModelerScene().getModelerFile();
        final TGateway gatewaySpec = (TGateway) this.getBaseElementSpec();
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Sequence Flow", false, String.class, 285));
        columns.add(new Column("Is Default", false, Boolean.class, 65));
        columns.add(new Column("Condition", false, String.class, 450));
        columns.add(new Column("Script Type", false, String.class, 100));

        attributeEntity.setColumns(columns);
        attributeEntity.setTable(new Table(800, 400));
        attributeEntity.setEnableActionPanel(Boolean.FALSE);
        attributeEntity.setCustomDialog(new SequenceFlowConditionPanel());//TODO : MAINTAIN EntityComponent row to entity converter
        attributeEntity.setTableDataListener(new NEntityDataListener() {
            List<Object[]> data;
            int count;

            @Override
            public void initCount() {
                count = GatewayWidget.this.getOutgoingSequenceFlows().size();
            }

            @Override
            public int getCount() {
                return count;
            }

            @Override
            public void initData() {
                List<SequenceFlowWidget> sequenceFlowWidgets = GatewayWidget.this.getOutgoingSequenceFlows();
                String defaultSequenceFlow = null;
                if (gatewaySpec instanceof TComplexGateway) {
                    defaultSequenceFlow = ((TComplexGateway) gatewaySpec).getDefault();
                } else if (gatewaySpec instanceof TInclusiveGateway) {
                    defaultSequenceFlow = ((TInclusiveGateway) gatewaySpec).getDefault();
                } else if (gatewaySpec instanceof TExclusiveGateway) {
                    defaultSequenceFlow = ((TExclusiveGateway) gatewaySpec).getDefault();
                }
                List<Object[]> data_local = new LinkedList<Object[]>();
                Iterator<SequenceFlowWidget> itr = sequenceFlowWidgets.iterator();
                while (itr.hasNext()) {
                    SequenceFlowWidget sequenceFlowWidget = itr.next();
                    TSequenceFlow sequenceFlow = (TSequenceFlow) sequenceFlowWidget.getBaseElementSpec();
                    boolean isDefault = false;
                    if (sequenceFlow.getId().equals(defaultSequenceFlow)) {
                        isDefault = true;
                    }
                    String sourceGatewayTitle = (gatewaySpec.getName() != null && !gatewaySpec.getName().trim().isEmpty()) ? gatewaySpec.getName() : gatewaySpec.getClass().getSimpleName().substring(1) + "[Id:" + gatewaySpec.getId() + "]";
                    FlowNodeWidget flowNodeWidget = sequenceFlowWidget.getTargetNode();
                    String targetNodeTitle = (flowNodeWidget.getName() != null && !flowNodeWidget.getName().trim().isEmpty()) ? flowNodeWidget.getName()
                            : flowNodeWidget.getBaseElementSpec().getClass().getSimpleName().substring(1) + "[Id:" + flowNodeWidget.getBaseElementSpec().getId() + "]";
                    Object[] row = new Object[5];
                    row[0] = sequenceFlow;
                    row[1] = sourceGatewayTitle + " -> " + targetNodeTitle;
                    row[2] = isDefault;
                    row[3] = sequenceFlow.getConditionExpression() == null ? "" : sequenceFlow.getConditionExpression().getContent() == null ? "" : sequenceFlow.getConditionExpression().getContent();
                    row[4] = sequenceFlow.getConditionExpression() == null ? LanguageManager.getLanguage(null) : sequenceFlow.getConditionExpression().getLanguage() == null ? LanguageManager.getLanguage(null) : LanguageManager.getLanguage(sequenceFlow.getConditionExpression().getLanguage());

                    data_local.add(row);
                }
                this.data = data_local;
            }

            @Override
            public List<Object[]> getData() {
                return data;
            }

            @Override
            public void setData(List<Object[]> data) {
                if (gatewaySpec instanceof TComplexGateway) {//initially set null ; if found then set value
                    ((TComplexGateway) gatewaySpec).setDefault(null);
                } else if (gatewaySpec instanceof TInclusiveGateway) {
                    ((TInclusiveGateway) gatewaySpec).setDefault(null);
                } else if (gatewaySpec instanceof TExclusiveGateway) {
                    ((TExclusiveGateway) gatewaySpec).setDefault(null);
                }
                for (Object[] row : data) {
                    TSequenceFlow sequenceFlow = (TSequenceFlow) row[0];
                    Boolean _default = (Boolean) row[2];

                    if (_default) {
                        if (gatewaySpec instanceof TComplexGateway) {
                            ((TComplexGateway) gatewaySpec).setDefault(sequenceFlow.getId());
                        } else if (gatewaySpec instanceof TInclusiveGateway) {
                            ((TInclusiveGateway) gatewaySpec).setDefault(sequenceFlow.getId());
                        } else if (gatewaySpec instanceof TExclusiveGateway) {
                            ((TExclusiveGateway) gatewaySpec).setDefault(sequenceFlow.getId());
                        }
                        sequenceFlow.setConditionExpression(null);
                    } else {
                        String condition = (String) row[3];
                        String language = (((ComboBoxValue<String>) row[4]).getValue());
                        if (sequenceFlow.getConditionExpression() == null) {
                            TFormalExpression expression = new TFormalExpression();
                            expression.setId(NBModelerUtil.getAutoGeneratedStringId());
                            sequenceFlow.setConditionExpression(expression);
                        }
                        sequenceFlow.getConditionExpression().setContent(condition);
                        sequenceFlow.getConditionExpression().setLanguage(language);
                    }

                }
                initData();// this.data = data;
            }

        });
        return new NEntityPropertySupport(this.getModelerScene().getModelerFile(), attributeEntity);
    }

    private ComboBoxPropertySupport getGatewayDirectionTypeProperty() {
        final GatewayWidget gatewayWidget = this;
        final TGateway gatewaySpec = (TGateway) gatewayWidget.getBaseElementSpec();
        ComboBoxListener comboBoxListener = new ComboBoxListener() {
            @Override
            public void setItem(ComboBoxValue value) {
                gatewaySpec.setGatewayDirection((TGatewayDirection) value.getValue());
            }

            @Override
            public ComboBoxValue getItem() {
                return new ComboBoxValue(gatewaySpec.getGatewayDirection(), gatewaySpec.getGatewayDirection().value());
            }

            @Override
            public List<ComboBoxValue> getItemList() {
                ComboBoxValue[] values = new ComboBoxValue[]{
                    new ComboBoxValue(TGatewayDirection.CONVERGING, TGatewayDirection.CONVERGING.value()),
                    new ComboBoxValue(TGatewayDirection.DIVERGING, TGatewayDirection.DIVERGING.value()),
                    new ComboBoxValue(TGatewayDirection.MIXED, TGatewayDirection.MIXED.value()),
                    new ComboBoxValue(TGatewayDirection.UNSPECIFIED, TGatewayDirection.UNSPECIFIED.value())};
                return Arrays.asList(values);
            }

            @Override
            public String getDefaultText() {
                return TGatewayDirection.UNSPECIFIED.value();
            }

            @Override
            public ActionHandler getActionHandler() {
                return null;
            }
        };
        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(), "TGatewayDirection", "Gateway Direction", "", comboBoxListener);
    }

}
