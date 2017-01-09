package com.example.newspaperapp;

/**
 * Created by Ver on 1/8/2017.
 */

public class DumbData {

    ObjectItem[] objectItemData;

    public DumbData() {
        objectItemData = new ObjectItem[20];
        for(int i = 0; i < objectItemData.length ; i++){
            objectItemData[i]= new ObjectItem(i, "Look at this dog!", R.drawable.doggy );
        }
    }

    public ObjectItem[] getObjectItemData(){
        return objectItemData;
    }
}
