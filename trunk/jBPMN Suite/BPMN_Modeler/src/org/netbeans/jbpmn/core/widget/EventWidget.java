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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.error.ErrorPanel;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.escalation.EscalationPanel;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.signal.SignalPanel;
import org.netbeans.jbpmn.modeler.widget.properties.io_specification.InputParameterPanel;
import org.netbeans.jbpmn.modeler.widget.properties.io_specification.OutputParameterPanel;
import org.netbeans.jbpmn.modeler.widget.properties.user_interface.property.ProcessVariableDefinitionPanel;
import org.netbeans.jbpmn.spec.TActivity;
import org.netbeans.jbpmn.spec.TCatchEvent;
import org.netbeans.jbpmn.spec.TCompensateEventDefinition;
import org.netbeans.jbpmn.spec.TConditionalEventDefinition;
import org.netbeans.jbpmn.spec.TDataInput;
import org.netbeans.jbpmn.spec.TDataInputAssociation;
import org.netbeans.jbpmn.spec.TDataOutput;
import org.netbeans.jbpmn.spec.TDataOutputAssociation;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TError;
import org.netbeans.jbpmn.spec.TErrorEventDefinition;
import org.netbeans.jbpmn.spec.TEscalation;
import org.netbeans.jbpmn.spec.TEscalationEventDefinition;
import org.netbeans.jbpmn.spec.TEvent;
import org.netbeans.jbpmn.spec.TEventDefinition;
import org.netbeans.jbpmn.spec.TFlowElement;
import org.netbeans.jbpmn.spec.TInputSet;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TMessageEventDefinition;
import org.netbeans.jbpmn.spec.TOutputSet;
import org.netbeans.jbpmn.spec.TProcess;
import org.netbeans.jbpmn.spec.TProperty;
import org.netbeans.jbpmn.spec.TRootElement;
import org.netbeans.jbpmn.spec.TSignal;
import org.netbeans.jbpmn.spec.TSignalEventDefinition;
import org.netbeans.jbpmn.spec.TThrowEvent;
import org.netbeans.jbpmn.spec.extend.LanguageManager;
import org.netbeans.jbpmn.spec.extend.MessageHandler;
import org.netbeans.jbpmn.spec.extend.OperationHandler;
import org.netbeans.modeler.config.element.ElementConfigFactory;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ActionHandler;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ComboBoxListener;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.support.ComboBoxPropertySupport;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;
import org.netbeans.modeler.properties.nentity.Column;
import org.netbeans.modeler.properties.nentity.NAttributeEntity;
import org.netbeans.modeler.properties.nentity.NEntityDataListener;
import org.netbeans.modeler.properties.nentity.NEntityPropertySupport;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.openide.nodes.PropertySupport;

public class EventWidget extends FlowNodeWidget {

    public EventWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        super.createPropertySet(set);
//        BPMNUtil.createEventDefinitionPropertySet(, set);
        List<TEventDefinition> eventDefinitions = ((TEvent) this.getBaseElementSpec()).getEventDefinition();
        try {
            if (eventDefinitions.isEmpty()) {
                // name = "None";
            } else if (eventDefinitions.size() == 1) {
                TEventDefinition eventDefinition = eventDefinitions.get(0);
                ElementConfigFactory elementConfigFactory = this.getModelerScene().getModelerFile().getVendorSpecification().getElementConfigFactory();
                elementConfigFactory.createPropertySet(set, eventDefinition, null, false);
            } else if (eventDefinitions.size() > 1) {
                //Multiple Pending
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this instanceof CatchEventWidget) {
            set.put("IO_SPEC_PROP", addDataOutputProperty());
        } else if (this instanceof ThrowEventWidget) {
            set.put("IO_SPEC_PROP", addDataInputProperty());
        }

        TEvent eventSpec = (TEvent) this.getBaseElementSpec();
        for (TEventDefinition eventDefinition : eventSpec.getEventDefinition()) {
            if (eventDefinition instanceof TErrorEventDefinition) {
                set.put("BASIC_PROP", getErrorTypeProperty((TErrorEventDefinition) eventDefinition));
            } else if (eventDefinition instanceof TMessageEventDefinition) {
                set.put("BASIC_PROP", getMessageTypeProperty((MessageHandler) eventDefinition));
                set.put("BASIC_PROP", getOperationProperty((OperationHandler) eventDefinition));
            } else if (eventDefinition instanceof TSignalEventDefinition) {
                set.put("BASIC_PROP", getSignalTypeProperty((TSignalEventDefinition) eventDefinition));
            } else if (eventDefinition instanceof TEscalationEventDefinition) {
                set.put("BASIC_PROP", getEscalationTypeProperty((TEscalationEventDefinition) eventDefinition));
            } else if (eventDefinition instanceof TConditionalEventDefinition) {
                //   set.put("BASIC_PROP", getConditionalTypeProperty((TConditionalEventDefinition)eventDefinition));
            } else if (eventDefinition instanceof TCompensateEventDefinition) {
                set.put("BASIC_PROP", getCompensateTypeProperty((TCompensateEventDefinition) eventDefinition));
            }
        }

        set.put("DATA_ITEMS", addProperty());

    }

