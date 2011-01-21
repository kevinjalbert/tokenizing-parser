package ca.tokenizing_parser.tokenizer.languages;

import java.util.HashSet;

/**
 * A data class to represent of all Java Keywords (1.2, 1.4 and 5.0 Java) as well as the possible
 * delimiters. The keywords and delimiters are stored within separate {@link HashSet} which are
 * inherited from {@link LanguageKeywords}. This class is also implemented in a singleton design
 * pattern.
 * 
 * @author Kevin Jalbert
 */
public class JavaKeywords extends LanguageKeywords {

	private static JavaKeywords	_instance	= null;

	/**
	 * Private constructor to conform to the singleton design pattern of this class.
	 */
	private JavaKeywords() {

		// Populate the language
		populate();
	}

	/**
	 * Acquire singleton instance of the {@link JavaKeywords} class.
	 * 
	 * @return singleton instance of the {@link JavaKeywords} class
	 */
	public static JavaKeywords getInstance() {

		if( _instance == null ) {
			_instance = new JavaKeywords();
		}

		return _instance;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Populates the {@link LanguageKeywords} with the {@link JavaKeywords}.
	 */
	@Override
	protected void populate() {

		// Standard
		addSpaceDelimiter( "\n" ); // New line
		addSpaceDelimiter( "\r" ); // Carriage return
		addSpaceDelimiter( " " ); // Space
		addSpaceDelimiter( "	" ); // Tab
		addDelimiter( ";" ); // Statement termination

		// Brackets
		addDelimiter( "(" ); // Open parentheses
		addDelimiter( ")" ); // Close parentheses
		addDelimiter( "[" ); // Open array index
		addDelimiter( "]" ); // Close array index
		addDelimiter( "{" ); // Open statement delimiter
		addDelimiter( "}" ); // Close statement delimiter

		// Literals
		addDelimiter( "\"" ); // String literal
		addDelimiter( "'" ); // Character literal

		// Logic
		addDelimiter( "!" ); // Logical not
		addDelimiter( "||" ); // Logical or
		addDelimiter( "&&" ); // Logical and
		addDelimiter( "?" ); // Used in single line if statments

		// Math Operations
		addDelimiter( "+" ); // Addition
		addDelimiter( "-" ); // Subtraction
		addDelimiter( "*" ); // Multiplication
		addDelimiter( "/" ); // Division
		addDelimiter( "%" ); // Modulo
		addDelimiter( "=" ); // Assignment
		addDelimiter( "++" ); // Increase
		addDelimiter( "--" ); // Decrease

		// Comparators
		addDelimiter( "==" ); // Equal
		addDelimiter( "!=" ); // Does not equal
		addDelimiter( ">" ); // Greater than
		addDelimiter( "<" ); // Less than
		addDelimiter( ">=" ); // Greater than or equal to
		addDelimiter( "<=" ); // Less than or equal to

		// Methods and decimal
		addMethodCall( "." ); // Method's dot notation and decimal

		// Java keywords
		addKeyword( "abstract" );
		addKeyword( "assert" );
		addKeyword( "boolean" );
		addKeyword( "break" );
		addKeyword( "byte" );
		addKeyword( "case" );
		addKeyword( "catch" );
		addKeyword( "char" );
		addKeyword( "class" );
		addKeyword( "const" );
		addKeyword( "continue" );
		addKeyword( "default" );
		addKeyword( "do" );
		addKeyword( "double" );
		addKeyword( "else" );
		addKeyword( "enum" );
		addKeyword( "extends" );
		addKeyword( "final" );
		addKeyword( "finally" );
		addKeyword( "float" );
		addKeyword( "for" );
		addKeyword( "goto" );
		addKeyword( "if" );
		addKeyword( "implements" );
		addKeyword( "import" );
		addKeyword( "instanceof" );
		addKeyword( "int" );
		addKeyword( "interface" );
		addKeyword( "long" );
		addKeyword( "native" );
		addKeyword( "new" );
		addKeyword( "package" );
		addKeyword( "private" );
		addKeyword( "protected" );
		addKeyword( "public" );
		addKeyword( "return" );
		addKeyword( "short" );
		addKeyword( "static" );
		addKeyword( "strictfp" );
		addKeyword( "super" );
		addKeyword( "switch" );
		addKeyword( "synchronized" );
		addKeyword( "this" );
		addKeyword( "throw" );
		addKeyword( "throws" );
		addKeyword( "if" );
		addKeyword( "transient" );
		addKeyword( "try" );
		addKeyword( "void" );
		addKeyword( "volatile" );
		addKeyword( "while" );
	}
}
