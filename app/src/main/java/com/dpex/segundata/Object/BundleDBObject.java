package com.dpex.segundata.Object;
public class BundleDBObject {
    //private variables
    int _id;
  int _bundleid;
    String _bundlename;
    String _companyid;
    String _price;
    String _bundletype;
    String _bundlesubtype;
String _discount;
    String _delete;
    // Empty constructor
    public BundleDBObject(){

    }
    // constructor
    public BundleDBObject(int id, int bundleid, String bundlename,  String price, String discount,String companyid,String bundletype,String bundlesubtype, String delete){
        this._id = id;
        this._bundleid = bundleid;
        this._bundlename =bundlename;
      this._price= price;
      this._companyid = companyid;
      this._bundletype = bundletype;
        this._bundlesubtype = bundlesubtype;
        this._delete = delete;
        this._discount = discount;
    }

    // constructor
    public BundleDBObject(int bundleid, String bundlename, String price,String discount, String companyid,String bundletype,String bundlesubtype, String delete){
        this._bundleid = bundleid;
        this._bundlename =bundlename;
        this._price= price;
        this._companyid = companyid;
        this._bundletype = bundletype;
        this._bundlesubtype = bundlesubtype;
        this._delete = delete;
        this._discount = discount;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public int getBundleID(){
        return this._bundleid;
    }

    public void setBundleID(int bundleid){
        this._bundleid = bundleid;
    }


    public String getDiscount(){
        return this._discount;
    }

    public void setDiscount(String  discount){
        this._discount = discount;
    }



    public String getBundleName(){
        return this._bundlename;
    }

    public void setBundleName(String bundlename){
        this._bundlename =bundlename;
    }

    public String getBundleType(){
        return this._bundletype;
    }

    public void setBundleType(String bundletype){
        this._bundletype =bundletype;
    }

    public String getBundleSubType(){
        return this._bundlesubtype;
    }

    public void setBundleSubType(String bundlesubtype){
        this._bundlesubtype =bundlesubtype;
    }


    public String getPrice(){
        return this._price;
    }

    public void setPrice(String price){
        this._price =price;
    }



    public String getCompanyID(){
        return this._companyid;
    }

    public void setCompanyID(String companyid){
        this._companyid = companyid;
    }



    public String getDelete(){
        return this._delete;
    }

    public void setDelete(String delete){
        this._delete = delete;
    }

}