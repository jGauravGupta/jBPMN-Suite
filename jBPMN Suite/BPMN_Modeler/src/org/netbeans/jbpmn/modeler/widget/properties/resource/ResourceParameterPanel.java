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

import javax.swing.JOptionPane;
import org.netbeans.jbpmn.modeler.specification.bpmn.util.BPMNModelUtil;
import org.netbeans.jbpmn.modeler.widget.properties.itemdefinition.ItemDefinitionPanel;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.Entity;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.RowValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TResourceParameter;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.ModelerFile;

public class ResourceParameterPanel extends EntityComponent<TResourceParameter> {

// private ComboBoxValue<TResourceParameter> comboBoxValue;
    private ModelerFile modelerFile;
    private TDefinitions definition;
    private String actionPanelType;
    private Boolean isNew;
    private TResourceParameter resourceParameter;

    /**
     * Creates new form CreateErrorPanel
     */
    public ResourceParameterPanel(ModelerFile modelerFile) {
        super("", true);
        this.modelerFile = modelerFile;
        definition = (TDefinitions) modelerFile.getDefinitionElement();
        initComponents();

    }

    @Override
    public void init() {
        initDataType();
        dataType_ComboBox.setSelectedItem(new ComboBoxValue<TItemDefinition>(null, null, ""));
    }

    void initDataType() {
        dataType_ComboBox.removeAllItems();
        for (ComboBoxValue<TItemDefinition> itemDefinition : BPMNModelUtil.getItemDefinitionList(modelerFile)) {
            dataType_ComboBox.addItem(itemDefinition);
        }
    }

    @Override
    public void createEntity(Class<? extends Entity> entityWrapperType) {
        this.setTitle("Create new Resource Parameter");
        isNew = true;
//        if (entityWrapperType == ComboBoxValue.class) {
//            this.setEntity(new ComboBoxValue<TResourceParameter>());
//        }
        if (entityWrapperType == RowValue.class) {
            this.setEntity(new RowValue(new Object[4]));
        }
        name_TextField.setText("");
        required_CheckBox.setSelected(false);
    }

