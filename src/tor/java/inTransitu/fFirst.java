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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.CodeText;
import JCommonTools.ComboBoxTools;
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
	//private JTable					_tabTrans;
	private JList<CodeText> _lstTransSrc;
	private DefaultListModel<CodeText> _lstModel;
	private JTable					_tabWrd;
	private JTable					_tabDic;
	private JTable					_tabLng;
	private JTextArea			_txtTransTgt;
	private JTextArea 			_txtStatus;
	private JSplitPane 			_splVPanel;
	private JSplitPane 			_splHPnlTrans;
	
	
	
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
		actFilterApply.putValue(Action.SMALL_ICON, _it.getImageIcon("exec.png"));
		bar.add(actFilterApply);
		bar.addSeparator();
		actSetDBConnection.putValue(Action.SMALL_ICON, _it.getImageIcon("dbconnection.png"));
		bar.add(actSetDBConnection);
		bar.addSeparator();

		_tp = new JTabbedPane();
		//
		//  TAB Translate:
		//
		GridBagLayout gblTransSrc = new GridBagLayout();
		JPanel pnlTransSrc = new JPanel(gblTransSrc);
			JLabel lbl = new JLabel(_it.getString("fFirst.Label.Trans.Filter_Src"));
			gblTransSrc.setConstraints(lbl, new GBC(0,0).setIns(2)); //.setAnchor(GBC.WEST));
			pnlTransSrc.add(lbl);
			_cboTransFilterSrc = new JComboBox<CodeText>();
			gblTransSrc.setConstraints(_cboTransFilterSrc, new GBC(0,1).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlTransSrc.add(_cboTransFilterSrc);
//			_tabTrans = new JTable();
			_lstModel = new DefaultListModel<CodeText>();
			_lstTransSrc = new JList<>(_lstModel);
			JScrollPane scPnl = new JScrollPane(_lstTransSrc);
			//_pnTrans.setBorder(BorderFactory.createTitledBorder(_it.getString("fFirst.TitledBorder.Result")));
			gblTransSrc.setConstraints(scPnl, new GBC(0,2).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlTransSrc.add(scPnl);
		GridBagLayout gblTransTgt = new GridBagLayout();
		JPanel pnlTransTgt = new JPanel(gblTransTgt);
			lbl = new JLabel(_it.getString("fFirst.Label.Trans.Filter_Tgt"));
			gblTransTgt.setConstraints(lbl, new GBC(0,0).setIns(2)); //.setAnchor(GBC.WEST));
			pnlTransTgt.add(lbl);
			_cboTransFilterTgt = new JComboBox<CodeText>();
			gblTransTgt.setConstraints(_cboTransFilterTgt, new GBC(0,1).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlTransTgt.add(_cboTransFilterTgt);
			_txtTransTgt = new JTextArea();
			scPnl = new JScrollPane(_txtTransTgt);
			gblTransTgt.setConstraints(scPnl, new GBC(0,2).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlTransTgt.add(scPnl);
		_splHPnlTrans = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlTransSrc, pnlTransTgt);
		_tp.addTab(_it.getString("fFirst.TabbedPane.Translate"), _splHPnlTrans);
		///
		/// Tab Words
		// 
		GridBagLayout gblWrd = new GridBagLayout();
		JPanel pnlWrd = new JPanel(gblWrd);
			lbl = new JLabel(_it.getString("fFirst.Label.Dic.Filter"));
			gblWrd.setConstraints(lbl, new GBC(0,0).setIns(2)); //.setAnchor(GBC.WEST));
			pnlWrd.add(lbl);
			_cboDicFilter = new JComboBox<CodeText>();
			gblWrd.setConstraints(_cboDicFilter, new GBC(1,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlWrd.add(_cboDicFilter);
			_tabWrd = new JTable();
			//_tabWrd.setEnabled(false);
			JScrollPane scpWrd = new JScrollPane(_tabWrd);
			scpWrd.setBorder(BorderFactory.createTitledBorder(_it.getString("fFirst.TitledBorder.Result")));
			gblWrd.setConstraints(scpWrd, new GBC(0,1).setGridSpan(2, 1).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlWrd.add(scpWrd);
		_tp.addTab(_it.getString("fFirst.TabbedPane.Words"), pnlWrd);
		///
		/// Tab Dictionary
		// 
		GridBagLayout gblDic = new GridBagLayout();
		JPanel pnlDic = new JPanel(gblDic);
			_tabDic = new JTable();
			JScrollPane scpDic = new JScrollPane(_tabDic);
			scpDic.setBorder(BorderFactory.createTitledBorder(_it.getString("fFirst.TitledBorder.Result")));
			gblDic.setConstraints(scpDic, new GBC(0,0).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlDic.add(scpDic);
		_tp.addTab(_it.getString("fFirst.TabbedPane.Dictionary"), pnlDic);
		///
		/// Tab Language
		// 
		GridBagLayout gblLng = new GridBagLayout();
		JPanel pnlLng = new JPanel(gblLng);
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

		_cboTransFilterSrc.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				_doResultTransSrc();
				_txtTransTgt.setText(CC.STR_EMPTY);
			}
		});

		_cboTransFilterTgt.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_txtTransTgt.setText(CC.STR_EMPTY);
				_doResultTransTgt();
			}
		});
		
		_cboDicFilter.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				_doResultWordsOfDic();
			}
		});
		
		_lstTransSrc.addListSelectionListener(new ListSelectionListener() 
		{
			@Override
			public void valueChanged(ListSelectionEvent arg0) 
			{
				_doResultTransTgt();
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
				actFilterApply.actionPerformed(null);
//				if (_tp.getSelectedIndex() == 0)
//				{
//					_doResultTrans();
//				}
//				else if (_tp.getSelectedIndex() == 1)
//				{
//					_doResultWordsOfDic();
//				}
//				else if (_tp.getSelectedIndex() == 2)
//				{
//					_doResultDictionaries();
//				}
			}
		});
		
	}

	private void _load()
	{
		_it.FillCbo(_cboTransFilterSrc, "WHERE dicType=1");
		_it.FillCbo(_cboTransFilterTgt);
		_it.FillCbo(_cboDicFilter);
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
			if (_tp.getTitleAt(_tp.getSelectedIndex()).equals(_it.getString("fFirst.TabbedPane.Translate")))
			{
				_doResultTransSrc();
			}
			if (_tp.getTitleAt(_tp.getSelectedIndex()).equals(_it.getString("fFirst.TabbedPane.Words")))
			{
				_doResultWordsOfDic();
			}
			if (_tp.getTitleAt(_tp.getSelectedIndex()).equals(_it.getString("fFirst.TabbedPane.Dictionary")))
			{
				_doResultDictionaries();
			}
		}
	};

	Action actAddTrans = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_tp.getSelectedIndex() == 0)
			{
				fWord2Words dlg = new fWord2Words(_it, ((CodeText)_cboTransFilterSrc.getSelectedItem()).getCode(), ((CodeText)_cboTransFilterTgt.getSelectedItem()).getCode());
				dlg.setVisible(true);
			}
			else if (_tp.getSelectedIndex() == 1)
			{
				fWord dlg = new fWord(_it);
				int selDic = ((CodeText)_cboDicFilter.getSelectedItem()).getCode();
				dlg.SetSelectedDictionary(selDic);
				dlg.setVisible(true);
				if (dlg.isSavedNewWord())
				{
					_doResultWordsOfDic();
				}
			}
		}
	};
	
	Action actEditTrans = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_tp.getSelectedIndex() == 0)
			{
			}
			else if (_tp.getSelectedIndex() == 1)
			{
				fWord dlg = new fWord(_it);
				int selDic = ((CodeText)_cboDicFilter.getSelectedItem()).getCode();
				dlg.SetSelectedDictionary(selDic);
				int wrdId = 0;
				try { wrdId =  Integer.parseInt(_tabWrd.getModel().getValueAt(_tabWrd.getSelectedRow(), 0).toString()); }
				catch(Exception ex) {}
				if (wrdId > 0)
					dlg.Load(wrdId);
				dlg.setVisible(true);
				if (dlg.isSavedNewWord())
				{
					_doResultWordsOfDic();
				}
			}
		}
	};
	
	Action actRemoveTrans = new AbstractAction() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_tp.getSelectedIndex() == 0)
			{
			}
			else if (_tp.getSelectedIndex() == 1)
			{
				int wrdId = 0;
				try { wrdId =  Integer.parseInt(_tabWrd.getModel().getValueAt(_tabWrd.getSelectedRow(), 0).toString()); }
				catch(Exception ex) {}
				if (wrdId > 0)
				{
					String wrd = _tabWrd.getModel().getValueAt(_tabWrd.getSelectedRow(), 1).toString();
					int response = JOptionPane.showConfirmDialog(fFirst.this, String.format(_it.getString("Text.Question.Delete.Word"), wrd));
					if (response == JOptionPane.YES_OPTION)
					{
						Statement sqlCmd = null;
						try
						{
							Statement stm = _it.get_wdb().getConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stm.executeUpdate(String.format(_it.getSQL("Delete.Word"), wrdId));
							_doResultWordsOfDic();
						}
						catch (Exception ex)
						{
							errorNewLine(ex.getMessage());
						}
						finally
						{
							try
							{
								if (sqlCmd != null)
									sqlCmd.close();
							}
							catch (Exception ex){}
						}	
					}
				}
			}
		}
	};
	

	private void _doResultTransSrc()
	{
		Statement sqlCmd = null;
		ResultSet rs = null;
		try
		{
			Statement stm = _it.get_wdb().getConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			//sqlCmd = _it.get_wdb().getConn().createStatement();
			int srcCode = ((CodeText)_cboTransFilterSrc.getSelectedItem()).getCode();
			//int tgtCode = ((CodeText)_cboTransFilterTgt.getSelectedItem()).getCode();
			//String strSelect = String.format(_it.getSQL("Select.WordTrans2Dic"), srcCode, tgtCode);
			String strSelect = String.format(_it.getSQL("Select.WordTransSrc"), srcCode);
			rs = stm.executeQuery(strSelect);
			_lstModel.clear();
			while (rs.next())
			{
				_lstModel.addElement(new CodeText(rs.getInt(1), rs.getString(2)));
			}
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

	private void _doResultWordsOfDic()
	{
		Statement sqlCmd = null;
		ResultSet rs = null;
		try
		{
			Statement stm = _it.get_wdb().getConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int dicCode = ((CodeText)_cboDicFilter.getSelectedItem()).getCode();
			String strSelect = String.format(_it.getSQL("Select.WordsOfDic"), dicCode);
			rs = stm.executeQuery(strSelect);
			_fillTabRS(rs, _tabWrd);
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

	
	private void _doResultTransTgt()
	{
		Statement sqlCmd = null;
		ResultSet rs = null;
		try
		{
			Statement stm = _it.get_wdb().getConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int codeSrc = _lstTransSrc.getSelectedValue().getCode();
			int codeDicTgt = ((CodeText)_cboTransFilterTgt.getSelectedItem()).getCode();
			String strSelect = String.format(_it.getSQL("Select.WordTransTgt"), codeSrc, codeDicTgt);
			rs = stm.executeQuery(strSelect);
			StringBuilder txt = new StringBuilder();
			while (rs.next())
			{
				txt.append(rs.getString(1));
				if (rs.getString(2) != null && rs.getString(2).length() > 0)
				{
					txt.append(" [");
					txt.append(rs.getString(2));
					txt.append("]");
				}
				txt.append(System.getProperty("line.separator"));
			}
			_txtTransTgt.setText(txt.toString());
			//infoNewLine(_wld.getString("Text.Message.ExecutedCommand")); 
			rs.close();
		}
		catch (Exception ex)
		{
			_txtTransTgt.setText(CC.STR_EMPTY);
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
	
	private void _doResultDictionaries()
	{
		Statement sqlCmd = null;
		ResultSet rs = null;
		try
		{
			Statement stm = _it.get_wdb().getConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stm.executeQuery(_it.getSQL("Select.Dictionaries"));
			_fillTabRS(rs, _tabDic);
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
	
	private void _fillTabRS(ResultSet aRS, JTable aJTab) throws SQLException
	{
		String[] cols = new String[aRS.getMetaData().getColumnCount()];
		for (int ii=0; ii < aRS.getMetaData().getColumnCount(); ii++)
			cols[ii] = aRS.getMetaData().getColumnLabel(ii+1);
		DefaultTableModel tm = new DefaultTableModel(cols, 0);
		while (aRS.next())
		{
			for (int ii=0; ii < cols.length; ii++)
				cols[ii] = aRS.getString(ii+1);
			tm.addRow(cols);
		}
		
		aJTab.setModel(tm);
		aJTab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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

		int ctc = node.getInt("CurrentTransDicSrc", 0);
		if (ctc > 0)
			ComboBoxTools.SetSelected2Code(_cboTransFilterSrc, ctc);

		ctc = node.getInt("CurrentTransDicTgt", 0);
		if (ctc > 0)
			ComboBoxTools.SetSelected2Code(_cboTransFilterTgt, ctc);

		ctc = node.getInt("CurrentDicFilter", 0);
		if (ctc > 0)
			ComboBoxTools.SetSelected2Code(_cboDicFilter, ctc);
	
	}
	
	private void SaveProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(inTransitu.PREFERENCE_PATH+"/fFirst" );
		
		AsRegister.SaveFrameStateSizeLocation(node, this);
		
		node.putInt("SplitDividerLocation", _splVPanel.getDividerLocation());
		CodeText ct = (CodeText)_cboTransFilterSrc.getSelectedItem();
		if (ct != null && ct.getCode() > 0)
			node.putInt("CurrentTransDicSrc", ct.getCode());
		ct = (CodeText)_cboTransFilterTgt.getSelectedItem();
		if (ct != null && ct.getCode() > 0)
			node.putInt("CurrentTransDicTgt", ct.getCode());
		ct = (CodeText)_cboDicFilter.getSelectedItem();
		if (ct != null && ct.getCode() > 0)
			node.putInt("CurrentDicFilter", ct.getCode());
		
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
