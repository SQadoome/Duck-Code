package layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class AnchorLayout implements LayoutManager {
	
	// called whenever a change happens
		@Override
		public void layoutContainer(Container parent) {
			Vector2 size = new Vector2(parent.getWidth(), parent.getHeight());
			
			int childCount = parent.getComponentCount();
			
			for (int i = 0; i < childCount; i++) {
				Component child = parent.getComponent(i);
				Dimension childSize = child.getBounds().getSize();
				
				double normalX = child.getPreferredSize().width / 100.0;
				double normalY = child.getPreferredSize().height / 100.0;
				
				child.setBounds( (int) (normalX*size.x - child.getWidth()/2), (int) (normalY*size.y - child.getHeight()/2), childSize.width, childSize.height);
				
			}
			
		}
	
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

}
