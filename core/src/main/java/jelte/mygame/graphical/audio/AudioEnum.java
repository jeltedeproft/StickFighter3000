package jelte.mygame.graphical.audio;

public enum AudioEnum {
	PLAYLIST_MAIN,
	SOUND_WALK,
	SOUND_JUMP,
	SOUND_LANDING,
	SOUND_FALL,
	SOUND_APPEAR,
	SOUND_ATTACK_WINDUP,
	SOUND_ATTACK_LOOP,
	SOUND_ATTACK_END,
	SOUND_SHIELD,
	SOUND_CLIMB,
	SOUND_DASH,
	SOUND_DEATH,
	SOUND_GRAB,
	SOUND_HURT,
	SOUND_ROLL,
	SOUND_SPRINTING,
	SOUND_TELEPORT,
	SOUND_SLIDE,
	SOUND_FIREBALL_LOOP,
	SOUND_FIREBALL_END,
	SOUND_FIREBALL_WINDUP,
	SOUND_BOOM,
	SOUND_FLYBY,
	SOUND_FALLSTRIKE,
	SOUND_PRECAST_SWORDMASTER,
	SOUND_CAST_SWORDMASTER,
	SOUND_AMBIENCE_CAVE,
	SOUND_CHASE_STATE_ALCHEMIST,
	SOUND_ATTACK_STATE_ALCHEMIST,
	SOUND_FIREBALL_ITEM,
	SOUND_RANGEDATTACK_LOOP,
	SOUND_RANGEDATTACK_END,
	SOUND_RANGEDATTACK_WINDUP;

	private static final AudioEnum[] copyOfValues = AudioEnum.values();

	public static AudioEnum forName(String name) {
		for (AudioEnum value : copyOfValues) {
			if (value.name().equalsIgnoreCase(name)) {
				return value;
			}
		}
		return null;
	}

	public static boolean isSound(AudioEnum audioEnum) {
		return audioEnum.name().startsWith("SOUND_");
	}

	public static boolean isPlaylist(AudioEnum audioEnum) {
		return audioEnum.name().startsWith("PLAYLIST_");
	}

}
