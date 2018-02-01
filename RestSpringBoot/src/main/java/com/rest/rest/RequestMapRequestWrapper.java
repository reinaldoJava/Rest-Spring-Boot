package com.rest.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestMapRequestWrapper extends HttpServletRequestWrapper {
    /**
     * construct a wrapper for this request
     *
     * @param request
     */
    public RequestMapRequestWrapper(HttpServletRequest request) {
        super(request);
    }

      @Override
      public String getParameter(String name) {
        return super.getParameter(name);
      }

      @Override
      public Map<String, String[]> getParameterMap() {
        Map<String, String[]> rawMap = super.getParameterMap();
        Map<String, String[]> filteredMap = new HashMap<String, String[]>(rawMap.size());
        Set<String> keys = rawMap.keySet();
        for (String key : keys) {
          String[] rawValue = rawMap.get(key);
          String[] filteredValue = filterStringArray(rawValue);
          filteredMap.put(key, filteredValue);
        }
        return filteredMap;
      }

      protected String[] filterStringArray(String[] rawValue) {
        String[] filteredArray = new String[rawValue.length];
        for (int i = 0; i < rawValue.length; i++) {
          filteredArray[i] = rawValue[i];
        }
        return filteredArray;
      }

      @Override
      public String[] getParameterValues(String name) {
        String[] rawValues = super.getParameterValues(name);
        if (rawValues == null)
          return null;
        String[] filteredValues = new String[rawValues.length];
        for (int i = 0; i < rawValues.length; i++) {
          filteredValues[i] = rawValues[i];
        }
        return filteredValues;
      }

}