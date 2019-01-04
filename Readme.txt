Details regarding total lines of code written:
1. Lines of code considering all Java-Servlet files: 10,820 Lines of code approximately
2. Lines of code for javascript:
	i.		In "javascript.js" file : 233 Lines of code
	ii.		In "Header.html" file having javascript: 290 Lines of code
	iii.	In "MedicineVsAvailableMedicinebarchart.js" file: 106 Lines of code

********************************************************************************************************************
Assignment Features implemented in our project:
(We have 3 roles, i.e. Patient, Doctor and Medicine Manager.)

1. Home page having header,left navigation bar, content and footer: Implemented
1. Add products to cart, carousel for recommended products or accessory, place an order and payment Transaction by Patient: Implemented
2. Login, Register, View Order, View Account: Implemented
3. Add, Update, Delete products done by the Medicine Manager: Implemented
4. Offering discount and manufacturer rebate while buying a product: Implemented
5. Expected delivery date for placed order is 15 days ahead of the current date and can cancel an order at a later timer, though
it must be 3 business days before the delivery date: Implemented
6. Having good amount of data in terms of products and doctors: Done
7. All accounts login information is stored in MYSQL database and All patient transactions/orders/appointments stored in SQL
database (MySQL): Implemented
8. Patient is able to submit product reviews: Implemented
9. Patient/Doctor/Medicine Manager is able to view submitted product reviews: Implemented
10. Product reviews are stored in NoSQL database (MongoDB): Implemented
11. Add Trending, Inventory Report and Sales Report feature using google bar chart and for some cases displaying information in table on UI: Implemented
12. All code added for MySQL is placed in a class called "MySQLDataStoreUtilities.java": Implemented
13. All new code added for MongoDB is placed in a class called "MongoDBDataStoreUtilities.java": Implemented
14. Auto-complete-feature for searching a medicine or accessory or doctor (auto-complete feature placed in AjaxUtility.java): Implemented
15. Deal matches guarantee and producd recommendation feature: Implemented
16. Build servlet-based web application: Followed

For extra feature and detailed feature implemented for each role, we have given separate file named "Checklist_Of_Features.pdf" in the extracted zip folder "CSP584 Project - Patel, Khushbu".

********************************************************************************************************************
Pre-requisites for running "KNNHealthCare" web-site:
1. Need to have "Firefox" web browser installed in the machine.
2. Need to have java "jdk1.7.0_40" and "jre7" installed in the machine in C drive's "Program Files" folder.
3. Need to have "apache-tomcat-7.0.34" installed in C drive and it should be running.
4. Need to have following jar files in the "C:\Program Files\Java\jre7\lib\ext" folder. (You can copy this following mentioned jar files from the "C:\apache-tomcat-7.0.34\lib" folder.)
	i.   tomcat-dbcp.jar
	ii.	 servlet-api.jar
	iii. jsp-api.jar
	iv.	 el-api.jar
5. Need to have MySql installed and configured in the machine in "C:\Program Files". Make sure whenever you run MySql, it uses port number "3306".
6. Need to have Mongodb installed and configured in the machine in "C:\Program Files". Make sure whenever you run Mongodb, it runs on port number "27017".
7. Need to have following jar files in the "C:\apache-tomcat-7.0.34\lib" folder which is installed in the C drive in above step number 3.
	i. 	    mongo-java-driver-3.2.2.jar
	ii. 	mysql-connector-java-5.1.47-bin.jar
	iii. 	gson-2.3.1.jar
	iv.		mail.jar
	v.      activation.jar
	vi.     smtp-1.4.4.jar
8. Open "env-setup-for-tomcat" file available in the "C:\apache-tomcat-7.0.34" directory in text editor and place the following content in the file and save the changes made to the file.
Content that needs to be pasted in the "env-setup-for-tomcat" file:

set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_40

set PATH="C:\Program Files\Java\jdk1.7.0_40\bin",%PATH%

