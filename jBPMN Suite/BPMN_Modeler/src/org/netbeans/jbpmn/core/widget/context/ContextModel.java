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
package org.netbeans.jbpmn.core.widget.context;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.jbpmn.core.widget.ArtifactWidget;
import org.netbeans.jbpmn.core.widget.EndEventWidget;
import org.netbeans.jbpmn.core.widget.SubProcessWidget;
import org.netbeans.jbpmn.core.widget.conversation.CollaborationNodeWidget;
import org.netbeans.modeler.config.palette.CategoryNodeConfig;
import org.netbeans.modeler.config.palette.IPaletteConfig;
import org.netbeans.modeler.config.palette.SubCategoryNodeConfig;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.scene.ModelerScene;
import org.netbeans.modeler.specification.model.document.IModelerScene;
import org.netbeans.modeler.specification.model.document.widget.IFlowNodeWidget;
import org.netbeans.modeler.widget.context.ContextActionType;
import org.netbeans.modeler.widget.context.ContextPaletteButtonModel;
import org.netbeans.modeler.widget.context.ContextPaletteModel;
import org.netbeans.modeler.widget.context.action.ConnectAction;
import org.netbeans.modeler.widget.context.action.ContextPaletteConnectDecorator;
import org.netbeans.modeler.widget.context.action.SceneConnectProvider;
import org.netbeans.modeler.widget.context.base.DefaultContextPaletteModel;
import org.netbeans.modeler.widget.context.base.DefaultPaletteButtonModel;
import org.netbeans.modeler.widget.node.INodeWidget;
import org.netbeans.modeler.widget.node.NodeWidget;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.openide.util.Utilities;

public class ContextModel {

    public static ContextPaletteModel getContextPaletteModel(INodeWidget nodeWidget) {
        ContextPaletteModel contextPaletteModel = new DefaultContextPaletteModel(nodeWidget);

        ContextPaletteButtonModel connectionModel = new DefaultPaletteButtonModel();
        connectionModel.setId("DEFAULT_CONNECTION");
        connectionModel.setImage(Utilities.loadImage("org/netbeans/jbpmn/resource/context/EDGE.png"));
        connectionModel.setTooltip("Edge");
        connectionModel.setPaletteModel(contextPaletteModel);
        connectionModel.setContextActionType(ContextActionType.CONNECT);
        connectionModel.setWidgetActions(getConnectActions(nodeWidget.getModelerScene(), connectionModel.getId()));
        contextPaletteModel.getChildren().add(connectionModel);

        if (!(nodeWidget instanceof EndEventWidget) && !(nodeWidget instanceof ArtifactWidget)) {
            ContextPaletteButtonModel newWidgetModel = new DefaultPaletteButtonModel();
            contextPaletteModel.getChildren().add(newWidgetModel);
            newWidgetModel.setImage(Utilities.loadImage("org/netbeans/jbpmn/resource/context/ADD.png"));
            newWidgetModel.setTooltip("Add New");
            newWidgetModel.setPaletteModel(contextPaletteModel);
            newWidgetModel.setMouseListener(getAddWidgetAction(nodeWidget));
        }

        if (!(nodeWidget instanceof EndEventWidget) && !(nodeWidget instanceof SubProcessWidget)
                && !(nodeWidget instanceof ArtifactWidget) && !(nodeWidget instanceof CollaborationNodeWidget)) {
            ContextPaletteButtonModel morphWidgetModel = new DefaultPaletteButtonModel();
            contextPaletteModel.getChildren().add(morphWidgetModel);
            morphWidgetModel.setImage(Utilities.loadImage("org/netbeans/jbpmn/resource/context/TRANSFORM_SHAPE.png"));
            morphWidgetModel.setTooltip("Transform Shape");
            morphWidgetModel.setPaletteModel(contextPaletteModel);
            morphWidgetModel.setMouseListener(getTransformWidgetAction(nodeWidget));
        }
        ContextPaletteButtonModel deleteModel = new DefaultPaletteButtonModel();
        contextPaletteModel.getChildren().add(deleteModel);
        deleteModel.setImage(Utilities.loadImage("org/netbeans/jbpmn/resource/context/DELETE.png"));
        deleteModel.setTooltip("Delete");
        deleteModel.setPaletteModel(contextPaletteModel);
        deleteModel.setMouseListener(getRemoveWidgetAction(nodeWidget));
        return contextPaletteModel;
    }

