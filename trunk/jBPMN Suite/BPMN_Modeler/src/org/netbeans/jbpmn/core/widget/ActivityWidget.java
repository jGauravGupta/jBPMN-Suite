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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.util.BPMNProcessUtil;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.loop.attribute.MultiInstanceLoopCharacteristicsPropertyPanel;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.loop.attribute.StandardLoopCharacteristicsPropertyPanel;
import org.netbeans.jbpmn.modeler.widget.properties.io_specification.InputParameterPanel;
import org.netbeans.jbpmn.modeler.widget.properties.io_specification.OutputParameterPanel;
import org.netbeans.jbpmn.modeler.widget.properties.user_interface.property.ProcessVariableDefinitionPanel;
import org.netbeans.jbpmn.spec.TActivity;
import org.netbeans.jbpmn.spec.TBoundaryEvent;
import org.netbeans.jbpmn.spec.TDataInput;
import org.netbeans.jbpmn.spec.TDataInputAssociation;
import org.netbeans.jbpmn.spec.TDataOutput;
import org.netbeans.jbpmn.spec.TDataOutputAssociation;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TInputOutputSpecification;
import org.netbeans.jbpmn.spec.TInputSet;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TLoopCharacteristics;
import org.netbeans.jbpmn.spec.TMultiInstanceLoopCharacteristics;
import org.netbeans.jbpmn.spec.TOutputSet;
import org.netbeans.jbpmn.spec.TProcess;
import org.netbeans.jbpmn.spec.TProperty;
import org.netbeans.jbpmn.spec.TStandardLoopCharacteristics;
import org.netbeans.jbpmn.spec.extend.LanguageManager;
import org.netbeans.modeler.config.document.FlowDimensionType;
import org.netbeans.modeler.config.palette.SubCategoryNodeConfig;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.properties.embedded.EmbeddedDataListener;
import org.netbeans.modeler.properties.embedded.EmbeddedPropertySupport;
import org.netbeans.modeler.properties.embedded.GenericEmbedded;
import org.netbeans.modeler.properties.embedded.GenericEmbeddedEditor;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ActionHandler;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ComboBoxListener;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.support.ComboBoxPropertySupport;
import org.netbeans.modeler.properties.nentity.Column;
import org.netbeans.modeler.properties.nentity.NAttributeEntity;
import org.netbeans.modeler.properties.nentity.NEntityDataListener;
import org.netbeans.modeler.properties.nentity.NEntityPropertySupport;
import org.netbeans.modeler.scene.AbstractModelerScene;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.generic.ElementCustomPropertySupport;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;
import org.netbeans.modeler.widget.transferable.MoveWidgetTransferable;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.util.Exceptions;

public class ActivityWidget extends FlowNodeWidget {

    private List<BoundaryEventWidget> boundaryEventWidgets = new ArrayList<BoundaryEventWidget>();

