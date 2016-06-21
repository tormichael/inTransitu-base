package tor.java.inTransitu;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.CodeText;
import JCommonTools.GBC;
import JCommonTools.TableTools;
import JCommonTools.DB.dDBConnection;

public class fFirst extends JFrame 
{
	private inTransitu _it;

	private JTabbedPane 		_tp;
	private JComboBox<CodeText> _cboFilterSrc;
	private JComboBox<CodeText> _cboFilterTgt;
	private JTable				_tabResult;
	private JTextArea 			_txtStatus;
	//private JTextField			_txtDBMCondition;
	private JButton				_cmdFilterApply;
	//private JLabel				_lblDBMCondition;
	private JScrollPane 		_pnResult;
	private JSplitPane 			_splVPanel;
	//private JSplitPane 			_splvDBMan;
	
	
	private void infoNewLine(String aText)
	{
		if (aText == null)
			return;
		
		String result =  _txtStatus.getText()  + CC.NEW_LINE + aText;
		if (_txtStatus != null)
		{
			_txtStatus.setText(result);
			_txtStatus.setSelectionStart(result.length());
		}
	}
	
	private void errorNewLine(String aText)
	{
		if (aText == null)
			return;
		
		String result =  _txtStatus.getText()  + CC.NEW_LINE + _it.getString("Text.Error") + aText;
		if (_txtStatus != null)
		{
			_txtStatus.setText(result);
			_txtStatus.setSelectionStart(result.length());
		}
		
	}

