package ca.tokenizing_parser.tokenizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * A wrapper class for a {@link HashMap} that adds the additional feature of auto incrementing a key
 * id for each unique value added. Values of the same time are referenced by the same key, which can
 * have a prefix for easy identification. The keys are of type {@link String} since the prefix can
 * be anything, the values are of type &lt;E&gt;.
 * 
 * @author Kevin Jalbert
 * @param <E> the value's type that will be stored within the {@link HashMap}
 */
public class AutoKeyHashMap<E> {

	/** The {@link HashMap} to be used. */
	private HashMap<String, E>	_autoKeyHashMap	= null;

	/** The count of the last inputed value */
	private int					_valueCount		= 0;

	/** The specified prefix for each key */
	private String				_keyPrefix		= null;

	/**
	 * Constructor that sets up the wrapped {@link HashMap} for use, there will be no key prefix
	 * used.
	 */
	public AutoKeyHashMap() {
		_autoKeyHashMap = new HashMap<String, E>();
		_keyPrefix = "";
	}

	/**
	 * Constructor that sets up the wrapped {@link HashMap} for use, the specified key's prefix will
	 * be used.
	 * 
	 * @param keyPrefix the key's prefix to be prepended to each key
	 */
	public AutoKeyHashMap( String keyPrefix ) {
		_autoKeyHashMap = new HashMap<String, E>();
		_keyPrefix = keyPrefix;
	}

	/**
	 * Gets the key's prefix for this {@link AutoKeyHashMap}.
	 * 
	 * @return the key's prefix
	 */
	public String getKeyPrefix() {
		return _keyPrefix;
	}

	/**
	 * Sets the key's prefix for this {@link AutoKeyHashMap}.
	 */
	public void setKeyPrefix( String keyPrefix ) {
		_keyPrefix = keyPrefix;
	}

	/**
	 * Gets the value that is indexed with the specified key.
	 * 
	 * @param key the unique key acquired after storing a value
	 * @return value indexed by the specified key
	 */
	public E getValue( String key ) {
		return _autoKeyHashMap.get( key );
	}

	/**
	 * Gets the key that is referencing the specified value (values are unique).
	 * 
	 * @param value the unique value to be used to find the key
	 * @return key that is used to reference the specified value
	 */
	public String getKey( E value ) {

		String keyId = null;

		for( String key : getAllKeys() ) {
			if( getValue( key ).equals( value ) || getValue( key ) == value ) {
				keyId = key;
			}
		}

		return keyId;
	}

	/**
	 * Adds a value into the {@link HashMap} and returns an identifier key for it. The key will
	 * either be the next unique key for this {@link AutoKeyHashMap} or it will be an existing key
	 * if the value already resides in the {@link AutoKeyHashMap}.
	 * 
	 * @param value the value to be stored in the {@link HashMap}
	 * @return the key that is used to index the specified value
	 */
	public String addValue( E value ) {

		String keyId = null;

		// Check to see if this value has already been added
		if( _autoKeyHashMap.containsValue( value ) ) {

			// Iterate over the key set
			for( String key : _autoKeyHashMap.keySet() ) {

				// Find the key that was used for the identical value
				if( _autoKeyHashMap.get( key ).equals( value ) ) {
					keyId = key;
				}
			}
		}
		else { // The value hasn't been added before

			// Get the next key id for this value, and added it to the hashmap
			keyId = getNextKey();
			_autoKeyHashMap.put( keyId, value );
		}

		return keyId;
	}

	/**
	 * Removes the value indexed by the specified key. This does not affect the auto incrementing id
	 * of the {@link AutoKeyHashMap}, as the id of the removed value is forever lost.
	 * 
	 * @param key the specified key to be used in the removal of the referenced value
	 */
	public void removeValue( String key ) {
		_autoKeyHashMap.remove( key );
	}

	/**
	 * Clears the {@link HashMap} and resets the auto incrementing unique id back to 0.
	 */
	public void clearAll() {
		_autoKeyHashMap.clear();
		_valueCount = 0;
	}

	/**
	 * Gets a {@link Set} of all the {@link String} keys in the {@link HashMap}.
	 */
	public Set<String> getAllKeys() {
		return _autoKeyHashMap.keySet();
	}

	/**
	 * Gets a {@link Collection} of all the {@link String} values in the {@link HashMap}.
	 */
	public Collection<E> getAllValues() {
		return _autoKeyHashMap.values();
	}

	/**
	 * Checks to see if the {@link HashMap} is empty (ie: no values are stored).
	 * 
	 * @return true if the {@link AutoKeyHashMap} is empty
	 */
	public boolean isEmpty() {
		return _autoKeyHashMap.isEmpty();
	}

	/**
	 * Acquires the next unique key for this {@link AutoKeyHashMap}.
	 * 
	 * @return the next unique key
	 */
	private String getNextKey() {
		String key = _keyPrefix + _valueCount;
		_valueCount++;
		return key;
	}
}
