package ao.it.chandsoft.coronavirus.util;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Nelson Chandimba da Silva
 */

public class UrlUtils {
    
    public static Response executeUrl(final String URL) throws IOException {
        OkHttpClient httpCliente = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(URL)
                .method("GET", null)
                .build();

        return httpCliente.newCall(request).execute();
    }

}
