#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
uniform sampler2D u_texture;
uniform vec2 u_resolution;

void main() {
    vec2 texelSize = vec2(1.0/u_resolution.x, 1.0/u_resolution.y);

    vec4 c11 = texture2D(u_texture, v_texCoord - texelSize);
    vec4 c12 = texture2D(u_texture, v_texCoord);
    vec4 c13 = texture2D(u_texture, v_texCoord + vec2(texelSize.x, -texelSize.y));
    vec4 c21 = texture2D(u_texture, v_texCoord - vec2(texelSize.x, -texelSize.y));
    vec4 c22 = c12;
    vec4 c23 = texture2D(u_texture, v_texCoord + vec2(texelSize.x, texelSize.y));
    vec4 c31 = texture2D(u_texture, v_texCoord - vec2(texelSize.x, texelSize.y*2.0));
    vec4 c32 = texture2D(u_texture, v_texCoord - vec2(0.0, texelSize.y*2.0));
    vec4 c33 = texture2D(u_texture, v_texCoord + vec2(texelSize.x, texelSize.y*2.0));

    vec4 p00 = c31 + 2.0*c32 + c33;
    vec4 p01 = c13 + 2.0*c23 + c33;
    vec4 p10 = c21 + 2.0*c22 + c23;
    vec4 p11 = c11 + 2.0*c12 + c13;

    float a = 1.0/16.0;
    float b = 3.0/16.0;
    float c = 5.0/16.0;
    float d = 7.0/16.0;

    vec4 r0 = d*p00 + c*p01 + b*p10 + a*p11;
    vec4 r1 = b*p00 + c*p01 + d*p10 + a*p11;
    vec4 r2 = a*p00 + d*p01 + c*p10 + b*p11;
    vec4 r3 = c*p00 + b*p01 + a*p10 + d*p11;

    gl_FragColor = (r0 + r1 + r2 + r3) / 4.0;
}
