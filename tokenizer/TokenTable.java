package ca.tokenizing_parser.tokenizer;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.regex.Pattern;

import ca.tokenizing_parser.tokenizer.languages.LanguageKeywords;

/**
 * This class is used to transform tokens into identifiers through a mapping process. Identical
 * tokens will be replaced with the same identifier, while new tokens are replaced with an auto
 * incremented identifier using the {@link AutoKeyHashMap} collection. The purpose of the
 * {@link TokenTable} is to abstract away the details of the tokens while retaining the values
 * through the mapping of the identifier.
 * <p>
 * Identical tokens will share the same character. The identifiers will replace and auto increment
 * for each token category:
 * <ul>
 * <li>Literals
 * <li>Primitives
 * <li>Objects
 * <li>Comments
 * <li>Keywords
 * <li>Delimiter
 * </ul>
 * 
 * @author Kevin Jalbert
 */
public class TokenTable {

	/** The constant to reference to the literal {@link AutoKeyHashMap} */
	public final static String		_LITERALS	= ":l";

	/** The constant to reference to the primitive {@link AutoKeyHashMap} */
	public final static String		_PRIMITIVES	= ":p";

	/** The constant to reference to the object {@link AutoKeyHashMap} */
	public final static String		_OBJECTS	= ":o";

	/** The constant to reference to the comment {@link AutoKeyHashMap} */
	public final static String		_COMMENTS	= ":c";

	/** The constant to reference to the keyword {@link AutoKeyHashMap} */
	public final static String		_KEYWORDS	= ":k";

	/** The constant to reference to the delimiter {@link AutoKeyHashMap} */
	public final static String		_DELIMITERS	= ":d";

	/** The {@link AutoKeyHashMap} of the literal tokens. */
	private AutoKeyHashMap<String>	_literals	= null;

	/** The {@link AutoKeyHashMap} of the primitive tokens. */
	private AutoKeyHashMap<String>	_primitives	= null;

	/** The {@link AutoKeyHashMap} of the object tokens. */
	private AutoKeyHashMap<String>	_objects	= null;

	/** The {@link AutoKeyHashMap} of the comment tokens. */
	private AutoKeyHashMap<String>	_comments	= null;

	/** The {@link AutoKeyHashMap} of the keyword tokens. */
	private AutoKeyHashMap<String>	_keywords	= null;

	/** The {@link AutoKeyHashMap} of the delimiter tokens. */
	private AutoKeyHashMap<String>	_delimiter	= null;

	/**
	 * Instantiates a new token table with the default key prefixes for each category of identifier.
	 * To change the prefix values acquire the specific {@link AutoKeyHashMap} then change it using
	 * the appropriate method.
	 * <p>
	 * To avoid complications each prefix should be uniquely identified using the
	 * {@link String#contains(CharSequence)} method (no substrings), otherwise undesired results
	 * will occur using the {@link TokenTable#getMap(String)} method.
	 * <ul>
	 * <li>literals :l
	 * <li>primitives :p
	 * <li>objects :o
	 * <li>comments :c
	 * <li>keywords :k
	 * <li>delimiter :d
	 * </ul>
	 */
	public TokenTable() {
		_literals = new AutoKeyHashMap<String>( _LITERALS );
		_primitives = new AutoKeyHashMap<String>( _PRIMITIVES );
		_objects = new AutoKeyHashMap<String>( _OBJECTS );
		_comments = new AutoKeyHashMap<String>( _COMMENTS );
		_keywords = new AutoKeyHashMap<String>( _KEYWORDS );
		_delimiter = new AutoKeyHashMap<String>( _DELIMITERS );
	}

