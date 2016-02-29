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

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.jbpmn.spec.extend.TCollaborationEdge;
import org.netbeans.jbpmn.spec.extend.TCollaborationNode;
import org.netbeans.modeler.config.element.ElementConfigFactory;
import org.netbeans.modeler.core.exception.InvalidElmentException;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.specification.model.document.widget.IFlowEdgeWidget;
import org.netbeans.modeler.specification.model.document.widget.IFlowNodeWidget;
import org.netbeans.modeler.widget.edge.EdgeWidget;
import org.netbeans.modeler.widget.edge.info.EdgeWidgetInfo;
import org.netbeans.modeler.widget.properties.generic.ElementPropertySupport;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;
import org.openide.nodes.Sheet;

public class CollaborationEdgeWidget extends EdgeWidget implements CollaborationElementWidget, IFlowEdgeWidget {

    public CollaborationEdgeWidget(IModelerScene scene, EdgeWidgetInfo edge) {
        super(scene, edge);
        this.addPropertyChangeListener("name", new PropertyChangeListener<String>() {
            @Override
            public void changePerformed(String value) {
                setName(value);
                CollaborationEdgeWidget.this.setLabel(name);
            }
        });
    }
    private IBaseElement baseElementSpec;
    private Widget flowElementsContainer; //FlowElementsContainerScenereverse ref
    private CollaborationNodeWidget sourceNode;
    private CollaborationNodeWidget targetNode;

    @Override
    public void createPropertySet(ElementPropertySet set) {
        ElementConfigFactory elementConfigFactory = this.getModelerScene().getModelerFile().getVendorSpecification().getElementConfigFactory();
        elementConfigFactory.createPropertySet(set, this.getBaseElementSpec(), getPropertyChangeListeners());

    }

    /**
     * @return the sourceNode
     */
    public CollaborationNodeWidget getSourceNode() {
        return sourceNode;
    }

    @Override
    public IFlowNodeWidget getSourceFlowNodeWidget() {
        return getSourceNode();
    }

    @Override
    public IFlowNodeWidget getTargetFlowNodeWidget() {
        return getTargetNode();
    }

    /**
     * @param sourceNode the sourceNode to set
     */
    public void setSourceNode(CollaborationNodeWidget sourceNode) {
        this.sourceNode = sourceNode;
        if (sourceNode != null) {
            IBaseElement baseElement = sourceNode.getBaseElementSpec();
            IBaseElement linkElement = this.getBaseElementSpec();
            if (baseElement instanceof TCollaborationNode && linkElement instanceof TCollaborationEdge) {
                TCollaborationNode collaborationNode = (TCollaborationNode) baseElement;
                TCollaborationEdge collaborationEdge = (TCollaborationEdge) linkElement;
                collaborationEdge.setSourceRef(collaborationNode.getId());
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else {
            IBaseElement linkElement = this.getBaseElementSpec();
            if (linkElement instanceof TCollaborationEdge) {
                TCollaborationEdge collaborationEdge = (TCollaborationEdge) linkElement;
                collaborationEdge.setSourceRef(null);
            }
        }
    }

    /**
     * @return the targetNode
     */
    public CollaborationNodeWidget getTargetNode() {
        return targetNode;
    }

    /**
     * @param targetNode the targetNode to set
     */
    public void setTargetNode(CollaborationNodeWidget targetNode) {
        this.targetNode = targetNode;
        if (targetNode != null) {
            IBaseElement baseElement = targetNode.getBaseElementSpec();
            IBaseElement linkElement = this.getCollaborationEdgeSpec();
            if (baseElement instanceof TCollaborationNode && linkElement instanceof TCollaborationEdge) {
                TCollaborationNode collaborationNode = (TCollaborationNode) baseElement;
                TCollaborationEdge collaborationEdge = (TCollaborationEdge) linkElement;
                collaborationEdge.setTargetRef(collaborationNode.getId());
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else {
            IBaseElement linkElement = this.getCollaborationEdgeSpec();
            if (linkElement instanceof TCollaborationEdge) {
                TCollaborationEdge collaborationEdge = (TCollaborationEdge) linkElement;
                collaborationEdge.setTargetRef(null);
            }
        }

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
            this.getCollaborationEdgeSpec().setName(name);
        } else {
            this.getCollaborationEdgeSpec().setName(null);
        }
    }

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
    private Color color;
    // private Float size;

    public Sheet.Set getVisualPropertiesSet(Sheet.Set set) throws NoSuchMethodException, NoSuchFieldException {
        set.put(new ElementPropertySupport(this, Color.class, "color", "Color", "The Line Color of the SequenceFlow Element."));
//        //  set.put(new BPMNElementPropertySupport(this, Float.class, "innerElementStartOffset", "Start Background Offset", "The Start Background Offset of the Inner Element."));
        return set;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
        this.setLineColor(color);
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
     * @return the sequenceFlowSpec
     */
    public TCollaborationEdge getCollaborationEdgeSpec() {
        return (TCollaborationEdge) baseElementSpec;
    }

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }
}
