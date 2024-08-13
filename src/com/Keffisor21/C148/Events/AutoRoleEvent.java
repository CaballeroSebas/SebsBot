package com.Keffisor21.C148.Events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AutoRoleEvent extends ListenerAdapter {
	public static List<Member> cooldownReaction = new ArrayList();
	private final String message_id;
	private final List<String> list;
	
	public AutoRoleEvent(String message_id, List<String> list) {
		this.message_id = message_id;
		this.list = list;
	}
	
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
		Member m = e.getMember();
		String emote_id = e.getReactionEmote().getEmote().getId();
		String message_id = e.getMessageId();
		Guild guild = e.getGuild();
		action(m, emote_id, message_id, guild, true);
	}
	
	@Override
	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e) {
		Member m = e.getMember();
		String emote_id = e.getReactionEmote().getEmote().getId();
		String message_id = e.getMessageId();
		Guild guild = e.getGuild();
		action(m, emote_id, message_id, guild, false);
	}
	
	public void action(Member m, String emote_id, String message_id, Guild guild, boolean status) {
		if(this.message_id.equals(message_id)) {
			List<String> matchs = list.stream().filter(sT -> sT.contains(emote_id)).collect(Collectors.toList());
			if(matchs.size() != 0) {
				String[] split = matchs.get(0).split(":");
				String emote_id_2 = split[0];
				String role_id = split[1];
				if(emote_id_2.equals(emote_id)) {
					Role role = guild.getRoleById(role_id);
					try {
						if(status) {
							guild.addRoleToMember(m, role).queue();
						} else {
							guild.removeRoleFromMember(m, role).queue();
						}
						cooldownReaction.add(m);
						startCooldown(guild.getDefaultChannel(), m);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void startCooldown(TextChannel channel, Member m) {
		  channel.getManager().setName(channel.getName()).queueAfter(3, TimeUnit.SECONDS, aa -> {
			  cooldownReaction.remove(m);
		  });
	  }
}