	/**
	 * Acquires a {@link String} table output of the tokenized input (has been converted into
	 * identifiers) to the mapped values.
	 * 
	 * @param tokenizedInput the passed tokenized input to be used to find the token mapping
	 * @return a {@link String} representing the token mapping for the tokenized input
	 */
	public String getTokenMappingForTokenizedInput( ArrayList<String> tokenizedInput ) {

		// If the input is empty return an empty string
		if( tokenizedInput.isEmpty() ) {
			return "";
		}

		StringBuilder output = new StringBuilder();

		output.append( "----------TOKEN TABLE----------\n" );

		for( String token : tokenizedInput ) {
			if( token.contains( _literals.getKeyPrefix() ) ) {
				output.append( String.format( "Literal:  	Key  %s	Value  %s", token,
						_literals.getValue( token ) ) );
			}
			else if( token.contains( _primitives.getKeyPrefix() ) ) {
				output.append( String.format( "Primitive:	Key  %s	Value  %s", token,
						_primitives.getValue( token ) ) );
			}
			else if( token.contains( _objects.getKeyPrefix() ) ) {
				output.append( String.format( "Object:   	Key  %s	Value  %s", token,
						_objects.getValue( token ) ) );
			}
			else if( token.contains( _comments.getKeyPrefix() ) ) {
				output.append( String.format( "Comment:  	Key  %s	Value  %s", token,
						_comments.getValue( token ) ) );
			}
			else if( token.contains( _keywords.getKeyPrefix() ) ) {
				output.append( String.format( "Keyword:  	Key  %s	Value  %s", token,
						_keywords.getValue( token ) ) );
			}
			else if( token.contains( _delimiter.getKeyPrefix() ) ) {
				output.append( String.format( "Delimiter:  	Key  %s	Value  %s", token,
						_delimiter.getValue( token ) ) );
			}
			output.append( "\n" );
		}

		return output.toString().trim();
	}

	/**
	 * Acquires a {@link String} table output of the {@link TokenTable}'s mapped values.
	 * 
	 * @return the {@link String} representing the current token mapping of the {@link TokenTable}
	 */
	public String getTokenMapping() {

		// Check to see if the token table is completely empty, if so return an empty string
		if( _literals.isEmpty() && _primitives.isEmpty() && _objects.isEmpty()
				&& _comments.isEmpty() && _keywords.isEmpty() ) {
			return "";
		}

		StringBuilder output = new StringBuilder();

		output.append( "----------LITERAL TOKEN TABLE----------\n" );
		for( String key : _literals.getAllKeys() ) {
			output.append( String.format( "Key  %s	Value  %s\n", key, _literals.getValue( key ) ) );
		}

		output.append( "\n----------PRIMITIVE TOKEN TABLE----------\n" );
		for( String key : _primitives.getAllKeys() ) {
			output.append( String.format( "Key  %s	Value  %s\n", key, _primitives.getValue( key ) ) );
		}

		output.append( "\n----------OBJECT TOKEN TABLE----------\n" );
		for( String key : _objects.getAllKeys() ) {
			output.append( String.format( "Key  %s	Value  %s\n", key, _objects.getValue( key ) ) );
		}

		output.append( "\n----------COMMENT TOKEN TABLE----------\n" );
		for( String key : _comments.getAllKeys() ) {
			output.append( String.format( "Key  %s	Value  %s\n", key, _comments.getValue( key ) ) );
		}

		output.append( "\n----------KEYWORD TOKEN TABLE----------\n" );
		for( String key : _keywords.getAllKeys() ) {
			output.append( String.format( "Key  %s	Value  %s\n", key, _keywords.getValue( key ) ) );
		}

		output.append( "\n----------DELIMITER TOKEN TABLE----------\n" );
		for( String key : _delimiter.getAllKeys() ) {
			output.append( String.format( "Key  %s	Value  %s\n", key, _delimiter.getValue( key ) ) );
		}

		return output.toString().trim();
	}

