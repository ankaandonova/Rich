package hu.pafr.richrail.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import hu.pafr.richrail.database.Database;
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.WagonFactory;
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

	private String message;
	private Object object;
	private Database database = Database.getDatabase();
	
	
	@Override
	public void visitTerminal(TerminalNode node) {
//		System.out.println("visitTerminal");
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
//		System.out.println("visitErrorNode");
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
//		System.out.println("enterEveryRule");
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
//		System.out.println("exitEveryRule");

	}

	@Override
	public void enterCommand(CommandContext ctx) {
//		System.out.println("enterCommand");
	}

	@Override
	public void exitCommand(CommandContext ctx) {
//		System.out.println("exitCommand");
	}

	@Override
	public void enterNewcommand(NewcommandContext ctx) {
//		System.out.println("enterNewcommand");
	}

	@Override
	public void exitNewcommand(NewcommandContext ctx) {
//		System.out.println("exitNewcommand");
	}

	@Override
	public void enterNewtraincommand(NewtraincommandContext ctx) {
//		System.out.println("enterNewtraincommand" + ctx);
	}

	@Override
	public void exitNewtraincommand(NewtraincommandContext ctx) {
		String id = ctx.ID().getText();

		Builder builder = new LocomotiefBuilder();
		builder.setNaam(id);
		Locomotief l1 = builder.build();
		this.object = l1;
		this.message = "De trein " + id + " is aangemaakt";
	}

	@Override
	public void enterNewwagoncommand(NewwagoncommandContext ctx) {
		System.out.println("enterNewwagoncommand");
	}

	@Override
	public void exitNewwagoncommand(NewwagoncommandContext ctx) {
		String id = ctx.ID().getText();
		int stoelen = 20;
		int bedden = 0;

		Factory factory = new WagonFactory();
		this.object = factory.createWagon(id, stoelen, bedden);
		this.message = "De wagon " + id + " is aangemaakt met " + stoelen + " stoelen";
	}

	@Override
	public void enterAddcommand(AddcommandContext ctx) {
		System.out.println("enterAddcommand");
	}

	@Override
	public void exitAddcommand(AddcommandContext ctx) {

	}

	@Override
	public void enterGetcommand(GetcommandContext ctx) {
		System.out.println("enterGetcommand");

	}

	@Override
	public void exitGetcommand(GetcommandContext ctx) {
		System.out.println("exitGetcommand");

	}

	@Override
	public void enterDelcommand(DelcommandContext ctx) {
		System.out.println("enterDelcommand");

	}

	@Override
	public void exitDelcommand(DelcommandContext ctx) {
		System.out.println("exitDelcommand");

	}

	@Override
	public void enterRemcommand(RemcommandContext ctx) {
		System.out.println("enterRemcommand");

	}

	@Override
	public void exitRemcommand(RemcommandContext ctx) {
		System.out.println("exitRemcommand");

	}

	@Override
	public void enterType(TypeContext ctx) {
		System.out.println("enterType");

	}

	@Override
	public void exitType(TypeContext ctx) {
		System.out.println("exitType");

	}

	public Object getObject() {
		return this.object;
	}

	public String getMessage() {
		return this.message;
	}
}
