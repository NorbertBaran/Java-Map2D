package uj.java.pwj2019.map2d;

import java.util.*;
import java.util.function.Function;

public class MyMap2D implements Map2D {
    Map<Object, Map<Object, Object>> map2D=new LinkedHashMap<>();

    @Override
    public Object put(Object rowKey, Object columnKey, Object value) {
        if(rowKey==null || columnKey==null)
            throw new NullPointerException();
        if(!map2D.containsKey(rowKey)) {
            map2D.put(rowKey, new HashMap<>());
        }
        Object preValue=map2D.get(rowKey).get(columnKey);
        map2D.get(rowKey).put(columnKey, value);
        return preValue;
    }

    @Override
    public Object get(Object rowKey, Object columnKey) {
        if(map2D.get(rowKey)!=null)
            return map2D.get(rowKey).get(columnKey);
        return null;
    }

    @Override
    public Object getOrDefault(Object rowKey, Object columnKey, Object defaultValue) {
        return map2D.getOrDefault(rowKey, Collections.emptyMap()).getOrDefault(columnKey, defaultValue);
    }

    @Override
    public Object remove(Object rowKey, Object columnKey) {
        return map2D.getOrDefault(rowKey, Collections.emptyMap()).remove(columnKey);
    }

    @Override
    public boolean isEmpty() {
        return map2D.isEmpty();
    }

    @Override
    public boolean nonEmpty() {
        return !isEmpty();
    }

    @Override
    public int size() {
        int size=0;
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> map: map2dSet){
            size+=map.getValue().size();
            System.out.println("Test2");
            System.out.println(map);
        }
        return size;
    }

    @Override
    public void clear() {
        map2D.clear();
    }

    @Override
    public Map rowView(Object rowKey) {
        return map2D.get(rowKey);
    }

    @Override
    public Map columnView(Object columnKey) {
        Map<Object, Object> map=new HashMap<Object, Object>();
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> iMap: map2dSet)
            if(iMap.getValue().get(columnKey)!=null)
                map.put(iMap.getKey(), iMap.getValue().get(columnKey));
        return map;
    }

    @Override
    public boolean hasValue(Object value) {
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> iMap: map2dSet)
            if(iMap.getValue().containsValue(value))
                return true;
        return false;
    }

    @Override
    public boolean hasKey(Object rowKey, Object columnKey) {
        if(map2D.get(rowKey)!=null)
            return map2D.get(rowKey).get(columnKey)!=null;
        return false;
    }

    @Override
    public boolean hasRow(Object rowKey) {
        return map2D.containsKey(rowKey);
    }

    @Override
    public boolean hasColumn(Object columnKey) {
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> iMap: map2dSet)
            if(iMap.getValue().containsKey(columnKey))
                return true;
        return false;
    }

    @Override
    public Map rowMapView() {
        Map<Object, Map<Object, Object>> result=new LinkedHashMap<>();
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> iMap2D: map2dSet)
            result.put(iMap2D.getKey(), Collections.unmodifiableMap(Map.copyOf(iMap2D.getValue())));
        return Collections.unmodifiableMap(Map.copyOf(result));
    }

    @Override
    public Map columnMapView() {
        Map2D columnMap=Map2D.createInstance();
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> iMap: map2dSet){
            Object rowKey=iMap.getKey();
            Set<Map.Entry<Object, Object>> mapSet=iMap.getValue().entrySet();
            for(Map.Entry<Object, Object> element: mapSet){
                Object columnKey=element.getKey();
                Object value=element.getValue();

                columnMap.put(columnKey, rowKey, value);
            }
        }
        return columnMap.rowMapView();
    }

    @Override
    public Map2D fillMapFromRow(Map target, Object rowKey) {
        if(map2D.containsKey(rowKey)) {
            target.putAll(map2D.get(rowKey));
        }
        return this;
    }

    @Override
    public Map2D fillMapFromColumn(Map target, Object columnKey) {
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> iMap: map2dSet){
            if(iMap.getValue().get(columnKey)!=null)
                target.put(iMap.getKey(), iMap.getValue().get(columnKey));
        }
        return this;
    }

    @Override
    public Map2D putAll(Map2D source) {
        this.map2D=source.rowMapView();
        return this;
    }

    @Override
    public Map2D putAllToRow(Map source, Object rowKey) {
        Set<Map.Entry<Object, Object>> mapSet=source.entrySet();
        for(Map.Entry<Object, Object> element: mapSet){
            this.put(rowKey, element.getKey(), element.getValue());
        }
        return this;
    }

    @Override
    public Map2D putAllToColumn(Map source, Object columnKey) {
        Set<Map.Entry<Object, Object>> mapSet=source.entrySet();
        for(Map.Entry<Object, Object> element: mapSet){
            this.put(element.getKey(), columnKey, element.getValue());
        }
        return this;
    }

    @Override
    public Map2D copyWithConversion(Function rowFunction, Function columnFunction, Function valueFunction) {
        Map2D convertedMap2D=Map2D.createInstance();

        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> iMap: map2dSet){
            Object rowKey=iMap.getKey();
            Object convertedRowKey=rowFunction.apply(rowKey);

            Set<Map.Entry<Object, Object>> mapSet=iMap.getValue().entrySet();
            for(Map.Entry<Object, Object> element: mapSet){
                Object columnKey=element.getKey();
                Object convertedColumnKey=columnFunction.apply(columnKey);

                Object value=element.getValue();
                Object convertedValue=valueFunction.apply(value);

                convertedMap2D.put(convertedRowKey, convertedColumnKey, convertedValue);
            }
        }

        return convertedMap2D;
    }
}
