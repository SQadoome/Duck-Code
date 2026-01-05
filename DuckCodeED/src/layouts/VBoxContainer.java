package layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class VBoxContainer implements LayoutManager {

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void layoutContainer(Container parent) {
		int atX = parent.getX();
		int sizeX = parent.getWidth()/parent.getComponentCount();
		
		for (int i = 0; i < parent.getComponentCount(); i++) {
			Component comp = parent.getComponent(i);
			
			comp.setBounds(atX, comp.getY(), sizeX, comp.getHeight());
			atX += comp.getWidth();
		}
		
	}

}
