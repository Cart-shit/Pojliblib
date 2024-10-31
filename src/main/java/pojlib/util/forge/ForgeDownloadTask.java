package pojlib.util.forge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import pojlib.install.ForgeMeta;
import pojlib.util.DownloadUtils;
import pojlib.util.FileUtils;
import pojlib.util.ForgeDownloadListener;

public class ForgeDownloadTask implements Runnable, DownloadUtils.DownloaderFeedback {
    private String mDownloadUrl;
    private String mFullVersion;
    private String mLoaderVersion;
    private String mGameVersion;
    private final ForgeDownloadListener mListener;
    public ForgeDownloadTask(ForgeDownloadListener listener, String forgeVersion) {
        this.mListener = listener;
        this.mDownloadUrl = ForgeUtils.getInstallerUrl(forgeVersion);
        this.mFullVersion = forgeVersion;
    }

    public ForgeDownloadTask(ForgeDownloadListener listener, String gameVersion, String loaderVersion) {
        this.mListener = listener;
        this.mLoaderVersion = loaderVersion;
        this.mGameVersion = gameVersion;
    }
    @Override
    public void run() {
        if(determineDownloadUrl()) {
            downloadForge();
        }
    }

    private void downloadForge() {
        try {
            File destinationFile = new File(FileUtils.DIR_CACHE, "forge-installer.jar");
            byte[] buffer = new byte[8192];
            DownloadUtils.downloadFileMonitored(mDownloadUrl, destinationFile, buffer, this);
            mListener.onDownloadFinished(destinationFile);
        }catch (FileNotFoundException e) {
            mListener.onDataNotAvailable();
        } catch (IOException e) {
            mListener.onDownloadError(e);
        }
    }

    public boolean determineDownloadUrl() {
        if(mDownloadUrl != null && mFullVersion != null) return true;
        try {
            if(!findVersion()) {
                mListener.onDataNotAvailable();
                return false;
            }
        }catch (IOException e) {
            mListener.onDownloadError(e);
            return false;
        }
        return true;
    }

    public boolean findVersion() throws IOException {
        ForgeMeta.ForgeVersion[] forgeVersions = ForgeMeta.getVersions();
        if(forgeVersions == null) return false;
        String versionStart = mGameVersion+"-"+mLoaderVersion;
        for(ForgeMeta.ForgeVersion versionName : forgeVersions) {
            if(!versionName.startsWith(versionStart)) continue;
            mFullVersion = versionName;
            mDownloadUrl = ForgeUtils.getInstallerUrl(mFullVersion);
            return true;
        }
        return false;
    }

}