/**
 * MIT License
 *
 * <p>Copyright (c) 2021 mixmicro
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lyx.component.skinny.archivers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * {@link ArchiveOutputStream} Archive output stream implementations are expected to override the
 * {@link #write(byte[], int, int)} method to improve performance.
 * They should also override {@link #close()} to ensure that any necessary
 * trailers are added.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/13
 */
public abstract class ArchiveOutputStream extends OutputStream {
    /**
     * Temporary buffer used for the {@link #write(int)} method.
     */
    private final byte[] oneByte = new byte[1];
    static final int BYTE_MASK = 0xFF;

    /**
     * holds the number of bytes written to this stream.
     */
    private long bytesWritten;

    public abstract void putArchiveEntry(ArchiveEntity entity) throws IOException;

    public abstract void closeArchiveEntry() throws IOException;

    public abstract void finish() throws IOException;

    public abstract ArchiveEntity createArchiveEntry(File inputFile, String entryName) throws IOException;


    public ArchiveEntity createArchiveEntry(final Path inputPath, final String entryName, final LinkOption... options) throws IOException {
        return createArchiveEntry(inputPath.toFile(), entryName);
    }

    public void write(final int b) throws IOException {
        oneByte[0] = (byte) (b & BYTE_MASK);
        write(oneByte, 0, 1);
    }

    protected void count(final int written) {
        count((long) written);
    }


    protected void count(final long written) {
        if (written != -1) {
            bytesWritten += written;
        }
    }

    public int getCount() {
        return (int) bytesWritten;
    }

    public long getBytesWritten() {
        return bytesWritten;
    }

    /**
     * Whether this stream is able to write the given entry.
     *
     * @param archiveEntity
     * @return
     */
    public boolean canWriteEntryData(final ArchiveEntity archiveEntity) {
        return true;
    }
}
