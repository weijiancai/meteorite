/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ectongs.http;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author wei_jc
 */
public class DataListOption implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String label;
    private String data;
    private Map<String, Object> map;
    
    public DataListOption(String data, String label) {
        this.label = label;
        this.data = data;
    }
    
    public DataListOption(String label, Map<String, Object> map) {
        this.label = label;
        this.map = map;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
