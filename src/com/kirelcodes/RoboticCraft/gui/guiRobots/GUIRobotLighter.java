package com.kirelcodes.RoboticCraft.gui.guiRobots;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotLighter;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

import net.md_5.bungee.api.ChatColor;

public class GUIRobotLighter extends GUI {

	private RobotLighter robot;
	private ItemStack /*itemFollow, itemNoFollow,*/ itemLight, itemNoLight, openInventory;

	public RobotLighter getRobot() {
		return robot;
	}

	public GUIRobotLighter(RobotLighter robot) {
		setSize(27);
		setTitle("&cMiner Robot GUI");
		instalizeInventory();
		this.robot = robot;
		//itemFollow = ItemStackUtils.createItem(Material.COMPASS, "&aFollow");
		//itemNoFollow = ItemStackUtils.createItem(Material.COMPASS, "&cStop Follow");
		itemLight = ItemStackUtils.createItem(Material.TORCH, "&aLight");
		itemNoLight = ItemStackUtils.createItem(Material.TORCH, "&cStop Light");
		openInventory = ItemStackUtils.createItem(Material.CHEST, "&cOpen Robot's Inventory");
		gettGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotLighter) gui).getRobot().getInventory());
			}
		});
		gettGUIAction().add(new GUIAction(itemLight) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLighter) gui).Light(player);
			}
		});
		gettGUIAction().add(new GUIAction(itemNoLight) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotLighter) gui).noLight();
			}
		});
		for (int i = 0; i < 27; i++) {
			ItemStack item = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, new Random().nextInt(16),
					ChatColor.BLACK + "DONT CLICK ME");
			getInventory().setItem(i, item);
			gettGUIAction().add(new GUIAction(item) {

				@Override
				public void actionNow(GUI gui, Player player) {

				}
			});
		}
		getInventory().setItem(12, openInventory);
		getInventory().setItem(14, (robot.isLightning()) ? itemNoLight : itemLight);
	}

	/*public void follow(Entity p) {
		robot.setFollow(p);
		if (robot.isLightning())
			noLight();
		getInventory().setItem(12, itemNoFollow);
	}

	public void noFollow(Entity p) {
		robot.cancelFollow();
		getInventory().setItem(12, itemFollow);
	}*/

	public void Light(Player p) {
		robot.setLightning(true);
		robot.setFollow(p);
		getInventory().setItem(14, itemNoLight);
	}

	public void noLight() {
		robot.setLightning(false);
		robot.cancelFollow();
		getInventory().setItem(14, itemLight);
	}

}