package ca.tokenizing_parser.tokenizer.languages;

import java.util.HashSet;

import ca.tokenizing_parser.tokenizer.TokenTable;

/**
 * This abstract class is used to conform languages to work in conjunction with the
 * {@link TokenTable} class. Various languages can extend this class to work in the
 * {@link TokenTable} class.
 * 
 * @author Kevin Jalbert
 */
public abstract class LanguageKeywords {

	/** The reserved keywords. */
	private HashSet<String>	_keywords			= new HashSet<String>();

	/** The delimiters (including symbols). */
	private HashSet<String>	_delimiters			= new HashSet<String>();

	/** The delimiters that are used for spacing. */
	private HashSet<String>	_spaceDelimiters	= new HashSet<String>();

	/** The delimiters that represent method calls. */
	private HashSet<String>	_methodCalls		= new HashSet<String>();

	/** The look ahead number for the language (based on the longest delimiter). */
	private int				_lookAheadNumber	= -1;

	/**
	 * Default constructor for the {@link LanguageKeywords} class.
	 */
	public LanguageKeywords() {
	}

	/**
	 * The passed string is checked to see if it resides in the keyword {@link HashSet}.
	 * 
	 * @param keyword the keyword to be checked against the keyword {@link HashSet}
	 * @return true if the keyword resides in the keyword {@link HashSet}
	 */
	public boolean isKeyword( String keyword ) {
		return _keywords.contains( keyword );
	}

	/**
	 * The passed string is checked to see if it resides in the delimiter {@link HashSet}.
	 * 
	 * @param delimiter the delimiter to be checked against the delimiter {@link HashSet}
	 * @return true if the delimiter resides in the delimiter {@link HashSet}
	 */
	public boolean isDelimiter( String delimiter ) {
		return _delimiters.contains( delimiter );
	}

	/**
	 * The passed string is checked to see if it resides in the space delimiter {@link HashSet}.
	 * 
	 * @param delimiter the delimiter to be checked against the space delimiter {@link HashSet}
	 * @return true if the delimiter resides in the space delimiter {@link HashSet}
	 */
	public boolean isSpaceDelimiter( String delimiter ) {
		return _spaceDelimiters.contains( delimiter );
	}

	/**
	 * The passed string is checked to see if it resides in the method call {@link HashSet}.
	 * 
	 * @param methodCall the method call to be checked against the method call {@link HashSet}
	 * @return true if the method call resides in the method call {@link HashSet}
	 */
	public boolean isMethodCall( String methodCall ) {
		return _methodCalls.contains( methodCall );
	}

	/**
	 * Adds a keyword to the keyword {@link HashSet}.
	 * 
	 * @param keyword the keyword
	 */
	protected void addKeyword( String keyword ) {
		_keywords.add( keyword );
	}

	/**
	 * Adds a delimiter to the delimiter {@link HashSet}.
	 * 
	 * @param delimiter the delimiter
	 */
	protected void addDelimiter( String delimiter ) {
		_delimiters.add( delimiter );
	}

	/**
	 * Adds a space delimiter to both the space delimiter and the delimiter {@link HashSet}.
	 * 
	 * @param spaceDelimiter the delimiter
	 */
	protected void addSpaceDelimiter( String spaceDelimiter ) {
		_delimiters.add( spaceDelimiter );
		_spaceDelimiters.add( spaceDelimiter );
	}

	/**
	 * Adds a method call delimiter to both the method call and the delimiter {@link HashSet}.
	 * 
	 * @param methodCall the method call delimiter
	 */
	protected void addMethodCall( String methodCall ) {
		_delimiters.add( methodCall );
		_methodCalls.add( methodCall );
	}

	/**
	 * Acquires the look ahead number to be used when parsing input of the specified language. The
	 * reasoning for the look ahead number is because there might be varies lengths of the
	 * delimiters, and it needs to be certain to take the largest matching one.
	 * <p>
	 * This is found by taking the longest length of all the delimiters and subtracting by one.
	 * 
	 * @return the look ahead number for this language
	 */
	public int getLookAheadNumber() {

		// Check to see if the look ahead number hasn't be changed
		if( _lookAheadNumber == -1 ) {

			// Iterate through all the delimiters
			for( String delimiter : _delimiters ) {

				// Take the largest delimiter
				if( delimiter.length() > _lookAheadNumber ) {
					_lookAheadNumber = delimiter.length() - 1;
				}
			}
		}

		return _lookAheadNumber;
	}

	/**
	 * The method used to populate the {@link HashSet}s of the keywords, delimiters, space
	 * delimiters and method calls for the language.
	 */
	protected abstract void populate();
}
