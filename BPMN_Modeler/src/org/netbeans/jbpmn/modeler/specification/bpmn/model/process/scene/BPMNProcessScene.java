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
package org.netbeans.jbpmn.modeler.specification.bpmn.model.process.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.netbeans.jbpmn.core.widget.ActivityWidget;
import org.netbeans.jbpmn.core.widget.AdHocSubProcessWidget;
import org.netbeans.jbpmn.core.widget.ArtifactWidget;
import org.netbeans.jbpmn.core.widget.AssociationWidget;
import org.netbeans.jbpmn.core.widget.BaseElementWidget;
import org.netbeans.jbpmn.core.widget.BoundaryEventWidget;
import org.netbeans.jbpmn.core.widget.BusinessRuleTaskWidget;
import org.netbeans.jbpmn.core.widget.CallActivityWidget;
import org.netbeans.jbpmn.core.widget.ComplexGatewayWidget;
import org.netbeans.jbpmn.core.widget.DefaultSubProcessWidget;
import org.netbeans.jbpmn.core.widget.EndEventWidget;
import org.netbeans.jbpmn.core.widget.EventBasedGatewayWidget;
import org.netbeans.jbpmn.core.widget.EventSubProcessWidget;
import org.netbeans.jbpmn.core.widget.EventWidget;
import org.netbeans.jbpmn.core.widget.ExclusiveGatewayWidget;
import org.netbeans.jbpmn.core.widget.FlowElementWidget;
import org.netbeans.jbpmn.core.widget.FlowNodeWidget;
import org.netbeans.jbpmn.core.widget.GatewayWidget;
import org.netbeans.jbpmn.core.widget.GroupWidget;
import org.netbeans.jbpmn.core.widget.InclusiveGatewayWidget;
import org.netbeans.jbpmn.core.widget.IntermediateCatchEventWidget;
import org.netbeans.jbpmn.core.widget.IntermediateThrowEventWidget;
import org.netbeans.jbpmn.core.widget.ManualTaskWidget;
import org.netbeans.jbpmn.core.widget.ParallelGatewayWidget;
import org.netbeans.jbpmn.core.widget.ReceiveTaskWidget;
import org.netbeans.jbpmn.core.widget.ScriptTaskWidget;
import org.netbeans.jbpmn.core.widget.SendTaskWidget;
import org.netbeans.jbpmn.core.widget.SequenceFlowWidget;
import org.netbeans.jbpmn.core.widget.ServiceTaskWidget;
import org.netbeans.jbpmn.core.widget.StartEventWidget;
import org.netbeans.jbpmn.core.widget.SubProcessWidget;
import org.netbeans.jbpmn.core.widget.TaskWidget;
import org.netbeans.jbpmn.core.widget.TextAnnotationWidget;
import org.netbeans.jbpmn.core.widget.TransactionSubProcessWidget;
import org.netbeans.jbpmn.core.widget.UserTaskWidget;
import org.netbeans.jbpmn.core.widget.swimlanes.LaneSetWidget;
import org.netbeans.jbpmn.core.widget.swimlanes.LaneWidget;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.util.BPMNProcessUtil;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn._interface.InterfacePanel;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.error.ErrorPanel;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.escalation.EscalationPanel;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.message.MessagePanel;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.signal.SignalPanel;
import org.netbeans.modeler.properties.nentity.Column;
import org.netbeans.modeler.properties.nentity.NEntityPropertySupport;
import org.netbeans.modeler.properties.nentity.NAttributeEntity;
import org.netbeans.modeler.properties.nentity.NEntityDataListener;
import org.netbeans.jbpmn.modeler.widget.properties.itemdefinition.ItemDefinitionPanel;
import org.netbeans.jbpmn.modeler.widget.properties.operation.OperationPanel;
import org.netbeans.jbpmn.modeler.widget.properties.resource.ResourcePanel;
import org.netbeans.jbpmn.modeler.widget.properties.user_interface.property.ProcessVariableDefinitionPanel;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ActionHandler;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ComboBoxListener;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.support.ComboBoxPropertySupport;
import org.netbeans.jbpmn.spec.BPMNDiagram;
import org.netbeans.jbpmn.spec.TActivity;
import org.netbeans.jbpmn.spec.TAdHocSubProcess;
import org.netbeans.jbpmn.spec.TArtifact;
import org.netbeans.jbpmn.spec.TAssociation;
import org.netbeans.jbpmn.spec.TBoundaryEvent;
import org.netbeans.jbpmn.spec.TBusinessRuleTask;
import org.netbeans.jbpmn.spec.TCallActivity;
import org.netbeans.jbpmn.spec.TCancelEventDefinition;
import org.netbeans.jbpmn.spec.TCompensateEventDefinition;
import org.netbeans.jbpmn.spec.TComplexGateway;
import org.netbeans.jbpmn.spec.TConditionalEventDefinition;
import org.netbeans.jbpmn.spec.TDataInputAssociation;
import org.netbeans.jbpmn.spec.TDataOutputAssociation;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TEndEvent;
import org.netbeans.jbpmn.spec.TError;
import org.netbeans.jbpmn.spec.TErrorEventDefinition;
import org.netbeans.jbpmn.spec.TEscalation;
import org.netbeans.jbpmn.spec.TEscalationEventDefinition;
import org.netbeans.jbpmn.spec.TEvent;
import org.netbeans.jbpmn.spec.TEventBasedGateway;
import org.netbeans.jbpmn.spec.TEventDefinition;
import org.netbeans.jbpmn.spec.TExclusiveGateway;
import org.netbeans.jbpmn.spec.TFlowElement;
import org.netbeans.jbpmn.spec.TFlowNode;
import org.netbeans.jbpmn.spec.TGroup;
import org.netbeans.jbpmn.spec.TInclusiveGateway;
import org.netbeans.jbpmn.spec.TInterface;
import org.netbeans.jbpmn.spec.TIntermediateCatchEvent;
import org.netbeans.jbpmn.spec.TIntermediateThrowEvent;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TLane;
import org.netbeans.jbpmn.spec.TLaneSet;
import org.netbeans.jbpmn.spec.TLinkEventDefinition;
import org.netbeans.jbpmn.spec.TManualTask;
import org.netbeans.jbpmn.spec.TMessage;
import org.netbeans.jbpmn.spec.TMessageEventDefinition;
import org.netbeans.jbpmn.spec.TOperation;
import org.netbeans.jbpmn.spec.TParallelGateway;
import org.netbeans.jbpmn.spec.TProcess;
import org.netbeans.jbpmn.spec.TProcessType;
import org.netbeans.jbpmn.spec.TProperty;
import org.netbeans.jbpmn.spec.TReceiveTask;
import org.netbeans.jbpmn.spec.TResource;
import org.netbeans.jbpmn.spec.TRootElement;
import org.netbeans.jbpmn.spec.TScriptTask;
import org.netbeans.jbpmn.spec.TSendTask;
import org.netbeans.jbpmn.spec.TSequenceFlow;
import org.netbeans.jbpmn.spec.TServiceTask;
import org.netbeans.jbpmn.spec.TSignal;
import org.netbeans.jbpmn.spec.TSignalEventDefinition;
import org.netbeans.jbpmn.spec.TStartEvent;
import org.netbeans.jbpmn.spec.TSubProcess;
import org.netbeans.jbpmn.spec.TTask;
import org.netbeans.jbpmn.spec.TTerminateEventDefinition;
import org.netbeans.jbpmn.spec.TTextAnnotation;
import org.netbeans.jbpmn.spec.TTimerEventDefinition;
import org.netbeans.jbpmn.spec.TTransaction;
import org.netbeans.jbpmn.spec.TUserTask;
import org.netbeans.modeler.config.element.ElementConfigFactory;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.core.exception.InvalidElmentException;
import org.netbeans.modeler.core.scene.ModelerScene;
import org.netbeans.modeler.specification.model.document.IDefinitionElement;
import org.netbeans.modeler.specification.model.document.IDiagramElement;
import org.netbeans.modeler.specification.model.document.IRootElement;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.specification.model.document.widget.IArtifactNodeWidget;
import org.netbeans.modeler.specification.model.document.widget.IBaseElementWidget;
import org.netbeans.modeler.specification.model.document.widget.IFlowNodeWidget;
import org.netbeans.modeler.widget.edge.EdgeWidget;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;
import org.openide.nodes.PropertySupport;

