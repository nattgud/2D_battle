package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import Util.Random;

public enum Texture {
	OBSTACLE_LOG("medievalEnvironment_log"),
	OBSTACLE_ROCK_01("medievalEnvironment_rock_01"),
	OBSTACLE_ROCK_02("medievalEnvironment_rock_02"),
	OBSTACLE_ROCK_03("medievalEnvironment_rock_03"),
	OBSTACLE_ROCK_04("medievalEnvironment_rock_04"),
	OBSTACLE_STUBBLE("medievalEnvironment_stubble"),
	OBSTACLE_TREE_01("medievalEnvironment_tree_01"),
	OBSTACLE_TREE_02("medievalEnvironment_tree_02"),
	OBSTACLE_TREE_03("medievalEnvironment_tree_03"),
	OBSTACLE_TREE_04("medievalEnvironment_tree_04"),
	GRASS_01("medievalTile_grass_01"),
	GRASS_02("medievalTile_grass_02"),
	SAND_01("medievalTile_sand_01"),
	SAND_02("medievalTile_sand_02"),
	SNOW_01("medievalTile_snow_01"),
	SNOW_02("medievalTile_snow_02"),
	WATER_01("medievalTile_water_01"),
	WATER_02("medievalTile_water_02"),
	UNIT_BEAST_BASILISK("beast_basilisk"),
	UNIT_BEAST_CENTAUR("beast_centaur"),
	UNIT_BEAST_HARPY("beast_harpy"),
	UNIT_BEAST_LIZARD("beast_lizard"),
	UNIT_BEAST_RAVEN("beast_raven"),
	UNIT_BEAST_SCORPION("beast_scorpion"),
	UNIT_BEAST_SNAKE("beast_snake"),
	UNIT_BEAST_TROLL("beast_troll"),
	UNIT_BEAST_WOLF("beast_wolf"),
	UNIT_DIVINE_ANGEL("divine_angel"),
	UNIT_DIVINE_CENTAUR_PALADIN("divine_centaur_paladin"),
	UNIT_DIVINE_DAEVA("divine_daeva"),
	UNIT_DIVINE_ERESHKIGAL("divine_ereshkigal"),
	UNIT_DIVINE_HOLY_DRAGON("divine_holy_dragon"),
	UNIT_DIVINE_PALADIN("divine_paladin"),
	UNIT_DIVINE_PRIEST("divine_priest"),
	UNIT_DIVINE_TITAN("divine_titan"),
	UNIT_DRAGON_DRACONIC("dragon_draconic"),
	UNIT_DRAGON_DRAGON("dragon_dragon"),
	UNIT_DRAGON_DRAKE("dragon_drake"),
	UNIT_DRAGON_FIRE_DRAGON("dragon_fire_dragon"),
	UNIT_DRAGON_HYDRA("dragon_hydra"),
	UNIT_DRAGON_ICE_DRAGON("dragon_ice_dragon"),
	UNIT_DRAGON_SHADOW_DRAGON("dragon_shadow_dragon"),
	UNIT_DRAGON_WYVERN("dragon_wyvern"),
	UNIT_MORTAL_ARCHER("mortal_archer"),
	UNIT_MORTAL_DEATH_KNIGHT("mortal_death_knight"),
	UNIT_MORTAL_GIANT("mortal_giant"),
	UNIT_MORTAL_GRIFFON("mortal_griffon"),
	UNIT_MORTAL_HIPPOGRIFF("mortal_hippogriff"),
	UNIT_MORTAL_HUMAN("mortal_human"),
	UNIT_MORTAL_JUGGERNAUT("mortal_juggernaut"),
	UNIT_MORTAL_SLAVE("mortal_slave"),
	UNIT_MAGIC_AIR_ELEMENTAL("magic_air_elemental"),
	UNIT_MAGIC_EYE("magic_eye"),
	UNIT_MAGIC_FIRE_ELEMENTAL("magic_fire_elemental"),
	UNIT_MAGIC_FROST_GIANT("magic_frost_giant"),
	UNIT_MAGIC_JELLY("magic_jelly"),
	UNIT_MAGIC_PHOENIX("magic_phoenix"),
	UNIT_MAGIC_SHADOW_WEAVER("magic_shadow_weaver"),
	UNIT_MAGIC_SHAPESHIFTER("magic_shapeshifter"),
	UNIT_MAGIC_TREANT("magic_treant"),
	UNIT_UNHOLY_BALROG("unholy_balrog"),
	UNIT_UNHOLY_BONE_DRAGON("unholy_bone_dragon"),
	UNIT_UNHOLY_EFREET("unholy_efreet"),
	UNIT_UNHOLY_GHOST("unholy_ghost"),
	UNIT_UNHOLY_HELLWING("unholy_hellwing"),
	UNIT_UNHOLY_IMP("unholy_imp"),
	UNIT_UNHOLY_LICH("unholy_lich"),
	UNIT_UNHOLY_REAPER("unholy_reaper"),
	UNIT_UNHOLY_SKELETAL_WARRIOR("unholy_skeletal_warrior"),
	ICON_RANGED("ranged"),
	ICON_FLIGHT("flight"),
	GRAVE("grave"),
	DAMAGE("damage"),
	DAMAGE_STRONG("damage_strong"),
	DAMAGE_MAGIC("damage_magic"),
	DAMAGE_MAGIC_STRONG("damage_magic_strong"),
	DAMAGE_UNHOLY("damage_unholy"),
	DAMAGE_UNHOLY_STRONG("damage_unholy_strong"),
	ATTACK("attack"),
	ARROW_UP("arrow_up"),
	ARROW_DOWN("arrow_down"),
	ARROW_LEFT("arrow_left"),
	ARROW_RIGHT("arrow_right"),
	ARROW_MAGIC("arrow_magic"),
	ARROW_FIRE("arrow_fire"),
	ARROW_WATER("arrow_water"),
	UI_YES("yes"),
	UI_NO("no");

	private final String url;
	private final BufferedImage img;
	Texture(String url) {
		this.url = url;
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream("/" + this.url + ".png"));
		} catch(Exception e) {
			System.out.println("Kunde inte ladda bilden! " + this.url);
		}
		this.img = temp;
	}
	public String value() {
		return url;
	}
	public BufferedImage image() {
		return img;
	}
	public String imgName() { return this.url; }
	public static Texture randomType(String prefix) {
		Texture[] filtered = Arrays.stream(values()).filter(t -> t.name().startsWith(prefix.toUpperCase())).toArray(Texture[]::new);
		return filtered[Random.randomInt(0, filtered.length - 1)];
	}
	public static Texture random() {
		Texture[] values = values();
		return values[Random.randomInt(0, values.length-1)];
	}
}