	/**
	 * Gets the specified {@link AutoKeyHashMap} that the token key belongs too.
	 * 
	 * @param tokenKey the token key that the {@link AutoKeyHashMap} corresponds too
	 * @return the correct {@link AutoKeyHashMap} based on the token key; null if token key didn't
	 *         match any maps
	 */
	public AutoKeyHashMap<String> getMap( String tokenKey ) {

		// Return the correct map
		if( tokenKey.contains( _LITERALS ) ) {
			return _literals;
		}
		else if( tokenKey.contains( _PRIMITIVES ) ) {
			return _primitives;
		}
		else if( tokenKey.contains( _OBJECTS ) ) {
			return _objects;
		}
		else if( tokenKey.contains( _COMMENTS ) ) {
			return _comments;
		}
		else if( tokenKey.contains( _KEYWORDS ) ) {
			return _keywords;
		}
		else if( tokenKey.contains( _DELIMITERS ) ) {
			return _delimiter;
		}
		else {
			return null;
		}
	}

	/**
	 * Clear all the {@link AutoKeyHashMap} being used in this {@link TokenTable}.
	 */
	public void clearAll() {
		_literals.clearAll();
		_primitives.clearAll();
		_objects.clearAll();
		_comments.clearAll();
		_keywords.clearAll();
		_delimiter.clearAll();
	}

