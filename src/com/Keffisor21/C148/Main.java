package com.Keffisor21.C148;

import java.util.List;
import java.io.File;
import java.util.HashMap;
import java.util.Timer;

import javax.security.auth.login.LoginException;

import com.Keffisor21.C148.Commands.AutoRoleCmd;
import com.Keffisor21.C148.Commands.EmbedCommand;
import com.Keffisor21.C148.Events.AutoMessage;
import com.Keffisor21.C148.Events.AutoRoleEvent;
import com.Keffisor21.C148.JDAExpansion.FileConfiguration;
import com.Keffisor21.C148.JDAExpansion.TokenConfiguration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {
	public static FileConfiguration config;
	private static JDA jda;
	
	public static void main(String[] args) throws LoginException {
		String token = TokenConfiguration.getTokenFileContent();
		if(token.isEmpty()) {
			System.out.println("\n[ERROR] The token.txt is empty, please write the token of the bot");
			return;
		}
		config = new FileConfiguration(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile().replace("%20", " ")), Main.getAbsolutePath(), "config.yml", new Main());
		JDA jda =  JDABuilder.createDefault(token).setMemberCachePolicy(MemberCachePolicy.ALL).enableIntents(GatewayIntent.GUILD_MEMBERS).build();
		Main.jda = jda;
		jda.addEventListener(new EmbedCommand());
		//start automessage task
		new Timer().schedule(new AutoMessage(config.getString("AUTOMESSAGE_TITLE"), config.getString("AUTOMESSAGE_MESSAGE")), 1000*7200);
		jda.addEventListener(new AutoRoleCmd());
		registerAutoRole();
	}
	
	public static void registerAutoRole() {
		config.getElements().forEach((k, v) -> {
			if(k.contains("AutoRole."))
				jda.addEventListener(new AutoRoleEvent(k.replace("AutoRole.", ""), (List<String>)((HashMap)v).get("Options")));
		});
	}
	
	public static JDA getJDA() {
		return jda;
	}
	
	public static String getAbsolutePath() {
		   return new File("").getAbsolutePath();
	}
}
