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
package org.netbeans.jbpmn.core.widget.conversation;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.jbpmn.core.widget.*;
import org.netbeans.jbpmn.core.widget.context.ContextModel;
import org.netbeans.modeler.widget.properties.customattr.CustomAttributeSupport;
import org.netbeans.jbpmn.spec.extend.TCollaborationNode;
import org.netbeans.modeler.config.element.ElementConfigFactory;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.specification.model.document.widget.IFlowEdgeWidget;
import org.netbeans.modeler.specification.model.document.widget.IFlowNodeWidget;
import org.netbeans.modeler.widget.context.ContextPaletteModel;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;
import org.openide.util.Exceptions;

/**
 *
 *
 *
 *
 */
public class CollaborationNodeWidget extends NodeWidget implements CollaborationElementWidget, IFlowNodeWidget {

    public CollaborationNodeWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
        this.addPropertyChangeListener("name", new PropertyChangeListener<String>() {
            @Override
            public void changePerformed(String value) {
                setName(value);
//                setLabel(value);
                if (value != null && !value.trim().isEmpty()) {
                    CollaborationNodeWidget.this.setLabel(value);
                } else {
                    CollaborationNodeWidget.this.setLabel("");
                }
            }
        });
    }
    private IBaseElement baseElementSpec;
    //private FlowElementsContainerScene flowElementsContainer ; //reverse ref
    private Widget flowElementsContainer; //reverse ref
    protected List<CollaborationEdgeWidget> incomingCollaborationEdges = new ArrayList<CollaborationEdgeWidget>();
    protected List<CollaborationEdgeWidget> outgoingCollaborationEdges = new ArrayList<CollaborationEdgeWidget>();
    private List<AssociationWidget> incomingAssociation = new ArrayList<AssociationWidget>(); // Association ref not defined in spec
    private List<AssociationWidget> outgoingAssociation = new ArrayList<AssociationWidget>();

    @Override
    public List<? extends IFlowEdgeWidget> getIncommingFlowEdgeWidget() {
        return incomingCollaborationEdges;
    }

    @Override
    public List<? extends IFlowEdgeWidget> getOutgoingFlowEdgeWidget() {
        return outgoingCollaborationEdges;
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        ElementConfigFactory elementConfigFactory = this.getModelerScene().getModelerFile().getVendorSpecification().getElementConfigFactory();
        elementConfigFactory.createPropertySet(set, this.getBaseElementSpec(), getPropertyChangeListeners());
        set.put("BASIC_PROP", new CustomAttributeSupport(this.getModelerScene().getModelerFile(), this.getBaseElementSpec(), "Other Attributes", "Other Attributes of the BPMN Element"));

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
     * @return the incomingCollaborationEdges
     */
    public List<CollaborationEdgeWidget> getIncomingCollaborationEdges() {
        return incomingCollaborationEdges;
    }

    /**
     * @param incomingCollaborationEdges the incomingCollaborationEdges to set
     */
    public void setIncomingCollaborationEdges(List<CollaborationEdgeWidget> incomingCollaborationEdges) {
        this.incomingCollaborationEdges = incomingCollaborationEdges;
    }

    public void addIncomingCollaborationEdge(CollaborationEdgeWidget incomingCollaborationEdge) {
        this.incomingCollaborationEdges.add(incomingCollaborationEdge);
//        IBaseElement baseElement = this.getBaseElementSpec();//bpmnProcess.getFlowElement(this.getId());
//        IBaseElement collaborationEdgeElement = incomingCollaborationEdge.getBaseElementSpec();//bpmnProcess.getFlowElement(incomingCollaborationEdge.getId());
//        if (baseElement instanceof TCollaborationNode && collaborationEdgeElement instanceof TCollaborationEdge) {
//            TCollaborationNode collaborationNode = (TCollaborationNode) baseElement;
//            TCollaborationEdge collaborationEdge = (TCollaborationEdge) collaborationEdgeElement;
//            collaborationNode.addIncoming(collaborationEdge.getId());
//        } else {
//            throw new InvalidElmentException("Invalid BPMN Element");
//        }
    }

    public void removeIncomingCollaborationEdge(CollaborationEdgeWidget incomingCollaborationEdge) {
        this.incomingCollaborationEdges.remove(incomingCollaborationEdge);
//        IBaseElement baseElement = this.getBaseElementSpec();
//        IBaseElement collaborationEdgeElement = incomingCollaborationEdge.getBaseElementSpec();
//        if (baseElement instanceof TCollaborationNode && collaborationEdgeElement instanceof TCollaborationEdge) {
//            TCollaborationNode collaborationNode = (TCollaborationNode) baseElement;
//            TCollaborationEdge collaborationEdge = (TCollaborationEdge) collaborationEdgeElement;
//            collaborationNode.removeIncoming(collaborationEdge.getId());
//        } else {
//            throw new InvalidElmentException("Invalid BPMN Element");
//        }
    }

    /**
     * @return the outgoingCollaborationEdges
     */
    public List<CollaborationEdgeWidget> getOutgoingCollaborationEdges() {
        return outgoingCollaborationEdges;
    }

    /**
     * @param outgoingCollaborationEdges the outgoingCollaborationEdges to set
     */
    public void setOutgoingCollaborationEdges(List<CollaborationEdgeWidget> outgoingCollaborationEdges) {
        this.outgoingCollaborationEdges = outgoingCollaborationEdges;
    }

    public void addOutgoingCollaborationEdge(CollaborationEdgeWidget outgoingCollaborationEdge) {
        this.outgoingCollaborationEdges.add(outgoingCollaborationEdge);
//        IBaseElement baseElement = this.getBaseElementSpec();
//        IBaseElement collaborationEdgeElement = outgoingCollaborationEdge.getBaseElementSpec();//bpmnProcess.getFlowElement(outgoingCollaborationEdge.getId());
//        if (baseElement instanceof TCollaborationNode && collaborationEdgeElement instanceof TCollaborationEdge) {
//            TCollaborationNode collaborationNode = (TCollaborationNode) baseElement;
//            TCollaborationEdge collaborationEdge = (TCollaborationEdge) collaborationEdgeElement;
//            collaborationNode.addOutgoing(collaborationEdge.getId());
//        } else {
//            throw new InvalidElmentException("Invalid BPMN Element");
//        }
    }

    public void removeOutgoingCollaborationEdge(CollaborationEdgeWidget outgoingCollaborationEdge) {
        this.outgoingCollaborationEdges.remove(outgoingCollaborationEdge);
//        IBaseElement baseElement = this.getBaseElementSpec();
//        IBaseElement collaborationEdgeElement = outgoingCollaborationEdge.getBaseElementSpec();//bpmnProcess.getFlowElement(outgoingCollaborationEdge.getId());
//        if (baseElement instanceof TCollaborationNode && collaborationEdgeElement instanceof TCollaborationEdge) {
//            TCollaborationNode collaborationNode = (TCollaborationNode) baseElement;
//            TCollaborationEdge collaborationEdge = (TCollaborationEdge) collaborationEdgeElement;
//            collaborationNode.removeOutgoing(collaborationEdge.getId());
//        } else {
//            throw new InvalidElmentException("Invalid BPMN Element");
//        }
    }
    protected String id;
    protected String name;
    protected String documentation;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        if (name != null && !name.trim().isEmpty()) {
            ((TCollaborationNode) this.getBaseElementSpec()).setName(name);
        } else {
            ((TCollaborationNode) this.getBaseElementSpec()).setName(null);
        }

    }

