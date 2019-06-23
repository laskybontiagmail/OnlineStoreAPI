package com.lasky.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CustomMap<KeyType, ValueType> {
	private transient Map<KeyType, ValueType> internalMap = new ConcurrentHashMap<KeyType, ValueType>();

	/**
	 * Constructor.
	 */
	public CustomMap() {
	}
	
//	public CustomMap(Map<KeyType, ValueType> newMap) {
//		this.internalMap = new ConcurrentHashMap<KeyType, ValueType>(newMap);
//	}

	/**
	 * Set value
	 * @param key - key
	 * @param value -value
	 */
	public void set(KeyType key, ValueType value) {
		internalMap.put(key, value);
	}

	/**
	 * Get value
	 */
	public ValueType get(KeyType key) {
		ValueType value = internalMap.get(key);
		if (value != null) {
			return (ValueType) value;
		}
		return null;
	}

	/**
	 * Get all
	 * @return {Map<KeyType, ValueType>}
	 */
	//@SuppressWarnings("unchecked")
	public Map<KeyType, ValueType> getInternalMap() {
		return (Map<KeyType, ValueType>) internalMap;
	}
	
	/**
	 * Get values
	 * @return
	 */
	public List<ValueType> getValues() {
		List<ValueType> values = new ArrayList<ValueType>();
		values.addAll(this.internalMap.values());
		return values;
	}
	
	/**
	 * Get all the keys of this cache/map
	 * @return
	 */
	public List<KeyType> getKeys() {
		List<KeyType> keys = new ArrayList<KeyType>();
		keys.addAll(this.internalMap.keySet());
		return keys;
	}

	public boolean isEmpty() {
		return internalMap.isEmpty();
	}
	
	//@SuppressWarnings("rawtypes")
	public Iterator<Entry<KeyType, ValueType>> iterator() {
		return internalMap.entrySet().iterator();
	}
	
	@SuppressWarnings("rawtypes")
	public Set entries() {
		return internalMap.entrySet();
	}
	
	/**
	 * delete
	 * @param key - value
	 */
	public void delete(KeyType key) {
		internalMap.remove(key);
	}
	
	/**
	 * remove and retrieve
	 * @param key - value
	 */
	public ValueType remove(KeyType key) {
		return internalMap.remove(key);
	}
	
	public synchronized void replaceInternalMap(Map<KeyType, ValueType> newMap) {
		this.internalMap = new ConcurrentHashMap<KeyType, ValueType>(newMap);
	}
	
	/**
	 * Clear all the contents
	 */
	public void clearContents() {
		try {
			if (internalMap != null && !internalMap.isEmpty()) {
				internalMap.clear();
			}
		} catch (Exception exc) {
			exc = null;
		}
	}
	
	/**
	 * Clear all and set the internal cache to null
	 */
	public void clear() {
		this.clearContents();
		this.internalMap = null;
	}

}