    public static WidgetAction[] getConnectActions(IModelerScene scene, String connectionType) {
        WidgetAction[] retVal = new WidgetAction[0];
        SceneConnectProvider connector = new SceneConnectProvider(connectionType);
        LayerWidget layer = scene.getInterractionLayer();
        WidgetAction action = new ConnectAction(new ContextPaletteConnectDecorator(), layer, connector);
        retVal = new WidgetAction[]{action};
        return retVal;
    }

    public static MouseListener getTransformWidgetAction(final INodeWidget widget) {

        return new java.awt.event.MouseAdapter() {
            javax.swing.JPopupMenu addWidgetPopupMenu;

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (addWidgetPopupMenu == null) {
                    addWidgetPopupMenu = getTransformWidgetPopup(widget);
                }
                Rectangle rec = evt.getComponent().getBounds();
                addWidgetPopupMenu.show(evt.getComponent(), (int) (rec.getX() + rec.getWidth()), 0);
            }
        };
    }

    static JPopupMenu getTransformWidgetPopup(final INodeWidget widget) {
        javax.swing.JPopupMenu addWidgetPopupMenu = new javax.swing.JPopupMenu();

        IPaletteConfig paletteConfig = widget.getModelerScene().getModelerFile().getVendorSpecification().getPaletteConfig();

        CategoryNodeConfig categoryNodeConfig = null;
        for (CategoryNodeConfig categoryNodeConfig_TMP : paletteConfig.getCategoryNodeConfigs()) {
            for (final SubCategoryNodeConfig subCategoryNodeConfig : categoryNodeConfig_TMP.getSubCategoryNodeConfigs()) {
                if (subCategoryNodeConfig.getModelerDocument().getWidget() == widget.getClass()) {
                    categoryNodeConfig = categoryNodeConfig_TMP;
                    break;
                }
            }
        }
        if (categoryNodeConfig != null) {
            for (final SubCategoryNodeConfig subCategoryNodeConfig : categoryNodeConfig.getSubCategoryNodeConfigs()) {
                if (subCategoryNodeConfig.getModelerDocument().getId().equals(widget.getNodeWidgetInfo().getModelerDocument().getId())) {
                    continue;
                }
                JMenuItem subCategoryMenuItem = new javax.swing.JMenuItem();
                subCategoryMenuItem.setIcon(new ImageIcon(subCategoryNodeConfig.getPaletteDocument().getPaletteSmallIcon(22, 22)));
//                subCategoryMenuItem.setText(subCategoryNodeConfig.getBPMNDocument().getName());
                subCategoryMenuItem.setToolTipText(subCategoryNodeConfig.getName());
                subCategoryMenuItem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                subCategoryMenuItem.setIconTextGap(0);
                subCategoryMenuItem.setOpaque(true);
                subCategoryMenuItem.setMargin(new java.awt.Insets(-2, -3, 0, 0));
                subCategoryMenuItem.setPreferredSize(new java.awt.Dimension(22, 25));
                subCategoryMenuItem.setContentAreaFilled(false);
                subCategoryMenuItem.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        widget.getModelerScene().getModelerFile().getModelerUtil().transformNode((IFlowNodeWidget) widget, subCategoryNodeConfig.getModelerDocument());
                        widget.getModelerScene().getModelerPanelTopComponent().changePersistenceState(false);
                        NBModelerUtil.hideContextPalette(widget.getModelerScene());

                    }
                });
                addWidgetPopupMenu.add(subCategoryMenuItem);

            }
        }
        return addWidgetPopupMenu;

    }

    public static MouseListener getRemoveWidgetAction(final INodeWidget widget) {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                widget.remove(true);
                NBModelerUtil.hideContextPalette(widget.getModelerScene());
                widget.getModelerScene().getModelerPanelTopComponent().changePersistenceState(false);
            }
        };
    }
