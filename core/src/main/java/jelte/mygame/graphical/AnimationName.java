package jelte.mygame.graphical;

public interface AnimationName {

	@Override
	public String toString();

	public void update(float delta);

	public void resetTimer();

	public String getFullName();

	public void rebuildName();

}
