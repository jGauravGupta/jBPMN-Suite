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
import java.util.List;
import javax.swing.JOptionPane;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.jbpmn.core.widget.context.ContextModel;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.message.MessagePanel;
import org.netbeans.jbpmn.modeler.widget.properties.operation.OperationPanel;
import org.netbeans.jbpmn.spec.TAssociation;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TFlowNode;
import org.netbeans.jbpmn.spec.TInterface;
import org.netbeans.jbpmn.spec.TMessage;
import org.netbeans.jbpmn.spec.TOperation;
import org.netbeans.jbpmn.spec.TRootElement;
import org.netbeans.jbpmn.spec.TSequenceFlow;
import org.netbeans.jbpmn.spec.extend.MessageHandler;
import org.netbeans.jbpmn.spec.extend.OperationHandler;
import org.netbeans.modeler.config.document.FlowDimensionType;
import org.netbeans.modeler.config.element.ElementConfigFactory;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.core.exception.InvalidElmentException;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ActionHandler;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.listener.ComboBoxListener;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.support.ComboBoxPropertySupport;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.specification.model.document.widget.IFlowEdgeWidget;
import org.netbeans.modeler.specification.model.document.widget.IFlowNodeWidget;
import org.netbeans.modeler.widget.context.ContextPaletteModel;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.customattr.CustomAttributeSupport;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;
import org.openide.util.Exceptions;

/**
 *
 *
 *
 *
 */
public class FlowNodeWidget extends NodeWidget implements FlowElementWidget, IFlowNodeWidget {

