package uj.java.pwj2019.map2d;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;

public class MyMap2D implements Map2D {
    //Map<Object, Map<Object, Object>> map2D=new HashMap<Object, Map<Object, Object>>();
    Map<Object, Map<Object, Object>> map2D=new LinkedHashMap<>();
    //Map<Pair<Object, Object>, Object> map2D=new HashMap<Pair<Object, Object>, Object >();

    @Override
    public Object put(Object rowKey, Object columnKey, Object value) {
        if(rowKey==null || columnKey==null)
            throw new NullPointerException();

        map2D.put(rowKey, new HashMap<>());
        Object preValue=map2D.get(rowKey).get(columnKey);
        map2D.get(rowKey).put(columnKey, value);
        return preValue;
        //return map2D.put(new Pair(rowKey, columnKey), value);
    }

    @Override
    public Object get(Object rowKey, Object columnKey) {
        if(map2D.get(rowKey)!=null)
            return map2D.get(rowKey).get(columnKey);
        return null;
        //return map2D.get(new Pair(rowKey, columnKey));
    }

    @Override
    public Object getOrDefault(Object rowKey, Object columnKey, Object defaultValue) {
        return map2D.getOrDefault(rowKey, Collections.emptyMap()).getOrDefault(columnKey, defaultValue);
        //return map2D.getOrDefault(new Pair(rowKey, columnKey), defaultValue);
    }

    @Override
    public Object remove(Object rowKey, Object columnKey) {
        return map2D.getOrDefault(rowKey, Collections.emptyMap()).remove(columnKey);
        //return map2D.remove(new Pair(rowKey, columnKey));
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
        System.out.println("Test1");
        System.out.println(map2D);
        if(map2D.containsKey("X"))
            System.out.println(map2D.get("X").keySet());
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> map: map2dSet){
            //System.out.println(map.getValue().size());
            size+=map.getValue().size();
            System.out.println("Test2");
            System.out.println(map);
        }
        return size;
        //return map2D.size();*
    }

    @Override
    public void clear() {
        map2D.clear();
    }

    @Override
    public Map rowView(Object rowKey) {
        return map2D.get(rowKey);
        /*Map<Pair<Object, Object>, Object> map=new HashMap<Pair<Object, Object>, Object>();
        Set<Map.Entry<Pair<Object, Object>, Object>> mapSet=map2D.entrySet();
        for(Map.Entry<Pair<Object, Object>, Object> element: mapSet)
            if(element.getKey().getKey().equals(rowKey))
                map.put(element.getKey(), element.getValue());
        return Collections.unmodifiableMap(map);*/
    }

    @Override
    public Map columnView(Object columnKey) {
        Map<Object, Object> map=new HashMap<Object, Object>();
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> iMap: map2dSet)
            if(iMap.getValue().get(columnKey)!=null)
                map.put(iMap.getKey(), iMap.getValue().get(columnKey));
        return map;

        /*Map<Pair<Object, Object>, Object> map=new HashMap<Pair<Object, Object>, Object>();
        Set<Map.Entry<Pair<Object, Object>, Object>> mapSet=map2D.entrySet();
        for(Map.Entry<Pair<Object, Object>, Object> element: mapSet)
            if(element.getKey().getValue().equals(columnKey))ec
tr
                map.put(element.getKey(), element.getValue());
        return Collections.unmodifiableMap(map);*/
    }

    @Override
    public boolean hasValue(Object value) {
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> iMap: map2dSet)
            if(iMap.getValue().containsValue(value))
                return true;
        return false;
        //return map2D.containsValue(value);
    }

    @Override
    public boolean hasKey(Object rowKey, Object columnKey) {
        if(map2D.get(rowKey)!=null)
            return map2D.get(rowKey).get(columnKey)!=null;
        return false;
        //return map2D.containsKey(new Pair<>(rowKey, columnKey));
    }

    @Override
    public boolean hasRow(Object rowKey) {
        return map2D.containsKey(rowKey);

        /*Map<Pair<Object, Object>, Object> map=new HashMap<Pair<Object, Object>, Object>();
        Set<Map.Entry<Pair<Object, Object>, Object>> mapSet=map2D.entrySet();
        for(Map.Entry<Pair<Object, Object>, Object> element: mapSet)
            if(element.getKey().getKey().equals(rowKey))
                return true;
        return false;*/
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
        return map2D;
    }

    @Override
    public Map columnMapView() {
        //Map<Object, Map<Object, Object>> columnMap=new HashMap<>();
        Map2D columnMap=Map2D.createInstance();
        Set<Map.Entry<Object, Map<Object, Object>>> map2dSet=map2D.entrySet();
        for(Map.Entry<Object, Map<Object, Object>> iMap: map2dSet){
            Object rowKey=iMap.getKey();
            Set<Map.Entry<Object, Object>> mapSet=iMap.getValue().entrySet();
            for(Map.Entry<Object, Object> element: mapSet){
                Object columnKey=element.getKey();
                Object value=element.getValue();

                columnMap.put(columnKey, rowKey, value);
                //columnMap.put(rowKey, Collections.emptyMap());
                //columnMap.get(rowKey).put(columnKey, value);
            }
        }
        return columnMap.rowMapView();
    }

    @Override
    public Map2D fillMapFromRow(Map target, Object rowKey) {
        target=map2D.get(rowKey);
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
        Map2D conwertedMap2D=Map2D.createInstance();

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

                conwertedMap2D.put(convertedRowKey, convertedColumnKey, convertedValue);
                //conwertedMap2D.put(convertedRowKey, Collections.emptyMap());
                //conwertedMap2D.get(convertedRowKey).put(convertedColumnKey, convertedValue);
            }
        }

        return conwertedMap2D;
    }
}
