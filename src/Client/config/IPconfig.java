package Client.config;

public class IPconfig {
    private static String ip = "192.168.177.211";
    private static String port = "5000";

    public IPconfig(){
    }

    public static String getAddress(){
        String address = ip+":"+port;
        return address;
    }
}