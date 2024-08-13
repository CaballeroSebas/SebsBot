package com.Keffisor21.C148.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Keffisor21.C148.Main;
import com.Keffisor21.C148.Events.AutoRoleEvent;
import com.Keffisor21.C148.JDAExpansion.createCommand;
import com.Keffisor21.C148.Utils.Embed;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AutoRoleCmd extends createCommand {

	public AutoRoleCmd() {
		super("!", "autorole", "autorol");
	}

	@Override
	protected void isExecuted(String[] args, MessageReceivedEvent e) {
		String contentRaw = e.getMessage().getContentRaw().replaceAll(args[0], "");
		Member member = e.getMember();
		if(!member.hasPermission(Permission.ADMINISTRATOR)) {
			e.getMessage().reply("Para ejecutar este comando necesitas el permiso de administrador.").queue();
			return;
		}
		if(args.length == 1) {
			sendHelp(e);
			return;
		} else if(args.length == 2) {
			contentRaw = contentRaw.replace(args[1], "");
			if(args[1].equalsIgnoreCase("crear")) {
				 sendHelp(e);
			} else if(args[1].equalsIgnoreCase("añadiropcion")) {
				e.getMessage().reply("!autorole añadiropcion ID_DEL_MENSAJE ID_DEL_EMOJI ID_DEL_ROLE").queue();
			}
			return;
		}
		if(args.length == 3 || args.length == 4) {
			if(args[1].equalsIgnoreCase("quitaropcion")) {
				String id_mensaje = args[2];
				Main.config.getElements().remove("AutoRole."+id_mensaje);
				Main.config.saveConfig();
				Main.config.reloadConfig();
				e.getMessage().reply("Se ha quitado el mensaje en la config").queue();
				update();
				return;
			}
			sendHelp(e);
			return;
		}
		if(args.length == 5) {
			if(args[1].equalsIgnoreCase("añadiropcion")) {
				String id_mensaje = args[2];
				String id_emoji = args[3];
				String id_role = args[4];
				e.getChannel().retrieveMessageById(id_mensaje).queue(m -> {
					List<String> lista = (List<String>)((HashMap)Main.config.getElements().get("AutoRole."+id_mensaje)).get("Options");
					m.addReaction(e.getGuild().getEmoteById(id_emoji)).queue();
					lista.add(id_emoji+":"+id_role);
					Main.config.getElements().put("AutoRole."+id_mensaje, new HashMap(){{
						put("Options", lista);
					}});
					Main.config.saveConfig();
					Main.config.reloadConfig();
					e.getMessage().reply("Se ha añadido esa configuración al mensaje").queue();
					update();
				});
				return;
			}
		}
		contentRaw = contentRaw.replaceFirst(args[1], "");
		Embed embed = new Embed();
		String[] split = contentRaw.split(" \\| ");
		String title = split[0];
		String description = split[1];
		embed.setTitle(title);
		embed.setDescription(description.replace("\\n", "\n"));
		e.getChannel().sendMessage(embed.build()).queue(m -> {
			String id = m.getId();
			Main.config.set("AutoRole."+id, new HashMap<String, List<String>>(){{
				put("Options", new ArrayList());
			}});
			e.getMessage().reply("Se ha añadido un nuevo autorole correctamente").queue();
			Main.config.saveConfig();
			Main.config.reloadConfig();
		});
		update();
	}
	public void sendHelp(MessageReceivedEvent e) {
		Embed embed = new Embed();
		embed.setTitle("**Comandos:**");
		embed.setDescription("`1) !autorole crear título | mensaje \n2) !autorole añadiropcion ID_DEL_MENSAJE ID_DEL_EMOJI ID_DEL_ROLE \n3) !autorole quitaropcion ID_DEL_MENSAJE`");
		e.getMessage().reply(embed.build()).queue();
	}
	public void update() {
		Main.getJDA().getEventManager().getRegisteredListeners().forEach(cl -> Main.getJDA().removeEventListener(cl));
		Main.registerAutoRole();
	}
}
