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
@javax.xml.bind.annotation.XmlSchema(
        namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL",
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
        //        attributeFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
        xmlns = {
            @XmlNs(prefix = "bpmndi",
                    namespaceURI = "http://www.omg.org/spec/BPMN/20100524/DI"),
            @XmlNs(prefix = "omgdc",
                    namespaceURI = "http://www.omg.org/spec/DD/20100524/DC"),
            @XmlNs(prefix = "omgdi",
                    namespaceURI = "http://www.omg.org/spec/DD/20100524/DI"),
            @XmlNs(prefix = "xsi",
                    namespaceURI = "http://www.w3.org/2001/XMLSchema-instance"),
            @XmlNs(prefix = "java",
                    namespaceURI = "http://jcp.org/en/jsr/detail?id=270"),
            @XmlNs(prefix = "jbpmn",
                    namespaceURI = "http://jbpmn.java.net"),
            @XmlNs(prefix = "bpmn",
                    namespaceURI = "http://www.omg.org/spec/BPMN/20100524/MODEL"),
            @XmlNs(prefix = "nbm",
                    namespaceURI = "http://nbmodeler.java.net")

        })
package org.netbeans.jbpmn.spec;

import javax.xml.bind.annotation.XmlNs;

//@javax.xml.bind.annotation.XmlSchema(
//        namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL",
//        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)
//package org.netbeans.jbpmn.spec;
