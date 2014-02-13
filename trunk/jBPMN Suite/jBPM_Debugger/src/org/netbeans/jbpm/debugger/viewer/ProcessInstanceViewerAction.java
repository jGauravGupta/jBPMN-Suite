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
package org.netbeans.jbpm.debugger.viewer;

import com.sun.jdi.ArrayReference;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.ClassType;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.StringReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.debugger.DebuggerManager;
import org.netbeans.api.debugger.jpda.JPDADebugger;
import org.netbeans.api.debugger.jpda.JPDAThread;
import org.netbeans.api.debugger.jpda.ObjectVariable;
import org.netbeans.jbpmn.modeler.specification.bpmn.model.process.file.action.debug.BPMNProcessFileDebugAction;
import org.netbeans.spi.viewmodel.Models;

public class ProcessInstanceViewerAction implements Models.ActionPerformer {

    ObjectVariable objectVariable;

    ProcessInstanceViewerAction(ObjectVariable objectVariable) {
        this.objectVariable = objectVariable;
    }

    public boolean isEnabled(Object node) {
        return true;
    }

    ObjectReference getObjectReference(ObjectReference or, String field) {
        return (ObjectReference) or.getValue(or.referenceType().fieldByName(field));
    }

    String getStringReference(ObjectReference or, String field) {
        return ((StringReference) or.getValue(or.referenceType().fieldByName(field))).value();
    }

//     PrimitiveValue getNumberReference(ObjectReference or, String field) {
//        return (PrimitiveValue) or.getValue(or.referenceType().fieldByName(field));
//    }
//
    ThreadReference getThreadReference() {
        ThreadReference thread = null;
        try {
            JPDAThread jpdaThread = DebuggerManager.getDebuggerManager().getCurrentEngine().lookupFirst(null, JPDADebugger.class).getCurrentThread();
            java.lang.reflect.Field f = jpdaThread.getClass().getDeclaredField("threadReference"); //NoSuchFieldException
            f.setAccessible(true);
            thread = (ThreadReference) f.get(jpdaThread);

        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return thread;
    }

    ObjectReference invokeMethod(ThreadReference thread, ObjectReference objectReference, String methodName, String methodSign) {
        return invokeMethod(thread, objectReference, methodName, methodSign, new ArrayList<Value>());
    }

    ObjectReference invokeMethod(ThreadReference thread, ObjectReference objectReference, String methodName, String methodSign, ArrayList<? extends Value> arrayList) {
        ObjectReference objectReference_Res = null;
        try {
            Method method = ((ClassType) objectReference.referenceType()).concreteMethodByName(methodName, methodSign);
            objectReference_Res = (ObjectReference) objectReference.invokeMethod(thread, method, arrayList, 0);
        } catch (InvalidTypeException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotLoadedException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncompatibleThreadStateException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objectReference_Res;
    }

    @Override
    public void perform(Object[] nodes) {
        try {
            ThreadReference thread = getThreadReference();
            VirtualMachine vm = thread.virtualMachine();
            ObjectVariable nodeInstances = (ObjectVariable) objectVariable.getField("nodeInstances");//).getField("size").getValue();
            java.lang.reflect.Field f = nodeInstances.getClass().getDeclaredField("objectReference"); //NoSuchFieldException
            f.setAccessible(true);
            ObjectReference t = (ObjectReference) f.get(nodeInstances);//instanceof RuleFlowProcessInstance
            String processId = getStringReference(t, "processId");
            Map<String, String> nodeList = new HashMap<String, String>();

            List<Value> nodeInstance_ValueList = ((ArrayReference) getObjectReference(getObjectReference(t, "nodeInstances"), "elementData")).getValues();
            for (Value nodeInstance_Value : nodeInstance_ValueList) {
                if (nodeInstance_Value != null) {
                    ObjectReference nodeInstance = (ObjectReference) nodeInstance_Value;
                    ObjectReference node_OR = null;
//                    try {
                    node_OR = invokeMethod(thread, nodeInstance, "getNode", "()Lorg/drools/definition/process/Node;");
//                    } catch (Exception e) {
//                        // FOR jBPM 6.0 in developement
//                        node_OR = getObjectReference(nodeInstance, "workItem");//invokeMethod(thread, nodeInstance, "getWorkItem", "()Lorg/drools/core/process/instance/impl/WorkItemImpl");// "()Lorg/kie/api/runtime/process/WorkItem;");
//                    }
                    String nodeName = getStringReference(node_OR, "name");
                    String nodeId = null;

                    ObjectReference metaData = null;
//                    try {
                    metaData = getObjectReference(node_OR, "metaData");
//                    } catch (Exception e) {
//                        // FOR jBPM 6.0 in developement
//                        metaData = getObjectReference(nodeInstance, "metaData");//(node_OR, "parameters");
//                    }
                    List<Value> metaData_ValueList = ((ArrayReference) getObjectReference(metaData, "table")).getValues();
                    for (Value metaData_Value : metaData_ValueList) {
                        if (metaData_Value != null) {
                            ObjectReference metaData_OR = (ObjectReference) metaData_Value;
                            if ("UniqueId".equals(getStringReference(metaData_OR, "key"))) {
                                nodeId = getStringReference(metaData_OR, "value");
                            }
                        }
                    }

                    nodeList.put(nodeId, nodeName);
                }
            }
            System.out.println("Process Id : " + processId);
            System.out.println("Node List : " + nodeList);

            ObjectReference classPathRes_OR = getObjectReference(getObjectReference(t, "process"), "resource");
            File file = new File(getStringReference(invokeMethod(thread, classPathRes_OR, "getURL", "()Ljava/net/URL;"), "path"));

            BPMNProcessFileDebugAction processFileDebugAction = new BPMNProcessFileDebugAction();
            processFileDebugAction.openFile(processId, nodeList, file);

//            final JPDADebugger jpd = ((JPDADebugger) DebuggerManager.getDebuggerManager().getCurrentEngine().lookupFirst(null, JPDADebugger.class));//..evaluate("wfInstance.getNodeInstances()");
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcessInstanceViewerAction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

//
//Type Signature
//Java Type
//Z boolean
//B byte
//C char
//S short
//I int
//J long
//F float
//D double
//L fully-qualified-class ; fully-qualified-class
//[ type type[]
//( arg-types ) ret-type method type
