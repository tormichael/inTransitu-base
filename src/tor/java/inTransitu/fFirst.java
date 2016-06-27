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
	private JComboBox<CodeText> _cboTransFilterSrc;
	private JComboBox<CodeText> _cboTransFilterTgt;
	private JComboBox<CodeText> _cboDicFilter;
	private JTable				_tabTrans;
	private JTable				_tabDic;
	private JTable				_tabLng;
	private JTextArea 			_txtStatus;
	private JButton				_cmdTransFilterApply;
	private JButton				_cmdDicFilterApply;
	private JScrollPane 		_pnTrans;
	private JSplitPane 			_splVPanel;
	
	
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
		//  TAB Translate:
		//
		GridBagLayout gblTrans = new GridBagLayout();
		JPanel pnlTrans = new JPanel(gblTrans);
			JLabel lbl = new JLabel(_it.getString("fFirst.Label.Trans.Filter_Src"));
			gblTrans.setConstraints(lbl, new GBC(0,0).setIns(2)); //.setAnchor(GBC.WEST));
			pnlTrans.add(lbl);
			_cboTransFilterSrc = new JComboBox<CodeText>();
			gblTrans.setConstraints(_cboTransFilterSrc, new GBC(1,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlTrans.add(_cboTransFilterSrc);
			lbl = new JLabel(_it.getString("fFirst.Label.Trans.Filter_Tgt"));
			gblTrans.setConstraints(lbl, new GBC(2,0).setIns(2)); //.setAnchor(GBC.WEST));
			pnlTrans.add(lbl);
			_cboTransFilterTgt = new JComboBox<CodeText>();
			gblTrans.setConstraints(_cboTransFilterTgt, new GBC(3,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlTrans.add(_cboTransFilterTgt);
			_cmdTransFilterApply = new JButton(actFilterApply);
			_cmdTransFilterApply.setText(_it.getString("fFirst.Button.FilterApply"));
			gblTrans.setConstraints(_cmdTransFilterApply, new GBC(4,0).setIns(2));
			pnlTrans.add(_cmdTransFilterApply);
			_tabTrans = new JTable();
			_pnTrans = new JScrollPane(_tabTrans);
			_pnTrans.setBorder(BorderFactory.createTitledBorder(_it.getString("fFirst.TitledBorder.Result")));
			gblTrans.setConstraints(_pnTrans, new GBC(0,1).setGridSpan(5, 1).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlTrans.add(_pnTrans);
		_tp.addTab(_it.getString("fFirst.TabbedPane.Translate"), pnlTrans);
		///
		/// Tab Dictionary
		// 
		GridBagLayout gblDic = new GridBagLayout();
		JPanel pnlDic = new JPanel(gblDic);
			lbl = new JLabel(_it.getString("fFirst.Label.Dic.Filter"));
			gblDic.setConstraints(lbl, new GBC(0,0).setIns(2)); //.setAnchor(GBC.WEST));
			pnlDic.add(lbl);
			_cboDicFilter = new JComboBox<CodeText>();
			gblDic.setConstraints(_cboDicFilter, new GBC(1,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlDic.add(_cboDicFilter);
			_cmdDicFilterApply = new JButton(actDicFilterApply);
			_cmdDicFilterApply.setText(_it.getString("fFirst.Button.FilterApply"));
			gblDic.setConstraints(_cmdDicFilterApply, new GBC(2,0).setIns(2));
			pnlDic.add(_cmdDicFilterApply);
			_tabDic = new JTable();
			JScrollPane scpDic = new JScrollPane(_tabDic);
			scpDic.setBorder(BorderFactory.createTitledBorder(_it.getString("fFirst.TitledBorder.Result")));
			gblDic.setConstraints(scpDic, new GBC(0,1).setGridSpan(3, 1).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlDic.add(scpDic);
		_tp.addTab(_it.getString("fFirst.TabbedPane.Dictionary"), pnlDic);
		///
		/// Tab Language
		// 
		GridBagLayout gblLng = new GridBagLayout();
		JPanel pnlLng = new JPanel(gblTrans);
		_tp.addTab(_it.getString("fFirst.TabbedPane.Language"), pnlLng);
			


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
		_cboTransFilterSrc.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (((CodeText)_cboTransFilterSrc.getSelectedItem()).getCode() == 3)
				{
					//_lblDBMCondition.setText(_it.getString("Label.fCSV.DBM.TableName"));
					//_pnResult.setBorder(BorderFactory.createTitledBorder(_it.getString("TitledBorder.fCSV.DBM.ColDef")));
					//_tabDBMResult.setModel(new tmDBMColDef(_wld));
					_tabTrans.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				}
				else
				{
					//_lblDBMCondition.setText(_it.getString("Label.fFirst.DBM.Condition"));
					//_pnResult.setBorder(BorderFactory.createTitledBorder(_it.getString("TitledBorder.fCSV.DBM.Result")));
					//_tabDBMResult.setModel(new tmDBMResult(null));
					_tabTrans.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
		_fillCbo(_cboTransFilterSrc);
		_fillCbo(_cboTransFilterTgt);
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

	Action actDicFilterApply = new AbstractAction() 
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
			fWord2Words dlg = new fWord2Words(_it);
			dlg.setVisible(true);
			
		}
	};
	
	Action actEditTrans = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			fWord dlg = new fWord(_it);
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
			int srcCode = ((CodeText)_cboTransFilterSrc.getSelectedItem()).getCode();
			int tgtCode = ((CodeText)_cboTransFilterTgt.getSelectedItem()).getCode();
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
			
			_tabTrans.setModel(tm);
			_tabTrans.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
