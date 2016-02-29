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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JOptionPane;
import org.netbeans.jbpmn.modeler.specification.bpmn.util.BPMNModelUtil;
import org.netbeans.modeler.properties.nentity.Column;
import org.netbeans.modeler.properties.nentity.NAttributeEntity;
import org.netbeans.modeler.properties.nentity.NEntityDataListener;
import org.netbeans.jbpmn.modeler.widget.properties.itemdefinition.ItemDefinitionPanel;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.Entity;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.RowValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TFormalExpression;
import org.netbeans.jbpmn.spec.THumanPerformer;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TPerformer;
import org.netbeans.jbpmn.spec.TPotentialOwner;
import org.netbeans.jbpmn.spec.TResource;
import org.netbeans.jbpmn.spec.TResourceAssignmentExpression;
import org.netbeans.jbpmn.spec.TResourceParameter;
import org.netbeans.jbpmn.spec.TResourceParameterBinding;
import org.netbeans.jbpmn.spec.TResourceRole;
import org.netbeans.jbpmn.spec.extend.LanguageManager;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.ModelerFile;

public class ResourceRoleDialog extends EntityComponent {

    private ModelerFile modelerFile;
    private TDefinitions definition;
    String originalId;
    private Boolean isNew;
    private String actionPanelType;
    private TResourceRole resourceRole;
    NAttributeEntity attributeEntity;

    /**
     * Creates new form NewJDialog
     */
    public ResourceRoleDialog(ModelerFile file) {
        super((Frame) null, true);
        initComponents();
//        this.setPreferredSize(new Dimension(600, 900));
        this.setSize(650, 725);

        this.modelerFile = file;
        definition = (TDefinitions) file.getDefinitionElement();

        language_ComboBox.setEditable(true);

    }

    @Override
    public void init() {
        initCustomNAttributeEditor();
        initEvaluatesToTypeComboBox();
        resource_ComboBox.removeAllItems();
        for (ComboBoxValue<TResource> resource : BPMNModelUtil.getResourceList(modelerFile)) {
            resource_ComboBox.addItem(resource);
        }
        initScriptTypeComboBox();
        language_ComboBox.setSelectedItem(LanguageManager.getLanguage(null));
        evaluatesToType_ComboBox.setSelectedItem(new ComboBoxValue<TItemDefinition>(null, null, ""));
        getResource_ComboBox().setSelectedItem(new ComboBoxValue<TResource>(null, null, ""));
    }

    @Override
    public void createEntity(Class/*<? extends Entity>*/ entityWrapperType) {
        this.setTitle("Add New Resource Role");
        isNew = true;
        if (entityWrapperType == RowValue.class) {
            this.setEntity(new RowValue(new Object[6]));
        }
        type_ComboBox.setSelectedItem("Performer");
        assignmentExp__TextArea.setText("");

        attributeEntity = getResourceParameterBinding();
        customNAttributeClientEditor1.setAttributeEntity(attributeEntity);

    }

    @Override
    public void updateEntity(Entity entityValue) {
        this.setTitle("Update Resource Role");
        isNew = false;
        if (entityValue.getClass() == RowValue.class) {
            this.setEntity(entityValue);
            Object[] row = ((RowValue) entityValue).getRow();
            resourceRole = (TResourceRole) row[0];
            type_ComboBox.setSelectedItem((String) row[1]);

            TResource resource = (TResource) definition.getRootElement(resourceRole.getResourceRef(), TResource.class);
            if (resource != null) {
                for (int i = 0; i < resource_ComboBox.getItemCount(); i++) {
                    ComboBoxValue<TResource> resource_ComboBoxValue = (ComboBoxValue<TResource>) resource_ComboBox.getItemAt(i);
                    if (resource_ComboBoxValue.getValue() != null && resource_ComboBoxValue.getValue().getId().equals(resource.getId())) {
                        resource_ComboBox.setSelectedItem(resource_ComboBoxValue);
                        break;
                    }
                }
            }
            language_ComboBox.setSelectedItem(LanguageManager.getLanguage(resourceRole.getResourceAssignmentExpression().getExpression().getLanguage()));//(ComboBoxValue<String>) row[3]);//(LanguageManager.getLanguage((String) row[3]));
            assignmentExp__TextArea.setText(resourceRole.getResourceAssignmentExpression().getExpression().getContent());

            TItemDefinition evaluatesToType = (TItemDefinition) definition.getRootElement(resourceRole.getResourceAssignmentExpression().getExpression().getEvaluatesToTypeRef(), TItemDefinition.class);
            if (evaluatesToType != null) {
                for (int i = 0; i < evaluatesToType_ComboBox.getItemCount(); i++) {
                    ComboBoxValue<TItemDefinition> itemDefinition = (ComboBoxValue<TItemDefinition>) evaluatesToType_ComboBox.getItemAt(i);
                    if (itemDefinition.getValue() != null && itemDefinition.getValue().getId().equals(evaluatesToType.getId())) {
                        evaluatesToType_ComboBox.setSelectedItem(itemDefinition);
                        break;
                    }
                }
            }
            attributeEntity = getResourceParameterBinding();
            customNAttributeClientEditor1.setAttributeEntity(attributeEntity);

        }

//        originalId = (String) row[0];
    }

