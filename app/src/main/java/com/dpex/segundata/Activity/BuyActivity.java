package com.dpex.segundata.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.Handler.BundleDatabaseHandler;
import com.dpex.segundata.Handler.CartDatabaseHandler;
import com.dpex.segundata.Object.BundleDBObject;
import com.dpex.segundata.Object.CartDBObject;
import com.dpex.segundata.R;
import com.dpex.segundata.adapter.SimpleListAdapter;
import com.shashank.platform.fancyflashbarlib.Flashbar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.IStatusListener;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;


public class BuyActivity extends AppCompatActivity {
    private static final String TAG = "Flashbar";

    private Flashbar flashbar = null;
    String email, password;
    String Select = "";
    String BundleID = "";
    String DisCount = "";
    int LoadPinListOnce = 0;
    private SimpleListAdapter mSimpleListAdapter;
    private final ArrayList<String> mStringsdiscount = new ArrayList<>();
    private final ArrayList<String> mStringsbundlename = new ArrayList<>();
    private final ArrayList<Integer> mStringsbundleid = new ArrayList<>();
    private final ArrayList<String> mStringsprice = new ArrayList<>();
    private SearchableSpinner mSearchableSpinner1;

    private SimpleListAdapter mSimpleListAdapter2;
    private final ArrayList<String> mStringsdiscount2 = new ArrayList<>();
    private final ArrayList<String> mStringsbundlename2 = new ArrayList<>();
    private final ArrayList<Integer> mStringsbundleid2 = new ArrayList<>();
    private final ArrayList<String> mStringsprice2 = new ArrayList<>();
    private SearchableSpinner mSearchableSpinner2;
    String Price;
    String ServiceSubType = "";
    int PERMISSION_REQUEST_CONTACT = 1;
EditText mobile,cmobile;
    private static final int RESULT_PICK_CONTACT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final CheckBox disabled = findViewById(R.id.disabled);
  mobile = findViewById(R.id.mobile);
 cmobile = findViewById(R.id.cmobile);
        final Button add = findViewById(R.id.add);
        final ImageView image = findViewById(R.id.image);
        final TextView wallet = findViewById(R.id.wallet);
        final EditText amount = findViewById(R.id.amount);

        mSearchableSpinner1 = (SearchableSpinner) findViewById(R.id.SearchableSpinner);
        mSearchableSpinner2 = (SearchableSpinner) findViewById(R.id.SearchableSpinner2);

        askForContactPermission();


