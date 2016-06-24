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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import JCommonTools.AsRegister;
import JCommonTools.CodeText;
import JCommonTools.GBC;

public class fWord2Words  extends JFrame
{
	private inTransitu _it;
	private JButton _cmdSave;
	private JButton _cmdCancel;
	
	public fWord2Words(inTransitu aIT)
	{
		_it = aIT;
		
		GridBagLayout gblMain = new GridBagLayout();
		JPanel pnlMain = new JPanel(gblMain);
//			gblMain.setConstraints(pnlRight, new GBC(1,0).setIns(2).setGridSpan(2, 1).setFill(GBC.BOTH).setWeight(1.0, 1.0));
//			pnlMain.add(pnlRight);
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