    void initCustomNAttributeEditor() {
        parameterBinding_LayeredPane.removeAll();
        customNAttributeClientEditor1 = new org.netbeans.modeler.properties.nentity.NEntityEditor();
        javax.swing.GroupLayout parameterBinding_LayeredPaneLayout = new javax.swing.GroupLayout(parameterBinding_LayeredPane);
        parameterBinding_LayeredPane.setLayout(parameterBinding_LayeredPaneLayout);
        parameterBinding_LayeredPaneLayout.setHorizontalGroup(
                parameterBinding_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(customNAttributeClientEditor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );
        parameterBinding_LayeredPaneLayout.setVerticalGroup(
                parameterBinding_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(parameterBinding_LayeredPaneLayout.createSequentialGroup()
                        .addComponent(customNAttributeClientEditor1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
        );
        parameterBinding_LayeredPane.setLayer(customNAttributeClientEditor1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    }

    void initScriptTypeComboBox() {
        language_ComboBox.removeAllItems();
        for (ComboBoxValue<String> language : LanguageManager.getLanguageList()) {
            language_ComboBox.addItem(language);
        }
    }

    void initEvaluatesToTypeComboBox() {
        evaluatesToType_ComboBox.removeAllItems();
        for (ComboBoxValue<TItemDefinition> itemDefinition : BPMNModelUtil.getItemDefinitionList(modelerFile)) {
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
        resourceType_LayeredPane = new javax.swing.JLayeredPane();
        type_Label = new javax.swing.JLabel();
        type_ComboBox = new javax.swing.JComboBox();
        resource_LayeredPane = new javax.swing.JLayeredPane();
        resource_Label = new javax.swing.JLabel();
        resource_ComboBox = new javax.swing.JComboBox();
        resource_Action = new javax.swing.JButton();
        expression_LayeredPane = new javax.swing.JLayeredPane();
        language_LayeredPane = new javax.swing.JLayeredPane();
        language_Label = new javax.swing.JLabel();
        language_ComboBox = new javax.swing.JComboBox();
        assignmentExpression_LayeredPane = new javax.swing.JLayeredPane();
        assignmentExp_Label = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        assignmentExp__TextArea = new javax.swing.JTextArea();
        evaluateType_LayeredPane = new javax.swing.JLayeredPane();
        evaluatesToType_Label = new javax.swing.JLabel();
        evaluatesToType_ComboBox = new javax.swing.JComboBox();
        evaluatesToType_Action = new javax.swing.JButton();
        action_LayeredPane = new javax.swing.JLayeredPane();
        save_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();
        parameterBinding_LayeredPane = new javax.swing.JLayeredPane();
        customNAttributeClientEditor1 = new org.netbeans.modeler.properties.nentity.NEntityEditor();

        createItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/icon_plus.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(createItem_MenuItem, org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.createItem_MenuItem.text")); // NOI18N
        createItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.createItem_MenuItem.toolTipText")); // NOI18N
        createItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(createItem_MenuItem);

        editItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/edit.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(editItem_MenuItem, org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.editItem_MenuItem.text")); // NOI18N
        editItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.editItem_MenuItem.toolTipText")); // NOI18N
        editItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(editItem_MenuItem);

        deleteItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/delete.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(deleteItem_MenuItem, org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.deleteItem_MenuItem.text")); // NOI18N
        deleteItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.deleteItem_MenuItem.toolTipText")); // NOI18N
        deleteItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(deleteItem_MenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        wrapper_LayeredPane.setPreferredSize(new java.awt.Dimension(500, 360));

        resourceType_LayeredPane.setPreferredSize(new java.awt.Dimension(460, 30));

        org.openide.awt.Mnemonics.setLocalizedText(type_Label, org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.type_Label.text")); // NOI18N
        type_Label.setPreferredSize(new java.awt.Dimension(30, 14));

        type_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Performer", "Human Performer", "Potential Owner" }));

        javax.swing.GroupLayout resourceType_LayeredPaneLayout = new javax.swing.GroupLayout(resourceType_LayeredPane);
        resourceType_LayeredPane.setLayout(resourceType_LayeredPaneLayout);
        resourceType_LayeredPaneLayout.setHorizontalGroup(
            resourceType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, resourceType_LayeredPaneLayout.createSequentialGroup()
                .addComponent(type_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(type_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        resourceType_LayeredPaneLayout.setVerticalGroup(
            resourceType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resourceType_LayeredPaneLayout.createSequentialGroup()
                .addGroup(resourceType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type_Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        resourceType_LayeredPane.setLayer(type_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        resourceType_LayeredPane.setLayer(type_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(resource_Label, org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.resource_Label.text")); // NOI18N

        resource_ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                resource_ComboBoxItemStateChanged(evt);
            }
        });
        resource_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resource_ComboBoxActionPerformed(evt);
            }
        });
        resource_ComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                resource_ComboBoxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                resource_ComboBoxFocusLost(evt);
            }
        });

        resource_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        resource_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                resource_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout resource_LayeredPaneLayout = new javax.swing.GroupLayout(resource_LayeredPane);
        resource_LayeredPane.setLayout(resource_LayeredPaneLayout);
        resource_LayeredPaneLayout.setHorizontalGroup(
            resource_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resource_LayeredPaneLayout.createSequentialGroup()
                .addComponent(resource_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resource_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resource_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        resource_LayeredPaneLayout.setVerticalGroup(
            resource_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resource_LayeredPaneLayout.createSequentialGroup()
                .addGroup(resource_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(resource_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(resource_Label)
                        .addComponent(resource_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(resource_Action))
                .addContainerGap())
        );
        resource_LayeredPane.setLayer(resource_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        resource_LayeredPane.setLayer(resource_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        resource_LayeredPane.setLayer(resource_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(language_Label, org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.language_Label.text")); // NOI18N
        language_Label.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.language_Label.toolTipText")); // NOI18N

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

        org.openide.awt.Mnemonics.setLocalizedText(assignmentExp_Label, org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.assignmentExp_Label.text")); // NOI18N
        assignmentExp_Label.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.assignmentExp_Label.toolTipText")); // NOI18N

        assignmentExp__TextArea.setColumns(20);
        assignmentExp__TextArea.setRows(5);
        jScrollPane1.setViewportView(assignmentExp__TextArea);

        javax.swing.GroupLayout assignmentExpression_LayeredPaneLayout = new javax.swing.GroupLayout(assignmentExpression_LayeredPane);
        assignmentExpression_LayeredPane.setLayout(assignmentExpression_LayeredPaneLayout);
        assignmentExpression_LayeredPaneLayout.setHorizontalGroup(
            assignmentExpression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assignmentExpression_LayeredPaneLayout.createSequentialGroup()
                .addComponent(assignmentExp_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
        );
        assignmentExpression_LayeredPaneLayout.setVerticalGroup(
            assignmentExpression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assignmentExpression_LayeredPaneLayout.createSequentialGroup()
                .addComponent(assignmentExp_Label)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(assignmentExpression_LayeredPaneLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        assignmentExpression_LayeredPane.setLayer(assignmentExp_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        assignmentExpression_LayeredPane.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(evaluatesToType_Label, org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.evaluatesToType_Label.text")); // NOI18N

        evaluatesToType_ComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                evaluatesToType_ComboBoxFocusGained(evt);
            }
        });

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
                .addComponent(evaluatesToType_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        evaluateType_LayeredPaneLayout.setVerticalGroup(
            evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(evaluateType_LayeredPaneLayout.createSequentialGroup()
                .addGroup(evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(evaluatesToType_Label)
                        .addComponent(evaluatesToType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(evaluatesToType_Action))
                .addGap(0, 11, Short.MAX_VALUE))
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
                        .addComponent(assignmentExpression_LayeredPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(language_LayeredPane, javax.swing.GroupLayout.Alignment.LEADING))
                    .addContainerGap(39, Short.MAX_VALUE)))
        );
        expression_LayeredPaneLayout.setVerticalGroup(
            expression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expression_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(232, Short.MAX_VALUE)
                .addComponent(evaluateType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(expression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(expression_LayeredPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(language_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(assignmentExpression_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(54, Short.MAX_VALUE)))
        );
        expression_LayeredPane.setLayer(language_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        expression_LayeredPane.setLayer(assignmentExpression_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        expression_LayeredPane.setLayer(evaluateType_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(save_Button, org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.save_Button.text")); // NOI18N
        save_Button.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.save_Button.toolTipText")); // NOI18N
        save_Button.setLabel(org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.save_Button.label")); // NOI18N
        save_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_ButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(cancel_Button, org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.cancel_Button.text")); // NOI18N
        cancel_Button.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.cancel_Button.toolTipText")); // NOI18N
        cancel_Button.setLabel(org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.cancel_Button.label_1")); // NOI18N
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(save_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancel_Button)
                .addGap(2, 2, 2))
        );
        action_LayeredPaneLayout.setVerticalGroup(
            action_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(action_LayeredPaneLayout.createSequentialGroup()
                .addGroup(action_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save_Button)
                    .addComponent(cancel_Button))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        action_LayeredPane.setLayer(save_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);
        action_LayeredPane.setLayer(cancel_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);

        parameterBinding_LayeredPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), org.openide.util.NbBundle.getMessage(ResourceRoleDialog.class, "ResourceRoleDialog.parameterBinding_LayeredPane.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        parameterBinding_LayeredPane.setPreferredSize(new java.awt.Dimension(460, 30));

        javax.swing.GroupLayout parameterBinding_LayeredPaneLayout = new javax.swing.GroupLayout(parameterBinding_LayeredPane);
        parameterBinding_LayeredPane.setLayout(parameterBinding_LayeredPaneLayout);
        parameterBinding_LayeredPaneLayout.setHorizontalGroup(
            parameterBinding_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(customNAttributeClientEditor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );
        parameterBinding_LayeredPaneLayout.setVerticalGroup(
            parameterBinding_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parameterBinding_LayeredPaneLayout.createSequentialGroup()
                .addComponent(customNAttributeClientEditor1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        parameterBinding_LayeredPane.setLayer(customNAttributeClientEditor1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout wrapper_LayeredPaneLayout = new javax.swing.GroupLayout(wrapper_LayeredPane);
        wrapper_LayeredPane.setLayout(wrapper_LayeredPaneLayout);
        wrapper_LayeredPaneLayout.setHorizontalGroup(
            wrapper_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wrapper_LayeredPaneLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(wrapper_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(expression_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(wrapper_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(resource_LayeredPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 487, Short.MAX_VALUE)
                        .addComponent(resourceType_LayeredPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(wrapper_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(wrapper_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(action_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(parameterBinding_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        wrapper_LayeredPaneLayout.setVerticalGroup(
            wrapper_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wrapper_LayeredPaneLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(resourceType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resource_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expression_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parameterBinding_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(action_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        wrapper_LayeredPane.setLayer(resourceType_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        wrapper_LayeredPane.setLayer(resource_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        wrapper_LayeredPane.setLayer(expression_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        wrapper_LayeredPane.setLayer(action_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        wrapper_LayeredPane.setLayer(parameterBinding_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(wrapper_LayeredPane);
        wrapper_LayeredPane.setBounds(0, 0, 634, 703);

        pack();
    }// </editor-fold>//GEN-END:initComponents
NAttributeEntity getResourceParameterBinding() {
        final NAttributeEntity attributeEntity = new NAttributeEntity("TResourceParameterBinding", "Resource Parameter Binding", "Resource Parameter Binding Desc");
        attributeEntity.setCountDisplay(new String[]{"No Resource Parameters Binding set", "One Resource Parameter Binding set", "Resource Parameters Binding set"});

        List<Column> columns = new ArrayList<Column>();

        columns.add(new Column("OBJECT", false, true, Object.class));
        columns.add(new Column("Parameter", false, String.class));
        columns.add(new Column("Language", false, String.class));
        columns.add(new Column("Script", false, String.class));
        columns.add(new Column("Evaluates to Type", false, String.class));
        attributeEntity.setColumns(columns);
        attributeEntity.setCustomDialog(new ResourceParameterBindingPanel(modelerFile, ResourceRoleDialog.this));
        attributeEntity.setTableDataListener(new NEntityDataListener() {
            List<Object[]> data = new LinkedList<Object[]>();
            int count;

            @Override
            public void initCount() {
                if (resourceRole != null && resourceRole.getResourceParameterBinding() != null) {
                    count = resourceRole.getResourceParameterBinding().size();
                } else {
                    count = 0;
                }
            }

            @Override
            public int getCount() {
                return count;
            }

            @Override
            public void initData() {
                List<Object[]> data_local = new LinkedList<Object[]>();
                if (resourceRole != null && resourceRole.getResourceParameterBinding() != null) {
                    for (TResourceParameterBinding resourceParameterBinding : new CopyOnWriteArrayList<TResourceParameterBinding>(resourceRole.getResourceParameterBinding())) {
                        Object[] row = new Object[5];
                        row[0] = resourceParameterBinding;

                        TResource resource = (TResource) definition.getRootElement(resourceRole.getResourceRef(), TResource.class);
                        if (resource != null) {
                            TResourceParameter resourceParameter = resource.getResourceParameter(resourceParameterBinding.getParameterRef());
                            if (resourceParameter == null) { //remove because resource ref is changed and move to next parameter
                                resourceRole.getResourceParameterBinding().remove(resourceParameterBinding);
                                continue;
                            }
                            row[1] = resourceParameter.getDisplayValue();
                        } else {
                            row[1] = "";
                        }

                        TItemDefinition itemDefinition = (TItemDefinition) definition.getRootElement(resourceParameterBinding.getExpression().getEvaluatesToTypeRef(), TItemDefinition.class);
                        row[2] = resourceParameterBinding.getExpression().getLanguage();
                        row[3] = resourceParameterBinding.getExpression().getContent();
                        row[4] = itemDefinition == null ? null : itemDefinition.getDisplayValue();
                        data_local.add(row);
                    }
                }
                this.data = data_local;
            }

            @Override
            public List<Object[]> getData() {
                return data;
            }

            @Override
            public void setData(List<Object[]> data) {
                if (resourceRole.getResourceParameterBinding() != null) {
                    resourceRole.getResourceParameterBinding().clear();
                }
                for (Object[] row : data) {
                    TResourceParameterBinding resourceParameterBinding = (TResourceParameterBinding) row[0];
                    resourceRole.getResourceParameterBinding().add(resourceParameterBinding);
                }
                initData();
            }
        });
        return attributeEntity;
    }

    private boolean validateField() {
//        ComboBoxValue<TInterface> interfaceItem = (ComboBoxValue<TInterface>) evaluatesToType_ComboBox.getSelectedItem();
//        if (interfaceItem.getValue() == null) {
//            JOptionPane.showMessageDialog(this, "Please select a interface", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
//            return false;
//        }//I18n

        return true;
    }

    EntityComponent getActionPanel() {
        if ("evaluatesToType".equals(actionPanelType)) {
            return new ItemDefinitionPanel(modelerFile);
        } else if ("resource".equals(actionPanelType)) {
            return new ResourcePanel(modelerFile);
        } else {
            return null;
        }
    }

    ComboBoxValue getSelectedActionItem() {
        if ("evaluatesToType".equals(actionPanelType)) {
            return (ComboBoxValue) evaluatesToType_ComboBox.getSelectedItem();
        } else if ("resource".equals(actionPanelType)) {
            return (ComboBoxValue) getResource_ComboBox().getSelectedItem();
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
            } else if ("resource".equals(actionPanelType)) {
                getResource_ComboBox().addItem(comboBoxValue);
                getResource_ComboBox().setSelectedItem(comboBoxValue);
                definition.addRootElement((TResource) comboBoxValue.getValue());
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
            } else if ("resource".equals(actionPanelType)) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Resource ?", "Delete Resource", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    definition.removeRootElement((TItemDefinition) comboBoxValue.getValue());
                    getResource_ComboBox().removeItem(comboBoxValue);
                }
            }
        } catch (IllegalStateException ex) {
            System.out.println("EX : " + ex.toString());
        }
    }//GEN-LAST:event_deleteItem_MenuItemActionPerformed

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        cancelActionPerformed(evt);
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    private void save_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_ButtonActionPerformed

        //     id is required...
        //    if (this.resource_TextField.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/)
        //        {
        //            javax.swing.JOptionPane.showMessageDialog(this,
        //                    "Resource can't be empty","Invalid Value",
        //                    javax.swing.JOptionPane.WARNING_MESSAGE );
        //            return;
        //        }//I18n
        //    if (this.assignmentExp__TextArea.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/)
        //        {
        //            javax.swing.JOptionPane.showMessageDialog(this,
        //                    "Name can't be empty","Invalid Value",
        //                    javax.swing.JOptionPane.WARNING_MESSAGE );
        //            return;
        //        }//I18n
        //    if (this.evaluatesToType_TextField.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/)
        //        {
        //            javax.swing.JOptionPane.showMessageDialog(this,
        //                    "Item Subject Reference can't be empty","Invalid Value",
        //                    javax.swing.JOptionPane.WARNING_MESSAGE );
        //            return;
        //        }//I18n
        //
        ////         check if the id is unique...
        // String id = resource_TextField.getText().trim();
        //            for (Object[] row : attributeEntity.getTableDataListener().getData())   {
        //                if (row[0].equals(id) && (originalId == null || !originalId.equals(id))) {
        //                    javax.swing.JOptionPane.showMessageDialog(this,
        //                    "Id already in use!","Invalid Value",
        //                    javax.swing.JOptionPane.WARNING_MESSAGE );
        //                    return;
        //                }
        //            }//I18n
        if (!validateField()) {
            return;
        }
        RowValue rowValue = (RowValue) this.getEntity();
        Object[] row = rowValue.getRow();
        if (isNew) {
            String type = (String) type_ComboBox.getSelectedItem();
            if ("Potential Owner".equals(type)) {
                resourceRole = new TPotentialOwner();
            } else if ("Human Performer".equals(type)) {
                resourceRole = new THumanPerformer();
            } else if ("Performer".equals(type)) {
                resourceRole = new TPerformer();
            } else {
                resourceRole = new TResourceRole();
            }
            resourceRole.setId(NBModelerUtil.getAutoGeneratedStringId());
            row[0] = resourceRole;
            row[1] = type_ComboBox.getSelectedItem(); // 1: here because disable in case of update
        }

        resourceRole.setResourceRef(((ComboBoxValue<TResource>) getResource_ComboBox().getSelectedItem()).getId());
        row[2] = ((ComboBoxValue<TResource>) getResource_ComboBox().getSelectedItem()).getDisplayValue();

        if (resourceRole.getResourceAssignmentExpression() == null) {
            TResourceAssignmentExpression resourceAssignmentExp = new TResourceAssignmentExpression();
            resourceRole.setResourceAssignmentExpression(resourceAssignmentExp);
        }
        if (resourceRole.getResourceAssignmentExpression().getExpression() == null) {
            resourceRole.getResourceAssignmentExpression().setExpression(new TFormalExpression());
        }

        ComboBoxValue<String> language_comboBoxValue;
        if (language_ComboBox.getSelectedItem() instanceof String) {
            language_comboBoxValue = new ComboBoxValue<String>((String) language_ComboBox.getSelectedItem(), (String) language_ComboBox.getSelectedItem());
        } else {
            language_comboBoxValue = (ComboBoxValue<String>) language_ComboBox.getSelectedItem();
        }
        resourceRole.getResourceAssignmentExpression().getExpression().setLanguage(language_comboBoxValue.getValue());
        row[3] = language_comboBoxValue;

        resourceRole.getResourceAssignmentExpression().getExpression().setContent(assignmentExp__TextArea.getText().trim());
        row[4] = assignmentExp__TextArea.getText().trim();

        resourceRole.getResourceAssignmentExpression().getExpression().setEvaluatesToTypeRef(((ComboBoxValue<TItemDefinition>) evaluatesToType_ComboBox.getSelectedItem()).getId());
        row[5] = ((ComboBoxValue<TItemDefinition>) evaluatesToType_ComboBox.getSelectedItem()).getDisplayValue();//evaluatesToType_TextField.getText().trim();

        attributeEntity.getTableDataListener().setData(customNAttributeClientEditor1.getSavedModel());
        saveActionPerformed(evt);
    }//GEN-LAST:event_save_ButtonActionPerformed

    private void evaluatesToType_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_evaluatesToType_ActionMousePressed
        actionPanelType = "evaluatesToType";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_evaluatesToType_ActionMousePressed

    private void resource_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resource_ActionMousePressed
        actionPanelType = "resource";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_resource_ActionMousePressed

    private void evaluatesToType_ComboBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_evaluatesToType_ComboBoxFocusGained
        Object obj = evaluatesToType_ComboBox.getSelectedItem();
        initEvaluatesToTypeComboBox();
        evaluatesToType_ComboBox.setSelectedItem(obj);
    }//GEN-LAST:event_evaluatesToType_ComboBoxFocusGained

    private void resource_ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_resource_ComboBoxItemStateChanged
        if (resource_tx) {
            ComboBoxValue<TResource> preValue = (ComboBoxValue<TResource>) evt.getItem();
//        ComboBoxValue<TResource> newValue = (ComboBoxValue<TResource>) evt.getItemSelectable().getSelectedObjects()[0];
            List<Object[]> data = customNAttributeClientEditor1.getSavedModel();
            if (data.size() > 0) {
//                int option = JOptionPane.showConfirmDialog(null, "Are you sue you want to change Resource ? \n"
//                        + data.size() + " Resource Parameter Binding exist for Resource " + preValue.getDisplayValue() + ".",
//                        "Delete Resource", JOptionPane.OK_CANCEL_OPTION);
//                if (option == JOptionPane.CANCEL_OPTION) {
//                    resource_ComboBox.setSelectedItem(preValue);
//                }
                System.out.println("Resource Role Dialog Clear Param Bind ");
//                customNAttributeClientEditor1.clearRow();

            }
        }

    }//GEN-LAST:event_resource_ComboBoxItemStateChanged

    private void resource_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resource_ComboBoxActionPerformed
        System.out.println("");        // TODO add your handling code here:
    }//GEN-LAST:event_resource_ComboBoxActionPerformed
    private boolean resource_tx;
    private void resource_ComboBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_resource_ComboBoxFocusGained
        resource_tx = true;
    }//GEN-LAST:event_resource_ComboBoxFocusGained

    private void resource_ComboBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_resource_ComboBoxFocusLost
        resource_tx = false;
    }//GEN-LAST:event_resource_ComboBoxFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane action_LayeredPane;
    protected javax.swing.JLabel assignmentExp_Label;
    private javax.swing.JTextArea assignmentExp__TextArea;
    private javax.swing.JLayeredPane assignmentExpression_LayeredPane;
    protected javax.swing.JButton cancel_Button;
    private javax.swing.JMenuItem createItem_MenuItem;
    private org.netbeans.modeler.properties.nentity.NEntityEditor customNAttributeClientEditor1;
    private javax.swing.JMenuItem deleteItem_MenuItem;
    private javax.swing.JMenuItem editItem_MenuItem;
    private javax.swing.JLayeredPane evaluateType_LayeredPane;
    private javax.swing.JButton evaluatesToType_Action;
    private javax.swing.JComboBox evaluatesToType_ComboBox;
    protected javax.swing.JLabel evaluatesToType_Label;
    private javax.swing.JLayeredPane expression_LayeredPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox language_ComboBox;
    protected javax.swing.JLabel language_Label;
    private javax.swing.JLayeredPane language_LayeredPane;
    private javax.swing.JLayeredPane parameterBinding_LayeredPane;
    private javax.swing.JLayeredPane resourceType_LayeredPane;
    private javax.swing.JButton resource_Action;
    private javax.swing.JComboBox resource_ComboBox;
    protected javax.swing.JLabel resource_Label;
    private javax.swing.JLayeredPane resource_LayeredPane;
    protected javax.swing.JButton save_Button;
    private javax.swing.JPopupMenu setting_PopupMenu;
    private javax.swing.JComboBox type_ComboBox;
    protected javax.swing.JLabel type_Label;
    private javax.swing.JLayeredPane wrapper_LayeredPane;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the resource_ComboBox
     */
    public javax.swing.JComboBox getResource_ComboBox() {
        return resource_ComboBox;
    }
}
