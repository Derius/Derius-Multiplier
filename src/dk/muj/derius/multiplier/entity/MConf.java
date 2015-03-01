package dk.muj.derius.multiplier.entity;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventPriority;

import com.massivecraft.massivecore.store.Entity;

public class MConf extends Entity<MConf>
{
	// -------------------------------------------- //
	// META
	// -------------------------------------------- //
	
	protected static transient MConf i;
	public static MConf get() { return i; }
	
	// -------------------------------------------- //
	// CONF
	// -------------------------------------------- //
	
	public Map<String, Double> skillBaseMultipliers = new HashMap<>();
	
	public EventPriority priority = EventPriority.LOW;

	public double baseMultiplier = 1.0D; 
	
}
