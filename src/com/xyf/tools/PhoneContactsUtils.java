package com.xyf.tools;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import com.xyf.model.ContactsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shxiayf on 2015/6/23.
 */
public class PhoneContactsUtils {

    private static final String TAG = PhoneContactsUtils.class.getSimpleName();

    public static List<?> getPhoneContacts(Context mContext){
        List<ContactsModel> contactsModels = new ArrayList<ContactsModel>();

        Cursor cursor = mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        int contactIdIndex = 0;
        int nameIndex = 0;

        if (cursor.getCount() > 0){
            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while (cursor.moveToNext()){
            ContactsModel tempModel = new ContactsModel();
            String contactId = cursor.getString(contactIdIndex);
            String name = cursor.getString(nameIndex);
            tempModel.setName(name);
             /*
             * 查找该联系人的phone信息
             */
            Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null, null);
            int phoneIndex = 0;
            if(phones.getCount() > 0) {
                phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                if (phones.moveToFirst()){
                    String phoneNumber = phones.getString(phoneIndex);
                    Log.i(TAG, phoneNumber);
                    tempModel.setPhone(phoneNumber);
                }
            }

            /*
             * 查找该联系人的email信息
             */
            Cursor emails = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId,
                    null, null);
            int emailIndex = 0;
            if(emails.getCount() > 0) {
                emailIndex = emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                if (emails.moveToFirst()){
                    String email = emails.getString(emailIndex);
                    Log.i(TAG, email);
                    tempModel.setEmail(email);
                }
            }

            //im
            Cursor Ims  = mContext.getContentResolver().query(ContactsContract.Data.CONTENT_URI,new String[] { ContactsContract.Data._ID, ContactsContract.CommonDataKinds.Im.PROTOCOL, ContactsContract.CommonDataKinds.Im.DATA },
                    ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE + "'",
                    new String[] { contactId }, null);
            if (Ims.getCount() > 0){
                if (Ims.moveToFirst()){
                    int protocol = Ims.getInt(Ims
                            .getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL));
                    String date = Ims
                            .getString(Ims.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));

                    if (protocol == ContactsContract.CommonDataKinds.Im.PROTOCOL_MSN){
                        tempModel.setMsn(date);
                    }

                    if (protocol == ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ){
                        tempModel.setQq(date);
                    }
                }
            }


            Cursor address = getContentResolver()
                    .query(
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = " + contactId, null, null);
            if (address.moveToFirst()) {
                do {
                    // 遍历所有的地址
                    String street = address
                            .getString(address
                                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                    String city = address
                            .getString(address
                                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                    String region = address
                            .getString(address
                                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                    String postCode = address
                            .getString(address
                                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                    String formatAddress = address
                            .getString(address
                                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                    Log.i("street", street);
                    Log.i("city", city);
                    Log.i("region", region);
                    Log.i("postCode", postCode);
                    Log.i("formatAddress", formatAddress);
                } while (address.moveToNext());
            }

            // 获取该联系人组织
            Cursor organizations = getContentResolver().query(
                    Data.CONTENT_URI,
                    new String[] { Data._ID, Organization.COMPANY,
                            Organization.TITLE },
                    Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
                            + Organization.CONTENT_ITEM_TYPE + "'",
                    new String[] { contactId }, null);
            if (organizations.moveToFirst()) {
                do {
                    String company = organizations.getString(organizations
                            .getColumnIndex(Organization.COMPANY));
                    String title = organizations.getString(organizations
                            .getColumnIndex(Organization.TITLE));
                    Log.i("company", company);
                    Log.i("title", title);
                } while (organizations.moveToNext());
            }

            // 获取备注信息
            Cursor notes = getContentResolver().query(
                    Data.CONTENT_URI,
                    new String[] { Data._ID, Note.NOTE },
                    Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
                            + Note.CONTENT_ITEM_TYPE + "'",
                    new String[] { contactId }, null);
            if (notes.moveToFirst()) {
                do {
                    String noteinfo = notes.getString(notes
                            .getColumnIndex(Note.NOTE));
                    Log.i("noteinfo", noteinfo);
                } while (notes.moveToNext());
            }

            // 获取nickname信息
            Cursor nicknames = getContentResolver().query(
                    Data.CONTENT_URI,
                    new String[] { Data._ID, Nickname.NAME },
                    Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
                            + Nickname.CONTENT_ITEM_TYPE + "'",
                    new String[] { contactId }, null);
            if (nicknames.moveToFirst()) {
                do {
                    String nickname_ = nicknames.getString(nicknames
                            .getColumnIndex(Nickname.NAME));
                    Log.i("nickname_", nickname_);
                } while (nicknames.moveToNext());
            }
            
        }


        return contactsModels;
    }

}
