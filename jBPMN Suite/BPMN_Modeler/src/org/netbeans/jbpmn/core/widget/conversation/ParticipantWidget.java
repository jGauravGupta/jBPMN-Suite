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

import org.netbeans.jbpmn.spec.TParticipant;
import org.netbeans.jbpmn.spec.TParticipantMultiplicity;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.node.image.svg.ResizeType;
import org.netbeans.modeler.widget.node.image.svg.SVGResizeHandler;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.generic.ElementPropertySupport;
import org.openide.util.Exceptions;
import org.w3c.dom.svg.SVGDocument;

public class ParticipantWidget extends CollaborationNodeWidget {

    public ParticipantWidget(IModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);

        this.getNodeImageWidget().setResizeHandler(new SVGResizeHandler() {
            @Override
            public void resizing(SVGDocument svgDocument, ResizeType resizeType, Double documentWidth, Double documentHeight, Double scaleX, Double scaleY) {
                resizingFinished(svgDocument, resizeType, documentWidth, documentHeight, scaleX, scaleY);
            }

            @Override
            public void resizingStarted(SVGDocument svgDocument, ResizeType resizeType, Double documentWidth, Double documentHeight, Double scaleX, Double scaleY) {
            }

            @Override
            public void resizingFinished(SVGDocument svgDocument, ResizeType resizeType, Double documentWidth, Double documentHeight, Double scaleX, Double scaleY) {
                if (resizeType == ResizeType.OUTER) {//for annotation
                    String w1 = new Double(new Double(getElementValue(svgDocument, "CAPTION", "w")) * scaleX).toString();
                    String h1 = new Double(new Double(getElementValue(svgDocument, "CAPTION", "h")) * scaleY).toString();
                    ParticipantWidget.this.setElementValue(svgDocument, "CAPTION", "width", w1, true);
                    ParticipantWidget.this.setElementValue(svgDocument, "CAPTION", "height", h1, true);

                    String w2 = new Double(new Double(getElementValue(svgDocument, "OUTLINE", "w")) * scaleX).toString();
                    String h2 = new Double(new Double(getElementValue(svgDocument, "OUTLINE", "h")) * scaleY).toString();
                    ParticipantWidget.this.setElementValue(svgDocument, "OUTLINE", "width", w2, true);
                    ParticipantWidget.this.setElementValue(svgDocument, "OUTLINE", "height", h2, true);

                    String wr = new Double(new Double(getElementValue(svgDocument, "TEXT-REGION", "w")) * scaleX).toString();
                    String hr = new Double(new Double(getElementValue(svgDocument, "TEXT-REGION", "h")) * scaleY).toString();
                    ParticipantWidget.this.setElementValue(svgDocument, "TEXT-REGION", "width", wr, true);
                    ParticipantWidget.this.setElementValue(svgDocument, "TEXT-REGION", "height", hr, true);

//                    ParticipantWidget.this.setElementValue(svgDocument, "TEXT-REGION2", "width", wr, true);
//                    ParticipantWidget.this.setElementValue(svgDocument, "TEXT-REGION2", "height", hr, true);
                    String px = new Double(new Double(getElementValue(svgDocument, "TEXT-REGION", "px")) * scaleX).toString();
                    String py = new Double(new Double(getElementValue(svgDocument, "TEXT-REGION", "py")) * scaleY).toString();
                    ParticipantWidget.this.setElementValue(svgDocument, "TEXT-REGION", "x", px, true);
                    ParticipantWidget.this.setElementValue(svgDocument, "TEXT-REGION", "y", py, true);

                    ParticipantWidget.this.setElementValue(svgDocument, "TEXT-REGION2", "x", px, true);
                    ParticipantWidget.this.setElementValue(svgDocument, "TEXT-REGION2", "y", py, true);

                    String x = new Double(new Double(getElementValue(svgDocument, "ParticipantMultiplicity", "x")) * scaleX).toString();
                    String y = new Double(new Double(getElementValue(svgDocument, "ParticipantMultiplicity", "y")) * scaleY).toString();
                    ParticipantWidget.this.setElementValue(svgDocument, "ParticipantMultiplicity", "transform", "translate(" + x + "," + y + ")", true);

                    svgDocument.getRootElement().setAttribute("width", new Double((documentWidth * scaleX) + 4).toString());
                    svgDocument.getRootElement().setAttribute("height", new Double((documentHeight * scaleY) + 4).toString());
                }
            }
        });
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        try {
            super.createPropertySet(set);
//              ElementConfigFactory.createPropertySet(set, this.getBaseElementSpec(), getPropertyChangeListeners());

            set.put("BASIC_PROP", new ElementPropertySupport(this, Boolean.class,
                    "participantMultiplicityEmbedded", "Participant Multiplicity", "participantMultiplicityEmbedded des"));
            set.put("BASIC_PROP", new ElementPropertySupport(this, Integer.class,
                    "minimumParticipantEmbedded", "Minimum", "Minimum des"));
            set.put("BASIC_PROP", new ElementPropertySupport(this, Integer.class,
                    "maximumParticipantEmbedded", "Maximum", "Maximum des"));
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoSuchFieldException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    @Override
    public void init() {
        manageParticipantMultiplicityIcon();
    }

    void manageParticipantMultiplicityIcon() {
        if (((TParticipant) this.getBaseElementSpec()).getParticipantMultiplicity() != null) {
            setElementValue("ParticipantMultiplicity", "opacity", "1");
        } else {
            setElementValue("ParticipantMultiplicity", "opacity", "0");
        }
    }

    @Override
    public void setLabel(String label) {
        setElementTextValue(label);
    }
    private Boolean participantMultiplicityEmbedded;
    private Integer minimumParticipantEmbedded = 0;
    private Integer maximumParticipantEmbedded = 1;

    /**
     * @return the participantMultiplicityEmbedded
     */
    public Boolean getParticipantMultiplicityEmbedded() {
        return participantMultiplicityEmbedded;
    }

    /**
     * @param participantMultiplicityEmbedded the
     * participantMultiplicityEmbedded to set
     */
    public void setParticipantMultiplicityEmbedded(Boolean participantMultiplicityEmbedded) {
        this.participantMultiplicityEmbedded = participantMultiplicityEmbedded;
        manageParticipantMultiplicity();
    }

    void manageParticipantMultiplicity() {
        TParticipant participant = (TParticipant) this.getBaseElementSpec();
        if (participantMultiplicityEmbedded) {
            TParticipantMultiplicity participantMultiplicity = new TParticipantMultiplicity();
            participantMultiplicity.setMinimum(this.getMinimumParticipantEmbedded());
            participantMultiplicity.setMaximum(this.getMaximumParticipantEmbedded());
            participant.setParticipantMultiplicity(participantMultiplicity);
        } else {
            participant.setParticipantMultiplicity(null);
        }
        manageParticipantMultiplicityIcon();

    }

    /**
     * @return the minimumParticipantEmbedded
     */
    public Integer getMinimumParticipantEmbedded() {
        if (minimumParticipantEmbedded == null) {
            return 0;
        } else {
            return minimumParticipantEmbedded;
        }
    }

    /**
     * @param minimumParticipantEmbedded the minimumParticipantEmbedded to set
     */
    public void setMinimumParticipantEmbedded(Integer minimumParticipantEmbedded) {
        this.minimumParticipantEmbedded = minimumParticipantEmbedded;
        manageParticipantMultiplicity();
    }

    /**
     * @return the maximumParticipantEmbedded
     */
    public Integer getMaximumParticipantEmbedded() {
        if (maximumParticipantEmbedded == null) {
            return 1;
        } else {
            return maximumParticipantEmbedded;
        }
    }

    /**
     * @param maximumParticipantEmbedded the maximumParticipantEmbedded to set
     */
    public void setMaximumParticipantEmbedded(Integer maximumParticipantEmbedded) {
        this.maximumParticipantEmbedded = maximumParticipantEmbedded;
        manageParticipantMultiplicity();
    }
}