    public ActivityWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);

    }

    @Override
    public void init() {
        manageLoopTypeIcon();
        manageCompensationIcon();
    }

    public void manageLoopTypeIcon() {
        TLoopCharacteristics loopCharacteristics = ((TActivity) this.getBaseElementSpec()).getLoopCharacteristics();
        setElementValue("standard", "opacity", "0");
        setElementValue("parallel", "opacity", "0");
        setElementValue("sequential", "opacity", "0");

        if (loopCharacteristics instanceof TStandardLoopCharacteristics) {
            setElementValue("standard", "opacity", "1");
        } else if (loopCharacteristics instanceof TMultiInstanceLoopCharacteristics) {
            if (((TMultiInstanceLoopCharacteristics) loopCharacteristics).isIsSequential()) {
                setElementValue("sequential", "opacity", "1");
            } else {
                setElementValue("parallel", "opacity", "1");
            }
        }
    }

    public void manageCompensationIcon() {
        if (((TActivity) this.getBaseElementSpec()).getIsForCompensation()) {
            setElementValue("compensation", "opacity", "1");
        } else {
            setElementValue("compensation", "opacity", "0");
        }
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        try {
            super.createPropertySet(set);
            set.put("BASIC_PROP", new ElementCustomPropertySupport(this.getModelerScene().getModelerFile(), (TActivity) this.getBaseElementSpec(), Boolean.class,
                    "isForCompensation", "Is For Compensation", "A flag that identifies whether this activity is intended for the purposes of compensation.",
                    new PropertyChangeListener<Boolean>() {
                        @Override
                        public void changePerformed(Boolean value) {
                            //  ((TActivity)ActivityWidget.this.getFlowNodeSpec()).setIsForCompensation(value);
                            manageCompensationIcon();
                        }
                    }));

            set.put("BASIC_PROP", getLoopCharacteristicProperty());//Move To Activity

//            set.put("BASIC_PROP", new LoopAttributesPropertySupport((TActivity) this.getBaseElementSpec()));
            final ModelerFile modelerFile = this.getModelerScene().getModelerFile();
            final TActivity activitySpec = (TActivity) this.getBaseElementSpec();
            GenericEmbedded entity = new GenericEmbedded("loopProperties", "LoopType Attributes", "LoopType Attributes of the BPMN Element") {
                StandardLoopCharacteristicsPropertyPanel standardLoopCharacteristicsPropertyPanel = new StandardLoopCharacteristicsPropertyPanel(modelerFile);
                MultiInstanceLoopCharacteristicsPropertyPanel multiInstanceLoopCharacteristicsPropertyPanel = new MultiInstanceLoopCharacteristicsPropertyPanel(modelerFile, activitySpec);

                public GenericEmbeddedEditor getEntityEditor() {
                    if (activitySpec.getLoopCharacteristics() instanceof TStandardLoopCharacteristics) {
                        return standardLoopCharacteristicsPropertyPanel;
                    } else if (activitySpec.getLoopCharacteristics() instanceof TMultiInstanceLoopCharacteristics) {
                        return multiInstanceLoopCharacteristicsPropertyPanel;
                    }
                    return null;
                }
            };
            entity.setDataListener(new EmbeddedDataListener<TLoopCharacteristics>() {
                TActivity baseElement;

                @Override
                public void init() {
                    baseElement = (TActivity) ActivityWidget.this.getBaseElementSpec();
                }

                @Override
                public TLoopCharacteristics getData() {
                    return baseElement.getLoopCharacteristics();
                }

                @Override
                public void setData(TLoopCharacteristics data) {
                    baseElement.setLoopCharacteristics(data);
                }

                @Override
                public String getDisplay() {
                    return "";
                }

            });
//            entity.setEntityEditor(new LoopCharacteristicsPanel());
            set.put("BASIC_PROP", new EmbeddedPropertySupport(this.getModelerScene().getModelerFile(), entity));

            set.put("BASIC_PROP", BPMNProcessUtil.addTResourceRole(this.getModelerScene(), this.getBaseElementSpec()));

            set.put("IO_SPEC_PROP", addDataInputProperty());
            set.put("IO_SPEC_PROP", addDataOutputProperty());

//            set.put("IO_SPEC_PROP", addDataOutputProperty());
//
//            set.put("BASIC_PROP", addTProperty());
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }

        set.put("DATA_ITEMS", addProperty());

    }

    /**
     * ------- SGUI Property Start -------- *
     */
    private PropertySupport addProperty() {

        final ModelerFile modelerFile = this.getModelerScene().getModelerFile();

        final NAttributeEntity attributeEntity = new NAttributeEntity("TProperty", "Variable Definitions", "Variable Definitions Desc");
        attributeEntity.setCountDisplay(new String[]{"No Variables set", "One Variable set", "Variables set"});
        final TActivity activitySpec = (TActivity) this.getBaseElementSpec();
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
                        count = activitySpec.getProperty().size();
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public void initData() {
                        List<TProperty> properties = activitySpec.getProperty();
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
                        activitySpec.setProperty(properties);
                        this.data = data;
                    }
                });
        return new NEntityPropertySupport(this.getModelerScene().getModelerFile(), attributeEntity);
    }

    private ComboBoxPropertySupport getLoopCharacteristicProperty() {
        final ActivityWidget activityWidget = this;
        final TActivity activitySpec = (TActivity) activityWidget.getBaseElementSpec();
        ComboBoxListener comboBoxListener = new ComboBoxListener() {
            @Override
            public void setItem(ComboBoxValue value) {
                activitySpec.setLoopCharacteristics((TLoopCharacteristics) value.getValue());
                activityWidget.manageLoopTypeIcon();
            }

            @Override
            public ComboBoxValue getItem() {
                if (activitySpec.getLoopCharacteristics() instanceof TStandardLoopCharacteristics) {
                    return new ComboBoxValue(activitySpec.getLoopCharacteristics(), "Standard");
                } else if (activitySpec.getLoopCharacteristics() instanceof TMultiInstanceLoopCharacteristics) {
                    if (((TMultiInstanceLoopCharacteristics) activitySpec.getLoopCharacteristics()).isIsSequential()) {
                        return new ComboBoxValue(activitySpec.getLoopCharacteristics(), "Multi Instance Sequential");
                    } else {
                        return new ComboBoxValue(activitySpec.getLoopCharacteristics(), "Multi Instance Parallel");
                    }
                } else {
                    return new ComboBoxValue(null, "None");
                }
            }

            @Override
            public List<ComboBoxValue> getItemList() {
                List<ComboBoxValue> values = new ArrayList<ComboBoxValue>();
                values.add(new ComboBoxValue(null, "None"));
                values.add(new ComboBoxValue(new TStandardLoopCharacteristics(NBModelerUtil.getAutoGeneratedStringId()), "Standard"));
                values.add(new ComboBoxValue(new TMultiInstanceLoopCharacteristics(NBModelerUtil.getAutoGeneratedStringId(), Boolean.TRUE), "Multi Instance Sequential"));
                values.add(new ComboBoxValue(new TMultiInstanceLoopCharacteristics(NBModelerUtil.getAutoGeneratedStringId(), Boolean.FALSE), "Multi Instance Parallel"));
                return values;
            }

            @Override
            public String getDefaultText() {
                return "None";
            }

            @Override
            public ActionHandler getActionHandler() {
                return null;
            }
        };
        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(), "loop", "Loop Type", "Repeated activity execution (parallel or sequential) can be displayed through different loop types", comboBoxListener);
    }

    /**
     * ------- TInputOutputSpecification > TDataInput -------- *
     */
    PropertySupport addDataInputProperty() {
        final NAttributeEntity attributeEntity = new NAttributeEntity("TDataInput", "Input Parameter Mapping", "Input Parameter Mapping Desc");
        attributeEntity.setCountDisplay(new String[]{"No Input Parameters set", "One Input Parameter set", "Input Parameters set"});

        final ModelerFile modelerFile = this.getModelerScene().getModelerFile();
        final TActivity activitySpec = (TActivity) this.getBaseElementSpec();
        final TProcess processSpec = (TProcess) modelerFile.getRootElement();
        final TInputOutputSpecification inputOutputSpecification;
        if (activitySpec.getIoSpecification() == null) {
            TInputOutputSpecification ioSpec = new TInputOutputSpecification();
            ioSpec.setId(NBModelerUtil.getAutoGeneratedStringId());
            activitySpec.setIoSpecification(ioSpec);
        }
        inputOutputSpecification = activitySpec.getIoSpecification();
//        final TDefinitions definitions = (TDefinitions) this.getModelerScene().getModelerFile().getDefinitionElement();
//        List<String> standardTypeList = new LinkedList<String>();
//        standardTypeList.add("String");
//        standardTypeList.add("Integer");
//        standardTypeList.add("Boolean");
//        standardTypeList.add("Float");
//        standardTypeList.add("Object");
        List<Column> columns = new ArrayList<Column>();
//        columns.add(new Column("Id", true, String.class));

        columns.add(new Column("OBJECT", false, true, String.class));
        columns.add(new Column("From", false, String.class));
        columns.add(new Column("To", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new InputParameterPanel(modelerFile));//TODO : MAINTAIN EntityComponent row to entity converter
        attributeEntity.setTableDataListener(new NEntityDataListener() {
            List<Object[]> data;
            int count;

            @Override
            public void initCount() {
                count = inputOutputSpecification.getDataInput().size();
            }

            @Override
            public int getCount() {
                return count;
            }
            List<TItemDefinition> itemDefinitions_Selected = new ArrayList<TItemDefinition>();

            private TDataInputAssociation getDataAssociation(TDataInput dataInput) {
                for (TDataInputAssociation dataInputAssociation : activitySpec.getDataInputAssociation()) {
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
                List<TDataInput> dataInputList = inputOutputSpecification.getDataInput();
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

                inputOutputSpecification.setInputSet(new ArrayList<TInputSet>());
                TInputSet inputSet = new TInputSet();
                inputSet.setId(NBModelerUtil.getAutoGeneratedStringId());
                inputOutputSpecification.addInputSet(inputSet);

                activitySpec.setDataInputAssociation(new ArrayList<TDataInputAssociation>());

                for (Object[] row : data) {
                    Object[] obj = (Object[]) row[0];
                    TDataInput dataInput = (TDataInput) obj[2];

                    dataInputList.add(dataInput);//TDataInput
                    inputOutputSpecification.getInputSet().get(0).addDataInputRefs(dataInput.getId());//TInputSet

                    TDataInputAssociation dataInputAssociation = (TDataInputAssociation) obj[1];
                    activitySpec.addDataInputAssociation(dataInputAssociation);
                }
                if (activitySpec.getIoSpecification() == null) {
                    TInputOutputSpecification ioSpec = new TInputOutputSpecification();
                    ioSpec.setId(NBModelerUtil.getAutoGeneratedStringId());
                    activitySpec.setIoSpecification(ioSpec);
                }
                activitySpec.getIoSpecification().setDataInput(dataInputList);
                this.data = data;
            }
        });
        return new NEntityPropertySupport(this.getModelerScene().getModelerFile(), attributeEntity);
    }

    /**
     * ------- TInputOutputSpecification > TDataOutput -------- *
     */
    PropertySupport addDataOutputProperty() {
        final NAttributeEntity attributeEntity = new NAttributeEntity("TDataOutput", "Output Parameter Mapping", "Output Parameter Mapping Desc");
        attributeEntity.setCountDisplay(new String[]{"No Output Parameters set", "One Output Parameter set", "Output Parameters set"});

        final ModelerFile modelerFile = this.getModelerScene().getModelerFile();
        final TActivity activitySpec = (TActivity) this.getBaseElementSpec();
        final TProcess processSpec = (TProcess) modelerFile.getRootElement();
        final TInputOutputSpecification inputOutputSpecification;
        if (activitySpec.getIoSpecification() == null) {
            TInputOutputSpecification ioSpec = new TInputOutputSpecification();
            ioSpec.setId(NBModelerUtil.getAutoGeneratedStringId());
            activitySpec.setIoSpecification(ioSpec);
        }
        inputOutputSpecification = activitySpec.getIoSpecification();
//        final TDefinitions definitions = (TDefinitions) this.getModelerScene().getModelerFile().getDefinitionElement();
//        List<String> standardTypeList = new LinkedList<String>();
//        standardTypeList.add("String");
//        standardTypeList.add("Integer");
//        standardTypeList.add("Boolean");
//        standardTypeList.add("Float");
//        standardTypeList.add("Object");
        List<Column> columns = new ArrayList<Column>();
//        columns.add(new Column("Id", true, String.class));

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
                count = inputOutputSpecification.getDataOutput().size();
            }

            @Override
            public int getCount() {
                return count;
            }

            private TDataOutputAssociation getDataAssociation(TDataOutput dataOutput) {
                for (TDataOutputAssociation dataOutputAssociation : activitySpec.getDataOutputAssociation()) {
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
                List<TDataOutput> dataOutputList = inputOutputSpecification.getDataOutput();
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

                inputOutputSpecification.setOutputSet(new ArrayList<TOutputSet>());
                TOutputSet outputSet = new TOutputSet();
                outputSet.setId(NBModelerUtil.getAutoGeneratedStringId());
                inputOutputSpecification.addOutputSet(outputSet);

                activitySpec.setDataOutputAssociation(new ArrayList<TDataOutputAssociation>());

                for (Object[] row : data) {
                    Object[] obj = (Object[]) row[0];
                    TDataOutput dataOutput = (TDataOutput) obj[0];

//                    TItemDefinition itemDefinition = new TItemDefinition();
//                    itemDefinition.setId(BPMNUtil.getAutoGeneratedStringId() + "_" + dataOutput.getId());
//                    itemDefinition.setStructureRef(dataOutput.getItemSubjectRef());
//                   dataOutput.setItemSubjectRef(itemDefinition.getId());
//                    definitions.addRootElement(itemDefinition);
                    dataOutputList.add(dataOutput);//TDataOutput
                    inputOutputSpecification.getOutputSet().get(0).addDataOutputRefs(dataOutput.getId());//TOutputSet

                    TDataOutputAssociation dataOutputAssociation = (TDataOutputAssociation) obj[1];
                    activitySpec.addDataOutputAssociation(dataOutputAssociation);
                }
                activitySpec.getIoSpecification().setDataOutput(dataOutputList);
                this.data = data;
            }
        });

        return new NEntityPropertySupport(this.getModelerScene().getModelerFile(), attributeEntity);
    }

    ///-----------------