    @Override
    public void updateEntity(Entity<TResourceParameter> entityValue) {
        this.setTitle("Update Resource Parameter");
        isNew = false;
        if (entityValue.getClass() == RowValue.class) {
            this.setEntity(entityValue);
            Object[] row = ((RowValue) entityValue).getRow();
            resourceParameter = (TResourceParameter) row[0];
            name_TextField.setText(resourceParameter.getName());
            required_CheckBox.setSelected(resourceParameter.isIsRequired());
            TItemDefinition itemDefinition = (TItemDefinition) definition.getRootElement(resourceParameter.getType(), TItemDefinition.class);
            if (itemDefinition != null) {
                for (int i = 0; i < dataType_ComboBox.getItemCount(); i++) {
                    ComboBoxValue<TItemDefinition> itemDefinition_Combo = (ComboBoxValue<TItemDefinition>) dataType_ComboBox.getItemAt(i);
                    if (itemDefinition_Combo.getValue() != null && itemDefinition_Combo.getValue().getId().equals(itemDefinition.getId())) {
                        dataType_ComboBox.setSelectedItem(itemDefinition_Combo);
                        break;
                    }
                }
            }

        }
//        if (entityValue.getClass() == ComboBoxValue.class) {
//            this.setEntity(entityValue);
//            TResourceParameter resourceParameter = ((ComboBoxValue<TResourceParameter>) entityValue).getValue();
//            name_TextField.setText(resourceParameter.getName());
//            required_CheckBox.setSelected(resourceParameter.isIsRequired());
//            TItemDefinition itemDefinition = definition.findItemDefinition(resourceParameter.getType());
//            for (int i = 0; i < dataType_ComboBox.getItemCount(); i++) {
//                ComboBoxValue<TItemDefinition> itemDefinition_Combo = (ComboBoxValue<TItemDefinition>) dataType_ComboBox.getItemAt(i);
//                if (itemDefinition_Combo.getValue() != null && itemDefinition_Combo.getValue().getId().equals(itemDefinition.getId())) {
//                    dataType_ComboBox.setSelectedItem(itemDefinition_Combo);
//                    break;
//                }
//            }
//        }
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
        jLayeredPane1 = new javax.swing.JLayeredPane();
        name_LayeredPane = new javax.swing.JLayeredPane();
        name_Label = new javax.swing.JLabel();
        name_TextField = new javax.swing.JTextField();
        code_LayeredPane = new javax.swing.JLayeredPane();
        required_Label = new javax.swing.JLabel();
        required_CheckBox = new javax.swing.JCheckBox();
        action_LayeredPane = new javax.swing.JLayeredPane();
        save_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();
        dataType_LayeredPane = new javax.swing.JLayeredPane();
        dataType_Label = new javax.swing.JLabel();
        dataType_ComboBox = new javax.swing.JComboBox();
        dataType_Action = new javax.swing.JButton();

        createItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/icon_plus.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(createItem_MenuItem, org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.createItem_MenuItem.text")); // NOI18N
        createItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.createItem_MenuItem.toolTipText")); // NOI18N
        createItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(createItem_MenuItem);

        editItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/edit.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(editItem_MenuItem, org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.editItem_MenuItem.text")); // NOI18N
        editItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.editItem_MenuItem.toolTipText")); // NOI18N
        editItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(editItem_MenuItem);

        deleteItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/delete.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(deleteItem_MenuItem, org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.deleteItem_MenuItem.text")); // NOI18N
        deleteItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.deleteItem_MenuItem.toolTipText")); // NOI18N
        deleteItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(deleteItem_MenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        org.openide.awt.Mnemonics.setLocalizedText(name_Label, org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.name_Label.text")); // NOI18N
        name_LayeredPane.add(name_Label);
        name_Label.setBounds(0, 0, 90, 14);

        name_TextField.setText(org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.name_TextField.text")); // NOI18N
        name_TextField.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.name_TextField.toolTipText")); // NOI18N
        name_LayeredPane.add(name_TextField);
        name_TextField.setBounds(90, 0, 250, 20);

        jLayeredPane1.add(name_LayeredPane);
        name_LayeredPane.setBounds(20, 20, 340, 30);

        org.openide.awt.Mnemonics.setLocalizedText(required_Label, org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.required_Label.text")); // NOI18N
        code_LayeredPane.add(required_Label);
        required_Label.setBounds(0, 4, 90, 14);

        org.openide.awt.Mnemonics.setLocalizedText(required_CheckBox, org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.required_CheckBox.text")); // NOI18N
        required_CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                required_CheckBoxActionPerformed(evt);
            }
        });
        code_LayeredPane.add(required_CheckBox);
        required_CheckBox.setBounds(90, 0, 21, 21);

        jLayeredPane1.add(code_LayeredPane);
        code_LayeredPane.setBounds(20, 100, 340, 30);

        org.openide.awt.Mnemonics.setLocalizedText(save_Button, org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.save_Button.text")); // NOI18N
        save_Button.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.save_Button.toolTipText")); // NOI18N
        save_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_ButtonActionPerformed(evt);
            }
        });
        action_LayeredPane.add(save_Button);
        save_Button.setBounds(30, 0, 57, 23);

        org.openide.awt.Mnemonics.setLocalizedText(cancel_Button, org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.cancel_Button.text")); // NOI18N
        cancel_Button.setToolTipText(org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.cancel_Button.toolTipText")); // NOI18N
        cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_ButtonActionPerformed(evt);
            }
        });
        action_LayeredPane.add(cancel_Button);
        cancel_Button.setBounds(90, 0, 65, 23);

        jLayeredPane1.add(action_LayeredPane);
        action_LayeredPane.setBounds(210, 140, 160, 30);

        org.openide.awt.Mnemonics.setLocalizedText(dataType_Label, org.openide.util.NbBundle.getMessage(ResourceParameterPanel.class, "ResourceParameterPanel.dataType_Label.text")); // NOI18N
        dataType_LayeredPane.add(dataType_Label);
        dataType_Label.setBounds(0, 0, 90, 14);

        dataType_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataType_ComboBoxActionPerformed(evt);
            }
        });
        dataType_LayeredPane.add(dataType_ComboBox);
        dataType_ComboBox.setBounds(90, 0, 250, 20);

