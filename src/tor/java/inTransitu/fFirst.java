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
	private JTable				_tabDBMResult;
	private JTextArea 			_txtStatus;
	//private JTextField			_txtDBMCondition;
	private JButton				_cmdDBMExecute;
	//private JLabel				_lblDBMCondition;
	private JScrollPane 		_pnlDBMResult;
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

	public fFirst(inTransitu aIT)
	{
		_it = aIT;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(_it.getString("Titles.fFirst"));
		this.setIconImage(_it.getImage("inTransitu.png"));
		
		JToolBar bar = new JToolBar();
		add(bar, BorderLayout.NORTH);
		//actLoadCSVDef.putValue(Action.SMALL_ICON, _wld.getImageIcon("open.png"));
		//bar.add(actLoadCSVDef);
		//actSaveCSVDef.putValue(Action.SMALL_ICON, _wld.getImageIcon("save.png"));
		//bar.add(actSaveCSVDef);
		//actSaveAsCSVDef.putValue(Action.SMALL_ICON, _wld.getImageIcon("save-as.png"));
		//bar.add(actSaveAsCSVDef);
		actSetDBConnection.putValue(Action.SMALL_ICON, _it.getImageIcon("dbconnection.png"));
		bar.add(actSetDBConnection);
		bar.addSeparator();

		_tp = new JTabbedPane();
		//
		//  TAB DB manager:
		//

		GridBagLayout gblDBMan = new GridBagLayout();
		JPanel pnlDBMan = new JPanel(gblDBMan);
			JLabel lbl = new JLabel(_it.getString("Label.fFirst.DBM.Command"));
			gblDBMan.setConstraints(lbl, new GBC(0,0).setIns(2).setAnchor(GBC.WEST));
			pnlDBMan.add(lbl);
			_cboFilterSrc = new JComboBox<CodeText>();
			gblDBMan.setConstraints(_cboFilterSrc, new GBC(1,0).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
			pnlDBMan.add(_cboFilterSrc);
			_cmdDBMExecute = new JButton(actDBMExecute);
			_cmdDBMExecute.setText(_it.getString("Button.fFirst.DBM.Execute"));
			gblDBMan.setConstraints(_cmdDBMExecute, new GBC(2,0).setIns(2));
			pnlDBMan.add(_cmdDBMExecute);
//			_lblDBMCondition = new JLabel(_it.getString("Label.fFirst.DBM.Condition"));
//			gblDBMan.setConstraints(_lblDBMCondition, new GBC(0,1).setIns(2).setAnchor(GBC.WEST));
//			pnlDBMan.add(_lblDBMCondition);
//			_txtDBMCondition = new JTextField();
//			gblDBMan.setConstraints(_txtDBMCondition, new GBC(1,1).setGridSpan(2, 1).setIns(2).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
//			pnlDBMan.add(_txtDBMCondition);
			_tabDBMResult = new JTable();
			_pnlDBMResult = new JScrollPane(_tabDBMResult);
			_pnlDBMResult.setBorder(BorderFactory.createTitledBorder(_it.getString("TitledBorder.fFirst.DBM.Result")));
			gblDBMan.setConstraints(_pnlDBMResult, new GBC(0,2).setGridSpan(3, 1).setIns(2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
			pnlDBMan.add(_pnlDBMResult);
		//_splvDBMan = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _treeDB, pnlDBMan);
		//_tp.addTab(_it.getString("TabbedPane.fFirst.DBMan"), _splvDBMan);


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

		_cboFilterSrc.addItem(new CodeText(1, _it.getString("Text.DBM.Command.SELECT")));
		_cboFilterSrc.addItem(new CodeText(2, _it.getString("Text.DBM.Command.DeleteRows")));
		_cboFilterSrc.addItem(new CodeText(3, _it.getString("Text.DBM.Command.CreateTable")));
		_cboFilterSrc.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (((CodeText)_cboFilterSrc.getSelectedItem()).getCode() == 3)
				{
					//_lblDBMCondition.setText(_it.getString("Label.fCSV.DBM.TableName"));
					_pnlDBMResult.setBorder(BorderFactory.createTitledBorder(_it.getString("TitledBorder.fCSV.DBM.ColDef")));
					//_tabDBMResult.setModel(new tmDBMColDef(_wld));
					_tabDBMResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				}
				else
				{
					//_lblDBMCondition.setText(_it.getString("Label.fFirst.DBM.Condition"));
					_pnlDBMResult.setBorder(BorderFactory.createTitledBorder(_it.getString("TitledBorder.fCSV.DBM.Result")));
					//_tabDBMResult.setModel(new tmDBMResult(null));
					_tabDBMResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
				//_showCurrentConnectionURL();
				//_showDBManager();
			}
		}
	};
	
	Action actDBMExecute = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
	};

	private void _execCommandCreateTable()
	{
		try
		{
//			String tabName = _treeDB.getSelectionPath().getLastPathComponent().toString(); 
//			String strSelect = CC.STR_EMPTY;
//			
//			strSelect = String.format(_wld.getSQL("Command.Delete"), tabName, 
//				_txtDBMCondition.getText().length() > 0 ? _txtDBMCondition.getText() : "1=1");
//	
//			Statement stm = _wld.get_wdb().getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			infoNewLine(String.format(_wld.getString("Text.Message.ExecutingCommand"), strSelect)); 
//			ResultSet rs = stm.executeQuery(strSelect);
//			TableModel tm = _tabDBMResult.getModel();
//			if (tm != null)
//				tm = null;
//			_tabDBMResult.setModel(null);
//			_tabDBMResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//			infoNewLine(_wld.getString("Text.Message.ExecutedCommand"));
			infoNewLine("DO IT LATTER !!!!!!!");
		}
		catch (Exception ex)
		{
			infoNewLine(ex.getMessage());
		}
	}

	
 	private void LoadProgramPreference()
	{
		_it.get_wdb().LoadDBConnectioParam2Reg(inTransitu.PREFERENCE_PATH);

		Preferences node = Preferences.userRoot().node(inTransitu.PREFERENCE_PATH+"/CSV" );
		AsRegister.LoadFrameStateSizeLocation(node, this);
		_splVPanel.setDividerLocation(node.getInt("SplitDividerLocation", 100));
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
		Preferences node = Preferences.userRoot().node(inTransitu.PREFERENCE_PATH+"/CSV" );
		
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
