package com.kirelcodes.RoboticCraft.gui.guiRobots;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kirelcodes.RoboticCraft.configs.ConfigManager;
import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class GUIRobotBasic extends GUI {
	private RobotBase robot;
	private ItemStack itemFollow, itemNoFollow, Destroy, openInventory , fuel;
	private int chestPos, followPos, destroyPos , fuelPos;

	public GUIRobotBasic(RobotBase robot) {
		setSize(27);
		setTitle(ConfigManager.getLang("BasicGUIT"));
		instalizeInventory();
		this.robot = robot;
		Destroy = ItemStackUtils.createItem(Material.BARRIER, ConfigManager.getLang("BasicItem_Destroy"));
		itemFollow = ItemStackUtils.createItem(Material.COMPASS, ConfigManager.getLang("BasicItem_Follow"));
		itemNoFollow = ItemStackUtils.createItem(Material.COMPASS, ConfigManager.getLang("BasicItem_NoFollow"));
		openInventory = ItemStackUtils.createItem(Material.CHEST, ConfigManager.getLang("BasicItem_Inventory"));
		int fuelD = 0;
		ChatColor color = ChatColor.WHITE;
		if(robot.getFuel() >= 75){
			fuelD = 5;
			color = ChatColor.GREEN;
		}
		else if (robot.getFuel() >= 50){
			fuelD = 4;
			color = ChatColor.YELLOW;
		}
		else if(robot.getFuel() >= 25){
			fuelD = 1;
			color = ChatColor.RED;
		}
		else if(robot.getFuel() >= 0){
			fuelD = 14;
			color = ChatColor.DARK_RED;
		}
		fuel = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, fuelD, color + ConfigManager.getLang("BasicItem_Fuel") + robot.getFuel());
		chestPos = 13;
		followPos = 14;
		destroyPos = 3;
		fuelPos = 8;
		fillInventory();
		getGUIAction().add(new GUIAction(fuel) {
			
			@Override
			public void actionNow(GUI gui, Player player) {
				// TODO Auto-generated method stub
				
			}
		});
		getGUIAction().add(new GUIAction(openInventory) {

			@Override
			public void actionNow(GUI gui, Player player) {
				player.closeInventory();
				player.openInventory(((GUIRobotBasic) gui).getRobot().getInventory());
			}
		});
		getGUIAction().add(new GUIAction(itemFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotBasic) gui).follow(player);
			}
		});
		getGUIAction().add(new GUIAction(Destroy) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotBasic) gui).getRobot().guiDestroy(player);
				player.closeInventory();
			}
		});
		getGUIAction().add(new GUIAction(itemNoFollow) {

			@Override
			public void actionNow(GUI gui, Player player) {
				((GUIRobotBasic) gui).noFollow(player);
			}
		});
		getInventory().setItem(chestPos, openInventory);
		getInventory().setItem(destroyPos, Destroy);
		getInventory().setItem(followPos, (robot.isFollowing()) ? itemNoFollow : itemFollow);
		getInventory().setItem(fuelPos, fuel);
	}

	protected void setChestPos(int i) {
		ItemStack item = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, 0, ChatColor.BLACK + ConfigManager.getLang("BasicItem_DontClickItem"));
		getInventory().setItem(chestPos, item);
		this.chestPos = i;
		getInventory().setItem(chestPos, openInventory);
	}

	protected void setRemovePos(int i) {
		ItemStack item = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, 0, ChatColor.BLACK + ConfigManager.getLang("BasicItem_DontClickItem"));
		getInventory().setItem(destroyPos, item);
		this.destroyPos = i;
		getInventory().setItem(destroyPos, Destroy);
	}

	public void setFollowPos(int i) {
		ItemStack item = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, 0, ChatColor.BLACK + ConfigManager.getLang("BasicItem_DontClickItem"));
		getInventory().setItem(followPos, item);
		this.followPos = i;
		getInventory().setItem(followPos, (robot.isFollowing()) ? itemNoFollow : itemFollow);
	}
	public void setFuelPos(int i) {
		ItemStack item = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, 0, ChatColor.BLACK + ConfigManager.getLang("BasicItem_DontClickItem"));
		getInventory().setItem(fuelPos, item);
		this.fuelPos = i;
		getInventory().setItem(fuelPos, fuel);
	}

	protected RobotBase getRobot() {
		return robot;
	}

	public void follow(Entity p) {
		robot.setFollow(p);
		getInventory().setItem(followPos, itemNoFollow);
	}

	public void noFollow(Entity p) {
		robot.cancelFollow();
		getInventory().setItem(followPos, itemFollow);
	}
	public void fillInventory(){
		for (int i = 0; i < 27; i++) {
			ItemStack item = ItemStackUtils.createItem(Material.STAINED_GLASS_PANE, 0,
					ChatColor.BLACK + ConfigManager.getLang("BasicItem_DontClickItem"));
			getGUIAction().add(new GUIAction(item) {

				@Override
				public void actionNow(GUI gui, Player player) {

				}
			});
			getInventory().setItem(i, item);
		}
		getInventory().setItem(chestPos, openInventory);
		getInventory().setItem(destroyPos, Destroy);
		getInventory().setItem(followPos, (robot.isFollowing()) ? itemNoFollow : itemFollow);
		getInventory().setItem(fuelPos, fuel);
	}
}
