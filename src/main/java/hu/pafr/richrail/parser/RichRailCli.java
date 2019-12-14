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
    public static RichRailUitvoerListener voerCommandUit(String command) {
	    CharStream stream = CharStreams.fromString(command);
	    Lexer lexer = new RichRailLexer(stream);

	    CommonTokenStream tokens = new CommonTokenStream(lexer);
        System.out.println("2");
        RichRailParser parser = new RichRailParser(tokens);
        System.out.println("3");
        ParseTree tree = parser.command();
        
     	System.out.println("1");
        RichRailUitvoerListener richrail = new RichRailUitvoerListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(richrail, tree);
        return richrail;
	}
}
