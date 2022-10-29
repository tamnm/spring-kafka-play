package com.example.templateservice.http;

import com.example.templateservice.function.ConsumerThrow;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;

public class StreamResponse implements StreamingResponseBody {
    private final ConsumerThrow<OutputStream> streamConsumer;

    public StreamResponse(ConsumerThrow<OutputStream> streamConsumer) {
        this.streamConsumer = streamConsumer;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        try {
            streamConsumer.accept(outputStream);
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
