package com.Keffisor21.C148.Utils;

import java.awt.Color;

import com.Keffisor21.C148.Main;

import net.dv8tion.jda.api.EmbedBuilder;

public class Embed extends EmbedBuilder {
	public Embed() {
		setColor(Color.HSBtoRGB((float)(System.currentTimeMillis() % 1000L) / 1000.0F, 0.9F, 0.9F));
		setFooter(Main.config.getString("EMBED_NAME_FOOTER"), Main.config.getString("EMBED_LINK_FOOTER"));
	}
}
