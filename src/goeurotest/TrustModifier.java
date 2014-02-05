/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

import java.net.*;
import java.security.*;
import java.security.cert.*;
import javax.net.ssl.*;

public class TrustModifier
{
   private static final TrustingHostnameVerifier TRUSTING_HOSTNAME_VERIFIER = new TrustingHostnameVerifier();
   
   private static SSLSocketFactory factory;

   /** Call this with any HttpURLConnection, and it will 
    modify the trust settings if it is an HTTPS connection */
   public static void relaxHostChecking(HttpURLConnection conn) 
       throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
   {   
      if (conn instanceof HttpsURLConnection)
      {
         HttpsURLConnection httpsConnection = (HttpsURLConnection) conn;
         SSLSocketFactory factory = prepFactory(httpsConnection);
         httpsConnection.setSSLSocketFactory(factory);
         httpsConnection.setHostnameVerifier(TRUSTING_HOSTNAME_VERIFIER);
      }
   }

   static synchronized SSLSocketFactory prepFactory(HttpsURLConnection httpsConnection) 
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException
   {
      if (factory == null)
      {
         SSLContext ctx = SSLContext.getInstance("TLS");
         ctx.init(null, new TrustManager[]{ new AlwaysTrustManager() }, null);
         factory = ctx.getSocketFactory();
      }
      return factory;
   }
   
   private static final class TrustingHostnameVerifier implements HostnameVerifier
   {
        @Override
        public boolean verify(String hostname, SSLSession session)
        {
            return true;
        }
   }

   private static class AlwaysTrustManager implements X509TrustManager
   {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { }
        
        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { }
        
        @Override
        public X509Certificate[] getAcceptedIssuers() { return null; }      
   }   
}