//    PropertySupport addDataInputProperty() {
//        final NAttributeEntity attributeEntity = new NAttributeEntity("TDataInput", "Input Set", "Data Input Desc");
//        attributeEntity.setCountDisplay(new String[]{"No DataInputs set", "One DataInput set", "DataInputs set"});
//        final TActivity activitySpec = (TActivity) this.getBaseElementSpec();
//        final TInputOutputSpecification inputOutputSpecification;
//        if (activitySpec.getIoSpecification() == null) {
//            activitySpec.setIoSpecification(new TInputOutputSpecification());
//        }
//        inputOutputSpecification = activitySpec.getIoSpecification();
//        final TDefinitions definitions = (TDefinitions) this.getModelerScene().getModelerFile().getDefinitionElement();
//        List<String> standardTypeList = new LinkedList<String>();
//        standardTypeList.add("String");
//        standardTypeList.add("Integer");
//        standardTypeList.add("Boolean");
//        standardTypeList.add("Float");
//        standardTypeList.add("Object");
//        List<Column> columns = new ArrayList<Column>();
//        columns.add(new Column("Id", true, String.class));
//        columns.add(new Column("Name", true, String.class));
//        columns.add(new Column("Standard Type", true, String.class, standardTypeList));
//        columns.add(new Column("Custom Type", true, String.class));
//        attributeEntity.setColumns(columns);
////        attributeEntity.setCustomDialog(new PropertyDialog(attributeEntity));//TODO : MAINTAIN EntityComponent row to entity converter
//        attributeEntity.setTableDataListener(new TableDataListener() {
//            Object[][] data;
//            List<TItemDefinition> itemDefinitions_Selected = new ArrayList<TItemDefinition>();
//
//            @Override
//            public void init() {
//                List<TDataInput> dataInputList = inputOutputSpecification.getDataInput();
//                Object[][] data_local = new Object[dataInputList.size()][attributeEntity.getColumns().size()];
//                Iterator<TDataInput> itr = dataInputList.iterator();
//                int i = 0;
//                while (itr.hasNext()) {
//                    TDataInput dataInput = itr.next();
//                    Object[] row = new Object[4];
//                    row[0] = dataInput.getId();
//                    row[1] = dataInput.getName();
////                        row[2] = dataInput.isIsCollection();
//                    String itemSubjectRef = dataInput.getItemSubjectRef() == null ? null
//                            : dataInput.getItemSubjectRef().getLocalPart();
//                    TItemDefinition itemDefinition = itemSubjectRef == null ? null
//                            : (TItemDefinition) definitions.getRootElement(itemSubjectRef, TItemDefinition.class);
//                    QName standardType_Qname = itemDefinition == null ? null
//                            : itemDefinition.getStructureRef();
//                    String standardType = standardType_Qname == null ? null : standardType_Qname.getLocalPart();
////                        row[2] = standardType;
//                    if (itemDefinition != null) {
//                        itemDefinitions_Selected.add(itemDefinition);
//                    }
//                    if ("String".equals(standardType) || "Integer".equals(standardType) || "Boolean".equals(standardType) || "Float".equals(standardType) || "Object".equals(standardType)) {
//                        row[2] = standardType;
//                        row[3] = "";
//                    } else {
//                        row[2] = "Object";
//                        row[3] = standardType;
//                    }
//                    data_local[i++] = row;
//                }
//                this.data = data_local;
//            }
//
//            @Override
//            public Object[][] getData() {
//                return data;
//            }
//
//            @Override
//            public void setData(Object[][] data) {
//                List<TDataInput> dataInputList = new ArrayList<TDataInput>();
//                for (TItemDefinition itemDefinition : itemDefinitions_Selected) {
//                    definitions.removeRootElement(itemDefinition);
//                }
//
//                inputOutputSpecification.setInputSet(new ArrayList<TInputSet>());
//                TInputSet inputSet = new TInputSet();
//                inputSet.setId(BPMNUtil.getAutoGeneratedStringId());
//                inputOutputSpecification.addInputSet(inputSet);
//
//
//                for (Object[] row : data) {
//                    TDataInput dataInput = new TDataInput();
//                    dataInput.setId((String) row[0]);
//                    dataInput.setName((String) row[1]);
//
//                    TItemDefinition itemDefinition = new TItemDefinition();
//                    itemDefinition.setId(BPMNUtil.getAutoGeneratedStringId() + "_" + dataInput.getId());
//
//                    if ("Object".equals((String) row[2])) {
//                        itemDefinition.setStructureRef(new QName("", (String) row[3], ""));
//                    } else {
//                        itemDefinition.setStructureRef(new QName("", (String) row[2], ""));
//                    }
//                    dataInput.setItemSubjectRef(new QName("", itemDefinition.getId(), ""));
//
//                    definitions.addRootElement(itemDefinition);
//
//                    dataInputList.add(dataInput);
//
//                    inputOutputSpecification.getInputSet().get(0).addDataInputRefs(dataInput.getId());
//
//                }
//                activitySpec.getIoSpecification().setDataInput(dataInputList);
//                this.data = data;
//            }
//        });
//        return new CustomNAttributeSupport(attributeEntity);
//    }
    /**
     * ------- TInputOutputSpecification > TDataInput -------- *
     */
    /**
     * ------- TInputOutputSpecification > TDataOutput -------- *
     */
