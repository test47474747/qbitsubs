package qbitsubs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.TransferHandler;
import javax.swing.WindowConstants;

public class StringPicker extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4088059301707484224L;
	private JList<String> initialList;
	private JList<String> reorderedList;
	private JScrollPane initialPane;
	private JScrollPane reorderedPane;
	private JButton saveButton;
	private JPanel buttonPanel = new JPanel();
	private JButton upButton = new JButton("Up");
	private JButton downButton = new JButton("Down");
	private JButton removeButton = new JButton("Remove");
	private List<String> strings;

	public StringPicker() {
		this.strings = Arrays.asList("Arabic", "baq", "Bokmal", "cat", "Chinese", "Czech", "Danish", "Dutch", "English",
				"fil", "Finnish", "French", "German", "glg", "Greek", "Hebrew", "hrv", "Hungarian", "Indonesian",
				"Italian", "Japanese", "Korean", "may", "Polish", "Portuguese", "Romanian", "Russian", "Spanish",
				"Swedish", "Thai", "Turkish", "ukr", "Vietnamese");
		setTitle("String Picker");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		// Initialize the initial list and scroll pane
		initialList = new JList<>(strings.toArray(new String[strings.size()]));
		initialList.setDragEnabled(true);
		initialList.setTransferHandler(new TransferHandler() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7283837142581487784L;

			@Override
			public int getSourceActions(JComponent c) {
				return MOVE;
			}

			@Override
			protected Transferable createTransferable(JComponent c) {
				return new Transferable() {
					@Override
					public DataFlavor[] getTransferDataFlavors() {
						return new DataFlavor[] { DataFlavor.stringFlavor };
					}

					@Override
					public boolean isDataFlavorSupported(DataFlavor flavor) {
						return flavor.equals(DataFlavor.stringFlavor);
					}

					@Override
					public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
						return initialList.getSelectedValue();
					}
				};
			}
		});
		initialPane = new JScrollPane(initialList);

		// Initialize the reordered list and scroll pane
		DefaultListModel<String> model = new DefaultListModel<>();
		reorderedList = new JList<>(model);
		reorderedList.setDropMode(DropMode.INSERT);
		reorderedList.setTransferHandler(new TransferHandler() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 845894545124148672L;

			@Override
			public boolean canImport(TransferHandler.TransferSupport support) {
				return support.isDataFlavorSupported(DataFlavor.stringFlavor);
			}

			@Override
			public boolean importData(TransferHandler.TransferSupport support) {
				try {
					String string = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
					DefaultListModel<String> model = (DefaultListModel<String>) reorderedList.getModel();
					int index = reorderedList.getDropLocation().getIndex();
					model.add(index, string);
					return true;
				} catch (UnsupportedFlavorException | IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		});
		reorderedPane = new JScrollPane(reorderedList);

		// Add action listeners for buttons
		upButton.addActionListener(new UpButtonListener(reorderedList, model));
		downButton.addActionListener(new DownButtonListener(reorderedList, model));
		removeButton.addActionListener(new RemoveButtonListener(reorderedList, model));

		// Add buttons to button panel
		buttonPanel.add(upButton);
		buttonPanel.add(downButton);
		buttonPanel.add(removeButton);

		// Initialize the save button
		saveButton = new JButton("Save");
		final ListModel<String> reorderedListModel = reorderedList.getModel();
		saveButton.addActionListener(e -> {			
			List<String> reorderedStrings = new ArrayList<>();
			for (int i = 0; i < reorderedListModel.getSize(); i++) {
				reorderedStrings.add(reorderedListModel.getElementAt(i));
			}
			Path path = Paths.get(System.getProperty("user.dir") + "\\Languages.txt");
			try {
				Files.write(path, reorderedStrings);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});

		// Add the scroll panes and button to the frame
		add(initialPane);
		add(reorderedPane);
		add(saveButton);
		add(buttonPanel, BorderLayout.SOUTH);

		// Set the size and make the frame visible
		setSize(500, 500);
		setVisible(true);
	}
}