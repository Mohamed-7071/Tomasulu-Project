package com.example.demo.logic;

/**
 * Simple direct-mapped data cache with byte addressing.
 * Address mapping: blockNumber = address / blockSize; index = blockNumber % numLines; tag = blockNumber / numLines.
 * On miss, miss latency = hitLatency + missPenalty (one lump sum).
 */
public class DataCache {
    private final int blockSize;      // bytes per block
    private final int cacheSize;      // total bytes
    private final int numLines;       // cacheSize / blockSize
    private final int hitLatency;
    private final int missPenalty;

    private final boolean[] valid;
    private final int[] tags;
    private final byte[][] data;

    public static class CacheLineSnapshot {
        public final int index;
        public final boolean valid;
        public final int tag;
        public final byte[] bytes;

        public CacheLineSnapshot(int index, boolean valid, int tag, byte[] bytes) {
            this.index = index;
            this.valid = valid;
            this.tag = tag;
            this.bytes = bytes;
        }
    }

    public static class AccessResult {
        public final boolean hit;
        public final int latency;
        public final int value; // 4-byte word (little endian)

        public AccessResult(boolean hit, int latency, int value) {
            this.hit = hit;
            this.latency = latency;
            this.value = value;
        }
    }

    public DataCache(int blockSize, int cacheSize, int hitLatency, int missPenalty) {
        this.blockSize = Math.max(4, blockSize); // enforce at least a word
        this.cacheSize = Math.max(this.blockSize, cacheSize);
        this.hitLatency = Math.max(1, hitLatency);
        this.missPenalty = Math.max(0, missPenalty);
        this.numLines = this.cacheSize / this.blockSize;

        this.valid = new boolean[this.numLines];
        this.tags = new int[this.numLines];
        this.data = new byte[this.numLines][this.blockSize];
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public int getHitLatency() {
        return hitLatency;
    }

    public int getMissPenalty() {
        return missPenalty;
    }

    public int getNumLines() {
        return numLines;
    }

    /** Allow external editing of a cache line for UI-driven tweaks. */
    public void setLine(int index, boolean validBit, int tagValue, byte[] bytes) {
        if (index < 0 || index >= numLines) return;
        valid[index] = validBit;
        tags[index] = Math.max(0, tagValue);
        if (bytes != null) {
            int len = Math.min(blockSize, bytes.length);
            System.arraycopy(bytes, 0, data[index], 0, len);
            for (int i = len; i < blockSize; i++) {
                data[index][i] = 0;
            }
        }
    }

    public AccessResult loadWord(int address, byte[] memory) {
        if (address < 0) address = 0;
        int blockNumber = address / blockSize;
        int index = blockNumber % numLines;
        int tag = blockNumber / numLines;
        int blockOffset = address % blockSize;

        boolean hit = valid[index] && tags[index] == tag;
        int latency = hit ? hitLatency : hitLatency + missPenalty;

        if (!hit) {
            // Bring block from memory
            int baseAddr = blockNumber * blockSize;
            for (int i = 0; i < blockSize; i++) {
                int memAddr = baseAddr + i;
                data[index][i] = (memAddr >= 0 && memAddr < memory.length) ? memory[memAddr] : 0;
            }
            valid[index] = true;
            tags[index] = tag;
        }

        // Read 4 bytes little-endian
        int b0 = getByteFromLine(index, blockOffset);
        int b1 = getByteFromLine(index, blockOffset + 1);
        int b2 = getByteFromLine(index, blockOffset + 2);
        int b3 = getByteFromLine(index, blockOffset + 3);
        int word = (b0 & 0xFF) | ((b1 & 0xFF) << 8) | ((b2 & 0xFF) << 16) | ((b3 & 0xFF) << 24);

        return new AccessResult(hit, latency, word);
    }

    public CacheLineSnapshot getLineSnapshot(int index) {
        if (index < 0 || index >= numLines) return new CacheLineSnapshot(index, false, 0, new byte[blockSize]);
        byte[] copy = new byte[blockSize];
        System.arraycopy(data[index], 0, copy, 0, blockSize);
        return new CacheLineSnapshot(index, valid[index], tags[index], copy);
    }

    private int getByteFromLine(int index, int offset) {
        int off = Math.max(0, Math.min(blockSize - 1, offset));
        return data[index][off];
    }

    public void reset() {
        for (int i = 0; i < numLines; i++) {
            valid[i] = false;
            tags[i] = 0;
            for (int j = 0; j < blockSize; j++) {
                data[i][j] = 0;
            }
        }
    }

    public void invalidateLine(int index) {
        if (index >= 0 && index < numLines) {
            valid[index] = false;
        }
    }
}
