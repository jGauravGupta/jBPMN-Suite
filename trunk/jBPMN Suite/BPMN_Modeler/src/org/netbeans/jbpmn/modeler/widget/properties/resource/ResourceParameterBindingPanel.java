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
package org.netbeans.jbpmn.modeler.widget.properties.resource;

import java.awt.Frame;
import javax.swing.JOptionPane;
import org.netbeans.jbpmn.modeler.specification.bpmn.util.BPMNModelUtil;
import org.netbeans.jbpmn.modeler.widget.properties.itemdefinition.ItemDefinitionPanel;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TFormalExpression;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TResource;
import org.netbeans.jbpmn.spec.TResourceParameter;
import org.netbeans.jbpmn.spec.TResourceParameterBinding;
import org.netbeans.jbpmn.spec.extend.LanguageManager;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.Entity;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.RowValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;

public class ResourceParameterBindingPanel extends EntityComponent {

    private ModelerFile file;
    private TDefinitions definition;
    String originalId;
    private Boolean isNew;
    private String actionPanelType;
    private ResourceRoleDialog resourceRoleDialog;
    TResourceParameterBinding resourceParameterBinding;

    /**
     * Creates new form NewJDialog
     */
    public ResourceParameterBindingPanel(ModelerFile file, ResourceRoleDialog resourceRoleDialog) {
        super((Frame) null, true);
        this.file = file;
        definition = (TDefinitions) file.getDefinitionElement();
        this.resourceRoleDialog = resourceRoleDialog;
        initComponents();

        language_ComboBox.setEditable(true);

    }

    @Override
    public void init() {
        initScriptTypeComboBox();
        initEvaluatesToTypeComboBox();
        initParameterList();
        parameter_ComboBox.setSelectedItem(new ComboBoxValue<TResourceParameter>(null, null, ""));
        language_ComboBox.setSelectedItem(LanguageManager.getLanguage(null));
        evaluatesToType_ComboBox.setSelectedItem(new ComboBoxValue<TItemDefinition>(null, null, ""));
    }

    @Override
    public void createEntity(Class/*<? extends Entity>*/ entityWrapperType) {
        this.setTitle("Add New Resource Parameter Binding");
        isNew = true;
        if (entityWrapperType == RowValue.class) {
            this.setEntity(new RowValue(new Object[5]));
        }
        script_TextArea.setText("");

    }

    @Override
    public void updateEntity(Entity entityValue) {
        this.setTitle("Update Resource Parameter Binding");
        isNew = false;
        if (entityValue.getClass() == RowValue.class) {
            this.setEntity(entityValue);
            Object[] row = ((RowValue) entityValue).getRow();
            resourceParameterBinding = (TResourceParameterBinding) row[0];
            for (int i = 0; i < parameter_ComboBox.getItemCount(); i++) {
                ComboBoxValue<TResourceParameter> resourceParameter_ComboBoxValue = (ComboBoxValue<TResourceParameter>) parameter_ComboBox.getItemAt(i);
                if (resourceParameter_ComboBoxValue.getValue() != null && resourceParameter_ComboBoxValue.getValue().getId().equals(resourceParameterBinding.getParameterRef())) {
                    parameter_ComboBox.setSelectedItem(resourceParameter_ComboBoxValue);
                    break;
                }
            }

            language_ComboBox.setSelectedItem(LanguageManager.getLanguage(resourceParameterBinding.getExpression().getLanguage()));//(ComboBoxValue<String>) row[3]);//(LanguageManager.getLanguage((String) row[3]));
            script_TextArea.setText(resourceParameterBinding.getExpression().getContent());

            for (int i = 0; i < evaluatesToType_ComboBox.getItemCount(); i++) {
                ComboBoxValue<TItemDefinition> itemDefinition = (ComboBoxValue<TItemDefinition>) evaluatesToType_ComboBox.getItemAt(i);
                if (itemDefinition.getValue() != null && itemDefinition.getValue().getId().equals((String) row[4])) {//BUG : remove row[4] use object
                    evaluatesToType_ComboBox.setSelectedItem(itemDefinition);
                    break;
                }
            }
        }

    }

