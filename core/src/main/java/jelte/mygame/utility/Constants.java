package jelte.mygame.utility;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

	public static final int MAX_DOWNKEYS = 10;
	public static final int PLAYER_MAX_HP = 100;

	// PHYSICS
	public static final float MAX_SPEED = 1000;
	public static final Vector2 GRAVITY = new Vector2(0, -10);
	public static final Vector2 JUMP_SPEED = new Vector2(0, 300);
	public static final Vector2 PLAYER_START = new Vector2(100, 50);
	public static final Vector2 ENEMY_START = new Vector2(50, 50);
	public static final int PLAYER_WIDTH = 10;
	public static final int PLAYER_HEIGHT = 10;
	public static final float WALK_SPEED = 2.0f;
	public static final float MOVEMENT_SPEED = 3.0f;
	public static final float SPRINT_SPEED = 4.0f;
	public static final float ROLL_SPEED = 6.0f;
	public static final float DASH_DISTANCE = 150f;
	public static final float MOVEMENT_SPEED_CROUCHED = 1.0f;
	public static final float CAMERA_MOVE_SPEED = 5;

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

	// TILED
	public static final String BLOCK_TYPE_TOP = "TOP";
	public static final String BLOCK_TYPE_BOT = "BOT";
	public static final String BLOCK_TYPE_LEFT = "LEFT";
	public static final String BLOCK_TYPE_RIGHT = "RIGHT";
	public static final String BLOCK_TYPE_PLATFORM = "PLATFORM";

	// HOTKEYS
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

	// TIMES
	public static final Long DASH_TIME = 1000L;
	public static final float DEFAULT_ANIMATION_SPEED = 0.05f;

	// DIMENSIONS
	public static final float HUD_BARS_WIDTH = 500;
	public static final float HUD_TOP_BAR_HEIGHT = 100;
	public static final float HUD_MIDDLE_BAR_HEIGHT = 100;
	public static final float HUD_BOTTOM_BAR_HEIGHT = 100;
	public static final float VISIBLE_WIDTH = 200;
	public static final float VISIBLE_HEIGHT = 200;
	public static final float VISIBLE_MAP_WIDTH = 600;
	public static final float VISIBLE_MAP_HEIGHT = 600;
	public static final float MAP_UNIT_SCALE = 1.0f;
	public static final float DEFAULT_TEXT_BUTTON_WIDTH = 400.0f;
	public static final float DEFAULT_TEXT_BUTTON_HEIGHT = 80.0f;
	public static final float PIXEL_SIZE = 1.0f;

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

	public static final String LUMINANCE_SHADER = "//direction of movement :  0 : up, 1, down\r\n"
			+ "uniform int direction = 1; \r\n"
			+ "//luminance threshold\r\n"
			+ "uniform float l_threshold = 0.8; \r\n"
			+ "//does the movement takes effect above or below luminance threshold ?\r\n"
			+ "uniform bool above = false; \r\n"
			+ "\r\n"
			+ "\r\n"
			+ "//Random function borrowed from everywhere\r\n"
			+ "float rand(vec2 co){\r\n"
			+ "  return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "// Simplex noise :\r\n"
			+ "// Description : Array and textureless GLSL 2D simplex noise function.\r\n"
			+ "//      Author : Ian McEwan, Ashima Arts.\r\n"
			+ "//  Maintainer : ijm\r\n"
			+ "//     Lastmod : 20110822 (ijm)\r\n"
			+ "//     License : MIT  \r\n"
			+ "//               2011 Ashima Arts. All rights reserved.\r\n"
			+ "//               Distributed under the MIT License. See LICENSE file.\r\n"
			+ "//               https://github.com/ashima/webgl-noise\r\n"
			+ "// \r\n"
			+ "\r\n"
			+ "vec3 mod289(vec3 x) {\r\n"
			+ "  return x - floor(x * (1.0 / 289.0)) * 289.0;\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "vec2 mod289(vec2 x) {\r\n"
			+ "  return x - floor(x * (1.0 / 289.0)) * 289.0;\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "vec3 permute(vec3 x) {\r\n"
			+ "  return mod289(((x*34.0)+1.0)*x);\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "float snoise(vec2 v)\r\n"
			+ "  {\r\n"
			+ "  const vec4 C = vec4(0.211324865405187,  // (3.0-sqrt(3.0))/6.0\r\n"
			+ "                      0.366025403784439,  // 0.5*(sqrt(3.0)-1.0)\r\n"
			+ "                     -0.577350269189626,  // -1.0 + 2.0 * C.x\r\n"
			+ "                      0.024390243902439); // 1.0 / 41.0\r\n"
			+ "// First corner\r\n"
			+ "  vec2 i  = floor(v + dot(v, C.yy) );\r\n"
			+ "  vec2 x0 = v -   i + dot(i, C.xx);\r\n"
			+ "\r\n"
			+ "// Other corners\r\n"
			+ "  vec2 i1;\r\n"
			+ "  //i1.x = step( x0.y, x0.x ); // x0.x > x0.y ? 1.0 : 0.0\r\n"
			+ "  //i1.y = 1.0 - i1.x;\r\n"
			+ "  i1 = (x0.x > x0.y) ? vec2(1.0, 0.0) : vec2(0.0, 1.0);\r\n"
			+ "  // x0 = x0 - 0.0 + 0.0 * C.xx ;\r\n"
			+ "  // x1 = x0 - i1 + 1.0 * C.xx ;\r\n"
			+ "  // x2 = x0 - 1.0 + 2.0 * C.xx ;\r\n"
			+ "  vec4 x12 = x0.xyxy + C.xxzz;\r\n"
			+ "  x12.xy -= i1;\r\n"
			+ "\r\n"
			+ "// Permutations\r\n"
			+ "  i = mod289(i); // Avoid truncation effects in permutation\r\n"
			+ "  vec3 p = permute( permute( i.y + vec3(0.0, i1.y, 1.0 ))\r\n"
			+ "		+ i.x + vec3(0.0, i1.x, 1.0 ));\r\n"
			+ "\r\n"
			+ "  vec3 m = max(0.5 - vec3(dot(x0,x0), dot(x12.xy,x12.xy), dot(x12.zw,x12.zw)), 0.0);\r\n"
			+ "  m = m*m ;\r\n"
			+ "  m = m*m ;\r\n"
			+ "\r\n"
			+ "// Gradients: 41 points uniformly over a line, mapped onto a diamond.\r\n"
			+ "// The ring size 17*17 = 289 is close to a multiple of 41 (41*7 = 287)\r\n"
			+ "\r\n"
			+ "  vec3 x = 2.0 * fract(p * C.www) - 1.0;\r\n"
			+ "  vec3 h = abs(x) - 0.5;\r\n"
			+ "  vec3 ox = floor(x + 0.5);\r\n"
			+ "  vec3 a0 = x - ox;\r\n"
			+ "\r\n"
			+ "// Normalise gradients implicitly by scaling m\r\n"
			+ "// Approximation of: m *= inversesqrt( a0*a0 + h*h );\r\n"
			+ "  m *= 1.79284291400159 - 0.85373472095314 * ( a0*a0 + h*h );\r\n"
			+ "\r\n"
			+ "// Compute final noise value at P\r\n"
			+ "  vec3 g;\r\n"
			+ "  g.x  = a0.x  * x0.x  + h.x  * x0.y;\r\n"
			+ "  g.yz = a0.yz * x12.xz + h.yz * x12.yw;\r\n"
			+ "  return 130.0 * dot(m, g);\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "// Simplex noise -- end\r\n"
			+ "\r\n"
			+ "float luminance(vec4 color){\r\n"
			+ "  //(0.299*R + 0.587*G + 0.114*B)\r\n"
			+ "  return color.r*0.299+color.g*0.587+color.b*0.114;\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "vec2 center = vec2(1.0, direction);\r\n"
			+ "\r\n"
			+ "vec4 transition(vec2 uv) {\r\n"
			+ "  vec2 p = uv.xy / vec2(1.0).xy;\r\n"
			+ "  if (progress == 0.0) {\r\n"
			+ "    return getFromColor(p);\r\n"
			+ "  } else if (progress == 1.0) {\r\n"
			+ "    return getToColor(p);\r\n"
			+ "  } else {\r\n"
			+ "    float x = progress;\r\n"
			+ "    float dist = distance(center, p)- progress*exp(snoise(vec2(p.x, 0.0)));\r\n"
			+ "    float r = x - rand(vec2(p.x, 0.1));\r\n"
			+ "    float m;\r\n"
			+ "    if(above){\r\n"
			+ "     m = dist <= r && luminance(getFromColor(p))>l_threshold ? 1.0 : (progress*progress*progress);\r\n"
			+ "    }\r\n"
			+ "    else{\r\n"
			+ "     m = dist <= r && luminance(getFromColor(p))<l_threshold ? 1.0 : (progress*progress*progress);  \r\n"
			+ "    }\r\n"
			+ "    return mix(getFromColor(p), getToColor(p), m);    \r\n"
			+ "  }\r\n"
			+ "}\r\n"
			+ "";

	public static final String VERTEX_SHADER_SIMPLE = "attribute vec4 a_position;\n"
			+ "attribute vec4 a_color;\n"
			+ "attribute vec2 a_texCoord0;\n"
			+ "uniform mat4 u_projTrans;\n"
			+ "varying vec4 v_color;\n"
			+ "varying vec2 v_texCoords;\n"
			+ "void main() {\n"
			+ "v_color = vec4(1, 1, 1, 1);\n"
			+ "v_texCoords = a_texCoord0;\n"
			+ "gl_Position =  u_projTrans * a_position;\n"
			+ "}";
	public static final String FRAGMENT_SHADER_SIMPLE = "#ifdef GL_ES\n"
			+ "precision mediump float;\n"
			+ "#endif\n"
			+ "varying vec4 v_color;\n"
			+ "varying vec2 v_texCoords;\n"
			+ "uniform sampler2D u_texture;\n"
			+ "void main() {\n"
			+ "gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n"
			+ "}";

	public static final String VERTEX_SHADER_PIXEL = "attribute vec4 a_position;\n"
			+ "attribute vec4 a_color;\n"
			+ "attribute vec2 a_texCoord0;\n"
			+ "uniform mat4 u_projTrans;\n"
			+ "varying vec4 v_color;\n"
			+ "varying vec2 v_texCoord;\n"
			+ "\n"
			+ "void main()\n"
			+ "{\n"
			+ "    v_color = a_color;\n"
			+ "    v_texCoord = a_texCoord0;\n"
			+ "    gl_Position = u_projTrans * a_position;\n"
			+ "}";

	public static final String FRAGMENT_SHADER_PIXEL = "#ifdef GL_ES\n"
			+ "precision mediump float;\n"
			+ "#endif\n"
			+ "uniform sampler2D u_texture;\n"
			+ "uniform vec2 u_texelSize;\n"
			+ "uniform float u_texelsPerPixel;\n"
			+ "varying vec4 v_color;\n"
			+ "varying vec2 v_texCoord;\n"
			+ "\n"
			+ "void main() {\n"
			+ "    vec2 locationWithinTexel = fract(v_texCoord);\n"
			+ "    vec2 interpolationAmount = clamp(locationWithinTexel / u_texelsPerPixel, 0.0, 0.5) +\n"
			+ "                               clamp((locationWithinTexel - 1.0) / u_texelsPerPixel + 0.5, 0.0, 0.5);\n"
			+ "    vec2 finalTextureCoords = (floor(v_texCoord) + interpolationAmount) * u_texelSize;\n"
			+ "\n"
			+ "    gl_FragColor = texture2D(u_texture, finalTextureCoords) * v_color;\n"
			+ "}"
			+ "\n";

}
