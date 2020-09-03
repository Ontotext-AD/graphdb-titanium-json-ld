package com.apicatalog.jsonld.serialization;

import java.util.AbstractMap;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

public class JsonObjectRef extends AbstractMap<String, JsonValue> implements JsonObject {
    
    private JsonObject jsonObject;

    JsonObjectRef(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public JsonArray getJsonArray(String name) {
        return jsonObject.getJsonArray(name);
    }

    @Override
    public JsonObject getJsonObject(String name) {
        return jsonObject.getJsonObject(name);
    }

    @Override
    public JsonNumber getJsonNumber(String name) {
        return jsonObject.getJsonNumber(name);
    }

    @Override
    public JsonString getJsonString(String name) {
        return jsonObject.getJsonString(name);
    }

    @Override
    public String getString(String name) {
        return jsonObject.getString(name);
    }

    @Override
    public String getString(String name, String defaultValue) {
        return jsonObject.getString(name, defaultValue);
    }

    @Override
    public int getInt(String name) {
        return jsonObject.getInt(name);
    }

    @Override
    public int getInt(String name, int defaultValue) {
        return jsonObject.getInt(name, defaultValue);
    }
    
    @Override
    public boolean getBoolean(String name) {
        return jsonObject.getBoolean(name);
    }

    @Override
    public boolean getBoolean(String name, boolean defaultValue) {
        return jsonObject.getBoolean(name, defaultValue);
    }

    @Override
    public boolean isNull(String name) {
        return jsonObject.isNull(name);
    }

    @Override
    public ValueType getValueType() {
        return jsonObject.getValueType();
    }

    @Override
    public Set<Entry<String, JsonValue>> entrySet() {
        return jsonObject.entrySet();
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }

    @Override
    public JsonObject asJsonObject() {
        return this;
    }

    @Override
    public int size() {
        return jsonObject.size();
    }

    @Override
    public JsonValue get(Object key) {
        return jsonObject.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return jsonObject.containsKey(key);
    }

    @Override
    public int hashCode() {
        return jsonObject.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        return jsonObject.equals(o);
    }

    @Override
    public JsonValue put(String key, JsonValue value) {
        JsonValue previous = jsonObject.get(key);
        
        jsonObject = Json.createObjectBuilder(jsonObject).add(key, value).build();
                
        return previous;
    }
    
    @Override
    public JsonValue remove(Object key) {

        JsonValue previous = jsonObject.get(key);
        
        jsonObject = Json.createObjectBuilder(jsonObject).remove(key.toString()).build();
                
        return previous;
    }
}
