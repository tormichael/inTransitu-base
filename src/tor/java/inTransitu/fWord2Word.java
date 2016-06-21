package tor.java.inTransitu;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import JCommonTools.AsRegister;
import JCommonTools.CodeText;
import JCommonTools.GBC;

public class fWord2Word  extends JFrame
{
	private inTransitu _it;
	
	private JComboBox<CodeText> _cboFilterSrc;
	private JComboBox<CodeText> _cboFilterTgt;
	private JComboBox<CodeText> _cboWordSrc;
	private JTextField _txtTranscription;
	private GridBagLayout _gblTgt;
	private ArrayList<JComboBox<CodeText>> _arCboWordTgt;
	private JTextArea _txtWordsTgt;
	private JButton _cmdWordAdd;
	private JButton _cmdWordMultyLine;
	private JPanel _pnlTgt;
	private JButton _cmdSave;
	private JButton _cmdCancel;
	
	public fWord2Word(inTransitu aIT)
	{
		_it = aIT;
		
		GridBagLayout gblMain = new GridBagLayout();
		JPanel pnlMain = new JPanel(gblMain);
			JLabel lbl = new JLabel(_it.getString("fWord2Word.Label.WordSrc"));
			gblMain.setConstraints(lbl, new GBC(0,0).setIns(2)); //.setAnchor(GBC.WEST));
			pnlMain.add(lbl);
			lbl = new JLabel(_it.getString("fWord2Word.Label.WordTgt"));
			gblMain.setConstraints(lbl, new GBC(1,0).setIns(2).setGridSpan(2, 1)); //.setAnchor(GBC.WEST));
			pnlMain.add(lbl);
			
			_cboFilterSrc = new JComboBox<CodeText>();
			gblMain.setConstraints(_cboFilterSrc, new GBC(0,1).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlMain.add(_cboFilterSrc);
			_cboFilterTgt = new JComboBox<CodeText>();
			gblMain.setConstraints(_cboFilterTgt, new GBC(1,1).setIns(2).setGridSpan(2, 1).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlMain.add(_cboFilterTgt);
			
			///// LEFT //////////////////////////////////////////////////////////////
			GridBagLayout gblSrc = new GridBagLayout();
			JPanel pnlSrc = new JPanel(gblSrc);
				_cboWordSrc = new JComboBox<CodeText>();
				gblSrc.setConstraints(_cboWordSrc, new GBC(0,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0)); //.setAnchor(GBC.WEST));
				pnlSrc.add(_cboWordSrc);
				lbl = new JLabel(_it.getString("fWord2Word.Label.Transcription"));
				gblSrc.setConstraints(lbl, new GBC(0,1).setIns(2)); //.setAnchor(GBC.WEST));
				pnlSrc.add(lbl);
				_txtTranscription = new JTextField();
				gblSrc.setConstraints(_txtTranscription, new GBC(0,2).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0)); //.setAnchor(GBC.WEST));
				pnlSrc.add(_txtTranscription);
			gblMain.setConstraints(pnlSrc, new GBC(0,2).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 1.0));
			pnlMain.add(pnlSrc);
			///// RIGHT //////////////////////////////////////////////////////////////
			_gblTgt = new GridBagLayout();
			_pnlTgt = new JPanel(_gblTgt);
				_arCboWordTgt = new ArrayList<JComboBox<CodeText>>();
				_txtWordsTgt = null;
				JComboBox<CodeText> cbo = new JComboBox<CodeText>();
				_arCboWordTgt.add(cbo);
				_gblTgt.setConstraints(cbo, new GBC(0,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0)); //.setAnchor(GBC.WEST));
				_pnlTgt.add(cbo);
				_cmdWordAdd = new JButton(actWordAdd);
				_cmdWordAdd.setText("+");
				_gblTgt.setConstraints(_cmdWordAdd, new GBC(1,0).setIns(2)); //.setAnchor(GBC.WEST));
				_pnlTgt.add(_cmdWordAdd);
				_cmdWordMultyLine = new JButton(actWordMultyLine);
				_cmdWordMultyLine.setText("#");
				_gblTgt.setConstraints(_cmdWordMultyLine, new GBC(3,0).setIns(2)); //.setAnchor(GBC.WEST));
				_pnlTgt.add(_cmdWordMultyLine);
			gblMain.setConstraints(_pnlTgt, new GBC(1,2).setIns(2).setGridSpan(2, 1).setFill(GBC.HORIZONTAL).setWeight(1.0, 1.0));
			pnlMain.add(_pnlTgt);
			//////////////////////////////////////////////////////////////////////////
			_cmdSave = new JButton(actSave);
			_cmdSave.setText(_it.getString("fWord2Word.Button.Save"));
			gblMain.setConstraints(_cmdSave, new GBC(1,3).setIns(2));
			pnlMain.add(_cmdSave);
			_cmdCancel = new JButton(actCancel);
			_cmdCancel.setText(_it.getString("fWord2Word.Button.Cancel"));
			gblMain.setConstraints(_cmdCancel, new GBC(2,3).setIns(2));
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

	Action actWordAdd = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}
	};
	
	Action actWordMultyLine = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			JComboBox<CodeText> cbo = _arCboWordTgt.get(0);
			_pnlTgt.remove(cbo);
			_txtWordsTgt = new JTextArea();
			_gblTgt.setConstraints(_txtWordsTgt, new GBC(0,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 1.0)); //.setAnchor(GBC.WEST));
			_pnlTgt.add(_txtWordsTgt);
			_cmdWordAdd.setEnabled(false);
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

		Preferences node = Preferences.userRoot().node(inTransitu.PREFERENCE_PATH+"/fWord2Word" );
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
		Preferences node = Preferences.userRoot().node(inTransitu.PREFERENCE_PATH+"/fWord2Word" );
		
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
