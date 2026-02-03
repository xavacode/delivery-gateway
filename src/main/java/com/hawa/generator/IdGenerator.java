package com.hawa.generator;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Enumeration;

public class IdGenerator {

    private static final long EPOCH = 1730000000L; // custom epoch (e.g., 2024-10-28)
    private static final long MACHINE_ID_BITS = 10L;
    private static final long SEQUENCE_BITS = 22L;

    private static final int MAX_MACHINE_ID = (int)(Math.pow(2, MACHINE_ID_BITS) - 1);
    private static final int MAX_SEQUENCE = (int)(Math.pow(2, SEQUENCE_BITS) - 1);

    private final long nodeId;
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public IdGenerator() {
        this.nodeId = createNodeId();
    }

    public synchronized long nextId() {
        long currentTimestamp = currentTime();

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) currentTimestamp = waitNextSecond(currentTimestamp);
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - EPOCH) << (MACHINE_ID_BITS + SEQUENCE_BITS))
                | (nodeId << SEQUENCE_BITS)
                | sequence;
    }

    private long waitNextSecond(long ts) {
        while (ts <= lastTimestamp) ts = currentTime();
        return ts;
    }

    private long currentTime() {
        return System.currentTimeMillis() / 1000; // use seconds, not ms
    }

    private int createNodeId() {
        int nodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for(int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X", mac[i]));
                    }
                }
            }
            nodeId = sb.toString().hashCode();
        } catch (Exception ex) {
            nodeId = (new SecureRandom().nextInt());
        }
        nodeId = nodeId & MAX_MACHINE_ID;
        return nodeId;
    }

}
