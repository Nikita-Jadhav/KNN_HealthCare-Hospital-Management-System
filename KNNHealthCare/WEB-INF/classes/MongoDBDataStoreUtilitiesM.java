import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;
                	
public class MongoDBDataStoreUtilitiesM
{
	static DBCollection myMedicineReviews;
	public static DBCollection getConnection()
	{
		MongoClient mongo;
		mongo = new MongoClient("localhost",27017);
		DB db = mongo.getDB("MedicineReviews");
		myMedicineReviews= db.getCollection("myMedicineReviews");	
		return myMedicineReviews; 
	}

	public static String insertReviewM (String productname,String producttype,String price,String productmaker,String reviewrating,String manufacturerrebate,
	String reatilerpin,String retailerCity,String retailerstate,String reviewdate,String userid,String userage,String usergender,
	String reviewtext)
	
	/*(String productname,String username,String producttype,String productmaker,String reviewrating,String reviewdate,
	String reviewtext,String retailerpin,String price,String retailerState)*/
	{
		try
		{		
			getConnection();
			BasicDBObject doc = new BasicDBObject("title", "myMedicineReviews").
			//append("userName", username).
			append("userName", userid).
			append("productName", productname).
			append("productType", producttype).
			append("price",(int) Double.parseDouble(price)).
			append("productMaker", productmaker).
			append("reviewRating",Integer.parseInt(reviewrating)).
			append("manufacturerRebate", manufacturerrebate).
			append("retailerPin", reatilerpin).
			append("retailerCity", retailerCity).
			append("retailerState", retailerstate).
		//	append("productOnSale", productonsale).
			append("reviewDate", reviewdate).
			append("userAge", userage).
			append("userGender", usergender).
		//	append("userOccupation", useroccupation).			
			append("reviewText", reviewtext);
			myMedicineReviews.insert(doc);
			return "Successfull";
		}
		catch(Exception e)
		{
			return "UnSuccessfull";
		}	
		
}


public static HashMap<String, ArrayList<ReviewM>> selectReviewM()
{	
	HashMap<String, ArrayList<ReviewM>> reviewsM=null;
	
	try
		{

	getConnection();
	DBCursor cursor = myMedicineReviews.find();
	reviewsM=new HashMap<String, ArrayList<ReviewM>>();
	while (cursor.hasNext())
	{
			BasicDBObject obj = (BasicDBObject) cursor.next();				
	
		   if(!reviewsM.containsKey(obj.getString("productName")))
			{	
				ArrayList<ReviewM> arr = new ArrayList<ReviewM>();
				reviewsM.put(obj.getString("productName"), arr);
			}
			ArrayList<ReviewM> listReviewM = reviewsM.get(obj.getString("productName"));		
			ReviewM reviewM =new ReviewM(obj.getString("userName"),obj.getString("productName"),obj.getString("productType"),obj.getString("price"),
			obj.getString("productMaker"),obj.getString("reviewRating"),obj.getString("manufacturerRebate"),obj.getString("retailerpin"),
			obj.getString("retailerCity"),obj.getString("retailerState"),obj.getString("reviewDate"),
			obj.getString("userAge"),obj.getString("userGender"),obj.getString("reviewText"));
			//add to review hashmap
			listReviewM.add(reviewM);		
			}
 		return reviewsM;
		}
		catch(Exception e)
		{
		 reviewsM=null;
		 return reviewsM;	
		}	

     
	}
	/*

  public static  ArrayList <Bestrating> topProducts(){
	  ArrayList <Bestrating> Bestrate = new ArrayList <Bestrating> ();
	  try{
		  
	  getConnection();
	  int retlimit =5;
	  DBObject sort = new BasicDBObject();
	  sort.put("reviewRating",-1);
	  DBCursor cursor = myMedicineReviews.find().limit(retlimit).sort(sort);
	  while(cursor.hasNext()) {
		  BasicDBObject obj = (BasicDBObject) cursor.next();
		  		  		   
		  String prodcutnm = obj.get("productName").toString();
		  String rating = obj.get("reviewRating").toString();
	      Bestrating best = new Bestrating(prodcutnm,rating);
		  Bestrate.add(best);
	  }
	
	}catch (Exception e){ System.out.println(e.getMessage());}
   return Bestrate;
  } */
  /*
  	  public static ArrayList <Mostsoldzip> mostsoldZip(){
	  ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
	  try{
		  
	  getConnection();
      DBObject groupProducts = new BasicDBObject("_id","$retailerPin"); 
	  groupProducts.put("count",new BasicDBObject("$sum",1));
	  DBObject group = new BasicDBObject("$group",groupProducts);
	  DBObject limit=new BasicDBObject();
      limit=new BasicDBObject("$limit",5);
	  
	  DBObject sortFields = new BasicDBObject("count",-1);
	  DBObject sort = new BasicDBObject("$sort",sortFields);
	  AggregationOutput output = myMedicineReviews.aggregate(group,sort,limit);
      for (DBObject res : output.results()) {
        
		String zipcode =(res.get("_id")).toString();
        String count = (res.get("count")).toString();	
        Mostsoldzip mostsldzip = new Mostsoldzip(zipcode,count);
		mostsoldzip.add(mostsldzip);
	
	  }
	  
	 
	  
	}catch (Exception e){ System.out.println(e.getMessage());}
      return mostsoldzip;
  }
  
   public static ArrayList <Mostsold> mostsoldProducts(){
	  ArrayList <Mostsold> mostsold = new ArrayList <Mostsold> ();
	  try{
		  
	  
      getConnection();
      DBObject groupProducts = new BasicDBObject("_id","$productName"); 
	  groupProducts.put("count",new BasicDBObject("$sum",1));
	  DBObject group = new BasicDBObject("$group",groupProducts);
	  DBObject limit=new BasicDBObject();
      limit=new BasicDBObject("$limit",5);
	  
	  DBObject sortFields = new BasicDBObject("count",-1);
	  DBObject sort = new BasicDBObject("$sort",sortFields);
	  AggregationOutput output = myMedicineReviews.aggregate(group,sort,limit);
	  
      for (DBObject res : output.results()) {
	  
      
       
		String prodcutname =(res.get("_id")).toString();
        String count = (res.get("count")).toString();	
        Mostsold mostsld = new Mostsold(prodcutname,count);
		mostsold.add(mostsld);
	
	  }
	  
	 
	  
	}catch (Exception e){ System.out.println(e.getMessage());}
      return mostsold;
  }	

  //Get all the reviews grouped by product and zip code;
/*public static ArrayList<Review> selectReviewForChart() {

		
        ArrayList<Review> reviewList = new ArrayList<Review>();
        try {

            getConnection();
            Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
            dbObjIdMap.put("retailerPin", "$retailerPin");
            dbObjIdMap.put("productName", "$productName");
            DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
            groupFields.put("count", new BasicDBObject("$sum", 1));
            DBObject group = new BasicDBObject("$group", groupFields);

            DBObject projectFields = new BasicDBObject("_id", 0);
            projectFields.put("retailerPin", "$_id");
            projectFields.put("productName", "$productName");
            projectFields.put("reviewCount", "$count");
            DBObject project = new BasicDBObject("$project", projectFields);

            DBObject sort = new BasicDBObject();
            sort.put("reviewCount", -1);

            DBObject orderby = new BasicDBObject();            
            orderby = new BasicDBObject("$sort",sort);
            

            AggregationOutput aggregate = myReviews.aggregate(group, project, orderby);

            for (DBObject result : aggregate.results()) {

                BasicDBObject obj = (BasicDBObject) result;
                Object o = com.mongodb.util.JSON.parse(obj.getString("retailerPin"));
                BasicDBObject dbObj = (BasicDBObject) o;
                Review review = new Review(dbObj.getString("productName"), dbObj.getString("retailerPin"),
                        obj.getString("reviewCount"), null);
                reviewList.add(review);
                
            }
            return reviewList;

        }

        catch (

        Exception e) {
            reviewList = null;
            
            return reviewList;
        }

    }*/
/*
	public static ArrayList<ReviewM> selectReviewForChart() {

		
        ArrayList<ReviewM> reviewListM = new ArrayList<ReviewM>();
        try {

            getConnection();
            Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
            dbObjIdMap.put("retailerCity", "$retailerCity");
            dbObjIdMap.put("productName", "$productName");
            DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
            groupFields.put("count", new BasicDBObject("$sum", 1));
            DBObject group = new BasicDBObject("$group", groupFields);

            DBObject projectFields = new BasicDBObject("_id", 0);
            projectFields.put("retailerCity", "$_id");
            projectFields.put("productName", "$productName");
            projectFields.put("reviewCount", "$count");
            DBObject project = new BasicDBObject("$project", projectFields);

            DBObject sort = new BasicDBObject();
            sort.put("reviewCount", -1);

            DBObject orderby = new BasicDBObject();            
            orderby = new BasicDBObject("$sort",sort);
            

            AggregationOutput aggregate = myMedicineReviews.aggregate(group, project, orderby);

            for (DBObject result : aggregate.results()) {

                BasicDBObject obj = (BasicDBObject) result;
                Object o = com.mongodb.util.JSON.parse(obj.getString("retailerCity"));
                BasicDBObject dbObj = (BasicDBObject) o;
                ReviewM reviewM = new ReviewM(dbObj.getString("productName"), dbObj.getString("retailerCity"),
                        obj.getString("reviewCount"), null);
                reviewListM.add(reviewM);
                
            }
            return reviewListM;

        }

        catch (Exception e) {
            reviewList = null;
            
            return reviewListM;
        }

    }
	
	public static HashMap<String,ArrayList<ReviewM>> selectReviewCount()
	{	
	HashMap<String,ArrayList<ReviewM>> reviewsM=null;
	
	try
		{

	getConnection();
	DBCursor cursor = myMedicineReviews.find();
	reviews=new HashMap<String, ArrayList<Review>>();
	int i=1;
	while (cursor.hasNext())
	{
			BasicDBObject obj = (BasicDBObject) cursor.next();				
	
		   /*if(reviews.containsKey(obj.getString("productName")))
			{	*/
			/*	ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(obj.getString("productName"), arr);
			//}  
	/*		ArrayList<ReviewM> listReviewM = reviewsM.get(obj.getString("productName"));		
			ReviewM reviewM =new ReviewM(obj.getString("userName"),obj.getString("productName"),obj.getString("productType"),obj.getString("price"),
			obj.getString("productMaker"),obj.getString("reviewRating"),obj.getString("manufacturerRebate"),obj.getString("retailerpin"),
			obj.getString("retailerCity"),obj.getString("retailerState"),obj.getString("reviewDate"),
			obj.getString("patientAge"),obj.getString("patientGender"),obj.getString("reviewText"));
			//add to review hashmap
			listReviewM.add(reviewM);
			i++;			
			}
 		return reviews;
		}
		catch(Exception e)
		{
		 reviewsM=null;
		 return reviewsM;	
		}	

     
	}  */
	
}	