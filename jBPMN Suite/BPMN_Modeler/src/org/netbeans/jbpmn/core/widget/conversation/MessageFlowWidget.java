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

import java.awt.BasicStroke;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.modeler.anchorshape.CircleAnchorShape;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.widget.edge.info.EdgeWidgetInfo;

public class MessageFlowWidget extends CollaborationEdgeWidget {

    public MessageFlowWidget(IModelerScene scene, EdgeWidgetInfo edge) {
        super(scene, edge);
        setStroke(new BasicStroke(1.2f,
                BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                BasicStroke.JOIN_MITER, new float[]{4, 4}, 0));
        setTargetAnchorShape(AnchorShape.TRIANGLE_HOLLOW);
        setSourceAnchorShape(new CircleAnchorShape(9, new BasicStroke(1.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)));
    }
}