//    PropertySupport addDataOutputProperty() {
//        final NAttributeEntity attributeEntity = new NAttributeEntity("TDataOutput", "Output Set", "Data Output Desc");
//        attributeEntity.setCountDisplay(new String[]{"No DataOutputs set", "One DataOutput set", "DataOutputs set"});
//        final TActivity activitySpec = (TActivity) this.getBaseElementSpec();
//        final TInputOutputSpecification inputOutputSpecification;
//        if (activitySpec.getIoSpecification() == null) {
//            activitySpec.setIoSpecification(new TInputOutputSpecification());
//        }
//        inputOutputSpecification = activitySpec.getIoSpecification();
//
//        final TDefinitions definitions = (TDefinitions) this.getModelerScene().getModelerFile().getDefinitionElement();
//        List<String> standardTypeList = new LinkedList<String>();
//        standardTypeList.add("String");
//        standardTypeList.add("Integer");
//        standardTypeList.add("Boolean");
//        standardTypeList.add("Float");
//        standardTypeList.add("Object");
//        List<Column> columns = new ArrayList<Column>();
//        columns.add(new Column("Id", true, String.class));
//        columns.add(new Column("Name", true, String.class));
//        columns.add(new Column("Standard Type", true, String.class, standardTypeList));
//        columns.add(new Column("Custom Type", true, String.class));
//        attributeEntity.setColumns(columns);
////        attributeEntity.setCustomDialog(new PropertyDialog(attributeEntity));//TODO : MAINTAIN EntityComponent row to entity converter
//        attributeEntity.setTableDataListener(new TableDataListener() {
//            Object[][] data;
//            List<TItemDefinition> itemDefinitions_Selected = new ArrayList<TItemDefinition>();
//
//            @Override
//            public void init() {
//                List<TDataOutput> dataOutputList = inputOutputSpecification.getDataOutput();
//                Object[][] data_local = new Object[dataOutputList.size()][attributeEntity.getColumns().size()];
//                Iterator<TDataOutput> itr = dataOutputList.iterator();
//                int i = 0;
//                while (itr.hasNext()) {
//                    TDataOutput dataOutput = itr.next();
//                    Object[] row = new Object[4];
//                    row[0] = dataOutput.getId();
//                    row[1] = dataOutput.getName();
////                        row[2] = dataOutput.isIsCollection();
//                    String itemSubjectRef = dataOutput.getItemSubjectRef() == null ? null
//                            : dataOutput.getItemSubjectRef().getLocalPart();
//                    TItemDefinition itemDefinition = itemSubjectRef == null ? null
//                            : (TItemDefinition) definitions.getRootElement(itemSubjectRef, TItemDefinition.class);
////                    QName standardType_Qname = itemDefinition == null ? null
////                            : itemDefinition.getStructureRef();
//                    String standardType = itemDefinition == null ? null : itemDefinition.getStructureRef();
////                        row[2] = standardType;
//                    if (itemDefinition != null) {
//                        itemDefinitions_Selected.add(itemDefinition);
//                    }
//                    if ("String".equals(standardType) || "Integer".equals(standardType) || "Boolean".equals(standardType) || "Float".equals(standardType) || "Object".equals(standardType)) {
//                        row[2] = standardType;
//                        row[3] = "";
//                    } else {
//                        row[2] = "Object";
//                        row[3] = standardType;
//                    }
//                    data_local[i++] = row;
//                }
//                this.data = data_local;
//            }
//
//            @Override
//            public Object[][] getData() {
//                return data;
//            }
//
//            @Override
//            public void setData(Object[][] data) {
//                List<TDataOutput> dataOutputList = new ArrayList<TDataOutput>();
//                for (TItemDefinition itemDefinition : itemDefinitions_Selected) {
//                    definitions.removeRootElement(itemDefinition);
//                }
//
//                inputOutputSpecification.setOutputSet(new ArrayList<TOutputSet>());
//                TOutputSet outputSet = new TOutputSet();
//                outputSet.setId(BPMNUtil.getAutoGeneratedStringId());
//                inputOutputSpecification.addOutputSet(outputSet);
//
//
//                for (Object[] row : data) {
//                    TDataOutput dataOutput = new TDataOutput();
//                    dataOutput.setId((String) row[0]);
//                    dataOutput.setName((String) row[1]);
//
//                    TItemDefinition itemDefinition = new TItemDefinition();
//                    itemDefinition.setId(BPMNUtil.getAutoGeneratedStringId() + "_" + dataOutput.getId());
//
//                    if ("Object".equals((String) row[2])) {
//                        itemDefinition.setStructureRef((String) row[3]);
//                    } else {
//                        itemDefinition.setStructureRef((String) row[2]);
//                    }
//
//                  itemDefinition =  definitions.addItemDefinition(itemDefinition);
//                    dataOutput.setItemSubjectRef(new QName("", itemDefinition.getId(), ""));
//
//                    dataOutputList.add(dataOutput);
//
//                    inputOutputSpecification.getOutputSet().get(0).addDataOutputRefs(dataOutput.getId());
//
//
//                }
//                activitySpec.getIoSpecification().setDataOutput(dataOutputList);
//                this.data = data;
//            }
//        });
//
//        return new CustomNAttributeSupport(attributeEntity);
//    }
    /**
     * ------- TInputOutputSpecification > TDataOutput -------- *
     */
    /**
     * ------- TProperty Start -------- *
     */
