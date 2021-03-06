package com.kirelcodes.RoboticCraft;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.kirelcodes.RoboticCraft.gui.GUI;
import com.kirelcodes.RoboticCraft.robot.RobotBase;
import com.kirelcodes.RoboticCraft.utils.ItemStackUtils;

public class RobotItem {
	private static ArrayList<RobotItem> robotItems = new ArrayList<>();
	private Class<? extends RobotBase> robotClass;
	private ItemStack item;
	private Recipe recipe;
	private Class<? extends GUI> gui;
	private boolean hasPermission;
	private String permission;

	public RobotItem(Class<? extends RobotBase> robotClass, ItemStack item,
			Recipe recipe , Class<? extends GUI> gui) {
		this.robotClass = robotClass;
		this.item = item;
		this.recipe = recipe;
		Bukkit.addRecipe(getRecipe());
		robotItems.add(this);
		this.gui = gui;
	}
	public Class<? extends RobotBase> getRobotClass(){
		return robotClass;
	}
	public ItemStack getItem(){
		return item;
	}
	public Recipe getRecipe(){
		return recipe;
	}
	public Class<? extends GUI> getGUI(){
		return gui;
	}
	public boolean hasPermission(){
		return hasPermission;
	}
	public void setPermission(String permission){
		hasPermission = true;
		this.permission = permission;
	}
	public String getPermission(){
		return this.permission;
	}
	public static GUI getGUI(RobotBase robot) throws Exception{
		for(RobotItem robotItem : robotItems){
			if(robotItem.getRobotClass().getName().equalsIgnoreCase(robot.getClass().getName()))
				return robotItem.getGUI().getConstructor(robotItem.getRobotClass()).newInstance(robot);
		}
		return null;
	}
	public static ItemStack getItem(Class<? extends RobotBase> clazz){
		for(RobotItem robotItem : robotItems){
			if(robotItem.getRobotClass().getName().equalsIgnoreCase(clazz.getName()))
				return robotItem.getItem();
		}
		return null;
	}
	public static RobotBase getRobot(ItemStack item , Player p) throws Exception{
		for(RobotItem robot : robotItems){
			if(robot.getItem().isSimilar(item))
				return robot.getRobotClass().getConstructor(Location.class , UUID.class).newInstance(p.getLocation() , p.getUniqueId());
		}
		return null;
	}
	public static boolean containsItem(ItemStack item){
		for(RobotItem robot : robotItems){
			if(ItemStackUtils.isSimmilarNoNBT(item, robot.getItem()))
				return true;
		}
		return false;
	}
	public static boolean hasPermission(ItemStack item){
		for(RobotItem robot : robotItems){
			if(ItemStackUtils.isSimmilarNoNBT(item, robot.getItem()))
				return robot.hasPermission();
		}
		return false;
	}
	public static String getPermission(ItemStack item){
		for(RobotItem robot : robotItems){
			if(ItemStackUtils.isSimmilarNoNBT(item, robot.getItem()))
				return robot.getPermission();
		}
		return "";

	}
	public static List<RobotItem> getRobotItemList(){
		return robotItems;
	}

}
