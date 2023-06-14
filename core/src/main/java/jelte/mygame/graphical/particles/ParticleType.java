package jelte.mygame.graphical.particles;

public enum ParticleType {
	DUST("particles/dust.p");

	private String particleFileLocation;

	public String getParticleFileLocation() {
		return particleFileLocation;
	}

	ParticleType(final String particleLocation) {
		particleFileLocation = particleLocation;
	}
}
