package ai.maths.snn;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;

public class MyPipedOutputStream extends OutputStream {

    protected HashSet<MyPipedInputStream> sinks;

    public MyPipedOutputStream(MyPipedInputStream sink) {
        sinks = new HashSet<>();
        connect(sink);
    }

    public MyPipedOutputStream() {
        sinks = new HashSet<>();
    }

    public synchronized void connect(MyPipedInputStream sink) {
        if (sink == null) {
            throw new NullPointerException();
        }
        sinks.add(sink);
        sink.newConnection();
    }

    public synchronized void disconnect(MyPipedInputStream sink) {
        if (sink == null) {
            throw new NullPointerException();
        }
        sinks.remove(sink);
        sink.receivedLast();
    }

    public void write(int b) throws IOException {
        if (sinks.isEmpty()) {
            throw new IOException("Pipe not connected");
        }
        for (MyPipedInputStream sink : sinks) {
            if (!sink.closedByReader) {
                sink.receive(b);
            } else {
                disconnect(sink);
            }
        }
    }

    public void write(byte b[], int off, int len) throws IOException {
        if (sinks.isEmpty()) {
            throw new IOException("Pipe not connected");
        } else if (b == null) {
            throw new NullPointerException();
        } else if ((off < 0) || (off > b.length) || (len < 0) ||
                ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        for (MyPipedInputStream sink : sinks) {
            if (!sink.closedByReader) {
                sink.receive(b, off, len);
            } else {
                disconnect(sink);
            }
        }
    }

    public synchronized void flush() throws IOException {
        if (!sinks.isEmpty()) {
            for (MyPipedInputStream sink : sinks) {
                synchronized (sink) {
                    sink.notifyAll();
                }
            }
        }
    }

    public void close() throws IOException {
        if (!sinks.isEmpty()) {
            for (MyPipedInputStream sink : sinks) {
                sinks.remove(sink);
                sink.receivedLast();
            }
        }
    }
}

