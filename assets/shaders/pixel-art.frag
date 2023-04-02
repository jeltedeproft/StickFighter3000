#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform vec2 u_texSize;
uniform vec2 u_resolution;
uniform float u_pixelSize;

void main() {
    vec2 st = gl_FragCoord.xy / u_resolution.xy;
    vec2 uv = st * u_texSize / u_resolution;

    // Pixelate
    uv = floor(uv / u_pixelSize) * u_pixelSize + (u_pixelSize / 2.0);
    vec2 pixel = 1.0 / u_texSize;
    vec2 pixelOffset = pixel / u_pixelSize;

    // Sample texture
    vec3 color = vec3(0.0);
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            vec2 sampleUV = uv + vec2(float(i), float(j)) * pixelOffset;
            color += texture(u_texture, sampleUV).rgb;
        }
    }
    color /= 9.0;

    // Edges
    float edgeFactor = 2.5;
    vec4 texel = texture(u_texture, uv);
    float dx = edgeFactor * (texel.x - texture(u_texture, uv + vec2(pixel.x, 0.0)).x);
    float dy = edgeFactor * (texel.x - texture(u_texture, uv + vec2(0.0, pixel.y)).x);
    float edge = 1.0 - length(vec2(dx, dy));
    color *= edge * edge;

    gl_FragColor = vec4(color, 1.0);
}
