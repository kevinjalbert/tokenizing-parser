package ca.tokenizing_parser.tokenizer;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

import ca.tokenizing_parser.tokenizer.languages.LanguageKeywords;

/**
 * This class will take a {@link String} input and tokenize it based on the language (an object that
 * extends the {@link LanguageKeywords}). There are two forms of output for the tokenized input.
 * <p>
 * The output can either be in a standard tokenized format (just a string {@link ArrayList} of
 * tokens). The second output is to map the tokens to identifies to abstract the text of the tokens.
 * <p>
 * The second type of output makes use of the {@link TokenTable} class which handles the mapping of
 * tokens to unique identifiers.
 * 
 * @author Kevin Jalbert
 */
public class Tokenizer {

	/** The {@link StringCharacterIterator} used to navigate the input string */
	private StringCharacterIterator	_iter			= null;

	/** The tokenized input of the passed input. */
	private ArrayList<String>		_tokenizedInput	= null;

	/** The token buffer of the next token to be added. */
	private String					_tokenBuffer	= "";

	/** The {@link LanguageKeywords} being used for this {@link Tokenizer}. */
	private LanguageKeywords		_language		= null;

	/** The {@link TokenTable} that holds the mapping of the tokens to identifiers. */
	private TokenTable				_tokenTable		= null;

	/**
	 * Instantiates a new {@link Tokenizer} with the specified {@link LanguageKeywords}.
	 * 
	 * @param language the {@link LanguageKeywords} language to be used for this {@link Tokenizer}
	 */
	public Tokenizer( LanguageKeywords language ) {
		_language = language;
		_iter = new StringCharacterIterator( "" );
		_tokenizedInput = new ArrayList<String>();
		_tokenTable = new TokenTable();
	}

	/**
	 * Sets the {@link LanguageKeywords} to be used for this {@link Tokenizer}.
	 * 
	 * @param language the new {@link LanguageKeywords} to be used
	 */
	public void setLanguage( LanguageKeywords language ) {
		_language = language;
	}

	/**
	 * Gets the {@link LanguageKeywords} that this {@link Tokenizer} is using.
	 * 
	 * @return the {@link LanguageKeywords} currently in use
	 */
	public LanguageKeywords getLanguage() {
		return _language;
	}

	/**
	 * Clears the {@link TokenTable} being used by this {@link Tokenizer}.
	 */
	public void clearTokenTable() {
		_tokenTable.clearAll();
	}

	/**
	 * Gets the {@link TokenTable} that is being used by this {@link Tokenizer}.
	 * 
	 * @return the {@link TokenTable} being used by this {@link Tokenizer}
	 */
	public TokenTable getTokenTable() {
		return _tokenTable;
	}

	/**
	 * Tokenize the input to produce an {@link ArrayList} of the actual tokens (excluding spacing
	 * delimiters). Comments are removed and literals are also reduced to "" and '' for simplicity.
	 * <p>
	 * There is <b>no</b> mapping of tokens to identifiers in this method.
	 * 
	 * @param input the input {@link String} to be tokenized
	 * @return an {@link ArrayList} of {@link String} tokens (excluding spacing), from the input
	 */
	public ArrayList<String> tokenizeInput( String input ) {

		// First remove comments and literals
		input = _tokenTable.replaceRemoveLiteralsAndComments( input, false, false );

		// Prepare to tokenize a new input
		_iter.setText( input );
		_tokenBuffer = "";

		// Perform the tokenization of the input
		boolean firstToken = true;
		addNextTokens( firstToken ); // First token is a special case
		firstToken = false;

		// Iterate till tokenization is done
		while( _iter.current() != CharacterIterator.DONE ) {
			addNextTokens( firstToken );
		}

		return _tokenizedInput;
	}

