package project.astix.com.balajisosfadirectsales;

//import java.io.BufferedOutputStream;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;


//import android.content.Context;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
//import android.provider.SyncStateContract.Constants;

import com.astix.Common.CommonInfo;

public class DatabaseAssistant 
{
	// private DatabaseHelper2 DBHelper2;
	private static final String DATASUBDIRECTORY = CommonInfo.OrderXMLFolder;
	private DatabaseHelper2 DBHelper2;
	private SQLiteDatabase db; // removed final
	private XmlBuilder xmlBuilder;
	static String DATABASE_NAME_2SYNC = CommonInfo.DATABASE_NAME;
	 static  int DATABASE_VERSION = CommonInfo.DATABASE_VERSIONID;
	private final Context context;

	/*
	 * public DatabaseAssistant(final SQLiteDatabase db) { this.db = db;
	 * //DBHelper2 = new DatabaseHelper2(context); }
	 */

	public DatabaseAssistant(Context ctx)
	{
		this.context = ctx;
		DBHelper2 = new DatabaseHelper2(context);
	}

	private static class DatabaseHelper2 extends SQLiteOpenHelper
	{
		    DatabaseHelper2(Context context)
		    {
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
	public DatabaseAssistant open() throws SQLException 
	{
		db = DBHelper2.getWritableDatabase();

		return this;
	}

	// ---closes the database---
	public void close()
	{
		DBHelper2.close();
	}

	public void export(final String dbName, final String exportFileNamePrefix,String ActiveRouteID)
			throws IOException
			{

		//////System.out.println("abhinav raj File Name Called 2:"+exportFileNamePrefix);
		xmlBuilder = new XmlBuilder();
		xmlBuilder.start(dbName);

		// get the tables
		String sql = "select * from sqlite_master";
		//////System.out.println("db print inside DA: " + db);
		Cursor c = db.rawQuery(sql, new String[0]);


			exportTableStoreList("tblStoreList");

			exportTable("tblNewAddedStoreLocationDetails");
			exportTableStoreVisit("tblStoreVisitMstr");
			exportTabletblTransac("tblInvoiceDetails");
				exportTableStoreCheckIn("tblActualVisitStock");

			exportTabletblInvoice("tblInvoiceHeader");


			exportCollection("tblAllCollectionData");
			//exportTableProductMappedWithSchemeSlabApplied("tblProductMappedWithSchemeSlabApplied");
			exportTablStoreProductPhotoDetail("tblStoreProductPhotoDetail");
			
		//	exportTableStoreReturnDetail("tblStoreReturnDetail");
			exportTableDayStartEndDetails("tblDayStartEndDetails");
		
		//	exportTableStoreProductAppliedSchemesBenifitsRecords("tblStoreProductAppliedSchemesBenifitsRecords");
			
			exportTableStoreMain("tblNewStoreEntries");
			//exportTable("tblNewStoreListEntries");
		//	exportTableTemp("tblTemp");
			
			exportTable("tblOutletQuestAnsMstr");
		//	exportTableStoreMaterialDetail("tblStoreMaterialDetail");
			//exportTable("tblStoreMaterialPhotoDetail");
			
			exportTable("tblNoVisitStoreDetails");
			
			//exportTableForRouteType("tblRouteMstr",ActiveRouteID);
			
			//exportSalesQuotePersonMeetTable("tblSalesQuotePersonMeetMstr");
			//exportTable("tblSalesQuoteProductsMstr");
			
			//exportTable("tblNewStoreSalesQuotePaymentDetails");
			
			//exportTable("tblStoreOrderBillAddressDetails");
			
			//exportTabletblStoreSalesOrderPaymentDetails("tblStoreSalesOrderPaymentDetails");
			
			//exportTable("tblRateDistribution");
			exportTableNotification("tblNotificationMstr");

			exportTable("tblSelectedManagerDetails");

			exportTable("tableImage");
			exportTable("tblLatLongDetails");
			exportTable("tblsameLocationForStoreRestartDone");

			exportTable("tblDsrRegDetails");
			exportTable("tblStoreCloseLocationDetails");
			exportTable("tblStoreClosedPhotoDetail");
			exportTable("tblStoreCloseReasonSaving");
			exportTableAttandance("tblAttandanceDetails");
				exportTable("tblDsrLocationDetails");




			//String routeID=GetActiveRouteIDSunil();
			UpdateNewAddedStorephotoFlag();

		String xmlString = xmlBuilder.end();
		writeToFile(xmlString, exportFileNamePrefix + ".xml");

	}
	private void exportTableAttandance(final String tableName) throws IOException {

		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;
		String sql = "select AttandanceTime,PersonNodeID,PersonNodeType,ReasonID,ReasonDesc,Comment,fnLati,fnLongi,fnAccuracy,fnAddress,AllProvidersLocation,flgLocNotFound,DistributorId,DistributorNodeType,DistributorName from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	private void exportTableNotification(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	
	
	private void exportTableForRouteType(final String tableName,String ActiveRouteID) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select ID,RouteType from " + tableName + " WHERE ID='"+ ActiveRouteID +"' Limit 1";		// chk for flag - DB adapter
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
	
	
	private void exportTableStoreMaterialDetail(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3 and  (ExistStock<>0 OR ReturntoDistributor<>0 OR FreshOrder<>0 OR DiscardDamage<>0)";		// chk for flag - DB adapter
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
	
	private void exportTablStoreProductPhotoDetail(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select StoreID,ProductID,ClickedDateTime,PhotoName,PhotoValidation,PDAPhotoPath,TmpInvoiceCodePDA from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	
	
	private void exportTableStoreMain(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	
	private void exportTableTemp(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	
	
	/*private static final String DATABASE_CREATE_TABLE_32 = "create table tblInvoice (IMEIno text not null, " +
			"StoreID text not null, InvoiceDate string not null, " +
			"TotalBeforeTaxDis real not null, TaxAmt real not null, " +
			"TotalDis real not null, InvoiceVal real not null, FreeTotal integer not null," +
			" Sstat integer not null, InvAfterDis real not null, AddDis real not null, AmtPrevDue real null, " +
			"AmtColl real null, AmtOut real null, NoCoupon int null, TotalCoupunAmount real null,RouteID int null);";*/
	//

	private void exportTableStoreCheckIn(final String tableName) throws IOException {

		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;
		String sql = "select storeID,ProductID,Stock from " + tableName + " where Sstat = 3";//CollectionCode		// chk for flag - DB adapter
		//String	sql = db.rawQuery("SELECT ifnull(INVPrefix,''),ifnull(INVSuffix,'') FROM tblActualVisitStock", null); //order by AutoIdOutlet Desc
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

	private void exportTabletblInvoice(final String tableName) throws IOException {
		LinkedHashMap<String,String> hmapInvoiceCaptionPrefixAndSuffix=fetch_InvoiceCaptionPrefixAndSuffix();
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select StoreID,StoreVisitCode,'"+hmapInvoiceCaptionPrefixAndSuffix.get("INVPrefix")+"-'||InvoiceNumber||'/"+hmapInvoiceCaptionPrefixAndSuffix.get("INVSuffix")+"' AS InvoiceNumber,InvoiceDate,TotalBeforeTaxDis,TaxAmt,TotalDis,InvoiceVal,FreeTotal,InvAfterDis,AddDis,NoCoupon,TotalCoupunAmount,TransDate,FlgInvoiceType,flgWholeSellApplicable,CycleID,TmpInvoiceCodePDA,flgDrctslsIndrctSls from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	public LinkedHashMap<String,String> fetch_InvoiceCaptionPrefixAndSuffix()
	{

		// open();
		LinkedHashMap<String,String>hmapInvoiceCaptionPrefixAndSuffix=new LinkedHashMap<String,String>();



		Cursor	cursor = db.rawQuery("SELECT ifnull(INVPrefix,''),ifnull(INVSuffix,'') FROM tblInvoiceCaption", null); //order by AutoIdOutlet Desc

		try
		{
			if(cursor.getCount()>0)
			{
				if (cursor.moveToFirst())
				{
					hmapInvoiceCaptionPrefixAndSuffix.put("INVPrefix",cursor.getString(0));
					hmapInvoiceCaptionPrefixAndSuffix.put("INVSuffix",cursor.getString(1));

				}
			}

			return hmapInvoiceCaptionPrefixAndSuffix;
		}
		finally
		{
			if(cursor!=null) {
				cursor.close();
			}
			//   close();
		}
	}
	//
	private void exportCollection(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;
		String sql = "select StoreID,StoreVisitCode,PaymentMode,PaymentModeID,Amount,RefNoChequeNoTrnNo,Date,Bank,TmpInvoiceCodePDA,CollectionCode from " + tableName + " where Sstat = 3";//CollectionCode		// chk for flag - DB adapter
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
	
	/*private static final String DATABASE_CREATE_TABLE_31 = "create table tblTransac (IMEIno text not null, " +
			"TransDate string not null, StoreID text not null, ProdID text not null, Stock integer not null," +
			" OrderQty integer not null, OrderVal real not null, FreeQty integer not null, DisVal real not null, " +
			"SchemeID text null, AppliedSlab text null, AppliedAbsVal text null, Sstat integer not null, " +
			"SampleQuantity int null, ProductShortName text null, ProductPrice real null,RouteID int null,CatID text  null);";*///, DisplayUnit text null

	
	
	/*<col name="IMEIno"/>
	<col name="RouteID">7</col>
	<col name="StoreID">1</col>
	<col name="CatID">3</col>
	<col name="ProdID">36</col>
	<col name="TransDate"/>
	<col name="Stock">0</col>
	<col name="OrderQty">5</col>
	<col name="OrderVal">429.8</col>
	<col name="FreeQty">0</col>
	<col name="DisVal">0</col>
	<col name="Sstat">3</col>
	<col name="SampleQuantity">0</col>
	<col name="ProductShortName">Go_Spread_Btl_200gm</col>
	<col name="ProductPrice">85.96</col>*/
	
	
	private void exportTabletblTransac(final String tableName) throws IOException {
		LinkedHashMap<String,String> hmapInvoiceCaptionPrefixAndSuffix=fetch_InvoiceCaptionPrefixAndSuffix();
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select '"+hmapInvoiceCaptionPrefixAndSuffix.get("INVPrefix")+"-'||InvoiceNumber||'/"+hmapInvoiceCaptionPrefixAndSuffix.get("INVSuffix")+"' AS InvoiceNumber,CatID,ProdID,ProductPrice,TaxRate,flgRuleTaxVal,OrderQty,UOMId,LineValBfrTxAftrDscnt,LineValAftrTxAftrDscnt,FreeQty,DisVal,SampleQuantity,ProductShortName,TaxValue,flgIsQuoteRateApplied,ServingDBRId,flgWholeSellApplicable,ProductExtraOrder,TmpInvoiceCodePDA,flgDrctslsIndrctSls from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	
	
	private void exportTableDayStartEndDetails(final String tableName) throws IOException {
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
	private void exportSalesQuotePersonMeetTable(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select SalesQuoteId,SalesQuoteCode,SalesQuotePrcsId,SalesQuotePrcs,StoreName,Remarks,StoreId,SalesQuoteValidFrom,SalesQuoteValidTo,SalesQuoteDate,SalesQuoteType,ContactPerson,ContactPersonEmail,ContactPersonPhone,PymtStageId,Sstat,ManufacturerID,ManufacturerName from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	//
	private void exportTabletblStoreSalesOrderPaymentDetails(final String tableName) throws IOException {

		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;
		String sql = "select StoreId,PymtStageId,TmpInvoiceCodePDA,Sstat from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	private void exportTable(final String tableName) throws IOException {
	
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	
	private void exportTableStoreReturnDetail(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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
	private void exportTableStoreProductAppliedSchemesBenifitsRecords(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3 and WhatFinallyApplied=1";		// chk for flag - DB adapter
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
	
	//
	private void exportTableStoreList(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);

		String sql = "select IMEINumber,StoreID,StoreName,OwnerName,StoreContactNo,StoreAddress,StoreType, StoreLatitude,StoreLongitude,LastVisitDate,LastTransactionDate,ISNewStore,PaymentStage,DBR,StoreCity,StorePinCode,StoreState,0 AS flgRestart,"+CommonInfo.DATABASE_VERSIONID+" AS AppVersion from " + tableName + " where Sstat = 3 AND ISNewStore=1";		// chk for flag - DB adapter
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

	public void UpdateNewAddedStorephotoFlag()
	{
		try
		{
			String sql = "Update tableImage SET Sstat=5 WHERE EXISTS(SELECT StoreID FROM tblStoreList WHERE(tableImage.StoreID=tblStoreList.StoreID AND tblStoreList.ISNewStore=1))  AND tableImage.Sstat=3";		// chk for flag - DB adapter
			db.execSQL(sql);

			String sql1 = "Update tblLatLongDetails SET Sstat=4 WHERE EXISTS(SELECT StoreID FROM tblStoreList WHERE(tblLatLongDetails.StoreID=tblStoreList.StoreID)) AND tblLatLongDetails.Sstat=3";		// chk for flag - DB adapter
			db.execSQL(sql1);

		}
		catch (Exception ex)
		{
			System.out.println("shivam query = "+ex.toString());
		}
	}
	private void exportTableStoreVisit(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);

		String sql = "select IMEINumber,StoreID,StoreVisitCode,ForDate,ActualLatitude,ActualLongitude,VisitTimeOutSideStore, VisitTimeInSideStore,VisitTimeCheckStore,VisitEndTS,LocProvider,Accuracy,BateryLeftStatus,StoreClose,StoreNextDay,ISNewStore,IsNewStoreDataCompleteSaved,flgFromWhereSubmitStatus,flgSubmitFromQuotation,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgStoreOrder,flgRetailerCreditBalnce,VisitTypeStatus,flgVisitCollectionMarkedStatus from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
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

	private void exportTableProductMappedWithSchemeSlabApplied(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;	
		// text null,  text null, 
		//StoreID text not null, StoreType string not null, StoreName string not null, StoreLatitude real not null, StoreLongitude real not null, LastVisitDate string not null, LastTransactionDate string not null, Sstat integer not null, ForDate string not null, ActualLatitude real null, ActualLongitude real null, VisitStartTS text null, VisitEndTS text null);";
		String sql = "select * from " + tableName + " where Sstat= 3";		// chk for flag - DB adapter
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
				DatabaseAssistant.DATASUBDIRECTORY);
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
	 * public String newfilename(){ ////System.out.println("inside newfilename()");
	 * 
	 * filNameTS = System.currentTimeMillis(); filNameFullPath =
	 * Environment.getExternalStorageDirectory().getPath(); EXPORT_FILE_NAME =
	 * filNameFullPath + "/" + filNameTS +".xml";
	 * ////System.out.println("new file name: " + EXPORT_FILE_NAME); return
	 * EXPORT_FILE_NAME; }
	 * 
	 * public DatabaseAssistant( Context ctx, SQLiteDatabase db ) { _ctx = ctx;
	 * _db = db;
	 * 
	 * newfilename(); try {
	 * ////System.out.println("inside try databaseAssitant() -- file name: " +
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
	 * try { ////System.out.println("_exporter: " + _exporter);
	 * ////System.out.println("_db: " + _db);
	 * ////System.out.println("inside try exportData()"); _exporter.startDbExport(
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