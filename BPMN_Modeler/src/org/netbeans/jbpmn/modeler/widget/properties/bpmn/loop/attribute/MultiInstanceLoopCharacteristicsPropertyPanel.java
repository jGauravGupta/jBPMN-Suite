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
package org.netbeans.jbpmn.modeler.widget.properties.bpmn.loop.attribute;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import org.netbeans.jbpmn.modeler.specification.bpmn.util.BPMNModelUtil;
import org.netbeans.jbpmn.modeler.widget.properties.io_specification.DataItemPanel;
import org.netbeans.jbpmn.modeler.widget.properties.itemdefinition.ItemDefinitionPanel;
import org.netbeans.jbpmn.spec.TActivity;
import org.netbeans.jbpmn.spec.TDataInput;
import org.netbeans.jbpmn.spec.TDataOutput;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TFormalExpression;
import org.netbeans.jbpmn.spec.TItemDefinition;
import org.netbeans.jbpmn.spec.TMultiInstanceLoopCharacteristics;
import org.netbeans.jbpmn.spec.extend.LanguageManager;
import org.netbeans.jbpmn.spec.extend.TDataParam;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.properties.embedded.GenericEmbeddedEditor;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;

public class MultiInstanceLoopCharacteristicsPropertyPanel extends GenericEmbeddedEditor<TMultiInstanceLoopCharacteristics> {

    private TMultiInstanceLoopCharacteristics milCharacteristics;
    private ModelerFile modelerFile;
    private TDefinitions definition;
    TActivity activitySpec;
    private String actionPanelType;

    @Override
    public TMultiInstanceLoopCharacteristics getValue() {
        //Completion Condition
        if (!milCharacteristics.isIsSequential()) {
            if (milCharacteristics.getCompletionCondition() == null) {
                milCharacteristics.setCompletionCondition(TFormalExpression.getNewInstance());
            }
            BPMNModelUtil.setExpressionPanelValue(milCharacteristics.getCompletionCondition(), completionConditionExpression_language_ComboBox, completionConditionExpression_script_TextArea, completionConditionExpression_evaluatesToType_ComboBox);
            if (milCharacteristics.getCompletionCondition().isEmpty()) {
                milCharacteristics.setCompletionCondition(null);
            }
        }
        //Input DataItem
        if (inputDataCollection_RadioButton.isSelected()) {
            milCharacteristics.setLoopCardinality(null);
            ComboBoxValue<TDataInput> dataInputComboboxValue = (ComboBoxValue<TDataInput>) loopDataInputRef_ComboBox.getSelectedItem();
            milCharacteristics.setLoopDataInputRef(dataInputComboboxValue.getId());
            if (inputDataItem_TextField.getText().trim().isEmpty()) {
                milCharacteristics.setInputDataItem(null);
            }
        } else if (expression_RadioButton.isSelected()) {
            milCharacteristics.setLoopDataInputRef(null);
            milCharacteristics.setInputDataItem(null);
            if (milCharacteristics.getLoopCardinality() == null) {
                milCharacteristics.setLoopCardinality(TFormalExpression.getNewInstance());
            }
            BPMNModelUtil.setExpressionPanelValue(milCharacteristics.getLoopCardinality(), inputExpression_language_ComboBox, inputExpression_script_TextArea, inputExpression_evaluatesToType_ComboBox);
            if (milCharacteristics.getLoopCardinality().isEmpty()) {
                milCharacteristics.setLoopCardinality(null);
            }
        }

        if (produceOutput_CheckBox.isSelected()) {
            ComboBoxValue<TDataOutput> dataOutputComboboxValue = (ComboBoxValue<TDataOutput>) loopDataOutputRef_ComboBox.getSelectedItem();
            milCharacteristics.setLoopDataOutputRef(dataOutputComboboxValue.getId());
            if (outputDataItem_TextField.getText().trim().isEmpty()) {
                milCharacteristics.setOutputDataItem(null);
            }
        } else {
            milCharacteristics.setLoopDataOutputRef(null);
            milCharacteristics.setOutputDataItem(null);
        }

        return milCharacteristics;
    }

    @Override
    public void setValue(TMultiInstanceLoopCharacteristics miLoop) {
        this.milCharacteristics = miLoop;
        initScriptTypeComboBox();
        initEvaluatesToTypeComboBox();
        initloopDataInputRefComboBox();

        //Completion Condition
        if (miLoop.isIsSequential()) {
            if (jTabbedPane1.getTabCount() == 4) {
                jTabbedPane1.removeTabAt(3);
            }
        } else {
            jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.completionCondition_Panel.TabConstraints.tabTitle"), completionCondition_Panel); // NOI18N
        }
        if (!miLoop.isIsSequential() && miLoop.getCompletionCondition() != null) {
            BPMNModelUtil.initExpressionPanel(miLoop.getCompletionCondition(), completionConditionExpression_language_ComboBox, completionConditionExpression_script_TextArea, completionConditionExpression_evaluatesToType_ComboBox);
        }
        //Input DataItem
        if (miLoop.getLoopCardinality() == null) {
            inputDataCollection_RadioButtonActivate();
            if (activitySpec.getIoSpecification() != null) {
                TDataInput loopDataInput = activitySpec.getIoSpecification().getDataInput(miLoop.getLoopDataInputRef());
                if (loopDataInput != null) {
                    loopDataInputRef_ComboBox.setSelectedItem(new ComboBoxValue<TDataInput>(loopDataInput.getId(), loopDataInput, loopDataInput.getName()));
                }
            }
            if (miLoop.getInputDataItem() != null) {
                inputDataItem_TextField.setText(miLoop.getInputDataItem().getName());
            }
        } else {
            expression_RadioButtonActivated();
            BPMNModelUtil.initExpressionPanel(miLoop.getLoopCardinality(), inputExpression_language_ComboBox, inputExpression_script_TextArea, inputExpression_evaluatesToType_ComboBox);
        }

        //Output DataItem
        boolean produceOutput_Checkbox_Status = false;
        if (activitySpec.getIoSpecification() != null) {
            TDataOutput loopDataOutput = activitySpec.getIoSpecification().getDataOutput(miLoop.getLoopDataOutputRef());
            if (loopDataOutput != null) {
                produceOutput_Checkbox_Status = true;
                loopDataOutputRef_ComboBox.setSelectedItem(new ComboBoxValue<TDataOutput>(loopDataOutput.getId(), loopDataOutput, loopDataOutput.getName()));
            }
        }
        if (miLoop.getOutputDataItem() != null) {
            produceOutput_Checkbox_Status = true;
            outputDataItem_TextField.setText(miLoop.getOutputDataItem().getName());
        }
        if (produceOutput_Checkbox_Status) {
            produceOutput_CheckBox.setSelected(true);
        } else {
            produceOutput_CheckBox.setSelected(false);
        }
        produceOutput_CheckBoxActionPerformed(null);

    }

