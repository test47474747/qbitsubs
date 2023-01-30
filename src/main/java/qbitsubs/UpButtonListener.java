package qbitsubs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;

class UpButtonListener implements ActionListener {

	private final JList<String> stringList;
	private final DefaultListModel<String> stringListModel;

	public UpButtonListener(JList<String> stringList, DefaultListModel<String> stringListModel) {
		this.stringList = stringList;
		this.stringListModel = stringListModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int selectedIndex = stringList.getSelectedIndex();
		if (selectedIndex > 0) {
			String selectedString = stringListModel.remove(selectedIndex);
			stringListModel.add(selectedIndex - 1, selectedString);
			stringList.setSelectedIndex(selectedIndex - 1);
		}
	}
}