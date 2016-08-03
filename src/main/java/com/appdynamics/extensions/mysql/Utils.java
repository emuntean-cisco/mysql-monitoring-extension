/**
 * Copyright 2013 AppDynamics
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.appdynamics.extensions.mysql;

import java.util.Map;

public class Utils {

    private Map<String, String> oldValueMap;
    private Map<String, String> valueMap;

    public Utils(Map<String, String> oldValueMap, Map<String, String> valueMap) {
        this.oldValueMap = oldValueMap;
        this.valueMap = valueMap;
    }

    public String getString(float num) {
        int result = Math.round(num);
        return Integer.toString(result);
    }

    // lookup value for key, convert to float, round up or down and then return as string form of int
    public String getString(String key) {
        return getString(key, true);
    }

    // specify whether to convert this key to uppercase before looking up the value
    public String getString(String key, boolean convertUpper) {
        if (convertUpper)
            key = key.toUpperCase();

        String strResult = valueMap.get(key);

        if (strResult == null)
            return "0";

        // round the result to a integer since we don't handle fractions
        float result = Float.valueOf(strResult);
        String resultStr = getString(result);
        return resultStr;
    }

    public String getPercent(String numerator, String denumerator) {
        float tmpResult = 0;

        try {
            tmpResult = getValue(numerator) / getValue(denumerator);
        } catch (Exception e) {
            return null;
        }
        tmpResult = tmpResult * 100;
        return getString(tmpResult);
    }

    public String getReversePercent(float numerator, float denumerator) {
        if (denumerator == 0)
            return "100";       //fix for ACE-147

        float tmpResult = numerator / denumerator;
        tmpResult = 1 - tmpResult;
        tmpResult = tmpResult * 100;
        return getString(tmpResult);
    }

    public String getPercent(float numerator, float denumerator) {
        if (denumerator == 0)
            return getString(0);

        float tmpResult = numerator / denumerator;
        tmpResult = tmpResult * 100;
        return getString(tmpResult);
    }

    // math utility
    public float getValue(String key) {
        String strResult = valueMap.get(key.toUpperCase());

        if (strResult == null)
            return 0;

        float result = Float.valueOf(strResult);
        return result;
    }

    public float getDiffValue(String key) {
        String strResult = valueMap.get(key.toUpperCase());

        if (strResult == null)
            return 0;

        float result = Float.valueOf(strResult);
        float oldResult = 0;

        String oldResultStr = oldValueMap.get(key.toUpperCase());
        if (oldResultStr != null)
            oldResult = Float.valueOf(oldResultStr);

        return (result - oldResult);
    }

}
