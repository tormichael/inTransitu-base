package tor.java.inTransitu;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.CodeText;
import JCommonTools.ComboBoxTools;
import JCommonTools.GBC;

public class fWord2Words  extends JFrame
{
	private inTransitu _it;
	private int _dicSrc;
	private int _dicTgt;
	
	private JComboBox<CodeText> _cboWordSrc;
	private DefaultComboBoxModel<CodeText> _cbmWordSrc;
	private JTable	_tabWordsTgt;
	private JTextArea _txtWordTgt;
	private JButton _cmdSave;
	private JButton _cmdCancel;
	
	public fWord2Words(inTransitu aIT, int aDicSrc, int aDicTgt)
	{
		_it = aIT;
		_dicSrc = aDicSrc;
		_dicTgt = aDicTgt;
		
		GridBagLayout gblMain = new GridBagLayout();
		JPanel pnlMain = new JPanel(gblMain);
			JLabel lbl = new JLabel(_it.getString("fWord2Word.Label.WordSrc"));
			gblMain.setConstraints(lbl, new GBC(0,0).setIns(2));
			pnlMain.add(lbl);
			_cbmWordSrc = new DefaultComboBoxModel<CodeText>();
			_cboWordSrc = new JComboBox<CodeText>(_cbmWordSrc);
			gblMain.setConstraints(_cboWordSrc, new GBC(1,0).setIns(2).setGridSpan(2, 1).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlMain.add(_cboWordSrc);
			_tabWordsTgt = new JTable();
			_txtWordTgt = new JTextArea();
			JSplitPane spp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, _tabWordsTgt, _txtWordTgt);
			gblMain.setConstraints(spp, new GBC(0,1).setIns(2).setGridSpan(3, 1).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlMain.add(spp);
			//////////////////////////////////////////////////////////////////////////
			_cmdSave = new JButton(actSave);
			_cmdSave.setText(_it.getString("common.Button.Save"));
			gblMain.setConstraints(_cmdSave, new GBC(1,2).setAnchor(GBC.EAST).setWeight(1.0, 0.0).setIns(2));
			pnlMain.add(_cmdSave);
			_cmdCancel = new JButton(actCancel);
			_cmdCancel.setText(_it.getString("common.Button.Cancel"));
			gblMain.setConstraints(_cmdCancel, new GBC(2,2).setAnchor(GBC.EAST).setIns(2));
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
	
	private void _load()
	{
		Statement stm = null;
		ResultSet rs = null;
		try
		{
			stm = _it.get_wdb().getConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			String strSelect = String.format(_it.getSQL("Select.WordsOfDic"), _dicSrc);
			rs = stm.executeQuery(strSelect);
			_cbmWordSrc.removeAllElements();

		while (rs.next())
		{
			//_cboWordSrc.addElement(new CodeText(rs.getInt(1),rs.getString(2)));
		}
		
		rs.close();
		}
		catch (Exception ex)
		{
			//errorNewLine(ex.getMessage());
		}
		finally
		{
			try
			{
				if (stm != null)
					stm.close();
			}
			catch (Exception ex){}
		}
	}
	
	Action actSave2 = new AbstractAction() 
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
