package net.coyne.authserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.TimeZone;
 
/**
 * The main authentificcation class for our servers.
 * @author Tim
 *
 */

public class AuthServer
{
 
    private static Socket socket;
    private static String loggerFormat = null;
    
    /**
    *
    * Return the prefix in the form of a string of the logger.
    * @return Logger Format as String
    */
    
    public static String getLoggerFormat() {
        return loggerFormat;
    }
 
    public static void main(String[] args)
    {
    	try
        {
            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(">> Tidal Services started on 127.0.0.1:" + port);
 
            while(true)
            {
            	loggerFormat = "[" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR) + "] @ " + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);
                if (Calendar.getInstance().get(Calendar.MONTH) == 0) {
                	loggerFormat = "[" + 1 + "/" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR) + "] @ " + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);
                }
            	
                //Reading the message from the client
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String number = br.readLine();
                System.out.println( loggerFormat + " [INFO] " + "Received packet request from " + socket.getInetAddress() + ":" + socket.getPort() + ", processing..." );
 
                String returnMessage = "0";
                try
                {	
                	if ( AuthFormat.isAuthRequest(number) == true ) {
                		returnMessage = String.valueOf(Authenticator.authenticate(number.substring(0, number.lastIndexOf("&password?=")), number.substring(number.lastIndexOf("&password?=") + 11, number.lastIndexOf("&authrequest?=" + ProtocolConstants.PROTOCOL)))) + "\n";
                	} else {
                		if ( number.equals("format&protocol?=" + ProtocolConstants.PROTOCOL)) {
                			returnMessage = "USERNAME&password?=PASSWORD&authrequest?=PROTOCOL_ID";
                		}
                	}
                } catch(Exception e) {
                	e.printStackTrace();
                	returnMessage = "-1";
                }
 
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                
                System.out.println( loggerFormat + " [INFO] " + "Response to login packet from " + socket.getInetAddress() + ":" + socket.getPort() + " returned as " + returnMessage );
                bw.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
                System.out.println(">> Shut down Tidal Services, Goodbye!");
            }
            catch(Exception e){}
        }
    }
}