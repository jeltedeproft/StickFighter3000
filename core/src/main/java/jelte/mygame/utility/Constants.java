package jelte.mygame.utility;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

	public static final int MAX_DOWNKEYS = 10;
	public static final int PLAYER_MAX_HP = 100;

	// PHYSICS
	public static final float MAX_SPEED = 1000;
	public static final float FRICTION = 0.7f;
	public static final float VELOCITY_MIN_TRESHOLD = 0.1f;
	public static final float VELOCITY_STARTUP_TRESHOLD = 100f;
	public static final float STARTUP_SPEED = 0.1f;
	public static final float CLIMB_SPEED = 1.0f;
	public static final float MAX_JUMP_DURATION = 0.5f; // Maximum jump duration in seconds
	public static final int PLAYER_WIDTH = 10;
	public static final float WALK_SPEED = 200.0f;
	public static final float RUN_SPEED = 300.0f;
	public static final float FALL_MOVEMENT_SPEED = 200.0f;
	public static final float SPRINT_SPEED = 400.0f;
	public static final float ROLL_SPEED = 270.0f;
	public static final float FALL_ATTACK_SPEED_BOOST = 50.0f;
	public static final float DASH_DISTANCE = 600;
	public static final float TELEPORT_DISTANCE = 150f;

	public static final Vector2 GRAVITY = new Vector2(0, -9.81f);
	public static final Vector2 JUMP_SPEED = new Vector2(0, 300);
	public static final Vector2 PLAYER_START = new Vector2(300, 300);
	public static final Vector2 MAX_JUMP_SPEED = new Vector2(0, 600);

	// SPELLS
	public static final int MAX_SPELL_SLOTS = 8;

	// AI
	public static final float CONTROL_POINT_REACHED_BUFFER_DISTANCE = 15;

	// COLLISION
	public static final int SPATIAL_MESH_CELL_SIZE = 32;

	// PATHS
	public static final String SPRITES_ATLAS_PATH = "sprites/dark.atlas";
	public static final String SPRITES_BACKGROUND_ATLAS_PATH = "sprites/backgroundSprites.atlas";
	public static final String SKIN_FILE_PATH = "skin/dark.json";
	public static final String INTRO_VIDEO_PATH = "video/main3.ogg";
	public static final String DEFAULT_MAP_PATH = "map/dark/bigone.tmx";
	public static final String MAIN_MENU_MAP_PATH = "map/dark/mainMenu.tmx";
	public static final String DARK1_MAP_PATH = "map/dark/dark.tmx";
	public static final String PLAYER_STATS_FILE_LOCATION = "units/players.json";
	public static final String ENEMY_STATS_FILE_LOCATION = "units/enemies.json";
	public static final String SPELL_STATS_FILE_LOCATION = "spells/spells.json";
	public static final String AUDIO_FILE_LOCATION = "audio/audio.json";
	public static final String MODIFIER_STATS_FILE_LOCATION = "spells/modifiers.json";
	public static final String SPECIAL_EFFECTS_STATS_FILE_LOCATION = "particles/effects.json";

	// TEST
	public static final String PLAYER_STATS_TEST_FILE_LOCATION = "units/players.json";
	public static final String ENEMY_STATS_TEST_FILE_LOCATION = "units/enemies.json";
	public static final String SPELL_STATS_TEST_FILE_LOCATION = "spells/spells.json";
	public static final String SPECIAL_EFFECTS_STATS_TEST_FILE_LOCATION = "particles/effects.json";
	public static final String MAP_TEST_FILE_LOCATION = "tests/testMap/testMap.tmx";
	public static final String TEST_FILE_CEILING_EXPECTED = "tests/TestCollisionSystem/expected/ceiling.txt";
	public static final String TEST_FILE_CEILING_ACTUAL = "tests/TestCollisionSystem/actual/ceiling.txt";
	public static final String TEST_FILE_POSITIONS_EXPECTED = "tests/TestCollisionSystem/expected/positions.txt";
	public static final String TEST_FILE_POSITIONS_ACTUAL = "tests/TestCollisionSystem/actual/positions.txt";
	public static final String TEST_FILE_ACCELERATION_EXPECTED = "tests/TestCollisionSystem/expected/accelerations.txt";
	public static final String TEST_FILE_ACCELERATION_ACTUAL = "tests/TestCollisionSystem/actual/accelerations.txt";
	public static final String TEST_FILE_STUCK_IN_WALL_EXPECTED = "tests/TestCollisionSystem/expected/stuckInWall.txt";
	public static final String TEST_FILE_STUCK_IN_WALL_ACTUAL = "tests/TestCollisionSystem/actual/stuckInWall.txt";

	// NAMES
	public static final String PREFERENCES_KEYBINDINGS = "darkKeyBindings";

	// TILED
	public static final String BLOCK_TYPE_TOP = "TOP";
	public static final String BLOCK_TYPE_BOT = "BOT";
	public static final String BLOCK_TYPE_LEFT = "LEFT";
	public static final String BLOCK_TYPE_RIGHT = "RIGHT";
	public static final String BLOCK_TYPE_PLATFORM = "PLATFORM";
	public static final String LAYER_NAME_BLOCK = "blocks";
	public static final String LAYER_NAME_ITEMS = "items";
	public static final String LAYER_NAME_VISUAL_ITEMS = "visualItemLayer";
	public static final String LAYER_NAME_SPAWN = "spawns";
	public static final String LAYER_NAME_PATROL = "patrols";
	public static final String PROPERTY_DUPLICATE_INDEX = "duplicateIndex";
	public static final String PROPERTY_ENTITY_TYPE_INDEX = "entityTypeIndex";
	public static final String PROPERTY_PATH_INDEX = "pathIndex";

	// HOTKEYS
	public static final String EXIT = "exit";
	public static final String RIGHT = "right";
	public static final String LEFT = "left";
	public static final String DOWN = "down";
	public static final String UP = "up";
	public static final String ATTACK = "attack";
	public static final String KEY_SPRINT = "sprint";
	public static final String KEY_BLOCK = "block";
	public static final String KEY_DASH = "dash";
	public static final String KEY_ROLL = "roll";
	public static final String KEY_TELEPORT = "teleport";
	public static final String KEY_SPELL0 = "spell0";

	// TIMES
	public static final float DEFAULT_ANIMATION_SPEED = 0.05f;
	public static final float MUSIC_FADE_IN_DURATION = 3f;
	public static final float MUSIC_FADE_OUT_DURATION = 5f;

	// DIMENSIONS
	public static final float MINIMAP_WIDTH = 280;
	public static final float MINIMAP_HEIGHT = 120;
	public static final float VISIBLE_WIDTH = 200;
	public static final float VISIBLE_HEIGHT = 200;
	public static final float MAIN_MENU_VISIBLE_WIDTH = 800;
	public static final float MAIN_MENU_VISIBLE_HEIGHT = 400;
	public static final float MAIN_MENU_VISIBLE_UI_WIDTH = 1000;
	public static final float MAIN_MENU_VISIBLE_UI_HEIGHT = 1000;
	public static final float VISIBLE_UI_WIDTH = 800;
	public static final float VISIBLE_UI_HEIGHT = 800;

	public static final float MAP_UNIT_SCALE = 1.0f;
	public static final float MAX_WIDTH_HP_BAR = 100;
	public static final float BORDER_WIDTH_HP_BAR = 1;
	public static final float OFFSET_Y_HP_BAR = 40;
	public static final float MAX_HEIGHT_HP_BAR = 4;
	public static final float STATS_WINDOW_WIDTH_PERCENT_SCREEN = 0.5f;
	public static final float STATS_WINDOW_HEIGHT_PERCENT_SCREEN = 0.15f;
	public static final float STATS_BAR_WIDTH_PERCENT_SCREEN = 0.4f;
	public static final float STATS_BAR_HEIGHT_PERCENT_SCREEN = 0.03f;
	public static final float MINIMAP_DOT_SIZE = 1;

	public static final Color MINIMAP_DOT_COLOR = Color.RED;
	public static final String RED_HEALTHBAR_SPRITE_NAME = "redpixel";
	public static final String HEALTHBAR_BORDER_SPRITE_NAME = "graypixel";

}
