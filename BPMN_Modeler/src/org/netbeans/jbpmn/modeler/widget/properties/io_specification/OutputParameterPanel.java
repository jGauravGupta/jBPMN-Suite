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
package org.netbeans.jbpmn.modeler.widget.properties.io_specification;

import java.awt.Frame;
import javax.swing.JOptionPane;
import org.netbeans.jbpmn.modeler.specification.bpmn.util.BPMNModelUtil;
import org.netbeans.jbpmn.modeler.widget.properties.itemdefinition.ItemDefinitionPanel;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.Entity;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.RowValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;
import org.netbeans.jbpmn.spec.TDataOutput;
import org.netbeans.jbpmn.spec.TDataOutputAssociation;
import org.netbeans.jbpmn.spec.TDataState;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TProcess;
import org.netbeans.jbpmn.spec.TProperty;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.ModelerFile;

public class OutputParameterPanel extends EntityComponent {

    private ModelerFile modelerFile;
    private TDefinitions definition;
    private TProcess process;
    private Boolean isNew;
    private String actionPanelType;

    /**
     * Creates new form OutputParameterPanel
     */
    public OutputParameterPanel(ModelerFile modelerFile) {
        super((Frame) null, true);
        initComponents();
        this.modelerFile = modelerFile;
        definition = (TDefinitions) modelerFile.getDefinitionElement();
        process = (TProcess) modelerFile.getRootElement();

    }

    void initDataType() {
        sourceDataType_ComboBox.removeAllItems();
        for (ComboBoxValue<TItemDefinition> itemDefinition : BPMNModelUtil.getItemDefinitionList(modelerFile)) {
            sourceDataType_ComboBox.addItem(itemDefinition);
        }
    }

    @Override
    public void init() {
        sourceName_TextField.setText("");
        sourceDataState_TextField.setText("");
        initDataType();
        sourceDataType_ComboBox.setSelectedItem(new ComboBoxValue<TItemDefinition>(null, null, ""));
        isCollection_CheckBox.setSelected(false);
        initTargetRefComboBox();
        targetRef_ComboBox.setSelectedItem(new ComboBoxValue<TProperty>(null, null, ""));
    }

    @Override
    public void createEntity(Class/*<? extends Entity>*/ entityWrapperType) {
        this.setTitle("Add New Output Parameter");
        isNew = true;
        if (entityWrapperType == RowValue.class) {
            this.setEntity(new RowValue(new Object[3]));
        }
    }
    TProperty property;
    TDataOutputAssociation dataOutputAssociation;
    TDataOutput dataOutput;

    @Override
    public void updateEntity(Entity entityValue) {
        this.setTitle("Update Output Parameter");
        isNew = false;
        if (entityValue.getClass() == RowValue.class) {
            this.setEntity(entityValue);
            Object[] row = ((RowValue) entityValue).getRow();
            Object key[] = (Object[]) row[0];

            dataOutput = (TDataOutput) key[0];
            dataOutputAssociation = (TDataOutputAssociation) key[1];
            property = (TProperty) key[2];

            sourceName_TextField.setText(dataOutput.getName());
            sourceDataState_TextField.setText(dataOutput.getDataState() == null ? null : dataOutput.getDataState().getName());
            TItemDefinition itemSubjectRef = (TItemDefinition) definition.getRootElement(dataOutput.getItemSubjectRef(), TItemDefinition.class);
            if (itemSubjectRef != null) {
                for (int i = 0; i < sourceDataType_ComboBox.getItemCount(); i++) {
                    ComboBoxValue<TItemDefinition> itemDefinition_Combo = (ComboBoxValue<TItemDefinition>) sourceDataType_ComboBox.getItemAt(i);
                    if (itemDefinition_Combo.getValue() != null && itemDefinition_Combo.getValue().getId().equals(itemSubjectRef.getId())) {
                        sourceDataType_ComboBox.setSelectedItem(itemDefinition_Combo);
                        break;
                    }
                }
            }
            isCollection_CheckBox.setSelected(dataOutput.isIsCollection());
            TProperty propertySourceRef = process.getProperty(dataOutputAssociation.getTargetRef());
            targetRef_ComboBox.setSelectedItem(new ComboBoxValue<TProperty>(propertySourceRef.getId(), propertySourceRef, propertySourceRef.getName() == null ? propertySourceRef.getId() : propertySourceRef.getName()));
        }
    }

