import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;
                	
public class MongoDBDataStoreUtilities
{
	static DBCollection myReviews;
	public static DBCollection getConnection()
	{
		MongoClient mongo;
		mongo = new MongoClient("localhost",27017);
		DB db = mongo.getDB("PatientReviews");
		myReviews= db.getCollection("myReviews");	
		return myReviews; 
	}

	public static String insertReview (String docname,String docspeciality,String reviewrating,
	String reviewdate,String userid,String patientage,String patientgender,
	String reviewtext)
	
	/*(String productname,String username,String producttype,String productmaker,String reviewrating,String reviewdate,
	String reviewtext,String retailerpin,String price,String retailerState)*/
	{
		try
		{		
			getConnection();
			BasicDBObject doc = new BasicDBObject("title", "myReviews").
			//append("userName", username).
			append("userName", userid).
			append("doctorName", docname).
			append("doctorSpeciality", docspeciality).
			//append("price",(int) Double.parseDouble(price)).
			//append("productMaker", productmaker).
			append("reviewRating",Integer.parseInt(reviewrating)).
			//append("manufacturerRebate", manufacturerrebate).
			//append("retailerPin", reatilerpin).
			//append("retailerCity", retailerCity).
			//append("retailerState", retailerstate).
			//append("productOnSale", productonsale).
			append("reviewDate", reviewdate).
			append("patientAge", patientage).
			append("patientGender", patientgender).
		//	append("userOccupation", useroccupation).			
			append("reviewText", reviewtext);
			myReviews.insert(doc);
			return "Successfull";
		}
		catch(Exception e)
		{
			return "UnSuccessfull";
		}	
		
}


public static HashMap<String, ArrayList<Review>> selectReview()
{	
	HashMap<String, ArrayList<Review>> reviews=null;
	
	try
		{

	getConnection();
	DBCursor cursor = myReviews.find();
	reviews=new HashMap<String, ArrayList<Review>>();
	while (cursor.hasNext())
	{
			BasicDBObject obj = (BasicDBObject) cursor.next();				
	
		   if(!reviews.containsKey(obj.getString("doctorName")))
			{	
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(obj.getString("doctorName"), arr);
			}
			ArrayList<Review> listReview = reviews.get(obj.getString("doctorName"));		
			Review review =new Review(obj.getString("userName"),obj.getString("doctorName"),obj.getString("doctorSpeciality"),
			obj.getString("reviewRating"),obj.getString("reviewDate"),obj.getString("patientAge"),obj.getString("patientGender"),obj.getString("reviewText"));
			//add to review hashmap
			listReview.add(review);		
			}
 		return reviews;
		}
		catch(Exception e)
		{
		 reviews=null;
		 return reviews;	
		}	

     
	}
public static ArrayList <Mostpatientsvisited> mostpatientsvisited(){
	  ArrayList <Mostpatientsvisited> mostpatientsvisited = new ArrayList <Mostpatientsvisited> ();
	  try{
		  
	  getConnection();
      DBObject groupPatients = new BasicDBObject("_id","$doctorSpeciality");
	  groupPatients.put("count",new BasicDBObject("$sum",1));
	  DBObject group = new BasicDBObject("$group",groupPatients);
	  DBObject limit=new BasicDBObject();
      limit=new BasicDBObject("$limit",5);
	  
	  DBObject sortFields = new BasicDBObject("count",-1);
	  DBObject sort = new BasicDBObject("$sort",sortFields);
	  AggregationOutput output = myReviews.aggregate(group,sort,limit);
	  for (DBObject res : output.results()) {
        
		String specialty =(res.get("_id")).toString();
        String count = (res.get("count")).toString();	
        Mostpatientsvisited mostpvisited = new Mostpatientsvisited(specialty,count);
		mostpatientsvisited.add(mostpvisited);
	  }
	  }
	catch (Exception e){ System.out.println(e.getMessage());}
      return mostpatientsvisited;
  }
    public static  ArrayList <Bestrating> topDoctors(){
	  ArrayList <Bestrating> Bestrate = new ArrayList <Bestrating> ();
	  try{
		  
	  getConnection();
	  int retlimit =5;
	  DBObject sort = new BasicDBObject();
	  sort.put("reviewRating",-1);
	  DBCursor cursor = myReviews.find().limit(retlimit).sort(sort);
	  while(cursor.hasNext()) {
		  BasicDBObject obj = (BasicDBObject) cursor.next();
		  		  		   
		  String doctornm = obj.get("doctorName").toString();
		  String rating = obj.get("reviewRating").toString();
	      Bestrating best = new Bestrating(doctornm,rating);
		  Bestrate.add(best);
	  }
	
	}catch (Exception e){ System.out.println(e.getMessage());}
   return Bestrate;
  }
     
	}
	
