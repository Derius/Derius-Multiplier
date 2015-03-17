package dk.muj.derius.multiplier;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.massivecraft.massivecore.MassivePlugin;

import dk.muj.derius.api.DeriusAPI;
import dk.muj.derius.api.events.SkillRegisteredEvent;
import dk.muj.derius.api.events.player.PlayerExpAddEvent;
import dk.muj.derius.api.skill.Skill;
import dk.muj.derius.multiplier.entity.MConf;
import dk.muj.derius.multiplier.entity.MConfColl;

public final class DeriusMultiplier extends MassivePlugin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static DeriusMultiplier i;
	public static DeriusMultiplier get() { return i; }
	public DeriusMultiplier() { i = this; }
	
	// -------------------------------------------- //
	// OVERRIDE: PLUGIN
	// -------------------------------------------- //
	
	@Override
	public void onEnable()
	{
		if ( ! this.preEnable()) return;
		
		MConfColl.get().init();
		
		for (Skill skill : DeriusAPI.getAllSkills())
		{
			if (MConf.get().skillBaseMultipliers.containsKey(skill.getId())) continue;
			MConf.get().skillBaseMultipliers.put(skill.getId(), 1D);
		}
		
		this.postEnable();
	}
	
	// -------------------------------------------- //
	// EVENT LISTENER
	// -------------------------------------------- //
	
	@EventHandler
	public void initSkill(SkillRegisteredEvent event)
	{
		Skill skill = event.getSkill();
		if (MConf.get().skillBaseMultipliers.containsKey(skill.getId())) return;
		MConf.get().skillBaseMultipliers.put(skill.getId(), 1D);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void muliplier(PlayerExpAddEvent event)
	{
		CommandSender sender = event.getDPlayer().getSender();
		if (sender == null) return;
		Skill skill = event.getSkill();
		
		double exp = event.getExpAmount();
		
		exp *= MConf.get().baseMultiplier;
		exp *= MConf.get().skillBaseMultipliers.get(skill.getId());
		
		for (int i = 100; i >= 0; i++)
		{
			if ( ! sender.hasPermission("derius.basemultiplier." + String.valueOf(i))) continue;
			exp *= i / 10.0;
			break;
		}
		
		for (int i = 100; i >= 0; i++)
		{
			if ( ! sender.hasPermission("derius.skillmultiplier." + skill.getId() + "." + String.valueOf(i))) continue;
			exp *= i / 10.0;
			break;
		}
		
		event.setExpAmount(exp);
	}
	
}
