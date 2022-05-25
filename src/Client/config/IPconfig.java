package Client.config;

public class IPconfig {
    private static String ip = "localhost";
    private static String port = "5000";

    public IPconfig(){
    }

    public static String getAddress(){
        String address = ip+":"+port;
        return address;
    }
}