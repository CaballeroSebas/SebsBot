package com.Keffisor21.C148.Commands;

import com.Keffisor21.C148.JDAExpansion.createCommand;
import com.Keffisor21.C148.Utils.Embed;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EmbedCommand extends createCommand {

	public EmbedCommand() {
		super("!", "embed");
	}

	@Override
	protected void isExecuted(String[] args, MessageReceivedEvent e) {
		String contentRaw = e.getMessage().getContentRaw().replaceFirst("!", "").replaceFirst("embed ", "");
		Member member = e.getMember();
		if(!member.hasPermission(Permission.ADMINISTRATOR)) {
			e.getMessage().reply("Para ejecutar este comando necesitas el permiso de administrador.").queue();
			return;
		}
		if(args.length == 1) {
			e.getMessage().reply("Uso correcto: !embed título del embed | Descripción del embed (Usa el '\\n' para saltar de línea)").queue();
		} else {
			String[] split = contentRaw.split(" \\| ");
			String title = split[0];
			String description = split[1];
			Embed embed = new Embed();
			embed.setTitle(title);
			embed.setDescription(description.replace("\\n", "\n"));
			e.getChannel().sendMessage(embed.build()).queue();
		}
		
	}
	
}