public class BPMNProcessScene extends ModelerScene {

    private List<IFlowNodeWidget> debugNodeWidget = new ArrayList<IFlowNodeWidget>();

    public BPMNProcessScene() {
        this.addPropertyChangeListener("name", new PropertyChangeListener<String>() {
            @Override
            public void changePerformed(String value) {
                setName(value);
            }
        });

        this.addPropertyChangeListener("id", new PropertyChangeListener<String>() {
            @Override
            public void changePerformed(String value) {
                IDiagramElement diagramElement = BPMNProcessScene.this.getModelerFile().getDiagramElement();
                ((BPMNDiagram) diagramElement).getBPMNPlane().setBpmnElement(value);
            }
        });
    }
    private List<FlowElementWidget> flowElements = new ArrayList<FlowElementWidget>(); // Linked hashmap to preserve order of inserted elements
    private List<ArtifactWidget> artifacts = new ArrayList< ArtifactWidget>(); // Linked hashmap to preserve order of inserted elements

    @Override
    public void createPropertySet(ElementPropertySet set) {

        IRootElement rootElement = this.getModelerFile().getRootElement();
        ElementConfigFactory elementConfigFactory = this.getModelerFile().getVendorSpecification().getElementConfigFactory();
        elementConfigFactory.createPropertySet(set, rootElement, getPropertyChangeListeners());
        IDefinitionElement definitionElement = this.getModelerFile().getDefinitionElement();
        elementConfigFactory.createPropertySet(set, definitionElement, null);
        set.put("BASIC_PROP", getProcessTypeProperty());
        set.put("BASIC_PROP", BPMNProcessUtil.addTResourceRole(this, this.getBaseElementSpec()));

        set.put("DATA_ITEMS", addProperty());

        set.put("DATA_DEFINITIONS", getErrorListProperty());
        set.put("DATA_DEFINITIONS", getEscalationListProperty());
        set.put("DATA_DEFINITIONS", getMessageListProperty());
        set.put("DATA_DEFINITIONS", getSignalListProperty());
        set.put("DATA_DEFINITIONS", getInterfaceListProperty());
//        set.put("DATA_DEFINITIONS", getOperationListProperty());
        set.put("DATA_DEFINITIONS", getResourceListProperty());
        set.put("DATA_DEFINITIONS", getItemDefinitionListProperty());

    }

