package hu.pafr.richrail.parser;

import org.antlr.v4.runtime.CharStream; 
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import parser.RichRailLexer;
import parser.RichRailParser;

public class RichRailCli {
    public static void main(String[] args) {
		String input = "new train 1";
	    CharStream stream = CharStreams.fromString(input);
	    
	    Lexer lexer = new RichRailLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RichRailParser parser = new RichRailParser(tokens);
        ParseTree tree = parser.command();

        RichRailUitvoerListener richrail = new RichRailUitvoerListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(richrail, tree);
	}
}
