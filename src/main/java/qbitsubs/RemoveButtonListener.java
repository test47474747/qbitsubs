package qbitsubs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;

class RemoveButtonListener implements ActionListener {

	private final JList<String> stringList;
	private final DefaultListModel<String> stringListModel;

	public RemoveButtonListener(JList<String> stringList, DefaultListModel<String> stringListModel) {
		this.stringList = stringList;
		this.stringListModel = stringListModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int selectedIndex = stringList.getSelectedIndex();
		stringListModel.remove(selectedIndex);
	}
}