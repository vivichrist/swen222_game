/**
 * 
 */
package ServerClients;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author  Zhaojiang Chang
 *
 */
public class clientNameMap<K,V> extends HashMap<K,V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * remove key from give value
	 * 
	 * */
	public void removeByValue(Object o){
		for(Object key: keySet()){
			if(get(key)==o){
				remove(key);
				break;
			}
		}
	}
	
	/**
	 * return a set of value
	 * */
	public Set<V> valueSet(){
		Set<V>values = new HashSet<V>();
		for(K key: keySet()){
			values.add(get(key));
		}
		return values;
	}
	
	/**
	 * search for key from value
	 * 
	 * **/
	public K getKeyByValue(V value){
		for(K key: keySet()){
			if(get(key).equals(value)&&get(key)==value){
				return key;
			}
		}
		return null;

	}
	
	/**
	 * 
	 * 
	 * */
	public V put(K key, V value){
        for (V val: valueSet()){
            if(val.equals(value)&&val.hashCode()==value.hashCode()){
                throw new RuntimeException("no duplicate value allowed");
            }
        }
        return super.put(key,value);
    }
	

}