	public fFirst(inTransitu aIT)
	{
		_it = aIT;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(_it.getString("fFirst.Title"));
		this.setIconImage(_it.getImage("inTransitu003.png"));
		
		JToolBar bar = new JToolBar();
		add(bar, BorderLayout.NORTH);
		actAddTrans.putValue(Action.SMALL_ICON, _it.getImageIcon("add.png"));
		bar.add(actAddTrans);
		actEditTrans.putValue(Action.SMALL_ICON, _it.getImageIcon("edit.png"));
		bar.add(actEditTrans);
		actRemoveTrans.putValue(Action.SMALL_ICON, _it.getImageIcon("trash.png"));
		bar.add(actRemoveTrans);
		bar.addSeparator();
		actSetDBConnection.putValue(Action.SMALL_ICON, _it.getImageIcon("dbconnection.png"));
		bar.add(actSetDBConnection);
		bar.addSeparator();

		_tp = new JTabbedPane();
		//
		//  TAB DB manager:
		//

		GridBagLayout gblMain = new GridBagLayout();
		JPanel pnlMain = new JPanel(gblMain);
			JLabel lbl = new JLabel(_it.getString("fFirst.Label.Filter_Src"));
			gblMain.setConstraints(lbl, new GBC(0,0).setIns(2)); //.setAnchor(GBC.WEST));
			pnlMain.add(lbl);
			_cboFilterSrc = new JComboBox<CodeText>();
			gblMain.setConstraints(_cboFilterSrc, new GBC(1,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlMain.add(_cboFilterSrc);
			lbl = new JLabel(_it.getString("fFirst.Label.Filter_Tgt"));
			gblMain.setConstraints(lbl, new GBC(2,0).setIns(2)); //.setAnchor(GBC.WEST));
			pnlMain.add(lbl);
			_cboFilterTgt = new JComboBox<CodeText>();
			gblMain.setConstraints(_cboFilterTgt, new GBC(3,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlMain.add(_cboFilterTgt);
			
			_cmdFilterApply = new JButton(actFilterApply);
			_cmdFilterApply.setText(_it.getString("fFirst.Button.FilterApply"));
			gblMain.setConstraints(_cmdFilterApply, new GBC(4,0).setIns(2));
			pnlMain.add(_cmdFilterApply);
//			_lblDBMCondition = new JLabel(_it.getString("Label.fFirst.DBM.Condition"));
//			gblDBMan.setConstraints(_lblDBMCondition, new GBC(0,1).setIns(2).setAnchor(GBC.WEST));
//			pnlDBMan.add(_lblDBMCondition);
//			_txtDBMCondition = new JTextField();
//			gblDBMan.setConstraints(_txtDBMCondition, new GBC(1,1).setGridSpan(2, 1).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
//			pnlDBMan.add(_txtDBMCondition);
			_tabResult = new JTable();
			_pnResult = new JScrollPane(_tabResult);
			_pnResult.setBorder(BorderFactory.createTitledBorder(_it.getString("fFirst.TitledBorder.Result")));
			gblMain.setConstraints(_pnResult, new GBC(0,1).setGridSpan(5, 1).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlMain.add(_pnResult);
		//_splvDBMan = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _treeDB, pnlDBMan);
		//_tp.addTab(_it.getString("TabbedPane.fFirst.DBMan"), _splvDBMan);
			_tp.addTab(_it.getString("fFirst.TabbedPane.Main"), pnlMain);


		//
		// DOWN PANEL:
		//
		JPanel pnlStatus = new JPanel(new BorderLayout());
		_txtStatus = new JTextArea();
		pnlStatus.add(new JScrollPane(_txtStatus), BorderLayout.CENTER);
		
		//_splHPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlData, pnlRight);
		//_splVPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, _splHPanel, pnlStatus);
		_splVPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, _tp, pnlStatus);
		add(_splVPanel, BorderLayout.CENTER);

		LoadProgramPreference();

//		_cboFilterSrc.addItem(new CodeText(1, _it.getString("Text.DBM.Command.SELECT")));
//		_cboFilterSrc.addItem(new CodeText(2, _it.getString("Text.DBM.Command.DeleteRows")));
//		_cboFilterSrc.addItem(new CodeText(3, _it.getString("Text.DBM.Command.CreateTable")));
		_cboFilterSrc.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (((CodeText)_cboFilterSrc.getSelectedItem()).getCode() == 3)
				{
					//_lblDBMCondition.setText(_it.getString("Label.fCSV.DBM.TableName"));
					//_pnResult.setBorder(BorderFactory.createTitledBorder(_it.getString("TitledBorder.fCSV.DBM.ColDef")));
					//_tabDBMResult.setModel(new tmDBMColDef(_wld));
					_tabResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				}
				else
				{
					//_lblDBMCondition.setText(_it.getString("Label.fFirst.DBM.Condition"));
					//_pnResult.setBorder(BorderFactory.createTitledBorder(_it.getString("TitledBorder.fCSV.DBM.Result")));
					//_tabDBMResult.setModel(new tmDBMResult(null));
					_tabResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				}
			}
		});

		this.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				SaveProgramPreference();
				super.windowClosing(e);
			}
		});
		
		_tp.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				//if(_tp.getTitleAt(_tp.getSelectedIndex()).equals(_it.getString("TabbedPane.fFirst.DBMan")))
				//	_showDBManager();
			}
		});
		
	}

	private void _load()
	{
		_fillCbo(_cboFilterSrc);
		_fillCbo(_cboFilterTgt);
	}
	
	Action actSetDBConnection = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dDBConnection dlg = new dDBConnection(_it.get_wdb());
			dlg.setPreferencesPath(inTransitu.PREFERENCE_PATH);
			dlg.setModal(true);
			dlg.setIconImage(_it.getImage("dbconnection.png"));
			dlg.setVisible(true);
			if (dlg.isResultOk())
			{
				infoNewLine(
						_it.get_wdb().getDBConnParam().Driver.getDescription()
						+ CC.NEW_LINE
						+ _it.get_wdb().getConnectDescription()
					);
				_load();
			}
		}
	};
	
	Action actFilterApply = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			_doResult();
		}
	};

	Action actAddTrans = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			fWord2Word dlg = new fWord2Word(_it);
			dlg.setVisible(true);
			
		}
	};
	
	Action actEditTrans = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			fWord2Word dlg = new fWord2Word(_it);
			dlg.setVisible(true);
			
		}
	};
	
	Action actRemoveTrans = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
	};
	
	private void _fillCbo(JComboBox<CodeText> aCbo)
	{
		Statement sqlCmd = null;
		ResultSet rs = null;
		try
		{
			sqlCmd = _it.get_wdb().getConn().createStatement();
			rs = sqlCmd.executeQuery(_it.getSQL("Select.DictionaryList"));
			while (rs.next())
			{
				aCbo.addItem(new CodeText(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
		}
		catch (Exception ex)
		{
			errorNewLine(ex.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (sqlCmd != null)
					sqlCmd.close();
			}
			catch (Exception ex){}
		}
	}

	private void _doResult()
	{
		Statement sqlCmd = null;
		ResultSet rs = null;
		try
		{
			Statement stm = _it.get_wdb().getConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			//sqlCmd = _it.get_wdb().getConn().createStatement();
			int srcCode = ((CodeText)_cboFilterSrc.getSelectedItem()).getCode();
			int tgtCode = ((CodeText)_cboFilterTgt.getSelectedItem()).getCode();
			String strSelect = String.format(_it.getSQL("Select.WordTrans2Dic"), srcCode, tgtCode);
			rs = stm.executeQuery(strSelect);
			//tmResult tm = new tmResult(rs);

			String[] cols = new String[rs.getMetaData().getColumnCount()];
			for (int ii=0; ii < rs.getMetaData().getColumnCount(); ii++)
				cols[ii] = rs.getMetaData().getColumnLabel(ii+1);
			DefaultTableModel tm = new DefaultTableModel(cols, 0);
			//tm.addColumn("1");
			//tm.addColumn("2");
			//tm.addColumn("3");
			while (rs.next())
			{
				for (int ii=0; ii < cols.length; ii++)
					cols[ii] = rs.getString(ii+1);
				tm.addRow(cols);
				//tm.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3)});
			}
			
			_tabResult.setModel(tm);
			_tabResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//infoNewLine(_wld.getString("Text.Message.ExecutedCommand")); 
			rs.close();
		}
		catch (Exception ex)
		{
			errorNewLine(ex.getMessage());
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (sqlCmd != null)
					sqlCmd.close();
			}
			catch (Exception ex){}
		}
	}

	
 	private void LoadProgramPreference()
	{
		_it.get_wdb().LoadDBConnectioParam2Reg(inTransitu.PREFERENCE_PATH);

		Preferences node = Preferences.userRoot().node(inTransitu.PREFERENCE_PATH+"/fFirst" );
		AsRegister.LoadFrameStateSizeLocation(node, this);
		_splVPanel.setDividerLocation(node.getInt("SplitDividerLocation", 100));
		//_splvDBMan.setDividerLocation(node.getInt("SplitDBMan", 100));
		//_splHPanel.setDividerLocation(node.getInt("SplitHDividerLocation", 100));
		//_txtCSVFileName.setText(node.get("PrevFileName", CC.STR_EMPTY));
		//_currCSVDefFN = node.get("LastPath", CC.STR_EMPTY);
		//_loadCSVData();
		
		//TableTools.SetColumnsWidthFromString(_tabFields, node.get("TabColWidth_Fields", CC.STR_EMPTY));
		//TableTools.SetColumnsWidthFromString(_tabColCorr, node.get("TabColWidth_ColCorr", CC.STR_EMPTY));
		
		
		if (_it.get_wdb().IsDBConnectionParamDefined())
			_load();
	}
	
	private void SaveProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(inTransitu.PREFERENCE_PATH+"/fFirst" );
		
		AsRegister.SaveFrameStateSizeLocation(node, this);
		
		node.putInt("SplitDividerLocation", _splVPanel.getDividerLocation());
		//node.putInt("SplitDBMan", _splvDBMan.getDividerLocation());
		//node.putInt("SplitHDividerLocation", _splHPanel.getDividerLocation());
		//if (_txtCSVFileName.getText().length() > 0)
		//	node.put("PrevFileName", _txtCSVFileName.getText());
		
		//if (_currCSVDefFN != null)
		//	node.put("LastPath", _currCSVDefFN);

		//node.put("TabColWidth_Data", TableTools.GetColumnsWidthAsString(_tabDB));
		//node.put("TabColWidth_Fields", TableTools.GetColumnsWidthAsString(_tabFields));
		//node.put("TabColWidth_ColCorr", TableTools.GetColumnsWidthAsString(_tabColCorr));

		_it.get_wdb().SaveDBConnectioParam2Reg(inTransitu.PREFERENCE_PATH);
	}
	
}
