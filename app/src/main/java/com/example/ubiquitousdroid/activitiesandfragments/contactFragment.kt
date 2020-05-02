package com.example.ubiquitousdroid.activitiesandfragments

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.ubiquitousdroid.R
import com.example.ubiquitousdroid.adapters.phoneContactAdapter
import com.example.ubiquitousdroid.models.contactObject
import com.example.ubiquitousdroid.utils.CommonTag
import com.example.ubiquitousdroid.utils.fileUtil
import com.example.ubiquitousdroid.viewModels.filesViewModel
import com.example.ubiquitousdroid.viewModels.getImagesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_contact.*

class contactFragment : Fragment() {
    val MY_PERMISSIONS_REQUEST_READ_CONTACTS =7
    companion object{
        const val filrName="phoneContact.csv"
        lateinit var allContacts: ArrayList<contactObject>
    }
    private lateinit var viewModel :filesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(CommonTag," onview created called  ")
        getThePermission()
        viewModel= ViewModelProviders.of(
            this
        ).get(filesViewModel::class.java)

        btn_zip.setOnClickListener(View.OnClickListener {
//            fileUtil.saveAFileWithName("phoneContact.csv",allContacts)  // csv and zip conversion in some place
            viewModel.GenerateAndZipFile()
        })
    }

    private fun getThePermission() {
        Log.d(CommonTag," asking permission function")
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                        android.Manifest.permission.READ_CONTACTS)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(activity!!,
                         arrayOf(android.Manifest.permission.READ_CONTACTS,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS)
                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                }
            }
        }else{
            // permission is already there do the work
            getTheContacts()
        }
    }

    private fun getTheContacts() {
        Log.d(CommonTag,  "getting contact called ")
        allContacts=ArrayList<contactObject>()
        val phones = activity!!.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        while (phones!!.moveToNext()) {
            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contactModel = contactObject(name,"0",phoneNumber)
            allContacts.add(contactModel)
//            Log.d(CommonTag, name + "  " + phoneNumber)
        }
        phones.close()

        val customAdapter = phoneContactAdapter(context!!, allContacts)
        listView!!.adapter = customAdapter
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        Log.d(CommonTag," onrequest result called ")
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(context,"granted ",Toast.LENGTH_SHORT).show();
                    getTheContacts()
                } else {
                    Snackbar.make(view!!," please grant the permission ",Snackbar.LENGTH_LONG)
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

}
