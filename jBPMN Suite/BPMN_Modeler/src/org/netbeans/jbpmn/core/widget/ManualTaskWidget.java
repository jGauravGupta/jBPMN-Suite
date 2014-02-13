/** Copyright [2014] Gaurav Gupta
   *
   *Licensed under the Apache License, Version 2.0 (the "License");
   *you may not use this file except in compliance with the License.
   *You may obtain a copy of the License at
   *
   *    http://www.apache.org/licenses/LICENSE-2.0
   *
   *Unless required by applicable law or agreed to in writing, software
   *distributed under the License is distributed on an "AS IS" BASIS,
   *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   *See the License for the specific language governing permissions and
   *limitations under the License.
   */
 package org.netbeans.jbpmn.core.widget;

import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;

/**
 *
 * 
 *  
 *    
 */

public class ManualTaskWidget extends TaskWidget {
  public ManualTaskWidget(IModelerScene scene,  NodeWidgetInfo node) {
        super(scene, node);
//              this.getNodeImageWidget().setResizeHandler(new SVGResizeHandler() {
//            @Override
//            public void resizing(SVGDocument svgDocument, ResizeType resizeType, Double documentWidth, Double documentHeight, Double scaleX, Double scaleY) {
//                  resizingFinished(svgDocument, resizeType, documentWidth, documentHeight, scaleX, scaleY);
//              }
//
//            @Override
//            public void resizingStarted(SVGDocument svgDocument, ResizeType resizeType, Double documentWidth, Double documentHeight, Double scaleX, Double scaleY) {
//             }
//
//            @Override
//            public void resizingFinished(SVGDocument svgDocument, ResizeType resizeType, Double documentWidth, Double documentHeight, Double scaleX, Double scaleY) {
//             if (resizeType == ResizeType.OUTER) {//for annotation
//                    
//                    String w2 = new Double(new Double(getElementValue(svgDocument, "OUTLINE", "w")) * scaleX).toString();
//                    String h2 = new Double(new Double(getElementValue(svgDocument, "OUTLINE", "h")) * scaleY).toString();
//                    ManualTaskWidget.this.setElementValue(svgDocument, "OUTLINE", "width", w2, true);
//                    ManualTaskWidget.this.setElementValue(svgDocument, "OUTLINE", "height", h2, true);
//
//                    String wr = new Double(new Double(getElementValue(svgDocument, "TEXT-REGION", "w")) * scaleX).toString();
//                    String hr = new Double(new Double(getElementValue(svgDocument, "TEXT-REGION", "h")) * scaleY).toString();
//                    ManualTaskWidget.this.setElementValue(svgDocument, "TEXT-REGION", "width", wr, true);
//                    ManualTaskWidget.this.setElementValue(svgDocument, "TEXT-REGION", "height", hr, true);
//
////                    ParticipantWidget.this.setElementValue(svgDocument, "TEXT-REGION2", "width", wr, true);
////                    ParticipantWidget.this.setElementValue(svgDocument, "TEXT-REGION2", "height", hr, true);
//
//                    String px = new Double(new Double(getElementValue(svgDocument, "TEXT-REGION", "px")) * scaleX).toString();
//                    String py = new Double(new Double(getElementValue(svgDocument, "TEXT-REGION", "py")) * scaleY).toString();
//                    ManualTaskWidget.this.setElementValue(svgDocument, "TEXT-REGION", "x", px, true);
//                    ManualTaskWidget.this.setElementValue(svgDocument, "TEXT-REGION", "y", py, true);
//
//                    svgDocument.getRootElement().setAttribute("width", new Double((documentWidth * scaleX) + 4).toString());
//                    svgDocument.getRootElement().setAttribute("height", new Double((documentHeight * scaleY) + 4).toString());
//                }
//            }
//        });
      }
   

}
