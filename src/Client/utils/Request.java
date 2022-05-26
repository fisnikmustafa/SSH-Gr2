package Client.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import Client.models.Student;
import Client.config.IPconfig;
import com.google.gson.Gson;


public class Request {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private JSONProcessor studentJsonProcessor = new JSONProcessor<Student>(Student.class);


    public int POST(String url, String[] keys, String[] values) throws IOException, InterruptedException {
        Map<Object, Object> data = new HashMap<>();

        for (int i=0; i<keys.length; i++){
            data.put(keys[i], values[i]);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create(url))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response: " + response);
        return response.statusCode();
    }

    public int changeStatus(String email, int status) throws IOException, InterruptedException {
        String data = "status=" + status;
        String uri = "http://" + IPconfig.getAddress() + "/api/v1/students/" + email;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(data))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        return response.statusCode();
    }

    public Student getStudent(String email) throws IOException, InterruptedException {
        String url = "http://" + IPconfig.getAddress() + "/api/v1/students/email=" + email;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //the response will come as a json array with a single object, so we need to remove the opening and closing brackets
        StringBuilder responseSb = new StringBuilder(response.body());
        if(responseSb.charAt(0) == '['){
            responseSb.deleteCharAt(0);
        }
        if(responseSb.charAt(responseSb.length()-1) == ']'){
            responseSb.deleteCharAt(responseSb.length()-1);
        }


        Student student = (Student)studentJsonProcessor.deserialize(responseSb.toString());
        return student;
    }

    public Student[] getActiveStudents() throws IOException, InterruptedException {
        String url = "http://" + IPconfig.getAddress() + "/api/v1/students/active/id=" + SessionManager.student.getId();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        Student[] activeStudents = gson.fromJson(response.body(), Student[].class);
        return activeStudents;
    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}
