package view;

import org.eclipse.swt.events.PaintEvent;
/**
 * defines the game character picture
 * @author Chen Zissu
 * @version 1.0
 * @since 2015-12-28
 */
public interface GameCharacter 
{
	/**
	 * drawing the game character picture
	 * @param e event that draw the picture
	 * @param x x axis in the left up corner
	 * @param y y axis in the left up corner
	 * @param width picture width
	 * @param height picture height
	 */
	public void paint(PaintEvent e,int x,int y, int width, int height);
}
