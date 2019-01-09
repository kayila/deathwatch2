package com.kitsuneindustries.deathwatch2.command;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.google.common.annotations.VisibleForTesting;
import com.kitsuneindustries.deathwatch2.Deathwatch2;
import com.kitsuneindustries.deathwatch2.data.PlayerDeath;
import com.kitsuneindustries.deathwatch2.store.DeathRegistry;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class DWCommand extends CommandBase {
	
	private static final List<String> aliases = Arrays.asList(Deathwatch2.MODID, "dw", "DW");
	private static final DeathRegistry registry = new DeathRegistry();

	@Override
	@Nonnull
	public String getName() {
		return "dw";
	}
	
	@Override
	@Nonnull
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Make this run server side only? Do they ever run on the client?
		if (args.length == 0) {
			// TODO print to the user some help
			return;
		}
		if ("last".equalsIgnoreCase(args[0])) {
			last(server, sender, Arrays.copyOfRange(args, 1, args.length));
			return;
		}
	}
	
	private void last(MinecraftServer server, ICommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender instanceof EntityPlayer) {
				Optional<PlayerDeath> death = registry.findLastDeathByName(sender.getName());
				if (death.isPresent()) {
					ITextComponent message = formatDeathForGameDisplay(death.get());
					sender.sendMessage(message);
				} else {
					sender.sendMessage(new TextComponentTranslation("command.deathwatch2.notfound"));
				}
			}
		}
	}
	
	@VisibleForTesting
	ITextComponent formatDeathForGameDisplay(PlayerDeath death) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String who = death.getPlayerName();
		String date = dateFormat.format(death.getTimestamp());
		String time = timeFormat.format(death.getTimestamp());
		String dim = Integer.toString(death.getDimension());
		String where = String.format("[%.0f, %.0f, %.0f]", death.getX(), death.getY(), death.getZ());
		
		ITextComponent optional;
		if (death.getType() != null) {
			String deathType = death.getType().replace(".player", "");
			optional = new TextComponentTranslation("command.deathwatch2.from", new TextComponentTranslation(deathType));
			if (death.getKiller() != null) {
				optional.appendText(" ");
				optional.appendSibling(new TextComponentTranslation("command.deathwatch2.by", new TextComponentTranslation(death.getKiller())));
			}
			optional.appendText(" ");
		} else {
			optional = new TextComponentString("");
		}
		
		return new TextComponentTranslation("command.deathwatch2.display.death.last", date, time, who, dim, where, optional);
	}

}
