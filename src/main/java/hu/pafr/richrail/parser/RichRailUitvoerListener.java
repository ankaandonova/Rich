package hu.pafr.richrail.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import parser.RichRailListener;
import parser.RichRailParser.AddcommandContext;
import parser.RichRailParser.CommandContext;
import parser.RichRailParser.DelcommandContext;
import parser.RichRailParser.GetcommandContext;
import parser.RichRailParser.NewcommandContext;
import parser.RichRailParser.NewtraincommandContext;
import parser.RichRailParser.NewwagoncommandContext;
import parser.RichRailParser.RemcommandContext;
import parser.RichRailParser.TypeContext;


public class RichRailUitvoerListener implements RichRailListener {

	@Override
	public void visitTerminal(TerminalNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterCommand(CommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitCommand(CommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterNewcommand(NewcommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitNewcommand(NewcommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterNewtraincommand(NewtraincommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitNewtraincommand(NewtraincommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterNewwagoncommand(NewwagoncommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitNewwagoncommand(NewwagoncommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterAddcommand(AddcommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAddcommand(AddcommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterGetcommand(GetcommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitGetcommand(GetcommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterDelcommand(DelcommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitDelcommand(DelcommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterRemcommand(RemcommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitRemcommand(RemcommandContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterType(TypeContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitType(TypeContext ctx) {
		// TODO Auto-generated method stub
		
	}

}