//    PropertySupport addTProperty() {
//        final NAttributeEntity attributeEntity = new NAttributeEntity("TProperty", "Properties", "Activity Properties Desc");
//        attributeEntity.setCountDisplay(new String[]{"No Properties set", "One Property set", "Properties set"});
//        final TActivity activitySpec = (TActivity) this.getBaseElementSpec();
//        List<Column> columns = new ArrayList<Column>();
//        columns.add(new Column("Id", true, String.class));
//        columns.add(new Column("Name", true, String.class));
//        columns.add(new Column("DataState", true, String.class));
//        columns.add(new Column("Item Subject Reference", true, String.class));
//        attributeEntity.setColumns(columns);
////        attributeEntity.setCustomDialog(new TPropertyDialog(attributeEntity)); //TODO : MAINTAIN EntityComponent row to entity converter
//        attributeEntity.setTableDataListener(new TableDataListener() {
//            Object[][] data;
//
//            @Override
//            public void init() {
//                List<TProperty> properties = activitySpec.getProperty();
//                Object[][] data_local = new Object[properties.size()][attributeEntity.getColumns().size()];
//                Iterator<TProperty> itr = properties.iterator();
//                int i = 0;
//                while (itr.hasNext()) {
//                    TProperty property = itr.next();
//                    Object[] row = new Object[4];
//                    row[0] = property.getId();
//                    row[1] = property.getName();
//                    row[2] = property.getDataState() != null ? property.getDataState().getName() : null;
//                    row[3] = property.getItemSubjectRef();
//                    data_local[i++] = row;
//                }
//                this.data = data_local;
//            }
//
//            @Override
//            public Object[][] getData() {
//                return data;
//            }
//
//            @Override
//            public void setData(Object[][] data) {
//                List<TProperty> properties = new ArrayList<TProperty>();
////                   Map<String, String> customAttributes = new HashMap<String, String>();
//                for (Object[] row : data) {
//                    TProperty property = new TProperty();
//                    property.setId((String) row[0]);
//                    property.setName((String) row[1]);
//                    TDataState dataState = new TDataState();
//                    dataState.setId(BPMNUtil.getAutoGeneratedStringId());
//                    dataState.setName((String) row[2]);
//                    property.setDataState(dataState);
//                    property.setItemSubjectRef(row[3] == null ? "":(String)row[3]);
//                    properties.add(property);
//
//                }
//                activitySpec.setProperty(properties);
//                this.data = data;
//            }
//        });
//
//        return new CustomNAttributeSupport(attributeEntity);
//    }
    /**
     * ------- TProperty End -------- *
     */