    /**
     * ------- TInputOutputSpecification > TDataOutput -------- *
     */
    PropertySupport addDataOutputProperty() {
        final NAttributeEntity attributeEntity = new NAttributeEntity("TDataOutput", "Output Parameter Mapping", "Output Parameter Mapping Desc");
        attributeEntity.setCountDisplay(new String[]{"No Output Parameters set", "One Output Parameter set", "Output Parameters set"});

        final ModelerFile modelerFile = this.getModelerScene().getModelerFile();
        final TCatchEvent outputHandler = (TCatchEvent) this.getBaseElementSpec();
        final TProcess processSpec = (TProcess) modelerFile.getRootElement();

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, String.class));
        columns.add(new Column("From", false, String.class));
        columns.add(new Column("To", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new OutputParameterPanel(modelerFile));//TODO : MAINTAIN EntityComponent row to entity converter
        attributeEntity.setTableDataListener(new NEntityDataListener() {
            List<Object[]> data;

            List<TItemDefinition> itemDefinitions_Selected = new ArrayList<TItemDefinition>();
            int count;

            @Override
            public void initCount() {
                count = outputHandler.getDataOutput().size();
            }

            @Override
            public int getCount() {
                return count;
            }

            private TDataOutputAssociation getDataAssociation(TDataOutput dataOutput) {
                for (TDataOutputAssociation dataOutputAssociation : outputHandler.getDataOutputAssociation()) {
                    if (dataOutputAssociation.getSourceRef().equals(dataOutput.getId())) {
                        return dataOutputAssociation;
                    }
                }
                return null;
            }

            private TProperty getProcessProperty(TDataOutputAssociation dataOutputAssociation) {
                for (TProperty property : processSpec.getProperty()) {
                    if (property.getId().equals(dataOutputAssociation.getTargetRef())) {
                        return property;
                    }
                }
                return null;
            }

            @Override
            public void initData() {
                List<TDataOutput> dataOutputList = outputHandler.getDataOutput();
                List<Object[]> data_local = new LinkedList<Object[]>();//attributeEntity.getColumns().size()];
                Iterator<TDataOutput> itr = dataOutputList.iterator();
                while (itr.hasNext()) {
                    TDataOutput dataOutput = itr.next();
                    TDataOutputAssociation dataOutputAssociation = getDataAssociation(dataOutput);
                    TProperty property = getProcessProperty(dataOutputAssociation);
                    Object[] obj = new Object[]{dataOutput, dataOutputAssociation, property};
                    Object[] row = new Object[3];
                    row[0] = obj;
                    row[1] = dataOutput.getName();
                    row[2] = property.getName();
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
                List<TDataOutput> dataOutputList = new ArrayList<TDataOutput>();

                TOutputSet outputSet = new TOutputSet();
                outputSet.setId(NBModelerUtil.getAutoGeneratedStringId());
                outputHandler.setOutputSet(outputSet);

                outputHandler.setDataOutputAssociation(new ArrayList<TDataOutputAssociation>());

                for (Object[] row : data) {
                    Object[] obj = (Object[]) row[0];
                    TDataOutput dataOutput = (TDataOutput) obj[0];
                    dataOutputList.add(dataOutput);
                    outputHandler.getOutputSet().addDataOutputRefs(dataOutput.getId());//TOutputSet

                    TDataOutputAssociation dataOutputAssociation = (TDataOutputAssociation) obj[1];
                    outputHandler.addDataOutputAssociation(dataOutputAssociation);
                }
                outputHandler.setDataOutput(dataOutputList);
                this.data = data;
            }
        });
        return new NEntityPropertySupport(this.getModelerScene().getModelerFile(), attributeEntity);
    }

    /**
     * ------- TDataInput -------- *
     */
    PropertySupport addDataInputProperty() {
        final NAttributeEntity attributeEntity = new NAttributeEntity("TDataInput", "Input Parameter Mapping", "Input Parameter Mapping Desc");
        attributeEntity.setCountDisplay(new String[]{"No Input Parameters set", "One Input Parameter set", "Input Parameters set"});

        final ModelerFile modelerFile = this.getModelerScene().getModelerFile();
        final TThrowEvent inputHandler = (TThrowEvent) this.getBaseElementSpec();
        final TProcess processSpec = (TProcess) modelerFile.getRootElement();

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, String.class));
        columns.add(new Column("From", false, String.class));
        columns.add(new Column("To", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new InputParameterPanel(modelerFile));
        attributeEntity.setTableDataListener(new NEntityDataListener() {
            List<Object[]> data;
            int count;

            @Override
            public void initCount() {
                count = inputHandler.getDataInput().size();
            }

            @Override
            public int getCount() {
                return count;
            }
            List<TItemDefinition> itemDefinitions_Selected = new ArrayList<TItemDefinition>();

            private TDataInputAssociation getDataAssociation(TDataInput dataInput) {
                for (TDataInputAssociation dataInputAssociation : inputHandler.getDataInputAssociation()) {
                    if (dataInputAssociation.getTargetRef().equals(dataInput.getId())) {
                        return dataInputAssociation;
                    }
                }
                return null;
            }

            private TProperty getProcessProperty(TDataInputAssociation dataInputAssociation) {
                for (TProperty property : processSpec.getProperty()) {
                    if (property.getId().equals(dataInputAssociation.getSourceRef())) {
                        return property;
                    }
                }
                return null;
            }

            @Override
            public void initData() {
                List<TDataInput> dataInputList = inputHandler.getDataInput();
                List<Object[]> data_local = new LinkedList<Object[]>();//attributeEntity.getColumns().size()];
                Iterator<TDataInput> itr = dataInputList.iterator();
                while (itr.hasNext()) {
                    TDataInput dataInput = itr.next();
                    TDataInputAssociation dataInputAssociation = getDataAssociation(dataInput);
                    TProperty property = getProcessProperty(dataInputAssociation);
                    Object[] obj = new Object[]{property, dataInputAssociation, dataInput};
                    Object[] row = new Object[3];
                    row[0] = obj;
                    row[1] = dataInputAssociation.getAssignment() != null ? LanguageManager.getLanguage(dataInputAssociation.getAssignment().getFrom().getLanguage()).getDisplayValue() + " Expression" : property.getName();
                    row[2] = dataInput.getName();
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
                List<TDataInput> dataInputList = new ArrayList<TDataInput>();

//                inputHandler.setInputSet(new ArrayList<TInputSet>());
                TInputSet inputSet = new TInputSet();
                inputSet.setId(NBModelerUtil.getAutoGeneratedStringId());
                inputHandler.setInputSet(inputSet);

                inputHandler.setDataInputAssociation(new ArrayList<TDataInputAssociation>());

                for (Object[] row : data) {
                    Object[] obj = (Object[]) row[0];
                    TDataInput dataInput = (TDataInput) obj[2];

                    dataInputList.add(dataInput);
                    inputHandler.getInputSet().addDataInputRefs(dataInput.getId());//TInputSet

                    TDataInputAssociation dataInputAssociation = (TDataInputAssociation) obj[1];
                    inputHandler.addDataInputAssociation(dataInputAssociation);
                }
                inputHandler.setDataInput(dataInputList);
                this.data = data;
            }
        });
        return new NEntityPropertySupport(this.getModelerScene().getModelerFile(), attributeEntity);
    }

    /**
     * ------- SGUI Property Start -------- *
     */
    private PropertySupport addProperty() {

        final ModelerFile modelerFile = this.getModelerScene().getModelerFile();

        final NAttributeEntity attributeEntity = new NAttributeEntity("TProperty", "Variable Definitions", "Variable Definitions Desc");
        attributeEntity.setCountDisplay(new String[]{"No Variables set", "One Variable set", "Variables set"});
        final TEvent eventSpec = (TEvent) this.getBaseElementSpec();
        final TDefinitions definitions = (TDefinitions) this.getModelerScene().getModelerFile().getDefinitionElement();
//        List<String> standardTypeList = new LinkedList<String>();
//        standardTypeList.add("String");
//        standardTypeList.add("Integer");
//        standardTypeList.add("Boolean");
//        standardTypeList.add("Float");
//        standardTypeList.add("Object");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Name", false, String.class));
//        columns.add(new Column("Standard Type", true, String.class , standardTypeList));
        columns.add(new Column("Data Type", false, String.class));
        columns.add(new Column("ID", false, true, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new ProcessVariableDefinitionPanel(modelerFile));
        attributeEntity.setTableDataListener(new NEntityDataListener/*<TProperty>*/() {

                    List<Object[]> data;
                    int count;

                    @Override
                    public void initCount() {
                        count = eventSpec.getProperty().size();
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public void initData() {
                        List<TProperty> properties = eventSpec.getProperty();
                        List<Object[]> data_local = new LinkedList<Object[]>();
                        Iterator<TProperty> itr = properties.iterator();
                        int i = 0;
                        while (itr.hasNext()) {
                            TProperty property = itr.next();
                            Object[] row = new Object[attributeEntity.getColumns().size()];

                            row[0] = property;
                            row[1] = property.getId();
                            String standardType = definitions.getItemDefinitionStructure(property.getItemSubjectRef());
                            row[2] = standardType;
                            row[3] = property.getId(); //property id storage in hidden form for refractor
                            data_local.add(row);
                        }
                        this.data = data_local;
                    }

                    @Override
                    public List<Object[]> getData() {
                        return data;
                    }

                    @Override
                    public void setData(List data) {
                        List<TProperty> properties = new ArrayList<TProperty>();
                        for (Object[] row : (List<Object[]>) data) {
                            TProperty property;
                            String preId = (String) row[3];
                            property = (TProperty) row[0];
                            String newId = property.getId();

//                            if (!preId.equals(newId)) {  //refract property id from all object where property id is used
//                                for (TFlowElement flowElement : processSpec.getFlowElement()) {
//                                    if (flowElement instanceof TActivity) {
//                                        TActivity activity = (TActivity) flowElement;
//                                        for (TDataInputAssociation dataInputAssociation : activity.getDataInputAssociation()) {
//                                            if (preId.equals(dataInputAssociation.getSourceRef())) {
//                                                dataInputAssociation.setSourceRef(newId);
//                                            }
//                                        }
//                                        for (TDataOutputAssociation dataOutputAssociation : activity.getDataOutputAssociation()) {
//                                            if (preId.equals(dataOutputAssociation.getTargetRef())) {
//                                                dataOutputAssociation.setTargetRef(newId);
//                                            }
//                                        }
//                                    }
//                                }
//                            }
                            properties.add(property);
                        }
                        eventSpec.setProperty(properties);
                        this.data = data;
                    }
                });
        return new NEntityPropertySupport(this.getModelerScene().getModelerFile(), attributeEntity);
    }

    private ComboBoxPropertySupport getCompensateTypeProperty(final TCompensateEventDefinition compensateEventDefinition) {
        final EventWidget eventWidget = this;
        final ModelerFile modelerFile = eventWidget.getModelerScene().getModelerFile();
        final TProcess process = (TProcess) modelerFile.getRootElement();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();

        ComboBoxListener<TActivity> comboBoxListener = new ComboBoxListener<TActivity>() {
            @Override
            public void setItem(ComboBoxValue<TActivity> comboBoxValue) {
                if (comboBoxValue.getValue() != null) {
                    compensateEventDefinition.setActivityRef(comboBoxValue.getId());
                } else {
                    compensateEventDefinition.setActivityRef(null);
                }
            }

            @Override
            public ComboBoxValue<TActivity> getItem() {
                ComboBoxValue<TActivity> comboBoxValue;
                TActivity activity = (TActivity) process.getFlowElement(compensateEventDefinition.getActivityRef());
                if (activity != null) {
                    comboBoxValue = new ComboBoxValue<TActivity>(activity.getId(), activity, (activity.getName() != null && !activity.getName().trim().isEmpty()) ? activity.getName() : "Activity[" + activity.getId() + "]");
                } else {
                    comboBoxValue = new ComboBoxValue<TActivity>(null, null, "");//BUG: initialize activity
                }
                return comboBoxValue;
            }

            @Override
            public List<ComboBoxValue<TActivity>> getItemList() {
                List<ComboBoxValue<TActivity>> values = new ArrayList<ComboBoxValue<TActivity>>();
                values.add(new ComboBoxValue<TActivity>(null, null, ""));
                for (TFlowElement flowElement : process.getFlowElement()) {
                    if (flowElement instanceof TActivity) {
                        TActivity activity = (TActivity) flowElement;
                        values.add(new ComboBoxValue<TActivity>(activity.getId(), activity, (activity.getName() != null && !activity.getName().trim().isEmpty()) ? activity.getName() : "Activity[" + activity.getId() + "]"));
                    }
                }
                return values;
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
        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(), "activity", "Activity", "", comboBoxListener);
    }

//    private ComboBoxPropertySupport getCompensateTypeProperty(final TCompensateEventDefinition compensateEventDefinition) {
//        final BPMNProcessScene processScene = this;
//        final TProcess processSpec = (TProcess)processScene.getRootElementSpec();
//        ComboBoxListener comboBoxListener = new ComboBoxListener() {
//            @Override
//            public void setItem(ComboBoxValue value) {
//                 processSpec.setProcessType((TProcessType)value.getValue());
//            }
//            @Override
//            public ComboBoxValue getItem() {
//                return new ComboBoxValue(processSpec.getProcessType(), processSpec.getProcessType().value());
//             }
//            @Override
//            public List<ComboBoxValue> getItemList() {
//                 ComboBoxValue[] values = new ComboBoxValue[]{
//            new ComboBoxValue(TProcessType.NONE, "None"),
//            new ComboBoxValue(TProcessType.PRIVATE,"Private"),
//            new ComboBoxValue(TProcessType.PUBLIC,"Public")};
//                 return Arrays.asList(values);
//            }
//            @Override
//            public String getDefaultText() {
//               return "None";
//            }
//
//            @Override
//            public ActionHandler getActionHandler() {
//                return null;
//            }
//        };
//        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(),"processType", "Process Type", "The process type attribute provides additional information about the level of abstraction modeled by this process.", comboBoxListener);
//    }
//
    private ComboBoxPropertySupport getEscalationTypeProperty(final TEscalationEventDefinition escalationEventDefinition) {
        final EventWidget eventWidget = this;
        final ModelerFile modelerFile = eventWidget.getModelerScene().getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();

        ComboBoxListener<TEscalation> comboBoxListener = new ComboBoxListener<TEscalation>() {
            @Override
            public void setItem(ComboBoxValue<TEscalation> value) {
                if (value.getValue() != null) {
                    escalationEventDefinition.setEscalationRef(value.getId());
                } else {
                    escalationEventDefinition.setEscalationRef(null);
                }
            }

            @Override
            public ComboBoxValue<TEscalation> getItem() {
                ComboBoxValue<TEscalation> comboBoxValue;
                TEscalation escalation = (TEscalation) definition.getRootElement(escalationEventDefinition.getEscalationRef(), TEscalation.class);
                if (escalation != null) {
                    comboBoxValue = new ComboBoxValue<TEscalation>(escalation.getId(), escalation, escalation.getName() + "[" + escalation.getEscalationCode() + "]");
                } else {
                    comboBoxValue = new ComboBoxValue<TEscalation>(null, null, "");
                }
                return comboBoxValue;
            }

            @Override
            public List<ComboBoxValue<TEscalation>> getItemList() {
                List<ComboBoxValue<TEscalation>> values = new ArrayList<ComboBoxValue<TEscalation>>();
                values.add(new ComboBoxValue<TEscalation>(null, null, ""));
                for (TRootElement rootElement : definition.getRootElement()) {
                    if (rootElement instanceof TEscalation) {
                        TEscalation escalation = (TEscalation) rootElement;
                        values.add(new ComboBoxValue<TEscalation>(escalation.getId(), escalation, escalation.getName() + "[" + escalation.getEscalationCode() + "]"));
                    }
                }
                return values;
            }

            @Override
            public String getDefaultText() {
                return "";
            }

            @Override
            public ActionHandler getActionHandler() {
                ActionHandler<TEscalation> actHan = new ActionHandler<TEscalation>() {
                    @Override
                    public EntityComponent getItemPanel() {
                        return new EscalationPanel(modelerFile);
                    }

                    @Override
                    public void createItem(ComboBoxValue<TEscalation> comboBoxValue) {
                        definition.addRootElement(comboBoxValue.getValue());
                    }

                    @Override
                    public int showRemoveItemDialog() {
                        return JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Escalation Element ?", "Delete Escalation Element", JOptionPane.OK_CANCEL_OPTION);
                    }

                    @Override
                    public void deleteItem(ComboBoxValue<TEscalation> comboBoxValue) {
                        definition.removeRootElement(comboBoxValue.getValue());
                        escalationEventDefinition.setEscalationRef(null);
                    }

                    @Override
                    public void updateItem(ComboBoxValue<TEscalation> comboBoxValue) {
//                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };
                return actHan;
            }
        };
        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(), "escalation", "Escalation", "The escalation attribute provides additional information about the event escalation definition (name , code , structure reference).", comboBoxListener);
    }

    private ComboBoxPropertySupport getSignalTypeProperty(final TSignalEventDefinition signalEventDefinition) {
        final EventWidget eventWidget = this;
        final ModelerFile modelerFile = eventWidget.getModelerScene().getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();

        ComboBoxListener<TSignal> comboBoxListener = new ComboBoxListener<TSignal>() {
            @Override
            public void setItem(ComboBoxValue<TSignal> comboBoxValue) {
                if (comboBoxValue.getValue() != null) {
                    signalEventDefinition.setSignalRef(comboBoxValue.getId());
                } else {
                    signalEventDefinition.setSignalRef(null);
                }
            }

            @Override
            public ComboBoxValue<TSignal> getItem() {
                ComboBoxValue<TSignal> comboBoxValue;
                TSignal signal = (TSignal) definition.getRootElement(signalEventDefinition.getSignalRef(), TSignal.class);
                if (signal != null) {
                    comboBoxValue = new ComboBoxValue<TSignal>(signal.getId(), signal, signal.getName());
                } else {
                    comboBoxValue = new ComboBoxValue<TSignal>(null, null, "");
                }
                return comboBoxValue;
            }

            @Override
            public List<ComboBoxValue<TSignal>> getItemList() {
                List<ComboBoxValue<TSignal>> values = new ArrayList<ComboBoxValue<TSignal>>();
                values.add(new ComboBoxValue<TSignal>(null, null, ""));
                for (TRootElement rootElement : definition.getRootElement()) {
                    if (rootElement instanceof TSignal) {
                        TSignal signal = (TSignal) rootElement;
                        values.add(new ComboBoxValue<TSignal>(signal.getId(), signal, signal.getName()));
                    }
                }
                return values;
            }

            @Override
            public String getDefaultText() {
                return "";
            }

            @Override
            public ActionHandler getActionHandler() {
                ActionHandler<TSignal> actHan = new ActionHandler<TSignal>() {
                    @Override
                    public EntityComponent getItemPanel() {
                        return new SignalPanel(modelerFile);
                    }

                    @Override
                    public void createItem(ComboBoxValue<TSignal> comboBoxValue) {
                        definition.addRootElement(comboBoxValue.getValue());
                    }

                    @Override
                    public int showRemoveItemDialog() {
                        return JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Signal Element ?", "Delete Signal Element", JOptionPane.OK_CANCEL_OPTION);
                    }

                    @Override
                    public void deleteItem(ComboBoxValue<TSignal> comboBoxValue) {
                        definition.removeRootElement(comboBoxValue.getValue());
                        signalEventDefinition.setSignalRef(null);
                    }

                    @Override
                    public void updateItem(ComboBoxValue<TSignal> comboBoxValue) {
//                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };
                return actHan;
            }
        };
        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(), "signal", "Signal", "The signal attribute provides additional information about the event signal definition (name).", comboBoxListener);
    }

    private ComboBoxPropertySupport getErrorTypeProperty(final TErrorEventDefinition errorEventDefinition) {
        final EventWidget eventWidget = this;
        final ModelerFile modelerFile = eventWidget.getModelerScene().getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();

        ComboBoxListener<TError> comboBoxListener = new ComboBoxListener<TError>() {
            @Override
            public void setItem(ComboBoxValue<TError> value) {
                if (value.getValue() != null) {
                    errorEventDefinition.setErrorRef(value.getId());
                } else {
                    errorEventDefinition.setErrorRef(null);
                }
            }

            @Override
            public ComboBoxValue<TError> getItem() {
                ComboBoxValue<TError> comboBoxValue;
                TError error = (TError) definition.getRootElement(errorEventDefinition.getErrorRef(), TError.class);
                if (error != null) {
                    comboBoxValue = new ComboBoxValue<TError>(error.getId(), error, error.getName());
                } else {
                    comboBoxValue = new ComboBoxValue<TError>(null, null, "");
                }
                return comboBoxValue;
            }

            @Override
            public List<ComboBoxValue<TError>> getItemList() {
                List<ComboBoxValue<TError>> values = new ArrayList<ComboBoxValue<TError>>();
                values.add(new ComboBoxValue<TError>(null, null, ""));
                for (TRootElement rootElement : definition.getRootElement()) {
                    if (rootElement instanceof TError) {
                        TError error = (TError) rootElement;
                        values.add(new ComboBoxValue<TError>(error.getId(), error, error.getName()));
                    }
                }
                return values;
            }

            @Override
            public String getDefaultText() {
                return "";
            }

            @Override
            public ActionHandler getActionHandler() {
                ActionHandler<TError> actHan = new ActionHandler<TError>() {
                    @Override
                    public EntityComponent getItemPanel() {
                        return new ErrorPanel(modelerFile);
                    }

                    @Override
                    public void createItem(ComboBoxValue<TError> comboBoxValue) {
                        definition.addRootElement(comboBoxValue.getValue());
                    }

                    @Override
                    public int showRemoveItemDialog() {
                        return JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Error Element ?", "Delete Error Element", JOptionPane.OK_CANCEL_OPTION);
                    }

                    @Override
                    public void deleteItem(ComboBoxValue<TError> comboBoxValue) {
                        definition.removeRootElement(comboBoxValue.getValue());
                        errorEventDefinition.setErrorRef(null);
                    }

                    @Override
                    public void updateItem(ComboBoxValue<TError> comboBoxValue) {
//                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };
                return actHan;
            }
        };
        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(), "error", "Error", "The error attribute provides additional information about the event error definition (name , code , structure reference).", comboBoxListener);
    }

    TEventDefinition getEventDefinition(Class clazz) {
        List<TEventDefinition> eventDefinitions = ((TEvent) this.getBaseElementSpec()).getEventDefinition();
        for (TEventDefinition eventDefinition : eventDefinitions) {
            if (eventDefinition.getClass() == clazz) {
                return eventDefinition;
            }
        }
        return null;
    }

//    private String
//
//    /**
//     * @return the conditionEmbedded
//     */
//    public String getConditionEmbedded() {
//        TConditionalEventDefinition conditionalEventDefinition = (TConditionalEventDefinition) getEventDefinition(TConditionalEventDefinition.class);
//        if (conditionalEventDefinition != null
//                && conditionalEventDefinition.getCondition() != null
//                && conditionalEventDefinition.getCondition().getContent() != null) {
//            return conditionalEventDefinition.getCondition().getContent();
//        }
//        return "";
//    }
//
//    /**
//     * @param conditionEmbedded the conditionEmbedded to set
//     */
//    public void setConditionEmbedded(String conditionEmbedded) {
//        TConditionalEventDefinition conditionalEventDefinition = (TConditionalEventDefinition) getEventDefinition(TConditionalEventDefinition.class);
//        if (conditionalEventDefinition != null) {
//            if (conditionalEventDefinition.getCondition() == null) {
//                conditionalEventDefinition.setCondition(new TExpression());
//            }
//            conditionalEventDefinition.getCondition().setContent(conditionEmbedded);
//        }
//    }
}