    public void init() {

    }

    private void initScriptTypeComboBox() {
        inputExpression_language_ComboBox.removeAllItems();
        completionConditionExpression_language_ComboBox.removeAllItems();
        for (ComboBoxValue<String> language : LanguageManager.getLanguageList()) {
            inputExpression_language_ComboBox.addItem(language);
            completionConditionExpression_language_ComboBox.addItem(language);
        }
        inputExpression_language_ComboBox.setSelectedItem(LanguageManager.getLanguage(null));
        completionConditionExpression_language_ComboBox.setSelectedItem(LanguageManager.getLanguage(null));
    }

    private void initEvaluatesToTypeComboBox() {
        inputExpression_evaluatesToType_ComboBox.removeAllItems();
        completionConditionExpression_evaluatesToType_ComboBox.removeAllItems();
        for (ComboBoxValue<TItemDefinition> itemDefinition : BPMNModelUtil.getItemDefinitionList(modelerFile)) {
            inputExpression_evaluatesToType_ComboBox.addItem(itemDefinition);
            completionConditionExpression_evaluatesToType_ComboBox.addItem(itemDefinition);
        }
        inputExpression_evaluatesToType_ComboBox.setSelectedItem(new ComboBoxValue<TItemDefinition>(null, null, ""));
        completionConditionExpression_evaluatesToType_ComboBox.setSelectedItem(new ComboBoxValue<TItemDefinition>(null, null, ""));
    }

    private void initloopDataInputRefComboBox() {
        loopDataInputRef_ComboBox.removeAllItems();
        loopDataInputRef_ComboBox.addItem(new ComboBoxValue<TDataInput>(null, null, ""));
        loopDataOutputRef_ComboBox.removeAllItems();
        loopDataOutputRef_ComboBox.addItem(new ComboBoxValue<TDataInput>(null, null, ""));
        if (activitySpec.getIoSpecification() != null) {
            for (TDataInput dataInput : activitySpec.getIoSpecification().getDataInput()) {
                loopDataInputRef_ComboBox.addItem(new ComboBoxValue<TDataInput>(dataInput.getId(), dataInput, dataInput.getName()));
            }
            for (TDataOutput dataOutput : activitySpec.getIoSpecification().getDataOutput()) {
                loopDataOutputRef_ComboBox.addItem(new ComboBoxValue<TDataOutput>(dataOutput.getId(), dataOutput, dataOutput.getName()));
            }
        }
        loopDataInputRef_ComboBox.setSelectedItem(new ComboBoxValue<TDataInput>(null, null, ""));
        loopDataOutputRef_ComboBox.setSelectedItem(new ComboBoxValue<TDataInput>(null, null, ""));
    }

