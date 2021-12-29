package me.NorthAlaska.BloodMoonEventV2.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.entity.IronGolem;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import me.NorthAlaska.BloodMoonEventV2.Main;

public class Data implements Serializable
{
	private static transient final long serialVersionUID = -1681012206529286330L;
	
	private Main plugin;
	private ArrayList<IronGolem> array;
	
	public Data(ArrayList<IronGolem> array, Main plugin)
	{
		this.plugin = plugin;
		this.array = array;
	}
    
	//Saves data of the mailboxes the parameters are the filepath and the arraylist from the PostalService object
	public boolean saveData(String filePath) 
	{
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
	
	public ArrayList<IronGolem> getArray()
	{
		return array;
	}
	
	public void setArray(ArrayList<IronGolem> array)
	{
		this.array = array;
	}
	
	
	//Loads data from the file given its filePath
	public static Data loadData(String filePath) 
	{
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            Data data = (Data) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
	}

}