        jLayeredPane1.add(dataType_LayeredPane);
        dataType_LayeredPane.setBounds(20, 60, 350, 30);

        dataType_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        dataType_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dataType_ActionMousePressed(evt);
            }
        });
        jLayeredPane1.add(dataType_Action);
        dataType_Action.setBounds(370, 60, 20, 21);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean validateField() {
        if (this.name_TextField.getText().trim().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Parameter name can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n
        ComboBoxValue<TItemDefinition> itemDefinitionItem = (ComboBoxValue<TItemDefinition>) dataType_ComboBox.getSelectedItem();
        if (itemDefinitionItem.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Please select a Data Type", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
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
            resourceParameter = new TResourceParameter();
            resourceParameter.setId(NBModelerUtil.getAutoGeneratedStringId());
        }
        resourceParameter.setName(name_TextField.getText());
        resourceParameter.setIsRequired(required_CheckBox.isSelected());
        resourceParameter.setType(((ComboBoxValue<TItemDefinition>) dataType_ComboBox.getSelectedItem()).getId());

        row[0] = resourceParameter;
        row[1] = resourceParameter.getName();
        TItemDefinition itemDefinition = (TItemDefinition) definition.getRootElement(resourceParameter.getType(), TItemDefinition.class);
        row[2] = itemDefinition == null ? null : itemDefinition.getDisplayValue();
        row[3] = resourceParameter.isIsRequired();
        saveActionPerformed(evt);
    }//GEN-LAST:event_save_ButtonActionPerformed

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        cancelActionPerformed(evt);
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    private void dataType_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataType_ActionMousePressed
        actionPanelType = "dataType";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_dataType_ActionMousePressed

    private void dataType_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataType_ComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataType_ComboBoxActionPerformed

    EntityComponent getActionPanel() {
        if ("dataType".equals(actionPanelType)) {
            return new ItemDefinitionPanel(modelerFile);
        } else {
            return null;
        }
    }

    ComboBoxValue getSelectedActionItem() {
        if ("dataType".equals(actionPanelType)) {
            return (ComboBoxValue) dataType_ComboBox.getSelectedItem();
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

            if ("dataType".equals(actionPanelType)) {
                dataType_ComboBox.addItem(comboBoxValue);
                dataType_ComboBox.setSelectedItem(comboBoxValue);
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
            if ("dataType".equals(actionPanelType)) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Data Type ?", "Delete Data Type", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    definition.removeRootElement((TItemDefinition) comboBoxValue.getValue());
                    dataType_ComboBox.removeItem(comboBoxValue);
                }
            }
        } catch (IllegalStateException ex) {
            System.out.println("EX : " + ex.toString());
        }
    }//GEN-LAST:event_deleteItem_MenuItemActionPerformed

    private void required_CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_required_CheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_required_CheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane action_LayeredPane;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JLayeredPane code_LayeredPane;
    private javax.swing.JMenuItem createItem_MenuItem;
    private javax.swing.JButton dataType_Action;
    private javax.swing.JComboBox dataType_ComboBox;
    private javax.swing.JLabel dataType_Label;
    private javax.swing.JLayeredPane dataType_LayeredPane;
    private javax.swing.JMenuItem deleteItem_MenuItem;
    private javax.swing.JMenuItem editItem_MenuItem;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel name_Label;
    private javax.swing.JLayeredPane name_LayeredPane;
    private javax.swing.JTextField name_TextField;
    private javax.swing.JCheckBox required_CheckBox;
    private javax.swing.JLabel required_Label;
    private javax.swing.JButton save_Button;
    private javax.swing.JPopupMenu setting_PopupMenu;
    // End of variables declaration//GEN-END:variables
}
