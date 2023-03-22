package jelte.mygame.utility;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

	public static final int MAX_DOWNKEYS = 10;

	// PATHS
	public static final String SPRITES_ATLAS_PATH = "sprites/dark.atlas";
	public static final String SPRITES_BACKGROUND_ATLAS_PATH = "sprites/backgroundSprites.atlas";
	public static final String SKIN_TEXTURE_ATLAS_PATH = "skin/dark.atlas";
	public static final String SKIN_FILE_PATH = "skin/dark.json";
	public static final String INTRO_VIDEO_PATH = "video/main3.ogg";
	public static final String DEFAULT_MAP_PATH = "map/dark/dark2.tmx";
	public static final String UNIT_STATS_FILE_LOCATION = "units/characters.json";

	// NAMES
	public static final String PLAYER_SPRITE_NAME = "archer";
	public static final String PARALLAX_BG_NAME = "layer";
	public static final String PREFERENCES = "preferences";
	public static final String PREFERENCES_KEYBINDINGS = "darkKeyBindings";
	public static final String PREFERENCE_WIZARD_NAME = "wizardname";
	public static final String TEAM_RED_HEALTHBAR_SPRITE_NAME = "redpixel";
	public static final String TEAM_BLUE_HEALTHBAR_SPRITE_NAME = "bluepixel";
	public static final String TEAM_GREEN_HEALTHBAR_SPRITE_NAME = "greenpixel";
	public static final String TEAM_YELLOW_HEALTHBAR_SPRITE_NAME = "yellowpixel";
	public static final String TEAM_PURPLE_HEALTHBAR_SPRITE_NAME = "purplepixel";
	public static final String HEALTHBAR_BORDER_SPRITE_NAME = "graypixel";
	public static final String EXIT = "Exit";
	public static final String WRONG_CREDENTIALS = "Wrong Credentials";
	public static final String USERNAME = "Username";
	public static final String PASSWORD = "Password";
	public static final String DEFAULT_USERNAME = "Username";
	public static final String DEFAULT_PASSWORD = "Password";
	public static final String LOGIN = "Login";
	public static final String CREATE_ACCOUNT = "Create New Account";
	public static final String CONNECT_FAILED = "Failed to connect to the server.";
	public static final String ERROR_FONT_NAME = "error";
	public static final String ALREADY_LOGGED_IN = "Already logged in";
	public static final String ALREADY_EXISTS = "Account already exists";
	public static final String CHANGE_KEYBINDINGS = "Change Keybindings";
	public static final String MATCHMAKING_CHAOS = "MatchmakingChaos";
	public static final String MATCHMAKING = "Matchmaking";
	public static final String PLAYGROUND = "Playground";
	public static final String FIND_GAME = "Find Game";
	public static final String NEXT_MAP_RIGHT = "Next Map Right";
	public static final String NEXT_MAP_LEFT = "Next Map Left";
	public static final String SELECT_MAP = "Select Map";
	public static final String SELECTED_SPELLS = "Selected Spells";
	public static final String SPELLS = "Spells";
	public static final String SPELL_INFO = "\nSpell Info";
	public static final String SELECT_SPELLS = "Select Spells";
	public static final String INFO = "Info";
	public static final String SEARCH_OPPONENT = "Search Opponent";
	public static final String STATS = "Stats";
	public static final String SELECT_FIGHTER = "Select Fighter";
	public static final String DUMMY = "dummy";
	public static final String PORTAL_SPRITE_NAME = "portal";

	// HOTKEYS
	public static final String RIGHT = "right";
	public static final String LEFT = "left";
	public static final String DOWN = "down";
	public static final String UP = "up";
	public static final String SHIELD = "shield";
	public static final String BASIC_ATTACK = "basicattack";
	public static final String DASH = "dash";
	public static final String CAMERA_LEFT_KEY = "cameraLeft";
	public static final String CAMERA_RIGHT_KEY = "cameraRight";
	public static final String CAMERA_UP_KEY = "cameraUp";
	public static final String CAMERA_DOWN_KEY = "cameraDown";
	public static final String SPELL_9 = "spell9";
	public static final String SPELL_8 = "spell8";
	public static final String SPELL_7 = "spell7";
	public static final String SPELL_6 = "spell6";
	public static final String SPELL_5 = "spell5";
	public static final String SPELL_4 = "spell4";
	public static final String SPELL_3 = "spell3";
	public static final String SPELL_2 = "spell2";
	public static final String SPELL_1 = "spell1";
	public static final String SPELL_0 = "spell0";
	public static final int BASIC_ATTACK_INDEX = 0;
	public static final int DASH_INDEX = 1;
	public static final int SHIELD_INDEX = 2;
	public static final int SPELL_0_INDEX = 3;
	public static final int SPELL_1_INDEX = 4;
	public static final int SPELL_2_INDEX = 5;
	public static final int SPELL_3_INDEX = 6;
	public static final int SPELL_4_INDEX = 7;
	public static final int SPELL_5_INDEX = 8;
	public static final int SPELL_6_INDEX = 9;
	public static final int SPELL_7_INDEX = 10;
	public static final int SPELL_8_INDEX = 11;
	public static final int SPELL_9_INDEX = 12;

	// TIMES
	public static final Long DASH_TIME = 1000L;
	public static final float DEFAULT_ANIMATION_SPEED = 0.05f;

	// BOX2D
	public static final float TIME_STEP = 1 / 60f;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 2;
	public static final Vector2 GRAVITY = new Vector2(0, -300);
	public static final Vector2 JUMP_SPEED = new Vector2(0, 500000);
	public static final Vector2 PLAYER_START = new Vector2(5, 5);
	public static final float DIAGONAL_FACTOR = 1.6f;
	public static final float MOVEMENT_SPEED = 500.0f;
	public static final float DASH_SPEED = 150.0f;
	public static final float CAMERA_MOVE_SPEED = 5;
	public static final float BOUNDARY_SIZE = 0;

	// OFFSETS
	public static final int HP_X_OFFSET = 100;
	public static final int HP_Y_OFFSET = 100;

	// DIMENSIONS
	public static final float VISIBLE_WIDTH = 200;
	public static final float VISIBLE_HEIGHT = 200;
	public static final float VISIBLE_MAP_WIDTH = 600;
	public static final float VISIBLE_MAP_HEIGHT = 600;
	public static final float MAP_UNIT_SCALE = 1.0f;
	public static final float DEFAULT_TEXT_BUTTON_WIDTH = 400.0f;
	public static final float DEFAULT_TEXT_BUTTON_HEIGHT = 80.0f;

	// COLORS
	public static final Color COLOR_PLAYER_1 = Color.RED;
	public static final Color COLOR_PLAYER_2 = Color.GREEN;
	public static final Color COLOR_PLAYER_3 = Color.BLUE;
	public static final Color COLOR_PLAYER_4 = Color.YELLOW;
	public static final float AMBIENT_RED = 0.0f;
	public static final float AMBIENT_GREEN = 0.0f;
	public static final float AMBIENT_BLUE = 0.0f;
	public static final float AMBIENT_ALPHA = 0.0f;
	public static final float DEFAULT_RED = 0.8f;
	public static final float DEFAULT_GREEN = 0.25f;
	public static final float DEFAULT_BLUE = 0.1f;
	public static final float DEFAULT_ALPHA = 1.0f;
	public static final float DEFAULT_SCALE = 3.0f;

	// DATES
	public static final DecimalFormat df = new DecimalFormat("0.00");

	// SHADERS
	public static final String SHADER_VERTEX_ALL_BLACK = "shaders/allblack.vs";
	public static final String SHADER_FRAG_ALL_BLACK = "shaders/allblack.fs";

	public static final String LUMINANCE_SHADER = "//direction of movement :  0 : up, 1, down\r\n" + "uniform int direction = 1; \r\n" + "//luminance threshold\r\n" + "uniform float l_threshold = 0.8; \r\n" + "//does the movement takes effect above or below luminance threshold ?\r\n" + "uniform bool above = false; \r\n" + "\r\n" + "\r\n"
			+ "//Random function borrowed from everywhere\r\n" + "float rand(vec2 co){\r\n" + "  return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);\r\n" + "}\r\n" + "\r\n" + "\r\n" + "// Simplex noise :\r\n" + "// Description : Array and textureless GLSL 2D simplex noise function.\r\n" + "//      Author : Ian McEwan, Ashima Arts.\r\n"
			+ "//  Maintainer : ijm\r\n" + "//     Lastmod : 20110822 (ijm)\r\n" + "//     License : MIT  \r\n" + "//               2011 Ashima Arts. All rights reserved.\r\n" + "//               Distributed under the MIT License. See LICENSE file.\r\n" + "//               https://github.com/ashima/webgl-noise\r\n" + "// \r\n" + "\r\n"
			+ "vec3 mod289(vec3 x) {\r\n" + "  return x - floor(x * (1.0 / 289.0)) * 289.0;\r\n" + "}\r\n" + "\r\n" + "vec2 mod289(vec2 x) {\r\n" + "  return x - floor(x * (1.0 / 289.0)) * 289.0;\r\n" + "}\r\n" + "\r\n" + "vec3 permute(vec3 x) {\r\n" + "  return mod289(((x*34.0)+1.0)*x);\r\n" + "}\r\n" + "\r\n" + "float snoise(vec2 v)\r\n" + "  {\r\n"
			+ "  const vec4 C = vec4(0.211324865405187,  // (3.0-sqrt(3.0))/6.0\r\n" + "                      0.366025403784439,  // 0.5*(sqrt(3.0)-1.0)\r\n" + "                     -0.577350269189626,  // -1.0 + 2.0 * C.x\r\n" + "                      0.024390243902439); // 1.0 / 41.0\r\n" + "// First corner\r\n"
			+ "  vec2 i  = floor(v + dot(v, C.yy) );\r\n" + "  vec2 x0 = v -   i + dot(i, C.xx);\r\n" + "\r\n" + "// Other corners\r\n" + "  vec2 i1;\r\n" + "  //i1.x = step( x0.y, x0.x ); // x0.x > x0.y ? 1.0 : 0.0\r\n" + "  //i1.y = 1.0 - i1.x;\r\n" + "  i1 = (x0.x > x0.y) ? vec2(1.0, 0.0) : vec2(0.0, 1.0);\r\n" + "  // x0 = x0 - 0.0 + 0.0 * C.xx ;\r\n"
			+ "  // x1 = x0 - i1 + 1.0 * C.xx ;\r\n" + "  // x2 = x0 - 1.0 + 2.0 * C.xx ;\r\n" + "  vec4 x12 = x0.xyxy + C.xxzz;\r\n" + "  x12.xy -= i1;\r\n" + "\r\n" + "// Permutations\r\n" + "  i = mod289(i); // Avoid truncation effects in permutation\r\n" + "  vec3 p = permute( permute( i.y + vec3(0.0, i1.y, 1.0 ))\r\n"
			+ "		+ i.x + vec3(0.0, i1.x, 1.0 ));\r\n" + "\r\n" + "  vec3 m = max(0.5 - vec3(dot(x0,x0), dot(x12.xy,x12.xy), dot(x12.zw,x12.zw)), 0.0);\r\n" + "  m = m*m ;\r\n" + "  m = m*m ;\r\n" + "\r\n" + "// Gradients: 41 points uniformly over a line, mapped onto a diamond.\r\n"
			+ "// The ring size 17*17 = 289 is close to a multiple of 41 (41*7 = 287)\r\n" + "\r\n" + "  vec3 x = 2.0 * fract(p * C.www) - 1.0;\r\n" + "  vec3 h = abs(x) - 0.5;\r\n" + "  vec3 ox = floor(x + 0.5);\r\n" + "  vec3 a0 = x - ox;\r\n" + "\r\n" + "// Normalise gradients implicitly by scaling m\r\n"
			+ "// Approximation of: m *= inversesqrt( a0*a0 + h*h );\r\n" + "  m *= 1.79284291400159 - 0.85373472095314 * ( a0*a0 + h*h );\r\n" + "\r\n" + "// Compute final noise value at P\r\n" + "  vec3 g;\r\n" + "  g.x  = a0.x  * x0.x  + h.x  * x0.y;\r\n" + "  g.yz = a0.yz * x12.xz + h.yz * x12.yw;\r\n" + "  return 130.0 * dot(m, g);\r\n" + "}\r\n"
			+ "\r\n" + "// Simplex noise -- end\r\n" + "\r\n" + "float luminance(vec4 color){\r\n" + "  //(0.299*R + 0.587*G + 0.114*B)\r\n" + "  return color.r*0.299+color.g*0.587+color.b*0.114;\r\n" + "}\r\n" + "\r\n" + "vec2 center = vec2(1.0, direction);\r\n" + "\r\n" + "vec4 transition(vec2 uv) {\r\n" + "  vec2 p = uv.xy / vec2(1.0).xy;\r\n"
			+ "  if (progress == 0.0) {\r\n" + "    return getFromColor(p);\r\n" + "  } else if (progress == 1.0) {\r\n" + "    return getToColor(p);\r\n" + "  } else {\r\n" + "    float x = progress;\r\n" + "    float dist = distance(center, p)- progress*exp(snoise(vec2(p.x, 0.0)));\r\n" + "    float r = x - rand(vec2(p.x, 0.1));\r\n" + "    float m;\r\n"
			+ "    if(above){\r\n" + "     m = dist <= r && luminance(getFromColor(p))>l_threshold ? 1.0 : (progress*progress*progress);\r\n" + "    }\r\n" + "    else{\r\n" + "     m = dist <= r && luminance(getFromColor(p))<l_threshold ? 1.0 : (progress*progress*progress);  \r\n" + "    }\r\n" + "    return mix(getFromColor(p), getToColor(p), m);    \r\n"
			+ "  }\r\n" + "}\r\n" + "";
}
