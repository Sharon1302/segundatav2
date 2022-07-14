package com.dpex.segundata.Constant;

public class Constants {

//public static String MAINURL = "http://192.168.0.134:1337/api/" ;
//public static String MAINURL = "https://segundata.dpex.com.ng/api/" ;
    public static String MAINURL = "https://segundata.com/api/" ;
public static String SeLECTORDERSTATUS= MAINURL+"getorderstatus/" ;
    public static String CONTACTUS= MAINURL+"contact" ;
    public static String PRICELIST= MAINURL+"price"  ;
    public static String LOGIN = MAINURL+"login_user" ;
    public static String REGISTER= MAINURL+"register" ;
    public static String TRANSFERFUND= MAINURL+"transferrfunds" ;
    public static String GETBANKACCOUNTS= MAINURL+"getbankaccounts" ;
    public static String CRON= MAINURL+"cron" ;
    public static String WITHDRAWBONUS= MAINURL+"withdrawbonus" ;
    public static String GETBUNDLE= MAINURL+"getbundle" ;
    public static String SEND_MESSAGE= MAINURL+"send_message_api" ;
    public static String GETTOKEN= MAINURL+"passwordreset" ;

    public static String VALIDATETOKEN= MAINURL+"tokenvalidate" ;
    public static String CHANGEPASSWORD= MAINURL+"passwordchanged" ;
    public static String RESENDTOKENEMAIL= MAINURL+"resendtokenmail" ;
    public static String PAYMENTHISTORY= MAINURL+"paymenthistory" ;
    public static String BANKFUND= MAINURL+"bankfund" ;
    public static String PENDINGFUND= MAINURL+"getpendingfunders" ;
    public static String BANKAPICONFIRM= MAINURL+"bankconfirmapi/" ;
    public static String ORDER= MAINURL+"order" ;
    public static String UPGRADE= MAINURL+"upgrade" ;
    public static String PAYMENT= MAINURL+"pay/" ;
    public static String ORDERHISTORY= MAINURL+"ordermobile" ;
    public static String VIEWWALLET= MAINURL+"ViewWallet" ;
    public static String ADMINCUSTOMERVIEW= MAINURL+"dashboard/" ;
    public static String ADMINPAYMENTVIEW= MAINURL+"fundingview/" ;
    public static String UPDATEORDERSTATUS= MAINURL+"updateorderstatus" ;
    public static String UPDATEMYACCOUNT= MAINURL+"updatemyaccount" ;
    public static String UPDATEPIN2= MAINURL+"updatepin2" ;
    public static String resetPin= MAINURL+"pinreset";
    public static String UPDATEPIN= MAINURL+"updatepin" ;
    public static String ADMINBUNDLE= MAINURL+"" ;
    public static String WEBVIEW= "" ;
    public static int runsubmit = 0 ;
    public static String  PinpAdTitle = "";
    public static int unread= 0 ;
    public static String InTSANTFUNDINGCOMMISION ="2" ;
    public static String PAYTYPE ="" ;
    public static String RESELLERMINIMUM ="10000" ;
    public static String UserEmail ="" ;
    public static String UserPassword ="" ;
    public static String Bonus ="0" ;
    public static String UserROLE ="" ;
    public static String resetType ="";
    public static String forgotPassword = "";
    public static String UserNAME="" ;
    public static String CardFundAmount = "";
    public static String TOKEN ="" ;
    public static String  LastIID = "";
    public static String RESETEMAIL ="" ;
    public static String USERNAME ="" ;
    public static String BalanceText ="loading..." ;
    public static String Balance ="loading..." ;
    public static String LogRole ="customer" ;
    public static String COMPANYID= "" ;
    public static String NetWorkType ="" ;
    public static String ServiceType ="" ;

    public static String FirstName ="" ;
    public static String LastName ="" ;


    public static String BankName ="" ;
    public static String AccountName ="" ;
    public static String AccountNo ="" ;

    public static String PhoneNo ="" ;

    public static String UpPrice ="" ;
    public static String UpPhone ="" ;
    public static String UpBundleID ="" ;
    public static String UpDiscount ="" ;

    public static String  SYNCDURATION = "" ;
    public static String  traderorigin = "";
    public static String  OPERATINGMODE= "" ;
    public static String mdate ="";
    public static String mtitle ="" ;
    public static String mdata ="" ;
    public static String  ORNUMBER = "" ;
    public static String   ORDERID = "";
    public static String  cusid="";
  public static int CARTTOTALITEM =0 ;

    public static String USERID = "" ;

    public static int  messageid=0;
    public static String toCamelCase(String s){
        if(s.length() == 0){
            return s;
        }
        String[] parts = s.split(" ");
        String camelCaseString = "";
        for (String part : parts){
            camelCaseString = camelCaseString + toProperCase(part) + " ";
        }
        return camelCaseString;
    }

    public static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }
    //https://www.geeksforgeeks.org/volley-library-in-android/
    /*

       <View
                android:id="@+id/view1"
                android:layout_width="7dp"
                android:layout_height="90dp"
                android:background="@color/warm_grey" />




        ProgressDialog pDialog = new ProgressDialog(this);
    pDialog.setMessage("Loading...PLease wait");
    pDialog.show();
                  pDialog.hide();


                     // Update the TextView
        TextView text = (TextView) findViewById(R.id.text);
        text.setText("This app has been started " + counter + " times.");


        https://www.androhub.com/login-signup-and-forgot-password-screen-design-android/


                android:layout_marginTop="70dp"

                        android:layout_centerInParent="true"



                        private boolean isValidNumber(String phonenumber) {
            String PHONE_PATTERN = "^(984|986)\\d{7}$";
// phone number starts with 984 and 986 and has 7 digit after that ex: 9842248633 (true or valid) and 9851048633(false or not valid) and in total 10 digits number

            Pattern pattern = Pattern.compile(PHONE_PATTERN);
            Matcher matcher = pattern.matcher(phonenumber);
            return matcher.matches();
        }
https://github.com/Shashank02051997/FancyFlashbar-Android

https://github.com/michaelprimez/searchablespinner
https://github.com/levitnudi/LegacyTableView
     */


}
