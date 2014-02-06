/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

import java.net.*;
import java.security.*;
import java.security.cert.*;
import javax.net.ssl.*;

/**
 * Service class providing calls dedicated to relax SSL certificates validation
 * @author carey
 */
public class TrustModifier
{
   private static final TrustingHostnameVerifier TRUSTING_HOSTNAME_VERIFIER = new TrustingHostnameVerifier();
   
   /**
    * Unique instance of a SSL socket factory, which trusts every client
    * and certificates, used to relax the certificate validation when opening
    * a SSL connection
    */
   private static SSLSocketFactory factory;

   /**
    * Call this with any HttpURLConnection, and it will 
    * modify the trust settings if it is an HTTPS connection
    */
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

   /**
    * Returns the unique instance of a SSL socket factory which accepts every
    * client and certificate
    * @param httpsConnection The HTTPS connection the application is trying to
    *                        connect to
    * @return @see method description
    * @throws NoSuchAlgorithmException
    * @throws KeyStoreException
    * @throws KeyManagementException 
    */
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
   
   /**
    * Service class which trusts any hostname
    */
   private static final class TrustingHostnameVerifier implements HostnameVerifier
   {
        @Override
        public boolean verify(String hostname, SSLSession session)
        {
            return true;
        }
   }

   /**
    * Service class which validates every certificate
    */
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

