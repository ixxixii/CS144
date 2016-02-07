package edu.ucla.cs.cs144;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.ucla.cs.cs144.DbManager;
import edu.ucla.cs.cs144.SearchRegion;
import edu.ucla.cs.cs144.SearchResult;

public class AuctionSearch implements IAuctionSearch {

	/* 
         * You will probably have to use JDBC to access MySQL data
         * Lucene IndexSearcher class to lookup Lucene index.
         * Read the corresponding tutorial to learn about how to use these.
         *
	 * You may create helper functions or classes to simplify writing these
	 * methods. Make sure that your helper functions are not public,
         * so that they are not exposed to outside of this class.
         *
         * Any new classes that you create should be part of
         * edu.ucla.cs.cs144 package and their source files should be
         * placed at src/edu/ucla/cs/cs144.
         *
         */
	
	public SearchResult[] basicSearch(String query, int numResultsToSkip, 
			int numResultsToReturn) {
		// TODO: Your code here!
		TopDocs topDocs=null;
		try {
			SearchEngine searchEngine = new SearchEngine();
			topDocs=searchEngine.performSearch(query,numResultsToReturn+numResultsToSkip);
			ScoreDoc[] scoreDocs=topDocs.scoreDocs;
			SearchResult[] r=new SearchResult[numResultsToReturn];
			for(int i=numResultsToSkip;i<scoreDocs.length;++i){
				Document doc=searchEngine.getDocument(scoreDocs[i].doc);
				String itemID=doc.get("ItemID");
				String itemName=doc.get("ItemName");
				r[i-numResultsToSkip]=new SearchResult(itemID,itemName);
				return r;
			}
		}catch (IOException ex){
			ex.printStackTrace();
		}catch (ParseException ex){
			ex.printStackTrace();
		}
		return new SearchResult[0];
	}

	public SearchResult[] spatialSearch(String query, SearchRegion region,
			int numResultsToSkip, int numResultsToReturn) {
		// TODO: Your code here!
		SearchResult[] r1=null;
		try {
			TopDocs topDocs=null;
			SearchEngine searchEngine = new SearchEngine();
			topDocs=searchEngine.performSearch(query,numResultsToReturn+numResultsToSkip);
			ScoreDoc[] scoreDocs=topDocs.scoreDocs;
			r1=new SearchResult[numResultsToReturn];
			for(int i=numResultsToSkip;i<scoreDocs.length;++i){
				Document doc=searchEngine.getDocument(scoreDocs[i].doc);
				String itemID=doc.get("ItemID");
				String itemName=doc.get("ItemName");
				r1[i-numResultsToSkip]=new SearchResult(itemID,itemName);
//				return r1;
			}
		}catch (IOException ex){
			ex.printStackTrace();
		}catch (ParseException ex){
			ex.printStackTrace();
		}

		Connection conn=null;
		String spatialQuery="select ItemID,ItemName,AsText(Coordinate)" +
				"from "
		try{
			conn=DbManager.getConnection(true);
			Statement stmt=conn.createStatement();
		}catch (SQLException ex){
			ex.printStackTrace();
		}

		return new SearchResult[0];
	}

	public String getXMLDataForItemId(String itemId) {
		// TODO: Your code here!
		return "";
	}
	
	public String echo(String message) {
		return message;
	}

}