set CLASSPATH=.;C:\apache-tomcat-7.0.34\lib\servlet-api.jar;C:\apache-tomcat-7.0.34\lib\jsp-api.jar;C:\apache-tomcat-7.0.34\lib\el-api.jar;C:\apache-tomcat-7.0.34\lib\commons-beanutils-1.8.3.jar;C:\apache-tomcat-7.0.34\lib\mysql-connector-java-5.1.47-bin.jar;C:\apache-tomcat-7.0.34\lib\mongo-java-driver-3.2.2.jar;C:\apache-tomcat-7.0.34\lib\gson-2.3.1.jar;C:\apache-tomcat-7.0.34\lib\mail.jar;C:\apache-tomcat-7.0.34\lib\activation.jar;C:\apache-tomcat-7.0.34\lib\smtp-1.4.4.jar

set ANT_HOME=c:\apache-tomcat-7.0.34

set TOMCAT_HOME=C:\apache-tomcat-7.0.34

set CATALINA_HOME=C:\apache-tomcat-7.0.34

********************************************************************************************************************
Steps to Deploy and Run "KNNHealthCare" web page:

1. Extract the zip folder "CSP584 Project - Patel, Khushbu".
2. In the extracted zip folder, there will be folder named "KNNHealthCare".
3. Copy this "KNNHealthCare" folder in the "webapps" folder of "apache-tomcat-7.0.34" folder in C drive (path: "C:\apache-tomcat-7.0.34\webapps").
4. Start the MySql server and run the script given in the file "MySql_KNNHealthCare_Script.txt" which is available in the extracted folder "CSP584 Project - Patel, Khushbu". (avoid the select mysql statement diven in the "MySql_KNNHealthCare_Script.txt" to run the script.)
5. Start the MongoDb Server:
	i.		Open command prompt window.
	ii. 	Change the directory by giving command "cd C:\Program Files\MongoDB\Server\3.0\bin".
	iii.	Type "mongod"and press "enter" key from the keyboard. No error should be seen on the command prompt window and it should show message as "waiting for connections on port 27017".
	iv. 	Open another command prompt window.
	v. 		Change the directory by giving command "cd C:\Program Files\MongoDB\Server\3.0\bin".
	vi.		Type "mongo" and press "enter" key from the keyboard. Once "enter" key is pressed, you should see "C:\WINDOWS\system32\cmd.exe - mongo" as the tilte for opened command prompt.
	vii.	Incase if database "PatientReviews" and "MedicineReviews" is not present in the mongodb then create Database "PatientReviews" and "MedicineReviews" by giving following command in the mongo terminal.
			Command: "use PatientReviews" and "use MedicineReviews"
	viii.	Incase if collection "myReviews" not present in the database "PatientReviews" in the mongodb then create collection named "myReviews" by giving following command in the mongo terminal.
			Command: "db.createCollection(myReviews)"
	ix.		Incase if collection "myMedicineReviews" not present in the database "MedicineReviews" in the mongodb then create collection named "myReviews" by giving following command in the mongo terminal.
			Command: "db.createCollection(myMedicineReviews)"

7. Once both MySql database and mongodb servers are started, then start the Tomcat server:
	i.		Open new command prompt window.
	ii.		Change the directory by giving command "cd C:\apache-tomcat-7.0.34".
	iii.	Type "env-setup-for-tomcat" and press "enter" key from the keyboard.
	iv.		Change the directory by giving command "cd C:\apache-tomcat-7.0.34\bin".
	v.		Type "startup" and press "enter" key from the keyboard. Now a new window should get opened having window title as "Tomcat" and in this "Tomcat" window, there should not be seen any errors.
8. Once tomcat server is up, compile all the java files available in the "C:\apache-tomcat-7.0.34\webapps\KNNHealthCare\WEB-INF\classes" folder:
	i.		Open new command prompt window.
	ii.		Change the directory by giving command "cd C:\apache-tomcat-7.0.34\webapps\KNNHealthCare\WEB-INF\classes".
	iii.	Type "javac *.java" and press "enter" key from the keyboard.
	All the java files should be compiled and no error should be seen on the command prompt.
9. Now open the "Firefox" web browser in order to run the "KNNHealthCare" web-site (Make sure port number "8080" is free).
10. In opened "Firefox" web browser, enter url as "http://localhost:8080/KNNHealthCare/Home". Once given url is entered, "KNNHealthCare" web page should be seen.
********************************************************************************************************************