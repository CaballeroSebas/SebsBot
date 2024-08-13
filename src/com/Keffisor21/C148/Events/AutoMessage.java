package com.Keffisor21.C148.Events;

import java.util.TimerTask;

import com.Keffisor21.C148.Main;
import com.Keffisor21.C148.Utils.Embed;

import net.dv8tion.jda.api.entities.TextChannel;

public class AutoMessage extends TimerTask {
	private String title;
	private String message;
	
	public AutoMessage(String title, String message) {
		this.title = title;
		this.message = message;
	}

	@Override
	public void run() {
		Embed embed = new Embed();
		embed.setTitle(title);
		embed.setDescription(message);
		TextChannel channel = Main.getJDA().getTextChannelById(Main.config.getString("AUTOMESSAGE_CHANNEL_ID"));
		channel.sendMessage(embed.build()).queue();
	}
}