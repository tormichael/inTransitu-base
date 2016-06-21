package tor.java.inTransitu;


import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class tmResult  extends AbstractTableModel 
{
	public class ResCol
	{
		public String Src;
		public String Tgt;
		public String Trans;
	};
	
	//private ArrayList<ResCol> _rsData;
	private ResultSet _rsData;
	
	
	
	public tmResult(ResultSet aRSData) //, ResultSet aRSCol)
	{
		_rsData = aRSData;
		//_rsCol = aRSCol;
	}

//	public ArrayList<ResCol> get_rsData() {
//		return _rsData;
//	}
//
//	public void set_rsData(ArrayList<ResCol> _rsData) {
//		this._rsData = _rsData;
//	}
//
//	public tmResult(ArrayList<ResCol> _rsData) {
//		super();
//		this._rsData = _rsData;
//	}

	@Override
	public int getRowCount() 
	{
		int ret = 0;
		if (_rsData != null)
			try	
			{
				_rsData.last();
				ret =_rsData.getRow(); 
			}
			catch (Exception ex) {}
		return ret;
	}

	@Override
	public int getColumnCount() 
	{
		int ret = 0;
		if (_rsData != null)
			try	{ ret =_rsData.getMetaData().getColumnCount(); }
			catch (Exception ex) {}
		return ret;
	}

	@Override
	public String getColumnName(int column) 
	{
		String ret = super.getColumnName(column);
		if (_rsData != null)
			try	{ ret = _rsData.getMetaData().getColumnLabel(column+1); }
			catch (Exception ex){ }
		return ret;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		Object ret = null;
		if (_rsData != null)
			try	
			{ 
				if (_rsData.absolute(rowIndex+1))
				{
					ret = _rsData.getObject(columnIndex+1);
				}
				
			}
			catch (Exception ex) {}
		
		return ret;
	}

}
