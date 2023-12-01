package Interfaces;

import java.io.IOException;

public interface ApiClient {
    /**
     * Sends a GET request to the specified endpoint.
     *
     * @param endpoint the endpoint to send the request to
     * @return the response from the server
     * @throws IOException if an I/O error occurs
     */
    String get(String endpoint) throws IOException;

    /**
     * Sends a POST request to the specified endpoint with the given body.
     *
     * @param endpoint the endpoint to send the request to
     * @param body the body of the request
     * @return the response from the server
     * @throws IOException if an I/O error occurs
     */
    String post(String endpoint, String body) throws IOException;
}
