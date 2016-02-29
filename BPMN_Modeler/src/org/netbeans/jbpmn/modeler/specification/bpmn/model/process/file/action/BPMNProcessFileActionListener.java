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
package org.netbeans.jbpmn.modeler.specification.bpmn.model.process.file.action;

import org.netbeans.jbpmn.modeler.specification.bpmn.BPMNSpecification;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.BPMNProcessDiagramModel;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.component.BPMNProcessComponent;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.engine.BPMNProcessDiagramEngine;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.file.BPMNProcessFileDataObject;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.scene.BPMNProcessScene;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.util.BPMNProcessUtil;
import org.netbeans.jbpmn.modeler.widget.connection.relation.RelationValidator;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.specification.annotaton.DiagramModel;
import org.netbeans.modeler.specification.annotaton.ModelerConfig;
import org.netbeans.modeler.specification.annotaton.Vendor;
import org.netbeans.modeler.specification.model.ModelerSpecificationDiagramModel;
import org.netbeans.modeler.specification.model.file.action.ModelerFileActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Build",
        id = "bpmn.file.BPMNProcessFileActionListener")
@ActionRegistration(
        displayName = "#CTL_BPMNProcessFileActionListener")
@ActionReference(path = "Loaders/text/bpmn+xml/Actions", position = -100, separatorBefore = -150, separatorAfter = -50)
@Messages("CTL_BPMNProcessFileActionListener=Edit in Modeler")
@ModelerConfig(palette = "org/netbeans/jbpmn/resource/document/ProcessPaletteConfig.xml",
        document = "org/netbeans/jbpmn/resource/document/BPMNDocumentConfig.xml",
        element = "org/netbeans/jbpmn/resource/document/ElementConfig.xml")
@Vendor(id = "BPMN", version = 2.0F, name = "BPMN", displayName = "BPMN 2.0 Standard")
@DiagramModel(id = "Process", name = "Process")
public class BPMNProcessFileActionListener extends ModelerFileActionListener {

    public BPMNProcessFileActionListener(BPMNProcessFileDataObject context) {
        super(context);
    }

    @Override
    public void initSpecification(ModelerFile modelerFile) {
        modelerFile.setModelerVendorSpecification(new BPMNSpecification());
        ModelerSpecificationDiagramModel diagramModel = new BPMNProcessDiagramModel();
        modelerFile.getVendorSpecification().setModelerSpecificationDiagramModel(diagramModel);

        diagramModel.setModelerUtil(new BPMNProcessUtil());
        diagramModel.setModelerDiagramEngine(new BPMNProcessDiagramEngine());
        diagramModel.setModelerScene(new BPMNProcessScene());
        diagramModel.setModelerPanelTopComponent(new BPMNProcessComponent());
        diagramModel.setRelationValidator(new RelationValidator());

    }
}
