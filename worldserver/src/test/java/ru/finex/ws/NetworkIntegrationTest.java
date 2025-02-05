package ru.finex.ws;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import ru.finex.core.Version;
import ru.finex.testing.container.Container;
import ru.finex.testing.container.ContainerType;
import ru.finex.testing.server.Server;
import ru.finex.ws.network.NetworkConfiguration;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/**
 * @author m0nster.mind
 */
@Slf4j
@Container({ContainerType.Redis, ContainerType.Database})
@Server
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NetworkIntegrationTest {

    @Inject
    private NetworkConfiguration networkConfiguration;

    @Order(0)
    @Test
    public void testConnection() throws Exception {
        try (Socket socket = new Socket()) {
            socket.connect(
                new InetSocketAddress(networkConfiguration.getHost(), networkConfiguration.getPort()),
                (int) TimeUnit.SECONDS.toMillis(4)
            );
        }
    }

    @Test
    @Timeout(10)
    public void testPingPong() throws Exception {
        try (SocketChannel socket = socketChannel(networkConfiguration)) {
            socket.write(inputTestDto());

            ByteBuffer buffer = ByteBuffer.allocate(8 * 1024).order(ByteOrder.LITTLE_ENDIAN);
            int readyBytes = socket.read(buffer);
            buffer.flip();
            log.info("Read {} bytes", readyBytes);

            int length = buffer.getShort() & 0xff;
            Assertions.assertEquals(readyBytes, length);
            buffer.limit(length);

            int opcode = buffer.getInt();
            Assertions.assertEquals(0x02, opcode);

            int versionLength = buffer.getInt();
            byte[] versionBytes = new byte[versionLength];
            buffer.get(versionBytes);
            String version = new String(versionBytes, StandardCharsets.UTF_8);
            Assertions.assertEquals(Version.getImplVersion(), version);
        }
    }

    private static SocketChannel socketChannel(NetworkConfiguration networkConfiguration) throws Exception {
        return SocketChannel.open(new InetSocketAddress(networkConfiguration.getHost(), networkConfiguration.getPort()));
    }

    private static ByteBuffer inputTestDto() {
        ByteBuffer buffer = ByteBuffer.allocate(8*1024)
            .order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(2); // skip packet header / packet size, write it later
        buffer.putInt(0x01); // opcode - see TestDeserializer (annotations)
        buffer.putInt(0x01); // InputTestDto::version
        buffer.putShort(0, (short) buffer.position()); // packet header / packet size
        buffer.flip();
        return buffer;
    }

}