        mobile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mobile.getRight() - mobile.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
                        return true;
                    }
                }
                return false;
            }
        });


        if ("data".equalsIgnoreCase(Constants.ServiceType)) {
            initListValues();
            amount.setVisibility(View.GONE);
            mSearchableSpinner1.setVisibility(View.VISIBLE);
            mSearchableSpinner2.setVisibility(View.GONE);
            ServiceSubType = "data";
        } else if ("airtime".equalsIgnoreCase(Constants.ServiceType)) {

            amount.setVisibility(View.VISIBLE);
            mSearchableSpinner1.setVisibility(View.GONE);
            mSearchableSpinner2.setVisibility(View.GONE);
            ServiceSubType = "vtu_airtime";


        }


        if ("airtime".equalsIgnoreCase(Constants.ServiceType)) {
            if ("mtn".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.mtnair));
            } else if ("airtel".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.airtelair));
            } else if ("glo".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.gloair));
            } else {
                image.setImageDrawable(getDrawable(R.drawable.nineair));
            }
        } else {
            if ("mtn".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.mtndata));
            } else if ("airtel".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.airteldata));
            } else if ("glo".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.glodata));
            } else {
                image.setImageDrawable(getDrawable(R.drawable.ninedata));
            }
        }


        setTitle((Constants.NetWorkType + " -> " + Constants.toCamelCase(Constants.ServiceType) + " Request").toUpperCase());


        wallet.setText("Wallet Balance: N" + Constants.Balance);
        Constants.CARTTOTALITEM = 0;
        CartDatabaseHandler localsmsdb = new CartDatabaseHandler(BuyActivity.this);
        List<CartDBObject> settingsdata = localsmsdb.getAllContacts();
        for (CartDBObject cn : settingsdata) {
            // id = cn.getID();
            Constants.CARTTOTALITEM = Constants.CARTTOTALITEM + 1;
        }

     /*   if ((flashbar == null) && (Constants.CARTTOTALITEM>0)) {
            flashbar = fancyFlashbar(Constants.CARTTOTALITEM);

            flashbar.show();
        }
*/
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Mobile = mobile.getText().toString();
                String CMobile = cmobile.getText().toString();

                if ("airtime".equalsIgnoreCase(Constants.ServiceType)) {
                    if ("vtu_airtime".equalsIgnoreCase(ServiceSubType)) {


                        if ("mtn".equalsIgnoreCase(Constants.NetWorkType)) {
                            Select = "MTN VTU";
                            BundleID = "37";
                        } else if ("airtel".equalsIgnoreCase(Constants.NetWorkType)) {
                            Select = "AIRTEL VTU";
                            BundleID = "20";
                        } else if ("glo".equalsIgnoreCase(Constants.NetWorkType)) {
                            Select = "GLO VTU";
                            BundleID = "22";
                        } else {
                            Select = "9MOBILE VTU";
                            BundleID = "21";
                        }

                        BundleDatabaseHandler localsmsdb = new BundleDatabaseHandler(BuyActivity.this);
                        List<BundleDBObject> settingsdata = localsmsdb.getairtimeContacts();
                        for (BundleDBObject cn : settingsdata) {


                            if (Integer.parseInt(BundleID) == cn.getBundleID()) {
                                DisCount = cn.getDiscount();

                            }


                        }

                        //     Toast.makeText(BuyActivity.this, Constants.COMPANYID+"The "+DisCount+" me "+BundleID, Toast.LENGTH_SHORT).show();

                    }
                }


                if (Mobile.length() != 11) {
                    Toast.makeText(BuyActivity.this, "The mobile number must be 11 digit", Toast.LENGTH_SHORT).show();
                } else if (CMobile.length() != 11) {
                    Toast.makeText(BuyActivity.this, "The confirm mobile number must be 11 digit", Toast.LENGTH_SHORT).show();
                } else if (!CMobile.equalsIgnoreCase(Mobile)) {
                    Toast.makeText(BuyActivity.this, "The mobile number did not match", Toast.LENGTH_SHORT).show();
                } else if ("".equalsIgnoreCase(Mobile)) {
                    Toast.makeText(BuyActivity.this, "mobile number cannot be empty", Toast.LENGTH_SHORT).show();
                } else if ("".equalsIgnoreCase(CMobile)) {
                    Toast.makeText(BuyActivity.this, "confirm mobile cannot be empty", Toast.LENGTH_SHORT).show();
                } else if ("".equalsIgnoreCase(Select)) {
                    Toast.makeText(BuyActivity.this, "bundle cannot be empty", Toast.LENGTH_SHORT).show();
                } else if ("No Selection".equalsIgnoreCase(Select)) {
                    Toast.makeText(BuyActivity.this, "bundle cannot be empty", Toast.LENGTH_SHORT).show();
                } else if ((!isValidNumber(Constants.NetWorkType, Mobile)) && (!disabled.isChecked())) {
                    Toast.makeText(BuyActivity.this, "this is an invalid " + Constants.NetWorkType + " number", Toast.LENGTH_SHORT).show();
                } else if ("".equalsIgnoreCase(amount.getText().toString()) && ("airtime".equalsIgnoreCase(Constants.ServiceType)) && ("vtu_pin".equalsIgnoreCase(ServiceSubType))) {
                    Toast.makeText(BuyActivity.this, "amount cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (("airtime".equalsIgnoreCase(Constants.ServiceType)) && ("vtu_airtime".equalsIgnoreCase(ServiceSubType))) {
                        Price = amount.getText().toString();
                    }
                    //  this is not useful for now...i only need one item in a cart.no need of multi items
                   /* CartDatabaseHandler sdb = new CartDatabaseHandler(BuyActivity.this);
                    sdb.addContact(new CartDBObject(1,Select,Constants.ServiceType,Price,Mobile,"","",BundleID,""));
                    */
                    if (Double.parseDouble(Price) < 50) {
                        Toast.makeText(BuyActivity.this, "Transaction amount too low", Toast.LENGTH_SHORT).show();

                    } else if (Double.parseDouble(Price) <= Double.parseDouble(Constants.Balance)) {

                        Constants.UpBundleID = BundleID;
                        Constants.UpPhone = Mobile;
                        Constants.UpPrice = Price;

                        Constants.UpDiscount = DisCount;
                        //  Toast.makeText(BuyActivity.this,  Constants.UpDiscount, Toast.LENGTH_SHORT).show();






                        Constants.PinpAdTitle = "Old";
                        Constants.runsubmit = 0 ;


                        Intent intent = new Intent(BuyActivity.this, FinalActivity.class);

                        startActivity(intent);






                    } else {
                        Toast.makeText(BuyActivity.this, "Your account balance is too low for this transactions", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        mSimpleListAdapter = new SimpleListAdapter(this, mStringsbundlename);
        mSearchableSpinner1.setAdapter(mSimpleListAdapter);
        mSearchableSpinner1.setOnItemSelectedListener(mOnItemSelectedListener);
        mSearchableSpinner1.setStatusListener(new IStatusListener() {
            @Override
            public void spinnerIsOpening() {

            }

            @Override
            public void spinnerIsClosing() {

            }
        });


        mSimpleListAdapter2 = new SimpleListAdapter(this, mStringsbundlename2);
        mSearchableSpinner2.setAdapter(mSimpleListAdapter2);
        mSearchableSpinner2.setOnItemSelectedListener(mOnItemSelectedListener2);
        mSearchableSpinner2.setStatusListener(new IStatusListener() {
            @Override
            public void spinnerIsOpening() {

            }

            @Override
            public void spinnerIsClosing() {

            }
        });


    }

    private String[] MTN() {

        String[] code = {"0704", "07025", "07026", "0803", "0806", "0703", "0903", "0906", "0706", "0810", "0813", "0814", "0816"};


        return code;
    }

    private String[] AIRTEL() {

        String[] code = {"0802", "0808", "0708", "0812", "0701", "0902", "0907", "0901", "0904"};


        return code;
    }

    private String[] GLO() {

        String[] code = {"0705", "0805", "0807", "0811", "0815", "0905"};

        return code;
    }

    private String[] NINEMOBILE() {

        String[] code = {"0809", "0817", "0818", "0908", "0909",};

        return code;
    }

    private boolean isValidNumber(String Network, String phonenumber) {
        String myNum[] = {};
        if ("mtn".equalsIgnoreCase(Constants.NetWorkType)) {
            myNum = MTN();
        } else if ("glo".equalsIgnoreCase(Constants.NetWorkType)) {
            myNum = GLO();
        } else if ("airtel".equalsIgnoreCase(Constants.NetWorkType)) {
            myNum = AIRTEL();

        } else if ("nine".equalsIgnoreCase(Constants.NetWorkType)) {
            myNum = NINEMOBILE();

        }


        int size = myNum.length;
        boolean keywordpresent = false;
        for (int a = 0; a < size; a++) {
            keywordpresent = false;
            String keyword = myNum[a];
            int keywordlenght = keyword.length();
            String Phonenumbershort = phonenumber.substring(0, keywordlenght);
            if (keyword.equalsIgnoreCase(Phonenumbershort)) {

                keywordpresent = true;
                break;
            }

        }

        return keywordpresent;
    }


    private void initListValues() {
        mStringsprice.add("");
        mStringsbundleid.add(0);
        mStringsdiscount.add("");
        BundleDatabaseHandler localsmsdb = new BundleDatabaseHandler(BuyActivity.this);
        List<BundleDBObject> settingsdata = localsmsdb.getdataContacts();
        for (BundleDBObject cn : settingsdata) {

            if (Constants.COMPANYID.equalsIgnoreCase(cn.getCompanyID())) {
                mStringsprice.add(cn.getPrice());
                mStringsbundlename.add(cn.getBundleName() + " - N" + cn.getPrice());
                mStringsbundleid.add(cn.getBundleID());
                mStringsdiscount.add(cn.getDiscount());
            }
            // Toast.makeText(BuyActivity.this, Constants.COMPANYID+cn.getCompanyID()+"The mobile number must be 11 digit", Toast.LENGTH_SHORT).show();


        }

    }


    private void initListValuespin(String COMPANYID, String BundleSubType) {
        mStringsprice2.add("");
        mStringsbundleid2.add(0);
        mStringsdiscount2.add("");
        BundleDatabaseHandler localsmsdb = new BundleDatabaseHandler(BuyActivity.this);
        List<BundleDBObject> settingsdata = localsmsdb.getpinContacts(COMPANYID, BundleSubType);
        for (BundleDBObject cn : settingsdata) {

            if (Constants.COMPANYID.equalsIgnoreCase(cn.getCompanyID())) {
                mStringsprice2.add(cn.getPrice());
                mStringsbundlename2.add(cn.getBundleName() + " - N" + cn.getPrice());
                mStringsbundleid2.add(cn.getBundleID());
                mStringsdiscount2.add(cn.getDiscount());
            }


        }


    }


    private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
            if (position > 0) {
                Select = mSimpleListAdapter.getItem(position).toString();
                Price = mStringsprice.get(position);
                BundleID = Integer.toString(mStringsbundleid.get(position));
                DisCount = mStringsdiscount.get(position);
            } else {
                Select = "No Selection";
                Toast.makeText(BuyActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
            //  Toast.makeText(BuyActivity.this, Price+"Item on position " + position + " : " + Select+ " Selected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected() {
            Select = "No Selection";
            Toast.makeText(BuyActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();

        }
    };


    private OnItemSelectedListener mOnItemSelectedListener2 = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
            if (position > 0) {
                Select = mSimpleListAdapter2.getItem(position).toString();
                Price = mStringsprice2.get(position);
                BundleID = Integer.toString(mStringsbundleid2.get(position));
                DisCount = mStringsdiscount2.get(position);
            } else {
                Select = "No Selection";
                Toast.makeText(BuyActivity.this, Constants.COMPANYID + "Nothing Selected" + ServiceSubType, Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(BuyActivity.this, Price + "Item on position " + position + " : " + Select + " Selected" + BundleID, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected() {
            Select = "No Selection";
            Toast.makeText(BuyActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();

        }
    };

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Contacts access needed");
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setMessage("please confirm Contacts access");
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Only call the permission request api on Android M
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CONTACT);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                  getContact();
                    Toast.makeText(getApplicationContext(), "Permission is Done", Toast.LENGTH_LONG).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(getApplicationContext(), "No permission for contacts", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                    showDialog();
                }
                showDialog();
            }
        } else {
            showDialog();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



/*
    private Flashbar fancyFlashbar(int No) {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .enableSwipeToDismiss() //By default this feature is disabled
                .icon(R.drawable.email)
                .title("Your Cart")
                .message("You already have "+No+" item in your cart")
                .showIcon()
                .positiveActionText("View Cart")
                //.negativeActionText("No, other time")
                .positiveActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        Intent intent = new Intent(BuyActivity.this,Cart.class);
                        startActivity(intent);
                        bar.dismiss();


                    }
                })
                /* .negativeActionTapListener(new Flashbar.OnActionTapListener() {
                      @Override
                      public void onActionTapped(@NotNull Flashbar bar) {
                          bar.dismiss();
                      }
                  })*/ /*
                .enterAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(550)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(500)
                        .alpha()
                        .overshoot())
                .duration(30000)
                //.vibrateOn(Flashbar.Vibration.SHOW, Flashbar.Vibration.DISMISS)
                //.showOverlay()
                //.titleColorRes(R.color.white)
                //.titleSizeInSp(28)
                //.titleTypeface(Typeface.createFromAsset(getAssets(), "BeautifulAndOpenHearted.ttf"))
                //.messageColor(ContextCompat.getColor(this, R.color.white))
                //.messageSizeInSp(24)
                //.messageTypeface(Typeface.createFromAsset(getAssets(), "BeautifulAndOpenHearted.ttf"))
                //.overlayColorRes(R.color.modal)
                //.positiveActionTextColorRes(R.color.black)
                //.negativeActionTextColorRes(R.color.yellow)
                /*.iconAnimation(FlashAnim.with(this)
                        .animateIcon()
                        .pulse()
                        .alpha()
                        .duration(750)
                        .accelerate())*/
                /*.enterAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(400)
                        .slideFromLeft()
                        .overshoot())
                .exitAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(250)
                        .slideFromLeft()
                        .accelerate())*//*
                .build();
    }
*/


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
// check whether the result is ok
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForResult
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("ContactFragment", "Failed to pick contact");
        }
    }

    private void contactPicked(Intent data) {

        Uri uri = data.getData();

        Cursor cursor;
        ContentResolver cr = this.getContentResolver();

        try {
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (null != cur && cur.getCount() > 0) {
                cur.moveToFirst();
                for (String column : cur.getColumnNames()) {
                    Log.i(TAG, "contactPicked() Contacts column " + column + " : " + cur.getString(cur.getColumnIndex(column)));
                }
            }

            if (cur.getCount() > 0) {
                //Query the content uri
                cursor = this.getContentResolver().query(uri, null, null, null, null);

                if (null != cursor && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    for (String column : cursor.getColumnNames()) {
                        Log.i(TAG, "contactPicked() uri column " + column + " : " + cursor.getString(cursor.getColumnIndex(column)));
                    }
                }

                cursor.moveToFirst();
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                String contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                Log.i(TAG, "contactPicked() uri contact id " + contact_id);
                // column index of the contact name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                // column index of the phone number
                String phoneNo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //get Email id of selected contact....


                Cursor cur1 = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contact_id}, null);

                if (null != cur1 && cur1.getCount() > 0) {
                    cur1.moveToFirst();
                    for (String column : cur1.getColumnNames()) {
                        Log.i(TAG, "contactPicked() Email column " + column + " : " + cur1.getString(cur1.getColumnIndex(column)));
                        email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }

                    //HERE YOU GET name, phoneno & email of selected contact from contactlist.....
                    Log.e("setcontactDetails", "::>>" + name + "\nPhoneno:" + phoneNo + "\nEmail: " + email);
                  //  Toast.makeText(BuyActivity.this, "\nPhoneno:" + phoneNo, Toast.LENGTH_SHORT).show();
                    mobile.setText(phoneNo.replaceAll("\\s+",""));
                    cmobile.setText(phoneNo.replaceAll("\\s+",""));
                } else {
                   mobile.setText(phoneNo.replaceAll("\\s+",""));
                    cmobile.setText(phoneNo.replaceAll("\\s+",""));
                //    Toast.makeText(BuyActivity.this, phoneNo, Toast.LENGTH_SHORT).show();
                }
                Log.e("setcontactDetails", "::>>" + name + "\nPhoneno:" + phoneNo + "\nEmail: " + email);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

}
