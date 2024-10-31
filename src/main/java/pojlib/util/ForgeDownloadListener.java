package pojlib.util;

import java.io.File;

public interface ForgeDownloadListener {
    void onDownloadFinished(File downloadedFile);
    void onDataNotAvailable();
    void onDownloadError(Exception e);
}
