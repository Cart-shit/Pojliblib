package pojlib.install;

import com.google.gson.annotations.SerializedName;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import pojlib.APIHandler;
import pojlib.util.Constants;

public class ForgeMeta extends DefaultHandler {
    private static final APIHandler handler = new APIHandler(Constants.FORGE_META_URL);


    public static class ForgeVersion {
        @SerializedName("version")
        public String version;
    }

    public static ForgeVersion[] getVersions() {
        return handler.get("forge-versions.json", ForgeMeta.ForgeVersion[].class);
    }

    public static ForgeMeta.ForgeVersion getLatestVersion() {
        ForgeMeta.ForgeVersion[] versions = getVersions();
        if (versions != null) return versions[0];
        return null;
    }

    public static VersionInfo getVersionInfo(QuiltMeta.QuiltVersion quiltVersion, String minecraftVersion) {
        return handler.get(String.format("versions/loader/%s/%s/profile/json", minecraftVersion, quiltVersion.version), VersionInfo.class);
    }
}