    private void initParameterList() {
        parameter_ComboBox.removeAllItems();
        TResource resource = (TResource) ((ComboBoxValue<TResource>) resourceRoleDialog.getResource_ComboBox().getSelectedItem()).getValue();
//        TResource resource = (TResource) definition.getRootElement(resourceRole.getResourceRef(), TResource.class);
        if (resource != null) {
            parameter_ComboBox.addItem(new ComboBoxValue<TResourceParameter>(null, null, ""));
            for (TResourceParameter resourceParameter : resource.getResourceParameter()) {
                parameter_ComboBox.addItem(new ComboBoxValue<TResourceParameter>(resourceParameter.getId(), resourceParameter, resourceParameter.getDisplayValue()));
            }
        }

    }

    private void initScriptTypeComboBox() {
        language_ComboBox.removeAllItems();
        for (ComboBoxValue<String> language : LanguageManager.getLanguageList()) {
            language_ComboBox.addItem(language);
        }
    }

    private void initEvaluatesToTypeComboBox() {
        evaluatesToType_ComboBox.removeAllItems();
        for (ComboBoxValue<TItemDefinition> itemDefinition : BPMNModelUtil.getItemDefinitionList(file)) {
            evaluatesToType_ComboBox.addItem(itemDefinition);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setting_PopupMenu = new javax.swing.JPopupMenu();
        createItem_MenuItem = new javax.swing.JMenuItem();
        editItem_MenuItem = new javax.swing.JMenuItem();
        deleteItem_MenuItem = new javax.swing.JMenuItem();
        wrapper_LayeredPane = new javax.swing.JLayeredPane();
        parameter_LayeredPane = new javax.swing.JLayeredPane();
        parameter_Label = new javax.swing.JLabel();
        parameter_ComboBox = new javax.swing.JComboBox();
        expression_LayeredPane = new javax.swing.JLayeredPane();
        language_LayeredPane = new javax.swing.JLayeredPane();
        language_Label = new javax.swing.JLabel();
        language_ComboBox = new javax.swing.JComboBox();
        script_LayeredPane = new javax.swing.JLayeredPane();
        script_Label = new javax.swing.JLabel();
        script_ScrollPane = new javax.swing.JScrollPane();
        script_TextArea = new javax.swing.JTextArea();
        evaluateType_LayeredPane = new javax.swing.JLayeredPane();
        evaluatesToType_Label = new javax.swing.JLabel();
        evaluatesToType_ComboBox = new javax.swing.JComboBox();
        evaluatesToType_Action = new javax.swing.JButton();
        action_LayeredPane = new javax.swing.JLayeredPane();
        save_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();

        createItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/icon_plus.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(createItem_MenuItem, org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.createItem_MenuItem.text")); // NOI18N
        createItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.createItem_MenuItem.toolTipText")); // NOI18N
        createItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(createItem_MenuItem);

        editItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/edit.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(editItem_MenuItem, org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.editItem_MenuItem.text")); // NOI18N
        editItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.editItem_MenuItem.toolTipText")); // NOI18N
        editItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(editItem_MenuItem);

        deleteItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/delete.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(deleteItem_MenuItem, org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.deleteItem_MenuItem.text")); // NOI18N
        deleteItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.deleteItem_MenuItem.toolTipText")); // NOI18N
        deleteItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(deleteItem_MenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(525, 425));
        getContentPane().setLayout(null);

        wrapper_LayeredPane.setPreferredSize(new java.awt.Dimension(500, 360));

        parameter_LayeredPane.setPreferredSize(new java.awt.Dimension(460, 30));

        org.openide.awt.Mnemonics.setLocalizedText(parameter_Label, org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.parameter_Label.text")); // NOI18N
        parameter_Label.setPreferredSize(new java.awt.Dimension(30, 14));

        javax.swing.GroupLayout parameter_LayeredPaneLayout = new javax.swing.GroupLayout(parameter_LayeredPane);
        parameter_LayeredPane.setLayout(parameter_LayeredPaneLayout);
        parameter_LayeredPaneLayout.setHorizontalGroup(
            parameter_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, parameter_LayeredPaneLayout.createSequentialGroup()
                .addComponent(parameter_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parameter_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        parameter_LayeredPaneLayout.setVerticalGroup(
            parameter_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parameter_LayeredPaneLayout.createSequentialGroup()
                .addGroup(parameter_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(parameter_Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(parameter_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        parameter_LayeredPane.setLayer(parameter_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        parameter_LayeredPane.setLayer(parameter_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(language_Label, org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.language_Label.text")); // NOI18N
        language_Label.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.language_Label.toolTipText")); // NOI18N

        javax.swing.GroupLayout language_LayeredPaneLayout = new javax.swing.GroupLayout(language_LayeredPane);
        language_LayeredPane.setLayout(language_LayeredPaneLayout);
        language_LayeredPaneLayout.setHorizontalGroup(
            language_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(language_LayeredPaneLayout.createSequentialGroup()
                .addComponent(language_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(language_ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        language_LayeredPaneLayout.setVerticalGroup(
            language_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(language_LayeredPaneLayout.createSequentialGroup()
                .addGroup(language_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(language_Label)
                    .addComponent(language_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );
        language_LayeredPane.setLayer(language_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        language_LayeredPane.setLayer(language_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(script_Label, org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.script_Label.text")); // NOI18N
        script_Label.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.script_Label.toolTipText")); // NOI18N

        script_TextArea.setColumns(20);
        script_TextArea.setRows(5);
        script_ScrollPane.setViewportView(script_TextArea);

        javax.swing.GroupLayout script_LayeredPaneLayout = new javax.swing.GroupLayout(script_LayeredPane);
        script_LayeredPane.setLayout(script_LayeredPaneLayout);
        script_LayeredPaneLayout.setHorizontalGroup(
            script_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(script_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(script_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
        );
        script_LayeredPaneLayout.setVerticalGroup(
            script_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(script_Label)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(script_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        script_LayeredPane.setLayer(script_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        script_LayeredPane.setLayer(script_ScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(evaluatesToType_Label, org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.evaluatesToType_Label.text")); // NOI18N

        evaluatesToType_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        evaluatesToType_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                evaluatesToType_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout evaluateType_LayeredPaneLayout = new javax.swing.GroupLayout(evaluateType_LayeredPane);
        evaluateType_LayeredPane.setLayout(evaluateType_LayeredPaneLayout);
        evaluateType_LayeredPaneLayout.setHorizontalGroup(
            evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(evaluateType_LayeredPaneLayout.createSequentialGroup()
                .addComponent(evaluatesToType_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(evaluatesToType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(evaluatesToType_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        evaluateType_LayeredPaneLayout.setVerticalGroup(
            evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(evaluateType_LayeredPaneLayout.createSequentialGroup()
                .addGroup(evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(evaluatesToType_Label)
                        .addComponent(evaluatesToType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(evaluatesToType_Action))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        evaluateType_LayeredPane.setLayer(evaluatesToType_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        evaluateType_LayeredPane.setLayer(evaluatesToType_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        evaluateType_LayeredPane.setLayer(evaluatesToType_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout expression_LayeredPaneLayout = new javax.swing.GroupLayout(expression_LayeredPane);
        expression_LayeredPane.setLayout(expression_LayeredPaneLayout);
        expression_LayeredPaneLayout.setHorizontalGroup(
            expression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expression_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(evaluateType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(expression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(expression_LayeredPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(expression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(script_LayeredPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(language_LayeredPane, javax.swing.GroupLayout.Alignment.LEADING))
                    .addContainerGap(39, Short.MAX_VALUE)))
        );
        expression_LayeredPaneLayout.setVerticalGroup(
            expression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expression_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(236, Short.MAX_VALUE)
                .addComponent(evaluateType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(expression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(expression_LayeredPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(language_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(script_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(61, Short.MAX_VALUE)))
        );
        expression_LayeredPane.setLayer(language_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        expression_LayeredPane.setLayer(script_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        expression_LayeredPane.setLayer(evaluateType_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(save_Button, org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.save_Button.text")); // NOI18N
        save_Button.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.save_Button.toolTipText")); // NOI18N
        save_Button.setLabel(org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.save_Button.label")); // NOI18N
        save_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_ButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(cancel_Button, org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.cancel_Button.text")); // NOI18N
        cancel_Button.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.cancel_Button.toolTipText")); // NOI18N
        cancel_Button.setLabel(org.openide.util.NbBundle.getMessage(ResourceParameterBindingPanel.class, "ResourceParameterBindingPanel.cancel_Button.label")); // NOI18N
        cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout action_LayeredPaneLayout = new javax.swing.GroupLayout(action_LayeredPane);
        action_LayeredPane.setLayout(action_LayeredPaneLayout);
        action_LayeredPaneLayout.setHorizontalGroup(
            action_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, action_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(320, Short.MAX_VALUE)
                .addComponent(save_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancel_Button)
                .addGap(2, 2, 2))
        );
        action_LayeredPaneLayout.setVerticalGroup(
            action_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(action_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(save_Button)
                .addComponent(cancel_Button))
        );
        action_LayeredPane.setLayer(save_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);
        action_LayeredPane.setLayer(cancel_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout wrapper_LayeredPaneLayout = new javax.swing.GroupLayout(wrapper_LayeredPane);
        wrapper_LayeredPane.setLayout(wrapper_LayeredPaneLayout);
        wrapper_LayeredPaneLayout.setHorizontalGroup(
            wrapper_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wrapper_LayeredPaneLayout.createSequentialGroup()
                .addGroup(wrapper_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(wrapper_LayeredPaneLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(action_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wrapper_LayeredPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(expression_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(wrapper_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(parameter_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        wrapper_LayeredPaneLayout.setVerticalGroup(
            wrapper_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wrapper_LayeredPaneLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(parameter_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expression_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(action_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        wrapper_LayeredPane.setLayer(parameter_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        wrapper_LayeredPane.setLayer(expression_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        wrapper_LayeredPane.setLayer(action_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(wrapper_LayeredPane);
        wrapper_LayeredPane.setBounds(0, 0, 525, 381);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean validateField() {

        ComboBoxValue<TResourceParameter> resourceParameterItem = (ComboBoxValue<TResourceParameter>) parameter_ComboBox.getSelectedItem();
        if (resourceParameterItem == null) {
            JOptionPane.showMessageDialog(this, "Please add a Resource Reference to ResourceRole", "Invalid State", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n
        if (resourceParameterItem.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Please select a Resource Parameter", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n
        if (this.script_TextArea.getText().trim().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Parameter script can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n
        return true;
    }

    private void save_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_ButtonActionPerformed

        if (!validateField()) {
            return;
        }

        RowValue rowValue = (RowValue) this.getEntity();
        Object[] row = rowValue.getRow();
        if (isNew) {
            resourceParameterBinding = new TResourceParameterBinding();
            resourceParameterBinding.setId(NBModelerUtil.getAutoGeneratedStringId());
        }
        resourceParameterBinding.setParameterRef(((ComboBoxValue<TResourceParameter>) parameter_ComboBox.getSelectedItem()).getId());
        row[0] = resourceParameterBinding;
        row[1] = ((ComboBoxValue<TResourceParameter>) parameter_ComboBox.getSelectedItem()).getDisplayValue();

        if (resourceParameterBinding.getExpression() == null) {
            TFormalExpression expression = new TFormalExpression();
            resourceParameterBinding.setExpression(expression);
        }

        ComboBoxValue<String> language_comboBoxValue;
        if (language_ComboBox.getSelectedItem() instanceof String) {
            language_comboBoxValue = new ComboBoxValue<String>((String) language_ComboBox.getSelectedItem(), (String) language_ComboBox.getSelectedItem());
        } else {
            language_comboBoxValue = (ComboBoxValue<String>) language_ComboBox.getSelectedItem();
        }
        resourceParameterBinding.getExpression().setLanguage(language_comboBoxValue.getValue());
        row[2] = language_comboBoxValue;

        resourceParameterBinding.getExpression().setContent(script_TextArea.getText().trim());
        row[3] = script_TextArea.getText().trim();

        resourceParameterBinding.getExpression().setEvaluatesToTypeRef(((ComboBoxValue<TItemDefinition>) evaluatesToType_ComboBox.getSelectedItem()).getId());
        row[4] = ((ComboBoxValue<TItemDefinition>) evaluatesToType_ComboBox.getSelectedItem()).getDisplayValue();//evaluatesToType_TextField.getText().trim();

        saveActionPerformed(evt);
    }//GEN-LAST:event_save_ButtonActionPerformed

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        cancelActionPerformed(evt);
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    private void evaluatesToType_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_evaluatesToType_ActionMousePressed
        actionPanelType = "evaluatesToType";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_evaluatesToType_ActionMousePressed

    EntityComponent getActionPanel() {
        if ("evaluatesToType".equals(actionPanelType)) {
            return new ItemDefinitionPanel(file);
        } else {
            return null;
        }
    }

    ComboBoxValue getSelectedActionItem() {
        if ("evaluatesToType".equals(actionPanelType)) {
            return (ComboBoxValue) evaluatesToType_ComboBox.getSelectedItem();
        } else {
            return null;
        }
    }

    private void createItem_MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createItem_MenuItemActionPerformed
        EntityComponent itemComponent = getActionPanel();// actionHandler.getItemPanel();

        itemComponent.init();
        itemComponent.createEntity(ComboBoxValue.class);
        itemComponent.setVisible(true);

        if (itemComponent.getDialogResult() == javax.swing.JOptionPane.OK_OPTION) {
            ComboBoxValue comboBoxValue = (ComboBoxValue) itemComponent.getEntity();

            if ("evaluatesToType".equals(actionPanelType)) {
                evaluatesToType_ComboBox.addItem(comboBoxValue);
                evaluatesToType_ComboBox.setSelectedItem(comboBoxValue);
                definition.addItemDefinition((TItemDefinition) comboBoxValue.getValue());
            }

        }
    }//GEN-LAST:event_createItem_MenuItemActionPerformed

    private void editItem_MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editItem_MenuItemActionPerformed
        ComboBoxValue comboBoxValue;
        try {
            comboBoxValue = getSelectedActionItem();// = (ComboBoxValue) interface_ComboBox.getSelectedItem();

            if (comboBoxValue == null) {
                JOptionPane.showMessageDialog(null, "No element selected !", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            EntityComponent itemComponent = getActionPanel();
            itemComponent.init();
            itemComponent.updateEntity(comboBoxValue);
            itemComponent.setVisible(true);
        } catch (IllegalStateException ex) {
            System.out.println("EX : " + ex.toString());
        }
    }//GEN-LAST:event_editItem_MenuItemActionPerformed

    private void deleteItem_MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteItem_MenuItemActionPerformed
        ComboBoxValue comboBoxValue;
        try {
            comboBoxValue = getSelectedActionItem();// = (ComboBoxValue) interface_ComboBox.getSelectedItem();

            if (comboBoxValue == null) {
                JOptionPane.showMessageDialog(null, "No element selected !", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if ("evaluatesToType".equals(actionPanelType)) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Data Type ?", "Delete Data Type", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    definition.removeRootElement((TItemDefinition) comboBoxValue.getValue());
                    evaluatesToType_ComboBox.removeItem(comboBoxValue);
                }
            }
        } catch (IllegalStateException ex) {
            System.out.println("EX : " + ex.toString());
        }
    }//GEN-LAST:event_deleteItem_MenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane action_LayeredPane;
    protected javax.swing.JButton cancel_Button;
    private javax.swing.JMenuItem createItem_MenuItem;
    private javax.swing.JMenuItem deleteItem_MenuItem;
    private javax.swing.JMenuItem editItem_MenuItem;
    private javax.swing.JLayeredPane evaluateType_LayeredPane;
    private javax.swing.JButton evaluatesToType_Action;
    private javax.swing.JComboBox evaluatesToType_ComboBox;
    protected javax.swing.JLabel evaluatesToType_Label;
    private javax.swing.JLayeredPane expression_LayeredPane;
    private javax.swing.JComboBox language_ComboBox;
    protected javax.swing.JLabel language_Label;
    private javax.swing.JLayeredPane language_LayeredPane;
    private javax.swing.JComboBox parameter_ComboBox;
    protected javax.swing.JLabel parameter_Label;
    private javax.swing.JLayeredPane parameter_LayeredPane;
    protected javax.swing.JButton save_Button;
    protected javax.swing.JLabel script_Label;
    private javax.swing.JLayeredPane script_LayeredPane;
    private javax.swing.JScrollPane script_ScrollPane;
    private javax.swing.JTextArea script_TextArea;
    private javax.swing.JPopupMenu setting_PopupMenu;
    private javax.swing.JLayeredPane wrapper_LayeredPane;
    // End of variables declaration//GEN-END:variables
}
