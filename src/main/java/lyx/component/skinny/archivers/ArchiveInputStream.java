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

import com.sun.org.apache.bcel.internal.generic.FNEG;

import java.io.IOException;
import java.io.InputStream;

/**
 * {@link ArchiveInputStream} Archive input stream must override
 * the {@link #read(byte[], int, int)} or {@link #read()} method
 * so that reading from the stream generates EOF for the end of
 * data in each entry as well as at the end of file proper.
 *
 * <p>
 * The {@link #getNextEntry()} method is used to reset the input stream
 * ready for reading the data from the next entry.
 * <p>
 * The input stream classes must also implement a method with the signature:
 * </pre>
 * public static boolean matches(byte[] signature, int length)
 * </pre>
 * which is used by the {@link ArchiveStreamFactory} to autodetect the
 * archive type from the first few bytes of ta stream.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/13
 */
public abstract class ArchiveInputStream extends InputStream {
    private final byte[] single = new byte[1];
    private static final int BYTE_MASK = 0xFF;

    /**
     * holds the number of bytes read in this stream
     */
    private long bytesRead;

    /**
     * Returns the next Archive Entry in this Stream
     *
     * @return the next entry or {@code null} if there are no more entries.
     * @throws IOException if the next entity could not be read.
     */
    public abstract ArchiveEntity getNextEntity() throws IOException;


    /**
     * Reads a byte of data. This method will block until enough inout is available.
     * <p>
     * Simply calls the {@link #read(byte[], int, int)} method.
     * <p>
     * Must be overridden if the {@link #read(byte[], int, int)} method is not
     * overridden; may be overridden otherwise.
     *
     * @return the byte read, or -1 if end of input is reached.
     * @throws IOException
     * @todo Have a better method can read through async.
     */
    @Override
    public int read() throws IOException {
        final int num = read(single, 0, 1);
        return num == -1 ? -1 : single[0] & BYTE_MASK;
    }

    /**
     * Increment the counter of already read bytes.
     * Doesn't increment if the EOF has been hit (rea == -1)
     *
     * @param read the number of the bytes read.
     */
    protected void count(final long read) {
        if (read != -1) {
            bytesRead += read;
        }
    }

    /**
     * Decrements the counter of already read bytes.
     *
     * @param pushedBack the number of bytes pushed back.
     */
    protected void pushedBackBytes(final long pushedBack) {
        bytesRead -= pushedBack;
    }

    /**
     * Returns the current number of bytes read from this stream.
     *
     * @return the number of read bytes.
     */
    public int getCount() {
        return (int) bytesRead;
    }

    /**
     * Returns the current number of bytes read from the this stream.
     *
     * @return the number of read bytes.
     */
    public long getBytesRead() {
        return bytesRead;
    }

    /**
     * Whether this stream is able to read the given entry.
     *
     * @param archiveEntity need to read
     * @return
     */
    public boolean canReadEntryData(final ArchiveEntity archiveEntity) {
        return true;
    }
}