    private PropertySupport getItemDefinitionListProperty() {
        final ModelerFile modelerFile = this.getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();
        final NAttributeEntity attributeEntity = new NAttributeEntity("TItemDefinition", "ItemDefinition List", "ItemDefinition List Desc");
        attributeEntity.setCountDisplay(new String[]{"No ItemDefinitions exist", "One ItemDefinition exist", "ItemDefinitions exist"});

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Data Type", false, String.class));
        columns.add(new Column("Is Collection", false, Boolean.class));
        columns.add(new Column("Item Kind", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new ItemDefinitionPanel(modelerFile));

        attributeEntity.setTableDataListener(new NEntityDataListener() {
            List<Object[]> data;
            int count;

            @Override
            public void initCount() {
                count = 0;
                for (TRootElement rootElement : definition.getRootElement()) {
                    if (!(rootElement instanceof TItemDefinition)) {
                        continue;
                    }
                    count++;
                }
            }

            @Override
            public int getCount() {
                return count;
            }

            @Override
            public void initData() {
                List<TRootElement> rootElements = definition.getRootElement();
                List<Object[]> data_local = new LinkedList<Object[]>();
                Iterator<TRootElement> itr = rootElements.iterator();
                while (itr.hasNext()) {
                    TRootElement rootElement = itr.next();
                    if (!(rootElement instanceof TItemDefinition)) {
                        continue;
                    }
                    TItemDefinition itemDefinition = (TItemDefinition) rootElement;
                    Object[] row = new Object[attributeEntity.getColumns().size()];
                    row[0] = itemDefinition;
                    row[1] = itemDefinition.getStructureRef();
                    row[2] = itemDefinition.isIsCollection();
                    row[3] = itemDefinition.getItemKind();
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
                List<TItemDefinition> itemDefinitionList = new ArrayList<TItemDefinition>();
                for (Object[] row : (List<Object[]>) data) { //prepare list
                    TItemDefinition itemDefinition = (TItemDefinition) row[0];
                    itemDefinitionList.add(itemDefinition);
                }

                List<TRootElement> rootElements = new CopyOnWriteArrayList<TRootElement>(definition.getRootElement());
                Iterator<TRootElement> itr = rootElements.iterator();
                while (itr.hasNext()) {  //remove all
                    TRootElement rootElement = itr.next();
                    if (rootElement instanceof TItemDefinition) {
                        definition.getRootElement().remove(rootElement);
                    }
                }

                for (TItemDefinition itemDefinition : itemDefinitionList) { //add TItemDefinition list
                    definition.getRootElement().add(itemDefinition);
                }

                this.data = data;
            }

        });

        return new NEntityPropertySupport(this.getModelerFile(), attributeEntity);
    }

    private PropertySupport getResourceListProperty() {
        final ModelerFile modelerFile = this.getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();
        final NAttributeEntity attributeEntity = new NAttributeEntity("TResource", "Resource List", "Resource List Desc");
        attributeEntity.setCountDisplay(new String[]{"No Resources exist", "One Resource exist", "Resources exist"});

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Name", false, String.class));
        columns.add(new Column("Parameters", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new ResourcePanel(modelerFile));

        attributeEntity.setTableDataListener(new NEntityDataListener/*<TProperty>*/() {
                    List<Object[]> data;
                    int count;

                    @Override
                    public void initCount() {
                        count = 0;
                        for (TRootElement rootElement : definition.getRootElement()) {
                            if (!(rootElement instanceof TResource)) {
                                continue;
                            }
                            count++;
                        }
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public void initData() {
                        List<TRootElement> rootElements = definition.getRootElement();
                        List<Object[]> data_local = new LinkedList<Object[]>();
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {
                            TRootElement rootElement = itr.next();
                            if (!(rootElement instanceof TResource)) {
                                continue;
                            }
                            TResource resource = (TResource) rootElement;
                            Object[] row = new Object[attributeEntity.getColumns().size()];

                            row[0] = resource;
                            row[1] = resource.getName();
                            String paramCountText = "";
                            int paramCount = resource.getResourceParameter().size();
                            if (paramCount == 0) {
                                paramCountText = "No Parameters exist";
                            } else if (paramCount == 1) {
                                paramCountText = "One Parameter exist";
                            } else if (paramCount > 1) {
                                paramCountText = paramCount + " Parameters exist";
                            }

                            row[2] = paramCountText;
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
                        List<TResource> resourceList = new ArrayList<TResource>();
                        for (Object[] row : (List<Object[]>) data) { //prepare list
                            TResource resource = (TResource) row[0];
                            resourceList.add(resource);
                        }

                        List<TRootElement> rootElements = new CopyOnWriteArrayList<TRootElement>(definition.getRootElement());
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {  //remove all
                            TRootElement rootElement = itr.next();
                            if (rootElement instanceof TResource) {
                                definition.getRootElement().remove(rootElement);
                            }
                        }

                        for (TResource resource : resourceList) { //add TResource list
                            definition.getRootElement().add(resource);
                        }

                        this.data = data;
                    }

                });

        return new NEntityPropertySupport(this.getModelerFile(), attributeEntity);
    }

    private PropertySupport getOperationListProperty() {
        final ModelerFile modelerFile = this.getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();
        final NAttributeEntity attributeEntity = new NAttributeEntity("TOperation", "Operation List", "Operation List Desc");
        attributeEntity.setCountDisplay(new String[]{"No Operations exist", "One Operation exist", "Operations exist"});

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Name", false, String.class));
        columns.add(new Column("Implementation", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new OperationPanel(modelerFile));

        attributeEntity.setTableDataListener(new NEntityDataListener/*<TProperty>*/() {
                    List<Object[]> data;
                    int count;

                    @Override
                    public void initCount() {
                        count = 0;
                        for (TRootElement rootElement : definition.getRootElement()) {
                            if (!(rootElement instanceof TInterface)) {
                                continue;
                            }
                            TInterface _interface = (TInterface) rootElement;
                            count = count + _interface.getOperation().size();
                        }
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public void initData() {
                        List<TRootElement> rootElements = definition.getRootElement();
                        List<Object[]> data_local = new LinkedList<Object[]>();
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {
                            TRootElement rootElement = itr.next();
                            if (!(rootElement instanceof TInterface)) {
                                continue;
                            }
                            TInterface _interface = (TInterface) rootElement;
                            for (TOperation operation : _interface.getOperation()) {
                                Object[] row = new Object[attributeEntity.getColumns().size()];
                                row[0] = operation;
                                row[1] = operation.getName();
                                // TItemDefinition itemDefinition = (TItemDefinition) definition.getRootElement(_interface.getImplementationRef(), TItemDefinition.class);
                                row[2] = operation.getImplementationRef();//itemDefinition.getDisplayValue();
                                data_local.add(row);
                            }
                        }
                        this.data = data_local;
                    }

                    @Override
                    public List<Object[]> getData() {
                        return data;
                    }

                    @Override
                    public void setData(List<Object[]> data) {
                        List<TInterface> interfaceList = new ArrayList<TInterface>();
                        for (Object[] row : data) { //prepare list
                            TInterface _interface = (TInterface) row[0];
                            interfaceList.add(_interface);
                        }

                        List<TRootElement> rootElements = new CopyOnWriteArrayList<TRootElement>(definition.getRootElement());
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {  //remove all
                            TRootElement rootElement = itr.next();
                            if (rootElement instanceof TInterface) {
                                definition.getRootElement().remove(rootElement);
                            }
                        }

                        for (TInterface _interface : interfaceList) { //add TInterface list
                            definition.getRootElement().add(_interface);
                        }

                        this.data = data;
                    }

                });

        return new NEntityPropertySupport(this.getModelerFile(), attributeEntity);
    }

    private PropertySupport getInterfaceListProperty() {
        final ModelerFile modelerFile = this.getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();
        final NAttributeEntity attributeEntity = new NAttributeEntity("TInterface", "Interface List", "Interface List Desc");
        attributeEntity.setCountDisplay(new String[]{"No Interfaces exist", "One Interface exist", "Interfaces exist"});

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Name", false, String.class));
        columns.add(new Column("Implementation", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new InterfacePanel(modelerFile));

        attributeEntity.setTableDataListener(new NEntityDataListener/*<TProperty>*/() {
                    List<Object[]> data;
                    int count;

                    @Override
                    public void initCount() {
                        count = 0;
                        for (TRootElement rootElement : definition.getRootElement()) {
                            if (!(rootElement instanceof TInterface)) {
                                continue;
                            }
                            count++;
                        }
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public void initData() {
                        List<TRootElement> rootElements = definition.getRootElement();
                        List<Object[]> data_local = new LinkedList<Object[]>();
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {
                            TRootElement rootElement = itr.next();
                            if (!(rootElement instanceof TInterface)) {
                                continue;
                            }
                            TInterface _interface = (TInterface) rootElement;
                            Object[] row = new Object[attributeEntity.getColumns().size()];

                            row[0] = _interface;
                            row[1] = _interface.getName();
                            // TItemDefinition itemDefinition = (TItemDefinition) definition.getRootElement(_interface.getImplementationRef(), TItemDefinition.class);
                            row[2] = _interface.getImplementationRef();//itemDefinition.getDisplayValue();
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
                        List<TInterface> interfaceList = new ArrayList<TInterface>();
                        for (Object[] row : data) { //prepare list
                            TInterface _interface = (TInterface) row[0];
                            interfaceList.add(_interface);
                        }

                        List<TRootElement> rootElements = new CopyOnWriteArrayList<TRootElement>(definition.getRootElement());
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {  //remove all
                            TRootElement rootElement = itr.next();
                            if (rootElement instanceof TInterface) {
                                definition.getRootElement().remove(rootElement);
                            }
                        }

                        for (TInterface _interface : interfaceList) { //add TInterface list
                            definition.getRootElement().add(_interface);
                        }

                        this.data = data;
                    }

                });

        return new NEntityPropertySupport(this.getModelerFile(), attributeEntity);
    }

    /**
     * ------- SGUI Property Start -------- *
     */
    PropertySupport addProperty() {

        final ModelerFile modelerFile = this.getModelerFile();

        final NAttributeEntity attributeEntity = new NAttributeEntity("TProperty", "Variable Definitions", "Variable Definitions Desc");
        attributeEntity.setCountDisplay(new String[]{"No Variables set", "One Variable set", "Variables set"});
        final TProcess processSpec = (TProcess) this.getBaseElementSpec();
        final TDefinitions definitions = (TDefinitions) this.getModelerFile().getDefinitionElement();
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
//             @Override
//             public List<TProperty> getEntities() {
//                 return processSpec.getProperty();
//             }
//
//             @Override
//             public void setEntities(List<TProperty> entities) {
//                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//             }
//
//             @Override
//             public Object[] getRow(TProperty entity) {
//                    Object[] row = new Object[2];
//                    row[0] = entity.getName();
//                    String standardType = definitions.getItemDefinitionStructure(entity.getItemSubjectRef());
//                    row[1] = standardType;
//                    return row;
//             }
//
//             @Override
//             public TProperty getEntity(Object[] row) {
//
//             }
                    List<Object[]> data;
//               List<TItemDefinition> itemDefinitions_Selected = new ArrayList<TItemDefinition>();
                    int count;

                    @Override
                    public void initCount() {
                        count = processSpec.getProperty().size();
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public void initData() {
                        List<TProperty> properties = processSpec.getProperty();
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

                            if (preId != null && !preId.equals(newId)) {  //refract property id from all object where property id is used
                                for (TFlowElement flowElement : processSpec.getFlowElement()) {
                                    if (flowElement instanceof TActivity) {
                                        TActivity activity = (TActivity) flowElement;
                                        for (TDataInputAssociation dataInputAssociation : activity.getDataInputAssociation()) {
                                            if (preId.equals(dataInputAssociation.getSourceRef())) {
                                                dataInputAssociation.setSourceRef(newId);
                                            }
                                        }
                                        for (TDataOutputAssociation dataOutputAssociation : activity.getDataOutputAssociation()) {
                                            if (preId.equals(dataOutputAssociation.getTargetRef())) {
                                                dataOutputAssociation.setTargetRef(newId);
                                            }
                                        }
                                    }
                                }
                            }

                            properties.add(property);
                        }
                        processSpec.setProperty(properties);
                        this.data = data;
                    }

                });
        return new NEntityPropertySupport(this.getModelerFile(), attributeEntity);
    }

    private PropertySupport getErrorListProperty() {
        final ModelerFile modelerFile = this.getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();
        final NAttributeEntity attributeEntity = new NAttributeEntity("TError", "Error List", "Error List Desc");
        attributeEntity.setCountDisplay(new String[]{"No Errors exist", "One Error exist", "Errors exist"});

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Name", false, String.class));
        columns.add(new Column("Code", false, String.class));
        columns.add(new Column("Data Type", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new ErrorPanel(modelerFile));

        attributeEntity.setTableDataListener(new NEntityDataListener/*<TProperty>*/() {
                    List<Object[]> data;
                    int count;

                    @Override
                    public void initCount() {
                        count = 0;
                        for (TRootElement rootElement : definition.getRootElement()) {
                            if (!(rootElement instanceof TError)) {
                                continue;
                            }
                            count++;
                        }
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public void initData() {
                        List<TRootElement> rootElements = definition.getRootElement();
                        List<Object[]> data_local = new LinkedList<Object[]>();
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {
                            TRootElement rootElement = itr.next();
                            if (!(rootElement instanceof TError)) {
                                continue;
                            }
                            TError error = (TError) rootElement;
                            Object[] row = new Object[attributeEntity.getColumns().size()];

                            row[0] = error;
                            row[1] = error.getName();
                            TItemDefinition itemDefinition = (TItemDefinition) definition.getRootElement(error.getStructureRef(), TItemDefinition.class);
                            row[2] = error.getErrorCode();
                            row[3] = itemDefinition == null ? null : itemDefinition.getDisplayValue();
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
                        List<TError> errorList = new ArrayList<TError>();
                        for (Object[] row : data) { //prepare list
                            TError error = (TError) row[0];
                            errorList.add(error);
                        }

                        List<TRootElement> rootElements = new CopyOnWriteArrayList<TRootElement>(definition.getRootElement());
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {  //remove all
                            TRootElement rootElement = itr.next();
                            if (rootElement instanceof TError) {
                                definition.getRootElement().remove(rootElement);
                            }
                        }

                        for (TError error : errorList) { //add error list
                            definition.getRootElement().add(error);
                        }

                        this.data = data;
                    }

                });

        return new NEntityPropertySupport(this.getModelerFile(), attributeEntity);
    }

    private PropertySupport getEscalationListProperty() {
        final ModelerFile modelerFile = this.getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();
        final NAttributeEntity attributeEntity = new NAttributeEntity("TEscalation", "Escalation List", "Escalation List Desc");
        attributeEntity.setCountDisplay(new String[]{"No Escalations exist", "One Escalation exist", "Escalations exist"});

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Name", false, String.class));
        columns.add(new Column("Code", false, String.class));
        columns.add(new Column("Data Type", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new EscalationPanel(modelerFile));

        attributeEntity.setTableDataListener(new NEntityDataListener/*<TProperty>*/() {
                    List<Object[]> data;
                    int count;

                    @Override
                    public void initCount() {
                        count = 0;
                        for (TRootElement rootElement : definition.getRootElement()) {
                            if (!(rootElement instanceof TEscalation)) {
                                continue;
                            }
                            count++;
                        }
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public void initData() {
                        List<TRootElement> rootElements = definition.getRootElement();
                        List<Object[]> data_local = new LinkedList<Object[]>();
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {
                            TRootElement rootElement = itr.next();
                            if (!(rootElement instanceof TEscalation)) {
                                continue;
                            }
                            TEscalation escalation = (TEscalation) rootElement;
                            Object[] row = new Object[attributeEntity.getColumns().size()];

                            row[0] = escalation;
                            row[1] = escalation.getName();
                            TItemDefinition itemDefinition = (TItemDefinition) definition.getRootElement(escalation.getStructureRef(), TItemDefinition.class);
                            row[2] = escalation.getEscalationCode();
                            row[3] = itemDefinition == null ? null : itemDefinition.getDisplayValue();
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
                        List<TEscalation> escalationList = new ArrayList<TEscalation>();
                        for (Object[] row : data) { //prepare list
                            TEscalation escalation = (TEscalation) row[0];
                            escalationList.add(escalation);
                        }

                        List<TRootElement> rootElements = new CopyOnWriteArrayList<TRootElement>(definition.getRootElement());
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {  //remove all
                            TRootElement rootElement = itr.next();
                            if (rootElement instanceof TEscalation) {
                                definition.getRootElement().remove(rootElement);
                            }
                        }

                        for (TEscalation escalation : escalationList) { //add TEscalation list
                            definition.getRootElement().add(escalation);
                        }

                        this.data = data;
                    }

                });

        return new NEntityPropertySupport(this.getModelerFile(), attributeEntity);
    }

    private PropertySupport getMessageListProperty() {
        final ModelerFile modelerFile = this.getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();
        final NAttributeEntity attributeEntity = new NAttributeEntity("TMessage", "Message List", "Message List Desc");
        attributeEntity.setCountDisplay(new String[]{"No Messages exist", "One Message exist", "Messages exist"});

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Name", false, String.class));
        columns.add(new Column("Data Type", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new MessagePanel(modelerFile));

        attributeEntity.setTableDataListener(new NEntityDataListener/*<TProperty>*/() {
                    List<Object[]> data;
                    int count;

                    @Override
                    public void initCount() {
                        count = 0;
                        for (TRootElement rootElement : definition.getRootElement()) {
                            if (!(rootElement instanceof TMessage)) {
                                continue;
                            }
                            count++;
                        }
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public void initData() {
                        List<TRootElement> rootElements = definition.getRootElement();
                        List<Object[]> data_local = new LinkedList<Object[]>();
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {
                            TRootElement rootElement = itr.next();
                            if (!(rootElement instanceof TMessage)) {
                                continue;
                            }
                            TMessage message = (TMessage) rootElement;
                            Object[] row = new Object[attributeEntity.getColumns().size()];

                            row[0] = message;
                            row[1] = message.getName();
                            TItemDefinition itemDefinition = (TItemDefinition) definition.getRootElement(message.getItemRef(), TItemDefinition.class);
                            row[2] = itemDefinition == null ? null : itemDefinition.getDisplayValue();
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
                        List<TMessage> messageList = new ArrayList<TMessage>();
                        for (Object[] row : data) { //prepare list
                            TMessage message = (TMessage) row[0];
                            messageList.add(message);
                        }

                        List<TRootElement> rootElements = new CopyOnWriteArrayList<TRootElement>(definition.getRootElement());
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {  //remove all
                            TRootElement rootElement = itr.next();
                            if (rootElement instanceof TMessage) {
                                definition.getRootElement().remove(rootElement);
                            }
                        }

                        for (TMessage message : messageList) { //add TMessage list
                            definition.getRootElement().add(message);
                        }

                        this.data = data;
                    }

                });

        return new NEntityPropertySupport(this.getModelerFile(), attributeEntity);
    }

    private PropertySupport getSignalListProperty() {
        final ModelerFile modelerFile = this.getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();
        final NAttributeEntity attributeEntity = new NAttributeEntity("TSignal", "Signal List", "Signal List Desc");
        attributeEntity.setCountDisplay(new String[]{"No Signals exist", "One Signal exist", "Signals exist"});

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Name", false, String.class));
        columns.add(new Column("Data Type", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new SignalPanel(modelerFile));

        attributeEntity.setTableDataListener(new NEntityDataListener/*<TProperty>*/() {
                    List<Object[]> data;
                    int count;

                    @Override
                    public void initCount() {
                        count = 0;
                        for (TRootElement rootElement : definition.getRootElement()) {
                            if (!(rootElement instanceof TSignal)) {
                                continue;
                            }
                            count++;
                        }
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public void initData() {
                        List<TRootElement> rootElements = definition.getRootElement();
                        List<Object[]> data_local = new LinkedList<Object[]>();
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {
                            TRootElement rootElement = itr.next();
                            if (!(rootElement instanceof TSignal)) {
                                continue;
                            }
                            TSignal signal = (TSignal) rootElement;
                            Object[] row = new Object[attributeEntity.getColumns().size()];

                            row[0] = signal;
                            row[1] = signal.getName();
                            TItemDefinition itemDefinition = (TItemDefinition) definition.getRootElement(signal.getStructureRef(), TItemDefinition.class);
                            row[2] = itemDefinition == null ? null : itemDefinition.getDisplayValue();
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
                        List<TSignal> signalList = new ArrayList<TSignal>();
                        for (Object[] row : (List<Object[]>) data) { //prepare list
                            TSignal signal = (TSignal) row[0];
                            signalList.add(signal);
                        }

                        List<TRootElement> rootElements = new CopyOnWriteArrayList<TRootElement>(definition.getRootElement());
                        Iterator<TRootElement> itr = rootElements.iterator();
                        while (itr.hasNext()) {  //remove all
                            TRootElement rootElement = itr.next();
                            if (rootElement instanceof TSignal) {
                                definition.getRootElement().remove(rootElement);
                            }
                        }

                        for (TSignal signal : signalList) { //add TSignal list
                            definition.getRootElement().add(signal);
                        }

                        this.data = data;
                    }

                });

        return new NEntityPropertySupport(this.getModelerFile(), attributeEntity);
    }

//    PropertySupport addProperty() {
//        final NAttributeEntity attributeEntity = new NAttributeEntity("TProperty", "Variable Definitions", "Variable Definitions Desc");
//        attributeEntity.setCountDisplay(new String[]{"No Variables set", "One Variable set", "Variables set"});
//        final TProcess processSpec = (TProcess) this.getBaseElementSpec();
//        final TDefinitions definitions = (TDefinitions) this.getModelerFile().getDefinitionElement();
//        List<String> standardTypeList = new LinkedList<String>();
//        standardTypeList.add("String");
//        standardTypeList.add("Integer");
//        standardTypeList.add("Boolean");
//        standardTypeList.add("Float");
//        standardTypeList.add("Object");
//
//        List<Column> columns = new ArrayList<Column>();
//        columns.add(new Column("Id", true, String.class));
//        columns.add(new Column("Name", true, String.class));
//        columns.add(new Column("Standard Type", true, String.class , standardTypeList));
//        columns.add(new Column("Custom Type", true, String.class));
//        attributeEntity.setColumns(columns);
//        attributeEntity.setCustomDialog(new PropertyDialog(attributeEntity));
//        attributeEntity.setTableDataListener(new TableDataListener() {
//            Object[][] data;
//               List<TItemDefinition> itemDefinitions_Selected = new ArrayList<TItemDefinition>();
//
//
//            @Override
//            public void init() {
//                List<TProperty> properties = processSpec.getProperty();
//                Object[][] data_local = new Object[properties.size()][attributeEntity.getColumns().size()];
//                Iterator<TProperty> itr = properties.iterator();
//                int i = 0;
//                while (itr.hasNext()) {
//                    TProperty property = itr.next();
//                    Object[] row = new Object[4];
//                    row[0] = property.getId();
//                    row[1] = property.getName();
//                    String itemSubjectRef = property.getItemSubjectRef() == null ? null
//                            : property.getItemSubjectRef().getLocalPart();
//                    TItemDefinition itemDefinition = itemSubjectRef == null ? null
//                            : (TItemDefinition) definitions.getRootElement(itemSubjectRef,TItemDefinition.class);
//                    QName standardType_Qname = itemDefinition == null ? null
//                            : itemDefinition.getStructureRef();
//                    String standardType = standardType_Qname == null ? null : standardType_Qname.getLocalPart();
////                        row[2] = standardType;
//                   if(itemDefinition!=null){
//                    itemDefinitions_Selected.add(itemDefinition);
//                   }
//                    if ("String".equals(standardType) || "Integer".equals(standardType) || "Boolean".equals(standardType) || "Float".equals(standardType) || "Object".equals(standardType)) {
//                        row[2] = standardType;
//                        row[3] = "";
//                    } else {
//                        row[2] = "Object";
//                        row[3] = standardType;
//                    }
//
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
//                for(TItemDefinition itemDefinition : itemDefinitions_Selected){
//                    definitions.removeRootElement(itemDefinition);
//                }
//
////                List<TItemDefinition> itemDefinitions = new ArrayList<TItemDefinition>();
//                for (Object[] row : data) {
//                    TProperty property = new TProperty();
//                    property.setId((String) row[0]);
//                    property.setName((String) row[1]);
//
//                    TItemDefinition itemDefinition = new TItemDefinition();
//                    itemDefinition.setId(BPMNUtil.getAutoGeneratedStringId() + "_" + property.getId());
//
//                    if ("Object".equals((String) row[2])) {
//                        itemDefinition.setStructureRef(new QName("", (String) row[3], ""));
//                    } else {
//                        itemDefinition.setStructureRef(new QName("", (String) row[2], ""));
//                    }
//                    property.setItemSubjectRef(new QName("", itemDefinition.getId(), ""));
//
//                    definitions.addRootElement(itemDefinition);
//                    properties.add(property);
//                }
//                processSpec.setProperty(properties);
//                this.data = data;
//            }
//        });
//        return new CustomNAttributeSupport(attributeEntity);
//    }
    /**
     * ------- Property End -------- *
     */
    private ComboBoxPropertySupport getProcessTypeProperty() {
        final BPMNProcessScene processScene = this;
        final TProcess processSpec = (TProcess) processScene.getRootElementSpec();
        ComboBoxListener comboBoxListener = new ComboBoxListener() {
            @Override
            public void setItem(ComboBoxValue value) {
                processSpec.setProcessType((TProcessType) value.getValue());
            }

            @Override
            public ComboBoxValue getItem() {
                return new ComboBoxValue(processSpec.getProcessType(), processSpec.getProcessType().value());
            }

            @Override
            public List<ComboBoxValue> getItemList() {
                ComboBoxValue[] values = new ComboBoxValue[]{
                    new ComboBoxValue(TProcessType.NONE, "None"),
                    new ComboBoxValue(TProcessType.PRIVATE, "Private"),
                    new ComboBoxValue(TProcessType.PUBLIC, "Public")};
                return Arrays.asList(values);
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
        return new ComboBoxPropertySupport(this.getModelerFile(), "processType", "Process Type", "The process type attribute provides additional information about the level of abstraction modeled by this process.", comboBoxListener);
    }

    @Override
    public void manageLayerWidget() {
        for (ArtifactWidget artifact : this.getArtifacts()) {
            if (artifact instanceof GroupWidget) {
                ((GroupWidget) artifact).bringToBack();
            } else if (artifact instanceof TextAnnotationWidget) {
                ((TextAnnotationWidget) artifact).bringToFront();
            }
        }
        super.manageLayerWidget();
    }

    /**
     * @return the flowElements
     */
    public List<FlowElementWidget> getFlowElements() {
        return flowElements;
    }

    public FlowElementWidget getFlowElement(String id) {
        FlowElementWidget widget = null;
        for (FlowElementWidget flowElementWidget : flowElements) {
            if (flowElementWidget.getId().equals(id)) {
                widget = flowElementWidget;
                break;
            }
        }
        return widget;
    }

    public ArtifactWidget getArtifact(String id) {
        ArtifactWidget widget = null;
        for (ArtifactWidget artifactWidget : artifacts) {
            if (artifactWidget.getId().equals(id)) {
                widget = artifactWidget;
                break;
            }
        }
        return widget;
    }

//    public FlowElementWidget findFlowElement(String id) {
//        FlowElementWidget widget = null;
//        for (FlowElementWidget flowElementWidget : flowElements) {
//            if (flowElementWidget instanceof FlowNodeWidget) {
//                if (((FlowNodeWidget) flowElementWidget).getId().equals(id)) {
//                    widget = flowElementWidget;
//                    break;
//                } else if (flowElementWidget instanceof SubProcessWidget) {
//                    widget = ((SubProcessWidget) flowElementWidget).findFlowElement(id);
//                    if (widget != null) {
//                        return widget;
//                    }
//                }
//            } else if (flowElementWidget instanceof SequenceFlowWidget) {
//                if (((SequenceFlowWidget) flowElementWidget).getId().equals(id)) {
//                    widget = flowElementWidget;
//                    break;
//                }
//            } else {
//                throw new InvalidElmentException("Invalid BPMN Element" + flowElementWidget);
//            }
//        }
//        return widget;
//    }
    /**
     * @param flowElements the flowElements to set
     */
    public void setFlowElements(List<FlowElementWidget> flowElements) {
        this.flowElements = flowElements;
    }

    public void removeFlowElement(FlowElementWidget flowElementWidget) {
        this.flowElements.remove(flowElementWidget);
    }

    public void addFlowElement(FlowElementWidget flowElementWidget) {
        this.flowElements.add(flowElementWidget);
    }

    @Override
    public BaseElementWidget findBaseElement(String id) {
        BaseElementWidget widget = null;
        List<BaseElementWidget> baseElementWidgets = new ArrayList<BaseElementWidget>(flowElements);
        baseElementWidgets.addAll(artifacts);
        for (BaseElementWidget baseElementWidget : baseElementWidgets) {
            if (baseElementWidget instanceof FlowNodeWidget) {
                if (((FlowNodeWidget) baseElementWidget).getId().equals(id)) {
                    widget = baseElementWidget;
                    break;
                } else if (baseElementWidget instanceof SubProcessWidget) {
                    widget = ((SubProcessWidget) baseElementWidget).findBaseElement(id);
                    if (widget != null) {
                        return widget;
                    }
                }
            } else if (baseElementWidget instanceof SequenceFlowWidget) {
                if (((SequenceFlowWidget) baseElementWidget).getId().equals(id)) {
                    widget = baseElementWidget;
                    break;
                }
            } else if (baseElementWidget instanceof ArtifactWidget) {
                if (((ArtifactWidget) baseElementWidget).getId().equals(id)) {
                    widget = baseElementWidget;
                    break;
                }
            } else {
                throw new InvalidElmentException("Invalid BPMN Element" + baseElementWidget);
            }
        }
        return widget;
    }

    @Override
    public BaseElementWidget getBaseElement(String id) {
        BaseElementWidget widget = null;
        List<BaseElementWidget> baseElementWidgets = new ArrayList<BaseElementWidget>(flowElements);
        baseElementWidgets.addAll(artifacts);
        for (BaseElementWidget baseElementWidget : baseElementWidgets) {
            if (baseElementWidget.getId().equals(id)) {
                widget = baseElementWidget;
                break;
            }
        }
        return widget;
    }

    @Override
    public List<IBaseElementWidget> getBaseElements() {
        List<IBaseElementWidget> baseElementWidgets = new ArrayList<IBaseElementWidget>(flowElements);
        baseElementWidgets.addAll(artifacts);
        return baseElementWidgets;
    }

    @Override
    public void removeBaseElement(IBaseElementWidget baseElementWidget) {
        if (baseElementWidget instanceof FlowElementWidget) {
            removeFlowElement((FlowElementWidget) baseElementWidget);
        } else if (baseElementWidget instanceof ArtifactWidget) {
            removeArtifact((ArtifactWidget) baseElementWidget);
        }
    }

    @Override
    public void addBaseElement(IBaseElementWidget baseElementWidget) {
        if (baseElementWidget instanceof FlowElementWidget) {
            addFlowElement((FlowElementWidget) baseElementWidget);
        } else if (baseElementWidget instanceof ArtifactWidget) {
            addArtifact((ArtifactWidget) baseElementWidget);
        }
    }

    @Override
    public void deleteBaseElement(IBaseElementWidget baseElementWidget) {
        TProcess processSpec = (TProcess) this.getModelerFile().getRootElement();
        if (baseElementWidget instanceof FlowElementWidget) {
            if (baseElementWidget instanceof FlowNodeWidget) { //reverse ref
                FlowNodeWidget flowNodeWidget = (FlowNodeWidget) baseElementWidget;
                IBaseElement baseElementSpec = flowNodeWidget.getBaseElementSpec();

                List<SequenceFlowWidget> sequenceFlowWidgetList = new ArrayList<SequenceFlowWidget>();
                sequenceFlowWidgetList.addAll(flowNodeWidget.getOutgoingSequenceFlows());
                sequenceFlowWidgetList.addAll(flowNodeWidget.getIncomingSequenceFlows());

                for (SequenceFlowWidget sequenceFlowWidget : sequenceFlowWidgetList) {
                    sequenceFlowWidget.remove();
                }

                List<AssociationWidget> associationWidgetList = new ArrayList<AssociationWidget>();
                associationWidgetList.addAll(flowNodeWidget.getOutgoingAssociation());
                associationWidgetList.addAll(flowNodeWidget.getIncomingAssociation());

                for (AssociationWidget associationWidget : associationWidgetList) {
                    associationWidget.remove();
                }

                if (flowNodeWidget instanceof ActivityWidget) {
                    ActivityWidget activityWidget = (ActivityWidget) flowNodeWidget;
                    for (BoundaryEventWidget boundaryEventWidget : activityWidget.getBoundaryEvents()) {
                        boundaryEventWidget.remove();
                    }
                    if (flowNodeWidget instanceof SubProcessWidget) {
                    }
                }
                processSpec.removeBaseElement(baseElementSpec);
                flowNodeWidget.setFlowElementsContainer(null);
                this.flowElements.remove(flowNodeWidget);
            } else if (baseElementWidget instanceof SequenceFlowWidget) {
                SequenceFlowWidget sequenceFlowWidget = ((SequenceFlowWidget) baseElementWidget);
                TSequenceFlow sequenceFlowSpec = sequenceFlowWidget.getSequenceFlowSpec();

                FlowNodeWidget sourceWidget = sequenceFlowWidget.getSourceNode();
                TFlowNode sourceSpec = (TFlowNode) sourceWidget.getBaseElementSpec();
                FlowNodeWidget targetWidget = sequenceFlowWidget.getTargetNode();
                TFlowNode targetSpec = (TFlowNode) targetWidget.getBaseElementSpec();

                sourceSpec.getOutgoing().remove(sequenceFlowSpec.getId());
                targetSpec.getIncoming().remove(sequenceFlowSpec.getId());

                sourceWidget.getOutgoingSequenceFlows().remove(sequenceFlowWidget);
                targetWidget.getIncomingSequenceFlows().remove(sequenceFlowWidget);

                processSpec.removeFlowElement(sequenceFlowSpec);
                sequenceFlowWidget.setFlowElementsContainer(null);
                this.flowElements.remove(sequenceFlowWidget);
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else if (baseElementWidget instanceof ArtifactWidget) {
            if (baseElementWidget instanceof AssociationWidget) {
                AssociationWidget associationWidget = ((AssociationWidget) baseElementWidget);
                TAssociation associationSpec = (TAssociation) associationWidget.getBaseElementSpec();

                IBaseElementWidget sourceBaseElementWidget = associationWidget.getSourceElementWidget();
                if (sourceBaseElementWidget instanceof IFlowNodeWidget) {
                    FlowNodeWidget sourceWidget = (FlowNodeWidget) sourceBaseElementWidget;
                    TFlowNode sourceSpec = (TFlowNode) sourceWidget.getBaseElementSpec();
                    sourceSpec.getOutgoing().remove(associationSpec.getId());
                    sourceWidget.getOutgoingAssociation().remove(associationWidget);
                } else if (sourceBaseElementWidget instanceof IArtifactNodeWidget) {
                    IArtifactNodeWidget sourceWidget = (IArtifactNodeWidget) sourceBaseElementWidget;
                    sourceWidget.getOutgoingArtifactEdgeWidget().remove(associationWidget);
                }

                IBaseElementWidget targetBaseElementWidget = associationWidget.getTargetElementWidget();
                if (targetBaseElementWidget instanceof IFlowNodeWidget) {
                    FlowNodeWidget targetWidget = (FlowNodeWidget) targetBaseElementWidget;
                    TFlowNode targetSpec = (TFlowNode) targetWidget.getBaseElementSpec();
                    targetSpec.getIncoming().remove(associationSpec.getId());
                    targetWidget.getIncomingAssociation().remove(associationWidget);
                } else if (targetBaseElementWidget instanceof IArtifactNodeWidget) {
                    IArtifactNodeWidget targetWidget = (IArtifactNodeWidget) targetBaseElementWidget;
                    targetWidget.getIncommingArtifactEdgeWidget().remove(associationWidget);
                }

                processSpec.removeArtifact(associationSpec);
                associationWidget.setFlowElementsContainer(null);
                this.artifacts.remove(associationWidget);
            } else {
                ArtifactWidget artifactWidget = (ArtifactWidget) baseElementWidget;
                TArtifact artifactSpec = (TArtifact) artifactWidget.getBaseElementSpec();

                List<AssociationWidget> associationWidgetList = new ArrayList<AssociationWidget>();
                if (baseElementWidget instanceof TextAnnotationWidget) {
                    associationWidgetList.addAll(((TextAnnotationWidget) artifactWidget).getOutgoingAssociation());
                    associationWidgetList.addAll(((TextAnnotationWidget) artifactWidget).getIncomingAssociation());
                } else if (baseElementWidget instanceof GroupWidget) {
                    associationWidgetList.addAll(((GroupWidget) artifactWidget).getOutgoingAssociation());
                    associationWidgetList.addAll(((GroupWidget) artifactWidget).getIncomingAssociation());
                }
                for (AssociationWidget associationWidget : associationWidgetList) {
                    associationWidget.remove();
                }

                processSpec.removeArtifact(artifactSpec);
                artifactWidget.setFlowElementsContainer(null);
                this.artifacts.remove(artifactWidget);
            }
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }

    }

    @Override
    public void createBaseElement(IBaseElementWidget baseElementWidget) {
        String baseElementId = "";
        Boolean isExist = false;
        if (baseElementWidget instanceof FlowElementWidget) {
            this.flowElements.add((FlowElementWidget) baseElementWidget);
            if (baseElementWidget instanceof FlowNodeWidget) { //reverse ref
                ((FlowNodeWidget) baseElementWidget).setFlowElementsContainer(this);
                baseElementId = ((FlowNodeWidget) baseElementWidget).getId();
                isExist = ((FlowNodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
            } else if (baseElementWidget instanceof SequenceFlowWidget) {
                ((SequenceFlowWidget) baseElementWidget).setFlowElementsContainer(this);
                baseElementId = ((SequenceFlowWidget) baseElementWidget).getId();
                isExist = ((SequenceFlowWidget) baseElementWidget).getEdgeWidgetInfo().isExist();
            } else {
                throw new InvalidElmentException("Invalid BPMN FlowElement : " + baseElementWidget);
            }
        } else if (baseElementWidget instanceof ArtifactWidget) {
            this.artifacts.add((ArtifactWidget) baseElementWidget);
            if (baseElementWidget instanceof TextAnnotationWidget) { //reverse ref
                ((TextAnnotationWidget) baseElementWidget).setFlowElementsContainer(this);
                baseElementId = ((TextAnnotationWidget) baseElementWidget).getId();
                isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
            } else if (baseElementWidget instanceof AssociationWidget) {
                ((AssociationWidget) baseElementWidget).setFlowElementsContainer(this);
                baseElementId = ((AssociationWidget) baseElementWidget).getId();
                isExist = ((EdgeWidget) baseElementWidget).getEdgeWidgetInfo().isExist();
            } else if (baseElementWidget instanceof GroupWidget) { //reverse ref
                ((GroupWidget) baseElementWidget).setFlowElementsContainer(this);
                baseElementId = ((GroupWidget) baseElementWidget).getId();
                isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
            } else {
                throw new InvalidElmentException("Invalid BPMN ArtifactWidget : " + baseElementWidget);
            }
        } else if (baseElementWidget instanceof LaneSetWidget) {
            ((LaneSetWidget) baseElementWidget).setFlowElementsContainer(this); //LWG
            baseElementId = ((LaneSetWidget) baseElementWidget).getId();
            //isExist = ((ContainerWidget) baseElementWidget).getNodeWidgetInfo().isExist();
        } else if (baseElementWidget instanceof LaneWidget) {
            ((LaneWidget) baseElementWidget).setFlowElementsContainer(this);
            baseElementId = ((LaneWidget) baseElementWidget).getId();
            //  isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
        } //        else if (baseElementWidget instanceof ParticipantWidget) {
        //            ((ParticipantWidget) baseElementWidget).setFlowElementsContainer(this);
        //            baseElementId = ((ParticipantWidget) baseElementWidget).getId();
        //            isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
        //        } else if (baseElementWidget instanceof ConversationNodeWidget) {
        //            if (baseElementWidget instanceof ConversationWidget) {
        //                ((ConversationWidget) baseElementWidget).setFlowElementsContainer(this);
        //                baseElementId = ((ConversationWidget) baseElementWidget).getId();
        //                isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
        //            } else if (baseElementWidget instanceof SubConversationWidget) {
        //                ((SubConversationWidget) baseElementWidget).setFlowElementsContainer(this);
        //                baseElementId = ((SubConversationWidget) baseElementWidget).getId();
        //                isExist = ((NodeWidget) baseElementWidget).getNodeWidgetInfo().isExist();
        //            } else {
        //                throw new InvalidElmentException("Invalid BPMN ConversationNodeWidget Element");
        //            }
        //        }
        else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }

        if (!isExist) {
            /*specification Start*///ELEMENT_UPGRADE
            //Process Model
            TProcess process = (TProcess) this.getModelerFile().getRootElement();
            IBaseElement baseElement = null;
            if (baseElementWidget instanceof FlowElementWidget) {
                if (baseElementWidget instanceof FlowNodeWidget) {
                    if (baseElementWidget instanceof ActivityWidget) {
                        if (baseElementWidget instanceof TaskWidget) {
                            if (baseElementWidget instanceof ManualTaskWidget) {
                                baseElement = new TManualTask();
                            } else if (baseElementWidget instanceof ServiceTaskWidget) {
                                baseElement = new TServiceTask();
                            } else if (baseElementWidget instanceof UserTaskWidget) {
                                baseElement = new TUserTask();
                            } else if (baseElementWidget instanceof ScriptTaskWidget) {
                                baseElement = new TScriptTask();
                            } else if (baseElementWidget instanceof BusinessRuleTaskWidget) {
                                baseElement = new TBusinessRuleTask();
                            } else if (baseElementWidget instanceof SendTaskWidget) {
                                baseElement = new TSendTask();
                            } else if (baseElementWidget instanceof ReceiveTaskWidget) {
                                baseElement = new TReceiveTask();
                            } else if (baseElementWidget.getClass() == TaskWidget.class) { // class compared to it is not instance of any other subclass
                                baseElement = new TTask();
                            } else {
                                throw new InvalidElmentException("Invalid BPMN Task Element : " + baseElement);
                            }
                        } else if (baseElementWidget instanceof SubProcessWidget) {
                            if (baseElementWidget instanceof DefaultSubProcessWidget) {
                                baseElement = new TSubProcess();
                            } else if (baseElementWidget instanceof EventSubProcessWidget) {
                                baseElement = new TSubProcess();
                                ((TSubProcess) baseElement).setTriggeredByEvent(true);
                            } else if (baseElementWidget instanceof TransactionSubProcessWidget) {
                                baseElement = new TTransaction();
                            } else if (baseElementWidget instanceof AdHocSubProcessWidget) {
                                baseElement = new TAdHocSubProcess();
                            }
                        } else if (baseElementWidget instanceof CallActivityWidget) {
                            baseElement = new TCallActivity();
                        }
                    } else if (baseElementWidget instanceof EventWidget) {
                        if (baseElementWidget instanceof StartEventWidget) {
                            baseElement = new TStartEvent();
                        } else if (baseElementWidget instanceof BoundaryEventWidget) {
                            baseElement = new TBoundaryEvent();
                        } else if (baseElementWidget instanceof IntermediateCatchEventWidget) {
                            baseElement = new TIntermediateCatchEvent();
                        } else if (baseElementWidget instanceof IntermediateThrowEventWidget) {
                            baseElement = new TIntermediateThrowEvent();
                        } else if (baseElementWidget instanceof EndEventWidget) {
                            baseElement = new TEndEvent();
                        } else {
                            throw new InvalidElmentException("Invalid BPMN Event Element : " + baseElement);
                        }
                        String docDefnition = ((EventWidget) baseElementWidget).getNodeWidgetInfo().getModelerDocument().getDefinition();

                        List<TEventDefinition> eventDefinitions = getEventDefinitions(docDefnition);
                        //set definition that is used to accessed  TError , TEsclation in embedded method .
//                        if (!eventDefinitions.isEmpty() && eventDefinitions.get(0) != null) {
//                            eventDefinitions.get(0).setDefinitionElement(this.getModelerFile().getDefinitionElement());
//                        }

                        ((TEvent) baseElement).setEventDefinition(eventDefinitions);

                    } else if (baseElementWidget instanceof GatewayWidget) {
                        if (baseElementWidget instanceof ComplexGatewayWidget) {
                            baseElement = new TComplexGateway();
                        } else if (baseElementWidget instanceof EventBasedGatewayWidget) {
                            baseElement = new TEventBasedGateway();
                        } else if (baseElementWidget instanceof ExclusiveGatewayWidget) {
                            baseElement = new TExclusiveGateway();
                        } else if (baseElementWidget instanceof InclusiveGatewayWidget) {
                            baseElement = new TInclusiveGateway();
                        } else if (baseElementWidget instanceof ParallelGatewayWidget) {
                            baseElement = new TParallelGateway();
                        } else {
                            throw new InvalidElmentException("Invalid BPMN Gateway Element : " + baseElement);
                        }
                    }
                } else if (baseElementWidget instanceof SequenceFlowWidget) {
                    baseElement = new TSequenceFlow();
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else if (baseElementWidget instanceof ArtifactWidget) {
                if (baseElementWidget instanceof TextAnnotationWidget) { //reverse ref
                    baseElement = new TTextAnnotation();
                } else if (baseElementWidget instanceof AssociationWidget) {
                    baseElement = new TAssociation();
                } else if (baseElementWidget instanceof GroupWidget) {
                    baseElement = new TGroup();
                } else {
                    throw new InvalidElmentException("Invalid BPMN ArtifactWidget : " + baseElementWidget);
                }
            } else if (baseElementWidget instanceof LaneSetWidget) {
                baseElement = new TLaneSet();
            } else if (baseElementWidget instanceof LaneWidget) {
                baseElement = new TLane();
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
            baseElementWidget.setBaseElementSpec(baseElement);
            baseElement.setId(baseElementId);
            if (baseElement instanceof TFlowElement) {
                process.addFlowElement((TFlowElement) baseElement);
            } else if (baseElement instanceof TArtifact) {
                process.addArtifact((TArtifact) baseElement);
            }
            ElementConfigFactory elementConfigFactory = this.getModelerFile().getVendorSpecification().getElementConfigFactory();
            elementConfigFactory.initializeObjectValue(baseElement);

        } else {
            if (baseElementWidget instanceof FlowElementWidget) {
                if (baseElementWidget instanceof FlowNodeWidget) {
                    FlowNodeWidget flowNodeWidget = (FlowNodeWidget) baseElementWidget;
                    flowNodeWidget.setBaseElementSpec(flowNodeWidget.getNodeWidgetInfo().getBaseElementSpec());
                    //set TDefinition that is used to accessed  TError , TEsclation in TEventDefinition embedded method .
//                    if (baseElementWidget instanceof EventWidget) {
//                        List<TEventDefinition> eventDefinitions = ((TEvent) baseElementWidget.getBaseElementSpec()).getEventDefinition();
//                        if (!eventDefinitions.isEmpty() && eventDefinitions.get(0) != null) {
//                            eventDefinitions.get(0).setDefinitionElement(this.getModelerFile().getDefinitionElement());
//                        }
//                    }
                } else if (baseElementWidget instanceof SequenceFlowWidget) {
                    SequenceFlowWidget sequenceFlowWidget = (SequenceFlowWidget) baseElementWidget;//TBF_CODE
                    baseElementWidget.setBaseElementSpec(sequenceFlowWidget.getEdgeWidgetInfo().getBaseElementSpec());
                } else {
                    throw new InvalidElmentException("Invalid BPMN Element");
                }
            } else if (baseElementWidget instanceof ArtifactWidget) {
                if (baseElementWidget instanceof TextAnnotationWidget) {
                    TextAnnotationWidget textAnnotationWidget = (TextAnnotationWidget) baseElementWidget;
                    textAnnotationWidget.setBaseElementSpec(textAnnotationWidget.getNodeWidgetInfo().getBaseElementSpec());
                } else if (baseElementWidget instanceof AssociationWidget) {
                    AssociationWidget associationWidget = (AssociationWidget) baseElementWidget;
                    associationWidget.setBaseElementSpec(associationWidget.getEdgeWidgetInfo().getBaseElementSpec());
                } else if (baseElementWidget instanceof GroupWidget) {
                    GroupWidget groupWidget = (GroupWidget) baseElementWidget;
                    groupWidget.setBaseElementSpec(groupWidget.getNodeWidgetInfo().getBaseElementSpec());
                } else {
                    throw new InvalidElmentException("Invalid BPMN ArtifactWidget : " + baseElementWidget);
                }
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        }

    }

    /**
     * @return the artifacts
     */
    public List<ArtifactWidget> getArtifacts() {
        return artifacts;
    }

    /**
     * @param artifacts the artifacts to set
     */
    public void setArtifacts(List<ArtifactWidget> artifacts) {
        this.artifacts = artifacts;
    }

    public void addArtifact(ArtifactWidget artifactWidget) {
        this.artifacts.add(artifactWidget);
    }

    public void removeArtifact(ArtifactWidget artifactWidget) {
        this.artifacts.remove(artifactWidget);
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //ELEMENT_UPGRADE
    public List<TEventDefinition> getEventDefinitions(String docDefnition) {
        List<TEventDefinition> eventDefinitions = new ArrayList<TEventDefinition>();

        if (docDefnition == null) {
            // skip None
        } else if (docDefnition.equalsIgnoreCase("TMessageEventDefinition")) {
            eventDefinitions.add(new TMessageEventDefinition());
        } else if (docDefnition.equalsIgnoreCase("TTimerEventDefinition")) {
            eventDefinitions.add(new TTimerEventDefinition());
        } else if (docDefnition.equalsIgnoreCase("TErrorEventDefinition")) {
            eventDefinitions.add(new TErrorEventDefinition());
        } else if (docDefnition.equalsIgnoreCase("TEscalationEventDefinition")) {
            eventDefinitions.add(new TEscalationEventDefinition());
        } else if (docDefnition.equalsIgnoreCase("TCancelEventDefinition")) {
            eventDefinitions.add(new TCancelEventDefinition());
        } else if (docDefnition.equalsIgnoreCase("TCompensateEventDefinition")) {
            eventDefinitions.add(new TCompensateEventDefinition());
        } else if (docDefnition.equalsIgnoreCase("TConditionalEventDefinition")) {
            eventDefinitions.add(new TConditionalEventDefinition());
        } else if (docDefnition.equalsIgnoreCase("TLinkEventDefinition")) {
            eventDefinitions.add(new TLinkEventDefinition());
        } else if (docDefnition.equalsIgnoreCase("TSignalEventDefinition")) {
            eventDefinitions.add(new TSignalEventDefinition());
        } else if (docDefnition.equalsIgnoreCase("TTerminateEventDefinition")) {
            eventDefinitions.add(new TTerminateEventDefinition());
        } else {
            throw new InvalidElmentException("Invalid BPMN Document Name : " + docDefnition);
        }

        ElementConfigFactory elementConfigFactory = this.getModelerFile().getVendorSpecification().getElementConfigFactory();
        for (TEventDefinition eventDefinition : eventDefinitions) {
            elementConfigFactory.initializeObjectValue(eventDefinition);
        }

        return eventDefinitions;
    }

    @Override
    public void createVisualPropertySet(ElementPropertySet elementPropertySet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the debugNodeWidget
     */
    public List<IFlowNodeWidget> getDebugNodeWidget() {
        return debugNodeWidget;
    }

    /**
     * @param debugNodeWidget the debugNodeWidget to set
     */
    public void setDebugNodeWidget(List<IFlowNodeWidget> debugNodeWidget) {
        this.debugNodeWidget = debugNodeWidget;
    }

    public void addDebugNodeWidget(IFlowNodeWidget debugNodeWidget) {
        this.debugNodeWidget.add(debugNodeWidget);
    }
}
