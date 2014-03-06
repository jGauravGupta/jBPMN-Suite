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
package org.netbeans.jbpmn.modeler.widget.properties.user_interface.property;

import javax.swing.JOptionPane;
import org.netbeans.jbpmn.modeler.specification.bpmn.util.BPMNModelUtil;
import org.netbeans.jbpmn.modeler.widget.properties.itemdefinition.ItemDefinitionPanel;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.Entity;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.RowValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;
import org.netbeans.jbpmn.spec.TDataState;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TProperty;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.ModelerFile;

public class ProcessVariableDefinitionPanel extends EntityComponent<TProperty> {

//    private ComboBoxValue<TMessage> comboBoxValue;
    private ModelerFile modelerFile;
    private TDefinitions definition;
    private TProperty property;
    private Boolean isNew;
    private String actionPanelType;

    /**
     * Creates new form CreateMessagePanel
     */
    public ProcessVariableDefinitionPanel(ModelerFile modelerFile) {
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
        this.setTitle("Create new Variable");
        isNew = true;
        if (entityWrapperType == RowValue.class) {
            this.setEntity(new RowValue(new Object[4]));
        }
        name_TextField.setText("");
        dataState_TextField.setText("");
    }

    @Override
    public void updateEntity(Entity<TProperty> entityValue) {
        this.setTitle("Update Variable");
        isNew = false;
        if (entityValue.getClass() == RowValue.class) {
            this.setEntity(entityValue);
            Object[] row = ((RowValue) entityValue).getRow();
            property = (TProperty) row[0];
            name_TextField.setText(property.getId());
            dataState_TextField.setText(property.getDataState() == null ? null : property.getDataState().getName());

            TItemDefinition itemSubjectRef = (TItemDefinition) definition.getRootElement(property.getItemSubjectRef(), TItemDefinition.class);
            if (itemSubjectRef != null) {
                for (int i = 0; i < dataType_ComboBox.getItemCount(); i++) {
                    ComboBoxValue<TItemDefinition> itemDefinition_Combo = (ComboBoxValue<TItemDefinition>) dataType_ComboBox.getItemAt(i);
                    if (itemDefinition_Combo.getValue() != null && itemDefinition_Combo.getValue().getId().equals(itemSubjectRef.getId())) {
                        dataType_ComboBox.setSelectedItem(itemDefinition_Combo);
                        break;
                    }
                }
            }
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
        jLayeredPane1 = new javax.swing.JLayeredPane();
        name_LayeredPane = new javax.swing.JLayeredPane();
        name_Label = new javax.swing.JLabel();
        name_TextField = new javax.swing.JTextField();
        dataState_Pane = new javax.swing.JLayeredPane();
        dataState_Label = new javax.swing.JLabel();
        dataState_TextField = new javax.swing.JTextField();
        dataType_LayeredPane = new javax.swing.JLayeredPane();
        dataType_Label1 = new javax.swing.JLabel();
        dataType_ComboBox = new javax.swing.JComboBox();
        dataType_Action = new javax.swing.JButton();
        jLayeredPane8 = new javax.swing.JLayeredPane();
        save_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();

        createItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/icon_plus.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(createItem_MenuItem, org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.createItem_MenuItem.text")); // NOI18N
        createItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.createItem_MenuItem.toolTipText")); // NOI18N
        createItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(createItem_MenuItem);

        editItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/edit.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(editItem_MenuItem, org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.editItem_MenuItem.text")); // NOI18N
        editItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.editItem_MenuItem.toolTipText")); // NOI18N
        editItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(editItem_MenuItem);

        deleteItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/delete.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(deleteItem_MenuItem, org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.deleteItem_MenuItem.text")); // NOI18N
        deleteItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.deleteItem_MenuItem.toolTipText")); // NOI18N
        deleteItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(deleteItem_MenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        org.openide.awt.Mnemonics.setLocalizedText(name_Label, org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.name_Label.text_1")); // NOI18N
        name_LayeredPane.add(name_Label);
        name_Label.setBounds(0, 0, 100, 14);

        name_TextField.setText(org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.name_TextField.text_1")); // NOI18N
        name_TextField.setToolTipText(org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.name_TextField.toolTipText_1")); // NOI18N
        name_LayeredPane.add(name_TextField);
        name_TextField.setBounds(92, 0, 300, 20);

        dataState_Pane.setName(""); // NOI18N
        dataState_Pane.setPreferredSize(new java.awt.Dimension(400, 30));

        org.openide.awt.Mnemonics.setLocalizedText(dataState_Label, org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.dataState_Label.text")); // NOI18N
        dataState_Label.setPreferredSize(new java.awt.Dimension(50, 15));

        dataState_TextField.setToolTipText(org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.dataState_TextField.toolTipText")); // NOI18N

        javax.swing.GroupLayout dataState_PaneLayout = new javax.swing.GroupLayout(dataState_Pane);
        dataState_Pane.setLayout(dataState_PaneLayout);
        dataState_PaneLayout.setHorizontalGroup(
            dataState_PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataState_PaneLayout.createSequentialGroup()
                .addComponent(dataState_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataState_TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addContainerGap())
        );
        dataState_PaneLayout.setVerticalGroup(
            dataState_PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataState_PaneLayout.createSequentialGroup()
                .addGroup(dataState_PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataState_PaneLayout.createSequentialGroup()
                        .addComponent(dataState_Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(dataState_TextField))
                .addContainerGap())
        );
        dataState_Pane.setLayer(dataState_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dataState_Pane.setLayer(dataState_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        dataType_LayeredPane.setPreferredSize(new java.awt.Dimension(400, 30));

        org.openide.awt.Mnemonics.setLocalizedText(dataType_Label1, org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.dataType_Label1.text")); // NOI18N

        dataType_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        dataType_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dataType_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout dataType_LayeredPaneLayout = new javax.swing.GroupLayout(dataType_LayeredPane);
        dataType_LayeredPane.setLayout(dataType_LayeredPaneLayout);
        dataType_LayeredPaneLayout.setHorizontalGroup(
            dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataType_LayeredPaneLayout.createSequentialGroup()
                .addComponent(dataType_Label1, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addGap(16, 16, 16)
                .addComponent(dataType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataType_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        dataType_LayeredPaneLayout.setVerticalGroup(
            dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataType_LayeredPaneLayout.createSequentialGroup()
                .addGroup(dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dataType_Label1)
                        .addComponent(dataType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dataType_Action))
                .addGap(0, 9, Short.MAX_VALUE))
        );
        dataType_LayeredPane.setLayer(dataType_Label1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dataType_LayeredPane.setLayer(dataType_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dataType_LayeredPane.setLayer(dataType_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(save_Button, org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.save_Button.text_1")); // NOI18N
        save_Button.setToolTipText(org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.save_Button.toolTipText_1")); // NOI18N
        save_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_ButtonActionPerformed(evt);
            }
        });
        jLayeredPane8.add(save_Button);
        save_Button.setBounds(20, 0, 57, 23);

        org.openide.awt.Mnemonics.setLocalizedText(cancel_Button, org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.cancel_Button.text_1")); // NOI18N
        cancel_Button.setToolTipText(org.openide.util.NbBundle.getMessage(ProcessVariableDefinitionPanel.class, "ProcessVariableDefinitionPanel.cancel_Button.toolTipText_1")); // NOI18N
        cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_ButtonActionPerformed(evt);
            }
        });
        jLayeredPane8.add(cancel_Button);
        cancel_Button.setBounds(90, 0, 73, 23);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataState_Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLayeredPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)))
                .addGap(10, 10, 10))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(name_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dataState_Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dataType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jLayeredPane1.setLayer(name_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(dataState_Pane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(dataType_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLayeredPane8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
  private boolean validateField() {
        if (this.name_TextField.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
            JOptionPane.showMessageDialog(this, "Parameter name can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
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
            property = new TProperty();
        }

        row[0] = property;
        property.setId(name_TextField.getText().trim());
        property.setName(property.getId());
        row[1] = name_TextField.getText().trim();

        if (!dataState_TextField.getText().trim().isEmpty()) { //BUG : Id change on every update
            TDataState dataState = new TDataState();
            dataState.setId(NBModelerUtil.getAutoGeneratedStringId());
            dataState.setName(dataState_TextField.getText().trim());
            property.setDataState(dataState);
        }

        property.setItemSubjectRef(((ComboBoxValue<TItemDefinition>) dataType_ComboBox.getSelectedItem()).getId());
        row[2] = ((ComboBoxValue<TItemDefinition>) dataType_ComboBox.getSelectedItem()).getDisplayValue();

        saveActionPerformed(evt);
    }//GEN-LAST:event_save_ButtonActionPerformed

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        cancelActionPerformed(evt);
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    private void dataType_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataType_ActionMousePressed
        actionPanelType = "dataType";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_dataType_ActionMousePressed

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
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ErrorPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ErrorPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ErrorPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ErrorPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                ErrorPanel dialog = new ErrorPanel(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel_Button;
    private javax.swing.JMenuItem createItem_MenuItem;
    private javax.swing.JLabel dataState_Label;
    private javax.swing.JLayeredPane dataState_Pane;
    private javax.swing.JTextField dataState_TextField;
    private javax.swing.JButton dataType_Action;
    private javax.swing.JComboBox dataType_ComboBox;
    protected javax.swing.JLabel dataType_Label1;
    private javax.swing.JLayeredPane dataType_LayeredPane;
    private javax.swing.JMenuItem deleteItem_MenuItem;
    private javax.swing.JMenuItem editItem_MenuItem;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane8;
    private javax.swing.JLabel name_Label;
    private javax.swing.JLayeredPane name_LayeredPane;
    private javax.swing.JTextField name_TextField;
    private javax.swing.JButton save_Button;
    private javax.swing.JPopupMenu setting_PopupMenu;
    // End of variables declaration//GEN-END:variables
}