	/**
	 * Replace all the literals and comments in the input {@link String} with the appropriate
	 * identifier from the literal and comment {@link AutoKeyHashMap}. Can also remove the literals
	 * and comments in the input; literals are replaced with empty quotes and comments are entirely
	 * removed.
	 * <p>
	 * Java styled literals and comments are used.
	 * 
	 * @param input the input {@link String} to have all the literals and comments replaced with
	 *            identifiers
	 * @param replaceLiterals if toggled the literals will be replaced with identifiers, otherwise
	 *            they are removed
	 * @param replaceComments if toggled the comments will be replaced with identifiers, otherwise
	 *            they are removed
	 * @return the input {@link String} with identifiers replacing all the literals and comments
	 */
	public String replaceRemoveLiteralsAndComments( String input, boolean replaceLiterals,
			boolean replaceComments ) {

		StringCharacterIterator iter = new StringCharacterIterator( input );

		boolean done = false;
		boolean inDoubleQuote = false;
		boolean inSingleQuote = false;
		boolean inLineComment = false;
		boolean inBlockComment = false;
		StringBuilder output = new StringBuilder();
		StringBuilder literal = new StringBuilder();
		StringBuilder comment = new StringBuilder();

		// Keep going till no more characters
		while( !done ) {

			char token = iter.current();
			boolean escaped = false;

			// Handle the token
			if( token == CharacterIterator.DONE ) { // Handles initial token as ending
				done = true;
			}
			else { // Valid token 

				if( token == '\"' && !inSingleQuote && !inLineComment && !inBlockComment ) { // Handle the double quote case

					if( inDoubleQuote ) { // Inside the double quotes

						// Check to see escape status, then return position
						int currentPosition = iter.getIndex();
						while( iter.previous() == '\\' ) {
							if( escaped ) {
								escaped = false;
							}
							else {
								escaped = true;
							}
						}
						iter.setIndex( currentPosition );

						if( !escaped ) { // If not escaped then exit this double quote

							// If the replace flag is toggled replace with identifier; otherwise remove
							if( replaceLiterals ) {
								output.append( _literals.addValue( "\"" + literal.toString() + "\"" ) );
							}
							else {
								output.append( "\"\"" );
							}
							literal.delete( 0, literal.length() );
							inDoubleQuote = false;
						}
						else { // In the double quote still; append token to literal output
							literal.append( token );
						}

					}
					else { // Outside double quotes; just entered them
						inDoubleQuote = true;
					}
				}
				else if( token == '\'' && !inDoubleQuote && !inLineComment && !inBlockComment ) { // Handle the single quote case

					if( inSingleQuote ) { // Inside the single quotes

						// Check to see escape status, then return position
						int currentPosition = iter.getIndex();
						while( iter.previous() == '\\' ) {
							if( escaped ) {
								escaped = false;
							}
							else {
								escaped = true;
							}
						}
						iter.setIndex( currentPosition );

						if( !escaped ) { // If not escaped then exit this single quote

							// If the replace flag is toggled replace with identifier; otherwise remove
							if( replaceLiterals ) {
								output.append( _literals.addValue( "\'" + literal.toString() + "\'" ) );
							}
							else {
								output.append( "\'\'" );
							}
							literal.delete( 0, literal.length() );
							inSingleQuote = false;
						}
						else { // In the single quote still; append token to literal output
							literal.append( token );
						}
					}
					else { // Outside single quotes; just entered them
						inSingleQuote = true;
					}
				}
				else if( token == '/' && !inDoubleQuote && !inSingleQuote ) { // Handle the start of line/block comment

					if( !inBlockComment && !inLineComment ) { // Not inside a comment

						int currentPosition = iter.getIndex();
						char nextToken = iter.next();

						if( nextToken == '/' ) { // The line comment starts
							inLineComment = true;
						}
						else if( nextToken == '*' ) { // The block comment starts
							inBlockComment = true;
						}
						else { // Reset back; false alarm
							iter.setIndex( currentPosition );
						}
					}
					else { // Inside a comment still; append token to comment output
						comment.append( token );
					}
				}
				else if( token == '\n' && inLineComment ) { // Handle end of the line comment

					// If the replace flag is toggled replace with identifier; otherwise remove
					if( replaceComments ) {
						output.append( _comments.addValue( "//" + comment.toString() + "\n" )
								+ "\n" );
					}
					else {
						output.append( "" );
					}
					comment.delete( 0, comment.length() );
					inLineComment = false;
				}
				else if( token == '*' && inBlockComment ) { // Handle end of the block comment

					int currentPosition = iter.getIndex();
					char nextToken = iter.next();

					if( nextToken == '/' ) { // The block comment ends

						// If the replace flag is toggled replace with identifier; otherwise remove
						if( replaceComments ) {
							output.append( _comments.addValue( "/*" + comment.toString() + "*/" ) );
						}
						else {
							output.append( "\n" );
						}
						comment.delete( 0, comment.length() );
						inBlockComment = false;
					}
					else { // Reset back; false alarm
						iter.setIndex( currentPosition );
					}
				}
				else if( inDoubleQuote || inSingleQuote ) { // Inside a literal; append token
					literal.append( token );
				}
				else if( inBlockComment || inLineComment ) { // Inside a comment; append token 
					comment.append( token );
				}
				else { // Not inside anything; append token to output
					output.append( token );
				}
				if( iter.next() == CharacterIterator.DONE ) { // Check to see if the end is next
					done = true;
					// Wrap up the possibility that the ending was a line comment
					if( inLineComment ) {

						// If the replace flag is toggled replace with identifier; otherwise remove
						if( replaceComments ) {
							output.append( _comments.addValue( "//" + comment.toString() ) );
						}
						else {
							output.append( "" );
						}
					}
				}
			}
		}
		return output.toString();
	}

