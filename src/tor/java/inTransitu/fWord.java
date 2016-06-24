package tor.java.inTransitu;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import JCommonTools.AsRegister;
import JCommonTools.CodeText;
import JCommonTools.GBC;
import JCommonTools.Panel.pnImage;

public class fWord  extends JFrame
{
	private inTransitu _it;
	
	private JComboBox<CodeText> _cboDictionary;
	private JTextArea _txtWord;
	private JTextField _txtPath2Image;
	private JTextField _txtPath2Sound;
	private pnImage _img;
	private JButton _cmdPath2Image;	
	private JButton _cmdPath2Sound;
	private JPanel _snd;
	private JButton _cmdSave;
	private JButton _cmdCancel;

	public fWord(inTransitu aIT)
	{
		_it = aIT;
		
		GridBagLayout gblMain = new GridBagLayout();
		JPanel pnlMain = new JPanel(gblMain);
			///// LEFT //////////////////////////////////////////////////////////////
			GridBagLayout gblLeft = new GridBagLayout();
			JPanel pnlLeft = new JPanel(gblLeft);
				JLabel lbl = new JLabel(_it.getString("fWord.Label.Dictionary"));
				gblLeft.setConstraints(lbl, new GBC(0,0).setIns(2)); //.setAnchor(GBC.WEST));
				pnlLeft.add(lbl);
				_cboDictionary = new JComboBox<CodeText>();
				gblLeft.setConstraints(_cboDictionary, new GBC(0,1).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0)); //.setAnchor(GBC.WEST));
				pnlLeft.add(_cboDictionary);
				_txtWord = new JTextArea();
				JScrollPane sp = new JScrollPane(_txtWord);
				gblLeft.setConstraints(sp, new GBC(0,2).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0)); //.setAnchor(GBC.WEST));
				pnlLeft.add(sp);
			gblMain.setConstraints(pnlLeft, new GBC(0,0).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlMain.add(pnlLeft);
			///// RIGHT //////////////////////////////////////////////////////////////
			GridBagLayout gblRight = new GridBagLayout();
			JPanel pnlRight = new JPanel(gblRight);
				//// TOP /////////////////////////////////////////////
				GridBagLayout gblRightTop = new GridBagLayout();
				JPanel pnlRightTop = new JPanel(gblRightTop);
					lbl = new JLabel(_it.getString("fWord.Label.Path2Image"));
					gblRightTop.setConstraints(lbl, new GBC(0,0).setIns(2).setGridSpan(2,1)); //.setAnchor(GBC.WEST));
					pnlRightTop.add(lbl);
					_txtPath2Image = new JTextField();
					//_txtPath2Image.
					gblRightTop.setConstraints(_txtPath2Image, new GBC(0,1).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0)); //.setAnchor(GBC.WEST));
					pnlRightTop.add(_txtPath2Image);
					_cmdPath2Image = new JButton(actPath2Image);
					_cmdPath2Image.setText(_it.getString("fWord.Button.Path2Image"));
					gblRightTop.setConstraints(_cmdPath2Image, new GBC(1,1).setIns(2));
					pnlRightTop.add(_cmdPath2Image);
					_img = new pnImage();
					gblRightTop.setConstraints(_img, new GBC(0,2).setIns(2).setFill(GBC.BOTH).setGridSpan(2, 1).setWeight(1.0, 1.0));
					pnlRightTop.add(_img);
				gblRight.setConstraints(pnlRightTop, new GBC(0,0).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
				pnlRight.add(pnlRightTop);
				//// DOWN ///////////////////////////////////////////
				GridBagLayout gblRightDown = new GridBagLayout();
				JPanel pnlRightDown = new JPanel(gblRightDown);
					lbl = new JLabel(_it.getString("fWord.Label.Path2Sound"));
					gblRightDown.setConstraints(lbl, new GBC(0,0).setIns(2).setGridSpan(2,1)); //.setAnchor(GBC.WEST));
					pnlRightDown.add(lbl);
					_txtPath2Sound = new JTextField();
					//_txtPath2Image.
					gblRightDown.setConstraints(_txtPath2Sound, new GBC(0,1).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0)); //.setAnchor(GBC.WEST));
					pnlRightDown.add(_txtPath2Sound);
					_cmdPath2Sound = new JButton(actPath2Sound);
					_cmdPath2Sound.setText(_it.getString("fWord.Button.Path2Sound"));
					gblRightDown.setConstraints(_cmdPath2Sound, new GBC(1,1).setIns(2));
					pnlRightDown.add(_cmdPath2Sound);
					_snd = new JPanel();
					gblRightDown.setConstraints(_snd, new GBC(0,2).setIns(2).setFill(GBC.HORIZONTAL).setGridSpan(2, 1).setWeight(1.0, 0.0));
					pnlRightDown.add(_snd);
				gblRight.setConstraints(pnlRightDown, new GBC(0,1).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 0.0));
				pnlRight.add(pnlRightDown);
			gblMain.setConstraints(pnlRight, new GBC(1,0).setIns(2).setGridSpan(2, 1).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlMain.add(pnlRight);
			//////////////////////////////////////////////////////////////////////////

			_cmdSave = new JButton(actSave);
			_cmdSave.setText(_it.getString("common.Button.Save"));
			gblMain.setConstraints(_cmdSave, new GBC(0,1).setAnchor(GBC.EAST).setIns(2));
			pnlMain.add(_cmdSave);
			_cmdCancel = new JButton(actCancel);
			_cmdCancel.setText(_it.getString("common.Button.Cancel"));
			gblMain.setConstraints(_cmdCancel, new GBC(1,1).setAnchor(GBC.WEST).setIns(2));
			pnlMain.add(_cmdCancel);
			
		add(pnlMain, BorderLayout.CENTER);

		
		LoadProgramPreference();
	
		this.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				SaveProgramPreference();
				super.windowClosing(e);
			}
		});
		
	}
	
	Action actPath2Image = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			JFileChooser dlg = new JFileChooser();
			//if (_lastPath != null)
			//	dlg.setCurrentDirectory(new File(_lastPath));
			dlg.setAcceptAllFileFilterUsed(false);
			dlg.addChoosableFileFilter(new FileNameExtensionFilter("Image files", "jpg","png","gif","tiff"));
			//dlg.addChoosableFileFilter(new FileNameExtensionFilter("All files"));
			dlg.setMultiSelectionEnabled(false);
			if (dlg.showOpenDialog(fWord.this) == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					//_lastPath = dlg.getSelectedFile().getPath();
					_txtPath2Image.setText(dlg.getSelectedFile().getPath());
					BufferedImage bi = ImageIO.read(new File(_txtPath2Image.getText()));
					_img.setImage(bi);
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
			
		}
	};
	
	Action actPath2Sound = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}
	};
	
	Action actSave = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}
	};
	
	Action actCancel = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			
		}
	};
	
 	private void LoadProgramPreference()
	{
		_it.get_wdb().LoadDBConnectioParam2Reg(inTransitu.PREFERENCE_PATH);

		Preferences node = Preferences.userRoot().node(inTransitu.PREFERENCE_PATH+"/fWord" );
		AsRegister.LoadFrameStateSizeLocation(node, this);
		//_splVPanel.setDividerLocation(node.getInt("SplitDividerLocation", 100));
		//_splvDBMan.setDividerLocation(node.getInt("SplitDBMan", 100));
		//_splHPanel.setDividerLocation(node.getInt("SplitHDividerLocation", 100));
		//_txtCSVFileName.setText(node.get("PrevFileName", CC.STR_EMPTY));
		//_currCSVDefFN = node.get("LastPath", CC.STR_EMPTY);
		//_loadCSVData();
		
		//TableTools.SetColumnsWidthFromString(_tabFields, node.get("TabColWidth_Fields", CC.STR_EMPTY));
		//TableTools.SetColumnsWidthFromString(_tabColCorr, node.get("TabColWidth_ColCorr", CC.STR_EMPTY));
		
	}
	
	private void SaveProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(inTransitu.PREFERENCE_PATH+"/fWord" );
		
		AsRegister.SaveFrameStateSizeLocation(node, this);
		
		//node.putInt("SplitDBMan", _splvDBMan.getDividerLocation());
		//node.putInt("SplitHDividerLocation", _splHPanel.getDividerLocation());
		//if (_txtCSVFileName.getText().length() > 0)
		//	node.put("PrevFileName", _txtCSVFileName.getText());
		
		//if (_currCSVDefFN != null)
		//	node.put("LastPath", _currCSVDefFN);

		//node.put("TabColWidth_Data", TableTools.GetColumnsWidthAsString(_tabDB));
		//node.put("TabColWidth_Fields", TableTools.GetColumnsWidthAsString(_tabFields));
		//node.put("TabColWidth_ColCorr", TableTools.GetColumnsWidthAsString(_tabColCorr));
	}
}
