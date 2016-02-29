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
import org.netbeans.jbpmn.spec.TAssignment;
import org.netbeans.jbpmn.spec.TDataInput;
import org.netbeans.jbpmn.spec.TDataInputAssociation;
import org.netbeans.jbpmn.spec.TDataState;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TExpression;
import org.netbeans.jbpmn.spec.TFormalExpression;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TProcess;
import org.netbeans.jbpmn.spec.TProperty;
import org.netbeans.jbpmn.spec.extend.LanguageManager;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.ModelerFile;

public class InputParameterPanel extends EntityComponent {

    private ModelerFile modelerFile;
    private TDefinitions definition;
    private TProcess process;
    private Boolean isNew;
    private String actionPanelType;

    /**
     * Creates new form InputParameterPanel
     */
    public InputParameterPanel(ModelerFile modelerFile) {
        super((Frame) null, true);
        initComponents();
        this.setSize(575, 300);
        this.modelerFile = modelerFile;
        definition = (TDefinitions) modelerFile.getDefinitionElement();
        process = (TProcess) modelerFile.getRootElement();

        variable_RadioButton.setSelected(true);
        variablePane.setVisible(true);
        expressionPane.setVisible(false);
        scriptType_ComboBox.setEditable(true);

    }

    void initDataType() {
        evaluatesToType_ComboBox.removeAllItems();
        dataType_ComboBox.removeAllItems();
        for (ComboBoxValue<TItemDefinition> itemDefinition : BPMNModelUtil.getItemDefinitionList(modelerFile)) {
            evaluatesToType_ComboBox.addItem(itemDefinition);
            dataType_ComboBox.addItem(itemDefinition);
        }
    }

    @Override
    public void init() {
        initSourceRefComboBox();
        sourceRef_ComboBox.setSelectedItem(new ComboBoxValue<TProperty>(null, null, ""));
        initScriptTypeComboBox();
        scriptType_ComboBox.setSelectedItem(LanguageManager.getLanguage(null));
        script_TextArea.setText("");
        initDataType();
        evaluatesToType_ComboBox.setSelectedItem(new ComboBoxValue<TItemDefinition>(null, null, ""));
        targetName_TextField.setText("");
        targetDataState_TextField.setText("");
        dataType_ComboBox.setSelectedItem(new ComboBoxValue<TItemDefinition>(null, null, ""));
        isCollection_CheckBox.setSelected(false);

    }

    @Override
    public void createEntity(Class/*<? extends Entity>*/ entityWrapperType) {
        this.setTitle("Add New Input Parameter");
        isNew = true;
        if (entityWrapperType == RowValue.class) {
            this.setEntity(new RowValue(new Object[3]));
        }
        variable_RadioButtonActivate();

    }
    TProperty property;
    TDataInputAssociation dataInputAssociation;
    TDataInput dataInput;