//    @Override
//    public void setLabel(String label) {
//        CollaborationNodeWidget.this.setElementTextValue(label);
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
    @Override
    public Widget getFlowElementsContainer() {
        return flowElementsContainer;
    }

    /**
     * @param flowElementsContainer the flowElementsContainer to set
     */
    @Override
    public void setFlowElementsContainer(Widget flowElementsContainer) {
        this.flowElementsContainer = flowElementsContainer;
    }

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }

    /**
     * @return the baseElementSpec
     */
    @Override
    public IBaseElement getBaseElementSpec() {
        return baseElementSpec;
    }

    /**
     * @param baseElementSpec the baseElementSpec to set
     */
    @Override
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
//        IBaseElement baseElement = this.getBaseElementSpec();
//        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
//        if (baseElement instanceof TCollaborationNode && associationElement instanceof TAssociation) {
//            TCollaborationNode flowNode = (TCollaborationNode) baseElement;
//            TAssociation association = (TAssociation) associationElement;
//            flowNode.addIncoming(association.getId());
//        } else {
//            throw new InvalidElmentException("Invalid BPMN Element");
//        }
    }

    public void removeIncomingAssociation(AssociationWidget outgoingAssociation) {
        this.incomingAssociation.remove(outgoingAssociation);
//        IBaseElement baseElement = this.getBaseElementSpec();
//        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
//        if (baseElement instanceof TCollaborationNode && associationElement instanceof TAssociation) {
//            TCollaborationNode flowNode = (TCollaborationNode) baseElement;
//            TAssociation association = (TAssociation) associationElement;
//            flowNode.removeIncoming(association.getId());
//        } else {
//            throw new InvalidElmentException("Invalid BPMN Element");
//        }
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
//        IBaseElement baseElement = this.getBaseElementSpec();
//        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
//        if (baseElement instanceof TCollaborationNode && associationElement instanceof TAssociation) {
//            TCollaborationNode collaborationNode = (TCollaborationNode) baseElement;
//            TAssociation association = (TAssociation) associationElement;
//            collaborationNode.addOutgoing(association.getId());
//        } else {
//            throw new InvalidElmentException("Invalid BPMN Element");
//        }
    }

    public void removeOutgoingAssociation(AssociationWidget outgoingAssociation) {
        this.outgoingAssociation.remove(outgoingAssociation);
//        IBaseElement baseElement = this.getBaseElementSpec();
//        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
//        if (baseElement instanceof TCollaborationNode && associationElement instanceof TAssociation) {
//            TCollaborationNode collaborationNode = (TCollaborationNode) baseElement;
//            TAssociation association = (TAssociation) associationElement;
//            collaborationNode.removeOutgoing(association.getId());
//        } else {
//            throw new InvalidElmentException("Invalid BPMN Element");
//        }
    }

//    @Override
//    public JMenuItem getConvertWidgetSetting() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    @Override
    public ContextPaletteModel getContextPaletteModel() {
        return ContextModel.getContextPaletteModel(this);
    }
}
