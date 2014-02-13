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
package org.netbeans.jbpmn.modeler.specification.bpmn.model.process.file.action.debug;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.file.BPMNProcessFileDataObject;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.file.action.BPMNProcessFileActionListener;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.scene.BPMNProcessScene;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.ModelerCore;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.file.ModelerFileDataObject;
import org.netbeans.modeler.specification.model.document.widget.IBaseElementWidget;
import org.netbeans.modeler.specification.model.document.widget.IFlowNodeWidget;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public class BPMNProcessFileDebugAction {

    public void openFile(String processId, Map<String, String> nodeList, File file) throws IOException {
        FileObject fileObject = FileUtil.toFileObject(file);

        String path = fileObject.getPath();
        ModelerFile modelerFile = ModelerCore.getModelerFile(path);

//        BPMNProcessFileActionListener fileActionListener = new BPMNProcessFileActionListener((BPMNProcessFileDataObject) modelerFile.getModelerFileDataObject());
//        fileActionListener.actionPerformed(null);
        if (modelerFile == null) {
            modelerFile = new ModelerFile();

            modelerFile.setModelerFileDataObject((ModelerFileDataObject) BPMNProcessFileDataObject.find(fileObject));
            modelerFile.setName(fileObject.getName());
            modelerFile.setExtension(fileObject.getExt());
            modelerFile.setPath(path);
            modelerFile.setIcon(modelerFile.getModelerFileDataObject().getIcon());

            BPMNProcessFileActionListener fileActionListener = new BPMNProcessFileActionListener((BPMNProcessFileDataObject) modelerFile.getModelerFileDataObject());
            fileActionListener.initSpecification(modelerFile);

            ModelerCore.addModelerFile(path, modelerFile);
            modelerFile.getVendorSpecification().getModelerDiagramModel().getModelerPanelTopComponent().open();
            modelerFile.getVendorSpecification().getModelerDiagramModel().getModelerPanelTopComponent().requestActive();

            NBModelerUtil.loadModelerFile(modelerFile);
        } else {
            modelerFile.getVendorSpecification().getModelerDiagramModel().getModelerPanelTopComponent().requestActive();
        }

        BPMNProcessScene scene = (BPMNProcessScene) modelerFile.getVendorSpecification().getModelerDiagramModel().getModelerScene();
        //        scene.findBaseElement(path)
        //            Iterator<String> itr =  nodeList.keySet().iterator();
        //            while(itr.hasNext()){
        //
        //            }

        for (IFlowNodeWidget nodeWidget : scene.getDebugNodeWidget()) {
            ((NodeWidget) nodeWidget).setOuterElementBorderColor(((NodeWidget) nodeWidget).getOuterElementBorderColor());
        }
        scene.getDebugNodeWidget().clear();

        for (IBaseElementWidget baseElementWidgets : scene.getBaseElements()) {
            if (nodeList.get(baseElementWidgets.getId()) != null) {

                if (baseElementWidgets instanceof IFlowNodeWidget && baseElementWidgets instanceof NodeWidget) {
                    scene.addDebugNodeWidget((IFlowNodeWidget) baseElementWidgets);
                    ((NodeWidget) baseElementWidgets).setElementValue("outer", "stroke", "RGB(195,15,15)");
                }
            }
        }
//        scene.getModelerPanelTopComponent().

    }

}
