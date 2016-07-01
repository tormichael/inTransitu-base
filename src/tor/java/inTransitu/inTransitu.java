package tor.java.inTransitu;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.CodeText;
import JCommonTools.DB.DBWork;


public class inTransitu 
{

	public final static String FN_RESOURCE_TEXT = "inTransitu-text";
	public final static String FN_RESOURCE_SQL = "inTransitu-sql";
	
	public final static String FD_RESOURCE_ICONS = "img/";

	public final static String PREFERENCE_PATH = "/inTransitu";
	
	//public final static String COLUMN_NAME_ROW_HASH = "id_row_hash";

	//public final static String DEF_TGT_CATALOG = "depot";
	//public final static String DEF_TGT_SCHEMA = "negative";
	//public final static String DEF_TGT_TABLE = "base";

	
	//public final static String FUNCTION_SRC_CODE = "SRC_CODE";
	//public final static String FUNCTION_NEXT_CODE = "NEXT_CODE";
	//public final static String FUNCTION_TO_DATE = "TO_DATE";
	//public final static String FUNCTION_UNION = "UNION";
	
	private ResourceBundle _bnd;
	private ResourceBundle _bndSQL;
	private DBWork _wdb;
	private AsRegister _reg;

	public ResourceBundle get_bnd() {
		return _bnd;
	}
	public ResourceBundle get_bndSQL() {
		return _bndSQL;
	}

	public String getString(String aKey)
	{
		return _bnd.getString(aKey);
	}
	public String getSQL(String aKey)
	{
		return _bndSQL.getString(aKey);
	}
	public  ImageIcon getImageIcon(String aName)
	{
		URL url = this.getClass().getClassLoader().getResource(FD_RESOURCE_ICONS+aName);
		if (url ==null)
			url = this.getClass().getResource(FD_RESOURCE_ICONS+aName);
		
		if (url != null)
		{
			ImageIcon ico = new ImageIcon(url);
			return new ImageIcon(ico.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
		}
		else
		{
			return new ImageIcon();
		}
	}

	public  Image getImage(String aName)
	{
		URL url = this.getClass().getClassLoader().getResource(FD_RESOURCE_ICONS+aName);
		if (url != null)
		{
			return Toolkit.getDefaultToolkit().getImage(url);
		}
		else
		{
			return new ImageIcon().getImage();
		}
		
		//return ImageTools.CreateIcon(aName, 24).getImage();
	}

	public DBWork get_wdb() 
	{
		return _wdb;
	}
	public void set_wdb(DBWork _wdb) 
	{
		this._wdb = _wdb;
	}

	public AsRegister get_reg() 
	{
		return _reg;
	}
	

	public inTransitu()
	{
		_bnd = ResourceBundle.getBundle(FN_RESOURCE_TEXT);
		_bndSQL = ResourceBundle.getBundle(FN_RESOURCE_SQL);
		_wdb =new DBWork();
		_reg = new AsRegister();
	}
	
	public String FillCbo(JComboBox<CodeText> aCbo)
	{
		String ret = CC.STR_EMPTY;
		Statement sqlCmd = null;
		ResultSet rs = null;
		try
		{
			sqlCmd = get_wdb().getConn().createStatement();
			rs = sqlCmd.executeQuery(getSQL("Select.DictionaryList"));
			while (rs.next())
			{
				aCbo.addItem(new CodeText(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
		}
		catch (Exception ex)
		{
			ret =ex.getMessage();
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
		
		return ret;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		fFirst frm = new fFirst(new inTransitu());
		frm.setVisible(true);
	}

}