//    private static javax.swing.JPopupMenu addWidgetPopupMenu;

    static JPopupMenu getAddWidgetPopup(final INodeWidget widget) {
        javax.swing.JPopupMenu addWidgetPopupMenu = new javax.swing.JPopupMenu();
        IPaletteConfig paletteConfig = widget.getModelerScene().getModelerFile().getVendorSpecification().getPaletteConfig();//PaletteConfigRepository.getDiagramModelPaletteConfig(BPMNProcessFileDataObject.class);
        for (CategoryNodeConfig categoryNodeConfig : paletteConfig.getCategoryNodeConfigs()) {
            String categoryName = categoryNodeConfig.getName();
            if (categoryNodeConfig.getId().equals("CONTAINER")
                    || categoryNodeConfig.getId().equals("ARTIFACT")
                    || categoryNodeConfig.getId().equals("BOUNDARY_EVENT")
                    || categoryNodeConfig.getId().equals("START_EVENT")) {
                continue;
            }
            JMenu categoryMenuItem = null;
            Image img = categoryNodeConfig.getIcon(20, 20);//if icon not exist then dont create parent icon , subicon added to parent icon place
            if (img != null) {
                categoryMenuItem = new javax.swing.JMenu();
                categoryMenuItem.setIcon(new ImageIcon(img));
                categoryMenuItem.setToolTipText(categoryName);
                categoryMenuItem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                categoryMenuItem.setIconTextGap(0);
                categoryMenuItem.setOpaque(true);
                categoryMenuItem.setMargin(new java.awt.Insets(0, -3, 0, -3));
                categoryMenuItem.setPreferredSize(new java.awt.Dimension(24, 23));
            }
            for (final SubCategoryNodeConfig subCategoryNodeConfig : categoryNodeConfig.getSubCategoryNodeConfigs()) {
                JMenuItem subCategoryMenuItem = new javax.swing.JMenuItem();
                subCategoryMenuItem.setIcon(new ImageIcon(subCategoryNodeConfig.getPaletteDocument().getPaletteSmallIcon(22, 22)));
//                subCategoryMenuItem.setText(subCategoryNodeConfig.getBPMNDocument().getName());
                subCategoryMenuItem.setToolTipText(subCategoryNodeConfig.getName());
                subCategoryMenuItem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                subCategoryMenuItem.setIconTextGap(0);
                subCategoryMenuItem.setOpaque(true);
                subCategoryMenuItem.setMargin(new java.awt.Insets(-2, -3, 0, 0));
                subCategoryMenuItem.setPreferredSize(new java.awt.Dimension(22, 25));
                subCategoryMenuItem.setContentAreaFilled(false);

                subCategoryMenuItem.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {

                        NodeWidget nodeWidget = (NodeWidget) widget;

                        Rectangle rec = nodeWidget.getSceneViewBound();

                        Point point = new Point((int) (rec.getX() + rec.getWidth() + 75), (int) (rec.getY()));
                        INodeWidget new_nodewidget = widget.getModelerScene().createNodeWidget(
                                new NodeWidgetInfo("_" + NBModelerUtil.getAutoGeneratedId().toString(), subCategoryNodeConfig, point));
                        Rectangle new_rec = new_nodewidget.getSceneViewBound();

                        point = new Point((int) point.getX(), (int) (point.getY() + (rec.getHeight() - new_rec.getHeight()) / 2));

                        //   getSceneAnimator().animatePreferredLocation(w, widget.convertLocalToScene(point));
                        new_nodewidget.setPreferredLocation(point);

                        SceneConnectProvider connectProvider = new SceneConnectProvider(null);// ModelerUtil.getEdgeType() will decide EdgeType
                        connectProvider.createConnection((ModelerScene) nodeWidget.getModelerScene(), nodeWidget, (NodeWidget) new_nodewidget);

//                        if (nodeWidget instanceof FlowNodeWidget) {
//                            FlowNodeWidget flowNodeWidget = (FlowNodeWidget) nodeWidget;
//                            if (flowNodeWidget.getFlowElementsContainer() instanceof IModelerSubScene) {
//                                ((SubProcessWidget) flowNodeWidget.getFlowElementsContainer()).moveFlowNodeWidget((FlowNodeWidget) new_nodewidget);
//                            }
//                        }
                        widget.getModelerScene().getModelerPanelTopComponent().changePersistenceState(false);

                    }
                });
                if (categoryMenuItem != null) {
                    categoryMenuItem.add(subCategoryMenuItem);
                } else {
                    addWidgetPopupMenu.add(subCategoryMenuItem);
                }

            }
            if (categoryMenuItem != null) {
                addWidgetPopupMenu.add(categoryMenuItem);
            }
        }
        return addWidgetPopupMenu;

    }

    public static MouseListener getAddWidgetAction(final INodeWidget widget) {

        return new java.awt.event.MouseAdapter() {
            javax.swing.JPopupMenu addWidgetPopupMenu;

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (addWidgetPopupMenu == null) {
                    addWidgetPopupMenu = getAddWidgetPopup(widget);
                }
                Rectangle rec = evt.getComponent().getBounds();
                addWidgetPopupMenu.show(evt.getComponent(), (int) (rec.getX() + rec.getWidth()), 0);
            }
        };
    }
}