	/**
	 * Replace all the primitives in the {@link ArrayList} of {@link String} tokens with the
	 * appropriate identifier from the primitive {@link AutoKeyHashMap}. Due to the tokenizing
	 * process, primitives such as one with a decimal (ie: 2.51) are split over 3 tokens. This
	 * method will discover and piece together the tokens and replace them with a single identifier.
	 * 
	 * @param tokenizedInput the {@link ArrayList} of {@link String} tokens to have all the
	 *            (potentially split up) primitives replaced with identifiers
	 * @return the tokenizedInput the {@link ArrayList} of {@link String} tokens with identifiers
	 *         replacing all the primitives
	 */
	public ArrayList<String> replacePrimitives( ArrayList<String> tokenizedInput ) {

		// Acquire a temporary copy of the tokenized input to be filled with the new tokenize input
		ArrayList<String> tempTokenizedInput = new ArrayList<String>();

		// Acquire an iterator for the tokenized input
		ListIterator<String> iter = tokenizedInput.listIterator();

		// Loop for as long as there is more tokens
		while( iter.hasNext() ) {

			// Acquire the next token and reset the primitives
			String token = iter.next();
			String primitiveTokenLeft = ""; // The left side of the primitive (2).51
			String primitiveTokenRight = ""; // The right side of the primitive 2.(51)

			// Check to see if the current token matches a digit
			if( Pattern.matches( "[0-9]+", token ) ) {

				primitiveTokenLeft = token;
				token = iter.next();

				// Check to see if the current token matches the decimal point for a primitive
				if( token.equals( "." ) ) {

					token = iter.next();

					// Check to see if the current token matches a digit
					if( Pattern.matches( "[0-9]+", token ) ) {

						primitiveTokenRight = token;

						// Replace the captured primitive with an identifier
						tempTokenizedInput.add( _primitives.addValue( primitiveTokenLeft + "."
								+ primitiveTokenRight ) );
					}
					else { // Current token didn't match a digit

						// Go back one token then replace the captured primitive with an identifier
						iter.previous();
						tempTokenizedInput.add( _primitives.addValue( primitiveTokenLeft + "." ) );
					}
				}
				else { // No match on the decimal point

					// Go back one token then replace the captured primitive with an identifier
					iter.previous();
					tempTokenizedInput.add( _primitives.addValue( primitiveTokenLeft ) );
				}
			}
			else if( token.equals( "." ) ) { // Handles situations such without leading digit (.51)

				// Temporary token is used since a reference to the original is needed if this fails
				String tempToken = iter.next();

				// Check to see if the current token matches a digit
				if( Pattern.matches( "[0-9]+", tempToken ) ) {

					primitiveTokenRight = tempToken;

					// Replace the captured primitive with an identifier
					tempTokenizedInput.add( _primitives.addValue( "." + primitiveTokenRight ) );
				}
				else { // No match on a decimal with trailing digits; keep decimal as a token

					// Go back one token then add the decimal as a token to the tokenized input
					iter.previous();
					tempTokenizedInput.add( token );
				}
			}
			else { // No match on either a digit or a decimal

				// Add the token to the tokenized input
				tempTokenizedInput.add( token );
			}
		}

		// Replace the tokenized input with the temporary tokenized input (has the replacements)
		tokenizedInput = tempTokenizedInput;
		return tokenizedInput;
	}