	/**
	 * Tokenize the input to produce an {@link ArrayList} of identifiers that map to corresponding
	 * tokens. This mapping occurs by using the {@link TokenTable} object. The option of keeping
	 * comments and/pr literals for the mapping process can be specified as well. A
	 * {@link LanguageKeywords} must have been set for this to work, otherwise a <code>null</code>
	 * is returned
	 * <p>
	 * The types of tokens are:
	 * <ul>
	 * <li>literals
	 * <li>primitives
	 * <li>comments (optional)
	 * <li>objects
	 * <li>keywords
	 * </ul>
	 * <p>
	 * There <b>is</b> mapping of tokens to identifiers in this method.
	 * 
	 * @param input the input {@link String} to be tokenized
	 * @param keepLiterals if true then the literals will be mapped as well, otherwise they are
	 *            omitted
	 * @param keepComments if true then the comments will be mapped as well, otherwise they are
	 *            omitted
	 * @return an {@link ArrayList} of {@link String} identifiers that correspond to tokens
	 *         (excluding spacing), from the input. If no {@link LanguageKeywords} is set a
	 *         <code>null</code> is returned.
	 */
	public ArrayList<String> tokenizeInputWithMapping( String input, boolean keepLiterals,
			boolean keepComments ) {

		// Make sure there is a language set if not return null.
		if( _language == null ) {
			return null;
		}

		// Replace/remove the literals and comments
		input = _tokenTable.replaceRemoveLiteralsAndComments( input, keepLiterals, keepComments );

		// Prepare to tokenize a new input
		_iter.setText( input );
		_tokenBuffer = "";
		_tokenizedInput.clear();

		// Perform the tokenization of the input
		boolean isFirstToken = true;
		addNextTokens( isFirstToken ); // First token is a special case
		isFirstToken = false;

		// Iterate till tokenization is done
		while( _iter.current() != CharacterIterator.DONE ) {
			addNextTokens( isFirstToken );
		}

		// Replace the primitives 
		_tokenizedInput = _tokenTable.replacePrimitives( _tokenizedInput );

		// Replace the objects
		_tokenizedInput = _tokenTable.replaceObjects( _tokenizedInput, _language );

		// Replace the keywords
		_tokenizedInput = _tokenTable.replaceKeywords( _tokenizedInput, _language );

		// Replace the delimiters
		_tokenizedInput = _tokenTable.replaceDelimiters( _tokenizedInput, _language );

		// Deep clone the token output
		ArrayList<String> tokenOutput = new ArrayList<String>();
		for( String token : _tokenizedInput ) {
			tokenOutput.add( token.toString() );
		}

		return tokenOutput;
	}

	/**
	 * Finds and adds the next token to the internal tokenized input {@link ArrayList}, using the
	 * {@link LanguageKeywords} of this {@link Tokenizer}. The process of finding tokens involves
	 * using the delimiters of the {@link LanguageKeywords} as well as a
	 * {@link StringCharacterIterator}.
	 * 
	 * @param isFirstToken if this is the first token to be found
	 */
	private void addNextTokens( boolean isFirstToken ) {

		// Special case of handling the first token's first character
		String character = null;
		if( isFirstToken ) {
			character = String.valueOf( _iter.current() );
		}
		else {
			character = String.valueOf( _iter.next() );
		}

		// Check to see if the the character is a delimiter
		if( _language.isDelimiter( character ) ) {

			// Add this character to the delimiter buffer 
			String delimiterBuffer = character;

			// Look farther ahead to see if a larger delimiter match can be found
			for( int i = 0; i < _language.getLookAheadNumber(); i++ ) {
				delimiterBuffer = delimiterBuffer.concat( String.valueOf( _iter.next() ) );
			}

			// Check to see if the farthest look ahead is a valid delimiter
			if( _language.isDelimiter( delimiterBuffer ) ) {

				// A delimiter was found, handle it and reset the buffer
				handleDelimiter( delimiterBuffer );
				delimiterBuffer = "";
			}
			else {

				// Fall back one character and try to match again; for as many look ahead times
				for( int i = 0; i < _language.getLookAheadNumber(); i++ ) {

					// Try without the last look ahead
					delimiterBuffer = delimiterBuffer.substring( 0, delimiterBuffer.length() - 1 );
					_iter.previous();

					// Check to see if the delimiter buffer matches now
					if( _language.isDelimiter( delimiterBuffer ) ) {

						// A delimiter was found, handle it and reset the buffer then break
						handleDelimiter( delimiterBuffer );
						delimiterBuffer = "";
						break;
					}
				}
			}
		}
		else if( _iter.current() == CharacterIterator.DONE ) { // The input string is done
			handleDelimiter( " " ); // Space delimiter to ensure the last token is caught
		}
		else { // No delimiter was found, so add this character to the token buffer
			_tokenBuffer = _tokenBuffer.concat( character );
		}
	}

	/**
	 * Handle the found delimiter appropriately along with the token buffer. Will add a token if
	 * there is something in the token buffer, as well as ignoring the delimiter buffer if the
	 * delimiter is a space delimiter.
	 * 
	 * @param delimiterBuffer a string that matched a delimiter found in the
	 *            {@link LanguageKeywords}
	 */
	private void handleDelimiter( String delimiterBuffer ) {

		// If token buffer is not empty then add the token buffer as a token
		if( !_tokenBuffer.isEmpty() ) {
			_tokenizedInput.add( _tokenBuffer );
		}

		// Add delimiter as a token only if it is not a space delimiter
		if( !_language.isSpaceDelimiter( delimiterBuffer ) ) {
			_tokenizedInput.add( delimiterBuffer );
		}

		// Reset the token buffer
		_tokenBuffer = "";
	}
}