    private void initTargetRefComboBox() {
        targetRef_ComboBox.removeAllItems();
        targetRef_ComboBox.addItem(new ComboBoxValue<TProperty>(null, null, ""));
        for (TProperty property_Tmp : process.getProperty()) {
            targetRef_ComboBox.addItem(new ComboBoxValue<TProperty>(property_Tmp.getId(), property_Tmp, property_Tmp.getName() == null ? property_Tmp.getId() : property_Tmp.getName()));
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
        layeredPane = new javax.swing.JLayeredPane();
        fromParameterPane = new javax.swing.JLayeredPane();
        sourceNamePane = new javax.swing.JLayeredPane();
        sourceName_Label = new javax.swing.JLabel();
        sourceName_TextField = new javax.swing.JTextField();
        sourceDataTypePane = new javax.swing.JLayeredPane();
        sourceDataType_Label = new javax.swing.JLabel();
        sourceDataType_ComboBox = new javax.swing.JComboBox();
        sourceDataType_Action = new javax.swing.JButton();
        sourceDataStatePane = new javax.swing.JLayeredPane();
        sourceDataState_Label = new javax.swing.JLabel();
        sourceDataState_TextField = new javax.swing.JTextField();
        isCollection_LayeredPane = new javax.swing.JLayeredPane();
        isCollection_Label = new javax.swing.JLabel();
        isCollection_CheckBox = new javax.swing.JCheckBox();
        toParameterPane = new javax.swing.JLayeredPane();
        targetPane = new javax.swing.JLayeredPane();
        targetRef_Label = new javax.swing.JLabel();
        targetRef_ComboBox = new javax.swing.JComboBox();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        cancel_Button = new javax.swing.JButton();
        save_Button = new javax.swing.JButton();

        createItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/icon_plus.png"))); // NOI18N
        createItem_MenuItem.setText(org.openide.util.NbBundle.getMessage(OutputParameterPanel.class, "ResourceDialog.createItem_MenuItem.text")); // NOI18N
        createItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(OutputParameterPanel.class, "ResourceDialog.createItem_MenuItem.toolTipText")); // NOI18N
        createItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(createItem_MenuItem);

        editItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/edit.png"))); // NOI18N
        editItem_MenuItem.setText(org.openide.util.NbBundle.getMessage(OutputParameterPanel.class, "ResourceDialog.editItem_MenuItem.text")); // NOI18N
        editItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(OutputParameterPanel.class, "ResourceDialog.editItem_MenuItem.toolTipText")); // NOI18N
        editItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(editItem_MenuItem);

        deleteItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/delete.png"))); // NOI18N
        deleteItem_MenuItem.setText(org.openide.util.NbBundle.getMessage(OutputParameterPanel.class, "ResourceDialog.deleteItem_MenuItem.text")); // NOI18N
        deleteItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(OutputParameterPanel.class, "ResourceDialog.deleteItem_MenuItem.toolTipText")); // NOI18N
        deleteItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(deleteItem_MenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        fromParameterPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "From", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB Demi", 0, 12), new java.awt.Color(51, 51, 51))); // NOI18N
        fromParameterPane.setPreferredSize(new java.awt.Dimension(535, 110));

        sourceNamePane.setPreferredSize(new java.awt.Dimension(400, 30));

        sourceName_Label.setText("Name :");
        sourceName_Label.setPreferredSize(new java.awt.Dimension(50, 15));

        sourceName_TextField.setToolTipText("Target Variable Name");

        javax.swing.GroupLayout sourceNamePaneLayout = new javax.swing.GroupLayout(sourceNamePane);
        sourceNamePane.setLayout(sourceNamePaneLayout);
        sourceNamePaneLayout.setHorizontalGroup(
            sourceNamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sourceNamePaneLayout.createSequentialGroup()
                .addComponent(sourceName_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sourceName_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sourceNamePaneLayout.setVerticalGroup(
            sourceNamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sourceNamePaneLayout.createSequentialGroup()
                .addGroup(sourceNamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sourceName_Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sourceName_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        sourceNamePane.setLayer(sourceName_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        sourceNamePane.setLayer(sourceName_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        sourceDataType_Label.setText("Data Type :");

        sourceDataType_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        sourceDataType_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sourceDataType_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout sourceDataTypePaneLayout = new javax.swing.GroupLayout(sourceDataTypePane);
        sourceDataTypePane.setLayout(sourceDataTypePaneLayout);
        sourceDataTypePaneLayout.setHorizontalGroup(
            sourceDataTypePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sourceDataTypePaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(sourceDataType_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sourceDataType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(sourceDataType_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        sourceDataTypePaneLayout.setVerticalGroup(
            sourceDataTypePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sourceDataTypePaneLayout.createSequentialGroup()
                .addGroup(sourceDataTypePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sourceDataTypePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sourceDataType_Label)
                        .addComponent(sourceDataType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sourceDataType_Action))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        sourceDataTypePane.setLayer(sourceDataType_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        sourceDataTypePane.setLayer(sourceDataType_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        sourceDataTypePane.setLayer(sourceDataType_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        sourceDataStatePane.setName(""); // NOI18N
        sourceDataStatePane.setPreferredSize(new java.awt.Dimension(400, 30));

        sourceDataState_Label.setText("Data State :");
        sourceDataState_Label.setPreferredSize(new java.awt.Dimension(50, 15));

        sourceDataState_TextField.setToolTipText("Target Variable Data Type");

        javax.swing.GroupLayout sourceDataStatePaneLayout = new javax.swing.GroupLayout(sourceDataStatePane);
        sourceDataStatePane.setLayout(sourceDataStatePaneLayout);
        sourceDataStatePaneLayout.setHorizontalGroup(
            sourceDataStatePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sourceDataStatePaneLayout.createSequentialGroup()
                .addComponent(sourceDataState_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sourceDataState_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        sourceDataStatePaneLayout.setVerticalGroup(
            sourceDataStatePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sourceDataStatePaneLayout.createSequentialGroup()
                .addGroup(sourceDataStatePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sourceDataStatePaneLayout.createSequentialGroup()
                        .addComponent(sourceDataState_Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(sourceDataState_TextField))
                .addContainerGap())
        );
        sourceDataStatePane.setLayer(sourceDataState_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        sourceDataStatePane.setLayer(sourceDataState_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        isCollection_LayeredPane.setName(""); // NOI18N
        isCollection_LayeredPane.setPreferredSize(new java.awt.Dimension(400, 30));

        isCollection_Label.setText("Is Collection :");
        isCollection_Label.setPreferredSize(new java.awt.Dimension(50, 15));

        javax.swing.GroupLayout isCollection_LayeredPaneLayout = new javax.swing.GroupLayout(isCollection_LayeredPane);
        isCollection_LayeredPane.setLayout(isCollection_LayeredPaneLayout);
        isCollection_LayeredPaneLayout.setHorizontalGroup(
            isCollection_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(isCollection_LayeredPaneLayout.createSequentialGroup()
                .addComponent(isCollection_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(isCollection_CheckBox)
                .addGap(305, 305, 305))
        );
        isCollection_LayeredPaneLayout.setVerticalGroup(
            isCollection_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(isCollection_LayeredPaneLayout.createSequentialGroup()
                .addGroup(isCollection_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(isCollection_CheckBox)
                    .addComponent(isCollection_Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        isCollection_LayeredPane.setLayer(isCollection_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        isCollection_LayeredPane.setLayer(isCollection_CheckBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout fromParameterPaneLayout = new javax.swing.GroupLayout(fromParameterPane);
        fromParameterPane.setLayout(fromParameterPaneLayout);
        fromParameterPaneLayout.setHorizontalGroup(
            fromParameterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, fromParameterPaneLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(fromParameterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(isCollection_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sourceDataTypePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(fromParameterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(sourceNamePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sourceDataStatePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        fromParameterPaneLayout.setVerticalGroup(
            fromParameterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fromParameterPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sourceNamePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sourceDataStatePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sourceDataTypePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(isCollection_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        fromParameterPane.setLayer(sourceNamePane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        fromParameterPane.setLayer(sourceDataTypePane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        fromParameterPane.setLayer(sourceDataStatePane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        fromParameterPane.setLayer(isCollection_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        toParameterPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "To", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB Demi", 0, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        targetPane.setPreferredSize(new java.awt.Dimension(400, 35));

        targetRef_Label.setText("Source :");

        javax.swing.GroupLayout targetPaneLayout = new javax.swing.GroupLayout(targetPane);
        targetPane.setLayout(targetPaneLayout);
        targetPaneLayout.setHorizontalGroup(
            targetPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(targetPaneLayout.createSequentialGroup()
                .addComponent(targetRef_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(targetRef_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        targetPaneLayout.setVerticalGroup(
            targetPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(targetPaneLayout.createSequentialGroup()
                .addGroup(targetPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(targetRef_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(targetRef_Label))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        targetPane.setLayer(targetRef_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        targetPane.setLayer(targetRef_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout toParameterPaneLayout = new javax.swing.GroupLayout(toParameterPane);
        toParameterPane.setLayout(toParameterPaneLayout);
        toParameterPaneLayout.setHorizontalGroup(
            toParameterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toParameterPaneLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(targetPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(62, 62, 62))
        );
        toParameterPaneLayout.setVerticalGroup(
            toParameterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toParameterPaneLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(targetPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        toParameterPane.setLayer(targetPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cancel_Button.setText("Cancel");
        cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_ButtonActionPerformed(evt);
            }
        });

        save_Button.setText("Save");
        save_Button.setToolTipText("");
        save_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(save_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancel_Button))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(save_Button)
                .addComponent(cancel_Button))
        );
        jLayeredPane1.setLayer(cancel_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(save_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layeredPaneLayout = new javax.swing.GroupLayout(layeredPane);
        layeredPane.setLayout(layeredPaneLayout);
        layeredPaneLayout.setHorizontalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layeredPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layeredPaneLayout.createSequentialGroup()
                        .addGroup(layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fromParameterPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toParameterPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layeredPaneLayout.setVerticalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fromParameterPane, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(toParameterPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layeredPane.setLayer(fromParameterPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPane.setLayer(toParameterPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPane.setLayer(jLayeredPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean validateField() {
        if (this.sourceName_TextField.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
            JOptionPane.showMessageDialog(this, "Parameter name can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n
        //o       if (this.sourceDataType_TextField.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
//        JOptionPane.showMessageDialog(this, "Parameter datatype can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
        //o           sourceDataTypePane1se;
        //o       }//I18n
//        if (expression_RadioButton.isSelected()) {
//            if (this.script_TextArea.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
//                JOptionPane.showMessageDialog(this, "Script can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
//                return false;
//            }//I18n
//        } else {
        String targetRefId = ((ComboBoxValue<TProperty>) targetRef_ComboBox.getSelectedItem()).getId();
        if (targetRefId == null /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
            JOptionPane.showMessageDialog(this, "Target can't be blank", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n

//        }
        return true;
    }

    private void save_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_ButtonActionPerformed

        if (!validateField()) {
            return;
        }
        RowValue rowValue = (RowValue) this.getEntity();
        Object[] row = rowValue.getRow();

        if (isNew) {
            dataOutputAssociation = new TDataOutputAssociation();
            dataOutputAssociation.setId(NBModelerUtil.getAutoGeneratedStringId());
            dataOutput = new TDataOutput();
            dataOutput.setId(NBModelerUtil.getAutoGeneratedStringId());
        }

        dataOutput.setName(sourceName_TextField.getText());
        if (!sourceDataState_TextField.getText().trim().isEmpty()) { //BUG : Id change on every update
            TDataState dataState = new TDataState();
            dataState.setId(NBModelerUtil.getAutoGeneratedStringId());
            dataState.setName(sourceDataState_TextField.getText().trim());
            dataOutput.setDataState(dataState);
        }
        dataOutput.setItemSubjectRef(((ComboBoxValue<TItemDefinition>) sourceDataType_ComboBox.getSelectedItem()).getId());
        dataOutput.setIsCollection(isCollection_CheckBox.isSelected());

        TProperty propertySourceRef = ((ComboBoxValue<TProperty>) targetRef_ComboBox.getSelectedItem()).getValue();
        this.property = propertySourceRef;
        dataOutputAssociation.setTargetRef(propertySourceRef.getId());
        dataOutputAssociation.setAssignment(null);
//        }
        dataOutputAssociation.setSourceRef(dataOutput.getId());

        row[0] = new Object[]{dataOutput, dataOutputAssociation, property};
        row[1] = dataOutput.getName();
        row[2] = property.getName();

        saveActionPerformed(evt);
    }//GEN-LAST:event_save_ButtonActionPerformed

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        cancelActionPerformed(evt);
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    private void sourceDataType_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sourceDataType_ActionMousePressed
        actionPanelType = "dataType";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_sourceDataType_ActionMousePressed

    EntityComponent getActionPanel() {
        if ("dataType".equals(actionPanelType)) {
            return new ItemDefinitionPanel(modelerFile);
        } else {
            return null;
        }
    }

    ComboBoxValue getSelectedActionItem() {
        if ("dataType".equals(actionPanelType)) {
            return (ComboBoxValue) sourceDataType_ComboBox.getSelectedItem();
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
                sourceDataType_ComboBox.addItem(comboBoxValue);
                sourceDataType_ComboBox.setSelectedItem(comboBoxValue);
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
                    sourceDataType_ComboBox.removeItem(comboBoxValue);
                }
            }
        } catch (IllegalStateException ex) {
            System.out.println("EX : " + ex.toString());
        }
    }//GEN-LAST:event_deleteItem_MenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel_Button;
    private javax.swing.JMenuItem createItem_MenuItem;
    private javax.swing.JMenuItem deleteItem_MenuItem;
    private javax.swing.JMenuItem editItem_MenuItem;
    private javax.swing.JLayeredPane fromParameterPane;
    private javax.swing.JCheckBox isCollection_CheckBox;
    private javax.swing.JLabel isCollection_Label;
    private javax.swing.JLayeredPane isCollection_LayeredPane;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane layeredPane;
    private javax.swing.JButton save_Button;
    private javax.swing.JPopupMenu setting_PopupMenu;
    private javax.swing.JLayeredPane sourceDataStatePane;
    private javax.swing.JLabel sourceDataState_Label;
    private javax.swing.JTextField sourceDataState_TextField;
    private javax.swing.JLayeredPane sourceDataTypePane;
    private javax.swing.JButton sourceDataType_Action;
    private javax.swing.JComboBox sourceDataType_ComboBox;
    protected javax.swing.JLabel sourceDataType_Label;
    private javax.swing.JLayeredPane sourceNamePane;
    private javax.swing.JLabel sourceName_Label;
    private javax.swing.JTextField sourceName_TextField;
    private javax.swing.JLayeredPane targetPane;
    private javax.swing.JComboBox targetRef_ComboBox;
    private javax.swing.JLabel targetRef_Label;
    private javax.swing.JLayeredPane toParameterPane;
    // End of variables declaration//GEN-END:variables

}
/*
 Pane  400 30      10 10 0
 Label 80
 TextField 300

 */
