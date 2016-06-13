package com.zibbeo.phototrimbree.Account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.zibbeo.phototrimbree.AXSessionAuthentification;
import com.zibbeo.phototrimbree.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by samchaw on 5/18/16 AD.
 */
public class connectionTask extends AsyncTask<String, String, String> {

    HttpsURLConnection connection = null;
    Context mContext;
    int mDoWhat;
    AXSessionAuthentification mAuthen;
    String mEmail, mPassword;

    public connectionTask(Context context, int doWhat) {
        mContext = context;
        mDoWhat = doWhat;
        mAuthen = new AXSessionAuthentification(mContext);
    }

    @Override
    protected String doInBackground(String... params) {

        String tUrlParameter = null;
        mEmail = params[1];
        mPassword = params[2];

        //set parameter to send to server
        if (mDoWhat == R.string.doSignIn || mDoWhat == R.string.doSignUp) {
            tUrlParameter = "login=" + params[1] + "&password=" + params[2];
        } else if (mDoWhat == R.string.doPasswordForgot) {
            tUrlParameter = "email=" + params[1];
        } else if (mDoWhat == R.string.doModify) {
            tUrlParameter = "login=" + params[1] + "&old_password=" + mAuthen.getPassword() + "&password=" + params[2];
        }

        Log.i("URLP", params[3] + " " + tUrlParameter);

        try {
            //set up connection
            URL url = new URL(params[0]);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(1000);
            connection.setConnectTimeout(15000);
            connection.setRequestProperty("os", "android");
            connection.setRequestProperty("version", params[3]);
            if (mDoWhat == R.string.doModify)
                connection.setRequestProperty("apikey", mAuthen.getToken());
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);


            //write data to server
            OutputStream stream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
            writer.write(tUrlParameter);
            writer.flush();
            writer.close();
            stream.close();

            connection.connect();

            int responseCode = connection.getResponseCode();

            //check connection status
            if (responseCode == 200) {

                //get response object
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                reader.close();

                return stringBuilder.toString();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception ex) {
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String sStringBuilder) {
        super.onPostExecute(sStringBuilder);

        //create json response
        try {
            if (sStringBuilder != null) {
                JSONObject tJsonObject = new JSONObject(sStringBuilder);

                switch (mDoWhat) {
                    case R.string.doSignIn: //do sign in
                        signinResponse(tJsonObject);
                        break;
                    case R.string.doPasswordForgot: //do password forgot
                        passwordForgotResponse(tJsonObject);
                        break;
                    case R.string.doSignUp: //do sign up
                        signupResponse(tJsonObject);
                        break;
                    case R.string.doModify: //do modify
                        signupResponse(tJsonObject);
                        break;
                    default:
                        break;
                }

            } else {
                Log.i("Error", "sStringBuilder is null");
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //sign in response
    private void signinResponse(JSONObject sJsonObject) throws JSONException {
        if (sJsonObject.getBoolean("error")) {
            //set authentification
            mAuthen.clearAllData();
            mAuthen.setStatus(R.string.statusNoConnect);
            //region error code
            if (sJsonObject.getString("code_error").equals("UPD01")) {

                Toast.makeText(mContext, "WARNING UPDATE!!!!!",
                        Toast.LENGTH_LONG).show();

            } else if (sJsonObject.getString("code_error").equals("LOG01")) {
                System.out.println(sJsonObject);
                //close
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
                //////print
                Toast.makeText(mContext, R.string.error_LOG01,
                        Toast.LENGTH_LONG).show();

            } else if (sJsonObject.getString("code_error").equals("LOG02")) {
                System.out.println(sJsonObject);
                //close
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
                //////print
                Toast.makeText(mContext, R.string.error_LOG02,
                        Toast.LENGTH_LONG).show();

            } else if (sJsonObject.getString("code_error").equals("LOG03")) {
                System.out.println(sJsonObject);
                //close
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
                //////print
                Toast.makeText(mContext, R.string.error_LOG03,
                        Toast.LENGTH_LONG).show();

            } else if (sJsonObject.getString("code_error").equals("LOG04")) {
                System.out.println(sJsonObject);
                //close
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
                //////print
                Toast.makeText(mContext, R.string.error_LOG04,
                        Toast.LENGTH_LONG).show();

            } else if (sJsonObject.getString("code_error").equals("LOG05")) {
                System.out.println(sJsonObject);
                //close
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
                //////print
                Toast.makeText(mContext, R.string.error_LOG05,
                        Toast.LENGTH_LONG).show();
            }
            //endregion
            mAuthen.print();
        } else {
            //set authentification
            mAuthen.clearAllData();
            mAuthen.setStatus(R.string.statusConnected);
            mAuthen.setType(R.string.typeLoginPasswordURL);
            mAuthen.setEmail(mEmail);
            mAuthen.setPassword(mPassword);
            mAuthen.setToken(sJsonObject.getString("apikey"));
            //go
            Intent intent = new Intent(mContext, ZPTAccountInfoView.class);
            mContext.startActivity(intent);
            //////print
            Toast.makeText(mContext, "Sign in complete.",
                    Toast.LENGTH_LONG).show();

            mAuthen.print();
        }
    }

    //password forgot response
    private void passwordForgotResponse(JSONObject sJsonObject) throws JSONException {
        if (sJsonObject.getBoolean("error")) {
            if (sJsonObject.getString("code_error").equals("UPD01")) {
//                Intent intent = new Intent(mContext, ZPTAccountForgotView.class);
//                mContext.startActivity(intent);
                Toast.makeText(mContext, "WARNING UPDATE!!!!!",
                        Toast.LENGTH_LONG).show();
            } else if (sJsonObject.getString("code_error").equals("REG0x")) {
                System.out.println(sJsonObject);
//                Intent intent = new Intent(mContext, ZPTAccountForgotView.class);
//                mContext.startActivity(intent);
                Toast.makeText(mContext, R.string.error_REG0x,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            //ask password complete
//            Intent intent = new Intent(mContext, ZPTAccountInfoView.class);
//            mContext.startActivity(intent);
            Toast.makeText(mContext, "Password request complete.",
                    Toast.LENGTH_LONG).show();
        }
    }

    //sign up response
    private void signupResponse(JSONObject sJsonObject) throws JSONException {
        //if error print error type
        if (sJsonObject.getBoolean("error")) {
            //set authentification
            if (mDoWhat == R.string.doSignUp) {
                mAuthen.clearAllData();
                mAuthen.setStatus(R.string.statusNoConnect);
            }
            //region error code
            if (sJsonObject.getString("code_error").equals("UPD01")) {
                //close
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
                //////print
                Toast.makeText(mContext, "WARNING UPDATE!!!!!",
                        Toast.LENGTH_LONG).show();

            } else if (sJsonObject.getString("code_error").equals("REG01")) {
                System.out.println(sJsonObject);
                //close
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
                //////print
                Toast.makeText(mContext, R.string.error_REG01,
                        Toast.LENGTH_LONG).show();

            } else if (sJsonObject.getString("code_error").equals("REG07")) {
                System.out.println(sJsonObject);
                //close
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
                //////print
                Toast.makeText(mContext, R.string.error_REG07,
                        Toast.LENGTH_LONG).show();

            } else if (sJsonObject.getString("code_error").equals("REG012")) {
                System.out.println(sJsonObject);
                //close
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
                //////print
                Toast.makeText(mContext, R.string.error_REG012,
                        Toast.LENGTH_LONG).show();

            } else if (sJsonObject.getString("code_error").equals("REG013")) {
                System.out.println(sJsonObject);
                //close
                Intent intent = new Intent(ZPTAccountConnectionWaitingView.ACTION_CLOSE);
                mContext.sendBroadcast(intent);
                //////print
                Toast.makeText(mContext, R.string.error_REG013,
                        Toast.LENGTH_LONG).show();
            }
            //endregion
            mAuthen.print();
        } else {
            //set authentification
            mAuthen.clearAllData();
            mAuthen.setStatus(R.string.statusConnected);
            mAuthen.setType(R.string.typeLoginPasswordURL);
            mAuthen.setEmail(mEmail);
            mAuthen.setPassword(mPassword);
            mAuthen.setToken(sJsonObject.getString("apikey"));

            if (mDoWhat == R.string.doSignUp) {//sign up complete
                Toast.makeText(mContext, "Sign up complete.",
                        Toast.LENGTH_LONG).show();

            } else if (mDoWhat == R.string.doModify) {//modify complete
                Toast.makeText(mContext, "Modification complete.",
                        Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(mContext, ZPTAccountInfoView.class);
            mContext.startActivity(intent);

            mAuthen.print();
        }
    }
}
