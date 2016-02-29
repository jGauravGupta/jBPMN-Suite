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
package org.netbeans.jbpmn.spec;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for tInputOutputSpecification complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * <complexType name="tInputOutputSpecification">
 *   <complexContent>
 *     <extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tBaseElement">
 *       <sequence>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}dataInput" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}dataOutput" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}inputSet" maxOccurs="unbounded"/>
 *         <element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}outputSet" maxOccurs="unbounded"/>
 *       </sequence>
 *       <anyAttribute processContents='lax' namespace='##other'/>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInputOutputSpecification", propOrder = {
    "dataInput",
    "dataOutput",
    "inputSet",
    "outputSet"
})
public class TInputOutputSpecification
        extends TBaseElement {

    private List<TDataInput> dataInput;
    private List<TDataOutput> dataOutput;
    @XmlElement(required = true)
    private List<TInputSet> inputSet;
    @XmlElement(required = true)
    private List<TOutputSet> outputSet;

    /**
     * Gets the value of the dataInput property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the dataInput property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataInput().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TDataInput }
     *
     *
     */
    public List<TDataInput> getDataInput() {
        if (dataInput == null) {
            setDataInput(new ArrayList<TDataInput>());
        }
        return this.dataInput;
    }

    public TDataInput getDataInput(String id) {
        if (dataInput != null) {
            for (TDataInput dataInput_TMP : dataInput) {
                if (dataInput_TMP.getId().equals(id)) {
                    return dataInput_TMP;
                }
            }
        }
        return null;
    }

    /**
     * Gets the value of the dataOutput property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the dataOutput property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataOutput().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TDataOutput }
     *
     *
     */
    public List<TDataOutput> getDataOutput() {
        if (dataOutput == null) {
            setDataOutput(new ArrayList<TDataOutput>());
        }
        return this.dataOutput;
    }

    public TDataOutput getDataOutput(String id) {
        if (dataOutput != null) {
            for (TDataOutput dataOutput_TMP : dataOutput) {
                if (dataOutput_TMP.getId().equals(id)) {
                    return dataOutput_TMP;
                }
            }
        }
        return null;
    }

    /**
     * Gets the value of the inputSet property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the inputSet property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputSet().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TInputSet }
     *
     *
     */
    public List<TInputSet> getInputSet() {
        if (inputSet == null) {
            setInputSet(new ArrayList<TInputSet>());
        }
        return this.inputSet;
    }

    public void addInputSet(TInputSet obj) {
        if (inputSet == null) {
            setInputSet(new ArrayList<TInputSet>());
        }
        this.inputSet.add(obj);
    }

    /**
     * Gets the value of the outputSet property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the outputSet property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutputSet().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TOutputSet }
     *
     *
     */
    public List<TOutputSet> getOutputSet() {
        if (outputSet == null) {
            setOutputSet(new ArrayList<TOutputSet>());
        }
        return this.outputSet;
    }

    public void addOutputSet(TOutputSet obj) {
        if (outputSet == null) {
            setOutputSet(new ArrayList<TOutputSet>());
        }
        this.outputSet.add(obj);
    }

    /**
     * @param dataInput the dataInput to set
     */
    public void setDataInput(List<TDataInput> dataInput) {
        this.dataInput = dataInput;
    }

    /**
     * @param dataOutput the dataOutput to set
     */
    public void setDataOutput(List<TDataOutput> dataOutput) {
        this.dataOutput = dataOutput;
    }

    /**
     * @param inputSet the inputSet to set
     */
    public void setInputSet(List<TInputSet> inputSet) {
        this.inputSet = inputSet;
    }

    /**
     * @param outputSet the outputSet to set
     */
    public void setOutputSet(List<TOutputSet> outputSet) {
        this.outputSet = outputSet;
    }

}