    public FlowNodeWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
        this.addPropertyChangeListener("name", new PropertyChangeListener<String>() {
            @Override
            public void changePerformed(String value) {
                setName(value);
                if (value != null && !value.trim().isEmpty()) {
                    FlowNodeWidget.this.setLabel(value);
                } else {
                    FlowNodeWidget.this.setLabel("");
                }
//                if (value != null && !value.trim().isEmpty()) {
//                    FlowNodeWidget.this.setElementTextValue(value);
//                } else {
//                    FlowNodeWidget.this.setElementTextValue("");
//                }
            }
        });
    }
    private IBaseElement baseElementSpec;
    //private FlowElementsContainerScene flowElementsContainer ; //reverse ref
    private Widget flowElementsContainer; //reverse ref
    protected List<SequenceFlowWidget> incomingSequenceFlows = new ArrayList<SequenceFlowWidget>();
    protected List<SequenceFlowWidget> outgoingSequenceFlows = new ArrayList<SequenceFlowWidget>();
    private List<AssociationWidget> incomingAssociation = new ArrayList<AssociationWidget>(); // Association ref not defined in spec
    private List<AssociationWidget> outgoingAssociation = new ArrayList<AssociationWidget>();

    @Override
    public List<? extends IFlowEdgeWidget> getIncommingFlowEdgeWidget() {
        return incomingSequenceFlows;
    }

    @Override
    public List<? extends IFlowEdgeWidget> getOutgoingFlowEdgeWidget() {
        return outgoingSequenceFlows;
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        ElementConfigFactory elementConfigFactory = this.getModelerScene().getModelerFile().getVendorSpecification().getElementConfigFactory();
        elementConfigFactory.createPropertySet(set, this.getBaseElementSpec(), getPropertyChangeListeners());

        set.put("OTHER_PROP", new CustomAttributeSupport(this.getModelerScene().getModelerFile(), this.getBaseElementSpec(), "Other Attributes", "Other Attributes of the BPMN Element"));

        TFlowNode flowNodeSpec = (TFlowNode) this.getBaseElementSpec();
        if (flowNodeSpec instanceof MessageHandler) {
            set.put("BASIC_PROP", getMessageTypeProperty((MessageHandler) flowNodeSpec));
        }
        if (flowNodeSpec instanceof OperationHandler) {
            set.put("BASIC_PROP", getOperationProperty((OperationHandler) flowNodeSpec));
        }

//        final GenericEmbedded entity = new GenericEmbedded("documentation", "Documentation", "Description : Element Documentation");
//        entity.setEntityEditor(new DocumentationPane());
//        entity.setDataListener(new EmbeddedDataListener<String>() {
//            TBaseElement baseElement;
//
//            @Override
//            public void init() {
//                baseElement = (TBaseElement) FlowNodeWidget.this.getBaseElementSpec();
////                ((DocumentationPane)entity.getEntityEditor()).gett
//            }
//
//            @Override
//            public String getData() {
//                return baseElement.getDocumentationEmbedded();
//            }
//
//            @Override
//            public void setData(String data) {
//                baseElement.setDocumentationEmbedded(data);
//            }
//
//            @Override
//            public String getDisplay() {
//                String text = baseElement.getDocumentationEmbedded().replaceAll("<.*>", "").trim();
//                if (text.length() > 30) {
//                    return text.substring(30);
//                } else {
//                    return text.substring(text.length());
//                }
//
//            }
//        });
//        set.put("OTHER_PROP", new EmbeddedPropertySupport(this.getModelerScene().getModelerFile(),entity));
    }

    @Override
    public void createVisualPropertySet(ElementPropertySet elementPropertySet) {
        try {
            createVisualOuterPropertiesSet(elementPropertySet);
            createVisualInnerPropertiesSet(elementPropertySet);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * @return the incomingSequenceFlows
     */
    public List<SequenceFlowWidget> getIncomingSequenceFlows() {
        return incomingSequenceFlows;
    }

    /**
     * @param incomingSequenceFlows the incomingSequenceFlows to set
     */
    public void setIncomingSequenceFlows(List<SequenceFlowWidget> incomingSequenceFlows) {
        this.incomingSequenceFlows = incomingSequenceFlows;
    }

    public void addIncomingSequenceFlow(SequenceFlowWidget incomingSequenceFlow) {
        this.incomingSequenceFlows.add(incomingSequenceFlow);

        //TProcess bpmnProcess = this.getFlowElementsContainer().getBPMNComponent().getBPMNFile().getBPMNProcess();
        IBaseElement baseElement = this.getBaseElementSpec();//bpmnProcess.getFlowElement(this.getId());
        IBaseElement flowSeqElement = incomingSequenceFlow.getBaseElementSpec();//bpmnProcess.getFlowElement(incomingSequenceFlow.getId());

        if (baseElement instanceof TFlowNode && flowSeqElement instanceof TSequenceFlow) {
            TFlowNode flowNode = (TFlowNode) baseElement;
            TSequenceFlow sequenceFlow = (TSequenceFlow) flowSeqElement;
            flowNode.addIncoming(sequenceFlow.getId());
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
    }

    public void removeIncomingSequenceFlow(SequenceFlowWidget incomingSequenceFlow) {
        this.incomingSequenceFlows.remove(incomingSequenceFlow);

        IBaseElement baseElement = this.getBaseElementSpec();
        IBaseElement flowSeqElement = incomingSequenceFlow.getBaseElementSpec();

        if (baseElement instanceof TFlowNode && flowSeqElement instanceof TSequenceFlow) {
            TFlowNode flowNode = (TFlowNode) baseElement;
            TSequenceFlow sequenceFlow = (TSequenceFlow) flowSeqElement;
            flowNode.removeIncoming(sequenceFlow.getId());
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
    }

    /**
     * @return the outgoingSequenceFlows
     */
    public List<SequenceFlowWidget> getOutgoingSequenceFlows() {
        return outgoingSequenceFlows;
    }

    /**
     * @param outgoingSequenceFlows the outgoingSequenceFlows to set
     */
    public void setOutgoingSequenceFlows(List<SequenceFlowWidget> outgoingSequenceFlows) {
        this.outgoingSequenceFlows = outgoingSequenceFlows;
    }

    public void addOutgoingSequenceFlow(SequenceFlowWidget outgoingSequenceFlow) {
        this.outgoingSequenceFlows.add(outgoingSequenceFlow);
        IBaseElement baseElement = this.getBaseElementSpec();
        IBaseElement flowSeqElement = outgoingSequenceFlow.getBaseElementSpec();//bpmnProcess.getFlowElement(outgoingSequenceFlow.getId());
        if (baseElement instanceof TFlowNode && flowSeqElement instanceof TSequenceFlow) {
            TFlowNode flowNode = (TFlowNode) baseElement;
            TSequenceFlow sequenceFlow = (TSequenceFlow) flowSeqElement;
            flowNode.addOutgoing(sequenceFlow.getId());
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
    }

    public void removeOutgoingSequenceFlow(SequenceFlowWidget outgoingSequenceFlow) {
        this.outgoingSequenceFlows.remove(outgoingSequenceFlow);
        //TProcess bpmnProcess = this.getFlowElementsContainer().getBPMNComponent().getBPMNFile().getBPMNProcess();
        IBaseElement baseElement = this.getBaseElementSpec();
        IBaseElement flowSeqElement = outgoingSequenceFlow.getBaseElementSpec();//bpmnProcess.getFlowElement(outgoingSequenceFlow.getId());
        if (baseElement instanceof TFlowNode && flowSeqElement instanceof TSequenceFlow) {
            TFlowNode flowNode = (TFlowNode) baseElement;
            TSequenceFlow sequenceFlow = (TSequenceFlow) flowSeqElement;
            flowNode.removeOutgoing(sequenceFlow.getId());
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
    }
//
//    /**
//     * @return the subProcess
//     */
//    public bpmn.widget.SubProcess getSubProcess() {
//        return subProcess;
//    }
//
//    /**
//     * @param subProcess the subProcess to set
//     */
//    public void setSubProcess(bpmn.widget.SubProcess subProcess) {
//        this.subProcess = subProcess;
//    }
    protected String id;
    protected String name;
    protected String documentation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        if (name != null && !name.trim().isEmpty()) {
            ((TFlowNode) FlowNodeWidget.this.getBaseElementSpec()).setName(name);
        } else {
            ((TFlowNode) FlowNodeWidget.this.getBaseElementSpec()).setName(null);
        }

    }
//    @Override
//    public void setLabel(String name){
//        setName(name);
//    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    /**
     * @return the flowElementsContainer
     */
    public Widget getFlowElementsContainer() {
        return flowElementsContainer;
    }

    /**
     * @param flowElementsContainer the flowElementsContainer to set
     */
    public void setFlowElementsContainer(Widget flowElementsContainer) {
        this.flowElementsContainer = flowElementsContainer;
    }

//    /**
//     * @return the flowNodeSpec
//     */
//    public TFlowNode getFlowNodeSpec() {
//        return flowNodeSpec;
//    }
//
//    /**
//     * @param flowNodeSpec the flowNodeSpec to set
//     */
//    public void setFlowNodeSpec(TFlowNode flowNodeSpec) {
//        this.flowNodeSpec = flowNodeSpec;
//    }
    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }

    /**
     * @return the baseElementSpec
     */
    public IBaseElement getBaseElementSpec() {
        return baseElementSpec;
    }

    /**
     * @param baseElementSpec the baseElementSpec to set
     */
    public void setBaseElementSpec(IBaseElement baseElementSpec) {
        this.baseElementSpec = baseElementSpec;
    }

    /**
     * @return the incomingAssociation
     */
    public List<AssociationWidget> getIncomingAssociation() {
        return incomingAssociation;
    }

    /**
     * @param incomingAssociation the incomingAssociation to set
     */
    public void setIncomingAssociation(List<AssociationWidget> incomingAssociation) {
        this.incomingAssociation = incomingAssociation;
    }

    public void addIncomingAssociation(AssociationWidget outgoingAssociation) {
        this.incomingAssociation.add(outgoingAssociation);
        IBaseElement baseElement = this.getBaseElementSpec();
        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
        if (baseElement instanceof TFlowNode && associationElement instanceof TAssociation) {
            TFlowNode flowNode = (TFlowNode) baseElement;
            TAssociation association = (TAssociation) associationElement;
            flowNode.addIncoming(association.getId());
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
    }

    public void removeIncomingAssociation(AssociationWidget outgoingAssociation) {
        this.incomingAssociation.remove(outgoingAssociation);
        IBaseElement baseElement = this.getBaseElementSpec();
        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
        if (baseElement instanceof TFlowNode && associationElement instanceof TAssociation) {
            TFlowNode flowNode = (TFlowNode) baseElement;
            TAssociation association = (TAssociation) associationElement;
            flowNode.removeIncoming(association.getId());
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
    }

    /**
     * @return the outgoingAssociation
     */
    public List<AssociationWidget> getOutgoingAssociation() {
        return outgoingAssociation;
    }

    /**
     * @param outgoingAssociation the outgoingAssociation to set
     */
    public void setOutgoingAssociation(List<AssociationWidget> outgoingAssociation) {
        this.outgoingAssociation = outgoingAssociation;
    }

    public void addOutgoingAssociation(AssociationWidget outgoingAssociation) {
        this.outgoingAssociation.add(outgoingAssociation);
        IBaseElement baseElement = this.getBaseElementSpec();
        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
        if (baseElement instanceof TFlowNode && associationElement instanceof TAssociation) {
            TFlowNode flowNode = (TFlowNode) baseElement;
            TAssociation association = (TAssociation) associationElement;
            flowNode.addOutgoing(association.getId());
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
    }

    public void removeOutgoingAssociation(AssociationWidget outgoingAssociation) {
        this.outgoingAssociation.remove(outgoingAssociation);
        IBaseElement baseElement = this.getBaseElementSpec();
        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
        if (baseElement instanceof TFlowNode && associationElement instanceof TAssociation) {
            TFlowNode flowNode = (TFlowNode) baseElement;
            TAssociation association = (TAssociation) associationElement;
            flowNode.removeOutgoing(association.getId());
        } else {
            throw new InvalidElmentException("Invalid BPMN Element");
        }
    }

//    @Override
//    public JMenuItem getConvertWidgetSetting() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    protected ComboBoxPropertySupport getMessageTypeProperty(final MessageHandler handlerSpec) {
        final FlowNodeWidget flowNodeWidget = this;
        final ModelerFile modelerFile = flowNodeWidget.getModelerScene().getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();

        ComboBoxListener<TMessage> comboBoxListener = new ComboBoxListener<TMessage>() {
            @Override
            public void setItem(ComboBoxValue<TMessage> comboBoxValue) {
                if (comboBoxValue.getValue() != null) {
                    handlerSpec.setMessageRef(comboBoxValue.getId());
                } else {
                    handlerSpec.setMessageRef(null);
                }
            }

            @Override
            public ComboBoxValue<TMessage> getItem() {
                ComboBoxValue<TMessage> comboBoxValue;
                TMessage message = (TMessage) definition.getRootElement(handlerSpec.getMessageRef(), TMessage.class);
                if (message != null) {
                    comboBoxValue = new ComboBoxValue<TMessage>(message.getId(), message, message.getName());
                } else {
                    comboBoxValue = new ComboBoxValue<TMessage>(null, null, "");
                }
                return comboBoxValue;
            }

            @Override
            public List<ComboBoxValue<TMessage>> getItemList() {
                List<ComboBoxValue<TMessage>> values = new ArrayList<ComboBoxValue<TMessage>>();
                values.add(new ComboBoxValue<TMessage>(null, null, ""));
                for (TRootElement rootElement : definition.getRootElement()) {
                    if (rootElement instanceof TMessage) {
                        TMessage message = (TMessage) rootElement;
                        values.add(new ComboBoxValue<TMessage>(message.getId(), message, message.getName()));
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
                ActionHandler<TMessage> actHan = new ActionHandler<TMessage>() {
                    @Override
                    public EntityComponent getItemPanel() {
                        return new MessagePanel(modelerFile);
                    }

                    @Override
                    public void createItem(ComboBoxValue<TMessage> comboBoxValue) {
                        definition.addRootElement(comboBoxValue.getValue());
                    }

                    @Override
                    public int showRemoveItemDialog() {
                        return JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Message Element ?", "Delete Message Element", JOptionPane.OK_CANCEL_OPTION);
                    }

                    @Override
                    public void deleteItem(ComboBoxValue<TMessage> comboBoxValue) {
                        definition.removeRootElement(comboBoxValue.getValue());
                        handlerSpec.setMessageRef(null);
                    }

                    @Override
                    public void updateItem(ComboBoxValue<TMessage> comboBoxValue) {
//                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };
                return actHan;
            }
        };
        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(), "message", "Message", "The message attribute provides additional information about the event message definition (name , structure reference).", comboBoxListener);
    }

    protected ComboBoxPropertySupport getOperationProperty(final OperationHandler handlerSpec) {
        final FlowNodeWidget flowNodeWidget = this;
        final ModelerFile modelerFile = flowNodeWidget.getModelerScene().getModelerFile();
        final TDefinitions definition = (TDefinitions) modelerFile.getDefinitionElement();

        ComboBoxListener<TOperation> comboBoxListener = new ComboBoxListener<TOperation>() {
            @Override
            public void setItem(ComboBoxValue<TOperation> comboBoxValue) {
                if (comboBoxValue.getValue() != null) {
                    handlerSpec.setOperationRef(comboBoxValue.getId());
                } else {
                    handlerSpec.setOperationRef(null);
                }
            }

            @Override
            public ComboBoxValue<TOperation> getItem() {
                String operationRef = handlerSpec.getOperationRef();
                if (operationRef == null) {
                    return new ComboBoxValue<TOperation>(null, null, "");
                }
                for (TRootElement rootElement : definition.getRootElement()) {
                    if (rootElement instanceof TInterface) {
                        TInterface _interface = (TInterface) rootElement;
                        for (TOperation operation : _interface.getOperation()) {
                            if (operationRef.equals(operation.getId())) {
                                String interfaceName;
                                if (_interface.getName() != null && !_interface.getName().trim().isEmpty()) {
                                    interfaceName = _interface.getName();
                                } else {
                                    interfaceName = "Interface[Id:" + _interface.getId() + "]";
                                }
                                String operationName;
                                if (operation.getName() != null && !operation.getName().trim().isEmpty()) {
                                    operationName = operation.getName();
                                } else {
                                    operationName = "Operation[Id:" + operation.getId() + "]";
                                }
                                return new ComboBoxValue<TOperation>(operation.getId(), operation, interfaceName + "/" + operationName);
                            }
                        }
                    }
                }
                return new ComboBoxValue<TOperation>(null, null, "");
            }

            @Override
            public List<ComboBoxValue<TOperation>> getItemList() {
                List<ComboBoxValue<TOperation>> values = new ArrayList<ComboBoxValue<TOperation>>();
                values.add(new ComboBoxValue<TOperation>(null, null, ""));
                for (TRootElement rootElement : definition.getRootElement()) {
                    if (rootElement instanceof TInterface) {
                        TInterface _interface = (TInterface) rootElement;
                        String interfaceName;
                        if (_interface.getName() != null && !_interface.getName().trim().isEmpty()) {
                            interfaceName = _interface.getName();
                        } else {
                            interfaceName = "Interface[Id:" + _interface.getId() + "]";
                        }
                        for (TOperation operation : _interface.getOperation()) {
                            String operationName;
                            if (operation.getName() != null && !operation.getName().trim().isEmpty()) {
                                operationName = operation.getName();
                            } else {
                                operationName = "Operation[Id:" + operation.getId() + "]";
                            }
                            operation.setInterface_Ref(_interface.getId());
                            values.add(new ComboBoxValue<TOperation>(operation.getId(), operation, interfaceName + "/" + operationName));
                        }
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
                ActionHandler<TOperation> actHan = new ActionHandler<TOperation>() {
                    @Override
                    public EntityComponent getItemPanel() {
                        return new OperationPanel(modelerFile);
                    }

                    @Override
                    public void createItem(ComboBoxValue<TOperation> comboBoxValue) {
                        TOperation operation = comboBoxValue.getValue();
                        TInterface _interface = (TInterface) definition.getRootElement(operation.getInterface_Ref(), TInterface.class);
                        _interface.addOperation(operation);
                    }

                    @Override
                    public int showRemoveItemDialog() {
                        return JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Operation Element ?", "Delete Operation Element", JOptionPane.OK_CANCEL_OPTION);
                    }

                    @Override
                    public void deleteItem(ComboBoxValue<TOperation> comboBoxValue) {
                        TOperation operation = comboBoxValue.getValue();
                        TInterface _interface = (TInterface) definition.getRootElement(operation.getInterface_Ref(), TInterface.class);
                        _interface.removeOperation(operation);
                        handlerSpec.setOperationRef(null);
                    }

                    @Override
                    public void updateItem(ComboBoxValue<TOperation> comboBoxValue) {
                        TOperation operation = comboBoxValue.getValue();
                        TInterface _interface = (TInterface) definition.getRootElement(operation.getInterface_Ref(), TInterface.class);
                        _interface.addOperation(operation);
                    }
                };
                return actHan;
            }
        };
        return new ComboBoxPropertySupport(this.getModelerScene().getModelerFile(), "TOperation", "Operation", "", comboBoxListener);
    }

    @Override
    public ContextPaletteModel getContextPaletteModel() {
        return ContextModel.getContextPaletteModel(this);
    }

    private static final Float START_EVENT_HOVER_BORDER = 0.4F;
    private static final Float INTERMEDIATE_CATCH_EVENT_HOVER_BORDER = 0.2F;
    private static final Float INTERMEDIATE_THROW_EVENT_HOVER_BORDER = 0.2F;
    private static final Float END_EVENT_HOVER_BORDER = 0.4F;
    private static final Float TASK_HOVER_BORDER = 0.5F;
    private static final Float GATEWAY_HOVER_BORDER = 0.5F;

    public void unhoverWidget(int padding) {
        String model = this.getNodeWidgetInfo().getModelerDocument().getDocumentModel();
        FlowDimensionType flowDimension = this.getNodeWidgetInfo().getModelerDocument().getFlowDimension();
        if (model.equals(DocumentModelType.EVENT.name())) {
            if (flowDimension == FlowDimensionType.START) {
                decreaseBorderWidth(START_EVENT_HOVER_BORDER + padding);
            } else if (flowDimension == FlowDimensionType.INTERMIDATE_CATCH) {
                decreaseBorderWidth(INTERMEDIATE_CATCH_EVENT_HOVER_BORDER + padding);
            } else if (flowDimension == FlowDimensionType.INTERMIDATE_THROW) {
                decreaseBorderWidth(INTERMEDIATE_THROW_EVENT_HOVER_BORDER + padding);
            } else if (flowDimension == FlowDimensionType.END) {
                decreaseBorderWidth(END_EVENT_HOVER_BORDER + padding);
            }
        } else if (model.equals(DocumentModelType.ACTIVITY.name())) {
            decreaseBorderWidth(TASK_HOVER_BORDER + padding);
        } else if (model.equals(DocumentModelType.GATEWAY.name())) {
            decreaseBorderWidth(GATEWAY_HOVER_BORDER + padding);
        }
    }

    public void hoverWidget(int padding) {
        String model = this.getNodeWidgetInfo().getModelerDocument().getDocumentModel();
        FlowDimensionType flowDimension = this.getNodeWidgetInfo().getModelerDocument().getFlowDimension();

        //instance match (instanceof EndEventWidget) based not used because of incovertible type
        if (model.equals(DocumentModelType.EVENT.name())) {
            if (flowDimension == FlowDimensionType.START) {
                increaseBorderWidth(START_EVENT_HOVER_BORDER + padding);
            } else if (flowDimension == FlowDimensionType.INTERMIDATE_CATCH) {
                increaseBorderWidth(INTERMEDIATE_CATCH_EVENT_HOVER_BORDER + padding);
            } else if (flowDimension == FlowDimensionType.INTERMIDATE_THROW) {
                increaseBorderWidth(INTERMEDIATE_THROW_EVENT_HOVER_BORDER + padding);
            } else if (flowDimension == FlowDimensionType.END) {
                increaseBorderWidth(END_EVENT_HOVER_BORDER + padding);
            }
        } else if (model.equals(DocumentModelType.ACTIVITY.name())) {
            increaseBorderWidth(TASK_HOVER_BORDER + padding);
        } else if (model.equals(DocumentModelType.GATEWAY.name())) {
            increaseBorderWidth(GATEWAY_HOVER_BORDER + padding);
        }

    }
}
