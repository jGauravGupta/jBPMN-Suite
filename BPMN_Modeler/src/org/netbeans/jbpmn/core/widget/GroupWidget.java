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
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.jbpmn.core.widget.context.ContextModel;
import org.netbeans.jbpmn.spec.TArtifact;
import org.netbeans.modeler.config.element.ElementConfigFactory;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.specification.model.document.widget.IArtifactNodeWidget;
import org.netbeans.modeler.widget.context.ContextPaletteModel;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.customattr.CustomAttributeSupport;
import org.openide.util.Exceptions;

public class GroupWidget extends NodeWidget implements ArtifactWidget, IArtifactNodeWidget {

    public GroupWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
    }
    protected String id;
    private IBaseElement baseElementSpec;
    private Widget flowElementsContainer; //reverse ref   //must be moved to Node Widget & also from FlowNodeWidget
    private List<AssociationWidget> incomingAssociation = new ArrayList<AssociationWidget>();// Association ref not defined in spec
    private List<AssociationWidget> outgoingAssociation = new ArrayList<AssociationWidget>();

    @Override
    public List<AssociationWidget> getIncommingArtifactEdgeWidget() {
        return incomingAssociation;
    }

    @Override
    public List<AssociationWidget> getOutgoingArtifactEdgeWidget() {
        return outgoingAssociation;
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        ElementConfigFactory elementConfigFactory = this.getModelerScene().getModelerFile().getVendorSpecification().getElementConfigFactory();
        elementConfigFactory.createPropertySet(set, this.getBaseElementSpec(), null);
        set.put("OTHER_PROP", new CustomAttributeSupport(this.getModelerScene().getModelerFile(), this.getBaseElementSpec(), "Other Attributes", "Other Attributes of the BPMN Element"));
    }

    @Override
    public void createVisualPropertySet(ElementPropertySet elementPropertySet) {
        try {
            createVisualOuterPropertiesSet(elementPropertySet);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
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
     * @return the baseElementSpec
     */
    public TArtifact getArtifactSpec() {
        return (TArtifact) baseElementSpec;
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
        this.incomingAssociation.add(outgoingAssociation); //Below Commented because according to BPMN Standard TTextAnnotation don't need to save incoming & outgoning
//        IBaseElement baseElement = this.getBaseElementSpec();
//        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
//        if (baseElement instanceof TTextAnnotation && associationElement instanceof TAssociation) {
//            TTextAnnotation textAnnotation = (TTextAnnotation) baseElement;
//            TAssociation association = (TAssociation) associationElement;
//           // textAnnotation.addIncoming(association.getId());
//        } else {
//            throw new InvalidElmentException("Invalid BPMN Element");
//        }
    }

    public void removeIncomingAssociation(AssociationWidget outgoingAssociation) {
        this.incomingAssociation.remove(outgoingAssociation);//Below Commented because according to BPMN Standard TTextAnnotation don't need to save incoming & outgoning
//        IBaseElement baseElement = this.getBaseElementSpec();
//        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
//        if (baseElement instanceof TTextAnnotation && associationElement instanceof TAssociation) {
//            TTextAnnotation textAnnotation = (TTextAnnotation) baseElement;
//            TAssociation association = (TAssociation) associationElement;
//            //textAnnotation.removeIncoming(association.getId());
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
        this.outgoingAssociation.add(outgoingAssociation);//Below Commented because according to BPMN Standard TTextAnnotation don't need to save incoming & outgoning
//         IBaseElement baseElement = this.getBaseElementSpec();
//       IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
//       if (baseElement instanceof TTextAnnotation && associationElement instanceof TAssociation) {
//           TTextAnnotation textAnnotation = (TTextAnnotation) baseElement;
//           TAssociation association = (TAssociation) associationElement;
//           //textAnnotation.addOutgoing(association.getId());
//       } else {
//           throw new InvalidElmentException("Invalid BPMN Element");
//       }
    }

    public void removeOutgoingAssociation(AssociationWidget outgoingAssociation) {
        this.outgoingAssociation.remove(outgoingAssociation);//Below Commented because according to BPMN Standard TTextAnnotation don't need to save incoming & outgoning
//         IBaseElement baseElement = this.getBaseElementSpec();
//        IBaseElement associationElement = outgoingAssociation.getBaseElementSpec();
//       if (baseElement instanceof TTextAnnotation && associationElement instanceof TAssociation) {
//           TTextAnnotation textAnnotation = (TTextAnnotation) baseElement;
//           TAssociation association = (TAssociation) associationElement;
//           textAnnotation.removeOutgoing(association.getId());
//       } else {
//           throw new InvalidElmentException("Invalid BPMN Element");
//       }
    }

    @Override
    public ContextPaletteModel getContextPaletteModel() {
        return ContextModel.getContextPaletteModel(this);
    }
}
