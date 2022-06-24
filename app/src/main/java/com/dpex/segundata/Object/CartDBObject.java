package com.dpex.segundata.Object;
public class CartDBObject {
    //private variables
    int _id;
    String _networktype;
    String _servicetype;
    String _price;
    String _mobile;
    String _code;
    String _unit ;
    String _bundleid;
    String _delete ;
    // Empty constructor
    public CartDBObject(){

    }
    // constructor
    public CartDBObject(int id, String networktype, String servicetype, String price, String mobile, String code, String unit,String bundleid, String delete){
        this._id = id;
        this._networktype = networktype;
        this._servicetype =servicetype;
      this._price= price;
      this._mobile = mobile;
    this._code = code;
        this._unit = unit;
        this._bundleid=bundleid;
        this._delete = delete;

    }

    // constructor
    public CartDBObject(String networktype, String servicetype, String price, String mobile, String code, String unit,String bundleid,String delete){
        this._networktype = networktype;
        this._servicetype =servicetype;
        this._price= price;
        this._mobile = mobile;
        this._code = code;
        this._unit = unit;
        this._bundleid=bundleid;
        this._delete = delete;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getNetworkType(){
        return this._networktype;
    }

    public void setNetworkType(String networkType){
        this._networktype = networkType;
    }


    public String getServiceType(){
        return this._servicetype;
    }

    public void setServiceType(String serviceType){
        this._servicetype =serviceType;
    }


    public String getPrice(){
        return this._price;
    }

    public void setPrice(String price){
        this._price =price;
    }



    public String getCode(){
        return this._code;
    }

    public void setCode(String code){
        this._code = code;
    }


    public String getUnit(){
        return this._unit;
    }

    public void setUnit(String  unit){
        this._unit =unit;
    }


    public String getMobile(){
        return this._mobile;
    }

    public void setMobile(String mobile){
        this._mobile = mobile;
    }



    public String getBundleID(){
        return this._bundleid;
    }

    public void setBundleID(String bundleid){
        this._bundleid = bundleid;
    }



    public String getDelete(){
        return this._delete;
    }

    public void setDelete(String delete){
        this._delete = delete;
    }

}