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
package org.netbeans.jbpmn.modeler.specification.bpmn.model.conversation.file.action;

import org.netbeans.jbpmn.modeler.specification.bpmn.BPMNSpecification;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.conversation.BPMNConversationDiagramModel;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.conversation.component.BPMNConversationComponent;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.conversation.engine.BPMNConversationDiagramEngine;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.conversation.file.BPMNConversationFileDataObject;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.conversation.scene.BPMNConversationScene;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.conversation.util.BPMNConversationUtil;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.file.action.BPMNProcessFileActionListener;
import org.netbeans.jbpmn.modeler.widget.connection.relation.RelationValidator;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.specification.Vendor;
import org.netbeans.modeler.specification.annotaton.ModelerConfig;
import org.netbeans.modeler.specification.model.DiagramModel;
import org.netbeans.modeler.specification.model.ModelerSpecificationDiagramModel;
import org.netbeans.modeler.specification.model.file.action.ModelerFileActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Build",
        id = "bpmn.file.BPMNConversationFileActionListener")
@ActionRegistration(
        displayName = "#CTL_BPMNConversationFileActionListener")
@ActionReference(path = "Loaders/text/conv+xml/Actions", position = -100, separatorBefore = -150, separatorAfter = -50)
@Messages("CTL_BPMNConversationFileActionListener=Edit in Modeler")
@ModelerConfig(palette = "org/netbeans/jbpmn/resource/document/ConversationPaletteConfig.xml",
        document = "org/netbeans/jbpmn/resource/document/BPMNDocumentConfig.xml",
        element = "org/netbeans/jbpmn/resource/document/ElementConfig.xml")
@org.netbeans.modeler.specification.annotaton.Vendor(id = "BPMN", version = 2.0F, name = "BPMN", displayName = "BPMN 2.0 Standard")
@org.netbeans.modeler.specification.annotaton.DiagramModel(id = "Conversation", name = "Conversation")
public final class BPMNConversationFileActionListener extends ModelerFileActionListener {

    public BPMNConversationFileActionListener(BPMNConversationFileDataObject context) {
        super(context);
    }

    @Override
    protected void initSpecification(ModelerFile modelerFile) {
        modelerFile.setModelerVendorSpecification(new BPMNSpecification());

        ModelerSpecificationDiagramModel diagramModel = new BPMNConversationDiagramModel();
        modelerFile.getVendorSpecification().setModelerSpecificationDiagramModel(diagramModel);

        diagramModel.setModelerUtil(new BPMNConversationUtil());
        diagramModel.setModelerDiagramEngine(new BPMNConversationDiagramEngine());
        diagramModel.setModelerScene(new BPMNConversationScene());
        diagramModel.setModelerPanelTopComponent(new BPMNConversationComponent());
        diagramModel.setRelationValidator(new RelationValidator());

    }
}