    @Override
    public void updateEntity(Entity entityValue) {
        this.setTitle("Update Input Parameter");
        isNew = false;
        if (entityValue.getClass() == RowValue.class) {
            this.setEntity(entityValue);
            Object[] row = ((RowValue) entityValue).getRow();
            Object key[] = (Object[]) row[0];
            property = (TProperty) key[0];
            dataInputAssociation = (TDataInputAssociation) key[1];
            dataInput = (TDataInput) key[2];

            if (dataInputAssociation.getAssignment() != null
                    && dataInputAssociation.getAssignment().getTo() != null
                    && dataInputAssociation.getAssignment().getFrom() != null) {
                expression_RadioButtonActivate();
                scriptType_ComboBox.setSelectedItem(LanguageManager.getLanguage(dataInputAssociation.getAssignment().getFrom().getLanguage()));
                script_TextArea.setText(dataInputAssociation.getAssignment().getFrom().getContent());

                TItemDefinition evaluatesToType = (TItemDefinition) definition.getRootElement(dataInputAssociation.getAssignment().getFrom().getEvaluatesToTypeRef(), TItemDefinition.class);
                if (evaluatesToType != null) {
                    for (int i = 0; i < evaluatesToType_ComboBox.getItemCount(); i++) {
                        ComboBoxValue<TItemDefinition> itemDefinition_Combo = (ComboBoxValue<TItemDefinition>) evaluatesToType_ComboBox.getItemAt(i);
                        if (itemDefinition_Combo.getValue() != null && itemDefinition_Combo.getValue().getId().equals(evaluatesToType.getId())) {
                            evaluatesToType_ComboBox.setSelectedItem(itemDefinition_Combo);
                            break;
                        }
                    }
                }
            } else {
                variable_RadioButtonActivate();
                TProperty propertySourceRef = process.getProperty(dataInputAssociation.getSourceRef());
                sourceRef_ComboBox.setSelectedItem(new ComboBoxValue<TProperty>(propertySourceRef.getId(), propertySourceRef, propertySourceRef.getName() == null ? propertySourceRef.getId() : propertySourceRef.getName()));
            }

            targetName_TextField.setText(dataInput.getName());
            targetDataState_TextField.setText(dataInput.getDataState() == null ? null : dataInput.getDataState().getName());
            TItemDefinition itemSubjectRef = (TItemDefinition) definition.getRootElement(dataInput.getItemSubjectRef(), TItemDefinition.class);
            if (itemSubjectRef != null) {
                for (int i = 0; i < dataType_ComboBox.getItemCount(); i++) {
                    ComboBoxValue<TItemDefinition> itemDefinition_Combo = (ComboBoxValue<TItemDefinition>) dataType_ComboBox.getItemAt(i);
                    if (itemDefinition_Combo.getValue() != null && itemDefinition_Combo.getValue().getId().equals(itemSubjectRef.getId())) {
                        dataType_ComboBox.setSelectedItem(itemDefinition_Combo);
                        break;
                    }
                }
            }
            isCollection_CheckBox.setSelected(dataInput.isIsCollection());

        }
    }

    private void initScriptTypeComboBox() {
        scriptType_ComboBox.removeAllItems();
        for (ComboBoxValue<String> language : LanguageManager.getLanguageList()) {
            scriptType_ComboBox.addItem(language);
        }
    }

    private void initSourceRefComboBox() {
        sourceRef_ComboBox.removeAllItems();
        sourceRef_ComboBox.addItem(new ComboBoxValue<TProperty>(null, null, ""));
        for (TProperty property_Tmp : process.getProperty()) {
            sourceRef_ComboBox.addItem(new ComboBoxValue<TProperty>(property_Tmp.getId(), property_Tmp, property_Tmp.getName() == null ? property_Tmp.getId() : property_Tmp.getName()));
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

        parameterTypeGroup = new javax.swing.ButtonGroup();
        setting_PopupMenu = new javax.swing.JPopupMenu();
        createItem_MenuItem = new javax.swing.JMenuItem();
        editItem_MenuItem = new javax.swing.JMenuItem();
        deleteItem_MenuItem = new javax.swing.JMenuItem();
        layeredPane = new javax.swing.JLayeredPane();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        cancel_Button = new javax.swing.JButton();
        save_Button = new javax.swing.JButton();
        fromParameterPane = new javax.swing.JLayeredPane();
        parameterTypeRadioGroup = new javax.swing.JLayeredPane();
        variable_RadioButton = new javax.swing.JRadioButton();
        expression_RadioButton = new javax.swing.JRadioButton();
        parameterTypePane = new javax.swing.JLayeredPane();
        variablePane = new javax.swing.JLayeredPane();
        sourcePane = new javax.swing.JLayeredPane();
        sourceRef_Label = new javax.swing.JLabel();
        sourceRef_ComboBox = new javax.swing.JComboBox();
        expressionPane = new javax.swing.JLayeredPane();
        scriptType_Pane = new javax.swing.JLayeredPane();
        scriptType_Label = new javax.swing.JLabel();
        scriptType_ComboBox = new javax.swing.JComboBox();
        script_LayeredPane = new javax.swing.JLayeredPane();
        script_Label = new javax.swing.JLabel();
        script_ScrollPane = new javax.swing.JScrollPane();
        script_TextArea = new javax.swing.JTextArea();
        evaluateType_LayeredPane = new javax.swing.JLayeredPane();
        evaluatesToType_Label = new javax.swing.JLabel();
        evaluatesToType_ComboBox = new javax.swing.JComboBox();
        evaluatesToType_Action = new javax.swing.JButton();
        toParameterPane = new javax.swing.JLayeredPane();
        targetNamePane = new javax.swing.JLayeredPane();
        targetName_Label = new javax.swing.JLabel();
        targetName_TextField = new javax.swing.JTextField();
        targetDataStatePane = new javax.swing.JLayeredPane();
        targetDataState_Label = new javax.swing.JLabel();
        targetDataState_TextField = new javax.swing.JTextField();
        dataType_LayeredPane = new javax.swing.JLayeredPane();
        dataType_Label = new javax.swing.JLabel();
        dataType_ComboBox = new javax.swing.JComboBox();
        dataType_Action = new javax.swing.JButton();
        isCollection_LayeredPane = new javax.swing.JLayeredPane();
        isCollection_Label = new javax.swing.JLabel();
        isCollection_CheckBox = new javax.swing.JCheckBox();

        createItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/icon_plus.png"))); // NOI18N
        createItem_MenuItem.setText(org.openide.util.NbBundle.getMessage(InputParameterPanel.class, "ResourceDialog.createItem_MenuItem.text")); // NOI18N
        createItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(InputParameterPanel.class, "ResourceDialog.createItem_MenuItem.toolTipText")); // NOI18N
        createItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(createItem_MenuItem);

        editItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/edit.png"))); // NOI18N
        editItem_MenuItem.setText(org.openide.util.NbBundle.getMessage(InputParameterPanel.class, "ResourceDialog.editItem_MenuItem.text")); // NOI18N
        editItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(InputParameterPanel.class, "ResourceDialog.editItem_MenuItem.toolTipText")); // NOI18N
        editItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(editItem_MenuItem);

        deleteItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/delete.png"))); // NOI18N
        deleteItem_MenuItem.setText(org.openide.util.NbBundle.getMessage(InputParameterPanel.class, "ResourceDialog.deleteItem_MenuItem.text")); // NOI18N
        deleteItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(InputParameterPanel.class, "ResourceDialog.deleteItem_MenuItem.toolTipText")); // NOI18N
        deleteItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(deleteItem_MenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        layeredPane.setLayout(new java.awt.BorderLayout());

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
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save_Button)
                    .addComponent(cancel_Button))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jLayeredPane1.setLayer(cancel_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(save_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);

        fromParameterPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "From", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB Demi", 0, 12), new java.awt.Color(51, 51, 51))); // NOI18N
        fromParameterPane.setLayout(new java.awt.BorderLayout());

        parameterTypeRadioGroup.setPreferredSize(new java.awt.Dimension(400, 20));

        parameterTypeGroup.add(variable_RadioButton);
        variable_RadioButton.setText("Variable");
        variable_RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variable_RadioButtonActionPerformed(evt);
            }
        });

        parameterTypeGroup.add(expression_RadioButton);
        expression_RadioButton.setText("Expression");
        expression_RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expression_RadioButtonActionPerformed(evt);
            }
        });

        parameterTypePane.setLayout(new java.awt.FlowLayout());

        sourcePane.setPreferredSize(new java.awt.Dimension(400, 35));

        sourceRef_Label.setText("Source :");

        javax.swing.GroupLayout sourcePaneLayout = new javax.swing.GroupLayout(sourcePane);
        sourcePane.setLayout(sourcePaneLayout);
        sourcePaneLayout.setHorizontalGroup(
            sourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sourcePaneLayout.createSequentialGroup()
                .addComponent(sourceRef_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sourceRef_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        sourcePaneLayout.setVerticalGroup(
            sourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sourcePaneLayout.createSequentialGroup()
                .addGroup(sourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sourceRef_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sourceRef_Label))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        sourcePane.setLayer(sourceRef_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        sourcePane.setLayer(sourceRef_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout variablePaneLayout = new javax.swing.GroupLayout(variablePane);
        variablePane.setLayout(variablePaneLayout);
        variablePaneLayout.setHorizontalGroup(
            variablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(variablePaneLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(sourcePane, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );
        variablePaneLayout.setVerticalGroup(
            variablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, variablePaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(sourcePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        variablePane.setLayer(sourcePane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        parameterTypePane.add(variablePane);

        scriptType_Label.setText("Script Type :");

        javax.swing.GroupLayout scriptType_PaneLayout = new javax.swing.GroupLayout(scriptType_Pane);
        scriptType_Pane.setLayout(scriptType_PaneLayout);
        scriptType_PaneLayout.setHorizontalGroup(
            scriptType_PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scriptType_PaneLayout.createSequentialGroup()
                .addComponent(scriptType_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scriptType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        scriptType_PaneLayout.setVerticalGroup(
            scriptType_PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scriptType_PaneLayout.createSequentialGroup()
                .addGroup(scriptType_PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scriptType_Label)
                    .addComponent(scriptType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );
        scriptType_Pane.setLayer(scriptType_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        scriptType_Pane.setLayer(scriptType_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        script_Label.setText("Script :");

        script_TextArea.setColumns(20);
        script_TextArea.setRows(5);
        script_ScrollPane.setViewportView(script_TextArea);

        javax.swing.GroupLayout script_LayeredPaneLayout = new javax.swing.GroupLayout(script_LayeredPane);
        script_LayeredPane.setLayout(script_LayeredPaneLayout);
        script_LayeredPaneLayout.setHorizontalGroup(
            script_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(script_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(script_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        script_LayeredPaneLayout.setVerticalGroup(
            script_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(script_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(script_Label)
                .addGap(100, 100, 100))
            .addGroup(script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(script_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        script_LayeredPane.setLayer(script_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        script_LayeredPane.setLayer(script_ScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        evaluatesToType_Label.setText(org.openide.util.NbBundle.getMessage(InputParameterPanel.class, "ResourceDialog.evaluatesToType_Label.text")); // NOI18N

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
                .addComponent(evaluatesToType_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(evaluatesToType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(evaluatesToType_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        evaluateType_LayeredPaneLayout.setVerticalGroup(
            evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(evaluateType_LayeredPaneLayout.createSequentialGroup()
                .addGroup(evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(evaluatesToType_Action)
                    .addGroup(evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(evaluatesToType_Label)
                        .addComponent(evaluatesToType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 19, Short.MAX_VALUE))
        );
        evaluateType_LayeredPane.setLayer(evaluatesToType_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        evaluateType_LayeredPane.setLayer(evaluatesToType_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        evaluateType_LayeredPane.setLayer(evaluatesToType_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout expressionPaneLayout = new javax.swing.GroupLayout(expressionPane);
        expressionPane.setLayout(expressionPaneLayout);
        expressionPaneLayout.setHorizontalGroup(
            expressionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expressionPaneLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(expressionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(expressionPaneLayout.createSequentialGroup()
                        .addComponent(evaluateType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expressionPaneLayout.createSequentialGroup()
                        .addGroup(expressionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scriptType_Pane)
                            .addComponent(script_LayeredPane))
                        .addGap(20, 20, 20))))
        );
        expressionPaneLayout.setVerticalGroup(
            expressionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expressionPaneLayout.createSequentialGroup()
                .addComponent(scriptType_Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(script_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(evaluateType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        expressionPane.setLayer(scriptType_Pane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        expressionPane.setLayer(script_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        expressionPane.setLayer(evaluateType_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        parameterTypePane.add(expressionPane);

        javax.swing.GroupLayout parameterTypeRadioGroupLayout = new javax.swing.GroupLayout(parameterTypeRadioGroup);
        parameterTypeRadioGroup.setLayout(parameterTypeRadioGroupLayout);
        parameterTypeRadioGroupLayout.setHorizontalGroup(
            parameterTypeRadioGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parameterTypeRadioGroupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parameterTypeRadioGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(parameterTypePane, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(parameterTypeRadioGroupLayout.createSequentialGroup()
                        .addComponent(variable_RadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(expression_RadioButton)))
                .addContainerGap())
        );
        parameterTypeRadioGroupLayout.setVerticalGroup(
            parameterTypeRadioGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parameterTypeRadioGroupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parameterTypeRadioGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(variable_RadioButton)
                    .addComponent(expression_RadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(parameterTypePane, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
        );
        parameterTypeRadioGroup.setLayer(variable_RadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        parameterTypeRadioGroup.setLayer(expression_RadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        parameterTypeRadioGroup.setLayer(parameterTypePane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        fromParameterPane.add(parameterTypeRadioGroup, java.awt.BorderLayout.CENTER);

        toParameterPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "To", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB Demi", 0, 12), new java.awt.Color(51, 51, 51))); // NOI18N
        toParameterPane.setPreferredSize(new java.awt.Dimension(535, 110));

        targetNamePane.setPreferredSize(new java.awt.Dimension(400, 30));

        targetName_Label.setText("Name :");
        targetName_Label.setPreferredSize(new java.awt.Dimension(50, 15));

        targetName_TextField.setToolTipText("Target Variable Name");

        javax.swing.GroupLayout targetNamePaneLayout = new javax.swing.GroupLayout(targetNamePane);
        targetNamePane.setLayout(targetNamePaneLayout);
        targetNamePaneLayout.setHorizontalGroup(
            targetNamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(targetNamePaneLayout.createSequentialGroup()
                .addComponent(targetName_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(targetName_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        targetNamePaneLayout.setVerticalGroup(
            targetNamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(targetNamePaneLayout.createSequentialGroup()
                .addGroup(targetNamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(targetName_Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(targetName_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        targetNamePane.setLayer(targetName_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        targetNamePane.setLayer(targetName_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        targetDataStatePane.setName(""); // NOI18N
        targetDataStatePane.setPreferredSize(new java.awt.Dimension(400, 30));

        targetDataState_Label.setText("Data State :");
        targetDataState_Label.setPreferredSize(new java.awt.Dimension(50, 15));

        targetDataState_TextField.setToolTipText("Target Variable Data Type");

        javax.swing.GroupLayout targetDataStatePaneLayout = new javax.swing.GroupLayout(targetDataStatePane);
        targetDataStatePane.setLayout(targetDataStatePaneLayout);
        targetDataStatePaneLayout.setHorizontalGroup(
            targetDataStatePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(targetDataStatePaneLayout.createSequentialGroup()
                .addComponent(targetDataState_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(targetDataState_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        targetDataStatePaneLayout.setVerticalGroup(
            targetDataStatePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(targetDataStatePaneLayout.createSequentialGroup()
                .addGroup(targetDataStatePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(targetDataStatePaneLayout.createSequentialGroup()
                        .addComponent(targetDataState_Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(targetDataState_TextField))
                .addContainerGap())
        );
        targetDataStatePane.setLayer(targetDataState_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        targetDataStatePane.setLayer(targetDataState_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        dataType_Label.setText("Data Type :");

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
                .addComponent(dataType_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dataType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dataType_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        dataType_LayeredPaneLayout.setVerticalGroup(
            dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataType_LayeredPaneLayout.createSequentialGroup()
                .addGroup(dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dataType_Label)
                        .addComponent(dataType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dataType_Action))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        dataType_LayeredPane.setLayer(dataType_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dataType_LayeredPane.setLayer(dataType_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dataType_LayeredPane.setLayer(dataType_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

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

        javax.swing.GroupLayout toParameterPaneLayout = new javax.swing.GroupLayout(toParameterPane);
        toParameterPane.setLayout(toParameterPaneLayout);
        toParameterPaneLayout.setHorizontalGroup(
            toParameterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, toParameterPaneLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(toParameterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(isCollection_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(targetNamePane, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(targetDataStatePane, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        toParameterPaneLayout.setVerticalGroup(
            toParameterPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toParameterPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(targetNamePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(targetDataStatePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(isCollection_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        toParameterPane.setLayer(targetNamePane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        toParameterPane.setLayer(targetDataStatePane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        toParameterPane.setLayer(dataType_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        toParameterPane.setLayer(isCollection_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(layeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(fromParameterPane)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(toParameterPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(419, 419, 419)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 4, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(layeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fromParameterPane, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(toParameterPane, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void variable_RadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_variable_RadioButtonActionPerformed
        variable_RadioButtonActivated();
    }//GEN-LAST:event_variable_RadioButtonActionPerformed

    private void expression_RadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expression_RadioButtonActionPerformed
        expression_RadioButtonActivated();
    }//GEN-LAST:event_expression_RadioButtonActionPerformed

    void variable_RadioButtonActivate() {
        variable_RadioButton.setSelected(true);
        variable_RadioButtonActivated();
    }

    void expression_RadioButtonActivate() {
        expression_RadioButton.setSelected(true);
        expression_RadioButtonActivated();
    }

    void variable_RadioButtonActivated() {
        variable_RadioButton.setSelected(true);
        variablePane.setVisible(true);
        expressionPane.setVisible(false);
        this.setSize(575, 385);
    }

    void expression_RadioButtonActivated() {
        expression_RadioButton.setSelected(true);
        variablePane.setVisible(false);
        expressionPane.setVisible(true);
        this.setSize(575, 560);
    }

    private boolean validateField() {
        if (this.targetName_TextField.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
            JOptionPane.showMessageDialog(this, "Parameter name can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n
//        if (this.targetDataType_TextField.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
//            JOptionPane.showMessageDialog(this, "Parameter datatype can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
//            return false;
//        }//I18n
        if (expression_RadioButton.isSelected()) {
            if (this.script_TextArea.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
                JOptionPane.showMessageDialog(this, "Script can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
                return false;
            }//I18n
        } else {
            String sourceRefId = ((ComboBoxValue<TProperty>) sourceRef_ComboBox.getSelectedItem()).getId();
            if (sourceRefId == null /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
                JOptionPane.showMessageDialog(this, "Source can't be blank", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
                return false;
            }//I18n

        }
        return true;
    }

    private void save_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_ButtonActionPerformed
        if (!validateField()) {
            return;
        }
        RowValue rowValue = (RowValue) this.getEntity();
        Object[] row = rowValue.getRow();
        ComboBoxValue<String> scriptType_comboBoxValue;
        if (scriptType_ComboBox.getSelectedItem() instanceof String) {
            scriptType_comboBoxValue = new ComboBoxValue<String>((String) scriptType_ComboBox.getSelectedItem(), (String) scriptType_ComboBox.getSelectedItem());
        } else {
            scriptType_comboBoxValue = (ComboBoxValue<String>) scriptType_ComboBox.getSelectedItem();
        }

        if (isNew) {
            dataInputAssociation = new TDataInputAssociation();
            dataInputAssociation.setId(NBModelerUtil.getAutoGeneratedStringId());
            dataInput = new TDataInput();
            dataInput.setId(NBModelerUtil.getAutoGeneratedStringId());
        }

        dataInput.setName(targetName_TextField.getText());
        if (!targetDataState_TextField.getText().trim().isEmpty()) { //BUG : Id change on every update
            TDataState dataState = new TDataState();
            dataState.setId(NBModelerUtil.getAutoGeneratedStringId());
            dataState.setName(targetDataState_TextField.getText().trim());
            dataInput.setDataState(dataState);
        }
        dataInput.setItemSubjectRef(((ComboBoxValue<TItemDefinition>) dataType_ComboBox.getSelectedItem()).getId());
        dataInput.setIsCollection(isCollection_CheckBox.isSelected());

        if (expression_RadioButton.isSelected()) {
            if (dataInputAssociation.getAssignment() == null) {
                TAssignment assignment = new TAssignment();
                assignment.setId(NBModelerUtil.getAutoGeneratedStringId());
                dataInputAssociation.setAssignment(assignment);
            }
            if (dataInputAssociation.getAssignment().getTo() == null) {
                TExpression expression = new TExpression();
                expression.setId(NBModelerUtil.getAutoGeneratedStringId());
                dataInputAssociation.getAssignment().setTo(expression);
            }
            dataInputAssociation.getAssignment().getTo().setContent(dataInput.getId());
            if (dataInputAssociation.getAssignment().getFrom() == null) {
                TFormalExpression formalExpression = new TFormalExpression();
                formalExpression.setId(NBModelerUtil.getAutoGeneratedStringId());
                dataInputAssociation.getAssignment().setFrom(formalExpression);
            }
            dataInputAssociation.getAssignment().getFrom().setLanguage(scriptType_comboBoxValue.getValue());
            dataInputAssociation.getAssignment().getFrom().setContent(script_TextArea.getText());
            dataInputAssociation.getAssignment().getFrom().setEvaluatesToTypeRef(((ComboBoxValue<TItemDefinition>) evaluatesToType_ComboBox.getSelectedItem()).getId());

            dataInputAssociation.setSourceRef(null);
        } else {
            TProperty propertySourceRef = ((ComboBoxValue<TProperty>) sourceRef_ComboBox.getSelectedItem()).getValue();
            this.property = propertySourceRef;
            dataInputAssociation.setSourceRef(propertySourceRef.getId());
            dataInputAssociation.setAssignment(null);
        }
        dataInputAssociation.setTargetRef(dataInput.getId());

        row[0] = new Object[]{property, dataInputAssociation, dataInput};
        row[1] = dataInputAssociation.getAssignment() != null ? scriptType_comboBoxValue.getDisplayValue() + " Expression" : property.getName();
        row[2] = dataInput.getName();

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
        if ("evaluatesToType".equals(actionPanelType)) {
            return new ItemDefinitionPanel(modelerFile);
        } else if ("dataType".equals(actionPanelType)) {
            return new ItemDefinitionPanel(modelerFile);
        } else {
            return null;
        }
    }

    ComboBoxValue getSelectedActionItem() {
        if ("evaluatesToType".equals(actionPanelType)) {
            return (ComboBoxValue) evaluatesToType_ComboBox.getSelectedItem();
        } else if ("dataType".equals(actionPanelType)) {
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

            if ("evaluatesToType".equals(actionPanelType)) {
                evaluatesToType_ComboBox.addItem(comboBoxValue);
                dataType_ComboBox.addItem(comboBoxValue);
                evaluatesToType_ComboBox.setSelectedItem(comboBoxValue);
                definition.addItemDefinition((TItemDefinition) comboBoxValue.getValue());
            } else if ("dataType".equals(actionPanelType)) {
                evaluatesToType_ComboBox.addItem(comboBoxValue);
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
            if ("evaluatesToType".equals(actionPanelType)) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Data Type ?", "Delete Data Type", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    definition.removeRootElement((TItemDefinition) comboBoxValue.getValue());
                    evaluatesToType_ComboBox.removeItem(comboBoxValue);
                }
            } else if ("dataType".equals(actionPanelType)) {
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

    private void evaluatesToType_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_evaluatesToType_ActionMousePressed
        actionPanelType = "evaluatesToType";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_evaluatesToType_ActionMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel_Button;
    private javax.swing.JMenuItem createItem_MenuItem;
    private javax.swing.JButton dataType_Action;
    private javax.swing.JComboBox dataType_ComboBox;
    protected javax.swing.JLabel dataType_Label;
    private javax.swing.JLayeredPane dataType_LayeredPane;
    private javax.swing.JMenuItem deleteItem_MenuItem;
    private javax.swing.JMenuItem editItem_MenuItem;
    private javax.swing.JLayeredPane evaluateType_LayeredPane;
    private javax.swing.JButton evaluatesToType_Action;
    private javax.swing.JComboBox evaluatesToType_ComboBox;
    protected javax.swing.JLabel evaluatesToType_Label;
    private javax.swing.JLayeredPane expressionPane;
    private javax.swing.JRadioButton expression_RadioButton;
    private javax.swing.JLayeredPane fromParameterPane;
    private javax.swing.JCheckBox isCollection_CheckBox;
    private javax.swing.JLabel isCollection_Label;
    private javax.swing.JLayeredPane isCollection_LayeredPane;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane layeredPane;
    private javax.swing.ButtonGroup parameterTypeGroup;
    private javax.swing.JLayeredPane parameterTypePane;
    private javax.swing.JLayeredPane parameterTypeRadioGroup;
    private javax.swing.JButton save_Button;
    private javax.swing.JComboBox scriptType_ComboBox;
    private javax.swing.JLabel scriptType_Label;
    private javax.swing.JLayeredPane scriptType_Pane;
    private javax.swing.JLabel script_Label;
    private javax.swing.JLayeredPane script_LayeredPane;
    private javax.swing.JScrollPane script_ScrollPane;
    private javax.swing.JTextArea script_TextArea;
    private javax.swing.JPopupMenu setting_PopupMenu;
    private javax.swing.JLayeredPane sourcePane;
    private javax.swing.JComboBox sourceRef_ComboBox;
    private javax.swing.JLabel sourceRef_Label;
    private javax.swing.JLayeredPane targetDataStatePane;
    private javax.swing.JLabel targetDataState_Label;
    private javax.swing.JTextField targetDataState_TextField;
    private javax.swing.JLayeredPane targetNamePane;
    private javax.swing.JLabel targetName_Label;
    private javax.swing.JTextField targetName_TextField;
    private javax.swing.JLayeredPane toParameterPane;
    private javax.swing.JLayeredPane variablePane;
    private javax.swing.JRadioButton variable_RadioButton;
    // End of variables declaration//GEN-END:variables

}
/*
 Pane  400 30      10 10 0
 Label 80
 TextField 300

 */
