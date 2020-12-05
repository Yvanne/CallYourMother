//package com.example.callyourmother
////
//
//import android.Manifest
//import android.content.Context
//import android.content.pm.PackageManager
//import android.database.Cursor
//import android.provider.CallLog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import java.util.*
//
//
////
//class LogInfo : AppCompatActivity() {
//    //
////    private val sb = StringBuffer()
////    private val managedCursor: Cursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null)
////    private val number: Int = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
////    private val type: Int = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
////    private val date: Int = managedCursor.getColumnIndex(CallLog.Calls.DATE)
////    private val duration: Int = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
////
//////    sb.append("Call Log :")
//////    while (managedCursor.moveToNext()) {
//////        val phNumber: String = managedCursor.getString(number)
//////        val callType: String = managedCursor.getString(type)
//////        val callDate: String = managedCursor.getString(date)
//////        val callDayTime = Date(Long.valueOf(callDate))
//////        val callDuration: String = managedCursor.getString(duration)
//////        var dir: String? = null
//////        val dircode = callType.toInt()
//////        when (dircode) {
//////            CallLog.Calls.OUTGOING_TYPE -> dir = "OUTGOING"
//////            CallLog.Calls.INCOMING_TYPE -> dir = "INCOMING"
//////            CallLog.Calls.MISSED_TYPE -> dir = "MISSED"
//////        }
//////        sb.append("\nPhone Number:--- $phNumber \nCall Type:--- $dir \nCall Date:--- $callDayTime \nCall duration in sec :--- $callDuration")
//////        sb.append("\n----------------------------------")
//////    } //managedCursor.close();
//////
//////    textView.setText(sb)
////    sb.append("Call Log :");
////
////    while (managedCursor.moveToNext()) {
////        String phNumber = managedCursor.getString(number);
////        String callType = managedCursor.getString(type);
////        String callDate = managedCursor.getString(date);
////        Date callDayTime = new Date(Long.valueOf(callDate));
////        String callDuration = managedCursor.getString(duration);
////        String dir = null;
////        int dircode = Integer.parseInt(callType);
////        switch (dircode) {
////            case CallLog.Calls.OUTGOING_TYPE:
////            dir = "OUTGOING";
////            break;
////            case CallLog.Calls.INCOMING_TYPE:
////            dir = "INCOMING";
////            break;
////            case CallLog.Calls.MISSED_TYPE:
////            dir = "MISSED";
////            break;
////        }
////        sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration);
////        sb.append("\n----------------------------------");
////    } //managedCursor.close();
////    textView.setText(sb);
////}
//
//
//
////https://stackoverflow.com/questions/47429015/crash-requires-android-permission-read-call-log-or-android-permission-write-ca
//fun getCallLog(id: Long, service: CallLogService): ArrayList<CallLogModel> {
//    val callLogList = ArrayList<CallLogModel>()
//    if (!isCallPermissionEnabled(service)) {
//        return callLogList
//    }
//    var mCursor: Cursor? = null
//    try {
//        val args = arrayOf(id.toString())
//        if (id != 0L) {
//            mCursor = UCApplication
//                .getInstance()
//                .applicationContext
//                .contentResolver
//                .query(
//                    CallLog.Calls.CONTENT_URI,
//                    null,
//                    "_id > ? ",
//                    args,
//                    null
//                )
//        } else {
//            mCursor = UCApplication
//                .getInstance()
//                .applicationContext
//                .contentResolver
//                .query(
//                    CallLog.Calls.CONTENT_URI,
//                    null,
//                    null,
//                    null,
//                    CallLog.Calls._ID + " DESC" + " LIMIT 200 "
//                )
//        }
//    } catch (e: SecurityException) {
//        //
//    }
//    //
//    return callLogList
//}
//
//    fun isCallPermissionEnabled(context: Context?): Boolean {
//        if (context == null || ContextCompat.checkSelfPermission(context, PERMISSIONS)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            return false
//        }
//        return true
//    }
//
//    private fun checkCallLogPermission() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.READ_CALL_LOG
//            ) != PackageManager.PERMISSION_GRANTED
//            && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.WRITE_CALL_LOG
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    Manifest.permission.READ_CALL_LOG,
//                    Manifest.permission.WRITE_CALL_LOG
//                ),
//                PHONE_LOG
//            )
//            return
//        }
//    }
//}