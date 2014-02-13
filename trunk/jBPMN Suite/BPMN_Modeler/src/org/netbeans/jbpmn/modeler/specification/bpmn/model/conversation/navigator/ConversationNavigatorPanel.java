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
package org.netbeans.jbpmn.modeler.specification.bpmn.model.conversation.navigator;

import org.netbeans.modeler.navigator.ModelerNavigatorPanel;
import org.netbeans.spi.navigator.NavigatorPanel;

@NavigatorPanel.Registration(mimeType = "text/conv+xml", displayName = "BPMN Conversation Navigator")
public class ConversationNavigatorPanel extends ModelerNavigatorPanel {
}
