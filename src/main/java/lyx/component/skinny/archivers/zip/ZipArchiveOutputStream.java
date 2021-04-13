package lyx.component.skinny.archivers.zip;

import lyx.component.skinny.archivers.ArchiveEntity;
import lyx.component.skinny.archivers.ArchiveOutputStream;

import java.io.File;
import java.io.IOException;

/**
 * {@link ZipArchiveOutputStream}
 *
 * @author <a href="mailto:siran0611@gmail.com">siran.yao</a>
 * @version ${project.version}
 * @date 2021/4/13
 */
public class ZipArchiveOutputStream extends ArchiveOutputStream {
    @Override
    public void putArchiveEntry(ArchiveEntity entity) throws IOException {

    }

    @Override
    public void closeArchiveEntry() throws IOException {

    }

    @Override
    public void finish() throws IOException {

    }

    @Override
    public ArchiveEntity createArchiveEntry(File inputFile, String entryName) throws IOException {
        return null;
    }
}
