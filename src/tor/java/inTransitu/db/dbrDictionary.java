package tor.java.inTransitu.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import JCommonTools.CC;
import JCommonTools.DB.DBWork;
import JCommonTools.DB.dbrBase;

public class dbrDictionary extends dbrBase 
{
	public enum DicType
	{
		UNDEFINED(0),
		WORD (1),
		TEXT(2);
		
		private int _val;
		private DicType(int aVal) { this._val = aVal;} 

		public int getValue() {return this._val;}
		
		public static DicType getEnum(int aVal)
		{
			DicType ret = DicType.UNDEFINED;
			DicType[] vv = DicType.values();
			for (int ii=0; ii < vv.length; ii++)
			{
				if (vv[ii].getValue() == aVal)
				{
					ret = vv[ii];
					break;
				}
				
			}
			return ret;
		}
	};
	
	private int _idLng;
	private DicType _type;
	private String _name;
	private String _note;
	private String _codePage;
	private String _font;
	private int _fontSize;
	private int _alignment;

	public int getIdLng() {
		return _idLng;
	}

	public void setIdLng(int _idLng) {
		this._idLng = _idLng;
	}

	public DicType getType() {
		return _type;
	}

	public void setType(DicType _type) {
		this._type = _type;
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public String getNote() {
		return _note;
	}

	public void setNote(String _note) {
		this._note = _note;
	}

	public String getCodePage() {
		return _codePage;
	}

	public void setCodePage(String _codePage) {
		this._codePage = _codePage;
	}

	public String getFont() {
		return _font;
	}

	public void setFont(String _font) {
		this._font = _font;
	}

	public int getFontSize() {
		return _fontSize;
	}

	public void setFontSize(int _fontSize) {
		this._fontSize = _fontSize;
	}

	public int getAlignment() {
		return _alignment;
	}

	public void setAlignment(int _alignment) {
		this._alignment = _alignment;
	}

	public dbrDictionary(DBWork aDbWork)
	{
		super(aDbWork, "itDictionary", "dic");
	}
	
	@Override
	public void Init() 
	{
		super.Init();
		_idLng = 0;
		_type = DicType.WORD;
		_name = CC.STR_EMPTY;
		_note = null;
		_codePage = null;
		_font = null;
		_fontSize = 0;
		_alignment = 1;
	}
		
	@Override
	protected void _loadFlds(ResultSet rs) throws SQLException
	{
		_idLng =  rs.getInt(2);
		_type = DicType.getEnum(rs.getInt(3));
		_name = rs.getString(4);
		_note = rs.getString(5);
		_codePage = rs.getString(6);
		_font = rs.getString(7);
		_fontSize = rs.getInt(8);
		_alignment = rs.getInt(9);
	}
}
