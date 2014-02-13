/** Copyright [2014] Gaurav Gupta
   *
   *Licensed under the Apache License, Version 2.0 (the "License");
   *you may not use this file except in compliance with the License.
   *You may obtain a copy of the License at
   *
   *    http://www.apache.org/licenses/LICENSE-2.0
   *
   *Unless required by applicable law or agreed to in writing, software
   *distributed under the License is distributed on an "AS IS" BASIS,
   *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   *See the License for the specific language governing permissions and
   *limitations under the License.
   */
 package org.netbeans.jbpmn.spec;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tMultiInstanceFlowCondition.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * <simpleType name="tMultiInstanceFlowCondition">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="None"/>
 *     <enumeration value="One"/>
 *     <enumeration value="All"/>
 *     <enumeration value="Complex"/>
 *   </restriction>
 * </simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tMultiInstanceFlowCondition")
@XmlEnum
public enum TMultiInstanceFlowCondition {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("One")
    ONE("One"),
    @XmlEnumValue("All")
    ALL("All"),
    @XmlEnumValue("Complex")
    COMPLEX("Complex");
    private final String value;

    TMultiInstanceFlowCondition(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TMultiInstanceFlowCondition fromValue(String v) {
        for (TMultiInstanceFlowCondition c: TMultiInstanceFlowCondition.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
