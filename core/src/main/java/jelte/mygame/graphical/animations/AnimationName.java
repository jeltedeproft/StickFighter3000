package jelte.mygame.graphical.animations;

public interface AnimationName {

	@Override
	public String toString();

	public void update(float delta);

	public void resetTimer();

	public String getFullName();

	public void rebuildName();

}
