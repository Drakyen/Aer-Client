package net.aer.render.render2D;

import com.darkmagician6.eventapi.EventManager;
import net.aer.Aer;
import net.aer.command.CommandManager;
import net.aer.events.EventChatRecieved;
import net.aer.events.EventChatSent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;


/**
 * Handles client side chat
 */
public class ChatUtils {

	protected static Minecraft minecraft = Aer.minecraft;

	/**
	 * Sends a raw message to the chat, with optional colouring, info to apprehend onto "Aer", and whether to delete the previous chat lines from this sender
	 */
	public static void sendChatMessage(String string, String colour, String info, boolean delete) {
        TextComponentString test = new TextComponentString("\u00A77[\u00A73Aer \u00A77<" + info + "\u00A77]" + ": " + colour + string);
        ITextComponent text = test;
        if (delete) {
            clearChat(false, info);
        }
        minecraft.player.addChatMessage(text, 0);


    }

	/**
	 * Sends a raw message to the chat, with optional colouring, without the "Aer" watermark. Uses a invisible unicode character to identify the chat line.
	 */
	public static void sendChatMessage(String string, String colour, boolean delete) {
		TextComponentString test = new TextComponentString("�" + colour + string);
		ITextComponent text = test;
		if (delete) {
			clearChat(false, "�");
		}
		minecraft.player.addChatMessage(text, 0);


	}

	/**
	 * Clears either all chat, or just chat sent by "Aer" + senderToClear/senderToClear
	 */
	public static void clearChat(boolean clearPlayerMsgs, String senderToClear) {
		if (clearPlayerMsgs) {
			minecraft.ingameGUI.getChatGUI().clearChatMessages(true);
		} else {
			ArrayList<ChatLine> linesToDelete = new ArrayList<ChatLine>();
			List<ChatLine> lines = minecraft.ingameGUI.getChatGUI().getChatLines();
			for (ChatLine line : lines) {
				if (line.getChatComponent().getUnformattedText().startsWith("�3Aer" + senderToClear)) {
					linesToDelete.add(line);
				}
				if (line.getChatComponent().getUnformattedText().startsWith(senderToClear)) {
					linesToDelete.add(line);
				}
			}
			for (ChatLine lineToDelete : linesToDelete) {
				try {
					minecraft.ingameGUI.getChatGUI().deleteChatLine(lineToDelete.getChatLineID());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}


	/**
	 * Called whenever the player sends a chat message
	 */
	public static boolean onSendChatMessage(String s) {
		if (s.startsWith(Aer.settings.getProperty("CmdPrefix", "."))) {
			CommandManager.callCommand(s.substring(Aer.settings.getProperty("CmdPrefix", ".").length()));
			return false;
		}
		return !((EventChatSent) EventManager.call(new EventChatSent(s))).isCancelled();
	}


	/**
	 * Called whenever a chat message is recieved from the server
	 */
	public static boolean onRecieveChatMessage(SPacketChat packet) {
		return ((EventChatRecieved) EventManager.call(new EventChatRecieved(packet.getChatComponent().getFormattedText()))).isCancelled();
	}

}
	
