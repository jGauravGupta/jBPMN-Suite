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
package org.netbeans.jbpmn.modeler.widget.properties.operation;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn._interface.InterfacePanel;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.error.ErrorPanel;
import org.netbeans.jbpmn.modeler.widget.properties.bpmn.message.MessagePanel;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.Entity;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;
import org.netbeans.jbpmn.spec.TDefinitions;
import org.netbeans.jbpmn.spec.TError;
import org.netbeans.jbpmn.spec.TInterface;
import org.netbeans.jbpmn.spec.TMessage;
import org.netbeans.jbpmn.spec.TOperation;
import org.netbeans.jbpmn.spec.TRootElement;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.core.ModelerFile;

public class OperationPanel extends EntityComponent {

    private ModelerFile file;
    private TDefinitions definition;

    private Boolean isNew;
    private String actionPanelType;

    /**
     * Creates new form OutputParameterPanel
     */
    ErrorListModel error_ListModel;

    public OperationPanel(ModelerFile file) {
        this(null, true);

        this.file = file;
        definition = (TDefinitions) file.getDefinitionElement();

        error_ListModel = new ErrorListModel();
        error_List.setModel(error_ListModel);
        error_List.setSelectionModel(new DefaultListSelectionModel() {
            public void setSelectionInterval(int index0, int index1) {
                if (isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

    }

    private class ErrorListModel extends DefaultListModel {

        private List<ComboBoxValue<TError>> errorElements = new ArrayList<ComboBoxValue<TError>>();

        @Override
        public int getSize() {
            return getErrorElements().size();
        }

        @Override
        public Object getElementAt(int i) {
            return getErrorElements().get(i);
        }

        /**
         * @return the errorElements
         */
        public List<ComboBoxValue<TError>> getErrorElements() {
            return errorElements;
        }

        /**
         * @param errorElements the errorElements to set
         */
        public void setErrorElements(List<ComboBoxValue<TError>> errorElements) {
            this.errorElements = errorElements;
            error_List.setPreferredSize(new Dimension(250, error_ListModel.getSize() * 15));
        }

        public void addErrorElement(ComboBoxValue<TError> errorElement) {
            super.addElement(errorElement);
            this.errorElements.add(errorElement);
            error_List.setPreferredSize(new Dimension(250, error_ListModel.getSize() * 15));
        }

        public void removeErrorElements(ComboBoxValue<TError> errorElement) {
            super.removeElement(errorElement);
            this.errorElements.remove(errorElement);
            error_List.setPreferredSize(new Dimension(250, error_ListModel.getSize() * 15));
        }
    }

    public List<ComboBoxValue<TInterface>> getInterfaceItemList() {
        List<ComboBoxValue<TInterface>> values = new ArrayList<ComboBoxValue<TInterface>>();
        values.add(new ComboBoxValue<TInterface>(null, null, ""));
        TDefinitions definition_Local = (TDefinitions) file.getDefinitionElement();
        for (TRootElement rootElement : definition_Local.getRootElement()) {
            if (rootElement instanceof TInterface) {
                TInterface _interface = (TInterface) rootElement;
                values.add(new ComboBoxValue<TInterface>(_interface.getId(), _interface, _interface.getName()));
            }
        }
        return values;
    }

    public List<ComboBoxValue<TMessage>> getMessgeItemList() {
        List<ComboBoxValue<TMessage>> values = new ArrayList<ComboBoxValue<TMessage>>();
        values.add(new ComboBoxValue<TMessage>(null, null, ""));
        TDefinitions definition_Local = (TDefinitions) file.getDefinitionElement();
        for (TRootElement rootElement : definition_Local.getRootElement()) {
            if (rootElement instanceof TMessage) {
                TMessage message = (TMessage) rootElement;
                values.add(new ComboBoxValue<TMessage>(message.getId(), message, message.getName()));
            }
        }
        return values;
    }

    public List<ComboBoxValue<TError>> getErrorItemList() {
        List<ComboBoxValue<TError>> values = new ArrayList<ComboBoxValue<TError>>();
//        values.add(new ComboBoxValue<TError>(null, null, ""));
        TDefinitions definition_Local = (TDefinitions) file.getDefinitionElement();
        for (TRootElement rootElement : definition_Local.getRootElement()) {
            if (rootElement instanceof TError) {
                TError error = (TError) rootElement;
                values.add(new ComboBoxValue<TError>(error.getId(), error, error.getName()));
            }
        }
        return values;
    }

    @Override
    public void init() {
        interface_ComboBox.removeAllItems();
        for (ComboBoxValue<TInterface> _interface : getInterfaceItemList()) {
            interface_ComboBox.addItem(_interface);
        }
        List<ComboBoxValue<TMessage>> messgeItemList = getMessgeItemList();
        in_message_ComboBox.removeAllItems();
        out_message_ComboBox.removeAllItems();
        for (ComboBoxValue<TMessage> message : messgeItemList) {
            in_message_ComboBox.addItem(message);
            out_message_ComboBox.addItem(message);
        }

        error_ListModel.setErrorElements(getErrorItemList());

        interface_ComboBox.setSelectedItem(new ComboBoxValue<TInterface>(null, null, ""));
        in_message_ComboBox.setSelectedItem(new ComboBoxValue<TMessage>(null, null, ""));
        out_message_ComboBox.setSelectedItem(new ComboBoxValue<TMessage>(null, null, ""));
        error_ListModel.addElement(new ComboBoxValue<TError>(null, null, ""));
        name_TextField.setText("");
        implementation_TextField.setText("");
    }

    @Override
    public void createEntity(Class/*<? extends Entity>*/ entityWrapperType) {
        this.setTitle("Create new Operation");
        isNew = true;
        if (entityWrapperType == ComboBoxValue.class) {
            ComboBoxValue<TOperation> value = new ComboBoxValue<TOperation>();
            this.setEntity(value);
            TOperation operation = new TOperation();
            operation.setId(NBModelerUtil.getAutoGeneratedStringId());
            value.setValue(operation);
            value.setId(operation.getId());
        }
    }

    @Override
    public void updateEntity(Entity entityValue) {
        this.setTitle("Update Operation");
        isNew = false;
        if (entityValue.getClass() == ComboBoxValue.class) {
            this.setEntity(entityValue);
            TOperation operation = ((ComboBoxValue<TOperation>) entityValue).getValue();
            name_TextField.setText(operation.getName() == null ? "" : operation.getName());
            implementation_TextField.setText(operation.getImplementationRef() == null ? "" : operation.getImplementationRef());

            for (int i = 0; i < in_message_ComboBox.getItemCount(); i++) {
                ComboBoxValue<TMessage> messageItem = (ComboBoxValue<TMessage>) in_message_ComboBox.getItemAt(i);
                if (messageItem.getValue() != null && messageItem.getValue().getId().equals(operation.getInMessageRef())) {
                    in_message_ComboBox.setSelectedItem(messageItem);
                    break;
                }
            }
            for (int i = 0; i < out_message_ComboBox.getItemCount(); i++) {
                ComboBoxValue<TMessage> messageItem = (ComboBoxValue<TMessage>) out_message_ComboBox.getItemAt(i);
                if (messageItem.getValue() != null && messageItem.getValue().getId().equals(operation.getOutMessageRef())) {
                    out_message_ComboBox.setSelectedItem(messageItem);
                    break;
                }
            }
            for (int i = 0; i < interface_ComboBox.getItemCount(); i++) {
                ComboBoxValue<TInterface> interfaceItem = (ComboBoxValue<TInterface>) interface_ComboBox.getItemAt(i);
                if (interfaceItem.getValue() != null && interfaceItem.getValue().getId().equals(operation.getInterface_Ref())) {
                    interface_ComboBox.setSelectedItem(interfaceItem);
                    break;
                }
            }

            for (int i = 0; i < error_ListModel.getSize(); i++) {
                ComboBoxValue<TError> errorItem = (ComboBoxValue<TError>) error_ListModel.getElementAt(i);
                if (errorItem.getValue() != null && operation.getErrorRef().contains(errorItem.getId())) {
                    error_List.setSelectedIndex(i);
                }
            }
        }
    }

    private OperationPanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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
        interface_Panel = new javax.swing.JLayeredPane();
        interface_inner_Panel = new javax.swing.JLayeredPane();
        interface_Label = new javax.swing.JLabel();
        interface_ComboBox = new javax.swing.JComboBox();
        interface_Action = new javax.swing.JButton();
        operation_Panel = new javax.swing.JLayeredPane();
        name_Panel = new javax.swing.JLayeredPane();
        name_Label = new javax.swing.JLabel();
        name_TextField = new javax.swing.JTextField();
        implementation_Panel = new javax.swing.JLayeredPane();
        implementation_Label = new javax.swing.JLabel();
        implementation_TextField = new javax.swing.JTextField();
        implementation_Button = new javax.swing.JButton();
        message_Panel = new javax.swing.JLayeredPane();
        in_message_Panel = new javax.swing.JLayeredPane();
        in_message_Label = new javax.swing.JLabel();
        in_message_ComboBox = new javax.swing.JComboBox();
        in_message_Action = new javax.swing.JButton();
        out_message_Panel = new javax.swing.JLayeredPane();
        out_message_Label = new javax.swing.JLabel();
        out_message_ComboBox = new javax.swing.JComboBox();
        out_message_Action = new javax.swing.JButton();
        error_Panel = new javax.swing.JLayeredPane();
        error_inner_Panel = new javax.swing.JLayeredPane();
        error_Label = new javax.swing.JLabel();
        error_ScrollPane = new javax.swing.JScrollPane();
        error_List = new javax.swing.JList();
        error_Action = new javax.swing.JButton();
        action_Panel = new javax.swing.JLayeredPane();
        cancel_Button = new javax.swing.JButton();
        save_Button = new javax.swing.JButton();

        createItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/icon_plus.png"))); // NOI18N
        createItem_MenuItem.setText(org.openide.util.NbBundle.getMessage(OperationPanel.class, "JComboBoxPanel.createItem_MenuItem.text")); // NOI18N
        createItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(OperationPanel.class, "JComboBoxPanel.createItem_MenuItem.toolTipText")); // NOI18N
        createItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(createItem_MenuItem);

        editItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/edit.png"))); // NOI18N
        editItem_MenuItem.setText(org.openide.util.NbBundle.getMessage(OperationPanel.class, "JComboBoxPanel.editItem_MenuItem.text")); // NOI18N
        editItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(OperationPanel.class, "JComboBoxPanel.editItem_MenuItem.toolTipText")); // NOI18N
        editItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(editItem_MenuItem);

        deleteItem_MenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/delete.png"))); // NOI18N
        deleteItem_MenuItem.setText(org.openide.util.NbBundle.getMessage(OperationPanel.class, "JComboBoxPanel.deleteItem_MenuItem.text")); // NOI18N
        deleteItem_MenuItem.setToolTipText(org.openide.util.NbBundle.getMessage(OperationPanel.class, "JComboBoxPanel.deleteItem_MenuItem.toolTipText")); // NOI18N
        deleteItem_MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteItem_MenuItemActionPerformed(evt);
            }
        });
        setting_PopupMenu.add(deleteItem_MenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        interface_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Interface Reference", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB Demi", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        interface_inner_Panel.setPreferredSize(new java.awt.Dimension(400, 35));

        interface_Label.setText("Interface :");

        javax.swing.GroupLayout interface_inner_PanelLayout = new javax.swing.GroupLayout(interface_inner_Panel);
        interface_inner_Panel.setLayout(interface_inner_PanelLayout);
        interface_inner_PanelLayout.setHorizontalGroup(
            interface_inner_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(interface_inner_PanelLayout.createSequentialGroup()
                .addComponent(interface_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(interface_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        interface_inner_PanelLayout.setVerticalGroup(
            interface_inner_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(interface_inner_PanelLayout.createSequentialGroup()
                .addGroup(interface_inner_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(interface_Label)
                    .addComponent(interface_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );
        interface_inner_Panel.setLayer(interface_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        interface_inner_Panel.setLayer(interface_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        interface_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        interface_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                interface_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout interface_PanelLayout = new javax.swing.GroupLayout(interface_Panel);
        interface_Panel.setLayout(interface_PanelLayout);
        interface_PanelLayout.setHorizontalGroup(
            interface_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(interface_PanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(interface_inner_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(interface_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        interface_PanelLayout.setVerticalGroup(
            interface_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, interface_PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(interface_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(interface_Action)
                    .addComponent(interface_inner_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        interface_Panel.setLayer(interface_inner_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        interface_Panel.setLayer(interface_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        operation_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Operation", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB Demi", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        name_Panel.setPreferredSize(new java.awt.Dimension(400, 35));

        name_Label.setText("Name :");

        javax.swing.GroupLayout name_PanelLayout = new javax.swing.GroupLayout(name_Panel);
        name_Panel.setLayout(name_PanelLayout);
        name_PanelLayout.setHorizontalGroup(
            name_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(name_PanelLayout.createSequentialGroup()
                .addComponent(name_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(name_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        name_PanelLayout.setVerticalGroup(
            name_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(name_PanelLayout.createSequentialGroup()
                .addGroup(name_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_Label)
                    .addComponent(name_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );
        name_Panel.setLayer(name_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        name_Panel.setLayer(name_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        implementation_Panel.setPreferredSize(new java.awt.Dimension(400, 35));

        implementation_Label.setText("Implementation :");

        javax.swing.GroupLayout implementation_PanelLayout = new javax.swing.GroupLayout(implementation_Panel);
        implementation_Panel.setLayout(implementation_PanelLayout);
        implementation_PanelLayout.setHorizontalGroup(
            implementation_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(implementation_PanelLayout.createSequentialGroup()
                .addComponent(implementation_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(implementation_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        implementation_PanelLayout.setVerticalGroup(
            implementation_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(implementation_PanelLayout.createSequentialGroup()
                .addGroup(implementation_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(implementation_Label)
                    .addComponent(implementation_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );
        implementation_Panel.setLayer(implementation_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        implementation_Panel.setLayer(implementation_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        implementation_Button.setBackground(new java.awt.Color(255, 255, 255));
        implementation_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/resource/searchbutton.png"))); // NOI18N
        implementation_Button.setBorder(null);
        implementation_Button.setBorderPainted(false);
        implementation_Button.setContentAreaFilled(false);
        implementation_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                implementation_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout operation_PanelLayout = new javax.swing.GroupLayout(operation_Panel);
        operation_Panel.setLayout(operation_PanelLayout);
        operation_PanelLayout.setHorizontalGroup(
            operation_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(operation_PanelLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(operation_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(operation_PanelLayout.createSequentialGroup()
                        .addComponent(implementation_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(implementation_Button)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        operation_PanelLayout.setVerticalGroup(
            operation_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(operation_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(name_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(operation_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(implementation_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(implementation_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        operation_Panel.setLayer(name_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        operation_Panel.setLayer(implementation_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        operation_Panel.setLayer(implementation_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);

        message_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Message Reference", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB Demi", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N
        message_Panel.setPreferredSize(new java.awt.Dimension(535, 110));

        in_message_Panel.setPreferredSize(new java.awt.Dimension(400, 30));

        in_message_Label.setText("In Message :");
        in_message_Label.setPreferredSize(new java.awt.Dimension(50, 15));

        javax.swing.GroupLayout in_message_PanelLayout = new javax.swing.GroupLayout(in_message_Panel);
        in_message_Panel.setLayout(in_message_PanelLayout);
        in_message_PanelLayout.setHorizontalGroup(
            in_message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(in_message_PanelLayout.createSequentialGroup()
                .addComponent(in_message_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(in_message_ComboBox, 0, 300, Short.MAX_VALUE)
                .addContainerGap())
        );
        in_message_PanelLayout.setVerticalGroup(
            in_message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(in_message_PanelLayout.createSequentialGroup()
                .addGroup(in_message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(in_message_Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(in_message_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        in_message_Panel.setLayer(in_message_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        in_message_Panel.setLayer(in_message_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        in_message_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        in_message_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                in_message_ActionMousePressed(evt);
            }
        });

        out_message_Panel.setPreferredSize(new java.awt.Dimension(400, 30));

        out_message_Label.setText("Out  Message :");
        out_message_Label.setPreferredSize(new java.awt.Dimension(50, 15));

        javax.swing.GroupLayout out_message_PanelLayout = new javax.swing.GroupLayout(out_message_Panel);
        out_message_Panel.setLayout(out_message_PanelLayout);
        out_message_PanelLayout.setHorizontalGroup(
            out_message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(out_message_PanelLayout.createSequentialGroup()
                .addComponent(out_message_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(out_message_ComboBox, 0, 300, Short.MAX_VALUE)
                .addContainerGap())
        );
        out_message_PanelLayout.setVerticalGroup(
            out_message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(out_message_PanelLayout.createSequentialGroup()
                .addGroup(out_message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(out_message_Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(out_message_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        out_message_Panel.setLayer(out_message_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        out_message_Panel.setLayer(out_message_ComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        out_message_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        out_message_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                out_message_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout message_PanelLayout = new javax.swing.GroupLayout(message_Panel);
        message_Panel.setLayout(message_PanelLayout);
        message_PanelLayout.setHorizontalGroup(
            message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, message_PanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(message_PanelLayout.createSequentialGroup()
                        .addComponent(out_message_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(out_message_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(message_PanelLayout.createSequentialGroup()
                        .addComponent(in_message_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(in_message_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        message_PanelLayout.setVerticalGroup(
            message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(message_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(in_message_Action)
                    .addComponent(in_message_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(message_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(out_message_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(out_message_Action))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        message_Panel.setLayer(in_message_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        message_Panel.setLayer(in_message_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);
        message_Panel.setLayer(out_message_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        message_Panel.setLayer(out_message_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

        error_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Error Reference", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB Demi", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        error_inner_Panel.setPreferredSize(new java.awt.Dimension(400, 35));

        error_Label.setText("Error :");

        error_List.setMaximumSize(new java.awt.Dimension(500, 10000));
        error_List.setMinimumSize(new java.awt.Dimension(250, 50));
        error_List.setPreferredSize(new java.awt.Dimension(250, 160));
        error_ScrollPane.setViewportView(error_List);

        javax.swing.GroupLayout error_inner_PanelLayout = new javax.swing.GroupLayout(error_inner_Panel);
        error_inner_Panel.setLayout(error_inner_PanelLayout);
        error_inner_PanelLayout.setHorizontalGroup(
            error_inner_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(error_inner_PanelLayout.createSequentialGroup()
                .addComponent(error_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(error_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addContainerGap())
        );
        error_inner_PanelLayout.setVerticalGroup(
            error_inner_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(error_inner_PanelLayout.createSequentialGroup()
                .addComponent(error_Label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(error_inner_PanelLayout.createSequentialGroup()
                .addComponent(error_ScrollPane)
                .addContainerGap())
        );
        error_inner_Panel.setLayer(error_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        error_inner_Panel.setLayer(error_ScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        error_Action.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/jbpmn/modeler/widget/properties/operation/settings.png"))); // NOI18N
        error_Action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                error_ActionMousePressed(evt);
            }
        });

        javax.swing.GroupLayout error_PanelLayout = new javax.swing.GroupLayout(error_Panel);
        error_Panel.setLayout(error_PanelLayout);
        error_PanelLayout.setHorizontalGroup(
            error_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(error_PanelLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(error_inner_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(error_Action, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        error_PanelLayout.setVerticalGroup(
            error_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(error_inner_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
            .addGroup(error_PanelLayout.createSequentialGroup()
                .addComponent(error_Action)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        error_Panel.setLayer(error_inner_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        error_Panel.setLayer(error_Action, javax.swing.JLayeredPane.DEFAULT_LAYER);

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

        javax.swing.GroupLayout action_PanelLayout = new javax.swing.GroupLayout(action_Panel);
        action_Panel.setLayout(action_PanelLayout);
        action_PanelLayout.setHorizontalGroup(
            action_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(action_PanelLayout.createSequentialGroup()
                .addComponent(save_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancel_Button))
        );
        action_PanelLayout.setVerticalGroup(
            action_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(action_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(save_Button)
                .addComponent(cancel_Button))
        );
        action_Panel.setLayer(cancel_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);
        action_Panel.setLayer(save_Button, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layeredPaneLayout = new javax.swing.GroupLayout(layeredPane);
        layeredPane.setLayout(layeredPaneLayout);
        layeredPaneLayout.setHorizontalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(action_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(interface_Panel, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(error_Panel)
                        .addComponent(message_Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                        .addComponent(operation_Panel, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap())
        );
        layeredPaneLayout.setVerticalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(interface_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(operation_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(message_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(error_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(action_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163))
        );
        layeredPane.setLayer(interface_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPane.setLayer(operation_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPane.setLayer(message_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPane.setLayer(error_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPane.setLayer(action_Panel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 530, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//      private javax.swing.JComboBox interface_ComboBox;
//     private javax.swing.JTextField name_TextField;
//    private javax.swing.JTextField implementation_TextField;
//       private javax.swing.JComboBox in_message_ComboBox;
//     private javax.swing.JComboBox out_message_ComboBox;
//    private javax.swing.JList error_List;

    private boolean validateField() {
        if (this.name_TextField.getText().trim().length() <= 0 /*
                 * || Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()
                 */) {
            JOptionPane.showMessageDialog(this, "Parameter name can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n
//        if (this.implementation_TextField.getText().trim().length() <= 0 /*
//                 * || Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()
//                 */) {
//            JOptionPane.showMessageDialog(this, "Parameter implementation can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
//            return false;
//        }//I18n

        ComboBoxValue<TInterface> interfaceItem = (ComboBoxValue<TInterface>) interface_ComboBox.getSelectedItem();
        if (interfaceItem.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Please select a interface", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n

//        ComboBoxValue<TMessage> in_messageItem = (ComboBoxValue<TMessage>) in_message_ComboBox.getSelectedItem();
//        if (in_messageItem.getValue() == null) {
//            JOptionPane.showMessageDialog(this, "Please select a input message", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
//            return false;
//        }//I18n   ComboBoxValue<TMessage> messageItem = (ComboBoxValue<TMessage>) in_message_ComboBox.getSelectedItem();
//        ComboBoxValue<TMessage> out_messageItem = (ComboBoxValue<TMessage>) out_message_ComboBox.getSelectedItem();
//        if (out_messageItem.getValue() == null) {
//            JOptionPane.showMessageDialog(this, "Please select a output message", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
//            return false;
//        }//I18n
        return true;
    }

    private void save_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_ButtonActionPerformed
        ComboBoxValue<TOperation> comboBoxValue = (ComboBoxValue<TOperation>) this.getEntity();
        TOperation operation = comboBoxValue.getValue();

        if (!validateField()) {
            return;
        }
        if (!isNew) {
            TInterface _interface = (TInterface) definition.getRootElement(operation.getInterface_Ref(), TInterface.class);
            _interface.removeOperation(operation);
        }

        operation.setInterface_Ref(((ComboBoxValue<TInterface>) interface_ComboBox.getSelectedItem()).getId());
        operation.setName(name_TextField.getText());
        operation.setImplementationRef(implementation_TextField.getText().trim().isEmpty() ? null : implementation_TextField.getText());
        if (((ComboBoxValue<TMessage>) in_message_ComboBox.getSelectedItem()).getValue() != null) {
            operation.setInMessageRef(((ComboBoxValue<TMessage>) in_message_ComboBox.getSelectedItem()).getId());
        } else {
            operation.setInMessageRef(null);
        }
        if (((ComboBoxValue<TMessage>) out_message_ComboBox.getSelectedItem()).getValue() != null) {
            operation.setOutMessageRef(((ComboBoxValue<TMessage>) out_message_ComboBox.getSelectedItem()).getId());
        } else {
            operation.setOutMessageRef(null);
        }
        operation.getErrorRef().clear();
        for (Object obj : error_List.getSelectedValues()) {
            ComboBoxValue<TError> errorValue = (ComboBoxValue<TError>) obj;
            operation.addErrorRef(errorValue.getId());
        }

        saveActionPerformed(evt);
    }//GEN-LAST:event_save_ButtonActionPerformed

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        cancelActionPerformed(evt);
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    EntityComponent getActionPanel() {
        if ("in_message".equals(actionPanelType)) {
            return new MessagePanel(file);
        } else if ("out_message".equals(actionPanelType)) {
            return new MessagePanel(file);
        } else if ("interface".equals(actionPanelType)) {
            return new InterfacePanel(file);
        } else if ("error".equals(actionPanelType)) {
            return new ErrorPanel(file);
        } else {
            return null;
        }
    }

    ComboBoxValue getSelectedActionItem() {
        if ("in_message".equals(actionPanelType)) {
            return (ComboBoxValue) in_message_ComboBox.getSelectedItem();
        } else if ("out_message".equals(actionPanelType)) {
            return (ComboBoxValue) out_message_ComboBox.getSelectedItem();
        } else if ("interface".equals(actionPanelType)) {
            return (ComboBoxValue) interface_ComboBox.getSelectedItem();
        } else if ("error".equals(actionPanelType)) {
            if (error_List.getSelectedValues().length > 1) {
                JOptionPane.showMessageDialog(null, "Please select only one error item !", "", JOptionPane.INFORMATION_MESSAGE);
                throw new IllegalStateException("MultiElement Selected");
            } else if (error_List.getSelectedValues().length < 1) {
                JOptionPane.showMessageDialog(null, "Please select atleast one error item !", "", JOptionPane.INFORMATION_MESSAGE);
                throw new IllegalStateException("No Element Selected");
            }
            return (ComboBoxValue) error_List.getSelectedValues()[0];
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

            if ("in_message".equals(actionPanelType)) {
                in_message_ComboBox.addItem(comboBoxValue);
                out_message_ComboBox.addItem(comboBoxValue);
                in_message_ComboBox.setSelectedItem(comboBoxValue);
                definition.addRootElement((TMessage) comboBoxValue.getValue());
            } else if ("out_message".equals(actionPanelType)) {
                in_message_ComboBox.addItem(comboBoxValue);
                out_message_ComboBox.addItem(comboBoxValue);
                out_message_ComboBox.setSelectedItem(comboBoxValue);
                definition.addRootElement((TMessage) comboBoxValue.getValue());
            } else if ("interface".equals(actionPanelType)) {
                interface_ComboBox.addItem(comboBoxValue);
                interface_ComboBox.setSelectedItem(comboBoxValue);
                definition.addRootElement((TInterface) comboBoxValue.getValue());
            } else if ("error".equals(actionPanelType)) {
                error_ListModel.addErrorElement(comboBoxValue);
                definition.addRootElement((TError) comboBoxValue.getValue());
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
            if ("in_message".equals(actionPanelType) || "out_message".equals(actionPanelType)) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Message Element ?", "Delete Message Element", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    definition.removeRootElement((TMessage) comboBoxValue.getValue());
                    in_message_ComboBox.removeItem(comboBoxValue);
                    out_message_ComboBox.removeItem(comboBoxValue);
                }

            } else if ("interface".equals(actionPanelType)) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Interface ?", "Delete Interface", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    definition.removeRootElement((TInterface) comboBoxValue.getValue());
                    interface_ComboBox.removeItem(comboBoxValue);
                }
            } else if ("error".equals(actionPanelType)) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sue you want to delete this Error ?", "Delete Error", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    definition.removeRootElement((TError) comboBoxValue.getValue());
                    error_ListModel.removeErrorElements(comboBoxValue);
                }
            }
        } catch (IllegalStateException ex) {
            System.out.println("EX : " + ex.toString());
        }
    }//GEN-LAST:event_deleteItem_MenuItemActionPerformed

    private void implementation_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_implementation_ButtonActionPerformed
        implementation_TextField.setText(NBModelerUtil.browseClass(file));
    }//GEN-LAST:event_implementation_ButtonActionPerformed

    private void in_message_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_in_message_ActionMousePressed
        actionPanelType = "in_message";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_in_message_ActionMousePressed

    private void out_message_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_out_message_ActionMousePressed
        actionPanelType = "out_message";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_out_message_ActionMousePressed

    private void interface_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_interface_ActionMousePressed
        actionPanelType = "interface";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_interface_ActionMousePressed

    private void error_ActionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_error_ActionMousePressed
        actionPanelType = "error";
        setting_PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }//GEN-LAST:event_error_ActionMousePressed

    void enableConditionPanel(boolean enable) {
//        message_Panel.setEnabled(enable);
//        constraintPanel.setEnabled(enable);
//        constraint_Label.setEnabled(enable);
//        constraint_ScrollPane.setEnabled(enable);
//        constraint_TextArea.setEnabled(enable);
//        in_message_ComboBox.setEnabled(enable);
//        in_message_Panel.setEnabled(enable);
//        in_message_Label.setEnabled(enable);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane action_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JMenuItem createItem_MenuItem;
    private javax.swing.JMenuItem deleteItem_MenuItem;
    private javax.swing.JMenuItem editItem_MenuItem;
    private javax.swing.JButton error_Action;
    private javax.swing.JLabel error_Label;
    private javax.swing.JList error_List;
    private javax.swing.JLayeredPane error_Panel;
    private javax.swing.JScrollPane error_ScrollPane;
    private javax.swing.JLayeredPane error_inner_Panel;
    private javax.swing.JButton implementation_Button;
    private javax.swing.JLabel implementation_Label;
    private javax.swing.JLayeredPane implementation_Panel;
    private javax.swing.JTextField implementation_TextField;
    private javax.swing.JButton in_message_Action;
    private javax.swing.JComboBox in_message_ComboBox;
    private javax.swing.JLabel in_message_Label;
    private javax.swing.JLayeredPane in_message_Panel;
    private javax.swing.JButton interface_Action;
    private javax.swing.JComboBox interface_ComboBox;
    private javax.swing.JLabel interface_Label;
    private javax.swing.JLayeredPane interface_Panel;
    private javax.swing.JLayeredPane interface_inner_Panel;
    private javax.swing.JLayeredPane layeredPane;
    private javax.swing.JLayeredPane message_Panel;
    private javax.swing.JLabel name_Label;
    private javax.swing.JLayeredPane name_Panel;
    private javax.swing.JTextField name_TextField;
    private javax.swing.JLayeredPane operation_Panel;
    private javax.swing.JButton out_message_Action;
    private javax.swing.JComboBox out_message_ComboBox;
    private javax.swing.JLabel out_message_Label;
    private javax.swing.JLayeredPane out_message_Panel;
    private javax.swing.JButton save_Button;
    private javax.swing.JPopupMenu setting_PopupMenu;
    // End of variables declaration//GEN-END:variables

}
/*
 Pane  400 30      10 10 0
 Label 80
 TextField 300

 */
