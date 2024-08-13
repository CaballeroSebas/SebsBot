package com.Keffisor21.C148.JDAExpansion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class createCommand extends ListenerAdapter {
    private String command;
    private String contentRaw = "";
    private List<String> aliases = new ArrayList<String>();
    private MessageReceivedEvent event = null;
    private String prefix;
    
     /*
     Code from my proyect JDAExpansion in GitHub (https://github.com/Keffisor/JDAExpansion/blob/master/JDAExpansion/src/com/Keffisor21/JDAExpansion/EventsHandler/createCommand.java)
     */
    
    public createCommand(String prefix, String cmd, String... args) {
    	this.prefix = prefix;
    	this.command = prefix+cmd;
    	if(args.length != 0) {
    	this.aliases = Arrays.asList(args);
    	}
    }
   
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
    	if(e.getAuthor().isBot()) return;
      if(isCommand(e.getMessage().getContentRaw(), command) || getAliases(e.getMessage().getContentRaw(), false)) {
    	  
    	  contentRaw = e.getMessage().getContentRaw();
    	  event = e;
     	  isExecuted(getArgs(), e);
      }
    }

   protected abstract void isExecuted(String[] args, MessageReceivedEvent event);   
   
    private String[] getArgs() {
    	if(contentRaw.replace("  ", " ").split(" ").length != 0) {
    		return contentRaw.replace("  ", " ").split(" ");
    	}
    	return null;
    }
    
    private boolean getAliases(String contentRaw, boolean console) {
    if(this.aliases.isEmpty()) {
    	return false;
    }
    if(contentRaw.isEmpty()) {
    	return false;
    }
    	return !this.aliases.stream().filter(sT -> {if(!console) {sT = prefix+sT;} return isCommand(contentRaw, sT);}).collect(Collectors.toList()).isEmpty();
    }
    private boolean isCommand(String message, String command) {
    	if(!message.contains(" ")) return message.equalsIgnoreCase(command);
    	else {
			String cmd = message.split(" ")[0];
			return cmd.equalsIgnoreCase(command);
		}
    }
    private String getCommand(String content) {
    	if(!content.contains(" ")) return content.replaceFirst("\\"+prefix, "");
    	return content.split(" ")[0].replaceFirst("\\"+prefix, "");
    }
}