package hu.pafr.richrail.parser;

import java.io.FileNotFoundException;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
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

	Builder builder = new LocomotiefBuilder();
	Factory factory = new WagonFactory();
	
	private String message;
	private Object object;

	
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
	//	String NUMBER = ctx.NUMBER().getText();
	//	System.out.println(NUMBER);
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
		String locomotiefId = ctx.ID(1).getText();
		TerminalNode wagonId = ctx.ID().get(0);
		
		Builder builder = new LocomotiefBuilder();
		builder.setNaam(locomotiefId);
		Locomotief locomotief = builder.build();
		
		Factory factory = new WagonFactory();
		Wagon wagon = factory.createWagon(wagonId.toString(), 0, 0);
		
		locomotief.setWagon(wagon);
		
		message = "Wagon "+ wagon.getNaam() + " is toegevoegd aan "+ locomotief.getNaam();
		this.object = locomotief;	
	}

	@Override
	public void enterGetcommand(GetcommandContext ctx) {
		System.out.println("enterGetcommand");

	}

	@Override
	public void exitGetcommand(GetcommandContext ctx) {
		String id = ctx.ID().getText();
		String type = ctx.type().getText();

		switch(type) {
		case "train":
			Builder builder = new LocomotiefBuilder();
			builder.setNaam(id);
			Locomotief locomotief = builder.build();
			this.message = "Er zijn "+locomotief.getStoelen()+" stoelen in deze trein.";
			break;
		case "wagon":
			Factory factory = new WagonFactory();
			Wagon wagon = factory.createWagon(id, 0, 0);
			this.message = "Er zijn "+wagon.getStoelen()+" stoelen in deze wagon.";
			break;
		}
		
	}

	@Override
	public void enterDelcommand(DelcommandContext ctx) {
		System.out.println("enterDelcommand");

	}

	@Override
	public void exitDelcommand(DelcommandContext ctx) {
		String id = ctx.ID().getText();
		String type = ctx.type().getText();
		switch(type) {
		case "train":
			Builder builder = new LocomotiefBuilder();
			builder.setNaam(id);
			Locomotief locomotief = builder.build();
			locomotief.remove();
			this.message = "train "+locomotief.getNaam()+" is succesvol verwijderd.";
			break;
		case "wagon":
			Factory factory = new WagonFactory();
			Wagon wagon = factory.createWagon(id, 0, 0);
			wagon.remove();
			this.message = "wagon "+wagon.getNaam()+" is succesvol verwijderd.";
			break;
		}
	}

	@Override
	public void enterRemcommand(RemcommandContext ctx) {
		System.out.println("enterRemcommand");

	}

	@Override
	public void exitRemcommand(RemcommandContext ctx) throws FileNotFoundException {
		String locomotiefId = ctx.ID(1).getText();
		TerminalNode wagonId = ctx.ID().get(0);

		builder.setNaam(locomotiefId);
		Locomotief locomotief = builder.build();
		
		Wagon wagon = factory.createWagon(wagonId.toString(), 0, 0);
		
		locomotief.verwijderWagon(wagon);
		message = "Wagon "+ wagonId +" is verwijderd uit locomotief "+ locomotiefId;
		object = locomotief;
		
	//  if(locomotief.removeWagon(wagon)) {
	//		message = "Wagon "+ wagonId +" is verwijderd uit locomotief "+ locomotiefId;
	//	} else {
	//		message = "Wagon "+ wagonId +" is niet gevonden in locomotief "+ locomotiefId;
	//	}
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
