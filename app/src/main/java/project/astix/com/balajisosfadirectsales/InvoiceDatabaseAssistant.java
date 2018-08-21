package project.astix.com.balajisosfadirectsales;


//import java.io.BufferedOutputStream;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


//import android.content.Context;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
//import android.provider.SyncStateContract.Constants;
import android.util.Log;

import com.astix.Common.CommonInfo;

public class InvoiceDatabaseAssistant 
  {
	// private DatabaseHelper2 DBHelper2;
	private static final String DATASUBDIRECTORY = CommonInfo.InvoiceXMLFolder;
	public static final String LOG_TAG = "NMPsync";
	private DatabaseHelper2 DBHelper2;
	private SQLiteDatabase db; // removed final
	private XmlBuilder xmlBuilder;
	static String DATABASE_NAME_2SYNC = CommonInfo.DATABASE_NAME;
	 static  int DATABASE_VERSION = CommonInfo.DATABASE_VERSIONID;
	 public Context context;
	

	/*
	 * public DatabaseAssistant(final SQLiteDatabase db) { this.db = db;
	 * //DBHelper2 = new DatabaseHelper2(context); }
	 */

	public InvoiceDatabaseAssistant(Context ctx) {
		this.context = ctx;
		DBHelper2 = new DatabaseHelper2(context);
	}

	private static class DatabaseHelper2 extends SQLiteOpenHelper {
		DatabaseHelper2(Context context) {
			super(context, DATABASE_NAME_2SYNC, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}

	// ---opens the database---
	public InvoiceDatabaseAssistant open() throws SQLException {
		db = DBHelper2.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper2.close();
	}

	public void export(final String dbName, final String exportFileNamePrefix,int comFrom,String[] OrderListing)
			throws IOException {
		
	/*	System.out.println("Mobile called expoert with value :"+"exporting database - " + dbName
				+ " exportFileNamePrefix=" + exportFileNamePrefix);
		Log.i(LOG_TAG, "exporting database - " + dbName
				+ " exportFileNamePrefix=" + exportFileNamePrefix);
		System.out.println("abhinav raj File Name Called 2:"+exportFileNamePrefix);*/
		xmlBuilder = new XmlBuilder();
		xmlBuilder.start(dbName);

		// get the tables
		String sql = "select * from sqlite_master";
		System.out.println("db print inside DA: " + db);
		Cursor c = db.rawQuery(sql, new String[0]);
		
		if (1==1) {			//reqd. if.. block
			
			if(comFrom==7)
			{
				System.out.println("Mobile cancel callrd");
			exportTableCancel("tblInvoiceButtonTransac");
			//db.execSQL("Update tblInvoiceButtonTransac Set Sstat=4 where Sstat=7");
			//db.execSQL("Update tblInvoiceButtonStoreMstr Set Sstat=4 where Sstat=7");
			//tblInvoiceButtonStoreMstr
			
			}
			else if(comFrom==9)
			{
			System.out.println("Mobile confrm callrd");
			exportTableConformInvoice("tblInvoiceButtonTransac",OrderListing);
			
			//db.execSQL("Update tblInvoiceButtonTransac Set Sstat=4 where Sstat=9");
			//db.execSQL("Update tblInvoiceButtonStoreMstr Set Sstat=4 where Sstat=9");
			}
			else
			{
				//exportTable("tblInvoiceButtonStoreMstr");
				exportTable("tblInvoiceButtonTransac");
			}
			
			
			
		}
		String xmlString = xmlBuilder.end();
		writeToFile(xmlString, exportFileNamePrefix + ".xml");
		Log.i(LOG_TAG, "exporting database complete");
	}
	/*private static final String DATABASE_CREATE_TABLE_111 = "create table tblInvoiceButtonStoreMstr(StoreID text null," +
			"StoreName text null,RouteID text null,RouteName text null,DistID text null,DistName text null," +
			"InvoiceForDate text null,flgSubmit text null);";*/
	
	
private void exportTableConformInvoice(final String tableName,String[] OrderListing) throws IOException {
	
	String totalSendString="";

	int checkOrderID=0;

	int  oldOrderId=0;
	int  newOrderId=0;
	int newRow=0;
	String TransDate="";
	String additionalDiscount="";

	String cStoreID="";
	String CommonData="0";
		 
	xmlBuilder.openTable(tableName);
	for(int j=0;j<OrderListing.length;j++)
	{
		//xmlBuilder.openRow();
		totalSendString="";
		
		String sql = "select IMEIno,StoreID,TransDate,OrderID,ProdID,ProductPrice,DelQty,FreeQty,DiscountVal,additionalDiscount,flgCancel from " + tableName + " where OrderID='"+OrderListing[j]+"' and Sstat=9";		// chk for flag - DB adapter
		System.out.println("Invoice Submit Order ID :"+OrderListing[j]);
		Cursor c = db.rawQuery(sql, new String[0]);
	
		
		
		int canflag=0;
		if (c.moveToFirst()) 
		{
			for (int i = 0; i < c.getCount(); i++)
			{
				
				canflag=Integer.parseInt(c.getString(10));
				TransDate=c.getString(2);
				additionalDiscount=c.getString(9);
				
				if(canflag==0)
				{
					if(Integer.parseInt(c.getString(6))==0 && Integer.parseInt(c.getString(7))==0 && (Double.parseDouble(c.getString(8))==0.0 || Double.parseDouble(c.getString(8))==0))
					{
						/*if(totalSendString.equals(""))
						{
						totalSendString="";
						}*/
					}
					else
					{
						if(totalSendString.equals(""))
						{
							totalSendString=c.getString(4)+"^"+c.getString(5)+"^"+c.getString(6)+"^"+c.getString(7)+"^"+c.getString(8);
						}
						else
						{
						totalSendString+="^|"+c.getString(4)+"^"+c.getString(5)+"^"+c.getString(6)+"^"+c.getString(7)+"^"+c.getString(8);
						}
					}
				}
				else
				{
					totalSendString="";
				}
				
				if(i==(c.getCount()-1))
				{
					
						
					xmlBuilder.openRow();
					//xmlBuilder.addColumn(c.getColumnName(1), c.getString(1));
					xmlBuilder.addColumn(c.getColumnName(2), TransDate);
					xmlBuilder.addColumn(c.getColumnName(3), ""+OrderListing[j]);
					if(!totalSendString.equals("") && !totalSendString.equals("NA"))
					{
						totalSendString+="^|";
					}
					if(totalSendString.equals(""))
					{
						totalSendString="NA";
					}
					xmlBuilder.addColumn("strData", totalSendString);
					xmlBuilder.addColumn("additionalDiscount", additionalDiscount);
					xmlBuilder.addColumn("flgCancel", ""+canflag);
					xmlBuilder.closeRow();	
					
				
					
				}
				c.moveToNext();
			}
		
		}
		//xmlBuilder.closeRow();	
		c.close();
	}
	
	xmlBuilder.closeTable();
	
}
	
	
	
	private void exportTableCancel(final String tableName) throws IOException {
		
		
		String totalSendString="";
		
		 String CommonData="0";
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select IMEIno,StoreID,TransDate,OrderID,flgCancel from " + tableName + " where Sstat = 7";		// chk for flag - DB adapter
		//db.execSQL("Update tblInvoiceButtonTransac Set Sstat=4 where Sstat=7");
		
		
		Cursor c = db.rawQuery(sql, new String[0]);
		//System.out.println("Anil Jai Testing sql :"+sql);
		//System.out.println("Anil Jai Testing c.getCount() :"+c.getCount());
		int noRecords=c.getCount();
		int totalRecords=0;
		int checkFirstTime=0;
		
		if (c.moveToFirst()) 
		{
			int cols = c.getColumnCount();
			//System.out.println("Sunil Jai Testing c.getColumnCount() :"+c.getColumnCount());
			//System.out.println("Sunil Jai Testing totalSendString :"+totalSendString);
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++)
				{
					
					if(checkFirstTime==0)
					{
						CommonData=c.getString(0)+"^"+c.getString(1)+"^"+c.getString(2)+"^"+c.getString(3);
						//totalSendString=CommonData;
						checkFirstTime=checkFirstTime+1;
						//totalSendString=c.getString(4)+"^"+c.getString(5)+"^"+c.getString(6)+"^"+c.getString(7);
					}
					else 
					{
						if(totalSendString.equals(""))
						{
							
						}
						if(i==7)
						{
						//totalSendString=totalSendString+""+c.getString(4)+"^"+c.getString(5)+"^"+c.getString(6)+"^"+c.getString(7)+"^"+c.getString(8);
						
						}
						
						
						
					}
				
				}
				if(noRecords>0)
				{
					
					totalSendString=totalSendString+"^|";
				}
				noRecords=noRecords-1;
				xmlBuilder.addColumn(c.getColumnName(2), c.getString(2));
				xmlBuilder.addColumn(c.getColumnName(3), c.getString(3));
				xmlBuilder.addColumn("strData", "NA");
				xmlBuilder.addColumn("additionalDiscount", "0");
				xmlBuilder.addColumn("flgCancel", c.getString(4));
				
				
				
				xmlBuilder.closeRow();
				
				if(totalRecords==c.getCount())
				{
					
					xmlBuilder.addColumn(c.getColumnName(1), c.getString(1));
					
				}
				totalRecords=totalRecords+1;
				
				
			//	System.out.println("Sunil Jai Testing noRecords totalSendString :"+totalSendString);
			//	System.out.println("Sunil Jai Testing noRecords check :"+noRecords);
				
			} while (c.moveToNext());
			
			
			
			
		}
		
		
		c.close();
		xmlBuilder.closeTable();
	}
	
	
	private void exportTableDayStartEndDetails(final String tableName) throws IOException 
	{
		xmlBuilder.openTable(tableName);
		String sql = "select * from " + tableName + " where (DayEndFlag=1 OR ChangeRouteFlg=1)";		// chk for flag - DB adapter
		Cursor c = db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	
	/*private static final String DATABASE_CREATE_TABLE_114 = "create table tblInvoiceButtonTransac (IMEIno text not null, " +
			"TransDate string not null, StoreID text not null, ProdID text not null, OrderQty integer not null, " +
			"DelQty integer not null,FreeQty integer not null,Sstat integer not null,ProductShortName text null, ProductPrice real null," +
			"RouteID int null,OrderID text  null,CatID text null);";*/
	//PrdID^Rate^DelQty^FreeQty
	/*IMEI
	StoreID
	Date
	OrderID
	strData*/
	
	private void exportTable(final String tableName) throws IOException {
		
	 
		String totalSendString="";
		
		int checkOrderID=0;
		
		int  oldOrderId=0;
		int  newOrderId=0;
		int newRow=0;
		
		 String CommonData="0";
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select IMEIno,StoreID,TransDate,OrderID,ProdID,ProductPrice,DelQty,FreeQty,DiscountVal,additionalDiscount,flgCancel from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = db.rawQuery(sql, new String[0]);
		db.execSQL("Update tblInvoiceButtonTransac Set Sstat=4 where Sstat=4");
		//System.out.println("Jai Surya Testing sql :"+sql);
		//System.out.println("Jai Surya Testing c.getCount() :"+c.getCount());
		int noRecords=c.getCount();
		int totalRecords=0;
		int checkFirstTime=0;
		
		if (c.moveToFirst()) 
		{
			int cols = c.getColumnCount();
			//System.out.println("Jai Surya Testing c.getColumnCount() :"+c.getColumnCount());
			//System.out.println("Jai Surya Testing totalSendString if :"+totalSendString);
			do {
				
				//System.out.println("Jai Surya Testing OrderID DatabaseAssistant :"+c.getString(3));
				
				newOrderId=Integer.parseInt(c.getString(3));
				
				for (int i = 0; i < cols; i++)
				{
					//System.out.println("Jai Surya Testing c.getColumnCount() :"+c.getColumnCount());
					if(checkFirstTime==0)
					{
						CommonData=c.getString(0)+"^"+c.getString(1)+"^"+c.getString(2)+"^"+c.getString(3);
						checkFirstTime=checkFirstTime+1;
						//totalSendString=c.getString(4)+"^"+c.getString(5)+"^"+c.getString(6)+"^"+c.getString(7);
					}
					else 
					{
						if(totalSendString.equals(""))
						{
							
						}
						if(i==9 )
						{
						
						if(oldOrderId!=0 && newOrderId!=oldOrderId)
						{
							xmlBuilder.openRow();
							//xmlBuilder.addColumn(c.getColumnName(1), c.getString(1));
							xmlBuilder.addColumn(c.getColumnName(2), c.getString(2));
							xmlBuilder.addColumn(c.getColumnName(3), ""+oldOrderId);
							xmlBuilder.addColumn("strData", totalSendString);
							xmlBuilder.addColumn("additionalDiscount", c.getString(9));
							xmlBuilder.addColumn("flgCancel", c.getString(10));
							xmlBuilder.closeRow();	
							totalSendString="";
						}
						
						totalSendString=totalSendString+""+c.getString(4)+"^"+c.getString(5)+"^"+c.getString(6)+"^"+c.getString(7)+"^"+c.getString(8);
						
						}
						
					}
				}
				oldOrderId=Integer.parseInt(c.getString(3));
				
				checkOrderID=Integer.parseInt(c.getString(3));
				if(noRecords>0)
				{
					totalSendString=totalSendString+"^|";
				}
				noRecords=noRecords-1;
				
				totalRecords=totalRecords+1;
				
				if(newRow==1)
				{
					
				}
				
				if(totalRecords==c.getCount())
				{
					xmlBuilder.openRow();
					//xmlBuilder.addColumn(c.getColumnName(1), c.getString(1));
					xmlBuilder.addColumn(c.getColumnName(2), c.getString(2));
					xmlBuilder.addColumn(c.getColumnName(3), ""+oldOrderId);
					xmlBuilder.addColumn("strData", totalSendString);
					xmlBuilder.addColumn("additionalDiscount", c.getString(9));
					xmlBuilder.addColumn("flgCancel", c.getString(10));
					xmlBuilder.closeRow();	
					
				}
				
				
				
				//System.out.println("Sunil Jai Testing noRecords totalSendString :"+totalSendString);
				//System.out.println("Sunil Jai Testing noRecords check :"+noRecords);
				
			} while (c.moveToNext());
			
			
			
			
		}
		
		
		c.close();
		xmlBuilder.closeTable();
	}
	
	private void exportTableStoreList(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;	
		// text null,  text null, 
		//StoreID text not null, StoreType string not null, StoreName string not null, StoreLatitude real not null, StoreLongitude real not null, LastVisitDate string not null, LastTransactionDate string not null, Sstat integer not null, ForDate string not null, ActualLatitude real null, ActualLongitude real null, VisitStartTS text null, VisitEndTS text null);";
		String sql = "select StoreID, StoreLatitude, StoreLongitude, ActualLatitude, ActualLongitude, VisitStartTS, VisitEndTS, ISNewStore, LocProvider, Accuracy, BateryLeftStatus, Sstat, StoreClose, StoreNextDay,StoreRouteID AS "+"RouteID"+" from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	private void exportTableNewStoreListEntries(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;	
		//StoreID text not null, StoreType string not null, StoreName string not null, StoreLatitude real not null, StoreLongitude real not null, LastVisitDate string not null, LastTransactionDate string not null, Sstat integer not null, ForDate string not null, ActualLatitude real null, ActualLongitude real null, VisitStartTS text null, VisitEndTS text null);";
		String sql = "select * from " + tableName + " where Sstat = 3"; ;		// chk for flag - DB adapter
		Cursor c = db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}

	private void writeToFile(final String xmlString, final String exportFileName)
			throws IOException {
		File dir = new File(Environment.getExternalStorageDirectory(),
				InvoiceDatabaseAssistant.DATASUBDIRECTORY);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, exportFileName);
		file.createNewFile();

		ByteBuffer buff = ByteBuffer.wrap(xmlString.getBytes());
		FileChannel channel = new FileOutputStream(file).getChannel();
		try {
			channel.write(buff);
		} finally {
			if (channel != null) {
				channel.close();
			}
		}
	}

	/**
	 * XmlBuilder is used to write XML tags (open and close, and a few
	 * attributes) to a StringBuilder. Here we have nothing to do with IO or
	 * SQL, just a StringBuilder.
	 * 
	 * 
	 * 
	 */
	static class XmlBuilder {
		private static final String OPEN_XML_STANZA = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		private static final String CLOSE_WITH_TICK = "'>";
		private static final String DB_OPEN = "<database name='";
		private static final String DB_CLOSE = "</database>";
		private static final String TABLE_OPEN = "<table name='";
		private static final String TABLE_CLOSE = "</table>";
		private static final String ROW_OPEN = "<row>";
		private static final String ROW_CLOSE = "</row>";
		private static final String COL_OPEN = "<col name='";
		private static final String COL_CLOSE = "</col>";

		private final StringBuilder sb;

		public XmlBuilder() throws IOException {
			sb = new StringBuilder();
		}

		void start(final String dbName) {
			//sb.append(XmlBuilder.OPEN_XML_STANZA);
			sb.append(XmlBuilder.DB_OPEN + dbName + XmlBuilder.CLOSE_WITH_TICK);
		}

		String end() throws IOException {
			sb.append(XmlBuilder.DB_CLOSE);
			return sb.toString();
		}

		void openTable(final String tableName) {
			sb.append(XmlBuilder.TABLE_OPEN + tableName
					+ XmlBuilder.CLOSE_WITH_TICK);
		}

		void closeTable() {
			sb.append(XmlBuilder.TABLE_CLOSE);
		}

		void openRow() {
			sb.append(XmlBuilder.ROW_OPEN);
		}

		void closeRow() {
			sb.append(XmlBuilder.ROW_CLOSE);
		}

		void addColumn(final String name, final String val) throws IOException {
			sb.append(XmlBuilder.COL_OPEN + name + XmlBuilder.CLOSE_WITH_TICK
					+ val + XmlBuilder.COL_CLOSE);
		}
	}

	/*
	 * public String filNameFullPath; public long filNameTS; public String
	 * EXPORT_FILE_NAME; //private static final String EXPORT_FILE_NAME =
	 * "/sdcard/datanaexport.xml";
	 * 
	 * private Context _ctx; private SQLiteDatabase _db; private Exporter
	 * _exporter;
	 * 
	 * public String newfilename(){ System.out.println("inside newfilename()");
	 * 
	 * filNameTS = System.currentTimeMillis(); filNameFullPath =
	 * Environment.getExternalStorageDirectory().getPath(); EXPORT_FILE_NAME =
	 * filNameFullPath + "/" + filNameTS +".xml";
	 * System.out.println("new file name: " + EXPORT_FILE_NAME); return
	 * EXPORT_FILE_NAME; }
	 * 
	 * public DatabaseAssistant( Context ctx, SQLiteDatabase db ) { _ctx = ctx;
	 * _db = db;
	 * 
	 * newfilename(); try {
	 * System.out.println("inside try databaseAssitant() -- file name: " +
	 * EXPORT_FILE_NAME); // create a file on the sdcard to export the //
	 * database contents to File myFile = new File( EXPORT_FILE_NAME );
	 * myFile.createNewFile();
	 * 
	 * FileOutputStream fOut = new FileOutputStream(myFile);
	 * BufferedOutputStream bos = new BufferedOutputStream( fOut );
	 * 
	 * _exporter = new Exporter( bos ); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); } }
	 * 
	 * public void exportData( ) { log( "Exporting Data" );
	 * 
	 * try { System.out.println("_exporter: " + _exporter);
	 * System.out.println("_db: " + _db);
	 * System.out.println("inside try exportData()"); _exporter.startDbExport(
	 * _db.getPath() );
	 * 
	 * // get the tables out of the given sqlite database String sql =
	 * "SELECT * FROM sqlite_master";
	 * 
	 * Cursor cur = _db.rawQuery( sql, new String[0] ); Log.d("db",
	 * "show tables, cur size " + cur.getCount() ); cur.moveToFirst();
	 * 
	 * String tableName; while ( cur.getPosition() < cur.getCount() ) {
	 * tableName = cur.getString( cur.getColumnIndex( "name" ) ); log(
	 * "table name " + tableName );
	 * 
	 * // don't process these two tables since they are used // for metadata if
	 * ( ! tableName.equals( "android_metadata" ) && ! tableName.equals(
	 * "sqlite_sequence" ) ) { exportTable( tableName ); }
	 * 
	 * cur.moveToNext(); } _exporter.endDbExport(); _exporter.close(); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 * 
	 * private void exportTable( String tableName ) throws IOException {
	 * _exporter.startTable(tableName);
	 * 
	 * // get everything from the table String sql = "select * from " +
	 * tableName; Cursor cur = _db.rawQuery( sql, new String[0] ); int numcols =
	 * cur.getColumnCount();
	 * 
	 * log( "Start exporting table " + tableName );
	 * 
	 * // // logging // for( int idx = 0; idx < numcols; idx++ ) // { // log(
	 * "column " + cur.getColumnName(idx) ); // }
	 * 
	 * cur.moveToFirst();
	 * 
	 * // move through the table, creating rows // and adding each column with
	 * name and value // to the row while( cur.getPosition() < cur.getCount() )
	 * { _exporter.startRow(); String name; String val; for( int idx = 0; idx <
	 * numcols; idx++ ) { name = cur.getColumnName(idx); val = cur.getString(
	 * idx ); log( "col '" + name + "' -- val '" + val + "'" );
	 * 
	 * _exporter.addColumn( name, val ); }
	 * 
	 * _exporter.endRow(); cur.moveToNext(); }
	 * 
	 * cur.close();
	 * 
	 * _exporter.endTable(); }
	 * 
	 * private void log( String msg ) { Log.d( "DatabaseAssistant", msg ); }
	 * 
	 * class Exporter { private static final String CLOSING_WITH_TICK = "'>";
	 * private static final String START_DB = "<export-database name='"; private
	 * static final String END_DB = "</export-database>"; private static final
	 * String START_TABLE = "<table name='"; private static final String
	 * END_TABLE = "</table>"; private static final String START_ROW = "<row>";
	 * private static final String END_ROW = "</row>"; private static final
	 * String START_COL = "<col name='"; private static final String END_COL =
	 * "</col>";
	 * 
	 * private BufferedOutputStream _bos;
	 * 
	 * public Exporter() throws FileNotFoundException { this( new
	 * BufferedOutputStream( _ctx.openFileOutput( EXPORT_FILE_NAME,
	 * Context.MODE_WORLD_READABLE ) ) ); }
	 * 
	 * public Exporter( BufferedOutputStream bos ) { _bos = bos; }
	 * 
	 * public void close() throws IOException { if ( _bos != null ) {
	 * _bos.close(); } }
	 * 
	 * public void startDbExport( String dbName ) throws IOException { String
	 * stg = START_DB + dbName + CLOSING_WITH_TICK; _bos.write( stg.getBytes()
	 * ); }
	 * 
	 * public void endDbExport() throws IOException { _bos.write(
	 * END_DB.getBytes() ); }
	 * 
	 * public void startTable( String tableName ) throws IOException { String
	 * stg = START_TABLE + tableName + CLOSING_WITH_TICK; _bos.write(
	 * stg.getBytes() ); }
	 * 
	 * public void endTable() throws IOException { _bos.write(
	 * END_TABLE.getBytes() ); }
	 * 
	 * public void startRow() throws IOException { _bos.write(
	 * START_ROW.getBytes() ); }
	 * 
	 * public void endRow() throws IOException { _bos.write( END_ROW.getBytes()
	 * ); }
	 * 
	 * public void addColumn( String name, String val ) throws IOException {
	 * String stg = START_COL + name + CLOSING_WITH_TICK + val + END_COL;
	 * _bos.write( stg.getBytes() ); } }
	 * 
	 * class Importer {
	 * 
	 * }
	 */

}