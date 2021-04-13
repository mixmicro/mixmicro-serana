package lyx.component.skinny.archivers.zip;

import lyx.component.skinny.archivers.ArchiveEntity;
import lyx.component.skinny.archivers.ArchiveInputStream;

import java.io.IOException;

/**
 * {@link ZipArchiveInputStream}
 *
 * @author <a href="mailto:siran0611@gmail.com">siran.yao</a>
 * @version ${project.version}
 * @date 2021/4/13
 */
public class ZipArchiveInputStream extends ArchiveInputStream {
    @Override
    public ArchiveEntity getNextEntity() throws IOException {
        return null;
    }
}