	/**
	 * Replace all the objects in the {@link ArrayList} of {@link String} tokens with the
	 * appropriate identifier from the object {@link AutoKeyHashMap}. Due to the tokenizing process,
	 * objects such as with method calls (ie: obj.call) are split over 3 tokens. This method will
	 * discover and piece together the tokens and replace them with a single identifier.
	 * 
	 * @param tokenizedInput the {@link ArrayList} of {@link String} tokens to have all the
	 *            (potentially split up) objects replaced with identifiers
	 * @param language the {@link LanguageKeywords} to be used when replacing objects
	 * @return the tokenizedInput the {@link ArrayList} of {@link String} tokens with identifiers
	 *         replacing all the objects
	 */
	public ArrayList<String> replaceObjects( ArrayList<String> tokenizedInput,
			LanguageKeywords language ) {

		// Acquire a temporary copy of the tokenized input to be filled with the new tokenize input
		ArrayList<String> tempTokenizedInput = new ArrayList<String>();

		// Acquire an iterator for the tokenized input
		ListIterator<String> iter = tokenizedInput.listIterator();

		// Loop for as long as there is more tokens
		while( iter.hasNext() ) {

			String token = iter.next();
			StringBuffer object = new StringBuffer();

			// Check if the token matches a valid object name, and is not a keyword or delimiter
			if( Pattern.matches( "[_a-zA-Z][0-9a-zA-Z_]*", token ) && !language.isKeyword( token )
					&& !language.isDelimiter( token ) ) {

				object.append( token );
				token = iter.next();
				boolean done = false; // Needed in the situation where multiple calls can happen
				// TODO Maybe take this farther to handle stuff like: obj.call1().call2( obj1).call3() = :o0

				// Keep looping till the ending of this object is reached
				while( !done ) {

					// Check to see if the token is method call delimiter
					if( language.isMethodCall( token ) ) {

						String methodCall = token; // Keep the method call token stored
						token = iter.next();

						// Check if the token matches an object name and is not a keyword or delimiter
						if( Pattern.matches( "[_a-zA-Z][0-9a-zA-Z_]*", token )
								&& !language.isKeyword( token ) && !language.isDelimiter( token ) ) {

							// Append the method call and token to the object; get the next token
							object.append( methodCall + token );
							token = iter.next();
						}
						else { // No match on the token to an object	

							// No more objects; go back a token and replace object with an identifier
							done = true;
							iter.previous();
							tempTokenizedInput.add( _objects.addValue( object.toString() ) );
						}
					}
					else { // Token didn't match method call delimiter

						// No more objects; go back a token and replace object with an identifier
						done = true;
						iter.previous();
						tempTokenizedInput.add( _objects.addValue( object.toString() ) );
					}
				}
			}
			else { // No match on an object

				// Add the token to the tokenized input
				tempTokenizedInput.add( token );
			}
		}

		// Replace the tokenized input with the temporary tokenized input (has the replacements)
		tokenizedInput = tempTokenizedInput;
		return tokenizedInput;
	}

	/**
	 * Replace all the keywords in the input {@link String} with the appropriate identifier from the
	 * keyword {@link AutoKeyHashMap}.
	 * 
	 * @param input the input {@link String} to have all the keywords replaced with identifiers
	 * @param language the {@link LanguageKeywords} to be used for replacing keywords
	 * @return the input {@link String} with identifiers replacing all the keywords
	 */
	public ArrayList<String> replaceKeywords( ArrayList<String> tokenizedInput,
			LanguageKeywords language ) {

		// Copy a temporary copy of the tokenized input
		ArrayList<String> tempTokenizedInput = new ArrayList<String>();

		ListIterator<String> iter = tokenizedInput.listIterator();

		while( iter.hasNext() ) {

			String token = iter.next();

			// If the token is a keyword then add it as a keyword
			if( language.isKeyword( token ) ) {
				tempTokenizedInput.add( _keywords.addValue( token ) );
			}
			else {
				tempTokenizedInput.add( token );
			}
		}
		tokenizedInput = tempTokenizedInput;
		return tokenizedInput;
	}

	/**
	 * Replace all the delimiters in the input {@link String} with the appropriate identifier from
	 * the delimiter {@link AutoKeyHashMap}.
	 * 
	 * @param input the input {@link String} to have all the delimiters replaced with identifiers
	 * @param language the {@link LanguageKeywords} to be used for replacing delimiters
	 * @return the input {@link String} with identifiers replacing all the delimiters
	 */
	public ArrayList<String> replaceDelimiters( ArrayList<String> tokenizedInput,
			LanguageKeywords language ) {

		// Copy a temporary copy of the tokenized input
		ArrayList<String> tempTokenizedInput = new ArrayList<String>();

		ListIterator<String> iter = tokenizedInput.listIterator();

		while( iter.hasNext() ) {

			String token = iter.next();

			// If the token is a delimiter then add it as a keyword
			if( language.isDelimiter( token ) ) {
				tempTokenizedInput.add( _delimiter.addValue( token ) );
			}
			else {
				tempTokenizedInput.add( token );
			}
		}
		tokenizedInput = tempTokenizedInput;
		return tokenizedInput;
	}
}