//     /** ------- TInputOutputSpecification > TDataInput -------- **/
//  PropertySupport addDateInputProperty(){
//            final NAttributeEntity attributeEntity = new NAttributeEntity("TDataInput", "Data Input", "Data Input Desc");
//            attributeEntity.setCountDisplay(new String[]{"No DataInputs set", "One DataInput set", "DataInputs set"});
//            final TActivity activitySpec = (TActivity) this.getBaseElementSpec();
//            List<Column> columns = new ArrayList<Column>();
//            columns.add(new Column("Id", true, String.class));
//            columns.add(new Column("Name", true, String.class));
//            columns.add(new Column("Is Collection", true, Boolean.class));
//            columns.add(new Column("item Subject Reference", true, String.class));
//            attributeEntity.setColumns(columns);
//            attributeEntity.setCustomDialog(new DataInputDialog(attributeEntity));
//            attributeEntity.setTableDataListener(new TableDataListener() {
//                Object[][] data;
//
//                @Override
//                public void init() {
//                    if (activitySpec.getIoSpecification() == null) {
//                        activitySpec.setIoSpecification(new TInputOutputSpecification());
//                    }
//                    List<TDataInput> dataInputList = activitySpec.getIoSpecification().getDataInput();
//                    Object[][] data_local = new Object[dataInputList.size()][attributeEntity.getColumns().size()];
//                    Iterator<TDataInput> itr = dataInputList.iterator();
//                    int i = 0;
//                    while (itr.hasNext()) {
//                        TDataInput dataInput = itr.next();
//                        Object[] row = new Object[4];
//                        row[0] = dataInput.getId();
//                        row[1] = dataInput.getName();
//                        row[2] = dataInput.isIsCollection();
//                        row[3] = dataInput.getItemSubjectRef()!=null ? dataInput.getItemSubjectRef().getLocalPart() : null;
//                        data_local[i++] = row;
//                    }
//                    this.data = data_local;
//                }
//
//                @Override
//                public Object[][] getData() {
//                    return data;
//                }
//
//                @Override
//                public void setData(Object[][] data) {
//                    List<TDataInput> dataInputList = new ArrayList<TDataInput>();
////                   Map<String, String> customAttributes = new HashMap<String, String>();
//                    for (Object[] row : data) {
//                        TDataInput dataInput = new TDataInput();
//                        dataInput.setId((String) row[0]);
//                        dataInput.setName((String) row[1]);
//                        dataInput.setIsCollection((Boolean) row[2]);
//                        dataInput.setItemSubjectRef(new QName("", row[3] == null ? "":(String)row[3], ""));
//                        dataInputList.add(dataInput);
//
//                    }
//                    activitySpec.getIoSpecification().setDataInput(dataInputList);
//                    this.data = data;
//                }
//            });
//            return new CustomNAttributeSupport(attributeEntity);
//    }
//   /** ------- TInputOutputSpecification > TDataInput -------- **/
//
//       /** ------- TInputOutputSpecification > TDataOutput -------- **/
//  PropertySupport addDataOutputProperty(){
//            final NAttributeEntity attributeEntity = new NAttributeEntity("TDataOutput", "Data Output", "Data Output Desc");
//            attributeEntity.setCountDisplay(new String[]{"No DataOutputs set", "One DataOutput set", "DataOutputs set"});
//            final TActivity activitySpec = (TActivity) this.getBaseElementSpec();
//            List<Column> columns = new ArrayList<Column>();
//            columns.add(new Column("Id", true, String.class));
//            columns.add(new Column("Name", true, String.class));
//            columns.add(new Column("Is Collection", true, Boolean.class));
//            columns.add(new Column("item Subject Reference", true, String.class));
//            attributeEntity.setColumns(columns);
//            attributeEntity.setCustomDialog(new DataOutputDialog(attributeEntity));
//            attributeEntity.setTableDataListener(new TableDataListener() {
//                Object[][] data;
//
//                @Override
//                public void init() {
//                    if (activitySpec.getIoSpecification() == null) {
//                        activitySpec.setIoSpecification(new TInputOutputSpecification());
//                    }
//                    List<TDataOutput> dataOutputList = activitySpec.getIoSpecification().getDataOutput();
//                    Object[][] data_local = new Object[dataOutputList.size()][attributeEntity.getColumns().size()];
//                    Iterator<TDataOutput> itr = dataOutputList.iterator();
//                    int i = 0;
//                    while (itr.hasNext()) {
//                        TDataOutput dataOutput = itr.next();
//                        Object[] row = new Object[attributeEntity.getColumns().size()];
//                        row[0] = dataOutput.getId();
//                        row[1] = dataOutput.getName();
//                        row[2] = dataOutput.isIsCollection();
//                        row[3] = dataOutput.getItemSubjectRef()!=null ? dataOutput.getItemSubjectRef().getLocalPart() : null;
//                        data_local[i++] = row;
//                    }
//                    this.data = data_local;
//                }
//
//                @Override
//                public Object[][] getData() {
//                    return data;
//                }
//                @Override
//                public void setData(Object[][] data) {
//                    List<TDataOutput> dataOutputList = new ArrayList<TDataOutput>();
////                   Map<String, String> customAttributes = new HashMap<String, String>();
//                    for (Object[] row : data) {
//                        TDataOutput dataOutput = new TDataOutput();
//                        dataOutput.setId((String) row[0]);
//                        dataOutput.setName((String) row[1]);
//                        dataOutput.setIsCollection((Boolean) row[2]);
//                        dataOutput.setItemSubjectRef(new QName("", row[3] == null ? "":(String)row[3], ""));
//                        dataOutputList.add(dataOutput);
//
//                    }
//                    activitySpec.getIoSpecification().setDataOutput(dataOutputList);
//                    this.data = data;
//                }
//            });
//            return new CustomNAttributeSupport(attributeEntity);
//    }
//   /** ------- TInputOutputSpecification > TDataOutput -------- **/
//
    public WidgetAction getBoundaryAcceptProvider() {
        WidgetAction acceptAction = ActionFactory.createAcceptAction(new ActivityWidget.BoundaryAcceptProvider(this));
        return acceptAction;
    }

    /**
     * Test if a widget is fully with in the bounds of the container widget.
     *
     * @param widget The widget to test
     * @return True if the widget is in the containers bounds.
     */
    protected boolean isFullyContained(Widget widget) {
        // Calling getPreferredBounds forces the bounds to be calculated if it
        // has not already been calculated.  For example when the Widget was
        // just created and therefore has not had a chance to be displayed.
        Rectangle area = widget.getClientArea();

        boolean retVal = false;
        if (area != null) {
            Rectangle sceneArea = widget.convertLocalToScene(area);

            Rectangle localArea = convertSceneToLocal(sceneArea);
            Rectangle myArea = getClientArea();
            retVal = myArea.contains(localArea);
        }

        return retVal;
    }

    protected boolean isWidgetMove(Transferable transferable) {
        return transferable.isDataFlavorSupported(MoveWidgetTransferable.FLAVOR);
    }

    protected boolean isPaletteItem(Transferable transferable) {
        return transferable.isDataFlavorSupported(DataFlavor.imageFlavor);
    }

    /**
     * @return the boundaryEventWidget
     */
    public List<BoundaryEventWidget> getBoundaryEvents() {
        return boundaryEventWidgets;
    }

    /**
     * @param boundaryEventWidget the boundaryEventWidget to set
     */
    public void setBoundaryEventWidgets(List<BoundaryEventWidget> boundaryEventWidget) {
        this.boundaryEventWidgets = boundaryEventWidget;
    }

    public void addBoundaryEventWidget(BoundaryEventWidget boundaryEventWidget) {
        this.boundaryEventWidgets.add(boundaryEventWidget);
    }

    public void removeBoundaryEventWidget(BoundaryEventWidget boundaryEventWidget) {
        this.boundaryEventWidgets.remove(boundaryEventWidget);
    }

    public class BoundaryAcceptProvider implements /*Scene*/ AcceptProvider {

        private ActivityWidget activityWidget = null;

        public BoundaryAcceptProvider(ActivityWidget containerW) {
            activityWidget = containerW;
        }

        @Override
        public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
            ConnectorState retVal = ConnectorState.ACCEPT;

            if (isWidgetMove(transferable)) {
                Widget[] target = new Widget[]{getWidget(transferable)};
                for (Widget curWidget : target) {
                    if (widget == curWidget) {
                        retVal = ConnectorState.REJECT;
                        return retVal;
                    }
                    if (isFullyContained(curWidget) == false) {
                        retVal = ConnectorState.REJECT;
                        return retVal;
                    }
                    if (curWidget instanceof NodeWidget) {
                        NodeWidget nodeWidget = (NodeWidget) curWidget;
                        if (nodeWidget.getNodeWidgetInfo().getModelerDocument().getFlowDimension() != FlowDimensionType.BOUNDARY) {
                            retVal = ConnectorState.REJECT;
                            return retVal;
                        }
                    }

                }
            } else if (isPaletteItem(transferable)) {
                SubCategoryNodeConfig subCategoryInfo = getSubCategory(transferable);
                Image dragImage = subCategoryInfo.getImage();
                JComponent view = activityWidget.getScene().getView();
                Graphics2D g2 = (Graphics2D) view.getGraphics();
                Rectangle visRect = view.getVisibleRect();
                view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);

                point = activityWidget.convertLocalToScene(point);
                g2.drawImage(dragImage,
                        AffineTransform.getTranslateInstance(point.getLocation().getX(),
                                point.getLocation().getY()),
                        null);

                if (subCategoryInfo.getModelerDocument().getFlowDimension() != FlowDimensionType.BOUNDARY) {
                    retVal = ConnectorState.REJECT;
                    return retVal;
                }
            }
            return retVal;
        }

        @Override
        public void accept(Widget widget, Point point, Transferable transferable) {
            AbstractModelerScene scene = (AbstractModelerScene) widget.getScene();
            BoundaryEventWidget boundaryWidget = null;
            if (isWidgetMove(transferable)) {
                Widget[] target;
                try {
                    target = new Widget[]{getWidget(transferable)};
                } catch (Exception e) {
                    target = new Widget[0];
                }

                for (Widget curWidget : target) {
                    if (widget == curWidget) {
                        break;
                    }
                    if (!isFullyContained(curWidget)) {
                        break;
                    }
                    if (curWidget instanceof BoundaryEventWidget) {
                        boundaryWidget = (BoundaryEventWidget) curWidget;
                        if (boundaryWidget.isAutoMoveActionLocked()) {
                            break;
                        }
                        boundaryWidget.getActivityWidget().removeBoundaryEventWidget(boundaryWidget);
                    } else {
                        break;
                    }
                }

            } else if (isPaletteItem(transferable)) {
                SubCategoryNodeConfig info = getSubCategory(transferable);
                boundaryWidget = (BoundaryEventWidget) scene.createNodeWidget(new NodeWidgetInfo(NBModelerUtil.getAutoGeneratedStringId(), info, point));
                boundaryWidget.setPreferredLocation(widget.convertLocalToScene(point));
                boundaryWidget.getParentWidget().removeChild(boundaryWidget);
                ((AbstractModelerScene) boundaryWidget.getScene()).getBoundaryWidgetLayer().addChild(boundaryWidget);

            }
            if (boundaryWidget != null && !boundaryWidget.isAutoMoveActionLocked()) {
                boundaryWidget.getScene().getSceneAnimator().animatePreferredLocation(boundaryWidget, boundaryWidget.getBoundaryWidgetLocation(activityWidget));
                boundaryWidget.setActivityWidget(activityWidget);
                activityWidget.addBoundaryEventWidget(boundaryWidget);
                ((TBoundaryEvent) boundaryWidget.getBaseElementSpec()).setAttachedToRef(activityWidget.getBaseElementSpec().getId());
            }
        }
    }

    protected SubCategoryNodeConfig getSubCategory(Transferable transferable) {
        Object o = null;
        try {
            //o =  transferable.getTransferData(PaletteItemTransferable.FLAVOR);
            o = transferable.getTransferData(DataFlavor.imageFlavor);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (UnsupportedFlavorException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (o instanceof Node) {
            SubCategoryNodeConfig subCategoryInfo = (SubCategoryNodeConfig) ((Node) o).getValue("SubCategoryInfo");
            return subCategoryInfo;
        } else {
            return null;
        }
    }

    protected Widget getWidget(Transferable transferable) {
        try {
            MoveWidgetTransferable data = (MoveWidgetTransferable) transferable.getTransferData(MoveWidgetTransferable.FLAVOR);
            return data.getWidget();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (UnsupportedFlavorException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }
}
