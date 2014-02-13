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
import org.netbeans.jbpmn.spec.TTextAnnotation;
import org.netbeans.modeler.config.element.ElementConfigFactory;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.specification.model.document.widget.IArtifactNodeWidget;
import org.netbeans.modeler.widget.context.ContextPaletteModel;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.image.svg.ResizeType;
import org.netbeans.modeler.widget.node.image.svg.SVGResizeHandler;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.customattr.CustomAttributeSupport;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;
import org.openide.util.Exceptions;
import org.w3c.dom.svg.SVGDocument;

public class TextAnnotationWidget extends NodeWidget implements ArtifactWidget, IArtifactNodeWidget {

    public TextAnnotationWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
        //this.setLabelWidget(null);
//                     this.hideLabel();
//
        this.getNodeImageWidget().setResizeHandler(new SVGResizeHandler() {
            @Override
            public void resizing(SVGDocument svgDocument, ResizeType resizeType, Double documentWidth, Double documentHeight, Double scaleX, Double scaleY) {
                if (resizeType == ResizeType.OUTER) {//for annotation

                    String w1 = new Double(new Double(getElementValue(svgDocument, "OUTLINE-COVER", "w")) * scaleX).toString();
                    String h1 = new Double(new Double(getElementValue(svgDocument, "OUTLINE-COVER", "h")) * scaleY).toString();

                    TextAnnotationWidget.this.setElementValue(svgDocument, "OUTLINE-COVER", "d", "M" + w1 + ",1 L1,1 L1," + h1 + " L" + w1 + "," + h1 + "");

                    String w2 = new Double(new Double(getElementValue(svgDocument, "OUTLINE", "w")) * scaleX).toString();
                    String h2 = new Double(new Double(getElementValue(svgDocument, "OUTLINE", "h")) * scaleY).toString();
                    TextAnnotationWidget.this.setElementValue(svgDocument, "OUTLINE", "width", w2);
                    TextAnnotationWidget.this.setElementValue(svgDocument, "OUTLINE", "height", h2);

                    String wr = new Double(new Double(getElementValue(svgDocument, "TEXT-REGION", "w")) * scaleX).toString();
                    String hr = new Double(new Double(getElementValue(svgDocument, "TEXT-REGION", "h")) * scaleY).toString();
                    TextAnnotationWidget.this.setElementValue(svgDocument, "TEXT-REGION", "width", wr);
                    TextAnnotationWidget.this.setElementValue(svgDocument, "TEXT-REGION", "height", hr);

                    svgDocument.getRootElement().setAttribute("width", new Double((documentWidth * scaleX) + 4).toString());
                    svgDocument.getRootElement().setAttribute("height", new Double((documentHeight * scaleY) + 4).toString());
                }
            }

            @Override
            public void resizingStarted(SVGDocument svgDocument, ResizeType resizeType, Double documentWidth, Double documentHeight, Double scaleX, Double scaleY) {
            }

            @Override
            public void resizingFinished(SVGDocument svgDocument, ResizeType resizeType, Double documentWidth, Double documentHeight, Double scaleX, Double scaleY) {
            }
        });

        this.addPropertyChangeListener("text", new PropertyChangeListener<String>() {
            @Override
            public void changePerformed(String value) {
//                setName(value); //Name Set by IText Interface Handler in ElementPropertySet
                setLabel(value);
            }
        });

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
        elementConfigFactory.createPropertySet(set, this.getBaseElementSpec(), getPropertyChangeListeners());
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

    @Override
    public void setLabel(String label) {
        TextAnnotationWidget.this.setElementTextValue(label);
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
        TTextAnnotation textAnnotation = (TTextAnnotation) this.getBaseElementSpec();
        if (textAnnotation.getText() != null && textAnnotation.getText().getContent() != null && !textAnnotation.getText().getContent().isEmpty()) {
            ((PropertyChangeListener<String>) getPropertyChangeListeners().get("text")).changePerformed(textAnnotation.getText().getContent());
        }
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
