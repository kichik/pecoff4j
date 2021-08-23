/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.util;

public class IntMap {
	private final Entry[] hashtable;
	private int size;

	public IntMap() {
		this(16);
	}

	public IntMap(int size) {
		hashtable = new Entry[size];
	}

	public void remove(int key) {
		int pos = Math.abs(key % hashtable.length);
		Entry e = hashtable[pos];
		if (e == null) {
			return;
		} else if (e.key == key) {
			hashtable[pos] = e.next;
			size--;
		} else {
			Entry prev = e;
			e = e.next;
			while (e != null) {
				if (e.key == key) {
					prev.next = e.next;
					size--;
				}
				prev = e;
				e = e.next;
			}
		}
	}

	public void put(int key, Object value) {
		int pos = Math.abs(key % hashtable.length);
		Entry e = hashtable[pos];
		if (e == null) {
			hashtable[pos] = new Entry(key, value);
			size++;
		} else if (e.key == key) {
			e.value = value;
		} else {
			while (e.next != null) {
				if (e.next.key == key) {
					e.next.value = value;
					return;
				}
				e = e.next;
			}
			e.next = new Entry(key, value);
			size++;
		}
	}

	public Object get(int key) {
		int pos = Math.abs(key % hashtable.length);
		Entry e = hashtable[pos];
		if (e == null) {
			return null;
		} else if (e.key == key) {
			return e.value;
		} else {
			while (e.next != null) {
				if (e.next.key == key) {
					return e.next.value;
				}
				e = e.next;
			}
		}
		return null;
	}

	public int[] keySet() {
		int[] keys = new int[size];
		int idx = 0;
        for (Entry entry : hashtable)  {
            Entry e = entry;
            while (e != null) {
                keys[idx++] = e.key;
                e = e.next;
            }
        }
		return keys;
	}

	public int size() {
		return size;
	}

	private static class Entry {
		public final int key;
		public Object value;
		public Entry next;

		public Entry(int key, Object value) {
			this.key = key;
			this.value = value;
		}
	}
}
