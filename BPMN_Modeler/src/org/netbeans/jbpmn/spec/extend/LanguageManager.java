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
package org.netbeans.jbpmn.spec.extend;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;

public class LanguageManager {

    private static List<ComboBoxValue<String>> languageList = new ArrayList<ComboBoxValue<String>>();

    static {
        languageList.add(new ComboBoxValue<String>(null, null, ""));
        languageList.add(new ComboBoxValue<String>("http://www.java.com/java", "http://www.java.com/java", "JAVA"));
        languageList.add(new ComboBoxValue<String>("http://www.mvel.org/2.0", "http://www.mvel.org/2.0", "MVEL"));
    }

    public static ComboBoxValue<String> getLanguage(String key) {
        if (key == null) {
            return new ComboBoxValue<String>(null, null, "");
        }
        for (ComboBoxValue<String> language : languageList) {
            if (key.equals(language.getValue())) {
                return language;
            }
        }
        ComboBoxValue<String> comboBoxValue = new ComboBoxValue<String>(key, key);
        languageList.add(comboBoxValue);
        return comboBoxValue;
    }

    /**
     * Get the value of languageList
     *
     * @return the value of languageList
     */
    public static List<ComboBoxValue<String>> getLanguageList() {
        return languageList;
    }

    /**
     * Set the value of languageList
     *
     * @param languageList_Local
     * @param languageList new value of languageList
     */
    public static void setLanguageList(List<ComboBoxValue<String>> languageList_Local) {
        languageList = languageList_Local;
    }

}