    /**
     * Creates new form LoopCharacteristicsPropertyPanel
     */
    public MultiInstanceLoopCharacteristicsPropertyPanel(ModelerFile modelerFile, TActivity activitySpec) {
        this.activitySpec = activitySpec;
        this.modelerFile = modelerFile;
        definition = (TDefinitions) modelerFile.getDefinitionElement();
        initComponents();
        this.setSize(new Dimension(577, 387));
        this.setPreferredSize(new Dimension(577, 387));
        expression_RadioButtonActivate();
//        this.setSize(new Dimension(200, 260));
//        this.setPreferredSize(new Dimension(200, 260));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputInstanceEvalGroup = new javax.swing.ButtonGroup();
        setting_PopupMenu = new javax.swing.JPopupMenu();
        createItem_MenuItem = new javax.swing.JMenuItem();
        editItem_MenuItem = new javax.swing.JMenuItem();
        deleteItem_MenuItem = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        inputItems_Panel = new javax.swing.JPanel();
        instanceEval_LayeredPane = new javax.swing.JLayeredPane();
        instanceEval_Label = new javax.swing.JLabel();
        expression_RadioButton = new javax.swing.JRadioButton();
        inputDataCollection_RadioButton = new javax.swing.JRadioButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        inputExpression_loopCardinality_LayeredPane = new javax.swing.JLayeredPane();
        inputExpression_language_LayeredPane = new javax.swing.JLayeredPane();
        inputExpression_language_Label = new javax.swing.JLabel();
        inputExpression_language_ComboBox = new javax.swing.JComboBox();
        inputExpression_script_LayeredPane = new javax.swing.JLayeredPane();
        inputExpression_script_Label = new javax.swing.JLabel();
        inputExpression_script_ScrollPane = new javax.swing.JScrollPane();
        inputExpression_script_TextArea = new javax.swing.JTextArea();
        inputExpression_evaluateType_LayeredPane = new javax.swing.JLayeredPane();
        inputExpression_evaluatesToType_Label = new javax.swing.JLabel();
        inputExpression_evaluatesToType_ComboBox = new javax.swing.JComboBox();
        inputExpression_evaluatesToType_Action = new javax.swing.JButton();
        inputDataItems_LayeredPane = new javax.swing.JLayeredPane();
        loopDataInputRef_LayeredPane = new javax.swing.JLayeredPane();
        loopDataInputRef_Label = new javax.swing.JLabel();
        loopDataInputRef_ComboBox = new javax.swing.JComboBox();
        inputDataItem_LayeredPane = new javax.swing.JLayeredPane();
        inputDataItem_Label = new javax.swing.JLabel();
        inputDataItem_TextField = new javax.swing.JTextField();
        inputDataItem_Action = new javax.swing.JButton();
        outputItems_Panel = new javax.swing.JPanel();
        produceOutput_LayeredPane = new javax.swing.JLayeredPane();
        produceOutput_Label = new javax.swing.JLabel();
        produceOutput_CheckBox = new javax.swing.JCheckBox();
        outputDataItems_LayeredPane = new javax.swing.JLayeredPane();
        loopDataOutputRef_LayeredPane = new javax.swing.JLayeredPane();
        loopDataOutputRef_Label = new javax.swing.JLabel();
        loopDataOutputRef_ComboBox = new javax.swing.JComboBox();
        outputDataItem_LayeredPane = new javax.swing.JLayeredPane();
        outputDataItem_Label = new javax.swing.JLabel();
        outputDataItem_TextField = new javax.swing.JTextField();
        outputDataItem_Action = new javax.swing.JButton();
        completionCondition_Panel = new javax.swing.JPanel();
        completionConditionExpression_LayeredPane = new javax.swing.JLayeredPane();
        completionConditionExpression_language_LayeredPane = new javax.swing.JLayeredPane();
        completionConditionExpression_language_Label = new javax.swing.JLabel();
        completionConditionExpression_language_ComboBox = new javax.swing.JComboBox();
        completionConditionExpression_script_LayeredPane = new javax.swing.JLayeredPane();
        completionConditionExpression_script_Label = new javax.swing.JLabel();
        completionConditionExpression_script_ScrollPane = new javax.swing.JScrollPane();
        completionConditionExpression_script_TextArea = new javax.swing.JTextArea();
        completionConditionExpression_evaluateType_LayeredPane = new javax.swing.JLayeredPane();
        completionConditionExpression_evaluatesToType_Label = new javax.swing.JLabel();
        completionConditionExpression_evaluatesToType_ComboBox = new javax.swing.JComboBox();
        completionConditionExpression_evaluatesToType_Action = new javax.swing.JButton();

        createItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/icon_plus.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(createItem_MenuItem, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.createItem_MenuItem.text")); // NOI18N
        createItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.createItem_MenuItem.toolTipText")); // NOI18N
        createItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(createItem_MenuItem);

        editItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/edit.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(editItem_MenuItem, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.editItem_MenuItem.text")); // NOI18N
        editItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.editItem_MenuItem.toolTipText")); // NOI18N
        editItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(editItem_MenuItem);

        deleteItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/delete.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(deleteItem_MenuItem, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.deleteItem_MenuItem.text")); // NOI18N
        deleteItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.deleteItem_MenuItem.toolTipText")); // NOI18N
        deleteItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(deleteItem_MenuItem);

        setMaximumSize(new java.awt.Dimension(255, 189));
        setPreferredSize(new java.awt.Dimension(520, 380));

        org.openide.awt.Mnemonics.setLocalizedText(instanceEval_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.instanceEval_Label.text")); // NOI18N
        instanceEval_Label.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.instanceEval_Label.toolTipText")); // NOI18N

        inputInstanceEvalGroup.add(expression_RadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(expression_RadioButton, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.expression_RadioButton.text")); // NOI18N
        expression_RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expression_RadioButtonActionPerformed(evt);
            }
        });

        inputInstanceEvalGroup.add(inputDataCollection_RadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(inputDataCollection_RadioButton, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputDataCollection_RadioButton.text")); // NOI18N
        inputDataCollection_RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputDataCollection_RadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout instanceEval_LayeredPaneLayout = new javax.swing.GroupLayout(instanceEval_LayeredPane);
        instanceEval_LayeredPane.setLayout(instanceEval_LayeredPaneLayout);
        instanceEval_LayeredPaneLayout.setHorizontalGroup(
            instanceEval_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(instanceEval_LayeredPaneLayout.createSequentialGroup()
                .addComponent(instanceEval_Label)
                .addGap(18, 18, 18)
                .addComponent(expression_RadioButton)
                .addGap(18, 18, 18)
                .addComponent(inputDataCollection_RadioButton)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        instanceEval_LayeredPaneLayout.setVerticalGroup(
            instanceEval_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(instanceEval_LayeredPaneLayout.createSequentialGroup()
                .addGroup(instanceEval_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(instanceEval_Label)
                    .addComponent(expression_RadioButton)
                    .addComponent(inputDataCollection_RadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        instanceEval_LayeredPane.setLayer(instanceEval_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        instanceEval_LayeredPane.setLayer(expression_RadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        instanceEval_LayeredPane.setLayer(inputDataCollection_RadioButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEADING);
        flowLayout1.setAlignOnBaseline(true);
        jLayeredPane1.setLayout(flowLayout1);

        inputExpression_loopCardinality_LayeredPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputExpression_loopCardinality_LayeredPane.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        inputExpression_loopCardinality_LayeredPane.setPreferredSize(new java.awt.Dimension(517, 290));

        org.openide.awt.Mnemonics.setLocalizedText(inputExpression_language_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputExpression_language_Label.text")); // NOI18N
        inputExpression_language_Label.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputExpression_language_Label.toolTipText")); // NOI18N

        javax.swing.GroupLayout inputExpression_language_LayeredPaneLayout = new javax.swing.GroupLayout(inputExpression_language_LayeredPane);
        inputExpression_language_LayeredPane.setLayout(inputExpression_language_LayeredPaneLayout);
        inputExpression_language_LayeredPaneLayout.setHorizontalGroup(
            inputExpression_language_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputExpression_language_LayeredPaneLayout.createSequentialGroup()
                .addComponent(inputExpression_language_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputExpression_language_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inputExpression_language_LayeredPaneLayout.setVerticalGroup(
            inputExpression_language_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputExpression_language_LayeredPaneLayout.createSequentialGroup()
                .addGroup(inputExpression_language_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputExpression_language_Label)
                    .addComponent(inputExpression_language_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );
        inputExpression_language_LayeredPane.setLayer(inputExpression_language_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        inputExpression_language_LayeredPane.setLayer(inputExpression_language_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(inputExpression_script_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputExpression_script_Label.text")); // NOI18N
        inputExpression_script_Label.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputExpression_script_Label.toolTipText")); // NOI18N

        inputExpression_script_TextArea.setColumns(20);
        inputExpression_script_TextArea.setRows(5);
        inputExpression_script_ScrollPane.setViewportView(inputExpression_script_TextArea);

        javax.swing.GroupLayout inputExpression_script_LayeredPaneLayout = new javax.swing.GroupLayout(inputExpression_script_LayeredPane);
        inputExpression_script_LayeredPane.setLayout(inputExpression_script_LayeredPaneLayout);
        inputExpression_script_LayeredPaneLayout.setHorizontalGroup(
            inputExpression_script_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputExpression_script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(inputExpression_script_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputExpression_script_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
        );
        inputExpression_script_LayeredPaneLayout.setVerticalGroup(
            inputExpression_script_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputExpression_script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(inputExpression_script_Label)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(inputExpression_script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(inputExpression_script_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        inputExpression_script_LayeredPane.setLayer(inputExpression_script_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        inputExpression_script_LayeredPane.setLayer(inputExpression_script_ScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(inputExpression_evaluatesToType_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputExpression_evaluatesToType_Label.text")); // NOI18N

        inputExpression_evaluatesToType_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        inputExpression_evaluatesToType_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                inputExpression_evaluatesToType_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout inputExpression_evaluateType_LayeredPaneLayout = new javax.swing.GroupLayout(inputExpression_evaluateType_LayeredPane);
        inputExpression_evaluateType_LayeredPane.setLayout(inputExpression_evaluateType_LayeredPaneLayout);
        inputExpression_evaluateType_LayeredPaneLayout.setHorizontalGroup(
            inputExpression_evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputExpression_evaluateType_LayeredPaneLayout.createSequentialGroup()
                .addComponent(inputExpression_evaluatesToType_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputExpression_evaluatesToType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputExpression_evaluatesToType_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        inputExpression_evaluateType_LayeredPaneLayout.setVerticalGroup(
            inputExpression_evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputExpression_evaluateType_LayeredPaneLayout.createSequentialGroup()
                .addGroup(inputExpression_evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputExpression_evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(inputExpression_evaluatesToType_Label)
                        .addComponent(inputExpression_evaluatesToType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(inputExpression_evaluatesToType_Action))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        inputExpression_evaluateType_LayeredPane.setLayer(inputExpression_evaluatesToType_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        inputExpression_evaluateType_LayeredPane.setLayer(inputExpression_evaluatesToType_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        inputExpression_evaluateType_LayeredPane.setLayer(inputExpression_evaluatesToType_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout inputExpression_loopCardinality_LayeredPaneLayout = new javax.swing.GroupLayout(inputExpression_loopCardinality_LayeredPane);
        inputExpression_loopCardinality_LayeredPane.setLayout(inputExpression_loopCardinality_LayeredPaneLayout);
        inputExpression_loopCardinality_LayeredPaneLayout.setHorizontalGroup(
            inputExpression_loopCardinality_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputExpression_loopCardinality_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputExpression_loopCardinality_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputExpression_loopCardinality_LayeredPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(inputExpression_evaluateType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(inputExpression_loopCardinality_LayeredPaneLayout.createSequentialGroup()
                        .addComponent(inputExpression_script_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(inputExpression_loopCardinality_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inputExpression_loopCardinality_LayeredPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(inputExpression_language_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(30, Short.MAX_VALUE)))
        );
        inputExpression_loopCardinality_LayeredPaneLayout.setVerticalGroup(
            inputExpression_loopCardinality_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputExpression_loopCardinality_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inputExpression_script_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputExpression_evaluateType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
            .addGroup(inputExpression_loopCardinality_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inputExpression_loopCardinality_LayeredPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(inputExpression_language_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(223, Short.MAX_VALUE)))
        );
        inputExpression_loopCardinality_LayeredPane.setLayer(inputExpression_language_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        inputExpression_loopCardinality_LayeredPane.setLayer(inputExpression_script_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        inputExpression_loopCardinality_LayeredPane.setLayer(inputExpression_evaluateType_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane1.add(inputExpression_loopCardinality_LayeredPane);

        inputDataItems_LayeredPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputDataItems_LayeredPane.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        inputDataItems_LayeredPane.setPreferredSize(new java.awt.Dimension(540, 110));

        org.openide.awt.Mnemonics.setLocalizedText(loopDataInputRef_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.loopDataInputRef_Label.text")); // NOI18N
        loopDataInputRef_Label.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.loopDataInputRef_Label.toolTipText")); // NOI18N

        javax.swing.GroupLayout loopDataInputRef_LayeredPaneLayout = new javax.swing.GroupLayout(loopDataInputRef_LayeredPane);
        loopDataInputRef_LayeredPane.setLayout(loopDataInputRef_LayeredPaneLayout);
        loopDataInputRef_LayeredPaneLayout.setHorizontalGroup(
            loopDataInputRef_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loopDataInputRef_LayeredPaneLayout.createSequentialGroup()
                .addComponent(loopDataInputRef_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(loopDataInputRef_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        loopDataInputRef_LayeredPaneLayout.setVerticalGroup(
            loopDataInputRef_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loopDataInputRef_LayeredPaneLayout.createSequentialGroup()
                .addGroup(loopDataInputRef_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loopDataInputRef_Label)
                    .addComponent(loopDataInputRef_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        loopDataInputRef_LayeredPane.setLayer(loopDataInputRef_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        loopDataInputRef_LayeredPane.setLayer(loopDataInputRef_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(inputDataItem_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputDataItem_Label.text")); // NOI18N
        inputDataItem_Label.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputDataItem_Label.toolTipText")); // NOI18N

        inputDataItem_TextField.setText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputDataItem_TextField.text")); // NOI18N
        inputDataItem_TextField.setPreferredSize(new java.awt.Dimension(325, 20));

        javax.swing.GroupLayout inputDataItem_LayeredPaneLayout = new javax.swing.GroupLayout(inputDataItem_LayeredPane);
        inputDataItem_LayeredPane.setLayout(inputDataItem_LayeredPaneLayout);
        inputDataItem_LayeredPaneLayout.setHorizontalGroup(
            inputDataItem_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputDataItem_LayeredPaneLayout.createSequentialGroup()
                .addComponent(inputDataItem_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inputDataItem_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        inputDataItem_LayeredPaneLayout.setVerticalGroup(
            inputDataItem_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputDataItem_LayeredPaneLayout.createSequentialGroup()
                .addGroup(inputDataItem_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputDataItem_Label)
                    .addComponent(inputDataItem_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        inputDataItem_LayeredPane.setLayer(inputDataItem_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        inputDataItem_LayeredPane.setLayer(inputDataItem_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        inputDataItem_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        inputDataItem_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                inputDataItem_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout inputDataItems_LayeredPaneLayout = new javax.swing.GroupLayout(inputDataItems_LayeredPane);
        inputDataItems_LayeredPane.setLayout(inputDataItems_LayeredPaneLayout);
        inputDataItems_LayeredPaneLayout.setHorizontalGroup(
            inputDataItems_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputDataItems_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputDataItems_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputDataItems_LayeredPaneLayout.createSequentialGroup()
                        .addComponent(inputDataItem_LayeredPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputDataItem_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(loopDataInputRef_LayeredPane))
                .addContainerGap())
        );
        inputDataItems_LayeredPaneLayout.setVerticalGroup(
            inputDataItems_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputDataItems_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loopDataInputRef_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inputDataItems_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputDataItem_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputDataItem_Action)))
        );
        inputDataItems_LayeredPane.setLayer(loopDataInputRef_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        inputDataItems_LayeredPane.setLayer(inputDataItem_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        inputDataItems_LayeredPane.setLayer(inputDataItem_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane1.add(inputDataItems_LayeredPane);

        javax.swing.GroupLayout inputItems_PanelLayout = new javax.swing.GroupLayout(inputItems_Panel);
        inputItems_Panel.setLayout(inputItems_PanelLayout);
        inputItems_PanelLayout.setHorizontalGroup(
            inputItems_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputItems_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputItems_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputItems_PanelLayout.createSequentialGroup()
                        .addComponent(instanceEval_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(inputItems_PanelLayout.createSequentialGroup()
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        inputItems_PanelLayout.setVerticalGroup(
            inputItems_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputItems_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(instanceEval_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.inputItems_Panel.TabConstraints.tabTitle"), inputItems_Panel); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(produceOutput_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.produceOutput_Label.text")); // NOI18N
        produceOutput_Label.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.produceOutput_Label.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(produceOutput_CheckBox, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.produceOutput_CheckBox.text")); // NOI18N
        produceOutput_CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produceOutput_CheckBoxActionPerformed(evt);
            }
        });

        outputDataItems_LayeredPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.outputDataItems_LayeredPane.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        outputDataItems_LayeredPane.setPreferredSize(new java.awt.Dimension(540, 110));

        org.openide.awt.Mnemonics.setLocalizedText(loopDataOutputRef_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.loopDataOutputRef_Label.text")); // NOI18N
        loopDataOutputRef_Label.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.loopDataOutputRef_Label.toolTipText")); // NOI18N

        javax.swing.GroupLayout loopDataOutputRef_LayeredPaneLayout = new javax.swing.GroupLayout(loopDataOutputRef_LayeredPane);
        loopDataOutputRef_LayeredPane.setLayout(loopDataOutputRef_LayeredPaneLayout);
        loopDataOutputRef_LayeredPaneLayout.setHorizontalGroup(
            loopDataOutputRef_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loopDataOutputRef_LayeredPaneLayout.createSequentialGroup()
                .addComponent(loopDataOutputRef_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(loopDataOutputRef_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        loopDataOutputRef_LayeredPaneLayout.setVerticalGroup(
            loopDataOutputRef_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loopDataOutputRef_LayeredPaneLayout.createSequentialGroup()
                .addGroup(loopDataOutputRef_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loopDataOutputRef_Label)
                    .addComponent(loopDataOutputRef_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        loopDataOutputRef_LayeredPane.setLayer(loopDataOutputRef_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        loopDataOutputRef_LayeredPane.setLayer(loopDataOutputRef_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(outputDataItem_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.outputDataItem_Label.text")); // NOI18N
        outputDataItem_Label.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.outputDataItem_Label.toolTipText")); // NOI18N

        outputDataItem_TextField.setText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.outputDataItem_TextField.text")); // NOI18N
        outputDataItem_TextField.setPreferredSize(new java.awt.Dimension(325, 20));

        javax.swing.GroupLayout outputDataItem_LayeredPaneLayout = new javax.swing.GroupLayout(outputDataItem_LayeredPane);
        outputDataItem_LayeredPane.setLayout(outputDataItem_LayeredPaneLayout);
        outputDataItem_LayeredPaneLayout.setHorizontalGroup(
            outputDataItem_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputDataItem_LayeredPaneLayout.createSequentialGroup()
                .addComponent(outputDataItem_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(outputDataItem_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        outputDataItem_LayeredPaneLayout.setVerticalGroup(
            outputDataItem_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputDataItem_LayeredPaneLayout.createSequentialGroup()
                .addGroup(outputDataItem_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputDataItem_Label)
                    .addComponent(outputDataItem_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        outputDataItem_LayeredPane.setLayer(outputDataItem_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        outputDataItem_LayeredPane.setLayer(outputDataItem_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        outputDataItem_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        outputDataItem_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                outputDataItem_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout outputDataItems_LayeredPaneLayout = new javax.swing.GroupLayout(outputDataItems_LayeredPane);
        outputDataItems_LayeredPane.setLayout(outputDataItems_LayeredPaneLayout);
        outputDataItems_LayeredPaneLayout.setHorizontalGroup(
            outputDataItems_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outputDataItems_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(outputDataItems_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(outputDataItems_LayeredPaneLayout.createSequentialGroup()
                        .addComponent(outputDataItem_LayeredPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputDataItem_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(loopDataOutputRef_LayeredPane))
                .addContainerGap())
        );
        outputDataItems_LayeredPaneLayout.setVerticalGroup(
            outputDataItems_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputDataItems_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loopDataOutputRef_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(outputDataItems_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputDataItem_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputDataItem_Action)))
        );
        outputDataItems_LayeredPane.setLayer(loopDataOutputRef_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        outputDataItems_LayeredPane.setLayer(outputDataItem_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        outputDataItems_LayeredPane.setLayer(outputDataItem_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout produceOutput_LayeredPaneLayout = new javax.swing.GroupLayout(produceOutput_LayeredPane);
        produceOutput_LayeredPane.setLayout(produceOutput_LayeredPaneLayout);
        produceOutput_LayeredPaneLayout.setHorizontalGroup(
            produceOutput_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produceOutput_LayeredPaneLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(produceOutput_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(produceOutput_CheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, produceOutput_LayeredPaneLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(outputDataItems_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        produceOutput_LayeredPaneLayout.setVerticalGroup(
            produceOutput_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produceOutput_LayeredPaneLayout.createSequentialGroup()
                .addGroup(produceOutput_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(produceOutput_Label)
                    .addComponent(produceOutput_CheckBox))
                .addGap(18, 18, 18)
                .addComponent(outputDataItems_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        produceOutput_LayeredPane.setLayer(produceOutput_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        produceOutput_LayeredPane.setLayer(produceOutput_CheckBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        produceOutput_LayeredPane.setLayer(outputDataItems_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout outputItems_PanelLayout = new javax.swing.GroupLayout(outputItems_Panel);
        outputItems_Panel.setLayout(outputItems_PanelLayout);
        outputItems_PanelLayout.setHorizontalGroup(
            outputItems_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputItems_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(produceOutput_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(542, Short.MAX_VALUE))
        );
        outputItems_PanelLayout.setVerticalGroup(
            outputItems_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputItems_PanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(produceOutput_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(191, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.outputItems_Panel.TabConstraints.tabTitle"), outputItems_Panel); // NOI18N

        completionConditionExpression_LayeredPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.completionConditionExpression_LayeredPane.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(completionConditionExpression_language_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.completionConditionExpression_language_Label.text")); // NOI18N
        completionConditionExpression_language_Label.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.completionConditionExpression_language_Label.toolTipText")); // NOI18N

        javax.swing.GroupLayout completionConditionExpression_language_LayeredPaneLayout = new javax.swing.GroupLayout(completionConditionExpression_language_LayeredPane);
        completionConditionExpression_language_LayeredPane.setLayout(completionConditionExpression_language_LayeredPaneLayout);
        completionConditionExpression_language_LayeredPaneLayout.setHorizontalGroup(
            completionConditionExpression_language_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completionConditionExpression_language_LayeredPaneLayout.createSequentialGroup()
                .addComponent(completionConditionExpression_language_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(completionConditionExpression_language_ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        completionConditionExpression_language_LayeredPaneLayout.setVerticalGroup(
            completionConditionExpression_language_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completionConditionExpression_language_LayeredPaneLayout.createSequentialGroup()
                .addGroup(completionConditionExpression_language_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(completionConditionExpression_language_Label)
                    .addComponent(completionConditionExpression_language_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );
        completionConditionExpression_language_LayeredPane.setLayer(completionConditionExpression_language_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        completionConditionExpression_language_LayeredPane.setLayer(completionConditionExpression_language_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(completionConditionExpression_script_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.completionConditionExpression_script_Label.text")); // NOI18N
        completionConditionExpression_script_Label.setToolTipText(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.completionConditionExpression_script_Label.toolTipText")); // NOI18N

        completionConditionExpression_script_TextArea.setColumns(20);
        completionConditionExpression_script_TextArea.setRows(5);
        completionConditionExpression_script_ScrollPane.setViewportView(completionConditionExpression_script_TextArea);

        javax.swing.GroupLayout completionConditionExpression_script_LayeredPaneLayout = new javax.swing.GroupLayout(completionConditionExpression_script_LayeredPane);
        completionConditionExpression_script_LayeredPane.setLayout(completionConditionExpression_script_LayeredPaneLayout);
        completionConditionExpression_script_LayeredPaneLayout.setHorizontalGroup(
            completionConditionExpression_script_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completionConditionExpression_script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(completionConditionExpression_script_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(completionConditionExpression_script_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
        );
        completionConditionExpression_script_LayeredPaneLayout.setVerticalGroup(
            completionConditionExpression_script_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completionConditionExpression_script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(completionConditionExpression_script_Label)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(completionConditionExpression_script_LayeredPaneLayout.createSequentialGroup()
                .addComponent(completionConditionExpression_script_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        completionConditionExpression_script_LayeredPane.setLayer(completionConditionExpression_script_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        completionConditionExpression_script_LayeredPane.setLayer(completionConditionExpression_script_ScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(completionConditionExpression_evaluatesToType_Label, org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.completionConditionExpression_evaluatesToType_Label.text")); // NOI18N

        completionConditionExpression_evaluatesToType_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        completionConditionExpression_evaluatesToType_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                completionConditionExpression_evaluatesToType_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout completionConditionExpression_evaluateType_LayeredPaneLayout = new javax.swing.GroupLayout(completionConditionExpression_evaluateType_LayeredPane);
        completionConditionExpression_evaluateType_LayeredPane.setLayout(completionConditionExpression_evaluateType_LayeredPaneLayout);
        completionConditionExpression_evaluateType_LayeredPaneLayout.setHorizontalGroup(
            completionConditionExpression_evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completionConditionExpression_evaluateType_LayeredPaneLayout.createSequentialGroup()
                .addComponent(completionConditionExpression_evaluatesToType_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(completionConditionExpression_evaluatesToType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(completionConditionExpression_evaluatesToType_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        completionConditionExpression_evaluateType_LayeredPaneLayout.setVerticalGroup(
            completionConditionExpression_evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completionConditionExpression_evaluateType_LayeredPaneLayout.createSequentialGroup()
                .addGroup(completionConditionExpression_evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(completionConditionExpression_evaluateType_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(completionConditionExpression_evaluatesToType_Label)
                        .addComponent(completionConditionExpression_evaluatesToType_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(completionConditionExpression_evaluatesToType_Action))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        completionConditionExpression_evaluateType_LayeredPane.setLayer(completionConditionExpression_evaluatesToType_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        completionConditionExpression_evaluateType_LayeredPane.setLayer(completionConditionExpression_evaluatesToType_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        completionConditionExpression_evaluateType_LayeredPane.setLayer(completionConditionExpression_evaluatesToType_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout completionConditionExpression_LayeredPaneLayout = new javax.swing.GroupLayout(completionConditionExpression_LayeredPane);
        completionConditionExpression_LayeredPane.setLayout(completionConditionExpression_LayeredPaneLayout);
        completionConditionExpression_LayeredPaneLayout.setHorizontalGroup(
            completionConditionExpression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completionConditionExpression_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(completionConditionExpression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(completionConditionExpression_script_LayeredPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(completionConditionExpression_language_LayeredPane, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, completionConditionExpression_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(completionConditionExpression_evaluateType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        completionConditionExpression_LayeredPaneLayout.setVerticalGroup(
            completionConditionExpression_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completionConditionExpression_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(completionConditionExpression_language_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(completionConditionExpression_script_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(completionConditionExpression_evaluateType_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        completionConditionExpression_LayeredPane.setLayer(completionConditionExpression_language_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        completionConditionExpression_LayeredPane.setLayer(completionConditionExpression_script_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        completionConditionExpression_LayeredPane.setLayer(completionConditionExpression_evaluateType_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout completionCondition_PanelLayout = new javax.swing.GroupLayout(completionCondition_Panel);
        completionCondition_Panel.setLayout(completionCondition_PanelLayout);
        completionCondition_PanelLayout.setHorizontalGroup(
            completionCondition_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completionCondition_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(completionConditionExpression_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(569, Short.MAX_VALUE))
        );
        completionCondition_PanelLayout.setVerticalGroup(
            completionCondition_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completionCondition_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(completionConditionExpression_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(MultiInstanceLoopCharacteristicsPropertyPanel.class, "MultiInstanceLoopCharacteristicsPropertyPanel.completionCondition_Panel.TabConstraints.tabTitle"), completionCondition_Panel); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void createItem_MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createItem_MenuItemActionPerformed
        EntityComponent itemComponent = getActionPanel();// actionHandler.getItemPanel();

        itemComponent.init();
        itemComponent.createEntity(ComboBoxValue.class);
        itemComponent.setVisible(true);

        if (itemComponent.getDialogResult() == javax.swing.JOptionPane.OK_OPTION) {
            ComboBoxValue comboBoxValue = (ComboBoxValue) itemComponent.getEntity();

            if ("inputExpression_evaluatesToType".equals(actionPanelType) || "completionConditionExpression_evaluatesToType".equals(actionPanelType)) {
                inputExpression_evaluatesToType_ComboBox.addItem(comboBoxValue);
                completionConditionExpression_evaluatesToType_ComboBox.addItem(comboBoxValue);
                if ("inputExpression_evaluatesToType".equals(actionPanelType)) {
                    inputExpression_evaluatesToType_ComboBox.setSelectedItem(comboBoxValue);
                } else if ("completionConditionExpression_evaluatesToType".equals(actionPanelType)) {
                    completionConditionExpression_evaluatesToType_ComboBox.setSelectedItem(comboBoxValue);
                }
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
            if ("inputExpression_evaluatesToType".equals(actionPanelType) || "completionConditionExpression_evaluatesToType".equals(actionPanelType)) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Data Type ?", "Delete Data Type", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    definition.removeRootElement((TItemDefinition) comboBoxValue.getValue());
                    inputExpression_evaluatesToType_ComboBox.removeItem(comboBoxValue);
                    completionConditionExpression_evaluatesToType_ComboBox.removeItem(comboBoxValue);
                }
            }
        } catch (IllegalStateException ex) {
            System.out.println("EX : " + ex.toString());
        }
    }//GEN-LAST:event_deleteItem_MenuItemActionPerformed

    private void completionConditionExpression_evaluatesToType_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_completionConditionExpression_evaluatesToType_ActionMousePressed
        actionPanelType = "completionConditionExpression_evaluatesToType";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_completionConditionExpression_evaluatesToType_ActionMousePressed

    private void outputDataItem_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outputDataItem_ActionMousePressed
        if (!outputDataItem_Action.isEnabled()) {
            return;
        }
        EntityComponent itemComponent = new DataItemPanel(modelerFile) {
            @Override
            public TDataParam createDataParamInstance() {
                return new TDataOutput();
            }
        };
        itemComponent.init();

        if (outputDataItem_TextField.getText().trim().isEmpty() || milCharacteristics.getOutputDataItem() == null) {
            itemComponent.createEntity(ComboBoxValue.class);
            itemComponent.setVisible(true);
            if (itemComponent.getDialogResult() == javax.swing.JOptionPane.OK_OPTION) {
                ComboBoxValue<TDataOutput> comboBoxValue = (ComboBoxValue<TDataOutput>) itemComponent.getEntity();
                milCharacteristics.setOutputDataItem(comboBoxValue.getValue());
                outputDataItem_TextField.setText(comboBoxValue.getDisplayValue());
            }
        } else {
            itemComponent.updateEntity(new ComboBoxValue<TDataOutput>(milCharacteristics.getOutputDataItem().getId(),
                    milCharacteristics.getOutputDataItem(), milCharacteristics.getOutputDataItem().getName()));
            itemComponent.setVisible(true);
        }
    }//GEN-LAST:event_outputDataItem_ActionMousePressed

    private void produceOutput_CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produceOutput_CheckBoxActionPerformed
        if (produceOutput_CheckBox.isSelected()) {
            loopDataOutputRef_ComboBox.setEnabled(true);
            outputDataItem_TextField.setEnabled(true);
            outputDataItem_Action.setEnabled(true);
        } else {
            loopDataOutputRef_ComboBox.setEnabled(false);
            outputDataItem_TextField.setEnabled(false);
            outputDataItem_Action.setEnabled(false);

        }
    }//GEN-LAST:event_produceOutput_CheckBoxActionPerformed

    private void inputDataItem_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputDataItem_ActionMousePressed
        EntityComponent itemComponent = new DataItemPanel(modelerFile) {
            @Override
            public TDataParam createDataParamInstance() {
                return new TDataInput();
            }
        };
        itemComponent.init();

        if (inputDataItem_TextField.getText().trim().isEmpty() || milCharacteristics.getInputDataItem() == null) {
            itemComponent.createEntity(ComboBoxValue.class);
            itemComponent.setVisible(true);
            if (itemComponent.getDialogResult() == javax.swing.JOptionPane.OK_OPTION) {
                ComboBoxValue<TDataInput> comboBoxValue = (ComboBoxValue<TDataInput>) itemComponent.getEntity();
                milCharacteristics.setInputDataItem(comboBoxValue.getValue());
                inputDataItem_TextField.setText(comboBoxValue.getDisplayValue());
            }
        } else {
            itemComponent.updateEntity(new ComboBoxValue<TDataInput>(milCharacteristics.getInputDataItem().getId(),
                    milCharacteristics.getInputDataItem(), milCharacteristics.getInputDataItem().getName()));
            itemComponent.setVisible(true);
        }
    }//GEN-LAST:event_inputDataItem_ActionMousePressed

    private void inputExpression_evaluatesToType_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputExpression_evaluatesToType_ActionMousePressed
        actionPanelType = "inputExpression_evaluatesToType";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_inputExpression_evaluatesToType_ActionMousePressed

    private void inputDataCollection_RadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputDataCollection_RadioButtonActionPerformed
        inputDataCollection_RadioButtonActivated();
    }//GEN-LAST:event_inputDataCollection_RadioButtonActionPerformed

    private void expression_RadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expression_RadioButtonActionPerformed
        expression_RadioButtonActivated();
    }//GEN-LAST:event_expression_RadioButtonActionPerformed

    EntityComponent getActionPanel() {
        if ("inputExpression_evaluatesToType".equals(actionPanelType) || "completionConditionExpression_evaluatesToType".equals(actionPanelType)) {
            return new ItemDefinitionPanel(modelerFile);
        } else {
            return null;
        }
    }

    ComboBoxValue getSelectedActionItem() {
        if ("inputExpression_evaluatesToType".equals(actionPanelType) || "completionConditionExpression_evaluatesToType".equals(actionPanelType)) {
            return (ComboBoxValue) inputExpression_evaluatesToType_ComboBox.getSelectedItem();
        } else {
            return null;
        }
    }

    void inputDataCollection_RadioButtonActivate() {
        inputDataCollection_RadioButton.setSelected(true);
        inputDataCollection_RadioButtonActivated();
    }

    void expression_RadioButtonActivate() {
        expression_RadioButton.setSelected(true);
        expression_RadioButtonActivated();
    }

    void inputDataCollection_RadioButtonActivated() {
        inputDataCollection_RadioButton.setSelected(true);
        inputDataItems_LayeredPane.setVisible(true);
        inputExpression_loopCardinality_LayeredPane.setVisible(false);

//        this.setSize(new Dimension(503, 202));
//        this.setPreferredSize(new Dimension(503, 202));
    }

    void expression_RadioButtonActivated() {
        expression_RadioButton.setSelected(true);
        inputDataItems_LayeredPane.setVisible(false);
        inputExpression_loopCardinality_LayeredPane.setVisible(true);

//        this.setSize(new Dimension(577, 387));
//        this.setPreferredSize(new Dimension(577, 387));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane completionConditionExpression_LayeredPane;
    private javax.swing.JLayeredPane completionConditionExpression_evaluateType_LayeredPane;
    private javax.swing.JButton completionConditionExpression_evaluatesToType_Action;
    private javax.swing.JComboBox completionConditionExpression_evaluatesToType_ComboBox;
    private javax.swing.JLabel completionConditionExpression_evaluatesToType_Label;
    private javax.swing.JComboBox completionConditionExpression_language_ComboBox;
    private javax.swing.JLabel completionConditionExpression_language_Label;
    private javax.swing.JLayeredPane completionConditionExpression_language_LayeredPane;
    private javax.swing.JLabel completionConditionExpression_script_Label;
    private javax.swing.JLayeredPane completionConditionExpression_script_LayeredPane;
    private javax.swing.JScrollPane completionConditionExpression_script_ScrollPane;
    private javax.swing.JTextArea completionConditionExpression_script_TextArea;
    private javax.swing.JPanel completionCondition_Panel;
    private javax.swing.JMenuItem createItem_MenuItem;
    private javax.swing.JMenuItem deleteItem_MenuItem;
    private javax.swing.JMenuItem editItem_MenuItem;
    private javax.swing.JRadioButton expression_RadioButton;
    private javax.swing.JRadioButton inputDataCollection_RadioButton;
    private javax.swing.JButton inputDataItem_Action;
    private javax.swing.JLabel inputDataItem_Label;
    private javax.swing.JLayeredPane inputDataItem_LayeredPane;
    private javax.swing.JTextField inputDataItem_TextField;
    private javax.swing.JLayeredPane inputDataItems_LayeredPane;
    private javax.swing.JLayeredPane inputExpression_evaluateType_LayeredPane;
    private javax.swing.JButton inputExpression_evaluatesToType_Action;
    private javax.swing.JComboBox inputExpression_evaluatesToType_ComboBox;
    private javax.swing.JLabel inputExpression_evaluatesToType_Label;
    private javax.swing.JComboBox inputExpression_language_ComboBox;
    private javax.swing.JLabel inputExpression_language_Label;
    private javax.swing.JLayeredPane inputExpression_language_LayeredPane;
    private javax.swing.JLayeredPane inputExpression_loopCardinality_LayeredPane;
    private javax.swing.JLabel inputExpression_script_Label;
    private javax.swing.JLayeredPane inputExpression_script_LayeredPane;
    private javax.swing.JScrollPane inputExpression_script_ScrollPane;
    private javax.swing.JTextArea inputExpression_script_TextArea;
    private javax.swing.ButtonGroup inputInstanceEvalGroup;
    private javax.swing.JPanel inputItems_Panel;
    private javax.swing.JLabel instanceEval_Label;
    private javax.swing.JLayeredPane instanceEval_LayeredPane;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox loopDataInputRef_ComboBox;
    private javax.swing.JLabel loopDataInputRef_Label;
    private javax.swing.JLayeredPane loopDataInputRef_LayeredPane;
    private javax.swing.JComboBox loopDataOutputRef_ComboBox;
    private javax.swing.JLabel loopDataOutputRef_Label;
    private javax.swing.JLayeredPane loopDataOutputRef_LayeredPane;
    private javax.swing.JButton outputDataItem_Action;
    private javax.swing.JLabel outputDataItem_Label;
    private javax.swing.JLayeredPane outputDataItem_LayeredPane;
    private javax.swing.JTextField outputDataItem_TextField;
    private javax.swing.JLayeredPane outputDataItems_LayeredPane;
    private javax.swing.JPanel outputItems_Panel;
    private javax.swing.JCheckBox produceOutput_CheckBox;
    private javax.swing.JLabel produceOutput_Label;
    private javax.swing.JLayeredPane produceOutput_LayeredPane;
    private javax.swing.JPopupMenu setting_PopupMenu;
    // End of variables declaration//GEN-END:variables

}
