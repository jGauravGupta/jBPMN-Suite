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

import java.awt.BasicStroke;
import java.awt.Color;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modeler.widget.properties.customattr.CustomAttributeSupport;
import org.netbeans.jbpmn.spec.TAssociation;
import org.netbeans.jbpmn.spec.TBaseElement;
import org.netbeans.jbpmn.spec.TFlowElement;
import org.netbeans.modeler.config.element.ElementConfigFactory;
import org.netbeans.modeler.core.exception.InvalidElmentException;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.specification.model.document.widget.IArtifactEdgeWidget;
import org.netbeans.modeler.specification.model.document.widget.IBaseElementWidget;
import org.netbeans.modeler.widget.edge.EdgeWidget;
import org.netbeans.modeler.widget.edge.info.EdgeWidgetInfo;
import org.netbeans.modeler.widget.properties.generic.ElementPropertySupport;
import org.openide.nodes.Sheet;

public class AssociationWidget extends EdgeWidget implements ArtifactWidget, IArtifactEdgeWidget {

    public AssociationWidget(IModelerScene scene, EdgeWidgetInfo edge) {
        super(scene, edge);
        setStroke(new BasicStroke(1.2f,
                BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                BasicStroke.JOIN_MITER, new float[]{4, 4}, 0));

    }
    private IBaseElement baseElementSpec;
    private Widget flowElementsContainer; //FlowElementsContainerScenereverse ref
    private IBaseElementWidget sourceNode;  // BaseElementWidget not FlowNodeWidget because cross connection
    private IBaseElementWidget targetNode;

    @Override
    public void createPropertySet(ElementPropertySet set) {

        ElementConfigFactory elementConfigFactory = this.getModelerScene().getModelerFile().getVendorSpecification().getElementConfigFactory();
        elementConfigFactory.createPropertySet(set, this.getBaseElementSpec(), null);

        set.put("OTHER_PROP", new CustomAttributeSupport(this.getModelerScene().getModelerFile(),this.getBaseElementSpec(), "Other Attributes", "Other Attributes of the BPMN Element"));

    }
// public IBaseElementWidget getSourceElementWidget();
///    public IBaseElementWidget getSourceElementWidget();
//
//    public void setSourceElementWidget(IBaseElementWidget sourceNode);
//
//    public IBaseElementWidget getTargetElementWidget();
//
//    public void setTargetElementWidget(IBaseElementWidget targetNode);

    /**
     * @return the sourceNode
     */
    public IBaseElementWidget getSourceElementWidget() {
        return sourceNode;
    }

    /**
     * @param sourceNode the sourceNode to set
     */
    public void setSourceElementWidget(IBaseElementWidget sourceNode) {
        this.sourceNode = sourceNode;
        if (sourceNode != null) {
            IBaseElement baseElement = sourceNode.getBaseElementSpec();
            IBaseElement associationElement = this.getBaseElementSpec();
            if (associationElement instanceof TAssociation) {
                TAssociation association = (TAssociation) associationElement;
                association.setSourceRef(baseElement.getId());
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else {
            TBaseElement associationElement = (TFlowElement) this.getBaseElementSpec();
            if (associationElement instanceof TAssociation) {
                TAssociation association = (TAssociation) associationElement;
                association.setSourceRef(null);
            }
        }

    }

    /**
     * @return the targetNode
     */
    public IBaseElementWidget getTargetElementWidget() {
        return targetNode;
    }

    /**
     * @param targetNode the targetNode to set
     */
    public void setTargetElementWidget(IBaseElementWidget targetNode) {
        this.targetNode = targetNode;
        if (targetNode != null) {
            IBaseElement baseElement = targetNode.getBaseElementSpec();
            IBaseElement associationElement = this.getBaseElementSpec();
            if (associationElement instanceof TAssociation) {
                TAssociation association = (TAssociation) associationElement;
                association.setTargetRef(baseElement.getId());
            } else {
                throw new InvalidElmentException("Invalid BPMN Element");
            }
        } else {
            IBaseElement associationElement = this.getBaseElementSpec();
            if (associationElement instanceof TAssociation) {
                TAssociation association = (TAssociation) associationElement;
                association.setTargetRef(null);
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

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }
}
